package com.ipanel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="remote_server")
public class RemoteServer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//主键id
	
	@Column(name="remote_server_name")
	private String remoteServerName;//服务器名称
	
	@Column(name="remote_ip")
	private String remoteIP;//服务器IP
	
	@Column(name="remote_port")
	private Integer remotePort;//服务器端口
	
	@Column(name="user_name")
	private String userName;//登录名
	
	@Column(name="user_pass")
	private String userPass;//登录密码

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
