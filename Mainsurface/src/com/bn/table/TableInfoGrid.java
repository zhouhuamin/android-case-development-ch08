package com.bn.table;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TableInfoGrid extends View {
	//����
     public int colNum;
     //����ͼƬ�Ŀ��
     public float uintWidth;
     //����ͼƬ�ĸ߶�
     public float timageHeight;
     //��������
     public String tableText;
     //ͼƬ���
     public float span;
     //�����С
     public float textSize;
     //����߶�
     public float textHeight;
     //������ɫ
     public String textColor;
     //����ͼƬ
     public Bitmap dtBM[];
     //����ͼƬ
     public Bitmap bxBM[];
     //�ؼ��ĸ߶�
     float layoutHeight;
     //��ǰ���еĲ�̨�ĸ߶�
     static float totalHeight;
     //�ƶ�ƫ����
     float startY=0;
     //������
     float leftMargin;
     float topMargin;
     //����
     public Paint paint;
     String pointstate="����";
     //��̨��Ϣ
     List<String[]> tableInfo;
     TableInfoGridListener tableinfogridlistener;
	public TableInfoGrid(Context context, AttributeSet attrs) 
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
	//��ʼ������Ҫ��ͼƬ
	public void setTableImageSer(Bitmap dtBM[], Bitmap bxBM[])
	{
		this.dtBM=dtBM;
		this.bxBM=bxBM;
	}
	//������������
	public void setImageSizeSer(int colNum,float uintWidth,float timageHeight)
	{
		this.colNum=colNum;
		this.timageHeight=timageHeight;
		this.uintWidth=uintWidth;
	}
	public void setLayoutSer(float span,float leftMargin,float topMargin)
	{
		this.span=span;
		this.leftMargin=leftMargin;
		this.topMargin=topMargin;
	}
	public void setTextSer(float textSize,String textColor,float textHeight,List<String[]> tableInfo)
	{
	    this.textSize=textSize;
	    this.textColor=textColor;
	    this.textHeight=textHeight;
	    this.tableInfo=tableInfo;
	}
	//�������и߶�
	public void calTotalHeight()
	{
		int rowNum=((tableInfo.size()%colNum)==0)?(tableInfo.size()/colNum):(tableInfo.size()/colNum+1);
		totalHeight=topMargin+(timageHeight+4*textSize+span)*rowNum;
	}
	//��Ӽ���
	public void addTableInfoGridListener(TableInfoGridListener tableinfogridlistener)
	{
		this.tableinfogridlistener=tableinfogridlistener;
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		if(totalHeight<this.layoutHeight)
		{
			startY=0;
		}
		for(int i=0;i<tableInfo.size();i++)
		{
			if(tableInfo.get(i)[6].equals("����"))
			{
				drawInfo(dtBM,canvas,i);
			}
			else
			{
				drawInfo(bxBM,canvas,i);
			}
		  paint.setTextSize(textSize);
		  
		  canvas.drawText
		  (
			 "��������:"+tableInfo.get(i)[5],
			 (i%colNum)*(uintWidth+span)+leftMargin,
			 timageHeight+textSize+span+(i/colNum)*(timageHeight+4*textSize+span)+startY,
			 paint
		  );
		  canvas.drawText
		  (
			 "��̨����:"+tableInfo.get(i)[2],
			 (i%colNum)*(uintWidth+span)+leftMargin,
			 timageHeight+2*textSize+span+(i/colNum)*(timageHeight+4*textSize+span)+startY,
			 paint
		  );
		  canvas.drawText
		  (
			 "��ǰ״̬:"+pointstate,
			 (i%colNum)*(uintWidth+span)+leftMargin,
			 timageHeight+3*textSize+span+(i/colNum)*(timageHeight+4*textSize+span)+startY,
			 paint
		  );
		  canvas.drawText
		  (
			 "�����������:"+tableInfo.get(i)[4],
			 (i%colNum)*(uintWidth+span)+leftMargin,
			 timageHeight+4*textSize+span+(i/colNum)*(timageHeight+4*textSize+span)+startY,
			 paint
		  );
		}
	}
	public void drawInfo(Bitmap bm[],Canvas canvas,int i)
	{
		 if(tableInfo.get(i)[3].equals("0"))
		  {
			  pointstate="����";
			   canvas.drawBitmap
			   (
				bm[0], 
				(i%colNum)*(uintWidth+span)+leftMargin, 
				(i/colNum)*(timageHeight+4*textSize+span)+startY+topMargin, 
				paint
				);
		  }
		  else if(tableInfo.get(i)[3].equals("1"))
		  {
			  pointstate="����";
			  canvas.drawBitmap
			   (
				bm[1], 
				(i%colNum)*(uintWidth+span)+leftMargin, 
				(i/colNum)*(timageHeight+4*textSize+span)+startY+topMargin, 
				paint
				);
		  }
		  else if(tableInfo.get(i)[3].equals("2"))
		  {
			  pointstate="�Ѷ�";
			  canvas.drawBitmap
			   (
				bm[2], 
				(i%colNum)*(uintWidth+span)+leftMargin, 
				(i/colNum)*(timageHeight+4*textSize+span)+startY+topMargin,  
				paint
			   );
		  }
		  else if(tableInfo.get(i)[3].equals("3"))
		  {
			  pointstate="ѡ��";
			  canvas.drawBitmap
			   (
				bm[3], 
				(i%colNum)*(uintWidth+span)+leftMargin, 
				(i/colNum)*(timageHeight+4*textSize+span)+startY+topMargin,  
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
					if(startY<this.layoutHeight-totalHeight)
					{
						startY=this.layoutHeight-totalHeight;
					}
					this.invalidate();
				}
			return true;	
			case MotionEvent.ACTION_UP:
				//�������ƶ��¼�ʱִ�еĲ���
				if(!moveFlag)
				{//����������λ��
					float unitH=this.timageHeight+4*this.textSize+this.span;
					float unitW=this.uintWidth+this.span;
					int col=(int) ((e.getX())/unitW);
					int row=(int) ((e.getY()-startY)/unitH);
					int index=row*this.colNum+col;
					if(index<tableInfo.size())
					{
						tableinfogridlistener.onItemClick(index);
					}
				}
			return true;	
		}
		return false;
	}
}
