package com.mkyong.repository;

import com.mkyong.entity.Voie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoieRepository
        extends JpaRepository<Voie, Long> {

}