package com.mkyong.entity;


import javax.persistence.*;
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
// Setters


    public void setIdCommentaire(Long idCommentaire) { this.idCommentaire = idCommentaire;
    }

    public void setContenu(String contenu) { this.contenu = contenu;
    }

    public void setDateCommentaire(Date dateCommentaire) { this.dateCommentaire = dateCommentaire;
    }
}
