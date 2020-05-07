package com.mkyong.controller;


import com.mkyong.entity.ReservationTopo;
import com.mkyong.services.ReservationTopoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class reservationTopoController {

    @Autowired
    ReservationTopoService reservationTopoService;

    /* Controller pour la liste des topos */
    @RequestMapping()
    public String getAllReservations(Model model) {

        List<ReservationTopo> listReservations = reservationTopoService.getAllReservationTopos();
        model.addAttribute("reservations", listReservations);

        return "reservation/list-reservationTopos"; //view
    }
}
