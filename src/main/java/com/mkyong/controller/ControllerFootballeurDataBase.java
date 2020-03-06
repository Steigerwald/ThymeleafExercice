package com.mkyong.controller;

import com.mkyong.entity.League;
import com.mkyong.entity.Users;
import com.mkyong.services.ClubService;
import com.mkyong.services.FootballeurService;
import com.mkyong.entity.Footballeur;
import com.mkyong.entity.Club;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ControllerFootballeurDataBase {
    @Autowired
    FootballeurService footballeurService;

    @Autowired
    ClubService clubService;

    @Autowired
    LeagueService leagueService;


    private String message1;
    private String message2;
    private Users user=new Users();

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("user", new Users());
        return "presentation"; //view
    }

    @RequestMapping (path = "/footballeurs")
    public String getAllFootballeurs(Model model)
    {
        List<Footballeur> listF = footballeurService.getAllFootballeurs();

        model.addAttribute("footballeurs", listF);
        return "list-footballeurs";
    }

    @RequestMapping(path = {"/footballeurs/edit", "footballeurs/edit/{id}"})
    public String editEntityById(Model model, @PathVariable("id") Optional<Long> id)
            throws RecordNotFoundException
    {
        if (id.isPresent()) {
            Footballeur entity = footballeurService.getFootballeurById(id.get());
            model.addAttribute("footballeur", entity);
        } else {
            model.addAttribute("footballeur", new Footballeur());
        }
        return "add-edit-footballeur";
    }

    @RequestMapping(path = "/footballeurs/delete/{id}")
    public String deleteEntityById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        footballeurService.deleteFootballeurById(id);
        return "redirect:/footballeurs";
    }

    @RequestMapping(path = "/footballeurs/createFootballeur", method = RequestMethod.POST)
    public String createOrUpdateEntity(Footballeur footballeur)
    {
        footballeurService.createOrUpdateFootballeur(footballeur);

        return "redirect:/footballeurs";
    }

    @GetMapping("/clubs")
    public String getAllClubs(Model model) {

        List<Club> listC = clubService.getAllClubs();
        model.addAttribute("clubs", listC);
        return "list-clubs"; //view
    }

    @GetMapping("/leagues")
    public String getAllLeagues(Model model) {

        List<League> listL = leagueService.getAllLeagues();
        model.addAttribute("Leagues", listL);
        return "list-leagues"; //view
    }

    @GetMapping("/presentation")
    public String presentationUser(Model model) {
        model.addAttribute("message1", message1);
        model.addAttribute("message2", message2);
        return "welcome"; //view
    }
    // récupération de l'objet user avec ses attributs et récupération des attributs et revoi des attributs à la page welcome
    @PostMapping("/")
    public String formUser(@ModelAttribute("user") Users nouveauUser,Model model){
        message1=nouveauUser.getPrenomUser();
        message2=nouveauUser.getNomUser();
        user.setNomUser(message2);
        user.setPrenomUser(message1);
        model.addAttribute("message1", message1);
        model.addAttribute("message2", message2);
        return "welcome";
    }


}
