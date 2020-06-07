package com.mkyong.controller;

import com.mkyong.entity.Image;
import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ImageService;
import com.mkyong.services.SecteurService;
import com.mkyong.services.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/admin/images")
public class imageController {

    Logger logger = (Logger) LoggerFactory.getLogger(imageController.class);

    @Autowired
    private ImageService imageEntityService;

    @Autowired
    private SecteurService secteurService;

    @Autowired
    private SiteService siteService;

    @RequestMapping(method = RequestMethod.GET)
    public String getAllImages(Model model)  {

        List<Image> images = imageEntityService.getAllImages();
        model.addAttribute("images", images);

        return "image/list-images"; //view
    }

    @RequestMapping(path = "/addImage",method = RequestMethod.GET)
    public String addImageById(Model model) {

        List<Secteur> secteurs = secteurService.getAllSecteurs();
        model.addAttribute("secteurs", secteurs);
        List<Site> sites = siteService.getAllSites();
        model.addAttribute("sites", sites);
        model.addAttribute("image", new Image());
        return "image/add-images";
    }

    @RequestMapping(path = "/stockerImage", method = RequestMethod.POST)
    public String addImage(@RequestParam("file") MultipartFile fileImage, Image image) throws IOException {

        logger.info(" le nombre octet de image est: "+image.getImage());
        logger.info(" le type de l'image est: "+image.getMimeType());
        logger.info(" le nom de l'image est: "+image.getNomImage());
        logger.info(" la valeur d'octet du fichier est: "+fileImage.getBytes());
        logger.info(" le type du fichier est: "+fileImage.getContentType());
        logger.info(" la taille du fichier est: "+fileImage.getSize());
        logger.info(" Nom du paramètre du fichier est: "+fileImage.getName());
        logger.info(" Nom original du fichier est: "+fileImage.getOriginalFilename());
        image.setImage(fileImage.getBytes());
        image.setTaille(fileImage.getSize()/1000);
        String encodedString = Base64.getEncoder().encodeToString(fileImage.getBytes());
        logger.info(" taille de l'image après l'enregistrement "+image.getTaille());
        //BASE64Decoder decoder = new BASE64Decoder();
        //byte[] imageByte = decoder.decodeBuffer(encodedString);
        imageEntityService.stockerImage(image);
        return "redirect:/admin/images";
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.POST)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        imageEntityService.deleteImageById(id);
        return "redirect:/admin/images";
    }

}

