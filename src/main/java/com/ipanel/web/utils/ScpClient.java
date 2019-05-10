package com.ipanel.web.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.ipanel.web.entity.RemoteServer;
import com.ipanel.webapp.framework.util.Log;

public class ScpClient {
	
	private static final String TAG ="ScpClient";
	
	public static void copyFileToRemoteServer(List<RemoteServer> remoteServerList,String targetZipName){
		try {
			Log.i(TAG, "copyFileToRemoteServer start");
			//将本地文件夹进行压缩
			ZipUtil.compressDirectory(Constants.HFEPG_DATA_RELEASE_PHYSICAL_PATH,targetZipName);
			
			//将压缩文件上传到各个服务器
			if(remoteServerList!=null&&remoteServerList.size()>0){
				for(RemoteServer remoteServer:remoteServerList){
					Connection conn=new Connection(remoteServer.getRemoteIP(), remoteServer.getRemotePort());
					try {
						conn.connect();
						boolean isAuthedFlag=conn.authenticateWithPassword(remoteServer.getUserName(), remoteServer.getUserPass());
						Log.i(TAG, remoteServer.getRemoteIP()+remoteServer.getRemotePort()+","+remoteServer.getUserName()+" isAuthed:"+isAuthedFlag);
						if(isAuthedFlag==false){
							continue;
						}
						SCPClient client=conn.createSCPClient();
						//上传到服务器
						String sourceFile=Constants.HFEPG_DATA_RELEASE_PHYSICAL_PATH.substring(0, Constants.HFEPG_DATA_RELEASE_PHYSICAL_PATH.lastIndexOf(Constants.SYSTEM_FILE_SEPARATOR_TAG))+Constants.SYSTEM_FILE_SEPARATOR_TAG+ Constants.HFEPG_DATA_COMPRESS_NAME;
						client.put(sourceFile, Constants.HFEPG_DATA_RELEASE_PHYSICAL_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG);
						//开始解压缩已上传的文件
						Session sessionUnzip = conn.openSession();
						Log.i(TAG, "开始解压文件");
						sessionUnzip.requestPTY("bash");
						sessionUnzip.startShell();
					    InputStream stdoutUnzip = new StreamGobbler(sessionUnzip.getStdout());
					    InputStream stderrUnzip = new StreamGobbler(sessionUnzip.getStderr());
					    BufferedReader stdoutReaderUnzip = new BufferedReader(new InputStreamReader(stdoutUnzip));
					    BufferedReader stderrReaderUnzip = new BufferedReader(new InputStreamReader(stderrUnzip));
					    PrintWriter out = new PrintWriter(sessionUnzip.getStdin());
					    out.println("cd " + Constants.HFEPG_DATA_RELEASE_PHYSICAL_PATH);
					    out.println("ll");
					    out.println("unzip -o " + Constants.HFEPG_DATA_COMPRESS_NAME);
					    out.println("ll");
					    out.println("exit");
					    out.close();
					    sessionUnzip.waitForCondition(50, 30000L);
					    Log.i(TAG, "下面是从stdout输出:");
					    while (true) {
					      String line = stdoutReaderUnzip.readLine();
					      if (line == null) break;
					      Log.i(TAG, "line="+line);
					    }
					    Log.i(TAG, "下面是从stderr输出:");
					    while (true) {
					      String line = stderrReaderUnzip.readLine();
					      if (line == null) break;
					      Log.i(TAG, "line="+line);
					    }
					    Log.i(TAG, "ExitCode: " + sessionUnzip.getExitStatus());
					    Log.i(TAG, "解压文件成功");
					    stderrReaderUnzip.close();
					    stderrUnzip.close();
					    stdoutReaderUnzip.close();
					    stdoutUnzip.close();
					    sessionUnzip.close();
					    
					    
					    Log.i(TAG, "开始删除存在的压缩文件");
					    String result="";
					    String deleteCmd="rm -f "+Constants.HFEPG_DATA_RELEASE_PHYSICAL_PATH+Constants.SYSTEM_FILE_SEPARATOR_TAG+Constants.HFEPG_DATA_COMPRESS_NAME;
					   Session sessionDelete=conn.openSession();
					   sessionDelete.execCommand(deleteCmd);
					   InputStream stdoutDelete = new StreamGobbler(sessionDelete.getStdout());
					   BufferedReader  stdoutReaderDelete = new BufferedReader( new InputStreamReader(stdoutDelete));
					    while (true){
					      String line = stdoutReaderDelete.readLine();
					      if (line == null) {
					        break;
					      }
					      result = result + line + "\n";
					    }

					    InputStream stderrDelete = new StreamGobbler(sessionDelete.getStderr());
					    BufferedReader stderrReaderDelete = new BufferedReader( new InputStreamReader(stderrDelete));
					    while (true){
					      String line = stderrReaderDelete.readLine();
					      if (line == null) {
					        break;
					      }
					      result = result + line + "\n";
					    }
					    
					    Log.i(TAG, "delete result:"+result);
					    stderrReaderDelete.close();
					    stderrDelete.close();
					    stdoutReaderDelete.close();
					    stdoutDelete.close();
					    sessionDelete.close();
					    
					    conn.close();				       
					} catch (Exception e) {
						Log.e(TAG, "copyFileToRemoteServer "+remoteServer.getRemoteIP()+" throw a excepition"+e);
						continue;
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "copyFileToRemoteServer throw a excepition"+e);
		}
	}
}
