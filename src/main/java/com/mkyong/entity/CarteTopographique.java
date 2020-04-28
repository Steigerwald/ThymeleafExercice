package com.mkyong.entity;

import javax.persistence.*;

@Entity
@Table(name="TBL_CARTETOPOGRAPHIQUE")
public class CarteTopographique {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCarte;

    @Column(name="NOM_CARTE")
    private String numeroVoie;


    // Constructor

    public CarteTopographique() {
    }

    // Getters

    public Long getIdCarte() { return idCarte;
    }

    public String getNumeroVoie() { return numeroVoie;
    }



    // Setters

    public void setIdCarte(Long idCarte) { this.idCarte = idCarte;
    }

    public void setNumeroVoie(String numeroVoie) { this.numeroVoie = numeroVoie;
    }
}
