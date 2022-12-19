package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.UserWdgtDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class UserWdgtService extends AbstractVocService {
   @Autowired
   UserWdgtDao dao;

   @Override
   public IVocDao getDao() {
       return dao;
   }
}
