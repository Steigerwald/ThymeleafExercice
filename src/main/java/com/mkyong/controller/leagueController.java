package com.mkyong.controller;

import com.mkyong.entity.League;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.CustomUserDetailsService;
import com.mkyong.services.FootballeurService;
import com.mkyong.services.LeagueService;
import com.mkyong.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/leagues")
public class leagueController {

    @Autowired
    LeagueService leagueService;

    Logger logger = (Logger) LoggerFactory.getLogger(FootballeurService.class);

    @GetMapping()
    public String getAllLeagues(Principal principal,Model model) throws RecordNotFoundException {

        List<League> listL = leagueService.getAllLeagues();
        model.addAttribute("leagues", listL);
        return "list-leagues"; //view
    }

    @RequestMapping(path = "/edit/{id}")
    public String editLeagueById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        League entity = leagueService.getLeagueById(id);
        if (id!=0) {
            model.addAttribute("league", entity);
        } else {
            model.addAttribute("league", new League());
        }
        model.addAttribute("titreFormLeague","Editer une Ligue");
        return "add-edit-league";
    }

    @RequestMapping(path = {"/addLeague"})
    public String addLeagueById(Model model)
    {

        model.addAttribute("league", new League());

        model.addAttribute("titreFormLeague","Ajouter une Ligue");

        return "add-edit-league";
    }


    @RequestMapping(path = "/delete/{id}")
    public String deleteLeagueById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        leagueService.deleteLeagueById(id);
        return "redirect:/leagues";
    }

    @RequestMapping(path = "/createLeague", method = RequestMethod.POST)
    public String createOrUpdateLeague(League league)
    {
        leagueService.createOrUpdateLeague(league);

        return "redirect:/leagues";
    }

}
