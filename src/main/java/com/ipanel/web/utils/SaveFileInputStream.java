package com.ipanel.web.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 上传图片到工程中
 * 
 * @remark
 * @author zhaoyx
 * @createTime 2015年8月3日 上午11:22:19
 */
public class SaveFileInputStream {
	public static void saveFileFromInputStream(InputStream stream, String path,
			String picPath) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + picPath);
		byte[] buffer = new byte[1024 * 1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}
}
