package net.huseyinsekmenoglu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import net.huseyinsekmenoglu.namazvaktim.R;

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
    //The Android's default system path of your application database.
    private static final String DB_PATH = "/data/data/net.huseyinsekmenoglu.namazvaktim/databases/";
    private static final String DB_NAME = "namazvaktim.db";
    private static final int DB_VERSION = 1;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    //Constructor
    //Takes reference of the passed context to access to the application assets and resources.
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
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
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    // Copies database from assets-folder to the system folder by transfering bytestream.
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
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
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // ülkeler
    public ArrayList<String> getCountries() {
        ArrayList<String> liste = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT " + Ulke.Ad + " FROM " + Ulke.name + " order by " + Ulke.Ad;
        // database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String tmp;
        // looping through all rows and adding to list
        liste.add(myContext.getString(R.string.Turkiye));
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(cursor.getColumnIndex(Ulke.Ad));
                if (!tmp.equals(myContext.getString(R.string.Turkiye))) liste.add(tmp);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // return
        return liste;
    }

    // şehirler
    public ArrayList<String> getCities(String country) {
        ArrayList<String> liste = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT " + Sehir.name + "." + Sehir.Ad +
                " FROM " + Sehir.name + " INNER JOIN " + Ulke.name + " ON " + Sehir.name + "." + Sehir.Ulke_id + " = " + Ulke.name + "." + Ulke._id +
                " WHERE " + Ulke.name + "." + Ulke.Ad + " = '" + country + "' ORDER BY " + Sehir.name + "." + Sehir.Ad;
        // database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String tmp;
        // looping through all rows and adding to list
        if (country.equals(myContext.getString(R.string.Turkiye)))
            liste.add(myContext.getString(R.string.Istanbul));
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(cursor.getColumnIndex(Sehir.Ad));
                if (!tmp.equals(myContext.getString(R.string.Istanbul))) liste.add(tmp);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // return
        return liste;
    }

    // ilçeler
    public ArrayList<String> getTown(String city) {
        ArrayList<String> liste = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT " + Ilce.name + "." + Ilce.Ad +
                " FROM " + Ilce.name + " INNER JOIN " + Sehir.name + " ON " + Ilce.name + "." + Ilce.Sehir_id + " = " + Sehir.name + "." + Sehir._id +
                " WHERE " + Sehir.name + "." + Sehir.Ad + " = '" + city + "' ORDER BY " + Ilce.name + "." + Ilce.Ad;
        // database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String tmp;
        // looping through all rows and adding to list
        if (city.equals(myContext.getString(R.string.Istanbul)))
            liste.add(myContext.getString(R.string.Istanbul));
        if (cursor.moveToFirst()) {
            do {
                tmp = cursor.getString(cursor.getColumnIndex(Ilce.Ad));
                if (!tmp.equals(myContext.getString(R.string.Istanbul))) liste.add(tmp);
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // return
        return liste;
    }

    /*add new record to vakit table*/
    public boolean insertNewVakit(Vakit tablo) {
        SQLiteDatabase db = this.getReadableDatabase();
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
        db.insert(Vakit.name, null, newRow);
        db.close();
        return true;
    }

    //check if vakit exists
    public boolean VakitVar(int ilce, String tarih) {
        boolean sonuc = false;
        String selectQuery = "SELECT " + Vakit._id + " FROM " + Vakit.name + " WHERE " + Vakit.Ilce_id + "=" + ilce + " and " + Vakit.tarih + "='" + tarih + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            sonuc = true;
        }
        cursor.close();
        db.close();
        return sonuc;
    }

    //returns id of a table
    public String GetAnyID(String tablo, String Ad) {
        String sonuc = "";
        String selectQuery = "SELECT _id FROM " + tablo + " WHERE Ad='" + Ad + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            sonuc = cursor.getString(cursor.getColumnIndex(Ulke._id));
        }
        cursor.close();
        db.close();
        return sonuc;
    }

    //get todays namaz vakits for selected towm
    public Vakit getVakit(int IlceId, String Tarih) {
        Vakit tablo = new Vakit();
        String selectQuery = "SELECT * FROM " + Vakit.name + " WHERE " + Vakit.Ilce_id + "=" + IlceId + " AND " + Vakit.tarih + "='" + Tarih + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            tablo.SetId(cursor.getInt(cursor.getColumnIndex(Vakit._id)));
            tablo.SetImsak(cursor.getString(cursor.getColumnIndex(Vakit.imsak)));
            tablo.SetGunes(cursor.getString(cursor.getColumnIndex(Vakit.gunes)));
            tablo.SettOgle(cursor.getString(cursor.getColumnIndex(Vakit.ogle)));
            tablo.SetIkindi(cursor.getString(cursor.getColumnIndex(Vakit.ikindi)));
            tablo.SetAksam(cursor.getString(cursor.getColumnIndex(Vakit.aksam)));
            tablo.SetYatsi(cursor.getString(cursor.getColumnIndex(Vakit.yatsi)));
            tablo.SetKible(cursor.getString(cursor.getColumnIndex(Vakit.kible)));
        }
        cursor.close();
        db.close();
        return tablo;
    }
}
