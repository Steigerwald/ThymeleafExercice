package com.mkyong.entity;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name="TBL_SECTEUR")
public class Secteur {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idSecteur;

    @Column(name="NOM_SECTEUR")
    private String nomSecteur;

    @Column(name="DESCRIPTIF_SECTEUR")
    private String descriptifSecteur;

    @Column(name="HAUTEUR")
    private String hauteur;

    @ManyToOne
    private Site site;

    @OneToMany (mappedBy = "secteur")
    private Collection<Image> images;

    @OneToMany (mappedBy = "secteur")
    private Collection<Voie> voies;


    // Constructeur

    public Secteur() {
    }

    public String toStringVoies(Voie voies) {
        return " " + voies;
    }
// Getters

    public Long getIdSecteur() { return idSecteur;
    }

    public String getNomSecteur() { return nomSecteur;
    }

    public String getDescriptifSecteur() { return descriptifSecteur;
    }

    public String getHauteur() { return hauteur;
    }

    public Site getSite() { return site;
    }

    public Collection<Image> getImages() { return images;
    }

    public Collection<Voie> getVoies() { return voies;
    }



    // Setters


    public void setIdSecteur(Long idSecteur) { this.idSecteur = idSecteur;
    }

    public void setNomSecteur(String nomSecteur) { this.nomSecteur = nomSecteur;
    }

    public void setDescriptifSecteur(String descriptifSecteur) { this.descriptifSecteur = descriptifSecteur;
    }

    public void setHauteur(String hauteur) { this.hauteur = hauteur;
    }

    public void setSite(Site site) { this.site = site;
    }

    public void setImages(Collection<Image> images) { this.images = images;
    }

    public void setVoies(Collection<Voie> voies) { this.voies = voies;
    }


}
