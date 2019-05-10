package com.ipanel.web.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class MD5FileUtil {
	private static Logger logger = Logger.getLogger(MD5FileUtil.class);
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	public static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("MD5FileUtil messagedigest", e);
		}
	}

	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
				file.length());
		messagedigest.update(byteBuffer);
		ch.close();
		in.close();
		return bufferToHex(messagedigest.digest());
	}
	
	
	public static String getFileMD5String(InputStream is) throws IOException{
		byte[] buffer = new byte[1024];
		int read = 0;
		while((read = is.read(buffer))>0){
			messagedigest.update(buffer,0,read);
		}
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static void main(String[] args) throws IOException {
//		long begin = System.currentTimeMillis();
//		File big = new File("D:/logo.png");
//		String md5 = getFileMD5String(big);
//		long end = System.currentTimeMillis();
//		System.out.println("md5:" + md5);
//		System.out.println("time:" + ((end - begin) / 1000) + "s");
		
	}
}
