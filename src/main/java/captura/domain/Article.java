package captura.domain;

public class Article {

    private ArticleObject text;
    private ArticleUrl url;

    private Article(ArticleObject text, ArticleUrl url) {
        this.text = text;
        this.url = url;
    }

    public static Article of(ArticleObject text, ArticleUrl url) {
        return new Article(text, url);
    }

    public String getTextObject() {
        return this.text.getText();
    }

    public String getTextUrl() {
        return this.url.getUrl();
    }
}
