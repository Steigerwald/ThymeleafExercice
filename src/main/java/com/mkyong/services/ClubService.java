package com.mkyong.services;

import com.mkyong.entity.Club;
import com.mkyong.entity.Footballeur;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ClubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    @Autowired
    ClubRepository clubRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(ClubService.class);

    public List<Club> getAllClubs()
    {
        List<Club> result1 =(List<Club>) clubRepository.findAll();

        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result1 ");
            return new ArrayList<Club>();
        }
    }

    public Club getClubById(Long id) throws RecordNotFoundException
    {
        Optional<Club> club = clubRepository.findById(id);

        if(club.isPresent()) {
            logger.info(" retour du club car il est présent ");
            return club.get();
        } else {
            throw new RecordNotFoundException("No club record exist for given id");
        }
    }

    public Club createOrUpdateClub(Club entity)
    {
        if(entity.getId()  == null)
        {
            entity = clubRepository.save(entity);

            logger.info("retour entité Club de createOrUpdateClub si id nul ");
            return entity;
        }
        else
        {
            Optional<Club> club = clubRepository.findById(entity.getId());

            if(club.isPresent())
            {
                Club newEntity = club.get();
                newEntity.setNomClub(entity.getNomClub());
                newEntity.setPresidentClub(entity.getPresidentClub());
                newEntity.setAnneeCreation(entity.getAnneeCreation());
                newEntity.setLeague(entity.getLeague());

                newEntity = clubRepository.save(newEntity);
                logger.info(" retour nouvelle entité Club de createOrUpdateClub si club présent ");
                return newEntity;

            } else {
                entity = clubRepository.save(entity);
                logger.info(" retour entité Club de createOrUpdateClub si club n'est pas présent ");
                return entity;
            }
        }
    }


    public void deleteClubById(Long id) throws RecordNotFoundException
    {
        Optional<Club> club = clubRepository.findById(id);

        if(club.isPresent())
        {
            clubRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No club record exist for given id");
        }
    }

}
