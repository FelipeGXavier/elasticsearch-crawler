package envio.infra.mail;

import envio.application.MailSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import java.io.IOException;

public class MailSenderJob implements Job {

    private MailSender mailService;

    @Inject
    public MailSenderJob(MailSender mailService) {
        this.mailService = mailService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            this.mailService.send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
