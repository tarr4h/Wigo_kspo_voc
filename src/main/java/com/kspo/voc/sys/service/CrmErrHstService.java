package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.CrmErrHstDao;
import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.model.CrmErrHstVo;
import com.kspo.voc.sys.model.CrmMenuBaseVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

@Service
public class CrmErrHstService extends AbstractCrmService {
	@Autowired
	CrmErrHstDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	public void addErrorLog(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		try {
			CrmErrHstVo vo = new CrmErrHstVo();
			CrmMenuBaseVo menu = (CrmMenuBaseVo) request.getAttribute("currentMenu");
			if (menu != null) {
				vo.setMenuId(menu.getMenuId());
			}
			ex.getMessage();
			String nm = ex.getClass().getSimpleName();
			nm = nm.substring(0,nm.length()- "Exception".length());
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			String errorStr = sw.toString().substring(0,500);
			if(nm.length()>20)
				nm = nm.substring(0,20);
			vo.setErrCd(nm);
			vo.setErrMsgTxn(errorStr);
			vo.setMenuUrl(request.getRequestURI());
			dao.insert(vo);
		} catch (Exception e) {

		}
	}
}
