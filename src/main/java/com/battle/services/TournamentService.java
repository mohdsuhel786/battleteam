package com.battle.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.battle.entities.Game;
import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentGroup;
import com.battle.entities.TournamentResult;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.GameDto;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamMemberDto;
import com.battle.payloads.TeamResultDto;
import com.battle.payloads.TournamentDto;
import com.battle.payloads.TournamentResultDto;
import com.battle.payloads.UserUpdateDto;
import com.battle.repositories.GameRepository;
import com.battle.repositories.TeamMemberRepository;
import com.battle.repositories.TeamRepository;
import com.battle.repositories.TournamentGroupRepository;
import com.battle.repositories.TournamentRepository;
import com.battle.utils.TournamentUtils;

@Service
@Transactional
public class TournamentService {
	
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private TeamMemberService teamMemberService;
	@Autowired
	private TeamServices teamServices;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private TeamMemberRepository teamMemberRepository;
	
	@Autowired
	private TournamentResultService tournamentResultService;
	
	@Autowired
	private TournamentGroupRepository tournamentGroupRepository;
	
    public TournamentDto createTournament(TournamentDto tournamentDto, Long gameId) throws BattleException {
    	
    	Optional<Game> optionalgame = gameRepository.findById(gameId);
		if(!optionalgame.isPresent()) {
			throw new BattleException("game id Not Found!");
		}
		Game game = optionalgame.get();
		//Tournament tournament2 = new Tournament();
		GameDto gameDto = new GameDto();
		gameDto.setGameId(gameId);
		gameDto.setAboutGame(game.getAboutGame());
		gameDto.setDevice(game.getDevice());
		gameDto.setGameImageName(null);
		gameDto.setGameName(game.getGameName());
		tournamentDto.setGame(gameDto);
		
		Tournament tournament = new Tournament();
    	tournament.setTournamentId(tournamentDto.getTournamentId());
    	tournament.setTournamentImageName(tournamentDto.getTournamentImageName());
  //  	tournament.setResult(null);
    	tournament.setCheckInTime(tournamentDto.getCheckInTime());
    	tournament.setCredentials(tournamentDto.getCredentials());
    	tournament.setFormat(tournamentDto.getFormat());
    	tournament.setGame(game);
    	tournament.setHowToJoin(tournamentDto.getHowToJoin());
    	tournament.setLocation(tournamentDto.getLocation());
    	tournament.setName(tournamentDto.getName());
    	tournament.setPrizeDistribution(tournamentDto.getPrizeDistribution());
    	tournament.setPrizepool(tournamentDto.getPrizepool());
    	tournament.setRegisteredTeam(tournamentDto.getRegisteredTeam());
    	tournament.setRegistrationEndDate(tournamentDto.getRegistrationEndDate());
    	tournament.setTournamentDate(tournamentDto.getTournamentDate());
    	tournament.setTournamentEndDate(tournamentDto.getTournamentEndDate());
    	tournament.setRules(tournamentDto.getRules());
    	tournament.setSlot(tournamentDto.getSlot());
    	tournament.setStatus(tournamentDto.getStatus());

 //   	Set<TeamDto> teamDto = tournamentDto.getTeamsDto();
    
//    	Set<Team> teams = new HashSet<>();
//    	for(TeamDto t: teamDto) {
//    		Team teamDtoToTeam = teamServices.teamDtoToTeam(t);
//    		teams.add(teamDtoToTeam);
//    	}
//    	Set<TeamMemberDto> teamMemberDto = tournamentDto.getTeamMembersDto();
//    	Set<TeamMember> teamMembers = new HashSet<>();
//        for(TeamMemberDto t : teamMemberDto) {
//			TeamMember teamMember =	teamMemberService.teamMemberDtoToteamMember(t);
//			teamMembers.add(teamMember);
//        }
    	//tournament.setMembers(teamMembers);
    	//tournament.setTeams(teams);
    	tournament.setTeamSize(tournamentDto.getTeamSize());
    	tournament.setTournamentDate(tournamentDto.getTournamentDate());
    	tournament.setTournamentDetail(tournamentDto.getTournamentDetail());
	//Tournament tournament = tournamentDtoToTournament(tournamentDto);
        Tournament savedTournament = tournamentRepository.save(tournament);
        tournamentDto.setTournamentId(savedTournament.getTournamentId());
 //       tournamentDto.setTeamsDto(savedTournament.getTeams());
        return tournamentDto;
    }
    
    
    public List<TournamentDto> getAllTeams() throws BattleException {
        List<Tournament> allTourna = tournamentRepository.findAll();
        List<TournamentDto> tournaDtos = new ArrayList<>();
        for(Tournament t: allTourna) {
        	TournamentDto tournamentDto = tournamentToTournamentDto(t);
        	tournaDtos.add(tournamentDto);
        }
        return tournaDtos;
        		
    }
   
    
    public TournamentDto getTournament(Long tournamentId) throws BattleException{
    Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow( () -> new BattleException("Tournament Id Not Found"));
    	
    TournamentDto tournamentDto = tournamentToTournamentDto(tournament);
        return tournamentDto;
    }
    
    
    public List<TeamMemberDto> getTeamMember(Long teamMemberId) {
    	
    List<TeamMember> teamMembers =	tournamentRepository.findByMember(teamMemberId);
    Optional<TeamMember> t = teamMemberRepository.findById(teamMemberId);
    List<TeamMemberDto> teamMembersDtos = new ArrayList<>();
	for(TeamMember tm : teamMembers) {
		TeamMemberDto teamMemberDto = new TeamMemberDto();
		teamMemberDto.setInGameName(tm.getInGameName());
		teamMemberDto.setTeamMemberId(tm.getTeamMemberId());
		teamMembersDtos.add(teamMemberDto);
   }
  		List<Tournament> listOfTournament = tournamentRepository.findByMembers(t.get());
  		
        for (Tournament tournament : listOfTournament) {            
          	for (TeamMember tM : teamMembers) {
          		//tournament.getMembers().remove(t);
          		
          		
          		
          		if (tournament.getMembers().contains(tM)) {
                      tournament.getMembers().remove(tM);
                  }
              }
        	tournamentRepository.save(tournament);
  		
	}	
      
	return teamMembersDtos;
//        return ResponseEntity.ok("Team members selected successfully");
   
    }
    
    //TeamMembersAndRegisterTournament
    
    public TeamDto selectTeamMembersAndRegisterTournament( Long tournamentId,Long teamId, List<TeamMemberDto> teamMemberDto) throws BattleException {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
      if(!optionalTournament.isPresent()) {
      	throw new BattleException("Tournament not found");
      }
      
    
      Tournament tournament = optionalTournament.get();
      if (tournament.getSlot() == 0) {
 		 throw new BattleException("Slot Full");
      }
      if (tournament.getRegistrationEndDate().isBefore(LocalDateTime.now())) {
  		 throw new BattleException("Registration Closed");
      }
      if (tournament.getTournamentDate().isBefore(LocalDateTime.now())) {
   		 tournament.setStatus("Live");
   		 throw new BattleException("tournament is Live");
       }
      if (tournament.getTournamentEndDate().isBefore(LocalDateTime.now())) {
    		 tournament.setStatus("Completed");
    		 throw new BattleException("tournament Completed");
        }
       
      Optional<Team> optionalTeam = teamRepository.findById(teamId);
  	  
  	if(!optionalTeam.isPresent()) {
        throw new BattleException("Team not found");
  		}
  	Team team = optionalTeam.get();
      if (tournament.getTeams().contains(team)) {
    	  throw new BattleException("Team already Joined");
       }
      
      
      if (teamMemberDto.isEmpty()) {
    	  throw new BattleException("Invalid team Selection!");
      }
      if (teamMemberDto.size() != tournament.getTeamSize()) {
    	  throw new BattleException("team size not match!");
      }

 //    List<TeamMemberDto> listOfTeamMember = getTeamMemberRegister(tournamentId, team);
      
    	for (TeamMemberDto t : teamMemberDto) {
    		TeamMemberDto teamMember2 = teamMemberService.getTeamMember(t.getTeamMemberId());
    		if(teamMember2.getTeam().getTeamId() != teamId) {
    			
  	    	  throw new BattleException("participants team not matched!");
  	      }
    		TeamMember teamMember = new TeamMember();
    		teamMember.setTeamMemberId(t.getTeamMemberId());
    		teamMember.setInGameName(t.getInGameName());
    		
    		if (!tournament.getMembers().contains(teamMember)) {
                tournament.getMembers().add(teamMember);
            }
          //  tournament.getMembers().add(teamMember);
        }
    	if (!tournament.getTeams().contains(team)) {
            tournament.getTeams().add(team);
        }
    	
    	tournament.setRegisteredTeam(tournament.getTeams().size());
    	tournament.setSlot(tournament.getSlot() - tournament.getTeams().size());
    	tournamentRepository.save(tournament);
    	TeamDto teamDto = teamServices.teamToTeamDto(team);
        return teamDto;
    }
    
    
    public List<TournamentDto> getUpcomingTournaments() throws BattleException {
        LocalDateTime currentDate = LocalDateTime.now();
       List<Tournament> upcomingTournament = tournamentRepository.findAllByRegistrationEndDateAfterOrderByTournamentDateAsc(currentDate);
      // upcomingTournament.stream().map(this::mapTournamentToDTO).collect(Collectors.toList())
       List<TournamentDto> upComingTournaDtos = new ArrayList<>();
       for(Tournament t: upcomingTournament) {
       	TournamentDto tournamentDto = tournamentToTournamentDto(t);
       	upComingTournaDtos.add(tournamentDto);
       }
      upComingTournaDtos.sort(Comparator.comparing(TournamentDto::getTournamentDate));
   //	Collections.reverse(upComingTournaDtos);
    return upComingTournaDtos;
    }
    
    public List<TournamentDto> getUpcomingGameTournaments(Long gameId) throws BattleException {
        LocalDateTime currentDate = LocalDateTime.now();
//       List<Tournament> upcomingTournament = tournamentRepository.findAllByTournamentDateAfterOrderByTournamentDateAsc(currentDate);
        List<Tournament> upcomingTournament = tournamentRepository.findAllByRegistrationEndDateAfterOrderByTournamentDateAsc(currentDate);
      // upcomingTournament.stream().map(this::mapTournamentToDTO).collect(Collectors.toList())
       List<TournamentDto> upComingTournaDtos = new ArrayList<>();
       for(Tournament t: upcomingTournament) {
       	TournamentDto tournamentDto = tournamentToTournamentDto(t);
       	if(tournamentDto.getGame().getGameId().equals(gameId)) {
       		upComingTournaDtos.add(tournamentDto);
       	}
       	
       }
       return upComingTournaDtos;
    }

//    public List<Tournament> getLiveTournaments() {
//        LocalDateTime currentDate = LocalDateTime.now();
//        return tournamentRepository.findAllByregistrationEndDateBeforeAndEndDateAfterOrderByStartDateAsc(currentDate, currentDate);
//    }

    
    public List<TournamentDto> getLiveTournaments() throws BattleException {
      LocalDateTime currentDate = LocalDateTime.now();
      LocalDateTime nextDay = currentDate.plusDays(1);
   //   List<Tournament> liveTournaments = tournamentRepository.findAllByStatus("Live");
      List<Tournament> liveTournament = tournamentRepository.findAllByTournamentDateBeforeAndTournamentEndDateAfterOrderByTournamentDateAsc(currentDate, currentDate);
//      List<Tournament> startTournaments = tournamentRepository.findAllByTournamentEndDateBeforeOrderByTournamentDateAsc(currentDate);
     // liveTournaments.stream().map(this::tournamentToTournamentDto).collect(Collectors.toList());
      List<TournamentDto> liveTournaDtos = new ArrayList<>();
      for(Tournament t: liveTournament) {
    	  t.setStatus("Live");
         	TournamentDto tournamentDto = tournamentToTournamentDto(t);
         	liveTournaDtos.add(tournamentDto);
         }
      return liveTournaDtos ;
  }
    
    public List<TournamentDto> getCompletesTournaments() throws BattleException {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Tournament> completesTournaments = tournamentRepository.findAllByTournamentEndDateBeforeOrderByTournamentDateAsc(currentDate);
       // liveTournaments.stream().map(this::tournamentToTournamentDto).collect(Collectors.toList());
        List<TournamentDto> completesTournaDtos = new ArrayList<>();
        for(Tournament t: completesTournaments) {
      	  t.setStatus("Completed");
      	  TournamentDto tournamentDto = tournamentToTournamentDto(t);
            completesTournaDtos.add(tournamentDto);
           }
        return completesTournaDtos ;
    }
    
    public List<TeamMemberDto> getTeamMemberRegister(Long tournmentId,Long teamId) throws BattleException{
    	 Optional<Tournament> optionalTournament = tournamentRepository.findById(tournmentId);
         if(!optionalTournament.isPresent()) {
         	throw new BattleException("Tournament not found");
         }
         Tournament tournament = optionalTournament.get();
      
         Optional<Team> optionalTeam = teamRepository.findById(teamId);
     	  
     	if(!optionalTeam.isPresent()) {
           throw new BattleException("Team not found");
     		}
     	Team team = optionalTeam.get();
         if (!tournament.getTeams().contains(team)) {
       	  throw new BattleException("Team Not found");
          }
         List<TeamMember> teamMembers = tournamentRepository.findAllMembersByTournamentIdAndTeamId(tournmentId, teamId);
		List<TeamMemberDto> teamMembersDtos = new ArrayList<>();
		for(TeamMember tm : teamMembers) {
			TeamMemberDto teamMemberDto = new TeamMemberDto();
			teamMemberDto.setInGameName(tm.getInGameName());
			teamMemberDto.setTeamMemberId(tm.getTeamMemberId());
			teamMembersDtos.add(teamMemberDto);
       }
		 
    	return teamMembersDtos;
    }
    
    public String deleteTeamMemberRegister(Long tournmentId,Long teamId,Long teamMemberId) throws BattleException{
    	 	   
        Optional<Tournament> optionalTournament = tournamentRepository.findById(tournmentId);
        if(!optionalTournament.isPresent()) {
        	throw new BattleException("Tournament not found");
        }
        Tournament tournament = optionalTournament.get();
     
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
    	  
    	if(!optionalTeam.isPresent()) {
          throw new BattleException("Team not found");
    		}
    	Team team = optionalTeam.get();
        if (!tournament.getTeams().contains(team)) {
      	  throw new BattleException("Team Not found");
         }
    
    	List<TeamMember> ListteamMembers = tournamentRepository.findAllMembersByTeamIdAndTournamentId(tournmentId, team.getTeamId());

        
      	for (TeamMember t : ListteamMembers) {
      		tournament.getMembers().remove(t);
      		
      		
      		if (tournament.getMembers().contains(t)) {
                  tournament.getMembers().remove(t);
              }
             // tournament.getMembers().add(teamMember);
          }
      	
      	tournamentRepository.save(tournament);

      	return "Participant Remove from team!";
    }
    
  
    
    
 public TournamentDto updateTournament(Long tournamentId, TournamentDto tournamentDto) throws BattleException {
    	Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
		if(!optionalTournament.isPresent()) {
			throw new BattleException("Tournament id not found");
		}
		Tournament tournament = optionalTournament.get();
		
    	tournament.setCheckInTime(tournamentDto.getCheckInTime());
    	tournament.setCredentials(tournamentDto.getCredentials());
    	tournament.setFormat(tournamentDto.getFormat());
    	GameDto gameDto = tournamentDto.getGame();
    	Game game = new Game();
    	game.setAboutGame(gameDto.getAboutGame());
    	game.setDevice(gameDto.getDevice());
    	game.setGameId(gameDto.getGameId());
    	game.setGameImageName(gameDto.getGameImageName());
    	game.setGameName(gameDto.getGameImageName());
    	tournament.setGame(game);
    	tournament.setHowToJoin(tournamentDto.getHowToJoin());
    	tournament.setLocation(tournamentDto.getLocation());
    	tournament.setName(tournamentDto.getName());
    	tournament.setPrizeDistribution(tournamentDto.getPrizeDistribution());
    	tournament.setPrizepool(tournamentDto.getPrizepool());
    	tournament.setRegisteredTeam(tournamentDto.getRegisteredTeam());
    	tournament.setRegistrationEndDate(tournamentDto.getRegistrationEndDate());
    	tournament.setRules(tournamentDto.getRules());
    	tournament.setSlot(tournamentDto.getSlot());
    	tournament.setStatus(tournamentDto.getStatus());
    	tournament.setTournamentImageName(tournamentDto.getTournamentImageName());
//    	TournamentResultDto resultDto = tournamentDto.getResult();
//    	TournamentResult result = new TournamentResult();
//    	result.setRound(resultDto.getRound());
//    	result.setTeamResult(resultDto.getTeamsResult());
//    	result.setTournamentResultId(resultDto.getTournamentResultId());
//    	tournament.setResult(result);
    	
    	tournament.setTeamSize(tournamentDto.getTeamSize());
    	tournament.setTournamentDate(tournamentDto.getTournamentDate());
    	tournament.setTournamentDetail(tournamentDto.getTournamentDetail());

        Tournament savedTournament = tournamentRepository.save(tournament);
        TournamentDto savedTournamentDto = tournamentToTournamentDto(savedTournament);
        return savedTournamentDto;
    }
    
    
    public TournamentDto tournamentToTournamentDto(Tournament tournament) throws BattleException {
    	TournamentDto tournamentDto = new TournamentDto();
    	tournamentDto.setTournamentId(tournament.getTournamentId());
    	tournamentDto.setCheckInTime(tournament.getCheckInTime());
    	tournamentDto.setCredentials(tournament.getCredentials());
    	tournamentDto.setFormat(tournament.getFormat());
    	Game game = tournament.getGame();
    	GameDto gameDto = new GameDto();
    	gameDto.setGameId(game.getGameId());
    	gameDto.setAboutGame(game.getGameName());
    	gameDto.setDevice(game.getDevice());
    	gameDto.setGameName(game.getGameName());
    	gameDto.setGameImageName(game.getGameImageName());
   // 	gameDto.setUpcomingTournamentDto(getUpcomingGameTournaments(game.getGameId()));
    	
    	tournamentDto.setGame(gameDto);
    	tournamentDto.setHowToJoin(tournament.getHowToJoin());
    	tournamentDto.setLocation(tournament.getLocation());
    	tournamentDto.setName(tournament.getName());
    	tournamentDto.setPrizeDistribution(tournament.getPrizeDistribution());
    	tournamentDto.setPrizepool(tournament.getPrizepool());
    	tournamentDto.setRegisteredTeam(tournament.getRegisteredTeam());
    	tournamentDto.setRegistrationEndDate(tournament.getRegistrationEndDate());
    	tournamentDto.setTournamentDate(tournament.getTournamentDate());
    	tournamentDto.setTournamentEndDate(tournament.getTournamentEndDate());
    	
    	tournamentDto.setRules(tournament.getRules());
    	tournamentDto.setSlot(tournament.getSlot());
    	tournamentDto.setStatus(tournament.getStatus());
    	tournamentDto.setTournamentImageName(tournament.getTournamentImageName());
    	
    	if(tournament.getIsResultsDeclared().equals(true)) {
      	List<TournamentResultDto> tournamentResultdtos = tournamentResultService.getTournamentResultsByTournamentId(tournament.getTournamentId());
    	tournamentDto.setResult(tournamentResultdtos);
    	}
    	
    	Set<Team> teams = tournament.getTeams();
    
    	Set<TeamDto> teamsDtos = new HashSet<>();
    	for(Team t: teams) {
    		TeamDto teamDto = new TeamDto();
    		teamDto.setTeamId(t.getTeamId());
    		teamDto.setTeamName(t.getTeamName());
    		User captain = t.getCaptain();
    		UserUpdateDto updatedUser = new UserUpdateDto();
    		updatedUser.setEmail(captain.getEmail());
    		updatedUser.setName(captain.getName());
    		updatedUser.setUserId(captain.getUserId());
    		updatedUser.setUserImageName(captain.getUserImageName());
    		updatedUser.setBgmiId(captain.getBgmiId());
    		teamDto.setCaptain(updatedUser);
   // 	List<TeamMemberDto> teamMemberRegister = getTeamMemberRegister(tournament.getTournamentId(), t);
 //   		List<TeamMember> teamMembers = tournamentRepository.findAllMembersByTeamIdAndTournamentId(tournament.getTournamentId(), t.getTeamId());
    		List<TeamMember> teamMembers = tournamentRepository.findAllMembersByTournamentIdAndTeamId(tournament.getTournamentId(), t.getTeamId());
    		List<TeamMemberDto> teamMembersDtos = new ArrayList<>();
    		for(TeamMember tm : teamMembers) {
    			System.out.println(tm.getTeam().getTeamId());
    			TeamMemberDto teamMemberDto = new TeamMemberDto();
    			teamMemberDto.setInGameName(tm.getInGameName());
    			teamMemberDto.setTeamMemberId(tm.getTeamMemberId());
    			teamMembersDtos.add(teamMemberDto);
           }
    		teamDto.setMembers(teamMembersDtos);
    		teamsDtos.add(teamDto);
    	}
    	tournamentDto.setTeamsDto(teamsDtos);
    	Set<TeamMember> teamMembers = tournament.getMembers();
    	Set<TeamMemberDto> teamMembersDtos = new HashSet<>();
        for(TeamMember t : teamMembers) {
			TeamMemberDto teamMember =	teamMemberService.teamMemberToTeamMemberDto(t);
			teamMembersDtos.add(teamMember);
        }
    	tournamentDto.setTeamMembersDto(teamMembersDtos);
    	tournamentDto.setTeamsDto(teamsDtos);
    	tournamentDto.setTeamSize(tournament.getTeamSize());
    	tournamentDto.setTournamentDate(tournament.getTournamentDate());
    	tournamentDto.setTournamentDetail(tournament.getTournamentDetail());
    	
    	return tournamentDto;
    }
    
    public Tournament tournamentDtoToTournament(TournamentDto tournamentDto) throws BattleException {
    	Tournament tournament = new Tournament();
    	tournament.setTournamentId(tournamentDto.getTournamentId());
    	tournament.setCheckInTime(tournamentDto.getCheckInTime());
    	tournament.setCredentials(tournamentDto.getCredentials());
    	tournament.setFormat(tournamentDto.getFormat());
    	GameDto gameDto = tournamentDto.getGame();
    	Game game = new Game();
    	game.setAboutGame(gameDto.getAboutGame());
    	game.setDevice(gameDto.getDevice());
    	game.setGameId(gameDto.getGameId());
    	game.setGameImageName(gameDto.getGameImageName());
    	game.setGameName(gameDto.getGameImageName());
    	tournament.setGame(game);
    	tournament.setHowToJoin(tournamentDto.getHowToJoin());
    	tournament.setLocation(tournamentDto.getLocation());
    	tournament.setName(tournamentDto.getName());
    	tournament.setPrizeDistribution(tournamentDto.getPrizeDistribution());
    	tournament.setPrizepool(tournamentDto.getPrizepool());
    	tournament.setRegisteredTeam(tournamentDto.getRegisteredTeam());
    	tournament.setRegistrationEndDate(tournamentDto.getRegistrationEndDate());
    	tournament.setTournamentDate(tournamentDto.getTournamentDate());
    	tournament.setTournamentEndDate(tournamentDto.getTournamentEndDate());
    	tournament.setRules(tournamentDto.getRules());
    	tournament.setSlot(tournamentDto.getSlot());
    	tournament.setStatus(tournamentDto.getStatus());
    	tournament.setTournamentImageName(tournamentDto.getTournamentImageName());
    //	tournament.setResult(tournamentDto.getResult());
    	Set<TeamDto> teamDto = tournamentDto.getTeamsDto();
    
    	Set<Team> teams = new HashSet<>();
    	for(TeamDto t: teamDto) {
    		Team team = teamServices.teamDtoToTeam(t);
    		teams.add(team);
    	}
    	Set<TeamMemberDto> teamMemberDto = tournamentDto.getTeamMembersDto();
    	Set<TeamMember> teamMembers = new HashSet<>();
        for(TeamMemberDto t : teamMemberDto) {
			TeamMember teamMember =	teamMemberService.teamMemberDtoToTeamMember(t);
			teamMembers.add(teamMember);
        }
    	tournament.setMembers(teamMembers);
    	tournament.setTeams(teams);
    	tournament.setTeamSize(tournamentDto.getTeamSize());
    	tournament.setTournamentDate(tournamentDto.getTournamentDate());
    	tournament.setTournamentDetail(tournamentDto.getTournamentDetail());
    	
    	return tournament;
    }

}
