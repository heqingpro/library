package com.ipanel.web.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import cn.ipanel.apps.commons.util.FileUtil;

import com.ipanel.webapp.framework.util.Log;

public class ZipUtil {
	private static final String TAG = "ZipUtil";
//
//	public static Map<String, List<ChannelProgramModel>> parseZip(InputStream in) {
//		Map<String, List<ChannelProgramModel>> map = new HashMap<String, List<ChannelProgramModel>>();
//		File tempFile = null;
//		ZipFile zipFile = null;
//		String fileEncoding = Constants.IMPORT_PROGRAM_FILE_ENCODING;
//		try {
//			byte[] programFileData = FileUtil.getByteArrayFromInputStream(in);
//			// 构造zipFile
//			tempFile = File.createTempFile("program", ".tmp");
//			// 将字节写入临时文件中
//			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
//			bos.write(programFileData);
//			bos.close();
//
//			zipFile = new ZipFile(tempFile, fileEncoding);
//
//			// 顺序遍历zipFile
//			Enumeration<ZipEntry> enumeration = zipFile.getEntriesInPhysicalOrder();
//			while (enumeration != null && enumeration.hasMoreElements()) {
//				ZipEntry zipEntry = enumeration.nextElement();
//				if (zipEntry != null && !zipEntry.isDirectory()) {
//					InputStream tempin = zipFile.getInputStream(zipEntry);
//					map.putAll(XmlUtil.parseXml(zipEntry.getName().substring(0,zipEntry.getName().lastIndexOf(".")),tempin));
//				}
//			}
//		} catch (Exception e) {
//			Log.e(TAG, "parseZip throw a Excepition e:" + e);
//		} finally {
//			// 删除临时文件
//			try {
//				zipFile.close();
//				tempFile.deleteOnExit();
//			} catch (IOException e) {
//				Log.e(TAG, "parseZip finally throw a Excepition e:" + e);
//			}
//		}
//		return map;
//	}

	public static String unpressZip(String filePath,String targetPath){
	 	//将文件解压缩----------start  
        ZipFile zipFile;  
        String folderName = "";
        try {  
            zipFile = new ZipFile(filePath);  
            Enumeration<ZipEntry> enu = zipFile.getEntries(); 
            while (enu.hasMoreElements()) {  
                ZipEntry zipElement = enu.nextElement();// 单个文件 对象
                String fileName = zipElement.getName(); // 单个文件名字
                folderName = fileName.substring(0,fileName.indexOf("/"));
                InputStream read = zipFile.getInputStream(zipElement); // 单个文件文件的流  
                String outPath = (targetPath+ File.separator + fileName).replaceAll("\\*", "/");  
                // 判断路径是否存在,不存在则创建文件路径  
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
                if (!file.exists()) {  
                    file.mkdirs();  
                }  
                // 判断文件全路径是否为文件夹,如果是,不需要解压  
                if (new File(outPath).isDirectory()) {  
                    continue;  
                }  
                OutputStream out = new FileOutputStream(outPath);  
                byte[] buf1 = new byte[1024];  
                int len;  
                //将zipFileT中的文件复制到uploadFile文件夹中  
                while ((len = read.read(buf1)) > 0) {  
                    out.write(buf1, 0, len);  
                }  
                read.close();  
                out.close();  
            }
            zipFile.close();
            //删除zip
            FileUtil.deleteFile(filePath);
        } catch (IOException e) {  
            e.printStackTrace();  
        }   
        return folderName;
       //将文件解压缩----------end  
}
	// 压缩目录
	public static void compressDirectory(String path,String tartZipName) {
		try {
			Log.i(TAG, "start compress");
			File sourcefile = new File(path);
			if (sourcefile != null && sourcefile.exists()) {
				Project project = new Project();
				Zip zip = new Zip();
				zip.setProject(project);
				String desPathName=path.substring(0, path.lastIndexOf(Constants.SYSTEM_FILE_SEPARATOR_TAG))+Constants.SYSTEM_FILE_SEPARATOR_TAG+tartZipName;
				File desFile = new File(desPathName);
				zip.setDestFile(desFile);
				FileSet fileSet = new FileSet();
				fileSet.setProject(project);
				fileSet.setDir(sourcefile);
				zip.addFileset(fileSet);
				zip.execute();
				Log.i(TAG, "end compress");
			}
		} catch (Exception e) {
			Log.e(TAG, "compressDirectory throw a Exception e:" + e);
		}
	}

	public static void main(String[] args) {
		try {
			compressDirectory("E:\\ProgramFiles\\nginx\\html\\HFEPG","test.zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
