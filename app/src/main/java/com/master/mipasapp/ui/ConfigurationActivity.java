package com.master.mipasapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.master.mipasapp.R;
import com.master.mipasapp.app.MyApp;
import com.master.mipasapp.model.DatabaseSQLiteOpenHelper;

public class ConfigurationActivity extends AppCompatActivity {
    String checkdatabasecontect = "Madrid";
    Button button_checkSqldataBase;
    TextView textViewaux;
    int i = 0;

    //DatabaseSQLiteOpenHelper databaseAirQuality = new DatabaseSQLiteOpenHelper(this, "databaseAirQuality",null, 1);
    //SQLiteDatabase DataBaseAirQuality = databaseAirQuality.getWritableDatabase();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        button_checkSqldataBase = (Button)findViewById(R.id.button_checkSqldataBase_2);
        textViewaux = (TextView)findViewById(R.id.textViewaux);

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
                //Toast.makeText(ConfigurationActivity.this, "HOOOOOLAAA", Toast.LENGTH_SHORT).show();
                //Cursor aux = DataBaseAirQuality.rawQuery("select time from airquality where name =" + checkdatabasecontect,null);

                DatabaseSQLiteOpenHelper databaseAirQuality = MyApp.getDatabaseAirQuality();
                SQLiteDatabase DataBaseAirQuality = databaseAirQuality.getWritableDatabase();
                Cursor aux = DataBaseAirQuality.rawQuery(
                        "select time from airquality where name = Madrid",
                        null
                );
                Toast.makeText(ConfigurationActivity.this,aux.getString(0),Toast.LENGTH_LONG).show();
                i++;
                textViewaux.setText("count: " + i);

            }
        });


    }
}