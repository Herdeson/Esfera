package com.example.herdeson;

import java.util.concurrent.TimeUnit;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class ThreadRoda extends Thread{
	private boolean executando;
	private Esfera bola;
	private SurfaceHolder holder;
	private Bitmap campo;
	
	public ThreadRoda(Context context, Esfera bola, SurfaceHolder holder){
		this.bola = bola;
		this.holder = holder;
		this.campo = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.grass);
	}
	
	public void run(){
		executando = true;
		while(executando){
			try {
				this.bola.calcularFisica();
				pintar();
				TimeUnit.MILLISECONDS.sleep(16);
				
			} catch (Exception e) {
				// TODO: handle exception
				executando = false;
			}
		}
	}
	
	public void parar(){
		executando =false;
		interrupt();
	}
	
	public void pintar(){
		Canvas c = null;
		try{
			c = holder.lockCanvas();
			if (c != null){
				Rect rect = new Rect(0,0,c.getWidth(),c.getHeight());
				c.drawBitmap(campo, null, rect, null); //Desenhar primeiro o campo e depois desenhar posição da  bola
				c.drawBitmap(this.bola.getImgEsfera(), this.bola.getX(), this.bola.getY(), null);
			}
		}finally{
			if ( c != null){
				this.holder.unlockCanvasAndPost(c);
			}
		}
	}

}
