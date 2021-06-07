package com.master.mipasapp.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.FirebaseApp;
import com.master.mipasapp.model.DatabaseSQLiteOpenHelper;

public class MyApp extends Application {
    /**
     * DatabaseSQLiteOpenHelper databaseAirQuality = new DatabaseSQLiteOpenHelper(this, "databaseAirQuality",null, 1);
     * SQLiteDatabase DataBaseAirQuality = databaseAirQuality.getWritableDatabase();
     *
     * */
    private static DatabaseSQLiteOpenHelper databaseAirQuality;
    private SQLiteDatabase DataBaseAirQuality;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseAirQuality = new DatabaseSQLiteOpenHelper(this, "databaseAirQuality",null, 1);
        //DataBaseAirQuality = databaseAirQuality.getWritableDatabase();

        FirebaseApp.initializeApp(this);
    }
    public static DatabaseSQLiteOpenHelper getDatabaseAirQuality(){
        return databaseAirQuality;
    }
}
