package com.mkyong.controller;

import com.mkyong.entity.Image;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/images")
public class imageController {

    @Autowired
    ImageService imageEntityService;


    @GetMapping()
    public String getAllImages( Model model)  {

        List<Image> images = imageEntityService.getAllImages();
        model.addAttribute("images", images);

        return "image/list-images"; //view
    }

    @RequestMapping(path = "/addImage")
    public String addImageById(Model model) {

        model.addAttribute("image", new Image());
        return "image/add-images";
    }

    @RequestMapping(path = "/stockerImage", method = RequestMethod.POST)
    public String addImage(Image image) {

        imageEntityService.stockerImage(image);
        return "redirect:/images";
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteImageEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
        imageEntityService.deleteImageById(id);
        return "redirect:/footballeurs";
    }


}

