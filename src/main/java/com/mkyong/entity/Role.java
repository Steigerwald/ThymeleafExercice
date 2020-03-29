package com.mkyong.entity;

import javax.persistence.*;
import java.util.Collection;
import javax.persistence.GenerationType;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="TBL_ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idRole;

    @Column(nullable = false, unique = true,name="NOM_ROLE")
    @NotEmpty
    private String nomRole;

    @ManyToMany(mappedBy="roles")
    private Collection<User> users;

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

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
