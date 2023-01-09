package com.kspo.voc.program.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.program.setting.model.VocMgmtPrcdVo;
import com.kspo.voc.program.setting.model.VocMgmtTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.voc.program.common.dao.IVocPrcDao;
import com.kspo.voc.sys.service.AbstractVocService;

public abstract class VocAbstractService extends AbstractVocService {

    @Autowired
    IVocPrcDao dao;

    public List<? extends ITreeVo> selectVocMgmtCdTree(Map<String, Object> param){
        return dao.selectVocMgmtCdTree(param);
    }

    public <T> T selectMgmtCd(Object param){
        return dao.selectMgmtCd(param);
    }

    public <T> T selectPrcdBas(Object param){
        return dao.selectPrcdBas(param);
    }

    public <T> List<T> selectTaskBasList(Object param){
        return dao.selectTaskBasList(param);
    }

    public <T> List<T> selectPrcdBasList(Object param){
        return dao.selectPrcdBasList(param);
    }

    public <T> List<T> selectActvBasList(Object param){
        return dao.selectActvBasList(param);
    }

    public <T> T selectChannelDirCd(Map<String, Object> param){
        return dao.selectChannelDirCd(param);
    }

    public <T> List<T> selectDirOrg(EzMap param){
        return dao.selectDirOrg(param);
    }

    public <T> List<T> selectMgmtPrcdList(EzMap param) {
        List<EzMap> list = dao.selectDirPrcdList(param);

        if(list.size() == 0){
            return new ArrayList<>();
        }
        param.put("dirPrcdList", list);
        return dao.selectMgmtPrcdList(param);
    }

    public <T> List<T> selectMgmtTaskList(EzMap param){
        return dao.selectMgmtTaskList(param);
    }

    public <T> List<T> selectMgmtActvList(EzMap param){
        return dao.selectMgmtActvList(param);
    }



}
