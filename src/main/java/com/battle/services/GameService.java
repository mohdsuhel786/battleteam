package com.battle.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.battle.entities.Game;
import com.battle.entities.Team;
import com.battle.entities.Tournament;
import com.battle.exception.BattleException;
import com.battle.payloads.GameDto;
import com.battle.payloads.TournamentDto;
import com.battle.repositories.GameRepository;

@Service
@Transactional
public class GameService {

	@Autowired
	private GameRepository gameRepository;
	@Autowired
	private TournamentService tournamentService;
	
	 public GameDto registerGame(GameDto game) throws BattleException {
		 List<Game> existingGeam = gameRepository.findByGameName(game.getGameName());
	        if (!existingGeam.isEmpty()) {
	            throw new BattleException("GameName already exists");
	        }
	        Game game1 = new Game();
			game1.setGameName(game.getGameName());
			game1.setDevice(game.getDevice());
			game1.setAboutGame(game.getAboutGame());
			game1.setGameImageName(null);
	        gameRepository.save(game1);
	        return game;
	    }
	 
	
	    public List<GameDto> getGames() throws BattleException {
	        List<Game> games = gameRepository.findAll();
	        List<GameDto> gameDtos = new ArrayList<>();
	        for(Game g: games) {
	        	GameDto gameDto = gameToGameDto(g);
	        	gameDtos.add(gameDto);
	        }
	        return gameDtos;
	    }


		public GameDto getGameById(Long id) throws BattleException  {
		Optional<Game> optionalgame = gameRepository.findById(id);
		if(!optionalgame.isPresent()) {
			throw new BattleException("game id Not Found!");
		}
		Game game = optionalgame.get();
		
		GameDto gameDto = gameToGameDto(game);
			return gameDto;
		}
	 
	public GameDto gameToGameDto(Game game) throws BattleException {
		GameDto gameDto = new GameDto();
		
		gameDto.setGameId(game.getGameId());
		gameDto.setGameName(game.getGameName());
		gameDto.setDevice(game.getDevice());
		gameDto.setAboutGame(game.getAboutGame());
		gameDto.setGameImageName(game.getGameImageName());
		return gameDto;
		
	}
	
	public Game gameDtoToGame(GameDto gameDto) throws BattleException {
		Game game = new Game();
		
		game.setGameId(gameDto.getGameId());
		game.setGameName(gameDto.getGameName());
		game.setDevice(gameDto.getDevice());
		game.setAboutGame(gameDto.getAboutGame());
		game.setGameImageName(gameDto.getGameImageName());
		return game;
	}


	public GameDto updateGame(Long gameId, GameDto gameDto) throws BattleException {
		Game game = new Game();
		
		game.setGameId(gameDto.getGameId());
		game.setGameName(gameDto.getGameName());
		game.setDevice(gameDto.getDevice());
		game.setAboutGame(gameDto.getAboutGame());
		game.setGameImageName(gameDto.getGameImageName());
		Game savedGame = gameRepository.save(game);
		GameDto gameDtos = gameToGameDto(savedGame);
		return gameDtos;
	}
}
