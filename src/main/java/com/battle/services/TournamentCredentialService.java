package com.battle.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.battle.entities.Team;
import com.battle.entities.TeamCredential;
import com.battle.entities.TeamResult;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentCredential;
import com.battle.entities.TournamentGroup;
import com.battle.entities.TournamentResult;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamCredentialDto;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamResultDto;
import com.battle.payloads.TournamentCredentialDto;
import com.battle.payloads.TournamentGroupDto;
import com.battle.payloads.TournamentResultDto;
import com.battle.payloads.UpdateTournamentCredentialDto;
import com.battle.repositories.TeamCredentialRepository;
import com.battle.repositories.TeamRepository;
import com.battle.repositories.TeamResultRepository;
import com.battle.repositories.TournamentCredentialRepository;
import com.battle.repositories.TournamentGroupRepository;
import com.battle.repositories.TournamentRepository;

@Service
@Transactional
public class TournamentCredentialService {

	@Autowired
	private TournamentCredentialRepository tournamentCredentialRepository;
	@Autowired
	private TournamentRepository tournamentRepository;
	@Autowired
	private TournamentGroupRepository tournamentGroupRepository;
    
    @Autowired
    private TeamCredentialRepository teamCredentialRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private TournamentGroupService tournamentGroupService;
    
    public TournamentCredentialDto createTournamentResult(Long tournamentId, int round, int groupNo) throws BattleException {
    	Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
    	if (!optionalTournament.isPresent()) {
    		throw new BattleException("Tournament not found");
      }
    	Tournament tournament = optionalTournament.get();
    	 List<TournamentCredential> optionalCredential = tournamentCredentialRepository.findByTournament(tournament);
    	for(TournamentCredential tr : optionalCredential) {
    		if(tr.getRound() == round && tournament.getTournamentId().equals(tournamentId) && tr.getGroupNo() == groupNo) {
    			 throw new BattleException("Tournament result with Tournament id " + tournamentId + " and round "+ round + " and group " + groupNo +" Already Created!");
    		}
    	}
    	
//        if (optionalResult.isEmpty()) {
//            throw new BattleException("Tournament result with id " + tournamentId + " not found");
//        }
        
        
//    	TournamentDto tournamentDto = tournamentService.getTournament(tournamentId);
//    	Set<TeamDto> teamsDto = tournamentDto.getTeamsDto();
    	TournamentCredential tournamentCredential = new TournamentCredential();
//    	Set<Team> teams = tournament.getTeams();
    	
    	Optional<TournamentGroup> optionalTournamentGroup = tournamentGroupRepository.findByTournamentAndRoundAndGroupNo(tournament, round,groupNo);
	     if(!optionalTournamentGroup.isPresent()) {
	     	throw new BattleException("For Tournament and Round Group not exists!");
	     }
	     
	     TournamentGroup tournamentGroup = optionalTournamentGroup.get();
	     List<Team> teams = tournamentGroup.getTeam();
    	//Set<Team> teamList  = new HashSet<>();
    	// Map<Long, Integer> teamResult = new HashMap<>();
    	 List<TeamCredential> listTeamsCredential = new ArrayList<>();
    	for(Team t: teams) {
    		//teamResult.put(t.getTeamId(), 0);
    		int s = 1;
    		TeamCredential teamsCredential = new TeamCredential();
    		teamsCredential.setTeam(t);
    		teamsCredential.setSlot(s);
    		teamsCredential.setCheckIn(false);    	
    		listTeamsCredential.add(teamsCredential);
    		//teamList.add(t);
    		teamCredentialRepository.save(teamsCredential);
    		s++;
    	}

 		//	tournamentResult.setTeamResult(teamResult);
 			tournamentCredential.setTeamCredentials(listTeamsCredential);;
    		tournamentCredential.setRound(round);
    		tournamentCredential.setTournament(tournament);
    		tournamentCredential.setGroupNo(groupNo);
    		tournamentCredential.setMap(null);
    		tournamentCredential.setPass(null);
    		tournamentCredential.setRoomId(null);
    		tournamentCredential.setStartDateTime(null);//
    		tournamentCredential.setTournament(tournament);
    		
    		
    		//        // Check if the tournament result already exists
//        Optional<TournamentResult> existingResult = tournamentResultRepository
//                .findByTournamentAndTeam(tournamentResult.getTournament().getTournamentId(), tournamentResult.getTeam().getTeamId());
//        if (existingResult.isPresent()) {
//            throw new IllegalArgumentException("Tournament result for team " + tournamentResult.getTeam().getTeamId()
//                    + " in tournament " + tournamentResult.getTournament().getTournamentId() + " already exists");
//        }
    		TournamentCredential tournamentCred= tournamentCredentialRepository.save(tournamentCredential);
    		tournament.setIsResultsDeclared(true);
    		tournamentRepository.save(tournament);
    		
    		
    		TournamentCredentialDto tournamentCredDto = tournamentCredentialToTournamentDto(tournamentCred);
        return tournamentCredDto;
    }
    
  

	public TournamentCredentialDto updateTournamentCredential(Long tournamentId, int round,
			 int groupNo,UpdateTournamentCredentialDto updateTournamentResult) throws BattleException {
	//	List<TeamCredentialDto> listTeamCredentialDto,
	  	Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
    	if (!optionalTournament.isPresent()) {
    		throw new BattleException("Tournament not found");
      }
//    	Optional<TournamentResult> optionalRound = tournamentResultRepository.findByRound(round);
//    	if (!optionalRound.isPresent()) {
//    		throw new BattleException("Tournament not found");
//      }
    	Tournament tournament = optionalTournament.get();
    	Optional<TournamentCredential> optionalTournamentCredential =tournamentCredentialRepository.findByTournamentAndRoundAndGroupNo(tournament, round,groupNo);
    	if (!optionalTournamentCredential.isPresent()) {
    		throw new BattleException("Tournament result with Tournament id " + tournamentId + " and round "+ round + " and group " + groupNo +" Not Exist!");
      }
    	TournamentCredential tournamentCredential = optionalTournamentCredential.get();
 //   	List<TeamCredential> listTeamCred = tournamentCredential.getTeamCredentials();
    	tournamentCredential.setMap(updateTournamentResult.getMap());
    	tournamentCredential.setPass(updateTournamentResult.getPass());   	
    	tournamentCredential.setRoomId(updateTournamentResult.getRoomId());
    	tournamentCredential.setStartDateTime(updateTournamentResult.getStartTime());    	
    	TournamentCredentialDto updateTournamentCredentialDto = tournamentCredentialToTournamentDto(tournamentCredential);
        return updateTournamentCredentialDto;
	}

	public void deleteTournamentCredential(Long id) {
		// TODO Auto-generated method stub
		
	}

	public TournamentCredentialDto getTournamentCredentialByTournamentIdAndRoundAndTeamId(Long tournamentId, int round,int groupNo,
			long teamId) throws BattleException {
		// TODO Auto-generated method stub
		Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
    	if (!optionalTournament.isPresent()) {
    		throw new BattleException("Tournament not found");
      }
//    	Optional<TournamentResult> optionalRound = tournamentResultRepository.findByRound(round);
//    	if (!optionalRound.isPresent()) {
//    		throw new BattleException("Tournament not found");
//      }
    	Tournament tournament = optionalTournament.get();
    	Optional<TournamentCredential> optionalTournamentCredential =tournamentCredentialRepository.findByTournamentAndRoundAndGroupNo(tournament, round,groupNo);
    	if (!optionalTournamentCredential.isPresent()) {
    		throw new BattleException("Tournament result with Tournament id " + tournamentId + " and round "+ round + " and group " + groupNo +" Not Exist!");
      }
    	TournamentCredential tournamentCredential = optionalTournamentCredential.get();
    	List<TeamCredential> listTeamCredential = tournamentCredential.getTeamCredentials();
    	
    	
    	List<TeamCredentialDto> listTeamsCredentialDto = new ArrayList<>();
//    	for(TeamCredential tr: listTeamCredential) {
//    		TeamCredentialDto teamsCredentialDto = new TeamCredentialDto();
//    		Team team = tr.getTeam();
//    		TeamDto teamDto = new TeamDto();
//    		teamDto.setTeamId(team.getTeamId());
//    		teamDto.setTeamName(team.getTeamName());
//    		teamDto.setTeamImageName(team.getTeamImageName());
//    		teamsCredentialDto.setCredentialId(tr.getCredentialId());
//    		teamsCredentialDto.setTeamDto(teamDto);
//    		teamsCredentialDto.setSlot(tr.getSlot());
//    		listTeamsCredentialDto.add(teamsCredentialDto);
//    	}
    	
    	
    	Optional<Team> optionalTeam = teamRepository.findById(teamId);
    	if (!optionalTeam.isPresent()) {
    		throw new BattleException("Team not found");
      }
    	List<TeamDto> groupTeams = tournamentGroupService.getGroupTeams(tournamentId, round, groupNo);
   	 Optional<TournamentGroup> tournamentGroup = tournamentGroupRepository.findByTournamentAndRoundAndGroupNo(tournament,round, groupNo);
     if(tournamentGroup.isEmpty()) {
     	throw new BattleException("Tournament and Round group not exists!");
     }
     List<TournamentGroupDto> listTournamentGroupDto = new ArrayList<>();
     List<Team> listTeam = new ArrayList<>();
     TournamentGroup tg = tournamentGroup.get(); 
    	 TournamentGroupDto tournamentGroupDto = new TournamentGroupDto();
    	 tournamentGroupDto.setTournamentGroupId(tg.getTournamentGroupId());
    	 tournamentGroupDto.setRound(tg.getRound());
    	 listTeam = tg.getTeam();
    	int slot = 0;
    
    	if(!listTeam.contains(optionalTeam.get())){
    		
    		throw new BattleException("Team not Qualified or Not Registered!");
    	}
    		for(TeamCredential tr: listTeamCredential) {
	    		if(tr.getTeam().equals(optionalTeam.get())) {
	    			 slot = tr.getSlot();
	    		}
	    		else {
	    			throw new BattleException("Team not Register");
	    		}    		
    	}
    	TournamentCredentialDto tournamentCredDto = tournamentCredentialToTournamentDto(tournamentCredential);
    	tournamentCredDto.setSlot(slot);	
		return tournamentCredDto;
	}
	
	
	
	  public TournamentCredentialDto tournamentCredentialToTournamentDto(TournamentCredential tournamentCredential) {
	    	TournamentCredentialDto tournamentCredentialDto = new TournamentCredentialDto();
	    	tournamentCredentialDto.setTournamentCredentialId(tournamentCredential.getTournamentCredentialId());
	    	tournamentCredentialDto.setStartTime(tournamentCredential.getStartDateTime());
	    	tournamentCredentialDto.setRound(tournamentCredential.getRound());
	    	tournamentCredentialDto.setRoomId(tournamentCredential.getRoomId());
	    	tournamentCredentialDto.setPass(tournamentCredential.getPass());
	    	tournamentCredentialDto.setGroupNo(tournamentCredential.getGroupNo());
	    	tournamentCredentialDto.setMap(tournamentCredential.getMap());
	    	List<TeamCredential> teamCredentials = tournamentCredential.getTeamCredentials();
	    	List<TeamCredentialDto> teamCredentialDtos = new ArrayList<>();
	    	for(TeamCredential tC : teamCredentials) {
	    		TeamCredentialDto teamCredentialDto = new TeamCredentialDto();
	    		teamCredentialDto.setCredentialId(tC.getCredentialId()); 
	    		teamCredentialDto.setSlot(tC.getSlot());
	    		teamCredentialDto.setCheckIn(tC.getCheckIn());
	    		Team team = tC.getTeam();
	    		TeamDto teamDto = new TeamDto();
	    		teamDto.setTeamId(team.getTeamId());
	    		teamDto.setTeamName(team.getTeamName());
	    		teamDto.setTeamImageName(team.getTeamImageName());
	    		teamCredentialDto.setTeamDto(teamDto);
	    		teamCredentialDtos.add(teamCredentialDto);
	    	}
	    	return tournamentCredentialDto;
	    }
	
}
