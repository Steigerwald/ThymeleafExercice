package com.mkyong.repository;

import com.mkyong.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationTopoRepository
        extends JpaRepository<Reservation, Long> {

}