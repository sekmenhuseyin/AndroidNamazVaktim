package net.sekmetech.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import net.sekmetech.helpers.Functions;
import net.sekmetech.namazvaktim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by huseyin.sekmenoglu on 11.2.2016.
 * http://developer.android.com/training/basics/network-ops/connecting.html
 * https://gelecegiyazanlar.turkcell.com.tr/konu/android/egitim/android-301/asynctask
 * (http://developer.android.com/reference/android/os/AsyncTask.html)
 * <p/>
 * AsyncTask abstract bir sınıftır, kullanılması için başka sınıf üzerinden extend edilmesi gerekir.
 * AsyncTask sınıflarında doInBackground zorunludur ve arka planda gerçekleştirilecek bütün işlemler bu metod içerisinde yazılır.
 * <p/>
 * AsyncTask’ın oluşturulma yapısı "AsyncTask < Tip1,Tip2,Tip3 >"  şeklindedir.
 * Tip1 doInBackground metoduna verilecek parametrelerin tipini ya da sınıfını belirler
 * Tip2 doInBackground metodunun onProgressUpdate metoduna paslanacak değişkenin tipini belirtir.
 * Tip3 ise onPostExecute metoduna verilen değişkendir ve doInBackground metodunun return tipidir.
 */
public class DatabaseUpdate extends AsyncTask<String, Integer, String> {
    private ProgressDialog progressDialog;
    private Context myContext;
    private Activity myActivity;
    private Functions fn;

    public DatabaseUpdate(Activity activity) {
        this.myActivity = activity;
        this.myContext = activity.getApplicationContext();
        progressDialog = new ProgressDialog(activity);
        fn = new Functions(myContext);
    }

    //Arka plan işlemi başlamadan önce ön yüzde değiştirilmesi istenen değişkenlerin ataması yapılır
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.setMessage(myContext.getString(R.string.Updating));
        progressDialog.show();
    }

    //Arka planda yapılması istenen işlem burada gerçekleşir.
    //Eğer buradaki işlemler sonucunda ana akışa bir değişken gönderilmesi gerekiyorsa
    // return metodu ile bu değişken onPostExecute metoduna paslanabilir.
    @Override
    protected String doInBackground(String... params) {

        // params comes from the execute() call: params[0] is the url.
        try {
            String contentAsString = "";
            URL url = new URL(String.format(myContext.getString(R.string.UpdateLink), params[0]));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod(myContext.getString(R.string.get));
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //if connect properly
            if (response == 200) {
                // Convert the InputStream into a string
                String sb = "", line;
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                while ((line = reader.readLine()) != null) sb += line;
                is.close();
                //veritabanı ile bağlantı kuruyoruz
                Database db = new Database(myContext);
                //gelen veri_string değerini json arraye çeviriyoruz.
                JSONArray jsonArray = new JSONArray(sb);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //verilen tarih ve ilçe için kayır kontrolü
                    String tarih = jsonObject.getString(Vakit.tarih);
                    boolean varmi = db.VakitVar(Integer.parseInt(params[1]), tarih);
                    if (!varmi) {//eğer bu ilçe için o tarihte vakti yoksa
                        Vakit tablo = new Vakit();
                        tablo.SetTarih(tarih);
                        tablo.SetImsak(jsonObject.getString(Vakit.imsak));
                        tablo.SetGunes(jsonObject.getString(Vakit.gunes));
                        tablo.SettOgle(jsonObject.getString(Vakit.ogle));
                        tablo.SetIkindi(jsonObject.getString(Vakit.ikindi));
                        tablo.SetAksam(jsonObject.getString(Vakit.aksam));
                        tablo.SetYatsi(jsonObject.getString(Vakit.yatsi));
                        tablo.SetKible(jsonObject.getString(Vakit.kible));
                        tablo.SetIlce(Integer.parseInt(params[1]));
                        //yeni vakit kaydı ekle
                        db.insertNewVakit(tablo);
                    }
                }
                contentAsString = myContext.getString(R.string.Updated);
            }
            return contentAsString;

        } catch (IOException e) {
            return myContext.getString((R.string.errorConnection));

        } catch (JSONException e) {
            return myContext.getString((R.string.errorConnection));
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    //Arka plandaki işlemden gelen veri önde gösterilmek isteniyorsa bu metod içinde yapılabilir.
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.equals(myContext.getString(R.string.Updated))) {
            //save to preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(myContext);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(myContext.getString(R.string.prefUpdate), new Date().getTime());
            editor.apply();
            //show updated message
            Toast.makeText(myActivity, myContext.getString(R.string.Updated), Toast.LENGTH_SHORT).show();
            myActivity.recreate();
            fn.Notification();
        } else {//internete bağlanamadı diye mesaj gönder
            Toast.makeText(myActivity, result, Toast.LENGTH_SHORT).show();
        }
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    // Eğer doInBackground metodu içerisinde yaptığınız işlemin ilerleme durumunu bildirir.
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    //Eğer herhangi bir sebepten dolayı AsyncTask iptal edilirse bu metod uyarılır.
    // Burada kullandığınız kaynakları temizleyebilirsiniz.
    @Override
    protected void onCancelled(String result) {
        super.onCancelled(result);
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }
}
