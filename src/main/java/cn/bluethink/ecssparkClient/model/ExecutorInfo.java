package cn.bluethink.ecssparkClient.model;

import java.util.List;

/**
 * 运行环境信息
 * @author flw
 * @date 2018年9月19日 
 */
public class ExecutorInfo {
    private String name;
    // distribute/spark, distribute/hadoop, distribute/flink, standalone/windows, standalone/linux, standalone/mac
    private String type;
    private String os;
    private String osArch;
    private Long jvmMemTotal;
    private Long jvmMemAvailable;
    private Long jvmMemMax;
    private Integer jvmAvailableProcessors;
    private Long memTotal;//内存
    private Long memAvailable;//可用内存
    private Long memUsed;//已用内存
    private Long memSwapTotal;
    private Long memSwapUsed;
    private Long memSwapAvailable;
    private String cpuVendor;
    private String cpuModel;
    private Integer cpuMhz;
    private CpuUsage cpuUsage;
    private Integer cpuCores;
    private List<CpuUsage> cpuCoreUsage;
    private List<DiskInfo> diskInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(Long memTotal) {
        this.memTotal = memTotal;
    }

    public Long getMemAvailable() {
        return memAvailable;
    }

    public void setMemAvailable(Long memAvailable) {
        this.memAvailable = memAvailable;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(Integer cpuCores) {
        this.cpuCores = cpuCores;
    }

    public List<DiskInfo> getDiskInfo() {
        return diskInfo;
    }

    public void setDiskInfo(List<DiskInfo> diskInfo) {
        this.diskInfo = diskInfo;
    }

    public Long getJvmMemTotal() {
        return jvmMemTotal;
    }

    public void setJvmMemTotal(Long jvmMemTotal) {
        this.jvmMemTotal = jvmMemTotal;
    }

    public Long getJvmMemAvailable() {
        return jvmMemAvailable;
    }

    public void setJvmMemAvailable(Long jvmMemAvailable) {
        this.jvmMemAvailable = jvmMemAvailable;
    }

    public Integer getJvmAvailableProcessors() {
        return jvmAvailableProcessors;
    }

    public void setJvmAvailableProcessors(Integer jvmAvailableProcessors) {
        this.jvmAvailableProcessors = jvmAvailableProcessors;
    }

    public Long getJvmMemMax() {
        return jvmMemMax;
    }

    public void setJvmMemMax(Long jvmMemMax) {
        this.jvmMemMax = jvmMemMax;
    }

    public Long getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(Long memUsed) {
        this.memUsed = memUsed;
    }

    public Long getMemSwapTotal() {
        return memSwapTotal;
    }

    public void setMemSwapTotal(Long memSwapTotal) {
        this.memSwapTotal = memSwapTotal;
    }

    public Long getMemSwapUsed() {
        return memSwapUsed;
    }

    public void setMemSwapUsed(Long memSwapUsed) {
        this.memSwapUsed = memSwapUsed;
    }

    public Long getMemSwapAvailable() {
        return memSwapAvailable;
    }

    public void setMemSwapAvailable(Long memSwapAvailable) {
        this.memSwapAvailable = memSwapAvailable;
    }

    public String getCpuVendor() {
        return cpuVendor;
    }

    public void setCpuVendor(String cpuVendor) {
        this.cpuVendor = cpuVendor;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public Integer getCpuMhz() {
        return cpuMhz;
    }

    public void setCpuMhz(Integer cpuMhz) {
        this.cpuMhz = cpuMhz;
    }

    public CpuUsage getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(CpuUsage cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public List<CpuUsage> getCpuCoreUsage() {
        return cpuCoreUsage;
    }

    public void setCpuCoreUsage(List<CpuUsage> cpuCoreUsage) {
        this.cpuCoreUsage = cpuCoreUsage;
    }

    public static class DiskInfo {
        private String devName;
        private String dirName;
        private Long flags;
        private String fs;
        private String type;
        private Long total;
        private Long free;
        private Long available;
        private Long used;

        public DiskInfo(){}
        public DiskInfo(String devName, String dirName, Long flags, String fs, String type, Long total, Long free, Long available, Long used) {
            this.devName = devName;
            this.dirName = dirName;
            this.flags = flags;
            this.fs = fs;
            this.type = type;
            this.total = total;
            this.free = free;
            this.available = available;
            this.used = used;
        }

        public String getDevName() {
            return devName;
        }

        public void setDevName(String devName) {
            this.devName = devName;
        }

        public String getDirName() {
            return dirName;
        }

        public void setDirName(String dirName) {
            this.dirName = dirName;
        }

        public Long getFlags() {
            return flags;
        }

        public void setFlags(Long flags) {
            this.flags = flags;
        }

        public String getFs() {
            return fs;
        }

        public void setFs(String fs) {
            this.fs = fs;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public Long getFree() {
            return free;
        }

        public void setFree(Long free) {
            this.free = free;
        }

        public Long getAvailable() {
            return available;
        }

        public void setAvailable(Long available) {
            this.available = available;
        }

        public Long getUsed() {
            return used;
        }

        public void setUsed(Long used) {
            this.used = used;
        }
    }

    public static class CpuUsage {
        private Double user;
        private Double sys;
        private Double idle;
        private Double wait;
        private Double nice;
        private Double irq;

        public CpuUsage() {

        }

        public CpuUsage(Double user, Double sys, Double idle, Double wait, Double nice, Double irq) {
            this.user = user;
            this.sys = sys;
            this.idle = idle;
            this.wait = wait;
            this.nice = nice;
            this.irq = irq;
        }

        public Double getUser() {
            return user;
        }

        public void setUser(Double user) {
            this.user = user;
        }

        public Double getSys() {
            return sys;
        }

        public void setSys(Double sys) {
            this.sys = sys;
        }

        public Double getIdle() {
            return idle;
        }

        public void setIdle(Double idle) {
            this.idle = idle;
        }

        public Double getWait() {
            return wait;
        }

        public void setWait(Double wait) {
            this.wait = wait;
        }

        public Double getNice() {
            return nice;
        }

        public void setNice(Double nice) {
            this.nice = nice;
        }

        public Double getIrq() {
            return irq;
        }

        public void setIrq(Double irq) {
            this.irq = irq;
        }
    }

}
