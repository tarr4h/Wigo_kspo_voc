package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.CrmGenGridDao;
import com.kspo.voc.sys.dao.ICrmDao;

@Service
public class CrmGenGridService extends AbstractCrmService {
	@Autowired
	CrmGenGridDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}

}
