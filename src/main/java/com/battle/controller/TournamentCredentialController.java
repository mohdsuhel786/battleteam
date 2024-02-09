package com.battle.controller;

import java.util.List;

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

import com.battle.entities.TeamCredential;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamCredentialDto;
import com.battle.payloads.TeamResultDto;
import com.battle.payloads.TournamentCredentialDto;
import com.battle.payloads.TournamentResultDto;
import com.battle.payloads.UpdateTournamentCredentialDto;
import com.battle.services.TournamentCredentialService;

@RestController
@RequestMapping("/credentials")
public class TournamentCredentialController {

	@Autowired
	private TournamentCredentialService tournamentCredentialService;
	
    @PostMapping("/createtournamentresult/tournaments/{tournamentId}")
    public ResponseEntity<TournamentCredentialDto> createTournamentResult(@PathVariable Long tournamentId,
            @RequestParam int round,@RequestParam int groupNo) throws BattleException {
    	TournamentCredentialDto createdCredential = tournamentCredentialService.createTournamentResult(tournamentId,round,groupNo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCredential);
    }

    @PutMapping("/update")
    public ResponseEntity<TournamentCredentialDto> updateTournamentResult(@RequestParam Long tournamentId,@RequestParam int round,
           @RequestBody UpdateTournamentCredentialDto updateTournamentCredentialDto,@RequestParam int groupNo) throws BattleException {
    	TournamentCredentialDto updatedCredential = tournamentCredentialService.updateTournamentCredential(tournamentId,round,groupNo,updateTournamentCredentialDto);
        return ResponseEntity.ok(updatedCredential);
    }
    
//    @GetMapping("/tournaments/{tournamentId}/round/group")
//    public ResponseEntity<List<TeamResultDto>> getTournamentResultsByTournamentIdAndRoundAndGroup(@PathVariable Long tournamentId, @RequestParam int round,@RequestParam int groupNo) throws BattleException {
//        List<TeamResultDto> tournamentResults = tournamentCredentialService.getTournamentResultsByTournamentIdAndRoundAndGroup(tournamentId,round,groupNo);
//        // Return the tournament results with an OK status code
//        return ResponseEntity.ok(tournamentResults);
//    }
    @GetMapping("/tournaments/{tournamentId}/{round}/{groupNo}/{teamId}")
    public ResponseEntity<TournamentCredentialDto> getTournamentResultsByTournamentIdAndRound(@PathVariable Long tournamentId, @PathVariable int round,@PathVariable int groupNo, @PathVariable long teamId) throws BattleException {
       TournamentCredentialDto tournamentCredential= tournamentCredentialService.getTournamentCredentialByTournamentIdAndRoundAndTeamId(tournamentId,round,groupNo, teamId);
        // Return the tournament results with an OK status code
        return ResponseEntity.ok(tournamentCredential);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournamentResult(@PathVariable Long id) throws BattleException {
        tournamentCredentialService.deleteTournamentCredential(id);
        // Return a NO_CONTENT status code
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/tournaments/{tournamentId}")
//    public ResponseEntity<List<TournamentResultDto>> getTournamentResultsByTournamentId(@PathVariable Long tournamentId) throws BattleException {
//        List<TournamentResultDto> tournamentResults = tournamentResultService.getTournamentResultsByTournamentId(tournamentId);
//        // Return the tournament results with an OK status code
//        return ResponseEntity.ok(tournamentResults);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<TournamentResultDto> getTournamentResultById(@PathVariable Long id) throws BattleException {
//        TournamentResultDto tournamentResult = tournamentResultService.getTournamentResultById(id);
//        // Return the tournament result with an OK status code
//        return ResponseEntity.ok(tournamentResult);
//    }
    
}
