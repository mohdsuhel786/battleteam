package com.battle.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Game {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gameId;
	
	@Column(unique = true)
	private String gameName;
	private String gameImageName;
	private String device;
	
	private String aboutGame;
	
//	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
//	private List<Tournament> upcomingTournament;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getAboutGame() {
		return aboutGame;
	}

	public void setAboutGame(String aboutGame) {
		this.aboutGame = aboutGame;
	}

	public Game() {
		super();
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getGameImageName() {
		return gameImageName;
	}

	public void setGameImageName(String gameImageName) {
		this.gameImageName = gameImageName;
	}

//	public List<Tournament> getUpcomingTournament() {
//		return upcomingTournament;
//	}
//
//	public void setUpcomingTournament(List<Tournament> upcomingTournament) {
//		this.upcomingTournament = upcomingTournament;
//	}

	
	
	
	

}
