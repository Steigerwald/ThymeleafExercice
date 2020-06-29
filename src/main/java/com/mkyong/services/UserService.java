package com.mkyong.services;

import com.mkyong.entity.*;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TopoService topoService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ReservationTopoService reservationTopoService;

    @Autowired
    private CommentaireService commentaireService;



    Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);

    /*Methode pour avoir tous les users enregistrés dans la base de données*/
    public List<User> getAllUsers() {
        List<User> resultUser = (List<User>) userRepository.findAll();
        if(resultUser.size() > 0) {
            logger.info(" retour liste result car taille de resultUser >0 ");
            return resultUser;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste resultUser de getAllUsers ");
            return new ArrayList<User>();
        }
    }

    /*Methode pour obtenir un User par par une adresse mail*/
    public User getUserByMail(String mail) {
        Optional<User> user = userRepository.findByMailUser(mail);
        if(user.isPresent()) {
            logger.info(" retour du user trouvé grâce au mail car il est présent ");
            return user.get();
        } else {
            logger.info("No user record exist for given mail");
            return null ;
        }
    }

    /*Methode pour voir un user par Id*/
    public User getUserById(Long id) throws RecordNotFoundException {
        Optional<User> user = userRepository.findByIdUser(id);
        if(user.isPresent()) {
            logger.info(" retour du user car il est présent ");
            return user.get();
        } else {
            throw new RecordNotFoundException("No footballeur record exist for given id");
        }
    }

    /*Methode pour sauvegarder dans une base de données un User*/
    public void saveUser (User user) {
        user.setMotDePasseUser(passwordEncoder.encode(user.getMotDePasseUser()));
        logger.info(" récupération du mot de passe et l'encode pour l'enregistrer");
        userRepository.save(user);
    }

    /*Methode pour modifier un User*/
    public void updateUser (User user) throws RecordNotFoundException {
        Optional<User> userAModifier = userRepository.findByIdUser(user.getIdUser());
        if(userAModifier.isPresent())
        { logger.info(" l'entité user à modifier a été trouvée et modifiée");
        User newUser=userAModifier.get();
        newUser.setNomUser(user.getNomUser());
        newUser.setPrenomUser(user.getPrenomUser());
        newUser.setMailUser(user.getMailUser());
        newUser.setRole(user.getRole());
        userRepository.save(newUser);
        } else {
            throw new RecordNotFoundException("No user record exist for given id and to modify it");
        }
    }

    /*Methode pour effacer un User par Id*/
    public void deleteUserById(Long id) throws RecordNotFoundException {
        Optional<User> userAEffacer = userRepository.findByIdUser(id);
        if(userAEffacer.isPresent())
        { logger.info(" l'entité user à effacer a été trouvée et est effacée");
        User userTrouve =userAEffacer.get();

            /*
            // suppression des topos associés au user supprimé
            if (userTrouve.getTopos()!=null) {
                for (int i = 0; i < (userTrouve.getTopos()).size(); i++) {
                    List<Topo> listeTopos = new ArrayList<Topo>();
                    listeTopos.addAll(userTrouve.getTopos());
                    Topo topo = listeTopos.get(i);
                    topoService.deleteTopoById(topo.getIdTopo());
                }
            }
            // suppression des sites associés au user supprimé
            if (userTrouve.getSites()!=null) {
                for (int i = 0; i < (userTrouve.getSites()).size(); i++) {
                    List<Site> listeSites = new ArrayList<Site>();
                    listeSites.addAll(userTrouve.getSites());
                    Site site = listeSites.get(i);
                    siteService.deleteSiteById(site.getIdSite());
                }
            }
            // suppression des reservations associés au user supprimé
            if (userTrouve.getReservations()!=null) {
                for (int i = 0; i < (userTrouve.getReservations()).size(); i++) {
                    List<Reservation> listeReservations = new ArrayList<Reservation>();
                    listeReservations.addAll(userTrouve.getReservations());
                    Reservation reservation = listeReservations.get(i);
                    reservationTopoService.deleteReservationTopoById(reservation.getIdReservation());
                }
            }
            // suppression des commentaires associés au user supprimé
            if(userTrouve.getCommentaires()!=null) {
                for (int i = 0; i < (userTrouve.getCommentaires()).size(); i++) {
                    List<Commentaire> listeCommentaires = new ArrayList<Commentaire>();
                    listeCommentaires.addAll(userTrouve.getCommentaires());
                    Commentaire commentaire = listeCommentaires.get(i);
                    commentaireService.deleteCommentaireById(commentaire.getIdCommentaire());
                }
            }

             */
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user record exist for given id and to cancel it");
        }
    }

    /*Methode pour voir un role par Id*/
    public Role getRoleById(Integer id) throws RecordNotFoundException {
        Optional<Role> role = roleRepository.findByIdRole(id);

        if(role.isPresent()) {
            logger.info(" retour du role car il est présent ");
            return role.get();
        } else {
            throw new RecordNotFoundException("No role record exist for given id");
        }
    }
}
