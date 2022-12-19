package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.GrpMenuHstDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class GrpMenuHstService extends AbstractVocService {
   @Autowired
   GrpMenuHstDao dao;

   @Override
   public IVocDao getDao() {
       return dao;
   }
}
