package com.nischal.springbatchpoc.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

/**
 * @author Nischal Phuyal on 3/10/2025
 */

@Slf4j
@Configuration
public class RetryListenerConfig {

    @Bean
    public RetryListener retryListener() {
        return new RetryListener() {

            @Override
            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                log.info("Opening retry attempt {}", context.getRetryCount());
                return true;
            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                int retryCount = context.getRetryCount();
                log.warn(" Retrying operation. Attempt: {} | Exception: {}", retryCount, throwable.getMessage());
            }
        };
    }

}
