package com.mkyong.services;


import com.mkyong.entity.Topo;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.TopoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopoService {

    @Autowired
    TopoRepository topoRepository;

    Logger logger = (Logger) LoggerFactory.getLogger(TopoService.class);


    public List<Topo> getAllTopos()
    {
        List<Topo> result1 =(List<Topo>) topoRepository.findAll();

        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Topo>();
        }
    }

    public Topo getTopoById(Long id) throws RecordNotFoundException
    {
        Optional<Topo> topo = topoRepository.findById(id);

        if(topo.isPresent()) {
            logger.info(" retour du topo car il est présent ");
            return topo.get();
        } else {
            throw new RecordNotFoundException("Pas de topo enregistré avec cet Id");
        }
    }

    public Topo createOrUpdateTopo(Topo entity)
    {
        //Calendar calendar = Calendar.getInstance();
        //Date today =  calendar.getTime();
        //logger.info(" avec calendar aujourd'hui il est :" + today);
        //System.out.println(today);
        Date today = new Date();
        logger.info(" avec date aujourd'hui il est :"+ today);
        //System.out.println(date);

        if(entity.getIdTopo()  == null)
        {
            entity.setDateParution(today);
            entity = topoRepository.save(entity);

            logger.info(" retour de l'entité de createOrUpdateTopo car l'Id n'existe pas");
            return entity;
        }
        else
        {
            Optional<Topo> topo = topoRepository.findById(entity.getIdTopo());

            if(topo.isPresent())
            {
                //Topo existantTopo = topo.get();

                Topo newTopo = new Topo();
                newTopo.setIdTopo(entity.getIdTopo());
                newTopo.setNomTopo(entity.getNomTopo());
                newTopo.setDescription(entity.getDescription());
                newTopo.setDateParution(today);
                newTopo.setDisponible(entity.getDisponible());
                newTopo.setLocation(entity.getLocation());

                newTopo = topoRepository.save(newTopo);

                logger.info(" retour de la nouvelle entité topo de createOrUpdateTopo qui a été sauvegardée et le topo est existant");
                return newTopo;

            } else {
                entity.setDateParution(today);
                entity = topoRepository.save(entity);
                logger.info(" retour de l'entité topo de createOrUpdateTopo qui a été sauvegardée car le topo n'est pas existant");
                return entity;
            }
        }
    }


    public void deleteTopoById(Long id) throws RecordNotFoundException
    {
        Optional<Topo> topo = topoRepository.findById(id);

        if(topo.isPresent())
        {
            topoRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Pas de topo enregistré avec cet Id");
        }
    }
}
