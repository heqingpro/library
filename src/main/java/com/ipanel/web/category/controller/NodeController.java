package com.ipanel.web.category.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;





import com.ipanel.web.category.pageModel.NodeModel;
import com.ipanel.web.category.service.INodeService;
import com.ipanel.web.common.model.PageDataModel;
import com.ipanel.web.common.model.BaseDataModel.ResponseDataModel;
import com.ipanel.web.common.redis.RedisUtils;
import com.ipanel.web.entity.AppUser;
import com.ipanel.web.entity.SysUser;
import com.ipanel.web.utils.Constants;
import com.ipanel.webapp.framework.util.Log;

@Controller
@RequestMapping("/nodeController")
public class NodeController {
	
	private final String TAG = "NodeController";
	
	@Autowired
	private INodeService nodeService;

	/**
	 * 获取分类树
	 * @author fangg
	 * 2017年5月13日 下午5:00:27
	 * @param model
	 * @param appId
	 * @return
	 */
	@RequestMapping("/queryNodeTree")
	@ResponseBody
	public PageDataModel queryNodeTree(NodeModel model,Integer appId,Integer subView){
		try {
			Log.i(TAG, "***　queryNodeTree　enter appid="+appId+",subview="+subView);
			return nodeService.queryNodeTree(model,appId,subView);
		} catch (Exception e) {
			Log.e(TAG, "*** queryNodeTree throw Exception:"+e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 创建分类节点
	 * @author fangg
	 * 2017年5月13日 下午5:00:40
	 * @param nodeImage
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/addNode")
	@ResponseBody
	public ResponseDataModel addNode(
			@RequestParam("nodeImage") CommonsMultipartFile nodeImage,
			@RequestParam("nodeThumbnail") CommonsMultipartFile nodeThumbnail,
			HttpSession session,NodeModel model){
		
		String fileType = "";
		String nodeThumbnailType = "";
		try {
			Log.i(TAG, "*** addNode enter ,name="+model.getName()+",nodeImage="+nodeImage+",nodeThumbnail="+nodeThumbnail);
			if(nodeImage!=null){
				String fileName = nodeImage.getOriginalFilename();
				if (StringUtils.isNotEmpty(fileName)) {
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						fileType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"海报文件名异常");
					}
				}
			}
			if(nodeThumbnail!=null){
				String fileName = nodeThumbnail.getOriginalFilename();
				if (StringUtils.isNotEmpty(fileName)) {
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						nodeThumbnailType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"缩略图文件名异常");
					}
				}
			}
			//保存分类
			String msg  = nodeService.addNode((SysUser)session.getAttribute(Constants.SESSION_USER),fileType,nodeImage,nodeThumbnailType,nodeThumbnail,model);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** addNode throw exception:" + e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	} 
	/**
	 * 修改分类
	 * @author fangg
	 * 2017年5月13日 下午5:01:52
	 * @param nodeImage
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/editNode")
	@ResponseBody
	public ResponseDataModel editNode(@RequestParam("nodeImage") CommonsMultipartFile nodeImage,
			@RequestParam("nodeThumbnail") CommonsMultipartFile nodeThumbnail,
			HttpSession session,NodeModel model){
		String fileType = "";
		String nodeThumbnailType = "";
		try {
			Log.i(TAG, "*** editNode nodeName="+model.getName()+",nodeImage="+nodeImage);
			if(nodeImage!=null){
				String fileName = nodeImage.getOriginalFilename();
				if (StringUtils.isNotEmpty(fileName)) {
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						fileType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"海报文件名异常");
					}
				}
			}
			if(nodeThumbnail!=null){
				String fileName = nodeThumbnail.getOriginalFilename();
				if (StringUtils.isNotEmpty(fileName)) {
					String[] fileNames = fileName.trim().split("\\.");
					if (fileNames != null && fileNames.length > 1) {
						nodeThumbnailType = fileNames[fileNames.length - 1].trim(); //后缀
					} else {
						return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS,
								"缩略图文件名异常");
					}
				}
			}
			//保存分类
			String msg = nodeService.editNode((SysUser)session.getAttribute(Constants.SESSION_USER),fileType,nodeImage,nodeThumbnailType,nodeThumbnail,model);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return 	ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** editNode throw Exception:"+e);
			e.printStackTrace();
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	/**
	 * 上/下线分类
	 * @author fangg
	 * 2017年5月13日 下午5:02:06
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateNodeStatus")
	@ResponseBody	
	public ResponseDataModel updateNodeStatus(HttpSession session,Integer id){
		try{
			Log.i(TAG, "*** updateNodeStatus enter id="+id);
			nodeService.updateNodeStatus((SysUser)session.getAttribute(Constants.SESSION_USER),id);
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		}catch(Exception e){
			Log.e(TAG, "*** updateNodeStatus throw Exception:"+e);
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	/**
	 * 删除分类
	 * @author fangg
	 * 2017年5月13日 下午5:02:23
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteNode")
	@ResponseBody
	public ResponseDataModel deleteNode(HttpSession session,Integer id){
		try{
			Log.i(TAG, "*** deleteNode enter id="+id);
			if(nodeService.hasChildNodes(id)) {
				ResponseDataModel responseDataModel = new ResponseDataModel(false, "当前节点下还有子节点，删除失败！");
				return responseDataModel;
			}
			nodeService.deleteNode((SysUser)session.getAttribute(Constants.SESSION_USER),id);
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		}catch(Exception e){
			Log.e(TAG, "*** deleteNode throw Exception:"+e);
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	/**
	 * 拖拽分类排序
	 * @author fangg
	 * 2017年5月13日 下午5:02:30
	 * @param session
	 * @param model
	 * @param nodeId
	 * @param targetNodeId
	 * @param moveType
	 * @return
	 */
	@RequestMapping("/moveNode")
	@ResponseBody
	public ResponseDataModel moveNode(HttpSession session,NodeModel model,Integer nodeId,Integer targetNodeId,String moveType){
		try {
			Log.i(TAG, "*** moveNode enter ***");
			String msg = nodeService.moveNode((SysUser)session.getAttribute(Constants.SESSION_USER), nodeId,targetNodeId,moveType);
			if(StringUtils.isNotEmpty(msg)){
				return new ResponseDataModel(ResponseDataModel.NOT_SUCCESS, msg);
			}
			return ResponseDataModel.RESPONSE_SUCCESS_DATA_MODEL;
		} catch (Exception e) {
			Log.e(TAG, "*** moveNode throw Exception:"+e);
			return ResponseDataModel.RESPONSE_ERROR_DATA_MODEL;
		}
	}
	
	
	

}
