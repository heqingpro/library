package com.ipanel.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtil {
	
	public static String getFileEncode(CommonsMultipartFile entryImportFile) throws IOException {
		//编码判断是否为utf-8
		String languageCode = "GBK";
		InputStream in = entryImportFile.getInputStream();
		byte[] b = new byte[3];
		in.read(b);
		in.close();
		if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
			languageCode = "UTF-8";
		}
		return languageCode;
	}
	
	public static byte[] getByteArrayFromInputStream(InputStream in)throws Exception {
		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int data;
		try {
			while (true) {
				data = bis.read();
				if (data == -1) {
					break;
				}
				baos.write(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("readByteArrayFromInputStream IOException occured.", e);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				throw new Exception("readByteArrayFromInputStream close ByteArrayOutputStream occured.",e);
			}
		}
		return baos.toByteArray();
	}
	
	public static String getFileString(InputStream in, String languageCode, String fileName) throws Exception {
		File file = new File(fileName);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		BufferedReader br=new BufferedReader(new InputStreamReader(in,Constants.BOOK_ENCODING));
		StringBuilder sbBuilder = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),Constants.BOOK_ENCODING));
		JSONObject jsonObject = new JSONObject();
		try {
			String line = null;
            while((line = br.readLine()) != null) {
            	if(line.startsWith("#")) {
            		int level = line.lastIndexOf("#") + 1;
            		line = "<h"+level+" align='center'>" + line + "</h"+level+">";
            	}
            	sbBuilder.append(line+"\n");
            }
            jsonObject.put("content",sbBuilder.toString());
            bw.write(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("readByteArrayFromInputStream IOException occured.", e);
		} finally {
			try {
				br.close();
				bw.close();
			} catch (IOException e) {
				throw new Exception("readByteArrayFromInputStream close ByteArrayOutputStream occured.",e);
			}
		}
		return sbBuilder.toString();
	}
	
	public static void main(String[] args) {
		try {
			InputStream in=new FileInputStream("1.txt");
			String ss= getFileString(in, "UTF-8", "test123/123.txt");
			System.out.println(ss);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}