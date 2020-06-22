package com.mkyong.services;


import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.entity.Topo;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.TopoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                topoAModifier.setDisponible(entity.getDisponible());
                topoAModifier.setSites(entity.getSites());
                topoAModifier.setLocation(entity.getLocation());
                entity = topoRepository.save(topoAModifier);
                logger.info(" retour de la nouvelle entité topo de createOrUpdateTopo qui a été sauvegardée et le topo est existant");
                return entity;
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
        newTopo.setDisponible(entity.getDisponible());
        newTopo.setLocation(entity.getLocation());
        newTopo.setOwner(user);
        newTopo.setImage(entity.getImage());
        newTopo.setSites(entity.getSites());

        logger.info(" avec date aujourd'hui il est :"+ today);

        // enregistrement du topo dans liste des topos de user
        Collection listeTopos = user.getTopos();
        listeTopos.add(entity);
        user.setTopos(listeTopos);
        logger.info(" les topos de user: "+ user.getTopos());
        userService.updateUser(user);

        // enregistrement du topo dans chaque site concerné
        for(int i=0;i<(entity.getSites()).size();i++){
            List<Site> listeSites=new ArrayList<Site>();
            listeSites.addAll(entity.getSites());
            Site site=listeSites.get(i);
            site.setTopo(entity);
            logger.info(" les topos de site: "+ site.getTopo());
            logger.info(" les images de topo: "+ entity.getImage().getId());
            logger.info(" les images de site: "+ site.getImage().getId());
            siteService.UpdateSite(site);
        }

        // enregistrement de l'image dans le topo
        imageService.stockerImage(entity.getImage(), user);

        entity = topoRepository.save(newTopo);
        logger.info(" retour de l'entité de createOrUpdateTopo car l'Id n'existe pas");
        return entity;
    }


    /*Methode pour annuler un topo par id dans la base de données*/
    public void deleteTopoById(Long id) throws RecordNotFoundException {
        Optional<Topo> topo = topoRepository.findById(id);
        if(topo.isPresent()) {
            topoRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de topo enregistré avec cet Id");
        }
    }
}
