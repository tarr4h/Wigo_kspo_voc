package com.egov.voc.base.common.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 
* <pre>
* com.wigo.crm.common.util
*	- MailUtil.java
* </pre>
*
* @ClassName	: MailUtil 
* @Description	: 메일유틸리티 
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class MailUtil {
	public static boolean sendMail(String emailTo, String subject, String body,List<String> attFiles,List<String> dispnames) throws Exception {
		boolean ssl = BaseUtilities.getPropertyBoolean("smtp.ssl");
		String host = BaseUtilities.getProperty("smtp.host");
		int port = BaseUtilities.getPropertyInt("smtp.port");
//		String userTmp =  BaseUtilities.encrypt(BaseUtilities.getProperty("smtp.user"));
		String userTmp = BaseUtilities.getProperty("smtp.user");
		String user = BaseUtilities.decrypt(userTmp);
//		String pwdTmp =  BaseUtilities.encrypt(BaseUtilities.getProperty("smtp.pwd"));
		String pwdTmp = BaseUtilities.getProperty("smtp.pwd");
		String password = BaseUtilities.decrypt(pwdTmp);
		// 메일 옵션 설정
		String nameFrom = BaseUtilities.getProperty("smtp.sender.name");
		String emailFrom = BaseUtilities.getProperty("smtp.sender.email");
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		if (ssl) {
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.trust", host);

			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
		}
		Session session = null;
		if (BaseUtilities.isEmpty(user) || BaseUtilities.isEmpty(password)) {
			session = Session.getInstance(props);
		} else {
			session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
		}
		

		Multipart mParts = new MimeMultipart();

		MimeBodyPart mTextPart = new MimeBodyPart();
		mTextPart.setText(body, "UTF-8", "html");
		mParts.addBodyPart(mTextPart);
		for (int i = 0; attFiles != null && i < attFiles.size(); i++) {
			String fileName = attFiles.get(i);
			File file = new File(fileName);
			String name = file.getName();
			if(dispnames!=null && dispnames.size()>i) {
				name = dispnames.get(i);
			}
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setFileName(MimeUtility.encodeText(name, "UTF-8", "B"));
			bodyPart.setDescription(name, "UTF-8");
			FileDataSource fs = new FileDataSource(file);
			DataHandler dh = new DataHandler(fs);
			bodyPart.setDataHandler(dh);
			Path path = Paths.get(file.getCanonicalPath());
			String mime = Files.probeContentType(path);
			bodyPart.addHeader("Content-Type", mime);
			mParts.addBodyPart(bodyPart);
		}
		MimeMessage msg = new MimeMessage(session);

		InternetAddress fromAddress = new InternetAddress(emailFrom, nameFrom);
		msg.setFrom(fromAddress);
		msg.setSender(fromAddress);
		InternetAddress toAddress = new InternetAddress(emailTo);
		msg.setRecipient(RecipientType.TO, toAddress);
		msg.setSubject(subject);
		
		msg.setContent(mParts);
		msg.setSentDate(new Date());
		Transport.send(msg);
		return true;
	}
}
