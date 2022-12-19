package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.CrmMapper;
import com.kspo.voc.sys.model.CrmLoginUserVo;

@CrmMapper
public interface CrmUserBaseDao extends ICrmDao {

	CrmLoginUserVo selectUser(Object param) throws Exception;

	int updateLoginFail(Object param) throws Exception;

	int updateLogin(Object user) throws Exception;

	int insertLoginHist(Object user) throws Exception;

	int updatePassword(Object param) throws Exception;

	int updateMyInfo(Object param) throws Exception;

	int updateEmp(Object param)throws Exception;

}
