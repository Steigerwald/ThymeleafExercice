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
    public String getAllReservations(Model model) {
        List<Reservation> listReservations = reservationTopoService.getAllReservationTopos();
        model.addAttribute("reservations", listReservations);
        return "reservation/list-reservationTopos"; //view
    }

    /* Controller pour la liste des topos */
    @RequestMapping(path = "/gestion",method = RequestMethod.GET)
    public String getAllReservationsMonitoring(Model model) {
        List<Reservation> listReservations = reservationTopoService.getAllReservationTopos();
        model.addAttribute("reservations", listReservations);
        return "reservation/gestion-reservation"; //view
    }


    /* controller pour annuler une réservation de la base de données*/
    @RequestMapping(path = "/annulerReservation/{id}",method = RequestMethod.POST)
    public String deleteReservationTopoById(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("retour de l'id du topo concerné "+id);
        Topo topoReserve = topoService.getTopoById(id);
        model.addAttribute("topo", topoReserve);
        assert topoReserve.getReservation() != null;
        logger.info(" retour de l'id de réservation de topoReserve: " + topoReserve.getReservation().getIdReservation());
        reservationTopoService.deleteReservationTopoById(topoReserve.getReservation().getIdReservation());
        model.addAttribute("enableButton", 1);
        return "redirect:/topos/details/{id}";
    }

    /*controller pour créer une réservation dans la base de données*/
    @RequestMapping(path = "/reserverTopo/{id}",method = RequestMethod.POST)
    public String reserverReservationTopo(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("retour de l'id du topo concerné "+id);
        Topo topoReserve = topoService.getTopoById(id);
        model.addAttribute("topo", topoReserve);
        logger.info(" retour de l'entité: " + topoReserve);
        logger.info(" retour du boolean: " + topoReserve.getLocation());
        User currentUser = userService.getUserByMail(principal.getName());
        reservationTopoService.createReservationTopo(topoReserve, currentUser);
        model.addAttribute("enableButton", 2);
                return "redirect:/topos/details/{id}";
    }

    /*controller pour accepter une réservation dans la base de données*/
    @RequestMapping(path = "/reservationAcceptee/{id}",method = RequestMethod.POST)
    public String accepterReservationTopo(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        logger.info("retour de l'id de la reservation concernée "+id);
        Reservation reservationAcceptee = reservationTopoService.getReservationTopoById(id);
        reservationAcceptee.setAcceptation(true);
        reservationAcceptee.getTopo().setDisponible(false);
        reservationAcceptee.getTopo().setReservation(reservationAcceptee);
        topoService.createOrUpdateTopo(reservationAcceptee.getTopo());
        reservationTopoService.updateReservationTopo(reservationAcceptee);
        return "redirect:/reservations/gestion";
    }


    /*controller pour refuser la réservation suite à la demande dans la base de données*/
    @RequestMapping(path = "/reservationRefusee/{id}",method = RequestMethod.POST)
    public String refuserReservationTopo(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        logger.info("retour de l'id de la reservation concernée "+id);
        Reservation reservationRefusee = reservationTopoService.getReservationTopoById(id);
        reservationRefusee.getTopo().setReservation(null);
        reservationRefusee.getTopo().setDisponible(true);
        topoService.createOrUpdateTopo(reservationRefusee.getTopo());
        reservationTopoService.deleteReservationTopoById(id);
        return "redirect:/reservations/gestion";
    }

}

