package com.mkyong.entity;

public class Users {
    private String nomUser;
    private String prenomUser;

    //constructeurs
    public Users(){

    }

    public Users(String nomUser, String prenomUser) {
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
    }

    //getters
    public String getNomUser() {
        return nomUser;
    }
    public String getPrenomUser() {
        return prenomUser; }

        //setters
    public void setNomUser(String nomUser) {
        this.nomUser = nomUser; }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }
}
