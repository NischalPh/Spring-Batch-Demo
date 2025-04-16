package com.nischal.springbatchpoc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nischal Phuyal on 3/1/2025
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class ExportJobController {

    private final JobLauncher jobLauncher;
    private final Job exportUserJob;

    @GetMapping("/start")
    public ResponseEntity<String> startJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startTime", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(exportUserJob, jobParameters);
            return ResponseEntity.ok("Job started successfully: " + execution.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Job failed: " + e.getMessage());
        }
    }

}
