package com.egov.base.common.model;


import com.egov.base.common.util.BaseUtilities;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- GroupVo.java
* </pre>
*
* @ClassName	: GroupVo 
* @Description	: 그룹Vo
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class GroupVo extends AbstractTreeVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6906195905078527941L;
	/**
	 * 
	 */
	private String id;
	private String name;
	private String parentId;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getText() {
		return getName();
	}

	public GroupVo findGroupByName(String group1Name, String group2Name) {
		if (BaseUtilities.isEmpty(group1Name))
			return null;
		if (group1Name.equals(name)) {
			if (BaseUtilities.isEmpty(group2Name))
				return this;
			for (int i = 0; getChildren() != null && i < getChildren().size(); i++) {
				GroupVo vo = ((GroupVo) getChildren().get(i)).findGroupByName(group2Name);
				if (vo != null)
					return vo;
			}
			return null;
		} else {
			for (int i = 0; getChildren() != null && i < getChildren().size(); i++) {
				GroupVo vo = ((GroupVo) getChildren().get(i)).findGroupByName(group1Name, group2Name);
				if (vo != null)
					return vo;
			}
			return null;
		}
	}

	public GroupVo findGroupByName(String groupName) {
		if (BaseUtilities.isEmpty(groupName))
			return null;
		if (groupName.equals(name))
			return this;
		for (int i = 0; getChildren() != null && i < getChildren().size(); i++) {
			GroupVo vo = ((GroupVo) getChildren().get(i)).findGroupByName(groupName);
			if (vo != null)
				return vo;
		}
		return null;
	}


}
