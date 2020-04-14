package com.mkyong.services;

import com.mkyong.entity.ImageEntity;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ImageEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageEntityService {


    @Autowired
    ImageEntityRepository repositoryImage;

    Logger logger = (Logger) LoggerFactory.getLogger(ImageEntityService.class);


    public List<ImageEntity> getAllImages() {
        List<ImageEntity> listeImages = (List<ImageEntity>) repositoryImage.findAll();

        Source: https://prograide.com/pregunta/25305/comment-telecharger-et-stocker-une-image-avec-google-app-engine-java
        if (listeImages.size() > 0) {
            logger.info(" retour liste listeImages si taille de rde la liste >0 ");
            return listeImages;
        } else {
            logger.info(" retour nouvelle liste  car pas d'élément dans la liste result de getAllImages ");
            return new ArrayList<ImageEntity>();
        }


    }
    /*
public File transformerImageBlob (Blob image, HttpServletResponse res){

        res.setContentType("image/jpeg");
        File f = res.getOutputStream().write(image.getBytes());

        return f;
}*/




    public ImageEntity getImageById(Long id) throws RecordNotFoundException {
        Optional<ImageEntity> image = repositoryImage.findById(id);

        if (image.isPresent()) {
            logger.info(" retour de l'image car elle est présente ");
            return image.get();
        } else {
            throw new RecordNotFoundException("No picture record exist for given id");
        }
    }

    public void deleteImageById(Long id) throws RecordNotFoundException {
        Optional<ImageEntity> image = repositoryImage.findById(id);

        if (image.isPresent()) {
            logger.info(" l'entité image a été trouvée et est effacée");
            repositoryImage.deleteById(id);
        } else {
            throw new RecordNotFoundException("No footballer record exist for given id");
        }
    }

    public ImageEntity stockerImage(ImageEntity entity) {
        if (entity.getId() == null) {
            entity = repositoryImage.save(entity);

            logger.info(" retour de l'entité de stockerImage car l'Id n'existe pas");
            return entity;
        } else {
            Optional<ImageEntity> image = repositoryImage.findById(entity.getId());

            if (image.isPresent()) {
                ImageEntity newEntity = image.get();
                newEntity.setNomImage(entity.getNomImage());
                newEntity.setMimeType(entity.getMimeType());
                newEntity.setImage(entity.getImage());

                newEntity = repositoryImage.save(newEntity);

                logger.info(" retour de la nouvelle entité Image de stockerImage qui a été sauvegardée et l'image est existante");
                return newEntity;

            } else {
                entity = repositoryImage.save(entity);
                logger.info(" retour de l'entité Image de stockerImage qui a été sauvegardée car l'image n'est pas existante");
                return entity;
            }
        }
    }
    /**
     * Retourne l'image coorespondant à id
     //* @param id identifiant de l'image
     * @return bufferedImage
     */
    /*public BufferedImage getImageById(int id) {
        String sql= "select img from image where idImg = ?";
        BufferedImage buffimg = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet result = stmt.executeQuery();
            if(result.next()){
                InputStream img = result.getBinaryStream(1);
                buffimg= ImageIO.read(img);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return buffimg;
    }*/


    //public ImageEntity transformerImage(Image imageBlob) {
        //BufferedImage image = ImageIO.read(imageBlob.getBinaryStream());
       // InputStream img = imageBlob.getBinaryStream();
       // buffimg = ImageIO.read(img);
       // return image;
  //  }
}
