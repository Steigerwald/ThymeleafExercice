package com.mkyong.controller;

import com.mkyong.entity.Site;
import com.mkyong.entity.Topo;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.SiteService;
import com.mkyong.services.TopoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/sites")
public class siteController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private TopoService topoService;

    /* Controller pour la liste des sites */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllSites(Model model) {

        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites", listSites);

        return "site/list-sites"; //view
    }


    /* controller pour effacer un site de la base de données */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        siteService.deleteSiteById(id);
        return "redirect:/sites";
    }


    /* controller pour l'edition d'un site par Id */
    @RequestMapping(path = "/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Site entity = siteService.getSiteById(id);
        if (id!=0) {
            model.addAttribute("site", entity);
        } else {
            model.addAttribute("site", new Site());
        }
        List<Topo> listTopos = topoService.getAllTopos();
        model.addAttribute("topos",listTopos);

        model.addAttribute("titreFormSite","Editer un site");
        return "site/add-edit-site";
    }

    /* controller pour l'ajout d'un site */
    @RequestMapping(path = "/addSite",method = RequestMethod.GET)
    public String addEntityById(Model model) {

        model.addAttribute("site", new Site());
        model.addAttribute("titreFormSite","Ajouter un site");
        List<Topo> listTopos = topoService.getAllTopos();
        model.addAttribute("topos",listTopos);
        return "site/add-edit-site";
    }


    /* controller pour enregistrer les données d'un site dans la base de données */
    @RequestMapping(path = "/createSite", method = RequestMethod.POST)
    public String createOrUpdateVoie(Site site) {
        siteService.createOrUpdateSite(site);
        return "redirect:/voies";
    }

    /* controller pour avoir le détail du site */
    @RequestMapping(path="/details/{id}",method = RequestMethod.GET)
    public String getDetailsSite(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Site siteTrouve=siteService.getSiteById(id);

        model.addAttribute("site", siteTrouve);

        return "site/details-site"; //view
    }


}
