package com.ipanel.web.sysUser.pageModel;

public class PermissionModel {
	private static final long serialVersionUID = -6828681701689912307L;
	private Integer pid;
	private Integer id;
	private String text;

	public PermissionModel(Integer pid, Integer id, String text) {
		this.pid = pid;
		this.id = id;
		this.text = text;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
