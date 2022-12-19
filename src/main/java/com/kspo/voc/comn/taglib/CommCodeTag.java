package com.kspo.voc.comn.taglib;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.kspo.base.common.model.EzMap;
import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.ComnCdBaseVo;
import com.kspo.voc.sys.service.ComnCdService;

import java.util.List;

/**
 * 
 * <pre>
 * com.wigo.crm.common.taglib - CommCodeTag.java
 * </pre>
 *
 * @ClassName : CommCodeTag
 * @Description : 공통코드태그
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public class CommCodeTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6129581713388769101L;
	private String name = "";
	private String codeCd;
	private String codeType = "commCode";
	private String change = "";
	private boolean hasSelectTag = true;
	private String others = "";
	private String prefixLabel = "";
	private String prefixValue = "";
	private String selectedValue = "";
	private String className = "select";
	private boolean multiple = false;
	private boolean select2 = false;
	
	private String upCombo = "";
	private String useYn1;
	private String useYn2;
	private String useYn3;
	private String useYn4;
	private String useYn5;
	private String useYn6;
	private String useYn7;
	private String useYn8;
	private String useYn9;
	private String refCd1;
	private String refCd2;
	private String refCd3;
	private String refCd4;
	private String refCd5;
	private String refCd6;
	private String refCd7;
	private String refCd8;
	private String refCd9;
	private ComnCdService codeService = Utilities.getBean(ComnCdService.class);

	public String getCodeCd() {
		return codeCd;
	}

	public void setCodeCd(String codeCd) {
		this.codeCd = codeCd;
	}

	public boolean isHasSelectTag() {
		return hasSelectTag;
	}

	public void setHasSelectTag(boolean hasSelectTag) {
		this.hasSelectTag = hasSelectTag;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getPrefixLabel() {
		return prefixLabel;
	}

	public void setPrefixLabel(String prefixLabel) {
		this.prefixLabel = prefixLabel;
	}

	public String getPrefixValue() {
		return prefixValue;
	}

	public void setPrefixValue(String prefixValue) {
		this.prefixValue = prefixValue;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getUseYn1() {
		return useYn1;
	}

	public void setUseYn1(String useYn1) {
		this.useYn1 = useYn1;
	}

	public String getUseYn2() {
		return useYn2;
	}

	public void setUseYn2(String useYn2) {
		this.useYn2 = useYn2;
	}

	public String getUseYn3() {
		return useYn3;
	}

	public void setUseYn3(String useYn3) {
		this.useYn3 = useYn3;
	}

	public String getUseYn4() {
		return useYn4;
	}

	public void setUseYn4(String useYn4) {
		this.useYn4 = useYn4;
	}

	public String getUseYn5() {
		return useYn5;
	}

	public void setUseYn5(String useYn5) {
		this.useYn5 = useYn5;
	}

	public String getUseYn6() {
		return useYn6;
	}

	public void setUseYn6(String useYn6) {
		this.useYn6 = useYn6;
	}

	public String getUseYn7() {
		return useYn7;
	}

	public void setUseYn7(String useYn7) {
		this.useYn7 = useYn7;
	}

	public String getUseYn8() {
		return useYn8;
	}

	public void setUseYn8(String useYn8) {
		this.useYn8 = useYn8;
	}

	public String getUseYn9() {
		return useYn9;
	}

	public void setUseYn9(String useYn9) {
		this.useYn9 = useYn9;
	}

	public String getRefCd1() {
		return refCd1;
	}

	public void setRefCd1(String refCd1) {
		this.refCd1 = refCd1;
	}

	public String getRefCd2() {
		return refCd2;
	}

	public void setRefCd2(String refCd2) {
		this.refCd2 = refCd2;
	}

	public String getRefCd3() {
		return refCd3;
	}

	public void setRefCd3(String refCd3) {
		this.refCd3 = refCd3;
	}

	public String getRefCd4() {
		return refCd4;
	}

	public void setRefCd4(String refCd4) {
		this.refCd4 = refCd4;
	}

	public String getRefCd5() {
		return refCd5;
	}

	public void setRefCd5(String refCd5) {
		this.refCd5 = refCd5;
	}

	public String getRefCd6() {
		return refCd6;
	}

	public void setRefCd6(String refCd6) {
		this.refCd6 = refCd6;
	}

	public String getRefCd7() {
		return refCd7;
	}

	public void setRefCd7(String refCd7) {
		this.refCd7 = refCd7;
	}

	public String getRefCd8() {
		return refCd8;
	}

	public void setRefCd8(String refCd8) {
		this.refCd8 = refCd8;
	}

	public String getRefCd9() {
		return refCd9;
	}

	public void setRefCd9(String refCd9) {
		this.refCd9 = refCd9;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public int doStartTag() throws JspException {
		String options = "";

		EzMap param = new EzMap();
		param.put("codeCd", getCodeCd());
		param.put("codeType", getCodeType());
		param.put("useYn1", getUseYn1());
		param.put("useYn2", getUseYn2());
		param.put("useYn3", getUseYn3());
		param.put("useYn4", getUseYn4());
		param.put("useYn5", getUseYn5());
		param.put("useYn6", getUseYn6());
		param.put("useYn7", getUseYn7());
		param.put("useYn8", getUseYn8());
		param.put("useYn9", getUseYn9());

		param.put("refCd1", getRefCd1());
		param.put("refCd2", getRefCd2());
		param.put("refCd3", getRefCd3());
		param.put("refCd4", getRefCd4());
		param.put("refCd5", getRefCd5());
		param.put("refCd6", getRefCd6());
		param.put("refCd7", getRefCd7());
		param.put("refCd8", getRefCd8());
		param.put("refCd9", getRefCd9());

		try {
			List<ComnCdBaseVo> list = codeService.getComboCode(param);
			if (Utilities.isNotEmpty(prefixValue) || Utilities.isNotEmpty(prefixLabel)) {
				prefixValue = Utilities.nullCheck(prefixValue);
				prefixLabel = Utilities.nullCheck(prefixLabel);
				if (Utilities.isEmpty(prefixLabel))
					prefixLabel = prefixValue;
				options += "<option value=\"" + prefixValue + "\">" + prefixLabel + "</options>";
			}
			for (int i = 0; i < list.size(); i++) {
				ComnCdBaseVo code = list.get(i);
				String selected = "";
				if (code.getComnCd().equals(selectedValue)) {
					selected = " selected ";
				}
				String data = "";
				EzMap map =new EzMap( Utilities.beanToMap(code),false);
				map.put("codeCd", code.getComnCd());
				map.put("codeNm", code.getComnCdNm());
				for (String key : map.keySet()) {
					String value = (String) map.getString(key);
					String dataKey = Utilities.convert2CamelCaseToData(key);
					data += dataKey + "=\"" + value + "\" ";
				}

				options += "<option " + selected + " value=\"" + code.getComnCd() + "\" " + data + ">"
						+ code.getComnCdNm()+ "</option>";
			}
			StringBuffer sel = new StringBuffer();
			if (hasSelectTag) {

				sel.append("<select ");
				if (isMultiple())
					sel.append("multiple ");
				else if (isSelect2())
					sel.append("data-type=\"select2\" ");
				if (Utilities.isNotEmpty(id))
					sel.append("id=\"" + id + "\" ");
				if (Utilities.isNotEmpty(name))
					sel.append("name=\"" + name + "\" ");
				if (Utilities.isNotEmpty(className))
					sel.append("class=\"" + className + "\" ");
				if (Utilities.isNotEmpty(change))
					sel.append("data-change=\"" + change + "\" ");
				if (Utilities.isNotEmpty(upCombo))
					sel.append("data-up-combo=\"" + upCombo + "\" ");
				if (Utilities.isNotEmpty(prefixLabel))
					sel.append("data-label=\"" + prefixLabel + "\" ");
				if (Utilities.isNotEmpty(prefixValue))
					sel.append("data-label-value=\"" + prefixValue + "\" ");

				if (Utilities.isNotEmpty(others))
					sel.append(others);

				sel.append(">");
			}
			sel.append(options);
			if (hasSelectTag)
				sel.append("</select>");
			pageContext.getOut().print(sel.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	/**
	 * @return the codeType
	 */
	public String getCodeType() {
		if (Utilities.isEmpty(codeType))
			return "commCode";
		return codeType;
	}

	/**
	 * @param codeType the codeType to set
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * @return the change
	 */
	public String getChange() {
		return change;
	}

	/**
	 * @param change the change to set
	 */
	public void setChange(String change) {
		this.change = change;
	}

	public String getUpCombo() {
		return upCombo;
	}

	public void setUpCombo(String upCombo) {
		this.upCombo = upCombo;
	}

	public boolean isSelect2() {
		return select2;
	}

	public void setSelect2(boolean select2) {
		this.select2 = select2;
	}

}
