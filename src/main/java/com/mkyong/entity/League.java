package com.mkyong.entity;


import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="TBL_LEAGUE")
public class League {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column (name="NOM_LEAGUE")
    private String nomLeague;

    @Column (name="PAYS")
    private String pays;

    @Column (name="NOMBRE_CLUBS")
    private Integer nombreClubs;

    @OneToMany(mappedBy="league")
    private Collection<Club> clubs;

    //constructeurs

    public League() {
    }

    //getters

    public Long getId() {
        return id; }

    public String getNomLeague() {
        return nomLeague; }

    public String getPays() {
        return pays;
    }

    public Integer getNombreClubs() {
        return nombreClubs; }

    public Collection<Club> getClubs() {
        return clubs; }

    //Setters

    public void setId(Long id) {
        this.id = id; }

    public void setNomLeague(String nomLeague) {
        this.nomLeague = nomLeague; }

    public void setNombreClubs(Integer nombreClubs) {
        this.nombreClubs = nombreClubs; }

    public void setPays(String pays) {
        this.pays = pays; }

    public void setClubs(Collection<Club> clubs) {
        this.clubs = clubs; }
}
