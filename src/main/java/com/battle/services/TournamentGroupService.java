package com.battle.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.battle.entities.Team;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentGroup;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamResultDto;
import com.battle.payloads.TournamentGroupDto;
import com.battle.repositories.TeamRepository;
import com.battle.repositories.TournamentGroupRepository;
import com.battle.repositories.TournamentRepository;
import com.battle.utils.TournamentUtils;

@Service
@Transactional
public class TournamentGroupService {

	@Autowired
	private TournamentGroupRepository tournamentGroupRepository;
	
	@Autowired
	private TournamentRepository tournamentRepository;
	
	@Autowired
	private TournamentResultService tournamentResultService;
	
	@Autowired
	private TeamRepository teamRepository;
	
	 public Map<Integer, List<TeamDto>> createGroupsForRound(Long tournamentId,int round, int groupSize) throws BattleException {
	     //List<Team> teams = teamRepository.findAll();
		 Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
	     if(!optionalTournament.isPresent()) {
	     	throw new BattleException("Tournament not found");
	     }
	     Tournament tournament = optionalTournament.get();
	     Set<Team> teams = tournament.getTeams();
	     List<Team> teamList = new ArrayList<>(teams);
	     Map<Integer, List<Team>> divideTeamsIntoGroups = TournamentUtils.divideTeamsIntoGroups(teamList, groupSize);
	     Map<Integer, List<TeamDto>> teamGroups = new HashMap<>();
	     Set<Integer> keySet = divideTeamsIntoGroups.keySet();
	    // Collection<List<Team>> nextTeam = divideTeamsIntoGroups.values();
	   
	  //   List<Team> teamGroupList = nextTeam.iterator().next();
	     List<TournamentGroup> optionalTournamentGroup = tournamentGroupRepository.findByTournamentAndRound(tournament, round);
	     if(!optionalTournamentGroup.isEmpty()) {
	     	throw new BattleException("For Tournament and Round Groups already exists!");
	     }
	for(Integer i:keySet) {
		
		TournamentGroup tournamentGroup = new TournamentGroup();
		tournamentGroup.setRound(round);
		tournamentGroup.setTournament(tournament);
		List<Team> teamGroup = new ArrayList<>();
		List<TeamDto> teamsDto = new ArrayList<>();
	     for(Team t : divideTeamsIntoGroups.get(i))
	     {
	    	 TeamDto teamDto = new TeamDto();
	    	 teamDto.setTeamId(t.getTeamId());
	    	 teamDto.setTeamName(t.getTeamName());
	    	 teamDto.setTeamImageName(t.getTeamImageName());
	    	 teamGroup.add(t);
	    	 teamsDto.add(teamDto);
	     }
	     tournamentGroup.setTeam(teamGroup);
	     tournamentGroup.setGroupNo(i);
	     teamGroups.put(i, teamsDto);
	     TournamentGroup tournamentGroup2 = tournamentGroupRepository.save(tournamentGroup);
		}
	     return teamGroups;
	 }
	
	 
	 public Map<Integer, List<TeamDto>> createQualifiedTeamsGroupByRound(Long tournamentId, int round,int limit,int groupSize) throws BattleException {
	    	Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
		     if(!optionalTournament.isPresent()) {
		     	throw new BattleException("Tournament not found");
		     }
		     Tournament tournament = optionalTournament.get();
	    	List<TeamResultDto> teamResultDto = tournamentResultService.getTournamentResultsByTournamentIdAndRound(tournamentId, round);
	    	if(teamResultDto.isEmpty()) {
		     	throw new BattleException("Tournament and Round not exists!");
		     }
	    	List<TeamResultDto> qualifiedTeamResultDto = new ArrayList<>();
	    	List<Team> teamList = new ArrayList<>();
	    	for(TeamResultDto trd:teamResultDto)
	    		if(trd.getTotalPoint() >= limit) {
	    			qualifiedTeamResultDto.add(trd);
	    			;
	    			Optional<Team> team = teamRepository.findById(trd.getTeam().getTeamId());
	    			teamList.add(team.get());
	    		}
	    		
	    	
	    	qualifiedTeamResultDto.sort(Comparator.comparing(TeamResultDto::getTotalPoint));
	     	Collections.reverse(qualifiedTeamResultDto);
	     	List<TeamResultDto> qualifiedTeamResultDto2 = qualifiedTeamResultDto;
	     	
	     		
	     	Map<Integer, List<Team>> divideTeamsIntoGroups = TournamentUtils.divideTeamsIntoGroups(teamList, groupSize);
		     Map<Integer, List<TeamDto>> teamGroups = new HashMap<>();
		     Set<Integer> keySet = divideTeamsIntoGroups.keySet();
		     round++;
		 	for(Integer i:keySet) {
				
				TournamentGroup tournamentGroup = new TournamentGroup();
				tournamentGroup.setRound(round);
				tournamentGroup.setTournament(tournament);
				List<Team> teamGroup = new ArrayList<>();
				List<TeamDto> teamsDto = new ArrayList<>();
			     for(Team t : divideTeamsIntoGroups.get(i))
			     {
			    	 TeamDto teamDto = new TeamDto();
			    	 teamDto.setTeamId(t.getTeamId());
			    	 teamDto.setTeamName(t.getTeamName());
			    	 teamDto.setTeamImageName(t.getTeamImageName());
			    	 teamGroup.add(t);
			    	 teamsDto.add(teamDto);
			     }
			     tournamentGroup.setTeam(teamGroup);
			     tournamentGroup.setGroupNo(i);
			     teamGroups.put(i, teamsDto);
			     TournamentGroup savedTournamentGroup = tournamentGroupRepository.save(tournamentGroup);
				}
			     return teamGroups;
	 }

	 public List<TeamDto> getGroupTeams(Long tournamentId,int round, int groupNo) throws BattleException{
		 Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
	     if(!optionalTournament.isPresent()) {
	     	throw new BattleException("Tournament not found");
	     }
	     Tournament tournament = optionalTournament.get();
		 Optional<TournamentGroup> optionalTournamentGroup = tournamentGroupRepository.findByTournamentAndRoundAndGroupNo(tournament, round,groupNo);
	     if(!optionalTournamentGroup.isPresent()) {
	     	throw new BattleException("Tournament and Round or Group not exists!");
	     }
	     
	     TournamentGroup tournamentGroup = optionalTournamentGroup.get();
	     List<Team> listTeam = tournamentGroup.getTeam();
	     List<TeamDto> teamDtos = new ArrayList<>();
	     
	     
//	     List<TournamentGroupDto> listTournamentGroupDto = new ArrayList<>();
//		 List<TeamDto> teamDtos = new ArrayList<>();
//	     for(TournamentGroup tg : tournamentGroup) {
//	    	 
//	    	 if(tg.getTournamentGroupId().equals(tournamentGroupId)) {
//		    	 TournamentGroupDto tournamentGroupDto = new TournamentGroupDto();
//		    	 tournamentGroupDto.setTournamentGroupId(tg.getTournamentGroupId());
//		    	 tournamentGroupDto.setRound(tg.getRound());
//		    	 List<Team> listTeam = tg.getTeam();
//		    
		    	 for(Team t: listTeam) {
		    		 TeamDto teamDto = new TeamDto();
		    		 teamDto.setTeamId(t.getTeamId());
		    		 teamDto.setTeamImageName(t.getTeamImageName());
		    		 teamDto.setTeamName(t.getTeamName());
		    		// teamDto.setCaptain(t.getCaptain())
		    		// teamDto.setMembers(t.getMembers());
		    		 teamDtos.add(teamDto);;
		    	 }
//	    	 tournamentGroupDto.setTeam(teamName);
//	    	 tournamentGroupDto.setTounamentName(tg.getTournament().getName());;
//	    	 listTournamentGroupDto.add(tournamentGroupDto);
//	    	 }
//	     }
	     return teamDtos;
	     
	 }    
	     public List<TournamentGroupDto> getTeams(Long tournamentId,int round) throws BattleException{
			 Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
		     if(!optionalTournament.isPresent()) {
		     	throw new BattleException("Tournament not found");
		     }
		     Tournament tournament = optionalTournament.get();
			 List<TournamentGroup> tournamentGroup = tournamentGroupRepository.findByTournamentAndRound(tournament, round);
		     if(tournamentGroup.isEmpty()) {
		     	throw new BattleException("Tournament and Round group not exists!");
		     }
		     List<TournamentGroupDto> listTournamentGroupDto = new ArrayList<>();
		     for(TournamentGroup tg : tournamentGroup) {
		    	 TournamentGroupDto tournamentGroupDto = new TournamentGroupDto();
		    	 tournamentGroupDto.setTournamentGroupId(tg.getTournamentGroupId());
		    	 tournamentGroupDto.setRound(tg.getRound());
		    	 List<Team> listTeam = tg.getTeam();
		    	 List<String> teamName = new ArrayList<>();
		    	 for(Team t: listTeam) {
		    		 teamName.add(t.getTeamName());
		    	 }
		    	 tournamentGroupDto.setTeamDto(teamName);
		    	 tournamentGroupDto.setTounamentName(tg.getTournament().getName());;
		    	 listTournamentGroupDto.add(tournamentGroupDto);
		     }
		     return listTournamentGroupDto;
	     }
}
