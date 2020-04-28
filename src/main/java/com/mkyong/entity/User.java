package com.mkyong.entity;


import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import javax.persistence.GenerationType;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name="TBL_USER")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable=false,name="NOM_USER")
    @NotEmpty()
    private String nomUser;

    @Column(nullable=false,name="PRENOM_USER")
    @NotEmpty()
    private String prenomUser;

    @Column(nullable=false, unique=true,name="MAIL_USER")
    @NotEmpty()
    @Email(message="{errors.invalid_email}")
    private String mailUser;

    @Column(nullable=false,name="MOT_DE_PASSE_USER")
    @NotEmpty()
    @Size(min=4)
    private String motDePasseUser;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(
            name="TBL_USER_ROLES",
            joinColumns={@JoinColumn(name="USERS_ID_USER")},
            inverseJoinColumns={@JoinColumn(name="ROLES_ID_ROLE")})
    private List<Role> roles;

    @Override
    public String toString() {
        return "roles=" + roles;
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

    public List<Role> getRoles() { return roles; }


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

    public void setRoles(List<Role> roles) { this.roles = roles; }
}
