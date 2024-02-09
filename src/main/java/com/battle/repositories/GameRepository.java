package com.battle.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.battle.entities.Game;
import com.battle.entities.Team;
import com.battle.payloads.GameDto;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	 List<Game> findByGameName(String gameName);

}
