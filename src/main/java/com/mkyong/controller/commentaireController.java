package com.mkyong.controller;


import com.mkyong.entity.Commentaire;
import com.mkyong.services.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("admin/commentaires")
public class commentaireController {

    @Autowired
    CommentaireService commentaireService;

    /* Controller pour la liste des commentaires */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllTopos(Model model) {

        List<Commentaire> listCommentaires = commentaireService.getAllCommentaires();
        model.addAttribute("commentaires", listCommentaires);

        return "commentaire/list-commentaires"; //view
    }


}
