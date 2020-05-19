package com.mkyong.entity;


import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name="TBL_RESERVATION_TOPO")
public class Reservation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idReservation;

    @Column(name="ACCEPTATION")
    private Boolean acceptation;

    @Column(name="DATE_RESERVATION",nullable = false,length=6)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReservation;

    @OneToOne
    private Topo topo;

    @ManyToOne
    private User user;


    // Méthodes pour l'Affichage
    public String toStringDateReservation() {
        SimpleDateFormat formater = null;
        formater = new SimpleDateFormat("dd-MM-yy");
        formater.format(dateReservation);

        return " " +  formater.format(dateReservation);
    }

    public String toStringEtat (){
        if (acceptation==true) {
            String reponse ="accepté";
            return reponse;
        } else {
            String reponse ="en attente";
            return reponse;
        }
    }



    // Constructeur


    public Reservation() {
        setAcceptation(false);
    }

    // Getters

    public Long getIdReservation() { return idReservation;
    }

    public Boolean getAcceptation() { return acceptation;
    }

    public Date getDateReservation() { return dateReservation;
    }

    public Topo getTopo() { return topo;
    }

    public User getUser() { return user;
    }


    // Setters


    public void setIdReservation(Long idReservation) { this.idReservation = idReservation;
    }

    public void setAcceptation(Boolean acceptation) { this.acceptation = acceptation;
    }

    public void setDateReservation(Date dateReservation) { this.dateReservation = dateReservation;
    }

    public void setTopo(Topo topo) { this.topo = topo;
    }

    public void setUser(User user) { this.user = user;
    }
}
