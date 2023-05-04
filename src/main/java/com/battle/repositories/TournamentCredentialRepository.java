package com.battle.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.battle.entities.Tournament;
import com.battle.entities.TournamentCredential;
import com.battle.entities.TournamentResult;

@Repository
public interface TournamentCredentialRepository extends JpaRepository<TournamentCredential, Long> {
    List<TournamentCredential> findByTournament(Tournament tournament);

    Optional<TournamentCredential> findByTournamentAndRoundAndGroupNo(Tournament tournament, int round, int groupNo);

    List<TournamentCredential> findByTournamentAndRound(Tournament tournament, int round);
}
