package com.app.s03.cmn.utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
public class MP3Extractor {

	public static void main(String[] args) {
        String folderPath = "D:\\01.기타\\2024년 12월 02일 신곡 [MP3]"; // 추출할 폴더 경로 입력
        String destinationFolderPath = "C:\\Temp\\mp3_ALL"; // mp3 파일을 옮길 폴더 경로 입력
        List<File> mp3Files = new ArrayList<>();
        extractMP3Files(new File(folderPath), mp3Files);

        System.out.println("추출된 MP3 파일:");
        for (File mp3File : mp3Files) {
            try {
	        	Path sourcePath = mp3File.toPath();
	            Path destinationPath = Paths.get(destinationFolderPath, mp3File.getName());
	            Files.move(sourcePath, destinationPath);
	            System.out.println("파일 이동 성공: " + mp3File.getName());
	        } catch (IOException e) {
	            System.err.println("파일 이동 실패: " + mp3File.getName());
	            e.printStackTrace();
	        }
        }
    }

    public static void extractMP3Files(File folder, List<File> mp3Files) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 재귀적으로 폴더 내부 탐색
                    extractMP3Files(file, mp3Files);
                } else {
                    // mp3 파일인지 확인 후 목록에 추가
                    if (file.getName().toLowerCase().endsWith(".mp3")) {
                        mp3Files.add(file);
                    }
                }
            }
        }
    }

}
