package com.app.s03.cmn.mapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
@Slf4j
/**
 * 스프링 5.3 이상에선 오류남.
 * @author sbcoba http://sbcoba.tistory.com/16
 */
public class RefreshableSqlSessionFactoryBeanN extends SqlSessionFactoryBean implements DisposableBean {
	@Value("${sqlmapMode}") 
	private String sqlmapMode;
    private SqlSessionFactory proxy;
    private int interval = 1000;
 
    private Timer timer;
    private TimerTask task;
 
    private Resource[] mapperLocations;
 
    private boolean running = false;
 
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();
    
    @Autowired
	private ApplicationContext applicationContext;
    @Override
    public void setMapperLocations(Resource ... mapperLocations) {
        super.setMapperLocations(mapperLocations);
        this.mapperLocations = mapperLocations;
    }
 
    public void setInterval(int interval) {
        this.interval = interval;
    }
 
    public void refresh() throws Exception {
        w.lock();
        try {
            super.afterPropertiesSet();
        } finally {
            w.unlock();
        }
        
        //log.info("sqlMapClient refreshed.");
    }
 
    @Override
	public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }
 
    private void setRefreshable() {
        proxy = (SqlSessionFactory) Proxy.newProxyInstance(SqlSessionFactory.class.getClassLoader(),
                new Class[] { SqlSessionFactory.class }, new InvocationHandler() {
                    @Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return method.invoke(getParentObject(), args);
                    }
                });
 
        task = new TimerTask() {
            private Map<Resource, Long> map = new HashMap<Resource, Long>();
 
            @Override
			public void run() {
                if (isModified()) {
                    try {
                        refresh();
                    } catch (Exception e) {
                        //log.error("caught exception", e);
                    }
                }
            }
 
            private boolean isModified() {
            	try {
            		Thread.sleep(10000);
            	}catch(Exception e) {
            		e.printStackTrace();
            	}
            	
                boolean retVal = false;
                
                String chkDirPath = "D:\\APP\\workspaceADM\\ADMWeb\\src\\main\\resources\\sqlmap";
                File file = new File(chkDirPath);
                File files[] = file.listFiles();
                log.info("chkDirPath>>>>>>"+file.length());
                if(files.length>0) {
                	//String sqlMapMode = "db"; //db, file
                	if("doc".equals(sqlmapMode)) {
                		try {
                			List<Resource> result = getDBInfo();
                    		setMapperLocations((Resource[])result.toArray(new Resource[0]));
                		}catch(Exception e) {
                    		e.printStackTrace();
                    	}finally {
                    	}
                		delDir(chkDirPath);
                		for(int i=0; i<mapperLocations.length; i++) {
                			Resource mappingLocation = mapperLocations[i];
                			retVal |= findModifiedResource(mappingLocation);
                		}
                	}else if("xml".equals(sqlmapMode)){ //file인경우는 xml을 읽는다.
                    	PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                    	try {
                    		Resource[] res = resolver.getResources("sqlmap/*/*.xml");
                    		setMapperLocations(res);
                    		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>from XMLFile Loading");
                    	}catch(Exception e) {
                    		e.printStackTrace();
                    	}
                    	for(int i=0; i<mapperLocations.length; i++) {
                			Resource mappingLocation = mapperLocations[i];
                			retVal |= findModifiedResource(mappingLocation);
                		}
                    }else if("path".equals(sqlmapMode)){ //file인경우는 xml을 읽는다.
                    	PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                    	String locPattern = "sqlmap/*/*.xml";
                    	String rootDirPath = determineRootDir(locPattern);
                    	String subPattern = locPattern.substring(rootDirPath.length());
                    	
                    	log.info("rootDirPath {}", rootDirPath); //sqlmap/
                    	log.info("subPattern {}", subPattern); //*/*.xml
                    	Set<Resource> result = new LinkedHashSet<>(16);
                    	try {
                    		Resource[] rootDirResources = resolver.getResources(rootDirPath);
                    		for(Resource rootDirResource : rootDirResources) {
                    			URL rootDirUrl = rootDirResource.getURL();
                    			result.addAll(doFindPathMatchingFileResources(rootDirResource, subPattern));
                    		}
                    		setMapperLocations(result.toArray(new Resource[0]));
                    		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>from XMLFile Loading");
                    	}catch(Exception e) {
                    		e.printStackTrace();
                    	}
                    }
                	return retVal;
                }                
                return retVal;
            }
 
            private boolean findModifiedResource(Resource resource) {
                boolean retVal = false;
                List<String> modifiedResources = new ArrayList<String>();
 
                try {
                    long modified = resource.lastModified();
 
                    if (map.containsKey(resource)) {
                        long lastModified = map.get(resource).longValue();
 
                        if (lastModified != modified) {
                            map.put(resource, new Long(modified));
                            modifiedResources.add(resource.getDescription());
                            retVal = true;
                        }
                    } else {
                        map.put(resource, new Long(modified));
                    }
                } catch (IOException e) {
                    //log.error("caught exception", e);
                }
                if (retVal) {
                    //log.info("modified files : " + modifiedResources);
                }
                return retVal;
            }
        };
 
        timer = new Timer(true);
        resetInterval();
 
    }
 
    private Object getParentObject() throws Exception {
        r.lock();
        try {
            return super.getObject();
        } finally {
            r.unlock();
        }
    }
 
    @Override
	public SqlSessionFactory getObject() throws Exception {
        if(this.proxy == null) {
            setRefreshable();
        }
        return this.proxy;
    }
 
    @Override
	public Class<? extends SqlSessionFactory> getObjectType() {
        return (this.proxy != null ? this.proxy.getClass() : SqlSessionFactory.class);
    }
 
    @Override
	public boolean isSingleton() {
        return true;
    }
 
    public void setCheckInterval(int ms) {
        interval = ms;
 
        if (timer != null) {
            resetInterval();
        }
    }
 
    private void resetInterval() {
        if (running) {
            timer.cancel();
            running = false;
        }
        if (interval > 0) {
            timer.schedule(task, 0, interval);
            running = true;
        }
    }
 
    @Override
	public void destroy() throws Exception {
        timer.cancel();
    }
    
    public void delDir(String dirPath) {
    	Path dir = Paths.get(dirPath);
    	List<Path> rslt;
    	try {
    		@SuppressWarnings("resource")
    		Stream<Path> walk = Files.walk(dir);
    		rslt = walk.filter(Files::isDirectory).collect(Collectors.toList());
    		
    		for(Path path : rslt) {
    			if(!path.equals(dir)) {
    				File delFile = new File(path.toString());
    				FileUtils.deleteDirectory(delFile);
    				log.info("delete path===="+dirPath);
    			}
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    protected String determineRootDir(String loc) {
    	int prefixEnd = loc.indexOf(':')+1;
    	int rootDirEnd = loc.length();
    	while(rootDirEnd>prefixEnd && getPathMatcher().isPattern(loc.substring(prefixEnd, rootDirEnd))) {
    		rootDirEnd = loc.lastIndexOf('/', rootDirEnd -2)+1;
    	}
    	if(rootDirEnd==0) {
    		rootDirEnd = prefixEnd;
    	}
    	return loc.substring(0, rootDirEnd);
    }
    
    public PathMatcher getPathMatcher() {
    	return this.pathMatcher;
    }
    
    private PathMatcher pathMatcher = new AntPathMatcher();
    
    protected Resource resolveRootDirResource(Resource ori) throws IOException{
    	return ori;
    }
    
    protected boolean isJarResource(Resource resource) throws IOException{
    	return false;
    }
    
    protected Set<Resource> doFindPathMatchingFileResources(Resource rootDirRes, String subPattern) throws IOException{
    	File rootDir;
    	try {
    		rootDir = rootDirRes.getFile().getAbsoluteFile();
    	}catch(FileNotFoundException e) {
    		log.debug("canno search for matching files underneath "+rootDirRes + " in the file System:"+e.getMessage());
        	return Collections.emptySet();
    	}catch(Exception e) {
    		log.debug("canno search for matching files underneath "+rootDirRes + " in the file System:"+e.getMessage());
        	return Collections.emptySet();
    	}
    	return doFindPathMatchingFileResources(rootDir, subPattern);
    }
    
    protected Set<Resource> doFindPathMatchingFileResources(File rootDir, String subPattern) throws IOException{
    	Set<File> matchingFiles = retrieveMatchingFiles(rootDir, subPattern);
    	Set<Resource> result = new LinkedHashSet<>(matchingFiles.size());
    	for(File file:matchingFiles) {
    		FileSystemResource fres = new FileSystemResource(file);
    		result.add(new FileSystemResource(file));
    	}
    	return result;
    }
    
    protected Set<File> retrieveMatchingFiles(File rootDir, String pattern) throws IOException{
    	if(!rootDir.exists()) {
    		if(log.isDebugEnabled()) {
    			log.debug("Skipping[]==="+rootDir.getAbsolutePath()+"not exist");
    		}
    		return Collections.emptySet();
    	}
    	
    	if(!rootDir.isDirectory()) {
    		if(log.isDebugEnabled()) {
    			log.debug("Skipping[]==="+rootDir.getAbsolutePath()+"this is not denote a directory");
    		}
    		return Collections.emptySet();
    	}
    	
    	if(!rootDir.canRead()) {
    		if(log.isDebugEnabled()) {
    			log.debug("Skipping[]==="+rootDir.getAbsolutePath()+"this is not allowed to read the directory");
    		}
    		return Collections.emptySet();
    	}
    	
    	String fullPattern = StringUtils.replace(rootDir.getAbsolutePath(),  File.separator, "/");
    	if(!pattern.startsWith("/")) {
    		fullPattern += "/";
    	}
    	
    	fullPattern = fullPattern + StringUtils.replace(pattern,  File.separator, "/");
    	
    	Set<File> result = new LinkedHashSet<>(8);
    	
    	doRetrieveMatchingFiles(fullPattern, rootDir, result);
    	return result;
    	
    }
    
    protected void doRetrieveMatchingFiles(String fullPattern, File dir, Set<File> result) throws IOException{
		if(log.isDebugEnabled()) {
			log.debug("search==="+dir.getAbsolutePath());
		}
		for(File content:listDirectory(dir)) {
    		log.info("retrieve the content"+content);
			String currPath = StringUtils.replace(content.getAbsolutePath(),  File.separator, "/");
			if(content.isDirectory() && getPathMatcher().matchStart(fullPattern, currPath+"/")) {
				if(!content.canRead()) {
					if(log.isDebugEnabled()) {
		    			log.debug("Skipping subdirectory ["+ dir.getAbsolutePath()+"] because the app is not allowed to read the direcoty");
		    		}
				}else {
					doRetrieveMatchingFiles(fullPattern, content, result);
				}
			}
			if(getPathMatcher().match(fullPattern, currPath)) {
				log.info("doRetrieveMatchingFiles content"+content);
				result.add(content);
			}
		}
    }
    
    protected File[] listDirectory(File dir) {
    	File[] files = dir.listFiles();
    	if(files ==null) {
    		log.info("cant retrieve the dir");
    		return new File[0];
    	}
    	Arrays.sort(files, Comparator.comparing(File::getName));
    	return files;
    }
    
    private List<Resource> getDBInfo() {
    	List<Resource> result = new ArrayList<>();
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	try {
            Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
            String url = "jdbc:log4jdbc:oracle:thin:@localhost:1521:DWCD";
            String id = "Z01S";
            String pw = "z01sadm";
            Map<String, Object> param = new HashMap<>();
            param.put("SYS_CD", "adm");
            String sql = "SELECT SYS_CD, MAPPER_ID, NAME_SPACE, LOC_PATH LOC_PATH, XML_TXT FROM TB_SYS_MAPPER_M ";
            conn = DriverManager.getConnection(url, id, pw);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //Configuration configuration = getObject().getConfiguration();
            ArrayList<DBXmlVO> list = new ArrayList();
            DBXmlVO dbxmlVO = new DBXmlVO(); 
            while(rs.next()) {
            	dbxmlVO = new DBXmlVO();
            	dbxmlVO.setFilePath(rs.getString("LOC_PATH"));
            	dbxmlVO.setNameSpace(rs.getString("NAME_SPACE"));
            	dbxmlVO.setXmlTxt(rs.getString("XML_TXT"));
            	list.add(dbxmlVO); 
            }
            String targetInfo = "D:\\APP\\workspaceADM\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ADMWeb\\WEB-INF\\classes\\sqlmap\\1\\";
            for(DBXmlVO bean :  list) {
            	byte[] bytes = bean.getXmlTxt().getBytes(StandardCharsets.UTF_8);
            	XmlResultVO dxr = new XmlResultVO(bytes, targetInfo +""+ bean.getNameSpace());
            	result.add(dxr);
            }
            /*
            //결과확인
            Collection<String> mappedStatName = configuration.getMappedStatementNames();
            for(String name : mappedStatName) {
            	System.out.println("Mapped Statement Name>>>>>>>>>>"+name);
            }
            */
        }catch(Exception e) {
        	
        }
    	return result;
    }        
}
