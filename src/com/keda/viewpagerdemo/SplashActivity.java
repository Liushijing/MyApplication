package com.keda.viewpagerdemo;

import java.util.Timer;
import java.util.TimerTask;

import com.keda.utils.SPUtils;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Timer timer = new Timer();
	
		TimerTask task = new TimerTask() {
			private Intent intent;
			@Override
			public void run() {
				boolean isEnter = SPUtils.get(getApplicationContext(),"isEnter");
				
				if (isEnter) {
					intent = new Intent(SplashActivity.this, InfoActivity.class);
				}else {
					intent = new Intent(SplashActivity.this, MainActivity.class);
				}
				startActivity(intent);
				finish();
			}
		};
		timer.schedule(task, 3000);
	}
	
}
