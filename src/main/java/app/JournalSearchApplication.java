package app;

import captura.entities.ArticleEntity;
import captura.health.DefaultHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class JournalSearchApplication extends Application<JournalSearchConfiguration> {

    public static void main(String[] args) throws Exception {
        new JournalSearchApplication().run(args);
    }

    @Override
    public void run(JournalSearchConfiguration journalSearchConfiguration, Environment environment)
            throws Exception {
        environment.healthChecks().register("default", new DefaultHealthCheck());
    }

    @Override
    public void initialize(Bootstrap<JournalSearchConfiguration> bootstrap) {
        var hbnBundle = new HbnBundle(ArticleEntity.class);
        bootstrap.addBundle(hbnBundle);
        bootstrap.addBundle(
                GuiceBundle.builder()
                        .enableAutoConfig("captura.resources")
                        .printDiagnosticInfo()
                        .modules(new HbnModule(hbnBundle))
                        .build());
    }
}
