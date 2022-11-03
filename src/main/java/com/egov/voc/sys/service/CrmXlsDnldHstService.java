package com.egov.voc.sys.service;


import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.dao.CrmXlsDnldHstDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmXlsDnldHstVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
