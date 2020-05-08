package com.mkyong.entity;


import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="TBL_COMMENTAIRE")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommentaire;

    @Column(name = "CONTENU")
    private String contenu;

    @Column(name= "DATE_COMMENTAIRE", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommentaire;

    @ManyToOne
    private Site site;


    // Méthodes pour l'affichage

    @Override
    public String toString() {
        return "  " + contenu;
    }

    public String toStringDateCommentaire(Date date) {
        SimpleDateFormat formater = null;
        formater = new SimpleDateFormat("dd-MM-yy");
        formater.format(date);

        return " " +  formater.format(date);
    }

    // Constructor

    public Commentaire() {
    }


    // Getters

    public Long getIdCommentaire() { return idCommentaire;
    }

    public String getContenu() { return contenu;
    }

    public Date getDateCommentaire() { return dateCommentaire;
    }

    public Site getSite() { return site;
    }

    // Setters


    public void setIdCommentaire(Long idCommentaire) { this.idCommentaire = idCommentaire;
    }

    public void setContenu(String contenu) { this.contenu = contenu;
    }

    public void setDateCommentaire(Date dateCommentaire) { this.dateCommentaire = dateCommentaire;
    }

    public void setSite(Site site) { this.site = site;
    }


}
