//package com.battle.entities;
//
//import java.util.List;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//
//@Entity
//public class TournamentRound {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private int roundNumber;
//
//    @ManyToOne
//    @JoinColumn(name = "tournament_id")
//    private Tournament tournament;
//
//    @OneToMany(mappedBy = "round")
//    private List<TeamResult> teamResults;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public int getRoundNumber() {
//		return roundNumber;
//	}
//
//	public void setRoundNumber(int roundNumber) {
//		this.roundNumber = roundNumber;
//	}
//
//	public Tournament getTournament() {
//		return tournament;
//	}
//
//	public void setTournament(Tournament tournament) {
//		this.tournament = tournament;
//	}
//
//	public List<TeamResult> getTeamResults() {
//		return teamResults;
//	}
//
//	public void setTeamResults(List<TeamResult> teamResults) {
//		this.teamResults = teamResults;
//	}
//
//    // getter and setter methods
//    
//    
//}
//
