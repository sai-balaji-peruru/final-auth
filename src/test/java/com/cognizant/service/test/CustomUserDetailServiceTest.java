package com.cognizant.service.test ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cognizant.model.User;
import com.cognizant.repository.UserRepository;
import com.cognizant.service.CustomUserDetailService;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomUserDetailServiceTest {

	
	@Mock
	private UserRepository userRepository;

	@Mock
	private CustomUserDetailService service;
	
	@Test
	public void loadUserByUserNameTest() {

		UserDetails userDetails = new org.springframework.security.core.userdetails.User("sai","peruru",new ArrayList<>());
		when(service.loadUserByUsername("peruru")).thenReturn(userDetails);
		assertThat(service.loadUserByUsername("peruru")).isNotNull();
		assertEquals(service.loadUserByUsername("peruru"),userDetails);
	}	
	
}
