package com.egov.voc.kspo.setting.service;


import com.egov.voc.kspo.setting.dao.VocProcedureSettingDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VocProcedureSettingService {

    @Autowired
    VocProcedureSettingDao dao;


}
