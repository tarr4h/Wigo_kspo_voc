package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.CrmUserWdgtDao;
import com.kspo.voc.sys.dao.ICrmDao;

@Service
public class CrmUserWdgtService extends AbstractCrmService {
   @Autowired
   CrmUserWdgtDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }
}
