package com.ipanel.web.category.pageModel;

public class NodeTreeModel {
	
	private Integer id;
	
	private Integer pId;
	
	private String name;
	
	private String enName;	
	
	private Integer app_id;
		
	private String posterUrl;
	
	private String thumbnailUrl;
	
	private String posterName;
	
	private String thumbnailName;
	
	private String linkUrl;

	private Integer sort_value;
	
	//private Integer nodeType;
	
	private Integer angleId;//语种
	
	private String nodeTypeName;
	
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

	public Integer getApp_id() {
		return app_id;
	}

	public void setApp_id(Integer app_id) {
		this.app_id = app_id;
	}

	public Integer getSort_value() {
		return sort_value;
	}

	public void setSort_value(Integer sort_value) {
		this.sort_value = sort_value;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getPosterName() {
		return posterName;
	}

	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	/*public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}*/

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

	public String getNodeTypeName() {
		return nodeTypeName;
	}

	public void setNodeTypeName(String nodeTypeName) {
		this.nodeTypeName = nodeTypeName;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
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

	
	
	
	


}
