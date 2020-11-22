package app;

import busca.application.JournalFinder;
import busca.application.impl.JournalTextMatcher;
import captura.infra.persistence.ArticleRepository;
import envio.application.MailSender;
import envio.application.impl.MailSenderService;
import envio.infra.mail.SendGridSender;
import envio.infra.mail.Sender;
import envio.infra.persistence.UserBean;
import envio.infra.persistence.UserRepository;
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
        jdbi.registerRowMapper(new UserBean());
        bind(ArticleRepository.class).toInstance(jdbi.onDemand(ArticleRepository.class));
        bind(UserRepository.class).toInstance(jdbi.onDemand(UserRepository.class));
        bind(MailSender.class).to(MailSenderService.class);
        bind(ElasticConnection.class).toProvider(() -> new ElasticConnection(this.configuration));
        bind(JournalFinder.class).to(JournalTextMatcher.class);
        bind(Sender.class).to(SendGridSender.class);
    }

    @Override
    public void setConfiguration(JournalSearchConfiguration configuration) {
        this.configuration = configuration;
    }
}
