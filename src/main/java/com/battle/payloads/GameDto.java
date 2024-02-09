package com.battle.payloads;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDto {

	private Long gameId;
	@NotNull(message = "{game.name.absent}")
 	@Pattern(regexp = "[A-Z]+( [A-Z]+)*",message = "{game.name.invalid}")
	private String gameName;
	private String gameImageName;
	@Pattern(regexp = "(Mobile|PC|Laptop|Ipad|Tablet|Others)",message = "Validator.INVALID_DEVICE")
	private String device;
	
	@NotNull(message = "{game.aboutGame.absent}")
	@Pattern(regexp = "[A-Za-z0-9]+(\s[A-Za-z0-9]+)*",message = "{game.aboutGame.invalid}")
	private String aboutGame;
//	private List<TournamentDto> upcomingTournamentDto = new ArrayList<>();


	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	
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

	public GameDto() {
		super();
	}

//	public List<TournamentDto> getUpcomingTournamentDto() {
//		return upcomingTournamentDto;
//	}
//
//	public void setUpcomingTournamentDto(List<TournamentDto> upcomingTournamentDto) {
//		this.upcomingTournamentDto = upcomingTournamentDto;
//	}

	public String getGameImageName() {
		return gameImageName;
	}

	public void setGameImageName(String gameImageName) {
		this.gameImageName = gameImageName;
	}


	
	
	
}
