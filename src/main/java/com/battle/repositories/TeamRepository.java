package com.battle.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.User;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamName(String name);

    //  @Query("SELECT m FROM Team t JOIN t.members m WHERE m.TeamMemberId = ?1")
    Optional<Team> findByCaptain(User userId);

    //Optional<Team> findByTeamId(Long teamId);


}
