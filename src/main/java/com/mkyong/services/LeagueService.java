package com.mkyong.services;

import com.mkyong.exception.RecordNotFoundException;
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
            logger.info(" retour liste result2 si taille de result2 >0 ");
            return result2;
        } else {
            logger.info(" retour nouvelle liste League car pas d'élément dans la liste result2 ");
            return new ArrayList<League>();
        }
    }

    public League getLeagueById(Long id) throws RecordNotFoundException
    {
        Optional<League> league = leagueRepository.findById(id);

        if(league.isPresent()) {
            logger.info(" retour de la league car elle est présente ");
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
            logger.info("retour entité League de createOrUpdateLeague car id est nul ");
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
                logger.info(" retour de la nouvelle entité league de createOrUpdateLeague qui a été sauvegardée et  la League est existante");
                return newEntity;

            } else {
                entity = leagueRepository.save(entity);
                logger.info(" retour de l'entité League de createOrUpdateLeague qui a été sauvegardée car la league n'est pas existante");
                return entity;
            }
        }
    }


    public void deleteLeagueById(Long id) throws RecordNotFoundException
    {
        Optional<League> league = leagueRepository.findById(id);

        if(league.isPresent())
        {
            logger.info(" l'entité league a été trouvée et est effacée");
            leagueRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No club record exist for given id");
        }
    }


}
