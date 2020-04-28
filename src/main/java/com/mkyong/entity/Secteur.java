package com.mkyong.entity;

import javax.persistence.*;


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

    // Constructeur

    public Secteur() {
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

    // Setters


    public void setIdSecteur(Long idSecteur) { this.idSecteur = idSecteur;
    }

    public void setNomSecteur(String nomSecteur) { this.nomSecteur = nomSecteur;
    }

    public void setDescriptifSecteur(String descriptifSecteur) { this.descriptifSecteur = descriptifSecteur;
    }

    public void setHauteur(String hauteur) { this.hauteur = hauteur;
    }
}
