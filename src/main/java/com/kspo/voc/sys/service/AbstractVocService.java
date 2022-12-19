package com.kspo.voc.sys.service;


import java.util.ArrayList;
import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.kspo.base.common.model.BaseVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.IVocDao;

/**
 * <pre>
 * com.wigo.crm.common.service.AbstractCrmService
 * </pre>
 *
 * @ClassName : AbstractCrmService
 * @Description : AbstractCrmService
 * @author : 김성태
 * @date : 2021. 4. 27.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.CO.LTD. All Right Reserved
 */
public abstract class AbstractVocService extends EgovAbstractServiceImpl {
	public abstract IVocDao getDao();

	public <T> List<T> getList(Object param)  throws EgovBizException{
		return getDao().selectList(param);
	}

	public <T> T get(Object param)  throws EgovBizException{
		return getDao().select(param);
	}

	public int getListCount(Object param) throws EgovBizException {
		return getDao().selectListCount(param);
	}

	public int insert(Object param)  throws EgovBizException{
		return getDao().insert(param);
	}

	public int update(Object param)  throws EgovBizException{
		return getDao().update(param);
	}

	public int delete(Object param)  throws EgovBizException{
		return getDao().delete(param);
	};

	public EzMap save(BaseVo vo)  throws EgovBizException{
		if ("C".equalsIgnoreCase(vo.getStat()))
			return Utilities.getInsertResult(insert(vo), vo);
		else if ("U".equalsIgnoreCase(vo.getStat()))
			return Utilities.getUpdateResult(update(vo), vo);
		else if ("D".equalsIgnoreCase(vo.getStat()))
			return Utilities.getDeleteResult(delete(vo));
		return Utilities.getUpdateResult(0, vo);
	}

	public EzMap insertList(List<? extends BaseVo> list) throws EgovBizException {
		List<EzMap> result = new ArrayList<EzMap>();
		for (int i = 0; i < list.size(); i++) {
			result.add(Utilities.getInsertResult(insert(list.get(i)), list.get(i)));
		}
		return Utilities.getSaveResult(result);
	}

	public EzMap updateList(List<? extends BaseVo> list) throws EgovBizException {
		List<EzMap> result = new ArrayList<EzMap>();
		for (int i = 0; i < list.size(); i++) {
			result.add(Utilities.getUpdateResult(update(list.get(i)), list.get(i)));
		}
		return Utilities.getSaveResult(result);
	}

	public EzMap deleteList(List<? extends BaseVo> list) throws EgovBizException {
		List<EzMap> result = new ArrayList<EzMap>();
		for (int i = 0; i < list.size(); i++) {
			result.add(Utilities.getDeleteResult(delete(list.get(i))));
		}
		return Utilities.getSaveResult(result);
	}

	public EzMap saveList(List<? extends BaseVo> list) throws EgovBizException {
		List<EzMap> result = new ArrayList<EzMap>();
		for (int i = 0; i < list.size(); i++) {
			result.add(save(list.get(i)));
		}
		return Utilities.getSaveResult(result);
	}

}
