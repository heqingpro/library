package com.ipanel.web.utils;

/**
 * Program  : ImageUtil.java
 * Author   : sunny
 * Create   : 2008-1-17 ����04:35:11
 *
 * Copyright 1997-2005 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import cn.ipanel.apps.commons.util.FileUtil;

public class ImageUtil {
	/**
	 * �ж϶��������,�Ƿ�ΪͼƬ���(gif,jpg,jpeg,png,bmp);
	 * 
	 * @param data
	 * @return ����ͼƬ����,���򷵻�NULL.
	 * @author sunny
	 * @create 2008-1-17 ����04:53:31
	 */
	public static String imagePacketIsError = "";


	public static String getType(byte[] data) {
		if (data == null || data.length == 0)
			return null;

		byte jpeg[] = { (byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0,
				0x00, 0x10, 0x4A, 0x46, 0x49, 0x46, 0x00 };
		byte gif[] = { 0x47, 0x49, 0x46, 0x38, 0x39, 0x61 };// 87a��ʽGIFͼ
		byte gifs[] = { 0x47, 0x49, 0x46, 0x38, 0x37, 0x61 };// 89a��ʽGIFͼ

		byte bmp[] = { 0x42, 0x4D, '.', '.', '.', '.', 0x0, 0x0 };// .��ʾ���ֽ�Ϊ�κ�ֵ(ͼƬ�Ĵ�С)

		byte png[] = { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };

		for (int i = 0; i < gif.length; i++) {
			if (gif[i] != data[i])
				break;
			if (i == gif.length - 1)
				return "gif";
		}
		for (int i = 0; i < gifs.length; i++) {
			if (gifs[i] != data[i])
				break;
			if (i == gifs.length - 1)
				return "gif";
		}
		for (int i = 0; i < jpeg.length; i++) {
			if (jpeg[i] != data[i])
				break;
			if (i == jpeg.length - 1)
				return "jpg";
		}
		for (int i = 0; i < bmp.length; i++) {
			if (bmp[i] != data[i] && bmp[i] != '.')
				break;
			if (i == bmp.length - 1)
				return "bmp";
		}
		for (int i = 0; i < png.length; i++) {
			if (png[i] != data[i])
				break;
			if (i == png.length - 1)
				return "png";
		}
		return null;
	}

	/**
	 * ����ͼƬ�Ŀ�͸�
	 * 
	 * @param data
	 * @return int[0]=��,int[1]=��
	 * @author sunny
	 * @throws IOException
	 * @create 2008-5-19 ����03:51:52
	 */
	public static int[] getDimension(byte[] data) {
		int dimesion[] = { 0, 0 };
		/*
		 * if (!File.separator.equals("\\")) { Properties pro =
		 * System.getProperties(); String key = "java.awt.headless"; if
		 * (pro.get(key) == null)// Linux�� û����X Windows,Ҫ���øò�����ܻ�ø߿�;
		 * pro.setProperty(key, "true"); }
		 */
		ByteArrayInputStream is = new ByteArrayInputStream(data);
		BufferedImage image = null;
		;
		try {
			image = ImageIO.read(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (image != null) {
			dimesion[0] = image.getWidth();
			dimesion[1] = image.getHeight();
		}
		// System.out.println(image.getWidth()+","+image.getHeight());
		// ImageIcon icon = new ImageIcon(data);
		// dimesion[0] = icon.getIconWidth();
		// dimesion[1] = icon.getIconHeight();
		return dimesion;
	}

	/**
	 * ��ȡ��Ӧ�ļ�������ֽ����
	 * 
	 * @author ven
	 * @serialData
	 * @param file
	 * @return
	 */
	public static byte[] getImageContent(File file) {
		FileInputStream fissrc;
		byte[] data = null;
		try {
			if (file.exists() && file.length() != 0 && file.isFile()) {
				fissrc = new FileInputStream(file);
				int len = fissrc.available();
				data = new byte[len];
				int actual = 0;
				int bytesread = 0;
				while ((bytesread != len) && (actual != -1)) {
					actual = fissrc.read(data, bytesread, len - bytesread);
					bytesread += actual;
				}

				fissrc.close();
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	public static boolean delAllFiles(String path) {

		File f = new File(path);
		File[] files = f.listFiles();
		if(files == null || files.length<=0)return true;
		for(File ff :files){
			if(ff.isFile())ff.delete();
			else if(ff.isDirectory()){
					delAllFiles(ff.getPath());
					ff.delete();
			}
		}

		return true;
	}
	
	/**
	 * ��ȡָ�������е�ͼƬ�ļ� ���أ�byte[] ����
	 */
	public static byte[] readFileByByte(String filePath) throws Exception {

		FileInputStream fissrc = null;
		try {
			fissrc = new FileInputStream(filePath);
			int len = fissrc.available();
			byte[] data = new byte[len];
			fissrc.read(data);
			fissrc.close();
			return data;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (fissrc != null)
				try {
					fissrc.close();
				} catch (Exception e2) {

				}
		}

	}

	public static byte[] stream2Byte(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		in.close();
		byte[] content = out.toByteArray();
		return content;

	}
	
	
	/**
	 * �ж��ļ���׺���Ƿ�ΪͼƬ��ʽ
	 */
	public static boolean judgeFileImage(String fileName) {
		String[] image = { "JPG", "jpg", "gif", "GIF", "PNG", "png"};		
		String type = fileName.substring(fileName.lastIndexOf(".")+1, fileName
				.length());
		if(fileName.indexOf(".")==-1)return false;
		boolean b = false;
		for (int i = 0; i < image.length; i++) {
			if (type.equals(image[i])) {
				b = true;
				break;
			}
		}		
		return b;
	}
	
	/**
	 * б���滻
	 * 
	 * @param sourceStr
	 * @param lineType
	 *            true : ȫת��Ϊ"\"; false ȫת��Ϊ"/"
	 * @return
	 */
	public static String reverse(String sourceStr, boolean lineType) {
		if (lineType) {
			Pattern pattern = Pattern.compile("/", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(sourceStr);
			sourceStr = matcher.replaceAll("\\\\"); // ת�����
		} else {
			Pattern pattern = Pattern.compile("\\\\", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(sourceStr);
			sourceStr = matcher.replaceAll("/"); // ת�����
		}
		return sourceStr;
	}

	/**
	 * ��ȡӦ��·��
	 */
	public static String getAbsPathOfProject() {
		String url = null;
		try {
			url = URLDecoder.decode(FileUtil.class.getClassLoader()
					.getResource("").toString(), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String reg0 = "file:(.+?)WEB-INF"; // ��Tomcat��
		Matcher mat0 = Pattern.compile(reg0, Pattern.CASE_INSENSITIVE).matcher(
				url);
		String reg1 = "file:(.+?bin/)"; // Ӧ�ó�����
		Matcher mat1 = Pattern.compile(reg1, Pattern.CASE_INSENSITIVE).matcher(
				url);
		Matcher mat = mat0.find() ? mat0 : mat1;
		if (mat.find(0)) {
			String path = mat.group(1);
			path = path.replaceAll("/", "\\" + File.separator);
			return File.separator.equals("\\") ? path.substring(1) : path;
		}
		return null;
	}

	//���ͼƬ��չ��
	public static String getImageType(String imageName){
		String nameArray[]=imageName.split("\\.");
		String type=nameArray[nameArray.length-1];
		return type;
	}
}
