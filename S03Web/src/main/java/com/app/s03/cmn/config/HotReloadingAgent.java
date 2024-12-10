package com.app.s03.cmn.config;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class HotReloadingAgent {
	private static final String CLASS_PATH = "D:\\BNK\\workspaceS03\\S03Web\\target\\classes"; // 클래스 파일이 있는 디렉토리 경로
	
    @Autowired
    private ApplicationContext applicationContext;
    
	public HotReloadingAgent() {
		startFileMonitoring();
	}
    private void startFileMonitoring() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path directory = Paths.get(CLASS_PATH);
            directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey key;
                try {
                    key = watchService.poll(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    return;
                }

                if (key != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }

                        @SuppressWarnings("unchecked")
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path changedFile = directory.resolve(ev.context());
                        if (changedFile.toString().endsWith(".class")) {
                            reloadClass(changedFile.toFile());
                        }
                    }
                    key.reset();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void reloadClass(File file) {
        try {
            String className = file.getName().replace(".class", "");
            Class<?> clazz = applicationContext.getClassLoader().loadClass(className);
            Object newInstance = clazz.getDeclaredConstructor().newInstance();

            // 이전 인스턴스가 존재하는 경우, 해당 빈을 업데이트합니다.
            if (applicationContext.containsBean(className)) {
                ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
                configurableApplicationContext.getBeanFactory().registerSingleton(className, newInstance);
                log.info("changes file:", className);
            }

            System.out.println("Reloaded class: " + className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

}