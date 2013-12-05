package com.example.sqlitedemo.bean;

import java.io.Serializable;
import java.util.Arrays;


public class Restaurant  implements Serializable {
	private Data[] data;
	


	public Data[] getData() {
		return data;
	}


	public void setData(Data[] data) {
		this.data = data;
	}


	public static class Data implements Serializable{
		private long _id;
		private String id;
		private String name ;  
		private String content ;

		public long get_id() {
			return _id;
		}
		public void set_id(long _id) {
			this._id = _id;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
		
	
	}
	@Override
	public String toString() {
		return "Restaurant  [data=" + Arrays.toString(data) 
				+  "]";
	}
}
