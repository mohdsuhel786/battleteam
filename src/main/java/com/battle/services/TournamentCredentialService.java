package com.battle.services;

import java.util.ArrayList;
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
import com.battle.payloads.TournamentResultDto;
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


    public TournamentResultDto createTournamentResult(Long tournamentId, int round, int groupNo) throws BattleException {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);
        if (!optionalTournament.isPresent()) {
            throw new BattleException("Tournament not found");
        }
        Tournament tournament = optionalTournament.get();
        List<TournamentCredential> optionalCredential = tournamentCredentialRepository.findByTournament(tournament);
        for (TournamentCredential tr : optionalCredential) {
            if (tr.getRound() == round && tournament.getTournamentId().equals(tournamentId) && tr.getGroupNo() == groupNo) {
                throw new BattleException("Tournament result with Tournament id " + tournamentId + " and round " + round + " and group " + groupNo + " Already Created!");
            }
        }

//        if (optionalResult.isEmpty()) {
//            throw new BattleException("Tournament result with id " + tournamentId + " not found");
//        }


//    	TournamentDto tournamentDto = tournamentService.getTournament(tournamentId);
//    	Set<TeamDto> teamsDto = tournamentDto.getTeamsDto();
        TournamentCredential tournamentCredential = new TournamentCredential();
//    	Set<Team> teams = tournament.getTeams();

        Optional<TournamentGroup> optionalTournamentGroup = tournamentGroupRepository.findByTournamentAndRoundAndGroupNo(tournament, round, groupNo);
        if (!optionalTournamentGroup.isPresent()) {
            throw new BattleException("For Tournament and Round Group not exists!");
        }

        TournamentGroup tournamentGroup = optionalTournamentGroup.get();
        List<Team> teams = tournamentGroup.getTeam();
        //Set<Team> teamList  = new HashSet<>();
        // Map<Long, Integer> teamResult = new HashMap<>();
        List<TeamCredential> listTeamsCredential = new ArrayList<>();
        for (Team t : teams) {
            //teamResult.put(t.getTeamId(), 0);
            TeamCredential teamsCredential = new TeamCredential();
            teamsCredential.setTeam(t);
            teamsCredential.setSlot(0);
            listTeamsCredential.add(teamsCredential);
            //teamList.add(t);
            teamCredentialRepository.save(teamsCredential);
        }

        //	tournamentResult.setTeamResult(teamResult);
        tournamentCredential.setTeamCredentials(listTeamsCredential);
        ;
        tournamentCredential.setRound(round);
        tournamentCredential.setTournament(tournament);
        tournamentCredential.setGroupNo(groupNo);
        tournamentCredential.setMap(null);
        tournamentCredential.setPass(null);
        tournamentCredential.setRoomId(null);
        tournamentCredential.setStartDateTime(null);//
        tournamentCredential.setTournament(tournament);
        tournamentCredential.setCheckIn(null);

        //        // Check if the tournament result already exists
//        Optional<TournamentResult> existingResult = tournamentResultRepository
//                .findByTournamentAndTeam(tournamentResult.getTournament().getTournamentId(), tournamentResult.getTeam().getTeamId());
//        if (existingResult.isPresent()) {
//            throw new IllegalArgumentException("Tournament result for team " + tournamentResult.getTeam().getTeamId()
//                    + " in tournament " + tournamentResult.getTournament().getTournamentId() + " already exists");
//        }
        TournamentCredential tournamentCred = tournamentCredentialRepository.save(tournamentCredential);
        tournament.setIsResultsDeclared(true);
        tournamentRepository.save(tournament);
        TournamentResultDto tournamentResultDto = tournamentResultToTournamentResultDto(tournamentResult2);
        return tournamentResultDto;
    }


}
