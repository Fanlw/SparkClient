package cn.bluethink.ecssparkClient.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author flw
 * @date 2018年9月6日 
 */
public class JobInfo {
    private String id;
    private Integer execId;
    private String name;
    private String toolId;
    private String toolName;
    private String toolUrl;

    // 任务优先级，数字越大优先级越低
    private Integer priority;
    // 描述任务的类型，可以为以下几种
    // distribute/spark, distribute/hadoop, distribute/flink, standalone/windows, standalone/linux, standalone/mac
    private String type;

    // 任务重试模式
    // -1 or null:不允许重试，默认重试行为
    // 0:无限重试
    // n(n>=1):重试n次
    private Integer retry;

    // 描述任务执行上下文相关参数，例如：
    // SPARK:
    // ["mainClass":"xxx", "master":"yarn", "deployMode":"client"]
    // STANDALONE:
    // ["category":"java|python|command|app", "os.name":"windows 10", "os.arc":"amd64"]
    private HashMap<String, ?> context = new HashMap<>();

    // 执行参数表
    private List<Argument> args = new ArrayList<>();

    private String user;

    private Date submitTime;

    private Integer exitCode;
    private String message;
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getExecId() {
        return execId;
    }

    public void setExecId(Integer execId) {
        this.execId = execId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolUrl() {
        return toolUrl;
    }

    public void setToolUrl(String toolUrl) {
        this.toolUrl = toolUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, ?> getContext() {
        return context;
    }

    public void setContext(HashMap<String, ?> context) {
        this.context = context;
    }

    public List<Argument> getArgs() {
        return args;
    }

    public void setArgs(List<Argument> args) {
        this.args = args;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
