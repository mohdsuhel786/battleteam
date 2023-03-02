package com.battle.serviceImpl;

import com.battle.entities.User;
import com.battle.payloads.UserDto;
import com.battle.repositories.UserRepository;
import com.battle.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto registerUser(UserDto user) {
        User user1 = new User();
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        userRepository.save(user1);
        return user;
    }

  
    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

}


