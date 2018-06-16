package com.bn.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bn.constant.Constant;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class PicUtil 
{
	//����ͼƬ
		public static void saveImage(byte[] img,String imagename) throws Exception
		{
			if(android.os.Environment.getExternalStorageState().equals( 
					android.os.Environment.MEDIA_MOUNTED))
			{
				  String paths = Environment.getExternalStorageDirectory().toString()+"/OrderDish/pic";
				  File destDir = new File(paths);
				  if (!destDir.exists()) 
				  {
				      destDir.mkdirs();
				  }
				  //����Ӧ�óɹ������ļ���
                  File file=new File(destDir,"/"+imagename);
                  if(!file.exists())
                  {
          		    //��byte�����������sd����
          		    	 FileOutputStream fous=new FileOutputStream(file);
          		    	 BufferedOutputStream bos=new BufferedOutputStream(fous);
          		    	 Bitmap bm= BitmapFactory.decodeByteArray(img, 0, img.length);// bitmap          
          		         bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);    
          		         bos.flush();    
          		         bos.close();  
                }
			}
			else
			{
				throw new NoSdCardException("δ��⵽SD��,����룡����");
			}
		}
		//�Ӵ��̻��ͼƬ
		public static byte[] getPic(String path) throws Exception 
		{
			byte[] pic =null;
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024); 
			byte[] temp = new byte[1024]; 
			int size = 0; 
			while ((size = in.read(temp)) != -1) 
			{ 
				out.write(temp, 0, size); 
			}
			in.close();
			pic= out.toByteArray();
			return pic;
		}
		//�õ���Ʒ·��id��·��
		public static Map<String,String> getSdidpath() throws Exception 
		{
			Map<String,String> imageidpath=new HashMap<String,String>();
			List<String> imagepath=DBUtil.getAllvegepath();
			for(int i=0;i<imagepath.size();i++)
			{
				String[] curpath=imagepath.get(i).split("/");
				String pathid=curpath[curpath.length-1].replace(".jpg", "");
				imageidpath.put(pathid, imagepath.get(i));
			}
			return imageidpath;
		}
		public static String[] saveImagemsg(String[] vegemsg) throws Exception
		{
			 //�õ���Ʒ����ͼƬ
			  DataGetUtilSimple.ConnectSevert(Constant.GET_IMAGE+vegemsg[22]);
			  byte[] main_image= DataGetUtilSimple.data;
			  //�����µ�ͼƬ·��
			  String mainpath=vegemsg[22];
			  String[] imagepath=mainpath.split("/");
			  String imagename=imagepath[imagepath.length-1];
			  //����sdcard��
			  PicUtil.saveImage(main_image, imagename);
			  //��·�������޸�
			  vegemsg[22]=Environment.getExternalStorageDirectory().toString()+"/OrderDish/pic/"+imagename;
			  
			  //�õ���Ʒ����ͼƬ
				  String[] path=vegemsg[23].split(",");
				  StringBuilder sbmsgpath=new StringBuilder();
				  for(int j=0;j<path.length;j++)
				  {
					  DataGetUtilSimple.ConnectSevert(Constant.GET_IMAGE+path[j]);
				      byte[] imagemsg= DataGetUtilSimple.data;
					  //�����µ�ͼƬ·��
					  imagepath=path[j].split("/");
					  imagename=imagepath[imagepath.length-1];
					  //����sdcard��
					  PicUtil.saveImage(imagemsg, imagename);
					  sbmsgpath.append(Environment.getExternalStorageDirectory().toString()+"/OrderDish/pic/"+imagename+",");
				   }
				  //��·�������޸�
			  vegemsg[23]=sbmsgpath.toString();
			  return vegemsg;
		}
}
