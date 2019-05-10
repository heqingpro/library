package com.ipanel.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.ipanel.webapp.framework.util.Log;

public class FtpUtil {
	private static String TAG = "FtpUtil";
	private FTPClient ftpClient;

	public boolean ftpLogin(String ip,int port,String userName,String password) {
		ftpClient = new FTPClient();
		boolean isLogin = false;
		this.ftpClient.setControlEncoding("UTF-8");
		try {
			if (port > 0) {
				this.ftpClient.connect(ip,port);
			} else {
				this.ftpClient.connect(ip);
			}
			ftpClient.enterLocalPassiveMode();
			// FTP服务器连接回答
			int reply = this.ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.ftpClient.disconnect();
				return isLogin;
			}
			this.ftpClient.login(userName,password);
			isLogin = true;
		} catch (SocketException e) {
			Log.e(TAG, "****ftpLogin throw SocketException:" + e);
		} catch (IOException e) {
			Log.e(TAG, "****ftpLogin throw IOException:" + e);
		}
		this.ftpClient.setBufferSize(1024 * 2);
		this.ftpClient.setDataTimeout(2000);
		return isLogin;
	}

	/**
	 * 退出并关闭FTP连接
	 * 
	 */
	public boolean close() {
		if (null != this.ftpClient && this.ftpClient.isConnected()) {
			try {
				return this.ftpClient.logout();// 退出FTP服务器
			} catch (IOException e) {
				Log.e(TAG, "***close throw IOException:" + e);
				return false;
			} finally {
				try {
					this.ftpClient.disconnect();// 关闭FTP服务器的连接
				} catch (IOException e) {
					Log.e(TAG, "***disconnect throw IOException:" + e);
				}
			}
		}
		return true;
	}

	
	
	/**
	 * 下载文件
	 * 
	 * @param localFilePath
	 *            本地文件名及路径
	 * @param remoteFileName
	 *            远程文件名称
	 * @return
	 * @throws IOException 
	 */
	public String downloadFile( String remoteFileName) throws IOException {
		StringBuilder builder = null;
		ftpClient.enterLocalPassiveMode();
		InputStream ins = ftpClient.retrieveFileStream(remoteFileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
		String line="";
		builder = new StringBuilder(150);
		while ((line = reader.readLine()) != null) {
		builder.append(line);
		}
		reader.close();
		if (ins != null) {
		ins.close();
		}
		// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
		ftpClient.getReply();
		ftpClient.disconnect();
		    	
		    	return builder.toString();

	}
	
	public InputStream download(String remoteFileName){
		InputStream is = null;
		try {
			ftpClient.enterLocalPassiveMode();
            is =ftpClient.retrieveFileStream(remoteFileName);
//            if (is != null) {
//        		is.close();
//        	}
            ftpClient.getReply();
            ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return is;
	}

	/**
	 * 下载文件
	 * 
	 * @param localFilePath
	 *            本地文件
	 * @param remoteFileName
	 *            远程文件名称
	 * @return
	 */
	public boolean downloadFile(File localFile, String remoteFileName) {
		BufferedOutputStream outStream = null;
		FileOutputStream outStr = null;
		boolean success = false;
		try {
			outStr = new FileOutputStream(localFile);
			outStream = new BufferedOutputStream(outStr);
			success = this.ftpClient.retrieveFile(remoteFileName, outStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != outStream) {
					try {
						outStream.flush();
						outStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (null != outStr) {
					try {
						outStr.flush();
						outStr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}
		return success;
	}

	/**
	 * 上传文件
	 * 
	 * @param localFilePath
	 *            本地文件路径及名称
	 * @param remoteFileName
	 *            FTP 服务器文件名称
	 * @return
	 */
	public boolean uploadFile(InputStream inStream, String remoteFileName,
			String remoteDir) {
		boolean success = false;
		BufferedInputStream binStream = null;
		try {
			if (remoteDir != null && !remoteDir.equals("./")
					&& !remoteDir.equals("/") && remoteDir.contains("/")) {
				// System.out.println("****remoteFileName:" + remoteFileName);
				// 创建服务器远程目录结构，创建失败直接返回
				if (!CreateDirecroty(remoteDir)) {
					return false;
				}
			}
//			boolean changeResult = this.ftpClient
//					.changeWorkingDirectory(remoteDir);
//			Log.i(TAG, "****changeResult:"+changeResult+" "+this.ftpClient.printWorkingDirectory());
//			if (!changeResult)
//				return changeResult;
			binStream = new BufferedInputStream(inStream);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			success = this.ftpClient.storeFile(remoteFileName, binStream);
		} catch (FileNotFoundException e) {
			Log.e(TAG, "***uploadFile throw FileNotFoundException:"+e);
		} catch (IOException e) {
			Log.e(TAG, "***uploadFile throw IOException:"+e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (binStream != null) {
				try {
					binStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}

	

	/** */
	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * 
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public boolean CreateDirecroty(String remote) {
		try {
			boolean success = true;
			String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
			// 如果远程目录不存在，则递归创建远程服务器目录
			if (!directory.equalsIgnoreCase("/")
					&& !ftpClient.changeWorkingDirectory(new String(directory))) {
				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = new String(remote.substring(start,
							end));
					Log.i(TAG,
							"***workingDir:"
									+ ftpClient.printWorkingDirectory()
									+ " subDirectory:" + subDirectory);
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (ftpClient.makeDirectory(subDirectory)) {
							ftpClient.changeWorkingDirectory(subDirectory);
						} else {
							System.out.println("创建目录失败");
							success = false;
							return success;
						}
					}
					start = end + 1;
					end = directory.indexOf("/", start);
					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
			}
			return success;
		} catch (Exception e) {
			return false;
		}
	}

	

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean delFile(String fileName) {
		try {
			return ftpClient.deleteFile(fileName);
		} catch (IOException e) {
			Log.e(TAG, "***delFile fileName:" + fileName + " throw exception:"
					+ e);
			return false;
		}
	}

	/**
	 * 删除目录
	 * 
	 * @param ftpPath
	 * @return
	 */
	public boolean deleteDir(String ftpPath) {
		boolean flag = false;
		try {
			flag = iterateDelete(ftpPath);
		} catch (IOException e) {
			// TODO 异常处理块
			Log.e(TAG, "***deleteDir throw exception:" + e);
		}
		return flag;
	}

	private boolean iterateDelete(String ftpPath) throws IOException {
		Log.i(TAG, "--------------enter iterateDelete ftpPath:" + ftpPath
				+ " working:" + ftpClient.printWorkingDirectory());
		FTPFile[] files = ftpClient.listFiles(ftpPath);
		Log.i(TAG, "--------------files.length:" + files.length);
		boolean flag = false;
		for (FTPFile f : files) {
			String path = ftpPath + "/" + f.getName();
			Log.i(TAG,
					"-------------isFile:" + f.isFile() + " isDir:"
							+ f.isDirectory() + " path:" + f.getName());
			if (f.isFile()) {
				// 是文件就删除文件
				ftpClient.deleteFile(path);
			} else if (f.isDirectory()) {
				iterateDelete(path);
			}
		}
		// 每次删除文件夹以后就去查看该文件夹下面是否还有文件，没有就删除该空文件夹
		FTPFile[] files2 = ftpClient.listFiles(ftpPath);
		if (files2.length == 0) {
			flag = ftpClient.removeDirectory(ftpPath);
		} else {
			flag = false;
		}
		return flag;
	}

	
	public static void main(String arg[]) throws IOException {
		FtpUtil ftpUtil = new FtpUtil();
//		ftpUtil.ftpLogin("192.168.18.52",21,"huasu_live","huasu_live");
		ftpUtil.ftpLogin("192.168.20.42",21,"test","123456");
//		String test = "data123";
//		ByteArrayInputStream bais = new ByteArrayInputStream(test.getBytes());
//		boolean result = ftpUtil.uploadFile(bais, "wuxi.xml", "wuxi/xmlData/");
//		System.out.println("****result:"+result);

		// 测试上传目录，结果OK
		/*
		 * boolean result = ftpUtil.uploadDir(
		 * "D:\\temp\\677F290EA38616BACDED76FD08061D66\\", "", "/"); Log.i(TAG,
		 * "****result:" + result);
		 */

		// 测试删除目录，结果OK
		// boolean delDirResult = ftpUtil.deleteDir("ssidLogin");
		// System.out.println("delDirResult:" + delDirResult);
//ftpUtil.downloadFile(new File("D:\\a.xml"),"12/fsadf.xml");

		// 测试下载目录,结果OK
//		String str=ftpUtil.downloadFile("12/fsadf.xml");
//		System.out.println(str);
		InputStream is = ftpUtil.download("12/fsadf.xml");
		 BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));  
         StringBuffer sb = new StringBuffer();  
         String line = "";  
         while ((line = reader.readLine()) != null)  
         {  
             sb.append(line).append("\n");  
         }  
         System.out.println(sb.toString());
         
		ftpUtil.close();

	}
}
