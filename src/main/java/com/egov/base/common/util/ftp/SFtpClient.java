package com.egov.base.common.util.ftp;

import com.egov.base.common.util.BaseUtilities;
import com.jcraft.jsch.*;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Vector;

/**
 * 
 * <pre>
 * com.wigo.crm.common.util.ftp - SFtpClient.java
 * </pre>
 *
 * @ClassName : SFtpClient
 * @Description : SFtp 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

@Slf4j
public class SFtpClient implements IFtpClient {
	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	private SftpATTRS sftpAttrs = null;

	public SFtpClient(String host, int port, String userName, String password) throws JSchException {
		JSch jsch = new JSch();
		try {
			log.debug("sftp Init start host : [" + host + "]  port : [" + port + "]  userName : [" + userName + "]");
			session = jsch.getSession(userName, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();

			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "password");

			session.setConfig(config);
			log.debug("session connection start");
			session.connect();
			channel = session.openChannel("sftp");
			log.debug("channel connection start");
			channel.connect();
			log.info("sftp channel connection seccess");

		} catch (JSchException e) {
			throw e;

		}
		channelSftp = (ChannelSftp) channel;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String sendFile(String localFileName, String serverFileName) throws Exception {
		String path = BaseUtilities.getFilePath(serverFileName);
		String fileName = BaseUtilities.getFileNameWithoutExtension(serverFileName);
		String fileExt = BaseUtilities.getFileExtension(serverFileName);
		try {
			sftpAttrs = channelSftp.stat(path);
		} catch (Exception e) {
			log.debug("path not found : " + path);
		}
		if (sftpAttrs == null) {
			try {
				String cPath = "";
				String[] arr = path.split("/");
				for (int i = 0; i < arr.length; i++) {
					String str = arr[i];
					if (BaseUtilities.isNotEmpty(str)) {
						cPath += "/" + str;
						try {
							channelSftp.stat(cPath);
						} catch (Exception e) {
							channelSftp.mkdir(cPath);
						}
					}
				}

			} catch (SftpException e) {
				log.debug("Could not create path : " + path);
				throw e;
			}
		}
//		중복파일 확인
		Vector v = null;
		try {
			v = channelSftp.ls(serverFileName);
		} catch (Exception ex) {
			v = null;
		}

		int cnt = 1;
		while (v != null && v.size() > 0) {
			cnt++;
			serverFileName = path + "/" + fileName + "(" + cnt + ")." + fileExt;
			try {
				v = channelSftp.ls(serverFileName);
			} catch (SftpException ex) {
				break;
			}

		}
		File file = new File(localFileName);
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			channelSftp.put(in, serverFileName);
			log.debug("sendFile OK");
			return serverFileName;

		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
				log.error(e.toString());
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
		BaseUtilities.createDirectory(path);
		InputStream in = null;
		OutputStream out = null;
		try {
			// 경로탐색후 inputStream에 데이터를 넣음
			in = channelSftp.get(serverFileNm);
			out = new BufferedOutputStream(new FileOutputStream(localFileName));
			byte[] buffer = new byte[4096];
			int read = in.read(buffer);
			while (read > 0) {
				out.write(buffer, 0, read);
				read = in.read(buffer);
			}
			return localFileName;

		} catch (Exception se) {
			throw se;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				log.error(e.toString());
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.error(e.toString());
			}

		}
	}

	public void disconnect() {
		channelSftp.quit();
		log.info("sftp connection close");

	}

}
