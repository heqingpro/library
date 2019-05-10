package com.ipanel.web.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author fangg
 * 2017年5月31日 下午3:25:32
 */

@Entity
@Table(name = "sys_user_app")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysUserToApp {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private SysUser sysUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private AppInfo appInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}
	
	
	
}
