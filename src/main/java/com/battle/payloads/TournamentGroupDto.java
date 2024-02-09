package com.battle.payloads;

import java.util.List;

import com.battle.entities.Team;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentGroup;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentGroupDto {
	   private Long tournamentGroupId;
	    
	    private Long tournamentId;
	    private int groupNo;
	    private String tounamentName;
	    private int round;

	    private List<String> teamDto;
}
