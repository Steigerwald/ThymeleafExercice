package com.mkyong.entity;


import javax.persistence.*;
import java.util.Collection;
import javax.persistence.GenerationType;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;



@Entity
@Table(name="TBL_USER")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idUser;

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

    @ManyToMany
    private Collection<Role> roles;

    //constructeurs
    public User(){

    }

    //getters

    public Integer getIdUser() {
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

    public Collection<Role> getRoles() {
        return roles;
    }
//setters

    public void setIdUser(Integer idUser) {
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

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
