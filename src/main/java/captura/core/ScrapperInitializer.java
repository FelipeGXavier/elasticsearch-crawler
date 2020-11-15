package captura.core;

import captura.domain.InvalidArticleObject;
import captura.infra.Connector;

import java.io.IOException;

public abstract class ScrapperInitializer extends Connector {

    public abstract void init() throws IOException, InvalidArticleObject;
}
