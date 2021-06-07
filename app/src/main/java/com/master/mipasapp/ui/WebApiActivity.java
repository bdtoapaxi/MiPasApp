package com.master.mipasapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.master.mipasapp.R;
import com.master.mipasapp.app.MyApp;
import com.master.mipasapp.model.DatabaseSQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class WebApiActivity extends AppCompatActivity {
    TextView textView_web_name;
    TextView textView_web_geo;
    TextView textView_web_url;
    TextView textView_web_time;

    TextView textView_web_temp;
    TextView textView_web_wind;
    TextView textView_web_humidity;
    TextView textView_web_pressure;
    TextView textView_web_co;
    TextView textView_web_no2;
    TextView textView_web_o3;
    TextView textView_web_pm10;
    TextView textView_web_pm25;
    TextView textView_web_so2;

    RequestQueue requestQueue;

    String checkdatabasecontect;
    Button button_checkSqldataBase;
    //
    /**
     * Air Quality Open Data Platform
     * Token: a7cc039f3dbd3bbf0eab310f2419ed5a4e0604db
     * */
    private static final String URL = "https://api.waqi.info/feed/Madrid/?token=a7cc039f3dbd3bbf0eab310f2419ed5a4e0604db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_api);

        textView_web_name = findViewById(R.id.textView_web_name);
        textView_web_geo = findViewById(R.id.textView_web_geo);
        textView_web_url = findViewById(R.id.textView_web_url);
        textView_web_time = findViewById(R.id.textView_web_time);

        textView_web_temp = findViewById(R.id.textView_web_temp);
        textView_web_wind = findViewById(R.id.textView_web_wind);
        textView_web_humidity = findViewById(R.id.textView_web_humidity);
        textView_web_pressure = findViewById(R.id.textView_web_pressure);
        textView_web_co = findViewById(R.id.textView_web_co);
        textView_web_no2 = findViewById(R.id.textView_web_no2);
        textView_web_o3 = findViewById(R.id.textView_web_o3);
        textView_web_pm10 = findViewById(R.id.textView_web_pm10);
        textView_web_pm25 = findViewById(R.id.textView_web_pm25);
        textView_web_so2 = findViewById(R.id.textView_web_so2);

        button_checkSqldataBase = findViewById(R.id.button_checkSqldataBase);


        requestQueue = Volley.newRequestQueue(this);
        //checkSqldataBase();
        //stringRequest();
        jsonObjectRequest();
    }

    private void jsonObjectRequest(){

        //DatabaseSQLiteOpenHelper databaseAirQuality = new DatabaseSQLiteOpenHelper(this, "databaseAirQuality",null, 1);
        DatabaseSQLiteOpenHelper databaseAirQuality = MyApp.getDatabaseAirQuality();
        SQLiteDatabase DataBaseAirQuality = databaseAirQuality.getWritableDatabase();
        ContentValues register = new ContentValues();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            /**
                             * Datos
                             * */
                            textView_web_name.setText("Name : " + response.getJSONObject("data").getJSONObject("city").getString("name"));
                            textView_web_geo.setText("Geo : " + response.getJSONObject("data").getJSONObject("city").getString("geo"));
                            textView_web_url.setText("Url : " + response.getJSONObject("data").getJSONObject("city").getString("url"));
                            textView_web_time.setText("Time : " + response.getJSONObject("data").getJSONObject("time").getString("s"));
                            /**
                             * Madrid Real-time Air Quality Index
                             * */
                            textView_web_temp.setText("Temp : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("t").getString("v"));
                            textView_web_wind.setText("Wind : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("w").getString("v"));
                            textView_web_humidity.setText("Humidity : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("h").getString("v"));
                            textView_web_pressure.setText("Pressure : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("p").getString("v"));

                            textView_web_co.setText("CO : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("co").getString("v"));
                            textView_web_no2.setText("NO2 : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("no2").getString("v"));
                            textView_web_o3.setText("O3 : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("o3").getString("v"));
                            textView_web_pm10.setText("PM10 : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("pm10").getString("v"));
                            textView_web_pm25.setText("PM2.5 : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("pm25").getString("v"));
                            textView_web_so2.setText("SO2 : " + response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("so2").getString("v"));

                            saveOnDatabaseSQLite(response, register,DataBaseAirQuality);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void saveOnDatabaseSQLite(JSONObject response, ContentValues register, SQLiteDatabase DataBaseAirQuality) {
        /**
         * Guardamos los datos obtenidos de la web en la base de datos SQLite
         * estructura de la tabla de SQLite:
         * db.execSQL("create table airquality(name text primary key, geolocation text, url text, time text, temperature real, wind real, humidity real, pressure real, co real, no2 real, o3 real, pm10 real, pm25 real, so2 real )");
         * */
        try {
            checkdatabasecontect = response.getJSONObject("data").getJSONObject("city").getString("name");
            register.put("name", response.getJSONObject("data").getJSONObject("city").getString("name"));
            register.put("geolocation",response.getJSONObject("data").getJSONObject("city").getString("geo"));
            register.put("url",response.getJSONObject("data").getJSONObject("city").getString("url"));
            register.put("time",response.getJSONObject("data").getJSONObject("time").getString("s"));
            register.put("temperature",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("t").getString("v"));
            register.put("wind",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("w").getString("v"));
            register.put("humidity",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("h").getString("v"));
            register.put("pressure",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("p").getString("v"));
            register.put("co",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("co").getString("v"));
            register.put("no2",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("no2").getString("v"));
            register.put("o3",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("o3").getString("v"));
            register.put("pm10",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("pm10").getString("v"));
            register.put("pm25",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("pm25").getString("v"));
            register.put("so2",response.getJSONObject("data").getJSONObject("iaqi").getJSONObject("so2").getString("v"));

            DataBaseAirQuality.insert("airquality", null,register);
            DataBaseAirQuality.close();

            /**
             * Realizamos una prueba de lectura de la base de datos de SQLite para verificar la correcta escritura en ella
             * */

            //checkSqldataBase();
           /* Cursor aux = DataBaseAirQuality.rawQuery(
                    "select name, geolocation,url,time from airquality where name =" + response.getJSONObject("data").getJSONObject("city").getString("name"),
                    null
            );

            Toast.makeText(this,aux.getString(0),Toast.LENGTH_LONG).show();*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkSqldataBase(View view) {

        DatabaseSQLiteOpenHelper databaseAirQuality = new DatabaseSQLiteOpenHelper(this, "databaseAirQuality",null, 1);
        SQLiteDatabase DataBaseAirQuality = databaseAirQuality.getWritableDatabase();
        Cursor aux = DataBaseAirQuality.rawQuery(
                "select time from airquality where name =" + checkdatabasecontect,
                null
        );

        Toast.makeText(this,aux.getString(0),Toast.LENGTH_LONG).show();
    }

}