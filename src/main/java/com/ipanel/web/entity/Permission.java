package com.ipanel.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "permission")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Permission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="url")
	private String url;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Permission parentModel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Permission getParentModel() {
		return parentModel;
	}

	public void setParentModel(Permission parentModel) {
		this.parentModel = parentModel;
	}
}

