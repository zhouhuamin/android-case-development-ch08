package com.bn.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
/*
 *IOUtil�������ṩ�˶�DataInputStream���ж�ȡ�ķ���
 */
public class IOUtil 
{
	//��ȡ�ַ�������
	public static String readstr(DataInputStream din) throws Exception
	{		
		String str=null;
		byte[]  data=null;
		ByteArrayOutputStream out=null;
	    //ѭ����������
		try
		{
			out=new ByteArrayOutputStream(1024);
			int len=0,temRev=0,size;
			len=din.readInt();
			byte[] buf=new byte[len-temRev];
			while ((size = din.read(buf)) != -1) //������EOFException���ڴ˴���ִֹ��
			{ 	
				temRev+=size;
				out.write(buf, 0, size);
				if(temRev>=len)
				{
					break;
				}
				buf=new byte[len-temRev];
			}
			//����ȡ�����ݵĳ��Ⱥ����ݵ���ʵ���Ȳ���ʱ��˵������û�ж�ȫ��Ӧ�����»�ȡ����
			data=out.toByteArray();
			//len���ܱȶ��������ݵ���ʵ��СҪ�������len,����ܻ����StringIndexOutOfBoundsException
			str=new String(data,0,temRev,"utf-8");
			str=MyConverter.unescape(str);
		}
		finally
		{
			try{if(out!=null){out.close();} }catch(Exception e){e.printStackTrace();}
		}
		return str;
	}
	//��ȡͼƬ����
	public static byte[] readBytes(DataInputStream din) throws Exception
	{
		byte[]  data=null;
		ByteArrayOutputStream out=null;
		try
		{
			out= new ByteArrayOutputStream(1024); 
			int len =0,temRev =0,size;
			len =din.readInt();
			byte[] buf=new byte[len-temRev];
			while ((size = din.read(buf)) != -1)
			{ 	
				temRev+=size;
				out.write(buf, 0, size);
				if(temRev>=len)
				{
					break;
				}
				buf = new byte[len-temRev];
			}
			data = out.toByteArray();
		}
		finally
		{
			try{if(out!=null){out.close();} }catch(Exception e){e.printStackTrace();}
		}
		return data;
	}
}
