package com.kspo.voc.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.BatchInfoBasDao;
import com.kspo.voc.sys.dao.IVocDao;

/**
 * 
 * @ClassName    TbSysBatchInfoBasService
 * @author    user
 * @date    2022. 12. 22.
 * @Version    1.0
 * @description    배치정보기본 Service
 * @Company    Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class BatchInfoBasService extends AbstractVocService {
   @Autowired
   BatchInfoBasDao dao;

   @Override
   public IVocDao getDao() {
       return dao;
   }
}
