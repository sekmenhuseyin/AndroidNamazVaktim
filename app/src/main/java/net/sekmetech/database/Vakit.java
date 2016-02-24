package net.sekmetech.database;

/**
 * Created by huseyin on 2.2.2016
 * table: vakit
 */
public class Vakit {
    // Labels table details
    public static final String name = "Vakit";
    public static final String _id = "_id";
    public static final String tarih = "tarih";
    public static final String imsak = "imsak";
    public static final String gunes = "gunes";
    public static final String ogle = "ogle";
    public static final String ikindi = "ikindi";
    public static final String aksam = "aksam";
    public static final String yatsi = "yatsi";
    public static final String kible = "kible";
    public static final String Ilce_id = "Ilce_id";
    //private variables
    private int key_id;
    private String key_tarih;
    private String key_imsak;
    private String key_gunes;
    private String key_ogle;
    private String key_ikindi;
    private String key_aksam;
    private String key_yatsi;
    private String key_kible;
    private int key_ilce_id;

    // Empty constructor
    public Vakit() {
    }

    // constructor
    public Vakit(String tarih, String imsak, String gunes, String ogle, String ikindi, String aksam, String yatsi, String kible, int ilce_id) {
        this.key_tarih = tarih;
        this.key_imsak = imsak;
        this.key_gunes = gunes;
        this.key_ogle = ogle;
        this.key_ikindi = ikindi;
        this.key_aksam = aksam;
        this.key_yatsi = yatsi;
        this.key_kible = kible;
        this.key_ilce_id = ilce_id;
    }

    //id
    public int GetId() {
        return this.key_id;
    }

    public void SetId(int key_id) {
        this.key_id = key_id;
    }

    //tarih
    public String GetTarih() {
        return this.key_tarih;
    }

    public void SetTarih(String key_tarih) {
        this.key_tarih = key_tarih;
    }

    //imsak
    public String GetImsak() {
        return this.key_imsak;
    }

    public void SetImsak(String key_imsak) {
        this.key_imsak = key_imsak;
    }

    //güneş
    public String GetGunes() {
        return this.key_gunes;
    }

    public void SetGunes(String key_gunes) {
        this.key_gunes = key_gunes;
    }

    //öğle
    public String GetOgle() {
        return this.key_ogle;
    }

    public void SettOgle(String key_ogle) {
        this.key_ogle = key_ogle;
    }

    //ikindi
    public String GetIkindi() {
        return this.key_ikindi;
    }

    public void SetIkindi(String key_ikindi) {
        this.key_ikindi = key_ikindi;
    }

    //akşa
    public String GetAksam() {
        return this.key_aksam;
    }

    public void SetAksam(String key_aksam) {
        this.key_aksam = key_aksam;
    }

    //yatsı
    public String GetYatsi() {
        return this.key_yatsi;
    }

    public void SetYatsi(String key_yatsi) {
        this.key_yatsi = key_yatsi;
    }

    //kıble
    public String GetKible() {
        return this.key_kible;
    }

    public void SetKible(String key_kible) {
        this.key_kible = key_kible;
    }

    //ilçe id
    public int GetIlce() {
        return this.key_ilce_id;
    }

    public void SetIlce(int key_ilce_id) {
        this.key_ilce_id = key_ilce_id;
    }

}
