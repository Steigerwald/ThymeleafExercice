package com.mkyong.controller;

import com.mkyong.entity.ImageEntity;
import com.mkyong.services.ImageEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/images")
public class imageController {

    @Autowired
    ImageEntityService imageEntityService;


    @GetMapping()
    public String getAllImages( Model model){


        List<ImageEntity> listC = imageEntityService.getAllImages();

        model.addAttribute("images", listC);
        return "list-images"; //view
    }
    @RequestMapping(path = "/addImage")
    public String addImageById(Model model)
    {
        model.addAttribute("image", new ImageEntity());

        return "add-images";
    }

    @RequestMapping(path = "/stockerImage", method = RequestMethod.POST)
    public String addImage(ImageEntity image)
    {
        imageEntityService.stockerImage(image);

        return "redirect:/images";
    }


}

