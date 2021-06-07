package com.master.mipasapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.master.mipasapp.R;
import com.master.mipasapp.app.MyApp;
import com.master.mipasapp.model.DatabaseSQLiteOpenHelper;

import static android.content.ContentValues.TAG;

public class ConfigurationActivity extends AppCompatActivity {
    String checkdatabasecontect = "Madrid";
    Button button_checkSqldataBase;
    TextView textViewaux;
    TextView textView_cnf_requestid;
    TextView textView_cnf_name;
    TextView textView_cnf_geo;
    TextView textView_cnf_url;
    TextView textView_cnf_time;
    TextView textView_cnf_temp;
    TextView textView_cnf_wind;
    TextView textView_cnf_humidity;
    TextView textView_cnf_pressure;
    TextView textView_cnf_co;
    TextView textView_cnf_no2;
    TextView textView_cnf_o3;
    TextView textView_cnf_pm10;
    TextView textView_cnf_pm25;
    TextView textView_cnf_so2;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        button_checkSqldataBase = (Button)findViewById(R.id.button_checkSqldataBase_2);
        textViewaux = (TextView)findViewById(R.id.textViewaux);

        textView_cnf_requestid = (TextView)findViewById(R.id.textView_cnf_requestid);
        textView_cnf_name = (TextView)findViewById(R.id.textView_cnf_name);;
        textView_cnf_geo = (TextView)findViewById(R.id.textView_cnf_geo);;
        textView_cnf_url = (TextView)findViewById(R.id.textView_cnf_url);;
        textView_cnf_time = (TextView)findViewById(R.id.textView_cnf_time);;
        textView_cnf_temp = (TextView)findViewById(R.id.textView_cnf_temp);;
        textView_cnf_wind = (TextView)findViewById(R.id.textView_cnf_wind);;
        textView_cnf_humidity = (TextView)findViewById(R.id.textView_cnf_humidity);;
        textView_cnf_pressure = (TextView)findViewById(R.id.textView_cnf_pressure);;
        textView_cnf_co = (TextView)findViewById(R.id.textView_cnf_co);;
        textView_cnf_no2 = (TextView)findViewById(R.id.textView_cnf_no2);;
        textView_cnf_o3 = (TextView)findViewById(R.id.textView_cnf_o3);;
        textView_cnf_pm10 = (TextView)findViewById(R.id.textView_cnf_pm10);;
        textView_cnf_pm25 = (TextView)findViewById(R.id.textView_cnf_pm25);;
        textView_cnf_so2 = (TextView)findViewById(R.id.textView_cnf_so2);;

        checkSqldataBase();
    }

    private void checkSqldataBase() {

/*        DatabaseSQLiteOpenHelper databaseAirQuality = new DatabaseSQLiteOpenHelper(this, "databaseAirQuality",null, 1);
        SQLiteDatabase DataBaseAirQuality = databaseAirQuality.getWritableDatabase();
        Cursor aux = DataBaseAirQuality.rawQuery(
                "select time from airquality where name =" + checkdatabasecontect,
                null
        );*/

        button_checkSqldataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseSQLiteOpenHelper databaseAirQuality = MyApp.getDatabaseAirQuality();
                SQLiteDatabase DataBaseAirQuality = databaseAirQuality.getWritableDatabase();
                /**
                 * Recordemos la estructura de la base de datos SQlite que hemos creado:
                 * airquality (
                 * requestid integer primary key autoincrement,
                 * name         text,
                 * geolocation  text,
                 * url          text,
                 * time         text,
                 * temperature  real,
                 * wind         real,
                 * humidity     real,
                 * pressure     real,
                 * co           real,
                 * no2          real,
                 * o3           real,
                 * pm10         real,
                 * pm25         real,
                 * so2          real
                 * )
                 *
                 * */
                Cursor row = DataBaseAirQuality.rawQuery("select * from airquality where name = 'Madrid'",null);

                if (row.moveToFirst()){
                     textView_cnf_requestid.setText("requestid: " + row.getString(0));
                    textView_cnf_name.setText("name: " + row.getString(1));
                    textView_cnf_geo.setText("geolocation: " + row.getString(2));
                    textView_cnf_url.setText("url: " + row.getString(3));
                    textView_cnf_time.setText("time: " + row.getString(4));
                    textView_cnf_temp.setText("temperature: " + row.getString(5));
                    textView_cnf_wind.setText("wind: " + row.getString(6));
                    textView_cnf_humidity.setText("humidity: " + row.getString(7));
                    textView_cnf_pressure.setText("pressure: " + row.getString(8));
                    textView_cnf_co.setText("co: " + row.getString(9));
                    textView_cnf_no2.setText("no2: " + row.getString(10));
                    textView_cnf_o3.setText("o3: " + row.getString(11));
                    textView_cnf_pm10.setText("pm10: " + row.getString(12));
                    textView_cnf_pm25.setText("pm25: " + row.getString(13));
                    textView_cnf_so2.setText("so2: " + row.getString(13));
                }
                Log.i(TAG," Command getPath is: "+DataBaseAirQuality.getPath().toString());
                Log.i(TAG," Command getAttachedDbs is: "+DataBaseAirQuality.getAttachedDbs().toString());
                Log.i(TAG," Command getMaximumSize is: "+DataBaseAirQuality.getMaximumSize());
                Log.i(TAG," Command getPageSize is: "+DataBaseAirQuality.getPageSize());
                Log.i(TAG," Command getVersion is: "+DataBaseAirQuality.getVersion());
                Log.i(TAG," Command getClass is: "+DataBaseAirQuality.getClass().toString());
                Log.i(TAG," Command isDatabaseIntegrityOk is: "+DataBaseAirQuality.isDatabaseIntegrityOk());
                Log.i(TAG," Command isDbLockedByCurrentThread is: "+DataBaseAirQuality.isDbLockedByCurrentThread());
                Log.i(TAG," Command isOpen is: "+DataBaseAirQuality.isOpen());
                Log.i(TAG," Command isReadOnly is: "+DataBaseAirQuality.isReadOnly());
                Log.i(TAG," Command isWriteAheadLoggingEnabled is: "+DataBaseAirQuality.isWriteAheadLoggingEnabled());

                DataBaseAirQuality.close();
                Log.i(TAG," Command isOpen is: "+DataBaseAirQuality.isOpen());

                i++;
                textViewaux.setText("count: " + i);

            }
        });


    }
}