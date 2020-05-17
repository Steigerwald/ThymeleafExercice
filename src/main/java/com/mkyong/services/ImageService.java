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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {


    @Autowired
    ImageRepository repositoryImage;

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

    public BufferedImage getBufferedImage(Image image) throws IOException {
        ImageInputStream stream = ImageIO.createImageInputStream(image);
        BufferedImage bufferedImage = ImageIO.read(stream);
        stream.close();
        return bufferedImage;
    }


    public Image getImageById(Long id) throws RecordNotFoundException {

        Optional<Image> image = repositoryImage.findById(id);

        if (image.isPresent()) {
            logger.info(" retour de l'image car elle est présente ");
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
            entity = repositoryImage.save(entity);

            logger.info(" retour de l'entité de stockerImage car l'Id n'existe pas");
            return entity;
        } else {
                Image newEntity = new Image();
                newEntity.setNomImage(entity.getNomImage());
                newEntity.setMimeType(entity.getMimeType());
                newEntity.setImage(entity.getImage());

                newEntity = repositoryImage.save(newEntity);

                logger.info(" retour de la nouvelle entité Image de stockerImage qui a été sauvegardée et l'image est existante");
                return newEntity;

        }
    }
}