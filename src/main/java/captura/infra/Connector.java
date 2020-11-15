package captura.infra;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Map;

public class Connector {

    private final int TIMEOUT_CONNECTION = 60000;

    protected String get(String url, Map<String, String> headers) throws IOException {
        return Jsoup.connect(url)
                .timeout(TIMEOUT_CONNECTION)
                .followRedirects(true)
                .ignoreContentType(true)
                .headers(headers)
                .method(Connection.Method.GET)
                .execute()
                .body();
    }

    protected String post(String url, Map<String, String> data, Map<String, String> headers)
            throws IOException {
        return Jsoup.connect(url)
                .timeout(TIMEOUT_CONNECTION)
                .followRedirects(true)
                .ignoreContentType(true)
                .headers(headers)
                .data(data)
                .method(Connection.Method.POST)
                .execute()
                .body();
    }
}
