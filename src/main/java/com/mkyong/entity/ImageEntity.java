package com.mkyong.entity;

import javax.persistence.*;
import java.awt.*;

@Entity
@Table(name="TBL_IMAGE_ENTITY")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column (name="LIBELLE")
    private String libelle;

    @Column (name="IMAGE")
    private Image image;

    public ImageEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Image getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
