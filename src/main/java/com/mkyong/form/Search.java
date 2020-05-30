package com.mkyong.form;

public class Search {

    private String lieu;
    private Integer hauteur;
    private Integer nombreSecteurs;
    private String cotation;
    private Integer nombreLongueurs;
    private Integer nombrePoints;

    // Getters
    public String getLieu() { return lieu;
    }

    public Integer getHauteur() { return hauteur;
    }

    public Integer getNombreSecteurs() { return nombreSecteurs;
    }

    public String getCotation() { return cotation;
    }

    public Integer getNombreLongueurs() { return nombreLongueurs;
    }

    public Integer getNombrePoints() { return nombrePoints;
    }

    // Setters

    public void setLieu(String lieu) { this.lieu = lieu;
    }

    public void setHauteur(Integer hauteur) { this.hauteur = hauteur;
    }

    public void setNombreSecteurs(Integer nombreSecteurs) { this.nombreSecteurs = nombreSecteurs;
    }

    public void setCotation(String cotation) { this.cotation = cotation;
    }

    public void setNombreLongueurs(Integer nombreLongueurs) { this.nombreLongueurs = nombreLongueurs;
    }

    public void setNombrePoints(Integer nombrePoints) { this.nombrePoints = nombrePoints;
    }
}
