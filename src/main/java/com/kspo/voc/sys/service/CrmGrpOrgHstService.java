package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.CrmGrpOrgHstDao;
import com.kspo.voc.sys.dao.ICrmDao;

@Service
public class CrmGrpOrgHstService extends AbstractCrmService {
   @Autowired
   CrmGrpOrgHstDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
