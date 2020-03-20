package com.mkyong.services;

import com.mkyong.entity.Club;
import com.mkyong.entity.Footballeur;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.FootballeurRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.System.*;

@Service
public class FootballeurService {

    @Autowired
    FootballeurRepository repository;

    Logger logger = (Logger) LoggerFactory.getLogger(FootballeurService.class);

    public List<Footballeur> getAllFootballeurs()
    {
        List<Footballeur> result = (List<Footballeur>) repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Footballeur>();
        }
    }

    public Footballeur getFootballeurById(Long id) throws RecordNotFoundException
    {
        Optional<Footballeur> footballeur = repository.findById(id);

        if(footballeur.isPresent()) {
            return footballeur.get();
        } else {
            throw new RecordNotFoundException("No footballeur record exist for given id");
        }
    }
    public Footballeur createOrUpdateFootballeur(Footballeur entity)
    {
        if(entity.getId()  == null)
        {
            entity = repository.save(entity);

            return entity;
        }
        else
        {
            Optional<Footballeur> footballeur = repository.findById(entity.getId());

            if(footballeur.isPresent())
            {
                Footballeur newEntity = footballeur.get();
                newEntity.setNom(entity.getNom());
                newEntity.setPrenom(entity.getPrenom());
                newEntity.setAge(entity.getAge());
                newEntity.setNationalite(entity.getNationalite());
                newEntity.setPosition(entity.getPosition());
                newEntity.setClub(entity.getClub());

                newEntity = repository.save(newEntity);

                return newEntity;

            } else {
                entity = repository.save(entity);

                return entity;
            }
        }
    }


    public Footballeur transfereFootballeur(Footballeur entityWithNewClub) {

        logger.info("transfereFootballeur " + entityWithNewClub.toString());


        Optional<Footballeur> footballeur = repository.findById(entityWithNewClub.getId());


        if(footballeur.isPresent()) {
            Footballeur newEntity = footballeur.get();

            System.out.println(newEntity.toString());

            newEntity.setClub(entityWithNewClub.getClub());

            newEntity = repository.save(newEntity);

            return newEntity;
        } else{
            return entityWithNewClub;
        }
    }


    /* public Footballeur transfereFootballeur(Footballeur entityWithNewClub, Club newClub) {

        if (entityWithNewClub.getId() == null) {

            logger.debug("A DEBUG Message l'entité est nulle");

            return entityWithNewClub;

        } else {

            Optional<Footballeur> footballeur = repository.findById(entityWithNewClub.getId());

            if (footballeur.isPresent()) {
                Footballeur newEntity = footballeur.get();

                logger.debug("A DEBUG Message footballeur est présent");
                logger.trace("A TRACE Message");
                logger.debug("A DEBUG Message");
                logger.info("An INFO Message");
                logger.warn("A WARN Message");
                logger.error("An ERROR Message");

                    newEntity.setClub(newClub);

                    logger.debug("A DEBUG Message club est initialisé dans footballeur");

                    newEntity = repository.save(newEntity);

                    logger.debug("A DEBUG Message club est sauvegardé");

                return newEntity;
            } else {
                logger.debug("A DEBUG Message footballeur n'est pas présent");
                return entityWithNewClub;
            }
        }
    } */

    public void deleteFootballeurById(Long id) throws RecordNotFoundException
    {
        Optional<Footballeur> footballeur = repository.findById(id);

        if(footballeur.isPresent())
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No footballer record exist for given id");
        }
    }

}
