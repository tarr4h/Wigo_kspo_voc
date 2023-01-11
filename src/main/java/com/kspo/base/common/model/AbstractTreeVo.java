package com.kspo.base.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kspo.voc.comn.util.Utilities;

/**
 * 
 * <pre>
 * com.kspo.base.common.model - AbstractTreeVo.java
 * </pre>
 *
 * @ClassName : AbstractTreeVo
 * @Description : Tree 설정
 * @author : 김성태
 * @date : 2021. 1. 5.
 * @Version : 1.0
 * @Company : Copyright ⓒ wigo.ai. All Right Reserved
 */

public abstract class AbstractTreeVo extends BaseVo implements ITreeVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4368020448236656701L;
	/**
	* 
	*/
	private List<ITreeVo> nodes = null;
	private String leafIcon;
	private String folderIcon;
	private String color;
	private String backColor;
	private String href;
	private boolean selectable = true;
//	private State state = new State();
	private List<String> tags;
	private ITreeVo parent;

	public AbstractTreeVo() {
		setLeafIcon("fas fa-file");
		setFolderIcon("far fa-folder");
	}

	public AbstractTreeVo(Map<String, Object> param) {
		super(param);
		setLeafIcon("fas fa-file");
		setFolderIcon("far fa-folder");
	}

	public class State implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2972416295388254753L;
//		private boolean checked = false;
		private boolean disabled = false;
//		private boolean expanded = false;
		private boolean opened = false;
		private boolean selected = false;

//		public boolean isChecked() {
//			return checked;
//		}
//
//		public void setChecked(boolean checked) {
//			this.checked = checked;
//		}

		public boolean isDisabled() {
			return disabled;
		}

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

//		public boolean isExpanded() {
//			return expanded;
//		}
//
//		public void setExpanded(boolean expanded) {
//			this.expanded = expanded;
//		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public boolean isOpened() {
			return opened;
		}

		public void setOpened(boolean opened) {
			this.opened = opened;
		}
	}

	public static List<ITreeVo> makeHierarchy(List<? extends ITreeVo> list, EzMap mp) {
		List<ITreeVo> ret = new ArrayList<ITreeVo>();

		if (Utilities.isEmpty(list))
			return ret;
		EzMap itemMap = mp;
		if (itemMap == null)
			itemMap = new EzMap(false);
		for (int i = 0; i < list.size(); i++) {
			ITreeVo vo = list.get(i);
			if (Utilities.isEmpty(vo.getId()))
				continue;
			itemMap.put(vo.getId(), vo);
		}
		for (int i = 0; i < list.size(); i++) {

			ITreeVo vo = list.get(i);

			int level = vo.getLevel();
			if (level == 1) {
				ret.add(vo);
				continue;
			}
			if (!itemMap.containsKey(vo.getParentId())) {
				ret.add(vo);
				continue;
			}
			ITreeVo parent = (ITreeVo) itemMap.get(vo.getParentId());
			if (parent == null)
				continue;
			parent.addChild(vo);
		}
		return ret;
	}

	public static List<ITreeVo> makeHierarchy(List<? extends ITreeVo> list) {

		EzMap itemMap = new EzMap(false);
		return makeHierarchy(list, itemMap);

	}

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
	}

	public List<ITreeVo> createNodes() {
		setNodes(new ArrayList<ITreeVo>());
		return nodes;
	}

	@Override
	public List<ITreeVo> getChildren() {
		return nodes;
	}

	@Override
	public boolean isNode(String id) {
		if (this.getId().equals(id))
			return true;
		if (parent != null)
			return parent.isNode(id);
		else
			return false;
	}

	public void setNodes(List<ITreeVo> nodes) {
		this.nodes = nodes;
	}

	public String getIcon() {
		List<ITreeVo> list = getChildren();
		if (Utilities.isEmpty(list))
			return getLeafIcon();
		else
			return getFolderIcon();
	}

	public String getSelectedIcon() {
		return getIcon();
	}

	public String getExpandIcon() {
		return getIcon();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

//	public State getState() {
//		return state;
//	}
//
//	public void setState(State state) {
//		this.state = state;
//	}

	public List<String> getTags() {
		int cnt = getChildrenCount();
		if (cnt < 1)
			return null;
		if (tags == null)
			tags = new ArrayList<String>();
		tags.clear();
		tags.add(cnt + "");
		return tags;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public int getChildrenCount() {
		List<ITreeVo> list = getChildren();
		int ret = 0;
		for (int i = 0; list != null && i < list.size(); i++) {
			List<ITreeVo> l = list.get(i).getChildren();
			if (l == null || l.size() == 0)
				ret++;
			else
				ret += list.get(i).getChildrenCount();
		}
		return ret;
	}

	public String getLeafIcon() {
		return leafIcon;
	}

	public void setLeafIcon(String leafIcon) {
		this.leafIcon = leafIcon;
	}

	public String getFolderIcon() {
		return folderIcon;
	}

	public void setFolderIcon(String folderIcon) {
		this.folderIcon = folderIcon;
	}

	public ITreeVo parent() {
		return parent;
	}

	public void setParent(ITreeVo parent) {
		this.parent = parent;
	}

	public int getLevel() {

		ITreeVo p = parent();

		int level = 1;
		while (p != null) {
			level++;
			p = p.parent();
		}
		return level;
	}
//	@Override
//	public List<ITreeVo> getChildren() {
//		return getChildren();
//	}

}
