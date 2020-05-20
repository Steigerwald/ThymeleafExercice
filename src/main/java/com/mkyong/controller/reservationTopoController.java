package com.mkyong.controller;


import com.mkyong.entity.Reservation;
import com.mkyong.entity.Topo;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ReservationTopoService;
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

    Logger logger = (Logger) LoggerFactory.getLogger(reservationTopoController.class);


    /* Controller pour la liste des topos */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllReservations(Model model) {
        List<Reservation> listReservations = reservationTopoService.getAllReservationTopos();
        model.addAttribute("reservations", listReservations);
        return "reservation/list-reservationTopos"; //view
    }

    /* controller pour annuler une réservation de la base de données*/
    @RequestMapping(path = "/annulerReservation/{id}",method = RequestMethod.POST)
    public String deleteReservationTopoById(@PathVariable("id") Long id, Model model) throws RecordNotFoundException {
        Topo topoReserve =reservationTopoService.getReservationTopoById(id).getTopo();
        model.addAttribute("topo", topoReserve);
        reservationTopoService.deleteReservationTopoById(id);
        return "topo/details-Topo";
    }

/*
    /*controller pour créer une réservation dans la base de données
    @RequestMapping(path = "/reserverTopo",method = RequestMethod.POST)
    public String reserverReservationTopo(Principal principal,Topo topo, Model model) throws RecordNotFoundException {
        User currentUser = userService.getUserByMail(principal.getName());
        logger.info(" retour de l'identifiant"+principal.getName());
        logger.info(" retour de l'entité "+topo);
        model.addAttribute("topo", topo);
        reservationTopoService.createReservationTopo(topo,currentUser);
        return "topo/details-Topo";
    }*/
}

