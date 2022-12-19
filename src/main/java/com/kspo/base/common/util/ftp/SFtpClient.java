package com.kspo.base.common.util.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.kspo.voc.comn.util.Utilities;

import lombok.extern.slf4j.Slf4j;

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
	public String sendFile(String localFileName, String serverFileName) throws EgovBizException {
		String path = Utilities.getFilePath(serverFileName);
		String fileName = Utilities.getFileNameWithoutExtension(serverFileName);
		String fileExt = Utilities.getFileExtension(serverFileName);
		try {
			sftpAttrs = channelSftp.stat(path);
		} catch (SftpException e) {
			log.warn(e.getMessage());
		}
		if (sftpAttrs == null) {
			try {
				String cPath = "";
				String[] arr = path.split("/");
				for (int i = 0; i < arr.length; i++) {
					String str = arr[i];
					if (Utilities.isNotEmpty(str)) {
						cPath += "/" + str;
						try {
							channelSftp.stat(cPath);
						} catch (SftpException e) {
							channelSftp.mkdir(cPath);
						}
					}
				}

			} catch (SftpException e) {
				log.debug("Could not create path : " + path);
				throw new EgovBizException(e.getMessage(),e);
			}
		}
//		중복파일 확인
		Vector v = null;
		try {
			v = channelSftp.ls(serverFileName);
		} catch (SftpException ex) {
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

		} catch (SftpException | FileNotFoundException e) {
			throw new EgovBizException(e.getMessage(),e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch ( IOException e) {
				log.error(e.toString());
			}

		}

	}

	@Override
	public String receiveFile(String serverFileNm, String localFileName) throws EgovBizException {
		File file = new File(localFileName);
		String path = Utilities.getFilePath(localFileName);
		String fileName = Utilities.getFileNameWithoutExtension(localFileName);
		String fileExt = Utilities.getFileExtension(localFileName);
		int cnt = 1;
		while (file.exists()) {
			cnt++;
			localFileName = path + "/" + fileName + "(" + cnt + ")." + fileExt;
			file = new File(localFileName);
		}
		Utilities.createDirectory(path);
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

		} catch (SftpException | IOException e) {
			throw new EgovBizException(e.getMessage(),e);
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
