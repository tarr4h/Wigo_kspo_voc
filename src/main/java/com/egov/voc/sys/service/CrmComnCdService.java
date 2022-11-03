package com.egov.voc.sys.service;

import com.egov.voc.base.common.model.EzMap;
import com.egov.voc.comn.util.Utilities;
import com.egov.voc.sys.dao.CrmComnCdBaseDao;
import com.egov.voc.sys.dao.ICrmDao;
import com.egov.voc.sys.model.CrmComnCdBaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmComnCdService extends AbstractCrmService {
	@Autowired
	CrmComnCdBaseDao dao;

	@Override
	public ICrmDao getDao() {
		return dao;
	}
	
	@Override
	public int delete(Object param)  throws Exception{
		if(param instanceof CrmComnCdBaseVo) {
			dao.deleteChildren(param);
		}
		return super.delete(param);
	}

	public List<CrmComnCdBaseVo> getComboCode(Object param) throws Exception {
		return dao.selectCodeCombo(param);
	}

	public String getComboCodeName(EzMap param) throws Exception {
		String codeCd = param.getString("codeCd");
		if (Utilities.isEmpty(codeCd))
			return "";
		param.setString("codeName", "Y");
		List<CrmComnCdBaseVo> codeList = dao.selectCodeCombo(param);

		for (int i = 0; codeList != null && i < codeList.size(); i++) {
			CrmComnCdBaseVo vo = codeList.get(i);
			if (codeCd.equals(vo.getComnCd()))
				return vo.getComnCdNm();
		}
		return "";
	}
	
}
