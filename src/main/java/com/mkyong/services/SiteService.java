package com.mkyong.services;

import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.entity.Topo;
import com.mkyong.entity.Voie;
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

    public Site getSiteById(Long id) throws RecordNotFoundException {
        Optional<Site> site = siteRepository.findById(id);
        if(site.isPresent()) {
            logger.info(" retour du site car il est présent ");
            return site.get();
        } else {
            throw new RecordNotFoundException("Pas de site enregistré avec cet Id");
        }
    }

    public Site createOrUpdateSite(Site entity) {
        if(entity.getIdSite()  == null)
        {
            entity = siteRepository.save(entity);

            logger.info(" retour de l'entité de createOrUpdateSite car l'Id n'existe pas");
            return entity;
        }
        else
        {
                Site newSite = new Site();
                newSite.setIdSite(entity.getIdSite());
                newSite.setNomSite(entity.getNomSite());
                newSite.setLieu(entity.getLieu());
                newSite.setDescriptif(entity.getDescriptif());

                newSite = siteRepository.save(newSite);

                logger.info(" retour de la nouvelle entité site de createOrUpdateSite qui a été sauvegardée et le site est existant");
                return newSite;

        }
    }

    public void deleteSiteById(Long id) throws RecordNotFoundException {
        Optional<Site> site = siteRepository.findById(id);
        if(site.isPresent())
        {
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
