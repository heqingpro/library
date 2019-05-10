package com.ipanel.web.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import cn.ipanel.util.properties.PropertiesUtil;

public class Constants {

	public static Timer defaultTimer;

	static {
		defaultTimer = new Timer();
	}
	
	public static final String PREFIX_ID_CHANNEL="channel-";
	public static final String PREFIX_ID_PROGRAM="program-";
	

	public static String ELASTICSEARCH_HOST=PropertiesUtil.getValue("config.properties", "elasticsearch.host", "127.0.0.1");
	public static String ELASTICSEARCH_PORT=PropertiesUtil.getValue("config.properties", "elasticsearch.port=", "9300");
	public static String ELASTICSEARCH_CLUSTER_NAME=PropertiesUtil.getValue("config.properties", "elasticsearch.cluster.name", "migration.ac.cn");
	
	
	public static String FILE_SERVER_IP=PropertiesUtil.getValue("config.properties", "fileServerIp", "127.0.0.1");
	
	public static String FILE_SERVER_PORT=PropertiesUtil.getValue("config.properties", "fileServerPort", "80");
	
	public static String FILE_SERVER_ROOT_PATH=PropertiesUtil.getValue("config.properties", "fileServerRootPath", "usr/local/nginx/html");
	
	public static String BOOK_IMAGE_PATH=PropertiesUtil.getValue("config.properties", "libraryImageDir", "library/libraryImages");
	
	public static String BOOK_ALBUM_PATH=PropertiesUtil.getValue("config.properties", "libraryAlbumDir", "library/libraryAlbums");
	
	public static String BOOK_TEXT_PATH=PropertiesUtil.getValue("config.properties", "libraryTextDir", "library/libraryTexts");
	
	public static String BOOK_AUDIO_PATH=PropertiesUtil.getValue("config.properties", "libraryAudioDir", "library/libraryAudios");
	
	public static String BOOK_ENCODING = PropertiesUtil.getValue("config.properties", "bookCharCode", "utf-8");
	
	//版本初始化为1
	public static Long VERSION_INIT_VALUE = 1L;
	
	public static String HFEPG_DATA_COMPRESS_NAME="hfepgDataCompress.zip";
	public static String HFEPG_DATA_RELEASE_PHYSICAL_PATH=PropertiesUtil.getValue("config.properties", "hfepg_data_release_physical_path", null);
	
	public static String CHANNEL_DATA_STORE_PATH="channelData";
	public static String CHANNEL_DATA_SUF_FILE_NAME="_channel";
	public static String CHANNEL_ALL_DATA_SUF_FILE_NAME="allChannel";
	public static String HFEPG_CHANNEL_RELEASE_VERSION_FILE_NAME="channelVersion.properties";
	
	public static String BOUQUET_DATA_STORE_PATH="bouquetData";
	public static String BOUQUET_ALL_DATA_SUF_FILE_NAME="allBouquet";
	public static String HFEPG_BOUQUET_RELEASE_VERSION_FILE_NAME="bouquetVersion.properties";
	
	public static String PROGRAM_DATA_STORE_PATH="programData";
	public static String PROGRAM_DATA_SUF_FILE_NAME="_program";
	public static String PROGRAM_ALL_DATA_SUF_FILE_NAME="allProgram";
	public static String PROGRAM_TODAY_DATA_SUF_FILE_NAME="_today";
	public static String PROGRAM_ALL_TWO_DAY_DATA_SUF_FILE_NAME="allTwoDay";
	public static String PROGRAM_ALL_TODAY_DATA_SUF_FILE_NAME="allToday";
	public static String HFEPG_PROGRAM_RELEASE_VERSION_FILE_NAME="programVersion.properties";
	
	public static String PROGRAM_REVIEW_DATA_STORE_PATH="programReviewData";
	public static String PROGRAM_REVIEW_ALL_DATA_SUF_FILE_NAME="_program";
	public static String HFEPG_PROGRAM_REVIEW_RELEASE_VERSION_FILE_NAME="programReviewVersion.properties";
	
	public static final String EPUB_BOOK_SUFFIX = ".html";
	
	public static String HFEPG_RELEASE_DATA_FILE_NAME=".json";
	
	public static String IMPORT_PROGRAM_FILE_ENCODING=PropertiesUtil.getValue("config.properties", "import_program_file_encoding", "/ipanel/nfsroot/ipeg");
	

	
	
	//系统路径的分隔符
//	public static String SYSTEM_FILE_SEPARATOR_TAG=System.getProperty("file.separator");
	public static String SYSTEM_FILE_SEPARATOR_TAG="/";
	
	//判断输入的是否是数字
	public static final String REGIX_ALL_NUMBER="^[0-9]*[1-9][0-9]*$";
	
	public static final String SESSION_USER_ID = "session_user_id";

	public static final String SESSION_USER_NAME = "session_user_name";

	public static final String SESSION_USER_TYPE = "session_user_type";

	public static final String USER_TYPE_SUPER_ADMIN = "superAdmin";

	public static final String USER_TYPE_NORMAL_ADMIN = "admin";
	
	public static final String SESSION_USER = "session_user";
	
	public static final String TEMP_DIR = "tempFile";//存放临时文件的目录
	

	public static Map<String, String> USER_TYPE_MAP = null;
	static {
		USER_TYPE_MAP = new HashMap<String, String>();
		USER_TYPE_MAP.put(USER_TYPE_SUPER_ADMIN, "超级管理员");
		USER_TYPE_MAP.put(USER_TYPE_NORMAL_ADMIN, "普通管理员");
	}

	
}
