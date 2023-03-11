package com.battle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.battle.entities.TeamMember;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamMemberDto;
import com.battle.repositories.TeamMemberRepository;
import com.battle.services.TeamMemberService;
import com.battle.services.TeamServices;

@RestController
@RequestMapping("/teamMembers")
@Validated
public class TeamMemberController {

    @Autowired
    private TeamServices teamService;

    @Autowired
    private TeamMemberService teamMemberService;

//    @PostMapping("/{teamMemberId}/acceptInvitation")
//    public void acceptInvitation(@PathVariable("teamMemberId") Long teamMemberId, @RequestBody String invitationToken) {
//        teamService.acceptInvitation(teamMemberId, invitationToken);
//    }

    @GetMapping("/{teamMemberDto}")
    public TeamMemberDto findTeamMember(@PathVariable("teamMemberDto") Long teamMemberDto) throws BattleException {
        TeamMemberDto teamMember = teamMemberService.getTeamMember(teamMemberDto);
        return teamMember;
    }


    @GetMapping("/teams/{teamId}")
    public List<TeamMemberDto> findTeamMemberInTeam(@PathVariable("teamId") Long team) throws BattleException {
        List<TeamMemberDto> teamMemberByTeam = teamMemberService.getTeamMemberByTeam(team);
        return teamMemberByTeam;
    }

    @GetMapping("")
    public List<TeamMemberDto> findTeamMembers() throws BattleException {
        List<TeamMemberDto> allTeamMember = teamMemberService.getAllTeamMembers();
        return allTeamMember;
    }

    @DeleteMapping("/leave/{teamMemberId}")
    public void leaveTeam(@PathVariable("teamMemberId") Long teamMemberId) throws BattleException {
        //teamMemberService.leaveTeam(id);
        teamMemberService.leaveTeam(teamMemberId);
    }

    @DeleteMapping("/remove/{teamMemberId}/{userId}")
    public void RemoveTeamMember(@PathVariable("teamMemberId") Long teamMemberId, @PathVariable("userId") Long userId) throws BattleException {
        //teamMemberService.leaveTeam(id);
        teamMemberService.removeTeamMember(teamMemberId, userId);
    }

    @DeleteMapping("/delete/{teamMemberId}/{userId}")
    public void deleteTeamMember(@PathVariable("teamMemberId") Long teamMemberId, @PathVariable("userId") Long userId) throws BattleException {
        //teamMemberService.leaveTeam(id);
        teamMemberService.deleteTeamMember(teamMemberId, userId);
    }
}