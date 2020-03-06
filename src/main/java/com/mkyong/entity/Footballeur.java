package com.mkyong.entity;

import javax.persistence.*;

@Entity
@Table(name="TBL_FOOTBALLEUR")
public class Footballeur {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column (name="NOM")
    private String nom;

    @Column (name="PRENOM")
    private String prenom;

    @Column (name="AGE")
    private String age;

    @Column(name="NATIONALITE")
    private String nationalite;

    @Column (name="POSITION")
    private String position;

    @Column (name="NUMERO_MAILLOT")
    private String numeroMaillot;

    @ManyToOne
    private Club club;

    //constructeurs
    public Footballeur(String nom, String prenom, String age, String nationalite, String position, String numeroMaillot,Club club) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.nationalite=nationalite;
        this.position = position;
        this.numeroMaillot=numeroMaillot;
        this.club=club;
    }

    public Footballeur(){
    }

    @Override
    public String toString() {
        return "FootballeurEntity [id=" + id + ", nom= " + nom +
                ", prénom= " + prenom + ", age= " + age + ", nationalité= " + nationalite + ", position= " + position + ", numéro= " + numeroMaillot + "]";
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAge() {
        return age;
    }

    public String getNationalite() {
        return nationalite;
    }

    public String getPosition() {
        return position;
    }

    public String getNumeroMaillot() {
        return numeroMaillot;
    }

    public Club getClub() {
        return club;
    }

    //setters


    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setNumeroMaillot(String numeroMaillot) {
        this.numeroMaillot = numeroMaillot;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}