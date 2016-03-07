package net.sekmetech.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.sekmetech.namazvaktim.R;
import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by huseyin on 2.2.2016
 * http://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 */
public class Database extends SQLiteOpenHelper {
    private Context mContext;
    private SQLiteDatabase mDataBase;
    private String DB_PATH, DB_NAME, select, from, and, where, orderby, innerjoin, on,
            nokta, kesme, esit, carpi, bosluk, password;

    //Constructor
    //Takes reference of the passed context to access to the application assets and resources.
    public Database(Context context) {
        super(context, context.getString(R.string.dbName), null, R.string.dbVersion);
        this.mContext = context;
        SQLiteDatabase.loadLibs(mContext);
        DB_NAME = mContext.getString(R.string.dbName);
        DB_PATH = mContext.getDatabasePath(DB_NAME).getPath();
        select = mContext.getString(R.string.sqlSelect);
        from = mContext.getString(R.string.sqlFrom);
        where = mContext.getString(R.string.sqlWhere);
        innerjoin = mContext.getString(R.string.sqlInnerJoin);
        on = mContext.getString(R.string.sqlOn);
        and = mContext.getString(R.string.sqlAnd);
        orderby = mContext.getString(R.string.sqlOrderBy);
        nokta = mContext.getString(R.string.nokta);
        esit = mContext.getString(R.string.sqlEquals);
        kesme = "'";
        carpi = mContext.getString(R.string.multpily);
        bosluk = " ";
        password = mContext.getString(R.string.app_name) + R.string.dateFormat + mContext.getString(R.string.dateFormat);
        Log.d("hüseyin", password);
    }

    // Creates a empty database on the system and rewrites it with your own database.
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error(String.valueOf(R.string.errorSetup));
            }
        }
    }

    // Check if the database already exist to avoid re-copying the file
    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, password, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
        }
        if (checkDB != null) checkDB.close();
        return checkDB != null;
    }

    // Copies database from assets-folder to the system folder by transfering bytestream.
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //Open the database
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH, password, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // ülkeler
    public ArrayList<String> getCountries() {
        ArrayList<String> liste = new ArrayList<>();
        // Select All Query
        String selectQuery = select + bosluk + Ulke.Ad + bosluk + from + bosluk + Ulke.name + bosluk +
                orderby + bosluk + Ulke.Ad;
        // database
        openDataBase();
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        String tmp;
        // looping through all rows and adding to list
        liste.add(mContext.getString(R.string.Turkiye));
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(cursor.getColumnIndex(Ulke.Ad));
                if (!tmp.equals(mContext.getString(R.string.Turkiye))) liste.add(tmp);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        mDataBase.close();
        // return
        return liste;
    }

    // şehirler
    public ArrayList<String> getCities(String country) {
        ArrayList<String> liste = new ArrayList<>();
        // Select All Query
        String selectQuery = select + bosluk + Sehir.name + nokta + Sehir.Ad + bosluk + from + bosluk +
                Sehir.name + bosluk + innerjoin + bosluk + Ulke.name + bosluk + on + bosluk + Sehir.name +
                nokta + Sehir.Ulke_id + bosluk + esit + bosluk + Ulke.name + nokta + Ulke._id +
                bosluk + where + bosluk + Ulke.name + nokta + Ulke.Ad + bosluk + esit + bosluk + kesme +
                country + kesme + bosluk + orderby + bosluk + Sehir.name + nokta + Sehir.Ad;
        // database
        openDataBase();
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        String tmp;
        // looping through all rows and adding to list
        if (country.equals(mContext.getString(R.string.Turkiye)))
            liste.add(mContext.getString(R.string.Istanbul));
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(cursor.getColumnIndex(Sehir.Ad));
                if (!tmp.equals(mContext.getString(R.string.Istanbul))) liste.add(tmp);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        mDataBase.close();
        // return
        return liste;
    }

    // ilçeler
    public ArrayList<String> getTown(String city) {
        ArrayList<String> liste = new ArrayList<>();
        // Select All Query
        String selectQuery = select + bosluk + Ilce.name + nokta + Ilce.Ad + bosluk + from + bosluk +
                Ilce.name + bosluk + innerjoin + bosluk + Sehir.name + bosluk + on + bosluk + Ilce.name +
                nokta + Ilce.Sehir_id + bosluk + esit + bosluk + Sehir.name + nokta + Sehir._id +
                bosluk + where + bosluk + Sehir.name + nokta + Sehir.Ad + bosluk + esit + bosluk + kesme +
                city + kesme + bosluk + orderby + bosluk + Ilce.name + nokta + Ilce.Ad;
        // database
        openDataBase();
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        String tmp;
        // looping through all rows and adding to list
        if (city.equals(mContext.getString(R.string.Istanbul)))
            liste.add(mContext.getString(R.string.Istanbul));
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(cursor.getColumnIndex(Ilce.Ad));
                if (!tmp.equals(mContext.getString(R.string.Istanbul))) liste.add(tmp);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        mDataBase.close();
        // return
        return liste;
    }

    /*add new record to vakit table*/
    public boolean insertNewVakit(Vakit tablo) {
        openDataBase();
        ContentValues newRow = new ContentValues();
        newRow.put(Vakit.tarih, tablo.GetTarih());
        newRow.put(Vakit.imsak, tablo.GetImsak());
        newRow.put(Vakit.gunes, tablo.GetGunes());
        newRow.put(Vakit.ogle, tablo.GetOgle());
        newRow.put(Vakit.ikindi, tablo.GetIkindi());
        newRow.put(Vakit.aksam, tablo.GetAksam());
        newRow.put(Vakit.yatsi, tablo.GetYatsi());
        newRow.put(Vakit.kible, tablo.GetKible());
        newRow.put(Vakit.Ilce_id, tablo.GetIlce());
        mDataBase.insert(Vakit.name, null, newRow);
        mDataBase.close();
        return true;
    }

    //check if vakit exists
    public boolean VakitVar(int ilce, String tarih) {
        boolean sonuc = false;
        String selectQuery = select + bosluk + Vakit._id + bosluk + from + bosluk + Vakit.name + bosluk +
                where + bosluk + Vakit.Ilce_id + bosluk + esit + bosluk + ilce + bosluk + and + bosluk +
                Vakit.tarih + bosluk + esit + bosluk + kesme + tarih + kesme;
        openDataBase();
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) sonuc = true;
        cursor.close();
        mDataBase.close();
        return sonuc;
    }

    //returns id of a table
    public String GetAnyID(String tablo, String Ad) {
        String sonuc = "";
        String selectQuery = select + bosluk + Ulke._id + bosluk + from + bosluk + tablo + bosluk +
                where + bosluk + Ulke.Ad + bosluk + esit + bosluk + kesme + Ad + kesme;
        openDataBase();
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) sonuc = cursor.getString(cursor.getColumnIndex(Ulke._id));
        cursor.close();
        mDataBase.close();
        return sonuc;
    }

    //kıble açısını göster
    public String GetKible(String townID) {
        String sonuc = "";
        String selectQuery = select + bosluk + Ilce.Kible + bosluk + from + bosluk + Ilce.name + bosluk +
                where + bosluk + Ilce._id + bosluk + esit + bosluk + townID;
        openDataBase();
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) sonuc = cursor.getString(cursor.getColumnIndex(Ilce.Kible));
        cursor.close();
        mDataBase.close();
        return sonuc;
    }

    //get todays namaz vakits for selected towm
    public Vakit getVakit(int IlceId, String Tarih) {
        Vakit tablo = new Vakit();
        String selectQuery = select + bosluk + carpi + bosluk + from + bosluk + Vakit.name + bosluk +
                where + bosluk + Vakit.Ilce_id + bosluk + esit + bosluk + IlceId + bosluk + and + bosluk +
                Vakit.tarih + bosluk + esit + bosluk + kesme + Tarih + kesme;
        openDataBase();
        Cursor cursor = mDataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            tablo.SetId(cursor.getInt(cursor.getColumnIndex(Vakit._id)));
            tablo.SetTarih(cursor.getString(cursor.getColumnIndex(Vakit.tarih)));
            tablo.SetImsak(cursor.getString(cursor.getColumnIndex(Vakit.imsak)));
            tablo.SetGunes(cursor.getString(cursor.getColumnIndex(Vakit.gunes)));
            tablo.SettOgle(cursor.getString(cursor.getColumnIndex(Vakit.ogle)));
            tablo.SetIkindi(cursor.getString(cursor.getColumnIndex(Vakit.ikindi)));
            tablo.SetAksam(cursor.getString(cursor.getColumnIndex(Vakit.aksam)));
            tablo.SetYatsi(cursor.getString(cursor.getColumnIndex(Vakit.yatsi)));
            tablo.SetKible(cursor.getString(cursor.getColumnIndex(Vakit.kible)));
        } else tablo.SetTarih("");
        cursor.close();
        mDataBase.close();
        return tablo;
    }
}
