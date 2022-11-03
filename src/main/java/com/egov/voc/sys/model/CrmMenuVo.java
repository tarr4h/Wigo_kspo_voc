package com.egov.voc.sys.model;

import com.egov.voc.base.common.model.ITreeVo;
import com.egov.voc.base.common.util.BaseUtilities;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CrmMenuVo extends CrmMenuBaseVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String menuRegAuthYn;
	private String menuReadAuthYn;
	private String menuAmdAuthYn;
	private String menuDelAuthYn;
	private String menuPath;
	private String wdgtYn;
	
	public CrmMenuVo() {

	}

	public CrmMenuVo(Map<String, Object> param) {
		super(param);
	}

	@Override
	public String getIcon() {
		if (!BaseUtilities.isEmpty(getMenuIconCd()))
			return getMenuIconCd();
		if (getLevel() < 3 || getChildrenCount() > 0)
			return "far fa-folder";
		else
			return "far fa-circle";
//		switch (getLevel()) {
//		case 1:
//		case 2:
//		case 3:
//			return "far fa-folder";
//		case 4:
//			return "far fa-circle";
//		case 5:
//			return "far fa-dot-circle";
//		default:
//			return "fas fa-book";
//		}
	}

	public boolean hasLinkedMenu() {
		if (BaseUtilities.isNotEmpty(getMenuUrl()))
			return true;
		List<ITreeVo> list = getChildren();
		for (int i = 0; list != null && i < list.size(); i++) {
			CrmMenuVo ch = (CrmMenuVo) list.get(i);
			if (ch.hasLinkedMenu())
				return true;
		}
		return false;
	}

}
