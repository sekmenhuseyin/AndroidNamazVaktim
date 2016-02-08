package net.huseyinsekmenoglu.database;

/**
 * Created by huseyin on 2.2.2016
 * table: ulke
 */
public class Ulke {
    // Labels table details
    public static final String name = "Ulke";
    public static final String _id = "_id";
    public static final String Ad = "Ad";
    //private variables
    private int key_id;
    private String key_ad;

    // Empty constructor
    public Ulke() {
    }

    // constructor
    public Ulke(int key_id, String key_ad) {
        this.key_id = key_id;
        this.key_ad = key_ad;
    }

    // constructor
    public Ulke(String key_ad) {
        this.key_ad = key_ad;
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

}
