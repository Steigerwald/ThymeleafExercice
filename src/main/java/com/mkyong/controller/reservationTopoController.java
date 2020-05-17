package com.mkyong.controller;


import com.mkyong.entity.ReservationTopo;
import com.mkyong.entity.Topo;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ReservationTopoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class reservationTopoController {

    @Autowired
    ReservationTopoService reservationTopoService;


    Logger logger = (Logger) LoggerFactory.getLogger(reservationTopoController.class);


    /* Controller pour la liste des topos */
    @RequestMapping(method = RequestMethod.GET)
    public String getAllReservations(Model model) {
        List<ReservationTopo> listReservations = reservationTopoService.getAllReservationTopos();
        model.addAttribute("reservations", listReservations);
        return "reservation/list-reservationTopos"; //view
    }

    /* controller pour annuler une réservation de la base de données */
    @RequestMapping(path = "/annulerReservationTopo",method = RequestMethod.POST)
    public String deleteReservationTopoById(Topo topo, Model model) throws RecordNotFoundException {
        //ReservationTopo topoReservationTrouve=reservationTopoService.getReservationTopoById(id);
        //topoReservationTrouve.getTopo().setDisponible(false);
        model.addAttribute("topo", topo);
        logger.info(" retour de l'entité topo"+topo);
        logger.info(" retour de l'entité id de topo"+topo.getIdTopo());
        reservationTopoService.deleteReservationTopoById(topo.getReservation().getIdReservation());
        return "topo/details-Topo";
    }

    /* controller pour créer une réservation dans la base de données */
    @RequestMapping(path = "/reserverTopo",method = RequestMethod.POST)
    public String reserverReservationTopo(Topo topo, Model model) throws RecordNotFoundException {
        //User newUser = userService.getUserByMail(principal.getName());
        logger.info(" retour de l'entité "+topo);
        model.addAttribute("topo", topo);
        reservationTopoService.createReservationTopo(topo);
        return "topo/details-Topo";
    }
}


