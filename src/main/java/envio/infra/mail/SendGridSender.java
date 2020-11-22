package envio.infra.mail;

import app.JournalSearchConfiguration;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDate;

public class SendGridSender implements Sender {

    private Logger logger = LoggerFactory.getLogger(SendGridSender.class);
    private JournalSearchConfiguration configuration;

    @Inject
    public SendGridSender(JournalSearchConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void sendMail(String email, String hash) {
        var from = new Email(email);
        var to = new Email(email);
        var subject = "New Journal Notices! - " + LocalDate.now().toString();
        var accessUrl = "http://localhost:8080/v1/sender/mail/" + hash;
        var content = new Content("text/html", TemplateMail.getTemplate(accessUrl));
        var mail = new Mail(from, subject, to, content);
        var sendgrid = new SendGrid(this.configuration.getApiKey());
        var request = new Request();
        Response response = new Response();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendgrid.api(request);
            logger.info("response from email sent, status = " + response.getStatusCode() + ", email = " + email, ", hash = " + hash);
        } catch (Exception e) {
            logger.error("error to send email, status = " + response.getStatusCode() + ", email = " + email, ", hash = " + hash, e);
        }
    }
}
