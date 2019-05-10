package com.ipanel.web.book.pageModel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图书数据模型
 * @author fangg
 * 2017年5月10日 上午11:40:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryModel {
	
	private Integer page;
	
	private Integer rows;	
	
	private Integer id;
	
	private String ids;//批量删除使用
	
	private Integer nodeId; //分类下的图书列表
	
	private String title;//图书名称

	private String shortName;//长度200限制
	
	private String author;//作者
	
	private String desc;//简介
	
	private String pubOrg;// 出版机构
	
	private String editor; //编者
	
	private String originalAuthor; //原著作者
	
	private Integer yearsId; //  年代id: 1古代 2近现代 3现当代
	
	private Integer auditStatus;//状态   （分类状态：0未审核 1已审核）
	
	private Integer browseCount;//浏览次数
	
	private Integer downloadCount;//下载次数
	
	private String uid;//图书第三方id 
	
	private String global_guid;//图书唯一编码 
	
	private Integer pageCount; //页数
	
	private Integer width;//宽度
	
	private Integer height;//高度
	
	private Integer formatType; //格式:1无声 2有声,3 txt（三秦）
	
	private String coverImageUrl;//图书封面url
	
	private String coverThumbNailUrl;//图书封面小图url
	
	private String content;//文本类图书的内容
	
	private Integer editionType;//版本类型
	
	private Integer sortValue;//排序编号
	
	private Integer isPrize;//是否为获奖作品
	
	private Integer langId;//语种id
	
	private String langName;//图书绑定语种
	
	private Integer app_Id; //所属的内容提供商Id(appId这个单词获取不到参数值)
	
	private String appName; //所属的内容提供商名称
	
	private Integer entryTypeId;// 所属的图书套书信息
	
	private String entryTypeName;// 所属的图书套书信息
	
	private String nodeIds; // 所属的分类id集合
	
	private String nodeNames; //所属的分类名称集合
	
	private String addTime;//添加时间
	
	private String modifyTime; // 更新时间
	
	private String operateUserName;//操作人名字
	
	private Integer rankNumber;// 排序序号
	
	private Integer operation; // -1是降一位，1是升一位
	
	private EntryFileModel contentModel; //文件内容
	
	private List<EntryAudioModel> entryAudioModels;  //拥有的音频信息
	
	private List<EntryImageModel> entryImageModels;  //图片照片集合

}
