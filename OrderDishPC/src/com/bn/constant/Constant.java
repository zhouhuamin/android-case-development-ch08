package com.bn.constant;


import javax.swing.tree.DefaultMutableTreeNode;

public class Constant {
	public static final String SOCKET_ERROR="0";
	public static final String SOCKET_IOERROR="1";
	public static String operator;
	//�ڲ�Ʒû��ͼƬʱ�ϴ���ͼƬ
	public static final String IMAGENULL_PATH="C:\\Users\\lenovo\\Desktop\\�ϴ���ͼƬ\\null.jpg";
	//�õ��������ϵ�ǰ��Ʒû��ͼƬ��ͼƬ·��
	public static String vegenullimage;
	public static String vegenullBigimage;
	public final static String IP="localhost";//��ȡ��ǰip
	public final static int POINT=9999;//����piontΪ9999
	//�� �Ľڵ㾲ֵ̬
	public static DefaultMutableTreeNode temproot=new DefaultMutableTreeNode("���ϵͳ");
	//�ӽڵ�
	public static DefaultMutableTreeNode[] dmtn=
	{
		new DefaultMutableTreeNode("���׹���"),new DefaultMutableTreeNode("��λ����"),
		new DefaultMutableTreeNode("Ա������"),new DefaultMutableTreeNode("��������"),
		new DefaultMutableTreeNode("�������")
	};
	//��Ʒ�����ӽڵ�
	public static DefaultMutableTreeNode[] vegenode=
	{
			new DefaultMutableTreeNode("�������"),new DefaultMutableTreeNode("������"),
			new DefaultMutableTreeNode("��ϵ����"),new DefaultMutableTreeNode("������λ����"),
			new DefaultMutableTreeNode("������"),new DefaultMutableTreeNode("��Ʒ����")
	};
   //Ա�������ӽڵ�
	public static DefaultMutableTreeNode[] authority=
	{
		new DefaultMutableTreeNode("Ա������")
	};
	//Ա����Ϣ
	public static String workerPrivateInfo[] = 
	{
		"Ա��ID"
		,"Ա������"
		,"Ա������"
		,"�Ա�"
		,"��ɫ"
	};
	//������Ϣ�ӽڵ�
	public static DefaultMutableTreeNode[] desknode=
	{
		new DefaultMutableTreeNode("����"),new DefaultMutableTreeNode("����")
	};
	
	//�����ڵ�
	public static DefaultMutableTreeNode[] roompointnode=
	{
		new DefaultMutableTreeNode("������Ϣ")
    };
	//���������ӽڵ�
	public static DefaultMutableTreeNode[] ordernode=
	{
		new DefaultMutableTreeNode("������Ϣ")
    };
	//���˽ڵ�
	public static DefaultMutableTreeNode[] accountnode=
	{
		new DefaultMutableTreeNode("����")
    };
	
 //=========================���ӷ��������������Ϣ���ֶ�ͷ=============================
  //��Ʒ����
  public static final String GET_MCG="GET_MCG";
  public static final String GET_MCGMAXNO="GET_MCGMAXNO";
  public static final String UPDATE_CMG="UPDATE_CMG";
  public static final String ADD_CMG="ADD_CMG";
  public static final String DEL_CMG="DEL_CMG";
  ///��Ʒ���
  public static final String GET_CG="GET_CG";
  public static final String GET_CGMAXNO="GET_CGMAXNO";
  public static final String ADD_CG="ADD_CG";
  public static final String UPDATE_CG="UPDATE_CG";
  public static final String DEL_CG="DEL_CG";
  //��ϵ
  public static final String GET_VT="GET_VT";
  public static final String GET_VTMAXNO="GET_VTMAXNO";
  public static final String UPDATE_VT="UPDATE_VT";
  public static final String DEL_VT="DEL_VT";
  //������λ
  public static final String GET_CU="GET_CU";
  public static final String GET_CUMAXNO="GET_CUMAXNO";
  public static final String UPDATE_CU="UPDATE_CU";
  public static final String DEL_CU="DEL_CU";
  //���
  public static final String GET_VS="GET_VS";
  public static final String GET_VSMAXNO="GET_VSMAXNO";
  public static final String UPDATE_VS="UPDATE_VS";
  public static final String DEL_VS="DEL_VS";
  //��ƷͼƬ
  public static final String DELETE_ZBIMAGE="DELETE_ZBIMAGE";
  public static final String ISHAVENULLPIC="ISHAVENULLPIC";
  public static final String GET_VEGEIMAGE="GET_VEGEIMAGE";
  public static final String GET_VEGEIMAGEMAXNO="GET_VEGEIMAGEMAXNO";
  public static final String INSERT_IMAGE="INSERT_IMAGE";
  public static final String PDVEGEMAIN="PDVEGEMAIN";
  public static final String GET_VEGEMAINIMAGE="GET_VEGEMAINIMAGE";
  public static final String UPDATE_MAINIMAGE="UPDATE_MAINIMAGE";
  public static final String UPLOAD_IMAGE="UPLOAD_IMAGE";
  public static final String GET_IMAGE="GET_IMAGE";
  public static final String UPLOAD_MIMAGE="UPLOAD_MIMAGE";
  public static final String UPLOAD_ZIMAGE="UPLOAD_ZIMAGE";
  public static final String GET_W_PICBYVEGEID="GET_W_PICBYVEGEID";//���ݲ�Ʒid�õ���ͼ·��
  public static final String UPLOAD_MAIN_VEGEIAMGE="UPLOAD_MAIN_VEGEIAMGE";
  public static final String GET_MBIMAGE="GET_MBIMAGE";//���ݲ�Ʒid�õ���ͼ��ͼ·��
  public static final String GET_ZBIMAGE="GET_ZBIMAGE";//���ݲ�Ʒid�õ���ͼ·��
  public static final String UPDATE_MAINBIGIMAGE="UPDATE_MAINBIGIMAGE";
  
  //��Ʒ
  public static final String GET_VEGE="GET_VEGE";
  public static final String GET_VEGEMAXNO="GET_VEGEMAXNO";
  public static final String GETMCGNAMEBYCGNAME="GETMCGNAMEBYCGNAME";
  public static final String ADD_VEGE="ADD_VEGE";
  public static final String DEL_VEGE="DEL_VEGE";
  public static final String GET_YYCF="GET_YYCF";
  public static final String GET_CLCF="GET_CLCF";
  public static final String UPDATE_VEGE="UPDATE_VEGE";
  public static final String UPDATESSJG="UPDATESSJG";
  public static final String UPDATECXJG="UPDATECXJG";
  public static final String SEARCH_VEGE="SEARCH_VEGE";
  public static final String GS_VEGE="GS_VEGE";
  public static final String GET_USEVEGE="GET_USEVEGE";
  //Ա��
  public static final String GET_WOKERCG="GET_WOKERCG"; 
  public static final String GET_WOKERWAITER="GET_WOKERWAITER";
  
  public static final String GET_WORKERINFO="GET_WORKERINFO";
  public static final String GET_ROLEINFO="GET_ROLEINFO";
  public static final String GET_AUTHORITYINFO="GET_AUTHORITYINFO";
  public static final String GET_AUTHORITY_ROLE="GET_AUTHORITY_ROLE";
  public static final String GET_DEPTIDNAME = "GET_DEPTIDNAME";
  public static final String GET_GROUPIDNAME ="GET_GROUPIDNAME";
  public static final String GET_WORKERIMAGE ="GET_WORKERIMAGE";
  
  public static final String ADD_WORKERINFO = "ADD_WORKERINFO";
  public static final String UPLOAD_WORKERIMAGE = "UPLOAD_WORKERIMAGE";
  public static final String DEL_WORKERINFO="DEL_WORKERINFO";
  public static final String DEL_ROLE="DEL_ROLE";
  public static final String DEL_AUTHORITY="DEL_AUTHORITY";
  public static final String DEL_AUTHROLE="DEL_AUTHROLE";

  public static final String UPDATE_WORKERINFO="UPDATE_WORKERINFO";
  public static final String UPDATE_ROLE="UPDATE_ROLE";
  public static final String UPDATE_AUTHORITY="UPDATE_AUTHORITY";
  public static final String UPDATE_AUTHROLE="UPDATE_AUTHROLE";

  public static final String GET_WORKERMAXNO="GET_WORKERMAXNO";
  public static final String GET_ROLEMAXNO="GET_ROLEMAXNO";
  public static final String GET_AUTHORITYMAXNO="GET_AUTHORITYMAXNO";
  public static final String GET_AUTHROLEMAXNO="GET_AUTHROLEMAXNO";
  public static final String GET_WORKERNAMEBYID="GET_WORKERNAMEBYID";
  public static final String QUERY_W_ONEOFWORKERINFOBYID="QUERY_W_ONEOFWORKERINFOBYID";//����Ա��id,��ѯĳһ��Ա������Ϣ
	
  //��̨
  public static final String GET_ROOM="GET_ROOM";//��ȡ������Ϣ
  public static final String GET_RMNAME="GET_RMNAME";//�õ���̨����
  public static final String GET_POINT="GET_POINT";//�õ���̨��Ϣ
  public static final String GET_RTNAME="GET_RTNAME";//�õ���̨���� 
  public static final String GET_MAXPOINTNO="GET_MAXPOINTNO";//�õ���Ӧ�����еĲ�̨���ֵ
  public static final String GET_MAXROOMNO="GET_MAXROOMNO";//�õ��������ֵ
  //���²���
  public static final String ADD_POINT="ADD_POINT";
  public static final String ADD_ROOM="ADD_ROOM";
  public static final String UPDATE_ROOM="UPDATE_ROOM";
  public static final String UPDATE_POINT="UPDATE_POINT";
  public static final String DEL_POINT="DEL_POINT";
  public static final String DEL_ROOM="DEL_ROOM";
 
  //����
  public static final String GET_ORDER="GET_ORDER";//�õ�δ���˵Ķ�����Ϣ
  public static final String SEARCH_ACCORDER="SEARCH_ACCORDER";//
  public static final String SEARCH_ORDER="SEARCH_ORDER";//����������������Ʋ�ѯ����
  public static final String SEARCH_ODXX="SEARCH_ODXX";//��ѯ����Ķ�����Ϣ
  //���㷽ʽ
  public static final String GET_SA_MODE="GET_SA_MODE";//�õ����еĽ��㷽ʽ
  public static final String DEL_SA_MODE="DEL_SA_MODE";
  public static final String UPDATE_SA_MODE="UPDATE_SA_MODE";
  public static final String GET_SA_MAXNO="GET_SA_MAXNO";

  public static final String SEARCH_ACCOUNT_VEGE="SEARCH_ACCOUNT_VEGE";//��ѯҪ���˵Ĳ�̨�ϵĲ�Ʒ
  public static final String INSERT_ACCOUNT_VEGE="INSERT_ACCOUNT_VEGE";
   
//����
  public static final String GET_NOBOOKDESKNAME="GET_NOBOOKDESKNAME";//����Ԥ��ʱ��鿴��ʱ���û�б�Ԥ������������
  public static final String INSERT_BOOKORDER="INSERT_BOOKORDER"; //�ύԤ������
  public static final String GET_ALLTCNAME="GET_ALLTCNAME";
  public static final String GET_TCINFO_BYNAME="GET_TCINFO_BYNAME";//�����ײ�ɫ���Ʋ鿴���ײ͵��������۸���Ϣ
  public static final String GET_BOOKORDER="GET_BOOKORDER";//�õ�����Ԥ����Ϣ
  public static final String GET_BORDER_BYTIME="GET_BORDER_BYTIME";//����ʱ���ѯ����
  public static final String DEL_BXX_BYID="DEL_BXX_BYID";//���ݸ�����Ԥ�������Ų�ѯ��ϸ��������  
  public static final String SURE_WAITER="SURE_WAITER";//����
  public static final String GET_LOGINAUTH="GET_LOGINAUTH";//Ȩ��
  
  //��½�ɹ���������Ƿ���Ԥ����Ԥ�����Ӷ���æ
  public static final String UPDATE_TODAY_POINTSTATE="UPDATE_TODAY_POINTSTATE";
}
