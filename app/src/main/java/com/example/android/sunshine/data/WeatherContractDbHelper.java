package com.example.android.sunshine.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.sunshine.data.WeatherContract.*;

/**
 * Created by Rahul B Gautam on 4/11/18.
 */

public class WeatherContractDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "weather.db";

    private static final int DATABASE_VERSION = 3;

    public WeatherContractDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*
         * This String will contain a simple SQL statement that will create a table that will
         * cache our weather data.
         */
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + WeatherEntry.TABLE_NAME + " (" +
//              COMPLETED (1) Append NOT NULL to each column's type declaration except for the _ID
                /*
                 * WeatherEntry did not explicitly declare a column called "_ID". However,
                 * WeatherEntry implements the interface, "BaseColumns", which does have a field
                 * named "_ID". We use that here to designate our table's primary key.
                 */
                        WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL, " +

                        WeatherEntry.COLUMN_MIN_TEMP + " REAL NOT NULL, " +
                        WeatherEntry.COLUMN_MAX_TEMP + " REAL NOT NULL, " +

                        WeatherEntry.COLUMN_HUMIDITY + " REAL NOT NULL, " +
                        WeatherEntry.COLUMN_PRESSURE + " REAL NOT NULL, " +

                        WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, " +
                        WeatherEntry.COLUMN_DEGREES + " REAL NOT NULL" +

                        /*
                         * To ensure this table can only contain one weather entry per date, we declare
                         * the date column to be unique. We also specify "ON CONFLICT REPLACE". This tells
                         * SQLite that if we have a weather entry for a certain date and we attempt to
                         * insert another weather entry with that date, we replace the old weather entry.
                         */
                        " UNIQUE (" + WeatherEntry.COLUMN_DATE + ") ON CONFLICT REPLACE);";
        /*
         * After we've spelled out our SQLite table creation statement above, we actually execute
         * that SQL with the execSQL method of our SQLite database object.
         */
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
