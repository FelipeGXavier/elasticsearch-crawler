package captura.infra;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.JSONObject;
import shared.ElasticConnection;

import javax.inject.Inject;
import java.io.IOException;

public class ElasticSearchClient {

    private RestHighLevelClient client;
    private IndexResponse response;

    @Inject
    public ElasticSearchClient(ElasticConnection connection) {
        this.client = connection.getClient();
    }

    public void insert(String index, JSONObject document) throws IOException {
        var request = new IndexRequest(index);
        request.source(document.toString(), XContentType.JSON);
        request.timeout(TimeValue.timeValueSeconds(10));
        this.response = this.client.index(request, RequestOptions.DEFAULT);
    }

    public String getId() {
        return this.response.getId();
    }

    public ResponseType typeOfResponse() {
        if (this.response != null) {
            var res = this.response.getResult();
            if (this.response.getShardInfo().getFailures().length > 0) {
                return ResponseType.ERROR;
            }
            if (res == DocWriteResponse.Result.CREATED) {
                return ResponseType.CREATED;
            } else if (res == DocWriteResponse.Result.UPDATED) {
                return ResponseType.UPDATED;
            } else if (res == DocWriteResponse.Result.NOOP) {
                return ResponseType.NOOP;
            }
            return ResponseType.OTHER;
        }
        return ResponseType.UNDEFINED;
    }

    enum ResponseType {
        CREATED, UPDATED, NOOP, ERROR, OTHER, UNDEFINED
    }

}
