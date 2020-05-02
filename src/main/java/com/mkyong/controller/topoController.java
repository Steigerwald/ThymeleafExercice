package com.mkyong.controller;

import com.mkyong.entity.Image;
import com.mkyong.entity.Topo;
import com.mkyong.services.ImageService;
import com.mkyong.services.TopoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;




@Controller
@RequestMapping("/topos")
public class topoController {


    @Autowired
    TopoService topoService;


    @RequestMapping()
    public String getAllTopos(Model model) {

        List<Topo> listTopos = topoService.getAllTopos();
        model.addAttribute("topos", listTopos);

        return "topo/list-topos"; //view
    }



}
