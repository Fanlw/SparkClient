package cn.bluethink.ecssparkClient.model;

/**
 * @author flw
 * @date 2018年10月29日 
 */
public class ExecutorStatus {
    private String name;
    private String os = System.getProperty("os.name");
    private String osArch = System.getProperty("os.arch");
    private Integer runningJob;
    private Integer waitingJob;
    private Double load;

    public Integer getRunningJob() {
        return runningJob;
    }

    public void setRunningJob(Integer runningJob) {
        this.runningJob = runningJob;
    }

    public Double getLoad() {
        return load;
    }

    public void setLoad(Double load) {
        this.load = load;
    }

    public String getStatus() {
        if (load >= 90) {
            return "繁重";
        }
        if (load >= 60) {
            return "中等";
        }
        return "一般";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public Integer getWaitingJob() {
        return waitingJob;
    }

    public void setWaitingJob(Integer waitingJob) {
        this.waitingJob = waitingJob;
    }
}
