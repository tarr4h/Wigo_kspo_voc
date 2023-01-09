package com.kspo.base.common.model;



import java.util.ArrayList;
import java.util.List;

import com.kspo.voc.comn.util.Utilities;

/**
 * 
* <pre>
* com.kspo.base.common.model
*	- EzTreeMap.java
* </pre>
*
* @ClassName	: EzTreeMap 
* @Description	: TreeMap
* @author 		: 김성태
* @date 		: 2021. 1. 5.
* @Version 		: 1.0 
* @Company 		: Copyright ⓒ wigo.ai. All Right Reserved
 */

public class EzTreeMap extends EzMap implements ITreeVo {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 6732971600118045079L;
	public static final String _FOLDER_ICON = "far fa-folder-open";
	public static final String _LEAF_ICON = "far fa-folder-open";
//	public static final String _LEAF_ICON = "far fa-sticky-note";
	
	
	/**
	* 
	*/
//	private String leafIcon;
//	private String folderIcon;
//	private String color;
//	private String backColor;
//	private String href;
//	private boolean selectable = true;
//	private State state = new State();
//	private List<String> tags;

	public EzTreeMap() {
//		setLeafIcon("fas fa-book");
//		setFolderIcon("far fa-folder");
		setIcon(_LEAF_ICON);
//		setIcon("far fa-folder-open");
//		<i class="far fa-folder-open"></i>
//		setIcon("far fa-sticky-note");
//		<i class="far fa-sticky-note"></i>
//		put("selectedItem", "leafIcon");
//		setState(new State());
//		put("tags", new ArrayList<String>());
	}
	private ITreeVo parent = null;
//	public class State implements Serializable {
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 2972416295388254753L;
//		private boolean checked = false;
//		private boolean disabled = false;
//		private boolean expanded = false;
//		private boolean selected = false;
//
//		public boolean isChecked() {
//			return checked;
//		}
//
//		public void setChecked(boolean checked) {
//			this.checked = checked;
//		}
//
//		public boolean isDisabled() {
//			return disabled;
//		}
//
//		public void setDisabled(boolean disabled) {
//			this.disabled = disabled;
//		}
//
//		public boolean isExpanded() {
//			return expanded;
//		}
//
//		public void setExpanded(boolean expanded) {
//			this.expanded = expanded;
//		}
//
//		public boolean isSelected() {
//			return selected;
//		}
//
//		public void setSelected(boolean selected) {
//			this.selected = selected;
//		}
//	}
//	
	
	@Override
	public Object put(String key, Object value) {
		Object ret =  super.put(key, value);
		key = Utilities.convert2CamelCase(key);
//		if("lvl".equals(key))
//			setInt("level",value);
//		if("icon".equals(key)) 
//		{
//			setString("selectedIcon",value);
//			setString("expandIcon",value);
//		}
		
		

		return ret;
		
	}
	

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ITreeVo> getNodes() {
//		
//		return (List<ITreeVo>) get("nodes");
//	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ITreeVo> getChildren() {
		
		return (List<ITreeVo>) get("children");
	}
	@Override
	public void addChild(ITreeVo vo) {
		if (vo == null)
			return;
		List<ITreeVo> list = getChildren();
		if (list == null)
			list = createNodes();
		if (list == null)
			return;
		list.add(vo);
		vo.setParent(this);
//		setIcon(_FOLDER_ICON);
//		if(Utilities.isEmpty(getIcon()))
//			put("selectedItem", "fas fa-book");
		
	}

	@Override
	public int getChildrenCount() {
		List<ITreeVo> list = getChildren();
		if(Utilities.isEmpty(list))
			return 0;
		return list.size();
//		int ret = 0;
//		for (int i = 0; list != null && i < list.size(); i++) {
//			List<ITreeVo> l = list.get(i).getChildren();
//			if (l == null || l.size() == 0)
//				ret++;
//			else
//				ret += list.get(i).getChildrenCount();
//		}
//		return ret;
	}
	
	public List<ITreeVo> createNodes() {
		setNodes(new ArrayList<ITreeVo>());
		return getChildren();
	}

	public void setNodes(List<ITreeVo> nodes) {
//		put("nodes",nodes);
		put("children",nodes);
//		if(Utilities.isNotEmpty(nodes))
//			setIcon(_FOLDER_ICON);
//		else
//			setIcon(_LEAF_ICON);
	}
	
	@Override
	public String getId() {
		return getString("id");
	}

	@Override
	public String getText() {
		return getString("text");
	}

	@Override
	public String getParentId() {
		return getString("parentId");
	}

	@Override
	public ITreeVo parent() {
		return (ITreeVo) parent;
	}

	@Override
	public void setParent(ITreeVo vo) {
		parent = vo;
		
	}
	@Override
	public boolean isNode(String id) {
		if(this.getId().equals(id))
			return true;
		if(parent()!=null)
			return parent().isNode(id);
		else
			return false;
	}
	@Override
	public int getLevel() {
		return getInt("lvl");
	}

	public String getLeafIcon()
	{
		return getString("leafIcon");
	}
	public void setLeafIcon( String icon)
	{
		put("leafIcon",icon);
	}
	
	public String getFolderIcon()
	{
		return getString("folderIcon");
	}
	public void setFolderIcon( String icon)
	{
		put("folderIcon",icon);
	}
	public String getIcon() {
		String icon = getString("icon");
		if(Utilities.isNotEmpty(icon))
			return icon;
		List<ITreeVo> list = getChildren();
		if (Utilities.isEmpty(list))
			return getLeafIcon();
		else
			return getFolderIcon();
	}
	public void setIcon(String icon)
	{
		put("icon",icon);
	}
	public String getSelectedIcon() {
		return getIcon();
	}

	public String getExpandIcon() {
		return getIcon();
	}

	public String getColor() {
		return getString("color");
	}

	public void setColor(String color) {
		put("color",color);
	}

	public String getBackColor() {
		return getString("backColor");
	}

	public void setBackColor(String backColor) {
		put("backColor",backColor);
	}

	public String getHref() {
		return getString("href");
	}

	public void setHref(String href) {
		put("href",href);
	}

//	public State getState() {
//		return (State) get("state");
//		return null;
//	}

//	public void setState(State state) {
//		put("state",state);
//	}

	@SuppressWarnings("unchecked")
	public List<String> getTags() {
		int cnt = getChildrenCount();
		if (cnt < 1)
			return null;
		List<String> tags = (List<String>) get("tags");

		if (tags == null)
		{
			tags = new ArrayList<String>();
			put("tags",tags);
		}
		tags.clear();
		tags.add(cnt + "");
		return tags;
	}

	public boolean isSelectable() {
		return getBoolean("selectable");
	}

	public void setSelectable(boolean selectable) {
		put("selectable",selectable);
	}
	
}