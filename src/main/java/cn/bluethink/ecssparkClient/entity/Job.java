package cn.bluethink.ecssparkClient.entity;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bluethink.ecssparkClient.model.Argument;
import cn.bluethink.ecssparkClient.model.JobInfo;

/**
 * @author flw
 * @date 2018年9月6日 
 */
@Entity
public class Job {
    /** 任务结束标识 */
    public final static int STATUS_COMPLETED = 1;
    /** 任务未结束标识 */
    public final static int STATUS_UNFINISHED = 2;
    /** 等待执行，若当前执行器负载过重，任务将处于等待执行状态 */
    public final static int STATUS_WAIT = 4 | STATUS_UNFINISHED;
    /** 准备中，已经提交到执行器，执行器在做执行前准备，例如工具准备（可能需要下载）*/
    public final static int STATUS_PREPARING = 8 | STATUS_UNFINISHED;
    /** 执行中，执行器正在执行该任务*/
    public final static int STATUS_RUNNING = 16 | STATUS_UNFINISHED;
    /** 完成，任务执行成功*/
    public final static int STATUS_FINISHED = 32 | STATUS_COMPLETED;
    /** 执行失败*/
    public final static int STATUS_FAILED = 64 | STATUS_COMPLETED;
    /** 被杀死，可能用户或管理员主动终止任务执行*/
    public final static int STATUS_KILLED = 128 | STATUS_COMPLETED;
    /** 任务等待执行器*/
    public final static int STATUS_WAIT_FOR_EXECUTOR = 256 | STATUS_UNFINISHED;
    /** 任务已提交*/
    public final static int STATUS_SUBMITTED = 512 | STATUS_UNFINISHED;

    @Id
//    @GenericGenerator(name="uuid", strategy="org.hibernate.id.UUIDGenerator")
//    @GeneratedValue(generator="uuid")
    private String id;
    private String name;
    private String toolId;
    private String toolName;
    private String toolUrl;
    private String type;
    private Integer priority;
    @Column(columnDefinition="text") 
    private String contextInfo;
    @Column(columnDefinition="text") 
    private String args;
    private String user;
    private Integer retry;
    private Integer status;
    private String message;
    private Integer exitCode;
    private Date submitTime;
    // 任务执行失败次数，次数越多排序越靠后
    private Integer failTimes;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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

    public String getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(String contextInfo) {
        this.contextInfo = contextInfo;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
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

    public Integer getFailTimes() {
        return failTimes;
    }

    public void setFailTimes(Integer failTimes) {
        this.failTimes = failTimes;
    }

    public void from(JobInfo jobInfo) {
        ObjectMapper mapper = new ObjectMapper();
        name = jobInfo.getName();
        toolId = jobInfo.getToolId();
        toolName = jobInfo.getToolName();
        toolUrl = jobInfo.getToolUrl();
        type = jobInfo.getType();
        status = jobInfo.getStatus();
        message = jobInfo.getMessage();
        exitCode = jobInfo.getExitCode();
        try {
            contextInfo = mapper.writeValueAsString(jobInfo.getContext());
            args = mapper.writeValueAsString(jobInfo.getArgs());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        user = jobInfo.getUser();
        retry = jobInfo.getRetry();
    }

    public JobInfo to() {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setId(id);
        jobInfo.setName(name);
        jobInfo.setToolId(toolId);
        jobInfo.setToolName(toolName);
        jobInfo.setToolUrl(toolUrl);
        jobInfo.setType(type);
        jobInfo.setPriority(priority);
        jobInfo.setSubmitTime(submitTime);
        jobInfo.setRetry(retry);
        jobInfo.setExitCode(exitCode);
        jobInfo.setStatus(status);
        jobInfo.setMessage(message);
        ObjectMapper mapper = new ObjectMapper();
        if (contextInfo != null) {
            try {
                jobInfo.setContext(mapper.readValue(contextInfo, new TypeReference<HashMap<String, ?>>(){}));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (args != null) {
            try {
                jobInfo.setArgs(mapper.readValue(args, new TypeReference<List<Argument>>(){}));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jobInfo;
    }
}
