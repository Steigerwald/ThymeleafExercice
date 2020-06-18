package com.mkyong.entity;


import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Iterator;

@Entity
@Table(name="TBL_SITE")
public class Site {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idSite;

    @Column(name="NOM_SITE")
    @NotNull
    private String nomSite;

    @Column(name="LIEU")
    @NotNull
    private String lieu;

    @Column(name="DESCRIPTIF")
    @NotNull
    private String descriptif;

    @Column(name="OFFICIEL")
    @NotNull
    private Boolean officiel;

    @Column(name="ISPUBLIC")
    @NotNull
    private Boolean isPublic;

    @OneToOne(mappedBy="site")
    @Nullable
    private User user;

    @ManyToOne
    private Topo topo;

    @OneToMany (mappedBy = "site")
    @Nullable
    private Collection<Commentaire> commentaires;

    @OneToMany (mappedBy = "site")
    @Nullable
    private Collection<Image> images;

    @OneToMany (mappedBy = "site")
    private Collection<Secteur> secteurs;


    // Méthodes pour l'affichage

    @Override
    public String toString() {
        return " " + nomSite;
    }

    public String toStringSecteurs(){
        String[]tabSecteurs=new String [secteurs.size()];

        Iterator iterator = secteurs.iterator();
        while (iterator.hasNext()) {
            for (int i=0;i<secteurs.size();i++){
                tabSecteurs [i]=iterator.next().toString();
            }
        }
        String joinedResult=String.join(",",tabSecteurs);
        return joinedResult;
    }


    public String toStringCommentaires(){
        String[]tabCommentaires=new String [commentaires.size()];

        Iterator iterator = commentaires.iterator();
        while (iterator.hasNext()) {
            for (int i=0;i<commentaires.size();i++){
                tabCommentaires [i]=iterator.next().toString();
            }
        }
        String joinedResult=String.join(",",tabCommentaires);
        return joinedResult;
    }

    public String toStringLieu() {
        return " " + lieu;
    }


    public String toStringOfficiel(){
        if (officiel==true){
            return "officiel";
        } else{
            return "non officiel";
        }
    }

    public String toStringPublic(){
        if (isPublic==true){
            return "public";
        } else{
            return "privé";
        }
    }


    // Constructeur

    public Site() {
        setOfficiel(false);
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

    public Boolean getOfficiel() { return officiel;
    }

    public Boolean getPublic() { return isPublic;
    }

    public Topo getTopo() { return topo;
    }

    public Collection<Secteur> getSecteurs() { return secteurs;
    }

    public User getUser() { return user;
    }

    @Nullable
    public Collection<Commentaire> getCommentaires() { return commentaires;
    }

    @Nullable
    public Collection<Image> getImages() { return images;
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

    public void setOfficiel(Boolean officiel) { this.officiel = officiel;
    }

    public void setPublic(Boolean aPublic) { isPublic = aPublic;
    }

    public void setTopo(Topo topo) { this.topo = topo;
    }

    public void setSecteurs(Collection<Secteur> secteurs) { this.secteurs = secteurs;
    }

    public void setUser(User user) { this.user = user;
    }

    public void setCommentaires(@Nullable Collection<Commentaire> commentaires) { this.commentaires = commentaires;
    }

    public void setImages(@Nullable Collection<Image> images) { this.images = images;
    }
}
