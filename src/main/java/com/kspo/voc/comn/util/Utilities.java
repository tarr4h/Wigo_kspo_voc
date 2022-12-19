package com.kspo.voc.comn.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.encoding.Base64;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kspo.base.common.model.AbstractTreeVo;
import com.kspo.base.common.model.EzMap;
import com.kspo.base.common.model.EzPaginationInfo;
import com.kspo.base.common.model.EzPropertyServiceImpl;
import com.kspo.base.common.model.EzTreeMap;
import com.kspo.base.common.model.ITreeVo;
import com.kspo.base.common.util.ExcelUtil;
import com.kspo.base.common.util.HttpUtil;
import com.kspo.base.common.util.MailUtil;
import com.kspo.base.common.util.PKZip;
import com.kspo.base.common.util.security.KisaSeed256;
import com.kspo.voc.sys.model.LoginUserVo;
import com.kspo.voc.sys.model.MenuBaseVo;
import com.kspo.voc.sys.service.CommonService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <pre>
 * com.wigo.crm.common.util - Utilities.java
 * </pre>
 *
 * @ClassName : Utilities
 * @Description : 유틸리티
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
@Component
public class Utilities {

	private static final String _ENC_LANG = "UTF-8";
	private static ConfigurableApplicationContext context;
	private static ObjectMapper objectMapper;
	private static ServletContext servletContext;
//	private static SessionLocaleResolver localeResolver;
	private static MessageSource messageSource;

//    private static CommonService              commonService;
	private static CommonService commonService;
	@Autowired
	CommonService commService;
	private static EzPropertyServiceImpl propertiesService;

	@Autowired
	private ConfigurableApplicationContext ctx;
	@Autowired
	private ServletContext sCtx;
	@Autowired
	private MessageSource localMessageSource;

	@Resource(name = "propertiesService")
	private EzPropertyServiceImpl ezProperties;
	
	
	private static final String _SALT_KEY = "$5$AvoubMud";

	@PostConstruct
	public void init() throws Exception {

		context = this.ctx;
		objectMapper = (ObjectMapper) context.getBean("jacksonObjectMapper");
		servletContext = this.sCtx;
		commonService = this.commService;
		messageSource = this.localMessageSource;
		propertiesService = this.ezProperties;

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

	}

	/**
	 * 배열을 문자열로 변환
	 * 
	 * @param arr 배열
	 * @return
	 */
	public static String getArrayString(String[] arr) {
		return getArrayString(arr, ",");
	}

	/**
	 * 배열을 문자열로 변환
	 * 
	 * @param arr        배열
	 * @param strSpliter 구분자
	 * @return
	 */
	public static String getArrayString(String[] arr, String strSpliter) {
		if (arr == null || arr.length == 0)
			return "";
		String strRet = "";
		for (int i = 0; i < arr.length; i++) {
			if (strRet.length() > 0)
				strRet += strSpliter;
			strRet += arr[i];
		}
		return strRet;
	}

	/**
	 * 폴더를 생성
	 * 
	 * @param strDirectory 폴더명(절대경로)
	 */
	public static void clearDirectory(String strDirectory) {
		clearDirectory(new File(strDirectory));

	}

	/**
	 * 폴더를 생성
	 * 
	 * @param fDirectory 경로
	 */
	public static void clearDirectory(File fDirectory) {
		if (fDirectory == null)
			return;
		if (!fDirectory.isDirectory())
			return;

		File[] listFile = fDirectory.listFiles();
		int nLength = listFile.length;

		for (int i = 0; i < nLength; i++) {
			File fSub = listFile[i];
			if (fSub.isFile())
				fSub.delete();
			else if (fSub.isDirectory())
				deleteFile(fSub);
		}

	}

	/**
	 * 폴더를 삭제
	 * 
	 * @param fDirectory 경로
	 */
	static public void deleteDirectory(File fDirectory) {
		if (fDirectory == null || fDirectory.isDirectory() == false)
			return;
		deleteFile(fDirectory);
	}

	/**
	 * 폴더를 삭제
	 * 
	 * @param strDirectory 경로
	 */
	static public void deleteDirectory(String strDirectory) {
		deleteDirectory(new File(strDirectory));

	}

	/**
	 * 파일 또는 폴더를 삭제
	 * 
	 * @param fDelete 경로
	 */
	static public void deleteFile(File fDelete) {
		if (fDelete == null)
			return;
		clearDirectory(fDelete);
		fDelete.delete();
	}

	/**
	 * 폴더 생성
	 * 
	 * @param strDirectory 경로
	 * @return
	 */
	static public boolean createDirectory(String strDirectory) {
		File dInfo = new File(strDirectory);
		dInfo.mkdirs();
		return dInfo.exists();
	}

	/**
	 * 바이트를 문자열로
	 * 
	 * @param bt
	 * @return
	 */
	public static String getByteString(byte[] bt) {
		if (bt == null)
			return "";
		return new String(bt);
	}

	/**
	 * 날짜를 문자열로 변환
	 * 
	 * @return
	 */
	public static String getDateString() {
		return getDateString("");
	}

	/**
	 * 날짜를 문자열로 변환
	 * 
	 * @param dt 날짜
	 * @return
	 */
	public static String getDateString(Date dt) {
		return getDateString(dt, "");
	}

	/**
	 * 날짜를 문자열로 변환
	 * 
	 * @param strSpliter 구분자
	 * @return
	 */
	public static String getDateString(String strSpliter) {
		return getDateString(new Date(), strSpliter);
	}

	/**
	 * 날짜를 문자열로 변환
	 * 
	 * @param dt         날짜
	 * @param strSpliter 구분자
	 * @return
	 */
	public static String getDateString(Date dt, String strSpliter) {
		String strFormat = "yyyy" + strSpliter + "MM" + strSpliter + "dd";
		SimpleDateFormat sf = new SimpleDateFormat(strFormat, Locale.KOREA);
		return sf.format(dt);
	}

	/**
	 * 문자열을 날짜로 변환후 설정된 형식의 문자열로 변환
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getDateFormat(String strDate) {
		return Utilities.getDateFormat(strDate, "/");
	}

	/**
	 * 문자열을 날짜로 변환후 설정된 형식의 문자열로 변환
	 * 
	 * @param strDate 날짜
	 * @param spliter 구분자
	 * @return
	 */
	public static String getDateFormat(String strDate, String spliter) {
		if (Utilities.isEmpty(strDate))
			return null;
		strDate = Utilities.getOnlyNumberString(strDate);
		if (strDate.length() < 8)
			return strDate;
		return strDate.substring(0, 4) + spliter + strDate.substring(4, 6) + spliter + strDate.substring(6, 8);
	}

	/**
	 * 문자열을 시간으로 변환후 설정된 형식의 문자열로 변환
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getTimeFormat(String strDate) {
		return getTimeFormat(strDate, "");
	}

	/**
	 * 문자열을 시간으로 변환후 설정된 형식의 문자열로 변환
	 * 
	 * @param strDate 시간
	 * @param spliter 구분자
	 * @return
	 */
	public static String getTimeFormat(String strDate, String spliter) {
		if (Utilities.isEmpty(strDate))
			return null;
		strDate = Utilities.getOnlyNumberString(strDate);
		if (strDate.length() < 6)
			return strDate;
		return strDate.substring(0, 2) + spliter + strDate.substring(2, 4) + spliter + strDate.substring(4, 6);
	}

	/**
	 * 문자열을 날짜시간으로 변환후 설정된 형식의 문자열로 변환
	 * 
	 * @param strDateTime
	 * @return
	 */
	public static String getDateTimeFormat(String strDateTime) {
		return getDateTimeFormat(strDateTime, "", "");
	}

	/**
	 * 문자열을 시간으로 변환후 설정된 형식의 문자열로 변환
	 * 
	 * @param strDateTime
	 * @param spliterD    날짜 구분자
	 * @param spliterT    시간 구분자
	 * @return
	 */
	public static String getDateTimeFormat(String strDateTime, String spliterD, String spliterT) {
		if (Utilities.isEmpty(strDateTime))
			return null;
		strDateTime = Utilities.getOnlyNumberString(strDateTime);

		if (strDateTime.length() < 14)
			return strDateTime;
		return getDateFormat(strDateTime, spliterD) + "" + getTimeFormat(strDateTime.substring(8));
	}

	/**
	 * 문자열을 Date형식으로 변환
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate) {
		if (strDate == null || strDate.length() == 0)
			return null;
		return parseDate(strDate, "", "", "");

	}

	/**
	 * 문자열을 Date형식으로 변환
	 * 
	 * @param strDate
	 * @param spliter 구분자
	 * @return
	 */
	public static Date parseDate(String strDate, String spliter) {
		if (strDate == null || strDate.length() == 0)
			return null;
		return parseDate(strDate, spliter, spliter, spliter);

	}

	/**
	 * 문자열을 Date형식으로 변환
	 * 
	 * @param strDate   날짜
	 * @param spliterD  날짜 구분자
	 * @param spliterT  시간 구분자
	 * @param separator 날짜 시간 구분자
	 * @return
	 */
	public static Date parseDate(String strDate, String spliterD, String spliterT, String separator) {
		if (strDate == null || strDate.length() == 0)
			return null;
		String strFormat = "yyyy" + spliterD + "MM" + spliterD + "dd" + separator + "HH" + spliterT + "mm" + spliterT
				+ "ss";
		return parseDate(strDate, new SimpleDateFormat(strFormat, Locale.KOREA));

	}

	/**
	 * 문자열을 Date형식으로 변환
	 * 
	 * @param strDate 날짜
	 * @param format  Simpledate format
	 * @return
	 */
	public static Date parseDate(String strDate, SimpleDateFormat format) {
		try {
			return format.parse(strDate);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 파일 크기를 문자열로 변환
	 * 
	 * @param nSize
	 * @return
	 */
	public static String getSizeString(long nSize) {
		int K = 1024;
		int M = K * K;
		int G = M * K;
		if (nSize < K)
			return nSize + "";
		else if (nSize < M) {
			return parseInt(nSize / K + "") + "." + parseLong((nSize % K) * 100 / K + "") + " K";
		} else if (nSize < G) {
			return parseInt(nSize / M + "") + "." + parseLong((nSize % M) * 100 / M + "") + " M";
		} else {
			return parseInt(nSize / G + "") + "." + parseLong((nSize % G) * 100 / G + "") + " G";
		}
	}

	/**
	 * 파일 크기로 문자열로 변환
	 * 
	 * @param nSize
	 * @return
	 */
	// public static String getFileSizeString( Object nSize ) {
	// return FileUtils.byteCountToDisplaySize(parseLong(nSize));
	//
	// }

	/**
	 * linux형식 path로 변환
	 * 
	 * @param fullPath
	 * @return
	 */
	public static String getFilePath(String fullPath) {
		if (fullPath == null) {
			return null;
		}
		fullPath = fullPath.replace('\\', '/');
		int index = fullPath.lastIndexOf("/");
		if (index == -1) {
			index = fullPath.lastIndexOf("\\");
			if (index > -1) {
				return fullPath.substring(0, index);
			}
		}

		return fullPath.substring(0, index);
	}

	/**
	 * 경로를 제외한 파일명 가져오기
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileName(String filepath) {
		if (filepath == null) {
			return null;
		}
		int index = filepath.lastIndexOf("/");
		if (index > -1) {
			return filepath.substring(index + 1);
		}
		index = filepath.lastIndexOf("\\");
		if (index > -1) {
			return filepath.substring(index + 1);
		}

		return filepath;
	}

	/**
	 * 경로와 확장자를 제외한 파일명 가져오기
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileNameWithoutExtension(String filepath) {
		String filename = getFileName(filepath);
		if (filename == null || filename.length() == 0)
			return "";

		int pos = filename.lastIndexOf(".");
		if (pos <= 0)
			return filename;

		return filename.substring(0, pos);
	}

	/**
	 * 문자열 앞부분 trim
	 * 
	 * @param strSource
	 * @param strTrim
	 * @return
	 */
	public static String trimStart(String strSource, String strTrim) {
		if (strTrim == null || strTrim.length() == 0)
			return strSource;
		while (strSource.startsWith(strTrim)) {
			strSource = strSource.substring(strTrim.length());
		}
		return strSource;

	}

	/**
	 * 문자열 뒷부분 trim
	 * 
	 * @param strSource
	 * @param strTrim
	 * @return
	 */
	public static String trimEnd(String strSource, String strTrim) {
		if (strTrim == null || strTrim.length() == 0)
			return strSource;
		if (strSource == null || strSource.length() == 0)
			return "";
		while (strSource.endsWith(strTrim)) {
			int nIndex = strSource.length() - strTrim.length();
			strSource = strSource.substring(0, nIndex);
		}
		return strSource;

	}

	/**
	 * 왼쪽에 padding
	 * 
	 * @param source
	 * @param nSize
	 * @param szPad
	 * @return
	 */
	public static String padLeft(Object source, int nSize, char szPad) {
		String strSource = source == null ? "" : source.toString();

		int nCnt = nSize - strSource.length();
		if (nCnt < 1)
			return strSource;
		for (int i = 0; i < nCnt; i++) {
			strSource = szPad + strSource;

		}
		return strSource;
	}

	/**
	 * 오른쪽 padding
	 * 
	 * @param source
	 * @param nSize
	 * @param szPad
	 * @return
	 */
	public static String padRight(Object source, int nSize, char szPad) {
		String strSource = source == null ? "" : source.toString();

		int nCnt = nSize - strSource.length();
		if (nCnt < 1)
			return strSource;
		for (int i = 0; i < nCnt; i++) {
			strSource = strSource + szPad;
		}
		return strSource;
	}

	/**
	 * 숫자를 왼쪽에 0을 붙여 문자열로 리턴
	 * 
	 * @param nNumber
	 * @param nSize
	 * @return
	 */
	public static String getNumberString(long nNumber, int nSize) {
		return getNumberString(nNumber, nSize, '0');
	}

	/**
	 * 숫자를 왼쪽에 @param chPad 을 붙여 문자열로 리턴
	 * 
	 * @param nNumber
	 * @param nSize
	 * @param chPad
	 * @return
	 */
	public static String getNumberString(long nNumber, int nSize, char chPad) {
		String strNumber = nNumber + "";
		return padLeft(strNumber, nSize, chPad);
	}

	/**
	 * 자바스크립트 오류텍스트 변환
	 * 
	 * @param strValue
	 * @return
	 */
	public static String getScriptText(String strValue) {
		if (strValue == null)
			return "";
		return strValue.replace("\"", "\\\"").replace("\r\n", "\n").replace("\n", "\\n");

	}

	/**
	 * 문자열의 byte 크기를 가져옴
	 * 
	 * @param strText
	 * @return
	 * @throws Exception
	 */

	public static byte[] getStringByte(String strText) throws Exception {
		if (strText == null || strText.length() == 0)
			return new byte[] {};
		return strText.getBytes(_ENC_LANG);
	}

	/**
	 * milisecond tick를 시간형식으로 변환
	 * 
	 * @param time mili-second
	 * @return
	 */
	public static String getTimeString(Object time) {
		return getTimeString(parseLong(time));
	}

	/**
	 * milisecond tick를 시간형식으로 변환
	 * 
	 * @param time mili-second
	 * @return
	 */
	public static String getTimeString(long time) {
		long totalSec = time / 1000;
		long hour = totalSec / 3600;
		long min = (totalSec % 3600) / 60;
		long sec = (totalSec % 3600) % 60;
		return Utilities.padLeft(hour, 2, '0') + "" + Utilities.padLeft(min, 2, '0') + ""
				+ Utilities.padLeft(sec, 2, '0');
	}

	/**
	 * date를 date time 문자열로 변환
	 * 
	 * @param dt
	 * @return
	 */
	public static String getDateTimeString(Date dt) {
		if (dt == null)
			return null;
		return getDateString(dt, "") + "" + getTimeString(dt, "");
	}

	/**
	 * date를 date time 문자열로 변환
	 * 
	 * @return
	 */
	public static String getDateTimeString() {
		return getDateString() + "" + getTimeString();
	}

	/**
	 * date를 time 문자열로 변환
	 * 
	 * @return
	 */
	public static String getTimeString() {
		return getTimeString(new Date(), "");
	}

	/**
	 * date를 time 문자열로 변환
	 * 
	 * @param strSpliter 구분자
	 * @return
	 */
	public static String getTimeString(String strSpliter) {
		return getTimeString(new Date(), strSpliter);

	}

	/**
	 * date를 time 문자열로 변환
	 * 
	 * @param dt
	 * @param strSpliter 구분자
	 * @return
	 */
	public static String getTimeString(Date dt, String strSpliter) {
		try {
			String strFormat = "HH" + strSpliter + "mm" + strSpliter + "ss";
			SimpleDateFormat sf = new SimpleDateFormat(strFormat, Locale.KOREA);
			return sf.format(dt);
		} catch (Exception ex) {
			Utilities.trace(ex);
		}
		return "";
	}

	/**
	 * 고유한 문자열 리턴
	 * 
	 * @return
	 */
	public static String getPKCd() {
		return getPKCd(Long.toHexString(System.currentTimeMillis()).toUpperCase());
	}

	/**
	 * 고유한 문자열 리턴
	 * 
	 * @param prefix
	 * @return
	 */
	public static String getPKCd(String prefix) {
		int length = prefix == null ? 20 : 20 - prefix.length();
		if (length < 0)
			return prefix.substring(0, 20);
		return Utilities.nullCheck(prefix) + Utilities.getUniqID(length);
	}

	/**
	 * 문자 숫자로 고유값 생성
	 * 
	 * @param nLength
	 * @return
	 */
	public static String getUniqID(int nLength) {
		if (nLength < 1)
			return "";
		Random rd = new Random();
		final char chAdded = 'A';
		final char chRange = (char) 26;
		char[] chValue = new char[nLength];
		for (int i = 0; i < nLength; i++) {
			chValue[i] = (char) (rd.nextDouble() * chRange + chAdded);

		}
		return new String(chValue).toUpperCase();
	}

	/**
	 * 숫자로 고유값 생성
	 * 
	 * @param nLength
	 * @return
	 */
	public static String getUniqNumberID(int nLength) {
		if (nLength < 1)
			return "";
		Random rd = new Random();

		final char chAdded = '0';
		final char chRange = (char) 9;
		char[] chValue = new char[nLength];
		for (int i = 0; i < nLength; i++) {
			chValue[i] = (char) (rd.nextDouble() * chRange + chAdded + (i == 0 ? 1 : 0));
		}
		return new String(chValue);
	}

	/**
	 * uuid guid 생성
	 * 
	 * @return
	 */
	public static String getUUID() {
		return String.valueOf(UUID.randomUUID());
	}

	/**
	 * 쓰기가능 여부
	 * 
	 * @param strPath 경로
	 * @return
	 */

	public static boolean isWritableDirectory(String strPath) {
		File dir = new File(strPath);
		return dir.isDirectory() && dir.canWrite();
	}

	/**
	 * 문자열이 Null 일경우 "" 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static String nullCheck(String strValue) {
		return ((strValue == null) ? "" : strValue);
	}

	/**
	 * 문자열이 Null 일경우 "" 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static String nullCheck(Object obj) {
		return ((obj == null) ? "" : obj.toString());
	}

	/**
	 * boolean 형식으로 변환 에러시 false 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean parseBoolean(String strValue) {
		try {
			if (Utilities.isEmpty(strValue))
				return false;
			return Boolean.parseBoolean(strValue);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * boolean 형식으로 변환 에러시 false 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean parseBoolean(Object obj) {
		try {
			if (Utilities.isEmpty(obj))
				return false;
			return Utilities.parseBoolean(obj.toString());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Double 형식으로 변환 에러시 0 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static double parseDouble(String strValue) {
		try {
			return Double.parseDouble(strValue);
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * Double 형식으로 변환 에러시 0 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static double parseDouble(Object obj) {
		try {
			if (obj == null)
				return 0.0;
			return Utilities.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * Float 형식으로 변환 에러시 0 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static float parseFloat(String strValue) {
		try {
			return Float.parseFloat(strValue);
		} catch (Exception e) {
			return 0.0f;
		}
	}

	/**
	 * Float 형식으로 변환 에러시 0 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static float parseFloat(Object obj) {
		try {
			if (Utilities.isEmpty(obj))
				return 0.0f;
			return Utilities.parseFloat(obj.toString());
		} catch (Exception e) {
			return 0.0f;
		}
	}

	/**
	 * int 형식으로 변환 에러시 0 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static int parseInt(Object obj) {
		try {
			if (Utilities.isEmpty(obj))
				return 0;
			return (int) Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * byte 형식으로 변환 에러시 0 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static byte parseByte(Object obj) {
		try {
			if (Utilities.isEmpty(obj))
				return 0;
			return (byte) Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * long 형식으로 변환 에러시 0 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static long parseLong(String strValue) {

		try {
			return Long.parseLong(strValue);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * long 형식으로 변환 에러시 0 리턴
	 * 
	 * @param strValue
	 * @return
	 */
	public static long parseLong(Object obj) {
		try {
			if (obj == null)
				return 0;
			return Utilities.parseLong(obj.toString());
		} catch (Exception e) {
			traceWarning(e);
			return 0;
		}
	}

	public static boolean saveXML(String strFileName, Document xDoc) {
		try {

			return true;
		} catch (Exception ex) {
			trace(ex);
			return false;
		}
	}

	/**
	 * 로그찍기
	 * 
	 * @param ex
	 */
	public static void trace(Exception ex) {
		log.error(ex.getMessage(), ex);
	}

	/**
	 * 로그찍기
	 * 
	 * @param obj
	 */
	public static void trace(Object obj) {
		log.trace(obj.toString());
	}

	/**
	 * 로그찍기
	 * 
	 * @param strLog
	 */
	public static void trace(String strLog) {
		log.info(strLog);
	}

	/**
	 * 로그찍기
	 * 
	 * @param e
	 */
	public static void traceWarning(Exception e) {
		log.debug(e.getMessage(), e);
	}

	/**
	 * 로그찍기
	 * 
	 * @param msg
	 */
	public static void traceWarning(String msg) {
		log.debug(msg);
	}

	/**
	 * 올림함수
	 * 
	 * @param dbNumber
	 * @return
	 */
	public static int Ceiling(double dbNumber) {
		int nNumber = (int) dbNumber;
		if (nNumber < dbNumber)
			nNumber++;
		return nNumber;
	}

	/**
	 * url인코딩하기
	 * 
	 * @param strText
	 * @return
	 */
	public static String getEncodeText(String strText) {
		if (strText == null || strText.length() == 0)
			return "";
		String strRet = "";
		try {
			strRet = URLEncoder.encode(strText, _ENC_LANG);

		} catch (Exception e) {
			strRet = "";
		}
		return strRet;
	}

	/**
	 * url 디코딩하기
	 * 
	 * @param strText
	 * @return
	 */
	public static String getDecodeText(String strText) {
		if (strText == null || strText.length() == 0)
			return "";
		String strRet = "";
		try {
			strText = strText.replace("_amp_", "&");
			strText = strText.replace("_dpy_", "%");

			strRet = java.net.URLDecoder.decode(strText, _ENC_LANG);

		} catch (Exception e) {
			strRet = "";
		}
		return strRet;

	}

	/**
	 * date 형식으로 리턴
	 * 
	 * @param nYear
	 * @param nMonth
	 * @param nDay
	 * @return
	 */
	public static Date getDate(int nYear, int nMonth, int nDay) {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		cd.set(Calendar.MILLISECOND, 0);
		cd.set(Calendar.YEAR, nYear);
		cd.set(Calendar.MONTH, nMonth - 1);
		cd.set(Calendar.DATE, nDay);
		return cd.getTime();
	}

	/**
	 * 현재시간 리턴
	 * 
	 * @return
	 */
	public static Date getCurrentTime() {
		return getCalendar().getTime();
	}

	/**
	 * 현재 Calendar객체 리턴
	 * 
	 * @return
	 */
	public static Calendar getCalendar() {
		Calendar cd = Calendar.getInstance();
		return cd;
	}

	/**
	 * 현재 년
	 * 
	 * @return
	 */
	public static int getYear() {
		return getCalendar().get(Calendar.YEAR);
	}

	/**
	 * 현재월
	 * 
	 * @return
	 */
	public static int getMonth() {
		return getCalendar().get(Calendar.MONTH) + 1;
	}

	/**
	 * 현재일
	 * 
	 * @return
	 */
	public static int getDay() {
		return getCalendar().get(Calendar.DATE);
	}

	/**
	 * Calendar 객체 리턴
	 * 
	 * @param nYear
	 * @param nMonth
	 * @param nDay
	 * @return
	 */
	public static Calendar getCalendar(int nYear, int nMonth, int nDay) {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.YEAR, nYear);
		cd.set(Calendar.MONTH, nMonth - 1);
		cd.set(Calendar.DATE, nYear);
		return cd;
	}

	/**
	 * 파일 확장자 가져오기
	 * 
	 * @param filename
	 * @return
	 */

	public static String getFileExtension(String filename) {
		if (filename == null) {
			return null;
		}
		int index = filename.lastIndexOf(".");
		if (index > -1) {
			return filename.substring(index + 1);
		}
		return "";
	}

	/**
	 * 카멜 형식으로 변환
	 * 
	 * @param list
	 * @return
	 */
	public static String convert2CamelCaseToData(String key) {
		String ret = "data-";
		for (int i = 0; i < key.length(); i++) {
			char sz = key.charAt(i);
			if (sz >= 'A' && sz <= 'Z')
				ret += ("-" + sz).toLowerCase();
			else
				ret += sz;
		}
		return ret;
	}

	/**
	 * 카멜 형식으로 변환
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> convert2CamelCase(Map<String, Object> map) {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			ret.put(convert2CamelCase(key), map.get(key));
		}
		return ret;
	}

	/**
	 * 카멜 형식으로 변환
	 * 
	 * @param underScore
	 * @return
	 */
	public static String convert2CamelCase(String underScore) {

		if (Utilities.isEmpty(underScore))
			return underScore;
		if (underScore.startsWith("_"))
			return underScore;
		if (underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
			return underScore;
		}
		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		int len = underScore.length();

		for (int i = 0; i < len; i++) {
			char currentChar = underScore.charAt(i);
			if (currentChar == '_') {
				nextUpper = true;
			} else {
				if (nextUpper) {
					result.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return result.toString();
	}

	public static List<ITreeVo> makeHierarchy(List<? extends ITreeVo> list, EzMap itemMap) {
		return AbstractTreeVo.makeHierarchy(list, itemMap);
	}

	public static List<ITreeVo> makeHierarchy(List<? extends ITreeVo> list) {

		return AbstractTreeVo.makeHierarchy(list);

	}

	public static String wget(String strUri, String strPost, String token, boolean json) {
		return wget(strUri, strPost, token, json, null, null);
	}

	public static String wget(String strUri, String strPost, String token, boolean json, String method) {
		return wget(strUri, strPost, token, json, method, null);
	}

	public static String wget(String strUri, String strPost, String token, boolean json, String method, EzMap header) {
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;

		try {
			URL url = new URL(strUri);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn == null)
				return "";
			conn.setDoOutput(true);
			conn.setConnectTimeout(2000);
			conn.setUseCaches(false);
			if (strPost != null) {
				if (Utilities.isEmpty(method))
					method = "POST";

				if (json)
					conn.setRequestProperty("Content-Type", "application/json;charset=" + _ENC_LANG);
				else
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + _ENC_LANG);
			} else {
				if (Utilities.isEmpty(method))
					method = "GET";
				if (json)
					conn.setRequestProperty("Content-Type", "application/json;charset=" + _ENC_LANG);
				else
					conn.setRequestProperty("Content-Type", "text/plain");
			}
			conn.setRequestMethod(method);
			if (Utilities.isNotEmpty(token)) {
				conn.setRequestProperty("Authorization", "bearer " + token);
			}
			if (header != null) {
				for (String key : header.keySet()) {
					conn.setRequestProperty(key, header.getString(header.getString(key)));
				}
			}

			if (strPost != null && strPost.length() > 0) {
				osw = new OutputStreamWriter(conn.getOutputStream(), _ENC_LANG);
				osw.write(strPost);
				osw.flush();
			}

			int responseCode = conn.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK)
				return "";

			isr = new InputStreamReader(conn.getInputStream(), _ENC_LANG);
			BufferedReader br = new BufferedReader(isr);

			char[] buffer = new char[1024];
			StringBuffer sb = new StringBuffer();
			do {
				int nRead = br.read(buffer);
				if (nRead <= 0)
					break;

				sb.append(buffer, 0, nRead);
			} while (true);
//			 String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
//			 return sb.toString().replaceAll(match, "");
			return sb.toString().replace("​", "");
		} catch (Exception ex) {

			return "";
		} finally {
			try {
				if (isr != null)
					isr.close();
			} catch (Exception ex) {
			}
			try {
				if (osw != null)
					osw.close();
			} catch (Exception ex) {
			}

		}
	}

	/**
	 * 웹url에서 데이터를읽어 문자열로 리턴
	 * 
	 * @param strUri
	 * @param strPost
	 * @return
	 */
	public static String wget(String strUri) {
		return wget(strUri, null);
	}

	/**
	 * 웹url에서 데이터를읽어 문자열로 리턴
	 * 
	 * @param strUri
	 * @param strPost
	 * @return
	 */
	public static String wget(String strUri, String strPost) {
		return wget(strUri, strPost, null);
	}

	/**
	 * 웹url에서 데이터를읽어 문자열로 리턴
	 * 
	 * @param strUri
	 * @param strPost
	 * @return
	 */
	public static String wget(String strUri, String strPost, String token) {
		return wget(strUri, strPost, token, false);
	}

	/**
	 * 현 서버 아이피
	 * 
	 * @return
	 */
	static public String getServerIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * peer 아이피
	 * 
	 * @return
	 */
	static public String getPeerIp() {
		HttpServletRequest request = getRequest();
		if (request != null) {
			String ip = request.getHeader("X-Forwarded-For");

			if (ip == null) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null) {
				ip = request.getRemoteAddr().replaceAll("0:0:0:0:0:0:0:1", "127.0.0.1");
			}

			return ip;
//			return getRequest().getRemoteAddr().replaceAll("0:0:0:0:0:0:0:1", "127.0.0.1");
		} else
			return null;
	}

	/**
	 * 세션을 가져옵니다.
	 * 
	 * @return
	 */
	static public HttpSession getSession() {
		if (getRequest() != null)
			return getRequest().getSession(true);
		return null;
	}

	/**
	 * 세션에 고유값을 가져옵니다.
	 * 
	 * @return
	 */
	static public String getSessionId() {
		String sessionId = getSession() == null ? null : (String) getSession().getAttribute("timsSessionId");
		if (Utilities.isEmpty(sessionId)) {
			sessionId = Utilities.getUniqID(20);
			setSessionId(sessionId);
		}
		return sessionId;
	}

	/**
	 * 세션에 고유값을 하나 생성합니다.
	 * 
	 * @return
	 */
	static public void setSessionId(String sessionId) {

		if (getSession() != null)
			getSession().setAttribute("timsSessionId", sessionId);
	}

	/**
	 * 반올림
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public double round(double number, int num) {
		double pow = Math.pow(10, num);
		double tmp = number * pow;
		double value = Math.round(tmp);
		return value / pow;
	}

	/**
	 * 반올림
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public double round(float number, int num) {
		double pow = Math.pow(10, num);
		double tmp = number * pow;
		long value = Math.round(tmp);
		return value / pow;
	}

	/**
	 * 반올림 문자열
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public String roundString(double number, int num) {
		double value = Utilities.round(number, num);
		return Utilities.getNumberString(value, num);
	}

	/**
	 * 반올림 문자열
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public String roundString(float number, int num) {
		double value = Utilities.round(number, num);
		return Utilities.getNumberString(value, num);
	}

	/**
	 * 콤마들어가는 형식
	 * 
	 * @param nNumber
	 * @return
	 */
	public static String getMoneyString(long nNumber) {
		return getNumberString(nNumber);
	}

	/**
	 * 콤마들어가는 형식
	 * 
	 * @param nNumber
	 * @return
	 */
	public static String getNumberString(long nNumber) {
		String strMoney = "";
		while (nNumber > 0) {
			if (strMoney.length() > 0)
				strMoney = "," + strMoney;
			strMoney = getNumberString(nNumber % 1000, 3) + strMoney;
			nNumber /= 1000;
			if (nNumber < 1)
				break;
		}
		strMoney = trimStart(strMoney, "0");
		if (strMoney.length() == 0)
			strMoney = "0";
		return strMoney;
	}

	/**
	 * 콤마 들어가는 형식
	 * 
	 * @param number
	 * @param num
	 * @return
	 */
	static public String getNumberString(double number, int num) {
		long nVal = (long) number;
		String n = getNumberString(nVal);
		if (num < 1)
			return n;
		String format = String.format("%" + num + "f", number);
		int nIndex = format.indexOf(".");

		String dbVal = "";
		if (nIndex > -1)
			dbVal = format.substring(nIndex + 1);

		String zero = "";
		;
		for (int i = 0; i < num; i++) {
			zero += "0";
		}
		String db = dbVal + zero;
		db = db.substring(0, num);
		return n + "." + db;
	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param response
	 * @param strFileName
	 * @param strDisplayName
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean DownloadFile(HttpServletResponse response, String strFileName, String strDisplayName,
			long start, long end) {
		return DownloadFile(response, strFileName, strDisplayName, start, end, null);
	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param response
	 * @param strFileName
	 * @param strDisplayName
	 * @param start
	 * @param end
	 * @param contentsType
	 * @return
	 */
	public static boolean DownloadFile(HttpServletResponse response, String strFileName, String strDisplayName,
			long start, long end, String contentsType) {

		if (response == null)
			return false;
		File fDownload = new File(strFileName);
		if (!fDownload.isFile())
			return false;
		int nLength = (int) (end - start + 1);
		response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
		if (Utilities.isEmpty(contentsType))
			contentsType = "application/octet-stream";
		try {
			response.setContentType(contentsType);
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + URLEncoder.encode(strDisplayName, _ENC_LANG) + "\";");
			response.setContentLength(nLength);
			return DownloadStream(response.getOutputStream(), fDownload, start, end);
		} catch (Exception ex) {
			return false;
		}

	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param strFileName
	 * @param strDisplayName
	 * @return
	 */
	public static boolean DownloadFile(String strFileName, String strDisplayName) {
		return DownloadFile(Utilities.getResponse(), strFileName, strDisplayName, null);
	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param response
	 * @param strFileName
	 * @param strDisplayName
	 * @return
	 */
	public static boolean DownloadFile(HttpServletResponse response, String strFileName, String strDisplayName) {
		return DownloadFile(response, strFileName, strDisplayName, null);
	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param response
	 * @param strFileName
	 * @param strDisplayName
	 * @param contentsType
	 * @return
	 */
	public static boolean DownloadFile(HttpServletResponse response, String strFileName, String strDisplayName,
			String contentsType) {

		if (response == null)
			return false;
		File fDownload = new File(strFileName);
		log.debug("file  Path :" + strFileName);
		if (!fDownload.isFile())
			return false;
		int nLength = (int) fDownload.length();
		if (Utilities.isEmpty(contentsType))
			contentsType = "application/octet-stream";
		try {
			response.setContentType(contentsType);
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + URLEncoder.encode(strDisplayName, _ENC_LANG) + "\";");
			response.setContentLength(nLength);
			return DownloadStream(response.getOutputStream(), fDownload);
		} catch (Exception ex) {
			try {
				response.sendError(404);
			} catch (Exception e) {
				Utilities.traceWarning(e);
			}
			return false;
		}

	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param response
	 * @param text
	 * @param strDisplayName
	 * @return
	 */
	public static boolean DownloadText(HttpServletResponse response, String text, String strDisplayName) {

		if (response == null)
			return false;
		int nLength = text.length();

		try {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
					"attachment;filename=\"" + URLEncoder.encode(strDisplayName, _ENC_LANG) + "\";");
			response.setContentLength(nLength);
			return DownloadStream(response.getOutputStream(), text);
		} catch (Exception ex) {
			try {
				response.sendError(404);
			} catch (Exception e) {
				Utilities.traceWarning(e);
			}
			return false;
		}

	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param out
	 * @param text
	 * @return
	 */
	static public boolean DownloadStream(OutputStream out, String text) {
		if (out == null || text == null)
			return false;

		BufferedOutputStream outs = null;
		try {
			outs = new BufferedOutputStream(out);
			try {
				outs.write(text.getBytes(_ENC_LANG), 0, text.length());

			} catch (Exception e) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			log.error("file download error", ex);
			return false;
		} finally {
			try {
				if (outs != null)
					outs.close();

			} catch (Exception e) {

			}
		}
	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param out
	 * @param fDownload
	 * @return
	 * @throws Exception
	 */
	static public boolean DownloadStream(OutputStream out, File fDownload) throws Exception {
		if (out == null || fDownload == null)
			return false;
		if (!fDownload.isFile())
			return false;
		return DownloadStream(out, new FileInputStream(fDownload));

	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param out
	 * @param fDownload
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	static public boolean DownloadStream(OutputStream out, File fDownload, long start, long end) throws Exception {
		if (out == null || fDownload == null)
			return false;
		if (!fDownload.isFile())
			return false;
		return DownloadStream(out, new FileInputStream(fDownload), start, end);

	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param out
	 * @param is
	 * @param start
	 * @param end
	 * @return
	 */
	static public boolean DownloadStream(OutputStream out, InputStream is, long start, long end) {
		if (out == null || is == null)
			return false;

		long size = end - start + 1;
		long left = size;
		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;
		try {
			fin = new BufferedInputStream(is);
			if (start > 0)
				fin.skip(start);
			outs = new BufferedOutputStream(out);
			byte[] buffer = new byte[4096];
			try {
				int nRead = fin.read(buffer);
				while (nRead != -1 && left > 0) {
					int write = (int) (left > nRead ? nRead : left);
					outs.write(buffer, 0, write);
					left -= nRead;
					nRead = fin.read(buffer);
				}
			} catch (Exception e) {
				log.error("file download error", e);
				return false;
			}
			return true;
		} catch (Exception ex) {
			log.error("file download error", ex);
			return false;
		} finally {
			try {
				if (fin != null)
					fin.close();

			} catch (Exception e) {

			}
			try {
				if (outs != null)
					outs.close();

			} catch (Exception e) {

			}
		}
	}

	/**
	 * 브라우저에 파일을 내려줌
	 * 
	 * @param out
	 * @param is
	 * @return
	 */
	static public boolean DownloadStream(OutputStream out, InputStream is) {
		if (out == null || is == null)
			return false;

		BufferedInputStream fin = null;
		BufferedOutputStream outs = null;
		try {
			fin = new BufferedInputStream(is);
			outs = new BufferedOutputStream(out);
			byte[] buffer = new byte[4096];
			try {
				int nRead = fin.read(buffer);
				while (nRead != -1) {
					outs.write(buffer, 0, nRead);
					nRead = fin.read(buffer);
				}
			} catch (Exception e) {
				log.error("file download error", e);
				return false;
			}
			return true;
		} catch (Exception ex) {
			log.error("file download error", ex);
			return false;
		} finally {
			try {
				if (fin != null)
					fin.close();

			} catch (Exception e) {

			}
			try {
				if (outs != null)
					outs.close();

			} catch (Exception e) {

			}
		}
	}

	/**
	 * 파일에서 문자열 읽기
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String readText(File file) throws Exception {
		FileReader reader = new FileReader(file);
		return FileCopyUtils.copyToString(reader);
	}

	/**
	 * 파일 쓰기
	 * 
	 * @param file
	 * @param data
	 * @throws Exception
	 */
	public static void writeFile(File file, byte[] data) throws Exception {
		FileCopyUtils.copy(data, file);
	}

	/**
	 * 빈객체가 아닌지 여부
	 * 
	 * @param obj
	 * @return
	 */
	static public boolean isNotEmpty(Object obj) {
		return !Utilities.isEmpty(obj);
	}

	/**
	 * 빈객체인가
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	static public boolean isEmpty(Object obj) {

		if (obj == null)
			return true;
		else if ((obj instanceof String)) {
			return ((String) obj).isEmpty();
		} else if (obj instanceof Map) {
			return ((Map<?, ?>) obj).isEmpty();
		} else if (obj instanceof Collection) {
			return ((Collection<?>) obj).isEmpty();
		} else if (obj instanceof Object[]) {
			return (((Object[]) obj).length == 0);
		} else if (obj instanceof File) {
			return !((File) obj).exists();
		} else if (obj instanceof List) {
			return ((List) obj).size() == 0;
		}
		try {
			Method method = obj.getClass().getMethod("isEmpty", (Class<?>[]) null);
			return (Boolean) method.invoke(obj);
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 현소스 위치 확인용
	 * 
	 * @return
	 */
	public static StackTraceElement getSourceInfo() {
		try {
			return Thread.currentThread().getStackTrace()[2];

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * request 객체를 가져옵니다.
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		try {
			ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			if (servletContainer != null)
				return servletContainer.getRequest();
			else
				return null;
		} catch (Exception ex) {
			return null;
		}

	}

	/**
	 * response 객체를 가져옵니다.
	 * 
	 * @return
	 */
	static public HttpServletResponse getResponse() {
		HttpServletRequest request = getRequest();
		if (request == null)
			return null;

		return (HttpServletResponse) request.getAttribute("httpServletResponse");

	}

	static public String getRequestBody() {
		return HttpUtil.getBodyDataByRequest(getRequest());
	}

	/**
	 * 이미지 크기
	 * 
	 * @param fileName
	 * @return
	 */
	public static Dimension getImageSize(String fileName) {
		try {
			BufferedImage bimg = ImageIO.read(new File(fileName));
			int width = bimg.getWidth();
			int height = bimg.getHeight();
			return new Dimension(width, height);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 중복파일명 변경
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> renameFilenames(List<String> list) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> sList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			String name = list.get(i);
			if (map.containsKey(name)) {
				for (int j = 1; j < 100000; j++) {
					String name2 = name + "(" + j + ")";
					if (map.containsKey(name2))
						continue;
					else {
						map.put(name2, name);
						sList.add(name2);
						break;
					}
				}
			} else {
				map.put(name, name);
				sList.add(name);
			}
		}
		return sList;
	}

	/**
	 * 쿠키를 가져옵니다.
	 * 
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(String cookieName) {
		if (isEmpty(cookieName))
			return null;
		Cookie[] cookies = getRequest().getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookieName.equals(cookies[i].getName()))
				return cookies[i].getValue();
		}
		return null;
	}

	/**
	 * 쿠키를 가져옵니다.
	 * 
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(String cookieName) {
		if (isEmpty(cookieName))
			return null;
		Cookie[] cookies = getRequest().getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookieName.equals(cookies[i].getName()))
				return cookies[i];
		}
		return null;
	}

	/**
	 * 쿠키설정
	 * 
	 * @param name
	 * @param value
	 */
	public static void setCookie(String name, String value) {
		HttpServletResponse response = getResponse();
		value = value == null ? null : value.replace("\n", "").replace("\r", "");
		Cookie cookie = new Cookie(name, value);
//		cookie.setSecure(true);
		cookie.setPath("/");

		response.addCookie(cookie);
	}

	/**
	 * spring singlton 객체 가져오기
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> cls) {

		try {
			return (T) context.getBean(cls);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * spring singlton 객체 가져오기
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		try {
			return (T) context.getBean(name);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 폴더의 파일 객체 가져오기
	 * 
	 * @param path
	 * @param list
	 * @return
	 */
	public static List<String> getFiles(String path, List<String> list) {
		File fPath = new File(path);
		if (!fPath.isDirectory())
			return null;
		File[] fList = fPath.listFiles();
		for (int i = 0; i < fList.length; i++) {
			File file = fList[i];
			if (file.isFile())
				list.add(file.getPath());
			if (file.isDirectory()) {
				getFiles(file.getPath(), list);
			}
		}
		return list;
	}

	/**
	 * 폴더의 파일객체 가져오기
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getFiles(String path) {
		return getFiles(path, new ArrayList<String>());

	}

	/**
	 * 모바일 접속여부
	 * 
	 * @return
	 */
	public static boolean isMobile() {
		try {
			HttpServletRequest request = getRequest();
			String userAgent = request.getHeader("user-agent");
			if (Utilities.isEmpty(userAgent))
				return false;
			boolean mobile1 = userAgent.matches(
					".*(iPad|iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
			boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
			if (mobile1 || mobile2) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 16진수 문자열 byte로
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() % 2 != 0) {
			return new byte[] {};
		}

		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < hex.length(); i += 2) {
			byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
			bytes[(int) Math.floor(i / 2)] = value;
		}
		return bytes;
	}

	/**
	 * 파일쓰기
	 * 
	 * @param fileName
	 * @param content
	 * @throws Exception
	 */
	public static void writeFile(String fileName, String content) throws Exception {
		FileOutputStream fileOutputStream = null;
		BufferedWriter out = null;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			OutputStreamWriter OutputStreamWriter = new OutputStreamWriter(fileOutputStream, "EUC-KR");
			out = new BufferedWriter(OutputStreamWriter);
			if (Utilities.isNotEmpty(content))
				out.write(content);

		} catch (Exception ex) {
			Utilities.traceWarning(ex);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception ex) {
				Utilities.traceWarning(ex);
			}
		}

	}

	/**
	 * #{name} 이 포함 된 문자열을 변환
	 * 
	 * @param contents
	 * @param map
	 * @return
	 */
	public static String makeParamText(String contents, Map<String, String> map) {
		if (map == null)
			return contents;
		if (isEmpty(contents))
			return contents;

		for (Map.Entry<String, String> elem : map.entrySet()) {
			String name = "#{" + elem.getKey() + "}";
			String value = Utilities.nullCheck(elem.getValue());
			contents = contents.replace(name, value);
		}
		return contents;

	}

	/**
	 * 문자열에서 숫자만 가져온다
	 * 
	 * @param str
	 * @return
	 */
	public static String getOnlyNumberString(Object str) {
		if (str == null)
			return null;
		if (Utilities.isEmpty(str))
			return str.toString();
		return str.toString().replaceAll("[^0-9]", "");
	}

	/**
	 * 전화번호 형식으로 변환
	 * 
	 * @param str
	 * @return
	 */
	public static String getPhoneNumberFormat(Object phoneNumber) {
		if (Utilities.isEmpty(phoneNumber))
			return Utilities.nullCheck(phoneNumber);
		String numbers = Utilities.getOnlyNumberString(phoneNumber);
		if (numbers.length() == 10)
			return numbers.substring(0, 3) + "-" + numbers.substring(3, 6) + "-" + numbers.substring(6);
		else if (numbers.length() >= 11) {
			return numbers.substring(0, 3) + "-" + numbers.substring(3, 7) + "-" + numbers.substring(7);
		}
		return Utilities.nullCheck(phoneNumber);
	}

	/**
	 * 웹서버의 리얼Path리턴
	 * 
	 * @param webPath
	 * @return
	 */
	public static String getWebPathToRealPath(String webPath) {

		return servletContext.getRealPath(webPath);
	}

	/**
	 * 웹서버의 리얼Path리턴
	 * 
	 * @param webPath
	 * @return
	 */
	public static String getRealPath(String path) {
		return servletContext.getRealPath(path);
	}

	/**
	 * 현재사용자 언어 리턴
	 * 
	 * @return
	 */
	public static String getLangCd() {
		String langCd = "KO";
		HttpSession session = Utilities.getSession();
		if (session == null)
			return langCd;
		langCd = (String) session.getAttribute("langCd");
		if (Utilities.isEmpty(langCd))
			langCd = "KO";
		return langCd;
	}

	static public String passwordEncode(String pwd, String saltKey) {
		try {
			return Sha2Crypt.sha256Crypt(pwd.getBytes(_ENC_LANG), saltKey);
		} catch (UnsupportedEncodingException e) {
			return null;
		}

//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder.encode(pwd);
	}

	static public String passwordEncode(String pwd) {
		return passwordEncode(pwd, _SALT_KEY);
	}

	static public boolean passwordMatch(String pwd, String encodedPwd) {
		if (isEmpty(encodedPwd))
			return false;
		if (encodedPwd.length() < 11)
			return false;
		String salt = encodedPwd.substring(0, 11);
		String enc = passwordEncode(pwd, salt);
		return encodedPwd.equals(enc);

//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder.matches(pwd, encodedPwd);
	}

	/**
	 * 암호화
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	static public String encrypt(String text) throws Exception {
		return KisaSeed256.encrypt(text);
	}

	/**
	 * 복호화
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	static public String decrypt(String text) throws Exception {
		return KisaSeed256.decrypt(text);
	}

	@SuppressWarnings("rawtypes")
	public static <T> T beanToBean(Object obj, Class cls) {

		return mapToBean(beanToMap(obj), cls);

	}

	public static boolean beanToBean(Object target, Object src) {
		try {
			Map<String, Object> map = Utilities.beanToMap(src);
			Utilities.mapToBean(map, target);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T mapToBean(Map map, Class cls) {
		return (T) objectMapper.convertValue(map, cls);

	}

	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> convertMapList(List list) {
		if (list == null)
			return null;
		List<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			arr.add(beanToMap(list.get(i)));
		}
		return arr;
	}

	public static Map<String, Object> beanToMap(Object obj) {
		if (obj == null)
			return null;
		return (Map<String, Object>) objectMapper.convertValue(obj, EzMap.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean beanToMap(Map target, Object obj) {
		if (target == null)
			return false;
		EzMap map = objectMapper.convertValue(obj, EzMap.class);
		target.putAll((Map) map);
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean mapToBean(Map map, Object bean) {
		if (bean == null || map == null)
			return false;
		if (map == null || bean == null)
			return false;
		try {
			BeanUtils.populate(bean, map);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static List<Map<String, Object>> beanToMap(List<?> list) {
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			l.add(beanToMap(list.get(i)));
		}
		return l;

	}

	public static String encodeBase64(byte[] value) {
		return Base64.encode(value);
	}

	static public String encodeBase64(String value) throws Exception {
		return encodeBase64(value.getBytes());
	}

	static public String decodeBase64(String value) {
		try {
			int offset = value.length() % 4;
			String off = "";
			for (int i = 0; i < offset; i++) {
				off += "=";
			}
			value += off;
			return new String(Base64.decode(value), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static byte[] decodeBase64Byte(String value) {

		return Base64.decode(value);
	}

	public static <T> T parseJson(String json, Class<T> clz) throws Exception {
		try {
			return objectMapper.readValue(json, clz);
		} catch (Exception ex) {
			return null;
		}

	}

	public static <T> T wgetJson(String jsonUrl, Class<T> clz) throws Exception {
		try {
			URL url = new URL(jsonUrl);
			return objectMapper.readValue(url, clz);
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getJson(String json) throws Exception {
		return parseJson(json, Map.class);

	}

	public static String getJsonString(Object obj) throws Exception {
		if (obj == null)
			return null;
		if (obj instanceof String)
			return (String) obj;
		return objectMapper.writeValueAsString(obj);
	}

	public static String getJsonPString(Object obj, String callback) throws Exception {
		if (Utilities.isEmpty(callback))
			return objectMapper.writeValueAsString(obj);
		else
			return callback + "(" + objectMapper.writeValueAsString(obj) + ")";
	}

	/**
	 * 파일압축
	 * 
	 * @param zipName
	 * @param targets
	 * @param dispNames
	 * @return
	 */
	static public boolean zip(String zipName, List<File> targets, List<String> dispNames) {
		return PKZip.zip(zipName, targets, dispNames);
	}

	/**
	 * 파일압축
	 * 
	 * @param zipName
	 * @param target
	 * @return
	 */
	static public boolean zip(String zipName, String target) {
		return PKZip.zip(zipName, target);
	}

	/**
	 * 파일압축
	 * 
	 * @param zipName
	 * @param targets
	 * @return
	 */
	static public boolean zip(String zipName, List<String> targets) {
		return PKZip.zip(zipName, targets);
	}

	/**
	 * 압축풀기
	 * 
	 * @param strZipfilename
	 * @param strTargetDir
	 * @return
	 */
	static public boolean unZip(String strZipfilename, String strTargetDir) {
		return PKZip.unZip(strZipfilename, strTargetDir);
	}

	/**
	 * 엑셀을 읽어 리스트로 리턴
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> excelToList(File file) throws Exception {

		return ExcelUtil.excelToList(file);
	}

	public static Locale getLocale() {
//		HttpServletRequest request = getRequest();
//		if (request != null)
//			return localeResolver.resolveLocale(request);
//		else {
//			return new Locale("ko");
//		}
		return new Locale("ko");
	}

	public static String getLocaleLang() {
		return getLocale().getLanguage();
	}

	public static String getMessage(String code) {
		return getMessage(code, null, getLocale());
	}

	public static String getMessage(String code, Object[] arg) {
		return getMessage(code, arg, getLocale());
	}

	public static String getMessage(String code, Locale locale) {
		return getMessage(code, null, locale);
	}

	public static String getMessage(String code, Object[] arg, Locale locale) {
		return messageSource.getMessage(code, arg, locale);
	}

	public static String getProperty(String name) {
		return propertiesService.getString(name, "");
	}

	public static int getPropertyInt(String name) {
		return propertiesService.getInt(name, 0);
	}

	public static long getPropertyLong(String name) {
		return propertiesService.getLong(name, 0);
	}

	public static double getPropertyDouble(String name) {
		return propertiesService.getDouble(name, 0);
	}

	public static double getPropertyFloat(String name) {
		return propertiesService.getFloat(name, 0);
	}

	public static boolean getPropertyBoolean(String name) {
		return propertiesService.getBoolean(name, false);
	}

	public static EzMap getGridData(List<? extends Object> list, int totalRecordCount, int currentPageNo, int pageSize,
			int recordCountPerPage) {
		EzPaginationInfo page = new EzPaginationInfo();
		page.setCurrentPageNo(currentPageNo);
		page.setTotalRecordCount(totalRecordCount);
		page.setPageSize(pageSize);
		page.setRecordCountPerPage(recordCountPerPage);
		return getGridData(list, page);
	}

	public static EzMap getGridData(List<? extends Object> list, EzPaginationInfo page) {
		EzMap ezMap = new EzMap();
		EzMap data = new EzMap();
//        ezMap.put( "list", list );
//        ezMap.put( "pagination", page );
		ezMap.put("result", true);
		ezMap.put("data", data);
		data.put("contents", list);
		data.put("pagination", page.toToastPage());

		return ezMap;
	}

	public static EzMap getGridData(List<? extends Object> list) {
		EzMap ezMap = new EzMap();
		EzMap data = new EzMap();
		ezMap.put("result", true);
		ezMap.put("data", data);
		data.put("contents", list);

		return ezMap;
	}

	public static EzMap getInsertResult(int count) {
		return getInsertResult(count, null);
	}

	public static EzMap getInsertResult(int count, Object data) {
		EzMap ret = new EzMap();
		ret.put("insert", count);
		if (data != null)
			ret.put("result", data);
		return ret;
	}

	public static EzMap getUpdateResult(int count) {
		return getUpdateResult(count, null);
	}

	public static EzMap getUpdateResult(int count, Object data) {
		EzMap ret = new EzMap();
		ret.put("update", count);
		if (data != null)
			ret.put("result", data);
		return ret;
	}

	public static EzMap getDeleteResult(int count) {
		EzMap ret = new EzMap();
		ret.put("delete", count);
		return ret;
	}

	public static EzMap addResult(EzMap src, EzMap result) {
		if (src == null || result == null)
			return src;
		int insert = src.getInt("insert") + result.getInt("insert");
		int update = src.getInt("update") + result.getInt("update");
		int delete = src.getInt("delete") + result.getInt("delete");
		src.put("insert", insert);
		src.put("update", update);
		src.put("delete", delete);
		src.put("change", insert + update + delete);
		return src;
	}

	public static EzMap getSaveResult(List<EzMap> resultList) {
		EzMap result = new EzMap();
		result.put("resultList", resultList);
		for (int i = 0; i < resultList.size(); i++) {
			Utilities.addResult(result, resultList.get(i));
		}
		return result;
	}

	public static boolean sendMail(String emailTo, String subject, String body) throws Exception {
		return MailUtil.sendMail(emailTo, subject, body, null, null);
	}

	public static boolean sendMail(String emailTo, String subject, String body, List<String> attFiles,
			List<String> dispnames) throws Exception {
		return MailUtil.sendMail(emailTo, subject, body, attFiles, dispnames);
	}

	public static List<EzMap> convertCategoryTree(List<? extends EzMap> list) {
		return Utilities.convertTreeJson(list, "ctgryCd", "ctgryNm", "uprCtgryCd");
	}

	public static List<EzMap> convertTreeJson(List<? extends Object> list, String idKey, String textKey,
			String parentIdKey) {

		List<EzMap> treeList = new ArrayList<EzMap>();
		EzMap dataMap = new EzMap(false);
		for (int i = 0; i < list.size(); i++) {
			EzMap item = new EzMap(Utilities.beanToMap(list.get(i)));
			EzTreeMap treeItem = new EzTreeMap();
			List<ITreeVo> chilren = treeItem.getChildren();
			treeItem.putAll(item);
			treeItem.setNodes(chilren);
			treeItem.setString("id", item.get(idKey));
			treeItem.setString("parentId", item.get(parentIdKey));
			treeItem.setString("text", item.get(textKey));

			dataMap.put(treeItem.getId(), treeItem);
		}

		for (String key : dataMap.keySet()) {
			EzTreeMap treeItem = (EzTreeMap) dataMap.get(key);
			String parentId = treeItem.getParentId();
			if (Utilities.isEmpty(parentId) || !dataMap.containsKey(parentId)) {
				treeList.add(treeItem);
				treeItem.setInt("ord", treeList.size());
			} else {
				EzTreeMap parent = (EzTreeMap) dataMap.get(parentId);
				parent.addChild(treeItem);
				treeItem.setInt("ord", parent.getChildrenCount());
			}
		}
		return treeList;
	}

	public static LoginUserVo getLoginUser() {
		HttpSession session = getSession();
		if (session == null)
			return null;
		return (LoginUserVo) session.getAttribute(Constants._LOGIN_SESSION_NAME);
	}

	public static String getUserId() {
		LoginUserVo loginUser = getLoginUser();
		if (loginUser == null)
			return null;
		return loginUser.getUserId();

	}

	public static String getLoginId() {
		// return "2020000001";
		LoginUserVo loginUser = getLoginUser();
		if (loginUser == null)
			return null;
		return loginUser.getLoginId();

	}

	public static boolean isLogin() {

		return Utilities.isNotEmpty(getLoginId());
	}

	public static String getAutoSeq(String prefix) throws Exception {
		EzMap param = new EzMap();
		param.setString("prefix", prefix);
		return getAutoSeq(param);
	}

//	public static String getAutoSeq(String prefix, String suffix) throws Exception {
//		EzMap param = new EzMap();
//		param.setString("prefix", prefix);
//		param.setString("suffix", suffix);
//		return getAutoSeq(param);
//	}

	public static String getAutoSeq(Map<String, Object> param) throws Exception {
		return commonService.getAutoSeq(param);
	}

	public static String getFileId() throws Exception {
		EzMap param = new EzMap();
		param.setString("prefix", "FL");
		return getAutoSeq(param);
	}

	public static MenuBaseVo getCurrentMenu() {
		HttpServletRequest request = getRequest();
		if (request == null)
			return null;

		return (MenuBaseVo) request.getAttribute("currentMenu");
	}

	public static String getCurrentMenuId() {
		MenuBaseVo menu = getCurrentMenu();
		if (menu == null)
			return null;
		return menu.getMenuId();
	}
}
