package com.mkyong.services;

import com.mkyong.entity.Role;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.RoleRepository;
import com.mkyong.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


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
    /*
    public void saveUser(User user) {
        userRepository.save(user);
    }*/

    public void saveUser (User user) {

            User newUser = new User();
        logger.info(newUser.getNomUser());
            newUser.setMailUser(user.getMailUser());
            newUser.setNomUser(user.getNomUser());
            newUser.setPrenomUser(user.getPrenomUser());
        logger.info(" récupération des données nom, prenom, mail de create");
            newUser.setMotDePasseUser(passwordEncoder.encode(user.getMotDePasseUser()));
            List<Role> roles = (List<Role>) roleRepository.findAll();
            newUser.setRoles(roles);
            userRepository.save(newUser);
            logger.info(" enregistrement du newUser avec create");
    }

}
