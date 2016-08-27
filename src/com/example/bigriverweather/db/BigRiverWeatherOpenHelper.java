package com.example.bigriverweather.db;

import com.example.bigriverweather.util.LogUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 根据调试，发现是建表语句出现问题，导致整个程序无法运行。
 * @author Administrator
 *
 */
public class BigRiverWeatherOpenHelper extends SQLiteOpenHelper{

	/*
	 * Province建表语句
	 */
	public static final String CREATE_PROVINCE = "create table Province ("
			+ "id integer primary key autoincrement, " 
			+ "province_name text, "
			+ "province_code text)";
	/*
	 * city建表语句
	 */
	
	public static final String CREATE_CITY = "create table City ("
			+ "id integer primary key autoincrement, " 
			+ "city_name text, " 
			+ "city_code text, " 
			+ "province_id integer)";
	/*
	 * county的建表语句
	 */

	public static final String CREATE_COUNTY = "create table County ("
			+ "id integer primary key autoincrement, " 
			+ "county_name text, " 
			+ "county_code text, " 
			+ "city_id integer)";
	
	
	
	public BigRiverWeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		LogUtil.d("OpenHelper", "Constructor---------");
	}
	
	/**
	 * 数据库建好之后，当第二次
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		LogUtil.d("OpenHelper", "onCreate------first---");
		db.execSQL(CREATE_PROVINCE);//创建province的表
		LogUtil.d("OpenHelper", "onCreate------second---");
		db.execSQL(CREATE_CITY);//创建city的表
		db.execSQL(CREATE_COUNTY);//创建county的表
		LogUtil.d("OpenHelper", "onCreate------last---");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO 自动生成的方法存根
		
	}

}
