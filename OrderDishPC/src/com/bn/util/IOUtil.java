package com.bn.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IOUtil 
{
	//v2�汾���·���������din.writeUTF();�����ַ���
	public static void writeStr(DataOutputStream dout,String str) throws IOException
	{
		try{
			dout.writeInt(str.length());
			dout.write(str.getBytes());
			dout.flush();//������������
		    }catch(Exception e){
			  e.printStackTrace();
		     }finally{
		    	 try{dout.flush();}catch(Exception e){e.printStackTrace();}
		     }
	}//�����ַ���
	public static String readstr(DataInputStream din) throws IOException
	{		
		String str=null;
		byte[]  data=null;
		ByteArrayOutputStream out= new ByteArrayOutputStream(1024); //����һ���µ� byte�����������������ָ����С�Ļ���������(���ֽ�Ϊ��λ)
		//ѭ����������
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
			buf=new byte[len-temRev];
		}
		data = out.toByteArray();
		str= new String(data,0,len,"utf-8");
		str=MyConverter.unescape(str);//����
		return str;
	}
    //����ͼƬ����
	public static byte[]readBytes(DataInputStream din) throws IOException
	{
		byte[]  data=null;
		ByteArrayOutputStream out= new ByteArrayOutputStream(1024); 
			//ѭ����������
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
		return data;
	}
}
