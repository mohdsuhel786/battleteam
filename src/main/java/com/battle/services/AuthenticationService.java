package com.battle.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.battle.auth.AuthenticationRequest;
import com.battle.auth.AuthenticationResponse;
import com.battle.config.AppConstant;
import com.battle.entities.ERole;
import com.battle.entities.Role;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.JwtResponse;
import com.battle.payloads.RegisterRequest;
import com.battle.repositories.RoleRepository;
import com.battle.repositories.UserRepository;

@Service
@Transactional
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private RoleRepository roleRepository;
	
	public AuthenticationResponse register(RegisterRequest user) throws BattleException {
		String email = user.getEmail();
		Optional<User> getEmail = userRepository.findByEmail(email);
		if (getEmail.isPresent()) {
			throw new BattleException("User Already Register");
		}
		
		User user1 = new User();
		user1.setName(user.getName());
		user1.setEmail(email);
		user1.setPassword(this.passwordEncoder.encode(user.getPassword()));
//		Role role = this.roleRepository.findById(AppConstant.ROLE_USER).get();
//		user1.getRoles().add(role);
		
		 Set<String> strRoles = user.getRole();
		    Set<Role> roles = new HashSet<>();

		    if (strRoles == null) {
		      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		      roles.add(userRole);
		    } else {
		      strRoles.forEach(role -> {
		        switch (role) {
		        case "admin":
		          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(adminRole);

		          break;
		        case "org":
			          Role orgRole = roleRepository.findByName(ERole.ROLE_ORGANIZATION)
			              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			          roles.add(orgRole);

			          break;
		        case "mod":
		          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(modRole);

		          break;
		        default:
		          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(userRole);
		        }
		      });
		    }

		    user1.setRoles(roles);
		User savedUser = userRepository.save(user1);
	//	user.setId(savedUser.getUserId());
		MyUserDetails myUserDetails = MyUserDetails.build(savedUser);
	//	user.setId(savedUser.getUserId());
		
		String jwtToken = jwtService.generateToken(savedUser);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setToken(jwtToken);
			return authenticationResponse;
	}

	public ResponseEntity<JwtResponse> authenticate(AuthenticationRequest request) throws BattleException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(), request.getPassword())
				);
		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
		if (!optionalUser.isPresent()) {
			throw new BattleException("User id Not Found!");
		}
		User user = optionalUser.get();
//	MyUserDetails userDetails = (MyUserDetails) authenticate.getPrincipal();
		//System.out.println(userDetails.getPassword());
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String jwtToken = jwtService.generateToken(userDetails);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setToken(jwtToken);
			//return authenticationResponse;
		List<String> roles = userDetails.getAuthorities().stream()
		        .map(item -> item.getAuthority())
		        .collect(Collectors.toList());
//		List<String> roles = null;
		return ResponseEntity.ok(new JwtResponse(jwtToken,
				
				user.getUserId(), 
                user.getUsername(), 
                user.getEmail(), 
                roles));
	}

}
