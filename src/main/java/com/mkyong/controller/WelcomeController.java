package com.mkyong.controller;

import com.mkyong.entity.Club;
import com.mkyong.entity.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import com.mkyong.entity.Footballeur;

@Controller
@RequestMapping("/old")
public class WelcomeController {

    // inject via application.properties
    @Value("Joffrey")
    private String message;
    @Value("${error.message}")
    private String errorMessage;
    Club Bordeaux;

    private Footballeur huard = new Footballeur("Huard","Gaetan","28","Français","goal","1",Bordeaux);
    private Footballeur thouvenel = new Footballeur("Thouvenel","Christophe","30","Français","défenseur","2",Bordeaux);
    private Footballeur lizarazu = new Footballeur("Lizarazu","Vixente","22","Français","défenseur","3",Bordeaux);
    private Footballeur rohr = new Footballeur("Rohr","Gernot","33","Allemand","défenseur","4",Bordeaux);
    private Footballeur batiston = new Footballeur("Batiston","Patrick","30","Français","défenseur","5",Bordeaux);
    private Footballeur soler = new Footballeur("Soler","Gérard","32","Français","milieu","6",Bordeaux);
    private Footballeur vujovic = new Footballeur("Vujovic","Zlatko","25","Yougoslave","attaquant","7",Bordeaux);
    private Footballeur tigana = new Footballeur("Tigana","Jean Amadou","32","Français","milieu","8",Bordeaux);
    private Footballeur muller = new Footballeur("Muller","Dieter","29","Allemand","attaquant","9",Bordeaux);
    private Footballeur giresse = new Footballeur("Giresse","Alain","30","Français","milieu","10",Bordeaux);
    private Footballeur chalana = new Footballeur("Chalana","Ernesto","31","Portugais","attaquant","11",Bordeaux);

    private List<Footballeur> joueurs =Arrays.asList(huard,thouvenel,lizarazu,rohr,batiston,soler,vujovic,tigana,muller,giresse,chalana);
    private Users user=new Users();
    private String message1;
    private String message2;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("user", new Users());
        return "presentation"; //view
    }
    // /hello?name=kotlin récupération du paramêtre name
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") String name, Model model) {
        model.addAttribute("message", name);
        return "welcome"; //view
    }
    // recherche par numero de maillot et envoie de l'objet joueur correspondant
    @GetMapping("/{numeroMaillot}")
    public String singlePathVariable(@PathVariable("numeroMaillot") String numeroMaillot, Model model) {
        for(int i=0; i<joueurs.size();i++){
            if (numeroMaillot.equals((joueurs.get(i)).getNumeroMaillot())){
                model.addAttribute("joueurTrouve",joueurs.get(i));
                System.out.println(joueurs.get(i));
            }
        }
        return "autre";
    }
    @GetMapping("/footballeurs")
    public String mainFoot(Model model) {
        model.addAttribute("joueurs", joueurs);
        return "foot"; //view
    }
    // envoi de l'objet nouveauJoueur à la vue edition
    @GetMapping("/edition")
    public String editionJoueur(Model model) {
        model.addAttribute("nouveauJoueur", new Footballeur());
        return "edition"; //view
    }
// récupération de l'objet nouveauJoueur avec ses attributs et intégration dans la liste joueurs
    @PostMapping("/edition")
    public String submisionForm(@ModelAttribute("nouveauJoueur") Footballeur footballeurs, Model model){
        joueurs.set(Integer.parseInt(footballeurs.getNumeroMaillot())-1,footballeurs);
        model.addAttribute("joueurs", joueurs);
        return "foot";
    }
    @GetMapping("/presentation")
    public String presentationUser(Model model) {
        model.addAttribute("message1", message1);
        model.addAttribute("message2", message2);
        return "welcome"; //view
    }
    // récupération de l'objet user avec ses attributs et récupération des attributs et revoi des attributs à la page welcome
    @PostMapping("/")
    public String formUser(@ModelAttribute("user") Users nouveauUser,Model model){
        message1=nouveauUser.getPrenomUser();
        message2=nouveauUser.getNomUser();
        user.setNomUser(message2);
        user.setPrenomUser(message1);
        model.addAttribute("message1", message1);
        model.addAttribute("message2", message2);
        return "welcome";
    }
}