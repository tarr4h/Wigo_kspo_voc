package com.kspo.voc.sys.dao;

import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.LoginUserVo;

@VocMapper
public interface UserBaseDao extends IVocDao {

	LoginUserVo selectUser(Object param) throws Exception;

	int updateLoginFail(Object param) throws Exception;

	int updateLogin(Object user) throws Exception;

	int insertLoginHist(Object user) throws Exception;

	int updatePassword(Object param) throws Exception;

	int updateMyInfo(Object param) throws Exception;

	int updateEmp(Object param)throws Exception;

}
