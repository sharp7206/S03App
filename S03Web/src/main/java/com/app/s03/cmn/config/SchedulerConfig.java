package com.app.s03.cmn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Bean
    public JobTask jobTask() {
        return new JobTask();
    }
    public static class JobTask {

        private boolean taskCompleted = false;
        public boolean isTaskCompleted() {
            return taskCompleted;
        }
    }
}
