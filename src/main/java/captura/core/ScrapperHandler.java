package captura.core;

import org.jsoup.nodes.Element;

import java.io.IOException;

public abstract class ScrapperHandler extends Scrapper {

    protected Element content;

    protected abstract void execute() throws IOException, InvalidArticleObject;
}
