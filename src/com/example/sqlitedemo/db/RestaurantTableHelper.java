package com.example.sqlitedemo.db;

import java.util.ArrayList;
import java.util.List;



import com.example.sqlitedemo.bean.Restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class RestaurantTableHelper {
	public static  void saveRestaurant(Context context,Restaurant.Data data){
		Uri uri = Uri.withAppendedPath(Tables.Restaurant.CONTENT_URI, "");
		ContentValues values = new ContentValues();
		values.put(Tables.Restaurant.ID, data.getId());
		values.put(Tables.Restaurant.RESTAURANT_NAME, data.getName());
		values.put(Tables.Restaurant.RESTAURANT_CONTENT, data.getContent());			
		context.getContentResolver().insert(uri, values);		
	}
	
	//
	public static  List<Restaurant.Data> getRestaurants(Context context) {
		Uri uri = Uri.withAppendedPath(Tables.Restaurant.CONTENT_URI, "");
		Cursor cursor = null;
		List<Restaurant.Data> data = new ArrayList<Restaurant.Data>();
		try {
			//查出id为uid的且未被用户自主删除的提醒列表
			cursor = context.getContentResolver().query(uri, null,
					null,
					null, null);
			if (cursor.moveToFirst()) {
				do {
					Restaurant.Data rs = ContentValuesUtil.convertRemind(cursor);
					//mCateList.add(st);
					Log.d("restaurant ", "_Id: "+rs.get_id()+"id = "+ rs.getId() +" name = " + rs.getName() +"content = "+ rs.getContent());
					data.add(rs);
				} while (cursor.moveToNext());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return data;
	}
	
	public static int getAllCount(Context context){
		int count = 0;
		Uri uri = Uri.withAppendedPath(Tables.Restaurant.CONTENT_URI, "");
		Cursor cursor = null;
		List<Restaurant.Data> data = new ArrayList<Restaurant.Data>();
		try {
			//查出id为uid的且未被用户自主删除的提醒列表
			cursor = context.getContentResolver().query(uri, null,
					null,
					null, null);
			count = cursor.getCount();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return count;
	}
	public static  Restaurant.Data getOneRes(Context context,String id){
		
		Uri uri = Uri.withAppendedPath(Tables.Restaurant.CONTENT_URI, "");
		Cursor cursor = null;
		Restaurant.Data data = new Restaurant.Data();
		try {
			//查出id为uid的且未被用户自主删除的提醒列表
			cursor = context.getContentResolver().query(uri, null,
					Tables.Restaurant._ID + " =? ",
					new String[] { id + "" }, null);
			if (cursor.moveToFirst()) {
				do {
					data = ContentValuesUtil.convertRemind(cursor);
					//mCateList.add(st);					
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return data;
		
	}
}
