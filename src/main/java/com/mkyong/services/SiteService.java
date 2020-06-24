package com.mkyong.services;

import com.mkyong.entity.*;
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

    @Autowired
    private TopoService topoService;

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private ImageService imageService;



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
        Site newSite = new Site();
        newSite.setNomSite(entity.getNomSite());
        newSite.setLieu(entity.getLieu());
        newSite.setDescriptif(entity.getDescriptif());
        newSite.setOfficiel(false);
        newSite.setPublic(false);

        newSite.setUser(user);
        // enregistrement du site dans liste des sites de user
        Collection<Site> listeSites = user.getSites();
        listeSites.add(entity);
        user.setSites(listeSites);
        userService.updateUser(user);

        newSite.setTopo(null);
        newSite.setCommentaires(null);

        newSite.setImage(entity.getImage());
        if(newSite.getImage()!=null) {
            imageService.stockerImage(newSite.getImage(), user);
        }

        newSite.setSecteurs(entity.getSecteurs());
        // enregistrement du site dans chaque secteur concerné
        if (newSite.getSecteurs()!=null) {
            for (int i = 0; i < (newSite.getSecteurs()).size(); i++) {
                List<Secteur> listeSecteurs = new ArrayList<Secteur>();
                if (entity.getSecteurs() != null) {
                    listeSecteurs.addAll(entity.getSecteurs());
                    Secteur secteur = listeSecteurs.get(i);
                    secteur.setSite(newSite);
                    secteurService.createOrUpdateSecteur(secteur);
                }
            }
        }

        //enregistrement du site dans la basse de données
        entity = siteRepository.save(newSite);
        logger.info(" retour de l'entité de createSite qui a été créée car l'Id n'existe pas");
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
           siteAModifier.setSecteurs(entity.getSecteurs());
            // enregistrement du Site dans chaque secteur concerné
            List<Secteur> secteursModifies=new ArrayList<Secteur>();
            if (siteAModifier.getSecteurs()!=null) {
                for (int i = 0; i < (siteAModifier.getSecteurs()).size(); i++) {
                    List<Secteur> listeSecteurs = new ArrayList<Secteur>();
                    listeSecteurs.addAll(siteAModifier.getSecteurs());
                    Secteur secteur = listeSecteurs.get(i);
                    secteur.setSite(siteAModifier);
                    Secteur secteurModifie = secteurService.createOrUpdateSecteur(secteur);
                    secteursModifies.add(secteurModifie);
                }
                siteAModifier.setSecteurs(secteursModifies);
            }

           siteAModifier=siteRepository.save(siteAModifier);
            logger.info(" retour de la nouvelle entité site de createOrUpdateSite qui a été sauvegardée et le site est existant");
            return siteAModifier;
        } else {
            throw new RecordNotFoundException("No user record exist for given id and to modify it");
        }
    }


    /*Methode pour effacer un site dans la base de données*/
    public void deleteSiteById(Long id) throws RecordNotFoundException {
        Optional<Site> site = siteRepository.findById(id);
        if(site.isPresent()) {
            Site siteTrouve = site.get();

            // retrait du site de la liste des Sites de User
            List<Site> listSitesUser=new ArrayList<Site>();
            if (siteTrouve.getUser().getSites()!=null) {
                listSitesUser.addAll(siteTrouve.getUser().getSites());
            }
            listSitesUser.remove(siteTrouve);
            siteTrouve.getUser().setSites(listSitesUser);
            userService.updateUser(siteTrouve.getUser());

            // retrait du site de la liste des Sites de topo
            if (siteTrouve.getTopo()!=null) {
                List<Site> listSitesTopo = new ArrayList<Site>();
                listSitesTopo.addAll(siteTrouve.getTopo().getSites());
                listSitesTopo.remove(siteTrouve);
                siteTrouve.getTopo().setSites(listSitesTopo);
                topoService.UpdateTopo(siteTrouve.getTopo());
            }

            // annullation de chaque commentaire du site dans la base de données de commentaire
            if (siteTrouve.getCommentaires()!=null) {
                for (int i = 0; i < (siteTrouve.getCommentaires()).size(); i++) {
                    List<Commentaire> listeCommentaires = new ArrayList<Commentaire>();
                    listeCommentaires.addAll(siteTrouve.getCommentaires());
                    Commentaire commentaire = listeCommentaires.get(i);
                    commentaireService.deleteCommentaireById(commentaire.getIdCommentaire());
                }
            }

            //annulation de la basede donnée image de l'Image du site
            if ((siteTrouve.getImage())!=null) {
                imageService.deleteImageById(siteTrouve.getImage().getId());
            }
            // annullation de chaque secteur du site dans la base de données de secteur
            if (siteTrouve.getSecteurs()!=null) {
                for (int i = 0; i < (siteTrouve.getSecteurs()).size(); i++) {
                    List<Secteur> listeSecteurs = new ArrayList<Secteur>();
                    listeSecteurs.addAll(siteTrouve.getSecteurs());
                    Secteur secteur = listeSecteurs.get(i);
                    secteur.setSite(null);
                }
            }
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
        List<Site> listSitesTrouvesDeRecherche=siteRepository.findAllSitesByLieuOrByHauteurOrByNombreLongueursOrByNombrePoints(search.getLieu(),search.getHauteur(),search.getNombreLongueurs(),search.getNombrePoints());
        return listSitesTrouvesDeRecherche;
    }
}
