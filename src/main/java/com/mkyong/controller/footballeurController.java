package com.mkyong.controller;

import com.mkyong.entity.Club;
import com.mkyong.entity.Footballeur;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ClubService;
import com.mkyong.services.FootballeurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@RequestMapping("/footballeurs")
public class footballeurController {

    @Autowired
    FootballeurService footballeurService;

    @Autowired
    ClubService clubService;

    Logger logger = (Logger) LoggerFactory.getLogger(FootballeurService.class);

    @RequestMapping()
    public String getAllFootballeurs(Model model) {

        List<Footballeur> listF = footballeurService.getAllFootballeurs();
        model.addAttribute("footballeurs", listF);
        return "footballeur/list-footballeurs";
    }

    @RequestMapping(path = "/edit/{id}")
    public String editEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Footballeur entity = footballeurService.getFootballeurById(id);
        if (id!=0) {
            model.addAttribute("footballeur", entity);
        } else {
            model.addAttribute("footballeur", new Footballeur());
        }
        model.addAttribute("titreFormFootballeur","Editer un joueur");
        List<Club> listC = clubService.getAllClubs();
        model.addAttribute("clubs", listC);
        return "footballeur/add-edit-footballeur";
    }

    @RequestMapping(path = "/addFootballeur")
    public String addEntityById(Model model) {

        model.addAttribute("footballeur", new Footballeur());
        model.addAttribute("titreFormFootballeur","Ajouter un joueur");
        List<Club> listC = clubService.getAllClubs();
        model.addAttribute("clubs", listC);
        return "footballeur/add-edit-footballeur";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        footballeurService.deleteFootballeurById(id);
        return "redirect:/footballeurs";
    }

    @RequestMapping(path = "/createFootballeur", method = RequestMethod.POST)
    public String createOrUpdateEntity(Footballeur footballeur) {

        footballeurService.createOrUpdateFootballeur(footballeur);
        return "redirect:/footballeurs";
    }

    @RequestMapping(path = {"/transfert/{id}"})
    public String transfertEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        if (id!=0) {
            Footballeur entity = footballeurService.getFootballeurById(id);
            model.addAttribute("footballeur", entity);
        }
        List<Club> listClubs = clubService.getAllClubs();
        model.addAttribute("clubs", listClubs);

        return "footballeur/transfert-footballeur";
    }

    @RequestMapping(path = "/footballeurTransfere", method = RequestMethod.POST)
    public String UpdateEntity(Footballeur footballeur) {

        logger.info("methode post " + footballeur.toString());
        footballeurService.transfereFootballeur(footballeur);
        return "redirect:/footballeurs";
    }
}
