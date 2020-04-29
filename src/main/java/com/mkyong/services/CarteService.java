package com.mkyong.services;

import com.mkyong.entity.Carte;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.CarteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarteService {

    @Autowired
    CarteRepository carteRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(CarteService.class);



    public List<Carte> getAllCarteTopographiques()
    {
        List<Carte> result1 =(List<Carte>) carteRepository.findAll();

        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result1 ");
            return new ArrayList<Carte>();
        }
    }

    public Carte getCarteById(Long id) throws RecordNotFoundException
    {
        Optional<Carte> carte = carteRepository.findById(id);

        if(carte.isPresent()) {
            logger.info(" retour de la carte car il est présent ");
            return carte.get();
        } else {
            throw new RecordNotFoundException("Pas de carte enregistrée avec cet Id");
        }
    }

    public Carte createOrUpdateCarte(Carte entity)
    {
        if(entity.getIdCarte()  == null)
        {
            entity = carteRepository.save(entity);

            logger.info(" retour de l'entité de createOrUpdateCarte car l'Id n'existe pas");
            return entity;
        }
        else
        {
            Optional<Carte> carte = carteRepository.findById(entity.getIdCarte());

            if(carte.isPresent())
            {
                Carte newCarte = carte.get();
                newCarte.setNomCarte(entity.getNomCarte());
                newCarte.setMimeType(entity.getMimeType());

                newCarte = carteRepository.save(newCarte);

                logger.info(" retour de la nouvelle entité carte de createOrUpdateCarte qui a été sauvegardée et la carte est existante");
                return newCarte;

            } else {
                entity = carteRepository.save(entity);
                logger.info(" retour de l'entité carte de createOrUpdateCarte qui a été sauvegardée car la carte n'est pas existante");
                return entity;
            }
        }
    }



    public void deleteCarteById(Long id) throws RecordNotFoundException
    {
        Optional<Carte> carte = carteRepository.findById(id);

        if(carte.isPresent())
        {
            carteRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de carte enregistrée avec cet Id");
        }
    }



}
