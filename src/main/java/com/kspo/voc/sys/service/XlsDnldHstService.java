package com.kspo.voc.sys.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.XlsDnldHstDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.XlsDnldHstVo;

@Service
public class XlsDnldHstService extends AbstractVocService {
   @Autowired
   XlsDnldHstDao dao;

   @Override
   public IVocDao getDao() {
       return dao;
   }

public void addLog(XlsDnldHstVo vo) {
	String menuId = Utilities.getCurrentMenuId();
	if(Utilities.isEmpty(menuId))
		return ;
	vo.setMenuId(menuId);
	dao.insert(vo);
}
}
