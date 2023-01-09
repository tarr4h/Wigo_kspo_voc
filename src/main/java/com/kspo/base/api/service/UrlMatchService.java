package com.kspo.base.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.dao.ApiInfoBaseDao;
import com.kspo.voc.sys.model.ApiExecHstVo;

/**
 * 
 * @ClassName UrlMatchService
 * @author 김성태
 * @date 2022. 4. 8.
 * @Version 1.0
 * @description
 * @Company Copyright ⓒ wigo.ai. All Right Reserved
 */
@Service
public class UrlMatchService {
	@Autowired
	ApiInfoBaseDao dao;
	String lastUpdate = null;
	List<ApiExecHstVo> apiInfoList = null;
	Map<String, List<ApiExecHstVo>> apiSysMap;
	AntPathMatcher matcher = new AntPathMatcher();

	public ApiExecHstVo getMatchUrl(String uri, String methodName) {
		String method = methodName;
		String url = uri;
		if (Utilities.isEmpty(url))
			return null;
		if (url.endsWith("/"))
			url = url.substring(0, url.length() - 1);
		List<ApiExecHstVo> list = getApiList(url);
		for (int i = 0; list != null && i < list.size(); i++) {
			ApiExecHstVo vo = list.get(i);
			if (vo == null || Utilities.isEmpty(vo.getApiUrl()))
				continue;

			if (matcher.match(vo.getApiUrl(), url)
					&& (Utilities.isEmpty(vo.getCallMthdCd()) || vo.getCallMthdCd().equals(method))) {
				return vo;
			}
		}
		return null;
	}

	private List<ApiExecHstVo> getApiList(String requestUrl) {
		String reqUrl = requestUrl;
		if (apiInfoList == null || Utilities.isEmpty(lastUpdate)
				|| Utilities.getDateTimeString().indexOf(lastUpdate) != 0) {
			lastUpdate = Utilities.getDateTimeString().substring(0, 12);
			EzMap param = new EzMap();
			param.setString("recordCountPerPage", 100000);
			apiInfoList = dao.selectList(param);
			apiSysMap = new HashMap<String, List<ApiExecHstVo>>();
			for (int i = 0; i < apiInfoList.size(); i++) {
				ApiExecHstVo vo = apiInfoList.get(i);
				String url = vo.getApiUrl();
				if (url.endsWith("/"))
					vo.setApiUrl(url.substring(0, url.length() - 1));
				List<ApiExecHstVo> list = null;
				String sysCd = vo.getSysCd().toUpperCase();
				if (!apiSysMap.containsKey(sysCd)) {
					apiSysMap.put(sysCd, new ArrayList<ApiExecHstVo>());
				}
				list = apiSysMap.get(sysCd);
				list.add(vo);
			}
		}
		if (Utilities.isNotEmpty(reqUrl)) {
			if (reqUrl.startsWith("/"))
				reqUrl = reqUrl.substring(1);
			int idx = reqUrl.indexOf("/");
			if (idx > 0) {
				String sysCd = reqUrl.substring(0, idx).toUpperCase();
				if (apiSysMap.containsKey(sysCd))
					return apiSysMap.get(sysCd);
			}
		}
		return apiInfoList;
	}
}
