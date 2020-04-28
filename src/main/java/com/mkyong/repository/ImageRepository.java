package com.mkyong.repository;

import com.mkyong.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository
        extends JpaRepository<Image, Long> {
}