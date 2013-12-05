package com.example.sqlitedemo.db;

import com.example.sqlitedemo.bean.Restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;



/**
 * @author user
 * 数据库ContentValue转换器
 */
public class ContentValuesUtil {

	
	/**
	 * @param cursor
	 * @return 保存的列表的转换
	 */
	public static Restaurant.Data convertRemind(Cursor cursor) {
		ContentValues values = new ContentValues();
		DatabaseUtils.cursorRowToContentValues(cursor, values);
		Restaurant.Data rs = new Restaurant.Data();
		rs.set_id(values.getAsLong(Tables.Restaurant._ID));
		rs.setId(values.getAsString(Tables.Restaurant.ID));
		rs.setName(values.getAsString(Tables.Restaurant.RESTAURANT_NAME));
		rs.setContent(values.getAsString(Tables.Restaurant.RESTAURANT_CONTENT));		
		return rs;
	}
	
	
}
