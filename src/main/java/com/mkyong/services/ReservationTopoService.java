package com.mkyong.services;

import com.mkyong.entity.ReservationTopo;
import com.mkyong.entity.Topo;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ReservationTopoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationTopoService {

    @Autowired
    ReservationTopoRepository reservationTopoRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationTopoService.class);


    public List<ReservationTopo> getAllReservationTopos() {
        List<ReservationTopo> result1 = (List<ReservationTopo>) reservationTopoRepository.findAll();

        if (result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result1 ");
            return new ArrayList<ReservationTopo>();
        }
    }

    public ReservationTopo getReservationTopoById(Long id) throws RecordNotFoundException {
        Optional<ReservationTopo> reservation = reservationTopoRepository.findById(id);

        if (reservation.isPresent()) {
            logger.info(" retour de la réservation car elle est présente ");
            return reservation.get();
        } else {
            throw new RecordNotFoundException("Pas de réservation enregistrée avec cet Id");
        }
    }

    public void deleteReservationTopoById(Long id) throws RecordNotFoundException {

        if (id != null) {
            Optional<ReservationTopo> reservation = reservationTopoRepository.findById(id);

            if (reservation.isPresent()) {
                ReservationTopo topoReservationTrouve = getReservationTopoById(id);
                topoReservationTrouve.getTopo().setDisponible(false);
                topoReservationTrouve.getTopo().setReservation(null);
                reservationTopoRepository.deleteById(id);
            } else {
                throw new RecordNotFoundException("Pas de reservation enregistrée avec cet Id");
            }
        } else {
            throw new NullPointerException ( "l'id est null, la réservation n'existe pas");
        }
    }

    public void createReservationTopo(Topo entity) throws RecordNotFoundException {
        Date today = new Date();
        logger.info(" retour de l'entité topo "+entity);
        logger.info(" retour de l'entité reservation "+entity.getReservation());
        logger.info(" retour de l'entité disponible "+entity.getDisponible());
        if ((entity.getReservation() == null) && ((entity.getDisponible()==null)||entity.getDisponible()==false)) {
            logger.info(" retour de "+entity);
            entity.setDisponible(true);
            ReservationTopo newReservation =new ReservationTopo();
            newReservation.setAcceptation(false);
            newReservation.setDateReservation(today);
            //newReservation.setUser(user);
            newReservation.setTopo(entity);
            newReservation = reservationTopoRepository.save(newReservation);
            logger.info(" retour de l'entité de createReservationTopo car l'Id n'existe pas et donc la réservation a été créee");
        } else {
            logger.info(" retour de l'entité reservation n'a pas été sauvegardée car la reservation est existante ou non disponible");
        }
    }
}


