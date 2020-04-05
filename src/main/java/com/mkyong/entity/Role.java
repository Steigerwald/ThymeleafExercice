package com.mkyong.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
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

/*
    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="TBL_USER_ROLES",
            joinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="id")})
    private List<User> users;

*/

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

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
