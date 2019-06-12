package cn.bluethink.ecssparkClient.model;

import java.util.ArrayList;
import java.util.List;

public class SparkInfo {
	private String url;
	private List<workerInfo> workers = new ArrayList<>();
	private String aliveworkers;
	private String cores;
	private String coresused;
	private String memory;
	private String memoryused;
	private List<appInfo> activeapps = new ArrayList<>();
	private List<appInfo> completedapps = new ArrayList<>();
	private List<driverInfo> activedrivers = new ArrayList<>();
	private List<driverInfo> completeddrivers = new ArrayList<>();
	private String status;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<workerInfo> getWorkers() {
		return workers;
	}
	public void setWorkers(List<workerInfo> workers) {
		this.workers = workers;
	}
	public String getAliveworkers() {
		return aliveworkers;
	}
	public void setAliveworkers(String aliveworkers) {
		this.aliveworkers = aliveworkers;
	}
	public String getCores() {
		return cores;
	}
	public void setCores(String cores) {
		this.cores = cores;
	}
	public String getCoresused() {
		return coresused;
	}
	public void setCoresused(String coresused) {
		this.coresused = coresused;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getMemoryused() {
		return memoryused;
	}
	public void setMemoryused(String memoryused) {
		this.memoryused = memoryused;
	}
	public List<appInfo> getActiveapps() {
		return activeapps;
	}
	public void setActiveapps(List<appInfo> activeapps) {
		this.activeapps = activeapps;
	}
	public List<appInfo> getCompletedapps() {
		return completedapps;
	}
	public void setCompletedapps(List<appInfo> completedapps) {
		this.completedapps = completedapps;
	}
	public List<driverInfo> getActivedrivers() {
		return activedrivers;
	}
	public void setActivedrivers(List<driverInfo> activedrivers) {
		this.activedrivers = activedrivers;
	}
	public List<driverInfo> getCompleteddrivers() {
		return completeddrivers;
	}
	public void setCompleteddrivers(List<driverInfo> completeddrivers) {
		this.completeddrivers = completeddrivers;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
