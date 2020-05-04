package com.mkyong.controller;

import com.mkyong.entity.Role;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.CustomUserDetailsService;
import com.mkyong.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class userController {

    Logger logger = (Logger) LoggerFactory.getLogger(userController.class);

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;



    /* Controller pour la page d'entrée sans connection */
    @RequestMapping(method = RequestMethod.GET)
    public String siteHome(Principal principal,Model model) throws RecordNotFoundException {

        return "home/entree"; //view
    }

    /* Controller pour qu'un user se déconnecte */
    @RequestMapping(path="logout",method = RequestMethod.GET)
    public String logoutSite(Model model) {

        return "user/login-view"; //view
    }

    /* Controller pour un login d'un user */
    @RequestMapping(path="login",method = RequestMethod.GET)
    public String loginSite(Model model) {

        System.out.println(passwordEncoder.encode("brice"));
        User newUser = new User();
        model.addAttribute(newUser);
        return "user/login-view"; //view
    }

    /* controller pour effacer un user de la base de données */
    @RequestMapping(path = "admin/users/delete/{id}",method = RequestMethod.GET)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }


    /* controller pour l'edition du User par Id */
    @RequestMapping(path = "admin/users/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        User entity = userService.getUserById(id);
        if (id!=0) {
            model.addAttribute("user", entity);
        } else {
            model.addAttribute("user", new User());
        }
        model.addAttribute("titreFormUser","Editer un user");
        return "user/add-edit-user";
    }


    /* controller de la page de présentation */
    @RequestMapping(path="home",method = RequestMethod.GET)
    public String formUser(Principal principal, Model model) throws RecordNotFoundException {

        User newUser = userService.getUserByMail(principal.getName());
       String nom=newUser.getNomUser();
       String prenom=newUser.getPrenomUser();
       model.addAttribute("message1", nom);
       model.addAttribute("message2", prenom);
       logger.info(" on est passe par la avant l'appel de la page home/home de url /home");
            return "home/home";
    }

    /* controller pour la page de l'administrateur */
    @RequestMapping(path="admin/users",method = RequestMethod.GET)
    public String InsideHomeAdmin(Principal principal,Model model) {

        User newUser = userService.getUserByMail(principal.getName());
        String nom=newUser.getNomUser();
        String prenom=newUser.getPrenomUser();
        model.addAttribute("message1", nom);
        model.addAttribute("message2", prenom);
        List<User> listF = userService.getAllUsers();
        model.addAttribute("users", listF);
        logger.info(" on est passe par la avant l'appel de la page user/list-users de url /admin/home");
        return "user/list-users"; //view
    }

    /* controller pour la page d'enregistrement du user */
    @RequestMapping(path="registration",method = RequestMethod.GET)
    public String registration(Model model) {

        User newUser = new User();
        model.addAttribute("user", newUser);
        logger.info(" envoi de l'attribut user à registration view");
        return "user/registration-view";
    }


    /* controller pour enregistrer les données de User dans la base de données */
    @RequestMapping(path = "admin/users/createUser", method = RequestMethod.POST)
    public String createOrUpdateUser(User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    /* controller pour récupérer les données du formulaire d'enregistrement des users */
    @RequestMapping(value = "registration/create", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(@ModelAttribute("user") @Valid User user, Model model, BindingResult bindingResult, HttpServletRequest request,Principal principal,ModelAndView modelAndView) throws RecordNotFoundException {

        User userExists = userService.getUserByMail(user.getMailUser());
        System.out.println(userExists);
        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            logger.info(" enregistrement existe déjà de post register");
            modelAndView.setViewName("user/registration-view");
            bindingResult.reject("email");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/registration-view");
        } else {
            int identification=2;
            user.setRole(userService.getRoleById(identification));
            userService.saveUser(user);
            model.addAttribute("user", user);
            logger.info("enregistrement de user avec saveUser de post register et envoi du user");
            modelAndView.setViewName("user/success");
        }
        return modelAndView;
    }
}
