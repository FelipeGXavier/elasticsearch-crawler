package captura.domain;

import captura.core.InvalidArticleObject;
import org.jsoup.Jsoup;

import java.nio.charset.StandardCharsets;

import static io.dropwizard.logback.shaded.guava.base.Preconditions.checkNotNull;

public class ArticleObject {

    private final String text;
    private static final int MAX_LENGTH = 15000;
    private static final int MIN_LENGTH = 1;

    private ArticleObject(String text) {
        this.text = text;
    }

    public static ArticleObject of(String text) throws InvalidArticleObject {
        checkNotNull(text);
        var object = Jsoup.parse(text).text();
        if (object.length() > MIN_LENGTH && object.length() < MAX_LENGTH) {
            var buffer = StandardCharsets.UTF_8.encode(object);
            var utf8EncodedString = StandardCharsets.UTF_8.decode(buffer).toString();
            utf8EncodedString = utf8EncodedString.replaceAll("/  +/g"," ");
            return new ArticleObject(utf8EncodedString);
        }
        throw new InvalidArticleObject("Invalid object text " + object);
    }

    public String getText() {
        return text;
    }
}
