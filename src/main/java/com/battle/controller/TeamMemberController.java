package com.battle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.battle.services.TeamService;

@RestController
@RequestMapping("/teamMembers")
public class TeamMemberController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/{teamMemberId}/acceptInvitation")
    public void acceptInvitation(@PathVariable("teamMemberId") Long teamMemberId, @RequestBody String invitationToken) {
        teamService.acceptInvitation(teamMemberId, invitationToken);
    }
}