package com.battle.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CredentialId;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private int Slot;

//		private int placementPoint;
//		
//		private int totalPoint;
}
