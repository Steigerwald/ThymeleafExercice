package com.mkyong.controller;

import com.mkyong.entity.Club;
import com.mkyong.entity.League;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ClubService;
import com.mkyong.services.FootballeurService;
import com.mkyong.services.LeagueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/clubs")
public class clubController {

    @Autowired
    ClubService clubService;

    @Autowired
    LeagueService leagueService;


    Logger logger = (Logger) LoggerFactory.getLogger(FootballeurService.class);


    @GetMapping()
    public String getAllClubs(Model model) {

        List<Club> listC = clubService.getAllClubs();
        model.addAttribute("clubs", listC);
        return "club/list-clubs"; //view
    }

    @RequestMapping(path = "/edit/{id}")
    public String editClubById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Club entity = clubService.getClubById(id);
        if (id!=0) {
            model.addAttribute("club", entity);
        } else {
            model.addAttribute("club", new Club());
        }
        model.addAttribute("titreFormClub","Editer un club");
        List<League> listC = leagueService.getAllLeagues();
        model.addAttribute("leagues", listC);
        return "club/add-edit-club";
    }

    @RequestMapping(path = "/addClub")
    public String addClubById(Model model) {

        model.addAttribute("club", new Club());
        model.addAttribute("titreFormClub","Ajouter un club");
        List<League> listC = leagueService.getAllLeagues();
        model.addAttribute("leagues", listC);
        return "club/add-edit-club";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteClubsById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        clubService.deleteClubById(id);
        return "redirect:/clubs";
    }

    @RequestMapping(path = "/details/{id}")
    public String detailClubById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Club entity = clubService.getClubById(id);
        model.addAttribute("club", entity);
        return "club/details-Club";
    }

    @RequestMapping(path = "/createClub", method = RequestMethod.POST)
    public String createOrUpdateClub(Club club) {

        clubService.createOrUpdateClub(club);
        return "redirect:/clubs";
    }
}
