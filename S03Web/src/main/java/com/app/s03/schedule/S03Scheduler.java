package com.app.s03.schedule;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.s03.schedule.service.SchedulerService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@Scope("singleton")
public class S03Scheduler {
    @Autowired
    private SchedulerService schedulerService;
/**
1. 가장 앞에 오는 단위는 초(Seconds)이다.
2. 두번째는 분(Minutes)을 나타낸다.
3. 세번째는 시(Hours)를 나타낸다.
4. 네번째는 일(Day-of-Month, DOM)을 나타낸다.
5. 다섯번째로 월(Month)에 대한 정보를 기술한다.
6. 여섯번째는 요일(Day of Week)을 나타낸다. 요일은 0~6의 숫자로 쓸 수도 있지만 "MON", "SUN"과 같이 요일의 약자로 사용할 수도 있다.
7. 마지막으로 일곱번째에는 연도(Year)가 온다. 연도는 optional이다.
*/
    @Scheduled(cron = "30 * * * * *") // 10초마다실행
    public void myTask() {
    	
        try {
            schedulerService.updateSchedule(); // 스케줄 정보를 업데이트
            schedulerService.executeScheduledTasks(); // 스케줄링된 작업 실행
        } catch (Exception e) {
            e.printStackTrace();
        }  
        
    	LocalDateTime now = LocalDateTime.now();
        log.info("SampleTask Run >>> {}", now);
    }
}
