package com.battle.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.User;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByTeam(Team team);

    List<TeamMember> findByUser(User user);

    void deleteByUser(TeamMember teamMember);

    void deleteByTeamIsNull();
//TeamMember findByUserId(Long id);


}
