package com.battle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.battle.auth.AuthenticationRequest;
import com.battle.auth.AuthenticationResponse;
import com.battle.exception.BattleException;
import com.battle.payloads.RegisterRequest;
import com.battle.services.AuthenticationService;

import jakarta.validation.Valid;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/battle/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService; 
	
	@PostMapping("/signup")
	public ResponseEntity<?> register(	@Valid @RequestBody RegisterRequest request) throws BattleException{
		AuthenticationResponse tokenGenerated = authenticationService.register(request);
		return ResponseEntity.ok(tokenGenerated);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(
			@Valid @RequestBody AuthenticationRequest request
			) throws BattleException{
		return ResponseEntity.ok(authenticationService.authenticate(request));
		//return  ResponseEntity.ok("hello");
	}
}
