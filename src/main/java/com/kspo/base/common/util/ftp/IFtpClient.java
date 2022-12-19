package com.kspo.base.common.util.ftp;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

/**
 * 
* <pre>
* com.wigo.crm.common.util.ftp
*	- IFtpClient.java
* </pre>
*
* @ClassName	: IFtpClient 
* @Description	: Ftp 인터페이스
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public interface IFtpClient {
	
	/**
	 * 파일전송
	  * @param serverFileNm
	 * @param localFileName
	 * @throws EgovBizException
	 */
	String sendFile(String localFileName, String serverFileName) throws EgovBizException ;
	
	/**
	 * 파일다운로드
	 * @param serverFileNm
	 * @param localFileName
	 * @throws EgovBizException
	 */
	String receiveFile(String serverFileNm, String localFileName) throws  EgovBizException ;

	/**
	 * 접속종료
	 * @throws EgovBizException
	 */
	void disconnect() throws EgovBizException ;
	
}
