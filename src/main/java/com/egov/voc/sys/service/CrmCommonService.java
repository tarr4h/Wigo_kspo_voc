package com.egov.voc.sys.service;


import com.egov.voc.sys.dao.CrmCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commonService")
public class CrmCommonService {
	@Autowired
	CrmCommonDao commonDao;

	/**
	 * <pre>
	 * 자동채번
	 * </pre>
	 */
	public String getAutoSeq(Object param) {
		return commonDao.getAutoSeq(param);
	}

}
