package com.example.bigriverweather.util;

public interface HttpCallBackListener {
	void onFinish(String response);
	void onError(Exception e);
}
