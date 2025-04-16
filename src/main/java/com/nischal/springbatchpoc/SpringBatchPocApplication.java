package com.nischal.springbatchpoc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.nischal.springbatchpoc"})
@EnableBatchProcessing
public class SpringBatchPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchPocApplication.class, args);
    }

}
