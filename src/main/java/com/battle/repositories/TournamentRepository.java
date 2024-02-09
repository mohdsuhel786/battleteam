package com.battle.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.Tournament;

import jakarta.persistence.Tuple;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long>{

  //  List<Tournament> findAllByStartDateAfterOrderByStartDateAsc(LocalDate currentDate);
    
    List<Tournament> findAllByTournamentDateBeforeAndTournamentEndDateAfterOrderByTournamentDateAsc(LocalDateTime currentDate, LocalDateTime currentDate2);

    List<Tournament> findAllByTournamentDateOrderByTournamentDateAsc(LocalDateTime currentDate);
	List<Tournament> findAllByTournamentEndDateBeforeOrderByTournamentDateAsc(LocalDateTime currentDate);
	List<Tournament> findAllByTournamentDateAfterOrderByTournamentDateAsc(LocalDateTime currentDate);
	List<Tournament> findAllByRegistrationEndDateAfterOrderByTournamentDateAsc(LocalDateTime currentDate);
	List<Tournament> findAllByStatus(String status);
	List<Tournament> findAllByGame(Long gameId);
	
	//@Query("SELECT tm FROM Tournament t JOIN t.members tm WHERE t.tournamentId = ?1 AND t.teams = ?2")  
	//List<TeamMember> findAllMembersByTournamentIdAndTeams(Long tournamentId, Team teams);

//    List<Team> findAllByTournamentIdAndTeams(Long tournamentId, Long teamId);
//    
//    // Method to retrieve all members registered in a tournament with a specific tournament ID and team ID
//    default List<TeamMember> findAllMembersByTournamentIdAndTeamId(Long tournamentId, Long teamId) {
//        List<Team> tournamentTeams = findAllByTournamentIdAndTeams(tournamentId, teamId);
//        
//        List<TeamMember> members = new ArrayList<>();
//        for (Team team : tournamentTeams) {
//            members.addAll(team.getMembers());
//        }
//        
//        return members;
//    }
   @Query("SELECT tt FROM Tournament t JOIN t.teams tt WHERE t.tournamentId = ?1 AND tt.teamId = ?2")  

	List<Team> findAllByTournamentIdAndTeams(Long tournamentId, Long teamId);
	 default List<TeamMember> findAllMembersByTournamentIdAndTeamId(Long tournamentId, Long teamId) {
       List<Team> tournamentTeams = findAllByTournamentIdAndTeams(tournamentId, teamId);
       
       List<TeamMember> members = new ArrayList<>();
       for (Team team : tournamentTeams) {
           members.addAll(team.getMembers());
       }
       
       return members;
   }
	
	
	
	@Query("SELECT tm FROM Tournament t JOIN t.teams tt JOIN t.members tm WHERE t.tournamentId = :tournamentId AND tt.teamId = :teamId")
    List<TeamMember> findAllMembersByTeamIdAndTournamentId(@Param("tournamentId") Long tournamentId,@Param("teamId") Long teamId );
	List<Tournament> findByTeams(Team team);
	void deleteByTeams(Team team);
	List<Tournament> findByMembers(TeamMember member);
	@Query("SELECT m FROM Tournament t JOIN t.members m WHERE m.TeamMemberId = ?1")  
	List<TeamMember> findByMember(Long teamMemberId);



	
	
}
