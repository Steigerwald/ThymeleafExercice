package com.mkyong.entity;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import java.io.*;
import java.util.Arrays;

@Entity
@Table(name="TBL_IMAGE")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOM_IMAGE")
    private String nomImage;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Column(name = "TAILLE")
    private Long taille;

    @Lob()
    private byte[] image;

    @OneToOne (cascade = {CascadeType.ALL})
    @Nullable
    private Site site;

    @OneToOne (cascade = {CascadeType.ALL})
    @Nullable
    private Topo topo;


    // Méthodes pour l'affichage

    @Override
    public String toString() {
        return "Image{" +
                "image=" + Arrays.toString(image) +
                '}';
    }


    public String generateBase64Image()
    {
        return Base64.encodeBase64String(this.getImage());
    }


    // constructeur

    public Image() {
    }


    // getters

    public Long getId() { return id;
    }

    public String getNomImage() { return nomImage;
    }

    public String getMimeType() { return mimeType;
    }

    public Long getTaille() { return taille;
    }

    public byte[] getImage() { return image; }

    public String getImageBase64(){
        String encodedString = java.util.Base64.getEncoder().encodeToString(image);
        return encodedString;
    }
    @Nullable
    public Site getSite() { return site;
    }

    @Nullable
    public Topo getTopo() { return topo;
    }


    // setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setTaille(Long taille) { this.taille = taille; }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setSite(@Nullable Site site) { this.site = site; }

    public void setTopo(@Nullable Topo topo) { this.topo = topo; }

}


