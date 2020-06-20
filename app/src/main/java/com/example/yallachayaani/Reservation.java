package com.example.yallachayaani;

public class Reservation {
    private String name;
    byte[] img;
    String dep,des;
    int idUser, idOff,idRes, nb;
    public Reservation() {
        super();
    }
    public Reservation(byte[] img, String name,String dep,String des, int nb) {
        super();
        this.name= name;
        this.nb = nb;
        this.img = img;
        this.dep = dep;
        this.des= des;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdOff(int name) {
        this.idUser = name;
    }

    public int getIdOff() {
        return idOff;
    }
    public void setIdUser(int name) {
        this.idOff = name;
    }

    public int getIdRes() {
        return idRes;
    }
    public void setIdResr(int name) {
        this.idRes = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNb() {
        return nb;
    }
    public void setNb(int nb) {
        this.nb = nb;
    }

    public byte[] getImg() {
        return img;
    }
    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getDep() {
        return dep;
    }
    public void setDep(String name) {
        this.dep = name;
    }

    public String getDes() {
        return des;
    }
    public void setDes(String name) {
        this.des = name;
    }
}
