package com.battle.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.services.TeamResultService;
import com.battle.services.TournamentGroupService;
import com.battle.services.TournamentResultService;

@RestController
@RequestMapping("/groups")
public class TournamentGroupConroller {

	@Autowired
	private TournamentGroupService tournamentGroupService;
		
	 @PostMapping("/creategroups/{tournamentId}/{round}")
	    public Map<Integer, List<TeamDto>> getGroupsForRound(@PathVariable int round,@PathVariable Long tournamentId,@RequestParam int groupSize) throws BattleException {
	      //  int group = groupSize; // You can adjust this value according to your needs
	        return tournamentGroupService.createGroupsForRound(tournamentId,round, groupSize);
	    }
	
	 @PostMapping("/qualifiedteam/creategroups/{tournamentId}/round/{round}/groupsize")
	    public ResponseEntity<Map<Integer, List<TeamDto>>> createQualifiedTeamResultsByTournamentIdAndRound(@PathVariable Long tournamentId, @PathVariable int round,@RequestParam int maxQualificitionTotalScore,@RequestParam int groupSize) throws BattleException {
	        Map<Integer, List<TeamDto>> teamResults = tournamentGroupService.createQualifiedTeamsGroupByRound(tournamentId,round,maxQualificitionTotalScore,groupSize);
	        // Return the tournament results with an OK status code
	        return ResponseEntity.ok(teamResults);
	    }
		  
}
