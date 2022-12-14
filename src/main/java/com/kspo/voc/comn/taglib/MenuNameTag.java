package com.kspo.voc.comn.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.service.MenuService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <pre>
 * com.kspo.base.common.taglib - MenuNameTag.java
 * </pre>
 *
 * @ClassName : MenuNameTag
 * @Description : 메뉴이름태그
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */
@Slf4j
public class MenuNameTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6129581713388769101L;
	private String menuId;

	private final MenuService service = Utilities.getBean("menuService");

	@Override
	public int doStartTag() throws JspException {

		EzMap param = new EzMap();
		param.put("menuId", getMenuId());
		try {
			EzMap menu = service.get(param);
			if (menu != null)
				pageContext.getOut().print(menu.getString("menuNm"));
		} catch (EgovBizException | IOException e) {
			log.warn(e.getMessage());
		}
		return SKIP_BODY;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
