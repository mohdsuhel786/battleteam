package com.battle.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.battle.entities.Team;
import com.battle.entities.TeamResult;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentResult;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamResultDto;
import com.battle.repositories.TeamResultRepository;
import com.battle.repositories.TournamentRepository;
import com.battle.repositories.TournamentResultRepository;

@Service
public class TeamResultService {

	@Autowired
	private TeamResultRepository teamResultRepository;
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private TournamentResultRepository tournamentResultRepository;
	
    public TeamResultDto updateTeamResult( Long teamResultId, TeamResultDto teamResultDto) throws BattleException {
 	Optional<TeamResult> optionalTeamResult = teamResultRepository.findById(teamResultId);
 	if (!optionalTeamResult.isPresent()) {
 		throw new BattleException("TeamResult not found!");
 	}
 	TeamResult teamResult2 = optionalTeamResult.get();
 	
 	teamResult2.setKillPoint(teamResultDto.getKillPoint());
 	teamResult2.setPlacementPoint(teamResultDto.getPlacementPoint());
 	teamResult2.setTotalPoint(teamResultDto.getTotalPoint());
 	TeamResult updateTeamResult = teamResultRepository.save(teamResult2);
 	TeamResultDto teamResultDtos = teamResultToTeamResultDto(updateTeamResult);
         return teamResultDtos;
    } 

    
	public TeamResultDto teamResultToTeamResultDto(TeamResult teamResult) {
		TeamResultDto teamsResultDto = new TeamResultDto();
		Team team = teamResult.getTeam();
		TeamDto teamDto = new TeamDto();
		teamDto.setTeamId(team.getTeamId());
		teamDto.setTeamName(team.getTeamName());
		teamDto.setTeamImageName(team.getTeamImageName());
		teamsResultDto.setTeamResultId(teamResult.getTeamResultId());
		teamsResultDto.setTeam(teamDto);
		teamsResultDto.setPlacementPoint(teamResult.getPlacementPoint());
		teamsResultDto.setKillPoint(teamResult.getKillPoint());
		teamsResultDto.setTotalPoint(teamResult.getTotalPoint());
		return teamsResultDto;
	}
}
