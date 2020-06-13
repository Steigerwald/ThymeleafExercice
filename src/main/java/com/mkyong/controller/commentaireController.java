package com.mkyong.controller;

import com.mkyong.entity.*;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.CommentaireService;
import com.mkyong.services.SiteService;
import com.mkyong.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/commentaires")
public class commentaireController {

    @Autowired
    private CommentaireService commentaireService;

    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;


    /* Controller pour la liste des commentaires */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllTopos(Model model) {

        List<Commentaire> listCommentaires = commentaireService.getAllCommentaires();
        model.addAttribute("commentaires", listCommentaires);

        return "commentaire/list-commentaires"; //view
    }

    /* Controller pour creer un commentaire */
    @RequestMapping(path="/laisserCommentaires/{id}",method = RequestMethod.POST)
    public String createNewCommentaire(Principal principal,@PathVariable("id") Long id,Commentaire commentaire, Model model) throws RecordNotFoundException {

        Site siteConcerne =siteService.getSiteById(id);
        commentaire.setSite(siteConcerne);
        User currentUser = userService.getUserByMail(principal.getName());
        commentaireService.createCommentaire(commentaire,currentUser);

        return "redirect:/sites/details/{id}"; //view
    }

    /* controller pour modifier un commentaire par Id */
    @RequestMapping(path = "/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Commentaire entity = commentaireService.getCommentaireById(id);
        if (id!=0) {
            model.addAttribute("commentaire", entity);
        } else {
            model.addAttribute("commentaire", new Voie());
        }
        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites",listSites);

        return "commentaire/edit-commentaire";
    }


    /* controller pour effacer un commentaire de la base de données */
    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public String deleteCommentaireById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        commentaireService.deleteCommentaireById(id);
        return "redirect:/commentaires";
    }

    /* controller pour enregistrer les données d'un commentaire modifié dans la base de données */
    @RequestMapping(path = "/modifierCommentaire", method = RequestMethod.POST)
    public String UpdateCommentaire(Commentaire commentaire) throws RecordNotFoundException {
        commentaireService.modifyCommentaire(commentaire);
        return "redirect:/commentaires";
    }

}
