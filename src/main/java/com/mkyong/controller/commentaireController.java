package com.mkyong.controller;


import com.mkyong.entity.Commentaire;
import com.mkyong.entity.Topo;
import com.mkyong.services.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/commentaires")
public class commentaireController {

    @Autowired
    CommentaireService commentaireService;

    /* Controller pour la liste des commentaires */
    @RequestMapping()
    public String getAllTopos(Model model) {

        List<Commentaire> listCommentaires = commentaireService.getAllCommentaires();
        model.addAttribute("commentaires", listCommentaires);

        return "commentaire/list-commentaires"; //view
    }


}
