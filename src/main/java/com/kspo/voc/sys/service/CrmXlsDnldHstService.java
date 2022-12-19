package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.CrmXlsDnldHstDao;
import com.kspo.voc.sys.dao.ICrmDao;
import com.kspo.voc.sys.model.CrmXlsDnldHstVo;

@Service
public class CrmXlsDnldHstService extends AbstractCrmService {
   @Autowired
   CrmXlsDnldHstDao dao;

   @Override
   public ICrmDao getDao() {
       return dao;
   }

public void addLog(CrmXlsDnldHstVo vo) {
	String menuCd = Utilities.getCurrentMenuCd();
	if(Utilities.isEmpty(menuCd))
		return ;
	vo.setMenuCd(menuCd);
	dao.insert(vo);
}
}
