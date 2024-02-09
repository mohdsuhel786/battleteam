package com.battle.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
@Entity
public class TeamResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teamResultId;
	@ManyToOne
	@JoinColumn(name = "team_id")
    private Team team;
	
	private int killPoint;
	
	private int placementPoint;
	
	private int totalPoint;

	public Long getTeamResultId() {
		return teamResultId;
	}

	public void setTeamResultId(Long teamResultId) {
		this.teamResultId = teamResultId;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
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
