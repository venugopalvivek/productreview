package com.intuit.vivek.updater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vvenugopal on 10/21/17.
 * This is done to reduce locking and also allows for any spam, abuse filtering
 * and possibly use of weighted averages
 */
@Component
public class ProductReviewScoreUpdaterJob {

    static final Logger logger = LoggerFactory.getLogger(ProductReviewScoreUpdaterJob.class);

    @Autowired
    private ProductReviewScoreUpdater updater;

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        this.executorService = Executors.newSingleThreadExecutor();
        UpdaterJob updaterJob = new UpdaterJob(updater);
        executorService.execute(updaterJob);
    }

    public static class UpdaterJob implements Runnable {

        private ProductReviewScoreUpdater updater;

        public UpdaterJob(ProductReviewScoreUpdater updater) {
            this.updater = updater;
        }

        @Override
        public void run() {
            logger.info("Running...");
            sleep(10000); //initial delay
            while(true) {
                updater.updateProductReviewScore();
                sleep(10000); // interval
                logger.info("Continuing...");
            }
        }

        private void sleep(int milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                logger.error("Unable to sleep thread for {} ms", milliseconds);
                logger.debug("Unable to sleep thread for {} ms", milliseconds, e);
            }
        }


    }

}
