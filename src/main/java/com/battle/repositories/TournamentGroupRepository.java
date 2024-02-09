package com.battle.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.battle.entities.Tournament;
import com.battle.entities.TournamentGroup;
import com.battle.entities.TournamentResult;

@Repository
public interface TournamentGroupRepository extends JpaRepository<TournamentGroup, Long> {

	Optional<TournamentGroup> findByTournament(Long tournamentId);
	//Optional<TournamentGroup> findByTournamentAndRound(Long tournamentId,int round);
	List<TournamentGroup> findByTournamentAndRound(Tournament tournament, int round);
	Optional<TournamentGroup> findByTournamentAndRoundAndGroupNo(Tournament tournament, int round,int group);
}
