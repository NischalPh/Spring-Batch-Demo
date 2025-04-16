package com.nischal.springbatchpoc.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Nischal Phuyal on 3/1/2025
 */

@Slf4j
@Component
//@EnableScheduling
public class JobScheduler {

    private final JobLauncher jobLauncher;
    private final Job exportUserJob;

    public JobScheduler(JobLauncher jobLauncher, Job exportUserJob) {
        this.jobLauncher = jobLauncher;
        this.exportUserJob = exportUserJob;
    }

//    @Scheduled(cron = "0 0/1 * * * ?")
    public void runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startTime", System.currentTimeMillis())
                    .toJobParameters();

            log.info("Job started..");
            JobExecution execution = jobLauncher.run(exportUserJob, jobParameters);
            System.out.println("Scheduled Job Execution Status: " + execution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
