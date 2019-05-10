package com.ipanel.web.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="node")
public class NodeInfo {

	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="appInfo_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private AppInfo appInfo;
	
	@Column(name="parentNode_id")
	private Integer parentNode_id;	
	
	@Column(name="nodeName")	
	private String nodeName;//节点名称
	
	@Column(name="enName")
	private String enName;//英文名
	
	@Column(name="nodeType")
	private Integer nodeType;//节点类型（普通分类：0,1：推荐位……）
	
	@Column(name="entryWord")
	private String entryWord;//入口文字
	
	@Column(name="linkurl")
	private String linkUrl;//外链接
	
	@Column(name="hasEntry")
	private String hasEntry;//是否管理图书
	
	@Column(name="isHome")
	private Integer isHome;//是否是主页分类
	
	@Column(name="isParent")
	private Integer isParent;//1，是父节点，0否：叶子节点
	
	@Column(name="isOnline")
	private Integer isOnline; //是否上线
	
	
	@Column(name="hasPoster")
	private Integer hasPoster;//是否有海报，比如分类分类无海报
	
	@Column(name="hasAlbum")
	private Integer hasAlbum;//是否有相册
	
	@Column(name="hasVedio")
	private Integer hasVedio;//是否有视频
	
	@Column(name="hasLink")
	private Integer hasLink;//是否有外链接
		
	@Column(name="hasText")
	private Integer hasText;//是否含文本
	
	@Column(name="messagePeriod")
	private Integer messagePeriod;//消息更新周期（单位：天）
	
	@Column(name="addtime")
	private String addtime;//创建时间
	
	@Column(name="modifyTime")
	private String modifyTime;//最近编辑时间
	
	@Column(name="operateUserId")
	private String operateUserId;//操作人
	
	@Column(name="sortValue")
	private Integer sortValue;//排序编号
	
	@Column(name="isReleased")
	private Integer isReleased;//是否已发布（1：是，0：否）
	
	@Column(name="nodeImage_id")
	private Integer nodeImage_id; //分类入口海报
	
	@Column(name="thumbnail_id")
	private Integer thumbnail_id;//分类缩略图id
	
	@Column(name="angle_id")
	private Integer angle_id;//分类绑定语种，一对一的关系
	
	
	//映射图书 一对多关系
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nodeInfo")
	private List<EntryToNode> entryToNodeList;
	
	//映射海报 一对多关系
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nodeInfo")
	private List<NodeImageInfo> nodeImageList;//推荐位海报
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}

	public Integer getParentNode_id() {
		return parentNode_id;
	}

	public void setParentNode_id(Integer parentNode_id) {
		this.parentNode_id = parentNode_id;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public String getEntryWord() {
		return entryWord;
	}

	public void setEntryWord(String entryWord) {
		this.entryWord = entryWord;
	}

	public String getHasEntry() {
		return hasEntry;
	}

	public void setHasEntry(String hasEntry) {
		this.hasEntry = hasEntry;
	}

	public Integer getIsHome() {
		return isHome;
	}

	public void setIsHome(Integer isHome) {
		this.isHome = isHome;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getHasPoster() {
		return hasPoster;
	}

	public void setHasPoster(Integer hasPoster) {
		this.hasPoster = hasPoster;
	}

	public Integer getHasAlbum() {
		return hasAlbum;
	}

	public void setHasAlbum(Integer hasAlbum) {
		this.hasAlbum = hasAlbum;
	}

	public Integer getHasVedio() {
		return hasVedio;
	}

	public void setHasVedio(Integer hasVedio) {
		this.hasVedio = hasVedio;
	}

	public Integer getHasLink() {
		return hasLink;
	}

	public void setHasLink(Integer hasLink) {
		this.hasLink = hasLink;
	}
	
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getHasText() {
		return hasText;
	}

	public void setHasText(Integer hasText) {
		this.hasText = hasText;
	}

	public Integer getMessagePeriod() {
		return messagePeriod;
	}

	public void setMessagePeriod(Integer messagePeriod) {
		this.messagePeriod = messagePeriod;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public Integer getSortValue() {
		return sortValue;
	}

	public void setSortValue(Integer sortValue) {
		this.sortValue = sortValue;
	}

	public Integer getIsReleased() {
		return isReleased;
	}

	public void setIsReleased(Integer isReleased) {
		this.isReleased = isReleased;
	}

	public List<NodeImageInfo> getNodeImageList() {
		return nodeImageList;
	}

	public void setNodeImageList(List<NodeImageInfo> nodeImageList) {
		this.nodeImageList = nodeImageList;
	}

	public Integer getNodeImage_id() {
		return nodeImage_id;
	}

	public void setNodeImage_id(Integer nodeImage_id) {
		this.nodeImage_id = nodeImage_id;
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public Integer getThumbnail_id() {
		return thumbnail_id;
	}

	public void setThumbnail_id(Integer thumbnail_id) {
		this.thumbnail_id = thumbnail_id;
	}

	public Integer getAngle_id() {
		return angle_id;
	}

	public void setAngle_id(Integer angle_id) {
		this.angle_id = angle_id;
	}

	public List<EntryToNode> getEntryToNodeList() {
		return entryToNodeList;
	}

	public void setEntryToNodeList(List<EntryToNode> entryToNodeList) {
		this.entryToNodeList = entryToNodeList;
	}

	
	
	
	
	
	
}
