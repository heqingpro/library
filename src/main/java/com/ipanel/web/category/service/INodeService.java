package com.ipanel.web.category.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.category.pageModel.NodeModel;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.entity.SysUser;

public interface INodeService {

	public PageDataModel queryNodeTree(NodeModel model, Integer appId,Integer subView);

	
	public String editNode(SysUser user,String fileType, CommonsMultipartFile nodeImage, String nodeThumbnailType, CommonsMultipartFile nodeThumbnail, NodeModel model);

	public void deleteNode(SysUser user, Integer id);
	
	public String moveNode(SysUser user, Integer nodeIds, Integer targetNode,String moveType);

	public String addNode(SysUser user,String fileType, CommonsMultipartFile nodeImage, String nodeThumbnailType, CommonsMultipartFile nodeThumbnail, NodeModel model);


	public void updateNodeStatus(SysUser user, Integer id);

	
	public boolean hasChildNodes(Integer id);
}
