package com.battle.controller;

import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.UserDto;
import com.battle.payloads.UserUpdateDto;
import com.battle.services.UserService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) throws BattleException {
        UserDto savedUser = userService.registerUser(user);
        return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).build();
    }


    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByMail(@PathVariable String email) throws BattleException {
        UserDto user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws BattleException {
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserr(@PathVariable Long userId) throws BattleException {
        userService.deleteUserById(userId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto userDto) throws BattleException {
        UserDto user = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
