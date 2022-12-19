package com.kspo.voc.sys.service;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.ComnCdBaseDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.model.ComnCdBaseVo;

@Service
public class ComnCdService extends AbstractVocService {
	@Autowired
	ComnCdBaseDao dao;

	@Override
	public IVocDao getDao() {
		return dao;
	}
	
	@Override
	public int delete(Object param)  throws EgovBizException{
		if(param instanceof ComnCdBaseVo) {
			dao.deleteChildren(param);
		}
		return super.delete(param);
	}

	public List<ComnCdBaseVo> getComboCode(Object param) throws EgovBizException {
		return dao.selectCodeCombo(param);
	}

	public String getComboCodeName(EzMap param) throws EgovBizException {
		String codeCd = param.getString("codeCd");
		if (Utilities.isEmpty(codeCd))
			return "";
		param.setString("codeName", "Y");
		List<ComnCdBaseVo> codeList = dao.selectCodeCombo(param);

		for (int i = 0; codeList != null && i < codeList.size(); i++) {
			ComnCdBaseVo vo = codeList.get(i);
			if (codeCd.equals(vo.getComnCd()))
				return vo.getComnCdNm();
		}
		return "";
	}
	
}
