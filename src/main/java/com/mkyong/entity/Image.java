package com.mkyong.entity;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

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
    private Site site;

    @ManyToOne
    private Secteur secteur;


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
}


