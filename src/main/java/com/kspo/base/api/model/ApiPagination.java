package com.kspo.base.api.model;

import java.util.Map;

import com.kspo.voc.comn.util.Utilities;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "페이징 정보")
@Getter
@Setter
public class ApiPagination extends ApiBasePagination {
	@Parameter(description = "등록자ID", example = "USER00001", hidden = true)
	@Schema(description = "등록자ID", example = "USER00001")
	private String regrId;
	@Parameter(description = "등록일시", example = "20220101121212", hidden = true)
	@Schema(description = "등록일시", example = "20220101121212")
	private String regDt;
	@Parameter(description = "수정자ID", example = "USER00001", hidden = true)
	@Schema(description = "수정자ID", example = "USER00001")
	private String amdrId;
	@Parameter(description = "수정일시", example = "20220101121212", hidden = true)
	@Schema(description = "수정일시", example = "20220101121212")
	private String amdDt;

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
