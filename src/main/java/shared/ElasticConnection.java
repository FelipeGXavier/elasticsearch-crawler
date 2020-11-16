package shared;

import app.JournalSearchConfiguration;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

final public class ElasticConnection {

    private RestHighLevelClient client;
    private JournalSearchConfiguration configuration;

    public ElasticConnection(JournalSearchConfiguration configuration) {
        this.configuration = configuration;
        this.initClient();
    }

    private void initClient() {
        this.client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(this.configuration.getElasticConfiguration().getHost(), this.configuration.getElasticConfiguration().getPort(), "http"),
                        new HttpHost(this.configuration.getElasticConfiguration().getHost(), this.configuration.getElasticConfiguration().getPort(), "http")
                )
        );
    }

    public RestHighLevelClient getClient() {
        return client;
    }
}
