package com.bn.vegeinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VegeImageList  extends View{

    //��ʼ������
	private Paint paint;		
	//ͼƬ�Ŀ�͸�
    float vegeimageWidth;
	float vegeimageHeight;		
	//ͼƬ�ļ��
	float span;	
	//��ƷͼƬ��Ϣ
	Bitmap vegeImage[];		
    //�ƶ�ƫ��
    float startY=0;
    //���������ؼ��ĸ߶�
    float totalHeight;
    //��ǰ������ʾ�Ŀؼ��Ŀ��
    float layoutHeight; 
	    
	public VegeImageList (Context context,AttributeSet attrs)
	{
		super(context, attrs);
		int count=attrs.getAttributeCount();
		//�õ���ǰ����ʾ�ؼ��ĸ߶�
		for(int i=0; i<count; i++){
			if(attrs.getAttributeName(i).equals("layout_height"))
			{				
				layoutHeight=Float.parseFloat(attrs.getAttributeValue(i).replaceAll("dip|px", ""));
				break;
			}	
		}
		paint=new Paint();//�������ʶ���
		paint.setAntiAlias(true);//�򿪿����		
	}	
	public void setBMser(Bitmap vegeImage[])
	{
		this.vegeImage=vegeImage;
	}	
	public void setSizeser(float vegeimageWidth,float vegeimageHeight,float span)
	{
		this.vegeimageWidth=vegeimageWidth;
		this.vegeimageHeight=vegeimageHeight;
		this.span=span;
	}	
	public void onDraw(Canvas canvas)//��дonDraw����
	{
		for(int i=0;i<vegeImage.length;i++)
		{
			canvas.drawBitmap
			(
				vegeImage[i],
				0,
				i*(vegeimageHeight+span)+startY,
				paint
			);
		}
	}	
	float startTouchX;//��¼����X��λ��
	float startTouchY;//��¼���µ�Yλ��
	boolean moveFlag;//��ʾ�Ƿ��ƶ�
	float startYPre;
	static final float thold=20;//�ƶ���ֵ
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{	
		switch(e.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				startTouchX=e.getX();//��¼��ǰ���λ��
				startTouchY=e.getY();
				startYPre=startY;//��¼֮ǰ��ƫ��ֵ
				moveFlag=false;
			return true;
			case MotionEvent.ACTION_MOVE: 
				float x=e.getX();
				float y=e.getY();
				float xs=Math.abs(x-startTouchX);
				float ys=Math.abs(y-startTouchY);
				if(xs>thold||ys>thold)  //�ж������ƶ��ľ����Ƿ񳬹�����ֵ
				{
					moveFlag=true;
				}
				//����ƶ�
				if(moveFlag)
				{
					//�ƶ���ƫ����		
					startY=startYPre+(y-startTouchY);
					//�涨�Ͻ���
					if(startY>0)
					{
						startY=0;
					}
					//�涨�½���
					if(startY<this.layoutHeight-this.totalHeight)
					{
						startY=this.layoutHeight-this.totalHeight;
					}
					if(this.totalHeight<this.layoutHeight)
					{
						startY=0;
					}
					this.invalidate();
				}
			return true;	
			case MotionEvent.ACTION_UP:
				//�������ƶ��¼�ʱִ�еĲ���
				if(!moveFlag)
				{//����������λ��
					
				}
			return true;	
		}
		return false;
	}
	public void calTotalHeight() 
	{
		totalHeight=(vegeimageHeight+span)*vegeImage.length;
		System.out.println("totalheight===="+totalHeight);
	}
}
