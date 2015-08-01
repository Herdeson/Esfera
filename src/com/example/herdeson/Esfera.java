package com.example.herdeson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Esfera {
	private static final float REBOTE=0.4f;
	private static final float VELOCIDADE_MINIMA = 3f;
	private static final float PIXELS_POR_METRO = 15;
	
	private float mPosicaoX, mPosicaoY;
	private float mVelocidadeX, mVelocidadeY;
	private float mAceleracaoX, mAceleracaoY;
	private float mLarguraTela, mAlturaTela;
	private long mTempoUlAtualiz = 1;
	private Bitmap mImgEsfera;
	
	public Esfera(Context context){
		mImgEsfera = BitmapFactory.decodeResource
				(context.getResources(), R.drawable.ball);
	}
	
	public float getX(){
		return mPosicaoX;
	}
	
	public float getY(){
		return mPosicaoY;
	}
	
	public Bitmap getImgEsfera(){
		return mImgEsfera;
	}
	
	public void setAcelracao( float x, float y){
		mAceleracaoX = -x;
		mAceleracaoY = y;
		calcularFisica();
	}

	public void calcularFisica() {
		// TODO Auto-generated method stub
		if((mLarguraTela <= 0) || (mAlturaTela <= 0)) return;
		long tempo = System.currentTimeMillis();
		if (mTempoUlAtualiz < 0){
			mTempoUlAtualiz = tempo;
			return;
		}
		long tempoDecorrido = tempo;
		mVelocidadeX +=((mAceleracaoX*tempoDecorrido)/1000)* PIXELS_POR_METRO;
		mVelocidadeY +=((mAceleracaoY*tempoDecorrido)/1000)* PIXELS_POR_METRO;
		
		mPosicaoX += ((mVelocidadeX*tempoDecorrido)/1000)*PIXELS_POR_METRO;
		mPosicaoY += ((mVelocidadeY*tempoDecorrido)/1000)*PIXELS_POR_METRO;
		
		boolean rebateuX = false;
		boolean rebateuY = false;
		
		if (mPosicaoY < 0){
			mPosicaoY = 0;
			mVelocidadeY = -mVelocidadeY * REBOTE;
		}else if(mPosicaoY + mImgEsfera.getHeight() > mAlturaTela){// Verifica se o tamanho da imagem mais posição é maior que a tela
			mPosicaoY = mAlturaTela - mImgEsfera.getHeight();
			rebateuY = true;
		}
		if (rebateuY && Math.abs(mVelocidadeY) < VELOCIDADE_MINIMA){
			mVelocidadeY = 0;
		}
		//Eixo X
		if (mPosicaoX < 0){
			mPosicaoX = 0;
			mVelocidadeX = -mVelocidadeX * REBOTE;
		}else if(mPosicaoX + mImgEsfera.getWidth() > mLarguraTela){// Verifica se o tamanho da imagem mais posição é maior que a tela
			mPosicaoX = mLarguraTela - mImgEsfera.getWidth();
			rebateuX = true;
		}
		if (rebateuY && Math.abs(mVelocidadeX) < VELOCIDADE_MINIMA){
			mVelocidadeX = 0;
		}
		
	}
	
	public void setTamanhoTela(int m, int h){
		mLarguraTela = m;
		mAlturaTela = h;
		
	}

}
