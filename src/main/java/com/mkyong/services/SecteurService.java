package com.mkyong.services;

import com.mkyong.entity.Secteur;
import com.mkyong.entity.Site;
import com.mkyong.entity.Voie;
import com.mkyong.exception.RecordNotFoundException;
import com.mkyong.repository.SecteurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SecteurService {

    @Autowired
    private SecteurRepository secteurRepository;

    @Autowired
    private VoieService voieService;

    @Autowired
    private SiteService siteService;

    Logger logger = (Logger) LoggerFactory.getLogger(SecteurService.class);

    /*Methode pour obtenir tous les secteurs de la base de données*/
    public List<Secteur> getAllSecteurs() {
        List<Secteur> result1 =(List<Secteur>) secteurRepository.findAll();
        if(result1.size() > 0) {
            logger.info(" retour liste result1 si taille de result1 >0 ");
            return result1;
        } else {
            logger.info(" retour nouvelle liste car pas d'élément dans la liste result1 ");
            return new ArrayList<Secteur>();
        }
    }

    /*Methode pour obtenir un secteur par id de la base de données*/
    public Secteur getSecteurById(Long id) throws RecordNotFoundException {
        Optional<Secteur> secteur = secteurRepository.findById(id);
        if(secteur.isPresent()) {
            logger.info(" retour du secteur car il est présent ");
            return secteur.get();
        } else {
            throw new RecordNotFoundException("Pas de secteur enregistré avec cet Id");
        }
    }

    /*Methode pour creer ou modifier un secteur de la base de données*/
    public Secteur createOrUpdateSecteur(Secteur entity) throws RecordNotFoundException {
        if(entity.getIdSecteur()  == null) {
            Secteur newSecteur= new Secteur();
            newSecteur.setNomSecteur(entity.getNomSecteur());
            newSecteur.setDescriptifSecteur(entity.getDescriptifSecteur());
            newSecteur.setHauteur(entity.getHauteur());
            newSecteur.setSite(entity.getSite());
            newSecteur.setVoies(entity.getVoies());
            newSecteur = secteurRepository.save(newSecteur);

            // enregistrement du secteur dans chaque voie concernée
            if (newSecteur.getVoies()!=null) {
                List<Voie> listeVoies = new ArrayList<Voie>();
                if (entity.getVoies() != null){
                    listeVoies.addAll(entity.getVoies());
                    for (int i = 0; i < listeVoies.size(); i++) {
                        Voie voie = listeVoies.get(i);
                        voie=voieService.getVoieById(voie.getIdVoie());
                        voie.setSecteur(newSecteur);
                        voieService.createOrUpdateVoie(voie);
                    }
                }
            }

            if (newSecteur.getSite()!=null) {
                //rajout du secteur dans la liste des secteurs du site concerné
                Collection<Secteur> listeSecteurs = newSecteur.getSite().getSecteurs();
                listeSecteurs.add(newSecteur);
                Site siteAModifier=siteService.getSiteById(newSecteur.getSite().getIdSite());
                siteAModifier.setSecteurs(listeSecteurs);
                siteService.UpdateSite(siteAModifier);
            }


            logger.info(" retour de l'entité qui a été créée de createOrUpdateSecteur car l'Id n'existe pas");
            return newSecteur;
        } else {
            Secteur secteurAModifier = getSecteurById(entity.getIdSecteur());
            if(secteurAModifier!=null) {
                logger.info(" l'entité secteur à modifier a été trouvée et modifiée");
                secteurAModifier.setNomSecteur(entity.getNomSecteur());
                secteurAModifier.setDescriptifSecteur(entity.getDescriptifSecteur());
                secteurAModifier.setHauteur(entity.getHauteur());
                secteurAModifier.setSite(entity.getSite());
                secteurAModifier.setVoies(entity.getVoies());
                entity = secteurRepository.save(secteurAModifier);

                /*
                // enregistrement du secteur dans chaque voie concernée
                if (secteurAModifier.getVoies()!=null) {
                    List<Voie> listeVoies = new ArrayList<Voie>();
                    if (entity.getVoies() != null){
                        listeVoies.addAll(entity.getVoies());
                        for (int i = 0; i < listeVoies.size(); i++) {
                            Voie voie = listeVoies.get(i);
                            voie.setSecteur(secteurAModifier);
                            voieService.createOrUpdateVoie(voie);
                        }
                    }
                }

                // enregistrement du secteur dans chaque site concerné
                if (secteurAModifier.getSite()==null){
                    logger.info(" le secteur n'est plus utilisé pour ce site");
                }else {
                    if (!(entity.getSite().equals(secteurAModifier.getSite()))) {
                        if (secteurAModifier.getSite() != null) {
                            //rajout du secteur dans la liste des secteurs du site concerné
                            Collection<Secteur> listeSecteurs = secteurAModifier.getSite().getSecteurs();
                            listeSecteurs.add(secteurAModifier);
                            secteurAModifier.getSite().setSecteurs(listeSecteurs);
                            siteService.UpdateSite(secteurAModifier.getSite());
                        }
                    }
                }
                */

                logger.info(" retour de la nouvelle entité secteur de createOrUpdateSite qui a été sauvegardée et le secteur est existant");
                return secteurAModifier;
            } else {
                throw new RecordNotFoundException("No user record exist for given id and to modify it");
            }
        }
    }

    /*Methode pour effacer un secteur de la base de données*/
    public void deleteSecteurById(Long id) throws RecordNotFoundException {
        Secteur secteurAEffacer =getSecteurById(id);

        if(secteurAEffacer!=null) {
            List<Voie> listeVoies = new ArrayList<Voie>();
            if (secteurAEffacer.getVoies()!=null) {
                    listeVoies.addAll(secteurAEffacer.getVoies());
                    for (int i = 0; i < listeVoies.size(); i++) {
                        Voie voie = listeVoies.get(i);
                        voie.setSecteur(null);
                        voieService.createOrUpdateVoie(voie);
                    }
            }
            secteurRepository.deleteById(id);

        } else {
            throw new RecordNotFoundException("Pas de secteur enregistré avec cet Id");
        }
    }

}
