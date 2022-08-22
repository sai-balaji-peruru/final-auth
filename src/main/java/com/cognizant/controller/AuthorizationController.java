package com.cognizant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.exception.ResourceNotFound;
import com.cognizant.model.AuthRequest;
import com.cognizant.model.User;
import com.cognizant.service.CustomUserDetailService;
import com.cognizant.util.JwtUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthorizationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailService userDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;


	//testing message 
	
	@GetMapping("/test")
	public ResponseEntity<String> Test() {
		return ResponseEntity.ok("Spring boot application is working");
	}

	//jwt token authentication using user name and password
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		LOGGER.info("STARTED - generateToken");
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

		} catch (Exception e) {
			LOGGER.error("EXCEPTION - generateToken");
			throw new ResourceNotFound("user not found");
		}

		LOGGER.info("END - generateToken");
		return ResponseEntity.ok(jwtUtil.generateToken(authRequest.getUserName()));
	}
	
	@GetMapping("/current-user")
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = new User();
			user.setUserName(authentication.getName());
			
			return user;
		}
		return null;
	}
	//validtiion of the generated jwt token to access '/authorize' endpoint

	@GetMapping("/authorize")
	public ResponseEntity<?> authorization(@RequestHeader("Authorization") String token1) {

		LOGGER.info("STARTED - authorization");
		String token = token1.substring(7);

		UserDetails user = userDetailService.loadUserByUsername(jwtUtil.extractUsername(token));

		if (jwtUtil.validateToken(token, user)) {
			LOGGER.info("END - authorization");
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			LOGGER.info("END - authorization");
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}

	}

}
