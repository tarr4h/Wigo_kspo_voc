package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmGenGridDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmGenGridService extends AbstractCrmService {
	@Autowired
	CrmGenGridDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

}
