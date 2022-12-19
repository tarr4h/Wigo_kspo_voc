package com.kspo.base.common.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.kspo.voc.comn.util.Utilities;

import lombok.Data;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- BaseBo.java
* </pre>
*
* @ClassName	: BaseBo 
* @Description	: 기본Vo
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */
@Data
public class BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4361056844282850052L;
	private String stat;
	private String peerIp;
	private String regrId;
	private String regDt;
	private String amdrId;
	private String amdDt;
	private static final SimpleDateFormat _DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat _DATE_FORMAT2 = new SimpleDateFormat("yyyyMMdd");
	public BaseVo() {
	}

	public BaseVo(Map<String,Object> param) {
		Utilities.mapToBean(param,this);
	}
	public String getRegDtNm() {
		if(Utilities.isNotEmpty(regDt)) {
			if(regDt.length()==14) {
				return _DATE_FORMAT.format(Utilities.parseDate(regDt));
			}
			else if(regDt.length()==8) {
				return _DATE_FORMAT.format(Utilities.parseDate(regDt, _DATE_FORMAT2));
			}
		}
		return regDt;
		
		
	}
	public String getAmdDtNm() {
		if(Utilities.isNotEmpty(amdDt)) {
			if(amdDt.length()==14) {
				return _DATE_FORMAT.format(Utilities.parseDate(amdDt));
			}
			else if(amdDt.length()==8) {
				return _DATE_FORMAT.format(Utilities.parseDate(amdDt, _DATE_FORMAT2));
			}
		}
		return amdDt;
		
		
	}
//	
//	public String getInsDt() {
//		return insDt;
//	}
//	public void setInsDt(String insDt) {
//		this.insDt = insDt;
//	}
//	public String getInsId() {
//		return insId;
//	}
//	public void setInsId(String insId) {
//		this.insId = insId;
//	}
//	public String getUpdDt() {
//		return updDt;
//	}
//	public void setUpdDt(String updDt) {
//		this.updDt = updDt;
//	}
//	public String getUpdId() {
//		return updId;
//	}
//	public void setUpdId(String updId) {
//		this.updId = updId;
//	}
//	public String getPeerIp() {
//		return peerIp;
//	}
//	public void setPeerIp(String peerIp) {
//		this.peerIp = peerIp;
//	}
	
}
