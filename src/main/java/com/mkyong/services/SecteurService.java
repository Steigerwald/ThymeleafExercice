package com.mkyong.services;

import com.mkyong.entity.Secteur;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.SecteurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecteurService {

    @Autowired
    private SecteurRepository secteurRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(SecteurService.class);

    /*Methode pour obtenir tous les secteurs de la base de données*/
    public List<Secteur> getAllSecteurs() {
        List<Secteur> result1 =(List<Secteur>) secteurRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Secteur>();
        }
    }

    /*Methode pour obtenir un secteur par id de la base de données*/
    public Secteur getSecteurById(Long id) throws RecordNotFoundException {
        Optional<Secteur> secteur = secteurRepository.findById(id);
        if(secteur.isPresent()) {
            logger.info(" retour du secteur car il est présent ");
            return secteur.get();
        } else {
            throw new RecordNotFoundException("Pas de secteur enregistré avec cet Id");
        }
    }

    /*Methode pour creer ou modifier un secteur de la base de données*/
    public Secteur createOrUpdateSecteur(Secteur entity) throws RecordNotFoundException {
        if(entity.getIdSecteur()  == null) {
            entity = secteurRepository.save(entity);
            logger.info(" retour de l'entité qui a été créée de createOrUpdateSecteur car l'Id n'existe pas");
            return entity;
        } else {
            Secteur secteurAModifier = getSecteurById(entity.getIdSecteur());
            if(secteurAModifier!=null) {

                logger.info(" l'entité secteur à modifier a été trouvée et modifiée");

                entity.setVoies(secteurAModifier.getVoies());
                entity = secteurRepository.save(entity);
                logger.info(" retour de la nouvelle entité secteur de createOrUpdateSite qui a été sauvegardée et le secteur est existant");
                return entity;
            } else {
                throw new RecordNotFoundException("No user record exist for given id and to modify it");
            }
        }
    }

    /*Methode pour effacer un secteur de la base de données*/
    public void deleteSecteurById(Long id) throws RecordNotFoundException {
        Optional<Secteur> secteur = secteurRepository.findById(id);
        if(secteur.isPresent()) {
            secteurRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de secteur enregistré avec cet Id");
        }
    }

}
