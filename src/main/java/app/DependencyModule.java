package app;

import captura.domain.ArticleRepository;
import io.dropwizard.jdbi3.JdbiFactory;
import ru.vyarus.dropwizard.guice.module.support.ConfigurationAwareModule;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class DependencyModule extends DropwizardAwareModule<JournalSearchConfiguration> implements ConfigurationAwareModule<JournalSearchConfiguration> {

    private JournalSearchConfiguration configuration;


    @Override
    protected void configure() {
        var environment = environment();
        final var factory = new JdbiFactory();
        final var jdbi = factory.build(environment, this.configuration.getDataSourceFactory(), "postgresql");
        bind(ArticleRepository.class).toInstance(jdbi.onDemand(ArticleRepository.class));
    }

    @Override
    public void setConfiguration(JournalSearchConfiguration configuration) {
        this.configuration = configuration;
    }
}
