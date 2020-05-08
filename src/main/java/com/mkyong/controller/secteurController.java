package com.mkyong.controller;


import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.SecteurService;
import com.mkyong.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/secteurs")
public class secteurController {

    @Autowired
    SecteurService secteurService;

    @Autowired
    SiteService siteService;

    /* Controller pour la liste des secteurs */
    @RequestMapping()
    public String getAllSecteurs(Model model) {

        List<Secteur> listSecteurs = secteurService.getAllSecteurs();
        model.addAttribute("secteurs", listSecteurs);

        return "secteur/list-secteurs"; //view
    }

    /* controller pour effacer un secteur de la base de données */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.GET)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        secteurService.deleteSecteurById(id);
        return "redirect:/voies";
    }


    /* controller pour l'edition d'un secteur par Id */
    @RequestMapping(path = "/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Secteur entity = secteurService.getSecteurById(id);
        if (id!=0) {
            model.addAttribute("secteur", entity);
        } else {
            model.addAttribute("secteur", new Secteur());
        }
        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites",listSites);
        model.addAttribute("titreFormSecteur","Editer un secteur");
        return "secteur/add-edit-secteur";
    }

    /* controller pour l'ajout d'un secteur */
    @RequestMapping(path = "/addSecteur")
    public String addEntityById(Model model) {

        model.addAttribute("secteur", new Secteur());
        model.addAttribute("titreFormSecteur","Ajouter un secteur");
        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites",listSites);
        return "secteur/add-edit-secteur";
    }

    /* controller pour enregistrer les données d'un secteur dans la base de données */
    @RequestMapping(path = "/createVoie", method = RequestMethod.POST)
    public String createOrUpdateVoie(Secteur secteur) {
        secteurService.createOrUpdateSecteur(secteur);
        return "redirect:/secteurs";
    }

}
