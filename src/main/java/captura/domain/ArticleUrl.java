package captura.domain;

import captura.exceptions.InvalidArticleUrl;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;

import java.net.URI;
import java.net.URL;

import static io.dropwizard.logback.shaded.guava.base.Preconditions.checkNotNull;

public class ArticleUrl {

    private final String url;

    private ArticleUrl(String url) {
        this.url = url;
    }

    public static ArticleUrl of(String url) {
        checkNotNull(url);
        try {
            new URL(url);
            return new ArticleUrl(url);
        } catch (Exception e) {
            throw new InvalidArticleUrl("Invalid url " + url);
        }
    }

    public String getUrl() {
        return url;
    }
}
