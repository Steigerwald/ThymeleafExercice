package com.mkyong.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="TBL_RESERVATION_TOPO")
public class ReservationTopo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idReservation;

    @Column(name="NUMERO_RESERVATION")
    private String numeroReservation;

    @Column(name="ACCEPTATION")
    private Boolean acceptation;

    @Column(name="DATE_RESERVATION")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReservation;

    // Constructeur

    public ReservationTopo() {
    }

    // Getters

    public Long getIdReservation() { return idReservation;
    }

    public String getNumeroReservation() { return numeroReservation;
    }

    public Boolean getAcceptation() { return acceptation;
    }

    public Date getDateReservation() { return dateReservation;
    }


    // Setters


    public void setIdReservation(Long idReservation) { this.idReservation = idReservation;
    }

    public void setNumeroReservation(String numeroReservation) { this.numeroReservation = numeroReservation;
    }

    public void setAcceptation(Boolean acceptation) { this.acceptation = acceptation;
    }

    public void setDateReservation(Date dateReservation) { this.dateReservation = dateReservation;
    }
}
