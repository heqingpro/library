package  com.ipanel.web.entity;

import javax.persistence.Column;
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
@Table(name = "sys_log")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SystemLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "module_name")
	private String moduleName;

	@Column(name = "operating_function")
	private String operatingFunction;

	@Column(name = "operating_desc")
	private String operatingDesc;
	
	@Column(name = "operating_date")
	private String operatingDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private SysUser sysUser;

	public SystemLog() {
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOperatingFunction() {
		return operatingFunction;
	}

	public void setOperatingFunction(String operatingFunction) {
		this.operatingFunction = operatingFunction;
	}

	public String getOperatingDesc() {
		return operatingDesc;
	}

	public void setOperatingDesc(String operatingDesc) {
		this.operatingDesc = operatingDesc;
	}

	public String getOperatingDate() {
		return operatingDate;
	}

	public void setOperatingDate(String operatingDate) {
		this.operatingDate = operatingDate;
	}
	
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	public SystemLog(String moduleName, String operatingFunction, String operatingDesc, String operatingDate) {
		super();
		this.moduleName = moduleName;
		this.operatingFunction = operatingFunction;
		this.operatingDesc = operatingDesc;
		this.operatingDate = operatingDate;
	}	
	
	

}
