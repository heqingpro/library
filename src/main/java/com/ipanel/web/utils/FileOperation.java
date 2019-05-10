package com.ipanel.web.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.ipanel.web.define.SystemDefines;

import cn.ipanel.apps.commons.util.FileUtil;


/**
 * 文件服务器上的图片处理
 * @author  zhumi
 * @version  1.0.0
 * @2015年6月29日 下午2:11:04
 */
public class FileOperation {
	public static void writeAudios(String fileName,byte[] fileContent){
		String filePath=Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+Constants.BOOK_AUDIO_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+fileName;
		FileUtil.writeFile(filePath, fileContent); 
	}
	//写入文件到文件服务器上
	public static void writeAlbumImages(String imageName,byte[] fileContent){
		String filePath=Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+Constants.BOOK_ALBUM_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+imageName;
		FileUtil.writeFile(filePath, fileContent); 
	}
	//写入文件到文件服务器上
		public static void writeTextFile(String fileName,byte[] fileContent){
			String filePath=Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+Constants.BOOK_TEXT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+fileName;
			FileUtil.writeFile(filePath, fileContent); 
		}
	//写入图片到文件服务器上
	 public static void writeNodeImages(String imageName,byte[] imageContent){
			String filePath=Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+imageName;
			FileUtil.writeFile(filePath, imageContent); 
	 }
	 //
	 public static void writeFile(String filePath,byte[] fileContent){
		 	String rootPath = Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG;
		 	FileUtil.writeFile(rootPath+filePath, fileContent); 
	 }
	 //删除文件服务器上的文件
	 public static void deleteFile(String filePath){
		 	String rootPath = Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG;
		 	FileUtil.deleteFile(rootPath+filePath);
	 }
	 //删除文件服务器上的图片
	 public static void deleteNodeImages(String fileName){
		 String filePath=Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+fileName;
		 FileUtil.deleteFile(filePath);
	 }
	 //获得文件服务器上的图片路径
	 public static String getImageDirectory(String imageName){
			String fileDirectory=Constants.FILE_SERVER_ROOT_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+Constants.BOOK_IMAGE_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+imageName;
			return fileDirectory;
	 }
	/* //获得图片根路径(谷歌浏览器访问)
	 public static String  getImageRootPath(){
		 	String fileRootPath=SystemDefines.HTTP_PRE+ConfigUtil.getConfig("fileServerIP")
		 			+":"+ConfigUtil.getConfig("fileServerPort")
		 			+"/"+ConfigUtil.getConfig("fileFolder");
		 	return fileRootPath;
	 }*/
	// 获得图片根路径(谷歌浏览器访问)根据请求ip来适配
	 public static String  getImageRootPath(String requestIP){
		 	String fileRootPath=SystemDefines.HTTP_PRE+requestIP
		 			+":"+Constants.FILE_SERVER_PORT
		 			+"/"+Constants.BOOK_IMAGE_PATH;
		 	return fileRootPath;
	 }
	 //获得图片根路径(机顶盒访问)
	/* public static String  getImageRootPath_stb(){
		 	String fileRootPath=SystemDefines.HTTP_PRE+ConfigUtil.getConfig("fileServerIP_stb")
		 			+":"+ConfigUtil.getConfig("fileServerPort")
		 			+"/"+ConfigUtil.getConfig("fileFolder");
		 	return fileRootPath;
	 }*/
	 //获得文件服务器上图片的绝对地址
	 public static String  getImagePath(String imageName){
		 	String filePath=SystemDefines.HTTP_PRE+Constants.FILE_SERVER_IP
		 			+":"+Constants.FILE_SERVER_PORT
		 			+"/"+Constants.BOOK_IMAGE_PATH
		 			+"/"+imageName;
		 	return filePath;
	 }
	 //获得文件服务器上图片的绝对地址(机顶盒访问)
	/* public static String  getImagePath_stb(String imageName){
		 	String fileRootPath=SystemDefines.HTTP_PRE+ConfigUtil.getConfig("fileServerIP_stb")
		 			+":"+ConfigUtil.getConfig("fileServerPort")
		 			+"/"+ConfigUtil.getConfig("fileFolder")
		 			+"/"+imageName;
		 	return fileRootPath;
	 }*/
	
	
	 //获得文件服务器上视频文件的绝对地址
	 public static String  getAudioFilePath(String fileName){
		 	String filePath=SystemDefines.HTTP_PRE+Constants.FILE_SERVER_IP
		 			+":"+Constants.FILE_SERVER_PORT
		 			+"/"+Constants.BOOK_AUDIO_PATH
		 			+"/"+fileName;
		 	return filePath;
	 }
	 //获得文件服务器上图书文件的绝对地址
	 public static String  getTextFilePath(String fileName){
		 	String filePath=SystemDefines.HTTP_PRE+Constants.FILE_SERVER_IP
		 			+":"+Constants.FILE_SERVER_PORT
		 			+"/"+Constants.BOOK_TEXT_PATH
		 			+"/"+fileName;
		 	return filePath;
	 }
	 //获取相册文件的绝对地址
	 public static String  getAlbumFilePath(String fileName){
		 	String filePath=SystemDefines.HTTP_PRE+Constants.FILE_SERVER_IP
		 			+":"+Constants.FILE_SERVER_PORT
		 			+"/"+Constants.BOOK_ALBUM_PATH
		 			+"/"+fileName;
		 	return filePath;
	 }
	
	
	public static String readTxtFile(File file) throws IOException{
        String encoding="GBK";
    	String lineTxt = "";
    	StringBuffer content=new StringBuffer();
        InputStreamReader read = new InputStreamReader(
        new FileInputStream(file),encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        while((lineTxt = bufferedReader.readLine()) != null){
            content.append(lineTxt+"\n");
        }
        read.close();
        return content.toString();
	}
	//获得图片宽度和图片高度
	public static int[] getDimensionFromFile(File file){
		int dimesion[] = { 0, 0 };
		BufferedImage sourceImg;
		try {
			sourceImg = ImageIO.read(file);
			if (file != null) {
				dimesion[0] = sourceImg.getWidth();
				dimesion[1] = sourceImg.getHeight();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dimesion;
	}
	public static String getRequestIP(HttpServletRequest request){
		/*String requestIP="";
		if (("0.0.0.0").equals(request.getLocalAddr())) {
			requestIP="127.0.0.1";
		}else {
			requestIP=request.getLocalAddr();
		}*/
		StringBuffer requestUrl=request.getRequestURL();
		String requestIP=requestUrl.substring(7, requestUrl.lastIndexOf(":"));
		return requestIP;
	}
	public static String getFileNameWithoutSuffix(String originalFilename) {
		if (originalFilename != null && originalFilename.length()>0) {
			originalFilename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
		}
		return originalFilename;
	}
}

