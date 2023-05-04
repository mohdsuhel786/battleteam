package com.battle.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.battle.services.MyUserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String name;

	@Column(unique = true)
	private String email;

	private String userImageName;
	private Long bgmiId;
	private String password;

//    @Enumerated(EnumType.STRING)
//    private Role role;
	// getters and setters

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();
	private Collection<? extends GrantedAuthority> authorities;

	public User(Long id, String name, String email, String password,
				Collection<? extends GrantedAuthority> authorities) {
		this.userId = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static User build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new User(
				user.getUserId(),
				user.getName(),
				user.getEmail(),
				user.getPassword(),
				authorities);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
    }

//    @JsonIgnore
//    public String getPassword() {
//        return password;
//    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//
//		return List.of(new SimpleGrantedAuthority(role.getName()));
//	}

	public Long getBgmiId() {
		return bgmiId;
	}

	public void setBgmiId(Long bgmiId) {
		this.bgmiId = bgmiId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            Set<Role> roles = user.getRoles();
//            List<SimpleGrantedAuthority> authorities = new ArrayList()<>();
//             
//            for (Role role : roles) {
//                authorities.add(new SimpleGrantedAuthority(role.getName()));
//            }
//             
//            return authorities;

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


}
