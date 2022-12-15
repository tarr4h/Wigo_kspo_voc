package com.egov.base.common.util;



import com.egov.base.common.model.EzAjaxException;
import com.egov.base.common.model.address.EzAddressResult;
import com.egov.base.common.model.address.EzAddressSo;
import com.egov.base.common.model.address.EzAdressResultVo;

import java.net.URLEncoder;

/**
 * 
 * <pre>
 * com.wigo.crm.common.util - AddressUtil.java
 * </pre>
 *
 * @ClassName : AddressUtil
 * @Description : 주소 유틸리티
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public abstract class AddressUtil {

	private static final String _KEY_KO = "devU01TX0FVVEgyMDIyMTIxNTEzNTQyMjExMzM0MjI=";
	private static final String _KEY_EN = "devU01TX0FVVEgyMDIyMTIxNTEzNTUyNTExMzM0MjM=";
	private static final String _API_URL_KO = "https://www.juso.go.kr/addrlink/addrLinkApi.do";
	private static final String _API_URL_EN = "https://www.juso.go.kr/addrlink/addrEngApi.do";
	private static final int _COUNT_PER_PAGE = 100;
	
	private AddressUtil() {

	}

	public static String getKey() {
		return getKey(BaseUtilities.getLangCd());
	}

	public static String getUrl() {
		return getUrl(BaseUtilities.getLangCd());
	}

	public static String getKey(String lang) {
		
		if ("KO".equals(lang))
			return _KEY_KO;
		else
			return _KEY_EN;
	}

	public static String getUrl(String lang) {
		if ("KO".equals(lang))
			return _API_URL_KO;
		else
			return _API_URL_EN;
	}

	public static EzAdressResultVo searchAddr(String keyword) throws Exception {
		return searchAddr(keyword, 1, _COUNT_PER_PAGE, "KO");
	}

	public static EzAdressResultVo searchAddr(String keyword, int page, String language) throws Exception {
		return searchAddr(keyword, page, _COUNT_PER_PAGE, language);

	}

	public static EzAdressResultVo searchAddr(EzAddressSo so)
			throws Exception {
		return searchAddr(so.getKeyword(),so.getCurrentPage(),so.getCountPerPage(),so.getLanguage());

	}
	public static EzAdressResultVo searchAddr(String keyword, int page, int recordCountPerPage, String language)
			throws Exception {
		if (BaseUtilities.isEmpty(language))
			language = "KO";
		if (BaseUtilities.isEmpty(keyword))
			return null;
		if(recordCountPerPage < 1)
			recordCountPerPage = _COUNT_PER_PAGE;
		if(page < 1)
			page = 1;
		String url = getUrl(language) + "?countPerPage="+ recordCountPerPage + "&currentPage=" + page + "&resultType=json&keyword=" + URLEncoder.encode(keyword, "UTF-8") + "&confmKey=" + getKey(language)   
				 ;
		EzAddressResult result = BaseUtilities.wgetJson(url, EzAddressResult.class);
		if (result == null)
			return null;
		EzAdressResultVo vo = result.getResults();
		if(vo.getCommon().getTotalCount() > 9000)
			vo.getCommon().setTotalCount(9000);
		if(!vo.getCommon().getErrorCode().equals("0"))
			throw new EzAjaxException(vo.getCommon().getErrorMessage());
		return result.getResults();

	}
}
