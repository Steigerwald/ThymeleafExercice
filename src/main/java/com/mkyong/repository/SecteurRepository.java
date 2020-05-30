package com.mkyong.repository;

import com.mkyong.entity.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecteurRepository
extends JpaRepository<Secteur, Long> {
List<Secteur> findByHauteurGreaterThanEqual(Integer hauteur);
}