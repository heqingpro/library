package com.ipanel.web.category.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ipanel.web.category.pageModel.NodeModel;
import com.ipanel.web.category.pageModel.NodeTreeModel;
import com.ipanel.web.category.service.INodeService;
import com.ipanel.web.common.dao.BaseDao;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.common.redis.RedisUtils;
import com.ipanel.web.define.NodeDefined;
import com.ipanel.web.entity.AppInfo;
import com.ipanel.web.entity.EntryImageInfo;
import com.ipanel.web.entity.EntryInfo;
import com.ipanel.web.entity.EntryToNode;
import com.ipanel.web.entity.NodeImageInfo;
import com.ipanel.web.entity.NodeInfo;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.sysUser.service.impl.SystemLogService;
import com.ipanel.web.utils.Constants;
import com.ipanel.web.utils.FileOperation;
import com.ipanel.web.utils.TimeUtil;
import com.ipanel.webapp.framework.core.dao.DaoQueryOperator;
import com.ipanel.webapp.framework.util.Log;

@Service("nodeService")
public class NodeServiceImpl implements INodeService {

	private final String TAG = "NodeServiceImpl";
	
	@Resource
	private BaseDao baseDao;
	
	@Resource(name="systemLogService")
	private SystemLogService systemLogService;
	

	@Resource(name = "redisUtils")
	private RedisUtils redisUtils;	
	
	@Override
	public boolean hasChildNodes(Integer id) {
		Object[][] paramArray = {{"parentNode_id", id}};
		List<?> list = baseDao.query(NodeInfo.class, paramArray );
		if(list.size() > 0) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public PageDataModel queryNodeTree(NodeModel model, Integer appId, Integer subView) {
		if(appId==null){
			return null;
		}
		AppInfo appInfo = baseDao.get(AppInfo.class, appId);
		List<NodeTreeModel> nodetreeLs = new ArrayList<NodeTreeModel>(); 
		if(appInfo!=null){
			List<NodeInfo> nodeInfoList = appInfo.getNodeInfoList();
			for(NodeInfo node:nodeInfoList){
				if(node.getParentNode_id()==0) {//根节点不要
					continue;
				}
				if(subView!=null&&subView==1&&node.getIsParent()==1){
					continue;
				}
				NodeTreeModel nodeTreeModel = new NodeTreeModel();
				nodeTreeModel.setId(node.getId());
				nodeTreeModel.setpId(node.getParentNode_id());
				nodeTreeModel.setApp_id(appId);
				nodeTreeModel.setName(node.getNodeName());
				nodeTreeModel.setEnName(node.getEnName());
				nodeTreeModel.setSort_value(node.getSortValue());
				nodeTreeModel.setName(node.getNodeName());
				nodeTreeModel.setLinkUrl(node.getLinkUrl());
				nodeTreeModel.setIsParent(node.getIsParent());
				nodeTreeModel.setIsOnline(node.getIsOnline());
				//nodeTreeModel.setNodeType(node.getNodeType());
				//nodeTreeModel.setNodeTypeName(EnumNodeType.getName(node.getNodeType()));
				//nodeTreeModel.setAngleId(node.getAngle_id());
				if(node.getNodeImage_id()!=null){
					NodeImageInfo nodeImageInfo =  baseDao.get(NodeImageInfo.class, node.getNodeImage_id());
					nodeTreeModel.setPosterName(nodeImageInfo.getUniqueName());
					nodeTreeModel.setPosterUrl(FileOperation.getImagePath(nodeImageInfo.getUniqueName()));
				}
				if(node.getThumbnail_id()!=null){
					NodeImageInfo nodeImageInfo =  baseDao.get(NodeImageInfo.class, node.getThumbnail_id());
					nodeTreeModel.setThumbnailName(nodeImageInfo.getUniqueName());
					nodeTreeModel.setThumbnailUrl(FileOperation.getImagePath(nodeImageInfo.getUniqueName()));
				}
				//如果只显示子节点，subView==1
				nodetreeLs.add(nodeTreeModel);
			}
		}		
		return new PageDataModel(nodetreeLs!=null?nodetreeLs.size():0, nodetreeLs);
	}

	@Override
	public String addNode(SysUser user,String fileType, CommonsMultipartFile nodeImage, String nodeThumbnailType, CommonsMultipartFile nodeThumbnail, NodeModel model){
		List<NodeInfo> nodeInfoList = baseDao.query(NodeInfo.class,
					new Object[][]{
						{DaoQueryOperator.EQ,"nodeName",
							model.getName()},
						{DaoQueryOperator.EQ,"parentNode_id",
								model.getpId()}
				}); 
		if(nodeInfoList!=null&&nodeInfoList.size()>0){
			return NodeDefined.NODE_EXIST;
		}
		NodeInfo parentNode = baseDao.get(NodeInfo.class, model.getpId());
		//修改父节点的类型
		parentNode.setIsParent(1);
		baseDao.update(parentNode);
		
		//保存新的节点
		NodeInfo nodeInfo = new NodeInfo();		
		String hql="select max(n.id) from NodeInfo n";
        Query query=baseDao.getCurrentSession().createQuery(hql);
        int id=((Integer) query.uniqueResult()).intValue() + 1;
        nodeInfo.setId(id);
		
		nodeInfo.setAppInfo(parentNode!=null?parentNode.getAppInfo():null);
		nodeInfo.setParentNode_id(model.getpId());
		nodeInfo.setNodeName(model.getName());
		nodeInfo.setEnName(model.getEnName());
		nodeInfo.setEntryWord(model.getEntryWord());
		nodeInfo.setLinkUrl(model.getLinkUrl());
		nodeInfo.setIsOnline(1); //默认上线状态
		nodeInfo.setIsParent(0);//默认0：叶子节点
		//nodeInfo.setNodeType(model.getNodeType()); //普通分类，推荐位类型
		nodeInfo.setAngle_id(model.getAngleId());//绑定一个语种
		
		nodeInfo.setAddtime(TimeUtil.getCurrentTime());
		Integer nodeId = (Integer)baseDao.save(nodeInfo);
		nodeInfo.setSortValue(nodeId );
		//保存海报,并上传到服务器
		if(nodeImage!=null&&StringUtils.isNotEmpty(fileType)){
			NodeImageInfo nodeImageInfo = uploadImage(nodeImage,fileType);
			nodeImageInfo.setNodeInfo(nodeInfo);
			nodeInfo.setNodeImage_id(nodeImageInfo.getId());
		}
		//保存缩略图     
		if(nodeThumbnail!=null&&StringUtils.isNotEmpty(nodeThumbnailType)){
			NodeImageInfo nodeImageInfo = uploadImage(nodeThumbnail,nodeThumbnailType);
			nodeImageInfo.setNodeInfo(nodeInfo);
			nodeInfo.setThumbnail_id(nodeImageInfo.getId());
		}
		try {
			systemLogService.saveSystemLog("分类管理", "添加分类", "用户\""+user.getUserName()+"\"添加了\""+model.getName()+ "\"分类", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}

	@Override
	public String editNode(SysUser user,String fileType, CommonsMultipartFile nodeImage, String nodeThumbnailType, CommonsMultipartFile nodeThumbnail, NodeModel model){
		List<NodeInfo> nodeInfoList = baseDao.query(NodeInfo.class,
				new Object[][]{
					{DaoQueryOperator.EQ,"nodeName",
						model.getName()},
					{DaoQueryOperator.NIN,"id",
							model.getId()}
			});
		if (nodeInfoList != null && nodeInfoList.size() > 0) {
			return NodeDefined.NODE_EXIST;
		}
		NodeInfo nodeInfo = baseDao.get(NodeInfo.class, model.getId());
		nodeInfo.setNodeName(model.getName());
		nodeInfo.setEnName(model.getEnName());
		nodeInfo.setEntryWord(model.getEntryWord());
		nodeInfo.setLinkUrl(model.getLinkUrl());
		//nodeInfo.setNodeType(model.getNodeType());
		nodeInfo.setAngle_id(model.getAngleId());
		nodeInfo.setModifyTime(TimeUtil.getCurrentTime());
		//保存海报,并上传到服务器
		if(nodeImage!=null&&StringUtils.isNotEmpty(fileType)){
			//删除之前的海报图片
			if(nodeInfo.getNodeImage_id()!=null){
				NodeImageInfo nodeImageInfo = baseDao.get(NodeImageInfo.class, nodeInfo.getNodeImage_id());
				FileOperation.deleteNodeImages(nodeImageInfo.getUniqueName());
				baseDao.delete(nodeImageInfo);
			}
			//保存新的海报图片
			NodeImageInfo nodeImageInfo = uploadImage(nodeImage,fileType);
			nodeImageInfo.setNodeInfo(nodeInfo);
			nodeInfo.setNodeImage_id(nodeImageInfo.getId());
		}
		//保存海报缩略图,并上传到服务器
		if(nodeThumbnail!=null&&StringUtils.isNotEmpty(nodeThumbnailType)){
			//删除之前的海报缩略图片
			if(nodeInfo.getThumbnail_id()!=null){
				NodeImageInfo nodeImageInfo = baseDao.get(NodeImageInfo.class, nodeInfo.getThumbnail_id());
				FileOperation.deleteNodeImages(nodeImageInfo.getUniqueName());
				baseDao.delete(nodeImageInfo);
			}
			//保存新的海报图片
			NodeImageInfo nodeImageInfo = uploadImage(nodeThumbnail,nodeThumbnailType);
			nodeImageInfo.setNodeInfo(nodeInfo);
			nodeInfo.setThumbnail_id(nodeImageInfo.getId());
		}
		baseDao.update(nodeInfo);
		try {
			systemLogService.saveSystemLog("分类管理", "编辑分类", "用户\""+user.getUserName()+"\"编辑了\""+model.getName()+ "\"分类", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		return null;
	}

	@Override
	public void deleteNode(SysUser user, Integer id) {
		StringBuffer nodeNameBuffer = new StringBuffer();
		NodeInfo node = baseDao.get(NodeInfo.class,id);
		if(node!=null){
			if(node.getNodeImage_id()!=null){
				//删除节点图片（文件服务器）
				NodeImageInfo nodeImage = baseDao.get(NodeImageInfo.class, node.getNodeImage_id());
				FileOperation.deleteNodeImages(nodeImage.getUniqueName());
			}
			if(node.getThumbnail_id()!=null){
				NodeImageInfo nodeImage = baseDao.get(NodeImageInfo.class, node.getThumbnail_id());
				FileOperation.deleteNodeImages(nodeImage.getUniqueName());
			}
			//删除节点下的图书图片（文件服务器）
			List<EntryToNode> entryToNodes = node.getEntryToNodeList();
			for(EntryToNode entryToNode:entryToNodes){
				EntryInfo entryInfo = entryToNode.getEntryInfo();
				List<EntryImageInfo> entryImageInfos = entryInfo.getEntryImageList();
				for(EntryImageInfo entryImageInfo:entryImageInfos){
					FileOperation.deleteNodeImages(entryImageInfo.getUniqueName());
				}
			}
			//删除节点
			nodeNameBuffer.append(node.getNodeName()+",");
			baseDao.delete(node);
			node.getEntryToNodeList().clear();
			node.getNodeImageList().clear();
			
			//删除当前节点后，其父节点变为叶子节点
			NodeInfo parentNode = baseDao.get(NodeInfo.class, node.getParentNode_id());
			if(parentNode!=null&&parentNode.getIsParent()!=0){
				parentNode.setIsParent(0);
				baseDao.update(parentNode);
			}
		}
		
		try {
			systemLogService.saveSystemLog("分类管理", "删除分类", "用户\""+user.getUserName()+"\"删除了\""+nodeNameBuffer.toString()+ "\"分类", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}
		
	}

	@Override
	public String moveNode(SysUser user, Integer nodeId,
			Integer targetNodeId, String moveType) {
		NodeInfo node = baseDao.get(NodeInfo.class, nodeId);
		NodeInfo parentNode = baseDao.get(NodeInfo.class,node.getParentNode_id());
		NodeInfo targetNode = baseDao.get(NodeInfo.class, targetNodeId);
		NodeInfo targetParentNode = baseDao.get(NodeInfo.class, targetNode.getParentNode_id());
		Integer pId = parentNode.getId();
		Integer tarPId = targetParentNode.getId();
		NodeInfo targetLastChildren = null; //目标节点的最后一个子节点
		Integer targetSortValue = targetNode.getSortValue();
		List<NodeInfo> childrenNodes = baseDao.query(NodeInfo.class, new Object[][]{{
			DaoQueryOperator.EQ,"parentNode_id",targetNodeId
		}});
		if (childrenNodes.size()>0) {
			targetLastChildren = childrenNodes.get(childrenNodes.size()-1);
		}
		if(("prev").equals(moveType)){
			if(pId.equals(tarPId)){//如果是兄弟节点,被拖动节点的sortValue设为目标节点的sortValue，sortValue大于等于目标节点的都加1
				updateNodeSortValuePrev(node,targetNode);				
			}else{//其他情况，则被拖动节点的父节点改为和目标节点的父节点,序号在目标节点之前，被拖动节点下的所有节点的节点路径跟着更改。
				update_GTE_TargetNode(node,targetNode);
				node.setParentNode_id(tarPId);
				node.setSortValue(targetSortValue);
				baseDao.update(node);
			}			
		}else if(("next").equals(moveType)){
			if(pId.equals(tarPId)){	//如果是兄弟节点，被拖动节点的序号在目标节点之后
				updateNodeSortValueNext(node,targetNode);
			}else{//其他情况，则被拖动节点的父节点改为和目标节点的父节点,序号在目标节点之后，被拖动节点下的所有节点的节点路径跟着更改。
				update_GT_TargetNode(node,targetNode);
				node.setParentNode_id(tarPId);
				node.setSortValue(targetSortValue);
				baseDao.update(node);
			}	
		}else if (("inner").equals(moveType)){
			//其他情况，则被拖动节点的父节点改为目标节点,序号在目标节点最后一个子节点之后，被拖动节点下的所有节点的节点路径跟着更改。
			if(targetLastChildren!=null){//有最后一个子节点
				Integer targetLastChildrenSort = targetLastChildren.getSortValue();
				node.setSortValue(targetLastChildrenSort);
			}
			node.setParentNode_id(targetNodeId);
			baseDao.update(node);
			//父节点没法拖到子节点后面
		}
		try {
			systemLogService.saveSystemLog("分类管理", "移动分类", "用户\""+user.getUserName()+"\"移动了\""+node.getNodeName()+ "\"分类", user);
		} catch (Exception e) {
			Log.e(TAG, "系统添加日志出错"+e);
		}	
		return null;
	}

	private void update_GT_TargetNode(NodeInfo node, NodeInfo targetNode) {
		int parentNodeId=targetNode.getParentNode_id();
		int sortValue=node.getSortValue();
		int destSortValue=targetNode.getSortValue();
		baseDao.update(NodeInfo.class, new Object[][]{
			{"sortValue", "sortValue+1"}},
			new Object[][]{
				{DaoQueryOperator.GT,"sortValue",destSortValue},
				{DaoQueryOperator.EQ,"parentNode_id",parentNodeId}
		});						
	}

	private void updateNodeSortValueNext(NodeInfo node, NodeInfo targetNode) {
		int parentNodeId=node.getParentNode_id();
		int sortValue=node.getSortValue();
		int destSortValue=targetNode.getSortValue();
		//往后拖
		if(destSortValue>sortValue){//目标sortValue比当前sortValue大的时候，替换目标sortValue+1,原sortValue（不包括）到目标sortValue不包括）之间的对象sortValue都加1.例如1替换到10，则2到10之间的对象变为1~9
			baseDao.update(NodeInfo.class, new Object[][]{
				{"sortValue", "sortValue-1"}},
				new Object[][]{
					{DaoQueryOperator.GT,"sortValue",sortValue},
					{DaoQueryOperator.LTE,"sortValue",destSortValue},
					{DaoQueryOperator.EQ,"parentNode_id",parentNodeId}
			});
			node.setSortValue(destSortValue);
			baseDao.update(node);						
		}else if (destSortValue<sortValue) {//目标sortValue比当前sortValue小的时候，替换目标sortValue,目标sortValue（包括）到原sortValue（不包括）之间的对象sortValue都加1.例如10替换到1，则1到9之间的对象都变为2~10
			//往前拖
			baseDao.update(NodeInfo.class, new Object[][]{
				{"sortValue", "sortValue+1"}},
				new Object[][]{
					{DaoQueryOperator.GT,"sortValue",destSortValue},
					{DaoQueryOperator.LT,"sortValue",sortValue},
					{DaoQueryOperator.EQ,"parentNode_id",parentNodeId}
			});
			node.setSortValue(destSortValue+1);
			baseDao.update(node);		
		}
	}

	private void update_GTE_TargetNode(NodeInfo node, NodeInfo targetNode) {
		int parentNodeId=targetNode.getParentNode_id();
		int sortValue=node.getSortValue();
		int destSortValue=targetNode.getSortValue();
		baseDao.update(NodeInfo.class, new Object[][]{
				{"sortValue", "sortValue+1"}},
				new Object[][]{
					{DaoQueryOperator.GTE,"sortValue",destSortValue},
					{DaoQueryOperator.EQ,"parentNode_id",parentNodeId}
				});
	}

	/**
	 * 修改排序规则
	 * @param node
	 * @param targetNode
	 */
	private void updateNodeSortValuePrev(NodeInfo node, NodeInfo targetNode) {
		int parentNodeId=node.getParentNode_id();
		int sortValue=node.getSortValue();
		int destSortValue=targetNode.getSortValue();
		//往后拖
		if(destSortValue>sortValue){//目标sortValue比当前sortValue大的时候，替换目标sortValue-1,原sortValue（不包括）到目标sortValue不包括）之间的对象sortValue都加1.例如1替换到10，则2到10之间的对象变为1~9
			baseDao.update(NodeInfo.class,
				new Object[][]{{"sortValue", "sortValue-1"}},
				new Object[][]{{
					DaoQueryOperator.GT,"sortValue",sortValue},
					{DaoQueryOperator.LT,"sortValue",destSortValue},
					{DaoQueryOperator.EQ,"parentNode_id",parentNodeId}				
			});
			node.setSortValue(destSortValue-1);
			baseDao.update(node);
		}else if (destSortValue<sortValue) {//目标sortValue比当前sortValue小的时候，替换目标sortValue,目标sortValue（包括）到原sortValue（不包括）之间的对象sortValue都加1.例如10替换到1，则1到9之间的对象都变为2~10
			//往前拖
			baseDao.update(NodeInfo.class,
					new Object[][]{{"sortValue", "sortValue+1"}},
					new Object[][]{{
						DaoQueryOperator.GTE,"sortValue",destSortValue},
						{DaoQueryOperator.LT,"sortValue",sortValue},
						{DaoQueryOperator.EQ,"parentNode_id",parentNodeId}				
				});
			node.setSortValue(destSortValue);
			baseDao.update(node);
		}
		
	}

	/**
	 * 上传分类海报
	 * @author fangg
	 * 2017年5月11日 上午11:46:39
	 * @param imageFile
	 * @param suffix
	 * @return
	 */
	public NodeImageInfo uploadImage(CommonsMultipartFile imageFile,String suffix) {
		byte[] imageContent=imageFile.getBytes();
		String filepicPath = "";
		String remoteFileName = null;
		//获取文件后缀
		if(!StringUtils.isEmpty(imageFile.getOriginalFilename())){
			remoteFileName= System.currentTimeMillis()+"."+suffix;
			filepicPath=Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+remoteFileName;
			//将图片上到资源服务器上nginx或apache,与内容提供商部署在同一台服务器
			FileOperation.writeNodeImages(remoteFileName, imageContent);
			
			//保存到数据库
			NodeImageInfo nodeImage  = new NodeImageInfo();
			nodeImage.setDeviceType("0");
			nodeImage.setImageName(imageFile.getOriginalFilename());
			nodeImage.setUniqueName(remoteFileName);
			nodeImage.setImageType(suffix);
			nodeImage.setImagePath(filepicPath);
			nodeImage.setModifyTime(TimeUtil.getCurrentTime());
			baseDao.save(nodeImage);
			return nodeImage;
		}
		return null;
	}

	@Override
	public void updateNodeStatus(SysUser user, Integer id) {
		NodeInfo nodeInfo = baseDao.get(NodeInfo.class, id);
		//下线会将其子分类一起下线
		if(nodeInfo.getIsOnline()==1){
			baseDao.update(NodeInfo.class,
					new Object[][]{{"isOnline", 0}},
					new Object[][]{
					{DaoQueryOperator.EQ,"parentNode_id",id}
					});
			nodeInfo.setIsOnline(0);
		}else{//上线
			nodeInfo.setIsOnline(1);
		}
		baseDao.save(nodeInfo);		
	}
	

}
