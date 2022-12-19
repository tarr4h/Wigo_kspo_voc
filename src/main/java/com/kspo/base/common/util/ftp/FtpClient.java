package com.kspo.base.common.util.ftp;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.*;

import com.kspo.base.common.util.BaseUtilities;

import java.io.*;

/**
 * 
 * <pre>
 * com.wigo.crm.common.util.ftp - FtpClient.java
 * </pre>
 *
 * @ClassName : FtpClient
 * @Description : Ftp 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

@Slf4j
public class FtpClient implements IFtpClient {
	/** 서버 아이피 */
	private String SERVERIP = "";
	/** 접속 포트 */
	private int PORT = 0;
	/** 접속 아이디 */
	private String ID = "";
	/** 접속 비밀번호 */
	private String PWD = "";
	/** 보안채널 */
//	private boolean ftps;
	FTPClient ftpClient = null;

	public FtpClient(String strServerIP, int nServerPort, String strID, String strPWD, boolean ftps) {
		log.debug("sftp Init start host : [" + strServerIP + "]  port : [" + nServerPort + "]  userName : [" + strID
				+ "]");

		ftpClient = ftps ? new FTPSClient(true) : new FTPClient();

		SERVERIP = strServerIP;
		PORT = nServerPort;
		ID = strID;
		PWD = strPWD;
//		this.ftps=ftps;
		connect();
		login();
		if (ftps) {
			FTPSClient ftpsClient = (FTPSClient) ftpClient;
			try {
				ftpsClient.execPBSZ(0);
				ftpsClient.execPROT("P");
			} catch (Exception e) {
			}

		}
	}

	@Override
	public String sendFile(String localFileName, String serverFileName) throws Exception {
		String path = BaseUtilities.getFilePath(serverFileName);
		String fileName = BaseUtilities.getFileNameWithoutExtension(serverFileName);
		String fileExt = BaseUtilities.getFileExtension(serverFileName);
		BufferedInputStream bi = null;
		try {
			File f = new File(localFileName);
			bi = new BufferedInputStream(new FileInputStream(f.getPath()));
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// FTP 서버의 폴더...
			if (BaseUtilities.isNotEmpty(path)) {
				if (!ftpClient.changeWorkingDirectory(path)) {
					ftpClient.mkd(path);
					ftpClient.changeWorkingDirectory(path);

				}
			}
			FTPFile[] files = ftpClient.listFiles(serverFileName);
//				String file = serverFileName;
			int cnt = 1;
			while (files.length > 0) {
				cnt++;
				serverFileName = path + "/" + fileName + "(" + cnt + ")." + fileExt;
				files = ftpClient.listFiles(serverFileName);
			}
			ftpClient.storeFile(serverFileName, bi);
			return serverFileName;
		} finally {
			if (bi != null)
				try {
					bi.close();
				} catch (IOException e) {

				}
		}
	}

	@Override
	public String receiveFile(String serverFileNm, String localFileName) throws Exception {
		File file = new File(localFileName);
		String path = BaseUtilities.getFilePath(localFileName);

		String fileName = BaseUtilities.getFileNameWithoutExtension(localFileName);
		String fileExt = BaseUtilities.getFileExtension(localFileName);
		int cnt = 1;
		while (file.exists()) {
			cnt++;
			localFileName = path + "/" + fileName + "(" + cnt + ")." + fileExt;
			file = new File(localFileName);
		}
		String serverPath = BaseUtilities.getFilePath(serverFileNm);
		String serverFileName = BaseUtilities.getFileName(serverFileNm);
		if (BaseUtilities.isEmpty(serverPath))
			serverPath = "/";
		FileOutputStream fo = null;
		try {
			ftpClient.enterLocalPassiveMode();
			// 로컬경로 및 파일 중복 확인
			ftpClient.changeWorkingDirectory(serverPath);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			BaseUtilities.createDirectory(path);

			fo = new FileOutputStream(localFileName);
			ftpClient.retrieveFile(serverFileName, fo);
			return localFileName;
		} finally {
			if (fo != null)
				try {
					fo.close();
				} catch (Exception e) {

				}
		}
	}

	// 계정과 패스워드로 로그인
	public boolean login() {
		try {
			return ftpClient.login(ID, PWD);
		} catch (Exception ioe) {
//	            ioe.printStackTrace(); 
		}
		return false;
	}

	// 서버로부터 로그아웃
	public boolean logout() {
		try {
			return ftpClient.logout();
		} catch (Exception ioe) {
//	            ioe.printStackTrace(); 
		}
		return false;
	}

	// 서버로 연결
	public void connect() {
		try {

			ftpClient.connect(SERVERIP, PORT);
			int reply;
			// 연결 시도후, 성공했는지 응답 코드 확인
			reply = ftpClient.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
			}
		} catch (IOException ioe) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException f) {
					f.printStackTrace();
				}
			}

		}
	}

	// FTP의 ls 명령, 모든 파일 리스트를 가져온다
	public FTPFile[] list() {
		FTPFile[] files = null;
		try {
			files = this.ftpClient.listFiles();
			return files;
		} catch (IOException ioe) {
//	            ioe.printStackTrace(); 
		}
		return null;
	}

	// 서버로부터 연결을 닫는다
	public void disconnect() {
		try {
			ftpClient.disconnect();
			log.info("ftp connection close");

		} catch (IOException ioe) {
//	            ioe.printStackTrace(); 
		}
	}

}
