package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.GenGridDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class GenGridService extends AbstractVocService {
	@Autowired
	GenGridDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

}
