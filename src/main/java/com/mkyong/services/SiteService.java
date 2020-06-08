package com.mkyong.services;

import com.mkyong.entity.Site;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.form.Search;
import com.mkyong.repository.SecteurRepository;
import com.mkyong.repository.SiteRepository;
import com.mkyong.repository.VoieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SecteurRepository secteurRepository;

    @Autowired
    private VoieRepository voieRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(SiteService.class);

    /*Methode pour obtenir tous les sites de la base de données*/
    public List<Site> getAllSites() {
        List<Site> result1 =(List<Site>) siteRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Site>();
        }
    }

    /*Methode pour obtenir un site par Id de la base de données*/
    public Site getSiteById(Long id) throws RecordNotFoundException {
        Optional<Site> site = siteRepository.findById(id);
        if(site.isPresent()) {
            logger.info(" retour du site car il est présent ");
            return site.get();
        } else {
            throw new RecordNotFoundException("Pas de site enregistré avec cet Id");
        }
    }

    /*Methode pour creer ou modifier un site dans la base de données*/
    public Site createOrUpdateSite(Site entity) throws RecordNotFoundException {
        if(entity.getIdSite()  == null) {
            entity = siteRepository.save(entity);
            logger.info(" retour de l'entité de createOrUpdateSite qui a été créée car l'Id n'existe pas");
            return entity;
        } else {
            Site siteAModifier = getSiteById(entity.getIdSite());
            if(siteAModifier!=null) {
                logger.info(" l'entité site à modifier a été trouvée et modifiée");
                entity.setOfficiel(siteAModifier.getOfficiel());
                entity.setCommentaires(siteAModifier.getCommentaires());
                entity.setImages(siteAModifier.getImages());
                entity.setSecteurs(siteAModifier.getSecteurs());
                entity = siteRepository.save(entity);
                logger.info(" retour de la nouvelle entité site de createOrUpdateSite qui a été sauvegardée et le site est existant");
                return entity;
            } else {
                throw new RecordNotFoundException("No user record exist for given id and to modify it");
            }
        }
    }

    /*Methode pour effacer un site dans la base de données*/
    public void deleteSiteById(Long id) throws RecordNotFoundException {
        Optional<Site> site = siteRepository.findById(id);
        if(site.isPresent()) {
            siteRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de site enregistré avec cet Id");
        }
    }


    public List<Site> getAllSitesByLieu(String lieu){
        List<Site> listSitesTrouvesByLieu =siteRepository.findByLieu(lieu);
        return listSitesTrouvesByLieu;
    }

    public List<Site> getAllSitesByHauteur(Integer hauteur){
        List<Site> listSitesTrouvesByHauteur =siteRepository.findBySecteurs_HauteurGreaterThanEqual(hauteur);
        return listSitesTrouvesByHauteur;
    }

    public List<Site> getAllSitesBySearch(Search search){
        //List<Site> listSitesTrouvesByLieu =siteRepository.findByLieu(search.getLieu());
        //List<Site> listSitesTrouvesByLieuAndByHauteur=siteRepository.findByLieuAndSecteurs_HauteurGreaterThanEqual(search.getLieu(),search.getHauteur());
        //List<Site> listSitesTrouvesByLieu =siteRepository.findAllSitesByLieu(search.getLieu());
        //List<Site> listSitesTrouvesByNombreLongueursByNombrePoints=siteRepository.findAllSitesByNombreLongueursAndByNombrePoints(search.getNombreLongueurs(),search.getNombrePoints());
        List<Site> listSitesTrouves=siteRepository.findAllSitesByLieuAndByHauteurAndByNombreLongueursAndByNombrePoints(search.getLieu(),search.getHauteur(),search.getNombreLongueurs(),search.getNombrePoints());
        return listSitesTrouves;
    }
}
