package com.mkyong.repository;

import com.mkyong.entity.Site;
import com.mkyong.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SiteRepository
        extends JpaRepository<Site, Long> {
    List<Site> findByLieu(String lieu);
    /*List<Site> findByHauteurGreaterThan(String hauteur);
    List<Site> findByNombreSecteursGreaterThanEqual(Integer nombreSecteurs);
    List<Site> findByCotationLessThanEqual(String cotation);
    List<Site> findByNombreLongueursGreaterThanEqual(Integer nombreLongueurs);
    List<Site> findByNombrePointsGreaterThanEqual(Integer NombrePoints);*/
}