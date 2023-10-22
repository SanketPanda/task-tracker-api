package com.task.tracker.model;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditingEntityListener implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        System.out.println("Username to set is - "+ SecurityContextHolder.getContext().getAuthentication().getName());
        return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
