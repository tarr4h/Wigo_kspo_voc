package com.kspo.voc.sys.dao;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.kspo.voc.sys.mapper.VocMapper;
import com.kspo.voc.sys.model.GrpBaseVo;
import com.kspo.voc.sys.model.LoginUserVo;

@VocMapper
public interface UserBaseDao extends IVocDao {

	LoginUserVo selectUser(Object param) throws EgovBizException;

	int updateLoginFail(Object param) throws EgovBizException;

	int updateLogin(Object user) throws EgovBizException;

	int insertLoginHist(Object user) throws EgovBizException;

	int updatePassword(Object param) throws EgovBizException;

	int updateMyInfo(Object param) throws EgovBizException;

	int updateEmp(Object param) throws EgovBizException;

	List<GrpBaseVo> selectUserGroupList(LoginUserVo user) throws EgovBizException;

}
