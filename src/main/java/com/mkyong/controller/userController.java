package com.mkyong.controller;

import com.mkyong.entity.Site;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.CustomUserDetailsService;
import com.mkyong.services.SiteService;
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
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;

    /* Controller pour la page d'entrée sans connection */
    @RequestMapping(method = RequestMethod.GET)
    public String siteHome(Principal principal,Model model) throws RecordNotFoundException {
        return "home/entree"; //view
    }

    /* Controller pour qu'un user se déconnecte */
    @RequestMapping(path="logout",method = RequestMethod.GET)
    public String logoutSite(Model model, Principal principal) {
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        return "user/login-view"; //view
    }

    /* Controller pour un login d'un user */
    @RequestMapping(path="login",method = RequestMethod.GET)
    public String loginSite(Model model) {
        User newUser = new User();
        model.addAttribute(newUser);
        return "user/login-view"; //view
    }

    /* controller pour effacer un user de la base de données */
    @RequestMapping(path = "admin/users/delete/{id}",method = RequestMethod.POST)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    /* controller pour l'edition du User par Id */
    @RequestMapping(path = "admin/users/edit/{id}",method = RequestMethod.GET)
    public String editEntityById(Model model, Principal principal, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info(" valeur de l'id de user"+id);
        if (id!=0) {
            User entity = userService.getUserById(id);
            logger.info(" valeur de l'user de edit " + entity.getNomUser());
            logger.info(" on est passe par la avant l'appel de la page user/list-users de url /admin/home");
            model.addAttribute("user", entity);
            List<Site> listSites = siteService.getAllSites();
            model.addAttribute("sites",listSites);
            model.addAttribute("titreFormUser","Editer un user");
            User userConnecte = userService.getUserByMail(principal.getName());
            model.addAttribute("user", userConnecte);
            return "user/add-edit-user";
        } else {
           return "page404";
        }
    }

    /* controller de la page de présentation */
    @RequestMapping(path="home",method = RequestMethod.GET)
    public String formUser(Principal principal, Model model) throws RecordNotFoundException {
        User newUser = userService.getUserByMail(principal.getName());
       String nom=newUser.getNomUser();
       String prenom=newUser.getPrenomUser();
       model.addAttribute("user", newUser);
       model.addAttribute("message1", nom);
       model.addAttribute("message2", prenom);
        logger.info(" on est passe par la avant l'appel de la page home/home de url /home");
            return "home/home";
    }

    /* controller de la page de espace perso */
    @RequestMapping(path="user/espacePersonnel",method = RequestMethod.GET)
    public String EspacePersonnelUser(Principal principal, Model model) throws RecordNotFoundException {
        User newUser = userService.getUserByMail(principal.getName());
        model.addAttribute("user", newUser);
        model.addAttribute("enableButton",2);
        return "user/espacePersonnel";
    }

    /* controller pour la page de l'administrateur */
    @RequestMapping(path="admin/users",method = RequestMethod.GET)
    public String InsideHomeAdmin(Principal principal,Model model) {
        User userConnecte = userService.getUserByMail(principal.getName());
        System.out.println(userConnecte.getRole().getNomRole());
        if ((userConnecte.getRole().getNomRole()).equalsIgnoreCase("ROLE_ADMIN")) {
            String nom = userConnecte.getNomUser();
            String prenom = userConnecte.getPrenomUser();
            model.addAttribute("message1", nom);
            model.addAttribute("message2", prenom);
            List<User> listF = userService.getAllUsers();
            model.addAttribute("users", listF);
            model.addAttribute("user", userConnecte);
            logger.info(" on est passe par la avant l'appel de la page user/list-users de url /admin/home");
            return "user/list-users"; //view
        } else{
            String nom = userConnecte.getNomUser();
            String prenom = userConnecte.getPrenomUser();
            model.addAttribute("message1", nom);
            model.addAttribute("message2", prenom);
            model.addAttribute("user", userConnecte);
            return "home/home"; //view
        }
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
    @RequestMapping(path = "admin/users/updateUser", method = RequestMethod.POST)
    public String createOrUpdateUser(User user) throws RecordNotFoundException {
        logger.info(" valeur de l'id de edit de creatUser "+ user.getIdUser());
        User userAModofier=userService.getUserById(user.getIdUser());
        user.setMotDePasseUser(userAModofier.getMotDePasseUser());
        logger.info(" valeur de l'user de edit de creatUser "+ user.getMotDePasseUser());
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
            int identification = 2;
            user.setRole(userService.getRoleById(identification));
            userService.saveUser(user);
            model.addAttribute("user", user);
            logger.info("enregistrement de user avec saveUser de post register et envoi du user");
            modelAndView.setViewName("user/success");
        }
        return modelAndView;
    }
}
