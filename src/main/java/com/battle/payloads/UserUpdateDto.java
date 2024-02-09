package com.battle.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserUpdateDto {

	
    private Long userId;

    @NotNull(message = "{user.name.absent}")
 	@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{user.name.invalid}")
    private String name;

    private String userImageName;
    private Long bgmiId;
    @Email(message = "{user.emailid.invalid}")
    @NotNull(message = "{user.emailid.absent}")
    private String email;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public String getUserImageName() {
		return userImageName;
	}

	public void setUserImageName(String userImageName) {
		this.userImageName = userImageName;
	}

	public Long getBgmiId() {
		return bgmiId;
	}

	public void setBgmiId(Long bgmiId) {
		this.bgmiId = bgmiId;
	}

	
}
