package com.ipanel.web.system.controller.resp;

import java.util.List;

import com.ipanel.web.system.base.BaseInfomation;
import com.ipanel.web.system.base.CpuInfomation;
import com.ipanel.web.system.base.DiskInfomation;
import com.ipanel.web.system.base.MemInfomation;

public class SystemResp extends BaseResp{
	
	private BaseInfomation base;
	private List<CpuInfomation> cpus;
	private MemInfomation memory;
	private List<DiskInfomation> disks;
	
	public SystemResp() {
		super();
	}
	
	public SystemResp(Integer code, String msg) {
		super(code, msg);
	}

	public SystemResp(BaseInfomation base, List<CpuInfomation> cpus,
			MemInfomation memory, List<DiskInfomation> disks) {
		super();
		this.base = base;
		this.cpus = cpus;
		this.memory = memory;
		this.disks = disks;
	}

	public BaseInfomation getBase() {
		return base;
	}

	public void setBase(BaseInfomation base) {
		this.base = base;
	}

	public List<CpuInfomation> getCpus() {
		return cpus;
	}

	public void setCpus(List<CpuInfomation> cpus) {
		this.cpus = cpus;
	}

	public MemInfomation getMemory() {
		return memory;
	}

	public void setMemory(MemInfomation memory) {
		this.memory = memory;
	}

	public List<DiskInfomation> getDisks() {
		return disks;
	}

	public void setDisks(List<DiskInfomation> disks) {
		this.disks = disks;
	}
	
}
