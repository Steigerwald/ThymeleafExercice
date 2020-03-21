package com.mkyong.services;

import com.mkyong.entity.League;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.LeagueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(LeagueService.class);


    public List<League> getAllLeagues()
    {
        List<League> result2 = (List<League>) leagueRepository.findAll();

        if(result2.size() > 0) {
            return result2;
        } else {
            return new ArrayList<League>();
        }
    }

    public League getLeagueById(Long id) throws RecordNotFoundException
    {
        Optional<League> league = leagueRepository.findById(id);

        if(league.isPresent()) {
            return league.get();
        } else {
            throw new RecordNotFoundException("No league record exist for given id");
        }
    }

    public League createOrUpdateLeague(League entity)
    {
        if(entity.getId()  == null)
        {
            entity = leagueRepository.save(entity);

            return entity;
        }
        else
        {
            Optional<League> league = leagueRepository.findById(entity.getId());

            if(league.isPresent())
            {
                League newEntity = league.get();
                newEntity.setNomLeague(entity.getNomLeague());
                newEntity.setNombreClubs(entity.getNombreClubs());
                newEntity.setPays(entity.getPays());

                newEntity = leagueRepository.save(newEntity);

                return newEntity;

            } else {
                entity = leagueRepository.save(entity);

                return entity;
            }
        }
    }





    public void deleteLeagueById(Long id) throws RecordNotFoundException
    {
        Optional<League> league = leagueRepository.findById(id);

        if(league.isPresent())
        {
            leagueRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No club record exist for given id");
        }
    }


}
