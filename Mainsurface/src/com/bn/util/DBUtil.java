package com.bn.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DBUtil 
{
	public static SQLiteDatabase createOrOpenDatabase() throws Exception
    {
		 String paths = Environment.getExternalStorageDirectory().toString()+"/OrderDish";
		 File destDir = new File(paths);
		  if (!destDir.exists()) 
		  {
		      destDir.mkdirs();
		  }
		SQLiteDatabase sld=null;
    	sld=SQLiteDatabase.openDatabase
    	(
    			Environment.getExternalStorageDirectory().toString()+"/OrderDish/OrderDishdb", //���ݿ�����·��
    			null, 						 //CursorFactory
    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //��д�����������򴴽�
    	);
    	//��Ʒ��
		String sqlvege="create table if not exists vege" +
				 "(" +
					 "vege_id char(10) PRIMARY KEY," + //��Ʒid
				     "vege_name Varchar(20)," +	       //��Ʒ����
					 "vege_price number(6,2)," +       //��Ʒ�۸�
				     "vege_intro Varchar(500)," +      //��Ʒ����
				     "unit_name Varchar(20)," +        //��Ʒ������λ
					 "cate_name Varchar(20)," +        //��Ʒ�����
					 "mcate_name Varchar(20)," +       //��Ʒ����
					 "vt_name Varchar(20)," +          //��Ʒϵ��
					 "vs_name Varchar(20)," +          //��Ʒ���
					 "vege_code Varchar(10),"+"vege_key Varchar(10)," +"vege_eng Varchar(20)," +
					 "vege_discount number(3,2)," +"vege_advantage Varchar(200),"+"vege_ssflg char(1)," +
					 "vege_ssprice number(6,2)," +"vege_cxflg char(1)," +"vege_cxprice number(6,2)," +"vege_dotime Integer(11)," + 
					 "w_name Varchar(20),"+"room_name Varchar(20)," + "mm_name Varchar(50)," +
					 "mainpath Varchar(20)," +          //��Ʒ��ͼ·��
					 "path Varchar(100)," +             //��Ʒ��ͼ·��
					 "taste_name Varchar(50)," +
					 "nutrition_name Varchar(300)," +    //��ƷӪ��
					 "material_name Varchar(300)"+       //��Ʒ����
				 ")";
		String sqlmaincate="create table if not exists VegeMainCate" +
				 "(" +
					 "mcate_id char(10) PRIMARY KEY," +
					 "mcate_name char(20),"+
					 "limit_room_id char(10)"+
				 ")";
		String sqlcate="create table if not exists VegeChildCate" +
				 "(" +
					 "cate_name char(20) PRIMARY KEY," +
					 "mcate_name char(20)"+
				 ")";
		sld.execSQL(sqlvege);
		sld.execSQL(sqlmaincate);
		sld.execSQL(sqlcate);
		return sld;
    }
	public static void insertMainCate(String roomId,List<String[]> cateMsg) throws Exception
	{
		SQLiteDatabase sld=null;
		sld=createOrOpenDatabase();
		String sql;
		for(int i=0;i<cateMsg.size();i++)
		{
			sql="insert into VegeMainCate values('"+cateMsg.get(i)[0]+"','"+cateMsg.get(i)[1]+"'," +
			"'"+roomId+"')";
	        sld.execSQL(sql);
		}
		sld.close();
	}
	public static void insertChildCate(String mainCateName,String[] cateMsg) throws Exception
	{
		SQLiteDatabase sld=null;
		sld=createOrOpenDatabase();
		String sql;
		for(int i=0;i<cateMsg.length;i++)
		{
			sql="insert into VegeChildCate values('"+cateMsg[i]+"','"+mainCateName+"')";
	        sld.execSQL(sql);
		}
		sld.close();
	}
	 public static void insert(String tableName,List<String[]> vegemsg) throws Exception
	 {
    	SQLiteDatabase sld=null;
    	//ɾ���������е���Ϣ
    	deleteAllFromTable(tableName);
		sld=createOrOpenDatabase();
		String sql;
		for(int i=0;i<vegemsg.size();i++)
		{
			 sql="insert into '"+tableName+"' values('"+vegemsg.get(i)[0]+"','"+vegemsg.get(i)[1]+"'," +
			"'"+vegemsg.get(i)[2]+"','"+vegemsg.get(i)[3]+"','"+vegemsg.get(i)[4]+"'," +
			"'"+vegemsg.get(i)[5]+"','"+vegemsg.get(i)[6]+"','"+vegemsg.get(i)[7]+"'," +
			"'"+vegemsg.get(i)[8]+"','"+vegemsg.get(i)[9]+"','"+vegemsg.get(i)[10]+"'," +
			"'"+vegemsg.get(i)[11]+"','"+vegemsg.get(i)[12]+"','"+vegemsg.get(i)[13]+"'," +
			"'"+vegemsg.get(i)[14]+"','"+vegemsg.get(i)[15]+"','"+vegemsg.get(i)[16]+"'," +
			"'"+vegemsg.get(i)[17]+"','"+vegemsg.get(i)[18]+"','"+vegemsg.get(i)[19]+"'," +
			"'"+vegemsg.get(i)[20]+"','"+vegemsg.get(i)[21]+"','"+vegemsg.get(i)[22]+"'," +
			"'"+vegemsg.get(i)[23]+"','"+vegemsg.get(i)[24]+"','"+vegemsg.get(i)[25]+"'," +
			"'"+vegemsg.get(i)[26]+"')";
	        sld.execSQL(sql);
		}
		sld.close();
	 }
	 public static void insertCate(List<String[]> mainCate,List<String[]> childCate) throws Exception
	 {
    	SQLiteDatabase sld=null;
    	//ɾ���������е���Ϣ
    	deleteAllFromTable("VegeMainCate");
    	deleteAllFromTable("VegeChildCate");
		sld=createOrOpenDatabase();
		String sql;
		for(int i=0;i<mainCate.size();i++)
		{
			 sql="insert into VegeMainCate values('"+mainCate.get(i)[0]+"','"+mainCate.get(i)[1]+"'," +
					 "'"+mainCate.get(i)[2]+"')";
	        sld.execSQL(sql);
		}
		for(int i=0;i<childCate.size();i++)
		{
			for(int j=0;j<childCate.get(i).length;j++)
			{
				sql="insert into VegeChildCate values('"+childCate.get(i)[j]+"','"+mainCate.get(i)[1]+"')";
				sld.execSQL(sql);
			}
		}
		sld.close();
	 }
	 public static void insertAll(String tableName,List<String[]> vegemsg) throws Exception
	 {
    	SQLiteDatabase sld=null;
		sld=createOrOpenDatabase();
		String sql;
		for(int i=0;i<vegemsg.size();i++)
		{
			sql="select count(*) from vege where vege_id='"+vegemsg.get(i)[0]+"'";
			Cursor cur=sld.rawQuery(sql, null);
			cur.moveToNext();
			int count=cur.getInt(0);
			if(count!=0)
			{
				deleteFromTable(tableName,vegemsg.get(i)[0]);
			}
			 sql="insert into '"+tableName+"' values('"+vegemsg.get(i)[0]+"','"+vegemsg.get(i)[1]+"'," +
			"'"+vegemsg.get(i)[2]+"','"+vegemsg.get(i)[3]+"','"+vegemsg.get(i)[4]+"'," +
			"'"+vegemsg.get(i)[5]+"','"+vegemsg.get(i)[6]+"','"+vegemsg.get(i)[7]+"'," +
			"'"+vegemsg.get(i)[8]+"','"+vegemsg.get(i)[9]+"','"+vegemsg.get(i)[10]+"'," +
			"'"+vegemsg.get(i)[11]+"','"+vegemsg.get(i)[12]+"','"+vegemsg.get(i)[13]+"'," +
			"'"+vegemsg.get(i)[14]+"','"+vegemsg.get(i)[15]+"','"+vegemsg.get(i)[16]+"'," +
			"'"+vegemsg.get(i)[17]+"','"+vegemsg.get(i)[18]+"','"+vegemsg.get(i)[19]+"'," +
			"'"+vegemsg.get(i)[20]+"','"+vegemsg.get(i)[21]+"','"+vegemsg.get(i)[22]+"'," +
			"'"+vegemsg.get(i)[23]+"','"+vegemsg.get(i)[24]+"','"+vegemsg.get(i)[25]+"'," +
			"'"+vegemsg.get(i)[26]+"')";
	        sld.execSQL(sql);
		}
		sld.close();
	 }
	 //�õ����еĲ�Ʒid
	 public static String[] getAllvegeid() throws Exception
	 {
		    SQLiteDatabase sld=null;
		    int vegecount=getVegeCount();
	    	String[] list=new String[vegecount];
    		sld=createOrOpenDatabase();//�����ݿ�
    		String sql="select vege_id from vege";
    		Cursor cur=sld.rawQuery(sql, null);
    		int k=0;
    		while(cur.moveToNext())
    		{
    		    list[k]=cur.getString(0);
    		    k++;
    		}
    		cur.close();
    	    sld.close();
	    	return list;
	 }
	 //�õ�sqlite�в�Ʒ������
	 public static int getVegeCount() throws Exception
	 {
		    SQLiteDatabase sld=null;
		    int vegecount = 0;
    		sld=createOrOpenDatabase();//�����ݿ�
    		String sql="select sum(vege_id) from vege ";
    		Cursor cur=sld.rawQuery(sql, null);
    		cur.moveToNext();
    		vegecount=cur.getInt(0);
    		cur.close();
    		sld.close();
    		return vegecount;
	 }
	 
	 public static void deleteFromTable(String tableName,String vege_id) throws Exception
		{   
		    SQLiteDatabase sld=null;
		    sld=createOrOpenDatabase();
		    String sql="delete from '"+tableName+"' where vege_id='"+vege_id+"'";
			sld.execSQL(sql);
			sld.close();
		}
	 public static void deleteAllFromTable(String tableName) throws Exception
		{   
		    SQLiteDatabase sld=null;
		    sld=createOrOpenDatabase();
		    String sql="delete from '"+tableName+"'";
			sld.execSQL(sql);
			sld.close();
		}
	 //�õ�����ͼƬ��·��
	 public static List<String> getAllvegepath() throws Exception
	 {
		 List<String> listpath=new ArrayList<String>();
		 SQLiteDatabase sld=null;
		 sld=createOrOpenDatabase();//�����ݿ�
		 String sql="select distinct mainpath,path from vege";
		 Cursor cur=sld.rawQuery(sql, null);
		 while(cur.moveToNext())
		 {
			//����ͼ·����ӽ�list��
			String mainpath=cur.getString(0);
			listpath.add(mainpath);
			//����ͼ·����ӽ�list��
		    String[] path=cur.getString(1).split(",");
		    for(int i=0;i<path.length;i++)
		    {
		    	int k=0;
		    	for(int j=0;j<listpath.size();j++)
		    	{
		    		k++;
		    		if(path[i].equals(listpath.get(j)))
		    		{
		    			break;
		    		}
		    	}
		    	if(k>=listpath.size())
		    	{
		    		listpath.add(path[i]);
		    	}
		    }
		 }
		 cur.close();
		 sld.close();
		 return listpath;
	 }
	 //���ݲ�Ʒid�õ���Ʒ���ܵ���Ϣ
	 public static String[] getVegeintroByid(String id) throws Exception
	 {
		 String[] vegeintro=new String[8];
		 SQLiteDatabase sld=createOrOpenDatabase();//�����ݿ�
		 String sql="select vege_id,vege_name,vege_price,vege_intro," +
						"unit_name,nutrition_name,material_name,path from vege where vege_id='"+id+"'";
		 Cursor cur=sld.rawQuery(sql, null);
		while(cur.moveToNext())
		{
		 for(int i=0;i<vegeintro.length;i++)
		 {
			 vegeintro[i]=cur.getString(i);
		 }
		}
		 cur.close();
		 sld.close();
		 return vegeintro;
	 }	 
	//���ݲ�Ʒid�õ���Ʒ���ܵ���Ϣ
	 public static List<String[]> getVegeintroByid2(String id) throws Exception
	 {
		 List<String[]> vegeInfo = new ArrayList<String[]>();
		 SQLiteDatabase sld=createOrOpenDatabase();//�����ݿ�
		 System.out.println("SDCard-------------------------");
		 String sql="select vege_id,vege_name,vege_price,vege_intro," +
						"unit_name,nutrition_name,material_name,path,vege_key,cate_name from vege where vege_id like'"+id+"%'";
		 Cursor cur=sld.rawQuery(sql, null);
		 while(cur.moveToNext())
		 {
			 String[] vegeintro=new String[10];
			 for(int i=0;i<vegeintro.length;i++)
			 {
				 vegeintro[i]=cur.getString(i);
			 }
			 vegeInfo.add(vegeintro);
		 }
		 for(String[] arr : vegeInfo)
		 {
			 for( String str : arr)
			 {
				 System.out.print(str+" ");
			 }
			 System.out.println("\n in sdcard---\n");
		 }
		 cur.close();
		 sld.close();
		 return vegeInfo;
	 }	 
   //���ݲ�Ʒ����ѯ�����Ϣ
	 public static List<String[]> getVegeintroByCate(String catename) throws Exception
	 {
		 List<String[]> list=new ArrayList<String[]>();
		 SQLiteDatabase sld=createOrOpenDatabase();//�����ݿ�
		 String  sql="select vege_id,vege_name,vege_price,unit_name,cate_name,mainpath,mcate_name" +
					" from vege where cate_name ='"+catename+"'";
		 Cursor cur=sld.rawQuery(sql, null);
		 while(cur.moveToNext())
		 {
			 String[] vegeintro=new String[7];
			 for(int i=0;i<vegeintro.length;i++)
			 {
				 vegeintro[i]=cur.getString(i);
			 }
			 list.add(vegeintro);
		 }
		 cur.close();
		 sld.close();
		 return list;
	 }	 
	
	public static List<String[]> getVegeMainCateByRoomId(String roomId) throws Exception
	{
		List<String []> vegeMainCate=new ArrayList<String []>();
		SQLiteDatabase sqldb=createOrOpenDatabase();
		String sql="select distinct mcate_id,mcate_name from VegeMainCate where limit_room_id='"+roomId+"'";
		Cursor cur=sqldb.rawQuery(sql, null);
		while(cur.moveToNext())
		{
			String []vegeCateInfo=new String[2];
			for(int i=0;i<vegeCateInfo.length;i++)
			{
				vegeCateInfo[i]=cur.getString(i);
			}
			vegeMainCate.add(vegeCateInfo);
		}
		cur.close();
		sqldb.close();
		return vegeMainCate;
	}	
	public static String[] getChildCateByName(String cateName) throws Exception
	{
		List<String> vegeChildCate = new ArrayList<String>();
		SQLiteDatabase sqldb=createOrOpenDatabase();
		String sql="select cate_name from VegeChildCate where mcate_name='"+cateName+"'";
		Cursor cur=sqldb.rawQuery(sql, null);
		while(cur.moveToNext())
		{
			vegeChildCate.add(cur.getString(0));
		}
		cur.close();
		sqldb.close();
		return TypeExchangeUtil.listToStr(vegeChildCate);
	}
}
