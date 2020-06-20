package com.example.yallachayaani;

public class Offre {

    private String name,dep,des;
    byte[] img;
    int idUser, idOff;
    public Offre() {
        super();
    }
    public Offre(byte[] img, String name, String dep, String des) {
        super();
       this.name= name;
        this.dep = dep;
        this.des = des;
        this.img = img;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDep() {
        return dep;
    }
    public void setDep(String name) {
        this.dep = name;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int name) {
        this.idUser = name;
    }

    public int getIdOff() {
        return idOff;
    }
    public void setIdOff(int name) {
        this.idOff = name;
    }

    public String getDes() {
        return des;
    }
    public void setDes(String name) {
        this.des = name;
    }

    public byte[] getImg() {
        return img;
    }
    public void setImg(byte[] name) {
        this.img = name;
    }
}