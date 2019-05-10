package com.ipanel.web.common.model;

import java.util.List;

public class TreeGridModel {
	public static final String TREEGRID_STATE_CLOSED_VALUE="closed";
	public static final String TREEGRID_CHECKED_VALUE="checked";
	
	protected String id;
	protected String text;
	protected String iconCls;
	protected String state;
	protected String parentId;
	protected List<? extends TreeGridModel> children;
	protected String checked;
	private String channelNo;
	
	public TreeGridModel() {
		
	}

	public TreeGridModel(String id, String text, String channelNo,String checked) {
		this.id = id;
		this.text = text;
		this.checked = checked;
		this.channelNo = channelNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<? extends TreeGridModel> getChildren() {
		return children;
	}

	public void setChildren(List<? extends TreeGridModel> children) {
		this.children = children;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public static String getTreegridStateClosedValue() {
		return TREEGRID_STATE_CLOSED_VALUE;
	}

	public static String getTreegridCheckedValue() {
		return TREEGRID_CHECKED_VALUE;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	
	
	
	
}
