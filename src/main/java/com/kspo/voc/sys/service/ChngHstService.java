package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.ChngHstDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class ChngHstService extends AbstractVocService {
	@Autowired
	ChngHstDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}
}
