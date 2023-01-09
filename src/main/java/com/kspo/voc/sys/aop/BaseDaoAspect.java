package com.kspo.voc.sys.aop;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.kspo.base.common.model.BaseVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.voc.comn.util.SessionUtil;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.LoginUserVo;

/**
 * <pre>
 * com.kspo.base.common.aop - BaseDaoAspect.java
 * </pre>
 *
 * @ClassName : BaseDaoAspect
 * @Description : BaseDaoAspect
 * @author : MKH
 * @date : 2021. 1. 11.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

@Aspect
@Component
public class BaseDaoAspect {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before(value = "execution(* *..*Dao.insert*(..))")
	public void insertBefore(JoinPoint jp) {
		try {
			String userId="";
			HttpServletRequest req =  Utilities.getRequest();
			if(req != null) {
				Map<String,Object> info = (Map<String, Object>) req.getAttribute("ticketInfo");
				if(info != null)
				{
					userId = (String) info.get("userId");
					if(Utilities.isEmpty(userId) || "SYSTEM".equals(userId))
						userId = req.getParameter("tutorId");
				}
			}
			if(Utilities.isEmpty(userId) || "SYSTEM".equals(userId)) {
				LoginUserVo user = SessionUtil.getLoginUser();
//				userId = user==null? null : user.getLoginId();
				userId = user==null? null : user.getUserId();
				if (Utilities.isEmpty(userId)) {
					userId = "SYSTEM";
				}
			}
			String dt = Utilities.getDateTimeString();
			Map map = getMapObject(jp.getArgs());
			if (map != null) {
				map.put("peerIp", Utilities.getPeerIp());
				map.put("regrId", userId);
				map.put("regDt", dt);
				map.put("amdrId", userId);
				map.put("amdDt", dt);
				return;
			}
			BaseVo bo = getBaseVo(jp.getArgs());
			if (bo != null) {
				bo.setPeerIp(Utilities.getPeerIp());
				bo.setRegrId(userId);
				bo.setRegDt(dt);
				bo.setAmdrId(userId);
				bo.setAmdDt(dt);
				return;
			}

		} catch (Exception ex) {
			Utilities.trace(ex);
		}
	}

	@Before(value = "execution(* *..*Dao.update*(..))")
	public void updateBefore(JoinPoint jp) {
		try {
			insertBefore(jp);
		} catch (Exception ex) {
			Utilities.trace(ex);
		}
	}

	@SuppressWarnings("rawtypes")
	@Before(value = "execution(* *..*Dao.select*(..)) || execution(* *..*Dao.get*(..))")
	public void selectBefore(JoinPoint jp) {
		try {
			Map searchObj = getMapObject(jp.getArgs());
			if (searchObj == null)
				return;
		} catch (Exception ex) {
			Utilities.trace(ex);
		}

	}

	
	
	@Before(value = "execution(* *..*Dao.select*List(..)) || execution(* *..*Dao.get*List(..))")
	public void selectListBefore(JoinPoint jp) {
		try {

			setPagination(jp);
		} catch (Exception ex) {
			Utilities.trace(ex);
		}

	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setPagination(JoinPoint jp) {
		Map searchObj = getMapObject(jp.getArgs());
		if (searchObj == null)
			return;
		Object pinfo = searchObj.get("pagination");
		
			
		if (pinfo == null) {
			if(searchObj instanceof EzMap) {
				pinfo = ((EzMap) searchObj).getPaginationInfo();
				((EzMap) searchObj).setPaginationInfo((EzPaginationInfo) pinfo);
			}
			else {
				pinfo = new EzPaginationInfo();
				searchObj.put("pagination", pinfo);
			}
		}
		if (pinfo instanceof EzPaginationInfo) {
			EzPaginationInfo page = (EzPaginationInfo) pinfo;
			Utilities.mapToBean(searchObj, page);
			if (page.getCurrentPageNo() < 1)
				page.setCurrentPageNo(1);
			if (page.getPageSize() < 1)
				page.setPageSize(5);
			if (page.getRecordCountPerPage() < 1)
				page.setRecordCountPerPage(10);
			Utilities.beanToMap(searchObj, page);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		for (Object key : searchObj.keySet()) {
			Object obj = searchObj.get(key);
			if (obj instanceof String || obj instanceof Number || obj instanceof Boolean || !(obj instanceof Object)) {
				param.put(key.toString(), obj);
			}
		}
		searchObj.put("searchParam", param);

	}

	@SuppressWarnings("rawtypes")
	private Map getMapObject(Object[] args) {

		if (args == null || args.length == 0) {
			return null;
		}
		for (Object arg : args) {
			if (arg instanceof EzMap) {
				return (Map) arg;
			} else if (arg instanceof Map) {
				return (Map) arg;
			}
		}
		return null;
	}

	private BaseVo getBaseVo(Object[] args) {

		if (args == null || args.length == 0) {
			return null;
		}

		for (Object arg : args) {
			if (arg instanceof BaseVo) {
				return (BaseVo) arg;
			}
		}

		return null;
	}
	
}
