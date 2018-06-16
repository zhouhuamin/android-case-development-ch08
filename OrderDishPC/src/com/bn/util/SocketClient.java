package com.bn.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import com.bn.constant.Constant;
import static com.bn.constant.Constant.*;
public class SocketClient 
{
	static Socket s;
	private static DataInputStream din;
    private static DataOutputStream dout;
    public static String readinfo;
    public static byte[] data=null;
    static String getinfo;
    //�����ַ���
		public static void ConnectSevert(String info)
		{
    		try
    		{
    		   s=new Socket();
    		   s.connect(new InetSocketAddress(Constant.IP,Constant.POINT),5000);
    		}
    		catch(SocketTimeoutException e)
    		{
    			if(!s.isConnected())
    			{
    	   		  readinfo=SOCKET_ERROR;
    	   		}
    	   		return; 
    	   }
    	  catch(IOException e)
    		{
    			if(!s.isConnected())
    			{
   	   		     readinfo=SOCKET_ERROR;
   	   			}
   	   			return; 
    		}
    		try
    		{
	    		din=new DataInputStream(s.getInputStream());
	    		dout=new DataOutputStream(s.getOutputStream());	    		
	    		info=MyConverter.escape(info);//����
	    		dout.writeInt(info.length());
	    		dout.write(info.getBytes());
	    		getinfo=din.readUTF();
	    		if(getinfo.equals("STR"))
	    		{
	    			readinfo=IOUtil.readstr(din);
	    		}
	    		else if(getinfo.equals("BYTE"))
	    		{
	    			data=IOUtil.readBytes(din);
	    		}
    	    }
		   catch(Exception e)
		   {
    		 if(!s.isClosed()&&s.isConnected())
    		 {
    			 readinfo=SOCKET_IOERROR;
    			 System.out.println("��ȡ���ݳ�ʱ...");
    		 }
    		return;
    	   }
		 finally
		 {
    		try{dout.close();}catch(Exception e){e.printStackTrace();}
    		try{din.close();}catch(Exception e){e.printStackTrace();}
    		try{s.close();}catch(Exception e){e.printStackTrace();}
    	 }
		}
 //����ͼƬ
   public static void ConnectSevertBYTE(String info,String mz, byte[] data)
   {
   	try{
   		s=new Socket(IP,POINT);
   		din=new DataInputStream(s.getInputStream());
   		dout=new DataOutputStream(s.getOutputStream());
   		int len=data.length;
		info=MyConverter.escape(info);//����
		dout.writeInt(info.length());
		dout.write(info.getBytes());
		dout.writeUTF(mz);
   		dout.writeInt(len);
   		dout.write(data);
   		getinfo=din.readUTF();//��ȡ����������
		if(getinfo.equals("STR"))
		{
			readinfo=IOUtil.readstr(din);
		}
		else if(getinfo.equals("BYTE"))
		{
			data=IOUtil.readBytes(din);
		}
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
   	finally
   	{
   		try{dout.close();}catch(Exception e){e.printStackTrace();}
   		try{din.close();}catch(Exception e){e.printStackTrace();}
   		try{s.close();}catch(Exception e){e.printStackTrace();}
   	 }	
   }
}
