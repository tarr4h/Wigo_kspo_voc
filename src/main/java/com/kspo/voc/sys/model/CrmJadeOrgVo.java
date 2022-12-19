package com.kspo.voc.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrmJadeOrgVo extends CrmOrgBaseVo {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	private String cCd;
	private String cNm;

	public String getOrgClass() {
		return getOrgClassNm();

	}

	public void setOrgClass(String clsId) {
		setOrgClassId(clsId);
	}

	public Integer getLevelNo() {
		return getLvlNo();
	}

	public void setLevelNo(Integer lvlNo) {
		setLvlNo(lvlNo);
	}

	public Integer getDpOrder() {
		return getOrgOdrg();
	}

	public void setDpOrder(Integer dpOrder) {
		setOrgOdrg(dpOrder);
	}
}
