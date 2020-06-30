package com.mkyong.services;

import com.mkyong.entity.Commentaire;
import com.mkyong.entity.Secteur;
import com.mkyong.entity.Topo;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.CommentaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SiteService siteService;

    Logger logger = (Logger) LoggerFactory.getLogger(CommentaireService.class);

    /* méthode pour avoir la liste de tous les commentaires  de la base de données */
    public List<Commentaire> getAllCommentaires() {
        List<Commentaire> result1 =(List<Commentaire>) commentaireRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result1 ");
            return new ArrayList<Commentaire>();
        }
    }

    /* méthode pour obtenir un commentaire de la base de données en utilisant l'Id */
    public Commentaire getCommentaireById(Long id) throws RecordNotFoundException {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if(commentaire.isPresent()) {
            logger.info(" retour du commentaire car il est présent ");
            return commentaire.get();
        } else {
            throw new RecordNotFoundException("Pas de commentaire enregistré avec cet Id");
        }
    }

    /* méthode pour modifier les commentaires de la base de données à partir d'un commentaire*/
    public Commentaire modifyCommentaire(Commentaire entity) throws RecordNotFoundException {
        Commentaire commentaireTrouve = getCommentaireById(entity.getIdCommentaire());
        commentaireTrouve.setContenu(entity.getContenu());
        commentaireTrouve.setSite(entity.getSite());
        Commentaire newCommentaire = commentaireRepository.save(commentaireTrouve);
        logger.info(" retour de la nouvelle entité newCommentaire de modifyCommentaire qui a été sauvegardée et le commentaire est existant");
        return newCommentaire;
    }

    /* méthode pour créer le commentaire de la base de données à partir d'un commentaire et d'un utilisateur*/
    public Commentaire createCommentaire(Commentaire entity, User currentUser) throws RecordNotFoundException {
        Date today = new Date();
        Commentaire newCommentaire = new Commentaire();
        newCommentaire.setContenu(entity.getContenu());
        newCommentaire.setDateCommentaire(today);
        newCommentaire.setSite(entity.getSite());
        newCommentaire.setUser(currentUser);
        newCommentaire = commentaireRepository.save(newCommentaire);
        logger.info(" retour de la nouvelle entité commentaire de createOrUpdateCommentaire qui a été sauvegardée et le commentaire est existant");
        return newCommentaire;
    }

    /* méthode pour annuler le commentaire de la base de données à partir d'un id*/
    public void deleteCommentaireById(Long id) throws RecordNotFoundException {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if(commentaire.isPresent()) {
           // Commentaire commentaireTrouve = commentaire.get();
           //User userCommentaire =commentaireTrouve.getUser();
           /* if ((userCommentaire.getCommentaires())!=null) {
                (userCommentaire.getCommentaires()).remove(commentaireTrouve);
                userCommentaire.setCommentaires(userCommentaire.getCommentaires());
                userService.updateUser(userCommentaire);
            }*/
            commentaireRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de commentaire enregistré avec cet Id");
        }
    }
}
