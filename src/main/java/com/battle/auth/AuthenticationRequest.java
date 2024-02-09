package com.battle.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AuthenticationRequest {
	
	@Email(message = "{user.emailid.invalid}")
    @NotNull(message = "{user.emailid.absent}")
    private String email;

    // @NotNull(message = "{user.password.absent}")
    //   @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",message = "{user.password.invalid}")

    @NotNull(message = "{user.password.absent}")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "{user.password.invalid}")
    private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthenticationRequest() {
		super();
	}
    
    
    
}
