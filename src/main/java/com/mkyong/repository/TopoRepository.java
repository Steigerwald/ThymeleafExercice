package com.mkyong.repository;

import com.mkyong.entity.Topo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TopoRepository
        extends JpaRepository<Topo, Long> {

}