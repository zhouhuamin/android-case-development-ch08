package com.bn.serverinfo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

public class PicScaleUtil {
	//����ͼƬ
	public static BufferedImage scaledImg(byte[]pic,int width,int height) throws IOException{	
		ByteArrayInputStream bis = new ByteArrayInputStream(pic);//byte-->ͼƬ
		BufferedImage bi = ImageIO.read(bis);//����ͼƬ
		Image img = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);//����ͼƬ		
		BufferedImage bufImg=new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB); //��ͼƬ��Ϣ����ͼƬ����
		bufImg.getGraphics().drawImage(img,0,0,null); //����ͼƬ
		bis.close();//�ر���
		return bufImg;//����ͼƬ
	}	
	//����ȵȱ�������ͼƬ
	public static BufferedImage scaledImgByWid(BufferedImage bi, int width) throws IOException{			
		double primaryWid = 0;
		double primaryHei = 0;
		int height = 0;    //�����ŵĸ߶�
		double proportion = 0;
		primaryWid = bi.getWidth();//�õ�ͼƬ���
		primaryHei = bi.getHeight();//�õ�ͼƬ�߶�
		proportion = width/primaryWid;
		System.out.println("proportion="+proportion);//��ӡ��λ���
		height = (int)(primaryHei*proportion);//����߶�		
		Image img = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);//����ͼƬ
		BufferedImage bufImg=new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB); //��ͼƬ��Ϣ����ͼƬ���� 
		bufImg.getGraphics().drawImage(img,0,0,null); //����ͼƬ
		return bufImg;//����ͼƬ
	}	
	//���߶ȵȱ�������ͼƬ
	public static BufferedImage scaledImgByHei(BufferedImage bi, int height) throws IOException{		
		double primaryWid = 0;
		double primaryHei = 0;
		int width = 0;    //�����ŵĸ߶�
		double proportion = 0;//������λ
		primaryWid = bi.getWidth();//�õ�ͼƬ���
		primaryHei = bi.getHeight();//�õ�ͼƬ�߶�
		proportion = height/primaryWid;
		System.out.println("proportion="+proportion);//��ӡ��λ���
		width = (int)(primaryHei*proportion);//����߶�		
		Image img = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);//����ͼƬ
		BufferedImage bufImg=new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB); //��ͼƬ��Ϣ����ͼƬ����
		bufImg.getGraphics().drawImage(img,0,0,null); //����ͼƬ
		return bufImg;//����ͼƬ
	}	
	    //����ͼƬ
		public static void saveImage(BufferedImage img,String path) throws IOException{		
			File f=new File(path); //ͨ��������·��pathת��Ϊ����·����������һ���� File ʵ��
		    FileImageOutputStream imgout=new FileImageOutputStream(f);//��Fileʵ�����������
		    ImageIO.write(img,"JPEG",f); //��ʵ��д��ͼƬ��
		    imgout.close();//�ر�ͼƬ��
		}
		//�Ӵ��̻��ͼƬ
		public static byte[]getPic(String path) {		
			byte[] pic =null;//����ͼƬ��������
			try {
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));//����·������������
				ByteArrayOutputStream out = new ByteArrayOutputStream(1024); //����һ���µĻ����������ָ����������СΪ1024Byte
				byte[] temp = new byte[1024]; //������СΪ1024�ı�������
				int size = 0; //�����С����
				try {
					while ((size = in.read(temp)) != -1){  //�������ݶ���					
						out.write(temp, 0, size);//д��������� 
					}
					in.close();//�ر���
				} catch (IOException e) {//�������쳣
					e.printStackTrace();
				} 
				pic= out.toByteArray();//��ͼƬ��Ϣ�Ա���������ʽ��������ֵ��ͼƬ��������
			} catch (Exception e1) {//�����쳣
				e1.printStackTrace();
			}
			return pic;//����ͼƬ��������
		}
}