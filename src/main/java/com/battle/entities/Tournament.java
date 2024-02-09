package com.battle.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tournaments")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tournament{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tournamentId;
	private String tournamentImageName;
	private String name;
	private Double prizepool;
	private String location;
	private String tournamentDetail;
	private Integer slot;
	private String format;
	private String status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime tournamentDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime tournamentEndDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime registrationEndDate;
	private Integer registeredTeam;
	private String credentials;
	
//	@OneToMany
//	private TournamentResult result;
	@Column(nullable = false)
    private Boolean isResultsDeclared = false;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id")
	private Game game;
	private String checkInTime;
	private Integer teamSize;
//	@ElementCollection  
//	private HashMap<String, Integer> prizeDistribution;
    @ElementCollection
    @CollectionTable(name = "prize_distribution", joinColumns = @JoinColumn(name = "tournament_id"))
    @MapKeyColumn(name = "place")
    @Column(name = "prize")
    private Map<String, Integer> prizeDistribution;

	private String howToJoin;
	private String rules;
	@ManyToMany
    @JoinTable(
            name = "tournament_teams",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;
	
	@ManyToMany
    private Set<TeamMember> members;
	
//	public Long getTournamentId() {
//		return tournamentId;
//	}
//	public void setTournamentId(Long tournamentId) {
//		this.tournamentId = tournamentId;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public Double getPrizepool() {
//		return prizepool;
//	}
//	public void setPrizepool(Double prizepool) {
//		this.prizepool = prizepool;
//	}
//	public String getLocation() {
//		return location;
//	}
//	public void setLocation(String location) {
//		this.location = location;
//	}
//	public String getTournamentDetail() {
//		return tournamentDetail;
//	}
//	public void setTournamentDetail(String tournamentDetail) {
//		this.tournamentDetail = tournamentDetail;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	public LocalDate getTournamentDate() {
//		return tournamentDate;
//	}
//	public void setTournamentDate(LocalDate tournamentDate) {
//		this.tournamentDate = tournamentDate;
//	}
//	public LocalDate getRegistrationEndDate() {
//		return registrationEndDate;
//	}
//	public void setRegistrationEndDate(LocalDate registrationEndDate) {
//		this.registrationEndDate = registrationEndDate;
//	}
//	public Integer getRegisteredTeam() {
//		return registeredTeam;
//	}
//	public void setRegisteredTeam(Integer registeredTeam) {
//		this.registeredTeam = registeredTeam;
//	}
//	public String getCredentials() {
//		return credentials;
//	}
//	public void setCredentials(String credentials) {
//		this.credentials = credentials;
//	}
//	public Game getGame() {
//		return game;
//	}
//	public void setGame(Game game) {
//		this.game = game;
//	}
//	public String getCheckInTime() {
//		return checkInTime;
//	}
//	public void setCheckInTime(String checkInTime) {
//		this.checkInTime = checkInTime;
//	}
//	public Integer getTeamSize() {
//		return teamSize;
//	}
//	public void setTeamSize(Integer teamSize) {
//		this.teamSize = teamSize;
//	}
//
//	public String getHowToJoin() {
//		return howToJoin;
//	}
//	public void setHowToJoin(String howToJoin) {
//		this.howToJoin = howToJoin;
//	}
//	public String getRules() {
//		return rules;
//	}
//	public void setRules(String rules) {
//		this.rules = rules;
//	}
//	
//	
////	public Tournament() {
////		super();
////	}
//	public Set<Team> getTeams() {
//		return teams;
//	}
//	public void setTeams(Set<Team> teams) {
//		this.teams = teams;
//	}
//	public Integer getSlot() {
//		return slot;
//	}
//	public void setSlot(Integer slot) {
//		this.slot = slot;
//	}
//	public String getFormat() {
//		return format;
//	}
//	public void setFormat(String format) {
//		this.format = format;
//	}
//	@JsonIgnore
//	public Set<TeamMember> getMembers() {
//		return members;
//	}
//	public void setMembers(Set<TeamMember> members) {
//		this.members = members;
//	}
////	public HashMap<String, Integer> getPrizeDistribution() {
////		return prizeDistribution;
////	}
////	public void setPrizeDistribution(HashMap<String, Integer> prizeDistribution) {
////		this.prizeDistribution = prizeDistribution;
////	}
//	public Map<String, Integer> getPrizeDistribution() {
//		return prizeDistribution;
//	}
//	public void setPrizeDistribution(Map<String, Integer> prizeDistribution) {
//		this.prizeDistribution = prizeDistribution;
//	}
//	public String getTournamentImageName() {
//		return tournamentImageName;
//	}
//	public void setTournamentImageName(String tournamentImageName) {
//		this.tournamentImageName = tournamentImageName;
//	}
//	public String getResult() {
//		return result;
//	}
//	public void setResult(String result) {
//		this.result = result;
//	}
//	
////	  public void addPrizeDistribution(String place, Double prize) {
////	        this.prizeDistribution.put(place, prize);
////
////	
////	  }
	
}
