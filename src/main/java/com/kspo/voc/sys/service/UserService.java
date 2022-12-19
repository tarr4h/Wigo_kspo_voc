package com.kspo.voc.sys.service;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.GrpUserRelDao;
import com.kspo.voc.sys.dao.IVocDao;
import com.kspo.voc.sys.dao.UserBaseDao;
import com.kspo.voc.sys.model.UserBaseVo;

@Service
public class UserService extends AbstractVocService {
	@Autowired
	UserBaseDao dao;

	@Autowired
	GrpUserRelDao grpUserDao;

	@Override
	public IVocDao getDao() {
		return dao;
	}

	@Override
	public int insert(Object param) throws EgovBizException {
		UserBaseVo user = (UserBaseVo) param;
		if (Utilities.isEmpty(user.getUserId())) {
			String userId;
			try {
				userId = Utilities.getAutoSeq("USR");
			} catch (Exception e) {
				throw new EgovBizException(e.getMessage(), e);
			}
			user.setUserId(userId);
		}
		if (Utilities.isEmpty(user.getLoginPwd())) {
			String pwd = user.getLoginId() + "123!@";
			user.setLoginPwd(pwd);
		} else {
			String pwd = user.getLoginPwd();
			user.setLoginPwd(pwd);
		}
		String pwd = Utilities.passwordEncode(user.getLoginPwd());
		user.setLoginPwd(pwd);
		return super.insert(param);
	}

	public Object savePassword(UserBaseVo param) throws EgovBizException {
		param.setLoginPwd(Utilities.passwordEncode(param.getLoginPwd()));
		return Utilities.getUpdateResult(dao.updatePassword(param));
	}

	public EzMap updateMyInfo(EzMap param) throws EgovBizException {
		return Utilities.getUpdateResult(dao.updateMyInfo(param));
	}

	public Object updatePwd(EzMap param) throws EgovBizException {
		/* user 비밀번호 조회 */
		UserBaseVo user = dao.select(param);
		EzMap result = new EzMap();
		String checkPwd = user.getLoginPwd();
		String password = param.getString("password"); // 현재비밀번호
		String pwd = param.getString("pwd"); // 변경될 비밀번호

		/* 현재 비밀번호와 조회해온 비밀번호 비교 */
		if (!Utilities.passwordMatch(password, checkPwd)) {
			result.put("errorMsg", "현재비밀번호를 확인해주세요.");
			result.put("errorCode", "FAILED");
			return result;
		}

		/* 비밀번호 암호화 */
		user.setLoginPwd(Utilities.passwordEncode(pwd));
		return Utilities.getUpdateResult(dao.updatePassword(user));
	}

	@Override
	public int delete(Object param) throws EgovBizException {
		grpUserDao.deleteUserId(param);
		return super.delete(param);
	}
}
