package com.battle.payloads;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTournamentCredentialDto {

//    private Long tournamentCredentialId;
//    private Boolean checkIn;
    @NotNull(message = "{tournament.tournamentStartDateTime.absent}")
    @FutureOrPresent(message = "{tournament.tournamentStartDateTime.invalid}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @NotNull(message = "{tournament.roomId.absent}")
    private String roomId;
    @NotNull(message = "{tournament.password.absent}")
    private String pass;
    @NotNull(message = "{tournament.map.absent}")
    private String map;
//    @NotNull(message = "{tournament.slot.absent}")
//    private List TeamCredentialDto;
//    @NotNull(message = "{tournament.round.absent}")
//    private int round;
//    @NotNull(message = "{tournament.groupNo.absent}")
//    private int groupNo;

}
