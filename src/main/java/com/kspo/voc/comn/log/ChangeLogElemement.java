package com.kspo.voc.comn.log;

import java.util.ArrayList;
import java.util.List;

import com.kspo.voc.comn.util.Utilities;

public class ChangeLogElemement {
	protected ChangeLogElemement(String fullName, Object[] params, ChangeLogElemement parent) {
		parameters = params;
		this.parent = parent;
		if (this.parent != null)
			this.parent.addChild(this);
		init(fullName);
	}

	private String className;
	private String methodName;
	private Object parameters;
	private Object results;
	private ChangeLogElemement parent;
	private List<ChangeLogElemement> children = null;

	private void init(String fullName) {
		if (Utilities.isEmpty(fullName))
			return;
		int index = fullName.lastIndexOf(" ");
		String temp = fullName.substring(index + 1);
		index = temp.indexOf("(");
		temp = temp.substring(0, index);
		index = temp.lastIndexOf(".");
		className = temp.substring(0, index);
		methodName = temp.substring(index + 1);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object getParameters() {
		return parameters;
	}

	public void setParameters(Object parameters) {
		this.parameters = parameters;
	}

	public Object getResults() {
		return results;
	}

	public void setResults(Object results) {
		this.results = results;
	}

//	public List<ChangeLogElemement> getStacks() {
//		return children;
//	}
	protected ChangeLogElemement getParent() {
		return parent;
	}

	protected void addChild(ChangeLogElemement child) {
		if (children == null)
			children = new ArrayList<ChangeLogElemement>();
		children.add(child);
	}

	public List<ChangeLogElemement> addStacks(List<ChangeLogElemement> list) {
		if (list == null)
			return list;
		list.add(this);
		for (int i = 0; children != null && i < children.size(); i++) {
			children.get(i).addStacks(list);
		}
		return list;
	}
}
