package com.battle.payloads;

import com.battle.entities.Team;


public class TeamResultDto {
	private Long teamResultId;

    private TeamDto team;
	
	private int killPoint;
	
	private int placementPoint;
	
	private int totalPoint;

	public Long getTeamResultId() {
		return teamResultId;
	}

	public void setTeamResultId(Long teamResultId) {
		this.teamResultId = teamResultId;
	}

	public TeamDto getTeam() {
		return team;
	}

	public void setTeam(TeamDto team) {
		this.team = team;
	}

	public int getKillPoint() {
		return killPoint;
	}

	public void setKillPoint(int killPoint) {
		this.killPoint = killPoint;
	}

	public int getPlacementPoint() {
		return placementPoint;
	}

	public void setPlacementPoint(int placementPoint) {
		this.placementPoint = placementPoint;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
	
	
	
}
