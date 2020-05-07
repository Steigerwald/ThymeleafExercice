package com.mkyong.controller;


import com.mkyong.entity.Secteur;
import com.mkyong.services.SecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/secteurs")
public class secteurController {

    @Autowired
    SecteurService secteurService;

    /* Controller pour la liste des secteurs */
    @RequestMapping()
    public String getAllSecteurs(Model model) {

        List<Secteur> listSecteurs = secteurService.getAllSecteurs();
        model.addAttribute("secteurs", listSecteurs);

        return "secteur/list-secteurs"; //view
    }
}
