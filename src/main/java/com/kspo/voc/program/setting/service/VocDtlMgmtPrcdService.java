package com.kspo.voc.program.setting.service;

import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.program.common.service.VocAbstractService;
import com.kspo.voc.program.common.stnd.CodeGeneration;
import com.kspo.voc.program.common.stnd.ManageCodeCategory;
import com.kspo.voc.program.setting.dao.VocDtlMgmtPrcdDao;
import com.kspo.voc.program.setting.model.VocDirCdVo;
import com.kspo.voc.program.setting.model.VocDirMgmtVo;
import com.kspo.voc.sys.dao.IVocDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.kspo.voc.program.setting.service.VocDtlMgmtPrcdService
 *  - VocDtlMgmtPrcdService.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : VocDtlMgmtPrcdService
 * @description :
 * @date : 2023-01-09
 */

@SuppressWarnings("unchecked")
@Service
@Slf4j
public class VocDtlMgmtPrcdService extends VocAbstractService {

    @Autowired
    VocDtlMgmtPrcdDao dao;

    @Override
    public IVocDao getDao() {
        return dao;
    }

    public Object vocMgmtChCdTree(EzMap param) {
        ManageCodeCategory.setComnCdTreeMap(param, ManageCodeCategory.CHANNEL);
        return AbstractTreeVo.makeHierarchy(selectVocMgmtCdTree(param));
    }

    public Object vocMgmtTpCdTree(EzMap param) {
        ManageCodeCategory.setComnCdTreeMap(param, ManageCodeCategory.TYPE);
        return AbstractTreeVo.makeHierarchy(selectVocMgmtCdTree(param));
    }

    public Object selectComboDirCd(Map<String, Object> param) {
        String dirCd = null;

        List<VocDirMgmtVo> dirMgmtListByMgmtCd = dao.selectDirMgmtList(param);

        for(VocDirMgmtVo dirMgmtVo : dirMgmtListByMgmtCd){
            List<VocDirMgmtVo> dirMgmtListByDirCd = dao.selectDirMgmtList(Utilities.beanToMap(dirMgmtVo));

            int cnt = 0;
            for(VocDirMgmtVo dirMgmtVoByDirCd : dirMgmtListByDirCd){
                String mgmtCd = dirMgmtVoByDirCd.getMgmtCd();
                if(mgmtCd.equals(param.get("chMgmtCd")) || mgmtCd.equals(param.get("tpMgmtCd"))){
                    cnt++;
                }
            }

            if(cnt == 2){
                dirCd = dirMgmtVo.getDirCd();
                break;
            }
        }

        if(dirCd == null){
            String maxDirCd = dao.selectMaxDirCd();
            param.put("dirCd", CodeGeneration.generateCode(maxDirCd, CodeGeneration.DIRCD));
            dao.insertDirCd(param);
            dao.insertDirMgmtCombo(param);
        } else {
            param.put("dirCd", dirCd);
        }

        return param;
    }

    public Object insertDirPrcd(EzMap param) {

        return null;
    }
}
