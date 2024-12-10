package com.app.s03.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.app.s03.za.service.Z01Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulerService {
    @Autowired
    private Z01Service z01Service;

    @Autowired
    private TaskScheduler taskScheduler;

    // SampleTask 클래스의 패키지명과 클래스명을 지정합니다.
    private static final String SAMPLE_TASK_PACKAGE = "com.app.s03.schedule.Task.";

    // Map to hold scheduled tasks along with their corresponding job names
    private Map<String, ScheduledFuture<?>> scheduledTasksMap = new ConcurrentHashMap<>();

    // 스케줄링된 작업을 관리하기 위한 메서드
    public void updateSchedule() throws Exception{
        // 데이터베이스에서 스케줄 정보를 읽어옴
        List<Map<String, Object>> scheduleList = z01Service.searchZ01SchInfo();
        String jobName = "";
        // Remove existing scheduled tasks
        cancelAllTasks();
        // 스케줄링된 작업 정보를 업데이트
        for (Map<String, Object> scheduleInfo : scheduleList) {
            jobName = (String) scheduleInfo.get("JOB_NM");
            String cronExpression = (String) scheduleInfo.get("CRON_EXP");
            boolean enabled = "Y".equals(scheduleInfo.get("USE_YN"));

            if (enabled) {
                log.info("Scheduling task for: {}", jobName);
                scheduleTask(jobName, cronExpression);
            } else {
                log.info("Disabled task: {}", jobName);
            }
        }
    }

    // 작업을 스케줄링하는 메서드
    private void scheduleTask(String jobName, String cronExpression) {
        try {
            Class<?> taskClass = Class.forName((SAMPLE_TASK_PACKAGE + jobName).trim());
            Runnable taskInstance = (Runnable) taskClass.newInstance();
            CronTrigger cronTrigger = new CronTrigger(cronExpression);

            ScheduledFuture<?> scheduledTask = taskScheduler.schedule(taskInstance, cronTrigger);
            scheduledTasksMap.put(jobName, scheduledTask); // Store the scheduled task in the map
        } catch (Exception e) {
            log.error("Error scheduling task: {}", e.getMessage());
        }
    }

    private void cancelAllTasks() {
        // Cancel all scheduled tasks stored in the map
        for (Map.Entry<String, ScheduledFuture<?>> entry : scheduledTasksMap.entrySet()) {
            entry.getValue().cancel(false);
        }
        // Clear the map
        scheduledTasksMap.clear();
    }

    // 스케줄링된 작업을 실행하는 메서드
    public void executeScheduledTasks() {
        // 스케줄링된 작업을 실행하는 로직을 구현합니다.
        // 현재는 예시로 구현되지 않았습니다.
    	log.info(">>>>>>>>>>>>>>>>>>>>job start");;
    }
}
