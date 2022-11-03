package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmGrpMenuHstDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmGrpMenuHstService extends AbstractCrmService {
   @Autowired
   CrmGrpMenuHstDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
