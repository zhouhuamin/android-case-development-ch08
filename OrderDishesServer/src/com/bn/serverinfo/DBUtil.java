package com.bn.serverinfo;//���������
			
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
		
public class DBUtil{                                       //��������
		public static Connection getConnection(){		   //��д�����ݿ⽨�����ӵķ���
			Connection con = null;                         //��������
			try{			
				Class.forName("org.gjt.mm.mysql.Driver");	//��������
		con = DriverManager.getConnection("jdbc:mysql://localhost/orderdish?useUnicode=true&characterEncoding=UTF-8","root","");//�õ����ӣ����ݿ�����������ʽ�����ݿ��û��������ݿ����룩
	}catch(Exception e){
		e.printStackTrace();              //�����쳣
	}
	return con;                           //��������
}
	//����Ա���������õ�id
	public static String getWorkeridByname(String string){	
		Connection con = getConnection();//�����ݿ⽨������
		Statement st = null;//�����ӿ�
		ResultSet rs = null;//�����
		String str = null;//�ַ�������
		try{		
			st = con.createStatement();//����һ����������SQL��䷢�͵����ݿ�
			String task = "select w_id from WorkerInfo where w_name='"+string+"';";//��дSQL���
			rs = st.executeQuery(task);//ִ��SQL���
		    rs.next();                 //����ִ�� 
		    str=rs.getString(1);       //����ѯ�õ���Ա��ID�����ַ�������
		}
		catch(Exception e){		      //�����쳣�����ؿ�ֵ
			e.printStackTrace();
			return null;
		}
		finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}//�����
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;                                 //����Ա��ID
	}
	//�õ�������λ����Ϣ
	public static List<String[]> getCU(){	
		Connection con = getConnection();//�����ݿ⽨������
	    Statement st = null;//�����ӿ�
	    ResultSet rs = null;//�����
		List<String[]>ll=new ArrayList<String[]>();//����������ݵ��б�
		try{ 		 
			st=con.createStatement();//����һ����������SQL��䷢�͵����ݿ�
			String sql="select unit_id,unit_name,unit_remark from CountUnit where unit_delflg='"+0+"'";//��дSQL���
			rs=st.executeQuery(sql);//ִ��SQL���
			while(rs.next()){		  //ѭ�����������	
				String[] s=new String[3];
				for(int i=0;i<s.length;i++){				
					s[i]=rs.getString(i+1);     //���õ�����Ϣ�����ַ�������
				}
				ll.add(s);                      //���ַ���������ӵ��б�
			}	
		}catch(Exception e){		
			e.printStackTrace();                 //�����쳣
		}
		finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return ll;
	}
     ////�õ�������λ�������
	public static String getCUMaxNO(){	
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		String str=new String();
		try{ 		 
			st=con.createStatement();
			String sql="select max(unit_id) from countunit";
			rs=st.executeQuery(sql);
		    rs.next();
		    if(rs.getString(1)==null){		    
		    	str=String.valueOf(0);
		    }
		    else{		    
		    	str=rs.getString(1);
		    }
		}
		catch(Exception e){		
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//���¼�����λ
	private static Object UPDATECOUNTUNIT=new Object();
	public static String updateCU(String[] getmsg){ 	
		synchronized(UPDATECOUNTUNIT){		        //��������
			Connection con = getConnection();
			Statement st = null;
			String idnum[]=getCountUnitID();
			try{			
				int i;
				st = con.createStatement();
				for( i=0;i<idnum.length;i++){               //�������еļ�����λ���͸���				
					if(getmsg[1].equals(idnum[i])){					
						String sql = "update countunit set unit_name= '"+getmsg[2]+"',unit_remark= '"+getmsg[3]+"' where unit_id="+getmsg[1];
						st.executeUpdate(sql);
						break;
					}
				}
				if(i==idnum.length){	                //���������еģ��Ͳ���			
				String sql="insert into countunit(unit_id,unit_name,unit_remark) values('"+getmsg[1]+"','"+getmsg[2]+"','"+getmsg[3]+"')";
				st.executeUpdate(sql);
				}
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}		
	}
	//�õ�������λ�ı��
	private static String[] getCountUnitID(){ 	
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    int count=getCount("countunit");
		String[] ll=new String[count];
		try{		  
			st=con.createStatement();
			String sql="select unit_id from countunit";       
			rs=st.executeQuery(sql);
			int k=0;
			System.out.println(rs.getRow());             //��ӡ���
			while(rs.next()){				
				System.out.println(rs.getString(1));
				ll[k]=rs.getString(1);
				System.out.println(ll[k]+"k="+k);
				k++;				
			}	
		}catch(Exception e){		
			e.printStackTrace();
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return ll;
	}
	//ɾ��������λ��Ϣ
	private static Object DELCOUNTUNIT=new Object();
	public static String delCU(String id) {	
		synchronized(DELCOUNTUNIT){		             //����
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql = "update countunit set unit_delflg='"+1+"' where unit_id="+id;
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{		
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//���ݼ�����λid�õ�����
	public static String getCUnameById(String string) {	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		try{	
			st = con.createStatement();
			String task = "select unit_name from CountUnit where unit_id='"+string+"';";
			rs = st.executeQuery(task);
		    rs.next();
		    str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}	
	//����Ա��id�õ�����
	public static String getWorkernameById(String string){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		try{		
			st = con.createStatement();
			String task = "select w_name from WorkerInfo where w_id='"+string+"';";
			rs = st.executeQuery(task);
		    rs.next();
		    str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}	
	//���ݼ�����λ���Ƶõ�id
	public static String getCUidByname(String string) {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		try{		
			st = con.createStatement();
			String task = "select unit_id from CountUnit where unit_name='"+string+"';";
			rs = st.executeQuery(task);
		    rs.next();
		    str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}	
	//�õ���ϵ��Ϣ
	public static List<String[]> getVegeType(){	
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		List<String[]>ll=new ArrayList<String[]>();
		try{		  
			st=con.createStatement();
			String sql="select vt_id,vt_name from vegetype where vt_delflg='"+0+"'";
			rs=st.executeQuery(sql);
			while(rs.next()){			
				String[] s=new String[2];
				for(int i=0;i<s.length;i++){				
					s[i]=rs.getString(i+1);
				}
				ll.add(s);
			}	
		}catch(Exception e){		
			e.printStackTrace();
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return ll;
	}
	//�õ���ϵ�������
	public static String getVTMaxNo(){
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		String str=new String();
		try{		  
			st=con.createStatement();
			String sql="select max(vt_id) from vegetype";
			rs=st.executeQuery(sql);
		    rs.next();
		    if(rs.getString(1)==null){
		    	str=String.valueOf(0);
		    }
		    else{
		    	str=rs.getString(1);
		    }
		}
		catch(Exception e){		
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//���²�ϵ��Ϣ
	private static Object UPDATEVT=new Object();
	public static String updateVT(String[] str){	
		synchronized(UPDATEVT){		
			Connection con = getConnection();
			Statement st = null;
			String idnum[]=getVegetypeID();
			try{			
				int i;
				st = con.createStatement();
				for( i=0;i<idnum.length;i++){	           //�����в�ϵ�������			
					if(str[1].equals(idnum[i])){					
						String sql = "update vegetype set vt_name= '"+str[2]+"' where vt_id="+str[1];
						st.executeUpdate(sql);
						break;
					}
				}
				if(i==idnum.length){		                 //���޴˲�ϵ�������		
				String sql="insert into vegetype(vt_id,vt_name) values('"+str[1]+"','"+str[2]+"')";
				st.executeUpdate(sql);
				}
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//�õ���ϵ�ı��
	private static String[] getVegetypeID(){	
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    int count=getCount("vegetype");
		String[] ll=new String[count];
		try{		  
			st=con.createStatement();
			String sql="select vt_id from vegetype";
			rs=st.executeQuery(sql);
			int k=0;
			while(rs.next()){				
				ll[k]=rs.getString(1);
				k++;
			}	
		}catch(Exception e){		
			e.printStackTrace();
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return ll;
	}
	//ɾ����ϵ
	private static Object DELVT=new Object();
	public static String delVT(String id){	
		synchronized(DELVT){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql = "update vegetype set vt_delflg='"+1+"' where vt_id="+id;
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}	
    //�����������Ϣ
    public static List<String[]> getMCG(){   
    	Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String task = "select mcate_id,mcate_name from" +
			" MainCategory where MainCategory.mcate_delflag='"+0+"'";
			rs = st.executeQuery(task);
			while(rs.next()){			
				String[] str = new String[2];
				for(int i=0;i<str.length;i++){				
					str[i]=rs.getString(i+1);
				}
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
   //�õ������������
    public static String getMCGMaxNo(){    
    	Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		String str=new String();
		try{		  
			st=con.createStatement();
			String sql="select max(mcate_id) from MainCategory";
			rs=st.executeQuery(sql);
		    rs.next();
		    if(rs.getString(1)==null){		   
		    	str=String.valueOf(0);
		    }
		    else{
		    	str=rs.getString(1);
		    }
		}
		catch(Exception e){		
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
   //���������Ϣ�����ˣ��Ȳˣ���������
    private static Object ADDMCG=new Object();
	public static String addMCG(String getmsg, String getmsg2) {
		synchronized(ADDMCG){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql="insert into MainCategory(mcate_id,mcate_name) values('"+getmsg+"','"+getmsg2+"')";
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}	
	//�����������Ϣ
	private static Object UPDATEMCG=new Object();
	public static String updateMCG(String string,String string1) {
		synchronized(UPDATEMCG){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql = "update MainCategory set mcate_name='"+string1+"' " +
						"where mcate_id="+string;
				System.out.println(sql);
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//ɾ��������Ϣ
	private static Object DELMCG=new Object();
	public static String delMCG(String id) {
		synchronized(DELMCG){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql="update MainCategory set  mcate_delflag='"+1+"' " +
						"where mcate_id = '"+id+"';";
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//�õ���Ʒ������Ϣ
	public static List<String[]> getCG() {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String sql="select cate_id,cate_name,cate_createtime," +
					"cate_updatetime,mcate_name from Category,MainCategory " +
					"where cate_delflag='"+0+"' and Category.mcate_id = MainCategory.mcate_id";
			rs = st.executeQuery(sql);
			while(rs.next()){			
				String[] str = new String[5];
				for(int i=0;i<str.length;i++){
					str[i]=rs.getString(i+1);
				}
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//�õ���Ʒ���������
	public static String getCGMaxNo() {
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		String str=new String();
		try{ 		 
			st=con.createStatement();
			String sql="select max(cate_id) from Category";
			rs=st.executeQuery(sql);
		    rs.next();
		    if(rs.getString(1)==null){
		    	str=String.valueOf(0);
		    }
		    else{
		    	str=rs.getString(1);
		    }
		}
		catch(Exception e){		
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//������������Ƶõ������id
	public static String getMCGidByname(String string) {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str;
		try{		
			st = con.createStatement();
			String task = "select mcate_id from MainCategory where mcate_name='"+string+"'";
			rs = st.executeQuery(task);
            rs.next();
            str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//��Ӳ�Ʒ���
	private static Object ADDCG=new Object();
	public static String addCG(String[] getmsg, String mcgid) {
		synchronized(ADDCG){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql="insert into Category(cate_id,cate_name,cate_createtime," +
						"cate_updatetime,mcate_id) values('"+getmsg[1]+"','"+getmsg[2]
						+"','"+getmsg[3]+"','"+getmsg[4]+"','"+mcgid+"')";
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//���²�Ʒ�����Ϣ
	private static Object UPDATECG=new Object();
	public static String updateCG(String[] getmsg, String time, String mcgid) {
		synchronized(UPDATECG){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				System.out.println("shifoujinru");
				String sql = "update Category set cate_name='"+getmsg[2]+"'," +
			" cate_createtime='"+getmsg[3]+"',cate_updatetime='"+time+"',mcate_id='"+mcgid+"' where cate_id='"+getmsg[1]+"'";
				System.out.println(sql);
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//ɾ����Ʒ���
	private static Object DELCG=new Object();
	public static String delCG(String id) {
		synchronized(DELCG){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				System.out.println(id);
				st = con.createStatement();
				String sql="update Category set  cate_delflag='"+1+"' where cate_id = '"+id+"';";
				System.out.println(sql);
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//�õ���Ʒ��Ϣ
	public static List<String[]> getVege() {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			//��Ʒ��Ϣ��������ƷID�����ƣ��۸񣬼�����λ���ƣ�������ƣ�����������ϵ�������������Ʒ����
			String sql="select distinct vege_id,vege_name,vege_vegeprice," +
			"unit_name,cate_name,mcate_name,vt_name,vs_name,vege_intro " +
			"from vege,countunit,category,vegetype,MainCategory,VegeStandard " +
			"where  vege_useflg='"+1+"' and vege.unit_id =  countunit.unit_id and " +
			"vege.cate_id = category.cate_id  and category.mcate_id = maincategory.mcate_id and vege.vt_id = vegetype.vt_id  " +
			" and vege.vs_id = VegeStandard.vs_id ";
			rs = st.executeQuery(sql);
			while(rs.next()){			
				String[] str = new String[9];
				for(int i=0;i<str.length;i++){
					str[i]=rs.getString(i+1);
				}
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//�õ���Ʒ�������
	public static String getVegeMaxNO() {
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		String str=new String();
		try{		  
			st=con.createStatement();
			String sql="select max(vege_id) from Vege";
			rs=st.executeQuery(sql);
		    rs.next();
		    if(rs.getString(1)==null){
		    	str=String.valueOf(0);
		    }
		    else{
		    	str=rs.getString(1);
		    }
		}
		catch(Exception e){		
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//�õ�������Ϣ
	public static List<String[]> getVS() {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String sql="select * from VegeStandard";
			rs = st.executeQuery(sql);
			while(rs.next()){			
				System.out.println("sql");
				String[] str = new String[2];
				for(int i=0;i<str.length;i++){				
					str[i]=rs.getString(i+1);
				}
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//�õ����������
	public static String getVSMaxNO(){	
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		String str=new String();
		try{ 		 
			st=con.createStatement();
			String sql="select max(vs_id ) from VegeStandard";
			rs=st.executeQuery(sql);
		    rs.next();
		    if(rs.getString(1)==null){
		    	str=String.valueOf(0);
		    }
		    else{
		    	str=rs.getString(1);
		    }
		}
		catch(Exception e){		
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//���¹����Ϣ
	private static Object UPDATEVS=new Object();
	public static String updateVS(String[] getmsg) {
		synchronized(UPDATEVS){		
			Connection con = getConnection();
			Statement st = null;
			String idnum[]=getVSID();
			try{			
				int i;
				st = con.createStatement();
				for( i=0;i<idnum.length;i++){
					if(getmsg[1].equals(idnum[i])){
						String sql = "update VegeStandard set vs_name= '"+getmsg[2]+"' where vs_id ="+getmsg[1];
						st.executeUpdate(sql);
						break;
					}
				}
				if(i==idnum.length){
				String sql="insert into VegeStandard values('"+getmsg[1]+"','"+getmsg[2]+"')";
				st.executeUpdate(sql);
				}
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//�õ��������б��
	private static String[] getVSID() {
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    int count=getCount("VegeStandard");
	    
		String[] ll=new String[count];
		try{		  
			st=con.createStatement();
			String sql="select vs_id from VegeStandard";
			rs=st.executeQuery(sql);
			int k=0;
			while(rs.next()){				
				ll[k]=rs.getString(1);
				k++;
			}	
		}catch(Exception e){		
			e.printStackTrace();
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return ll;
	}
	//ɾ�������Ϣ
	private static Object DELVS=new Object();
	public static String delVS(String id) {
		synchronized(DELVS){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql = "delete from VegeStandard where vs_id="+id;
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//�������౻�����Ƶõ�����������
	public static String getMcgnameByCgname(String getmsg) {
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		String str=new String();
		try{ 		 
			st=con.createStatement();
			String sql="select mcate_name from category,maincategory where cate_name = '"+getmsg+"' " +
					"and category.mcate_id = maincategory.mcate_id";
			rs=st.executeQuery(sql);
		    rs.next();
		    	str=rs.getString(1);
		    	System.out.println(str);
		}
		catch(Exception e){		
			e.printStackTrace();
		}finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//���ݹ������Ƶõ�����id
	public static String getVSidByname(String string) {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str;
		try{		
			st = con.createStatement();
			String task = "select vs_id from VegeStandard where vs_name='"+string+"'";
			rs = st.executeQuery(task);
            rs.next();
            str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//�����������Ƶõ�����id
	public static String getCGidByname(String string) {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str;
		try{		
			st = con.createStatement();
			String task = "select cate_id from category where cate_name='"+string+"'";
			rs = st.executeQuery(task);
            rs.next();
            str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//����ϵ������Ƶõ�ϵ���id
	public static String getVTidByname(String string) {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str;
		try{		
			st = con.createStatement();
			String task = "select vt_id from vegetype where vt_name='"+string+"'";
			rs = st.executeQuery(task);
            rs.next();
            str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//���ݲ��������Ƶõ�������id
	public static String getRoomidByname(String string) {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str;
		try{		
			st = con.createStatement();
			String task = "select room_id from Room where room_name='"+string+"'";
			rs = st.executeQuery(task);
            rs.next();
            str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//��Ӳ�Ʒ��Ϣ
    private static Object ADDVEGE=new Object();
	public static String addVege(String[] getmsg, String cuid,
			 String cgid, String vtid, String ggid) {
		 synchronized(ADDVEGE){
		
 			Connection con = getConnection();
 			Statement st = null;
 			try{
 				st = con.createStatement();
 				String sql = "insert into vege(vege_id,vege_name,vege_vegeprice," +
 				"unit_id,cate_id,vt_id,vs_id,vege_intro)" +
 				" values('"+getmsg[1]+"','"+getmsg[2]+"',"+getmsg[3]+"," +
 				"'"+cuid+"','"+cgid+"','"+vtid+"','"+ggid+"','"+getmsg[8]+"')";
 				System.out.println(sql);
 				st.executeUpdate(sql);
 			}catch(Exception e){			
 				e.printStackTrace() ;
 				return null;
 			}finally{ 			
 				try{st.close();}catch(Exception e) {e.printStackTrace();}
 				try{con.close();}catch(Exception e) {e.printStackTrace();}
 			}
 			return "ok";
 		}
	}
	//ɾ����Ʒ��Ϣ
	private static Object DELVEGE=new Object();
	public static String delVege(String string) {
		synchronized(DELVEGE){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql = "update vege set  vege_useflg='"+0+"' where vege_id="+string;
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//���²�Ʒ��Ϣ
	private static Object UPDATEVEGE=new Object();
	public static String updateVege(String[] getmsg, String cuid,
			String cgid, String vtid,  String ggid) {
		synchronized(UPDATEVEGE){		
			Connection con = getConnection();
			Statement st = null;
			String idnum[]=getVegeID();
			try{			
				int i;
				st = con.createStatement();
				for( i=0;i<idnum.length;i++){
					if(getmsg[1].equals(idnum[i])){
				String sql="update vege set vege_name='"+getmsg[2]+"' , vege_vegeprice='"+getmsg[3]+"' , " +
				" unit_id='"+cuid+"' , cate_id='"+cgid+"' , vt_id='"+vtid+"' ," +
						" vs_id='"+ggid+"' , vege_intro='"+getmsg[8]+"' where vege_id = "+getmsg[1];
				System.out.println(sql);
				st.executeUpdate(sql);
				break;
					}
				}
				System.out.println("sucess.....");
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	 //�õ���Ʒ���еı��
	private static String[] getVegeID(){
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    int count=getCount("vege");
			String[] ll=new String[count];
			try{			  
				st=con.createStatement();
				String sql="select vege_id from vege";
				rs=st.executeQuery(sql);
				int k=0;
				while(rs.next()){					
					ll[k]=rs.getString(1);
					k++;
				}	
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return ll;
	}
	//���ݲ�Ʒ��Ų�ѯ���ڱ�ŵĲ�Ʒ
	public static List<String> sVegemid(String string) {
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{			  
				st=con.createStatement();
				String sql="select vege_id from vege where vege_id >= '"+string+"' and vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//���ݲ�Ʒ��Ų�ѯС�ڱ�ŵĲ�Ʒ
	public static List<String> sVegelid(String string) {
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{			  
				st=con.createStatement();
				String sql="select vege_id from vege where vege_id <= '"+string+"' and vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//���ݲ�Ʒ���Ʋ�ѯ��Ʒ
	public static List<String> sVegebyname(String string) {
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{ 			 
				st=con.createStatement();
				String sql="select vege_id from vege where vege_name='"+string+"' and vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//���ݲ�Ʒ����ѯ��Ʒ
	public static List<String> sVegebycg(String string) {
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{ 			 
				st=con.createStatement();
				String sql="select vege_id from vege,category where cate_name='"+string+"'" +
						" and category.cate_id = vege.cate_id and vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//���ݲ�Ʒ�����ѯ��Ʒ
	public static List<String> sVegebymcg(String string) {
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{			  
				st=con.createStatement();
				String sql="select vege_id from vege,maincategory,category where mcate_name='"+string+"' " +
			   "and maincategory.mcate_id = category.mcate_id and category.cate_id = vege.cate_id and vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//���ݲ�Ʒϵ���ѯ��Ʒ
	public static List<String> sVegebyvt(String string) {
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{ 			 
				st=con.createStatement();
				String sql="select vege_id from vege,vegetype where vegetype.vt_name='"
				+string+"' and vegetype.vt_id = vege.vt_id and vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//���ݲ�Ʒ�۸��ѯ���ڼ۸�Ĳ�Ʒ
	public static List<String> sVegemprice(String string) {
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{			  
				st=con.createStatement();
				String sql="select vege_id from vege where vege_vegeprice >= " +
						"'"+string+"' and vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//���ݲ�Ʒ�۸��ѯС�ڲ�Ʒ�ļ۸�
	public static List<String> sVegelprice(String string) {
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{			  
				st=con.createStatement();
				String sql="select vege_id from vege where vege_vegeprice <= '"+string+"' and vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//�õ����в�Ʒ�ı��
	public static List<String> getVegeid(){	
		 Connection con = getConnection();
		    Statement st = null;
		    ResultSet rs = null;
		    List<String> list=new ArrayList<String>();
			try{			  
				st=con.createStatement();
				String sql="select vege_id from vege where vege_useflg='"+1+"'";
				rs=st.executeQuery(sql);
				while(rs.next()){
					list.add(rs.getString(1));
				}
			}catch(Exception e){			
				e.printStackTrace();
			}
			finally{
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
	}
	//���ݲ�Ʒid�õ���Ʒ����Ϣ
	public static List<String[]> getVegeByid(String[] msgvege){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			for(int k=0;k<msgvege.length;k++){
			String sql="select vege_id" +"vege_name,vege_vegeprice," +
					"vege_intro,unit_name,cate_name,mcate_name,vt_name,vs_name " +
					"from vege,countunit,category,vegetype,MainCategory,VegeStandard" +
					" where  vege_useflg='"+1+"' and vege.vege_id='"+msgvege[k]+"' " +
							"and vege.unit_id =  countunit.unit_id and " +
					"vege.cate_id = category.cate_id  and category.mcate_id = " +
					"maincategory.mcate_id and vege.vt_id = vegetype.vt_id " +
					" and vege.vs_id = VegeStandard.vs_id ";
			rs = st.executeQuery(sql);
			while(rs.next()){			
				String[] str = new String[18];
				for(int i=0;i<str.length;i++){
					str[i]=rs.getString(i+1);
				}
				list.add(str);
			}
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//��ȡ���еĲ�����Ϣ
	public static List<String[]> getRoom(){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String task = "select room_no,room_name from Room where room_delflg='0'";
			rs = st.executeQuery(task);
			while(rs.next()){			
				String[] str = new String[3];
			    str[0]=rs.getString(1);
			    str[1]=rs.getString(2);			    
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//��ȡ���еĲ�������
		public static List<String[]> getRoomName(){		
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			List<String[]> list = new ArrayList<String[]>();
			try{			
				st = con.createStatement();
				String task = "select room_name from Room where room_delflg='0'";
				rs = st.executeQuery(task);
				while(rs.next()){				
					String[] str = new String[1];
					str[0]=rs.getString(1);
					list.add(str);
				}
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
		}//ɾ������
		private static Object DELROOM_LOCK=new Object();
		public static String delRoom(String room_id){		
			synchronized(DELROOM_LOCK){			
				Connection con = getConnection();
				Statement st = null;
				try{ 				
					String curtime=TypeExchangeUtil.getCurTime();
					st=con.createStatement();
					String sql="update room set room_delflg='1',room_lastedittime=" +
							"'"+curtime+"'"+" where room_id='"+room_id+"'";
					System.out.println(sql);
					st.executeUpdate(sql);
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
				return "ok";
			}			
		}		
		//���ݲ������Ʋ�id
		public static String getRidbyname(String roomname) {
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			String rid=null;
			try{			
				st = con.createStatement();
				String sql="select room_id from room where room_name='"+roomname+"'";
				rs=st.executeQuery(sql);
				rs.next();
				 rid=rs.getString(1);
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return rid;
		}
		//��ȡ����id�����ֵ
		public static String getMaxRoomId(){		
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			String maxnum=null;
			try{			
				st=con.createStatement();
				String sql="select max(room_id) from room ";
				rs=st.executeQuery(sql);
			    rs.next();
			    if(rs.getString(1)==null){
			    	maxnum=String.valueOf(0);
			    }
			    else{
			    	maxnum=rs.getString(1);
			    }
			    System.out.println(maxnum);
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return maxnum;
		}
       //��Ӳ���		
		private static Object ADDROOM_LOCK=new Object();
		public static String addRoom(String rid,String rname,String rkeywd){		
			synchronized(ADDROOM_LOCK){			
				Connection con = getConnection();
				Statement st = null;
				try{				 
					st=con.createStatement();
					String curtime=(TypeExchangeUtil.getCurTime());
				String sql="insert into room(room_id,room_no,room_name,room_keywd," +
						"room_createtime,room_lastedittime) values ('"+rid+"'," +
						"'"+rid+"','"+rname+"','"+rkeywd+"','"+curtime+"','"+curtime+"')";							
					System.out.println(sql);
					st.executeUpdate(sql);
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
				return "ok";
			}			
		}	
         //���²���
		private static Object UPDATEROOM_LOCK=new Object();
		public static String updateRoom(String rid,String rname,String rkeywd){	
			synchronized(UPDATEROOM_LOCK){			
				Connection con = getConnection();
				Statement st = null;
				try{ 				
					st=con.createStatement();
					String curtime=(TypeExchangeUtil.getCurTime());								
					String sql="update  room set room_no='"+rid+"',room_name='"+rname+"'," +
					"room_keywd='"+rkeywd+"',room_lastedittime='"+curtime+"'where room_id='"+rid+"'";		
					System.out.println(sql);
					st.executeUpdate(sql);
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
				return "ok";
			}			
		}	
		//���ݲ�����ѯ��̨
				public static List<String[]> getPointByroom(String roomname){ 				
					Connection con = getConnection();
					Statement st = null;
					ResultSet rs = null;
					List<String[]> list = new ArrayList<String[]>();
					try{					
						st=con.createStatement();
						String roomid=getRidbyname(roomname);
						String task = "select point_id,point_name,ptype_id,point_stopflg ," +
								"point_num,point_state from Point where room_id='"+roomid+"'" +
										"and point_delflg='0'";
						rs = st.executeQuery(task);
						while(rs.next()){						
							String[] str = new String[7];
							str[0]=rs.getString(1);                              //��̨ID
							str[1]=rs.getString(2);                              //��̨����
							str[2]=rs.getString(3);                              
							str[2]=getPtypename(str[2]);                         //��̨����
							str[3]=rs.getString(4).equals("0")?"����":"��ͣʹ��";    //ͣ�ñ�ǣ��Ƿ����
							str[4]=rs.getInt(5)+"";                              //��̨����
							str[5]=rs.getString(6).equals("0")?"����":"����";       //��̨״̬���Ƿ�����
							str[6]=roomname;                                     //��������
							list.add(str);
						}
					}catch(Exception e){					
						e.printStackTrace();
						return null;
					}finally{					
						try {rs.close();} catch (SQLException e) {e.printStackTrace();}
						try {st.close();} catch (SQLException e) {e.printStackTrace();}
						try {con.close();} catch (SQLException e) {e.printStackTrace();}
					}
					return list;
		}
		//���ݲ�������id��ѯ����
		public static String getPtypename(String typeid) {
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			String ptypename=null;
			try{			
				st = con.createStatement();
				String sql="select ptype_name from pointtype where ptype_id='"+typeid+"'";
				rs=st.executeQuery(sql);
				rs.next();
				ptypename=rs.getString(1);
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return ptypename;
		}
		//��ȡ��̨id�����ֵ
		public static String getMaxPointId() {
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			String maxnum=null;
			try{			
				st = con.createStatement();
				st=con.createStatement();
				String sql="select max(point_id) from point";
				rs=st.executeQuery(sql);
			    rs.next();
			    if(rs.getString(1)==null){
			    	maxnum=String.valueOf(0);
			    }
			    else{
			    	maxnum=rs.getString(1);
			    }
				
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return maxnum;
		}
		//��ȡ���еĲ������͵�����
	public static List<String[]> getRoomTypeName() {
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String task = "select ptype_name from pointtype";
			rs = st.executeQuery(task);
			while(rs.next()){			
				String[] str = new String[1];
				str[0]=rs.getString(1);
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
		//��ȡ���еĲ������͵�����
		public static String getPTypeNameByid (String ptname){
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			String ptid=null;
			try{			
				st = con.createStatement();
				String task = "select ptype_id from pointtype where ptype_name='"+ptname+"'";
				rs = st.executeQuery(task);
				rs.next();
				ptid=rs.getString(1);
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return ptid;
		}
	    //��Ӳ�̨				
		private static Object ADDPOINT_LOCK=new Object();
		public static String addPoint(String pid,String pname,int pnum,String pkeywd,String ptype,String proom){		
			synchronized(ADDPOINT_LOCK){			
				Connection con = getConnection();
				Statement st = null;
				try{ 				
					ptype=getPTypeNameByid(ptype);                   //��ȡ���еĲ������͵�����
					proom=getRidbyname(proom);                       //���ݲ������Ʋ�id
					st=con.createStatement();
					String sql="insert into point(point_id,point_no,point_name,ptype_id,room_id," +
							"point_num) values ('"+pid+"','"+pid+"','"+pname+"','"+ptype+"','"+proom+"',"
							+pnum+")";
					System.out.println(sql);
					st.executeUpdate(sql);
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
				return "ok";
			}			
		}
        //ɾ����̨
		private static Object DELPOINT_LOCK=new Object();
		public static String delPoint(String point_id){		
			synchronized(DELPOINT_LOCK){			
				Connection con = getConnection();
				Statement st = null;
				try{				
					st=con.createStatement();
					String sql="update  point set point_delflg='1' where point_id='"+point_id+"'";
					System.out.println(sql);
					st.executeUpdate(sql);
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
				return "ok";
			}			
		}
		//���²�̨��Ϣ
		private static Object UPDATEPOINT_LOCK=new Object();
		public static String updatePoint(String pid,String pname,int num,String pkey,String ptype,String istop,String proom){		
			synchronized(UPDATEPOINT_LOCK){			
				Connection con = getConnection();
				Statement st = null;
				try{				 
					st=con.createStatement();

					ptype=getPTypeNameByid(ptype);                     //��ȡ���еĲ������͵�����
					proom=getRidbyname(proom);                        //���ݲ������Ʋ�id
					istop=istop.equals("����")?"0":"1";                 //����ͣ�ñ���ж��Ƿ�ͣ��
					String sql="update  point set point_no='"+pid+"',point_name='"+pname+"'," +
							"point_num="+num+",ptype_id='"+ptype+"',point_stopflg= '"+istop+"'," +
									"room_id='"+proom+"' where point_id='"+pid+"'";
					System.out.println(sql);
					st.executeUpdate(sql);
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
				return "ok";
			}			
		}
			//���²�̨��Ϣ
			private static Object UPDATEPOS_LOCK=new Object();
			public static String updatePos(String pid,String pname,String pbz,String proom){			
				synchronized(UPDATEPOS_LOCK){			
					Connection con = getConnection();
					Statement st = null;
					try{ 					
						st=con.createStatement();
						String updatetime=TypeExchangeUtil.getCurTime();
						proom=getRidbyname(proom);                            //���ݲ������Ʋ�id
						pbz=pbz.equals("����")?"":pbz;                         
						String sql="update  pos set pos_name='"+pname+"',pos_bz='"+pbz+
						"',room_id='"+proom+"',pos_lastedittime='"+updatetime+"' where pos_id='"+pid+"'";
						System.out.println(sql);
						st.executeUpdate(sql);
					}catch(Exception e){					
						e.printStackTrace() ;
						return null;
					}finally{					
						try{st.close();}catch(Exception e) {e.printStackTrace();}
						try{con.close();}catch(Exception e) {e.printStackTrace();}
					}
					return "ok";
				}				
			}		
			//��ѯ���յ����н��˵Ķ�����Ϣ
			public static List<String[]> getAlAccOrderInfo() {
						Connection con = getConnection();
						Statement st = null;
						ResultSet rs = null;
						List<String[]> list=new ArrayList<String[]>();
						try{						
							String time[]=TypeExchangeUtil.getCurTime().split(" ");
							st = con.createStatement();
							String sql="select point.point_name,o_id,o_guestnum,o_ODTime,o_workerID,o_totalPri" +
									" from orderinfo,point,settleaccounts " +
									"where orderinfo.point_id=point.point_id " +
											" and orderinfo.o_id =settleaccounts.sa_OrderId " +
											" and sa_createtime like '"+time[0]+"%' order by sa_createtime";
							System.out.println(sql);
							rs=st.executeQuery(sql);
							while(rs.next()){
								String str[]=new String[7];
								str[0]=rs.getString(1);                       //��������
								str[1]=rs.getString(2);                       //������
								str[2]=rs.getInt(3)+"";                       //�˿�����
								str[3]=rs.getString(4);                       //����ʱ��
								str[4]=rs.getString(5);                       //����ԱID
								str[5]=rs.getFloat(6)+"";                     
								str[6]=rs.getFloat(7)+"";                     //�ܼ۸�
								list.add(str);
							}
							System.out.println(list.size()+list.get(0)[0]);
						}catch(Exception e){						
							e.printStackTrace();
							return null;
						}finally{						
							try {rs.close();} catch (SQLException e) {e.printStackTrace();}
							try {st.close();} catch (SQLException e) {e.printStackTrace();}
							try {con.close();} catch (SQLException e) {e.printStackTrace();}
						}
						return list;
					}
			//���ݲ�ѯδ���˵Ķ�����Ϣ
			public static List<String[]> getOrderInfo(){ 			
						Connection con = getConnection();
						Statement st = null;
						ResultSet rs = null;
						List<String[]> list=new ArrayList<String[]>();
						try{						
							st = con.createStatement();
							String sql="select point_name, o_id,o_guestnum,o_ODTime,o_workerID,o_totalPri" +
									" from point,orderinfo" +
									" where  orderinfo.point_id=point.point_id " +
									"and  point_state='1' and o_SAFlg='0' and o_id not in(select sa_OrderId from settleaccounts)";
							System.out.println(sql);
							rs=st.executeQuery(sql);
							while(rs.next()){
								String str[]=new String[6];
								str[0]=rs.getString(1);                     //��̨����
								str[1]=rs.getString(2);                     //������
								str[2]=rs.getInt(3)+"";                     //�˿�����
								str[3]=rs.getString(4);                     //����ʱ��
								str[4]=rs.getString(5);                     //����ԱID
								str[5]=rs.getFloat(6)+"";                   //�ܼ۸�
								list.add(str);
							}
							System.out.println(list.size());
						}catch(Exception e){						
							e.printStackTrace();
							return null;
						}finally{						
							try {rs.close();} catch (SQLException e) {e.printStackTrace();}
							try {st.close();} catch (SQLException e) {e.printStackTrace();}
							try {con.close();} catch (SQLException e) {e.printStackTrace();}
						}
						return list;
					}
			//���ݲ�̨id��ѯ���Ѿ����˵Ķ�����Ϣ
			public static List<String[]> SOrderInfoByPName(String pname){ 			
						Connection con = getConnection();
						Statement st = null;
						ResultSet rs = null;
						List<String[]> list=new ArrayList<String[]>();
						try{						
							String str[]=new String[6];
							st = con.createStatement();
							String sql="select distinct point.point_name,o_id,o_guestnum,o_ODTime,o_workerID,o_totalPri" +
									" from orderinfo,point " +
									" where orderinfo.point_id=point.point_id and point_name='"+pname+"'";
							System.out.println(sql);
							rs=st.executeQuery(sql);
							while(rs.next()){							
								str[0]=pname;
								str[1]=rs.getString(2);
								str[2]=rs.getInt(3)+"";
								str[3]=rs.getString(4);
								str[4]=rs.getString(5);
								str[5]=rs.getFloat(6)+"";
								list.add(str);
							}
						}catch(Exception e){						
							e.printStackTrace();
							return null;
						}finally{						
							try {rs.close();} catch (SQLException e) {e.printStackTrace();}
							try {st.close();} catch (SQLException e) {e.printStackTrace();}
							try {con.close();} catch (SQLException e) {e.printStackTrace();}
						}
						return list;
			}
			//���ݲ�̨���Ʋ�ѯδ���˵Ķ�����Ϣ(���ڽ���)
			public static List<String[]> SAccountInfoByPName(String pname){ 			
						Connection con = getConnection();
						Statement st = null;
						ResultSet rs = null;
						List<String[]> list=new ArrayList<String[]>();
						try{
							String str[]=new String[5];
							st = con.createStatement();
							String sql="select point.point_id,o_id,o_guestnum,o_workerID,o_totalPri" +
									" from orderinfo,point " +
									"where orderinfo.point_id=point.point_id and point_name='"+pname+"' and o_SAFlg='0' ";
							System.out.println(sql);
							rs=st.executeQuery(sql);
							if(rs.next()){							
								str[0]=pname;                         //��̨����
								str[1]=rs.getString(2);               //����ID
								str[2]=rs.getInt(3)+"";               //��̨����
								str[3]=rs.getString(4);               //����ԱID
								str[4]=rs.getFloat(5)+"";             //�ܼ۸�
							}
							list.add(str);
						}catch(Exception e){						
							e.printStackTrace();
							return null;
						}finally{						
							try {rs.close();} catch (SQLException e) {e.printStackTrace();}
							try {st.close();} catch (SQLException e) {e.printStackTrace();}
							try {con.close();} catch (SQLException e) {e.printStackTrace();}
						}
						return list;
			}
			//���ݲ�̨id��ϸ������Ϣ
			 //���ݲ�̨���Ʋ�ѯ�����˻���δ���˵ģ��˻�δ�뿪�ģ�������ϸ��Ϣ
		  public static List<String[]> SOrderXxByPid(String pname){			    
						Connection con = getConnection();
						Statement st = null;
						ResultSet rs = null;
						String sql=null;
						List<String[]> list=new ArrayList<String[]>();
						try{						
							String pid=searchPointIdByName(pname);
							st = con.createStatement();
							sql="select o_id from orderinfo where o_SAFlg='0' and point_id='"+pid+"'";
							rs=st.executeQuery(sql);
							rs.next();
							String oid=rs.getString(1);
							sql="select vege_name,odc_vegeQua,odc_vegePri,odc_remark" +
									" from orderdishconfirm ,vege " +
									"where orderdishconfirm.odc_vege_id=vege.vege_id and odc_orderID='"+oid+"' and odc_delFlg='0'";
							System.out.println(sql);
							rs=st.executeQuery(sql);
							while(rs.next()){
								String str[]=new String[5];
								str[0]=rs.getString(1);
								str[1]=pname;
								str[2]=rs.getInt(2)+"";
								str[3]=rs.getFloat(3)+"";	
								System.out.println(str[4]+"ddd");
								System.out.println(str[4]);
								str[4]=(rs.getString(4)+"").equals("null")?"����":(rs.getString(4)+"");
								list.add(str);								
							}
						}catch(Exception e){						
							e.printStackTrace();
							return null;
						}finally{						
							try {rs.close();} catch (SQLException e) {e.printStackTrace();}
							try {st.close();} catch (SQLException e) {e.printStackTrace();}
							try {con.close();} catch (SQLException e) {e.printStackTrace();}
						}
						return list;
				}
//==================��׼�ײ�����=========
   //���ݹ��id��ѯ����
     public static String getStname(String id) {
						Connection con = getConnection();
						Statement st = null;
						ResultSet rs = null;
						String list=null;
						try{						
							st = con.createStatement();							
							String sql="select vs_name from vegestandard where vs_id ='"+id+"'";
							System.out.println(sql);
							rs=st.executeQuery(sql);
							rs.next();
							list=rs.getString(1);						
						}catch(Exception e){						
							e.printStackTrace();
							return null;
						}finally{						
							try {rs.close();} catch (SQLException e) {e.printStackTrace();}
							try {st.close();} catch (SQLException e) {e.printStackTrace();}
							try {con.close();} catch (SQLException e) {e.printStackTrace();}
						}
						return list;
					}     
 	//���ݲ�Ʒid�õ����ɺ͹��
     public static String[] getVegeNameStd(String id) {
						Connection con = getConnection();
						Statement st = null;
						ResultSet rs = null;
						String[] str=new String[2];
						try{						
							st = con.createStatement();							
							String sql="select vege_name,vs_id from vege where vege_id ='"+id+"'";
							System.out.println(sql);
							rs=st.executeQuery(sql);
							while(rs.next()){								
								str[0]=rs.getString(1);
								str[1]=rs.getString(2);
								str[1]=getStname(str[1]);								
							}
						}catch(Exception e){						
							e.printStackTrace();
							return null;
						}finally{						
							try {rs.close();} catch (SQLException e) {e.printStackTrace();}
							try {st.close();} catch (SQLException e) {e.printStackTrace();}
							try {con.close();} catch (SQLException e) {e.printStackTrace();}
						}
						return str;
					}
  //���ÿ����ĳ���
 public static int getCount(String tname){ 
	 Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		int num=0;		
		try{ 		 
			st=con.createStatement();
			String sql="select count(*) from "+tname;
			rs=st.executeQuery(sql);
			rs.next();
			num=rs.getInt(1);
		}catch(Exception e){		
			e.printStackTrace();
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return num;
 }
//---------------------------------------------lzk----begin(PC��)
	   //Ա����workerInfo-------------------------------begin
			//��ȡԱ����Ϣ
			public static List<String[]> getWorkerInfo(){				
				Connection con = getConnection();
				Statement st = null;
				ResultSet rs = null;
				List<String[]> list = new ArrayList<String[]>();
				try{
					st = con.createStatement();
					String sql = "select w_id,w_name,w_password," +
							"w_sex, " +							
							"role_id from workerinfo where w_delflg='0'";
					rs = st.executeQuery(sql);
					while(rs.next()){    //����µĵ�ǰ����Ч���򷵻� true�������������һ�У��򷵻� false
						String str[] = new String[5];  
						for(int i=0; i<str.length; i++){
							str[i] = rs.getString(i+1);
						}
						list.add(str);
					}
					System.out.println("list.size="+list.size());   //					
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}finally{		 		
		 			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		 			try {st.close();} catch (SQLException e) {e.printStackTrace();}
		 			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		 		}
				return list;
			}
			//����Ա��id,��ѯĳһ��Ա������Ϣ
			public static String[] getOneOfWorkerInfo(String w_id){				
				Connection con = getConnection();
				Statement st = null;
				ResultSet rs = null;
				String[] resultArr = new String[5];
				try{
					st = con.createStatement();
					String sql = "select w_id,w_name,w_password," +
							"w_sex, " +
							"role_id  from workerinfo where  w_id='"+w_id+"'";
					rs = st.executeQuery(sql);
					if(rs.next()){ //����µĵ�ǰ����Ч���򷵻� true�������������һ�У��򷵻� false					   
						for(int i=0; i<resultArr.length; i++){						
							resultArr[i] = rs.getString(i+1);
						}
					}					
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}finally{		 		
		 			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		 			try {st.close();} catch (SQLException e) {e.printStackTrace();}
		 			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		 		}
				return resultArr;
			}
			public static Object DEL_WORKERINFO = new Object();
				public static String delWorkerInfo(String id){					
					synchronized(DEL_WORKERINFO){       //���²�������Ҫͬ��						
						Connection con = getConnection();
						Statement st = null;
						try{						
							st=con.createStatement();
							String sql="update workerinfo set w_delflg='1' where w_id='"+id+"'";
							st.executeUpdate(sql);
							
						}catch(Exception e){						
							e.printStackTrace();
							return null;
						}finally{
							try{ st.close(); }catch(Exception e){e.printStackTrace();}
							try{ con.close();}catch(Exception e){e.printStackTrace();}
						}
						return "ok";
					}					
			}
				private static Object UPDATE_WORKERINFO = new Object();
		 		public static String updateWorkerInfo(String param[]){		 		
		 			synchronized(UPDATE_WORKERINFO){		 			
		 				Connection con = getConnection();
		 				Statement st = null;
		 				try{		 				
		 					st = con.createStatement();
		 					String sql = "update workerinfo set w_name= '"+param[2]+"', " +
		 							"w_password= '"+param[3]+"',w_sex= '"+param[4]+"',"+
		 				            " role_id= '"+param[5]+"' "+
		 							"where w_id='"+param[1]+"'";
		 					System.out.println("sql="+sql);
		 					st.executeUpdate(sql);
		 				}catch(Exception e){		 				
		 					e.printStackTrace() ;
		 					return null;
		 				}finally{		 				
		 					try{st.close();}catch(Exception e) {e.printStackTrace();}
		 					try{con.close();}catch(Exception e) {e.printStackTrace();}
		 				}
		 				return "ok";
		 			}		 			
		 		}
		 		//��ȡWorkerInfo���е������
		 		public static String getWorkerMaxNo(){		 			
		 			String maxNo = null;
		 			Connection con = getConnection();
		 			Statement st = null;
		 			ResultSet rs = null;
		 			try{
		 				st = con.createStatement();
		 				String sql = "select max(w_id) from workerInfo";
		 				rs = st.executeQuery(sql);
		 				rs.next();
		 				maxNo = rs.getString(1);
		 				if(maxNo==null){  
		 					maxNo = "0";
		 				}
		 				System.out.println("maxNO="+maxNo);
		 			}catch(Exception e){
		 				e.printStackTrace();
		 				return null;
		 			}finally{
		 				try{ rs.close(); }catch(Exception e){ e.printStackTrace();}
		 				try{ st.close(); }catch(Exception e){ e.printStackTrace();}
		 				try{ con.close(); }catch(Exception e){ e.printStackTrace();}
		 			}
		 			return maxNo;
		 		}		 		
		 		private static Object ADD_WORKERINFO = new Object();
		 		public static String addWorkerInfo(String param[]){		 		
		 			synchronized(ADD_WORKERINFO){		 			
		 				Connection con = getConnection();
		 				Statement st = null;
		 				try{		 				
		 					st = con.createStatement();
		 					String sql="insert into workerinfo(w_id,w_name,w_password," +
		 							"w_sex," +"role_id) " +
		 							"values('"+param[1]+"','"+param[2]+"','"+param[3]+"','"+param[4]+"', '"+param[5]+"' )";
		 					st.executeUpdate(sql);		 				
		 				}catch(Exception e){		 				
		 					e.printStackTrace() ;
		 					return null;
		 				}finally{		 				
		 					try{st.close();}catch(Exception e) {e.printStackTrace();}
		 					try{con.close();}catch(Exception e) {e.printStackTrace();}
		 				}
		 				return "ok";
		 			}		 			
		 		}
			//Ա����WorkerInfo-------------------------------------------end
			//��ɫ��Role-------------------------------------begin
				public static List<String[]> getRole(){					
					Connection con = getConnection();
					Statement st = null;
					ResultSet rs = null;
					List<String[]> list = new ArrayList<String[]>();
					try{
						st = con.createStatement();
						String sql = "select role_id,role_name from role";
						rs = st.executeQuery(sql);
						while(rs.next()){    //����µĵ�ǰ����Ч���򷵻� true�������������һ�У��򷵻� false
							String str[] = new String[2];
							str[0] = rs.getString(1);
							str[1] = rs.getString(2);
							list.add(str);
						}
						System.out.println("list.size="+list.size());   //						
					}catch(Exception e){
						e.printStackTrace();
						return null;
					}finally{			 		
			 			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			 			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			 			try {con.close();} catch (SQLException e) {e.printStackTrace();}
			 		}
					return list;
				}
				public static String[] getRoleID(){					
					Connection con = getConnection();
				    Statement st = null;
				    ResultSet rs = null;
					String[] ll=new String[100];
					try{					  
						st=con.createStatement();
						String sql="select role_id from role";
						rs=st.executeQuery(sql);
						int k=0;
						while(rs.next()){							
							ll[k]=rs.getString(1);
							k++;
						}	
						for(int i=0;i<ll.length;i++){
							System.out.println("ll["+i+"]="+ll[i]);
						}
					}catch(Exception e){					
						e.printStackTrace();
					}
					finally{
						try {rs.close();} catch (SQLException e) {e.printStackTrace();}
						try {st.close();} catch (SQLException e) {e.printStackTrace();}
						try {con.close();} catch (SQLException e) {e.printStackTrace();}
					}
					return ll;					
				}
				//��ɫ��Role-------------------------------------end				
				//Ȩ�ޱ�Authority-------------------------------begin
				public static List<String[]> getAuthority(){					
					Connection con = getConnection();
					Statement st = null;
					ResultSet rs = null;
					List<String[]> list = new ArrayList<String[]>();
					try{
						st = con.createStatement();
						String sql = "select authority_id,authority_name from authority";
						rs = st.executeQuery(sql);
						while(rs.next()){    //����µĵ�ǰ����Ч���򷵻� true�������������һ�У��򷵻� false
							String str[] = new String[2];
							str[0] = rs.getString(1);
							str[1] = rs.getString(2);
							list.add(str);
						}
						System.out.println("list.size="+list.size());  
						
					}catch(Exception e){
						e.printStackTrace();
						return null;
					}finally{			 		
			 			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			 			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			 			try {con.close();} catch (SQLException e) {e.printStackTrace();}
			 		}
					return list;
				}
				public static String[] getAuthorityID(){					
					Connection con = getConnection();
				    Statement st = null;
				    ResultSet rs = null;
					String[] ll=new String[100];
					try{					  
						st=con.createStatement();
						String sql="select authority_id from authority";
						rs=st.executeQuery(sql);
						int k=0;
						while(rs.next()){							
							ll[k]=rs.getString(1);
							k++;
						}	
						for(int i=0;i<ll.length;i++){
							System.out.println("ll["+i+"]="+ll[i]);
						}
					}catch(Exception e){					
						e.printStackTrace();
					}
					finally{
						try {rs.close();} catch (SQLException e) {e.printStackTrace();}
						try {st.close();} catch (SQLException e) {e.printStackTrace();}
						try {con.close();} catch (SQLException e) {e.printStackTrace();}
					}
					return ll;					
				}
				//Ȩ�ޱ�Authority-------------------------------end
				//Ȩ�޷����authority_role--------------------------------------begin
				public static List<String[]> getAuthorityRole(){					
					Connection con = getConnection();
					Statement st = null;
					ResultSet rs = null;
					List<String[]> list = new ArrayList<String[]>();
					try{
						st = con.createStatement();
						String sql = "select role_id,authority_id from authority_role";
						rs = st.executeQuery(sql);
						while(rs.next()){    //����µĵ�ǰ����Ч���򷵻� true�������������һ�У��򷵻� false
							String str[] = new String[2];
							str[0] = rs.getString(1);
							str[1] = rs.getString(2);
							list.add(str);
						}
						System.out.println("list.size="+list.size());   //						
					}catch(Exception e){
						e.printStackTrace();
						return null;
					}finally{			 		
			 			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			 			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			 			try {con.close();} catch (SQLException e) {e.printStackTrace();}
			 		}
					return list;
				}			
				public static boolean isAuthorityRoleID(String authority_id,String role_id){					
					Connection con = getConnection();
				    Statement st = null;
				    ResultSet rs = null;
					try{					  
						st=con.createStatement();
						String sql="select role_id,authority_id from authority_role " +
								"where authority_id='"+authority_id+"' and role_id='"+role_id+"'";
						rs=st.executeQuery(sql);
						if(rs.next()!=false){
							return true;
						}else{
							return false;
						}
					}catch(Exception e){					
						e.printStackTrace();
					}
					finally{
						try {rs.close();} catch (SQLException e) {e.printStackTrace();}
						try {st.close();} catch (SQLException e) {e.printStackTrace();}
						try {con.close();} catch (SQLException e) {e.printStackTrace();}
					}
					return false;					
				}
				//���ݲ�̨������ϸ������Ϣ
public static List<String[]> SOrderACountByPid(String pname){
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql=null;
		List<String[]> list=new ArrayList<String[]>();
		try{		
			String pid=searchPointIdByName(pname);
			st = con.createStatement();
			sql="select o_id from orderinfo where o_SAFlg='0' and point_id='"+pid+"'";
			rs=st.executeQuery(sql);
			rs.next();
			String oid=rs.getString(1);
			sql="select vege_name,odc_vegeQua,odc_vegePri,unit_name,vs_name " +
					" from orderdishconfirm,vege,countunit,vegestandard " +
					"where odc_orderID='"+oid+"' and vege.unit_id=countunit.unit_id" +
					" and orderdishconfirm.odc_vege_id=vege.vege_id and vege.vs_id=vegestandard.vs_id ";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()){			
				String str[]=new String[4];
				str[0]=rs.getString(1);
				str[1]=rs.getInt(2)+"";
				str[2]=rs.getFloat(3)+"/"+rs.getString(4);
				str[3]=rs.getString(5);
				list.add(str);				
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
}
//���˵��������ݿ⣨������״̬��0����̨������1�����ã�
//���½��㷽ʽ�����룩
private static Object INSERTACCOUT=new Object();
public static String insertAccount(String str[]){
/*(0�������ţ�1.������2.Ӧ�շ���3.���ۺ�ķ���4.������5.ʵ�շ���6.�һ�Ǯ��7.��ȯ���8.���㷽ʽ����9.��������10.��ȯ��11Ĩ���Ǯ��12���Ƿ񿪷�Ʊ��� 13.������,14.����˱��
 * �£�0�������ţ�1.������2.Ӧ�գ�3.ʵ�գ�4.�һأ�5.��Ʊ��6.�����ˣ�7.�����
	String sa_giveBillflg,String orderid,String deskname,String allmoney,
	String dmoney, String givemoney,String backmoney,String sa_name,String disc_name,
	String zqMoney,String ticketScale,String createw_id
) */
		synchronized(INSERTACCOUT){		
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs=null;
			String sql=null;
		    String maxid="0";
			try{			  
				st = con.createStatement();
				//�õ���ǰʱ��
				String curtime=TypeExchangeUtil.getCurTime();
				//��ȡ�������ֵ
				sql="select max(sa_streamID) from settleaccounts";
				rs=st.executeQuery(sql);
				rs.next();
				if(rs.getString(1)!=null){				
					maxid=rs.getString(1);
				}
				maxid=(Integer.parseInt(maxid)+1)<10?"00"+(Integer.parseInt(maxid)+1):(Integer.parseInt(maxid)+1)<100?"0"+(Integer.parseInt(maxid)+1):(Integer.parseInt(maxid)+1)+"";
				System.out.println();
				//�õ��ۿ�id
				float totalfee=Float.parseFloat(str[3]);//���е�Ǯ
				//������˼�¼
				 sql="insert into settleaccounts(sa_streamID,sa_OrderId,sa_createtime,createw_id," +
						"sa_requestReceive,sa_factReceive,sa_ZLMoney," +"sa_giveBillFlg)" +
						"values('"+maxid+"','"+str[1]+"','"+curtime+"','"+str[6]+"','"+
								+totalfee+"',"+Float.parseFloat(str[4])+","+Float.parseFloat(str[5])+",'"+str[7]+"')";
				 System.out.println(sql);
				 st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{rs.close();}catch(Exception e){e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
//=============================�����================
//���ݲ�Ʒ���ƻ�ȡid
public static String getVegeIdByName(String vegename) {
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			String str=null;
			try{			
				st = con.createStatement();
				String sql="select vege_id from vege where vege_name='"+vegename+"'";
			    System.out.println(sql);
			    rs=st.executeQuery(sql);
		        rs.next();
		        str=rs.getString(1);	
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	} 
//��ȡ��Ʒ������Ϣ
public static List<String[]> getVegeSomeInfo(){
Connection con = getConnection();
Statement st = null;
ResultSet rs = null;
List<String[]> list = new ArrayList<String[]>();
try{
	st = con.createStatement();
	String task = "select vege_id,vege_name,vs_id,vege_vegeprice from vege where vege_useflg='1'";
rs = st.executeQuery(task);
while(rs.next()){
	String[] str = new String[4];
    str[0]=rs.getString(1);
    str[1]=rs.getString(2);
    str[2]=rs.getString(3);
    str[2]=getStname(str[2]);
    str[3]=""+rs.getFloat(4);
		list.add(str);
	}
}catch(Exception e){
	e.printStackTrace();
	return null;
}finally{
	try {rs.close();} catch (SQLException e) {e.printStackTrace();}
	try {st.close();} catch (SQLException e) {e.printStackTrace();}
	try {con.close();} catch (SQLException e) {e.printStackTrace();}
}
return list;
}
//==============����Ա�ֳֶ�
//�жϵ�½Ȩ��
	public static String pdWaiter(String str){	
	    Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    String name=null;
		try{		 
			st=con.createStatement();
			String sql="select w_password from WorkerInfo where w_id='"+str+"'and w_delflg='0'";
			rs=st.executeQuery(sql);
			rs.next();
			name=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return name;
	}
	//���Ա���Ƿ��Ѿ���½
	public static String isHasLogin(String str){	
	    Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    String name=null;
		try{		  
			st=con.createStatement();
			String sql="select is_loginflg from WorkerInfo where w_id='"+str+"'and w_delflg='0'";
			rs=st.executeQuery(sql);
			rs.next();
			name=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return name;
	}
	//���¶���
		private static Object UPDATELOgINFLG=new Object();
		public static String updateLoginFlg(String wid,String flg){		
			synchronized(UPDATELOgINFLG){			
				Connection con = getConnection();
				Statement st = null;
				try{				
					st = con.createStatement();
					String sql = "update workerinfo set is_loginflg='"+flg+"' where w_id='"+wid+"'";
					System.out.println("sql="+sql);
					st.executeUpdate(sql);
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
				return "ok";
			}
		}
	//����Ա��id��ѯȨ������
	public static List<String[]> getLoginAuth(String w_id){ 	
	    Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
	    List<String[]> list=new ArrayList<String[]>();
		try{		  
			st=con.createStatement();
			String sql="select authority_name from authority where authority_id in" +
					"(select authority_id from authority_role where role_id in" +
					"(select role_id from workerinfo where w_id='"+w_id+"'))";
			rs=st.executeQuery(sql);
			System.out.println(sql);
			while(rs.next()){
				String str[]=new String[1];
				str[0]=rs.getString(1);
				System.out.println(str[0]);
				list.add(str);
			}		
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	///===============================�ֳֶ�
	public static String getCurDeskState(String tname){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
	    String state = "0";
		try{		
			String pid=searchPointIdByName(tname);
			st=con.createStatement();
			String task = "select point_state from Point where point_id='"+pid+"'and point_delflg='0'";
			rs = st.executeQuery(task);
			if(rs.next()){			
			 state=rs.getString(1); 
			}
			System.out.println(state);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return state;
	}		
	//���ݲ�����ѯ��̨
	public static List<String[]> getClientPointByroom(String roomname,String ptype_id){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st=con.createStatement();
			String roomid=getRidbyname(roomname);
			String task = "select point_id,point_name,point_num,point_state from Point where room_id='"+roomid+"'and point_delflg='0' and ptype_id='"+ptype_id+"'";
			rs = st.executeQuery(task);
			while(rs.next()){			
				String[] str = new String[5];
				str[0]=rs.getString(1);
				str[1]=rs.getString(2);
				str[2]=getPtypename(ptype_id);				
				str[3]=rs.getInt(3)+"";
				str[4]=rs.getString(4);				
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//���ݶ�����Ϣ��ѯ�����Ʒ
	public static List<String[]> getClientOrderById(String orderid){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql=null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st=con.createStatement();			
			sql = "select orderinfo.point_id,orderinfo.o_totalPri,orderdishconfirm.odc_setMealFlg " +
					"from orderinfo,orderdishconfirm where o_id='"+orderid+"'and orderinfo.o_id=orderdishconfirm.odc_orderId";
			rs = st.executeQuery(sql);
			rs.next();
			String order[]=new String[3];
			for(int i=0;i<3;i++){
				order[i]=rs.getString(i+1);
				System.out.println("order="+order[i]);
			}
			if(order[2].equals("1")){
				//������ײͲ�ѯ
			}else{
				sql="select vege.vege_name,orderdishconfirm.odc_vegePri,orderdishconfirm.odc_vegeQua," +
						"countunit.unit_name" +
						" from vege,orderdishconfirm,countunit where " +
						"vege.vege_id=orderdishconfirm.odc_vege_id and " +
						"vege.unit_id=countunit.unit_id and orderdishconfirm.odc_vegeQua>0";
				System.out.println(sql);
			rs=st.executeQuery(sql);
			while(rs.next()){			
				String[] str = new String[11];
				str[0]=rs.getString(1);
				str[1]=rs.getFloat(2)+"";
				str[2]=rs.getInt(3)+"";
				str[3]=rs.getString(4);
				str[4]=rs.getString(5);
				str[5]=rs.getString(6);
				str[6]=rs.getString(7);
				str[7]=rs.getString(8);
				str[8]=rs.getString(9);
				str[9]=order[0];
				str[10]=order[1];
				list.add(str);
			}
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//�޸Ĳ�Ʒ��Ŀ
	private static Object UPDATENUM_LOCK=new Object();
	public static String updateClientNum(String vnum,String vegeprice,
						String orderid,String vegename){	
		synchronized(UPDATENUM_LOCK){		
			Connection con = getConnection();
			Statement st = null;
			String sql=null;
			int num=Integer.parseInt(vnum);
			float price=Float.parseFloat(vegeprice);
			String vid=getVegeIdByName(vegename);
			try{			 
				st=con.createStatement();
				sql="update orderdishconfirm set odc_vegeQua="+num+" where odc_orderId='"+orderid+"'and odc_vege_id='"+vid+"'";
				System.out.println(sql);
				st.executeUpdate(sql);
				sql="update orderinfo set o_totalPri="+price;
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
//����Ա�ֳֶ�===========================================
	//�޸�����
		private static Object UPDATEPASSWORD=new Object();
		public static String updatePassWord(String wid,String password){ 		
			synchronized(UPDATEPASSWORD){			
				Connection con = getConnection();
				Statement st = null;
				try{				
					st = con.createStatement();
					String sql = "update workerinfo set w_password='"+password+"' where w_id='"+wid+"'";
					st.executeUpdate(sql);
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
				return "ok";
			}
		}
	//��ȡ���еĲ���id,��ź����� ��Ϣ
	public static List<String[]> getRoomIdName(){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String task = "select room_id,room_no,room_name from Room where room_delflg='0'";
			System.out.println(task);
			rs = st.executeQuery(task);
			while(rs.next()){			
				String[] str = new String[3];
			    str[0]=rs.getString(1);
			    str[1]=rs.getString(2);
			    str[2]=rs.getString(3);
			    System.out.println(str[0]);
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}	
	//��ȡ���еĲ������͵�ID������
	public static List<String[]> getRoomTypeIdName(){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String task = "select ptype_id,ptype_name from pointtype where ptype_delflg='0'";
			rs = st.executeQuery(task);
			while(rs.next()){			
				String[] str = new String[2];
				str[0]=rs.getString(1);
				str[1] = rs.getString(2);
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//��ȡ����Ա�ֳֶ���Ҫ�Ĳ�̨��Ϣ
	public static List<String[]> getPointForWaiter(String room_id,String ptype_id){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String sql = "select point_id,point_no,point_name,point_state,point_num,room_name," +
						"ptype_name from point,room,pointtype " +"where point_delflg='0' and " +
						"room.room_id=point.room_id and point.ptype_id=pointtype.ptype_id and " +
						"point.room_id='"+room_id+"' and point.ptype_id='"+ptype_id+"'";
			rs = st.executeQuery(sql);
			while(rs.next()){			
				String[] str = new String[7];
				for(int i=0; i<str.length; i++){				
					str[i] = rs.getString(i+1);
				}
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//��ȡ����Ա�ֳֶ����˵Ĳ�̨��Ϣ
	public static List<String[]> getNoPersonPoint(String room_id,String ptype_id){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String sql = "select point_id,point_no,point_name,point_state,point_num,room_name," +
						"ptype_name from point,room,pointtype " +"where point_delflg='0' and " +
						"room.room_id=point.room_id and point.ptype_id=pointtype.ptype_id and " +
						"point.room_id='"+room_id+"' and point.ptype_id='"+ptype_id+"' and point.point_state='0'";
			rs = st.executeQuery(sql);
			while(rs.next()){			
				String[] str = new String[7];
				for(int i=0; i<str.length; i++){				
					str[i] = rs.getString(i+1);
				}
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//--------------begin--(����Ա�ֳֶ�)
	////////////-------------orderManage-begin
	//����Ա�˻�ȡ����ȷ�ϵ��еĲ�����Ϣ
	public static List<String[]> getOrderConfirmForWaiter(String pName){ 	
	Connection con = getConnection();
	Statement st = null;
	ResultSet rs = null;
	String sql=null;
	String pId=null;
	@SuppressWarnings("unused")
	String tempId=null;
	//��ŵ��ȷ�ϵ��е���Ϣ
	List<String[]> vegeInfo=new ArrayList<String[]>();  
	//������������еĸ�����id
    List<String[]> mergeTableNum=new ArrayList<String[]>();
	try{	
		st = con.createStatement();
		pId=searchPointIdByName(pName);
		mergeTableNum.add( new String[]{pId,pName});		
		sql="select odc_user_id,odc_orderId,odc_serveTime,odc_vege_id,odc_vegeQua," +
			"odc_vegePri,odc_code"+
			" from orderdishconfirm" +
			" where odc_orderId in(select o_id from orderinfo where point_id='"+pId+"' and o_SAFlg='0' ) and" +
			" odc_delFlg='0' ";
		rs=st.executeQuery(sql);
		System.out.println("sql2="+sql);
		while(rs.next()){		
			int j=0;
			String str[]=new String[9];  //str[16]����������str[17]��Ų�Ʒ����
			//��ȡ������е�����
			for(;j<str.length-2;j++){			
				str[j]=rs.getString(j+1);
			}
			str[j]=mergeTableNum.get(0)[1];  //���������ӵ�ÿ����Ϣ�����λ��
			vegeInfo.add(str);
		}				
		//�ж����������Ƿ��ѯ����Ʒ��Ϣ��������ж�,����Ʒ��ϢΪ��ʱ���ͻ������ָ���쳣
		if(vegeInfo!=null){		
			//���ݲ�Ʒid��ȡ��Ʒ���ƣ�����ӵ�ÿ����Ʒ���ݵ�β��
			String vegeId[]=new String[vegeInfo.size()];
			String vegeName[];
			for(int i=0; i<vegeId.length; i++){			
				System.out.println("vegeId[i]"+vegeInfo.get(i)[3]);
				vegeId[i]=vegeInfo.get(i)[3];
			}
			vegeName=getVegeNameForWaiter(vegeId);
			for(int i=0; i<vegeName.length;i++){			
				vegeInfo.get(i)[8]=vegeName[i];
			}
		}
	}
	catch(Exception e){	
		e.printStackTrace();
		return null;
	}
	finally{	
		try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		try {st.close();} catch (SQLException e) {e.printStackTrace();}
		try {con.close();} catch (SQLException e) {e.printStackTrace();}
	}
	return vegeInfo;
	}
	//���ݲ�̨���Ʋ�ѯ��̨id
	public static String searchPointNameById(String id){ 	
	Connection con=getConnection();
	Statement st=null;
	ResultSet rs=null;
	String pointName=null;
	try{
		st=con.createStatement();
		String sql="select point_name from point where point_id='"+id+"'";
		rs=st.executeQuery(sql);
	    if(rs.next()){	    
	    	pointName=rs.getString(1);
	    }		
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
	finally{
		try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		try {st.close();} catch (SQLException e) {e.printStackTrace();}
		try {con.close();} catch (SQLException e) {e.printStackTrace();}
	}
	return pointName;
	}
	//����Ա��-----���µ��ȷ�ϵ�����
	private static Object UPDATE_W_ORDERCONFIRM=new Object();
	public static String updateOrderConfirm(List<String[] > confirmList){	
	synchronized(UPDATE_W_ORDERCONFIRM){	
		Connection con=getConnection();
		Statement st=null;
	    String sql=null;
	    String odc_cancelCauseID = null;
	    String odc_cancelQua = null;
		try{		
			st=con.createStatement();
			for(String[] confirmArray : confirmList){			
				odc_cancelCauseID =  confirmArray[10];
				odc_cancelQua =  confirmArray[11];
				sql = "update OrderDishConfirm set odc_cancelCauseID= '"+odc_cancelCauseID+"', odc_cancelQua= '"+odc_cancelQua+"' " +
							"where odc_vege_id='"+confirmArray[3]+"'  and odc_orderId='"+confirmArray[1]+"'";
				System.out.println(sql);
				st.executeUpdate(sql);	
			}
		}
		catch(Exception e){		
			e.printStackTrace();
			return null;
		}
		finally{		
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return "ok";
	}
	}
	//����Ա������һ���Ʒid�õ���Ʒ����
	public static String[] getVegeNameForWaiter(String vege_id[]){ 	
	Connection con = getConnection();
	Statement st = null;
	ResultSet rs = null;
	String[] str=new String[vege_id.length];
	try{	
		st = con.createStatement();
		for(int i=0; i<vege_id.length; i++){		
			String sql="select vege_name from vege where vege_id ='"+vege_id[i]+"'";
			rs=st.executeQuery(sql);
			if(rs.next()){			
				str[i]=rs.getString(1);
			}
		}
	}
	catch(Exception e){	
		e.printStackTrace();
		return null;
	}
	finally{	
		try {if(rs!=null){rs.close();} } catch (SQLException e) {e.printStackTrace();}
		try {if(st!=null){st.close();} } catch (SQLException e) {e.printStackTrace();}
		try {if(con!=null){con.close();} } catch (SQLException e) {e.printStackTrace();}
	}
	return str;
	}
	//ɾ�����ȷ�ϵ��еĲ�Ʒ
		private static Object DELETEOCVEGE=new Object();
		public static String deleteOrderConfirmVege(String[] params){		
		synchronized(DELETEOCVEGE){		
			Connection con=getConnection();
			Statement st=null;
		    String sql=null;
		    ResultSet rs=null;
		    @SuppressWarnings("unused")
			String curCount=null;
		    String result="fail";
		    @SuppressWarnings("unused")
			String distributeFlg=null;
			try{			
				st=con.createStatement();
					sql="update orderdishconfirm set odc_delFlg='1'" +
							"where  odc_orderId='"+
							params[2]+"' and odc_vege_id='"+params[3]+"' and odc_code='"+params[4]+"'";
					System.out.println(sql);
					st.executeUpdate(sql);	
					sql="SELECT odc_vegeQua FROM orderdishconfirm"+
							" where odc_user_id='"+params[1]+"' and odc_orderId='"+
							params[2]+"' and odc_vege_id='"+params[3]+"' and odc_code='"+params[4]+"'";
					rs=st.executeQuery(sql);
					if(rs.next()){					
						curCount=rs.getString(1);
					}
			}
		catch(Exception e){		
			e.printStackTrace();
			return result;
		}
		finally{		
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}
	}	
	//����Ʒ��������1,�����˲�������1
	private static Object SUBVEGECOUNT=new Object();
	public static String subVegeCount(String[] params){	
	synchronized(SUBVEGECOUNT){	
		Connection con=getConnection();
		Statement st=null;
		ResultSet rs=null;
	    String sql=null;
	    List<String[]> list=new ArrayList<String[]>();
	    String result="fail";
	    @SuppressWarnings("unused")
		String distributeFlg=null;
	    int curVegeCount=0;
		try{		
			st=con.createStatement();
			sql="select odc_vegeQua from OrderDishConfirm " +
					"where  odc_orderId='"+
					params[2]+"' and odc_vege_id='"+params[3]+"' and odc_code='"+params[4]+"'";
			rs=st.executeQuery(sql);
			if(rs.next()){			
				curVegeCount=rs.getInt(1);
				System.out.println("-----curVegeCount="+curVegeCount);
			}
//			//�����ǰ������Ϊ1�����������˲ˣ�ֻ�ܹ�ɾ��
//			//ֻ���ڲ�����Ա�������ٵĵ���˲˰�ťʱ��������ٶȴ����Ļ��Ʒ�����ĸı��ٶȣ�
//			//�ͻ���ɵ���Ʒ��ʵ�����Ѿ�Ϊ1�ˣ�����Ȼ����ִ�м��ٲ�Ʒ�Ĳ�����������ݿ��е�����Ϊ��ֵ����������ʾ������Ϊ1
			if(curVegeCount<=1){			
				result="ok";
				return result;
			}
			sql="update orderdishconfirm set odc_vegeQua=odc_vegeQua-1 where  odc_orderId='"+
					params[2]+"' and odc_vege_id='"+params[3]+"' and odc_user_id='"+params[1]+"' and odc_code='"+params[4]+"'";
			System.out.println(sql);
			st.executeUpdate(sql);	
			list.add(new String[]{params[3],"1"}); //odc_vege_id,����1
		}		
		catch(Exception e){		
			e.printStackTrace();
			return result;
		}
		finally{		
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}
	}	
	//����Ʒ��������1
	private static Object ADDVEGECOUNT=new Object();
	public static String addVegeCount(String[] params){	
		synchronized(ADDVEGECOUNT){		
			Connection con=getConnection();
			Statement st=null;
		    String sql=null;
		    String result="fail";
			try{			
				st=con.createStatement();
					sql="update orderdishconfirm set odc_vegeQua=odc_vegeQua+'1' "+
							"where odc_user_id='"+params[1]+"' and odc_orderId='"+
							params[2]+"' and odc_vege_id='"+params[3]+"' and odc_code='"+params[4]+"'";
					System.out.println(sql);
					st.executeUpdate(sql);	
			}
			catch(Exception e){			
				e.printStackTrace();
				return result;
			}
			finally{			
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return result;
		}
		}
	//���ݷ���Ա��id,��ѯ�÷���Ա���µĶ������ڵ����Ӻ�
	public static String[] queryDeskNameByUserId(){	
	Connection con=getConnection();
	Statement st=null;
	ResultSet rs=null;
	String sql=null;
	List<String> pointId=new ArrayList<String>();
	String pointName[];
	try{	
		st=con.createStatement();
		sql="SELECT point_id FROM orderInfo " +
				"WHERE o_SAFlg='0'";
		rs=st.executeQuery(sql);
		while(rs.next()){		
			pointId.add(rs.getString(1));
		}	
		pointName=new String[pointId.size()];
		for(int i=0; i<pointId.size(); i++){		
			pointName[i]=searchPointNameById(pointId.get(i).trim());
		}
		for(String strr: pointName){		
			System.out.println(strr+" ");
		}
	}
	catch(Exception e){	
		e.printStackTrace();
		return null;
	}
	finally{	
		try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		try {st.close();} catch (SQLException e) {e.printStackTrace();}
		try {con.close();} catch (SQLException e) {e.printStackTrace();}
	}
		return pointName;
	}
	////////////-------------orderManage-end
	//���������õ�����������
	public static List<String> getCGByMCG(String string){	
		Connection con = getConnection();
 		Statement st = null;
 		ResultSet rs = null;
 		List<String> list=new ArrayList<String>();
 		try{		
 			st = con.createStatement();
 			String task ="select cate_name from  category,maincategory" +
 					" where maincategory.mcate_name='"+string+"' and " +
 							"category.mcate_id=maincategory.mcate_id and category.cate_delflag='"+0+"'";
 			System.out.println(task);
 			rs = st.executeQuery(task);
            while(rs.next()){           
            	list.add(rs.getString(1));
            }			
 		}catch(Exception e){		
 			e.printStackTrace();
 			return null;
 		}finally{ 		
 			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
 			try {st.close();} catch (SQLException e) {e.printStackTrace();}
 			try {con.close();} catch (SQLException e) {e.printStackTrace();}
 		}
 		return list;
	}
	//���ݲ�Ʒid�õ���Ʒ�������Ϣ
	public static String[] searchVegeByid(String string){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String[] vegemsg = new String[5];
		try{		
			st = con.createStatement();
			String sql="select vege_id,vege_name,vege_vegeprice,vege_intro,unit_name from vege,countunit where  vege_useflg='"+1+"' and vege.unit_id =  countunit.unit_id and " +
			"vege_id='"+string+"'";
			rs = st.executeQuery(sql);
			if(rs.next()){			
				for(int i=0;i<vegemsg.length;i++){				
					vegemsg[i]=rs.getString(i+1);
				}
			}
		}
		catch(Exception e){		
			e.printStackTrace();
			return null;
		}
		finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return vegemsg;
	}
	//��ȡ�������Ϣ
	public static List<String[]> getMCGinfo(String string){ 	
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		List<String[]>ll=new ArrayList<String[]>();
		try{		  
			st=con.createStatement();
			String sql="select distinct maincategory.mcate_id,maincategory.mcate_name " +
					"from vege,category,MainCategory where vege_useflg='"+1+"' and " +
			"vege.cate_id=category.cate_id and category.mcate_id=maincategory.mcate_id ";
			rs=st.executeQuery(sql);
			while(rs.next()){			
				String[] s=new String[2];
				for(int i=0;i<s.length;i++){				
					s[i]=rs.getString(i+1);
				}
				ll.add(s);
			}	
		}catch(Exception e){		
			e.printStackTrace();
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return ll;
	}
	//�������Ʋ�ѯ��̨״̬
	public static String getTableStateByname(String tname){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs=null;
		String sql=null;
		String state=null;
		try{		 
			st=con.createStatement();
			sql="select point_state from point where point_name='"+tname+"'";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()){
			 state=rs.getString(1);
			}else{			
				return null;
			}
		}catch(Exception e){		
			e.printStackTrace() ;
			return null;
		}finally{		
			try{st.close();}catch(Exception e) {e.printStackTrace();}
			try{con.close();}catch(Exception e) {e.printStackTrace();}
		}
		return state;
	}
	//�õ���ǰ��̨������
	public static String getCurDeskGuestNum(String tablename){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs=null;
		String sql=null;
		String gnum=null;
		String tableid=searchPointIdByName(tablename);
		try{		 
			System.out.println(tableid);
			st=con.createStatement();
			sql="select o_guestnum from orderinfo where point_id='"+tableid+"'";
			System.out.println(sql);
			rs=st.executeQuery(sql);
			if(rs.next()){			
				gnum=rs.getInt(1)+"";
			}else{
				return null;
			}
			System.out.println(gnum);
		}catch(Exception e){		
			e.printStackTrace() ;
			return null;
		}finally{		
			try{rs.close();}catch(Exception e) {e.printStackTrace();}
			try{st.close();}catch(Exception e) {e.printStackTrace();}
			try{con.close();}catch(Exception e) {e.printStackTrace();}
		}
		return gnum;
	}
	//�޸Ķ�������
	private static Object CHANGE_GUESTNUM_LOCK=new Object();
	public static String changeGuestnum(String tablename, String num){	
		synchronized(CHANGE_GUESTNUM_LOCK){		
			Connection con = getConnection();
			Statement st = null;
			String sql=null;
			String tableid=searchPointIdByName(tablename);
			int gnum=Integer.parseInt(num);
			try{			 
				st=con.createStatement();
				sql="update orderinfo set o_guestnum="+gnum+" where point_id='"+tableid+"' and o_SAFlg='0'";
				System.out.println(sql);
				st.executeUpdate(sql);				
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}		
//===========================��̨��Ϣ�޸Ľ���==
	//�õ���Ʒ������ͼƬ
	public static List<String[]> getALLVegeImage(){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String task = "select vimage_id,vimage_path from vegeimage where del_flg='"+0+"'";
			rs = st.executeQuery(task);
			while(rs.next()){			
				String[] str = new String[2];
			   for(int i=0;i<str.length;i++){			   
				   str[i]=rs.getString(i+1);
			   }
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//�õ���ƷͼƬ�������
	public static String getVegeImageMaxNo(){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		try{		
			st = con.createStatement();
			String task = "select max(vimage_id) from vegeimage";
			rs = st.executeQuery(task);
		    rs.next();
		    if(rs.getString(1)==null){		    
		    	str=String.valueOf(0); 
		    }
		    else{		    
		    	str=rs.getString(1);
		    }
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;		
	}
	//����ƷͼƬ��ӽ���
	private static Object ADDVEGEIMAGE=new Object();
	public static String addVegeImage(String string, String getinfo, String getinfo2, String getinfo3){ 	
		synchronized(ADDVEGEIMAGE){		
			Connection con = getConnection();
			Statement st = null;
			try{			
				st = con.createStatement();
				String sql="insert into vegeimage(vimage_id,vege_id,vimage_path,vimage_ismain) values('"+string+"','"+getinfo+"','"+getinfo2+"','"+getinfo3+"')";
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//���ݲ�Ʒid�õ�ͼƬ
	public static String vegetoimage(String string){	
		Connection con = getConnection();
	    Statement st = null;
	    ResultSet rs = null;
		StringBuilder msg = new StringBuilder();
		try{		  
			st=con.createStatement();
			String sql="select vimage_path from vegeimage where vegeimage.vege_id='"+string+"'and del_flg='"+0+"';";
			rs=st.executeQuery(sql);
			while(rs.next()){			
				msg.append(rs.getString(1));
				System.out.println("msg="+msg);
				msg.append(",");
			}
		}catch(Exception e){		
			e.printStackTrace();
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		System.out.println(msg);
		return msg.substring(0,msg.length());
	}
	//�жϵ�ǰ��Ʒ�Ƿ������ͼ
	public static String pdVegeImage(String string){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		try{		
			st = con.createStatement();
			String task = "select vimage_id from vegeimage where vege_id='"+string+"' " +
					"and vimage_ismain='"+1+"'and del_flg='"+0+"'";
			rs = st.executeQuery(task);
		    if(rs.next()){		    
		    	str=String.valueOf(1); 
		    }
		    else{		    
		    	str=String.valueOf(0);
		    }
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;		
	}
	//�õ�ָ����Ʒ����ͼƬ
	public static String getVegeMainImage(String string){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		try{		
		    st = con.createStatement();
		    String task = "select vimage_path from vegeimage where " +
		    		"vege_id='"+string+"' and vimage_ismain='"+1+"'and del_flg='"+0+"'";
			rs = st.executeQuery(task);
		    if(rs.next()){		    
		    	str=rs.getString(1);
		    }
		    else{		    
		    	str="";
		    }
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//�޸Ĳ�Ʒ��ͼ
	private static Object UPDATEVEGEMAINIMAGE=new Object();
	public static String updateVegeMainImage(String[] getstr){ 	
		synchronized(UPDATEVEGEMAINIMAGE){		
			Connection con = getConnection();
			Statement st = null;
			String sql=null;
			try{			 
				st=con.createStatement();
				sql="update vegeimage set del_flg='1' where vege_id='"+getstr[2]+"'and vimage_ismain='1'";
				System.out.println(sql);
				st.executeUpdate(sql);
				sql="insert into vegeimage(vimage_id,vege_id,vimage_path,vimage_ismain) " +
						"values('"+getstr[1]+"','"+getstr[2]+"','"+getstr[3]+"','"+getstr[4]+"')";
				st.executeUpdate(sql);
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}		
	}
	//ͨ����ƷID��ò�ƷͼƷ��Ϣ�������ò�Ʒ����ͼƬ·�����ط���Ա�ֳֶ�
	public static String getPicByVegeid(String vege_id){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		try{		
			st = con.createStatement();
			String sql="select vimage_path from vegeimage where vege_id='"+vege_id+
						"' and vimage_ismain='1' and del_flg='0';";
			rs=st.executeQuery(sql);
			rs.next();
			str=rs.getString(1);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str;
	}
	//ɾ����ͼ�Ĵ�ͼ
	public static String delZDImage(String string, String string2){ 	
		Connection con = getConnection();
		Statement st = null;
		try{		
			st = con.createStatement();
			String sql="update vegeimage set del_flg='1' where vege_id='"+string+"'and vimage_path= '"+string2+"'";
			st.executeUpdate(sql);
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return "ok";
	}
	//�����������Ƶõ����в�
	public static  List<String[]> searchVegeByCate(String cag){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list=new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String sql="select vege.vege_id,vege.vege_name,vege.vege_vegeprice,countunit.unit_name from vege,countunit where vege.unit_id=countunit.unit_id and vege.cate_id in(select cate_id from category where cate_name='"+cag+"')";
			rs = st.executeQuery(sql);
			while(rs.next()){				
				String str[]=new String[4];
				str[0]=rs.getString(1);
				str[1]=rs.getString(2);	
				str[2]=rs.getFloat(3)+"";
				str[3]=rs.getString(4);
				System.out.println(str[1]+str[3]);
				list.add(str);
			}
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//���ݲ�Ʒid�õ�����˵�������ͼ
	public static String searchzpathByvgid(String getstr){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		StringBuilder str = new StringBuilder();
		try{		
		    st = con.createStatement();
		    String task = "select vimage_path from vegeimage where vege_id='"+getstr+"' " +
		    		"and del_flg='"+0+"' and (vimage_ismain='"+0+"' or vimage_ismain='"+2+"') ";
			rs = st.executeQuery(task);
		   while(rs.next()){		   
			   str.append(rs.getString(1));
			   str.append(",");
		   }		   
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return str.substring(0,str.length());
	}
	//����Ա�ֳֶ˵õ���Ʒ��Ϣ
	public static List<String[]> getWVege(String cate_name){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		try{		
			st = con.createStatement();
			String sql="select vege_id" +
					",vege_name,vege_vegeprice," +
					"vege_intro,unit_name,cate_name,mcate_name,vt_name,vs_name " +
					"from vege,countunit,category,vegetype,MainCategory,VegeStandard " +
					"where  vege_useflg='"+1+"' and vege.unit_id =  countunit.unit_id and " +
					"vege.cate_id = category.cate_id  and category.mcate_id = maincategory.mcate_id and vege.vt_id = vegetype.vt_id  " +
					" and vege.vs_id = VegeStandard.vs_id  and category.cate_name='"+cate_name+"';";
			rs = st.executeQuery(sql);
			System.out.println(sql);
		
			while(rs.next()){			
				String[] str = new String[27];
				for(int i=0;i<9;i++){				
					str[i]=rs.getString(i+1);
				}
				//���ݲ�Ʒid�õ���ͼ·��
				String mainpath=DBUtil.getVegeMainImage(str[0]);
				//���ݲ�Ʒid�õ���ͼ·��
				String imagemsg=DBUtil.searchzpathByvgid(str[0]);
				str[22]=mainpath;
				str[23]=imagemsg;
				list.add(str);
			}	
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}	
	//�õ���Ʒ��Ϣ
	public static String[] searchWVegeByid(String vege_id){	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String[] list=new String[22];
		try{		
			st = con.createStatement();
			String sql="select vege_id,vege_name,vege_vegeprice," +
			"unit_name,cate_name,mcate_name,vt_name,vs_name,vege_intro" +
			"from vege,countunit,category,vegetype,WorkerInfo,MainCategory,VegeStandard" +
			" where  vege_useflg='"+1+"' and vege.unit_id =  countunit.unit_id and " +
			"vege.cate_id = category.cate_id  and category.mcate_id = maincategory.mcate_id  " +
			" and vege.vs_id = VegeStandard.vs_id and vege.vege_id='"+vege_id+"';";
			rs = st.executeQuery(sql);
			while(rs.next()){			
			for(int i=0;i<list.length;i++){			
				list[i]=rs.getString(i+1);
			}
			}			
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//���ݲ�Ʒ��id�õ���Ʒ���ܵ������Ϣ
	public static String[] getVegeIntroByid(String string){ 	
		Connection con = getConnection();
		Statement st = null;
		ResultSet rs = null;
		String[] vegemsg=new String[5] ;
		try{		
			st = con.createStatement();
			String sql="select vege_id,vege_name,vege_vegeprice,vege_intro," +
					"unit_name from vege,countunit where  vege_useflg='"+1+
					"' and vege.unit_id =  countunit.unit_id and vege.vege_id='"+string+"'";
			rs = st.executeQuery(sql);
			rs.next();
			for(int i=0;i<vegemsg.length;i++){			
				vegemsg[i]=rs.getString(i+1);
				System.out.println("["+i+"]==="+vegemsg[i]);
			}
		}
		catch(Exception e){		
			e.printStackTrace();
			return null;
		}
		finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return vegemsg;
	}
	//�������ȷ�ϵ�
	private static Object ADDNEWDETIALORDER=new Object();
	public static String addNewDetialOrder(List<String[]> orderList){	
		synchronized(ADDNEWDETIALORDER){		
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs=null;
			int order_code;
			@SuppressWarnings("unused")
			String addVegeFlg="0";
			try{			
				st = con.createStatement();
				String sql="select max(odc_code) from orderdishconfirm where odc_orderid='"+orderList.get(0)[2]+"';";
				rs=st.executeQuery(sql);
				rs.next();
				String s=rs.getString(1);
				if(s!=null){//���иö�������Ϊ�Ӳ˲�������ȡ������ֵ				
					order_code=Integer.parseInt(s)+1;
					addVegeFlg="1";
				}
				else{//���޸ö����ţ���Ϊ����¶�������				
					order_code=0;
				}
				for(int i=0;i<orderList.size();i++){//�����ϸ����				
					String str[]=orderList.get(i);
					String odc_serverTime=TypeExchangeUtil.getCurTime();
					sql="insert into orderdishconfirm(odc_vege_id,odc_user_id," +
							"odc_orderid,odc_vegequa,odc_vegepri,odc_serveTime," +
							"odc_code" +") values('"+str[0]+"','"+str[1]+"','"+
							str[2]+"',"+Float.parseFloat(str[3])+",'"+str[4]+"','"+odc_serverTime+
							"','"+(order_code+Integer.parseInt(str[7]))+
							"')";
					st.executeUpdate(sql);
					System.out.println("add detailorder: sql"+sql);
				}
			}
			catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}
			finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}
	}
	//���ݲ�ƷID���ӿ���п۳��ò�Ʒ���õ��Ĳ��ϵ�����������治�㣬�򷵻ز�ƷID
    //ͨ����̨Id��ò�ѯ�ò�̨δ�����˵��ţ���Ϊ�գ����ȡ��ǰ��󶩵��Ų����ݲ�̨Id��Ա��Id���ܼ۸񡢹˿����������¶���
	private static Object ADDNEWORDER=new Object();
	public static String addNewOrder(List<String[]> orderList,
			String pointName,String o_workerid, String o_totalPri,String o_guestnum){	
		synchronized(ADDNEWORDER){		
			 Connection con = getConnection();
			 Statement st = null;
			 ResultSet rs = null;
			 String str = "";
			 String orderId=null;
			 try{			 
				 st = con.createStatement(); 
					//ͨ����̨Id��ѯ�ò�̨�Ƿ����µ���δ���ˣ�����ò�̨�Ķ�����
					String pointId;
					String sql="select point_id from point where point_name='"+pointName+"';";				
					rs=st.executeQuery(sql);
					rs.next();
					pointId=rs.getString(1);
					sql="select o_id from orderinfo where point_id='"+pointId+"' and o_SAFlg='0';";
					rs=st.executeQuery(sql);
					if(!rs.next()){//�ò�̨δ�¶���					
						String time=(TypeExchangeUtil.getCurTime()).split(" ")[0].replace("-", "");
						System.out.println(time+"/////////////////////////////////////////");
						//��ѯ��󶩵���
						sql="select max(o_id) from orderinfo where o_id like '"+"YY00010001-"+time+"-____"+"'";
						rs=st.executeQuery(sql);
						rs.next();
						String maxNo=rs.getString(1);
						System.out.println("maxNo============"+maxNo);
						if(maxNo==null){						
							orderId="YY00010001-"+time+"-0001";
							String o_ODTime=TypeExchangeUtil.getCurTime();
							//����¶���
							sql="insert into orderinfo(o_id,point_id,o_workerid" +
									",o_totalPri,o_ODTime,o_guestnum) values('"+orderId+"','"+pointId+
									"','"+o_workerid+"','"+o_totalPri+"','"+o_ODTime+"','"+o_guestnum+"')";
							st.executeUpdate(sql);
						}
						else{//�����µ��Ķ����Ķ�����						
						 /*******************************************************************************/
							String startId=maxNo.substring(0, 10);
							String endId=maxNo.substring(20, 24);
							if(Integer.parseInt(endId)<9){							
								System.out.println("orderId============"+endId);
								orderId="000"+(Integer.parseInt(endId)+1);
								System.out.println("orderId============"+endId);
							}
							else
								if(Integer.parseInt(endId)<99){								
									orderId="00"+(Integer.parseInt(endId)+1);
								}
								else
									if(Integer.parseInt(endId)<999){									
										orderId="0"+(Integer.parseInt(endId)+1);
									}
									else{									
										orderId=""+(Integer.parseInt(endId)+1);
									}
							orderId=startId+"-"+time+"-"+orderId;
							System.out.println("orderId=========================="+orderId);
						 /*******************************************************************************/
							String o_ODTime=TypeExchangeUtil.getCurTime();
							//����¶���
							sql="insert into orderinfo(o_id,point_id,o_workerid" +
									",o_totalPri,o_ODTime,o_guestnum) values('"+orderId+"','"+pointId+
									"','"+o_workerid+"','"+o_totalPri+"','"+o_ODTime+"','"+o_guestnum+"')";
							st.executeUpdate(sql);
						}  
					}
					else{//�ò�̨���µ�������ΪΪ�ö����Ӳ�					
						orderId=rs.getString(1);
						sql="update orderinfo set o_totalPri=o_totalPri+'"+o_totalPri+
								"' where o_id='"+orderId+"';";
						st.executeUpdate(sql);
					}
					 str="sucess"+orderId;
				 }			 
			 catch(Exception e){			 
				 e.printStackTrace();
				 return null;
			 }
			 finally{			 
					try {rs.close();} catch (SQLException e) {e.printStackTrace();}
					try {st.close();} catch (SQLException e) {e.printStackTrace();}
					try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return str;
		}
	}
//���ݲ�̨���Ʋ�ѯ��̨id
	public static String searchPointIdByName(String name){ 	
		Connection con=getConnection();
		Statement st=null;
		ResultSet rs=null;
	    String pointid=null;
		try{
			st=con.createStatement();
			String sql="select point_id from point where point_name='"+name+"' and point_state='1'";
			rs=st.executeQuery(sql);
		    if(rs.next()){		    
			  pointid=rs.getString(1);
		    }
		    else{		    
		    	return null;
		    }
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally{
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return pointid;
	}
//�ֳֶ˸��²�̨��Ϣ
	private static Object CLIENTPOINT_LOCK=new Object();
	public static String updateClientPoint(String pname,String state){	
		synchronized(CLIENTPOINT_LOCK){		
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs=null;
			String sql=null;
			try{			 
				st=con.createStatement();
				sql="select point_state from Point where point_name='"+pname+"'and point_delflg='0'";
				rs=st.executeQuery(sql);
				rs.next();
				if(rs.getString(1).equals("0")){				
			      sql="update  point set point_state='"+state+"' where point_name='"+pname+"'";
				  System.out.println(sql);
				  st.executeUpdate(sql);
				}
				else{				
					return null;
				}
			}catch(Exception e){			
				e.printStackTrace() ;
				return null;
			}finally{			
				try{rs.close();}catch(Exception e) {e.printStackTrace();}
				try{st.close();}catch(Exception e) {e.printStackTrace();}
				try{con.close();}catch(Exception e) {e.printStackTrace();}
			}
			return "ok";
		}		
	}
	public static String getPointGuestNumByName(String name,String guestnum){	
		Connection con=getConnection();
		Statement st=null;
		ResultSet rs=null;
		try{		
		  String str[]=new String[2];
		  st=con.createStatement();
		  String sql="select point_num,point_state from point where point_name='"+name+"' and point_delflg='0'";
		  rs=st.executeQuery(sql);
		  System.out.println(sql);
		  if(rs.next()){		  
			  str[0]=rs.getInt(1)+"";
			  str[1]=rs.getString(2);
		  }
		  if(str[1].equals("1")||Integer.parseInt(str[0])<Integer.parseInt(guestnum)){		  
			  return null;
		  }
		}catch(Exception e){		
			e.printStackTrace();
			return null;
		}finally{		
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			try {st.close();} catch (SQLException e) {e.printStackTrace();}
			try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return "ok";
	}
	//ȡ����̨
	private static Object CANCLEPOINT_LOCK=new Object();
	public static String canclePointUpdate(String pname,String state){		
			synchronized(CANCLEPOINT_LOCK){			
				Connection con = getConnection();
				Statement st = null;
				String sql=null;
				ResultSet rs=null;
				try{				
					String pstate=null;
					st=con.createStatement();
					sql="select point_state from point where point_name='"+pname+"'";
					rs=st.executeQuery(sql);
					if(rs.next()){					
						pstate=rs.getString(1);
					}
					else{					
						return null;
					}
					if(pstate.equals(state)){					
						return null;
					}
					else{					
			          sql="update  point set point_state='"+state+"' where point_name='"+pname+"'";
				      System.out.println(sql);
				      st.executeUpdate(sql);
				      return "ok";
					}
				}catch(Exception e){				
					e.printStackTrace() ;
					return null;
				}finally{				
					try{rs.close();}catch(Exception e) {e.printStackTrace();}
					try{st.close();}catch(Exception e) {e.printStackTrace();}
					try{con.close();}catch(Exception e) {e.printStackTrace();}
				}
			}
			}
//��ȡ����Ա�ֳֶ����˵Ĳ�̨��Ϣ
	public static List<String[]> getHasPersonPoint(String room_id,String ptype_id){ 		
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			List<String[]> list = new ArrayList<String[]>();
			String sql=null;
			try{			
				st = con.createStatement();
				 sql = "select point_id,point_no,point_name,point_state,point_num,room_name,ptype_name" +
							" from point,room,pointtype " +"where point_delflg='0' and " +
							"room.room_id=point.room_id and point.ptype_id=pointtype.ptype_id and " +
							"point.room_id='"+room_id+"' and point.ptype_id='"+ptype_id+"' and point.point_state!='0' " ;									
				rs = st.executeQuery(sql);
				while(rs.next()){				
					String[] str = new String[7];
					
					for(int i=0; i<str.length; i++){					
						str[i] = rs.getString(i+1);
					}
					System.out.println("has order"+str[2]);
					list.add(str);
				}
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
		}	
	//��ʱ�䰴һ����ʽת��ΪString
	public static long getMillisDate( final String dataString){	  
		long time=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        try {  
            Date date = df.parse(dataString); 
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(date);
            time=calendar.getTimeInMillis();
            System.out.println("millis="+time);
        } catch (Exception ex) {  
            System.out.println(ex.getMessage());  
        }  
       return time;
	} 
	//���ݲ�Ʒid�õ�����˵ı�־Ϊ0����ͼ
		public static String searchzimagepath(String getstr){		
			Connection con = getConnection();
			Statement st = null;
			ResultSet rs = null;
			StringBuilder str=new StringBuilder() ;
			try{			
			    st = con.createStatement();
			    String task = "select vimage_path from vegeimage where " +
			    		"vege_id='"+getstr+"' and vimage_ismain='"+0+"'and del_flg='"+0+"'";
				rs = st.executeQuery(task);
				while(rs.next()){				   
					   str.append(rs.getString(1));
					   str.append(",");
				   }				
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}finally{			
				try {rs.close();} catch (SQLException e) {e.printStackTrace();}
				try {st.close();} catch (SQLException e) {e.printStackTrace();}
				try {con.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(str.length()>0){			
				System.out.println("str.length>>>>>>>>>>>>>>>>>0");
				return str.substring(0,str.length());
			}else{
				System.out.println("str.length===================0");
				return str.toString();
			}			
		}
		//���ݲ�Ʒid�õ�����˱�־λ2 ����ͼ�Ĵ�ͼ
				public static String searchmbimagepath(String getstr){				
					Connection con = getConnection();
					Statement st = null;
					ResultSet rs = null;
					String str ;
					try{					
					    st = con.createStatement();
					    String task = "select vimage_path from vegeimage " +
					    		"where vege_id='"+getstr+"' and vimage_ismain='"+2+"'and del_flg='"+0+"'";
						rs = st.executeQuery(task);
						rs.next();
						str=rs.getString(1);					   
					}catch(Exception e){					
						e.printStackTrace();
						return null;
					}finally{					
						try {rs.close();} catch (SQLException e) {e.printStackTrace();}
						try {st.close();} catch (SQLException e) {e.printStackTrace();}
						try {con.close();} catch (SQLException e) {e.printStackTrace();}
					}
					return str;
				}
				//�޸Ĳ�Ʒ��ͼ�Ĵ�ͼ
				private static Object UPDATEVEGEMAINBIGIMAGE=new Object();
				public static String updateVegeMainBigImage(String[] getstr){ 				
					synchronized(UPDATEVEGEMAINBIGIMAGE){					
						Connection con = getConnection();
						Statement st = null;
						String sql=null;
						//���ݲ�Ʒid���ҵõ���ǰ��Ʒ����ͼ�Ĵ�ͼ�Ƿ��ǲ�����ͼƬ������ͼ
						String path=searchmbimagepath(getstr[2]);
						System.out.println("path========="+path);
						try{						
							st=con.createStatement();
							if(path.equals(Constant.IMAGE_PATH+Constant.IMAGE_NULLBIGNAME)){							
								System.out.println("////////////////////////");
								sql="update vegeimage set del_flg='1' where vege_id='"+getstr[2]+"' and vimage_ismain='"+2+"'";
								st.executeUpdate(sql);
							}
							else{							
								sql="update vegeimage set vimage_ismain='0' where vege_id='"+getstr[2]+"' and vimage_ismain='"+2+"'";
								st.executeUpdate(sql);
							}
							sql="update vegeimage set vimage_ismain='2' where vege_id='"+getstr[2]+"' and vimage_path='"+getstr[3]+"'";
							st.executeUpdate(sql);
						}catch(Exception e){						
							e.printStackTrace() ;
							return null;
						}finally{						
							try{st.close();}catch(Exception e) {e.printStackTrace();}
							try{con.close();}catch(Exception e) {e.printStackTrace();}
						}
						return "ok";
					}					
				}
				//�õ�pc�����õĲ�Ʒ��Ϣ
				public static List<String[]> getUseVege() {
					Connection con = getConnection();
					Statement st = null;
					ResultSet rs = null;
					List<String[]> list = new ArrayList<String[]>();
					try{					
						st = con.createStatement();
						String sql="select vege_id,vege_name,vege_vegeprice," +
								"unit_name,cate_name,mcate_name,vt_name,vs_name,vege_intro " +
								"from vege,countunit,category,vegetype,MainCategory,VegeStandard where  vege_useflg='"+1+"' and vege.unit_id =  countunit.unit_id and " +
								"vege.cate_id = category.cate_id  and category.mcate_id = maincategory.mcate_id and vege.vt_id = vegetype.vt_id  " +
								"and vege.vs_id = VegeStandard.vs_id ";
								rs = st.executeQuery(sql);
						while(rs.next()){						
							String[] str = new String[9];
							for(int i=0;i<str.length;i++){
								str[i]=rs.getString(i+1);
							}
							list.add(str);
						}
						System.out.println("--------list.size="+list.size());
					}catch(Exception e){					
						e.printStackTrace();
						return null;
					}finally{					
						try {rs.close();} catch (SQLException e) {e.printStackTrace();}
						try {st.close();} catch (SQLException e) {e.printStackTrace();}
						try {con.close();} catch (SQLException e) {e.printStackTrace();}
					}
					return list;
				}
		public static void main(String args[]){		              //���Է���
			String str[]={"","001","YY00010001-20130116-0003","003","1"};
			 subVegeCount(str);
		}
}