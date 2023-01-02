package com.kspo.voc.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.dao.PostBasDao;

@Service
public class PostBasService extends AbstractVocService {
	@Autowired
	PostBasDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}
}
