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
            reservationTrouvee.setEtat("annulee");
            // je supprime la reservation dans le user
            if (reservationTrouvee.getUser()!=null) {
                Collection<Reservation> listeReservation = (reservationTrouvee.getUser().getReservations());
                if ((reservationTrouvee.getUser().getReservations() != null) && (listeReservation != null)) {
                    listeReservation.remove(reservationTrouvee);
                    reservationTrouvee.getUser().setReservations(listeReservation);
                    userService.updateUser(reservationTrouvee.getUser());
                }
            }
            // je supprime la reservation dans le topo
            if (reservationTrouvee.getTopo()!=null){
                reservationTrouvee.getTopo().setDisponible("libre");
                if(reservationTrouvee.getTopo().getReservations()!=null) {
                    reservationTrouvee.getTopo().getReservations().remove(reservationTrouvee);
                    reservationTrouvee.getTopo().setReservations(reservationTrouvee.getTopo().getReservations());
                }
                topoService.UpdateTopo(reservationTrouvee.getTopo());
            }
            reservationTopoService.updateReservationTopo(reservationTrouvee);
            User newUser = userService.getUserByMail(principal.getName());
            model.addAttribute("reservation", reservationTrouvee);
            model.addAttribute("user", newUser);
        }
        return "user/espacePersonnel";
    }

    /*controller pour créer une réservation dans la base de données*/
    @RequestMapping(path = "/reserverTopo/{id}",method = RequestMethod.POST)
    public String reserverReservationTopo(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        User currentUser = userService.getUserByMail(principal.getName());
        Topo topoConcerne = topoService.getTopoById(id);
            topoConcerne.setDisponible("attente");
            topoService.UpdateTopo(topoConcerne);
            logger.info("retour de l'id du topo concerné de reserverReservationTopo car la réservation n'a pas été trouvée " + id);
            reservationTopoService.createReservationTopo(topoConcerne, currentUser);
            model.addAttribute("topo", topoConcerne);
        return "redirect:/topos/details/{id}";
    }

    /*controller pour accepter une réservation dans la base de données*/
    @RequestMapping(path = "/reservationAcceptee/{id}",method = RequestMethod.POST)
    public String accepterReservationTopo(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("retour de l'id de la reservation concernée de accepterReservationTopo "+id);
        Reservation reservationAcceptee = reservationTopoService.getReservationTopoById(id);
        reservationAcceptee.setEtat("acceptee");
        if (reservationAcceptee.getTopo()!=null) {
            reservationAcceptee.getTopo().setDisponible("reservé");
            if(reservationAcceptee.getTopo().getReservations()!=null) {
                reservationAcceptee.getTopo().getReservations().add(reservationAcceptee);
                reservationAcceptee.getTopo().setReservations(reservationAcceptee.getTopo().getReservations());

            }
            topoService.UpdateTopo(reservationAcceptee.getTopo());
            if (reservationAcceptee.getUser()!=null) {
                reservationAcceptee.getUser().getReservations().add(reservationAcceptee);
                reservationAcceptee.getUser().setReservations(reservationAcceptee.getUser().getReservations());
                userService.updateUser(reservationAcceptee.getUser());
            }
        }
        reservationTopoService.updateReservationTopo(reservationAcceptee);
        User newUser = userService.getUserByMail(principal.getName());
        model.addAttribute("user", newUser);

        return "user/espacePersonnel";
    }


    /*controller pour refuser la réservation suite à la demande dans la base de données*/
    @RequestMapping(path = "/reservationRefusee/{id}",method = RequestMethod.POST)
    public String refuserReservationTopo(Principal principal,Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        logger.info("retour de l'id de la reservation concernée de refuserReservationTopo "+id);
        Reservation reservationRefusee = reservationTopoService.getReservationTopoById(id);
        reservationRefusee.setEtat("refusee");
        if (reservationRefusee.getTopo()!=null) {
            reservationRefusee.getTopo().setDisponible("libre");
            topoService.UpdateTopo(reservationRefusee.getTopo());
        }
        reservationTopoService.updateReservationTopo(reservationRefusee);
        User newUser = userService.getUserByMail(principal.getName());
        model.addAttribute("user", newUser);
        return "user/espacePersonnel";
    }
}

