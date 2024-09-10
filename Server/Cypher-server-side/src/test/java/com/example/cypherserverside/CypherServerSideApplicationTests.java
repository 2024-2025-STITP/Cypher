package com.example.cypherserverside;

import com.auth0.jwt.interfaces.Claim;
import com.example.cypherserverside.pojo.User;
import com.example.cypherserverside.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Map;

@SpringBootTest
class CypherServerSideApplicationTests {

	@Test
	void contextLoads2() {
		User user = new User();
		user.setUserId(12);
		user.setEmail("sfd");
		user.setRole(new Role("costum", new ArrayList<>()));
		user.setUserName("admin");
		user.setSaltPassword("admin");

		String token = JwtUtils.GeneateToken(user);
		Map<String, Claim> tokenObject = JwtUtils.getTokenObject(token);
		System.out.println(tokenObject.get("userName").asString());
	}

}
