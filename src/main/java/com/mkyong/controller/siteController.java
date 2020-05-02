package com.mkyong.controller;

import com.mkyong.entity.Site;
import com.mkyong.entity.Topo;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sites")
public class siteController {

    @Autowired
    SiteService siteService;

    @RequestMapping()
    public String getAllSites(Model model) {

        List<Site> listSites = siteService.getAllSites();
        model.addAttribute("sites", listSites);

        return "site/list-sites"; //view
    }

    @RequestMapping(path="/details/{id}")
    public String getDetailsSite(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

        Site siteTrouve=siteService.getSiteById(id);

        model.addAttribute("site", siteTrouve);

        return "site/details-site"; //view
    }

}
