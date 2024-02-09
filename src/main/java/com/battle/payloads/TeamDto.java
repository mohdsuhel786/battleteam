package com.battle.payloads;

import java.util.ArrayList;
import java.util.List;

import com.battle.entities.TeamMember;
import com.battle.entities.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



public class TeamDto {
	
	 	private Long teamId;
	 	@NotNull(message = "{team.name.absent}")
	 	@Pattern(regexp = "[A-Za-z0-9]+( [A-Za-z0-9]+)*",message = "{team.name.invalid}")
	    private String teamName;
	    private UserUpdateDto captain;

	    private String teamImageName;
	 	@NotNull(message = "{team.name.absent}")
	 	@Valid
	    private List<TeamMemberDto> members = new ArrayList<>();

		public Long getTeamId() {
			return teamId;
		}

		public void setTeamId(Long teamId) {
			this.teamId = teamId;
		}

		public String getTeamName() {
			return teamName;
		}

		public void setTeamName(String teamName) {
			this.teamName = teamName;
		}

	
		
		public List<TeamMemberDto> getMembers() {
			return members;
		}

		public void setMembers(List<TeamMemberDto> members) {
			this.members = members;
		}

		
		public UserUpdateDto getCaptain() {
			return captain;
		}

		public void setCaptain(UserUpdateDto captain) {
			this.captain = captain;
		}

		public String getTeamImageName() {
			return teamImageName;
		}

		public void setTeamImageName(String teamImageName) {
			this.teamImageName = teamImageName;
		}

		public TeamDto() {
			super();
		}
	    
	    
}
