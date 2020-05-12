package com.mkyong.controller;

import com.mkyong.entity.Image;
import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.entity.Topo;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ImageService;
import com.mkyong.services.SiteService;
import com.mkyong.services.TopoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;




@Controller
@RequestMapping("/topos")
public class topoController {


    @Autowired
    TopoService topoService;

    @Autowired
    SiteService siteService;

    /* Controller pour la liste des topos */
    @RequestMapping()
    public String getAllTopos(Model model) {

        List<Topo> listTopos = topoService.getAllTopos();
        model.addAttribute("topos", listTopos);

        return "topo/list-topos"; //view
    }

    /* controller pour effacer un topo de la base de données */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        topoService.deleteTopoById(id);
        return "redirect:/topos";
    }


    /* controller pour l'edition d'un topo par Id */
    @RequestMapping(path = "/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Topo entity = topoService.getTopoById(id);
        Date today = new Date();
        if (id!=0) {
            model.addAttribute("topo", entity);
            model.addAttribute("today", today);
        } else {
            model.addAttribute("topo", new Topo());
            model.addAttribute("today", today);
        }
        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites",listSites);
        model.addAttribute("titreFormTopo","Editer un topo");
        return "topo/add-edit-topo";
    }

    /* controller pour l'ajout d'un topo */
    @RequestMapping(path = "/addTopo")
    public String addEntityById(Model model) {
        Date today = new Date();
        model.addAttribute("today", today);
        model.addAttribute("topo", new Topo());
        model.addAttribute("titreFormTopo","Ajouter un topo");

        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites",listSites);

        return "topo/add-edit-topo";
    }

    /* controller pour enregistrer les données d'un topo dans la base de données */
    @RequestMapping(path = "/createTopo", method = RequestMethod.POST)
    public String createOrUpdateVoie(Topo topo) {
        topoService.createOrUpdateTopo(topo);
        return "redirect:/topos";
    }

    /* controller pour avoir le détail du topo */
    @RequestMapping(path="/details/{id}")
    public String getDetailsTopo(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Topo topoTrouve=topoService.getTopoById(id);

        model.addAttribute("topo", topoTrouve);

        return "topo/details-Topo"; //view
    }

}
