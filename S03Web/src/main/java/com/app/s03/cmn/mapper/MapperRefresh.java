/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2023
 * @filename MapperRefresh.java
 */
package com.app.s03.cmn.mapper;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;  
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Properties;  
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;  
import org.apache.ibatis.executor.ErrorContext;  
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.NestedIOException;  
import org.springframework.core.io.Resource;  
import com.google.common.collect.Sets;
/**
 * <pre>
 * 패키지명: com.app.s03.cmn.mapper
 * 클래스명:  Refresh the MyBatis Mapper XML thread
 * 설명: 클래스에 대한 설명 작성해주세요.
 *
 * ==================================================================
 * 변경일자       변경자      변경내용
 * ------------------------------------------------------------------
 * 2023.12.26     hslee      1.0 최초작성
 * </pre>
 */
@PropertySource("classpath:application.properties")
public class MapperRefresh implements java.lang.Runnable {  
  
    public static Logger log = LoggerFactory.getLogger(MapperRefresh.class);  
    private static String filename = "classpath:application.properties";  
    private static Properties prop = new Properties();  
  
    @Value("${mybatis_enabled}") private static boolean mybatis_enabled;         // Whether to enable Mapper refresh thread function
    @Value("${mybatis_refresh}") private static boolean mybatis_refresh;         // After refresh is mybatis_enabled, whether the refresh thread is started
      
    @Value("${db.mapperLocations}") private Set<String> location;                // Mapper actual resource path  
      
    private Resource[] mapperLocations;     // Mapper resource path  
    private Configuration configuration;    // MyBatis configuration object 
      
    private Long beforeTime = 0L;           // Last refresh time
    private static int mybatis_delaySeconds;        // Delay refresh seconds
    private static int mybatis_sleepSeconds;        // sleep time  
    private static String mybatis_mappingPath;      // xml folder matching string, needs to be modified as needed
    static {  
            
        
        URL url = MapperRefresh.class.getClassLoader().getResource(filename);
        InputStream is;
		try {
			is = url.openStream();
			if (is == null) {
	        	log.warn("applicationConfig.properties not found.");
	        } else {
	            prop.load(is);
	        } 
		} catch (IOException e) {
			e.printStackTrace();
		}
		String value = getPropString("mybatis_enabled");
		System.out.println(value);
        mybatis_enabled = "true".equalsIgnoreCase(value);  
          
        mybatis_delaySeconds = getPropInt("mybatis_delaySeconds");  
        mybatis_sleepSeconds = getPropInt("mybatis_sleepSeconds");  
        mybatis_mappingPath = getPropString("mybatis_mappingPath");  
  
        mybatis_delaySeconds = mybatis_delaySeconds == 0 ? 50 : mybatis_delaySeconds;  
        mybatis_sleepSeconds = mybatis_sleepSeconds == 0 ? 3 : mybatis_sleepSeconds;  
        mybatis_mappingPath = StringUtils.isBlank(mybatis_mappingPath) ? "mappings" : mybatis_mappingPath;  
  
        log.debug("[mybatis_enabled] " + mybatis_enabled);  
        log.debug("[mybatis_delaySeconds] " + mybatis_delaySeconds);  
        log.debug("[mybatis_sleepSeconds] " + mybatis_sleepSeconds);  
        log.debug("[mybatis_mappingPath] " + mybatis_mappingPath);  
    }  
  
    public static boolean isRefresh() {  
        return mybatis_refresh;  
    }  
  
    public MapperRefresh(Resource[] mapperLocations, Configuration configuration) {  
        this.mapperLocations = mapperLocations;  
        this.configuration = configuration;  
    }  
  
    @Override  
    public void run() {  
  
        beforeTime = System.currentTimeMillis();  
  
        log.debug("[location] " + location);  
        log.debug("[configuration] " + configuration);  
  
        if (mybatis_enabled) {  
            // 启动刷新线程  
            final MapperRefresh runnable = this;  
            new Thread(new java.lang.Runnable() {  
                @Override  
                public void run() {  
                      
                    if (location == null){  
                        location = Sets.newHashSet();  
                        log.debug("MapperLocation's length:" + mapperLocations.length);  
                        for (Resource mapperLocation : mapperLocations) {  
                            String s = mapperLocation.toString().replaceAll("\\", "/");  
                            s = s.substring("file [".length(), s.lastIndexOf(mybatis_mappingPath) + mybatis_mappingPath.length());  
                            if (!location.contains(s)) {  
                                location.add(s);  
                                log.debug("Location:" + s);  
                            }  
                        }  
                        log.debug("Locarion's size:" + location.size());  
                    }  
  
                    try {  
                        Thread.sleep(mybatis_delaySeconds * 1000);  
                    } catch (InterruptedException e2) {  
                        e2.printStackTrace();  
                    }  
                    mybatis_refresh = true;  
  
                    System.out.println("========= Enabled mybatis_refresh mybatis mapper =========");  
  
                    while (true) {  
                        try {  
                            for (String s : location) {  
                                runnable.refresh(s, beforeTime);  
                            }  
                        } catch (Exception e1) {  
                            e1.printStackTrace();  
                        }  
                        try {  
                            Thread.sleep(mybatis_sleepSeconds * 1000); 
                        } catch (InterruptedException e) {  
                            e.printStackTrace();  
                        }  
  
                    }  
                }  
            }, "MyBatis-Mapper-Refresh").start();  
        }  
    }  
  
    /** 
     * 执行刷新 
     * @param filePath 刷新目录 
     * @param beforeTime 上次刷新时间 
     * @throws NestedIOException 解析异常 
     * @throws FileNotFoundException 文件未找到 
     * @author ThinkGem 
     */  
    @SuppressWarnings({ "rawtypes", "unchecked" })  
    private void refresh(String filePath, Long beforeTime) throws Exception {  
  
        // 本次刷新时间  
        Long refrehTime = System.currentTimeMillis();  
  
        // 获取需要刷新的Mapper文件列表  
        List<File> fileList = this.getRefreshFile(new File(filePath), beforeTime);  
        if (fileList.size() > 0) {  
            log.debug("Refresh file: " + fileList.size());  
        }  
        for (int i = 0; i < fileList.size(); i++) {  
            InputStream inputStream = new FileInputStream(fileList.get(i));  
            String resource = fileList.get(i).getAbsolutePath();  
            try {  
                  
                // 清理原有资源，更新为自己的StrictMap方便，增量重新加载  
                String[] mapFieldNames = new String[]{  
                    "mappedStatements", "caches",  
                    "resultMaps", "parameterMaps",  
                    "keyGenerators", "sqlFragments"  
                };  
                for (String fieldName : mapFieldNames){  
                    Field field = configuration.getClass().getDeclaredField(fieldName);  
                    field.setAccessible(true);  
                    Map map = ((Map)field.get(configuration));  
                    if (!(map instanceof StrictMap)){  
                        Map newMap = new StrictMap(StringUtils.capitalize(fieldName) + "collection");  
                        for (Object key : map.keySet()){  
                            try {  
                                newMap.put(key, map.get(key));  
                            }catch(IllegalArgumentException ex){  
                                newMap.put(key, ex.getMessage());  
                            }  
                        }  
                        field.set(configuration, newMap);  
                    }  
                }  
                  
                // 清理已加载的资源标识，方便让它重新加载。  
                Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");  
                loadedResourcesField.setAccessible(true);  
                Set loadedResourcesSet = ((Set)loadedResourcesField.get(configuration));  
                loadedResourcesSet.remove(resource);  
                  
                //重新编译加载资源文件。  
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration,   
                        resource, configuration.getSqlFragments());  
                xmlMapperBuilder.parse();  
            } catch (Exception e) {  
                throw new NestedIOException("Failed to parse mapping resource: '" + resource + "'", e);  
            } finally {  
                ErrorContext.instance().reset();  
            }  
//            System.out.println("Refresh file: " + mybatis_mappingPath + StringUtils.substringAfterLast(fileList.get(i).getAbsolutePath(), mybatis_mappingPath));  
            if (log.isDebugEnabled()) {  
                log.debug("Refresh file: " + fileList.get(i).getAbsolutePath());  
                log.debug("Refresh filename: " + fileList.get(i).getName());  
            }  
        }  
        // 如果刷新了文件，则修改刷新时间，否则不修改  
        if (fileList.size() > 0) {  
            this.beforeTime = refrehTime;  
        }  
    }  
      
    /** 
     * 获取需要刷新的文件列表 
     * @param dir 目录 
     * @param beforeTime 上次刷新时间 
     * @return 刷新文件列表 
     */  
    private List<File> getRefreshFile(File dir, Long beforeTime) {  
        List<File> fileList = new ArrayList<File>();  
  
        File[] files = dir.listFiles();  
        if (files != null) {  
            for (int i = 0; i < files.length; i++) {  
                File file = files[i];  
                if (file.isDirectory()) {  
                    fileList.addAll(this.getRefreshFile(file, beforeTime));  
                } else if (file.isFile()) {  
                    if (this.checkFile(file, beforeTime)) {  
                        fileList.add(file);  
                    }  
                } else {  
                    System.out.println("Error file." + file.getName());  
                }  
            }  
        }  
        return fileList;  
    }  
  
    /** 
     * 判断文件是否需要刷新 
     * @param file 文件 
     * @param beforeTime 上次刷新时间 
     * @return 需要刷新返回true，否则返回false 
     */  
    private boolean checkFile(File file, Long beforeTime) {  
        if (file.lastModified() > beforeTime) {  
            return true;  
        }  
        return false;  
    }  
  
    /** 
     * 获取整数属性 
     * @param key 
     * @return 
     */  
    private static int getPropInt(String key) {  
        int i = 0;  
        try {  
            i = Integer.parseInt(getPropString(key));  
        } catch (Exception e) {  
        }  
        return i;  
    }  
  
    /** 
     * 获取字符串属性 
     * @param key 
     * @return 
     */  
    private static String getPropString(String key) {  
        return prop == null ? null : prop.getProperty(key).trim();  
    }  
  
    /** 
     * 重写 org.apache.ibatis.session.Configuration.StrictMap 类 
     * 来自 MyBatis3.4.0版本，修改 put 方法，允许反复 put更新。 
     */  
    public static class StrictMap<V> extends HashMap<String, V> {  
  
        private static final long serialVersionUID = -4950446264854982944L;  
        private String name;  
  
        public StrictMap(String name, int initialCapacity, float loadFactor) {  
            super(initialCapacity, loadFactor);  
            this.name = name;  
        }  
  
        public StrictMap(String name, int initialCapacity) {  
            super(initialCapacity);  
            this.name = name;  
        }  
  
        public StrictMap(String name) {  
            super();  
            this.name = name;  
        }  
  
        public StrictMap(String name, Map<String, ? extends V> m) {  
            super(m);  
            this.name = name;  
        }  
  
        @SuppressWarnings("unchecked")  
        public V put(String key, V value) {  
            // ThinkGem 如果现在状态为刷新，则刷新(先删除后添加)  
            if (MapperRefresh.isRefresh()) {  
                remove(key);  
//                MapperRefresh.log.debug("refresh key:" + key.substring(key.lastIndexOf(".") + 1));  
            }  
            // ThinkGem end  
            if (containsKey(key)) {  
                throw new IllegalArgumentException(name + " already contains value for " + key);  
            }  
            if (key.contains(".")) {  
                final String shortKey = getShortName(key);  
                if (super.get(shortKey) == null) {  
                    super.put(shortKey, value);  
                } else {  
                    super.put(shortKey, (V) new Ambiguity(shortKey));  
                }  
            }  
            return super.put(key, value);  
        }  
  
        public V get(Object key) {  
            V value = super.get(key);  
            if (value == null) {  
                throw new IllegalArgumentException(name + " does not contain value for " + key);  
            }  
            if (value instanceof Ambiguity) {  
                throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name  
                        + " (try using the full name including the namespace, or rename one of the entries)");  
            }  
            return value;  
        }  
  
        private String getShortName(String key) {  
            final String[] keyparts = key.split("\\.");  
            return keyparts[keyparts.length - 1];  
        }  
  
        protected static class Ambiguity {  
            private String subject;  
  
            public Ambiguity(String subject) {  
                this.subject = subject;  
            }  
  
            public String getSubject() {  
                return subject;  
            }  
        }  
    }  
}