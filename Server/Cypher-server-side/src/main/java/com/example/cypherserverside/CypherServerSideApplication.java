package com.example.cypherserverside;
import com.example.cypherserverside.service.collect.SysDataServer;
import com.example.cypherserverside.service.impl.SysDataServerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.*;


@SpringBootApplication
public class CypherServerSideApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(CypherServerSideApplication.class, args);
	}
}
