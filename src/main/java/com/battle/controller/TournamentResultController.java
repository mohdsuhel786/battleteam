package com.battle.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.battle.entities.Team;
import com.battle.entities.TeamResult;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentResult;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamResultDto;
import com.battle.payloads.TournamentResultDto;
import com.battle.services.TournamentResultService;

@RestController
@RequestMapping("/results")
public class TournamentResultController {

    private final TournamentResultService tournamentResultService;

    @Autowired
    public TournamentResultController(TournamentResultService tournamentResultService) {
        this.tournamentResultService = tournamentResultService;
    }

    @PostMapping("/createtournamentresult/tournaments/{tournamentId}")
    public ResponseEntity<TournamentResultDto> createTournamentResult(@PathVariable Long tournamentId,
            @RequestParam int round,@RequestParam int groupNo) throws BattleException {
        TournamentResultDto createdResult = tournamentResultService.createTournamentResult(tournamentId,round,groupNo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResult);
    }

    @PutMapping("/update")
    public ResponseEntity<TournamentResultDto> updateTournamentResult(@RequestParam Long tournamentId,@RequestParam int round,
           @RequestBody List<TeamResultDto> listTeamResultDto,@RequestParam int groupNo) throws BattleException {
        TournamentResultDto updatedResult = tournamentResultService.updateTournamentResult(tournamentId,round, listTeamResultDto,groupNo);
        return ResponseEntity.ok(updatedResult);
    }
    
    @GetMapping("/tournaments/{tournamentId}/round/group")
    public ResponseEntity<List<TeamResultDto>> getTournamentResultsByTournamentIdAndRoundAndGroup(@PathVariable Long tournamentId, @RequestParam int round,@RequestParam int groupNo) throws BattleException {
        List<TeamResultDto> tournamentResults = tournamentResultService.getTournamentResultsByTournamentIdAndRoundAndGroup(tournamentId,round,groupNo
        		);
        // Return the tournament results with an OK status code
        return ResponseEntity.ok(tournamentResults);
    }
    @GetMapping("/tournaments/{tournamentId}/round")
    public ResponseEntity<List<TeamResultDto>> getTournamentResultsByTournamentIdAndRound(@PathVariable Long tournamentId, @RequestParam int round) throws BattleException {
        List<TeamResultDto> tournamentResults = tournamentResultService.getTournamentResultsByTournamentIdAndRound(tournamentId,round);
        // Return the tournament results with an OK status code
        return ResponseEntity.ok(tournamentResults);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournamentResult(@PathVariable Long id) throws BattleException {
        tournamentResultService.deleteTournamentResult(id);
        // Return a NO_CONTENT status code
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tournaments/{tournamentId}")
    public ResponseEntity<List<TournamentResultDto>> getTournamentResultsByTournamentId(@PathVariable Long tournamentId) throws BattleException {
        List<TournamentResultDto> tournamentResults = tournamentResultService.getTournamentResultsByTournamentId(tournamentId);
        // Return the tournament results with an OK status code
        return ResponseEntity.ok(tournamentResults);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentResultDto> getTournamentResultById(@PathVariable Long id) throws BattleException {
        TournamentResultDto tournamentResult = tournamentResultService.getTournamentResultById(id);
        // Return the tournament result with an OK status code
        return ResponseEntity.ok(tournamentResult);
    }
    
    @GetMapping("/tournaments/qualifiedteam/{tournamentId}/round")
    public ResponseEntity<Map<Integer, List<TeamDto>>> getqualifiedTeamResultsByTournamentIdAndRound(@PathVariable Long tournamentId, @RequestParam int round,@RequestParam int limit) throws BattleException {
        Map<Integer, List<TeamDto>> teamResults = tournamentResultService.getQualifiedTeamsByRound(tournamentId,round,limit);
        // Return the tournament results with an OK status code
        return ResponseEntity.ok(teamResults);
    }
}
