package app;

import captura.domain.ArticleRepository;
import com.google.inject.name.Names;
import io.dropwizard.jdbi3.JdbiFactory;
import ru.vyarus.dropwizard.guice.module.support.ConfigurationAwareModule;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;
import shared.ElasticConnection;

public class DependencyModule extends DropwizardAwareModule<JournalSearchConfiguration> implements ConfigurationAwareModule<JournalSearchConfiguration> {

    private JournalSearchConfiguration configuration;


    @Override
    protected void configure() {
        var environment = environment();
        final var factory = new JdbiFactory();
        final var jdbi = factory.build(environment, this.configuration.getDataSourceFactory(), "postgresql");
        bind(ArticleRepository.class).toInstance(jdbi.onDemand(ArticleRepository.class));
        bind(ElasticConnection.class).toProvider(() -> new ElasticConnection(this.configuration));
    }

    @Override
    public void setConfiguration(JournalSearchConfiguration configuration) {
        this.configuration = configuration;
    }
}
