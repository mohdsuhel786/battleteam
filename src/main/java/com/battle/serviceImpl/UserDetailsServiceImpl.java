package com.battle.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.battle.entities.Role;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.repositories.UserRepository;
import com.battle.services.MyUserDetails;

public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
     
//    @Override
//    public UserDetails loadUserByUsername(String email)
//            throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByEmail(email);
//         
//        if (user.get() == null) {
//            throw new UsernameNotFoundException("Could not find user");
//        }
//        MyUserDetails myUserDetails = MyUserDetails.build(user.get());
//        return myUserDetails;
//    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getAuthorities(user.getRoles()));
    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
        		.map(role -> new SimpleGrantedAuthority(role.getName().name()))
               // .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

}
