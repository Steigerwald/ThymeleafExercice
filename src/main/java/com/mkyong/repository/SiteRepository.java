package com.mkyong.repository;

import com.mkyong.entity.Site;
import com.mkyong.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SiteRepository
        extends JpaRepository<Site, Long> {
    List<Site> findByLieu(String lieu);

    List<Site> findBySecteurs_HauteurGreaterThanEqual(Integer hauteur);
    List<Site> findByLieuAndSecteurs_HauteurGreaterThanEqual(String lieu,Integer hauteur);

    @Query("SELECT u FROM Site u WHERE u.lieu = ?1")
    List<Site> findAllSitesByLieu(String lieu);

   /* @Query("SELECT u FROM Site u HAVING COUNT(u.secteurs) >= ?1")
    List<Site> findAllSitesByNombreSecteurs(Integer nombreSecteurs);*/

    @Query(value = "SELECT DISTINCT u FROM Voie v, IN(v.secteur)s,IN(s.site)u WHERE v.nombreLongueurs>=?1 and v.nombrePoints>=?2")
    List<Site> findAllSitesByNombreLongueursAndByNombrePoints(Integer nombreLongueurs,Integer nombrePoints);

}