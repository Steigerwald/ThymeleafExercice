package com.mkyong.services;

import com.mkyong.entity.Reservation;
import com.mkyong.entity.Topo;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ReservationTopoRepository;
import com.mkyong.repository.TopoRepository;
import com.mkyong.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ReservationTopoService {

    @Autowired
    private ReservationTopoRepository reservationTopoRepository;

    @Autowired
    private TopoRepository topoRepository;

    @Autowired
    private UserRepository userRepository;


    Logger logger = (Logger) LoggerFactory.getLogger(ReservationTopoService.class);


    public List<Reservation> getAllReservationTopos() {
        List<Reservation> result1 = (List<Reservation>) reservationTopoRepository.findAll();

        if (result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result1 ");
            return new ArrayList<Reservation>();
        }
    }

    public Reservation getReservationTopoById(Long id) throws RecordNotFoundException {

        Optional<Reservation> reservation = reservationTopoRepository.findById(id);

        if (reservation.isPresent()) {
            logger.info(" retour de la réservation car elle est présente ");
            return reservation.get();
        } else {
            throw new RecordNotFoundException("Pas de réservation enregistrée avec cet Id");
        }
    }

    public void deleteReservationTopoById(Long id) throws RecordNotFoundException {
        if (id != null) {
            Optional<Reservation> reservation = reservationTopoRepository.findById(id);
            if (reservation.isPresent()) {
                Reservation topoReservationTrouve = getReservationTopoById(id);
                topoReservationTrouve.getTopo().setDisponible(true);
                //topoRepository.save(topoReservationTrouve.getTopo());
                reservationTopoRepository.deleteById(id);
            } else {
                throw new RecordNotFoundException("Pas de reservation enregistrée avec cet Id");
            }
        } else {
            throw new NullPointerException ( "l'id est null, la réservation n'existe pas");
        }
   }

    public void createReservationTopo(Topo entity,User currentUser) throws RecordNotFoundException {
        Date today = new Date();
            if ((entity.getReservation() == null) && ((entity.getDisponible() == null) || entity.getDisponible() == true)) {

                // je crée la réservation du topo
                Reservation newReservation = new Reservation();
                newReservation.setAcceptation(false);
                newReservation.setDateReservation(today);
                newReservation.setTopo(entity);
                newReservation.setUser(currentUser);

                // je renseigne la reservation dans le topo
                entity.setDisponible(true);
                entity.setReservation(newReservation);

                // je renseigne la reservation dans le user
                Collection listeReservations = currentUser.getReservations();
                listeReservations.add(newReservation);
                currentUser.setReservations(listeReservations);
                reservationTopoRepository.save(newReservation);
                topoRepository.save(entity);
                userRepository.save(currentUser);
                logger.info(" retour de l'entité de createReservationTopo car l'Id n'existe pas et donc la réservation a été créee");
            } else {
                logger.info(" retour de l'entité reservation n'a pas été sauvegardée car la reservation est existante ou non disponible");
            }
    }

    public void updateReservationTopo(Reservation entity) throws RecordNotFoundException {

        if ((entity.getIdReservation()) != null) {

            Optional<Reservation> reservation = reservationTopoRepository.findById(entity.getIdReservation());
            if (reservation.isPresent()) {

                // je modifie la réservation du topo
                Reservation updateReservation = new Reservation();
                updateReservation.setIdReservation(entity.getIdReservation());
                updateReservation.setAcceptation(entity.getAcceptation());
                updateReservation.setDateReservation(entity.getDateReservation());
                updateReservation.setTopo(entity.getTopo());
                updateReservation.setUser(entity.getUser());
                reservationTopoRepository.save(updateReservation);
                logger.info(" l'entité de updateReservation a été modifiée dans la base");

            } else {
                logger.info(" l'entité reservation n'a pas été sauvegardée car la reservation n'est pas existante ou non disponible");
            }

        } else {
            logger.info(" l'entité passée en paramètre de updateReservation n'existe pas ");
        }
    }


}


