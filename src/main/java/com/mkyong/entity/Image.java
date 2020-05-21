package com.mkyong.entity;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.lang.Nullable;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
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

    @Lob()
    private byte[] image;

    @ManyToOne
    @Nullable
    private Site site;

    @ManyToOne
    @Nullable
    private Secteur secteur;


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

    public Long getId() {
        return id;
    }

    public String getNomImage() {
        return nomImage;
    }

    public String getMimeType() {
        return mimeType;
    }

    public byte[] getImage() { return image; }

    @Nullable
    public Site getSite() { return site;
    }

    @Nullable
    public Secteur getSecteur() { return secteur;
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

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImage(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, mimeType.split("picture/")[1], baos);
        baos.flush();
        this.image = baos.toByteArray();
        baos.close();
    }

    public void setSite(@Nullable Site site) { this.site = site;
    }

    public void setSecteur(@Nullable Secteur secteur) { this.secteur = secteur;
    }
}


