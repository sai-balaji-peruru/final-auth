package com.cognizant.repository.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.model.User;
import com.cognizant.repository.UserRepository;


@SpringBootTest()
public class AuthRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Mock
	UserRepository userRepository;
	
	
	@Test
	public void findByUserNameTest() {
	

		User user = new User(1,"Lakshmi","pwd2");
		userRepository.save(user);
		
		when(userRepository.findByUserName("Lakshmi")).thenReturn(user);
		assertEquals(userRepository.findByUserName("Lakshmi"),user);
		logger.info("Test is running");
	}
}