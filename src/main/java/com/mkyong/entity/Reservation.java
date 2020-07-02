package com.mkyong.entity;


import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="TBL_RESERVATION")
public class Reservation {

    @Id
    @Nullable
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idReservation;

    @Column(name="ETAT")
    private String etat;

    @Column(name="DATE_RESERVATION",nullable = false,length=6)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReservation;

    @ManyToOne
    @Nullable
    private Topo topo;

    @ManyToOne
    @Nullable
    private User user;


    // Méthodes pour l'Affichage
    public String toStringDateReservation() {
        SimpleDateFormat formater = null;
        formater = new SimpleDateFormat("dd-MM-yy");
        formater.format(dateReservation);

        return " " +  formater.format(dateReservation);
    }

    @Override
    public String toString() {
        return " " + idReservation;
    }

    // Constructeur


    public Reservation() {
        setEtat("En attente");
    }

    // Getters


    @Nullable
    public Long getIdReservation() { return idReservation;
    }

    public String getEtat() { return etat;
    }

    public Date getDateReservation() { return dateReservation;
    }

    @Nullable
    public Topo getTopo() { return topo;
    }

    @Nullable
    public User getUser() { return user;
    }

    // Setters


    public void setIdReservation(@Nullable Long idReservation) { this.idReservation = idReservation;
    }

    public void setEtat(String etat) { this.etat = etat;
    }

    public void setDateReservation(Date dateReservation) { this.dateReservation = dateReservation;
    }

    public void setTopo(@Nullable Topo topo) { this.topo = topo;
    }

    public void setUser(@Nullable User user) { this.user = user;
    }
}
