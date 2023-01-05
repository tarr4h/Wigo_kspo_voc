package com.kspo.voc.program.setting.model;

import com.kspo.base.common.model.AbstractTreeVo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VocMgmtCdVo extends AbstractTreeVo {

	private static final long serialVersionUID = -2550864441873076810L;
	private String mgmtCd;
	private String topMgmtCd;
	private String prntsMgmtCd;
	private String topComnCd;
	private String comnCd;
	private int mgmtCdOrdr;
	private int mgmtCdLvlNo;
	private String mgmtCdNm;
	private String useYn;
	private String regrId;
	private String regDt;
	private String amdrId;
	private String amdDt;

	private String topMgmtCdNm;
	private String prntsMgmtCdNm;

	@Override
	public String getId() {
		return getMgmtCd();
	}

	@Override
	public String getText() {
		return getMgmtCdNm();
	}

	@Override
	public String getParentId() {
		return getPrntsMgmtCd();
	}

	@Override
	public int getLevel() {
		return getMgmtCdLvlNo();
	}
}
