package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.ApiInfoBaseDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.ApiInfoBaseVo;

@Service
public class ApiManageService extends AbstractVocService {
	@Autowired
	ApiInfoBaseDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

	@Override
	public int update(Object param) throws Exception {
		if(param instanceof ApiInfoBaseVo) {
			ApiInfoBaseVo vo = (ApiInfoBaseVo) param;
			String url = vo.getApiUrl();
			if(Utilities.isNotEmpty(url) && !url.startsWith("/"))
				vo.setApiUrl("/"+url);
		}
		return super.update(param);
	}
	@Override
	public int insert(Object param) throws Exception {
		if(param instanceof ApiInfoBaseVo) {
			ApiInfoBaseVo vo = (ApiInfoBaseVo) param;
			String url = vo.getApiUrl();
			if(Utilities.isNotEmpty(url) && !url.startsWith("/"))
				vo.setApiUrl("/"+url);
		}
		return super.insert(param);
	}
}
