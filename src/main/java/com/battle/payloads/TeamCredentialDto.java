package com.battle.payloads;

import com.battle.entities.Team;
import com.battle.entities.TeamCredential;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamCredentialDto {
	  private Long CredentialId;
	
	    private TeamDto teamDto;

	    private int Slot;
	    private Boolean checkIn;
}
