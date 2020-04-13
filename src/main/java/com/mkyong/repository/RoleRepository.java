package com.mkyong.repository;

import com.mkyong.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository
        extends JpaRepository<Role, Integer> {
    //Role findByNomRole(String nomRole);

}
