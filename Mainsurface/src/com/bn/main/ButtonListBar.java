package com.bn.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/*��������һ�鰴ť�Ŀؼ��࣬��ť�������ʾ��Ҫ���õĲ����а�ť�鱳��ͼƬ��
 * ��ť����ÿ����ť��ͼƬ��ť���а�ť�ĸ߶ȡ���ȡ�������ס���ť���*/
public class ButtonListBar extends View
{
	//����
	private Paint paint;
	//������Bitmap
	Bitmap bg;
	//��ťͼ��
	Bitmap[] buttonBMUp;
	Bitmap[] buttonBMDown;
	//�߶�
	float height;
	//��ť���
	float width;
	//�������
	float leftMargin;
	//��ť֮������
	float span;
	ButtonListBarListener butListBarListener;	
	public void addButtonListBarListener(ButtonListBarListener butListBarListener)
	{
		this.butListBarListener=butListBarListener;
	}	
	//���û�������ͼƬ
	public void setBMSer(Bitmap bg,Bitmap[] buttonBMUp,Bitmap[] buttonBMDown)
	{
		this.bg=bg;
		this.buttonBMUp=buttonBMUp;
		this.buttonBMDown=buttonBMDown;
	}	
	//���óߴ���ز���
	public void setSizeSer(float height,float width,float leftMargin,float span)
	{
		this.height=height;
		this.width=width;
		this.leftMargin=leftMargin;
		this.span=span;
	}	
	public ButtonListBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		paint=new Paint();//�������ʶ���
		paint.setAntiAlias(true);//�򿪿����		
	}	
	public void onDraw(Canvas canvas)//��дonDraw����
	{
		canvas.drawBitmap(bg, 0, 0, paint); //���ư�ť�ܱ���
		for(int i=0; i<buttonBMUp.length; i++)
		{  //���ư�ť
			canvas.drawBitmap
			(
				buttonBMUp[i], 
				this.leftMargin+i*(this.width+this.span), 
				0, 
				paint
			);
		}		
	}	
	float startX;//��¼����X��λ��
	float startY;//��¼���µ�Yλ��
	boolean moveFlag;//��ʾ�Ƿ��ƶ�
	static final float thold=20;//�ƶ���ֵ
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		switch(e.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				startX=e.getX();
				startY=e.getY();
				moveFlag=false;
			return true;
			case MotionEvent.ACTION_MOVE:
				float x=e.getX();
				float y=e.getY();
				float xs=Math.abs(x-startX);
				float ys=Math.abs(y-startY);
				if(xs>thold||ys>thold)  //�ж������ƶ��ľ����Ƿ񳬹�����ֵ
				{
					moveFlag=true;
				}
			return true;	
			case MotionEvent.ACTION_UP:
				//�������ƶ��¼�ʱִ�еĲ���
				if(!moveFlag)
				{//����������λ��
					int index=(int) ((e.getX()-this.leftMargin)/(this.width+this.span));
					this.butListBarListener.onButtonClick(index);
				}
			return true;	
		}
		return false;
	}
}
