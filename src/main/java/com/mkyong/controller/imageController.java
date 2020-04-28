package com.mkyong.controller;

import com.mkyong.entity.ImageEntity;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ImageEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/images")
public class imageController {

    @Autowired
    ImageEntityService imageEntityService;


    @GetMapping()
    public String getAllImages( Model model)  {

        List<ImageEntity> images = imageEntityService.getAllImages();
        model.addAttribute("images", images);

        return "image/list-images"; //view
    }

    @RequestMapping(path = "/addImage")
    public String addImageById(Model model) {

        model.addAttribute("image", new ImageEntity());
        return "image/add-images";
    }

    @RequestMapping(path = "/stockerImage", method = RequestMethod.POST)
    public String addImage(ImageEntity image) {

        imageEntityService.stockerImage(image);
        return "redirect:/images";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteImageEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        imageEntityService.deleteImageById(id);
        return "redirect:/footballeurs";
    }


}

