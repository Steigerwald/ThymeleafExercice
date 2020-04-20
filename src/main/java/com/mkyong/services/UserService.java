package com.mkyong.services;

import com.mkyong.entity.Footballeur;
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

    public User getUserByMail(String mail)
    {
        Optional<User> user = userRepository.findByMailUser(mail);

        if(user.isPresent()) {
            logger.info(" retour du user trouvé grâce au mail car il est présent ");
            return user.get();
        } else {
            logger.info("No user record exist for given mail");
            return null ;
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
        logger.info(" récupération des données nom, prenom, mail de saveUser");
            newUser.setMotDePasseUser(passwordEncoder.encode(user.getMotDePasseUser()));
            List<Role> roles = (List<Role>) roleRepository.findAll();
            newUser.setRoles(roles);
            userRepository.save(newUser);
            logger.info(" enregistrement du newUser avec saveUser");
    }

    public User updateUser(User entity) {

            Optional<User> user = userRepository.findById(entity.getIdUser());

            if(user.isPresent())
            {
                User newEntity = user.get();
                newEntity.setNomUser(entity.getNomUser());
                newEntity.setPrenomUser(entity.getPrenomUser());
                newEntity.setMotDePasseUser(entity.getMotDePasseUser());
                newEntity.setRoles(entity.getRoles());

                newEntity = userRepository.save(newEntity);

                logger.info(" retour de la nouvelle entité user de updateUser qui a été sauvegardée et le user est existant");
                return newEntity;

            } else {
                entity = userRepository.save(entity);
                logger.info(" retour de l'entité user de updateUser qui a été sauvegardée car le user n'est pas existant");
                return entity;
            }

    }

    public void deleteUserById(Long id) throws RecordNotFoundException
    {
    Optional<User> userAEffacer = userRepository.findByIdUser(id);

        if(userAEffacer.isPresent())
        {
            logger.info(" l'entité user à effacer a été trouvée et est effacée");
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user record exist for given id and to cancel it");
        }
    }


}
