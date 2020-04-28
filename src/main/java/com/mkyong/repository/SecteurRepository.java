package com.mkyong.repository;

import com.mkyong.entity.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecteurRepository
extends JpaRepository<Secteur, Long> {

}