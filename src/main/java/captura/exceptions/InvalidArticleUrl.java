package captura.exceptions;

public class InvalidArticleUrl extends RuntimeException {

    public InvalidArticleUrl(String message) {
        super(message);
    }
}
