package com.ipanel.web.system.base;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.Swap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SystemUtils.class);

    
	/**
	 * 获取系统信息
	 *
	 * @author lvchao
	 * @createtime 2018年4月26日 下午3:52:29
	 *
	 * @return
	 */
    public static BaseInfomation base() {
    	try {
	        Properties props = System.getProperties();
	        InetAddress addr = InetAddress.getLocalHost();
	
	        BaseInfomation baseInfomation = new BaseInfomation();
	        baseInfomation.setIp(addr.getHostAddress());
	        baseInfomation.setHostName(addr.getHostName());
	        
	        baseInfomation.setOsName(props.getProperty("os.name"));
	        baseInfomation.setOsArch(props.getProperty("os.arch"));
	        baseInfomation.setOsVersion(props.getProperty("os.version"));
	        
	        baseInfomation.setJdkVersion(props.getProperty("java.version"));
	        baseInfomation.setJdkVendor(props.getProperty("java.vendor"));
	        return baseInfomation;
    	} catch (Exception e) {
    		LOGGER.error("Get the system's infos error!", e);
    		return null;
		}
    }

    
    /**
     * 获取CPU信息
     *
     * @author lvchao
     * @createtime 2018年4月26日 上午11:20:09
     *
     * @return
     */
    public static HashMap<Integer, CpuInfomation> cpus() {
    	try {
	        Sigar sigar = new Sigar();
	        CpuInfo cpuInfos[] = sigar.getCpuInfoList();
	        CpuPerc cpuPercs[] = sigar.getCpuPercList();
	        HashMap<Integer, CpuInfomation> cpuMap = new HashMap<Integer, CpuInfomation>();
	        for (int i = 0; i < cpuInfos.length; i++) { // 不管是单块CPU还是多CPU都适用
	        	CpuInfomation infomaiton = new CpuInfomation();
	        	infomaiton.setId(i + 1);
	        	
	            infomaiton.setFrequency(cpuInfos[i].getMhz()); // 频率
	            infomaiton.setVendor(cpuInfos[i].getVendor()); // 卖家
	            infomaiton.setModel(cpuInfos[i].getModel()); // 类型
	            
	            infomaiton.setUser(cpuPercs[i].getUser()); // 用户使用率
	            infomaiton.setSys(cpuPercs[i].getSys()); // 系统使用率
	            infomaiton.setWait(cpuPercs[i].getWait()); // 当前等待率
	            infomaiton.setNice(cpuPercs[i].getNice()); // 当前空闲率
	            infomaiton.setIdle(cpuPercs[i].getIdle()); // 当前错误率
	            infomaiton.setCombined(cpuPercs[i].getCombined()); // 总的使用率
	            cpuMap.put(infomaiton.getId(), infomaiton);
	        }
	        return cpuMap;
    	} catch (Exception e) {
    		LOGGER.error("Get the cpus' infos error!", e);
    		return null;
		}
    }

    
    /**
     * 获取内存信息
     *
     * @author lvchao
     * @createtime 2018年4月26日 下午12:08:22
     *
     * @return
     */
    public static MemInfomation memory() {
    	try {
	        Sigar sigar = new Sigar();
	        MemInfomation memInfomation = new MemInfomation();
	        
	        // 内存
	        Mem mem = sigar.getMem();
	        memInfomation.setMemTotal(mem.getTotal() / 1024L / 1024L); //单位：MB
	        memInfomation.setMemUsed(mem.getUsed() / 1024L / 1024L);
	        memInfomation.setMemFree(mem.getFree() / 1024L / 1024L);
	        memInfomation.setMemUsedPercent(mem.getUsedPercent());
	        memInfomation.setMemFreePercent(mem.getFreePercent());
	
	        // 交换区
	        Swap swap = sigar.getSwap();
	        memInfomation.setSwapTotal(swap.getTotal() / 1024L / 1024L);
	        memInfomation.setSwapUsed(swap.getUsed() / 1024L / 1024L);
	        memInfomation.setSwapFree(swap.getFree() / 1024L / 1024L);
	        memInfomation.setSwapUsedPercent(swap.getUsed() * 1.0d / swap.getTotal());
	        memInfomation.setSwapFreePercent(swap.getFree() * 1.0d / swap.getTotal());
	        
	        return memInfomation;
    	} catch (Exception e) {
    		LOGGER.error("Get the memory's infos error!", e);
    		return null;
		}
    }
    
    
    /**
     * 获取磁盘信息
     *
     * @author lvchao
     * @createtime 2018年4月26日 下午3:28:35
     *
     * @return
     */
    public static HashMap<String, DiskInfomation> disks() {
    	try {
	        Sigar sigar = new Sigar();
	        FileSystem fslist[] = sigar.getFileSystemList();
	        HashMap<String, DiskInfomation> diskMap = new HashMap<String, DiskInfomation>();
	        for (int i = 0; i < fslist.length; i++) {
	            FileSystem fs = fslist[i];
	            if(fs.getType() == 2) { // TYPE_LOCAL_DISK : 本地硬盘
	            	DiskInfomation disk = new DiskInfomation();
		            disk.setDevName(fs.getDevName()); //盘符名称
		            disk.setDirName(fs.getDirName()); //盘符路径
		            disk.setSysTypeName(fs.getSysTypeName()); //盘符文件系统类型
		            
                    FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
                    disk.setTotal(usage.getTotal() / 1024L / 1024L); //单位：GB
                    disk.setUsed(usage.getUsed() / 1024L / 1024L);
                    disk.setFree(usage.getFree() / 1024L / 1024L);
                    disk.setAvail(usage.getAvail() / 1024L / 1024L);
                    disk.setUsedPercent(usage.getUsePercent());
                    disk.setFreePercent(usage.getFree() * 1.0d / usage.getTotal());
                    disk.setAvailPercent(usage.getAvail() * 1.0d / usage.getTotal());
                    disk.setUnAvailPercent(disk.getFreePercent() - disk.getAvailPercent());
                    diskMap.put(disk.getDevName(), disk);
	            }
	        }
	        return diskMap;
    	} catch (Exception e) {
    		LOGGER.error("Get the disks' infos error!", e);
    		return null;
		}
    }

}
