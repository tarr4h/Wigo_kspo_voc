package com.kspo.voc.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.dao.IpRelDao;

/**
 * 
 * @ClassName IpRelService
 * @author 김성태
 * @date 2023. 1. 9.
 * @Version 1.0
 * @description 아이피관계 Service
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class IpRelService extends AbstractVocService {
	@Autowired
	IpRelDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}
}
