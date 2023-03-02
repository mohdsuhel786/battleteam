package com.battle.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.User;
import com.battle.repositories.TeamMemberRepository;
import com.battle.repositories.TeamRepository;
import com.battle.repositories.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private UserRepository userRepository;

//   @Autowired
//   private EmailService emailService;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team createTeam(Team team) {
        Optional<Team> existingTeam = teamRepository.findByTeamName(team.getTeamName());
        if (existingTeam.isPresent()) {
            throw new EntityExistsException("Team already exists");
        }

        Team newTeam = teamRepository.save(team);
        // Optional<Team> optionalTeam = teamRepository.findById(teamId);
        String email = "loginMember@gmail.com";
        Optional<User> optionalUser = userRepository.findByEmail(email);
        // If either record does not exist, throw an exception
//      if (!optionalTeam.isPresent()) {
//          throw new EntityNotFoundException("Team not found");
//      }
        if (!optionalUser.isPresent()) {

            throw new EntityNotFoundException("User not found");
        }
        // Generate a random invitation token
        String invitationToken = UUID.randomUUID().toString();

        // Create a new TeamMember record with the team, user, and invitation token
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(newTeam);
        teamMember.setUser(optionalUser.get());
        teamMember.setInvitationToken(invitationToken);
        TeamMember teamMember2 = teamMemberRepository.save(teamMember);


        return newTeam;
    }

    public void deleteTeam(Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (!optionalTeam.isPresent()) {
            throw new EntityNotFoundException("Team not found");
        }
        teamRepository.deleteById(id);
    }

    public Team getTeam(Long id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (!optionalTeam.isPresent()) {
            throw new EntityNotFoundException("Team not found");
        }
        return optionalTeam.get();
    }

    public void inviteMember(Long teamId, String email) {
        System.out.println(email);
        // Retrieve team and user records from the database
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        // If either record does not exist, throw an exception
//     if (!optionalTeam.isPresent()) {
//         throw new EntityNotFoundException("Team not found");
//     }
        if (!optionalUser.isPresent()) {

            throw new EntityNotFoundException("User not found");
        }


        // If either record does not exist, throw an exception
        if (!optionalTeam.isPresent()) {
            throw new EntityNotFoundException("Team not found");
        }
        if (!optionalUser.isPresent()) {

            throw new EntityNotFoundException("User not found");
        }

        // Generate a random invitation token
        String invitationToken = UUID.randomUUID().toString();

        // Create a new TeamMember record with the team, user, and invitation token
        TeamMember teamMember = new TeamMember();
        teamMember.setTeam(optionalTeam.get());
        teamMember.setUser(optionalUser.get());
        teamMember.setInvitationToken(invitationToken);

        // Save the new TeamMember record to the database
        Team team = optionalTeam.get();
        teamRepository.save(team);

        // Send an invitation email to the user
        //  emailService.sendInvitationEmail(optionalUser.get(), optionalTeam.get(), invitationToken);
    }

    public void acceptInvitation(Long teamMemberId, String invitationToken) {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(teamMemberId);
        if (!optionalTeamMember.isPresent()) {
            throw new EntityNotFoundException("Team member not found");
        }

        TeamMember teamMember = optionalTeamMember.get();

        // Check that the invitation token matches
        if (!teamMember.getInvitationToken().equals(invitationToken)) {
            throw new IllegalArgumentException("Invalid invitation token");
        }

        // Add the user to the team as a member
        teamMember.getTeam().getMembers().add(teamMember);

        // Remove the invitation token from the TeamMember record
        teamMember.setInvitationToken(null);

        // Save the updated TeamMember record to the database
        teamMemberRepository.save(teamMember);
    }
}

