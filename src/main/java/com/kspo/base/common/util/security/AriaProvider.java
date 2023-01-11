package com.kspo.base.common.util.security;

import java.security.InvalidKeyException;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * <pre>
 * com.kspo.base.common.util.security - ARIAProvider.java
 * </pre>
 *
 * @ClassName : ARIAProvider
 * @Description : ARIAProvider
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class AriaProvider {
	private byte[][] plainTextBlock = null;
	private byte[][] cipherTextBlock = null;
	private byte[] masterKey;
	private int keySize = 256;
	private final AriaEncrypt aria;

	private static final char[] _HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	public AriaProvider(int keySize) throws InvalidKeyException {

		this.keySize = keySize;
		this.aria = new AriaEncrypt(this.keySize);
	}

	public void createMasterKey(String key) {

		int byteSize = ((this.keySize == 128) ? 16 : (this.keySize == 192) ? 24 : 32);
		byte[] temp = key.getBytes();
		if (temp.length == byteSize) {
			this.masterKey = temp;
		} else if (temp.length > byteSize) {
			byte[] buffer = new byte[byteSize];
			System.arraycopy(temp, 0, temp, 0, 16);
			this.masterKey = buffer;
		} else {
			byte[] buffer = new byte[byteSize];
			int cnt = 0;
			while (cnt < byteSize) {
				if (cnt + temp.length <= byteSize) {
					System.arraycopy(temp, 0, buffer, cnt, temp.length);
				} else {
					System.arraycopy(temp, 0, buffer, cnt, byteSize - cnt);
				}
				cnt += temp.length;
			}
			this.masterKey = buffer;
		}
	}

	public String encryptToString(String inputString) throws InvalidKeyException {

		if (StringUtils.isEmpty(inputString)) {
			return inputString;
		}
		this.encrypt(inputString);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.cipherTextBlock.length; i++) {
			sb.append(this.printBlock(this.cipherTextBlock[i]));
		}
		return sb.toString().replace(" ", "");
	}

	public String decryptFromString(String inputString) throws InvalidKeyException {

		byte[] dest = new byte[inputString.length() / 2];

		if (StringUtils.isEmpty(inputString)) {
			return inputString;
		}
		this.decrypt(inputString);

		for (int i = 0, j = 0; i < this.plainTextBlock.length; i++, j += 16) {
			System.arraycopy(this.plainTextBlock[i], 0, dest, j, 16);
		}
		return new String(dest).trim();
	}

	private void decrypt(String inputText) throws InvalidKeyException {

		this.aria.setKey(this.masterKey);
		this.aria.setupRoundKeys();

		this.decryptDivisionBlock(inputText);
		this.decrypt();
	}

	private void decryptDivisionBlock(String cipherText) {

		byte[] temp = this.createBlock(this.hexToBin(cipherText));
		int index = (temp.length - 1) / 16 + 1;
		this.cipherTextBlock = new byte[index][];
		for (int i = 0, j = 0; j < index; i += 16, j++) {
			this.cipherTextBlock[j] = new byte[16];
			System.arraycopy(temp, i, this.cipherTextBlock[j], 0, 16);
		}
	}

	private byte[] hexToBin(String str) {

		byte[] returnValue = new byte[str.length() / 2];
		for (int i = 0; i < returnValue.length; i++) {
			String hex = str.substring(i * 2, i * 2 + 2);
			returnValue[i] = (byte) Integer.parseInt(hex, 16);
		}
		return returnValue;
	}

	private void decrypt() throws InvalidKeyException {

		this.plainTextBlock = new byte[this.cipherTextBlock.length][];
		for (int i = 0; i < this.cipherTextBlock.length; i++) {
			this.plainTextBlock[i] = new byte[16];
			this.aria.decrypt(this.cipherTextBlock[i], 0, plainTextBlock[i], 0);
		}
	}

	private void encrypt(String inputText) throws InvalidKeyException {

		this.aria.setKey(this.masterKey);
		this.aria.setupRoundKeys();

		this.encryptDivisionBlock(inputText);
		this.encrypt();
	}

	private void encryptDivisionBlock(String plainText) {

		byte[] temp = this.createBlock(plainText.getBytes());
		int index = (temp.length - 1) / 16 + 1;
		this.plainTextBlock = new byte[index][];
		for (int i = 0, j = 0; j < index; i += 16, j++) {
			this.plainTextBlock[j] = new byte[16];
			System.arraycopy(temp, i, this.plainTextBlock[j], 0, 16);
		}
	}

	private void encrypt() throws InvalidKeyException {

		this.cipherTextBlock = new byte[this.plainTextBlock.length][];
		for (int i = 0; i < this.plainTextBlock.length; i++) {
			this.cipherTextBlock[i] = new byte[16];
			this.aria.encrypt(this.plainTextBlock[i], 0, this.cipherTextBlock[i], 0);
		}
	}

	private byte[] createBlock(byte[] byteArray) {

		if (byteArray.length % 16 == 0)
			return byteArray;
		byte[] temp = new byte[((byteArray.length / 16) + 1) * 16];
		System.arraycopy(byteArray, 0, temp, 0, byteArray.length);
		for (int i = byteArray.length; i < temp.length; i++) {
			temp[i] = 32;
		}
		return temp;
	}

//	private String binToHex(byte[] arrByte) {
//
//		String returnValue = null;
//		for (int i = 0; i < arrByte.length; i++) {
//			returnValue += HEX_DIGITS[(arrByte[i] >> 4)];
//			returnValue += HEX_DIGITS[(arrByte[i] & 0x0f)];
//		}
//		return returnValue;
//	}

	private String printBlock(byte[] p) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 4; i++) {
			byteToHex(sb, p[i]);
		}
		sb.append(" ");
		for (int i = 4; i < 8; i++) {
			byteToHex(sb, p[i]);
		}
		sb.append(" ");
		for (int i = 8; i < 12; i++) {
			byteToHex(sb, p[i]);
		}
		sb.append(" ");
		for (int i = 12; i < 16; i++) {
			byteToHex(sb, p[i]);
		}
		return sb.toString();
	}

	private void byteToHex(StringBuilder sb, byte b) {

		char[] buf = { _HEX_DIGITS[(b >>> 4) & 0x0F], _HEX_DIGITS[b & 0x0F] };
		sb.append(new String(buf));
	}
}
