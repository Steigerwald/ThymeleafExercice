package com.mkyong.controller;

import com.mkyong.entity.Image;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/admin/images")
public class imageController {

    @Autowired
    ImageService imageEntityService;


    @RequestMapping(method = RequestMethod.GET)
    public String getAllImages(Model model)  {


        List<Image> images = imageEntityService.getAllImages();
        model.addAttribute("images", images);

        return "image/list-images"; //view
    }

/*
    @RequestMapping(path = "/uneImage",method = RequestMethod.GET)
    public String printOneImageById(Model model, HttpServletRequest request, HttpServletResponse response) throws RecordNotFoundException, IOException {

        Long id= Long.valueOf(1);
        final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
        Image image1=imageEntityService.getImageById(id);

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(image1.getMimeType());
        //response.setHeader("Content-Length", String.valueOf(image1.Length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + image1.getNomImage() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            FileStream fileStream =new FileStream();
            FileInputStream fis=new FileInputStream(fileStream.writeByte(image1.getImage()));

            input = new BufferedInputStream(image1.getImage,0,image1.getImage().length), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            output.close();
            input.close();
        }

        model.addAttribute("image1", response.getHeader("Content-Disposition"));
        return "image/UneImage";
    }
*/
    @RequestMapping(path = "/addImage",method = RequestMethod.GET)
    public String addImageById(Model model) {

        model.addAttribute("image", new Image());
        return "image/add-images";
    }

    @RequestMapping(path = "/stockerImage", method = RequestMethod.POST)
    public String addImage(Image image) {

        imageEntityService.stockerImage(image);
        return "redirect:/admin/images";
    }

    @RequestMapping(path = "/delete/{id}",method = RequestMethod.GET)
    public String deleteEntityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        imageEntityService.deleteImageById(id);
        return "redirect:/admin/images";
    }

}

