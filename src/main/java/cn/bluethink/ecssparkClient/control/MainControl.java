package cn.bluethink.ecssparkClient.control;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.bluethink.ecssparkClient.model.JobFilter;
import cn.bluethink.ecssparkClient.model.JobInfo;
import cn.bluethink.ecssparkClient.service.JobScheduler;



/**
 * @author flw
 * @date 2018年9月6日 
 */
@RestController
public class MainControl {

    //查询
    @PostMapping(value = "/jobs")
    public HttpEntity jobs(@RequestBody JobFilter filer) throws IOException {
    	System.out.println("执行jobs");
        return new ResponseEntity<>(scheduler.jobs(filer), HttpStatus.OK);
    }
	//任务提交接口
    @Autowired
    private JobScheduler scheduler;
    @PostMapping(value = "/jobs/submission")
    public String submit(@RequestBody JobInfo job) throws IOException, InterruptedException {
        return scheduler.submit(job);
    }
    //任务终止
    @PostMapping(value = "/jobs/{job}/terminate")
    public String terminate(@PathVariable(name = "job") String job) {
        return scheduler.terminate(job);
    }
    //任务重试
    @PostMapping(value = "/jobs/{job}/retry")
    public HttpEntity retry(@PathVariable(name = "job") String job) throws IOException {
        return new HttpEntity<>(scheduler.retry(job));
    }

    //任务状态查询
    @GetMapping(value = "/jobs/{job}/status")
    public HttpEntity status(@PathVariable(name = "job") String job) {
        return new HttpEntity<>(scheduler.status(job));
    }
    //获取任务的执行结果
    @GetMapping(value = "/jobs/{job}/achievement")
    public HttpEntity achievement(@PathVariable(name = "job") String job) throws IOException {
        return new HttpEntity<>(scheduler.achievement(job));
    }
    //获取任务日志
    @GetMapping(value = "/jobs/{job}/logs")
    public HttpEntity logs(@PathVariable(name = "job") String job) {
        return new HttpEntity<>(scheduler.logs(job));
    }
    //获取计算环境信息
    @GetMapping(value = "/info")
    public HttpEntity info(){
        return new HttpEntity<>(scheduler.info());
    }
    
    //获取计算环境的负载情况
    @GetMapping(value ="/status")
    public HttpEntity status(){
    	 return new HttpEntity<>(scheduler.status());
    }
    
}
