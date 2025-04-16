package com.nischal.springbatchpoc.config;

import com.nischal.springbatchpoc.exception.InvalidDataException;
import com.nischal.springbatchpoc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Nischal Phuyal on 2/28/2025
 */

@Slf4j
@Component
public class UserProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) {
        log.info("User id :: {} processing", user.getId());
        if (user.getEmail().isBlank() || user.getPhone().isBlank()) {
            throw new InvalidDataException("Invalid user data");
        }

        user.setPhone(user.getPhone().replaceAll("[^0-9]", ""));

        String[] parts = user.getEmail().split("@");
        if (parts.length == 2) {
            user.setEmail(parts[0].charAt(0) + "****@" + parts[1]);
        }

        return user;
    }
}
