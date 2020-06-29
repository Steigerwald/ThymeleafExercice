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
        newSite.setOfficiel(entity.getOfficiel());
        newSite.setPublic(entity.getPublic());
        newSite.setUser(user);
        logger.info(" le user à mettre dans Newsite  "+user.getNomUser());
        newSite.setSecteurs(entity.getSecteurs());
        newSite.setImage(entity.getImage());
        newSite.setTopo(null);
        newSite.setCommentaires(null);

        //enregistrement du site dans la basse de données
        entity = siteRepository.save(newSite);

        // 1/ enregistrement du site dans liste des sites de user
        /*
        Collection<Site> listeSites = user.getSites();
        listeSites.add(entity);
        user.setSites(listeSites);
        logger.info(" le user à mettre dans Newsite  "+user.getNomUser());
        userService.updateUser(user);
        */

        // 2/ enregistrement du site dans chaque secteur concerné
        /*
        if (newSite.getSecteurs()!=null) {
            List<Secteur> listeSecteurs = new ArrayList<Secteur>();
            if (entity.getSecteurs() != null){
                listeSecteurs.addAll(entity.getSecteurs());
                for (int i = 0; i < listeSecteurs.size(); i++) {
                        Secteur secteur = listeSecteurs.get(i);
                        secteur.setSite(newSite);
                        secteurService.createOrUpdateSecteur(secteur);
                }
            }
        }
        */

        // 3/ enregistrement de l'image
       /*
        if(newSite.getImage()!=null) {
            newSite.getImage().setSite(newSite);
            newSite.getImage().setTopo(null);
            imageService.stockerImage(newSite.getImage(), user);
        }
        */

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
           siteAModifier.setOfficiel(entity.getOfficiel());
           siteAModifier.setPublic(entity.getPublic());
           siteAModifier.setUser(entity.getUser());
           siteAModifier.setTopo(entity.getTopo());
           siteAModifier.setCommentaires(entity.getCommentaires());
           siteAModifier.setImage(entity.getImage());
           siteAModifier.setSecteurs(entity.getSecteurs());
           siteAModifier=siteRepository.save(siteAModifier);

            // 1/ enregistrement du site dans liste des sites de user
            /*
            if ((siteAModifier.getUser()!=null)&&(entity.getUser()!=null)) {
                if (siteAModifier.getUser().getSites() != null) {
                    Collection<Site> listeSites = siteAModifier.getUser().getSites();
                    if (siteAModifier.getUser().getSites().contains(siteAModifier)) {
                        siteAModifier.getUser().setSites(listeSites);
                    } else {
                        listeSites.add(siteAModifier);
                        siteAModifier.getUser().setSites(listeSites);
                    }
                    userService.updateUser(siteAModifier.getUser());
                } else {
                    Collection<Site> listeSites = new ArrayList<Site>();
                    listeSites.add(siteAModifier);
                    siteAModifier.getUser().setSites(listeSites);
                    userService.updateUser(siteAModifier.getUser());
                }
            }
            */

            // 2/ enregistrement du site dans liste des sites de topo
            /*
            if ((siteAModifier.getTopo()!=null)&&(entity.getTopo()!=null)) {
                Collection<Site> listeSites = siteAModifier.getTopo().getSites();
                if (siteAModifier.getTopo().getSites().contains(siteAModifier)) {
                    siteAModifier.getTopo().setSites(listeSites);
                }else {
                    listeSites.add(siteAModifier);
                    siteAModifier.getTopo().setSites(listeSites);
                }
                topoService.UpdateTopo(siteAModifier.getTopo());
            }*/

            // 3/ enregistrement du Site dans chaque commentaire concerné
           /*
            if ((siteAModifier.getCommentaires()!=null)&&(entity.getCommentaires()!=null)) {
                List<Commentaire> listeCommentaires = new ArrayList<Commentaire>();
                if (entity.getCommentaires() != null){
                    listeCommentaires.addAll(entity.getCommentaires());
                    for (int i = 0; i < listeCommentaires.size(); i++) {
                        Commentaire commentaire = listeCommentaires.get(i);
                        commentaire.setSite(siteAModifier);
                        commentaireService.modifyCommentaire(commentaire);
                    }
                }
            }*/

            // 4/ enregistrement du Site dans l'image avec enregistrement de l'image si elle n'est pas présente
            /*
            if ((siteAModifier.getImage()!=null)&&(entity.getImage()!=null)) {
                Image imageTrouve = imageService.getImageById(siteAModifier.getImage().getId());
                if (imageTrouve == null) {
                    logger.info(" l'image de siteAmodifier "+siteAModifier.getImage());
                    siteAModifier.getImage().setSite(siteAModifier);
                    siteAModifier.getImage().setTopo(null);
                    imageService.stockerImage(siteAModifier.getImage(), siteAModifier.getUser());
                }else{
                    imageTrouve.setSite(siteAModifier);
                    imageTrouve.setTopo(null);
                }
            }
            */
            // 5/ enregistrement du site dans chaque secteur concerné
            /*
            if ((siteAModifier.getSecteurs()!=null)&&(entity.getSecteurs()!=null)) {
                List<Secteur> listeSecteurs = new ArrayList<Secteur>();
                listeSecteurs.addAll(siteAModifier.getSecteurs());
                for (int i = 0; i < listeSecteurs.size(); i++) {
                    Secteur secteur = listeSecteurs.get(i);
                    secteur.setSite(siteAModifier);
                    Secteur secteurModifie = secteurService.createOrUpdateSecteur(secteur);
                }
            }
             */
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
            /*
            User userDuSite =siteTrouve.getUser();
            List<Site> listSitesUser=new ArrayList<Site>();

            if (userDuSite.getSites()==null){
                userDuSite.setSites(null);
            }else{
                listSitesUser.addAll(userDuSite.getSites());
                listSitesUser.remove(siteTrouve);
                userDuSite.setSites(listSitesUser);
            }
            userService.updateUser(userDuSite);
            */

            // retrait du site de la liste des Sites de topo
            /*
            Topo topoDuSite =siteTrouve.getTopo();
            if (topoDuSite!=null) {
                List<Site> listSitesTopo = new ArrayList<Site>();
                listSitesTopo.addAll(topoDuSite.getSites());
                listSitesTopo.remove(siteTrouve);
                topoDuSite.setSites(listSitesTopo);
                topoService.UpdateTopo(topoDuSite);
            }
            */

            // annulation de chaque commentaire du site dans la base de données de commentaire
           /*
            if (siteTrouve.getCommentaires()!=null) {
                List<Commentaire> listeCommentaires = new ArrayList<Commentaire>();
                listeCommentaires.addAll(siteTrouve.getCommentaires());
                for (int i = 0; i < listeCommentaires.size(); i++) {
                    Commentaire commentaire = listeCommentaires.get(i);
                    commentaireService.deleteCommentaireById(commentaire.getIdCommentaire());
                }
            }

            */
           /*
            logger.info(" l'image de de site avant de l'effacer est: "+(siteTrouve.getImage()));
            //annulation de la base de donnée image de l'Image du site
            if ((siteTrouve.getImage())!=null) {
                imageService.deleteImageById(siteTrouve.getImage().getId());
            }
            */

            // annulation de chaque secteur du site dans la base de données de secteur
           /*
            if (siteTrouve.getSecteurs()!=null) {
                List<Secteur> listeSecteurs = new ArrayList<Secteur>();
                listeSecteurs.addAll(siteTrouve.getSecteurs());
                for (int i = 0; i < listeSecteurs.size(); i++) {
                    Secteur secteur = listeSecteurs.get(i);
                    secteur.setSite(null);
                    secteurService.createOrUpdateSecteur(secteur);
                }
            }
            */
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
