package net.huseyinsekmenoglu.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import net.huseyinsekmenoglu.namazvaktim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by huseyin.sekmenoglu on 11.2.2016.
 * http://developer.android.com/training/basics/network-ops/connecting.html
 * https://gelecegiyazanlar.turkcell.com.tr/konu/android/egitim/android-301/asynctask
 * (http://developer.android.com/reference/android/os/AsyncTask.html)
 *
 * AsyncTask abstract bir sınıftır, kullanılması için başka sınıf üzerinden extend edilmesi gerekir.
 * AsyncTask sınıflarında doInBackground zorunludur ve arka planda gerçekleştirilecek bütün işlemler bu metod içerisinde yazılır.
 *
 * AsyncTask’ın oluşturulma yapısı "AsyncTask < Tip1,Tip2,Tip3 >"  şeklindedir.
 * Tip1 doInBackground metoduna verilecek parametrelerin tipini ya da sınıfını belirler
 * Tip2 doInBackground metodunun onProgressUpdate metoduna paslanacak değişkenin tipini belirtir.
 * Tip3 ise onPostExecute metoduna verilen değişkendir ve doInBackground metodunun return tipidir.
 */
public class ApiConnect extends AsyncTask<String, Integer, String> {
    private ProgressDialog progressDialog;
    private Context myContext;
    private Activity myActivity;


    public ApiConnect(Activity activity) {
        this.myActivity = activity;
        this.myContext = activity.getApplicationContext();
        progressDialog = new ProgressDialog(activity);
    }

    //Arka plan işlemi başlamadan önce ön yüzde değiştirilmesi istenen değişkenlerin ataması yapılır
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(String.valueOf((R.string.Updating)));
        progressDialog.show();


    }

    //Arka planda yapılması istenen işlem burada gerçekleşir.
    //Eğer buradaki işlemler sonucunda ana akışa bir değişken gönderilmesi gerekiyorsa
    // return metodu ile bu değişken onPostExecute metoduna paslanabilir.
    @Override
    protected String doInBackground(String... params) {

        // params comes from the execute() call: params[0] is the url.
        InputStream is = null;
        try {
            int len = 500;
            String contentAsString = "";
            URL url = new URL(String.format("http://diyanet-api.herokuapp.com/namaz_vakti/%s/Aylik", params[0]));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //if connect properly
            if (response == 200) {
                is = conn.getInputStream();
                // Convert the InputStream into a string
                contentAsString = readIt(is, len);
                //
                JSONObject jsonReader = new JSONObject(contentAsString);
                JSONArray names = jsonReader.names();
                if (names == null) return String.valueOf((R.string.errorConnection));
                for (int i = 0; i < names.length(); i++) {
                    String name = names.getString(i);

                    //treeMap.put(jsonReader.getString(name), Integer.parseInt(name));
                }
                //veritabanına kaydet


                contentAsString = "Updated";
            }
            return contentAsString;

        } catch (IOException e) {
            return String.valueOf((R.string.errorConnection));

        } catch (JSONException e) {
            return String.valueOf((R.string.errorConnection));
        } finally {
            // Makes sure that the InputStream is closed after the app is finished using it.
            if (is != null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    //Arka plandaki işlemden gelen veri önde gösterilmek isteniyorsa bu metod içinde yapılabilir.
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.equals("Updated")) {
            Toast.makeText(myActivity, "Updated: " + R.string.Updated, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(myActivity, "result: " + result, Toast.LENGTH_LONG).show();
        }
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    // Eğer doInBackground metodu içerisinde yaptığınız işlemin ilerleme durumunu bildirir.
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Integer currentProgress = values[0];
        progressDialog.setProgress(currentProgress);
    }

    //Eğer herhangi bir sebepten dolayı AsyncTask iptal edilirse bu metod uyarılır.
    // Burada kullanıdığınız kaynakları temizleyebilirsiniz.
    @Override
    protected void onCancelled(String result) {
        super.onCancelled(result);
        if (progressDialog.isShowing()) progressDialog.dismiss();
    }

    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException {
        Reader reader;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
