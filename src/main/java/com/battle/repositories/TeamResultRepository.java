package com.battle.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.battle.entities.TeamResult;

@Repository
public interface TeamResultRepository extends JpaRepository<TeamResult, Long>{


}
