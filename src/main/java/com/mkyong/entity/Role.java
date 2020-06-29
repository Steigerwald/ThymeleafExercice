package com.mkyong.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import javax.persistence.GenerationType;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.lang.Nullable;

@Entity
@Table(name="TBL_ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idRole;

    @Column(nullable = false, unique = true,name="NOM_ROLE")
    @javax.validation.constraints.NotEmpty
    private String nomRole;

    @OneToMany(mappedBy = "role",cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @Nullable
    private Collection<User> users;


    // Méthodes pour l'affichage

    @Override
    public String toString() {
        return nomRole;
    }

    //Constructeurs

    public Role() {

    }

    // Getters

    public Integer getIdRole() {
        return idRole;
    }

    public String getNomRole() {
        return nomRole;
    }

    @Nullable
    public Collection<User> getUsers() {
        return users;
    }
// Setters

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    public void setUsers(@Nullable Collection<User> users) {
        this.users = users;
    }
}
