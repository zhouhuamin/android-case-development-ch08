package com.bn.util;

import java.util.HashMap;
import java.util.Map;
import static com.bn.constant.Constant.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * PicManagerUtil����ʵ�ֲ�ƷͼƬ�Ķ������棬
 * ����Ҫ��ʾ��ƷͼƬʱ������Bitmap�ڴ��в��ң����û�������ڴ��в��ң�
 * �ڴ���û�вŻᵽ���������
 */
public class PicManagerUtil 
{
	 //����30��ͼ
	 static final int bmCount=30;
	 //�ڻ����д�ͼƬ����Ϣ
     static Bitmap[] bitmapBuffer=new Bitmap[bmCount];
     //Ϊtrue��ǰλ����ͼ
     static boolean[] isFull=new boolean[bmCount];
     //ÿһ��ͼ�����ʹ�õ�ʱ��
     static long[] timeStamp=new long[bmCount];
     //ͼƬ���ݿ���ͼƬ�ı���������±��Ӧmap
     static Map<String,Integer> bmMap=new HashMap<String,Integer>();
     //ͼƬ�����±��Ӧmap�����ݿ���ͼƬ�ı��
     static Map<Integer,String> bmMapInverse=new HashMap<Integer,String>();    
     //��ͼ��id��ͼ������
     static Map<String,byte[]> bmData=new HashMap<String,byte[]>();
     //���ͼ�ķ���
     public static Bitmap getBM(String bmId) throws Exception
     {    	 
    	 //��һ�����Ȳ�bitmap����ָ����ŵ�ͼƬ�����򷵻�
    	 if(bmMap.containsKey(bmId))
    	 {
    		 int index=bmMap.get(bmId);
    		 timeStamp[index]=System.nanoTime();
    		 return bitmapBuffer[index];
    	 }  
    	//�ڶ����������ݻ���������ָ����ŵ�ͼƬ�����򷵻�
    	 if(bmData.containsKey(bmId))
    	 {
    		 byte[] picData=bmData.get(bmId);
    		 return fromMem(bmId,picData);
    	 }
    	 ////��sdcard�в�Ʒ�ı�ź�·��
         Map<String,String> bmMapId=PicUtil.getSdidpath();
    	 if(bmMapId.containsKey(bmId))
    	 {
    		 String path=bmMapId.get(bmId);
    		 byte[] picData=PicUtil.getPic(path);
    		 bmData.put(bmId, picData);
        	 return fromMem(bmId,picData);
    	 } 
    	 //����������������к��ڴ��ж�û��ָ����ŵ�ͼƬ�����������
    	 //���ݻ�����û��
    	 DataGetUtilSimple.ConnectSevert(GET_W_PICTUREINFO+bmId);
    	 //�쳣����
    	 byte[] picData=DataGetUtilSimple.data;
    	 bmData.put(bmId, picData);
    	 return fromMem(bmId,picData);
     }     
     private static Bitmap fromMem(String bmId,byte[] picData)
     {
		 //����ͼƬ�������޿�λ
		 int kw=-1;
		 for(int i=0;i<bmCount;i++)
		 {
			 if(isFull[i]==false)
			 {
				 kw=i;
				 break;
			 }
		 }
		 //�п�λ
		 if(kw!=-1)
		 {
			 //�����ݽ���ΪͼƬ
			 bitmapBuffer[kw]=BitmapFactory.decodeByteArray(picData, 0, picData.length);
			 //����λ����Ϊ��ͼƬ״̬
			 isFull[kw]=true;
			 //���µ�ǰ��ʹ��ʱ��
			 timeStamp[kw]=System.nanoTime();
			 //���뻺��������
			 bmMap.put(bmId, kw);
			 bmMapInverse.put(kw, bmId);
			 return bitmapBuffer[kw];
		 }
		 //û�п�λ
		 else
		 {
			 //�ҵ���Ȼ��С��ʱ��
			 long tempT=Long.MAX_VALUE;
			 kw=-1;
			 //�õ���ǰ��С�Ĳ��������λ
			 for(int i=0;i<bmCount;i++)
			 {
				 if(timeStamp[i]<tempT)
				 {
					 tempT=timeStamp[i];
					 kw=i;
				 }
			 }
			 //�ͷ�ԭ��ͼƬ�ڴ�
			 bitmapBuffer[kw].recycle();
			//�����ݽ���ΪͼƬ
			 bitmapBuffer[kw]=BitmapFactory.decodeByteArray(picData, 0, picData.length);
			 isFull[kw]=true;
			 //���µ�ǰ��ʹ��ʱ��
			 timeStamp[kw]=System.nanoTime();
			 //ɾ������������ԭ�е�ͼƬ��Ϣ
			 bmMap.remove(bmMapInverse.get(kw));
			 bmMapInverse.remove(kw);
			 //���뻺��������
			 bmMap.put(bmId, kw);
			 bmMapInverse.put(kw,bmId);
			 return bitmapBuffer[kw];
		 }
     }  
}
