package com.mkyong.services;

import com.mkyong.entity.Image;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {


    @Autowired
    private ImageRepository repositoryImage;

    Logger logger = (Logger) LoggerFactory.getLogger(ImageService.class);


    public List<Image> getAllImages() {
        List<Image> listeImages = (List<Image>) repositoryImage.findAll();
        if (listeImages.size() > 0) {
            logger.info(" retour liste listeImages si taille de rde la liste >0 ");
            return listeImages;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result de getAllImages ");
            return new ArrayList<Image>();
        }
    }

    public Image getImageById(Long id) throws RecordNotFoundException {
        Optional<Image> image = repositoryImage.findById(id);
        if (image.isPresent()) {
            logger.info(" retour de l'image car elle est présente ");
            logger.info(" retour de la valeur du BLOB"+image.get().getImage());
            return image.get();
        } else {
            throw new RecordNotFoundException("Pas d'image enregistrée avec cet Id");
        }
    }

    public void deleteImageById(Long id) throws RecordNotFoundException {
        Optional<Image> image = repositoryImage.findById(id);
        if (image.isPresent()) {
            logger.info(" l'entité image a été trouvée et est effacée");
            repositoryImage.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas d'image enregistrée avec cet Id");
        }
    }

    public Image stockerImage(Image entity) {
        if (entity.getId() == null) {
            Image newEntity = new Image();
            newEntity.setNomImage(entity.getNomImage());
            newEntity.setMimeType(entity.getMimeType());
            newEntity.setTaille(entity.getTaille());
            newEntity.setImage(entity.getImage());
            entity = repositoryImage.save(newEntity);
            logger.info(" retour de l'entité de stockerImage car cette image n'existe pas");
            return entity;
        } else {
            Image newEntity = new Image();
            newEntity.setId(entity.getId());
            newEntity.setNomImage(entity.getNomImage());
            newEntity.setMimeType(entity.getMimeType());
            newEntity.setTaille(entity.getTaille());
            newEntity.setImage(entity.getImage());
            newEntity = repositoryImage.save(newEntity);
            logger.info(" retour de la nouvelle entité Image de stockerImage qui a été sauvegardée et l'image est existante");
            return newEntity;
        }
    }

}