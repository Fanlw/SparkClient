package cn.bluethink.ecssparkClient.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.bluethink.ecssparkClient.entity.Job;


/**
 * Job的dao操作
 * @author flw
 * @date 2018年9月19日 
 */
public interface JobDao extends JpaRepository<Job, String>, JpaSpecificationExecutor<Job>{

}
