package com.mkyong.repository;

import com.mkyong.entity.Role;
import com.mkyong.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository
        extends JpaRepository<Role, Integer> {
    //Role findByNomRole(String nomRole);
    Optional<Role> findByIdRole(Integer idRole);


}
