package com.app.s03.schedule.Task;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleTask implements Runnable {

    // 작업 실행 여부를 나타내는 변수
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    //@Override
    public void run() {
        // 이미 실행 중인 경우 실행하지 않음
        synchronized (isRunning) {
            if (isRunning.get()) {
                log.info("SampleTask is already running. Skipping...");
                return;
            }
            isRunning.set(true);
        }

        try {
            LocalDateTime now = LocalDateTime.now();
            log.info("SampleTask Run >>> {}", now);
            // 작업이 끝나면 실행 중 상태를 false로 변경
        } finally {
            synchronized (isRunning) {
                isRunning.set(false);
            }
        }
    }
}
