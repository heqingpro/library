package com.ipanel.web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import com.ipanel.web.utils.ZipUtil;

/**
 * @author fangg
 * 2017年12月11日 下午2:32:27
 */
public class TestFile {
	
	public static void main(String[] args) {
		/*JSONObject jsonObject = new JSONObject();
		jsonObject.put("interfaceCode", "0031");
		jsonObject.put("sourceID", "LwWeb");
		jsonObject.put("destinationID", "GdWeb");
		jsonObject.put("visitUserCode", "GdWeb");
		jsonObject.put("visitDepartmentCode", "GdWeb");
		
		JSONObject timeJsonObject = new JSONObject();
		timeJsonObject.put("ksrq", "20170101");
		timeJsonObject.put("jsrq", "20171111");
		
		jsonObject.put("packageInfo",timeJsonObject );
		System.out.println(jsonObject);
		*/
		/*try {
			System.out.println(GetMin("2017-11-05 08:26:10", "2017-11-05 09:30:56"));	
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		/*String content = "<p><img src=\"ueditor/jsp/upload/image/20171123/1511349343063053803.jpg\" title=\"1511349343063053803.jpg\" alt=\"Desert.jpg\"/></p><p>茫茫的沙漠</p>";
		System.out.println(content);
		content=content.replaceAll("src=\""+SystemDefines.UEDITORIMAGEPATH+"/"+TimeOperation.getTodayString()+"/", "src=\"");
		System.out.println(content);*/
/*		String navigator = "统计测试1201>嵌套";
		navigator = navigator.substring(0,navigator.lastIndexOf(">"))+">"+"长度测试";
		System.out.println(navigator);
*/		
//		Assert.assertEquals("true", false);
		
		int BUFSIZE = 1024 * 8;
		String tempFolder= "d:/TS00010678";
		String folderName = ZipUtil.unpressZip("d:/TS00010678.zip", "d:/"); //解压之后的文件夹的名字，可能跟zip的名字不一样
		File subFileFolder = new File(tempFolder);
		File outFile = new File("d:/TS00010678.htm");
		File[] files = subFileFolder.listFiles();
		FileChannel outChannel = null ;   
	        try {   
	            outChannel = new FileOutputStream(outFile).getChannel();   
	            for(File file : files){
	            	if(file!=null&&file.isFile()&&file.getAbsolutePath().endsWith(".htm")){
	            		String f = file.getAbsolutePath();
		            	System.out.println(f);
		                Charset charset=Charset.forName("utf-8");   
		  
		                CharsetDecoder chdecoder=charset.newDecoder();   
		  
		                CharsetEncoder chencoder=charset.newEncoder();   
		  
		                FileChannel fc = new FileInputStream(f).getChannel();    
		  
		                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);   
		  
		                CharBuffer charBuffer=chdecoder.decode(bb);   
		  
		                ByteBuffer nbuBuffer=chencoder.encode(charBuffer);   
		  
		                while(fc.read(nbuBuffer) != -1){   
		  
		                    bb.flip();     
		  
		                    nbuBuffer.flip();   
		  
		                    outChannel.write(nbuBuffer);   
		  
		                    bb.clear();   
		  
		                    nbuBuffer.clear();   
		  
		                }   
		                fc.close();   
	            	}
	            }   
	        } catch (IOException ioe) {   
	  
	            ioe.printStackTrace();   
	  
	        } finally {   
	  
	            try {if (outChannel != null) {outChannel.close();}} catch (IOException ignore) {}   
	  
	        }   
	  
	}	  
	

}
