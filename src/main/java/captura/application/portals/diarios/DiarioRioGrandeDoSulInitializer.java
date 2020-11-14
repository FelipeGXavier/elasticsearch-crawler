package captura.application.portals.diarios;

import captura.core.InvalidArticleObject;
import captura.core.JsonConverterUtil;
import captura.core.ScrapperInitializer;
import captura.infra.ArticleDAO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class DiarioRioGrandeDoSulInitializer extends ScrapperInitializer {

    private final ArticleDAO repository;

    @Inject
    public DiarioRioGrandeDoSulInitializer(ArticleDAO model) {
        this.repository = model;
    }

    @Override
    public void init() throws IOException, InvalidArticleObject {
        int page = 1;
        var data = this.getJsonData(page);
        this.iterateJsonDataToScrap(data.getJSONArray("collection"));
        var lastPage =
                (int) Math.ceil((double) data.getInt("collectionSize") / data.getInt("pageSize"));
        if (lastPage > 1) {
            while (page <= lastPage) {
                page++;
                data = this.getJsonData(page);
                this.iterateJsonDataToScrap(data.getJSONArray("collection"));
            }
        }
    }

    public JSONObject getJsonData(int page) throws IOException {
        var url = this.formatRequestUrl(page);
        var json = this.get(url, new HashMap<>());
        return JsonConverterUtil.toJson(json);
    }

    private void iterateJsonDataToScrap(JSONArray data) throws IOException, InvalidArticleObject {
        for (var i = 0; i < data.length(); i++) {
            new DiarioRioGrandeDoSulHandler(this.repository, new JSONObject(data.get(i).toString()))
                    .execute();
        }
    }

    private String formatRequestUrl(int page) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        var now = formatter.format(LocalDate.now().minusDays(1L));
        var baseUrl = "https://secweb.procergs.com.br";
        var searchUrl = "/doe/rest/public/materias/?page=%d&tipoDiario=DOE&dataIni=%s&dataFim=%s";
        return String.format(baseUrl + searchUrl, page, now, now);
    }
}
