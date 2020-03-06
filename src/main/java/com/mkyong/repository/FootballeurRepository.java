package com.mkyong.repository;

import com.mkyong.entity.Footballeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballeurRepository
        extends JpaRepository<Footballeur, Long> {
}
