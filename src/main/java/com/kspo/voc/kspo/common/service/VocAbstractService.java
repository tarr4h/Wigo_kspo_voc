package com.kspo.voc.kspo.common.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.voc.kspo.common.dao.IVocPrcDao;
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





}
