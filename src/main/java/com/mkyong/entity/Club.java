package com.mkyong.entity;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="TBL_CLUB")
public class Club {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column (name="NOM_CLUB")
    private String nomClub;

    @Column (name="PRESIDENT_CLUB")
    private String presidentClub;

    @Column (name="ANNEE_CREATION")
    private String anneeCreation;

    @OneToMany(mappedBy="club")
    private Collection<Footballeur> footballeurs;

    @ManyToOne
    private League league;

    //constructors

    public Club() {
    }

    //getters

    public Long getId() {
        return id;
    }

    public String getNomClub() {
        return nomClub;
    }

    public String getPresidentClub() {
        return presidentClub;
    }

    public String getAnneeCreation() {
        return anneeCreation;
    }

    public Collection<Footballeur> getFootballeurs() {
        return footballeurs;
    }

    public League getLeague() { return league; }

    //setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setNomClub(String nomClub) {
        this.nomClub = nomClub;
    }

    public void setPresidentClub(String presidentClub) {
        this.presidentClub = presidentClub;
    }

    public void setAnneeCreation(String anneeCreation) {
        this.anneeCreation = anneeCreation;
    }

    public void setFootballeurs(Collection<Footballeur> footballeurs) {
        this.footballeurs = footballeurs;
    }

    public void setLeague(League league) { this.league = league; }
}
