package captura.infra.jobs;

import captura.application.portals.diarios.DiarioRioGrandeDoSulInitializer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;

public class ScrapperJob implements Job {


    @Inject
    private DiarioRioGrandeDoSulInitializer scrapper;
    private final Logger logger = LoggerFactory.getLogger(ScrapperJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("start scheduled job to scrap data");
        try {
            this.scrapper.init();
        } catch (IOException e) {
            logger.error("error executing job", e);
        }
    }
}
