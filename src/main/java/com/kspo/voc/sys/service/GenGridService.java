package com.kspo.voc.sys.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
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
	public List<EzMap > getCodeList(Object param){
		return dao.selectCodeList(param);
	}

}
