package com.mkyong.controller;

import com.mkyong.entity.*;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Date;
import java.util.List;



@Controller
@RequestMapping("/topos")
public class topoController {

    Logger logger = (Logger) LoggerFactory.getLogger(topoController.class);

    @Autowired
    private TopoService topoService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    /* Controller pour la liste des topos */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllTopos(Model model,Principal principal) {
        User userConnecte = userService.getUserByMail(principal.getName());
        List<Topo> listTopos = topoService.getAllTopos();
        model.addAttribute("user", userConnecte);
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
    public String editEntityById(Model model, Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        Date today = new Date();
        if (id!=0) {
            Topo entity = topoService.getTopoById(id);
            model.addAttribute("topo", entity);
            model.addAttribute("today", today);
        } else {
            model.addAttribute("topo", new Topo());
            model.addAttribute("today", today);
        }
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("users",listUsers);
        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites",listSites);
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        model.addAttribute("titreFormTopo","Modifier un topo");
        return "topo/add-edit-topo";
    }

    /* controller pour l'ajout d'un topo */
    @RequestMapping(path = "/addTopo",method = RequestMethod.GET)
    public String addEntityById(Model model, Principal principal) {
        Date today = new Date();
        model.addAttribute("today", today);
        model.addAttribute("topo", new Topo());
        model.addAttribute("titreFormTopo","Ajouter un topo");
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("users",listUsers);
        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites",listSites);
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        return "topo/add-edit-topo";
    }

    /* controller pour creer un topo dans la base de données */
    @RequestMapping(path = "/createTopoOrUpdateTopo", method = RequestMethod.POST)
    public String createOrUpdateVoie(Topo topo, Principal principal) throws RecordNotFoundException {
        User userConnecte = userService.getUserByMail(principal.getName());
        topoService.createOrUpdateTopo(topo,userConnecte);
        return "redirect:/topos";
    }

    /* controller pour avoir le détail du topo */
    @RequestMapping(path="/details/{id}",method = RequestMethod.GET)
    public String getDetailsTopo(Model model, Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {

        Topo topoTrouve=topoService.getTopoById(id);
        model.addAttribute("topo", topoTrouve);
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        if (topoTrouve.getLocation()==true) {
            if (topoTrouve.getDisponible()==true){
                model.addAttribute("enableButton", 1);
            } else {
                model.addAttribute("enableButton", 2);
            }
        }   else{
            model.addAttribute("enableButton", 3);
        }
        return "topo/details-Topo"; //view
    }

    /* controller pour rendre disponible en location un topo dans la base de données */
    @RequestMapping(path = "/DisponibleLocation/{id}", method = RequestMethod.POST)
    public String rendreDisponibleLocationTopo(Principal principal, Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        User newUser = userService.getUserByMail(principal.getName());
        Topo topoTrouve=topoService.getTopoById(id);
        topoTrouve.setLocation(true);
        topoService.createOrUpdateTopo(topoTrouve,newUser);
        model.addAttribute("user", newUser);
        model.addAttribute("topo", topoTrouve);
        return "user/espacePersonnel";
    }

    /* controller pour rendre indisponible en location un topo dans la base de données */
    @RequestMapping(path = "/AnnulerLocation/{id}", method = RequestMethod.POST)
    public String rendreIndisponibleLocationTopo(Principal principal, Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        User newUser = userService.getUserByMail(principal.getName());
        Topo topoTrouve=topoService.getTopoById(id);
        topoTrouve.setLocation(false);
        topoTrouve.setDisponible(false);
        logger.info("retour de l'id du topoTrouve "+topoTrouve.getReservation());
        topoService.createOrUpdateTopo(topoTrouve,newUser);
        model.addAttribute("topo", topoTrouve);
        model.addAttribute("user", newUser);
        return "user/espacePersonnel";
    }

}
