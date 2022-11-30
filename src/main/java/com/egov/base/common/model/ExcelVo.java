package com.egov.base.common.model;

import lombok.Data;

import java.util.List;
import java.util.Map.Entry;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- ExcelVo.java
* </pre>
*
* @ClassName	: ExcelVo 
* @Description	: 엑셀Vo
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */
@Data
public class ExcelVo implements IExcelVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 632137207670115220L;
	
	private String title;
	private List<Entry<String, String>> columns;
	private List<EzMap> data;
//	
//	@Override
//	public String getTitle() {
//		return title;
//	}
//
//	@Override
//	public List<Entry<String, String>> getColumns() {
//		return columns;
//	}
//
//	@Override
//	public List<EzMap> getData() {
//		return data;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public void setColumns(List<Entry<String, String>> columns) {
//		this.columns = columns;
//	}
//
//	public void setData(List<EzMap> data) {
//		this.data = data;
//	}



	

}
