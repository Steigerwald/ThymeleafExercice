package com.mkyong.controller;

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


    @GetMapping()
    public String siteHome(Principal principal,Model model) throws RecordNotFoundException {
        //User newUser = userService.getUserByMail(principal.getName());
        //String nom=newUser.getNomUser();
        //String prenom=newUser.getPrenomUser();
        //model.addAttribute("message1", nom);
       // model.addAttribute("message2", prenom);
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

    @RequestMapping(path = "users/delete/{id}")
    public String deleteEntityById(Model model, @PathVariable("id") Long id)
            throws RecordNotFoundException
    {
        userService.deleteUserById(id);
        return "redirect:/admin/home";
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

    @GetMapping("/admin/home")
    public String InsideHomeAdmin(Principal principal,Model model) {
        User newUser = userService.getUserByMail(principal.getName());
        String nom=newUser.getNomUser();
        String prenom=newUser.getPrenomUser();
        model.addAttribute("message1", nom);
        model.addAttribute("message2", prenom);

        List<User> listF = userService.getAllUsers();

        model.addAttribute("users", listF);
        logger.info(" on est passe par la avant mailuserForm de getMailUser");
        return "list-users"; //view
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        logger.info(" envoi de l'attribut user à registration view");
        return "registration-view";
    }

    // Return registration form template
    /*
    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){
        modelAndView.addObject("user", user);
        modelAndView.setViewName("enregistrement");
        logger.info(" envoi de user et appel de la vue enregistrement");
        return modelAndView;
    }
    */


    // Process form input data
    @RequestMapping(value = "/registration/create", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(@ModelAttribute("user") @Valid User user, Model model, BindingResult bindingResult, HttpServletRequest request,ModelAndView modelAndView) throws RecordNotFoundException {

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
            model.addAttribute("user", user);
            logger.info(" enregistrement de user avec saveUser de post register et envoi du user");
            modelAndView.setViewName("success");
        }
        return modelAndView;
    }
}
