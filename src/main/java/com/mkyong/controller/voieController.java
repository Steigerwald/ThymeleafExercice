package com.mkyong.controller;


import com.mkyong.entity.Secteur;
import com.mkyong.entity.User;
import com.mkyong.entity.Voie;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.SecteurService;
import com.mkyong.services.VoieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/voies")
public class voieController {

    @Autowired
    VoieService voieService;

    @Autowired
    SecteurService secteurService;

    /* Controller pour la liste des voies */
    @RequestMapping()
    public String getAllTopos(Model model) {

        List<Voie> listVoies = voieService.getAllVoies();
        model.addAttribute("voies", listVoies);

        return "voie/list-voies"; //view
    }

    /* controller pour effacer une voie de la base de données */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.GET)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
       voieService.deleteVoieById(id);
        return "redirect:/voies";
    }


    /* controller pour l'edition d'une voie par Id */
    @RequestMapping(path = "/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Voie entity = voieService.getVoieById(id);
        if (id!=0) {
            model.addAttribute("voie", entity);
        } else {
            model.addAttribute("voie", new Voie());
        }
        List<Secteur> listSecteurs = secteurService.getAllSecteurs();
        model.addAttribute("secteurs",listSecteurs);
        model.addAttribute("titreFormVoie","Editer une voie");
        return "voie/add-edit-voie";
    }

    /* controller pour l'ajout d'une voie */
    @RequestMapping(path = "/addVoie")
    public String addEntityById(Model model) {

        model.addAttribute("voie", new Voie());
        model.addAttribute("titreFormVoie","Ajouter une voie");
        List<Secteur> listSecteurs = secteurService.getAllSecteurs();
        model.addAttribute("secteurs", listSecteurs);
        return "voie/add-edit-voie";
    }


    /* controller pour enregistrer les données d'une voie dans la base de données */
    @RequestMapping(path = "/createVoie", method = RequestMethod.POST)
    public String createOrUpdateVoie(Voie voie) {
        voieService.createOrUpdateVoie(voie);
        return "redirect:/voies";
    }

}
