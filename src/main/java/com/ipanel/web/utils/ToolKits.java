package com.ipanel.web.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.ipanel.webapp.framework.util.Log;

public class ToolKits {
	
	private static String TAG = "ToolKits";
	
	public static void releaseData(Map<String, byte[]> map,String storePath,String versionFileName) {
		String sufFileName = Constants.HFEPG_RELEASE_DATA_FILE_NAME;

		String filePath = Constants.HFEPG_DATA_RELEASE_PHYSICAL_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+storePath+Constants.SYSTEM_FILE_SEPARATOR_TAG;

		if (map != null && map.keySet() != null && map.keySet().size() > 0) {
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String preFileName = it.next();
				byte[] dataByte = map.get(preFileName);
				writeData(preFileName + sufFileName, dataByte,filePath);
				updateVersion(preFileName,storePath,versionFileName);
			}
		}
	}
	
	public static void updateVersion(String versionKey,String storePath,String versionFileName ) {
		
		Long version = Constants.VERSION_INIT_VALUE;

		String filePath =Constants.HFEPG_DATA_RELEASE_PHYSICAL_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+storePath+Constants.SYSTEM_FILE_SEPARATOR_TAG;
		String fileName = versionFileName;
		if (!fileIsExist(filePath, fileName)) {
			createLocalFile(filePath, fileName);
		}
		Properties versionProperties =readLocalProperties(filePath,fileName);
		String propertyValue = versionProperties.getProperty(versionKey);
		if (propertyValue != null) {
			try {
				version = Long.parseLong(propertyValue);
				version++;
			} catch (Exception e) {
				Log.e(TAG, "updateVersion throw a exception e:"+e);
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put(versionKey, version.toString());
		updateLocalProperties(versionProperties, map, filePath, fileName);
	}
	

	public static void writeData(String fileName, byte[] data, String filePath) {
		writeLocalData(fileName, data, filePath);
	}

	public static void writeLocalData(String fileName, byte[] data,String filePath) {
		OutputStream os=writeLocal(filePath, fileName);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		try {
			bos.write(data);
		} catch (IOException e) {
			Log.e(TAG, "writeLocalData throw a exception e:"+e);
		} finally {
			try {
				bos.close();
				os.close();
			} catch (IOException e2) {
				Log.e(TAG, "writeLocalData throw a exception e2:"+e2);
			}
		}
	}

	public static OutputStream writeLocal(String filePath, String fileName) {
		OutputStream os = new ByteArrayOutputStream();
		File file = createLocalFile(filePath, fileName);
		try {
			os = new FileOutputStream(file);
		} catch (Exception e) {
			Log.e(TAG, "writeLocal throw a exception e:"+e);
		}
		return os;
	}

	public static File createLocalFile(String filePath, String fileName) {
		if (fileName == null) {
			return null;
		}
		File file;
		if (filePath == null) {
			file = new File(fileName);
		} else {
			File newFilePath = new File(filePath);
			if (!newFilePath.exists() || !newFilePath.isDirectory()) {
				newFilePath.mkdirs();
			}
			file = new File(newFilePath, fileName);
		}
		if (!file.exists() || !file.isFile()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				Log.e(TAG, "createLocalFile throw a exception e:"+e);
			}
		}
		return file;
	}

	public static boolean fileIsExist(String filePath, String fileName) {
		if (fileName == null) {
			return false;
		}
		File f;
		if (filePath != null) {
			File fp = new File(filePath);
			if (!fp.exists() || !fp.isDirectory()) {
				return false;
			}

			f = new File(fp, fileName);
		} else {
			f = new File(fileName);
		}

		if (f.exists() && f.isFile()) {
			return true;
		}

		else {
			return false;
		}
	}
	
	public static Properties readLocalProperties(String filePath,String fileName) {
		Properties properties = new Properties();
		InputStream in = readLocal(filePath, fileName);
		if (in != null) {
			try {
				properties.load(in);
			} catch (Exception e) {
				Log.e(TAG, "readLocalProperties throw a exception e:"+e);
			} finally {
				try {
					in.close();
				} catch (Exception e) {
					Log.e(TAG, "readLocalProperties finally throw a exception e:"+e);
				}
			}
		}
		return properties;
	}
	
	public static InputStream readLocal(String filePath, String fileName) {
		InputStream in = null;

		try {
			if (filePath == null) {
				in = new FileInputStream(new File(fileName));
			} else {
				in = new FileInputStream(new File(filePath, fileName));
			}
		} catch (Exception e) {
			Log.e(TAG, "readLocal throw a exception e:"+e);
		}
		return in;
	}

	public static void updateLocalProperties(Properties properties,Map<String, String> propertyKeyValueMap, String filePath,String fileName) {
		for (Entry<String, String> entry : propertyKeyValueMap.entrySet()) {
			properties.setProperty(entry.getKey(), entry.getValue());
		}
		OutputStream os=writeLocal(filePath, fileName);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		try {
			properties.store(bos, "update properties keySet="+ propertyKeyValueMap.keySet());
		} catch (Exception e) {
			Log.e(TAG, "updateLocalProperties throw a exception e:"+e);
		} finally {
			try {
				bos.close();
				os.close();
			} catch (Exception e) {
				Log.e(TAG, "updateLocalProperties finally throw a exception e:"+e);
			}
		}
	}
	
	public static void deleteLocalProperties(Properties properties,List<String> deleteKeyList, String filePath,String fileName) {
		for (String deleteKey: deleteKeyList) {
			if(properties.containsKey(deleteKey)){
				properties.remove(deleteKey);
			}
		}
		OutputStream os=writeLocal(filePath, fileName);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		try {
			properties.store(bos, "delete properties keyList="+ deleteKeyList);
		} catch (Exception e) {
			Log.e(TAG, "deleteLocalProperties throw a exception e:"+e);
		} finally {
			try {
				bos.close();
				os.close();
			} catch (Exception e) {
				Log.e(TAG, "deleteLocalProperties finally throw a exception e:"+e);
			}
		}
	}
	
	public static void clearOverSevenDayFile(String filePath,String fileVersionName) throws Exception{
		Log.i(TAG, "start clean over seven day files");
		createLocalFile(filePath, fileVersionName);
		Properties properties=readLocalProperties(filePath, fileVersionName);
		List<String> deleteKeyList=new ArrayList<String>();
		String currentTime=DateUtil.formatTimeForSimple(new Date());
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(sdf.parse(currentTime));
		long currentMS=calendar.getTimeInMillis();
		
		File file=new File(filePath);
		if(file.isDirectory()){
			File[] fileList=file.listFiles();
			if(fileList!=null){
				for(int i=0;i<fileList.length;i++){
					File tempFile=fileList[i];
					String tempFileName=tempFile.getName();
					if(tempFileName.contains("_")){
						String[] str=tempFileName.split("_");
						if(str.length>=3){
							String date=str[2];
							calendar.setTime(sdf.parse(date));
							long startMS=calendar.getTimeInMillis();
							long day=(currentMS-startMS)/(1000*3600*24);
							int result=Integer.parseInt(String.valueOf(day));
							if(result>7){
								deleteKeyList.add(tempFileName.substring(0, tempFileName.lastIndexOf(".")));
								tempFile.delete();
								
							}
						}
					}				
				}
				deleteLocalProperties(properties, deleteKeyList, filePath, fileVersionName);
				Log.i(TAG, "end clean over seven day files");
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		
	}
}
