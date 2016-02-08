package net.huseyinsekmenoglu.database;

import java.sql.Date;

/**
 * Created by huseyin on 2.2.2016
 * table: vakit
 */
public class Vakit {
    //private variables
    int _id;
    int _ilce_id;
    Date _tarih;
    String _imsak;
    String _gunes;
    String _ogle;
    String _ikindi;
    String _aksam;
    String _yatsi;
    String _kible;
    String _teheccud;

    // Empty constructor
    public Vakit() {
    }

    // constructor
    public Vakit(int id, int ilce_id, Date tarih, String imsak, String gunes, String ogle, String ikindi, String aksam, String yatsi, String kible, String teheccud) {
        this._id = id;
        this._ilce_id = ilce_id;
        this._tarih = tarih;
        this._imsak = imsak;
        this._gunes = gunes;
        this._ogle = ogle;
        this._ikindi = ikindi;
        this._aksam = aksam;
        this._yatsi = yatsi;
        this._kible = kible;
        this._teheccud = teheccud;
    }

    // constructor
    public Vakit(int ilce_id) {
        this._ilce_id = ilce_id;
    }

    // vakitler
    public String get_imsak(int ilce_id, Date _tarih) {
        return this._imsak;
    }

    public String get_gunes(int ilce_id, Date _tarih) {
        return this._gunes;
    }

    public String get_ogle(int ilce_id, Date _tarih) {
        return this._ogle;
    }

    public String get_ikindi(int ilce_id, Date _tarih) {
        return this._ikindi;
    }

    public String get_aksam(int ilce_id, Date _tarih) {
        return this._aksam;
    }

    public String get_yatsi(int ilce_id, Date _tarih) {
        return this._yatsi;
    }

    public String get_kible(int ilce_id, Date _tarih) {
        return this._kible;
    }

    public String get_teheccud(int ilce_id, Date _tarih) {
        return this._teheccud;
    }

}
