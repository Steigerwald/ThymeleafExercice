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

    public Footballeur(){
    }

    @Override
    public String toString() {
        return "Footballeur{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", age='" + getAge() + '\'' +
                ", nationalite='" + getNationalite() + '\'' +
                ", position='" + getPosition() + '\'' +
                ", numeroMaillot='" + getNumeroMaillot() + '\'' +
                ", club=" + getClub() +
                '}';
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

    public Club getClub() { return club; }

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

    public void setClub(Club club) { this.club = club; }
}