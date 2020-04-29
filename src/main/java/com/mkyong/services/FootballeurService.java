/*
package com.mkyong.services;

import com.mkyong.exception.RecordNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
*/
/*
@Service
public class FootballeurService {

    @Autowired
    FootballeurRepository repository;

    Logger logger = (Logger) LoggerFactory.getLogger(FootballeurService.class);

    public List<Footballeur> getAllFootballeurs()
    {
        List<Footballeur> result = (List<Footballeur>) repository.findAll();

        if(result.size() > 0) {
            logger.info(" retour liste result si taille de result >0 ");
            return result;
        } else {

            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result de getAllFootballeurs ");
            return new ArrayList<Footballeur>();
        }
    }

    public Footballeur getFootballeurById(Long id) throws RecordNotFoundException
    {
        Optional<Footballeur> footballeur = repository.findById(id);

        if(footballeur.isPresent()) {
            logger.info(" retour du footballeur car il est présent ");
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

            logger.info(" retour de l'entité de createOrUpdateFootballeur car l'Id n'existe pas");
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

                logger.info(" retour de la nouvelle entité Footballeur de createOrUpdateFootballeur qui a été sauvegardée et le footballeur est existant");
                return newEntity;

            } else {
                entity = repository.save(entity);
                logger.info(" retour de l'entité Footballeur de createOrUpdateFootballeur qui a été sauvegardée car le footballeur n'est pas existant");
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

            logger.info(" retour de la nouvelle entité Footballeur de transfereFootballeur qui a été sauvegardée et le footballeur est existant");
            return newEntity;
        } else{

            logger.info(" retour de l'entité entityWithNewClub Footballeur de transfereFootballeur qui a été sauvegardée et le footballeur est existant");
            return entityWithNewClub;
        }
    }

    public void deleteFootballeurById(Long id) throws RecordNotFoundException
    {
        Optional<Footballeur> footballeur = repository.findById(id);

        if(footballeur.isPresent())
        {
            logger.info(" l'entité footballeur a été trouvée et est effacée");
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de footballeur enregistré avec cet Id");
        }
    }

}*/