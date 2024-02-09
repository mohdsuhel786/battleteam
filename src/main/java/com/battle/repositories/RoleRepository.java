package com.battle.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.battle.entities.ERole;
import com.battle.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(ERole name);
}
