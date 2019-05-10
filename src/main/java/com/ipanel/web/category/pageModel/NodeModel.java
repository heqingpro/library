package com.ipanel.web.category.pageModel;



public class NodeModel {
	
	private Integer id;
	
	private Integer pId;

	private Integer app_id;
	
	private String name;
	
	private String enName;	
	
	private String entryWord;
	
	//private Integer nodeType;//0:普通分类,1:剧集类,单品类，专题类……，来自于推荐位类别表的数据
	
	private String typeIds; //剧集类,单品类，专题类……，来自于推荐位类别表的数据
	
	private Integer angleId;//语种，一个分类绑定一个语种
	
	private String nodeImageName; //海报名称
	
	private String thumbnailName;//缩略图名称
	
	private String linkUrl;  //外链接
	
	private String vodId;  //单集类名称
	
	private String fatherVodId;  //单集类名称
	
	private String resumeDuration;  //播放间隔时间
	
	private Integer isParent;// 是否为非叶子节点
	
	private Integer isOnline; //是否上/下线

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Integer getApp_id() {
		return app_id;
	}

	public void setApp_id(Integer app_id) {
		this.app_id = app_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEntryWord() {
		return entryWord;
	}

	public void setEntryWord(String entryWord) {
		this.entryWord = entryWord;
	}

	/*public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}*/

	public String getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
	}

	public String getNodeImageName() {
		return nodeImageName;
	}

	public void setNodeImageName(String nodeImageName) {
		this.nodeImageName = nodeImageName;
	}

	public String getVodId() {
		return vodId;
	}

	public void setVodId(String vodId) {
		this.vodId = vodId;
	}

	public String getFatherVodId() {
		return fatherVodId;
	}

	public void setFatherVodId(String fatherVodId) {
		this.fatherVodId = fatherVodId;
	}

	public String getResumeDuration() {
		return resumeDuration;
	}

	public void setResumeDuration(String resumeDuration) {
		this.resumeDuration = resumeDuration;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public String getThumbnailName() {
		return thumbnailName;
	}

	public void setThumbnailName(String thumbnailName) {
		this.thumbnailName = thumbnailName;
	}

	public Integer getAngleId() {
		return angleId;
	}

	public void setAngleId(Integer angleId) {
		this.angleId = angleId;
	}

	@Override
	public String toString() {
		return "NodeModel [id=" + id + ", pId=" + pId + ", app_id=" + app_id
				+ ", name=" + name + ", enName=" + enName + ", entryWord="
				+ entryWord + ", typeIds=" + typeIds + ", angleId=" + angleId
				+ ", nodeImageName=" + nodeImageName + ", thumbnailName="
				+ thumbnailName + ", linkUrl=" + linkUrl + ", vodId=" + vodId
				+ ", fatherVodId=" + fatherVodId + ", resumeDuration="
				+ resumeDuration + ", isParent=" + isParent + ", isOnline="
				+ isOnline + "]";
	}
	
}
