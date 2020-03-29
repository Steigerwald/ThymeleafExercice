package com.mkyong.repository;

import com.mkyong.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageEntityRepository
        extends JpaRepository<ImageEntity, Long> {
}
