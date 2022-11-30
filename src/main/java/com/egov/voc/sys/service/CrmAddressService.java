package com.egov.voc.sys.service;


import com.egov.base.common.model.address.EzAddressSo;
import com.egov.base.common.model.address.EzAdressResultVo;
import com.egov.base.common.util.AddressUtil;
import org.springframework.stereotype.Service;

@Service
public class CrmAddressService {

	public EzAdressResultVo getList(EzAddressSo so) throws Exception {
		return AddressUtil.searchAddr(so.getKeyword(), so.getCurrentPage(), so.getCountPerPage(), so.getLanguage());
	}

}
