package com.battle.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentCredentialId;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
    private String roomId;
    private String pass;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime StartDateTime;
    private String map;
    private int round;
    private int groupNo;
    @ManyToMany
    private List<TeamCredential> teamCredentials;
   
}
