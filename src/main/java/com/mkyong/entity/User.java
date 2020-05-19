package com.mkyong.entity;


import javax.persistence.*;
import java.util.Collection;
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


    @ManyToMany(cascade=CascadeType.MERGE)
    @Nullable
    @JoinTable(
            name="TBL_USER_SITES",
            joinColumns={@JoinColumn(name="USERS_ID_USER")},
            inverseJoinColumns={@JoinColumn(name="SITES_ID_SITE")})
    private List<Site> sites;


    @ManyToMany(cascade=CascadeType.MERGE)
    @Nullable
    @JoinTable(
            name="TBL_USER_TOPOS",
            joinColumns={@JoinColumn(name="USERS_ID_USER")},
            inverseJoinColumns={@JoinColumn(name="TOPOS_ID_TOPO")})
    private List<Topo> topos;


    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user")
    @Nullable
    private Collection <Reservation> reservations;


    public String toStringRole(Role role) {
        return " "+ role;
    }

    //constructeurs
    public User (){
    };

    public User(String mailUser, String motDePasseUser, Collection<? extends GrantedAuthority> authorities){
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
    public List<Site> getSites() { return sites;
    }

    @Nullable
    public List<Topo> getTopos() { return topos;
    }

    @Nullable
    public Collection<Reservation> getReservations() { return reservations;
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

    public void setSites(@Nullable List<Site> sites) { this.sites = sites;
    }

    public void setTopos(@Nullable List<Topo> topos) { this.topos = topos;
    }

    public void setReservations(@Nullable Collection<Reservation> reservations) { this.reservations = reservations;
    }
}
