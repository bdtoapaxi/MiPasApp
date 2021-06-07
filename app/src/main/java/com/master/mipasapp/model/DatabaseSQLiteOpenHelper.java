package com.master.mipasapp.model;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSQLiteOpenHelper extends SQLiteOpenHelper{


    public DatabaseSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creamos la tabla donde vamos a guardar nuestros datos
        db.execSQL("create table airquality(name text primary key, geolocation text, url text, time text, temperature real, wind real, humidity real, pressure real, co real, no2 real, o3 real, pm10 real, pm25 real, so2 real )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
