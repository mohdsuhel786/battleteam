package com.battle.payloads;


import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RegisterRequest {

		@NotNull(message = "{user.name.absent}")
	    @Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*", message = "{user.name.invalid}")
	    private String name;

	    @Email(message = "{user.emailid.invalid}")
	    @NotNull(message = "{user.emailid.absent}")
	    private String email;
	    
	    private Set<String> role;
	
	    @NotNull(message = "password")
	    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "{user.password.invalid}")
	    private String password;
	 

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }


	    

	    public RegisterRequest() {
			super();
		}

		public void setPassword(String password) {
	        this.password = password;
	    }

	    //  @JsonIgnore
	    public String getPassword() {
	        return password;
	    }

	    public Set<String> getRole() {
	        return this.role;
	      }

	      public void setRole(Set<String> role) {
	        this.role = role;
	      }
}
