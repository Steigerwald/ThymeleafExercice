package com.mkyong.services;


import com.mkyong.entity.*;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.TopoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.acl.Owner;
import java.util.*;

@Service
public class TopoService {

    @Autowired
    private TopoRepository topoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ReservationTopoService reservationTopoService;

    @Autowired
    private ImageService imageService;

    Logger logger = (Logger) LoggerFactory.getLogger(TopoService.class);

    /*Methode pour obtenir tous les topos de la base de données*/
    public List<Topo> getAllTopos() {
        List<Topo> result1 =topoRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Topo>();
        }
    }

    /*Methode pour obtenir un topo par Id*/
    public Topo getTopoById(Long id) throws RecordNotFoundException {
        Optional<Topo> topo = topoRepository.findById(id);
        if(topo.isPresent()) {
            logger.info(" retour du topo car il est présent ");
            return topo.get();
        } else {
            throw new RecordNotFoundException("Pas de topo enregistré avec cet Id");
        }
    }

    /*Methode pour modifier une entité topo*/
    public Topo UpdateTopo(Topo entity) throws RecordNotFoundException {

        Topo topoAModifier = getTopoById(entity.getIdTopo());
        if(topoAModifier!=null) {
            logger.info(" l'entité topo à modifier a été trouvée et modifiée");
            topoAModifier.setNomTopo(entity.getNomTopo());
            topoAModifier.setDescription(entity.getDescription());

            if (entity.getDateParution()!=null) {
                topoAModifier.setDateParution(entity.getDateParution());
            }
            logger.info(" la date de parution est: "+ topoAModifier.toStringDateParution());
            if (entity.getDisponible()!=null) {
                topoAModifier.setDisponible(entity.getDisponible());
            }
            if (entity.getLocation()!=null) {
                topoAModifier.setLocation(entity.getLocation());
            }
            topoAModifier.setSites(entity.getSites());
            topoAModifier.setReservation((entity.getReservation()));
            topoAModifier.setImage(entity.getImage());
            if (entity.getOwner()!=null) {
                topoAModifier.setOwner(entity.getOwner());
            }
            topoAModifier = topoRepository.save(topoAModifier);

            /*
            // 1/ enregistrement du topo dans chaque site concerné
            if (topoAModifier.getSites()!=null) {
                List<Site> listeSites = new ArrayList<Site>();
                listeSites.addAll(topoAModifier.getSites());
                for (int i = 0; i < listeSites.size(); i++) {
                    Site site = listeSites.get(i);
                    site.setTopo(topoAModifier);
                    siteService.UpdateSite(site);
                }
            }

            // 2/ enregistrement du topo dans Reservation
            if (topoAModifier.getReservation()!=null) {
                Reservation reservationTrouve = reservationTopoService.getReservationTopoById(topoAModifier.getReservation().getIdReservation());
                if (reservationTrouve == null) {
                    logger.info(" la réservation de topoAModifier "+topoAModifier.getReservation());
                    topoAModifier.getReservation().setTopo(topoAModifier);
                   reservationTopoService.updateReservationTopo(topoAModifier.getReservation());

                }else{
                    reservationTrouve.setTopo(topoAModifier);
                    reservationTopoService.updateReservationTopo(reservationTrouve);
                }
            }

            // 3/ enregistrement du Topo dans l'image avec enregistrement de l'image si elle n'est pas présente
            if (topoAModifier.getImage()!=null) {
                Image imageTrouve = imageService.getImageById(topoAModifier.getImage().getId());
                if (imageTrouve == null) {
                    logger.info(" l'image de topoAmodifier "+topoAModifier.getImage());
                    topoAModifier.getImage().setTopo(topoAModifier);
                    topoAModifier.getImage().setSite(null);
                    imageService.stockerImage(topoAModifier.getImage(), topoAModifier.getOwner());
                }else{
                    imageTrouve.setTopo(topoAModifier);
                    imageTrouve.setSite(null);
                }
            }

            // 4/ enregistrement du topo dans liste des topos de user
            if (topoAModifier.getOwner()!=null) {
                if (topoAModifier.getOwner().getTopos() != null) {
                    Collection<Topo> listeTopos = topoAModifier.getOwner().getTopos();
                    if (topoAModifier.getOwner().getTopos().contains(topoAModifier)) {
                        topoAModifier.getOwner().setTopos(listeTopos);
                    } else {
                        listeTopos.add(topoAModifier);
                        topoAModifier.getOwner().setTopos(listeTopos);
                    }
                    userService.updateUser(topoAModifier.getOwner());
                } else {
                    Collection<Topo> listeTopos = new ArrayList<Topo>();
                    listeTopos.add(topoAModifier);
                    topoAModifier.getOwner().setTopos(listeTopos);
                    userService.updateUser(topoAModifier.getOwner());
                }
            }*/

            logger.info(" retour de la nouvelle entité topo de UpdateTopo qui a été sauvegardée et le topo est existant");
            return topoAModifier;
        } else {
            throw new RecordNotFoundException("No user record exist for given id and to modify it");
        }
    }

    /*Methode pour creer une entité topo*/
    public Topo CreateTopo(Topo entity, User user) throws RecordNotFoundException {

        Date today = new Date();
        Topo newTopo = new Topo();
        newTopo.setNomTopo(entity.getNomTopo());
        newTopo.setDescription(entity.getDescription());
        newTopo.setDateParution(today);
        logger.info(" date de parution Topo créé"+ newTopo.toStringDateParution());
        newTopo.setDisponible(true);
        newTopo.setLocation(entity.getLocation());
        newTopo.setSites(entity.getSites());
        newTopo.setReservation(null);
        newTopo.setImage(entity.getImage());
        newTopo.setOwner(user);

        // enregistrement du topo dans la base de données
        topoRepository.save(newTopo);

        // 2/ enregistrement de l'image dans le topo
        if (newTopo.getImage()!=null) {
            newTopo.getImage().setTopo(newTopo);
            newTopo.getImage().setSite(null);
            imageService.stockerImage(newTopo.getImage(), user);
        }


        /*
        // 1/ enregistrement du topo dans chaque site concerné
       List<Site> listeSitesTopo=new ArrayList<Site>();
        if (newTopo.getSites()!=null){
            listeSitesTopo.addAll(newTopo.getSites());
            for(int i=0;i<listeSitesTopo.size();i++) {
                Site siteTopo = listeSitesTopo.get(i);
                siteTopo.setTopo(newTopo);
                siteService.UpdateSite(siteTopo);
            }
        }

        // 3/ enregistrement du topo dans liste des topos de owner
        Collection<Topo> listeToposDeOwner = newTopo.getOwner().getTopos();
        listeToposDeOwner.add(newTopo);
        newTopo.getOwner().setTopos(listeToposDeOwner);
        logger.info(" les topos de owner: "+ newTopo.getOwner().getTopos());
        userService.updateUser(newTopo.getOwner());
         */
        logger.info(" retour de l'entité newTopo de createTopo car l'Id n'existe pas");
        return newTopo;
    }

    /*Methode pour annuler un topo par id dans la base de données*/
    public void deleteTopoById(Long id) throws RecordNotFoundException {
        Optional<Topo> topo = topoRepository.findById(id);
        if(topo.isPresent()) {
            Topo topoTrouve = topo.get();
            topoTrouve.setSites(null);
            topoRepository.save(topoTrouve);

           /*
            // annulation des sites associés au topo supprimé
            List<Site> listeSites=new ArrayList<Site>();
            listeSites.addAll(topoTrouve.getSites());
            for(int i=0;i<listeSites.size();i++){
                Site site=listeSites.get(i);
                site.setTopo(null);
                siteService.UpdateSite(site);
            }


            // suppression de l'image du topo
            if (topoTrouve.getImage()!=null) {
                imageService.deleteImageById(topoTrouve.getImage().getId());
            }

            // suppression de la réservation du topo
            if (topoTrouve.getReservation()!=null) {
                User userReservation = topoTrouve.getReservation().getUser();
                Collection<Reservation> listeReservations = userReservation.getReservations();
                if (listeReservations != null) {
                listeReservations.remove(topoTrouve.getReservation());
                userReservation.setReservations(listeReservations);
                userService.updateUser(userReservation);
                }
                reservationTopoService.deleteReservationTopoById(topoTrouve.getReservation().getIdReservation());
            }

            // suppression des topos dans la liste du propriétaire
            List<Topo> listTopos=new ArrayList<Topo>();

            if(topoTrouve.getOwner().getTopos()!=null) {
                listTopos.addAll(topoTrouve.getOwner().getTopos());
                listTopos.remove(topoTrouve);
                User ownerDuTopo=topoTrouve.getOwner();
                ownerDuTopo.setTopos(listTopos);
                userService.updateUser(ownerDuTopo);
            }
            */

            // suppression du Topo de la base de données
            topoRepository.deleteById(topoTrouve.getIdTopo());

        } else {
            throw new RecordNotFoundException("Pas de topo enregistré avec cet Id");
        }
    }
}
