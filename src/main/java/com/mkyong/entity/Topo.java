package com.mkyong.entity;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Entity
@Table(name="TBL_TOPO")
public class Topo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTopo;

    @Column(name = "NOM_TOPO")
    private String nomTopo;

    @Column(name = "DESCRIPTION")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_PARUTION")
    private Date dateParution;
    /*
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_PARUTION",nullable=false,length=6)
    private Date dateParution;
*/
    @Column(name = "DISPONIBLE")
    private Boolean disponible;

    @Column(name = "LOCATION")
    private Boolean location;

    @OneToMany (mappedBy = "topo")
    private Collection<Site> sites;

    @OneToOne
    private ReservationTopo reservation;

    @ManyToMany(mappedBy="topos")
    private Collection<User> users;

    // Méthodes pour l'affichage

    public String toStringDateParution(Date date) {

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yy");
        String inActiveDate = null;
        inActiveDate = format1.format(date);

        return " " +  inActiveDate;
    }

    public String toStringSites(Collection<Site> sites){
        String[]tabSites=new String [sites.size()];

        Iterator iterator = sites.iterator();
        while (iterator.hasNext()) {
            for (int i=0;i<sites.size();i++){
                tabSites [i]=iterator.next().toString();
            }
        }
        String joinedResult=String.join(",",tabSites);
        return joinedResult;
    }

    public String toStringDisponible(Boolean dispo){
        if (dispo==true){
            return "reservé";
        }else{
            return "libre";
        }
    }

    public String toStringLocation(Boolean loc){
        if (loc==true){
            return "oui";
        }else{
            return "non";
        }
    }


    // Constructeur

    public Topo() {
    }

    // Getters

    public Long getIdTopo() { return idTopo;
    }

    public String getNomTopo() { return nomTopo; }

    public String getDescription() { return description; }

    public Date getDateParution() { return dateParution; }

    public Boolean getDisponible() { return disponible; }

    public Boolean getLocation() { return location; }

    public Collection<Site> getSites() { return sites; }

    public ReservationTopo getReservation() { return reservation; }

    public Collection<User> getUsers() { return users; }


    // Setters

    public void setIdTopo(Long idTopo) { this.idTopo = idTopo; }

    public void setNomTopo(String nomTopo) { this.nomTopo = nomTopo; }

    public void setDescription(String description) { this.description = description; }

    public void setDateParution(Date dateParution) { this.dateParution = dateParution; }

    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public void setLocation(Boolean location) { this.location = location; }

    public void setSites(Collection<Site> sites) {
        this.sites = sites;
    }


    public void setReservation(ReservationTopo reservation) { this.reservation = reservation; }

    public void setUsers(Collection<User> users) { this.users = users; }
}
