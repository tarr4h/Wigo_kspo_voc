package com.kspo.voc.comn.config;

import javax.sql.DataSource;

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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.kspo.voc.comn.log.LogDaoAspect;
import com.kspo.voc.sys.mapper.VocMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
@Primary
@Configuration("crmDatabaseConfig")
@MapperScan(basePackages = {"com.kspo"}, value = "최상위 패키지 경로", annotationClass = VocMapper.class, sqlSessionFactoryRef = "crmSqlSessionFactory"
	, basePackageClasses = MapperConfigurer.class)
public class DatabaseConfig {
	@Autowired
	private ApplicationContext applicationContext;

	@Value("${spring.datasource.driver-class-name}")
	private String dbDriverClassName;

	@Value("${spring.datasource.url}")
	private String dbURL;

	@Value("${spring.datasource.username}")
	private String userName;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.jndi-name}")
	private String jndiName;

	@Value("${spring.datasource.jndi-yn}")
	private String jdniYn;

	@Value("${spring.datasource.hikari.connection-timeout}")
	private int timeout;
	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private int poolSize;

	@Primary
	@Bean(name = "crmDataSource")
	DataSource dataSource() {
		if("Y".equals(jdniYn) ) {
			return getJndiSource();
		}
		
		if(poolSize <= 0)
			poolSize = 100;
		if(timeout <=0)
			timeout = 60000;
		HikariConfig hikariConfig = new HikariConfig();  // 3

		hikariConfig.setUsername(userName);
		hikariConfig.setPassword(password);
		hikariConfig.setJdbcUrl(dbURL);
		hikariConfig.setMaximumPoolSize(poolSize);  
		hikariConfig.setConnectionTimeout(timeout);
		hikariConfig.setLeakDetectionThreshold(30000);
		hikariConfig.setPoolName("wigo-voc-pool");

		return new HikariDataSource(hikariConfig);
	}
	
	private DataSource getJndiSource() {
		JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
		return jndiDataSourceLookup.getDataSource("java:comp/env/"+jndiName); 
		
	}
	@Primary
	@Bean(name = "crmSqlSessionFactory")
	SqlSessionFactory sqlSessionFactory(@Qualifier("crmDataSource") DataSource dataSource,@Qualifier("logDaoAspect") LogDaoAspect interceptor) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/kspo/sqlmap/config/sql-mapper-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/kspo/sqlmap/mappers/**/*_SqlMapper.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("com.kspo.**.model,com.kspo.voc.**.model,com.kspo.**.entity,com.kspo.voc.**.entity");
		sqlSessionFactoryBean.setPlugins(interceptor);
		return sqlSessionFactoryBean.getObject();
	}

//
	@Primary
	@Bean(name = "crmSqlSessionFactory")
	SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
