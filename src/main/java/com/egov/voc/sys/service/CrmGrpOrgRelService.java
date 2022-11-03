package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmGrpOrgHstDao;
import com.egov.voc.sys.dao.CrmGrpOrgRelDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmGrpOrgRelService extends AbstractCrmService {
	@Autowired
	CrmGrpOrgRelDao dao;
	@Autowired
	CrmGrpOrgHstDao hstDao;
	@Override
	public ICrmDao getDao() {
		return dao;
	}
	
	@Override
	public int delete(Object param) throws Exception {
		hstDao.insertDelete(param);
		return super.delete(param);
	}
	@Override
	public int insert(Object param) throws Exception {
		if(get(param)==null)
		{
			hstDao.insert(param);
			return super.insert(param);
		}
		return 0;
	}
}
