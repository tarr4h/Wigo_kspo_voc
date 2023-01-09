package com.kspo.voc.comn.log;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kspo.voc.comn.util.SessionUtil;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.LoginUserVo;
import com.kspo.voc.sys.model.MenuBaseVo;
import com.kspo.voc.sys.service.ChngHstService;

/**
 * <pre>
 * com.kspo.base.common.log - LogDaoAspect.java
 * </pre>
 *
 * @ClassName : LogDaoAspect
 * @Description : LogDaoAspect
 * @author : 김성태
 * @date : 2021. 4. 23.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
//, @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,  RowBounds.class, ResultHandler.class})
//, @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,  RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class })
})
@Aspect
@Component("logDaoAspect")
public class LogDaoAspect implements Interceptor {

	@Autowired
    ChngHstService service;
	static boolean logMode = true;

	@Before(value = "execution(* *..*Controller.*(..))")
	public void sqlBeforeController(JoinPoint jp) {
		if (!logMode)
			return;
		ChangeLogVo hist = LogDaoLocal.get();
		if (hist.isInLogMode()) {
			addStack(jp.getStaticPart().toLongString(), jp.getArgs());
			return;
		}
		
		HttpServletRequest request = Utilities.getRequest();
		if (request == null)
			return;

		MenuBaseVo menu = (MenuBaseVo) request.getAttribute("currentMenu");
		if (menu == null)
			return;

		if (!"Y".equals(menu.getChngLogYn()))
			return;

		LoginUserVo user = SessionUtil.getLoginUser();
		String userId = user == null ? null : user.getLoginId();
		if (Utilities.isEmpty(userId))
			userId = "SYSTEM";
		String reqUrl = Utilities.getRequest() == null ? null : Utilities.getRequest().getRequestURI();
		hist.setInLogMode(true);
		hist.setUserId(userId);
		hist.setMenuId(menu.getMenuId());
		hist.setChngCallUrl(reqUrl);
		hist.addStack(jp.getStaticPart().toLongString(), jp.getArgs());
	}

	@AfterReturning(value = "execution(* *..*Controller.*(..))", returning = "result")
	public void sqlAfterController(JoinPoint jp, Object result) {
		ChangeLogVo hist = LogDaoLocal.get();
		if (!logMode)
			return;
		if (!hist.isInLogMode())
		{
			LogDaoLocal.clear();
			return;
		}
		if(!hist.isHasChange())
		{
			LogDaoLocal.clear();
			return;
		}
		hist.setResult(result);
		if (hist.getElement() != null) {
			setResult(result);
			return;
		}
		hist.makeParam();
		if (hist.isHasChange())
			addLog(hist);
		LogDaoLocal.clear();
	}

	@Before(value = "execution(* *..*Service.*(..))")
	public void sqlBeforeService(JoinPoint jp) {
		addStack(jp.getStaticPart().toLongString(), jp.getArgs());
	}

	@AfterReturning(value = "execution(* *..*Service.*(..))", returning = "result")
	public void sqlAfterService(JoinPoint jp, Object result) {
		setResult(result);
	}

	@Before(value = "execution(* *..*Dao.*(..))")
	public void sqlBeforeDao(JoinPoint jp) {
		addStack(jp.getStaticPart().toLongString(), jp.getArgs());
	}

	@AfterReturning(value = "execution(* *..*Dao.*(..))", returning = "result")
	public void sqlAfterDao(JoinPoint jp, Object result) {
		setResult(result);
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		ChangeLogVo hist = LogDaoLocal.get();
		if (!logMode)
			return invocation.proceed();
		if (!hist.isInLogMode())
			return invocation.proceed();

		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		String mapId = ms.getId();
		if(mapId.endsWith("HstDao.insert"))
			return invocation.proceed();
		Object parameterObject = (Object) args[1];
		
		BoundSql boundSql = ms.getBoundSql(parameterObject);
		String sql = getSql(boundSql, parameterObject);
		if(Utilities.isNotEmpty(sql))
			sql = sql.replace("\\n", "\n");
//		Object[] arg = { sql };
		String id = ms.getId();
		int idx = id.lastIndexOf(".");
		String clz = id.substring(0,idx);
		String method = id.substring(idx+1);
		hist.getElement().setParameters(sql);
		hist.getElement().setClassName(clz);
		hist.getElement().setMethodName(method);
		hist.setHasChange(true);
		return invocation.proceed();
	}

	private String getSql(BoundSql boundSql, Object parameter) {
		try {

			StringBuilder sql = new StringBuilder(boundSql.getSql());

			if (parameter != null) {
				List<Object> valueList = new ArrayList<Object>();
				for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
					String property = parameterMapping.getProperty();
					Object value = null;
					if (boundSql.hasAdditionalParameter(property)) {
						value = boundSql.getAdditionalParameter(property);
					} else if (parameter != null) {
						value = PropertyUtils.getProperty(parameter, property);
						valueList.add(value);
					}
				}

				for (Object value : valueList) {
					int start = sql.indexOf("?");
					int end = start + 1;
					if (value == null) {
						sql.replace(start, end, "NULL");
					} else if (value instanceof String) {
						sql.replace(start, end, "'" + value.toString() + "'");
					} else {
						sql.replace(start, end, value.toString());
					}
				}
			}
			return sql.toString();
		} catch (Exception ex) {
			return null;
		}
	}

	private void addStack(String execution, Object[] param) {
		if (!logMode)
			return;
		ChangeLogVo hist = LogDaoLocal.get();
		if (!hist.isInLogMode())
			return;
		hist.addStack(execution, param);
	}

	private void setResult(Object result) {
		if (!logMode)
			return;
		ChangeLogVo hist = LogDaoLocal.get();
		if (!hist.isInLogMode())
			return;
		hist.setResult(result);
	}

	private void addLog(ChangeLogVo logVo) {
		try {
			service.insert(logVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
