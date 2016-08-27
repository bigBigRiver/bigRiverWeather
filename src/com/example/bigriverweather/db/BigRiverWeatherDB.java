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
 * ����ཫһЩ���õ����ݿ������װ�����������Ժ�����ʹ��
 * ����һ�������࣬�����췽��˽�л������ṩһ����������ȡʵ����
 * �����Ϳ��Ա���ȫ�־�ֻ��һ�������ʵ����
 * @author Administrator
 *
 */
public class BigRiverWeatherDB {

	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "big_river_weather";
	
	/*
	 * ���ݿ�İ汾
	 */
	public static final int VERSION = 1;
	
	public static BigRiverWeatherDB bigRiverWeatherDB;
	
	private SQLiteDatabase db;
	
	/*
	 * �����췽��˽�л�
	 */
	private BigRiverWeatherDB(Context context){
		BigRiverWeatherOpenHelper dbHelper = new BigRiverWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
		LogUtil.d("BigRiverWeatherDB", "private--constructor------");
	}
	
	/*
	 * ��ȡBigRiverWeatherDBʵ��
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
	 * ��provinceʵ�����浽���ݿ⵱��
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
	 * �����ݿ��ж�ȡȫ������ʡ�ݵ���Ϣ
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
	 * ��cityʵ���洢�����ݿ�
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
	 * �����ݿ⵱�ж�ȡĳʡ�����г��е���Ϣ
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
	 * ��countyʵ���洢�����ݿ⵱��
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
	 * �����ݿ��ж�ȡĳ���������е��ص���Ϣ
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















