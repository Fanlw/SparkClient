package cn.bluethink.ecssparkClient.model;

/**
 * 任务状态
 * @author flw
 * @date 2018年9月11日 
 */
public class JobStatus {
    private String id;
    private Integer status;
    private String message;
    private Integer exitCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

}
