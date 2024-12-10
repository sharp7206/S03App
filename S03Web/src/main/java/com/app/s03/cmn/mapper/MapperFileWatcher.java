/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename MapperFileWatcher.java
 */
package com.app.s03.cmn.mapper;
import java.io.IOException;
import java.nio.file.*;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 * 패키지명: com.app.s03.cmn.mapper
 * 클래스명: MapperFileWatcher
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.01.30     hslee      1.0 최초작성
 * </pre>
 */
@PropertySource("classpath:application.properties")
@Slf4j
public class MapperFileWatcher {
	@Value("${db.mapperLocations}") private static String location;                // Mapper actual resource path 
	/**
	 * 메소드에 대한 설명 작성해주세요.
	 * 
	 * @param args
	 */
    public static void main(String[] args) throws IOException, InterruptedException {
    	log.info(">>>>>>>>>>>>>>{}", location);
        Path mapperDirectory = Paths.get(location);

        // Create a WatchService
        WatchService watchService = FileSystems.getDefault().newWatchService();

        // Register the directory with the WatchService for modification events
        mapperDirectory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        // Start watching for changes
        while (true) {
            WatchKey key = watchService.take(); // Wait for a watch key to be signaled
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue; // Ignore overflow events
                }
                // Check if the event is for a modified mapper XML file
                Path changedFile = (Path) event.context();
                if (changedFile.toString().endsWith(".xml")) {
                    refreshSqlSessionFactory();
                }
            }
            key.reset(); // Reset the key to receive further watch events
        }
    }

    private static void refreshSqlSessionFactory() {
    	log.info(">>>>>>>>>>>>>>");
        // Implement logic to refresh the SqlSessionFactory
        // This could involve recreating the SqlSessionFactory with the updated mapper XML files
        // Example: sqlSessionFactory = createSqlSessionFactory();
    }

}
