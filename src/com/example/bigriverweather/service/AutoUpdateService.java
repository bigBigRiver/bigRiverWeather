package com.example.bigriverweather.service;

import com.example.bigriverweather.*;
import com.example.bigriverweather.db.BigRiverWeatherDB;
import com.example.bigriverweather.model.City;
import com.example.bigriverweather.model.County;
import com.example.bigriverweather.model.Province;
import com.example.bigriverweather.receiver.AutoUpdateReceiver;
import com.example.bigriverweather.util.HttpCallBackListener;
import com.example.bigriverweather.util.HttpUtil;
import com.example.bigriverweather.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				updateWeather();
			}
		}).start();
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//		int anHour = 8 * 60 * 60 * 1000; // 杩欐槸8灏忔椂鐨勬绉掓暟
		int anHour = 3 * 1000; // 杩欐槸8灏忔椂鐨勬绉掓暟
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void updateWeather() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code", "");
		String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
		HttpUtil.sendHttpRequest(address, new HttpCallBackListener(){

			@Override
			public void onFinish(String response) {
				// TODO 自动生成的方法存根
				
				Log.d("TAG", response);
				
				Utility.handleWeatherResponse(AutoUpdateService.this, response);
			}

			@Override
			public void onError(Exception e) {
				// TODO 自动生成的方法存根
				
				e.printStackTrace();
			}
			
		});
	
	}

}
