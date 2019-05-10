package com.ipanel.web.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * @author fangg
 * 2017年5月15日 下午4:26:32
 */
@Entity
@Table(name="app_user")
public class AppUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="user_uid")	
	private String userUId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="ca_id")
	private String caId;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appUser")
	private List<AppUserToEntry> appUserToEntry; 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserUId() {
		return userUId;
	}

	public void setUserUId(String userUId) {
		this.userUId = userUId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCaId() {
		return caId;
	}

	public void setCaId(String caId) {
		this.caId = caId;
	}

	public List<AppUserToEntry> getAppUserToEntry() {
		return appUserToEntry;
	}

	public void setAppUserToEntry(List<AppUserToEntry> appUserToEntry) {
		this.appUserToEntry = appUserToEntry;
	}
	
	
	
}
