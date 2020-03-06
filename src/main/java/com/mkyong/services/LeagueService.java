package com.mkyong.services;

import com.mkyong.entity.Footballeur;
import com.mkyong.entity.League;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

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

}
