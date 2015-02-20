package com.rockyniu.calculatempg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.rockyniu.calculatempg.model.Car;

/**
 * Created by Lei on 2015/2/16.
 */
public class CarDataSource extends BaseDataSource<Car> {

    private static final String TABLE_NAME = BaseSQLiteHelper.TABLE_CAR;
    private static final String[] ALL_COLUMNS = {BaseSQLiteHelper.COLUMN_ID,
            BaseSQLiteHelper.COLUMN_MAKER, BaseSQLiteHelper.COLUMN_MODEL,
            BaseSQLiteHelper.COLUMN_YEAR, BaseSQLiteHelper.COLUMN_STYLE,
            BaseSQLiteHelper.COLUMN_COLOR};

    protected CarDataSource(Context context) {
        super(context, TABLE_NAME, ALL_COLUMNS);
    }

    protected CarDataSource(Context context, int version) {
        super(context, version, TABLE_NAME, ALL_COLUMNS);
    }

    @Override
    ContentValues toValuesWithoutId(Car car) {
        ContentValues values = new ContentValues();
//        values.put(com.rockyniu.mpgcalculator.database.BaseSQLiteHelper.COLUMN_ID, car.getId());
        values.put(BaseSQLiteHelper.COLUMN_MAKER, car.getMaker());
        values.put(BaseSQLiteHelper.COLUMN_MODEL, car.getModel());
        values.put(BaseSQLiteHelper.COLUMN_YEAR, car.getYear());
        values.put(BaseSQLiteHelper.COLUMN_STYLE, car.getStyle());
        values.put(BaseSQLiteHelper.COLUMN_COLOR, car.getColor());
        values.put(BaseSQLiteHelper.COLUMN_MODIFIED_TIME, car.getModifiedTime());
        values.put(BaseSQLiteHelper.COLUMN_DELETED, car.isDeleted());
        return values;
    }

    @Override
    Car cursorToItem(Cursor cursor) {
        Car car = new Car();
        car.setId(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_ID)));
        car.setMaker(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_MAKER)));
        car.setModel(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_MODEL)));
        car.setYear(cursor.getInt(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_YEAR)));
        car.setStyle(cursor.getString(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_STYLE)));
        car.setModifiedTime(cursor.getLong(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_MODIFIED_TIME)));
        car.setDeleted(cursor.getInt(cursor
                .getColumnIndex(BaseSQLiteHelper.COLUMN_DELETED)) == 1 ? true : false);
        return car;
    }
}
