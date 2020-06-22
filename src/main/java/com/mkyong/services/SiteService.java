package com.mkyong.services;

import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.form.Search;
import com.mkyong.repository.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private SecteurService secteurService;

    @Autowired
    private UserService userService;


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

    /*Methode pour obtenir tous les sites publics de la base de données*/
    public List<Site> getAllSitesPublics() {
        List<Site> result1 =(List<Site>) siteRepository.findAll();
        List<Site> result2=new ArrayList<Site>();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            for (int i=0;i<result1.size();i=i+1){
                if (result1.get(i).getPublic()) {
                    result2.add(result1.get(i));
                }
            }
            return result2;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return result2;
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

    /*Methode pour creer un site dans la base de données*/
    public Site CreateSite(Site entity, User user) throws RecordNotFoundException {
            entity.setPublic(false);
            entity.setUser(user);
            //enregistrement du site dans la basse de données

            logger.info(" les secteurs présents dans le site au niveau createOrUpdate= "+entity.getSecteurs());
            entity = siteRepository.save(entity);

            // enregistrement du site dans chaque secteur concerné
            for(int i=0;i<(entity.getSecteurs()).size();i++){
                List<Secteur> listeSecteurs=new ArrayList<Secteur>();
                listeSecteurs.addAll(entity.getSecteurs());
                Secteur secteur =listeSecteurs.get(i);
                secteur.setSite(entity);
                secteurService.createOrUpdateSecteur(secteur);
            }
            // enregistrement du site dans liste des sites de user
            Collection listeSites = user.getSites();
            listeSites.add(entity);
            user.setSites(listeSites);
            userService.updateUser(user);
            logger.info(" retour de l'entité de createOrUpdateSite qui a été créée car l'Id n'existe pas");
            return entity;

    }


    /*Methode pour modifier un site dans la base de données*/
    public Site UpdateSite(Site entity) throws RecordNotFoundException {
            Site siteAModifier = getSiteById(entity.getIdSite());
            if(siteAModifier!=null) {
                logger.info(" l'entité site à modifier a été trouvée et modifiée");
               siteAModifier.setNomSite(entity.getNomSite());
               siteAModifier.setLieu(entity.getLieu());
               siteAModifier.setDescriptif(entity.getDescriptif());
               siteAModifier.setTopo(entity.getTopo());
               siteAModifier.setSecteurs(entity.getSecteurs());
               Site autreSite=new Site();
               autreSite=siteRepository.save(siteAModifier);
                logger.info(" retour de la nouvelle entité site de createOrUpdateSite qui a été sauvegardée et le site est existant");
                return autreSite;
            } else {
                throw new RecordNotFoundException("No user record exist for given id and to modify it");
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

/*
Autre recherche possible à activer si besoin en même temps que dans SiteRepository
    public List<Site> getAllSitesByLieu(String lieu){
        List<Site> listSitesTrouvesByLieu =siteRepository.findByLieu(lieu);
        return listSitesTrouvesByLieu;
    }

    public List<Site> getAllSitesByHauteur(Integer hauteur){
        List<Site> listSitesTrouvesByHauteur =siteRepository.findBySecteurs_HauteurGreaterThanEqual(hauteur);
        return listSitesTrouvesByHauteur;
    }
*/
    public List<Site> getAllSitesBySearch(Search search){
        List<Site> listSitesTrouves=siteRepository.findAllSitesByLieuOrByHauteurOrByNombreLongueursOrByNombrePoints(search.getLieu(),search.getHauteur(),search.getNombreLongueurs(),search.getNombrePoints());
        return listSitesTrouves;
    }
}
