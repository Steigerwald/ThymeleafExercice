package com.mkyong.services;

import com.mkyong.entity.Footballeur;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.FootballeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FootballeurService {

    @Autowired
    FootballeurRepository repository;

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
