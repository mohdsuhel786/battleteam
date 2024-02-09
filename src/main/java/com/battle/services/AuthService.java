//package com.battle.services;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.battle.auth.AuthenticationRequest;
//import com.battle.auth.AuthenticationResponse;
//import com.battle.config.AppConstant;
//import com.battle.entities.Role;
//import com.battle.entities.User;
//import com.battle.exception.BattleException;
//import com.battle.payloads.RegisterRequest;
//import com.battle.repositories.RoleRepository;
//import com.battle.repositories.UserRepository;
//
//public class AuthService {
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private JwtService jwtService;
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private RoleRepository roleRepository;
//	
//	public AuthenticationResponse register(RegisterRequest user) throws BattleException {
//		String email = user.getEmail();
//		Optional<User> getEmail = userRepository.findByEmail(email);
//		if (getEmail.isPresent()) {
//			throw new BattleException("User Already Register");
//		}
//		User user1 = new User();
//		user1.setName(user.getName());
//		user1.setEmail(email);
//		user1.setPassword(this.passwordEncoder.encode(user.getPassword()));
//		Role role = this.roleRepository.findById(AppConstant.ROLE_USER).get();
//		user1.getRoles().add(role);
//		User savedUser = userRepository.save(user1);
//	//	user.setId(savedUser.getUserId());
//		MyUserDetails myUserDetails = MyUserDetails.build(savedUser);
//		String jwtToken = jwtService.generateToken(myUserDetails);
//		//String jwtToken = jwtService.generateToken(savedUser);
//		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//		authenticationResponse.setToken(jwtToken);
//			return authenticationResponse;
//	}
//	
//	
//	public AuthenticationResponse authenticate(AuthenticationRequest request) throws BattleException {
//		Authentication authenticate = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(
//						request.getEmail(), request.getPassword())
//				);
//		Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
//		if (!optionalUser.isPresent()) {
//			throw new BattleException("User id Not Found!");
//		}
//		
//		MyUserDetails userDetails = (MyUserDetails) authenticate.getPrincipal();
//		User user = optionalUser.get();
//		
//		//System.out.println(userDetails);
//		String jwtToken = jwtService.generateToken(userDetails);
//	//	String jwtToken = jwtService.generateToken(user);
//		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//		authenticationResponse.setToken(jwtToken);
//			return authenticationResponse;
//	}
//	public AuthenticationResponse registerAdmin(RegisterRequest user) throws BattleException {
//		String email = user.getEmail();
//		Optional<User> getEmail = userRepository.findByEmail(email);
//		if (getEmail.isPresent()) {
//			throw new BattleException("User Already Register");
//		}
//		User user1 = new User();
//		user1.setName(user.getName());
//		user1.setEmail(email);
//		user1.setPassword(this.passwordEncoder.encode(user.getPassword()));
//		Role role = this.roleRepository.findById(AppConstant.ROLE_ADMIN).get();
//		user1.getRoles().add(role);
//		User savedUser = userRepository.save(user1);
//	//	user.setId(savedUser.getUserId());
//		MyUserDetails myUserDetails = MyUserDetails.build(savedUser);
//		String jwtToken = jwtService.generateToken(myUserDetails);
//	//	String jwtToken = jwtService.generateToken(savedUser);
//		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//		authenticationResponse.setToken(jwtToken);
//			return authenticationResponse;
//	}
//	
//	public AuthenticationResponse registerOrganization(RegisterRequest user) throws BattleException {
//		String email = user.getEmail();
//		Optional<User> getEmail = userRepository.findByEmail(email);
//		if (getEmail.isPresent()) {
//			throw new BattleException("User Already Register");
//		}
//		
//		User user1 = new User();
//		user1.setName(user.getName());
//		user1.setEmail(email);
//		user1.setPassword(this.passwordEncoder.encode(user.getPassword()));
//		
//		Role role = this.roleRepository.findById(AppConstant.ROLE_ORGANIZATON).get();
//		user1.getRoles().add(role);
//		User savedUser = userRepository.save(user1);
//	//	user.setId(savedUser.getUserId());
//		MyUserDetails myUserDetails = MyUserDetails.build(savedUser);
//		
//		String jwtToken = jwtService.generateToken(myUserDetails);
//		//String jwtToken = jwtService.generateToken(savedUser);
//		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//		authenticationResponse.setToken(jwtToken);
//			return authenticationResponse;
//	}
//
//
//}
