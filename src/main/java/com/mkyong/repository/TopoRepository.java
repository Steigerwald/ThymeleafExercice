package com.mkyong.repository;

import com.mkyong.entity.Topo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TopoRepository
        extends JpaRepository<Topo, Long> {
    List<Topo> findAllByDisponible(String Disponible);

}