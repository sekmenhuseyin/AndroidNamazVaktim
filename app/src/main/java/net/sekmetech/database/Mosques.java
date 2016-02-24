package net.sekmetech.database;

public class Mosques {
    String adres;
    String isim;
    double lat;
    double lon;
    double mesafe;

    public double getMesafe() {
        return this.mesafe;
    }

    public void setMesafe(double mesafe) {
        this.mesafe = mesafe;
    }

    public String getAdres() {
        return this.adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getIsim() {
        return this.isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }
}
