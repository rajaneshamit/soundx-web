package com.anstech.speechtotext.service;

import org.springframework.http.ResponseEntity;
import com.anstech.speechtotext.payload.LoginRequest;
import com.anstech.speechtotext.payload.SignUpRequest;

public interface UserService {

	public ResponseEntity<?> signUp(SignUpRequest signUpRequest);

	public ResponseEntity<?> signIn(LoginRequest loginRequest);
}
