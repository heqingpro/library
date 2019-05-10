package com.ipanel.web.remoteServer.pageModel;


public class RemoteServerModel {
	
	private int page;
	
	private int rows;
	
	private Integer id;
	private String remoteServerName;
	private String remoteIP;
	private Integer remotePort;
	private String userName;
	private String userPass;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRemoteServerName() {
		return remoteServerName;
	}
	public void setRemoteServerName(String remoteServerName) {
		this.remoteServerName = remoteServerName;
	}
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	public Integer getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(Integer remotePort) {
		this.remotePort = remotePort;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
	
}
