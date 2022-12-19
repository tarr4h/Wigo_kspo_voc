package com.kspo.voc.sys.service;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.dao.OrgBaseDao;
import com.kspo.voc.sys.model.OrgBaseVo;

@Service
public class OrgBaseService extends AbstractVocService {
   @Autowired
   OrgBaseDao dao;
   @Autowired
//   CrmJadeService jadeService;
   @Override
   public IVocDao getDao() {
       return dao;
   }
   
   @Override
	public int insert(Object param) throws EgovBizException {
	   OrgBaseVo vo = get(param);
	   if(vo == null)
		   return super.insert(param);
	   else
		   return super.update(param);
	}

public Object getTreeList(EzMap param) {
	param.setInt("recordCountPerPage", 100000);
	return AbstractTreeVo.makeHierarchy(dao.selectList(param));
}
   
   
}
