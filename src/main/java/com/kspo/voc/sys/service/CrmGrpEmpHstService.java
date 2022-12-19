package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.CrmGrpEmpHstDao;
import com.kspo.voc.sys.dao.ICrmDao;

@Service
public class CrmGrpEmpHstService extends AbstractCrmService {
   @Autowired
   CrmGrpEmpHstDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
