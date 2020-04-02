package com.mkyong.controller;

import com.mkyong.entity.ImageEntity;
import com.mkyong.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class userController {

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

        return "login-view"; //view
    }

    @PostMapping("login")
    public String formUser(@ModelAttribute("user") User nouveauUser, Model model){

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
