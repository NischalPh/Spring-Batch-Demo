package com.nischal.springbatchpoc.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nischal Phuyal on 3/9/2025
 */

@Slf4j
@Component
public class ChunkLoggingListener implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
        log.info("New item for chunk processing. Read count :: {}", context.getStepContext().getStepExecution().getReadCount());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        log.info("Completed chunk processing. {} items processed.", context.getStepContext().getStepExecution().getWriteCount());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        List<Throwable> failureExceptions = context.getStepContext().getStepExecution().getFailureExceptions();
        if (!failureExceptions.isEmpty()) {
            failureExceptions.forEach(e -> log.error("Error occurred during chunk processing. Message :: {}", e.getMessage()));

        } else {
            log.error("Chunk failed but exception message not available...");
        }
    }
}
