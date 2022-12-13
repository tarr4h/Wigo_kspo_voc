package com.egov.voc.sys.aop;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import javax.annotation.Resource;
import java.util.Map;

@Configuration
public class TransactionAspect {
//	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	private static final String AOP_TRANSCTION_EXPRESSION = "execution(* com.egov..service.*Impl.*(..))";
	private static final String CRM_AOP_TRANSCTION_EXPRESSION = "execution(* com.egov.voc..*Service.*(..))";
	private static int TX_TIMEOUT = 600;
	@Resource(name = "txManager")
	private TransactionManager txManager;

//	@Qualifier("transactionManager")
//	private TransactionManager transactionManager;
//
//	@Qualifier("crmTransactionManager")
//	private TransactionManager crmTransactionManager;

	@Bean
	TransactionInterceptor transactionAdvice() {
		return advice(txManager);
	}

	@Bean
	Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_TRANSCTION_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}

	@Bean
	TransactionInterceptor crmTransactionAdvice() {
		return advice(txManager);
	}

	@Bean
	Advisor crmTransactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(CRM_AOP_TRANSCTION_EXPRESSION);

		return new DefaultPointcutAdvisor(pointcut, crmTransactionAdvice());
	}

	TransactionInterceptor advice(TransactionManager txManager) {
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
		DefaultTransactionAttribute readOnlyTransactionAttribute = new DefaultTransactionAttribute(
				TransactionDefinition.PROPAGATION_REQUIRED);
		readOnlyTransactionAttribute.setReadOnly(true);
		readOnlyTransactionAttribute.setTimeout(TX_TIMEOUT);

		RuleBasedTransactionAttribute writeTransactionAttribute = new RuleBasedTransactionAttribute();
		writeTransactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		writeTransactionAttribute.setTimeout(TX_TIMEOUT);

		Map<String, TransactionAttribute> txAttribute = new HashedMap<String, TransactionAttribute>();

		txAttribute.put("get*", readOnlyTransactionAttribute);
		txAttribute.put("select*", readOnlyTransactionAttribute);
		txAttribute.put("insert*", writeTransactionAttribute);
		txAttribute.put("update*", writeTransactionAttribute);
		txAttribute.put("delete*", writeTransactionAttribute);
		txAttribute.put("save*", writeTransactionAttribute);
		source.setNameMap(txAttribute);
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
		transactionInterceptor.setTransactionManager(txManager);
		transactionInterceptor.setTransactionAttributeSource(source);
		return transactionInterceptor;
	}
}
