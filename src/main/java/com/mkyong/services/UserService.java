package com.mkyong.services;

import com.mkyong.entity.Role;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.RoleRepository;
import com.mkyong.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Methode pour avoir tous les users enregistrés dans la base de données
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

    //Methode pour obtenir un User par par une adresse mail
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

    //Methode pour voir un user par Id
    public User getUserById(Long id) throws RecordNotFoundException
    {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            logger.info(" retour du user car il est présent ");
            return user.get();
        } else {
            throw new RecordNotFoundException("No footballeur record exist for given id");
        }
    }

    //Methode pour sauvegarder dans une base de données un User
    public void saveUser (User user) {

            User newUser = new User();
        logger.info(newUser.getNomUser());
            newUser.setMailUser(user.getMailUser());
            newUser.setNomUser(user.getNomUser());
            newUser.setPrenomUser(user.getPrenomUser());
        logger.info(" récupération des données nom, prenom, mail de saveUser");
            newUser.setMotDePasseUser(passwordEncoder.encode(user.getMotDePasseUser()));
            newUser.setRole(user.getRole());
            userRepository.save(newUser);
            logger.info(" enregistrement du newUser avec saveUser");
    }


    //Methode pour modifier un User
    public void updateUser (User user){
        User newUser = new User();
        newUser.setMailUser(user.getMailUser());
        newUser.setNomUser(user.getNomUser());
        newUser.setPrenomUser(user.getPrenomUser());
        newUser.setMotDePasseUser(passwordEncoder.encode(user.getMotDePasseUser()));
        newUser.setRole((user.getRole()));
        userRepository.save(newUser);
        logger.info(" la nouvelle entité user de updateUser a été sauvegardée et le user est existant");
    }

    //Methode pour effacer un User par Id
    public void deleteUserById(Long id) throws RecordNotFoundException
    {
    Optional<User> userAEffacer = userRepository.findByIdUser(id);

        if(userAEffacer.isPresent())
        { logger.info(" l'entité user à effacer a été trouvée et est effacée");
        userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user record exist for given id and to cancel it");
        }
    }


    //Methode pour voir un role par Id
    public Role getRoleById(Integer id) throws RecordNotFoundException
    {
        Optional<Role> role = roleRepository.findByIdRole(id);

        if(role.isPresent()) {
            logger.info(" retour du role car il est présent ");
            return role.get();
        } else {
            throw new RecordNotFoundException("No role record exist for given id");
        }
    }



}
