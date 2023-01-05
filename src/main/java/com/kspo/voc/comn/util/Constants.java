package com.kspo.voc.comn.util;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * <pre>
 * com.wigo.crm.common.util - Constants.java
 * </pre>
 *
 * @ClassName : Constants
 * @Description : 상수 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class Constants {

	public static final String _LOGIN_SESSION_NAME = "LOGIN_USER";
	public static final String _USER_MENU_LIST_NAME = "USER_MENU_LIST";
	public static final String _USER_MENU_MAP_NAME = "USER_MENU_MAP";
	public static final String _USER_SYSTEM_LIST_NAME = "USER_SYSTEM_LIST";
	public static final String _LOGIN_ID = "userId";
	public static final String _LOGIN_STATE_KEY = "state";

	public final static SimpleDateFormat _TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
			Locale.KOREAN);
	public final static SimpleDateFormat _DATETIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREAN);
	public final static SimpleDateFormat _DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
	public final static String _DB_ENCODING = "UTF-8";
	public static int _ERROR_NEED_LOGIN = 462;
	public static int _ERROR_HAS_NO_RIGHT = 463;
	public static int _ERROR_BAN_LOGIN = 464;
	public static int _ERROR_KEY_DUPLICATE = 486;
	public static int _ERROR_USER = 487;
	public static int _ERROR_UNKNOWN = 488;

	public static final int _PASSWORD_CHANGE_MONTH = 1;
	public static final String _META_TYPE_CATEGORY = "01000000000000000000";
	public static final String _SEQ_KEY_CD = "seqKeyCd";
	public static final String _SEQ_PK_NAME = "seqPkCol";
	public static final String _SEQ_FORMAT = "seqFormat";
	
	public static final String _NOTICE_BBS_ID = "NOTICE";
	
	public final static String _UNKNOWN_SYSTEM_CD = "000";
	
	public static final String _API_URL = "/api";
	public static final String _API_INFO_KEY = "apiInfo";
	public static final String _APIKEY_PAYLOAD = "apikeyInfo";
	public static final String _APIKEY_PAYLOAD_KEY_SYSTEM = "systemCd";
	public static final String _API_CALL_URL_KEY = "api_request_uri";

	public final static String _API_CODE_OK = "IAR0200";
	public final static String _API_CODE_FAIL = "IAR0201";
	public final static String _API_CODE_NOT_FOUND = "IAR0202"; /* 해당api를 찾을 수 없습니다. */
	public final static String _API_CODE_NOT_FOUND_MSG = "해당api를 찾을 수 없습니다.";
	public final static String _API_CODE_NO_TOKEN = "IAR0203"; /* 토큰을 찾을 수 없습니다. */
	public final static String _API_CODE_EXPIRED_KEY = "IAR0204"; /* 만료된 apikey 입니다.. */
	public final static String _API_CODE_EXPIRED_KEY_MSG = "만료된 apikey 입니다.";
	public final static String _API_CODE_MALFROMED_KEY = "IAR0205"; /* 잘못된 apikey 입니다.. */
	public final static String _API_CODE_MALFROMED_KEY_MSG = "잘못된 apikey 입니다."; /* 잘못된 apikey 입니다.. */
	public final static String _API_CODE_NO_REG = "IAR0206"; /* 등록되지 않은 API입니다. */
	public final static String _API_CODE_NO_REG_MSG = "등록되지 않은 API입니다."; /* 등록되지 않은 API입니다. */
	public final static String _API_CODE_EXPIRED_URL = "IAR0207"; /* 사용 종료된 API 주소입니다. */
	public final static String _API_CODE_EXPIRED_URL_MSG = "사용 종료된 API 주소입니다."; /* 사용 종료된 API 주소입니다. */
	public final static String _API_CODE_NO_METHOD = "IAR0208"; /* 등록되지 않은 API입니다. */
	public final static String _API_CODE_NO_METHOD_MSG = "등록되지 않은 API입니다."; /* 등록되지 않은 API입니다. */
	public final static String _API_CODE_NO_RIGHT = "IAR0209"; /* 요청한 정보를 조회할 권한이 없습니다. */
	public final static String _API_CODE_NO_RIGHT_MSG = "요청한 정보를 조회할 권한이 없습니다"; /* 요청한 정보를 조회할 권한이 없습니다. */
	
	public final static String _API_CODE_INVALID_PARAM = "IAR0300"; /* 요청파라미터가 올바르지 않습니다. [파라미터] */
	public final static String _API_CODE_INVALID_PARAM_MSG = "요청파라미터가 올바르지 않습니다."; /* 요청파라미터가 올바르지 않습니다. [파라미터] */
	public final static String _API_CODE_DUPLICATED_PARAM = "IAR0301"; /* 요청파라미터가 올바르지 않습니다. [파라미터] */
	public final static String _API_CODE_DUPLICATED_PARAM_MSG = "이미 등록된 데이터가 존재합니다."; /* 중복된 값이 존재합니다. [파라미터] */
	public static final String _API_CODE_DUPLICATED_PARAM_USER_CI_MSG = "이미 등록된 CI값이 존재합니다.";
	public static final String _API_CODE_DUPLICATED_PARAM_USER_NM_PHONE_MSG = "동일한 이름에 동일한 전화번호가 존재합니다.";
	public static final String _API_CODE_DUPLICATED_PARAM_LOGIN_ID_MSG = "이미 사용중인 로그인 아이디 입니다.";
	
	public final static String _API_CODE_NO_DATA = "IAR0400"; /* 조회된 데이터가 없습니다. */
	public final static String _API_CODE_NO_DATA_MSG = "조회된 데이터가 없습니다."; /* 조회된 데이터가 없습니다. */
	
	static {
		_TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
}
