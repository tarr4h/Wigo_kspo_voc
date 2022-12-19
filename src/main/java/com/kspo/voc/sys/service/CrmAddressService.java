package com.kspo.voc.sys.service;


import org.springframework.stereotype.Service;

import com.kspo.base.common.model.address.EzAddressSo;
import com.kspo.base.common.model.address.EzAdressResultVo;
import com.kspo.base.common.util.AddressUtil;

@Service
public class CrmAddressService {

	public EzAdressResultVo getList(EzAddressSo so) throws Exception {
		return AddressUtil.searchAddr(so.getKeyword(), so.getCurrentPage(), so.getCountPerPage(), so.getLanguage());
	}

}
