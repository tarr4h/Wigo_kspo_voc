package com.egov.voc.sys.service;

import com.egov.base.common.model.AbstractTreeVo;
import com.egov.base.common.model.EzMap;
import com.egov.voc.sys.dao.CrmOrgBaseDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmJadeOrgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmOrgBaseService extends AbstractCrmService {
   @Autowired
   CrmOrgBaseDao dao;
   @Autowired
//   CrmJadeService jadeService;
   @Override
   public ICrmDao getDao() {
       return dao;
   }
   
   public void saveSyncOrg() throws Exception {
//	   List<CrmJadeOrgVo> list = jadeService.getCeragemOrgList();
//	   insertList(list);
   }
   @Override
	public int insert(Object param) throws Exception {
	   CrmJadeOrgVo vo = get(param);
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
