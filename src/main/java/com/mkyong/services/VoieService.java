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
import java.util.List;
import java.util.Optional;

@Service
public class VoieService {

    @Autowired
    private VoieRepository voieRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(TopoService.class);


    public List<Voie> getAllVoies()
    {
        List<Voie> result1 =(List<Voie>) voieRepository.findAll();

        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Voie>();
        }
    }

    public Voie getVoieById(Long id) throws RecordNotFoundException
    {
        Optional<Voie> voie = voieRepository.findById(id);

        if(voie.isPresent()) {
            logger.info(" retour de la voie car il est présent ");
            return voie.get();
        } else {
            throw new RecordNotFoundException("Pas de voie enregistrée avec cet Id");
        }
    }

    public Voie createOrUpdateVoie(Voie entity)
    {
        if(entity.getIdVoie()  == null)
        {
            entity = voieRepository.save(entity);

            logger.info(" retour de l'entité de createOrUpdateVoie car l'Id n'existe pas");
            return entity;
        }
        else {

                Voie newVoie = new Voie();
                newVoie.setIdVoie(entity.getIdVoie());
                newVoie.setNumeroVoie(entity.getNumeroVoie());
                newVoie.setCotation(entity.getCotation());
                newVoie.setNombreLongueurs(entity.getNombreLongueurs());
                newVoie.setNombrePoints(entity.getNombrePoints());
                newVoie = voieRepository.save(newVoie);

                logger.info(" retour de la nouvelle entité voie de createOrUpdateVoie qui a été sauvegardée et la voie est existante");
                return newVoie;

        }
    }


    public void deleteVoieById(Long id) throws RecordNotFoundException
    {
        Optional<Voie> voie = voieRepository.findById(id);

        if(voie.isPresent())
        {
            voieRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de voie enregistrée avec cet Id");
        }
    }

}
