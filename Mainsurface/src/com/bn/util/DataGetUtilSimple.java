package com.bn.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.bn.constant.Constant;

public class DataGetUtilSimple
{
	private static DataInputStream din;
	private static DataOutputStream dout;
	private static Socket s;
	public static String readinfo;
	public static byte[] data;
	
	//�ڷ���Ա�ˣ������ٵ�������Ʒ����ʱ�����ж���߳�ͬʱ����ConnectSevert��������din,dout���Ǿ�̬�ģ����
	//�����EOFException����˽�ConnectSevert()��������Ϊͬ��������
	public synchronized static void ConnectSevert(String info) throws Exception
	{
		try
		{
			s=new Socket();
			s.connect(new InetSocketAddress(Constant.IP,Constant.POINT),5000);
			if(!s.isConnected())
			{
				if(s!=null)
				{
					s.close();
				}
				return;
			}
			din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
			//����
			info=MyConverter.escape(info);
			dout.writeInt(info.length());
			dout.write(info.getBytes());
			String getinfo=din.readUTF();
			if(getinfo.equals("STR"))
			{
				readinfo=IOUtil.readstr(din);
			}
			else if(getinfo.equals("BYTE")) 
			{
				data=IOUtil.readBytes(din);
			}
		}
		finally
		{
			try{if(din!=null) {din.close();} }catch(Exception e){ e.printStackTrace();}
			try{if(dout!=null) {dout.close();} }catch(Exception e){ e.printStackTrace();}
			try{if(s!=null){s.close();} }catch(Exception e){e.printStackTrace();}
		}
    }
}