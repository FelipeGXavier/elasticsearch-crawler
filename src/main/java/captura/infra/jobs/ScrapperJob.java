package captura.infra.jobs;

import captura.application.portals.diarios.DiarioRioGrandeDoSulInitializer;
import captura.core.InvalidArticleObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import java.io.IOException;

public class ScrapperJob implements Job {


    @Inject
    private DiarioRioGrandeDoSulInitializer scrapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            this.scrapper.init();
        } catch (IOException | InvalidArticleObject e) {
            e.printStackTrace();
        }
    }
}
