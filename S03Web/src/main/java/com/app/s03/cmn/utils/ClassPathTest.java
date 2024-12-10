/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2024
 * @filename ClassPathTest.java
 */
package com.app.s03.cmn.utils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
/**
 * <pre>
 * 패키지명: com.app.s03.cmn.utils
 * 클래스명: ClassPathTest
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2024.02.21     hslee      1.0 최초작성
 * </pre>
 */
public class ClassPathTest {

	/**
	 * 메소드에 대한 설명 작성해주세요.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		// ApplicationContext를 생성합니다. 여기서는 ClassPathXmlApplicationContext를 사용합니다.
        ApplicationContext context = new ClassPathXmlApplicationContext();
     // 클래스 패스 상의 sqlmap 폴더에서 XML 파일을 찾아옵니다.
        Resource[] resources = context.getResources("classpath*:/sqlmap/*");

        // 찾아온 리소스들을 출력합니다.
        for (Resource resource : resources) {
            System.out.println("Found resource: " + resource.getFilename());
        }
	}

}
