package cn.bluethink.ecssparkClient.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ywilkof.sparkrestclient.DriverState;
import com.github.ywilkof.sparkrestclient.FailedSparkRequestException;
import com.github.ywilkof.sparkrestclient.JobStatusResponse;

import cn.bluethink.ecssparkClient.dao.JobDao;
import cn.bluethink.ecssparkClient.entity.Job;
import cn.bluethink.ecssparkClient.model.ExecutorInfo;
import cn.bluethink.ecssparkClient.model.ExecutorStatus;
import cn.bluethink.ecssparkClient.model.JobAchievementInfo;
import cn.bluethink.ecssparkClient.model.JobFilter;
import cn.bluethink.ecssparkClient.model.JobInfo;
import cn.bluethink.ecssparkClient.model.JobStatus;
import cn.bluethink.ecssparkClient.model.PageView;
import cn.bluethink.ecssparkClient.model.SparkInfo;
/**
 * 
 * @author flw
 * @date 2018年9月6日 
 */
@Service
public class JobScheduler{

	@Autowired
	private JobDao jobDao;
    @Autowired
    private JobExecutor jobExecutor;
    @Value("${esc.hadoop.hdfsUrl}")
    private String HDFSUrl;
	//处理提交的任务
	public String submit(JobInfo job){
	    Job newJob = new Job();
        newJob.from(job);
        newJob.setId(job.getId());
        newJob.setSubmitTime(new Date());
        newJob.setStatus(Job.STATUS_SUBMITTED);//更改状态为已提交
		if(job.getContext()==null||!job.getContext().containsKey("mainClass")) {
			newJob.setMessage("ERROR:没有提供可运行的主函数");
			newJob.setStatus(Job.STATUS_FAILED);
			jobDao.save(newJob);
			return job.getId();
		}
		if(job.getName()==null||job.getName().equals("")) {
			newJob.setMessage("ERROR:任务'name'属性不能为空");
			newJob.setStatus(Job.STATUS_FAILED);
			jobDao.save(newJob);
			return job.getId();
		}
		if(job.getToolUrl().isEmpty()) {
			newJob.setMessage("ERROR:工具地址不能为空");
			newJob.setStatus(Job.STATUS_FAILED);
			jobDao.save(newJob);
			return job.getId();
		}
		jobDao.save(newJob);
        try {
			jobExecutor.executor(job);
			newJob.setStatus(Job.STATUS_RUNNING);			
		} catch (FailedSparkRequestException e) {
			e.printStackTrace();
			newJob.setMessage(e.getMessage());
			newJob.setStatus(Job.STATUS_FAILED);
		}finally {
			jobDao.save(newJob);
		}
        return job.getId();
	}
	//终止任务
	public String terminate(String jobId) {
		String res="boolean";
		try {
			res = jobExecutor.kill(jobId);			
			Job jobinfo = jobDao.getOne(jobId);
			jobinfo.setStatus(Job.STATUS_KILLED);
			jobDao.save(jobinfo);
		} catch (FailedSparkRequestException e) {
			e.printStackTrace();
			Job jobinfo = jobDao.getOne(jobId);
			jobinfo.setMessage("终止任务失败："+e.getMessage());
			jobDao.save(jobinfo);
			res ="ERROR";
		}
		return res;
	}
	
	//任务重试
	public String retry(String jobId) {
		if(jobDao.existsById(jobId)) {
			Job curJob = jobDao.getOne(jobId);
			return submit(curJob.to());
		}
		else {			
			return "Error:不存在该任务-"+jobId;
		}
	}
	
	//任务状态查询
	public JobStatus status(String jobId) {
		 JobStatus jobstatus = new JobStatus();
		 JobStatusResponse rep;
		 if(jobDao.existsById(jobId)) {
			 Job job = jobDao.getOne(jobId);
			 try {
				 rep = jobExecutor.getStatu(jobId);
				 if(rep!=null&&rep.getSuccess()==true) {
					 jobstatus.setId(jobId);
					 DriverState state = rep.getDriverState();
					 Integer stateCode;
					 if(state==DriverState.ERROR) {//运行错误
						 stateCode=6;
						 job.setStatus(Job.STATUS_FAILED);
					 }else if(state==DriverState.FAILED) {//任务失败
						 stateCode=4;
						 job.setStatus(Job.STATUS_FAILED);
					 }else if(state==DriverState.FINISHED) {//任务完成
						 stateCode=3;
						 job.setStatus(Job.STATUS_FINISHED);
					 }else if(state==DriverState.KILLED) {//被终止的任务
						 stateCode=5;
						 job.setStatus(Job.STATUS_KILLED);
					 }else if(state==DriverState.RELAUNCHING) {//重试
						 stateCode=1;
						 job.setStatus(Job.STATUS_SUBMITTED);
					 }else if(state==DriverState.RUNNING) {//运行
						 stateCode=2;
						 job.setStatus(Job.STATUS_RUNNING);
					 }else if(state==DriverState.SUBMITTED) {//提交
						 stateCode=1;
						 job.setStatus(Job.STATUS_SUBMITTED);
					 }else{
						 stateCode=6;
						 job.setStatus(Job.STATUS_FAILED);
					 }
					 jobstatus.setStatus(stateCode);
					 jobstatus.setMessage("");//unknown
					 jobstatus.setExitCode(0);//unknown
				 }else {
					 job.setStatus(Job.STATUS_FAILED);
					 jobstatus.setId(jobId);
					 jobstatus.setStatus(6);
					 jobstatus.setMessage("");//unknown
					 jobstatus.setExitCode(0);//unknown
				 }
				 jobDao.save(job);
			 } catch (FailedSparkRequestException e) {
				 e.printStackTrace();
				 jobstatus.setId(jobId);
				 jobstatus.setStatus(6);
				 jobstatus.setMessage(e.getMessage());
			 }
		 }else {
			 //处理方法不确定
		 }
		 return jobstatus;
	}
	//获取任务的执行结果
	public JobAchievementInfo achievement(String jobId)  throws IOException{
		Job job = jobDao.getOne(jobId);
		JobAchievementInfo jobachinfo;
		status(jobId);//更新任务状态
        if (job.getStatus() != Job.STATUS_FINISHED) {
            throw new RuntimeException("任务未执行成功，无法获取成果信息");
        }
        Configuration conf = new Configuration();
        String hdfsUrl =HDFSUrl+"/usrData/"+jobId+"/manifest.json";//
		String res ="";
		FileSystem fs = FileSystem.get(URI.create(hdfsUrl), conf);
		ObjectMapper mapper = new ObjectMapper();
		if(fs.exists(new Path(hdfsUrl))) {//判断该文件是否存在
			FSDataInputStream hdfsInStream = fs.open(new Path(hdfsUrl));
			InputStreamReader stmReader = new InputStreamReader(hdfsInStream);
			BufferedReader bfReader = new BufferedReader(stmReader);
			String line = bfReader.readLine();
			
			while(line!=""&&line!=null) {
				res+=line;
				line =bfReader.readLine();
			}
			jobachinfo = mapper.readValue(res, JobAchievementInfo.class);
		}else {
			jobachinfo = new JobAchievementInfo();
		}
		return jobachinfo;
	}
	//获取任务日志
	public List<String> logs(String jobId) {
		List<String> res = new ArrayList<>();
		String logs ="";
		if(jobDao.existsById(jobId)) {
			try {
				res=jobExecutor.getLogs(jobId);
			} catch (FailedSparkRequestException | IOException e) {
				e.printStackTrace();
				res.add(new Date().toString()+"-Error:获取日志失败===>>>"+e.getMessage());
			}
		}
		return res;
	}
	//获取计算环境信息
	public ExecutorInfo info() {
		ExecutorInfo excInfo = new ExecutorInfo();
		excInfo.setOs("Linux");
		excInfo.setType("distribute/spark");
		String resInfo ="";
		SparkInfo sinfo;
		try {
			sinfo = jobExecutor.getInfo();
			resInfo = "url:"+sinfo.getUrl()+"\r\n";
			resInfo += "worker数："+sinfo.getAliveworkers()+"\r\n";
			resInfo += "内存："+sinfo.getMemory()+"\r\n";
			resInfo += "已用内存："+sinfo.getMemoryused()+"\r\n";
			resInfo += "内核数："+sinfo.getCores()+"\r\n";
			resInfo += "已使用内核数："+sinfo.getCoresused()+"\r\n";
			excInfo.setCpuCores(Integer.parseInt(sinfo.getCores()));
			excInfo.setMemTotal(Long.parseLong(sinfo.getMemory()));
			excInfo.setMemUsed(Long.parseLong(sinfo.getMemoryused()));
			excInfo.setMemAvailable(Long.parseLong(sinfo.getMemory())-Long.parseLong(sinfo.getMemoryused()));
		} catch (IOException e) {
			e.printStackTrace();
			resInfo += e.getMessage();
		}
		return excInfo;
	}
	//获取计算环境的负载情况
	public ExecutorStatus status() {
		ExecutorStatus status = new ExecutorStatus();
		try {
			status = jobExecutor.getStatus();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}
	//根据筛选条件进行查询
	public PageView<JobInfo> jobs(JobFilter filter) {
        int page = filter.getPage() == null ? 0 : filter.getPage();
        int pageSize = filter.getPageSize() == null ? 15 : filter.getPageSize();
        String[] orderBy = (filter.getOrderBy() == null || filter.getOrderBy().isEmpty()) ? new String[]{"id"} : filter.getOrderBy().toArray(new String[]{});
        Sort.Direction sort = filter.getAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, pageSize, sort, orderBy);
        Page<Job> jobs = jobDao.findAll((Root<Job> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Predicate p = null;
            if (filter.getIds() != null && !filter.getIds().isEmpty()) {
                p = root.get("id").in(filter.getIds());
            }
            if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                Predicate predicate = root.get("status").in(filter.getStatus());
                p = p == null ? predicate : criteriaBuilder.and(p, predicate);
            }
            if(filter.getSubmitBegin() != null) {
                Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("submitTime").as(Date.class), filter.getSubmitBegin());
                p = p == null ? predicate : criteriaBuilder.and(p, predicate);
            }
            if(filter.getSubmitEnd() != null) {
                Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("submitTime").as(Date.class), filter.getSubmitEnd());
                p = p == null ? predicate : criteriaBuilder.and(p, predicate);
            }
            if (filter.getToolNames() != null && !filter.getToolNames().isEmpty()) {
                Predicate predicate = root.get("toolName").in(filter.getToolNames());
                p = p == null ? predicate : criteriaBuilder.and(p, predicate);
            }
            if (filter.getName() != null && !filter.getName().isEmpty()) {
                Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%");
                p = p == null ? predicate : criteriaBuilder.and(p, predicate);
            }
            return p;
        }, pageable);
        List<JobInfo> jobInfoList = new ArrayList<>(jobs.getContent().size());
        for (Job job : jobs) {
            jobInfoList.add(job.to());
        }
        return new PageView<>(jobInfoList, pageable, jobs);
	}
}
