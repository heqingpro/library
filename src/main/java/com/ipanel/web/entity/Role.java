package com.ipanel.web.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "role")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "remark")
	private String remark;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserToRole> userRoleSet = new HashSet<UserToRole>(0);

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<RoleToPermission> roleToPermissionSet = new HashSet<RoleToPermission>(
			0);

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<UserToRole> getUserRoleSet() {
		return userRoleSet;
	}

	public void setUserRoleSet(Set<UserToRole> userRoleSet) {
		this.userRoleSet = userRoleSet;
	}

	public Set<RoleToPermission> getRoleToPermissionSet() {
		return roleToPermissionSet;
	}

	public void setRoleToPermissionSet(Set<RoleToPermission> roleToPermissionSet) {
		this.roleToPermissionSet = roleToPermissionSet;
	}

	

}
