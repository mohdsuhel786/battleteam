package com.battle.services;

import java.util.List;
import java.util.Optional;

import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.UserDto;
import com.battle.payloads.UserUpdateDto;

public interface UserService {

    UserDto registerUser(UserDto user) throws BattleException;

    UserDto findUserByEmail(String email) throws BattleException;

    List<UserDto> getUsers();

    // UserDto updateUser(UserDto userDto, Long userId) throws BattleException;
    UserDto getUserById(Long id) throws BattleException;

    void deleteUserById(Long id) throws BattleException;

    UserDto updateUser(UserUpdateDto userDto, Long userId) throws BattleException;

}
