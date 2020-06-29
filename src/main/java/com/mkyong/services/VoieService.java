package com.mkyong.services;


import com.mkyong.entity.Topo;
import com.mkyong.entity.Voie;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.VoieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class VoieService {

    @Autowired
    private VoieRepository voieRepository;

    @Autowired
    private SecteurService secteurService;

    Logger logger = (Logger) LoggerFactory.getLogger(TopoService.class);

    /*Methode pour obtenir toutes les voies de la base de données*/
    public List<Voie> getAllVoies() {
        List<Voie> result1 =(List<Voie>) voieRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Voie>();
        }
    }

    /*Methode pour obtenir une voie par Id*/
    public Voie getVoieById(Long id) throws RecordNotFoundException {
        Optional<Voie> voie = voieRepository.findById(id);
        if(voie.isPresent()) {
            logger.info(" retour de la voie car il est présent ");
            return voie.get();
        } else {
            throw new RecordNotFoundException("Pas de voie enregistrée avec cet Id");
        }
    }

    /*Methode pour creer ou modifier une entité voie dans la base de données*/
    public Voie createOrUpdateVoie(Voie entity) throws RecordNotFoundException {
        if(entity.getIdVoie()  == null) {
            Voie newVoie = new Voie();
            newVoie.setNumeroVoie(entity.getNumeroVoie());
            newVoie.setCotation(entity.getCotation());
            newVoie.setNombreLongueurs(entity.getNombreLongueurs());
            newVoie.setNombrePoints(entity.getNombrePoints());
            newVoie.setSecteur(entity.getSecteur());
            newVoie = voieRepository.save(newVoie);

            /*
            //rajout de la voie dans la liste des voies du secteur concerné
            Collection<Voie> listeVoies = newVoie.getSecteur().getVoies();
            listeVoies.add(newVoie);
            newVoie.getSecteur().setVoies(listeVoies);
            secteurService.createOrUpdateSecteur(newVoie.getSecteur());

            */
            logger.info(" retour de l'entité de createOrUpdateVoie car l'Id n'existe pas");
            return newVoie;

        }
        else {
            Voie voieAModifier = getVoieById(entity.getIdVoie());
            voieAModifier.setNumeroVoie(entity.getNumeroVoie());
            voieAModifier.setCotation(entity.getCotation());
            voieAModifier.setNombreLongueurs(entity.getNombreLongueurs());
            voieAModifier.setNombrePoints(entity.getNombrePoints());
            voieAModifier.setSecteur(entity.getSecteur());
            voieAModifier = voieRepository.save(voieAModifier);
           /*
            if ((entity.getSecteur()!=null)) {
                if (voieAModifier.getSecteur() != null) {
                    //rajout de la voie dans la liste des voies du secteur concerné
                    Collection<Voie> listeVoies = voieAModifier.getSecteur().getVoies();
                    listeVoies.add(voieAModifier);
                    voieAModifier.getSecteur().setVoies(listeVoies);
                    secteurService.createOrUpdateSecteur(voieAModifier.getSecteur());
                }
            }*/

            logger.info(" retour de la nouvelle entité voie de createOrUpdateVoie qui a été sauvegardée et la voie est existante");
            return voieAModifier;
        }
    }

    /*Methode pour annuler une voie par Id*/
    public void deleteVoieById(Long id) throws RecordNotFoundException {
        Optional<Voie> voie = voieRepository.findById(id);
        if(voie.isPresent()) {
            voieRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de voie enregistrée avec cet Id");
        }
    }
}
