package com.mkyong.services;

import com.mkyong.entity.Commentaire;
import com.mkyong.entity.User;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.CommentaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(CommentaireService.class);


    public List<Commentaire> getAllCommentaires()
    {
        List<Commentaire> result1 =(List<Commentaire>) commentaireRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result1 ");
            return new ArrayList<Commentaire>();
        }
    }

    public Commentaire getCommentaireById(Long id) throws RecordNotFoundException
    {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if(commentaire.isPresent()) {
            logger.info(" retour du commentaire car il est présent ");
            return commentaire.get();
        } else {
            throw new RecordNotFoundException("Pas de commentaire enregistré avec cet Id");
        }
    }


    public Commentaire modifyCommentaire(Commentaire entity)
    {
        Commentaire newCommentaire = new Commentaire();
        newCommentaire.setIdCommentaire(entity.getIdCommentaire());
        newCommentaire.setContenu(entity.getContenu());
        newCommentaire.setDateCommentaire(entity.getDateCommentaire());
        newCommentaire.setSite(entity.getSite());
        newCommentaire.setUser(entity.getUser());
        newCommentaire = commentaireRepository.save(newCommentaire);
        logger.info(" retour de la nouvelle entité newCommentaire de modifyCommentaire qui a été sauvegardée et le commentaire est existant");
        return newCommentaire;
    }

    public Commentaire createCommentaire(Commentaire entity, User currentUser)
    {
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



    public void deleteCommentaireById(Long id) throws RecordNotFoundException
    {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if(commentaire.isPresent()) {
            commentaireRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de commentaire enregistré avec cet Id");
        }
    }





}
