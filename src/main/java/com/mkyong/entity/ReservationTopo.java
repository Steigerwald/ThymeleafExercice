package com.mkyong.entity;


import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="TBL_RESERVATION_TOPO")
public class ReservationTopo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idReservation;

    @Column(name="ACCEPTATION")
    private Boolean acceptation;

    @Column(name="DATE_RESERVATION")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReservation;

    @ManyToOne
    private User user;

    @OneToOne (mappedBy = "reservation")
    private Topo topo;


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
            String reponse ="refusé";
            return reponse;
        }
    }

    // Constructeur

    public ReservationTopo() {
    }

    // Getters

    public Long getIdReservation() { return idReservation;
    }


    public Boolean getAcceptation() { return acceptation;
    }

    public Date getDateReservation() { return dateReservation;
    }

    public User getUser() { return user;
    }

    public Topo getTopo() { return topo;
    }

    // Setters


    public void setIdReservation(Long idReservation) { this.idReservation = idReservation;
    }

    public void setAcceptation(Boolean acceptation) { this.acceptation = acceptation;
    }

    public void setDateReservation(Date dateReservation) { this.dateReservation = dateReservation;
    }

    public void setUser(User user) { this.user = user;
    }

    public void setTopo(Topo topo) { this.topo = topo;
    }

}
