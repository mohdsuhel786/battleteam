package com.battle.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BaseUri;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.Tournament;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamMemberDto;
import com.battle.payloads.UserUpdateDto;
import com.battle.repositories.TeamMemberRepository;
import com.battle.repositories.TeamRepository;
import com.battle.repositories.TournamentRepository;
import com.battle.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;


@Service
@Transactional
public class TeamServices {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    
    @Autowired
    private TeamMemberService teamMemberService;
    
//    @Autowired
//    private EmailService emailService;

    public List<TeamDto> getAllTeams() throws BattleException {
    	List<Team> allTeam = teamRepository.findAll();
    	List<TeamDto> teams = new ArrayList<>();
    	for(Team td: allTeam) {
    		TeamDto teamDto = getTeamById(td.getTeamId());
    		teams.add(teamDto);    	}
    	return teams;
    }

    public TeamDto createTeam(Long userId,String teamName,String inGamaName) throws BattleException {
    	
    	 // If either record does not exist, throw an exception
    	Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new BattleException("User not found");
        }
        Optional<Team> existingTeam = teamRepository.findByTeamName(teamName);
        if (existingTeam.isPresent()) {
            throw new BattleException("TeamName already exists");
        }
    
        List<TeamMember> userT = teamMemberRepository.findByUser(optionalUser.get());
        
        if (!userT.isEmpty()) {
            throw new BattleException("Already in Team can not create!");
        }
        
      Team createdTeam  = new Team();
      createdTeam.setTeamName(teamName);
      
        createdTeam.setCaptain(optionalUser.get());

       
 
        
        String invitationToken = UUID.randomUUID().toString();
        // teamMemberRepository.findByUserId(user.getUserId());
         

         // Create a new TeamMember record with the team, user, and invitation token
         TeamMember teamMember = new TeamMember();
         
         teamMember.setUser(optionalUser.get());
         teamMember.setInvitationToken(invitationToken);
         teamMember.setInvitationToken(invitationToken);
         teamMember.setInGameName(inGamaName);
         teamMember.setTeam(createdTeam);

         List<TeamMember> teamList = new ArrayList<>();
         teamList.add(teamMember);
         // Save the new TeamMember record to the database
         //TeamMember Member = teamMemberRepository.save(teamMember);
        // teamMemberRepository.
         
        createdTeam.setMembers(teamList);
        Team savedTeam = teamRepository.save(createdTeam);
//         TeamDto teamDto = new TeamDto();
//         teamDto.setTeamName(savedTeam.getTeamName());
//         teamDto.setTeamId(savedTeam.getTeamId());
//         teamDto.setCaptain(savedTeam.getCaptain());
//         teamDto.setTeamImageName(null);
//         teamDto.setMembers(getTeamMember(savedTeam));
         TeamDto teamDto = teamToTeamDto(savedTeam);
      // team.setTeamId(teamRepository.findByTeamName(team.getTeamName()));
        return teamDto;
    }
    
    public TeamDto getTeamById(Long id) throws BattleException {
    	Optional<Team> optionalTeam = teamRepository.findById(id);
        if (!optionalTeam.isPresent()) {
            throw new BattleException("Team not found");
        }
        Team team = optionalTeam.get();
        TeamDto teamDto = teamToTeamDto(team);
        return teamDto;
    }


    public void deleteTeam(Long id,Long userId) throws BattleException {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (!optionalTeam.isPresent()) {
            throw new BattleException("Team not found");
        }
        
        Team team = optionalTeam.get();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new BattleException("User not found");
        }

        Optional<Team> optionalCaptain = teamRepository.findByCaptain(optionalUser.get());
        if (!optionalCaptain.isPresent()) {
            throw new BattleException("user is not captain");
        }
        
        if (!optionalCaptain.get().getCaptain().equals(team.getCaptain())) {
        	throw new BattleException("Only the captain can delete a team");
        }

//        for (TeamMember teamMember : team.getMembers()) {
//        	teamMember.setTeam(null);
//            teamMemberRepository.save(teamMember);
//        }
        // Remove the team from the tournament
       
    List<Tournament> listOfTournament = tournamentRepository.findByTeams(team);
        //listOfTournament.remove(team);
        for (Tournament tournament : listOfTournament) {
	        
	     //   tournament.getTeams().forEach(teams -> team.getMembers().removeIf(teamMember -> teamMember.getTeam().equals(team)));
        	List<TeamMember> ListteamMembers = tournamentRepository.findAllMembersByTournamentIdAndTeamId(tournament.getTournamentId(), team.getTeamId());

            
          	for (TeamMember t : ListteamMembers) {
          		//tournament.getMembers().remove(t);
          		
          		
          		
          		if (tournament.getMembers().contains(t)) {
                      tournament.getMembers().remove(t);
                  }
              }
          	if (tournament.getTeams().contains(team)) {
                tournament.getTeams().remove(team);
            }
          	
          	tournamentRepository.save(tournament);
    }
        
//        List<TeamMember> teamMembers = teamMemberRepository.findByTeam(team);
//	    for (TeamMember teamMember : teamMembers) {
//	    	teamMember.setTeam(null);
//	    
//            teamMemberRepository.save(teamMember);
//	    }
//	    for (TeamMember teamMember : teamMembers) {
//	 //       teamMemberRepository.deleteByUser(teamMember);
//	    	System.out.println(teamMember.getTeamMemberId());
//	        teamMember.setTeam(null);
//	        teamMember.setUser(null);
//	        teamMember.setInvitationToken(null);
//	     //   teamMember.setTeamMemberId(null);
//            teamMemberRepository.save(teamMember);
//	    }

      //  team.getMembers().remove(team);
        // Remove the team from all team members associated with it
//        for (TeamMember teamMember : team.getMembers()) {
//            teamMember.getTeam().getMembers().remove(team);
//            
//            teamMemberRepository.save(teamMember);
//        }

	//    team.setTeamName(null);	    
   //     teamRepository.save(team);
  //      team.setTeamId(null);

        teamRepository.delete(optionalTeam.get());
    }
    
    
    

    public String inviteMember(Long teamId, String email,HttpServletRequest request) throws BattleException {
    	System.out.println(email);
    	Optional<User> optionalUser = userRepository.findByEmail(email);
    	if (!optionalUser.isPresent()) {
            throw new BattleException("User Not found!");
        }

        User user = optionalUser.get();

        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            throw new BattleException("Team id Not found!");
        }

        Team team = optionalTeam.get();
        List<TeamMember> teamMembers = teamMemberRepository.findByTeam(team);
        if (teamMembers.isEmpty()) {
            throw new BattleException("Team Not Found!");
        }
        int teamSize = teamMembers.size();
        if(teamSize >=6) {
        	throw new BattleException("Team Full!");
        }
        TeamMember teamMember = teamMembers.get(0);
        String invitationToken = teamMember.getInvitationToken();

        // Create a new TeamMember record with the team, user, and invitation token
//        TeamMember teamMember = new TeamMember();
//        teamMember.setTeam(optionalTeam.get());
//        teamMember.setUser(optionalUser.get());
//        teamMember.setInvitationToken(invitationToken);

        // Save the new TeamMember record to the database
//        teamMemberRepository.save(teamMember);
//        teamRepository.save(optionalTeam.get());

        // Send an invitation email to the user
       // emailService.sendInvitationEmail(optionalUser.get(), optionalTeam.get(), invitationToken);
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
       
        //http://localhost:8080/teams/2/11?invitationToken=4c8350b5-44eb-4316-87a7-d66d92da7ce2
        String urlGenerate = baseUrl + "/teams/" + teamId.toString()+"/" + user.getUserId() + "?invitationToken="+ invitationToken ;
        System.out.println(urlGenerate);
        return urlGenerate;
    }
    public void acceptInvitation(Long userId,Long teamId, String invitationToken, String inGameName) throws BattleException {
    	Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new BattleException("User Not found!");
        }

        User user = optionalUser.get();

        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            throw new BattleException("Team id Not found!");
        }
        Team team = optionalTeam.get();

          List<TeamMember> teamMembers = teamMemberRepository.findByTeam(team);
        if (teamMembers.isEmpty()) {
            throw new BattleException("Team Not Found!");
        }
        int teamSize = teamMembers.size();
        if(teamSize >=6) {
        	throw new BattleException("Team Full!");
        }
        
        List<TeamMember> userTeamMember = teamMemberRepository.findByUser(user);
  
        if (!userTeamMember.isEmpty()) {
            throw new BattleException("Already in Team!");
        }

        TeamMember teamMember = teamMembers.get(0);

        // Check that the invitation token matches
        if (!teamMember.getInvitationToken().equals(invitationToken)) {
            throw new BattleException("Invalid invitation token");
        }

        // Add the user to the team as a member
        boolean bool1 = teamMember.getTeam().getMembers().add(teamMember);
        

        TeamMember newteamMember = new TeamMember();
        newteamMember.setTeam(team);
        newteamMember.setUser(user);
        newteamMember.setInvitationToken(invitationToken);
        newteamMember.setInGameName(inGameName);
        teamMemberRepository.save(newteamMember);
    }

    public List<TeamMemberDto> getTeamMember(Team team) throws BattleException {
    	 List<TeamMember> teamMembers = teamMemberRepository.findByTeam(team);
         if (teamMembers.isEmpty()) {
             throw new BattleException("Team Not Not Found!");
         }
         List<TeamMemberDto> teamMemberDtos = new ArrayList<TeamMemberDto>();
        for(TeamMember t : teamMembers) {
        	TeamMemberDto teamMemberDto = teamMemberService.teamMemberToTeamMemberDto(t);
			
			teamMemberDtos.add(teamMemberDto);
		}
         return teamMemberDtos;
    }
    
 public TeamDto updateTeam(Long teamId,Long userId,Long teamMemberId,String teamName) throws BattleException {
    	
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            throw new BattleException("Team not exists");
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new BattleException("Old Captain not found");
        }
         Optional<TeamMember> teamMember = teamMemberRepository.findById(teamMemberId);
        if (!teamMember.isPresent()) {
            throw new BattleException("Member not found");
        }
//  
        Team team = optionalTeam.get();
        List<TeamMember> teamMembers = team.getMembers();
        User captain = team.getCaptain();
        if(!captain.equals(optionalUser.get())) {
        	throw new BattleException("user is not captain");
        }
        
        if (teamMembers.isEmpty()) {
            throw new BattleException("Team Members Not Found!");
        }
       
        if(!team.equals(teamMember.get().getTeam())) {
        	throw new BattleException("User is not in same Team");
        }        
        team.setTeamName(teamName);
        teamRepository.save(team);
        TeamDto teamDto = getTeamById(teamId);
        return teamDto;
    }
    
 public TeamDto updateTeamPic(TeamDto team1, Long teamId,Long userId) throws BattleException {
	 Optional<Team> optionalTeam = teamRepository.findById(teamId);
     if (!optionalTeam.isPresent()) {
         throw new BattleException("Team not exists");
     }
     Optional<User> optionalUser = userRepository.findById(userId);
     if (!optionalUser.isPresent()) {
         throw new BattleException("Old Captain not found");
     }
     Team team = optionalTeam.get();
     User captain = team.getCaptain();
     if(!captain.equals(optionalUser.get())) {
     	throw new BattleException("user is not captain");
     }
     team.setTeamImageName(team1.getTeamImageName());
     teamRepository.save(team);
     TeamDto teamDto = getTeamById(teamId);
     return teamDto;
	}
    public TeamDto changeCaptain(Long teamId,Long oldCaptainId,Long teamMemberId ) throws BattleException {
    	
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        if (!optionalTeam.isPresent()) {
            throw new BattleException("Team not exists");
        }
        Optional<User> optionalUser = userRepository.findById(oldCaptainId);
        if (!optionalUser.isPresent()) {
            throw new BattleException("User Capatain not found");
        }
         Optional<TeamMember> teamMember = teamMemberRepository.findById(teamMemberId);
        if (!teamMember.isPresent()) {
            throw new BattleException("Member not found");
        }
        Team team = optionalTeam.get();
        List<TeamMember> teamMembers = team.getMembers();
        User captain = team.getCaptain();
        if(!captain.equals(optionalUser.get())) {
        	throw new BattleException("user is not captain");
        }
        
        if (teamMembers.isEmpty()) {
            throw new BattleException("Team Members Not Found!");
        }
       
        if(!team.equals(teamMember.get().getTeam())) {
        	throw new BattleException("User is not in same Team");
        }
        User user = teamMember.get().getUser();
        Optional<User> optionalUser2 = userRepository.findById(user.getUserId());
        if (!optionalUser2.isPresent()) {
            throw new BattleException("User Capatain not found");
        }
        
        team.setCaptain(optionalUser2.get());
        
        Team updatedTeam = teamRepository.save(team);
        TeamDto teamDto = teamToTeamDto(updatedTeam);
        return teamDto;
    }
	
    
    public TeamDto teamToTeamDto(Team team) throws BattleException {
    	TeamDto teamDto = new TeamDto();
        teamDto.setMembers(getTeamMember(team));
        teamDto.setTeamName(team.getTeamName());
        teamDto.setTeamId(team.getTeamId());
        User captain = team.getCaptain();
        UserUpdateDto updatedUser = new UserUpdateDto();
		updatedUser.setEmail(captain.getEmail());
		updatedUser.setName(captain.getName());
		updatedUser.setUserId(captain.getUserId());
		updatedUser.setUserImageName(captain.getUserImageName());
		updatedUser.setBgmiId(captain.getBgmiId());
        teamDto.setCaptain(updatedUser);
        teamDto.setTeamImageName(team.getTeamImageName());
        return teamDto;
    }
    
    public Team teamDtoToTeam(TeamDto teamDto) throws BattleException {
    	Team team = new Team();
    	List<TeamMemberDto> teamMemberDto = teamDto.getMembers();
    	List<TeamMember> teamMembers = new ArrayList<TeamMember>();
        for(TeamMemberDto t : teamMemberDto) {
			TeamMember teamMember =	teamMemberService.teamMemberDtoToTeamMember(t);
			teamMembers.add(teamMember);
        }
    	team.setMembers(teamMembers);
        team.setTeamName(teamDto.getTeamName());
        team.setTeamId(teamDto.getTeamId());
        
        UserUpdateDto captain = teamDto.getCaptain();
        User user = new User();
		user.setUserId(captain.getUserId());
		user.setName(captain.getName());
		user.setEmail(captain.getEmail());
		user.setUserImageName(captain.getUserImageName());
		user.setBgmiId(captain.getBgmiId());
        team.setCaptain(user);
        team.setTeamImageName(teamDto.getTeamImageName());
        return team;
    }

	
}

