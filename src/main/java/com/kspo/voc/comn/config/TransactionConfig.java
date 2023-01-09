package com.kspo.voc.comn.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * <pre>
 * com.kspo.voc.comn.config - TransactionConfig.java
 * </pre>
 *
 * @ClassName : TransactionConfig
 * @Description : 트랜잭션 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

//@SuppressWarnings("deprecation")
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
//	private TransactionManager transactionManager;
//	private UserTransaction transaction;
//	
//	@Bean(name = "logTransactionManager")
//	public DataSourceTransactionManager txManager(@Qualifier("logDataSource") DataSource dataSource) throws EgovBizException {
//		return new DataSourceTransactionManager(dataSource);
//	}
	@Bean(name = "txManager")
//	@Bean(name = "vocTransactionManager")
	DataSourceTransactionManager vocTxManager(@Qualifier("vocDataSource") DataSource dataSource)
			throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}
//	@Primary
//	@Bean(name = "txManager")
//	public PlatformTransactionManager chainedTransactionManager(
//			@Qualifier("logTransactionManager") PlatformTransactionManager transactionManager,
//			@Qualifier("vocTransactionManager") PlatformTransactionManager vocTransactionManager) {
//		
//		return new ChainedTransactionManager(transactionManager, vocTransactionManager);
//	}
	
//	@Bean(name = "userTransaction")
//	public UserTransaction userTransaction() throws Throwable {
//		UserTransactionImp userTransactionImp = new UserTransactionImp();
//		userTransactionImp.setTransactionTimeout(10000);
//		return userTransactionImp;
//	}

//	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
//	public TransactionManager atomikosTransactionManager() throws Throwable {
//		UserTransactionManager userTransactionManager = new UserTransactionManager();
//		userTransactionManager.setForceShutdown(false);
//		return userTransactionManager;
//	}
//
//	@Bean(name = "txManager")
//	@DependsOn({ "userTransaction", "atomikosTransactionManager" })
//	public PlatformTransactionManager transactionManager() throws Throwable {
//		UserTransaction userTransaction = userTransaction();
//		JtaTransactionManager manager = new JtaTransactionManager(userTransaction, atomikosTransactionManager());
//		return manager;
//	}
	
}
