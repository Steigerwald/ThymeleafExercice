package com.mkyong.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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
    @Column(name = "DATE_PARUTION",nullable = false,length=6)
    private Date dateParution;

    @Column(name = "DISPONIBLE")
    private Boolean disponible;

    @Column(name = "LOCATION")
    private Boolean location;

    @OneToMany (mappedBy = "topo",cascade = CascadeType.ALL)
    private Collection<Site> sites;

    @OneToOne (mappedBy= "topo",cascade = CascadeType.ALL)
    @Nullable
    private Reservation reservation;

    @OneToOne (mappedBy = "topo", cascade = CascadeType.ALL)
    @Nullable
    private Image image;

    @ManyToOne
    private User owner;



    // Méthodes pour l'affichage

    @Override
    public String toString() {
        return " " +nomTopo;
    }

    public String toStringDateParution() {

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yy");
        String inActiveDate = null;
        inActiveDate = format1.format(dateParution);

        return " " +  inActiveDate;
    }

    public String toStringSites(){
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

    public String toStringDisponible(){
        if (disponible==true){
            return "libre";
        }else{
            return "réservé";
        }
    }

    public String toStringLocation(){
        if (location==true){
            return "disponible en location";
        }else{
            return "indisponible en location";
        }
    }

    // Constructeur

    public Topo() {
        setDisponible(true);
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

    @Nullable
    public Reservation getReservation() { return reservation; }

    public User getOwner() { return owner; }

    @Nullable
    public Image getImage() { return image; }

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

    public void setReservation(@Nullable Reservation reservation) { this.reservation = reservation; }

    public void setOwner(User owner) { this.owner = owner; }

    public void setImage(@Nullable Image image) { this.image = image;
    }
}
