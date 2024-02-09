package com.battle.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.battle.entities.Team;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentResult;

@Repository
public interface TournamentResultRepository extends JpaRepository<TournamentResult, Long> {
    
//    List<TournamentResult> findByTeamAndTournament(Team team, Tournament tournament);
//    
//    Optional<TournamentResult> findByTeamAndTournamentAndRound(Team team, Tournament tournament, int round);
//    
//    void deleteByTeamAndTournament(Team team, Tournament tournament);
//
//	Optional<TournamentResult> findByTournamentAndTeam(Long tournamentId, Long teamId);
//
 //@Query("SELECT tt FROM Tournament t JOIN t.teams tt WHERE t.tournamentId = ?1 AND tt.teamId = ?2") 
	//@Query("select tr from TournamentResult tr WHERE tr.tournamentId = ?1")
	List<TournamentResult> findByTournament(Tournament tournament);
	Optional<TournamentResult> findByTournamentAndRoundAndGroupNo(Tournament tournament, int round,int groupNo);
    
	List<TournamentResult> findByTournamentAndRound(Tournament tournament, int round);
}
