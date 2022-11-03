package com.egov.voc.base.common.model;



import com.egov.voc.base.common.util.BaseUtilities;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- EzMap.java
* </pre>
*
* @ClassName	: EzMap 
* @Description	: Map
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class EzMap extends LinkedHashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1235619947606311249L;
	private EzPaginationInfo page;
	private boolean camel = true;
	public EzMap() {
		
		super();
	}
	public EzMap(boolean camel) {
		super();
		setCamel(camel);
	}
	public EzMap(Map<String, Object> param) {
		super();
		if(param != null)
			this.putAll(param);
	}
	public EzMap(Map<String, Object> param,boolean camel) {
		super();
		if(param != null)
			this.putAll(param);
		setCamel(camel);
	}
	public EzMap(Object object) {
		this(BaseUtilities.beanToMap(object));
	}
	
	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		super.putAll(m);
	}
	@Override
	public Object put(String key, Object value) {
		if(camel)
			key = BaseUtilities.convert2CamelCase(key);
		return super.put(key, value);
	}

	public String getString(String key) {
		return BaseUtilities.nullCheck(get(key));
	}

	public int getInt(String key) {
		return BaseUtilities.parseInt(get(key));
	}

	public long getLong(String key) {
		return BaseUtilities.parseLong(get(key));
	}

	public boolean getBoolean(String key) {
		return BaseUtilities.parseBoolean(get(key));
	}

	public float getFloat(String key) {
		return BaseUtilities.parseFloat(get(key));
	}

	public double getDouble(String key) {
		return BaseUtilities.parseDouble(get(key));
	}

	public void setString(String key, Object value) {
		put(key, BaseUtilities.nullCheck(value));
	}

	public void setInt(String key, Object value) {
		put(key, BaseUtilities.parseInt(value));
	}

	public void setLong(String key, Object value) {
		put(key, BaseUtilities.parseLong(value));
	}

	public void setBoolean(String key, Object value) {
		put(key, BaseUtilities.parseBoolean(value));
	}

	public void setFloat(String key, Object value) {
		put(key, BaseUtilities.parseFloat(value));
	}

	public void setDouble(String key, Object value) {
		put(key, BaseUtilities.parseDouble(value));
	}

	public EzPaginationInfo getPaginationInfo() {
		if (page == null) {
			
//			int currentPageNo = getInt("currentPageNo");
//			int totalRecordCount = getInt("totalRecordCount");
//			int pageSize = getInt("pageSize");
//			int recordCountPerPage = getInt("recordCountPerPage");
			
			int currentPageNo = getInt("page");
			if(currentPageNo ==0)
				currentPageNo = getInt("currentPageNo");
			int totalRecordCount = getInt("totalCount");
			if(totalRecordCount ==0)
				totalRecordCount = getInt("totalRecordCount");
			int pageSize = getInt("pageSize");
			int recordCountPerPage = getInt("perPage");
			if(recordCountPerPage==0)
				recordCountPerPage = getInt("recordCountPerPage");
			
			if (currentPageNo < 1)
				currentPageNo = 1;
			if (pageSize < 1)
				pageSize = BaseUtilities.getPropertyInt("pageSize");
			if (recordCountPerPage < 1)
				recordCountPerPage = BaseUtilities.getPropertyInt("recordCountPerPage");
			if (totalRecordCount < 0)
				totalRecordCount = 0;
			
			page = new EzPaginationInfo();
			page.setCurrentPageNo(currentPageNo);
			page.setTotalRecordCount(totalRecordCount);
			page.setPageSize(pageSize);
			page.setRecordCountPerPage(recordCountPerPage);
			setPaginationInfo(page);
		}
		return page;
	}

	public void setPaginationInfo(EzPaginationInfo page) {
		this.page = page;
		putAll(BaseUtilities.beanToMap(page));
	}
	/**
	 * @return the ignoreCamel
	 */
	public boolean isCamel() {
		return camel;
	}
	/**
	 * @param ignoreCamel the ignoreCamel to set
	 */
	public void setCamel(boolean camel) {
		this.camel = camel;
	}

}
