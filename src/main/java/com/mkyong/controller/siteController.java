package com.mkyong.controller;

import com.mkyong.entity.*;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.form.Search;
import com.mkyong.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/sites")
public class siteController {

    Logger logger = (Logger) LoggerFactory.getLogger(siteController.class);

    @Autowired
    private SiteService siteService;

    @Autowired
    private TopoService topoService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecteurService secteurService;

    @Autowired
    private ImageService imageService;


    /* Controller pour la liste des sites */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllSites(Model model, Principal principal) {
        User userConnecte = userService.getUserByMail(principal.getName());
        List<Site> listSites = siteService.getAllSites();
        logger.info(" le role du user est: "+userConnecte.getRole());
        model.addAttribute("user", userConnecte);
        model.addAttribute("sites", listSites);
        return "site/list-sites"; //view
    }

    /* Controller pour la liste des sites publics */
    @RequestMapping(path ="/publics",method = RequestMethod.GET)
    public String getAllSitesPublics(Model model, Principal principal) {
        User userConnecte = userService.getUserByMail(principal.getName());
        List<Site> listSitesPublics = siteService.getAllSitesPublics();
        logger.info(" le role du user est: "+userConnecte.getRole());
        model.addAttribute("user", userConnecte);
        model.addAttribute("sitesPublics", listSitesPublics);
        return "site/list-sitesPublics"; //view
    }

    /* controller pour rendre public un site */
    @RequestMapping(path = "/rendrePublicSite/{id}",method = RequestMethod.POST)
    public String rendrePublicSiteById(Model model, Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        User userConnecte = userService.getUserByMail(principal.getName());
        Site siteTrouve =siteService.getSiteById(id);
        siteTrouve.setPublic(true);
        siteService.UpdateSite(siteTrouve);
        return "redirect:/user/espacePersonnel";
    }

    /* controller pour rendre privé un site */
    @RequestMapping(path = "/rendrePriveSite/{id}",method = RequestMethod.POST)
    public String rendrePriveSiteById(Model model, Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        User userConnecte = userService.getUserByMail(principal.getName());
        Site siteTrouve =siteService.getSiteById(id);
        siteTrouve.setPublic(false);
        siteService.UpdateSite(siteTrouve);
        return "redirect:/user/espacePersonnel";
    }

    /* controller pour effacer un site de la base de données */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        siteService.deleteSiteById(id);
        return "redirect:/sites";
    }

    /* controller pour l'edition d'un site par Id */
    @RequestMapping(path = "/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model, Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        if (id!=0) {
            Site entity = siteService.getSiteById(id);
            model.addAttribute("site", entity);
        } else {
            model.addAttribute("site", new Site());
        }
        List<Topo> listTopos = topoService.getAllTopos();
        model.addAttribute("topos",listTopos);
        List<Secteur> listSecteurs=secteurService.getAllSecteurs();
        model.addAttribute("secteurs",listSecteurs);
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("users",listUsers);
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        model.addAttribute("titreFormSite","Editer un site");
        return "site/add-edit-site";
    }

    /* controller pour l'ajout d'un site */
    @RequestMapping(path = "/addSite",method = RequestMethod.GET)
    public String addEntityById(Model model, Principal principal) {
        model.addAttribute("site", new Site());
        model.addAttribute("titreFormSite","Ajouter un site");
        List<Secteur> listSecteurs=secteurService.getAllSecteurs();
        model.addAttribute("secteurs",listSecteurs);
        List<Topo> listTopos = topoService.getAllTopos();
        model.addAttribute("topos",listTopos);
        List<User> listUsers = userService.getAllUsers();
        model.addAttribute("users",listUsers);
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        return "site/add-edit-site";
    }

    /* controller pour enregistrer les données d'un site dans la base de données */
    @RequestMapping(path = "/createSiteOrUpdateSite", method = RequestMethod.POST)
    public String createOrUpdateSite(@RequestParam("file") MultipartFile fileImage,Site site, Image imageSite, Principal principal) throws RecordNotFoundException, IOException {
        User userConnecte = userService.getUserByMail(principal.getName());
        imageSite=imageService.recupererImageFile(fileImage);
        logger.info(" la valeur de l'Id de site : "+site.getIdSite());

        if (site.getIdSite()==null){
            if (fileImage.isEmpty()){
                site.setImage(null);
            }else {
                site.setImage(imageSite);
            }
            site.setOfficiel(false);
            site.setPublic(false);
            logger.info(" l'image de service est  "+site.getImage());
            siteService.CreateSite(site,userConnecte);

        }else {
            if (!(fileImage.isEmpty())){
                site.setImage(imageSite);
            }
            Site siteAModifier = siteService.getSiteById(site.getIdSite());
            siteService.UpdateSite(site);
        }

        return "redirect:/sites";
    }

    /* controller pour avoir le détail du site */
    @RequestMapping(path="/details/{id}",method = RequestMethod.GET)
    public String getDetailsSite(Model model,Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        Commentaire commentaire = new Commentaire();
        model.addAttribute(commentaire);
        User userConnecte = userService.getUserByMail(principal.getName());
        Site siteTrouve=siteService.getSiteById(id);
        model.addAttribute("site", siteTrouve);
        model.addAttribute("user", userConnecte);
        return "site/details-site"; //view
    }

    /* controller pour la recherche de sites */
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String searchSitesBySearch(Model model,Principal principal) {
        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites", listSites);
        List<Secteur> listSecteurs=secteurService.getAllSecteurs();
        model.addAttribute("secteurs", listSecteurs);
        model.addAttribute("search", new Search());
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        return "site/search-sites";
    }

    /* controller pour la recherche de sites par le lieu données venant de la base de données */
    @RequestMapping(path = "/search/mycriteres", method = RequestMethod.POST)
    public String searchSitesByLieu(Search search,Model model, Principal principal) {
        //List<Site> listSitesTrouves = siteService.getAllSitesByLieu(search.getLieu());
        List<Site> listSitesTrouves = siteService.getAllSitesBySearch(search);
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        logger.info(" la valeur de listSitestrouves est: "+listSitesTrouves);
        if (listSitesTrouves.size()==0){
            model.addAttribute("sitesTrouves", null);
        } else{
            model.addAttribute("sitesTrouves", listSitesTrouves);
        }
        return "site/list-sitesTrouves";
    }

    /* controller pour officialiser un site */
    @RequestMapping(path = "/officiel/{id}",method = RequestMethod.POST)
    public String officialiserSiteById(Model model, Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        User userConnecte = userService.getUserByMail(principal.getName());
        Site siteTrouve =siteService.getSiteById(id);
        siteTrouve.setOfficiel(true);
        siteService.UpdateSite(siteTrouve);
        return "redirect:/sites";
    }

    /* controller pour non officialiser un site */
    @RequestMapping(path = "/nonOfficiel/{id}",method = RequestMethod.POST)
    public String nonOfficialiserSiteById(Model model, Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        User userConnecte = userService.getUserByMail(principal.getName());
        Site siteTrouve =siteService.getSiteById(id);
        siteTrouve.setOfficiel(false);
        siteService.UpdateSite(siteTrouve);
        return "redirect:/sites";
    }



}
