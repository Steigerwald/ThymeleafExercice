package com.mkyong.entity;


import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

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

    @Column(name = "DATE_PARUTION")
    private Date dateParution;

    @Column(name = "DISPONIBLE")
    private Boolean disponible;

    @Column(name = "LOCATION")
    private Boolean location;

    @OneToMany (mappedBy = "topo")
    private Collection<Site> sites;

    @OneToOne
    private ReservationTopo reservation;

   /*
    @ManyToMany(mappedBy="topos")
    private Collection<User> users;

    */

    // Constructeur

    public Topo() {
    }

    // Getters

    public Long getIdTopo() { return idTopo;
    }

    public String getNomTopo() { return nomTopo;
    }

    public String getDescription() { return description;
    }

    public Date getDateParution() { return dateParution;
    }

    public Boolean getDisponible() { return disponible;
    }

    public Boolean getLocation() { return location;
    }

    public Collection<Site> getSites() { return sites;
    }


    public ReservationTopo getReservation() { return reservation;
    }


    // Setters

    public void setIdTopo(Long idTopo) { this.idTopo = idTopo;
    }

    public void setNomTopo(String nomTopo) { this.nomTopo = nomTopo;
    }

    public void setDescription(String description) { this.description = description;
    }

    public void setDateParution(Date dateParution) { this.dateParution = dateParution;
    }

    public void setDisponible(Boolean disponible) { this.disponible = disponible;
    }

    public void setLocation(Boolean location) { this.location = location;
    }

    public void setSites(Collection<Site> sites) {
        this.sites = sites;
    }


    public void setReservation(ReservationTopo reservation) { this.reservation = reservation;
    }
}
