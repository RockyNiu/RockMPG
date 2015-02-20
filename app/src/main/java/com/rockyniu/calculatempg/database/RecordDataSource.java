package com.rockyniu.calculatempg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.rockyniu.calculatempg.model.Record;

/**
 * Created by Lei on 2015/2/15.
 */

public class RecordDataSource extends BaseDataSource<Record> {

    private static final String TABLE_NAME = BaseSQLiteHelper.TABLE_MPG;
    private static final String[] ALL_COLUMNS = {BaseSQLiteHelper.COLUMN_ID,
            BaseSQLiteHelper.COLUMN_CAR_ID, BaseSQLiteHelper.COLUMN_HOME_ID,
            BaseSQLiteHelper.COLUMN_MILES, BaseSQLiteHelper.COLUMN_GAS,
            BaseSQLiteHelper.COLUMN_RECORD_MILES_TIME,
            BaseSQLiteHelper.COLUMN_MODIFIED_TIME,
            BaseSQLiteHelper.COLUMN_DELETED};

    public RecordDataSource(Context context) {
        super(context, TABLE_NAME, ALL_COLUMNS);
    }

    public RecordDataSource(Context context, int version) {
        super(context, version, TABLE_NAME, ALL_COLUMNS);
    }

    @Override
    ContentValues toValuesWithoutId(Record record) {
        ContentValues values = new ContentValues();
//        values.put(com.rockyniu.mpgcalculator.database.BaseSQLiteHelper.COLUMN_ID, record.getId());
        values.put(BaseSQLiteHelper.COLUMN_CAR_ID, record.getCarId());
        values.put(BaseSQLiteHelper.COLUMN_HOME_ID, record.getHomeId());
        values.put(BaseSQLiteHelper.COLUMN_MILES, record.getMiles());
        values.put(BaseSQLiteHelper.COLUMN_GAS, record.getGas());
        values.put(BaseSQLiteHelper.COLUMN_RECORD_MILES_TIME, record.getRecordMilesTime());
        values.put(BaseSQLiteHelper.COLUMN_MODIFIED_TIME, record.getModifiedTime());
        values.put(BaseSQLiteHelper.COLUMN_DELETED, record.isDeleted());
        return values;
    }

    @Override
    Record cursorToItem(Cursor cursor) {
        Record record = new Record();
        record.setId(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_ID)));
        record.setCarId(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_CAR_ID)));
        record.setHomeId(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_HOME_ID)));
        record.setMiles(cursor.getFloat(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_MILES)));
        record.setGas(cursor.getFloat(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_GAS)));
        record.setRecordMilesTime(cursor.getLong(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_RECORD_MILES_TIME)));
        record.setModifiedTime(cursor.getLong(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_MODIFIED_TIME)));
        record.setDeleted(cursor.getInt(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_DELETED)) == 1 ? true : false);
        return record;
    }
}