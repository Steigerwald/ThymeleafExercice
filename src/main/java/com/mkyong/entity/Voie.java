package com.mkyong.entity;

import javax.persistence.*;


@Entity
@Table(name="TBL_VOIE")
public class Voie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idVoie;

    @Column(name="NUMERO_VOIE")
    private String numeroVoie;

    @Column(name="COTATION")
    private String cotation;

    @Column(name="NOMBRE_LONGUEURS")
    private Integer nombreLongueurs;

    @Column(name="NOMBRE_POINTS")
    private Integer nombrePoints;

    @ManyToOne
    private Secteur secteur;


    // Méthodes d'affichage

    @Override
    public String toString() {
        return "" + numeroVoie;
    }


    // Constructor

    public Voie() {
    }


    // Getters

    public Long getIdVoie() { return idVoie;
    }

    public String getNumeroVoie() { return numeroVoie;
    }

    public String getCotation() { return cotation;
    }

    public Integer getNombreLongueurs() { return nombreLongueurs;
    }

    public Integer getNombrePoints() { return nombrePoints;
    }

    public Secteur getSecteur() { return secteur;
    }


    // Setters


    public void setIdVoie(Long idVoie) { this.idVoie = idVoie;
    }

    public void setNumeroVoie(String numeroVoie) { this.numeroVoie = numeroVoie;
    }

    public void setCotation(String cotation) { this.cotation = cotation;
    }

    public void setNombreLongueurs(Integer nombreLongueurs) { this.nombreLongueurs = nombreLongueurs;
    }

    public void setNombrePoints(Integer nombrePoints) { this.nombrePoints = nombrePoints;
    }

    public void setSecteur(Secteur secteur) { this.secteur = secteur;
    }


}
