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
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
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
            return image.get();
        } else {
            throw new RecordNotFoundException("Pas d'image enregistrée avec cet Id");
        }
    }

    public void deleteImageById(Long id) throws RecordNotFoundException {
        Optional<Image> image = repositoryImage.findById(id);
        if (image.isPresent()) {
            logger.info(" l'entité image a été trouvée et est effacée");
            Image ImageTrouve =image.get();
            if (ImageTrouve.getSite()!=null){
                ImageTrouve.getSite().setImage(null);
            }
            if (ImageTrouve.getTopo()!=null){
                ImageTrouve.getTopo().setImage(null);
            }
            repositoryImage.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas d'image enregistrée avec cet Id");
        }
    }

    public Image stockerImage(Image entity, User user) throws RecordNotFoundException {
        if (entity.getId() == null) {
            Image newImage =new Image();
            newImage.setNomImage(entity.getNomImage());
            newImage.setMimeType(entity.getMimeType());
            newImage.setTaille(entity.getTaille());
            newImage.setImage(entity.getImage());
            logger.info(" valeur de site de entity pour vérifier Image et l'affectation dans la table id_site_id "+entity.getSite());
            newImage.setSite(entity.getSite());
            newImage.setTopo(entity.getTopo());
            entity = repositoryImage.save(newImage);
            logger.info(" retour de l'entité de stockerImage car cette image n'existe pas et donc elle a été crée ");
            return entity;
        } else {
            Image imageAModifier =getImageById(entity.getId());
            Image result;
            if (imageAModifier!=null) {
                imageAModifier.setNomImage(entity.getNomImage());
                imageAModifier.setMimeType(entity.getMimeType());
                imageAModifier.setTaille(entity.getTaille());
                imageAModifier.setImage(entity.getImage());
                logger.info(" valeur de site de imageAModifier pour vérifier Image et l'affectation dans la table id_site_id " + imageAModifier.getSite());
                imageAModifier.setSite(entity.getSite());
                imageAModifier.setTopo(entity.getTopo());
                result = repositoryImage.save(imageAModifier);
            }else{
                result=entity;
            }
            logger.info(" retour de la nouvelle entité Image de stockerImage qui n'a pas été sauvegardée car l'image est existante");
            return result;
        }
    }

    public Image recupererImageFile(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setImage(file.getBytes());
        image.setTaille(file.getSize()/1000);
        String [] Type = file.getContentType().split("/");
        logger.info(" valeur de Type[1]= "+Type[1]);
        image.setMimeType(Type[1]);
        String [] Nom= (file.getOriginalFilename()).split("\\.");
        logger.info(" valeur de Nom[0]= "+Nom[0]);
        image.setNomImage(Nom[0]);
        return image;
    }
}