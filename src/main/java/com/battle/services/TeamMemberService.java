package com.battle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.Tournament;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamMemberDto;
import com.battle.payloads.UserDto;
import com.battle.repositories.TeamMemberRepository;
import com.battle.repositories.TeamRepository;
import com.battle.repositories.TournamentRepository;
import com.battle.repositories.UserRepository;
import com.battle.serviceImpl.UserServiceImpl;

@Service
@Transactional
public class TeamMemberService {


    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;
    

    @Autowired
    private TournamentRepository tournamentRepository;
//    @Autowired
//    private EmailService emailService;

    public List<TeamMemberDto> getAllTeamMembers() throws BattleException {
    	teamMemberRepository.deleteByTeamIsNull();
         List<TeamMember> teamMembers = teamMemberRepository.findAll();
         List<TeamMemberDto> teamMemberDtos = new ArrayList<TeamMemberDto>();
         for(TeamMember t : teamMembers) {
 			TeamMemberDto teamMemberDto = teamMemberToTeamMemberDto(t);
 			teamMemberDtos.add(teamMemberDto);
 			
 		}
        return teamMemberDtos;
    }

    public List<TeamMember> addMember( TeamMemberDto teamMemberDto, Long userId,Long teamId) throws BattleException {
    	
    	 // If either record does not exist, throw an exception
    	Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new BattleException("User not found");
        }
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            throw new BattleException("Team Not Found");
        }
        
        String invitationToken = UUID.randomUUID().toString();
        // teamMemberRepository.findByUserId(user.getUserId());
         

         // Create a new TeamMember record with the team, user, and invitation token
         TeamMember teamMember = new TeamMember();
         
         teamMember.setUser(optionalUser.get());
         teamMember.setInvitationToken(invitationToken);
         teamMember.setTeam(optionalTeam.get());

         List<TeamMember> teamList = new ArrayList<>();
         teamList.add(teamMember);
         // Save the new TeamMember record to the database
         TeamMember Member = teamMemberRepository.save(teamMember);
        
         
      // team.setTeamId(teamRepository.findByTeamName(team.getTeamName()));
        return teamList;
    }
    
    public List<TeamMemberDto> getTeamMemberByTeam(Long teamId) throws BattleException {
    	//List<TeamMember> findByTeam = teamMemberRepository.findByTeam(team);
    	Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            throw new BattleException("Team not found");
        }
        Team team = optionalTeam.get();
    	 List<TeamMember> teamMembers = teamMemberRepository.findByTeam(team);
         if (teamMembers.isEmpty()) {
             throw new BattleException("TeamMembers and team Not Found!");
         }
         List<TeamMemberDto> teamMemberDtos = new ArrayList<TeamMemberDto>();
        for(TeamMember t : teamMembers) {
        	
			 TeamMemberDto teamMembersDto = teamMemberToTeamMemberDto(t);
			
			teamMemberDtos.add(teamMembersDto);
			
		}
         return teamMemberDtos;
    }
    
    @Transactional
    public void leaveTeam(Long teamMemberId) throws BattleException {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(teamMemberId);
        if (!optionalTeamMember.isPresent()) {
            throw new BattleException("User not in any team");
        }
        TeamMember teamMember = optionalTeamMember.get();
        Team team = teamMember.getTeam();
        if (teamMember.getTeam() == null) {
            throw new BattleException("Team Member not in Team");
        }
        List<Tournament> listOfTournament = tournamentRepository.findByMembers(teamMember);
        List<TeamMember> tournateamMembers =	tournamentRepository.findByMember(teamMemberId);      		
            for (Tournament tournament : listOfTournament) {            
              	for (TeamMember tM : tournateamMembers) {            		
              		if (tournament.getMembers().contains(tM)) {
                          tournament.getMembers().remove(tM);
                      }
                  }
            	tournamentRepository.save(tournament);
            }
            if (team.getCaptain().equals(teamMember.getUser())) {
                throw new BattleException("First Give captiancy to other Member!");
            }
            
           // Team team = teamMember.getTeam();
       //    List<TeamMember> teamMembers = team.getMembers();
            teamMember.setTeam(null);
            teamMember.setUser(null);
           teamMember.setInvitationToken(null);
           teamMember.setInGameName(null);
         
 //           teamRepository.save(team);
//            int teamSize = getTeamMemberByTeam(team.getTeamId()).size();
//            if(teamSize == 0) {
//            	teamRepository.delete(team);
//            }
//            teamRepository.save(team);
     //   teamMemberRepository.delete(teamMember);
        teamMemberRepository.save(teamMember);
    }
    
    
    
    public void removeTeamMember(Long teamMemberId,Long UserId) throws BattleException {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(teamMemberId);
        
        if (!optionalTeamMember.isPresent()) {
            throw new BattleException("Team Member not found");
        }
        TeamMember teamMember = optionalTeamMember.get();
        Team team = teamMember.getTeam();
        if (teamMember.getTeam() == null) {
            throw new BattleException("Team Member not in Team");
        }
        User captain = team.getCaptain();
        if (!UserId.equals(captain.getUserId())) {
        	throw new BattleException("Only the captain can remove a team member.");
        }
        	leaveTeam(teamMemberId);
       // teamRepository.save(team);
      int teamSize = getTeamMemberByTeam(team.getTeamId()).size();
      if(teamSize == 0) {
      		teamRepository.delete(team);
      	}
      	teamRepository.save(team);
        
        teamMemberRepository.delete(teamMember);
        teamMemberRepository.deleteByTeamIsNull();
        
    }
    
    public void deleteTeamMember(Long teamMemberId,Long UserId) throws BattleException {
		//leaveTeam(teamMemberId);
		Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(teamMemberId);
		
		if (!optionalTeamMember.isPresent()) {
		    throw new BattleException("Team Member not found");
		}
        if (optionalTeamMember.get().getTeam() != null) {
            throw new BattleException("Team Member already in Team,Leave or remove from Team");
        }
    teamMemberRepository.delete(optionalTeamMember.get());
    teamMemberRepository.deleteByTeamIsNull();
    }
    
    
    public TeamMemberDto getTeamMember(Long teamMemberId) throws BattleException {
    	teamMemberRepository.deleteByTeamIsNull();
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(teamMemberId);
        if (!optionalTeamMember.isPresent()) {
            throw new BattleException("Team Member not found");
        }
        TeamMember teamMember = optionalTeamMember.get();
        TeamMemberDto teamMemberDto = teamMemberToTeamMemberDto(teamMember);
		
		return teamMemberDto;
    }
    
    public Team getTeamForTeamMember(Long teamMemberId) throws BattleException {
        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
            .orElseThrow(() -> new BattleException("Team member not found"));

        Team team = teamRepository.findById(teamMember.getTeam().getTeamId())
            .orElseThrow(() -> new BattleException("Team not found"));

        return team;
    }

    
    
    public TeamMemberDto teamMemberToTeamMemberDto(TeamMember teamMember) throws BattleException {
    	TeamMemberDto teamMemberDto = new TeamMemberDto();
		teamMemberDto.setTeamMemberId(teamMember.getTeamMemberId());
		User user = teamMember.getUser();
		UserDto userDto = new UserDto() ;
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getUserId());
		userDto.setName(user.getName());
		teamMemberDto.setUser(userDto);
		teamMemberDto.setInGameName(teamMember.getInGameName());
		Team team = teamMember.getTeam();
		TeamDto teamDto = new TeamDto();
		teamDto.setTeamId(team.getTeamId());
		teamDto.setTeamName(team.getTeamName());
		team.setMembers(team.getMembers());
		team.setTeamImageName(team.getTeamImageName());
		//TeamDto teamToTeamDto = teamServices.teamToTeamDto(team);
    	teamMemberDto.setTeam(teamDto);
		return teamMemberDto;
    }
    public TeamMember teamMemberDtoToTeamMember(TeamMemberDto teamMemberDto) {
    	TeamMember teamMember = new TeamMember();
		teamMember.setTeamMemberId(teamMemberDto.getTeamMemberId());
		UserDto userDto = teamMemberDto.getUser();
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setUserId(userDto.getId());
		teamMember.setUser(user);
		teamMember.setInGameName(teamMemberDto.getInGameName());
    	
		return teamMember;
    }
    
    
    
    
}
