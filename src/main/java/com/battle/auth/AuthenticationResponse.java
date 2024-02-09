package com.battle.auth;


public class AuthenticationResponse {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AuthenticationResponse(String token) {
		super();
		this.token = token;
	}

	public AuthenticationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
