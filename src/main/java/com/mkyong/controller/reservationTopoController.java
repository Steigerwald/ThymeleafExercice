package com.mkyong.controller;


import com.mkyong.entity.Reservation;
import com.mkyong.entity.Topo;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ReservationTopoService;
import com.mkyong.services.TopoService;
import com.mkyong.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class reservationTopoController {

    @Autowired
    private ReservationTopoService reservationTopoService;

   @Autowired
    private UserService userService;

    @Autowired
    private TopoService topoService;

    Logger logger = (Logger) LoggerFactory.getLogger(reservationTopoController.class);

    /* Controller pour la liste des topos */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllReservations(Model model, Principal principal) {
        List<Reservation> listReservations = reservationTopoService.getAllReservationTopos();
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        model.addAttribute("reservations", listReservations);
        return "reservation/list-reservationTopos"; //view
    }

    /* Controller pour la liste des topos */
    @RequestMapping(path = "/gestion",method = RequestMethod.GET)
    public String getAllReservationsMonitoring(Model model, Principal principal) {
        List<Reservation> listReservations = reservationTopoService.getAllReservationTopos();
        User userConnecte = userService.getUserByMail(principal.getName());
        model.addAttribute("user", userConnecte);
        model.addAttribute("reservations", listReservations);
        return "reservation/gestion-reservation"; //view
    }

    /* controller pour annuler une réservation de la base de données*/
    @RequestMapping(path = "/annulerReservation/{id}",method = RequestMethod.POST)
    public String deleteReservationTopoById(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("retour de l'id de la réservation concernée de deleteReservationTopoById "+id);
        Reservation reservationTrouvee = reservationTopoService.getReservationTopoById(id);
        if(reservationTrouvee!=null) {
            reservationTrouvee.setEtat("Annule");
            reservationTopoService.updateReservationTopo(reservationTrouvee);
            reservationTrouvee.getTopo().setDisponible(true);
            reservationTrouvee.getTopo().setReservation(null);
            topoService.UpdateTopo(reservationTrouvee.getTopo());
            if (reservationTrouvee.getUser().getReservations()!=null) {
                Collection<Reservation> listeReservation=(reservationTrouvee.getUser().getReservations());
                listeReservation .remove(reservationTrouvee);
                reservationTrouvee.getUser().setReservations(listeReservation);
                userService.updateUser(reservationTrouvee.getUser());
            }
            User newUser = userService.getUserByMail(principal.getName());
            model.addAttribute("reservation", reservationTrouvee);
            model.addAttribute("user", newUser);
            model.addAttribute("enableButton", 1);
        }
        return "user/espacePersonnel";
    }

    /*controller pour créer une réservation dans la base de données*/
    @RequestMapping(path = "/reserverTopo/{id}",method = RequestMethod.POST)
    public String reserverReservationTopo(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("retour de l'id du topo concerné de reserverReservationTopo "+id);
        Topo topoReserve = topoService.getTopoById(id);
        User currentUser = userService.getUserByMail(principal.getName());
        reservationTopoService.createReservationTopo(topoReserve, currentUser);
        model.addAttribute("topo", topoReserve);
        model.addAttribute("enableButton", 2);
        return "redirect:/topos/details/{id}";
    }

    /*controller pour accepter une réservation dans la base de données*/
    @RequestMapping(path = "/reservationAcceptee/{id}",method = RequestMethod.POST)
    public String accepterReservationTopo(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("retour de l'id de la reservation concernée de accepterReservationTopo "+id);
        Reservation reservationAcceptee = reservationTopoService.getReservationTopoById(id);
        reservationAcceptee.setEtat("Acceptee");
        reservationAcceptee.getTopo().setDisponible(false);
        reservationAcceptee.getTopo().setReservation(reservationAcceptee);
        User newUser = userService.getUserByMail(principal.getName());
        model.addAttribute("user", newUser);
        topoService.UpdateTopo(reservationAcceptee.getTopo());
        reservationTopoService.updateReservationTopo(reservationAcceptee);
        return "user/espacePersonnel";
    }


    /*controller pour refuser la réservation suite à la demande dans la base de données*/
    @RequestMapping(path = "/reservationRefusee/{id}",method = RequestMethod.POST)
    public String refuserReservationTopo(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("retour de l'id de la reservation concernée de refuserReservationTopo "+id);
        Reservation reservationRefusee = reservationTopoService.getReservationTopoById(id);
        reservationRefusee.getTopo().setReservation(null);
        reservationRefusee.getTopo().setDisponible(true);
        reservationRefusee.setEtat("Refusee");
        User newUser = userService.getUserByMail(principal.getName());
        model.addAttribute("user", newUser);
        topoService.UpdateTopo(reservationRefusee.getTopo());
        reservationTopoService.updateReservationTopo(reservationRefusee);
        return "user/espacePersonnel";
    }

}

