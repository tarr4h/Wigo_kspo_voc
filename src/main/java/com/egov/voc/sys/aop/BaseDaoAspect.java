package com.egov.voc.sys.aop;


import com.egov.base.common.model.BaseVo;
import com.egov.base.common.model.EzMap;
import com.egov.base.common.model.EzPaginationInfo;
import com.egov.voc.comn.util.SessionUtil;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.model.CrmLoginUserVo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * com.wigo.crm.common.aop - BaseDaoAspect.java
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
			String userCd="";
			HttpServletRequest req =  Utilities.getRequest();
			if(req != null) {
				Map<String,Object> info = (Map<String, Object>) req.getAttribute("ticketInfo");
				if(info != null)
				{
					userCd = (String) info.get("userCd");
					if(Utilities.isEmpty(userCd) || "SYSTEM".equals(userCd))
						userCd = req.getParameter("tutorId");
				}
			}
			if(Utilities.isEmpty(userCd) || "SYSTEM".equals(userCd)) {
				CrmLoginUserVo user = SessionUtil.getLoginUser();
//				userCd = user==null? null : user.getLoginId();
				userCd = user==null? null : user.getUserCd();
				if (Utilities.isEmpty(userCd)) {
					userCd = "SYSTEM";
				}
			}
			String dt = Utilities.getDateTimeString();
			Map map = getMapObject(jp.getArgs());
			if (map != null) {
				map.put("peerIp", Utilities.getPeerIp());
				map.put("regrId", userCd);
				map.put("regDt", dt);
				map.put("amdrId", userCd);
				map.put("amdDt", dt);
				return;
			}
			BaseVo bo = getBaseVo(jp.getArgs());
			if (bo != null) {
				bo.setPeerIp(Utilities.getPeerIp());
				bo.setRegrId(userCd);
				bo.setRegDt(dt);
				bo.setAmdrId(userCd);
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
