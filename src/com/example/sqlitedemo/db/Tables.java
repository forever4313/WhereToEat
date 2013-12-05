package com.example.sqlitedemo.db;
import android.net.Uri;

/**
 * @author KBETA
 * Êý¾Ý±í
 */
public class Tables {

	public static final String AUTHORITY = "com.example.sqlitedemo";
	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ Tables.AUTHORITY);
	static final String DATABASE_NAME = "sqlitedemo.db";
	static final int DATABASE_VERSION = 10;
	
	
	public static final class Restaurant extends RestaurantsColumns implements android.provider.BaseColumns{
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.sqlitedemo.restaurant";
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.sqlitedemo.restaurant";
		public static final Uri CONTENT_URI = Uri.parse("content://"
				+ Tables.AUTHORITY + "/" + Restaurant.TABLE);
		public static final String TABLE = "restaurants";
		
		static final String CREATE_STATEMENT = "CREATE TABLE "
				+ Restaurant.TABLE + "(" + Restaurant._ID + " "
				+ Restaurant._ID_TYPE + "," + " " + Restaurant.RESTAURANT_NAME+ " "
				+ Restaurant.RESTAURANT_NAME_TYPE + "," + " " + Restaurant.ID + " "
				+ Restaurant.ID_TYPE + "," + " "+ Restaurant.RESTAURANT_CONTENT + " "
				+ Restaurant.RESTAURANT_CONTENT_TYPE +");";
		
	}

	public static class RestaurantsColumns{
		public static final String ID = "id";
		static final String ID_TYPE ="INTEGER NOT NULL";
		static final String _ID_TYPE ="INTEGER PRIMARY KEY AUTOINCREMENT";
		public static final String  RESTAURANT_NAME = "name";
		static final String RESTAURANT_NAME_TYPE = "TEXT NOT NULL";
		public static final String RESTAURANT_CONTENT = "content";		
		static final String RESTAURANT_CONTENT_TYPE = "TEXT NOT NULL";
	}
	
	
	
	
	
}
