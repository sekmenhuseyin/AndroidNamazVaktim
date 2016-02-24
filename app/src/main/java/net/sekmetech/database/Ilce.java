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
    public static final String Sehir_id = "Sehir_id";
    //private variables
    private int key_id;
    private String key_ad;
    private int sehir_id;


    // Empty constructor
    public Ilce() {
    }

    // constructor
    public Ilce(int key_id, String key_ad, int sehir_id) {
        this.key_id = key_id;
        this.key_ad = key_ad;
        this.sehir_id = sehir_id;
    }

    // constructor
    public Ilce(String key_ad, int sehir_id) {
        this.key_ad = key_ad;
        this.sehir_id = sehir_id;
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

    // sehir
    public int getSehir() {
        return this.sehir_id;
    }

    public void setSehir(int sehir_id) {
        this.sehir_id = sehir_id;
    }
}
