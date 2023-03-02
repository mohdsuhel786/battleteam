package com.battle.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<Team> findByTeam(Team team);
}