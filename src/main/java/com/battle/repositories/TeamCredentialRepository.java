package com.battle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.battle.entities.TeamCredential;
import com.battle.entities.TournamentCredential;

@Repository
public interface TeamCredentialRepository extends JpaRepository<TeamCredential, Long> {

}
