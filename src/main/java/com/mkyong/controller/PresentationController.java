package com.mkyong.controller;

import com.mkyong.entity.League;
import com.mkyong.entity.Users;
import com.mkyong.services.ClubService;
import com.mkyong.services.FootballeurService;
import com.mkyong.entity.Footballeur;
import com.mkyong.entity.Club;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.LeagueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PresentationController {

    private String message1;
    private String message2;
    private Users user=new Users();

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("user", new Users());
        return "presentation"; //view
    }

    @GetMapping("presentation")
    public String presentationUser(Model model) {
        model.addAttribute("message1", message1);
        model.addAttribute("message2", message2);
        return "welcome"; //view
    }

    // récupération de l'objet user avec ses attributs et récupération des attributs et revoi des attributs à la page welcome
    @PostMapping()
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
