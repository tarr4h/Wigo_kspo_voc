package com.kspo.voc.comn.taglib;



import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.kspo.voc.comn.util.Utilities;
import com.kspo.voc.sys.model.MenuVo;

import java.io.IOException;

/**
 * 
* <pre>
* com.kspo.base.common.taglib
*	- GrdBtnTag.java
* </pre>
*
* @ClassName	: GrdBtnTag 
* @Description	: GrdBtnTag 
* @author 		: MKH
* @date 		: 2021. 1. 18.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class BtnGroupTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810949506019349503L;

	
	private String  name;
	private boolean forceInsert = false;
	private boolean hideInsert = false;
	private boolean forceSave = false;
	private boolean hideSave = false;
	private boolean forceDelete = false;
	private boolean hideDelete = false;
	private boolean hasCancel = false;
	private boolean hasSort = false;
	private String gridId = "";
	private boolean hideExcel = false;
	private String dispName = "";
	
	private String insertName ="추가";
	private String saveName ="저장";
	private String deleteName ="삭제";
	private String excelName ="엑셀";
	private String cancelName ="취소";
	private String seqName ="순서변경";
	
	@Override
    public int doStartTag() throws JspException {
    	
		ServletRequest req = pageContext.getRequest();
		MenuVo menu = (MenuVo)req.getAttribute("currentMenu");
		if(menu != null) {
			StringBuffer html = new StringBuffer();
			String gridIdHtml = "";
			if(Utilities.isNotEmpty(gridId)) {
				gridIdHtml = " data-grid-id=\""+gridId+"\"  data-disp-name=\""+dispName+"\"";
			}
			if(hasSort&&(forceSave || "Y".equals(menu.getMenuAmdAuthYn())))
				html.append(" <a href=\"#\"  ").append(gridIdHtml).append(" data-click=\"sort").append(name).append("\" id=\"btnSort").append(name).append("\" class=\"mBtn1 m lWhite\">").append(seqName).append("</a>");
			if(hasCancel)
				html.append(" <a href=\"#\" ").append(gridIdHtml).append(" data-click=\"cancel").append(name).append("\" id=\"btnCancel").append(name).append("\" class=\"mBtn1 m lWhite\">").append(cancelName).append("</a>");
			if(!hideExcel && Utilities.isNotEmpty(gridId))  
				html.append(" <a href=\"#\" ").append(gridIdHtml).append(" data-name=\"").append(name).append("\" data-click=\"excelDownload\" id=\"btnExcel").append(name).append("\" class=\"mBtn1 m lWhite\">").append(excelName).append("</a>");
			if(!hideDelete&&( forceDelete || "Y".equals(menu.getMenuDelAuthYn())))
				html.append(" <a href=\"#\" ").append(gridIdHtml).append(" data-click=\"remove").append(name).append("\" id=\"btnRemove").append(name).append("\" class=\"mBtn1 m\">").append(deleteName).append("</a>");
			if(!hideInsert&&(forceInsert || "Y".equals(menu.getMenuRegAuthYn())))
				html.append(" <a href=\"#\" ").append(gridIdHtml).append(" data-click=\"new").append(name).append("\" id=\"btnNew").append(name).append("\" class=\"mBtn1 m lWhite\">").append(insertName).append("</a>");
			if(!hideSave&&(forceSave || "Y".equals(menu.getMenuAmdAuthYn())))
				html.append(" <a href=\"#\" ").append(gridIdHtml).append(" data-click=\"save").append(name).append("\" id=\"btnSave").append(name).append("\" class=\"mBtn1 m lWhite\">").append(saveName).append("</a>");
			try {
				pageContext.getOut().print(html.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    
    	return SKIP_BODY;
    }

	/**
	 * @return the forceInsert
	 */
	public boolean isForceInsert() {
		return forceInsert;
	}

	/**
	 * @param forceInsert the forceInsert to set
	 */
	public void setForceInsert(boolean forceInsert) {
		this.forceInsert = forceInsert;
	}

	/**
	 * @return the forceSave
	 */
	public boolean isForceSave() {
		return forceSave;
	}

	/**
	 * @param forceSave the forceSave to set
	 */
	public void setForceSave(boolean forceSave) {
		this.forceSave = forceSave;
	}

	/**
	 * @return the forceDelete
	 */
	public boolean isForceDelete() {
		return forceDelete;
	}

	/**
	 * @param forceDelete the forceDelete to set
	 */
	public void setForceDelete(boolean forceDelete) {
		this.forceDelete = forceDelete;
	}

	/**
	 * @return the hasCancel
	 */
	public boolean isHasCancel() {
		return hasCancel;
	}

	/**
	 * @param hasCancel the hasCancel to set
	 */
	public void setHasCancel(boolean hasCancel) {
		this.hasCancel = hasCancel;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the hideInsert
	 */
	public boolean isHideInsert() {
		return hideInsert;
	}

	/**
	 * @param hideInsert the hideInsert to set
	 */
	public void setHideInsert(boolean hideInsert) {
		this.hideInsert = hideInsert;
	}

	/**
	 * @return the hideSave
	 */
	public boolean isHideSave() {
		return hideSave;
	}

	/**
	 * @param hideSave the hideSave to set
	 */
	public void setHideSave(boolean hideSave) {
		this.hideSave = hideSave;
	}

	/**
	 * @return the hideDelete
	 */
	public boolean isHideDelete() {
		return hideDelete;
	}

	/**
	 * @param hideDelete the hideDelete to set
	 */
	public void setHideDelete(boolean hideDelete) {
		this.hideDelete = hideDelete;
	}

	/**
	 * @return the gridId
	 */
	public String getGridId() {
		return gridId;
	}

	/**
	 * @param gridId the gridId to set
	 */
	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	/**
	 * @return the hideExcel
	 */
	public boolean isHideExcel() {
		return hideExcel;
	}

	/**
	 * @param hideExcel the hideExcel to set
	 */
	public void setHideExcel(boolean hideExcel) {
		this.hideExcel = hideExcel;
	}

	public boolean isHasSort() {
		return hasSort;
	}

	public void setHasSort(boolean hasSort) {
		this.hasSort = hasSort;
	}

	/**
	 * @return the dispName
	 */
	public String getDispName() {
		return dispName;
	}

	/**
	 * @param dispName the dispName to set
	 */
	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	public String getInsertName() {
		return insertName;
	}

	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getDeleteName() {
		return deleteName;
	}

	public void setDeleteName(String deleteName) {
		this.deleteName = deleteName;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public String getCancelName() {
		return cancelName;
	}

	public void setCancelName(String cancelName) {
		this.cancelName = cancelName;
	}

	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}
	
	
}
