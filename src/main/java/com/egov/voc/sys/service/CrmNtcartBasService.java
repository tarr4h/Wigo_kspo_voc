package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmNtcartBasDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmNtcartBasService extends AbstractCrmService {
   @Autowired
   CrmNtcartBasDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
