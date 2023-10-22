package com.task.tracker.config;

import com.task.tracker.model.AuditingEntityListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableJpaAuditing
@EnableScheduling
public class ApplicationConfig {
    @Bean
    public AuditorAware<String> auditorAware(){ return new AuditingEntityListener();}
}
