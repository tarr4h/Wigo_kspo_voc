package com.egov.voc.comn.config;


import com.egov.voc.sys.mapper.CrmLogMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.egovframe.rte.psl.dataaccess.mapper.MapperConfigurer;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 
 * <pre>
 * com.wigo.crm.config - DatabaseConfig.java
 * </pre>
 *
 * @ClassName : DatabaseConfig
 * @Description : DB 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */


/**
 * 22.11.01 : 전자정부프레임워크 Mapper 설정 적용(basePackageClasses)
 */
@Configuration
//@MapperScan(basePackages = {"com.wigo.voc"}, value = "최상위 패키지 경로", annotationClass = CrmLogMapper.class, sqlSessionFactoryRef = "logSqlSessionFactory")
@MapperScan(basePackages = {"com.egov.voc"}, value = "최상위 패키지 경로", annotationClass = CrmLogMapper.class, sqlSessionFactoryRef = "logSqlSessionFactory"
	, basePackageClasses = MapperConfigurer.class)
public class LogDatabaseConfig {
	@Autowired
	private ApplicationContext applicationContext;

	@Value("${spring.datasource.log-driver-class-name}")
	private String dbDriverClassName;

	@Value("${spring.datasource.log-url}")
	private String dbURL;

	@Value("${spring.datasource.log-username}")
	private String userName;

	@Value("${spring.datasource.log-password}")
	private String password;
	
	
	
	
	
	@Value("${spring.datasource.hikari.connection-timeout}")
	private int timeout;
	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private int poolSize;
	@Bean(name = "logDataSource")
	DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//		dataSource.setDriverClassName(dbDriverClassName);
//		dataSource.setUrl(dbURL);
//		dataSource.setUsername(userName);
//		dataSource.setPassword(password);
//		return dataSource;
		
		HikariConfig hikariConfig = new HikariConfig();  // 3
		if(poolSize <= 0)
			poolSize = 100;
		if(timeout <=0)
			timeout = 60000;
		
		hikariConfig.setUsername(userName);
		hikariConfig.setPassword(password);
		hikariConfig.setJdbcUrl(dbURL);
		hikariConfig.setConnectionTestQuery("SELECT 1 from dual");
		hikariConfig.setMaximumPoolSize(poolSize);  
		hikariConfig.setConnectionTimeout(timeout);
		hikariConfig.setLeakDetectionThreshold(30000);
		hikariConfig.setPoolName("wigo-voc-pool");
		return new HikariDataSource(hikariConfig);
	}

	@Bean(name = "logSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory(@Qualifier("logDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean
				.setConfigLocation(applicationContext.getResource("classpath:/egovframework/sqlmap/config/sql-mapper-config.xml"));
		sqlSessionFactoryBean
				.setMapperLocations(applicationContext.getResources("classpath:/egovframework/sqlmap/mappers/**/*_SqlMapper.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("com.egov.**.model,com.egov.voc.**.model,com.egov.**.entity,com.egov.voc.**.entity");
		return sqlSessionFactoryBean.getObject();
	}

//
	@Bean(name = "logSqlSessionTemplate")
	SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	

}
