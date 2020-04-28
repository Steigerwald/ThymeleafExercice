package com.mkyong.repository;

import com.mkyong.entity.CarteTopographique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarteTopographiqueRepository
        extends JpaRepository<CarteTopographique, Long> {

}
