package com.battle.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.battle.entities.Team;
import com.battle.services.TeamService;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping("")
    public Team createTeam(@RequestBody Team team) {

        return teamService.createTeam(team);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);
    }

    @PostMapping("/{teamId}/invite")
    public void inviteMember(@PathVariable("teamId") Long teamId, @RequestBody String email) {
        teamService.inviteMember(teamId, email);
    }
}
