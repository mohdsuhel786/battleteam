package com.battle.services;

import java.util.List;
import java.util.Optional;

import com.battle.auth.AuthenticationResponse;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.UserDto;
import com.battle.payloads.UserUpdateDto;

public interface UserService {

    AuthenticationResponse registerUser(UserDto user) throws BattleException;

    UserUpdateDto findUserByEmail(String email) throws BattleException;

    List<UserUpdateDto> getUsers() throws BattleException;

    // UserDto updateUser(UserDto userDto, Long userId) throws BattleException;
    UserUpdateDto getUserById(Long id) throws BattleException;

    void deleteUserById(Long id) throws BattleException;

    UserUpdateDto updateUser(UserUpdateDto userDto, Long userId) throws BattleException;

}
