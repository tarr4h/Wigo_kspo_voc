package com.kspo.base.common.util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.kspo.voc.comn.util.Utilities;

/**
 * 
* <pre>
* com.kspo.base.common.util
*	- CryptoDataSource.java
* </pre>
*
* @ClassName	: CryptoDataSource 
* @Description	: 암호화
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class CryptoDataSource extends DriverManagerDataSource {
	@Override
    public synchronized void setUrl(String url) {
        try {
			super.setUrl(Utilities.decrypt(url));
		} catch (Exception e) {
			super.setUrl(url);
		}
    }
  
    @Override
    public void setUsername(String username) {
        try {
			super.setUsername(Utilities.decrypt(username));
		} catch (Exception e) {
			super.setUsername(username);
		}
    }
  
    @Override
    public void setPassword(String password) {
        try {
			super.setPassword(Utilities.decrypt(password));
		} catch (Exception e) {
			super.setPassword(password);
		}
    }
}
