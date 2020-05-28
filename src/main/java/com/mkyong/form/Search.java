package com.mkyong.form;

public class Search {

    private String lieu;
    private String hauteur;
    private Integer nombreLongueurs;
    private Integer nombrePoints;

    // Getters
    public String getLieu() { return lieu;
    }

    public String getHauteur() { return hauteur;
    }

    public Integer getNombreLongueurs() { return nombreLongueurs;
    }

    public Integer getNombrePoints() { return nombrePoints;
    }

    // Setters

    public void setLieu(String lieu) { this.lieu = lieu;
    }

    public void setHauteur(String hauteur) { this.hauteur = hauteur;
    }

    public void setNombreLongueurs(Integer nombreLongueurs) { this.nombreLongueurs = nombreLongueurs;
    }

    public void setNombrePoints(Integer nombrePoints) { this.nombrePoints = nombrePoints;
    }
}
