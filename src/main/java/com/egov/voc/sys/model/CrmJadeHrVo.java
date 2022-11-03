package com.egov.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmJadeHrVo extends CrmEmpBaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;
	
	public String orgNm;
	public String cmpNm;
	public String jobNm;
	public String dutyNm;
	public String empGradeNm;
	public String empTypeNm;
	public String statusNm;
	
	public String getCpCd() {
		return getCmpCd();
	}
	public void setCpCd(String cpCd) {
		setCmpCd(cpCd);
	}
	public String getBithYmd() {
		return getBirthday();
	}
	public void setBirthYmd(String birthday) {
		setBirthday(birthday);
	}
	public String getMailAddr() {
		return getEmailAddr();
	}
	public void setMailAddr(String mailAddr) {
		setEmailAddr(mailAddr);
	}
	public String getMobileNo() {
		return getMphonNo();
	}
	public void setMobileNo(String mobileNo) {
		setMphonNo(mobileNo);
	}
	public String getStatCd() {
		return getStatusCd();
	}
	public void setStatCd(String statCd) {
		setStatusCd(statCd);
	}
	
	public String getEnterYmd() {
		return getEncoYmd();
	}
	public void setEnterYmd(String enterYmd) {
		setEncoYmd(enterYmd);
	}
	public String getEmpType() {
		return getEmpTypeCd();
	}
	public void setEmpType(String empType) {
		setEmpTypeCd(empType);
	}
	public String getCpNm() {
		return getCmpNm();
	}
	public void setCpNm(String cpNm) {
		setCmpNm(cpNm);
	}
	
	public String getStatNm() {
		return getStatusNm();
	}
	
	public void setStatNm(String statNm) {
		setStatusNm(statNm);
	}
	
}
