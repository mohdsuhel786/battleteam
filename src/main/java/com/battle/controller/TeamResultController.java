package com.battle.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.battle.entities.Team;
import com.battle.entities.TeamResult;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamResultDto;
import com.battle.payloads.TournamentResultDto;
import com.battle.repositories.TeamResultRepository;
import com.battle.services.TeamResultService;

@RestController
@RequestMapping("/teamResults")
public class TeamResultController {

	@Autowired
	private TeamResultService teamResultService;
	
	@PutMapping("/update/{teamResultId}")
    public TeamResultDto updateTeamResult(@PathVariable Long teamResultId,@RequestBody TeamResultDto teamResult) throws BattleException {
	TeamResultDto updateTeamResult = teamResultService.updateTeamResult(teamResultId, teamResult);
	
        return updateTeamResult;
    }
	
	

}
