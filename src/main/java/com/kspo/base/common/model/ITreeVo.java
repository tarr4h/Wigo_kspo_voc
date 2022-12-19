package com.kspo.base.common.model;

import java.util.List;

/**
 * 
* <pre>
* com.wigo.crm.common.model
*	- ITreeVo.java
* </pre>
*
* @ClassName	: ITreeVo 
* @Description	: Tree인터페이스
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public interface ITreeVo {
	String getId();
	String getText();
	String getParentId();
	ITreeVo parent();
	void setParent(ITreeVo vo);
	int getLevel();
//	List<ITreeVo> getNodes();
	List<ITreeVo> getChildren();
	void addChild(ITreeVo vo);
	int getChildrenCount();
	boolean isNode(String id);
}
