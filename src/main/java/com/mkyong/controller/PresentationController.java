package com.mkyong.controller;

import com.mkyong.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class PresentationController {

    private String message1;
    private String message2;
    private User user=new User();

    @GetMapping()
    public String main(Model model) {
        model.addAttribute("user", new User());
        return "presentation"; //view
    }

    @GetMapping("presentation")
    public String presentationUser(Model model) {
        model.addAttribute("message1", message1);
        model.addAttribute("message2", message2);
        return "welcome"; //view
    }

    // récupération de l'objet user avec ses attributs et récupération des attributs et revoi des attributs à la page welcome
    @PostMapping()
    public String formUser(@ModelAttribute("user") User nouveauUser, Model model){
        message1=nouveauUser.getPrenomUser();
        message2=nouveauUser.getNomUser();
        user.setNomUser(message2);
        user.setPrenomUser(message1);
        model.addAttribute("message1", message1);
        model.addAttribute("message2", message2);
        return "welcome";
    }

}
