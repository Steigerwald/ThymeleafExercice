package com.mkyong.entity;


import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="TBL_RESERVATION_TOPO")
public class ReservationTopo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idReservation;

    @Column(name="ACCEPTATION")
    private Boolean acceptation;

    @Column(name="DATE_RESERVATION",nullable = false,length=6)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReservation;

    @ManyToMany(cascade=CascadeType.MERGE)
    @Nullable
    @JoinTable(
            name="TBL_RESERVATION_TOPOS",
            joinColumns={@JoinColumn(name="RESERVATIONS_ID_RESERVATION")},
            inverseJoinColumns={@JoinColumn(name="TOPOS_ID_TOPO")})
    private List<Topo> topos;

    @ManyToOne
    private User user;


    // Méthodes pour l'Affichage
    public String toStringDateReservation(Date date) {
        SimpleDateFormat formater = null;
        formater = new SimpleDateFormat("dd-MM-yy");
        formater.format(date);

        return " " +  formater.format(date);
    }

    public String toStringEtat (Boolean etat){
        if (etat==true) {
            String reponse ="accepté";
            return reponse;
        } else {
            String reponse ="en attente";
            return reponse;
        }
    }


    // Constructeur


    public ReservationTopo() {
        setAcceptation(false);
    }

    // Getters

    public Long getIdReservation() { return idReservation;
    }

    public Boolean getAcceptation() { return acceptation;
    }

    public Date getDateReservation() { return dateReservation;
    }

    @Nullable
    public List<Topo> getTopos() { return topos;
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

    public void setTopos(@Nullable List<Topo> topos) { this.topos = topos;
    }

    public void setUser(User user) { this.user = user;
    }
}
