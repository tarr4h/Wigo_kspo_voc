package com.kspo.voc.sys.service;


import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.ErrHstDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.ErrHstVo;
import com.kspo.voc.sys.model.MenuBaseVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ErrHstService extends AbstractVocService {
	@Autowired
	ErrHstDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

	public void addErrorLog(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		try {
			ErrHstVo vo = new ErrHstVo();
			MenuBaseVo menu = (MenuBaseVo) request.getAttribute("currentMenu");
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
			log.warn(e.getMessage());
		}
	}
}
