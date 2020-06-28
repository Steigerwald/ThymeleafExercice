package com.mkyong.controller;


import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.entity.User;
import com.mkyong.entity.Voie;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.SecteurService;
import com.mkyong.services.SiteService;
import com.mkyong.services.UserService;
import com.mkyong.services.VoieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/secteurs")
public class secteurController {

    @Autowired
    private SecteurService secteurService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private UserService userService;

    @Autowired
    private VoieService voieService;


    /* Controller pour la liste des secteurs */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllSecteurs(Model model, Principal principal) {
        List<Secteur> listSecteurs = secteurService.getAllSecteurs();
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        model.addAttribute("secteurs", listSecteurs);
        return "secteur/list-secteurs"; //view
    }

    /* controller pour effacer un secteur de la base de données */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        secteurService.deleteSecteurById(id);
        return "redirect:/secteurs";
    }

    /* controller pour l'edition d'un secteur par Id */
    @RequestMapping(path = "/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model,Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        if (id!=0) {
            Secteur entity = secteurService.getSecteurById(id);
            model.addAttribute("secteur", entity);
        } else {
            model.addAttribute("secteur", new Secteur());
        }
        List<Site> listSites = siteService.getAllSites();
        List<Voie> listVoies=voieService.getAllVoies();
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        model.addAttribute("sites",listSites);
        model.addAttribute("voies",listVoies);
        model.addAttribute("titreFormSecteur","Editer un secteur");
        return "secteur/add-edit-secteur";
    }

    /* controller pour l'ajout d'un secteur */
    @RequestMapping(path = "/addSecteur",method = RequestMethod.GET)
    public String addEntityById(Model model, Principal principal) {
        model.addAttribute("secteur", new Secteur());
        model.addAttribute("titreFormSecteur","Ajouter un secteur");
        List<Site> listSites = siteService.getAllSites();
        List<Voie> listVoies=voieService.getAllVoies();
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        model.addAttribute("sites",listSites);
        model.addAttribute("voies",listVoies);
        return "secteur/add-edit-secteur";
    }

    /* controller pour enregistrer les données d'un secteur dans la base de données */
    @RequestMapping(path = "/createSecteur", method = RequestMethod.POST)
    public String createOrUpdateVoie(Secteur secteur) throws RecordNotFoundException {
        secteurService.createOrUpdateSecteur(secteur);
        return "redirect:/secteurs";
    }

}
