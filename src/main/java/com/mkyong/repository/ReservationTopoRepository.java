package com.mkyong.repository;

import com.mkyong.entity.ReservationTopo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationTopoRepository
        extends JpaRepository<ReservationTopo, Long> {

}