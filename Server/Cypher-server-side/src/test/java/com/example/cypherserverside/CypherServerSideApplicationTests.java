package com.example.cypherserverside;

import com.example.cypherserverside.service.collect.SysDataServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CypherServerSideApplicationTests {

	@Test
	void contextLoads() {
		Thread thread = new Thread(() -> {
			while(true){
				SysDataServer.collectData();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
		});
		thread.start();
	}

}
