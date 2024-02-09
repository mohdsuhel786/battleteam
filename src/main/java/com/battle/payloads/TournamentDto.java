package com.battle.payloads;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.battle.entities.Game;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class TournamentDto {

	
private Long tournamentId;
//@NotNull(message = "{tournament.tournamentImageName.absent}")
//@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.tournamentImageName.invalid}")
private String tournamentImageName;
@NotNull(message = "{tournament.name.absent}")
@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.name.invalid}")
private String name;
@NotNull(message = "{tournament.prizepool.absent}")
//@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.name.invalid}")
private Double prizepool;
@NotNull(message = "{tournament.location.absent}")
@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.location.invalid}")
private String location;
private String tournamentDetail;
@NotNull(message = "{tournament.slot.absent}")
@Min(1)
private Integer slot;
@NotNull(message = "{tournament.format.absent}")
@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.format.invalid}")
private String format;
@NotNull(message = "{tournament.status.absent}")
@Pattern(regexp = "(Open|Closed|Live|Completed|Others)",message = "{tournament.status.invalid}")
private String status;

@NotNull(message = "{tournament.tournamentDate.absent}")
@FutureOrPresent(message = "{tournament.tournamentDate.invalid}")
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime tournamentDate;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
@NotNull(message = "{tournament.tournamentEndDate.absent}")
@FutureOrPresent(message = "{tournament.tournamentEndDate.invalid}")
private LocalDateTime tournamentEndDate;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
@NotNull(message = "{tournament.tournamentEndDate.absent}")
@FutureOrPresent(message = "{tournament.tournamentEndDate.invalid}")
private LocalDateTime registrationEndDate;
@NotNull(message = "{tournament.registeredteam.absent}")
private Integer registeredTeam;
@NotNull(message = "{tournament.credentials.absent}")
@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.credentials.invalid}")
private String credentials;
private String checkInTime;
@NotNull(message = "{tournament.teamsSize.absent}")
private Integer teamSize;
private Map<String, Integer> prizeDistribution;
@NotNull(message = "{tournament.howToJoin.absent}")
@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.howToJoin.invalid}")
private String howToJoin;
@NotNull(message = "{tournament.rule.absent}")
@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.rule.invalid}")
private String rules;
//@NotNull(message = "{tournament.result.absent}")
//@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{tournament.result.invalid}")
private List<TournamentResultDto> result;
//@NotNull(message = "{tournament.game.absent}")
@Valid
private GameDto game;
//@NotNull(message = "{tournament.team.absent}")
@Valid
private Set<TeamDto> teamsDto;
//@NotNull(message = "{tournament.teamMember.absent}")
@Valid
private Set<TeamMemberDto> teamMembersDto;
public Long getTournamentId() {
	return tournamentId;
}
public void setTournamentId(Long tournamentId) {
	this.tournamentId = tournamentId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Double getPrizepool() {
	return prizepool;
}
public void setPrizepool(Double prizepool) {
	this.prizepool = prizepool;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getTournamentDetail() {
	return tournamentDetail;
}
public void setTournamentDetail(String tournamentDetail) {
	this.tournamentDetail = tournamentDetail;
}
public Integer getSlot() {
	return slot;
}
public void setSlot(Integer slot) {
	this.slot = slot;
}
public String getFormat() {
	return format;
}
public void setFormat(String format) {
	this.format = format;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

public Integer getRegisteredTeam() {
	return registeredTeam;
}
public void setRegisteredTeam(Integer registeredTeam) {
	this.registeredTeam = registeredTeam;
}
public String getCredentials() {
	return credentials;
}
public void setCredentials(String credentials) {
	this.credentials = credentials;
}

public GameDto getGame() {
	return game;
}
public void setGame(GameDto game) {
	this.game = game;
}
public String getCheckInTime() {
	return checkInTime;
}
public void setCheckInTime(String checkInTime) {
	this.checkInTime = checkInTime;
}
public Integer getTeamSize() {
	return teamSize;
}
public void setTeamSize(Integer teamSize) {
	this.teamSize = teamSize;
}

public String getHowToJoin() {
	return howToJoin;
}
public void setHowToJoin(String howToJoin) {
	this.howToJoin = howToJoin;
}
public String getRules() {
	return rules;
}
public void setRules(String rules) {
	this.rules = rules;
}
public Set<TeamDto> getTeamsDto() {
	return teamsDto;
}
public void setTeamsDto(Set<TeamDto> teamsDto) {
	this.teamsDto = teamsDto;
}
@JsonIgnore
public Set<TeamMemberDto> getTeamMembersDto() {
	return teamMembersDto;
}
public void setTeamMembersDto(Set<TeamMemberDto> teamMembersDto) {
	this.teamMembersDto = teamMembersDto;
}
//public TournamentDto() {
//	super();
//}
public Map<String, Integer> getPrizeDistribution() {
	return prizeDistribution;
}
public void setPrizeDistribution(Map<String, Integer> map) {
	this.prizeDistribution = map;
}
public String getTournamentImageName() {
	return tournamentImageName;
}
public void setTournamentImageName(String tournamentImageName) {
	this.tournamentImageName = tournamentImageName;
}
//public String getResult() {
//	return result;
//}
//public void setResult(String result) {
//	this.result = result;
//}
	
	
}
