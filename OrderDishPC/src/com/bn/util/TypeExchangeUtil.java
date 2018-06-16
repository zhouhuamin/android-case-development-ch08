package com.bn.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
/*
 * ����֮���ת���ľ�̬��
 * */
public class TypeExchangeUtil
{
	//��String ����ת���� JTABle�� ��Vector<Vector<String>>�͵�����
	public static Vector<Vector<String>> strToVector(String msg)
	{
		Vector<Vector<String>> list =new Vector<Vector<String>>();
		String []str=msg.split("#");
		System.out.println(str.length);
		for(int i=0;i<str.length;i++)
		{
			Vector<String> v=new Vector<String>();
			if(str[i].length()>0)
			{
			   String vinfo[]=str[i].split("��");
			   System.out.println(vinfo.length); 
			   for(String s:vinfo){   
				   System.out.print(s);
				   v.add(s);
			   }
			   list.add(v);
			}	
		}
		return list;
	}
	//��String[]ת���� String
	public static String exchangeStrToString(String[] str)
	{
		String result="";
		for(int i=0;i<str.length;i++)
		{
			result=result+str[i]+"��";
		}
		return result;
	}
	
	public static String[] getStringInfo(String msg,int num)
	{

		String row[]=msg.split("#");
		String str[]=null;
		String info[]=new String[row.length ];
		for(int i=0;i<row.length;i++){
			str=row[i].split("��");
			info[i]=str[num];
		}
		return info;
	}
	public static String[][] getString(String msg){
		String row[]=msg.split("#");
		String col[]=row[0].split("��");
		String info[][]=new String[row.length][col.length];
		for(int i=0;i<row.length;i++)
		{
			 col=row[i].split("��");
			for(int j=0;j<col.length;j++)
			{
				info[i][j]=col[j];
			}
		}
		return info;
	}
	
	public static List<String[]> strToList(String msg)
	{
		List<String[]> list =new ArrayList<String[]>();
		String []str=msg.split("#");
		for(int i=0;i<str.length;i++)
		{
			if(str[i].length()>0)
				list.add(str[i].split("��"));
		}
		return list;
	}
	public static Vector<Vector<String>> listToVector(List<String[]> list)
	{  
		Vector<Vector<String>> vector = new Vector<Vector<String>>();
		if(list==null)
		{
			return null;
		}
		for(String str[] : list)
		{
			Vector<String> vectorStr = new Vector<String>();
			for(String string : str)
			{
				vectorStr.add(string);
			}
			vector.add(vectorStr);
		}
		return vector;
	}
 //��fileת����String
	public static String filetostr(File file)
	{
		String msg=null;
		 BufferedInputStream in = null;
		 ByteArrayOutputStream out = null;
		try
		{
		   in = new BufferedInputStream(new FileInputStream(file));
		   out = new ByteArrayOutputStream(); 
		   byte[] temp = new byte[1024]; 
		   int size = 0; 
		   try 
		   {
			  while ((size = in.read(temp)) != -1) 
			   { 
				  out.write(temp, 0, size); 
			   }
			   in.close();
		    }catch (IOException e) 
		    {
			   e.printStackTrace();
		     } 
		   byte[] pic=out.toByteArray();
		   msg=new String(pic,"ISO-8859-1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
    		try{out.close();}catch(Exception e){e.printStackTrace();}
    		try{in.close();}catch(Exception e){e.printStackTrace();}
    	 }
		return msg;
	}
	
	public static List<String> liststring(String str)
	{
		List<String> list=new ArrayList<String>();
		String[] getmsg=str.split("#");
		for(int i=0;i<getmsg.length;i++)
		{
			list.add(getmsg[i]);
		}
		return list;
	}
	public static String[] strToString(String getinfo) 
	{
		String row[]=getinfo.split("#");
		String col[]=row[0].split("��");
		String info[]=new String[col.length];
		for(int i=0;i<col.length;i++)
		{
		  info[i]=col[i];
		}
		return info;
	}
	//��fileת����byte[]
	public static byte[] filetobyte(File file)
	{
         byte[] data = null;
		 BufferedInputStream in = null;
		 ByteArrayOutputStream out = null;
		try
		{
		   in = new BufferedInputStream(new FileInputStream(file));
		   out = new ByteArrayOutputStream(); 
		   byte[] temp = new byte[1024]; 
		   int size = 0; 
		   try {
			  while ((size = in.read(temp)) != -1) 
			   { 
				  out.write(temp, 0, size); 
			   }
			   in.close();
		    } catch (IOException e) {
			   e.printStackTrace();
		   } 
		data=out.toByteArray();

		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
    		try{out.close();}catch(Exception e){e.printStackTrace();}
    		try{in.close();}catch(Exception e){e.printStackTrace();}
    	 }
		return data;
	}

	//�õ������1�ŵ����Ƶ���Ϣ
	 @SuppressWarnings({ "rawtypes", "unchecked" })
     public static  Vector<String> getnamebynoone(List<String[]> cgdata)
	 {
		 Vector vec=new Vector();
		 for(String[] s:cgdata)
		 {
			vec.add(s[1]);
		 }
		return vec; 
	 }
	//�õ������2�ŵ����Ƶ���Ϣ
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 public static  Vector<String> getnamebynotwo(List<String[]> cgdata)
	 {
		 Vector vec=new Vector();
		 for(String[] s:cgdata)
		 {
			vec.add(s[1]);
		 }
	 	return vec; 
	 }
	  public static  String gettime()
	  {
		   Calendar   c   =   Calendar.getInstance(); 
	       c.setTime(new   java.util.Date()); 
	       int   year   =   c.get(Calendar.YEAR); 
	       int   month   =   c.get(Calendar.MONTH)+1; 
	       int   day   =   c.get(Calendar.DAY_OF_MONTH); 
	       int   hour   =   c.get(Calendar.HOUR_OF_DAY); 
	       int   minute   =   c.get(Calendar.MINUTE); 
	       int   second   =   c.get(Calendar.SECOND);
	       return year+"-"+(month<=9?"0"+month:month+"")+"-"+(day<=9?"0"+day:day+"")+" "+hour+":"+minute+":"+second;
	   }
}