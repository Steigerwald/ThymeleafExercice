package com.mkyong.repository;

import com.mkyong.entity.Voie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VoieRepository
        extends JpaRepository<Voie, Long> {

    List<Voie> findByCotationLessThanEqual(String cotation);
    List<Voie> findByNombreLongueursGreaterThanEqual(Integer nombreLongueurs);
    List<Voie> findByNombrePointsGreaterThanEqual(Integer NombrePoints);

}