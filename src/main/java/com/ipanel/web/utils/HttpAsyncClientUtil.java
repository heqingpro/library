package com.ipanel.web.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import com.ipanel.webapp.framework.util.Log;


public class HttpAsyncClientUtil{
	private static final String TAG = "HttpAsyncClientUtil";
	
	private final static Object syncPoolLock = new Object();
	 
	private final static Object syncClientLock=new Object();
	
	private static PoolingNHttpClientConnectionManager cm;
	private static CloseableHttpAsyncClient instance;
	
	private static PoolingNHttpClientConnectionManager getPool(){
		if(cm==null){
			synchronized (PoolingNHttpClientConnectionManager.class) {
				if(cm==null){
					try {
						ConnectingIOReactor ioReactor=new DefaultConnectingIOReactor();
						cm=new PoolingNHttpClientConnectionManager(ioReactor);
						cm.setMaxTotal(10000);
						//cm.setDefaultMaxPerRoute(1000);
						new IdleConnectionMonitorThread(cm).start();
					} catch (Exception e) {
						Log.e(TAG, "getPool throw a exception e:"+e);
					}
				}
			}
		}
		return cm;
	}
	
	
	public static CloseableHttpAsyncClient getInstance() {
		if(instance==null){
			synchronized (CloseableHttpAsyncClient.class) {
				if(instance==null){
					try {
						RequestConfig requestConfig=RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build();
						instance = HttpAsyncClients.custom().setConnectionManager(getPool()).setDefaultRequestConfig(requestConfig).build();
						instance.start();
					} catch (Exception e) {
						Log.e(TAG, "getInstance throw a exception e:"+e);
					}
				}	 
			}
		}  
		return instance;
	}
	
	/*public static CloseableHttpAsyncClient getInstance() {
		
		 // 设置协议http对应的处理socket链接工厂的对象  
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder.<SchemeIOSessionStrategy>create().register("http", NoopIOSessionStrategy.INSTANCE).build();  
        //配置io线程  
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom().setIoThreadCount(Runtime.getRuntime().availableProcessors()).build();  
        //设置连接池大小  
        ConnectingIOReactor ioReactor=null;  
        try {
			ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
		} catch (IOReactorException e) {
			Log.e(TAG, "create ioReactor throw a excepiton e:"+e);
		}  
        PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(ioReactor, null, sessionStrategyRegistry, null);  
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
        //创建自定义的httpclient对象  
        instance = HttpAsyncClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig).setMaxConnPerRoute(10000).setMaxConnTotal(10000).build();
	
		return instance;
	}
	*/
	/** 
     * private的构造函数用于避免外界直接使用new来实例化对象 
     */  
    private HttpAsyncClientUtil() {  
    
    }  
}

//开启线程，
class IdleConnectionMonitorThread extends Thread { 
	
	private String TAG = "HttpAsyncClientUtil";
	
	
    private final PoolingNHttpClientConnectionManager connMgr;  
    private volatile boolean shutdown;  
      
    public IdleConnectionMonitorThread(PoolingNHttpClientConnectionManager connMgr) {  
        super();  
        this.connMgr = connMgr;  
    }  
  
    @Override  
    public void run() {  
        try {  
            while (!shutdown) {  
                synchronized (this) {
                    wait(30*1000);
                    //Log.i(TAG, " check pool and close idleConnection");
                    connMgr.closeExpiredConnections();
                    //connMgr.closeIdleConnections(30, TimeUnit.SECONDS);  
                }  
            }  
        } catch (InterruptedException e) {  
            Log.e(TAG, "IdleConnectionMonitorThread run throw a exception e:"+e);
        }  
    }  
    public void shutdown() {  
        shutdown = true;  
        synchronized (this) {  
            notifyAll();  
        }  
    }  
      
}  
