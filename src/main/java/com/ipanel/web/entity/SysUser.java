package  com.ipanel.web.entity;

import java.util.List;
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
@Table(name = "sys_user")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SysUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "create_time")
	private String createTime;
	
	@Column(name="user_type")
	private String userType;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	private Set<UserToRole> userToRoles;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	private List<SysUserToApp> sysUserToApps;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Set<UserToRole> getUserToRoles() {
		return userToRoles;
	}

	public void setUserToRoles(Set<UserToRole> userToRoles) {
		this.userToRoles = userToRoles;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<SysUserToApp> getSysUserToApps() {
		return sysUserToApps;
	}

	public void setSysUserToApps(List<SysUserToApp> sysUserToApps) {
		this.sysUserToApps = sysUserToApps;
	}
	

}
