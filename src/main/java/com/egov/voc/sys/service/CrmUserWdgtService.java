package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmUserWdgtDao;
import com.egov.voc.sys.dao.ICrmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmUserWdgtService extends AbstractCrmService {
   @Autowired
   CrmUserWdgtDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
