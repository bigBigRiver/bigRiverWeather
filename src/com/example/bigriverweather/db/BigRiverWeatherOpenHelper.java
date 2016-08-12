package com.example.bigriverweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BigRiverWeatherOpenHelper extends SQLiteOpenHelper{

	/*
	 * Province�������
	 */
	public static final String CREATE_PROVINCE = "create table Province (" +
			"id integer primary key autoincrement , province_name text," +
			"province_code text)";
	/*
	 * city�������
	 */
	public static final String CREATE_CITY = "create table City (id integer primary" +
			"key autoincrement,city_name text,city_code text,province_id integer)";
	
	/*
	 * county�Ľ������
	 */
	public static final String CREATE_COUNTY = "create table County("
			+"id integer primary key autoincrement"
			+"county_name text"
			+"county_code text"
			+"city_id integer)";
	
	
	
	public BigRiverWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);//����province�ı�
		db.execSQL(CREATE_CITY);//����city�ı�
		db.execSQL(CREATE_COUNTY);//����county�ı�
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO �Զ����ɵķ������
		
	}

}
