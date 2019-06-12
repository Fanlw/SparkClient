package cn.bluethink.ecssparkClient.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ywilkof.sparkrestclient.ClusterMode;
import com.github.ywilkof.sparkrestclient.FailedSparkRequestException;
import com.github.ywilkof.sparkrestclient.JobStatusResponse;
import com.github.ywilkof.sparkrestclient.SparkRestClient;
import com.github.ywilkof.sparkrestclient.interfaces.JobSubmitRequestSpecification;

import cn.bluethink.ecssparkClient.dao.JobIdDao;
import cn.bluethink.ecssparkClient.entity.JobID;
import cn.bluethink.ecssparkClient.model.Argument;
import cn.bluethink.ecssparkClient.model.ExecutorStatus;
import cn.bluethink.ecssparkClient.model.JobInfo;
import cn.bluethink.ecssparkClient.model.SparkInfo;
import cn.bluethink.ecssparkClient.model.workerInfo;

/**
 * cluster模式
 * @author flw
 * @date 2018年9月6日 
 */
@Service
public class JobExecutor{
    @Value("${esc.Host.IP}")
    private String HostIP;
    @Value("${esc.Host.port}")
    private String port;   
    @Value("${esc.spark.Version}")
    private String sparkVersion;    
    @Value("${esc.spark.WebUIPort}")
    private String sparkWebUIPort;   
    @Autowired
    private JobIdDao jobidDao;
	//执行提交的任务
	public String executor(JobInfo job) throws FailedSparkRequestException {
		String submissionId="";//任务ID
		JobSubmitRequestSpecification jobSubmitSpec = SparkRestClientCreate().prepareJobSubmit()
				.appName(job.getName())
				.mainClass(job.getContext().get("mainClass").toString())//不确定这个主函数怎么命名
				.appResource(job.getToolUrl());
		//设置环境参数
		if(job.getContext().size()>0) {				
			if(job.getContext().containsKey("num-executors")) {
				jobSubmitSpec.withProperties().put("spark.num.executors",job.getContext().get("num-executors").toString());
			}
			if(job.getContext().containsKey("executor-memory")) {
				jobSubmitSpec.withProperties().put("spark.executor.memory",job.getContext().get("executor-memory").toString());
			}
			if(job.getContext().containsKey("executor-cores")) {
				jobSubmitSpec.withProperties().put("spark.executor.cores",job.getContext().get("executor-cores").toString());
			}
			if(job.getContext().containsKey("driver-memory")) {
				jobSubmitSpec.withProperties().put("spark.driver.memory",job.getContext().get("driver-memory").toString());
			}
            if(job.getContext().containsKey("spark.default.parallelism")) {
            	//并行度
            	jobSubmitSpec.withProperties().put("spark.default.parallelism",job.getContext().get("spark.default.parallelism").toString());
            }
		}	    
		//设置运行参数
	    if(job.getArgs().size()>0) {
	    	List<String> args = new ArrayList<>();
	    	for(Argument arg:job.getArgs()) {
	    		if(arg.getPaired()) {
		    		args.add(arg.getName()+" "+arg.getValue());
	    		}else {
		    		args.add(arg.getValue());
	    		}
	    	}
	    	jobSubmitSpec.appArgs(args);
 	    }
		//提交任务
		submissionId = jobSubmitSpec.submit();
		JobID jobid = new JobID();
		jobid.setId(job.getId());
		jobid.setSparkid(submissionId);
		jobidDao.save(jobid);
		return submissionId;
	}
	//终止任务
	public String kill(String submissionId) throws FailedSparkRequestException {
		Boolean execResult = false;
		if(jobidDao.existsById(submissionId)) {
			execResult = SparkRestClientCreate().killJob()
					.withSubmissionId(jobidDao.getOne(submissionId).getSparkid());
		}
		return execResult.toString();
	}
	//获取任务状态
	public JobStatusResponse getStatu(String submissionId) throws FailedSparkRequestException {
		JobStatusResponse statuResult =null;
		if(jobidDao.existsById(submissionId)) {
			statuResult = SparkRestClientCreate().checkJobStatus()
					.withSubmissionIdFullResponse(jobidDao.getOne(submissionId).getSparkid());
		}

		return statuResult;
	}
	//获取任务运行日志
	public List<String> getLogs(String submissionId) throws FailedSparkRequestException, IOException {
		List<String> res = new ArrayList<>();	
		if(jobidDao.existsById(submissionId)) {
			//获取worker的ui信息
			SparkInfo sparkInfo = getInfo();
			List<workerInfo> workersList = sparkInfo.getWorkers();
			for(workerInfo worker:workersList) {
				String id = worker.getId();
				String webuiaddress = worker.getWebuiaddress();
				Utils.workerWebUiAddress.put(id, webuiaddress);
			}
			//获取日志
			String id = jobidDao.getOne(submissionId).getSparkid();
			JobStatusResponse jobResp = getStatu(submissionId);
			Optional<String> wokerName = jobResp.getWorkerId();
			String workerUi = Utils.workerWebUiAddress.get(wokerName.get());
			String urlStdOut= workerUi+"/logPage/?driverId="+id+"&logType=stdout";//stdout日志
			String urlStdErr = workerUi+"/logPage/?driverId="+id+"&logType=stderr";//stderr日志
			Document docErr = Jsoup.connect(urlStdErr).get();
			Elements elementsErr = docErr.select("pre");
			String[] temp = elementsErr.text().split("\n");
			for(String ss:temp) {
				res.add(ss);
			}
			Document docOut = Jsoup.connect(urlStdOut).get();
			Elements elementsOut = docOut.select("pre");
			temp = elementsOut.text().split("\n");
			for(String ss:temp) {
				res.add("stdout-输出信息:"+ss);
			}
		}else {
			res.add(new Date().toString()+"-未找到该对象的日志信息");
		}
		return res;
	}

	//获取环境的运行状态
	public SparkInfo getInfo() throws ClientProtocolException, IOException {
		SparkInfo sinfo=null;
		String url = "http://"+HostIP+":"+sparkWebUIPort+"/json/";
		HttpGet get = new HttpGet(url);
		HttpClient client = HttpClientBuilder.create()
				.setConnectionManager(new BasicHttpClientConnectionManager()).build();
		final String stringResponse = client.execute(get, new BasicResponseHandler());
		ObjectMapper mapper = new ObjectMapper();
		sinfo = mapper.readValue(stringResponse, SparkInfo.class);
		return sinfo;
	}
	//获取负载情况
	public ExecutorStatus getStatus() throws ClientProtocolException, IOException {
		ExecutorStatus status = new ExecutorStatus();
		SparkInfo sinfo = getInfo();
		String cores = sinfo.getCores();
		String coresUsed = sinfo.getCoresused();
		String memory = sinfo.getMemory();
		String memoryUsed = sinfo.getMemoryused();
		Long coresNum = Long.parseLong(cores);
		Long coresUsedNum = Long.parseLong(coresUsed);
		Long memoryNum = Long.parseLong(memory);
		Long memoryUserNum = Long.parseLong(memoryUsed);
		Integer coresUsedRatio = (int) ((coresUsedNum/coresNum)*100);
		Integer memoryUsedRatio =(int)((memoryUserNum/memoryNum)*100);
		status.setName("spark-standalone");
		status.setOs("spark");
		status.setRunningJob(sinfo.getActiveapps().size());
		status.setLoad((double)(coresUsedRatio>memoryUsedRatio?coresUsedRatio:memoryUsedRatio));
		//return coresUsedRatio>memoryUsedRatio?coresUsedRatio:memoryUsedRatio;
		return status;
	}
	//创建任务提交方法
	private SparkRestClient SparkRestClientCreate() {
		SparkRestClient sparkRestClient = SparkRestClient.builder()
				.sparkVersion(sparkVersion)//版本号
				.masterHost(HostIP)//集群master的host
				.masterPort(Integer.parseInt(port))//REST 端口
				.clusterMode(ClusterMode.spark)
				.build();//模式
		return sparkRestClient;
	}
	
	

}
