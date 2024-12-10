package com.app.s03.cmn.mapper;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

/**
 * 스프링 5.3 이상에선 오류남.
 * @author sbcoba http://sbcoba.tistory.com/16
 */
public class RefreshableSqlSessionFactoryBean extends SqlSessionFactoryBean implements DisposableBean {

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
                boolean retVal = false;
                try {
                    applicationContext.getResources("classpath*:/sqlmap/*/*.xml");
                }catch(Exception e) {
                	//e.printStackTrace();
                }
                if (mapperLocations != null) {
                    for (int i = 0; i < mapperLocations.length; i++) {
                        Resource mappingLocation = mapperLocations[i];
                        retVal |= findModifiedResource(mappingLocation);
                    }  
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
    

}
