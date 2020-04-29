package com.mkyong.entity;

import javax.persistence.*;

@Entity
@Table(name="TBL_CARTE")
public class Carte {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCarte;

    @Column(name="NOM_CARTE")
    private String nomCarte;

    @Column(name="MIME_TYPE")
    private String mimeType;

    @OneToOne (mappedBy = "carte")
    private Site site;


    // Constructor

    public Carte() {
    }

    // Getters

    public Long getIdCarte() { return idCarte;
    }

    public String getNomCarte() { return nomCarte;
    }

    public String getMimeType() { return mimeType;
    }

    public Site getSite() { return site;
    }

    // Setters

    public void setIdCarte(Long idCarte) { this.idCarte = idCarte;
    }

    public void setNomCarte(String nomCarte) { this.nomCarte = nomCarte;
    }

    public void setMimeType(String mimeType) { this.mimeType = mimeType;
    }

    public void setSite(Site site) { this.site = site;
    }
}
