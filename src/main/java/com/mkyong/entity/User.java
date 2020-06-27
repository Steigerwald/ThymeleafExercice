package com.mkyong.entity;


import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name="TBL_USER")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable=false,name="NOM_USER")
    @NotNull
    private String nomUser;

    @Column(nullable=false,name="PRENOM_USER")
    @NotNull
    private String prenomUser;

    @Column(nullable=false, unique=true,name="MAIL_USER")
    @NotNull
    @Email(message="{errors.invalid_email}")
    private String mailUser;

    @Column(nullable=false,name="MOT_DE_PASSE_USER")
    @NotNull
    @Size(min=4)
    private String motDePasseUser;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    @Nullable
    private Collection<Site> sites;

    @OneToMany(mappedBy = "owner", cascade=CascadeType.ALL)
    @Nullable
    private Collection <Topo> topos;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL)
    @Nullable
    private Collection <Reservation> reservations;

    @OneToMany (mappedBy = "user",cascade=CascadeType.ALL)
    @Nullable
    private Collection<Commentaire> commentaires;


    //methodes pour l'affichage

    public String toStringRole(Role role) {
        return " "+ role;
    }

    public String toStringTopos() {
        String[]tabTopos=new String [topos.size()];
        Iterator iterator = topos.iterator();
        while (iterator.hasNext()) {
            for (int i=0;i<topos.size();i++){
                tabTopos [i]=iterator.next().toString();
            }
        }
        String joinedResult=String.join(",",tabTopos);
        return joinedResult;
    }

    public String toStringReservations() {
        String[]tabReservations=new String [reservations.size()];

        Iterator iterator = reservations.iterator();
        while (iterator.hasNext()) {
            for (int i=0;i<reservations.size();i++){
                tabReservations [i]=iterator.next().toString();
            }
        }
        String joinedResult=String.join(",",tabReservations);
        return joinedResult;
    }

    //constructeurs
    public User (){
    };

    //getters

    public Long getIdUser() {
        return idUser;
    }

    public String getNomUser() {
        return nomUser;
    }

    public String getPrenomUser() {
        return prenomUser;
    }

    public String getMailUser() {
        return mailUser;
    }

    public String getMotDePasseUser() {
        return motDePasseUser;
    }

    public Role getRole() { return role; }

    @Nullable
    public Collection<Site> getSites() { return sites;
    }

    @Nullable
    public Collection<Topo> getTopos() { return topos;
    }

    @Nullable
    public Collection<Reservation> getReservations() { return reservations;
    }

    @Nullable
    public Collection<Commentaire> getCommentaires() { return commentaires;
    }


    //setters

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public void setMotDePasseUser(String motDePasseUser) {
        this.motDePasseUser = motDePasseUser;
    }

    public void setRole(Role role) { this.role = role; }

    public void setSites(@Nullable Collection<Site> sites) { this.sites = sites;
    }

    public void setTopos(@Nullable Collection<Topo> topos) { this.topos = topos;
    }

    public void setReservations(@Nullable Collection<Reservation> reservations) { this.reservations = reservations;
    }

    public void setCommentaires(@Nullable Collection<Commentaire> commentaires) { this.commentaires = commentaires;
    }
}
