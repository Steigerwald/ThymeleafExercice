package com.mkyong.repository;

import com.mkyong.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository
        extends JpaRepository<League, Long> {
}
