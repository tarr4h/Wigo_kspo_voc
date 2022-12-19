package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.UserLoginHstDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class UserLoginHstService extends AbstractVocService {
	@Autowired
	UserLoginHstDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}
	
	
}
