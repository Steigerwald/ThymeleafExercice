package com.mkyong.entity;


import javax.persistence.*;
import java.util.Collection;

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
/*
    @ManyToMany(mappedBy="sites")
    private Collection<User> users;
*/
    @ManyToOne
    private Topo topo;

    @OneToMany (mappedBy = "site")
    private Collection<Commentaire> commentaires;

    @OneToMany (mappedBy = "site")
    private Collection<Image> images;

    @OneToMany (mappedBy = "site")
    private Collection<Secteur> secteurs;


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


    public Topo getTopo() { return topo;
    }

    public Collection<Commentaire> getCommentaires() { return commentaires;
    }

    public Collection<Image> getImages() { return images;
    }

    public Collection<Secteur> getSecteurs() { return secteurs;
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

    public void setTopo(Topo topo) { this.topo = topo;
    }

    public void setCommentaires(Collection<Commentaire> commentaires) { this.commentaires = commentaires;
    }

    public void setImages(Collection<Image> images) { this.images = images;
    }

    public void setSecteurs(Collection<Secteur> secteurs) { this.secteurs = secteurs;
    }
}
