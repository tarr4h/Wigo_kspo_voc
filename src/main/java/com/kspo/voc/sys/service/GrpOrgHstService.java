package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.GrpOrgHstDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class GrpOrgHstService extends AbstractVocService {
   @Autowired
   GrpOrgHstDao dao;

   @Override
   public IVocDao getDao() {
       return dao;
   }
}
