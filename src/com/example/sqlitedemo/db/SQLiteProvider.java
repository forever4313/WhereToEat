package com.example.sqlitedemo.db;

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author user
 */
public class SQLiteProvider extends ContentProvider {

	/* Action types as numbers for using the UriMatcher */
	private static final int RESTAURANT = 1;
	private static final int RESTAURANT_ID = 2;

	
	
	
	private static UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		SQLiteProvider.sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		SQLiteProvider.sURIMatcher.addURI(Tables.AUTHORITY, "restaurants",
				SQLiteProvider.RESTAURANT);
		SQLiteProvider.sURIMatcher.addURI(Tables.AUTHORITY, "restaurants/#",
				SQLiteProvider.RESTAURANT_ID);
		
	}

	private DatabaseHelper mDbHelper;

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
		if (this.mDbHelper == null) {
			this.mDbHelper = new DatabaseHelper(getContext());
		}
		return true;
	}
	
	@Override
	public String getType(Uri uri) {
		int match = SQLiteProvider.sURIMatcher.match(uri);
		String mime = null;
		switch (match) {
		
		case RESTAURANT:
			mime = Tables.Restaurant.CONTENT_TYPE;
			break;
		case RESTAURANT_ID:
			mime = Tables.Restaurant.CONTENT_ITEM_TYPE;
			break;
		}
		return mime;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		int count = 0;
		switch (sURIMatcher.match(uri)) {
		case RESTAURANT:
			count = this.mDbHelper.deleteRestaurant(where, whereArgs);
			break;
		case RESTAURANT_ID:
			String rowId = uri.getPathSegments().get(1);
			count = this.mDbHelper.deleteRestaurant(Tables.Restaurant._ID
							+ " = "
							+ rowId
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		
			
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		return count;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Log.d( TAG, "insert on "+uri );
		int match = SQLiteProvider.sURIMatcher.match(uri);
		long rowId = -1;
		switch (match) {
		case RESTAURANT:
			rowId = this.mDbHelper.insertRestaurant(values);
			if (rowId > 0) {
				Uri newUserUri = Uri.withAppendedPath(Tables.Restaurant.CONTENT_URI, ""
						+ rowId);
				getContext().getContentResolver()
						.notifyChange(newUserUri, null);
				return newUserUri;
			}
			break;
		case RESTAURANT_ID:
			rowId = this.mDbHelper.insertRestaurant(values);
			if (rowId > 0) {
				Uri newUserUri = Uri.withAppendedPath(Tables.Restaurant.CONTENT_URI, ""
						+ rowId);
				getContext().getContentResolver()
						.notifyChange(newUserUri, null);
				return newUserUri;
			}
			break;		
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String where,
			String[] whereArgs, String sortOrder) {
		int match = SQLiteProvider.sURIMatcher.match(uri);
		String tableName = null;
		String whereclause = null;
		String sortorder = sortOrder;
		List<String> pathSegments = uri.getPathSegments();
		switch (match) {
		case RESTAURANT:
			tableName = Tables.Restaurant.TABLE;
			break;
		case RESTAURANT_ID:
			tableName = Tables.Restaurant.TABLE;
			whereclause = Tables.Restaurant._ID + " = "
					+ new Long(pathSegments.get(1)).longValue();
			break;
		
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		// SQLiteQueryBuilder is a helper class that creates the
		// proper SQL syntax for us.
		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();

		// Set the table we're querying.
		qBuilder.setTables(tableName);

		// If the query ends in a specific record number, we're
		// being asked for a specific record, so set the
		// WHERE clause in our query.
		if (whereclause != null) {
			qBuilder.appendWhere(whereclause);
		}

		// Make the query.
		SQLiteDatabase db = this.mDbHelper.getReadableDatabase();
		Cursor queryCursor = qBuilder.query(db, projection, where,
				whereArgs, null, null, sortorder);
		queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return queryCursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int updates = -1;
		long rowId;
		int match = SQLiteProvider.sURIMatcher.match(uri);
		switch (match) {
		case RESTAURANT_ID:
			rowId = new Long(uri.getPathSegments().get(1)).longValue();
			updates = mDbHelper.updateRestaurant(rowId, values);
			break;
		
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return updates;
	}

}
