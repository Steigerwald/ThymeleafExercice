package com.mkyong.services;

import com.mkyong.entity.*;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.ImageRepository;
import com.mkyong.repository.SecteurRepository;
import com.mkyong.repository.SiteRepository;
import com.mkyong.repository.TopoRepository;
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
    private TopoService topoService;

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

    public Image stockerImage(Image entity, User user) throws RecordNotFoundException {
        if (entity.getId() == null) {
            entity = repositoryImage.save(entity);
            if ((entity.getTopo()!=null)&&(entity!=null)){
                Topo topoConcerne =entity.getTopo();
                topoConcerne.setImage(entity);
                topoService.createOrUpdateTopo(topoConcerne, user);
            }
            if ((entity.getSite()!=null)&&(entity!=null)){
                Site siteConcerne =entity.getSite();
                Collection listeSiteImages = siteConcerne.getImages();
                listeSiteImages.add(entity);
                siteConcerne.setImages(listeSiteImages);
                siteService.createOrUpdateSite(siteConcerne, user);
            }
            logger.info(" retour de l'entité de stockerImage car cette image n'existe pas et donc elle ");
            return entity;
        } else {
            logger.info(" retour de la nouvelle entité Image de stockerImage qui n'a pas été sauvegardée car l'image est existante");
            return entity;
        }
    }
}