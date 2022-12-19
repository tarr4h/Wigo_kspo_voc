package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.sys.dao.NtcartBasDao;
import com.kspo.voc.sys.dao.IVocDao;

@Service
public class NtcartBasService extends AbstractVocService {
   @Autowired
   NtcartBasDao dao;

   @Override
   public IVocDao getDao() {
       return dao;
   }
}
