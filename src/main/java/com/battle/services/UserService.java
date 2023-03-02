package com.battle.services;

import java.util.List;
import java.util.Optional;

import com.battle.entities.User;
import com.battle.payloads.UserDto;

public interface UserService {

    UserDto registerUser(UserDto user);

    Optional<User> findUserByEmail(String email);

    List<User> getUsers();
}
