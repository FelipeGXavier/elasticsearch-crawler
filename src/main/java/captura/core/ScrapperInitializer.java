package captura.core;

import java.io.IOException;

public abstract class ScrapperInitializer extends Scrapper {

    public abstract void init() throws IOException, InvalidArticleObject;
}
