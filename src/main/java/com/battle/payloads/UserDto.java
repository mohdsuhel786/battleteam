package com.battle.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;

    @NotNull(message = "{user.name.absent}")
    @Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*", message = "{user.name.invalid}")
    private String name;

    @Email(message = "{user.emailid.invalid}")
    @NotNull(message = "{user.emailid.absent}")
    private String email;

    // @NotNull(message = "{user.password.absent}")
    //   @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",message = "{user.password.invalid}")

    @NotNull(message = "password")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", message = "{user.password.invalid}")
    private String password;
    private String userImageName;
    private Long bgmiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public UserDto() {
        super();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //  @JsonIgnore
    public String getPassword() {
        return password;
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
