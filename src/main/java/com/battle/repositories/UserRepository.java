package com.battle.repositories;

import com.battle.entities.User;
import com.battle.payloads.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
//    Optional<User> findByUserId(Long userId);

}

