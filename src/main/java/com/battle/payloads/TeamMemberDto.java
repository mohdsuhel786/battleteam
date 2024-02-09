package com.battle.payloads;

import com.battle.entities.Team;
import com.battle.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamMemberDto {
//	@Mapping(target = "lastName", source = "lastName", index = 1)
	private Long teamMemberId;
	@NotNull(message = "{teamMember.name.absent}")
 	@Pattern(regexp = "[A-Za-z0-9]+(\\s[A-Za-z0-9]+)*",message = "{teaMember.name.invalid}")
	private String inGameName;
	
	//@NotNull
	//@Valid
    private UserDto userDto;
	@Valid
	private TeamDto teamDto;


	public Long getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(Long teamMemberId) {
		this.teamMemberId = teamMemberId;
	}

	public String getInGameName() {
		return inGameName;
	}

	public void setInGameName(String inGameName) {
		this.inGameName = inGameName;
	}

	@JsonIgnore
	public UserDto getUser() {
		return userDto;
	}

	
	public void setUser(UserDto user) {
		this.userDto = user;
	}

	@JsonIgnore
	public TeamDto getTeam() {
		return teamDto;
	}

	public void setTeam(TeamDto team) {
		this.teamDto = team;
	}

	

	

}
