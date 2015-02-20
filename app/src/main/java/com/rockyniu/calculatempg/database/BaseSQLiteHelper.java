package com.rockyniu.calculatempg.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Lei on 2015/2/15.
 */


public class BaseSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mpg.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MPG = "mpg";
    public static final String TABLE_CAR = "car";
    public static final String TABLE_HOME = "home";

    public static final String COLUMN_ID = "uuid";
    public static final String COLUMN_CAR_ID = "carId";
    public static final String COLUMN_HOME_ID = "homeId";
    public static final String COLUMN_MILES = "miles";
    public static final String COLUMN_GAS = "gas";
    public static final String COLUMN_RECORD_MILES_TIME = "recordMilesTime";
    public static final String COLUMN_MODIFIED_TIME = "modifiedTime"; // last modified time
    public static final String COLUMN_DELETED = "deleted";

    public static final String COLUMN_MAKER = "maker";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_STYLE = "style";
    public static final String COLUMN_COLOR = "color";

    public static final String COLUMN_ZIP_CODE = "zipCode";
    public static final String COLUMN_ADDRESS = "address";

    private static final String MPG_DATABASE_CREATE = "create table "
            + TABLE_MPG + "("
            + COLUMN_ID + " text primary key, "
            + COLUMN_CAR_ID + " integer default 0, "
            + COLUMN_HOME_ID + " text default '', "
            + COLUMN_MILES + " real default 0, "
            + COLUMN_GAS + " real default 0, "
            + COLUMN_RECORD_MILES_TIME + " integer default 0, "
            + COLUMN_MODIFIED_TIME + " integer default 0, "
            + COLUMN_DELETED + " integer default 0, "
            + " FOREIGN KEY (" + COLUMN_CAR_ID + ") REFERENCES "
            + TABLE_CAR + "(" + COLUMN_ID + ")" + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + COLUMN_HOME_ID + ") REFERENCES "
            + TABLE_HOME + "(" + COLUMN_ID + ")"
            + ");";

    private static final String CAR_DATABASE_CREATE = "create table "
            + TABLE_CAR + "("
            + COLUMN_ID + " text primary key, "
            + COLUMN_MAKER + " text default '', "
            + COLUMN_MODEL + " text default '', "
            + COLUMN_YEAR + " text default '', "
            + COLUMN_STYLE + " text default '', "
            + COLUMN_COLOR + " text default '', "
            + COLUMN_MODIFIED_TIME + " integer default 0, "
            + COLUMN_DELETED + " integer default 0 "
            + ");";

    private static final String HOME_DATABASE_CREATE = "create table "
            + TABLE_HOME + "("
            + COLUMN_ID + " text primary key, "
            + COLUMN_ZIP_CODE + " text default '', "
            + COLUMN_ADDRESS + " text default '', "
            + COLUMN_MODIFIED_TIME + " integer default 0, "
            + COLUMN_DELETED + " integer default 0"
            + ");";

    public BaseSQLiteHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public BaseSQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BaseSQLiteHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public BaseSQLiteHelper(Context context, int version) {
        this(context, DATABASE_NAME, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        if (!database.isReadOnly()) {
            // Enable foreign key constraints
            database.execSQL("PRAGMA foreign_keys=ON;");
        }
        database.execSQL(CAR_DATABASE_CREATE);
        database.execSQL(MPG_DATABASE_CREATE);
        database.execSQL(HOME_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(BaseSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MPG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOME);
        onCreate(db);
    }

}