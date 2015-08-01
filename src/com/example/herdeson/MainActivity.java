package com.example.herdeson;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity  implements SensorEventListener, SurfaceHolder.Callback{
	private SurfaceView sufView;
	private SurfaceHolder surfHolder;
	
	private SensorManager sensorManager;
	private ThreadRoda threadRoda;
	private Esfera bola;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.sufView = new SurfaceView(this);
		this.sufView.setKeepScreenOn(true);
		this.surfHolder = sufView.getHolder();
		this.surfHolder.addCallback(this);
		
		setContentView(sufView);
		this.bola = new Esfera(this);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Sensor acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_GAME);
	}
	@Override
	protected void onPause(){
		super.onPause();
		sensorManager.unregisterListener(this);
	}



	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		this.bola.setAcelracao(event.values[0], event.values[1]);
		
	}
	

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		try{
			this.bola.setTamanhoTela(0, 0);
			this.threadRoda.parar();
		}finally{
			this.threadRoda = null;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		this.bola.setTamanhoTela(width, height);
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		this.threadRoda = new ThreadRoda(this, this.bola, holder);
		this.threadRoda.start();
		
	}





}
