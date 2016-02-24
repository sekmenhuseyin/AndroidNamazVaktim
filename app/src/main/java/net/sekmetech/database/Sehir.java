package net.sekmetech.database;

/**
 * Created by huseyin on 2.2.2016
 * table: sehir
 */
public class Sehir {
    // Labels table details
    public static final String name = "Sehir";
    public static final String _id = "_id";
    public static final String Ad = "Ad";
    public static final String Ulke_id = "Ulke_id";
    //private variables
    private int key_id;
    private String key_ad;
    private int ulke_id;

    // Empty constructor
    public Sehir() {
    }

    // constructor
    public Sehir(int key_id, String key_ad, int ulke_id) {
        this.key_id = key_id;
        this.key_ad = key_ad;
        this.ulke_id = ulke_id;
    }

    // constructor
    public Sehir(String key_ad, int ulke_id) {
        this.key_ad = key_ad;
        this.ulke_id = ulke_id;
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

    // ulke
    public int getUlke() {
        return this.ulke_id;
    }

    public void setUlke(int ulke_id) {
        this.ulke_id = ulke_id;
    }
}
