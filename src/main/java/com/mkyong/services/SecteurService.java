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
    SecteurRepository secteurRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(SecteurService.class);


    public List<Secteur> getAllSecteurs()
    {
        List<Secteur> result1 =(List<Secteur>) secteurRepository.findAll();

        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Secteur>();
        }
    }

    public Secteur getSecteurById(Long id) throws RecordNotFoundException
    {
        Optional<Secteur> secteur = secteurRepository.findById(id);

        if(secteur.isPresent()) {
            logger.info(" retour du secteur car il est présent ");
            return secteur.get();
        } else {
            throw new RecordNotFoundException("Pas de secteur enregistré avec cet Id");
        }
    }

    public Secteur createOrUpdateSecteur(Secteur entity)
    {
        if(entity.getIdSecteur()  == null)
        {
            entity = secteurRepository.save(entity);

            logger.info(" retour de l'entité de createOrUpdateSecteur car l'Id n'existe pas");
            return entity;
        }
        else
        {
                Secteur newSecteur = new Secteur();
                newSecteur.setIdSecteur(entity.getIdSecteur());
                newSecteur.setNomSecteur(entity.getNomSecteur());
                newSecteur.setDescriptifSecteur(entity.getDescriptifSecteur());
                newSecteur.setHauteur(entity.getHauteur());

                newSecteur = secteurRepository.save(newSecteur);

                logger.info(" retour de la nouvelle entité secteur de createOrUpdateSecteur qui a été sauvegardée et le secteur est existant");
                return newSecteur;

        }
    }


    public void deleteSecteurById(Long id) throws RecordNotFoundException
    {
        Optional<Secteur> secteur = secteurRepository.findById(id);

        if(secteur.isPresent())
        {
            secteurRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de secteur enregistré avec cet Id");
        }
    }

}
