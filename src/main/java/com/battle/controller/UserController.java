package com.battle.controller;

import com.battle.entities.User;
import com.battle.payloads.UserDto;
import com.battle.services.UserService;

import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto user) {
        UserDto savedUser = userService.registerUser(user);
        return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).build();
    }


    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUser() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
