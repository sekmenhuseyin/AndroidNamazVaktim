/*
 * Copyright (c) 2016. Tüm hakları saklıdır.
 */

package net.huseyinsekmenoglu.database;

import java.util.Date;

/**
 * Created by huseyin.sekmenoglu on 23.2.2016.
 * enum vakit
 */
public class VakitAsDate {
    // Labels table details
    public static final String imsak = "imsak";
    public static final String gunes = "gunes";
    public static final String ogle = "ogle";
    public static final String ikindi = "ikindi";
    public static final String aksam = "aksam";
    public static final String yatsi = "yatsi";
    public static final String kible = "kible";
    //private variables
    private Date key_imsak;
    private Date key_gunes;
    private Date key_ogle;
    private Date key_ikindi;
    private Date key_aksam;
    private Date key_yatsi;
    private Date key_kible;

    // Empty constructor
    public VakitAsDate() {
    }

    // constructor
    public VakitAsDate(Date imsak, Date gunes, Date ogle, Date ikindi, Date aksam, Date yatsi, Date kible) {
        this.key_imsak = imsak;
        this.key_gunes = gunes;
        this.key_ogle = ogle;
        this.key_ikindi = ikindi;
        this.key_aksam = aksam;
        this.key_yatsi = yatsi;
        this.key_kible = kible;
    }

    //imsak
    public Date GetImsak() {
        return this.key_imsak;
    }

    public void SetImsak(Date key_imsak) {
        this.key_imsak = key_imsak;
    }

    //güneş
    public Date GetGunes() {
        return this.key_gunes;
    }

    public void SetGunes(Date key_gunes) {
        this.key_gunes = key_gunes;
    }

    //öğle
    public Date GetOgle() {
        return this.key_ogle;
    }

    public void SettOgle(Date key_ogle) {
        this.key_ogle = key_ogle;
    }

    //ikindi
    public Date GetIkindi() {
        return this.key_ikindi;
    }

    public void SetIkindi(Date key_ikindi) {
        this.key_ikindi = key_ikindi;
    }

    //akşa
    public Date GetAksam() {
        return this.key_aksam;
    }

    public void SetAksam(Date key_aksam) {
        this.key_aksam = key_aksam;
    }

    //yatsı
    public Date GetYatsi() {
        return this.key_yatsi;
    }

    public void SetYatsi(Date key_yatsi) {
        this.key_yatsi = key_yatsi;
    }

    //kıble
    public Date GetKible() {
        return this.key_kible;
    }

    public void SetKible(Date key_kible) {
        this.key_kible = key_kible;
    }

}
