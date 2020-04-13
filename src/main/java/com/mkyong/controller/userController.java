package com.mkyong.controller;

import com.mkyong.entity.User;
import com.mkyong.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class userController {

    Logger logger = (Logger) LoggerFactory.getLogger(userController.class);

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping()
    public String siteIndex(Model model) {

        return "index-view"; //view
    }

    @GetMapping("logout")
    public String logoutSite(Model model) {

        return "redirect:/"; //view
    }

    @GetMapping("login")
    public String loginSite(Model model) {

        System.out.println(passwordEncoder.encode("brice"));
        //User newUser = new User();
        //model.addAttribute(newUser);
        return "login-view"; //view
    }


// Spring security impose son controller
   @GetMapping("/welcome")
    public String formUser(@ModelAttribute("newUser") User newUser, Model model){
        logger.info(" on est passe par la avant mailuserForm de getMailUser");

            return "welcome";
    }


    @GetMapping("home")
    public String InsideHomeUser(Model model) {

        return "userHome-view"; //view
    }

    @GetMapping("admin/home")
    public String InsideHomeAdmin(Model model) {

        return "adminHome-view"; //view
    }
}
