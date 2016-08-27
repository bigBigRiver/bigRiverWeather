package com.example.bigriverweather.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.bigriverweather.model.City;
import com.example.bigriverweather.model.County;
import com.example.bigriverweather.model.Province;
import com.example.bigriverweather.util.LogUtil;


/**
 * 这个类将一些常用的数据库操作封装起来，方便以后我们使用
 * 这是一个单利类，将构造方法私有化，并提供一个方法俩获取实例，
 * 这样就可以保障全局就只有一个该类的实例。
 * @author Administrator
 *
 */
public class BigRiverWeatherDB {

	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "big_river_weather";
	
	/*
	 * 数据库的版本
	 */
	public static final int VERSION = 1;
	
	public static BigRiverWeatherDB bigRiverWeatherDB;
	
	private SQLiteDatabase db;
	
	/*
	 * 将构造方法私有化
	 */
	private BigRiverWeatherDB(Context context){
		BigRiverWeatherOpenHelper dbHelper = new BigRiverWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
		LogUtil.d("BigRiverWeatherDB", "private--constructor------");
	}
	
	/*
	 * 获取BigRiverWeatherDB实例
	 */
	public synchronized static BigRiverWeatherDB getInstance(Context context){
		if(bigRiverWeatherDB == null){
			bigRiverWeatherDB = new BigRiverWeatherDB(context);
			LogUtil.d("BigRiverWeatherDB", "getInstance-----if---");
		}
		LogUtil.d("BigRiverWeatherDB", "getInstance-----not-null---");
		
		return bigRiverWeatherDB;
	}
	
	/*
	 * 将province实例储存到数据库当中
	 */
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	
	/*
	 * 从数据库中读取全国所有省份的信息
	 */
	public List<Province> loadProvince(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	
	/*
	 * 将city实例存储到数据库
	 */
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name",city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	/*
	 * 从数据库当中读取某省下所有城市的信息
	 */
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?", 
				new String[] {String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("provinceId")));
				list.add(city);
			}while(cursor.moveToNext());
			
		}
		return list;
	}
	/*
	 * 将county实例存储到数据库当中
	 */
	public void saveCounty(County county){
		if(county != null){
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	/*
	 * 从数据库中读取某城市下所有的县的信息
	 */
	public List<County> loadCounties(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County",  null, "cityId = ?", 
				new String [] {String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		return list;
	}
}















