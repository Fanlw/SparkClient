package cn.bluethink.ecssparkClient.model;

public class workerInfo {
    private String id; 
    private String host; 
    private String port; 
    private String webuiaddress;
    private String cores;
    private String coresused; 
    private String coresfree;
    private String memory;
    private String memoryused; 
    private String memoryfree;
    private String state;
    private String lastheartbeat;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getWebuiaddress() {
		return webuiaddress;
	}
	public void setWebuiaddress(String webuiaddress) {
		this.webuiaddress = webuiaddress;
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
	public String getCoresfree() {
		return coresfree;
	}
	public void setCoresfree(String coresfree) {
		this.coresfree = coresfree;
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
	public String getMemoryfree() {
		return memoryfree;
	}
	public void setMemoryfree(String memoryfree) {
		this.memoryfree = memoryfree;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLastheartbeat() {
		return lastheartbeat;
	}
	public void setLastheartbeat(String lastheartbeat) {
		this.lastheartbeat = lastheartbeat;
	}
    
}
