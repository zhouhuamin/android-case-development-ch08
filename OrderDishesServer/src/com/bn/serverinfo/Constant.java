package com.bn.serverinfo;//���������

public class Constant {//��������Constant
	  public static final String IMAGE_PATH="pic/";//����ͼƬ·�����ַ���
	  public static final String IMAGE_NULLNAME="1.jpg";//������ͼʱ��ʾ��ͼƬ�����ַ���
	  public static final String IMAGE_NULLBIGNAME="0.jpg";//������ͼʱ��ʾ�Ĵ�ͼƬ�����ַ���
	  
	  public static boolean flag=true;//����boolean���͵ı�־λ
	  public static final int width=400;//������
	  public static final int height=300;//����߶�
	  public static final int xwidth=260;//���嵥λ���
	  public static final String TESTCONNECT="TESTCONNECT";//������������ַ���	
	  //�����
	  public static final String GET_MCG="GET_MCG";//�õ�����
	  public static final String GET_MCGMAXNO="GET_MCGMAXNO";//�õ����������
	  public static final String UPDATE_CMG="UPDATE_CMG";//��������
	  public static final String ADD_CMG="ADD_CMG";//�������
	  public static final String DEL_CMG="DEL_CMG";//ɾ������
	  //��Ʒ���
	  public static final String GET_CG="GET_CG";//�õ����
	  public static final String GET_CGMAXNO="GET_CGMAXNO";//�õ���������
	  public static final String ADD_CG="ADD_CG";//������
	  public static final String DEL_CG="DEL_CG";//ɾ�����
	  public static final String UPDATE_CG="UPDATE_CG";//�������
	//��ϵ
	  public static final String GET_VT="GET_VT";//�õ���ϵ
	  public static final String GET_VTMAXNO="GET_VTMAXNO";//�õ���ϵ�����
	  public static final String UPDATE_VT="UPDATE_VT";//���²�ϵ
	  public static final String DEL_VT="DEL_VT";//ɾ����ϵ
	  	  
	//������λ
	  public static final String GET_CU="GET_CU";//�õ�������λ
	  public static final String GET_CUMAXNO="GET_CUMAXNO";//�õ�������λ�����
	  public static final String UPDATE_CU="UPDATE_CU";//���¼�����λ
	  public static final String DEL_CU="DEL_CU";//ɾ��������λ
	  
	  //���
	  public static final String GET_VS="GET_VS";//�õ����
	  public static final String GET_VSMAXNO="GET_VSMAXNO";//�õ���������
	  public static final String UPDATE_VS="UPDATE_VS";//���¹��
	  public static final String DEL_VS="DEL_VS";//ɾ�����
	  
	  //��ƷͼƬ
	  public static final String DELETE_ZBIMAGE="DELETE_ZBIMAGE";//ɾ����ͼ
	  public static final String ISHAVENULLPIC="ISHAVENULLPIC";//�Ƿ���ͼ
	  public static final String GET_VEGEIMAGE="GET_VEGEIMAGE";//�õ���ƷͼƬ
	  public static final String GET_VEGEIMAGEMAXNO="GET_VEGEIMAGEMAXNO";//�õ���ƷͼƬ�����
	  public static final String INSERT_IMAGE="INSERT_IMAGE";//����ͼƬ
	  public static final String PDVEGEMAIN="PDVEGEMAIN";//�ж��Ƿ������ͼ
	  public static final String GET_VEGEMAINIMAGE="GET_VEGEMAINIMAGE";//�õ���Ʒ��ͼ
	  public static final String UPDATE_MAINIMAGE="UPDATE_MAINIMAGE";//�޸���ͼ
	  public static final String UPLOAD_MIMAGE="UPLOAD_MIMAGE";//������ͼ
	  public static final String UPLOAD_ZIMAGE="UPLOAD_ZIMAGE";//������ͼ
	  //��Ʒ
	  public static final String GET_VEGE="GET_VEGE";//�õ���Ʒ��Ϣ
	  public static final String GET_VEGEMAXNO="GET_VEGEMAXNO";//�õ���Ʒ�����
	  public static final String GETMCGNAMEBYCGNAME="GETMCGNAMEBYCGNAME";//���������õ������
	  public static final String ADD_VEGE="ADD_VEGE";//��Ӳ�Ʒ
	  public static final String DEL_VEGE="DEL_VEGE";//ɾ����Ʒ
	  public static final String UPDATE_VEGE="UPDATE_VEGE";//���²�Ʒ��Ϣ
	  public static final String SEARCH_VEGE="SEARCH_VEGE";//��ѯ��Ʒ
	  public static final String GS_VEGE="GS_VEGE";//���ݲ�ƷID�õ���Ʒ��Ϣ
	  public static final String GET_USEVEGE="GET_USEVEGE";//���pc�����õĲ�Ʒ��Ϣ
	  	//Ա��
	  public static final String GET_WOKERCG="GET_WOKERCG";   //�õ�Ա����Ϣ���ֳֶˣ�
	  public static final String GET_WOKERWAITER="GET_WOKERWAITER";//�õ�����Ա��Ϣ���ֳֶˣ�
	 
	  public static final String GET_WORKERINFO="GET_WORKERINFO";//�õ�Ա����Ϣ��PC�ˣ�
	  public static final String GET_ROLEINFO="GET_ROLEINFO";//�õ���ɫ��Ϣ
	  public static final String GET_AUTHORITYINFO="GET_AUTHORITYINFO";//�õ�Ȩ��
	  public static final String GET_AUTHORITY_ROLE="GET_AUTHORITY_ROLE";//�õ�Ȩ��--��ɫ��Ӧ��Ϣ
	  public static final String ADD_WORKERINFO = "ADD_WORKERINFO";//���Ա��
	  public static final String DEL_WORKERINFO="DEL_WORKERINFO";//ɾ��Ա����Ϣ
	  public static final String UPDATE_WORKERINFO="UPDATE_WORKERINFO";//����Ա����Ϣ
	  public static final String GET_WORKERMAXNO="GET_WORKERMAXNO";//�õ�Ա�������
	  public static final String GET_WORKERNAMEBYID="GET_WORKERNAMEBYID";//����ID��ѯԱ������
	  public static final String QUERY_W_ONEOFWORKERINFOBYID="QUERY_W_ONEOFWORKERINFOBYID";//����Ա��id,��ѯĳһ��Ա������Ϣ
	
	  //ͼƬ
	  public static final String UPLOAD_IMAGE="UPLOAD_IMAGE";//�ϴ�ͼƬ
	  public static final String GET_IMAGE="GET_IMAGE";//�õ�ͼƬ
	  public static final String UPLOAD_MAIN_VEGEIAMGE="UPLOAD_MAIN_VEGEIAMGE";//�ϴ���Ʒ��ͼ
	  public static final String GET_MBIMAGE="GET_MBIMAGE";//pc�˸��ݲ�Ʒid�õ���Ʒ��ͼ�Ĵ�ͼ
	  public static final String GET_ZBIMAGE="GET_ZBIMAGE";//pc�˸��ݲ�Ʒid�õ���Ʒ��ͼ
	  public static final String UPDATE_MAINBIGIMAGE="UPDATE_MAINBIGIMAGE";//�޸Ĳ�Ʒ��ͼ�Ĵ�ͼ

	//����
		 public static final String GET_ROOM="GET_ROOM";//�õ�������Ϣ
		 public static final String GET_RMNAME="GET_RMNAME";//�õ���������
		 public static final String GET_POINT="GET_POINT";//�õ����в�̨
		 public static final String GET_RTNAME="GET_RTNAME";//�õ���̨����
		 public static final String GET_ORDER="GET_ORDER";//�õ�δ���˵����ж�����Ϣ
		 public static final String GET_MAXPOINTNO="GET_MAXPOINTNO";//�õ���̨�����
		 public static final String GET_MAXROOMNO="GET_MAXROOMNO";//�õ����������
		 public static final String ADD_POINT="ADD_POINT";//��Ӳ�̨
		 public static final String ADD_ROOM="ADD_ROOM";//��Ӳ���
		
		 public static final String UPDATE_ROOM="UPDATE_ROOM";//���²���
		 public static final String UPDATE_POINT="UPDATE_POINT";//���²�̨ 
		 public static final String DEL_POINT="DEL_POINT";//ɾ����̨
		 public static final String DEL_ROOM="DEL_ROOM";//ɾ������
	   	public static final String UPDATE_REASON="UPDATE_REASON";
		//����
		 public static final String ADD_ORDER="ADD_ORDER";//��Ӷ���
		 public static final String SEARCH_ACCORDER="SEARCH_ACCORDER";//���ݲ�̨��ѯ����
		 public static final String SEARCH_ORDER="SEARCH_ORDER";//�õ�������Ϣ
		 public static final String SEARCH_ODXX="SEARCH_ODXX";//�õ�������ϸ��Ϣ
		
	 public static final String GET_VGSOMEINFO="GET_VGSOMEINFO";//�õ���Ʒ������Ϣ

		 //����
		 public static final String GET_ALACCORDER="GET_ALACCORDER";//�õ����н�����Ϣ
		 public static final String SEARCH_ACCOUNT_VEGE="SEARCH_ACCOUNT_VEGE";//��ѯҪ���˵Ĳ�̨�ϵĲ�Ʒ
		 public static final String INSERT_ACCOUNT_VEGE="INSERT_ACCOUNT_VEGE";//������˵����б���
		 
		 //����ֳֶ�
		 public static final String WOKERMAKENAME="��ʦ";
	     public static final String WOKERCGNAME="�ֿ����Ա";		
		 public static final String GET_LOGINAUTH="GET_LOGINAUTH";//��ѯ��½Ȩ��
		 
        //����Ա�ֳֶ�
		 public static final String GET_W_ROOM_POINTTYPE="GET_W_ROOM_POINTTYPE";       //�õ������Ͳ�̨������Ϣ
		 public static final String GET_W_POINTINFO="GET_W_POINTINFO"; //��ȡ��̨��Ϣ   
		 public static final String UPDATE_W_ORDERINFO="UPDATE_W_ORDERINFO";//�����������ֳֶ����¶���
		 public static final String GET_WAITERPASSWORD = "GET_WAITERPASSWORD";//��ѯԱ������
		 public static final String GET_ROOMIDNAME="GET_ROOMIDNAME";//��ѯ��������
		 public static final String GET_W_MCGINFO="GET_W_MCGINFO";//���ָ�������Ĳ�Ʒ���
		 public static final String GET_W_PICTUREINFO="GET_W_PICTUREINFO";//����·���õ�ͼƬ
		 public static final String SURE_WAITER="SURE_WAITER";//Ա����½Ȩ����֤
		 public static final String IS_HASlOGIN="IS_HASlOGIN";//�Ƿ��Ѿ���½
		 public static final String UPDATE_LOGINFLG="UPDATE_LOGINFLG";//���Ա���Ƿ��ѵ�½
		 public static final String RESET_PASSWORD="RESET_PASSWORD";//����Ա������
		 
		 public static final String GET_W_ORDERDISHCONFIRM="GET_W_ORDERDISHCONFIRM";  //�õ����ȷ�ϵ���Ϣ
		 public static final String GET_W_VEGEIDTONAME="GET_W_VEGEIDTONAME";  //��һ���Ʒid,ת����һ���Ʒ����
		 public static final String ADD_W_NEWORDER="ADD_W_NEWORDER";//���ݲ�ƷID���ӿ���п۳��ò�Ʒ���õ��Ĳ��ϵ�����
		 public static final String ADD_W_NEWDETIALORDER="ADD_W_NEWDETIALORDER";//�������ȷ�ϵ�
		 public static final String QUERY_W_POINTIDFROMORDER="QUERY_W_POINTIDFROMORDER";//��ѯ�Ƿ������Ӧ���ŵĶ���
		 public static final String DELETE_W_ORDERCONFIRMVEGE="DELETE_W_ORDERCONFIRMVEGE";//ɾ�����ȷ�ϵ��еĲ�Ʒ
		 public static final String QUERY_W_REFRUSHVEGEINFO="QUERY_W_REFRUSHVEGEINFO";//ˢ�²�Ʒ��Ϣ
		 public static final String DELETE_W_VEGESINGLECOUNT="DELETE_W_VEGESINGLECOUNT";//��ĳ����Ʒ��������1
		 public static final String ADD_W_VEGESINGLECOUNT="ADD_W_VEGESINGLECOUNT";//��ĳ����Ʒ��������1
		 public static final String QUERY_W_DESKNAMEOFORDER="QUERY_W_DESKNAMEOFORDER";//��ѯ���ж�����Ӧ����������
		 public static final String UPDATECLIENT_POINT="UPDATECLIENT_POINT";//�޸Ŀ�̨״̬
		 public static final String GET_W_GUESTNUMBYNAME="GET_W_GUESTNUMBYNAME";//���²�̨��Ϣ
		 public static final String SEAR_SELECTTABLE_STATEMENT="SEAR_SELECTTABLE_STATEMENT";//���ݲ�̨���Ʋ�ѯ״̬		 
		 //�õ��޸�ǰ������
		 public static final String GET_CURDESK_GUESTNUM="GET_CURDESK_GUESTNUM";
		 //�޸Ĳ�̨����
		 public static final String CHANGE_GUESTNUM="CHANGE_GUESTNUM";
		 public static final String GET_W_NOPERSONPOINTINFO="GET_W_NOPERSONPOINTINFO";//�õ��������˵Ĳ�̨
		 public static final String GET_W_HASPERSONPOINTINFO="GET_W_HASPERSONPOINTINFO";//�õ��������˵Ĳ�̨
		 public static final String GET_W_MAINHASPERSON="GET_W_MAINHASPERSON";//����ȡ����ֻ̨��ʾ����
		 
		 public static final String GET_CGBYMCG="GET_CGBYMCG";//ͨ���������Ʋ�ѯ�����
		 
		 public static final String UPDATE_W_ORDERDISHCONFIRM = "UPDATE_W_ORDERDISHCONFIRM";//���µ��ȷ�ϵ�

		 public static final String GET_W_PICBYVEGEID="GET_W_PICBYVEGEID";//ͨ����ƷID������ͼƬ·��
		 public static final String SEARCH_VGBYCATE="SEARCH_VGBYCATE";//��������ȡ��Ʒ		
		 public static final String SEARCH_ZPATHBYVGID="SEARCH_ZPATHBYVGID"; //���ݲ�Ʒid�õ�����˵�������ͼ
		 public static final String GET_W_VEGEINFO="GET_W_VEGEINFO";      //����Ա�ֳֶ˻�ò�Ʒ��Ϣ
		 public static final String SEARCH_W_VGBYID="SEARCH_W_VGBYID";//���ݲ�Ʒid�õ���Ʒ��Ϣ
		 public static final String DOWNLOAD_VEGE="DOWNLOAD_VEGE";//����Ա�˼��ز�Ʒ��Ϣ
		 
		 //========================================PC��-ǰ̨����
		 public static final String GOBACKORDER_GET_OC="GOBACKORDER_GET_OC"; //�����������ƣ���ѯ���ȷ�ϵ�(����ϸ����)�е�����
		 
}
