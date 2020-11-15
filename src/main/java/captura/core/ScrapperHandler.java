package captura.core;

import captura.domain.InvalidArticleObject;
import captura.infra.Connector;
import org.jsoup.nodes.Element;

import java.io.IOException;

public abstract class ScrapperHandler extends Connector {

    protected Element content;

    protected abstract void execute() throws IOException, InvalidArticleObject;

}
