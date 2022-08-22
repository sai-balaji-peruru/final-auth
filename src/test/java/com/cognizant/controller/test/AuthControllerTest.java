package com.cognizant.controller.test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cognizant.controller.AuthorizationController;
import com.cognizant.model.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Mock
	private AuthorizationController authorizationController;
	

	
	
	@Test
	public void contextLoads() {
		assertNotNull(authorizationController);
	}
	
	
	@Test
	public void loginTestSuccess() throws Exception {

		AuthRequest user = new AuthRequest("Prasanna", "pwd1");

		when(authorizationController.generateToken(user)).thenReturn(ResponseEntity.ok("200"));
		ResponseEntity<String> authenticationResponse = authorizationController.generateToken(user);

		assertEquals(HttpStatus.OK, authenticationResponse.getStatusCode());

	}
}