package com.mkyong.repository;

import java.util.Optional;
import com.mkyong.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository
        extends JpaRepository<User, Integer> {

    Optional<User> findByMailUser(String mailUser);

}
