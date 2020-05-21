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

    /* controller pour annuler une réservation de la base de données*/
    @RequestMapping(path = "/annulerReservation/{id}",method = RequestMethod.POST)
    public String deleteReservationTopoById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info(" retour de l'id de annulerReservation: "+id);
        Topo topoReserve = topoService.getTopoById(id);
        model.addAttribute("topo", topoReserve);
            logger.info(" retour de l'entité de annulerReservation: "+topoReserve);
        assert topoReserve.getReservation() != null;
        logger.info(" retour de l'id de réservation de topoReserve: "+topoReserve.getReservation().getIdReservation());
        reservationTopoService.deleteReservationTopoById(topoReserve.getReservation().getIdReservation());
        model.addAttribute("enableButton", 1);
        return "topo/details-Topo";

    }

    /*controller pour créer une réservation dans la base de données*/
    @RequestMapping(path = "/reserverTopo/{id}",method = RequestMethod.POST)
    public String reserverReservationTopo(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

            Topo topoReserve = topoService.getTopoById(id);
            model.addAttribute("topo", topoReserve);
            logger.info(" retour de l'entité: " + topoReserve);
            logger.info(" retour du boolean: " + topoReserve.getLocation());
                User currentUser = userService.getUserByMail(principal.getName());
                reservationTopoService.createReservationTopo(topoReserve, currentUser);
        model.addAttribute("enableButton", 2);
                return "topo/details-Topo";

    }
}

