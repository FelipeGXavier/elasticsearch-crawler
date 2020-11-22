package captura.application.portals.diarios;

import captura.core.ScrapperHandler;
import captura.domain.Article;
import captura.domain.ArticleObject;
import captura.infra.persistence.ArticleRepository;
import captura.domain.ArticleUrl;
import captura.infra.persistence.ElasticSearchClient;
import captura.infra.JsonConverterUtil;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class DiarioRioGrandeDoSulHandler extends ScrapperHandler {

    private final JSONObject data;
    private final ArticleRepository repository;
    private final ElasticSearchClient client;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private final String ELASTICSEARCH_INDEX = "articles";

    public DiarioRioGrandeDoSulHandler(ArticleRepository repository, ElasticSearchClient client, JSONObject data) {
        this.repository = repository;
        this.data = data;
        this.client = client;
    }

    @Override
    protected void execute() throws IOException {
        this.client.typeOfResponse();
        final var baseUrl = "https://www.diariooficial.rs.gov.br/materia?id=%s";
        var id = this.data.getJSONObject("procergs").getInt("id");
        var url = String.format(baseUrl, id);
        var urlJsoNData = "https://secweb.procergs.com.br/doe/rest/public/materias/%s";
        var json = this.get(String.format(urlJsoNData, id), new HashMap<>());
        var document = JsonConverterUtil.toJson(json);
        if (document.has("pdf") && !document.getBoolean("pdf")) {
            try {
                var tag = Jsoup.parse(document.getString("conteudo"));
                var text = tag.text().toLowerCase();
                if (!text.contains("carga horária") && !text.contains("mandato público eletivo")) {
                    var articleObject = ArticleObject.of(tag.text());
                    var articleUrl = ArticleUrl.of(url);
                    var article = Article.of(articleObject, articleUrl);
                    var timestamp = LocalDateTime.now();
                    this.repository.create(article.getTextObject(), article.getTextUrl(), timestamp);
                    this.client.insert(ELASTICSEARCH_INDEX, this.toJson(article, timestamp));
                    this.logger.info("saved article for id " + this.client.getId());
                }
            } catch (Exception e) {
                this.logger.error("error to save article", e);
            }
        }
    }

    private JSONObject toJson(Article entity, LocalDateTime timestamp) {
        var json = new JSONObject();
        json.put("content", entity.getTextObject());
        json.put("url", entity.getTextUrl());
        json.put("created_at", timestamp);
        return json;
    }
}
