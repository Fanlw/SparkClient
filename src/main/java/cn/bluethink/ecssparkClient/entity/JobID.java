package cn.bluethink.ecssparkClient.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 存储任务ID和spark任务ID的对应
 * @author flw
 * @date 2018年9月19日 
 */
@Entity
public class JobID {

	@Id
	private String id;
	private String sparkid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSparkid() {
		return sparkid;
	}
	public void setSparkid(String sparkid) {
		this.sparkid = sparkid;
	}
	
}
