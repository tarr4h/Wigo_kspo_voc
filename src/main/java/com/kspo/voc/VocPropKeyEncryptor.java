package com.kspo.voc;

import org.jasypt.encryption.StringEncryptor;

import com.kspo.voc.comn.config.JasyptConfiguration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VocPropKeyEncryptor {
//	public static void main(String[] args) {
//		getEncValue("abcdefg");
//	}

	public static String getEncValue(String value) {
		StringEncryptor encryptor = new JasyptConfiguration().stringEncryptor();
		String enc = encryptor.encrypt(value);
		log.info(value + " ==> ENC(" + enc + ")");
		return "ENC(" + enc + ")";
	}
}
