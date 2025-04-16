package com.nischal.springbatchpoc.config;

import com.nischal.springbatchpoc.exception.ConflictException;
import com.nischal.springbatchpoc.exception.InvalidDataException;
import com.nischal.springbatchpoc.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.RetryListener;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Nischal Phuyal on 3/1/2025
 */

@Configuration
@RequiredArgsConstructor
public class StepConfig {

    private final SkipListener skipListener;
    private final ChunkListener chunkListener;
    private final RetryListener retryListener;
    private final TaskExecutor taskExecutor;

    @Bean
//    @JobScope
    public Step exportUserStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               ItemWriter<User> writer,
                               JdbcPagingItemReader<User> reader,
                               UserProcessor processor) {

        return new StepBuilder("exportUserStep", jobRepository)
                .<User, User>chunk(4, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retry(InvalidDataException.class)
                .retryLimit(3)
                .skip(InvalidDataException.class)
                .skipLimit(5)
                .listener(skipListener)
//                .noRollback(InvalidDataException.class)
//                .listener(chunkListener)
                .listener(retryListener)
//                .taskExecutor(taskExecutor)
                .build();
    }

}
