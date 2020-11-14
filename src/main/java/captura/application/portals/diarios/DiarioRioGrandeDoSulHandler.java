package captura.application.portals.diarios;

import captura.converters.ArticleConverter;
import captura.core.InvalidArticleObject;
import captura.core.JsonConverterUtil;
import captura.core.ScrapperHandler;
import captura.domain.Article;
import captura.domain.ArticleObject;
import captura.domain.ArticleUrl;
import captura.infra.ArticleDAO;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;

public class DiarioRioGrandeDoSulHandler extends ScrapperHandler {

    private JSONObject data;
    private ArticleDAO repository;

    public DiarioRioGrandeDoSulHandler(ArticleDAO repository, JSONObject data) {
        this.repository = repository;
        this.data = data;
    }

    @Override
    protected void execute() throws IOException {
        final var baseUrl = "https://www.diariooficial.rs.gov.br/materia?id=%s";
        var id = this.data.getJSONObject("procergs").getInt("id");
        var url = String.format(baseUrl, id);
        var urlJsoNData = "https://secweb.procergs.com.br/doe/rest/public/materias/%s";
        var json = this.get(String.format(urlJsoNData, id), new HashMap<>());
        var document = JsonConverterUtil.toJson(json);
        if (document.has("pdf") && !document.getBoolean("pdf")) {
            var session = this.repository.getSession().beginTransaction();
            try {
                var tag = Jsoup.parse(document.getString("conteudo"));
                var articleObject = ArticleObject.of(tag.text());
                var articleUrl = ArticleUrl.of(url);
                var article = Article.of(articleObject, articleUrl);
                this.repository.persist(ArticleConverter.toEntityArticle(article));
                session.commit();
            } catch (Exception e) {
                session.rollback();
            }
        }
    }
}
