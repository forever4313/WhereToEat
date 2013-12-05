package com.example.sqlitedemo.db;


import com.example.sqlitedemo.db.Tables.Restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author user
 * 数据库帮助类
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, Tables.DATABASE_NAME, null,
				Tables.DATABASE_VERSION);
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("DB","开始新建数据库");
		db.execSQL(Restaurant.CREATE_STATEMENT);

		Log.d("DB","数据库新建完毕");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == 10) // From 10 to 11 ( metadata )
		{
			oldVersion = 11;
		}
	}

	/**
	 * @param table
	 * @param valuesArray
	 * @return 事物插入
	 */
	synchronized int bulkInsert(String table, ContentValues[] valuesArray) {
		SQLiteDatabase sqldb = getWritableDatabase();
		sqldb.beginTransaction();
		int inserted = 0;
		try {
			for (ContentValues args : valuesArray) {
				long id = sqldb.insert(table, null, args);
				if (id >= 0) {
					inserted++;
				}
			}
			sqldb.setTransactionSuccessful();
		} finally {
			if (sqldb.inTransaction()) {
				sqldb.endTransaction();
			}
		}
		return inserted;
	}
	
	/**
	 * @param values
	 * @return 插入Restaurant
	 */
	synchronized long insertRestaurant(ContentValues values) {
		SQLiteDatabase sqldb = getWritableDatabase();
		sqldb.beginTransaction();
		try {
			long insertCount = sqldb.insert(Tables.Restaurant.TABLE, null, values);
			sqldb.setTransactionSuccessful();
			return insertCount;
		} finally {
			if (sqldb.inTransaction()) {
				sqldb.endTransaction();
			}
		}
	}
	
	
	/**
	 * @param where
	 * @param whereArgs
	 * @return 删除Restaurant
	 */
	synchronized int deleteRestaurant(String where, String[] whereArgs) {
		SQLiteDatabase sqldb = getWritableDatabase();
		sqldb.beginTransaction();
		try {
			int deleteCount = sqldb.delete(Tables.Restaurant.TABLE, where,
					whereArgs);
			sqldb.setTransactionSuccessful();
			return deleteCount;
		} finally {
			if (sqldb.inTransaction()) {
				sqldb.endTransaction();
			}
		}
	}
	
	

	synchronized int updateRestaurant(long rowId, ContentValues values) {
		if ((rowId < 0)) {
			throw new IllegalArgumentException(
					"loc or meta-data id be provided");
		}
		SQLiteDatabase sqldb = getWritableDatabase();
		sqldb.beginTransaction();
		try {
			String[] whereParams;
			String whereclause;
			whereclause = Tables.Restaurant._ID + " = ? ";
			whereParams = new String[] { Long.toString(rowId) };
			int updateCount = sqldb.update(Tables.Restaurant.TABLE, values,
					whereclause, whereParams);
			sqldb.setTransactionSuccessful();
			return updateCount;
		} finally {
			if (sqldb.inTransaction()) {
				sqldb.endTransaction();
			}
		}
	}
	
	
	
	
	
}
