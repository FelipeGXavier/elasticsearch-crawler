package busca.application.impl;

import busca.application.JournalFinder;
import busca.application.QueryOperatorBuilder;
import busca.infra.SearchFilter;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import shared.ElasticConnection;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;

public class JournalTextMatcher implements JournalFinder {

    private ElasticConnection connection;

    @Inject
    public JournalTextMatcher(ElasticConnection connection) {
        this.connection = connection;
    }

    public JSONObject find(SearchFilter request, QueryOperatorBuilder operator, int page) throws IOException {
        var boolQueryBuilder = new BoolQueryBuilder();
        var parentBuilder = new SearchSourceBuilder();
        var highlightBuilder = new HighlightBuilder()
                .postTags("<em>")
                .preTags("</em>")
                .fragmentSize(15000)
                .field("content");
        parentBuilder.highlighter(highlightBuilder);
        boolQueryBuilder.must(operator.getElasticQuery());
        if (request.getTimestamp() != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("created_at").gte(request.getTimestamp().toString()));
        }
        parentBuilder.query(boolQueryBuilder).size(10).from((page * 10) - 10);
        var search = new SearchRequest();
        search.source(parentBuilder);
        var response = this.connection.getClient().search(search, RequestOptions.DEFAULT);
        return this.mapHitsToJson(response);
    }

    private JSONObject mapHitsToJson(SearchResponse response) {
        var hits = response.getHits().getHits();
        var data = new JSONObject();
        data.put("total", response.getHits().getTotalHits().value);
        var result = new JSONArray();
        Arrays.stream(hits).forEach(hit -> {
            var json = new JSONObject(hit.toString());
            var article = new JSONObject();
            article.put("url", json.getJSONObject("_source").getString("url"));
            article.put("created_at", json.getJSONObject("_source").getString("created_at"));
            article.put("content", json.getJSONObject("highlight").getJSONArray("content").get(0).toString());
            result.put(article);
        });
        data.put("result", result);
        return data;
    }

}
