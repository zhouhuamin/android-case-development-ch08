package com.bn.selectvege;

import java.util.List;
import com.bn.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/*VegeImageGrid�Զ���ؼ���ÿ����Ʒ��ͼƬ����Ϣ����Ϊһ����Ԫ�����������ʾ
�����趨ÿ����ʾ�ĵ�Ԫ����������,��ÿ����Ԫ��֮��ļ��
Ҫ���õĳ�Ա��������ƷͼƬ��Bitmap���顢��Ʒ��Ϣ��String���顢��Ʒ��Ϣ��������ɫ
ͼƬ�ĸ߶ȡ���ȡ����ִ�С����Ԫ���ࡢÿ�е�Ԫ�������*/
public class VegeImageGrid extends View
{
	private Paint paint;
	//����
    int colNum;
    //��λ���
    float unitWidth;
    //ͼƬ�߶�
    float imageHeight;
    //��ʾ��ͼƬ�߶�
    float picFlgHeight;
    //��ʾ��ͼƬ���
    float picFlgWidth;
    //���ִ�С
    int fontSize;
    //���屳����ɫ
    String fontColor;
    //���
    float span;
    //ͼƬ
    Bitmap imageBM[];
    //ͼƬ��Ϣ
    List<String[]> imageDataList;
    //�ƶ�ƫ��
    float startY=0;
    //���������ؼ��ĸ߶�
    float totalHeight;
    //��ǰ������ʾ�Ŀؼ��ĸ߶�
    float layoutHeight;
    VegeImageGridListener vegeImgGridListener;
    Bitmap flgBM[]=new Bitmap[2];
    //��Ʒ��ѡ��־λ����
    boolean []booFlg;
    
	public VegeImageGrid(Context context, AttributeSet attrs) 
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
		flgBM[1]=BitmapFactory.decodeResource(getResources(), R.drawable.checkbox_false_press);
		flgBM[0]=BitmapFactory.decodeResource(getResources(), R.drawable.checkbox_false);
	}
	public void addVegeImageGridListener(VegeImageGridListener vegeImgGridListener)
	{
		this.vegeImgGridListener=vegeImgGridListener;
	}
	//����ͼƬ��ͼƬ��Ϣ
	public void setBMSer(Bitmap[] imageBM,boolean []booFlg)
	{
		this.imageBM=imageBM;
		this.booFlg=booFlg;
	}
	public void setTextSer(List<String[]> imageDataList,String fontColor)
	{
		this.imageDataList=imageDataList;
		this.fontColor=fontColor;
	}
	
	//���óߴ���ز���
	public void setSizeSer(float imageHeight,float unitWidth,
							float picFlgHeight,float picFlgWidth,
							int fontSize,float span,int colNum)
	{
		this.imageHeight=imageHeight;
		this.unitWidth=unitWidth;
		this.picFlgHeight=picFlgHeight;
		this.picFlgWidth=picFlgWidth;
		this.fontSize=fontSize;
		this.span=span;
		this.colNum=colNum;
	}
	//����ؼ����ܸ߶�
	public void calTotalHeight()
	{
		int linecount = (this.imageBM.length%colNum==0)?(this.imageBM.length/colNum):(this.imageBM.length/colNum+1);
		totalHeight=linecount*(imageHeight+fontSize+span);
	}
	
	//��дonDraw����
	@Override
	public void onDraw(Canvas canvas)
	{
	 try
	 {
		if(imageBM.length==0 || imageDataList.size()==0)
		{
			return;
		}
		if(this.totalHeight<this.layoutHeight)
		{
			startY=0;
		}
		for(int i=0; i<imageBM.length; i++)
		{  //����ͼƬ
			if(booFlg[i])
			{
				canvas.drawBitmap
				(
					small(imageBM[i]), 
					(i%colNum)*(unitWidth+span)+(unitWidth+span)*0.05f, 
					((i/colNum)*(imageHeight+fontSize+span)+(imageHeight+fontSize+span)*0.05f)+startY, 
					paint
				);
				canvas.drawBitmap
				(
					flgBM[1], 
					(i%colNum)*(unitWidth+span), 
					(i/colNum)*(imageHeight+fontSize+span)+startY, 
					paint
					);
			}
			else
			{
				canvas.drawBitmap
				(
					imageBM[i], 
					(i%colNum)*(unitWidth+span), 
					(i/colNum)*(imageHeight+fontSize+span)+startY, 
					paint
				);
				canvas.drawBitmap
				(
						flgBM[0], 
						(i%colNum)*(unitWidth+span), 
						(i/colNum)*(imageHeight+fontSize+span)+startY, 
						paint
						);
			}
		}		
		for(int i=0; i<imageDataList.size(); i++)
		{  //����ͼƬ��Ϣ����
			//���������С
			paint.setTextSize(fontSize);
			//��������
			canvas.drawText
			(
				imageDataList.get(i)[1]+"   ��"+imageDataList.get(i)[2]+"/"+imageDataList.get(i)[3],
				(i%colNum)*(unitWidth+span), 
				imageHeight+fontSize+(i/colNum)*(imageHeight+fontSize+span)+startY, 
				paint
			);
		}	
		SelectVegeActivity.onchildclickflag=true;
	}
	catch(NullPointerException e)
	{
			return;
	}
	 finally{
		 SelectVegeActivity.onchildclickflag=true;
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
					this.invalidate();
				}
			return true;	
			case MotionEvent.ACTION_UP:
				//�������ƶ��¼�ʱִ�еĲ���
				if(!moveFlag)
				{//����������λ��
					float unitH=this.imageHeight+this.fontSize+this.span;
					float unitW=this.unitWidth+this.span;
					int col=(int) ((e.getX())/unitW);
					int row=(int) ((e.getY()-startY)/unitH);
					double xTouch=((e.getX())%unitW);
					double yTouch=((e.getY()-startY)%unitH);
					int index=row*this.colNum+col;
					if(index>=0&&index<imageBM.length)
					{
						if(xTouch<60&&yTouch<60)
						{
							vegeImgGridListener.onItemClick(index,1);
						}
						else
						{
							vegeImgGridListener.onItemClick(index,0);
						}
					}
				}
			return true;	
		}
		return false;
	}
	 private static Bitmap small(Bitmap bitmap)
	 {
		  Matrix matrix = new Matrix(); 
		  matrix.postScale(0.9f,0.9f); //���Ϳ�Ŵ���С�ı���
		  Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		  return resizeBmp;
		 }
}
