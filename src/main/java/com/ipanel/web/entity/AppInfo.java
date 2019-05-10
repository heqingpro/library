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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="app")
@DynamicUpdate(true)
@DynamicInsert(true)
public class AppInfo {
	
	public static final String STATUS_RELEASED = "released";//已发布
	
	public static final String STATUS_UNRELEASED = "unreleased";//取消发布
	
	public static final String STATUS_NEW = "new";//新建
	
	public static final String STATUS_EDITED = "edited";//已编辑
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//主键id
	
	@Column(name="appName")
	private String appName ;//专区内容提供商名
	
	@Column(name="enName")
	private String enName ;//英文名称
	
	@Column(name="remark")
	private String remark ;//补充说明
	
	@Column(name="addTime")
	private String addTime ;//创建时间
	
	@Column(name="modifyTime")
	private String modifyTime;//修改时间
	
	@Column(name="rootNode_Id")
	private Integer rootNodeId;//跟分类的id
	
	
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bouquet")
	private List<ChannelToBouquet> channelToBouquetList;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bouquet")
	private List<BouquetToServer> bouquetToServerList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bouquet")
	private List<BouquetVersion> bouquetVersionList;//历史版本集合
	
	@Column(name="release_version",nullable=false,columnDefinition="INT default 0")
	private Integer releaseVersion ;//发布数据的版本号,设置默认值，必须表可以动态更新，插入

	@Column(name="sort_no")
	private Integer sortNumber;//排序编号
 */	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appInfo")
	private List<NodeInfo> nodeInfoList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}
	
	
	
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<NodeInfo> getNodeInfoList() {
		return nodeInfoList;
	}

	public void setNodeInfoList(List<NodeInfo> nodeInfoList) {
		this.nodeInfoList = nodeInfoList;
	}

	public Integer getRootNodeId() {
		return rootNodeId;
	}

	public void setRootNodeId(Integer rootNodeId) {
		this.rootNodeId = rootNodeId;
	}


	
	
	
}
