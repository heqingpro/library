/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.37 : Database - library
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`library` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `library`;

/*Table structure for table `access_count` */

DROP TABLE IF EXISTS `access_count`;

CREATE TABLE `access_count` (
  `id` varchar(20) NOT NULL COMMENT '格式yyyy-mm-dd，且唯一',
  `count` int(11) DEFAULT NULL COMMENT '记录id日的访问次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `access_count` */

insert  into `access_count`(`id`,`count`) values ('2018-03-28',0),('2018-03-29',10),('2018-03-30',20),('2018-03-31',30),('2018-04-01',40),('2018-04-02',50),('2018-04-03',60),('2018-04-04',70),('2018-04-05',80),('2018-04-06',90),('2018-04-07',100),('2018-04-08',110),('2018-04-09',120),('2018-04-10',130),('2018-04-11',140),('2018-04-12',150),('2018-04-13',130),('2018-04-14',160),('2018-06-05',180),('2018-06-06',190),('2018-06-07',200),('2018-06-08',190),('2018-06-09',180),('2018-06-10',230),('2018-06-11',240),('2018-06-12',250),('2018-06-13',200),('2018-06-14',160),('2018-06-15',180),('2018-06-17',170),('2018-06-18',165),('2018-06-19',176),('2018-07-06',36),('2018-07-07',53),('2018-07-08',54),('2018-07-09',65),('2018-07-10',37),('2018-07-11',85),('2018-07-12',47),('2018-07-13',122),('2018-07-14',143),('2018-07-15',67),('2018-07-16',56),('2018-07-17',47),('2018-07-18',62),('2018-07-19',41),('2018-07-20',55);

/*Table structure for table `access_record` */

DROP TABLE IF EXISTS `access_record`;

CREATE TABLE `access_record` (
  `id` varchar(32) NOT NULL,
  `url` varchar(500) DEFAULT NULL COMMENT '访问地址',
  `access_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '访问时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `access_record` */

insert  into `access_record`(`id`,`url`,`access_time`) values ('0cb004a0555c47878f6fa50e7a6cc53a','/LibraryService/book/listBooks','2018-04-27 17:56:26'),('0f9e6decdc244447b96bb760e91f8120','/LibraryService/book/listBooks','2018-04-27 18:44:13'),('25d79837b1c5418d8a35f15e41f7703f','/LibraryService/book/listBooks','2018-04-27 14:34:11'),('3678ab9edf2f4a56abc8617d7e974927','/LibraryService/book/listBooks','2018-04-27 17:57:45'),('4b62094503a04a11ad889e30a29f8aaf','/LibraryService/book/listBooks','2018-04-27 17:56:55'),('8d21a61470024668b05cd7596dcd1395','/LibraryService/book/listBooks','2018-04-27 17:56:29'),('946493afb881493295dbf57229ffd7ac','/LibraryService/book/listBooks','2018-04-27 17:56:28'),('977a354bdccb4e4bb3086e9d7bc355af','/LibraryService/book/listBooks','2018-04-27 17:57:46'),('9b7344bc15d44b208baf7ea641b19806','/LibraryService/book/listBooks','2018-04-27 18:46:46'),('a41d863ddd1d4b00b399e4752a661a78','/LibraryService/book/listBooks','2018-04-27 17:57:45'),('b9398ad93cc4483294a267a7a2c6a114','/LibraryService/book/listBooks','2018-04-27 17:56:29'),('c4dc5350938a4273bb4feb9f98e6abe6','/LibraryService/book/listBooks','2018-04-27 17:57:44'),('d3dc2779c26a4fd0897a6b4e02d91691','/LibraryService/book/getBookDetail','2018-04-27 14:32:51'),('d8ec517ce5ce46ea8a5fd871b40efc6c','/LibraryService/book/listBooks','2018-04-27 17:56:28'),('dcaa94ece7854b45aa5dfd2f7e75d795','/LibraryService/book/listBooks','2018-04-27 17:56:56'),('de04fbe334be4c47b1faf23388068d52','/LibraryService/book/listBooks','2018-04-27 17:57:46'),('ffc38f268cca45df9b874140b5737073','/LibraryService/book/listBooks','2018-04-27 17:56:56');

/*Table structure for table `angle` */

DROP TABLE IF EXISTS `angle`;

CREATE TABLE `angle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `imageName` varchar(255) DEFAULT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `uniqueImageName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `angle` */

insert  into `angle`(`id`,`name`,`imageName`,`imagePath`,`uniqueImageName`) values (1,'汉语','','nodeImages\\1496306098198.jpg','1496306098198.jpg'),(3,'维吾尔族','','nodeImages\\1496315021583.jpg','1496315021583.jpg'),(4,'哈萨克语','','nodeImages\\1496366409617.jpg','1496366409617.jpg');

/*Table structure for table `app` */

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '专区名',
  `appName` varchar(255) NOT NULL COMMENT '专区名称',
  `rootNode_Id` int(11) NOT NULL DEFAULT '0',
  `enName` varchar(255) NOT NULL COMMENT '专区英文名',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `addTime` varchar(45) DEFAULT NULL COMMENT '创建时间',
  `modifyTime` varchar(45) DEFAULT NULL COMMENT '编辑时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='专区应用表';

/*Data for the table `app` */

insert  into `app`(`id`,`appName`,`rootNode_Id`,`enName`,`remark`,`addTime`,`modifyTime`) values (00000000005,'连环画 ',1,'LHH','提供图片，音频资源','2017-05-02 19:29:09','2017-08-05 18:16:17'),(00000000006,'三秦书屋',37,'SQSW','提供电子文字图书','2017-05-17 14:00:26','2017-09-26 17:52:32'),(00000000010,'内容提供商1',10,'NRTGS1,NRDGS1','内容提供商1','2018-09-30 11:51:04',NULL);

/*Table structure for table `app_to_node` */

DROP TABLE IF EXISTS `app_to_node`;

CREATE TABLE `app_to_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '专区应用id',
  `node_id` int(11) NOT NULL COMMENT '栏目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `app_to_node` */

/*Table structure for table `app_user` */

DROP TABLE IF EXISTS `app_user`;

CREATE TABLE `app_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL COMMENT '终端用户名',
  `user_uid` varchar(11) DEFAULT NULL COMMENT '终端用户的终端id',
  `ca_id` varchar(255) NOT NULL COMMENT '终端用户的CA卡号',
  PRIMARY KEY (`id`,`ca_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `app_user` */

insert  into `app_user`(`id`,`user_name`,`user_uid`,`ca_id`) values (1,'zhangsan','001','UID_001'),(2,'lisi','002','UID_002'),(8,NULL,NULL,'926612'),(9,NULL,NULL,'123'),(10,NULL,NULL,'123456'),(11,NULL,NULL,'123456'),(12,NULL,NULL,'12345645'),(13,NULL,NULL,'12345645'),(14,NULL,NULL,'1234564590'),(15,NULL,NULL,'1234564590'),(16,NULL,NULL,'123456451212'),(17,NULL,NULL,'123456451212'),(18,NULL,NULL,'123456451245'),(19,NULL,NULL,'123456451245'),(20,NULL,NULL,'123456451234'),(21,NULL,NULL,'123456451234'),(22,NULL,NULL,'45678910'),(23,NULL,NULL,'45678910');

/*Table structure for table `appuser_to_entry` */

DROP TABLE IF EXISTS `appuser_to_entry`;

CREATE TABLE `appuser_to_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appUser_id` varchar(50) DEFAULT NULL COMMENT '终端用户id',
  `entry_id` int(11) DEFAULT NULL COMMENT '素材id',
  `recordType` int(11) DEFAULT NULL COMMENT '记录用户行为类型（0：我想做，1：我会做，2：我做过，3：历史记录:4）',
  `recordTime` varchar(50) DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `appuser_to_entry` */

insert  into `appuser_to_entry`(`id`,`appUser_id`,`entry_id`,`recordType`,`recordTime`) values (1,'1',2,1,NULL),(2,'2',3,1,NULL),(3,'2',3,1,NULL),(4,'1',4,1,NULL),(5,'3',2,2,NULL);

/*Table structure for table `bookmark` */

DROP TABLE IF EXISTS `bookmark`;

CREATE TABLE `bookmark` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stb_id` varchar(100) DEFAULT NULL COMMENT '终端用户唯一标识',
  `entry_id` int(11) DEFAULT NULL COMMENT '图书唯一id',
  `flag` int(11) DEFAULT '0' COMMENT '用于区别文字图书还是音频图书，0是文字，1是音频',
  `font_size` int(11) DEFAULT '24' COMMENT '文字图书的默认字体大小',
  `total_pages` int(11) DEFAULT '0' COMMENT '文字图书总页数，该值由终端改变',
  `current_page` int(11) DEFAULT '0' COMMENT '文字图书当前阅读页码，该值由终端改变',
  `total_seconds` int(11) DEFAULT '0' COMMENT '音频图书总时长，该值由终端改变，单位是秒',
  `current_seconds` int(11) DEFAULT '0' COMMENT '音频图书当前阅读时间点，该值由终端改变，单位是秒',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `bookmark` */

insert  into `bookmark`(`id`,`stb_id`,`entry_id`,`flag`,`font_size`,`total_pages`,`current_page`,`total_seconds`,`current_seconds`) values (3,'xuyan',74,0,18,1,0,0,0),(4,'xuyan',75,1,10,0,0,210,66);

/*Table structure for table `entry` */

DROP TABLE IF EXISTS `entry`;

CREATE TABLE `entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entryType_id` int(11) DEFAULT NULL COMMENT '系列id(连环画单行书或者套书id)',
  `attachInfo_id` int(11) DEFAULT NULL,
  `lang_id` int(11) DEFAULT NULL,
  `app_id` int(11) DEFAULT NULL COMMENT '所属应用id',
  `title` varchar(200) NOT NULL COMMENT '素材标题',
  `short_name` varchar(200) NOT NULL COMMENT '标题的英文简拼',
  `author` varchar(45) DEFAULT NULL COMMENT '作者',
  `description` mediumtext COMMENT '内容描述',
  `editor` varchar(50) DEFAULT NULL COMMENT '编者',
  `pub_org` longtext COMMENT '出版机构',
  `original_author` varchar(255) DEFAULT NULL COMMENT '原著作者',
  `years_id` int(11) DEFAULT NULL COMMENT '年代id: 1古代 2近现代 3现当代',
  `uid` int(11) DEFAULT NULL COMMENT '第三方煤资id',
  `global_guid` varchar(11) DEFAULT NULL COMMENT '图书唯一编码',
  `page_count` int(11) DEFAULT NULL COMMENT '页数',
  `width` int(11) DEFAULT NULL COMMENT '宽度',
  `height` int(11) DEFAULT NULL COMMENT '高度',
  `path` int(11) DEFAULT NULL COMMENT '文件路径',
  `format_type` int(11) DEFAULT NULL COMMENT '格式:1无声 2有声',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '图书封面',
  `cover_image_id` int(11) DEFAULT NULL COMMENT '封面海报id',
  `cover_thumbNail_id` int(11) DEFAULT NULL COMMENT '封面小图id',
  `cover_thumbNail` varchar(255) DEFAULT NULL COMMENT '封面小图地址',
  `content_path` varchar(255) DEFAULT NULL COMMENT '内容文件保存的地方，txt文件',
  `content` longtext COMMENT '文本类图书的内容',
  `save_path` varchar(255) DEFAULT NULL COMMENT '图书保存路径',
  `edition_type` int(11) DEFAULT NULL COMMENT '版本类型',
  `is_prize` int(11) DEFAULT NULL COMMENT '是否为获奖作品',
  `add_time` varchar(255) DEFAULT NULL COMMENT '添加时间',
  `modify_time` varchar(255) DEFAULT NULL COMMENT '最新更新时间',
  `operate_userId` int(11) DEFAULT NULL COMMENT '修改素材信息的操作人id',
  `audio_path` varchar(255) DEFAULT NULL,
  `rank_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5C30872C2B15021` (`attachInfo_id`),
  KEY `FK5C308723E93CF7D` (`entryType_id`),
  KEY `FK5C308729A8F7A02` (`lang_id`),
  CONSTRAINT `FK5C308723E93CF7D` FOREIGN KEY (`entryType_id`) REFERENCES `entry_type` (`id`),
  CONSTRAINT `FK5C308729A8F7A02` FOREIGN KEY (`lang_id`) REFERENCES `angle` (`id`),
  CONSTRAINT `FK5C30872C2B15021` FOREIGN KEY (`attachInfo_id`) REFERENCES `entry_attach` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4;

/*Data for the table `entry` */

insert  into `entry`(`id`,`entryType_id`,`attachInfo_id`,`lang_id`,`app_id`,`title`,`short_name`,`author`,`description`,`editor`,`pub_org`,`original_author`,`years_id`,`uid`,`global_guid`,`page_count`,`width`,`height`,`path`,`format_type`,`cover_image`,`cover_image_id`,`cover_thumbNail_id`,`cover_thumbNail`,`content_path`,`content`,`save_path`,`edition_type`,`is_prize`,`add_time`,`modify_time`,`operate_userId`,`audio_path`,`rank_number`) values (74,25,73,1,6,'中美贸易大战即将拉开帷幕！','ZMMYDZJQLKWM！','吕超','中美贸易大战即将拉开帷幕！','莫言','新华社','习近平',3,NULL,NULL,222,111,111,NULL,1,'libaray/libraryImages/1524106159427.jpg',127,128,'libaray/libraryImages/1524106159471.jpg','libaray/libraryTexts/1524106159474451485.js',NULL,NULL,NULL,0,'2018-04-19 10:49:19',NULL,1,'libaray/libraryAudios/1524106159664.mp3',1),(75,24,74,1,6,'山海经','SHJ','李清照','山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！','吕超','湖南大学','伯益',1,NULL,NULL,344,444,444,NULL,2,'libaray/libraryImages/1524118447025.gif',129,130,'libaray/libraryImages/1524118447077.gif','libaray/libraryTexts/1524118447153192760.js',NULL,NULL,NULL,0,'2018-04-19 14:14:06','2018-04-20 11:04:16',1,'libaray/libraryAudios/1524118447505.mp3',2),(76,25,75,1,6,'qedaed','qedaed','adad','rgbdfbdfb','adadsa','adad','werqedq',1,NULL,NULL,10,12,12,NULL,1,'libaray/libraryImages/1538194408718.jpg',131,132,'libaray/libraryImages/1538194408772.jpg','libaray/libraryTexts/1538194408782735980.js',NULL,NULL,NULL,0,'2018-09-29 12:13:28',NULL,1,'libaray/libraryAudios/1538194408855.mp3',3);

/*Table structure for table `entry_attach` */

DROP TABLE IF EXISTS `entry_attach`;

CREATE TABLE `entry_attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entryInfo_id` int(11) DEFAULT NULL COMMENT '图书id',
  `browse_count` int(11) DEFAULT '0' COMMENT '浏览次数',
  `download_count` int(11) DEFAULT '0' COMMENT '下载次数',
  `sort_value` int(11) DEFAULT NULL COMMENT '排序值',
  `audit_status` int(11) DEFAULT '0' COMMENT '0：未审核；1：审核通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4;

/*Data for the table `entry_attach` */

insert  into `entry_attach`(`id`,`entryInfo_id`,`browse_count`,`download_count`,`sort_value`,`audit_status`) values (63,64,NULL,NULL,64,NULL),(64,65,NULL,NULL,65,NULL),(65,66,NULL,NULL,66,NULL),(66,67,NULL,NULL,67,NULL),(67,68,NULL,NULL,68,NULL),(68,69,NULL,NULL,69,NULL),(69,70,NULL,NULL,70,NULL),(70,71,NULL,NULL,71,NULL),(71,72,NULL,NULL,72,NULL),(72,73,NULL,NULL,73,NULL),(73,74,NULL,NULL,74,NULL),(74,75,NULL,NULL,75,NULL),(75,76,NULL,NULL,76,NULL);

/*Table structure for table `entry_audio` */

DROP TABLE IF EXISTS `entry_audio`;

CREATE TABLE `entry_audio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entryInfo_id` int(11) DEFAULT NULL,
  `audio_name` varchar(255) DEFAULT NULL COMMENT '图片名',
  `unique_name` varchar(45) DEFAULT NULL COMMENT '图片唯一名',
  `audio_path` varchar(45) DEFAULT NULL COMMENT '图片路径',
  `audio_type` varchar(45) DEFAULT NULL COMMENT '图片类型（图片后缀名）',
  `audio_size` int(45) DEFAULT NULL COMMENT '图片大小',
  `audio_content` longblob COMMENT '图片内容',
  `audio_width` int(11) DEFAULT NULL COMMENT '图片宽度',
  `audio_Height` int(11) DEFAULT NULL COMMENT '图片高度',
  `add_time` varchar(45) DEFAULT NULL COMMENT '最后修改时间',
  `operate_userId` int(11) DEFAULT NULL COMMENT '操作管理员id',
  PRIMARY KEY (`id`),
  KEY `FKCF8D69C9F2424AAF` (`entryInfo_id`),
  CONSTRAINT `FKCF8D69C9F2424AAF` FOREIGN KEY (`entryInfo_id`) REFERENCES `entry` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='素材海报';

/*Data for the table `entry_audio` */

insert  into `entry_audio`(`id`,`entryInfo_id`,`audio_name`,`unique_name`,`audio_path`,`audio_type`,`audio_size`,`audio_content`,`audio_width`,`audio_Height`,`add_time`,`operate_userId`) values (24,74,'周笔畅 - 最美的期待.mp3','1524106159664.mp3','libaray/libraryAudios/1524106159664.mp3','mp3',NULL,NULL,NULL,NULL,'2018-04-19 10:49:19',1),(25,75,'周笔畅 - 最美的期待.mp3','1524118447505.mp3','libaray/libraryAudios/1524118447505.mp3','mp3',NULL,NULL,NULL,NULL,'2018-04-19 14:14:07',1),(26,76,'周笔畅 - 最美的期待.mp3','1538194408855.mp3','libaray/libraryAudios/1538194408855.mp3','mp3',NULL,NULL,NULL,NULL,'2018-09-29 12:13:28',1);

/*Table structure for table `entry_file` */

DROP TABLE IF EXISTS `entry_file`;

CREATE TABLE `entry_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_time` varchar(255) DEFAULT NULL,
  `file_content` mediumblob,
  `file_height` int(11) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `file_size` varchar(255) DEFAULT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  `file_width` int(11) DEFAULT NULL,
  `operate_userId` int(11) DEFAULT NULL,
  `unique_name` varchar(255) DEFAULT NULL,
  `entryInfo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8AD51D29F2424AAF` (`entryInfo_id`),
  CONSTRAINT `FK8AD51D29F2424AAF` FOREIGN KEY (`entryInfo_id`) REFERENCES `entry` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;

/*Data for the table `entry_file` */

insert  into `entry_file`(`id`,`add_time`,`file_content`,`file_height`,`file_name`,`file_path`,`file_size`,`file_type`,`file_width`,`operate_userId`,`unique_name`,`entryInfo_id`) values (32,'2018-04-19 10:49:19',NULL,NULL,'柳林风声.txt','libaray/libraryTexts/1524106159474451485.js',NULL,'txt',NULL,1,'1524106159474451485.js',74),(33,'2018-04-19 14:14:07',NULL,NULL,'TS00000102.epub','libaray/libraryTexts/1524118447153192760.js',NULL,'mp3',NULL,1,'1524118447153192760.js',75),(34,'2018-09-29 12:13:28',NULL,NULL,'book.txt','libaray/libraryTexts/1538194408782735980.js',NULL,'txt',NULL,1,'1538194408782735980.js',76);

/*Table structure for table `entry_image` */

DROP TABLE IF EXISTS `entry_image`;

CREATE TABLE `entry_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entryInfo_id` int(11) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL COMMENT '图片名',
  `unique_name` varchar(45) DEFAULT NULL COMMENT '图片唯一名',
  `image_path` varchar(45) DEFAULT NULL COMMENT '图片路径',
  `image_type` varchar(45) DEFAULT NULL COMMENT '图片类型（图片后缀名）',
  `image_size` int(45) DEFAULT NULL COMMENT '图片大小',
  `image_content` longblob COMMENT '图片内容',
  `image_width` int(11) DEFAULT NULL COMMENT '图片宽度',
  `image_height` int(11) DEFAULT NULL COMMENT '图片高度',
  `position` int(11) DEFAULT NULL,
  `add_time` varchar(45) DEFAULT NULL COMMENT '最后修改时间',
  `operate_userId` int(11) DEFAULT NULL COMMENT '操作管理员id',
  PRIMARY KEY (`id`),
  KEY `FKCFFA774EF2424AAF` (`entryInfo_id`),
  CONSTRAINT `FKCFFA774EF2424AAF` FOREIGN KEY (`entryInfo_id`) REFERENCES `entry` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8mb4 COMMENT='素材海报';

/*Data for the table `entry_image` */

insert  into `entry_image`(`id`,`entryInfo_id`,`image_name`,`unique_name`,`image_path`,`image_type`,`image_size`,`image_content`,`image_width`,`image_height`,`position`,`add_time`,`operate_userId`) values (127,74,'{6934D7AB-E50F-43EB-BF74-2C6AAB2886D7}.jpg','1524106159427.jpg','libaray/libraryImages/1524106159427.jpg','jpg',NULL,NULL,NULL,NULL,0,'2018-04-19 10:49:19',1),(128,74,'{6FA2ACA6-1AFA-4891-BA0C-374EFD0854A3}_副本.jpg','1524106159471.jpg','libaray/libraryImages/1524106159471.jpg','jpg',NULL,NULL,NULL,NULL,0,'2018-04-19 10:49:19',1),(129,75,'11111111111111gif.gif','1524118447025.gif','libaray/libraryImages/1524118447025.gif','gif',NULL,NULL,NULL,NULL,0,'2018-04-19 14:14:07',1),(130,75,'QQ图片20171208140749.gif','1524118447077.gif','libaray/libraryImages/1524118447077.gif','gif',NULL,NULL,NULL,NULL,0,'2018-04-19 14:14:07',1),(131,76,'boy.jpg','1538194408718.jpg','libaray/libraryImages/1538194408718.jpg','jpg',NULL,NULL,NULL,NULL,0,'2018-09-29 12:13:28',1),(132,76,'c685d3705b1c6a3afd10a5fedde05fa1.jpg','1538194408772.jpg','libaray/libraryImages/1538194408772.jpg','jpg',NULL,NULL,NULL,NULL,0,'2018-09-29 12:13:28',1);

/*Table structure for table `entry_to_node` */

DROP TABLE IF EXISTS `entry_to_node`;

CREATE TABLE `entry_to_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entry_id` int(11) NOT NULL COMMENT '图书id',
  `node_id` int(11) NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=273 DEFAULT CHARSET=utf8mb4;

/*Data for the table `entry_to_node` */

insert  into `entry_to_node`(`id`,`entry_id`,`node_id`) values (1,1,3),(2,1,4),(3,1,5),(4,1,6),(5,1,7),(6,2,3),(7,2,4),(8,3,5),(9,3,4),(10,5,47),(255,64,56),(263,74,49),(265,75,69),(266,75,47),(267,75,49),(268,75,56),(269,75,62),(270,75,63),(271,75,71),(272,76,49);

/*Table structure for table `entry_type` */

DROP TABLE IF EXISTS `entry_type`;

CREATE TABLE `entry_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imageName` varchar(50) DEFAULT NULL COMMENT '海报',
  `uniqueImageName` varchar(255) DEFAULT NULL COMMENT '海报唯一名称',
  `imagePath` varchar(255) DEFAULT NULL COMMENT '海报路径',
  `typeName` varchar(255) DEFAULT NULL COMMENT '类型名',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `addTime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

/*Data for the table `entry_type` */

insert  into `entry_type`(`id`,`imageName`,`uniqueImageName`,`imagePath`,`typeName`,`remark`,`addTime`) values (12,NULL,NULL,NULL,'单行书','danxbook','2017-08-02 16:50:35'),(13,NULL,NULL,NULL,'经典成语故事','经典成语故事','2017-08-02 16:51:58'),(14,NULL,NULL,NULL,'史记故事','史记故事','2017-08-02 16:52:14'),(15,NULL,NULL,NULL,'水浒传','水浒传','2017-08-02 16:52:24'),(16,NULL,NULL,NULL,'西游故事','西游故事','2017-08-02 16:52:33'),(17,NULL,NULL,NULL,'八仙的传说','八仙的传说','2017-08-02 16:52:42'),(18,NULL,NULL,NULL,'聊斋故事','聊斋故事','2017-08-02 16:52:53'),(19,NULL,NULL,NULL,'平凡的世界','平凡的世界','2017-08-02 16:53:01'),(20,NULL,NULL,NULL,'沸腾的群山','沸腾的群山','2017-08-02 16:53:09'),(21,NULL,NULL,NULL,'白鹿原','白鹿原','2017-08-02 16:53:18'),(22,NULL,NULL,NULL,'封神演义','封神演义','2017-08-02 16:53:27'),(23,'','1502277411504.gif','libraryImages\\1502277411504.gif','岳飞传','历史人物传记','2017-08-09 19:16:51'),(24,'','1502277423177.jpg','libraryImages\\1502277423177.jpg','西厢记','古典文学','2017-08-09 19:17:03'),(25,'','1502277447673.gif','libraryImages\\1502277447673.gif','孔雀东南飞','民间故事','2017-08-09 19:17:27'),(26,'','1502277467124.gif','libraryImages\\1502277467124.gif','飞龙传','飞龙转','2017-08-09 19:17:47');

/*Table structure for table `es_entry` */

DROP TABLE IF EXISTS `es_entry`;

CREATE TABLE `es_entry` (
  `id` int(11) NOT NULL,
  `add_time` varchar(255) DEFAULT NULL,
  `app_id` int(11) DEFAULT NULL,
  `attach_info_id` int(11) DEFAULT NULL,
  `audio_path` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `content_path` varchar(255) DEFAULT NULL,
  `cover_image` varchar(255) DEFAULT NULL,
  `cover_image_id` int(11) DEFAULT NULL,
  `cover_thumb_nail` varchar(255) DEFAULT NULL,
  `cover_thumb_nail_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `edition_type` int(11) DEFAULT NULL,
  `editor` varchar(255) DEFAULT NULL,
  `entry_type_id` int(11) DEFAULT NULL,
  `format_type` varchar(255) DEFAULT NULL,
  `global_guid` varchar(255) DEFAULT NULL,
  `height` int(255) DEFAULT NULL,
  `is_prize` int(11) DEFAULT NULL,
  `lang_id` int(11) DEFAULT NULL,
  `modify_time` varchar(255) DEFAULT NULL,
  `operate_user_id` int(11) DEFAULT NULL,
  `original_author` varchar(255) DEFAULT NULL,
  `page_count` int(11) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `pub_org` varchar(255) DEFAULT NULL,
  `save_path` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `uid` varchar(255) DEFAULT NULL,
  `width` int(255) DEFAULT NULL,
  `years_id` int(11) DEFAULT NULL,
  `rank_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `es_entry` */

insert  into `es_entry`(`id`,`add_time`,`app_id`,`attach_info_id`,`audio_path`,`author`,`content`,`content_path`,`cover_image`,`cover_image_id`,`cover_thumb_nail`,`cover_thumb_nail_id`,`description`,`edition_type`,`editor`,`entry_type_id`,`format_type`,`global_guid`,`height`,`is_prize`,`lang_id`,`modify_time`,`operate_user_id`,`original_author`,`page_count`,`path`,`pub_org`,`save_path`,`short_name`,`title`,`uid`,`width`,`years_id`,`rank_number`) values (74,'2018-04-19 10:49:19',6,73,'libaray/libraryAudios/1524106159664.mp3','吕超',NULL,'libaray/libraryTexts/1524106159474451485.js','libaray/libraryImages/1524106159427.jpg',127,'libaray/libraryImages/1524106159471.jpg',128,'中美贸易大战即将拉开帷幕！',NULL,'莫言',25,'1',NULL,111,0,1,NULL,1,'习近平',222,NULL,'新华社',NULL,'ZMMYDZJQLKWM！','中美贸易大战即将拉开帷幕！',NULL,111,3,1),(75,'2018-04-19 14:14:06',6,74,'libaray/libraryAudios/1524118447505.mp3','李清照',NULL,'libaray/libraryTexts/1524118447153192760.js','libaray/libraryImages/1524118447025.gif',129,'libaray/libraryImages/1524118447077.gif',130,'山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！山海经是中国名著！',NULL,'吕超',24,'2',NULL,444,0,1,'2018-04-20 11:04:16',1,'伯益',344,NULL,'湖南大学',NULL,'SHJ','山海经',NULL,444,1,1),(76,'2018-09-29 12:13:28',6,75,'libaray/libraryAudios/1538194408855.mp3','adad',NULL,'libaray/libraryTexts/1538194408782735980.js','libaray/libraryImages/1538194408718.jpg',131,'libaray/libraryImages/1538194408772.jpg',132,'rgbdfbdfb',NULL,'adadsa',25,'1',NULL,12,0,1,NULL,1,'werqedq',10,NULL,'adad',NULL,'qedaed','qedaed',NULL,12,1,3);

/*Table structure for table `node` */

DROP TABLE IF EXISTS `node`;

CREATE TABLE `node` (
  `id` int(11) unsigned zerofill NOT NULL,
  `parentNode_id` int(11) DEFAULT NULL COMMENT '父节点id',
  `appInfo_id` int(11) DEFAULT NULL COMMENT '节点所属的应用的id',
  `thumbnail_id` int(11) DEFAULT NULL COMMENT '栏目缩略图',
  `nodeImage_id` int(11) DEFAULT NULL COMMENT '栏目海报图片',
  `nodeName` varchar(45) DEFAULT NULL COMMENT '节点名称_中文名',
  `enName` varchar(45) DEFAULT NULL COMMENT '节点对应的文件夹名(英文名)',
  `nodeType` int(255) DEFAULT NULL COMMENT '节点类型(推荐位：1，普通栏目：0)',
  `hasEntry` int(11) DEFAULT NULL COMMENT '是否管理素材(1:是，0：否)',
  `isHome` int(11) DEFAULT NULL COMMENT '是否是主页节点(1：是0:否)',
  `isParent` int(11) DEFAULT '0' COMMENT '1：是父节点;0：叶子节点（默认）',
  `isOnline` int(11) DEFAULT NULL COMMENT '是否是上线节点(1：是0:否)',
  `entryWord` varchar(45) DEFAULT NULL,
  `linkUrl` varchar(255) DEFAULT NULL COMMENT '外链接地址',
  `hasPoster` int(11) DEFAULT NULL COMMENT '是否含海报',
  `hasAlbum` int(11) DEFAULT NULL COMMENT '是否含相册',
  `hasVedio` int(11) DEFAULT NULL COMMENT '是否含视频',
  `hasLink` int(11) DEFAULT NULL COMMENT '是否含外链接',
  `hasText` int(11) DEFAULT NULL COMMENT '是否含文本',
  `messagePeriod` int(11) DEFAULT NULL COMMENT '消息更新周期，单位为天',
  `addtime` varchar(45) DEFAULT NULL,
  `modifyTime` varchar(45) DEFAULT NULL COMMENT '节点信息最后修改时间',
  `operateUserId` int(11) DEFAULT NULL COMMENT '修改节点信息的操作人id',
  `sortValue` int(11) DEFAULT '0' COMMENT '排序值，用于排序',
  `isReleased` int(11) DEFAULT NULL COMMENT '栏目发布状态,1:是,0:否',
  `angle_id` int(11) DEFAULT NULL COMMENT '栏目角标，映射到angle表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='栏目表';

/*Data for the table `node` */

insert  into `node`(`id`,`parentNode_id`,`appInfo_id`,`thumbnail_id`,`nodeImage_id`,`nodeName`,`enName`,`nodeType`,`hasEntry`,`isHome`,`isParent`,`isOnline`,`entryWord`,`linkUrl`,`hasPoster`,`hasAlbum`,`hasVedio`,`hasLink`,`hasText`,`messagePeriod`,`addtime`,`modifyTime`,`operateUserId`,`sortValue`,`isReleased`,`angle_id`) values (00000000005,0,5,NULL,NULL,'连环画 ','LHH',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-05-02 19:29:10','2017-08-05 18:16:17',NULL,NULL,NULL,NULL),(00000000006,0,6,NULL,NULL,'三秦书屋','SQSW',-1,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-05-17 14:00:26','2017-09-26 17:52:32',NULL,NULL,NULL,NULL),(00000000010,0,10,NULL,NULL,'内容提供商1','NRTGS1,NRDGS1',-1,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-30 11:51:04',NULL,NULL,NULL,NULL,NULL),(00000000047,6,6,18,17,'书刊推荐','SKTJ',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-05 11:14:45','2017-08-12 10:34:20',NULL,47,NULL,NULL),(00000000048,6,6,NULL,19,'本周排行','BZPX,BZPH',NULL,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-05 18:17:02','2017-08-12 10:34:45',NULL,48,NULL,NULL),(00000000049,6,6,NULL,20,'必读好书','BDHS',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-05 18:17:24','2017-08-12 10:34:56',NULL,49,NULL,NULL),(00000000051,1,5,22,21,'民间故事','MJGS',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:09:50',NULL,NULL,51,NULL,NULL),(00000000052,1,5,24,23,'刀马武侠','DMWX',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:10:10',NULL,NULL,52,NULL,NULL),(00000000056,6,6,NULL,NULL,'名人说书','MRYS,MRSS',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:11:11','2017-08-12 10:35:40',NULL,56,NULL,NULL),(00000000057,1,5,NULL,27,'连环画套数系列','LHHTSXL',NULL,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:12:10',NULL,NULL,57,NULL,NULL),(00000000062,6,6,NULL,NULL,'书林讲坛','SLJT',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-12 10:36:23',NULL,NULL,62,NULL,NULL),(00000000063,6,6,NULL,NULL,'图书馆介绍','TSGJS',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-08-12 10:36:39','2017-09-26 11:18:54',NULL,63,NULL,NULL),(00000000069,48,6,35,34,'名人传记','MRZJ',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-05 18:03:30',NULL,NULL,69,NULL,NULL),(00000000071,48,6,NULL,37,'经典名著','JDMZ',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-05 18:03:47',NULL,NULL,71,NULL,NULL),(00000000074,57,5,NULL,NULL,'werfw','werfw',NULL,NULL,NULL,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-29 15:49:51',NULL,NULL,74,NULL,NULL);

/*Table structure for table `node_to_entry` */

DROP TABLE IF EXISTS `node_to_entry`;

CREATE TABLE `node_to_entry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `node_id` int(11) DEFAULT NULL,
  `entry_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `node_to_entry` */

/*Table structure for table `nodeimage` */

DROP TABLE IF EXISTS `nodeimage`;

CREATE TABLE `nodeimage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nodeInfo_id` int(11) DEFAULT NULL COMMENT '栏目id',
  `imageName` varchar(255) DEFAULT NULL COMMENT '图片名',
  `uniqueName` varchar(45) DEFAULT NULL COMMENT '图片唯一名',
  `imagePath` varchar(255) DEFAULT NULL COMMENT '海报存储路径',
  `imageType` varchar(45) DEFAULT NULL COMMENT '图片类型（图片后缀名）',
  `imageSize` int(45) DEFAULT NULL COMMENT '图片大小',
  `imageContent` longblob COMMENT '图片内容',
  `imageWidth` int(11) DEFAULT NULL COMMENT '图片宽度',
  `imageHeight` int(11) DEFAULT NULL COMMENT '图片高度',
  `status` varchar(11) DEFAULT NULL COMMENT '图片属性（focus:焦点在时的图片，blur：失去焦点的图片）',
  `modifyTime` varchar(45) DEFAULT NULL COMMENT '最后修改时间',
  `operateUserId` int(11) DEFAULT NULL COMMENT '节点id',
  `deviceType` int(11) DEFAULT NULL COMMENT '终端类型：1:tv，2:app',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COMMENT='栏目海报表';

/*Data for the table `nodeimage` */

insert  into `nodeimage`(`id`,`nodeInfo_id`,`imageName`,`uniqueName`,`imagePath`,`imageType`,`imageSize`,`imageContent`,`imageWidth`,`imageHeight`,`status`,`modifyTime`,`operateUserId`,`deviceType`) values (8,31,'0c6fd9f2f340fa4b230b083ce2de1f6d.jpg','1494233412436.jpg','nodeImages\\1494233412436.jpg','jpg',NULL,NULL,NULL,NULL,NULL,'2017-05-08 16:50:12',NULL,0),(9,32,'{34B1522D-78DF-4875-A99E-603BACD8CC16}.gif','1494233462790.gif','nodeImages\\1494233462790.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-05-08 16:51:02',NULL,0),(10,33,'{F50B7B5D-8613-4821-A985-A6980776B8F7}.jpg','1494233502280.jpg','nodeImages\\1494233502280.jpg','jpg',NULL,NULL,NULL,NULL,NULL,'2017-05-08 16:51:42',NULL,0),(11,34,'{CF9D0723-54DA-448D-BDA0-66FEC68A6E79}.png','1494233553513.png','nodeImages\\1494233553513.png','png',NULL,NULL,NULL,NULL,NULL,'2017-05-08 16:52:33',NULL,0),(12,35,'{1C787CEA-CA32-4586-9348-3480F1E3D36D}.gif','1494233670500.gif','nodeImages\\1494233670500.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-05-08 16:54:30',NULL,0),(14,36,'{34B1522D-78DF-4875-A99E-603BACD8CC16}.gif','1494237103045.gif','nodeImages\\1494237103045.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-05-08 17:51:43',NULL,0),(15,32,'{1C787CEA-CA32-4586-9348-3480F1E3D36D}.gif','1496373633994.gif','nodeImages\\1496373633994.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-06-02 11:20:34',NULL,0),(16,32,'{1C787CEA-CA32-4586-9348-3480F1E3D36D}.gif','1496374233344.gif','nodeImages\\1496374233344.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-06-02 11:30:33',NULL,0),(17,47,'u=1129069461,2616850407&fm=117&gp=0 - 副本.jpg','1501902885553.jpg','libraryImages\\1501902885553.jpg','jpg',NULL,NULL,NULL,NULL,NULL,'2017-08-05 11:14:45',NULL,0),(18,47,'201612211121154981.gif','1501902885646.gif','libraryImages\\1501902885646.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-08-05 11:14:45',NULL,0),(19,48,'201606181658232244 - 副本.png','1501928222464.png','libraryImages\\1501928222464.png','png',NULL,NULL,NULL,NULL,NULL,'2017-08-05 18:17:02',NULL,0),(20,49,'u=1129069461,2616850407&fm=117&gp=0.jpg','1501928244980.jpg','libraryImages\\1501928244980.jpg','jpg',NULL,NULL,NULL,NULL,NULL,'2017-08-05 18:17:25',NULL,0),(21,51,'201605251341284212.gif','1502276990048.gif','libraryImages\\1502276990048.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:09:50',NULL,0),(22,51,'201605251341284212.gif','1502276990095.gif','libraryImages\\1502276990095.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:09:50',NULL,0),(23,52,'201608221133265582.gif','1502277010895.gif','libraryImages\\1502277010895.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:10:10',NULL,0),(24,52,'201609081054416679 - 副本 (2).gif','1502277010898.gif','libraryImages\\1502277010898.gif','gif',NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:10:10',NULL,0),(27,57,'u=1129069461,2616850407&fm=117&gp=0 - 副本.jpg','1502277130801.jpg','libraryImages\\1502277130801.jpg','jpg',NULL,NULL,NULL,NULL,NULL,'2017-08-09 19:12:10',NULL,0),(34,69,'986715820ccfc613af963de7bcddf4f2.jpg','1515146610934.jpg','libraryImages/1515146610934.jpg','jpg',NULL,NULL,NULL,NULL,NULL,'2018-01-05 18:03:31',NULL,0),(35,69,'899dedf31238abad63f37d12d96ab665.jpg','1515146611845.jpg','libraryImages/1515146611845.jpg','jpg',NULL,NULL,NULL,NULL,NULL,'2018-01-05 18:03:31',NULL,0),(37,71,'899dedf31238abad63f37d12d96ab665.jpg','1515146627460.jpg','libraryImages/1515146627460.jpg','jpg',NULL,NULL,NULL,NULL,NULL,'2018-01-05 18:03:47',NULL,0),(42,80,'50.png','1538272753522.png','libaray/libraryImages/1538272753522.png','png',NULL,NULL,NULL,NULL,NULL,'2018-09-30 09:59:13',NULL,0),(43,80,'64.png','1538272753533.png','libaray/libraryImages/1538272753533.png','png',NULL,NULL,NULL,NULL,NULL,'2018-09-30 09:59:13',NULL,0);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '模块名',
  `url` varchar(128) NOT NULL DEFAULT '' COMMENT '模块url',
  `parent_id` int(11) NOT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

/*Data for the table `permission` */

insert  into `permission`(`id`,`name`,`url`,`parent_id`) values (1,'系统管理','/systemManage',0),(2,'用户管理','/systemManage/userManage',1),(3,'角色管理','/systemManage/roleManage',1),(4,'日志管理','/systemManage/logManage',1),(5,'添加用户','/userManage/addSysUser',2),(6,'编辑用户','/userManage/editSysUser',2),(7,'删除用户','/userManage/deleteSysUser',2),(8,'设置用户角色','/userManage/setSysUserRole',2),(9,'重置密码','/userManage/resetPassword',2),(10,'添加角色','/roleManage/addRole',3),(11,'编辑角色','/roleManage/editRole',3),(12,'删除角色','/roleManage/deleteRole',3),(13,'删除日志','/logManage/deleteLog',4),(14,'导出日志','/logManage/exportLog',4),(15,'图书管理','/spManage',0),(16,'内容提供商管理','/spManage/appManage',15),(17,'添加内容提供商','/appManage/appAdd',16),(18,'修改内容提供商','/appManage/appEdit',16),(19,'删除内容提供商','/appManage/appDelete',16),(20,'分类管理','/spManage/nodeManage',15),(21,'添加分类','/nodeManage/addNode',20),(22,'编辑分类','/nodeManage/editNode',20),(23,'删除分类','/nodeManage/delNode',20),(24,'上/下线分类','/nodeManage/switchStatus',20),(25,'套书系列管理','/spManage/entryTypeManage',15),(26,'添加套书','/entryTypeManage/entryTypeAdd',25),(27,'修改套书','/entryTypeManage/entryTypeEdit',25),(28,'删除套书','/entryTypeManage/entryTypeDelete',25),(29,'书籍管理','/spManage/entryManage',15),(30,'添加书籍','/entryManage/entryAdd',29),(31,'修改书籍','/entryManage/editEntry',29),(32,'删除书籍','/entryManage/deleteEntry',29),(33,'编辑内容海报','/entryManage/entryAlbumsEdit',29),(34,'书籍详情','/entryManage/entryDetail',29),(39,'书籍列表','/nodeManage/entryList',20),(41,'语种管理','/spManage/angleManage',15),(42,'添加语种','/angleManage/angleAdd',41),(43,'编辑语种','/angleManage/angleEdit',41),(44,'删除语种','/angleManage/angleDelete',41),(47,'关联内容提供商','/userManage/editSysUserHasApp',2),(48,'重新创建图书索引','/entryManage/recreateBookIndex',29),(49,'','/entryManage/changeEntryRankNumber',29);

/*Table structure for table `remote_server` */

DROP TABLE IF EXISTS `remote_server`;

CREATE TABLE `remote_server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remote_server_name` varchar(100) NOT NULL COMMENT '服务器名称',
  `remote_ip` varchar(20) NOT NULL COMMENT '远程服务器IP地址',
  `remote_port` int(10) NOT NULL COMMENT '远程服务器端口',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `user_pass` varchar(50) NOT NULL COMMENT '登录密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `remote_server` */

insert  into `remote_server`(`id`,`remote_server_name`,`remote_ip`,`remote_port`,`user_name`,`user_pass`) values (3,'184服务器','192.168.18.184',21,'ipanelftp','ipanel');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `remark` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`remark`) values (1,'超级管理员',NULL),(2,'专区管理员',NULL);

/*Table structure for table `role_to_permission` */

DROP TABLE IF EXISTS `role_to_permission`;

CREATE TABLE `role_to_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7C700E0AC792CC05` (`role_id`),
  CONSTRAINT `FK7C700E0AC792CC05` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4;

/*Data for the table `role_to_permission` */

insert  into `role_to_permission`(`id`,`role_id`,`permission_id`) values (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(12,1,12),(13,1,13),(14,1,14),(15,1,15),(16,1,16),(17,1,17),(18,1,18),(19,1,19),(20,1,20),(21,1,21),(22,1,22),(23,1,23),(24,1,24),(25,1,25),(26,1,26),(27,1,27),(28,1,28),(29,1,29),(30,1,30),(31,1,31),(32,1,32),(33,1,33),(34,1,34),(37,1,37),(38,1,38),(39,1,39),(40,1,40),(41,1,41),(42,1,42),(43,1,43),(44,1,44),(45,1,45),(46,1,46),(47,1,47),(48,1,48),(49,1,49),(50,1,50),(51,2,2),(52,2,5),(53,2,6),(54,2,7),(55,2,8),(56,2,9),(57,2,40),(58,2,15),(59,2,16),(60,2,17),(61,2,18),(62,2,19),(63,2,20),(64,2,21),(65,2,22),(66,2,23),(67,2,24),(68,2,39),(69,2,25),(70,2,26),(71,2,27),(72,2,28),(73,2,29),(74,2,30),(75,2,31),(76,2,32),(77,2,33),(78,2,34),(79,2,41),(80,2,42),(81,2,43),(82,2,44);

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(128) DEFAULT NULL COMMENT '操作的模块名称',
  `operating_function` varchar(128) DEFAULT NULL COMMENT '操作的功能，如新建、修改、删除等',
  `sys_user_id` int(5) DEFAULT NULL COMMENT '操作的用户ID',
  `operating_desc` mediumtext COMMENT '操作日志描述',
  `operating_date` varchar(32) DEFAULT NULL COMMENT '日志产生时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `FK986862D2E5F4B0EA` (`sys_user_id`),
  CONSTRAINT `FK986862D2E5F4B0EA` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`module_name`,`operating_function`,`sys_user_id`,`operating_desc`,`operating_date`) values (1,'图书管理','编辑图书',1,'用户\"admin\"编辑了\"山海经\"图书','2018-04-20 11:04:16'),(2,'系统管理/用户管理','删除用户',1,'用户\"admin\"删除了\"范格格,fangg,test\"用户','2018-04-20 13:44:43'),(3,'系统管理/用户管理','添加用户',1,'用户\"admin\"添加了\"lvchao\"用户','2018-04-20 13:53:53'),(4,'系统管理/用户管理','用户修改',1,'用户\"admin\"修改了\"三秦书屋,\"内容提供商','2018-04-20 13:53:53'),(5,'系统管理/用户管理','内容提供商授权',5,'用户\"lvchao\"更改了\"lvchao\"的内容提供商','2018-04-20 13:54:21'),(6,'系统管理/用户管理','密码重置',1,'用户\"admin\"重置了\"lvchao\"的密码','2018-04-24 14:38:22'),(7,'专区内容提供商管理','添加内容提供商',1,'用户\"admin\"添加了\"2131\"内容提供商','2018-09-29 14:31:30'),(8,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-29 14:40:23'),(9,'分类管理','添加分类',1,'用户\"admin\"添加了\"werfw\"分类','2018-09-29 15:49:51'),(10,'分类管理','删除分类',1,'用户\"admin\"删除了\"1111,\"分类','2018-09-29 18:35:51'),(11,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-29 18:42:32'),(12,'分类管理','添加分类',1,'用户\"admin\"添加了\"2222\"分类','2018-09-29 18:42:55'),(13,'分类管理','删除分类',1,'用户\"admin\"删除了\"2222,\"分类','2018-09-29 18:43:12'),(14,'分类管理','删除分类',1,'用户\"admin\"删除了\"1111,\"分类','2018-09-29 18:43:17'),(15,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-29 18:46:00'),(16,'分类管理','删除分类',1,'用户\"admin\"删除了\"1111,\"分类','2018-09-29 18:46:07'),(17,'专区管理/内容提供商管理','删除内容提供商',1,'用户\"admin\"删除了\"2131\"内容提供商','2018-09-29 18:46:52'),(18,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 09:57:52'),(19,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-30 09:58:03'),(20,'分类管理','删除分类',1,'用户\"admin\"删除了\"1111,\"分类','2018-09-30 09:58:07'),(21,'分类管理','添加分类',1,'用户\"admin\"添加了\"吕超\"分类','2018-09-30 09:59:13'),(22,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 10:10:09'),(23,'分类管理','编辑分类',1,'用户\"admin\"编辑了\"我的早餐1\"分类','2018-09-30 10:31:53'),(24,'分类管理','删除分类',1,'用户\"admin\"删除了\"我的早餐1,\"分类','2018-09-30 10:32:10'),(25,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 10:52:23'),(26,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-30 10:52:31'),(27,'分类管理','删除分类',1,'用户\"admin\"删除了\"我的早餐,\"分类','2018-09-30 10:52:42'),(28,'分类管理','删除分类',1,'用户\"admin\"删除了\"1111,\"分类','2018-09-30 10:52:57'),(29,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 10:53:53'),(30,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-30 10:53:59'),(31,'分类管理','删除分类',1,'用户\"admin\"删除了\"我的早餐,\"分类','2018-09-30 10:54:17'),(32,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 11:03:18'),(33,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-30 11:03:24'),(34,'分类管理','编辑分类',1,'用户\"admin\"编辑了\"我的早餐11\"分类','2018-09-30 11:03:33'),(35,'分类管理','删除分类',1,'用户\"admin\"删除了\"1111,\"分类','2018-09-30 11:06:17'),(36,'分类管理','删除分类',1,'用户\"admin\"删除了\"我的早餐11,\"分类','2018-09-30 11:06:22'),(37,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 11:09:17'),(38,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-30 11:09:22'),(39,'分类管理','删除分类',1,'用户\"admin\"删除了\"我的早餐,\"分类','2018-09-30 11:09:35'),(40,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 11:15:29'),(41,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-30 11:15:35'),(42,'分类管理','添加分类',1,'用户\"admin\"添加了\"2222\"分类','2018-09-30 11:15:40'),(43,'分类管理','删除分类',1,'用户\"admin\"删除了\"2222,\"分类','2018-09-30 11:15:46'),(44,'分类管理','删除分类',1,'用户\"admin\"删除了\"1111,\"分类','2018-09-30 11:15:53'),(45,'分类管理','删除分类',1,'用户\"admin\"删除了\"我的早餐,\"分类','2018-09-30 11:15:58'),(46,'专区内容提供商管理','添加内容提供商',1,'用户\"admin\"添加了\"内容提供商1\"内容提供商','2018-09-30 11:21:45'),(47,'专区内容提供商管理','添加内容提供商',1,'用户\"admin\"添加了\"内容提供商12\"内容提供商','2018-09-30 11:28:01'),(48,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 11:35:17'),(49,'专区内容提供商管理','添加内容提供商',1,'用户\"admin\"添加了\"内容提供商1\"内容提供商','2018-09-30 11:51:04'),(50,'分类管理','添加分类',1,'用户\"admin\"添加了\"我的早餐\"分类','2018-09-30 11:56:39'),(51,'分类管理','添加分类',1,'用户\"admin\"添加了\"1111\"分类','2018-09-30 11:56:56'),(52,'分类管理','删除分类',1,'用户\"admin\"删除了\"1111,\"分类','2018-09-30 11:57:12'),(53,'分类管理','删除分类',1,'用户\"admin\"删除了\"我的早餐,\"分类','2018-09-30 11:57:16');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  `user_type` varchar(32) NOT NULL COMMENT 'admin-->系统管理员 normal-->普通管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`user_name`,`password`,`phone`,`email`,`create_time`,`user_type`) values (1,'admin','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,NULL,'superAdmin'),(5,'lvchao','96e79218965eb72c92a549dd5a330112','13345678901','123@qq.com','2018-04-20 13:53:53','admin');

/*Table structure for table `sys_user_app` */

DROP TABLE IF EXISTS `sys_user_app`;

CREATE TABLE `sys_user_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(11) NOT NULL,
  `app_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user_app` */

insert  into `sys_user_app`(`id`,`sys_user_id`,`app_id`) values (1,1,8),(12,2,8),(13,2,6),(14,2,5),(19,5,5),(20,5,6),(21,1,7),(22,1,8),(23,1,9),(24,1,10);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK660C5178C792CC05` (`role_id`),
  KEY `FK660C5178E5F4B0EA` (`sys_user_id`),
  CONSTRAINT `FK660C5178C792CC05` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK660C5178E5F4B0EA` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`sys_user_id`,`role_id`) values (1,1,1),(8,5,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
