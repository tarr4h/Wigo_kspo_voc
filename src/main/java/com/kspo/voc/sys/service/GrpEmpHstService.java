package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.GrpEmpHstDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class GrpEmpHstService extends AbstractVocService {
   @Autowired
   GrpEmpHstDao dao;

   @Override
   public IVocDao getDao() {
       return dao;
   }
}
