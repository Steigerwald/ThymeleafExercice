package com.mkyong.services;

import com.mkyong.entity.Footballeur;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ClubRepository;
import com.mkyong.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    public List<User> getAllUsers()
    {
        List<User> resultUser = (List<User>) userRepository.findAll();

        if(resultUser.size() > 0) {
            logger.info(" retour liste result car taille de resultUser >0 ");
            return resultUser;
        } else {

            logger.info(" retour nouvelle liste  car pas d'élément dans la liste resultUser de getAllUsers ");
            return new ArrayList<User>();
        }
    }

    public User getUserByMail(String mail) throws RecordNotFoundException
    {
        Optional<User> user = userRepository.findByMailUser(mail);

        if(user.isPresent()) {
            logger.info(" retour du user trouvé grâce au mail car il est présent ");
            return user.get();
        } else {
            throw new RecordNotFoundException("No user record exist for given mail");
        }
    }


}
