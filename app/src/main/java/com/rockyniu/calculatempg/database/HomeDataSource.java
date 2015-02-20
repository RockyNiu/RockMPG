package com.rockyniu.calculatempg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.rockyniu.calculatempg.model.Home;

/**
 * Created by Lei on 2015/2/16.
 */
public class HomeDataSource extends BaseDataSource<Home> {
    private static final String TABLE_NAME = BaseSQLiteHelper.TABLE_HOME;
    private static final String[] ALL_COLUMNS = {BaseSQLiteHelper.COLUMN_ID,
            BaseSQLiteHelper.COLUMN_ZIP_CODE, BaseSQLiteHelper.COLUMN_ADDRESS};

    public HomeDataSource(Context context) {
        super(context, TABLE_NAME, ALL_COLUMNS);
    }

    public HomeDataSource(Context context, int version) {
        super(context, version, TABLE_NAME, ALL_COLUMNS);
    }

    @Override
    ContentValues toValuesWithoutId(Home home) {
        ContentValues values = new ContentValues();
//        values.put(com.rockyniu.mpgcalculator.database.BaseSQLiteHelper.COLUMN_ID, home.getId());
        values.put(BaseSQLiteHelper.COLUMN_HOME_ID, home.getZipCode());
        values.put(BaseSQLiteHelper.COLUMN_MILES, home.getAddress());
        values.put(BaseSQLiteHelper.COLUMN_MODIFIED_TIME, home.getModifiedTime());
        values.put(BaseSQLiteHelper.COLUMN_DELETED, home.isDeleted());
        return values;
    }

    @Override
    Home cursorToItem(Cursor cursor) {
        Home home = new Home();
        home.setId(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_ID)));
        home.setZipCode(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_ZIP_CODE)));
        home.setAddress(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_ADDRESS)));
        home.setModifiedTime(cursor.getLong(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_MODIFIED_TIME)));
        home.setDeleted(cursor.getInt(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_DELETED)) == 1 ? true : false);
        return home;
    }
}
