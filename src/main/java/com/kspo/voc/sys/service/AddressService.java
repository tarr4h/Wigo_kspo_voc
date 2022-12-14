package com.kspo.voc.sys.service;


import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.address.EzAddressSo;
import com.kspo.base.common.model.address.EzAdressResultVo;
import com.kspo.base.common.util.AddressUtil;

@Service
public class AddressService {

	public EzAdressResultVo getList(EzAddressSo so) throws EgovBizException {
		try {
		return AddressUtil.searchAddr(so.getKeyword(), so.getCurrentPage(), so.getCountPerPage(), so.getLanguage());}
		catch(Exception e) {
			throw new EgovBizException(e.getMessage(),e);
		}
	}

}
