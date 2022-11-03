package com.egov.voc.sys.service;


import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.dao.CrmApiInfoBaseDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmApiInfoBaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmApiManageService extends AbstractCrmService {
	@Autowired
	CrmApiInfoBaseDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

	@Override
	public int update(Object param) throws Exception {
		if(param instanceof CrmApiInfoBaseVo) {
			CrmApiInfoBaseVo vo = (CrmApiInfoBaseVo) param;
			String url = vo.getApiUrl();
			if(Utilities.isNotEmpty(url) && !url.startsWith("/"))
				vo.setApiUrl("/"+url);
		}
		return super.update(param);
	}
	@Override
	public int insert(Object param) throws Exception {
		if(param instanceof CrmApiInfoBaseVo) {
			CrmApiInfoBaseVo vo = (CrmApiInfoBaseVo) param;
			String url = vo.getApiUrl();
			if(Utilities.isNotEmpty(url) && !url.startsWith("/"))
				vo.setApiUrl("/"+url);
		}
		return super.insert(param);
	}
}
