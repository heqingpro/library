package com.ipanel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * @author fangg
 * 2017年5月15日 下午4:27:52
 */
@Entity
@Table(name="appUser_to_entry")
public class AppUserToEntry {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="appUser_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private AppUser appUser;
	
	@ManyToOne
	@JoinColumn(name="entry_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private EntryInfo entryInfo;
	
	@Column(name="recordType")
	private Integer recordType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public EntryInfo getEntryInfo() {
		return entryInfo;
	}

	public void setEntryInfo(EntryInfo entryInfo) {
		this.entryInfo = entryInfo;
	}

	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}
	
	
	

}
