package com.kspo.voc.sys.service;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.EmpBaseDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.dao.UserBaseDao;
import com.kspo.voc.sys.model.EmpBaseVo;
import com.kspo.voc.sys.model.LoginUserVo;
import com.kspo.voc.sys.model.UserBaseVo;

@Service
public class EmpBaseService extends AbstractVocService {
	@Autowired
	EmpBaseDao dao;
	@Autowired
	UserBaseDao userDao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

	@Override
	public int insert(Object param) throws EgovBizException {
		if (param instanceof LoginUserVo) {

			LoginUserVo v = (LoginUserVo) param;
			if (Utilities.isEmpty(v.getOrgId()))
				return 0;
			if (Utilities.isEmpty(v.getEmailAddr()))
				v.setLoginId(v.getEmpId());
			else
				v.setLoginId(v.getEmailAddr());

			v.setDelYn("30".equals(v.getStatusCd()) ? "Y" : "N");
		}

		UserBaseVo usr = userDao.select(param);
		if (usr == null) {

			userDao.insert(param);
		} else {
			userDao.updateEmp(param);
		}
		EmpBaseVo vo = get(param);
		if (vo == null)
			return super.insert(param);
		else
			return super.update(param);

	}
}
