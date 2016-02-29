package net.sekmetech.database;

/**
 * Created by huseyin on 2.2.2016
 * table: ilçe
 */
public class Ilce {
    // Labels table details
    public static final String name = "Ilce";
    public static final String _id = "_id";
    public static final String Ad = "Ad";
    public static final String Kible = "Kible";
    public static final String Sehir_id = "Sehir_id";
    //private variables
    private int key_id;
    private String key_ad;
    private String key_kible;
    private int sehir_id;


    // Empty constructor
    public Ilce() {
    }

    // ID
    public int getID() {
        return this.key_id;
    }

    public void setID(int key_id) {
        this.key_id = key_id;
    }

    // name
    public String getName() {
        return this.key_ad;
    }

    public void setName(String key_ad) {
        this.key_ad = key_ad;
    }

    // kible
    public String getKible() {
        return this.key_kible;
    }

    public void setKible(String key_kible) {
        this.key_kible = key_kible;
    }

    // sehir
    public int getSehir() {
        return this.sehir_id;
    }

    public void setSehir(int sehir_id) {
        this.sehir_id = sehir_id;
    }
}
