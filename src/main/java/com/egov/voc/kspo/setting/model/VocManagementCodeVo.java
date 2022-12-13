package com.egov.voc.kspo.setting.model;

import com.egov.base.common.model.AbstractTreeVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocManagementCodeVo extends AbstractTreeVo {

	private static final long serialVersionUID = -2550864441873076810L;
	private String managementCd;
	private String topCd;
	private String prntsCd;
	private String topComnCd;
	private String comnCd;
	private int odrg;
	private int lvl;
	private String codeNm;
	private String useYn;
	private String regUsr;
	private String regDt;

	private String topCdNm;
	private String prntsCdNm;

	@Override
	public String getId() {
		return getManagementCd();
	}

	@Override
	public String getText() {
		return getCodeNm();
	}

	@Override
	public String getParentId() {
		return getPrntsCd();
	}

	@Override
	public int getLevel() {
		return getLvl();
	}
}
