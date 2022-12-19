package com.kspo.base.common.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- IExcelVo.java
* </pre>
*
* @ClassName	: IExcelVo 
* @Description	: 엑셀 인터페이스
* @author 		: 김성태
* @date 		: 2021. 1. 6.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public interface IExcelVo extends Serializable {
	String getTitle();
	List<Map.Entry<String,String>> getColumns();
	List<EzMap> getData();
}
