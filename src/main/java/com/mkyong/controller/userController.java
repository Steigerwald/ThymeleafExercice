package com.mkyong.controller;

import com.mkyong.entity.ImageEntity;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

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


    @GetMapping()
    public String siteIndex(Principal principal,Model model) throws RecordNotFoundException {

        User newUser = userService.getUserByMail(principal.getName());
        String nom=newUser.getNomUser();
        String prenom=newUser.getPrenomUser();
        model.addAttribute("message1", nom);
        model.addAttribute("message2", prenom);

        return "home"; //view
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
    public String formUser(Principal principal, Model model) throws RecordNotFoundException {
       User newUser = userService.getUserByMail(principal.getName());
       String nom=newUser.getNomUser();
       String prenom=newUser.getPrenomUser();
       model.addAttribute("message1", nom);
       model.addAttribute("message2", prenom);

       logger.info(" on est passe par la avant mailuserForm de getMailUser");

            return "home";
    }


    @GetMapping("admin/home")
    public String InsideHomeAdmin(Model model) {

        return "adminHome-view"; //view
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        logger.info(" envoi de l'attribut userForm à registration view");
        return "registration-view";
    }
/*
    @RequestMapping(path = "/registration/create", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("userform") User userForm) throws RecordNotFoundException {
        logger.info(" enregistrement de userForm avec create de post registration create");
        userService.create(userForm);

        return "redirect:/login";
    }
*/

    // Return registration form template
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("enregistrement");
        logger.info(" envoi de user et appel de la vue enregistrement");
        return modelAndView;
    }

    // Process form input data
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, HttpServletRequest request) throws RecordNotFoundException {

        // Lookup user in database by e-mail
        User userExists = userService.getUserByMail(user.getMailUser());

        System.out.println(userExists);

        if (userExists != null) {
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            logger.info(" enregistrement existe déjà de post register");
            modelAndView.setViewName("registration-view");
            bindingResult.reject("email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration-view");
        } else { // new user so we create user and send confirmation e-mail

            userService.saveUser(user);
            logger.info(" enregistrement de user avec saveUser de post register");
            modelAndView.setViewName("registration-view");
        }
        return modelAndView;
    }

}
