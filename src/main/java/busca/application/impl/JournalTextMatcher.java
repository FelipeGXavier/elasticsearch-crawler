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

    public JSONArray find(SearchFilter request, QueryOperatorBuilder operator) throws IOException {
        var bool = new BoolQueryBuilder();
        var builder = new SearchSourceBuilder();
        var highlightBuilder = new HighlightBuilder()
                .postTags("<em>")
                .preTags("</em>")
                .fragmentSize(15000)
                .field("content");
        builder.highlighter(highlightBuilder);
        bool.must(operator.getElasticQuery());
        if (request.getTimestamp() != null) {
            bool.must(QueryBuilders.rangeQuery("created_at").gte(request.getTimestamp().toString()));
        }
        builder.query(bool);
        var search = new SearchRequest();
        search.source(builder);
        var response = this.connection.getClient().search(search, RequestOptions.DEFAULT);
        return this.mapHitsToJson(response);
    }

    private JSONArray mapHitsToJson(SearchResponse response) {
        var hits = response.getHits().getHits();
        var data = new JSONArray();
        Arrays.stream(hits).forEach(hit -> {
            var json = new JSONObject(hit.toString());
            var article = new JSONObject();
            article.put("url", json.getJSONObject("_source").getString("url"));
            article.put("created_at", json.getJSONObject("_source").getString("created_at"));
            article.put("content", json.getJSONObject("highlight").getJSONArray("content").get(0).toString());
            data.put(article);
        });
        return data;
    }

}
