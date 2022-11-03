package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmGrpOrgHstDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmGrpOrgHstService extends AbstractCrmService {
   @Autowired
   CrmGrpOrgHstDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
