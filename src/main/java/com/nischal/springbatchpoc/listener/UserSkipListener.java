
package com.nischal.springbatchpoc.listener;

import com.nischal.springbatchpoc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

/**
 * @author Nischal Phuyal on 3/10/2025
 */

@Slf4j
@Component
public class UserSkipListener implements SkipListener<User, User> {

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("User skipped while reading due to exception :: {}", t.getMessage());
    }

    @Override
    public void onSkipInWrite(User user, Throwable t) {
        log.error("User :: {} skipped while writing due to exception : {}", user.getName(), t.getMessage());
    }

    @Override
    public void onSkipInProcess(User user, Throwable t) {
        log.error("User :: {} skipped while processing due to exception : {}", user.getName(), t.getMessage());
    }

}
