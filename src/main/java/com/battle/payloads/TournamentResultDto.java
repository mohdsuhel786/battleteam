package com.battle.payloads;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.battle.entities.Team;
import com.battle.entities.TeamResult;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentResultDto {
	
	    
	    private Long tournamentResultId;
	    private int round;
	    private int groupNo;
	//    private Map<Long, Integer> teamResult;

	 //   private TournamentDto tournament;
	    
	   private List<TeamResultDto> teamResult;


	}
