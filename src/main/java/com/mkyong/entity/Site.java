package com.mkyong.entity;


import javax.persistence.*;

@Entity
@Table(name="TBL_SITE")
public class Site {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idSite;


    @Column(name="NOM_SITE")
    private String nomSite;

    @Column(name="LIEU")
    private String lieu;

    @Column(name="DESCRIPTIF")
    private String descriptif;


    // Constructeur

    public Site() {
    }

    // Getters


    public Long getIdSite() { return idSite;
    }

    public String getNomSite() { return nomSite;
    }

    public String getLieu() { return lieu;
    }

    public String getDescriptif() { return descriptif;
    }


    // Setters


    public void setIdSite(Long idSite) { this.idSite = idSite;
    }

    public void setNomSite(String nomSite) { this.nomSite = nomSite;
    }

    public void setLieu(String lieu) { this.lieu = lieu;
    }

    public void setDescriptif(String descriptif) { this.descriptif = descriptif;
    }
}
