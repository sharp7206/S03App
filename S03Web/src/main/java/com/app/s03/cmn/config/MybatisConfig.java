/**
 * Copyright APP. All Rights Reserved.
 *
 * @since 2023
 * @filename MybatisConfig.java
 */
package com.app.s03.cmn.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.app.s03.cmn.mapper.CommonMapper;
import com.app.s03.cmn.mapper.RefreshableSqlSessionFactoryBean;

/**
 * SQL Mapper 설정, bean 작성
 * @author sbshin 
 *
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@MapperScan(basePackages = "com.app.s03.**", sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig{
	@Value("${db.driverClassName}") private String driverClassName;
	@Value("${db.url}") private String jdbcUrl;
	@Value("${db.username}") private String username;
	@Value("${db.password}") private String password;	
	@Value("${db.mapperLocations}") private String mapperLocations;
	//private ResourcePatternResolver resourceResolver;
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public DataSource dataSource() throws SQLException {
		
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(jdbcUrl);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(5);
		
		Properties databaseProperties = new Properties();
		databaseProperties.setProperty("cachePrepStmts", "true");
		databaseProperties.setProperty("prepStmtCacheSize", "250");
		databaseProperties.setProperty("prepStmtCacheSqlLimit", "2048");
		
		hikariConfig.setDataSourceProperties(databaseProperties);
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;
	}

	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new RefreshableSqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/spring/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocations));
		sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		sqlSessionFactoryBean.getObject().getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
		sqlSessionFactoryBean.getObject().getConfiguration().setCacheEnabled(false);
		sqlSessionFactoryBean.getObject().getConfiguration().setUseGeneratedKeys(false);
		return sqlSessionFactoryBean.getObject();
	}
	
	
	@Bean
	public CommonMapper commonMapper(SqlSessionFactory sqlSessionFactory) {

		CommonMapper commonMapper = new CommonMapper();
		commonMapper.setSqlSessionFactory(sqlSessionFactory);
		return commonMapper;
	}

	/** eclass Datasource end **/
	@Bean
	public SpringManagedTransactionFactory transactionFactory2(){
		SpringManagedTransactionFactory m= new SpringManagedTransactionFactory();
		return m;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		return new DataSourceTransactionManager(dataSource());
	}

}
