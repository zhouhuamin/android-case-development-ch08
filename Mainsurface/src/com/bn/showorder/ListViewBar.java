package com.bn.showorder;

import java.util.List;

import com.bn.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ListViewBar extends View
{
	private Paint paint;
    //��λ�߶�
    float unitHeight;
    //���ִ�С
    int fontSize;
    //���屳����ɫ
    int fontColor;
    //ͼƬ
    Bitmap imageBM[];
    //ͼƬ��Ϣ
    List<String[]> imageDataList;
    //���������ؼ��ĸ߶�
    float totalHeight;
    //��ǰ������ʾ�Ŀؼ��ĸ߶�
    float layoutHeight;
    //�Զ���ؼ�������
    ListViewBarListener tableListener;
    //������
    float span;
    static int index=0;
    float startY=0;
    static int temp=0;
	public ListViewBar(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		int count=attrs.getAttributeCount();
		//�õ���ǰ����ʾ�ؼ��ĸ߶�
		for(int i=0; i<count; i++)
		{
			if(attrs.getAttributeName(i).equals("layout_height"))
			{				
				layoutHeight=Float.parseFloat(attrs.getAttributeValue(i).replaceAll("dip|px", ""));
				break;
			}	
		}
		paint=new Paint();//�������ʶ���
		paint.setAntiAlias(true);//�򿪿����	
		imageBM=new Bitmap[3];
		imageBM[0]=BitmapFactory.decodeResource(getResources(), R.drawable.btn_dec1_normal);
		imageBM[1]=BitmapFactory.decodeResource(getResources(), R.drawable.btn_add1_normal);
		imageBM[2]=BitmapFactory.decodeResource(getResources(), R.drawable.btn_del_normal);
	}
	public void addTableListener(ListViewBarListener tableListener)
	{
		this.tableListener=tableListener;
	}
	public void setTextSer(List<String[]> imageDataList,int fontColor)
	{
		this.imageDataList=imageDataList;
		this.fontColor=fontColor;
	}	
	//���óߴ���ز���
	public void setSizeSer(float unitHeight,int fontSize,float span)
	{
		this.unitHeight=unitHeight;
		this.fontSize=fontSize;
		this.span=span;
	}
	//����ؼ����ܸ߶�
	public void calTotalHeight()
	{
		totalHeight = imageDataList.size()*(unitHeight+span);//+unitHeight;
	}	
	//��дonDraw����
	@Override
	public void onDraw(Canvas canvas)
	{
	 try
	 {
		if(this.totalHeight<this.layoutHeight)
		{
			startY=0;
		}
		else if((-totalHeight-startY+layoutHeight)>41)
		{
			startY=-totalHeight+layoutHeight-41;
		}
		if(index<imageDataList.size())
		{
			temp=index;
		}
		if(temp>=imageDataList.size())
		{
			temp=0;
		}
		paint.setTextSize(fontSize);
		for(int i=0;i<imageDataList.size();i++)
		{
			 if(i==temp)
			 {
				 paint.setColor(Color.RED);
			 }
			 else
			 {
				 paint.setColor(Color.rgb(255, 255, 221));
			 }
			paint.setStyle(Style.FILL);
			canvas.drawRect
			(
				0, 
				i*(unitHeight+span)+startY,
				1024, 
				(i+1)*(unitHeight+span)+startY, 
				paint
			);
			paint.setColor(fontColor);
			canvas.drawText
			(//���
				i+1+"",
				20,
				(i+1)*unitHeight+i*span+startY,
				paint
			);
			canvas.drawText
			(//��Ʒ����
				imageDataList.get(i)[0],
				110,
				(i+1)*unitHeight+i*span+startY,
				paint
			);
			canvas.drawText
			(//�۸�
				imageDataList.get(i)[1]+"Ԫ",
				230,
				(i+1)*unitHeight+i*span+startY,
				paint
			);
			canvas.drawBitmap
			(//���ٰ�ť
				imageBM[0],
				300,
				span+i*(unitHeight+span)+startY, 
				paint
			);
			canvas.drawText
			(//����
				imageDataList.get(i)[2],
				350,
				(i+1)*unitHeight+i*span+startY,
				paint
			);
			canvas.drawBitmap
			(//���Ӱ�ť
				imageBM[1],
				370,
				span+i*(unitHeight+span)+startY, 
				paint
			);
//			canvas.drawBitmap
//			(//��������
//				imageBM[1],
//				410,
//				span+i*(unitHeight+span)+startY, 
//				paint
//			);
			canvas.drawText
			(//С��
				Double.parseDouble(imageDataList.get(i)[1])*Double.parseDouble(imageDataList.get(i)[2])+"Ԫ",
				470,
				(i+1)*unitHeight+i*span+startY,
				paint
			);
			canvas.drawBitmap
			(//ɾ����ť
				imageBM[2],
				950,
				span+i*(unitHeight+span)+startY, 
				paint
			);
		}
	}
	catch(NullPointerException e)
	{
			return;
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
				startYPre=startY;
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
					if(startY<this.layoutHeight-this.totalHeight-unitHeight)
					{
						startY=this.layoutHeight-this.totalHeight-unitHeight;
					}
					this.invalidate();
				}
			return true;	
			case MotionEvent.ACTION_UP:
				//�������ƶ��¼�ʱִ�еĲ���
				if(!moveFlag)
				{//����������λ��
					float unitH=this.unitHeight+this.span;
					int row=(int) ((e.getY()-startY)/unitH);
					index=row;
					if(index<imageDataList.size())
					{
						if(e.getX()>300&&e.getX()<335)
						{//����
							if(!imageDataList.get(index)[2].equals("0"))
							{
								tableListener.onItemClick(index,-1);
							}
						}
						else
							if(e.getX()>370&&e.getX()<405)
							{//����
								tableListener.onItemClick(index,1);
							}
							else
								if(e.getX()>950&&e.getX()<990)
								{//ɾ��
									tableListener.onItemClick(index,2);
								}
//								else if(e.getX()>410&&e.getX()<445)
//								{//�ֶ���������
//									tableListener.onItemClick(index,3);
//								}
								else
								{
									tableListener.onItemClick(index,0);
								}
						this.invalidate();
					}
				}
			return true;	
		}
		return false;
	}
}
