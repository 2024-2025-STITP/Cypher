package com.example.cypherserverside;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.cypherserverside.entity.ProcessData;
import com.example.cypherserverside.entity.SysData;
import com.example.cypherserverside.service.collect.SysDataServer;
import com.example.cypherserverside.utils.SaltMD5Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CypherServerSideApplication {

	public static void main(String[] args) {

		SpringApplication.run(CypherServerSideApplication.class, args);

//				Thread thread = new Thread(() -> {
//			while(true){
//				SysData sysData = SysDataServer.collectData();
//				System.out.println("UpTime: "+ sysData.getUptime());
//				System.out.println("Usage: " + sysData.getCpuUsage() + "%");
//				System.out.println("DiskWriteBytes: " + sysData.getDiskWriteBytes()*1.0/1024/1024);
//				System.out.println("TotalMemory: " + sysData.getTotalMemory()*1.0/1024/1024/1024);
//				System.out.println("DiskReadByets: " + sysData.getDiskReadBytes()*1.0/1024/1024+"\n");
//				for (ProcessData p : sysData.getProcesses())
//				{
//					System.out.print(p.getPName() + ":" + p.getProcessCpuUsage() + " ");
//				}
//				try {
//					System.out.flush();
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					throw new RuntimeException(e);
//				}
//
//			}
//		});
//		thread.start();
	}

}
