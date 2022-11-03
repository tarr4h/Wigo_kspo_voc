package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmGrpEmpHstDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmGrpEmpHstService extends AbstractCrmService {
   @Autowired
   CrmGrpEmpHstDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
