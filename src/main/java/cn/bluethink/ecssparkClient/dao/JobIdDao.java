package cn.bluethink.ecssparkClient.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.bluethink.ecssparkClient.entity.JobID;

/**
 * 
 * @author flw
 * @date 2018年9月19日 
 */
public interface JobIdDao extends JpaRepository<JobID, String>{

}
