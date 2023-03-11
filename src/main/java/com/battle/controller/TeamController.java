package com.battle.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.battle.entities.Team;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.services.TeamServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/teams")
@Validated
public class TeamController {

    @Autowired
    private TeamServices teamService;

    @GetMapping("")
    public List<TeamDto> getAllTeams() throws BattleException {
        List<TeamDto> allTeams = teamService.getAllTeams();
        return allTeams;

    }

    @PostMapping("/createTeam/user/{userId}")
    public TeamDto createTeam(@Valid @PathVariable("userId") Long userId, @RequestParam String teamName, @RequestParam String inGameName) throws BattleException {

        return teamService.createTeam(userId, teamName, inGameName);
    }

    @DeleteMapping("/{teamId}/user/{userId}")
    public void deleteTeam(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) throws BattleException {
        teamService.deleteTeam(teamId, userId);
    }

    @GetMapping("/{teamId}")
    public TeamDto findTeam(@Valid @PathVariable("teamId") Long teamId) throws BattleException {
        TeamDto team = teamService.getTeamById(teamId);
        return team;
    }

    @PostMapping("/{teamId}/invite")
    public String inviteMember(@Valid @PathVariable("teamId") Long teamId, @RequestBody String email) throws BattleException {
        String invitationalCode = teamService.inviteMember(teamId, email);
        return invitationalCode;
    }

    //(@RequestBody inGame name)
    @PostMapping("/{teamId}/{userId}")

    public void acceptInvitation(@PathVariable("userId") Long userId, @Valid @RequestParam("invitationToken") String invitationToken, @PathVariable("teamId") Long teamId, @Valid @RequestParam String inGameName, HttpServletRequest request, HttpServletResponse response) throws BattleException {
        teamService.acceptInvitation(userId, teamId, invitationToken, inGameName);
    }

    @GetMapping("/{teamId}/invite")
    public String getInvitation(@PathVariable("teamId") Long teamId, @Valid @RequestParam(value = "invitationToken", required = false) String invitationToken, Model map) throws BattleException {
        //teamService.acceptInvitation(userId, teamId, invitationToken,inGameName);
        map.addAttribute("msg", "teams" + "employee requested by invitationToken: " + invitationToken);
//    	List<TeamMember> teamMember = teamMemberList.getTeamMember();
//    	for(User x:user) {
//    		if(x.getTeamName().equals(teamId.trim())&& x.getInvitationToken().equals(invittionToken.trim()))
//    		return "success";
//    	}

        return invitationToken;
    }

    @PutMapping("/{teamId}/user/{oldCaptainUserId}/member/{teamMemberId}")
    public TeamDto changeNewCaptain(@PathVariable Long teamId, @PathVariable Long oldCaptainUserId, @PathVariable Long teamMemberId) throws BattleException {
        TeamDto changeCaptain = teamService.changeCaptain(teamId, oldCaptainUserId, teamMemberId);
        return changeCaptain;

    }

    @PutMapping("/updateTeam/{teamId}/user/{userId}/member/{teamMemberId}")
    public ResponseEntity<TeamDto> updateTeamName(@PathVariable Long teamId, @PathVariable Long userId, @PathVariable Long teamMemberId, @RequestParam String teamName) throws BattleException {
        TeamDto updatedTeam = teamService.updateTeam(teamId, userId, teamMemberId, teamName);
        return new ResponseEntity<>(updatedTeam, HttpStatus.CREATED);
    }
}


