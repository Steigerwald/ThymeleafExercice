package com.mkyong.services;

import com.mkyong.entity.ReservationTopo;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ReservationTopoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationTopoService {

    @Autowired
    ReservationTopoRepository reservationTopoRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(ReservationTopoService.class);


    public List<ReservationTopo> getAllReservationTopos()
    {
        List<ReservationTopo> result1 =(List<ReservationTopo>) reservationTopoRepository.findAll();

        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result1 ");
            return new ArrayList<ReservationTopo>();
        }
    }

    public ReservationTopo getReservationTopoById(Long id) throws RecordNotFoundException
    {
        Optional<ReservationTopo> reservation = reservationTopoRepository.findById(id);

        if(reservation.isPresent()) {
            logger.info(" retour de la réservation car elle est présente ");
            return reservation.get();
        } else {
            throw new RecordNotFoundException("Pas de réservation enregistrée avec cet Id");
        }
    }

    public ReservationTopo createOrUpdateReservation(ReservationTopo entity)
    {
        if(entity.getIdReservation()  == null)
        {
            entity = reservationTopoRepository.save(entity);

            logger.info(" retour de l'entité de createOrUpdateReservation car l'Id n'existe pas");
            return entity;
        }
        else
        {
            Optional<ReservationTopo> reservation = reservationTopoRepository.findById(entity.getIdReservation());

            if(reservation.isPresent())
            {
                ReservationTopo newReservation = reservation.get();
                newReservation.setIdReservation(entity.getIdReservation());
                newReservation.setAcceptation(entity.getAcceptation());
                newReservation.setDateReservation(entity.getDateReservation());

                newReservation = reservationTopoRepository.save(newReservation);

                logger.info(" retour de la nouvelle entité reservation de createOrUpdateReservation qui a été sauvegardée et la reservation est existante");
                return newReservation;

            } else {
                entity = reservationTopoRepository.save(entity);
                logger.info(" retour de l'entité resrvationTopo de createOrUpdateReservation qui a été sauvegardée car la réservation n'est pas existante");
                return entity;
            }
        }
    }


    public void deleteReservationById(Long id) throws RecordNotFoundException
    {
        Optional<ReservationTopo> reservation = reservationTopoRepository.findById(id);

        if(reservation.isPresent())
        {
            reservationTopoRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de reservation enregistrée avec cet Id");
        }
    }

}
