package cn.bluethink.ecssparkClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author flw
 * @date 2018年9月6日 
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class EcsSparkClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcsSparkClientApplication.class, args);
	}
}
