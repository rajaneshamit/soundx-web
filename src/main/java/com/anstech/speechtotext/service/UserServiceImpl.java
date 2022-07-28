package com.anstech.speechtotext.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anstech.speechtotext.controller.AuthController;
import com.anstech.speechtotext.entity.User;
import com.anstech.speechtotext.model.UserResponse;
import com.anstech.speechtotext.payload.ApiResponse;
import com.anstech.speechtotext.payload.JwtAuthenticationResponse;
import com.anstech.speechtotext.payload.LoginRequest;
import com.anstech.speechtotext.payload.SignUpRequest;
import com.anstech.speechtotext.repo.UserRepository;
import com.anstech.speechtotext.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider tokenProvider;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Override
	public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {

		logger.info(" Request firstname {}, password {}, email {},  ", signUpRequest.getFirstName(),
				signUpRequest.getPassword(), signUpRequest.getEmail());

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getMobile())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(),
				signUpRequest.getPassword(), signUpRequest.getMobile(), signUpRequest.getActive());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
		// .orElseThrow(() -> new AppException("User Role not set."));
		// RoleName user_role = RoleName.valueOf(signUpRequest.getRole());
		// Role userRole = roleRepository.findByName(user_role).orElseThrow(() -> new
		// AppException("User Role not set."));
		// user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);
		UserResponse userResponse = new UserResponse(result.getId(), result.getFirstName(), result.getLastName(),
				result.getMobile(), result.getEmail());

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{firstname}")
				.buildAndExpand(result.getFirstName()).toUri();
		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "User registered successfully", userResponse));

	}

	@Override
	public ResponseEntity<?> signIn(LoginRequest loginRequest) {

		// logger.info("login request ::"+loginRequest);
		User userObj = null;
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginRequest.getMobileOrEmail(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			System.out.println("invalid username and password");
			Map<String, String> map = new HashMap<>();
			map.put("loggedIn", "false");
			map.put("message", "invalid username and password");
			return new ResponseEntity(map, HttpStatus.OK);
		}
		Optional<User> user = this.userRepository.findByMobileOrEmail(loginRequest.getMobileOrEmail(),
				loginRequest.getMobileOrEmail());
		if (user.isPresent()) {
			userObj = user.get();
		}
		UserResponse userDetails = new UserResponse(userObj.getId(), userObj.getFirstName(), userObj.getLastName(),
				userObj.getMobile(), userObj.getEmail());

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse("true", jwt, "Bearer", userDetails));

	}

//	@Override
//	public ResponseUtil createUser(User user) {
//		this.passwordEncoder = new BCryptPasswordEncoder();
//		ResponseUtil responseUtil = new ResponseUtil();
//		UserServiceImpl serviceImpl = new UserServiceImpl();
//		if (serviceImpl.isEmpty(user, responseUtil)) {
//			return responseUtil;
//		}
//		if (userRepository.existsByUserEmail(user.getUserEmail())) {
//			responseUtil.setMesg("email already exist");
//			return responseUtil;
//		}
//		if (!user.getPassword().equals(user.getConfPassword())) {
//			responseUtil.setMesg("password does not match");
//			return responseUtil;
//		}
//		if (user.getPassword().equals(user.getConfPassword())) {
//			String passowrd = this.passwordEncoder.encode(user.getPassword());
//			String congPassword = this.passwordEncoder.encode(user.getConfPassword());
//			user.setPassword(passowrd);
//			user.setConfPassword(congPassword);
//			userRepository.save(user);
//			responseUtil.setObject(user);
//			responseUtil.setMesg("created successfully");
//			return responseUtil;
//		}
//
//		return responseUtil;
//
//	}
//
//	public boolean isEmpty(User user, ResponseUtil responseUtil) {
//		boolean f = false;
//		if (user.getUserFirstName().trim() == "" || user.getUserFirstName() == null) {
//			responseUtil.setMesg("first name is required");
//			return f = true;
//		}
//		if (user.getUserLastName() == null || user.getUserLastName().trim() == "") {
//			responseUtil.setMesg("last name is required");
//			return f = true;
//		}
//		if (user.getPassword() == null || user.getPassword().trim() == "") {
//			responseUtil.setMesg("password is required");
//			return f = true;
//		}
//		if (user.getConfPassword() == null || user.getConfPassword().trim() == "") {
//			responseUtil.setMesg("password is required");
//			return f = true;
//		}
//		if (user.getUserEmail() == null || user.getUserEmail().trim() == "") {
//			responseUtil.setMesg("email is required");
//			return f = true;
//		}
//		return f;
//	}
//
//	@Override
//	public void removeUser() {
//		
//	}
}
