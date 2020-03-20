package com.mkyong.services;

import com.mkyong.entity.Club;
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
            return result1;
        } else {
            return new ArrayList<Club>();
        }
    }

    public Club getClubById(Long id) throws RecordNotFoundException
    {
        Optional<Club> club = clubRepository.findById(id);

        if(club.isPresent()) {
            return club.get();
        } else {
            throw new RecordNotFoundException("No club record exist for given id");
        }
    }

}
