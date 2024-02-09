package com.battle.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.TeamResult;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentGroup;
import com.battle.entities.TournamentResult;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamMemberDto;
import com.battle.payloads.TeamResultDto;
import com.battle.payloads.TournamentDto;
import com.battle.payloads.TournamentResultDto;
import com.battle.payloads.UserUpdateDto;
import com.battle.repositories.TeamRepository;
import com.battle.repositories.TeamResultRepository;
import com.battle.repositories.TournamentGroupRepository;
import com.battle.repositories.TournamentRepository;
import com.battle.repositories.TournamentResultRepository;
import com.battle.utils.TournamentUtils;

@Service
public class TournamentResultService {

    private final TournamentResultRepository tournamentResultRepository;

    @Autowired
    public TournamentResultService(TournamentResultRepository tournamentResultRepository) {
        this.tournamentResultRepository = tournamentResultRepository;
    }

//    @Autowired
//    private TournamentService tournamentService;
    
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
	private TournamentGroupRepository tournamentGroupRepository;
    
    @Autowired
    private TeamResultRepository teamResultRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    public TournamentResultDto createTournamentResult(Long tournamentId, int round, int groupNo) throws BattleException {
    	Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
    	if (!optionalTournament.isPresent()) {
    		throw new BattleException("Tournament not found");
      }
    	Tournament tournament = optionalTournament.get();
    	List<TournamentResult> optionalResult = tournamentResultRepository.findByTournament(tournament);
    	for(TournamentResult tr : optionalResult) {
    		if(tr.getRound() == round && tournament.getTournamentId().equals(tournamentId) && tr.getGroupNo() == groupNo) {
    			 throw new BattleException("Tournament result with Tournament id " + tournamentId + " and round "+ round + " and group " + groupNo +" Already Created!");
    		}
    	}
    	
//        if (optionalResult.isEmpty()) {
//            throw new BattleException("Tournament result with id " + tournamentId + " not found");
//        }
        
        
//    	TournamentDto tournamentDto = tournamentService.getTournament(tournamentId);
//    	Set<TeamDto> teamsDto = tournamentDto.getTeamsDto();
    	TournamentResult tournamentResult = new TournamentResult();
//    	Set<Team> teams = tournament.getTeams();
    	
    	Optional<TournamentGroup> optionalTournamentGroup = tournamentGroupRepository.findByTournamentAndRoundAndGroupNo(tournament, round,groupNo);
	     if(!optionalTournamentGroup.isPresent()) {
	     	throw new BattleException("For Tournament and Round Group not exists!");
	     }
	     
	     TournamentGroup tournamentGroup = optionalTournamentGroup.get();
	     List<Team> teams = tournamentGroup.getTeam();
    	//Set<Team> teamList  = new HashSet<>();
    	// Map<Long, Integer> teamResult = new HashMap<>();
    	 List<TeamResult> listTeamsResult = new ArrayList<>();
    	for(Team t: teams) {
    		//teamResult.put(t.getTeamId(), 0);
    		TeamResult teamsResult = new TeamResult();
    		teamsResult.setTeam(t);
    		teamsResult.setPlacementPoint(0);
    		teamsResult.setKillPoint(0);
    		teamsResult.setTotalPoint(0);
    		listTeamsResult.add(teamsResult);
    		//teamList.add(t);
    		teamResultRepository.save(teamsResult);
    	}

 		//	tournamentResult.setTeamResult(teamResult);
 			tournamentResult.setTeamResult(listTeamsResult);
    		tournamentResult.setRound(round);
    		tournamentResult.setTournament(tournament);
    		tournamentResult.setGroupNo(groupNo);
    		//    	
//        // Check if the tournament result already exists
//        Optional<TournamentResult> existingResult = tournamentResultRepository
//                .findByTournamentAndTeam(tournamentResult.getTournament().getTournamentId(), tournamentResult.getTeam().getTeamId());
//        if (existingResult.isPresent()) {
//            throw new IllegalArgumentException("Tournament result for team " + tournamentResult.getTeam().getTeamId()
//                    + " in tournament " + tournamentResult.getTournament().getTournamentId() + " already exists");
//        }
    		TournamentResult tournamentResult2 = tournamentResultRepository.save(tournamentResult);
    		tournament.setIsResultsDeclared(true);
    		tournamentRepository.save(tournament);
    		TournamentResultDto tournamentResultDto = tournamentResultToTournamentResultDto(tournamentResult2);
        return tournamentResultDto;
    }

    public TournamentResultDto updateTournamentResult(Long tournamentId,int round, List<TeamResultDto> teamResult,int groupNo ) throws BattleException {
        // Check if the tournament result exists
    	Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
    	if (!optionalTournament.isPresent()) {
    		throw new BattleException("Tournament not found");
      }
//    	Optional<TournamentResult> optionalRound = tournamentResultRepository.findByRound(round);
//    	if (!optionalRound.isPresent()) {
//    		throw new BattleException("Tournament not found");
//      }
    	Tournament tournament = optionalTournament.get();
    	Optional<TournamentResult> optionalTournamentResult =tournamentResultRepository.findByTournamentAndRoundAndGroupNo(tournament, round,groupNo);
    	if (!optionalTournamentResult.isPresent()) {
    		throw new BattleException("Tournament result with Tournament id " + tournamentId + " and round "+ round + " and group " + groupNo +" Not Exist!");
      }
    	TournamentResult tournamentResult = optionalTournamentResult.get();
    	List<TeamResult> listTeamResult = tournamentResult.getTeamResult();
    	for(TeamResultDto tR:teamResult) {
    		Long teamResultId = tR.getTeamResultId();
    		Optional<TeamResult> optionalTeamResult = teamResultRepository.findById(teamResultId);
    		if (!optionalTeamResult.isPresent()) {
        		throw new BattleException("Team Result not found!");
    		}
    		TeamResult teamResult2 = optionalTeamResult.get();
    		if(!listTeamResult.contains(teamResult2)) {
    			throw new BattleException("Team Result not Matched from Tournament!");
    		};
    		teamResult2.setKillPoint(tR.getKillPoint());
			teamResult2.setPlacementPoint(tR.getPlacementPoint());
			teamResult2.setTotalPoint(tR.getTotalPoint());
			teamResultRepository.save(teamResult2);
    	}
    	TournamentResult updateTournamentResult = tournamentResultRepository.save(tournamentResult);
    	//tournament.setResult(updateTournamentResult);
    //	tournamentRepository.save(tournament);
    	tournament.setIsResultsDeclared(true);
		tournamentRepository.save(tournament);
TournamentResultDto updateTournamentResultDto = tournamentResultToTournamentResultDto(updateTournamentResult);
        return updateTournamentResultDto;
    }

    public List<TeamResultDto> getTournamentResultsByTournamentIdAndRoundAndGroup(Long tournamentId, int round,int groupNo ) throws BattleException {
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
    	Optional<TournamentResult> optionalTournamentResult =tournamentResultRepository.findByTournamentAndRoundAndGroupNo(tournament, round,groupNo);
    	if (!optionalTournamentResult.isPresent()) {
    		throw new BattleException("Tournament result with Tournament id " + tournamentId + " and round "+ round + " and group " + groupNo +" Not Exist!");
      }
    	TournamentResult tournamentResult = optionalTournamentResult.get();
    	List<TeamResult> listTeamResult = tournamentResult.getTeamResult();
    	
    	List<TeamResultDto> listTeamsResultDto = new ArrayList<>();
    	for(TeamResult tr: listTeamResult) {
    		TeamResultDto teamsResultDto = new TeamResultDto();
    		Team team = tr.getTeam();
    		TeamDto teamDto = new TeamDto();
    		teamDto.setTeamId(team.getTeamId());
    		teamDto.setTeamName(team.getTeamName());
    		teamDto.setTeamImageName(team.getTeamImageName());
    		teamsResultDto.setTeamResultId(tr.getTeamResultId());
    		teamsResultDto.setTeam(teamDto);
    		teamsResultDto.setPlacementPoint(tr.getPlacementPoint());
    		teamsResultDto.setKillPoint(tr.getKillPoint());
    		teamsResultDto.setTotalPoint(tr.getTotalPoint());
    		listTeamsResultDto.add(teamsResultDto);
    	}
    	listTeamsResultDto.sort(Comparator.comparing(TeamResultDto::getTotalPoint));
    	Collections.reverse(listTeamsResultDto);
		return listTeamsResultDto;
	}
    
    public List<TeamResultDto> getTournamentResultsByTournamentIdAndRound(Long tournamentId, int round ) throws BattleException {
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
    	List<TournamentResult> TournamentResultList =tournamentResultRepository.findByTournamentAndRound(tournament, round);
    	if (TournamentResultList.isEmpty()) {
    		throw new BattleException("Tournament result with Tournament id " + tournamentId + " and round "+ round +" Not Exist!");
      }
    	List<TeamResult> listTeamResult = new ArrayList<>();
    	for(TournamentResult trs:TournamentResultList) {
             listTeamResult.addAll(trs.getTeamResult());
    	}
    	List<TeamResultDto> listTeamsResultDto = new ArrayList<>();
    	for(TeamResult tr: listTeamResult) {
    		TeamResultDto teamsResultDto = new TeamResultDto();
    		Team team = tr.getTeam();
    		TeamDto teamDto = new TeamDto();
    		teamDto.setTeamId(team.getTeamId());
    		teamDto.setTeamName(team.getTeamName());
    		teamDto.setTeamImageName(team.getTeamImageName());
    		teamsResultDto.setTeamResultId(tr.getTeamResultId());
    		teamsResultDto.setTeam(teamDto);
    		teamsResultDto.setPlacementPoint(tr.getPlacementPoint());
    		teamsResultDto.setKillPoint(tr.getKillPoint());
    		teamsResultDto.setTotalPoint(tr.getTotalPoint());
    		listTeamsResultDto.add(teamsResultDto);
    	}
    	listTeamsResultDto.sort(Comparator.comparing(TeamResultDto::getTotalPoint));
    	Collections.reverse(listTeamsResultDto);
		return listTeamsResultDto;
	}
	
    public void deleteTournamentResult(Long id) throws BattleException {
        // Check if the tournament result exists
    	Optional<TournamentResult> optionalResult = tournamentResultRepository.findById(id);
        if (optionalResult.isEmpty()) {
            throw new BattleException("Tournament result with id " + id + " not found");
        }
        tournamentResultRepository.delete(optionalResult.get());
    }

    public List<TournamentResultDto> getTournamentResultsByTournamentId(Long tournamentId) throws BattleException {
    Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
    if(!optionalTournament.isPresent()) {
		throw new BattleException("Tournament Not Found!");
	}	
    Tournament tournament = optionalTournament.get();
    	
    	if (!tournament.getIsResultsDeclared()) {
            return (List<TournamentResultDto>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("Results are not yet declared for this tournament");
        }
    	List<TournamentResult> tournamentResults = tournamentResultRepository.findByTournament(tournament);
    	if(tournamentResults.isEmpty()) {
    		throw new BattleException("Results are yet to be announced");
    	}
    	List<TournamentResultDto> tournamentResultDtos = new ArrayList<>();
    	for(TournamentResult t: tournamentResults) {
    		TournamentResultDto tournamentResultDto = tournamentResultToTournamentResultDto(t);
    		tournamentResultDtos.add(tournamentResultDto);
    	}
    	return tournamentResultDtos;
    }

    public TournamentResultDto getTournamentResultById(Long id) throws BattleException {
        Optional<TournamentResult> optionalResult = tournamentResultRepository.findById(id);
        if (optionalResult.isEmpty()) {
            throw new BattleException("Tournament result with id " + id + " not found");
        }
        TournamentResult tournamentResult = optionalResult.get();
        TournamentResultDto tournamentResultDto = tournamentResultToTournamentResultDto(tournamentResult);
        return tournamentResultDto;
    }
    
    public Map<Integer, List<TeamDto>> getQualifiedTeamsByRound(Long tournamentId, int round,int limit) throws BattleException {
    	Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
	     if(!optionalTournament.isPresent()) {
	     	throw new BattleException("Tournament not found");
	     }
	     Tournament tournament = optionalTournament.get();
    	List<TeamResultDto> teamResultDto = getTournamentResultsByTournamentIdAndRound(tournamentId, round);
    	
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
     	
     		
     	Map<Integer, List<Team>> divideTeamsIntoGroups = TournamentUtils.divideTeamsIntoGroups(teamList, qualifiedTeamResultDto2.size());
	     Map<Integer, List<TeamDto>> teamGroups = new HashMap<>();
	     Set<Integer> keySet = divideTeamsIntoGroups.keySet();
	//     round++;
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
		//     TournamentGroup savedTournamentGroup = tournamentGroupRepository.save(tournamentGroup);
			}
		     return teamGroups;

//                                .stream()
//                                .sorted(Comparator.comparingInt(TeamResult::getTotalPoint).reversed())
//                                .limit(limit)
//                                .collect(Collectors.toList());
    }

    
    
//    public List<TournamentResultDto> getSortedTeamsByScore() {
//   //     List<Team> teams = teamRepository.findAll();
//        Collections.sort(teams, new Comparator<TournamentResultDto>() {
//            @Override
//            public int compare(TournamentResultDto t1, TournamentResultDto t2) {
//                return t2.getTeamsResult(). - t1.getScore();
//            }
//        });
//        return teams;
//    }

    
    public TournamentResultDto tournamentResultToTournamentResultDto(TournamentResult tournamentResult) {
    	TournamentResultDto tournamentResultDto = new TournamentResultDto();
    	tournamentResultDto.setTournamentResultId(tournamentResult.getTournamentResultId());
    	tournamentResultDto.setRound(tournamentResult.getRound());
    	tournamentResultDto.setGroupNo(tournamentResult.getGroupNo());    	//tournamentResultDto.setTeamResult(tournamentResult.getTeamResult());
    	List<TeamResult> teamsResult = tournamentResult.getTeamResult();
    	List<TeamResultDto> listTeamsResultDto = new ArrayList<>();
    	for(TeamResult tr: teamsResult) {
    		TeamResultDto teamsResultDto = new TeamResultDto();
    		Team team = tr.getTeam();
    		TeamDto teamDto = new TeamDto();
    		teamDto.setTeamId(team.getTeamId());
    		teamDto.setTeamName(team.getTeamName());
    		teamDto.setTeamImageName(team.getTeamImageName());
    		teamsResultDto.setTeamResultId(tr.getTeamResultId());
    		teamsResultDto.setTeam(teamDto);
    		teamsResultDto.setPlacementPoint(tr.getPlacementPoint());
    		teamsResultDto.setKillPoint(tr.getKillPoint());
    		teamsResultDto.setTotalPoint(tr.getTotalPoint());
    		listTeamsResultDto.add(teamsResultDto);
    	}
    	listTeamsResultDto.sort(Comparator.comparing(TeamResultDto::getTotalPoint));
    	Collections.reverse(listTeamsResultDto);
    	tournamentResultDto.setTeamResult(listTeamsResultDto);
    	return tournamentResultDto;
    }

}
