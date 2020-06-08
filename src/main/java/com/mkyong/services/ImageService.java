package com.mkyong.services;

import com.mkyong.entity.Image;
import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ImageRepository;
import com.mkyong.repository.SecteurRepository;
import com.mkyong.repository.SiteRepository;
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
import java.util.*;

@Service
public class ImageService {


    @Autowired
    private ImageRepository repositoryImage;

    @Autowired
    private SecteurService secteurService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private SecteurRepository secteurRepository;

    @Autowired
    private SiteRepository siteRepository;

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

    public Image stockerImage(Image entity) throws RecordNotFoundException {
        if (entity.getId() == null) {
            Image newEntity = new Image();
            newEntity.setNomImage(entity.getNomImage());
            newEntity.setMimeType(entity.getMimeType());
            newEntity.setTaille(entity.getTaille());
            newEntity.setImage(entity.getImage());
            newEntity.setSecteur(entity.getSecteur());
            newEntity.setSite(entity.getSite());
            entity = repositoryImage.save(newEntity);
            if ((newEntity.getSecteur()!=null)&&(newEntity!=null)){
                Secteur secteurConcerne =newEntity.getSecteur();
                Collection listeSecteurImages = secteurConcerne.getImages();
                listeSecteurImages.add(newEntity);
                secteurConcerne.setImages(listeSecteurImages);
                secteurRepository.save(secteurConcerne);
            }
            if ((newEntity.getSite()!=null)&&(newEntity!=null)){

                Site siteConcerne =newEntity.getSite();
                Collection listeSiteImages = siteConcerne.getImages();
                listeSiteImages.add(newEntity);
                siteConcerne.setImages(listeSiteImages);
                siteRepository.save(siteConcerne);
            }
            logger.info(" retour de l'entité de stockerImage car cette image n'existe pas");
            return entity;
        } else {
            Image newEntity = new Image();
            newEntity.setId(entity.getId());
            newEntity.setNomImage(entity.getNomImage());
            newEntity.setMimeType(entity.getMimeType());
            newEntity.setTaille(entity.getTaille());
            newEntity.setImage(entity.getImage());
            newEntity.setSecteur(entity.getSecteur());
            newEntity.setSite(entity.getSite());
            newEntity = repositoryImage.save(newEntity);
            if (newEntity.getSecteur()!=null){
                Secteur secteurConcerne =newEntity.getSecteur();
                List<Image>listSecteurImage = null;
                assert listSecteurImage != null;
                listSecteurImage.add(newEntity);
                secteurConcerne.setImages(listSecteurImage);
                secteurService.createOrUpdateSecteur(secteurConcerne);
            }
            if (newEntity.getSite()!=null){
                Site siteConcerne =newEntity.getSite();
                List<Image>listSiteImage = null;
                assert listSiteImage != null;
                listSiteImage.add(newEntity);
                siteConcerne.setImages(listSiteImage);
                siteService.createOrUpdateSite(siteConcerne);
            }
            logger.info(" retour de la nouvelle entité Image de stockerImage qui a été sauvegardée et l'image est existante");
            return newEntity;
        }
    }

}