package com.mkyong.repository;

import com.mkyong.entity.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarteRepository
        extends JpaRepository<Carte, Long> {

}
