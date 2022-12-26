package com.kspo.base.api.model;

import java.util.Map;

import com.kspo.voc.comn.util.Utilities;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "API BASE")
public class ApiBaseVo {
//	@Schema(description = "변경구분", required = true, example = "c", hidden = true)
//	private String stat;

	@Schema(description = "등록자ID", required = true, example = "USER00001", hidden = true)
	private String regrId;
	@Schema(description = "등록일시", required = true, example = "20220101121212", hidden = true)
	private String regDt;
	@Schema(description = "수정자ID", required = true, example = "USER00001", hidden = true)
	private String amdrId;
	@Schema(description = "수정일시", required = true, example = "20220101121212", hidden = true)
	private String amdDt;

	/**
	 * 등록채널코드 공통코드 : S000 [CRM : CRM , CTC : 상담 , AS : AS , SAP : SAP , POS : POS]
	 */
	@Schema(description = "등록채널코드", required = true, example = "CRM", hidden = false)
	private String regChlCd;
	@Schema(description = "등록채널코드명", example = "", hidden = false, required = false, nullable = true, accessMode = AccessMode.READ_ONLY)
	private String regChlCdNm;

	@Override
	public String toString() {
		String className = this.getClass().getSimpleName();
		Map<String, Object> map = Utilities.beanToMap(this);
		StringBuffer bf = new StringBuffer();
		bf.append(className);
		bf.append(" [");
		int cnt = 0;
		for (String key : map.keySet()) {
			if (cnt++ > 0)
				bf.append(", ");
			bf.append(key);
			bf.append("=");
			bf.append(map.get(key));
			bf.append("]");
		}
		return bf.toString();
	}
}
