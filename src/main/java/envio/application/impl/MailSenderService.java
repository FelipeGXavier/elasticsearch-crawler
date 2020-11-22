package envio.application.impl;

import busca.application.impl.JournalTextMatcher;
import envio.application.MailSender;
import envio.domain.User;
import envio.infra.mail.Sender;
import envio.infra.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

public class MailSenderService implements MailSender {

    private Logger logger = LoggerFactory.getLogger(MailSenderService.class);
    private UserRepository repository;
    private JournalTextMatcher matcher;
    private Sender sender;

    @Inject
    public MailSenderService(UserRepository repository, JournalTextMatcher matcher, Sender sender) {
        this.repository = repository;
        this.matcher = matcher;
        this.sender = sender;
    }

    @Override
    public void send() throws IOException {
        var records = this.repository.findAll();
        this.logger.info("records found " + records.size());
        for (User record : records) {
            var payload = this.matcher.find("sends", record);
            this.logger.info("notices found from record " + payload.get() + ", hash = " + payload.getHash());
            if (payload.getTotal() > 0) {
                this.sender.sendMail(record.getEmail(), payload.getHash());
            }
        }
    }
}
