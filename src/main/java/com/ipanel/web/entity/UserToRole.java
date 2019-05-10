package  com.ipanel.web.entity;

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

@Entity
@Table(name = "sys_user_role")
@DynamicUpdate(true)
@DynamicInsert(true)
public class UserToRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private SysUser sysUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Role role;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}

