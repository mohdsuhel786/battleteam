package com.battle.entities;

import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentResultId;
    
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
    
    private int round;
    private int groupNo;
//    @ManyToOne
//    @JoinColumn(name = "round_id")
//    private TournamentRound round;

//    @ElementCollection
//    @CollectionTable(name = "team_result", joinColumns = @JoinColumn(name = "tournament_result_id"))
//    @MapKeyColumn(name = "team")
//    @Column(name = "point")
//    private Map<Long, Integer> teamResult;
   

//    @ManyToMany
//  @JoinTable(
//          name = "team_result",
//          joinColumns = @JoinColumn(name = "tournament_result_id"),
//          inverseJoinColumns = @JoinColumn(name = "team_result_id")
//  )
    @ManyToMany
    private List<TeamResult> teamResult;


    
}
