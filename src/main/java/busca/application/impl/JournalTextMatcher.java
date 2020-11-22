package busca.application.impl;

import busca.application.JournalFinder;
import busca.application.QueryOperatorBuilder;
import busca.infra.SearchFilter;
import envio.domain.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.ElasticConnection;

import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

public class JournalTextMatcher implements JournalFinder {

    private ElasticConnection connection;
    private Logger logger = LoggerFactory.getLogger(JournalTextMatcher.class);

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
        return this.mapOperatorSearchHits(response);
    }

    public Payload find(String index, User entity) throws IOException {
        var boolQueryBuilder = new BoolQueryBuilder();
        var parentBuilder = new SearchSourceBuilder();
        var now = LocalDate.now().minusDays(2L);
        var highlightBuilder = new HighlightBuilder()
                .postTags("<em>")
                .preTags("</em>")
                .fragmentSize(15000)
                .field("content");
        parentBuilder.highlighter(highlightBuilder);
        boolQueryBuilder.must(entity.getOperator().getElasticQuery());
        boolQueryBuilder.must(QueryBuilders.rangeQuery("created_at").gte(now.toString()));
        parentBuilder.query(boolQueryBuilder).size(1000);
        var search = new SearchRequest();
        search.source(parentBuilder);
        var response = this.connection.getClient().search(search, RequestOptions.DEFAULT);
        var hash = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        var hits = response.getHits().getHits();
        var total = response.getHits().getTotalHits().value;
        for (SearchHit hit : hits) {
            var data = new JSONObject(hit.toString());
            var newUserArticle = new JSONObject();
            newUserArticle.put("content", data.getJSONObject("highlight").getJSONArray("content").get(0).toString());
            newUserArticle.put("url", data.getJSONObject("_source").getString("url"));
            newUserArticle.put("hash", hash);
            newUserArticle.put("company", entity.getCompany());
            newUserArticle.put("sent_at", now);
            this.insert(index, newUserArticle);
        }
        return new Payload(total, hash);
    }

    public JSONObject findEmailSubmissionsFromUser(String hash) throws IOException {
        var parentBuilder = new SearchSourceBuilder();
        var boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.termQuery("hash.keyword", hash));
        parentBuilder.query(boolQueryBuilder).size(1000);
        var search = new SearchRequest();
        search.source(parentBuilder);
        var response = this.connection.getClient().search(search, RequestOptions.DEFAULT);
        return this.mapUserNoticeHits(response);
    }

    private void insert(String index, JSONObject data) throws IOException {
        var request = new IndexRequest(index);
        request.source(data.toString(), XContentType.JSON);
        request.timeout(TimeValue.timeValueSeconds(10));
        var response = this.connection.getClient().index(request, RequestOptions.DEFAULT);
        this.logger.info("new user article saved " + response.getId());
    }

    private JSONObject mapOperatorSearchHits(SearchResponse response) {
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

    private JSONObject mapUserNoticeHits(SearchResponse response) {
        var hits = response.getHits().getHits();
        var data = new JSONObject();
        data.put("total", response.getHits().getTotalHits().value);
        var result = new JSONArray();
        Arrays.stream(hits).forEach(hit -> {
            var json = new JSONObject(hit.toString());
            var article = new JSONObject();
            article.put("url", json.getJSONObject("_source").getString("url"));
            article.put("company", json.getJSONObject("_source").getInt("company"));
            article.put("content", json.getJSONObject("_source").getString("content"));
            article.put("sent_at", json.getJSONObject("_source").getString("sent_at"));
            result.put(article);
        });
        data.put("result", result);
        return data;
    }

    public static class Payload {

        private long total;
        private String hash;

        public Payload(long total, String hash) {
            this.total = total;
            this.hash = hash;
        }

        public Payload get() {
            return this;
        }

        public long getTotal() {
            return total;
        }

        public String getHash() {
            return hash;
        }
    }

}
