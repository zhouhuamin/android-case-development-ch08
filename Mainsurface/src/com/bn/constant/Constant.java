package com.bn.constant;

import java.util.ArrayList;     
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant 
{
public static final String RESETPASSWORD="8888";
public static  String IP;
public static  int POINT;
//Ĭ������µ�ip��point
public static final String ip="192.168.191.2";
public static final int point=9999;
//�ݶ����Ӻ�
public static String roomId="001";
public static String deskId;//��¼��ǰ���Ӻ�
public static List<String[]> vegeMsgList;//��¼���δ�ύ֮ǰ�Ĳ�Ʒ��Ϣ
public static String deskName;//��¼��ǰ����������
public static String defaultDeskName;
public static String userId;//��¼��ǰ��ʹ����
public static int guestNum;//��¼��ǰ���ӿ�������
public static final String IMAGEPATH="/mnt/sdcard/pic";//ͼƬ�洢·��
public static Map<String,Integer> allguestNum=new HashMap<String,Integer>();//����������п�̨��û�е�˵Ĳ�̨����
//��ǰ��������Ĳ�Ʒ����Ϣ
public static List<String[]> dcvegemsg=new ArrayList<String[]>();
public static List<String[]> curdcvegemsg=new ArrayList<String[]>();
public static Map<String,List<String[]>> allpointvegeinfo= new HashMap<String,List<String[]>>();
public static int ddd=0;
public static String FAIL="fail";
public static String OK="ok";
//������ʶ��ѯ�����̨������
public static final String ALLPERSON="all";
public static final String NOPERSON="noperson";
public static final String HASPERSON="hasperson";
public static final String MAINHASPERSON="MAINHASPERSON";
public static final String COMBINEPOINT="COMBINEPOINT";
//=========================��ƷͼƬ�ı���Ϣ�Լ�������Ϣ================
public static final String GET_MCG="GET_MCG";//����������Ϣ����Ӧ�������ƺ�id��
public static final String GET_W_MCGINFO="GET_W_MCGINFO";//���ָ�������Ĳ�Ʒ���
public static final String GET_W_PICTUREINFO="GET_W_PICTUREINFO";//ͨ����Ʒid���ͼƬbyte[]
public static final String GET_CGBYMCG="GET_CGBYMCG";//ͨ��������������Ϣ
public static final String GET_W_VEGEINFO="GET_W_VEGEINFO"; //���ݲ�Ʒ������ѯ��Ʒ��Ϣ
public static final String SEARCH_VGBYID="SEARCH_VGBYID";//���ݲ�ƷID��ѯ��Ʒ��Ϣ��ʾ
public static final String SEARCH_VGBYKEY="SEARCH_VGBYKEY";//���ݲ�Ʒ�ؼ��ֲ�ѯ��Ʒ��Ϣ��ʾ
public static final String SEARCH_VGBYNAME="SEARCH_VGBYNAME";//���ݲ�Ʒ���Ʋ�ѯ��Ʒ��Ϣ��ʾ
public static final String UPDATE_W_VEGEMATERIAL="UPDATE_W_VEGEMATERIAL";//�µ�����²�Ʒ����
public static final String GET_W_ORDERMAXNO="GET_W_ORDERMAXNO";//�õ��˵������ֵ
public static final String ADD_W_NEWORDER="ADD_W_NEWORDER";//�����Ϣ����
public static final String ADD_W_NEWDETIALORDER="ADD_W_NEWDETIALORDER";//��Ӷ�Ӧ����ϸ������Ʒ��Ϣ
public static final String SEARCH_W_VGBYID="SEARCH_W_VGBYID";//���ݲ�Ʒid�õ���Ʒ����Ϣ
public static final String SEARCH_ZPATHBYVGID="SEARCH_ZPATHBYVGID";	//���ݲ�Ʒid�ĵ���ͼ��·��
public static final String DOWNLOAD_VEGE="DOWNLOAD_VEGE";//���ز�Ʒ��Ϣ
public static final String GET_IMAGE="GET_IMAGE";//���ݲ�Ʒ·���õ���ƷͼƬ��Ϣ
//========================������̨��Ϣ============================
public static final String SURE_WAITER="SURE_WAITER";//�����û����������ж��Ƿ�Ϸ�
public static final String GET_LOGINAUTH="GET_LOGINAUTH";//��ȡԱ��Ȩ��
public static final String RESET_PASSWORD="RESET_PASSWORD";
public static final String GET_W_ROOM_POINTTYPE="GET_W_ROOM_POINTTYPE";//��ȡ��̨�Ĳ����Ͳ�������
public static final String GET_W_POINTINFO="GET_W_POINTINFO";//��ȡ��Ӧ�Ĳ�̨��Ϣ
public static final String GET_W_GUESTNUMBYNAME="GET_W_GUESTNUMBYNAME";
public static final String UPDATECLIENT_POINT="UPDATECLIENT_POINT";//�޸���ѡ��̨��״̬
public static final String SEAR_CURPOINT_ORDER="SEAR_CURPOINT_ORDER";//ȡ��������ѯ�Ƿ��Ѿ��µ�
public static final String GET_CURDESK_GUESTNUM="GET_CURDESK_GUESTNUM";//�õ���ǰ��̨������
public static final String GET_W_NOPERSONPOINTINFO="GET_W_NOPERSONPOINTINFO";//�õ��������˵Ĳ�̨
public static final String GET_W_HASPERSONPOINTINFO="GET_W_HASPERSONPOINTINFO";//�õ��������˵Ĳ�̨
public static final String CUR_POINT_ISMERGER="CUR_POINT_ISMERGER";
//================================��������===========================
public static final String GET_W_ORDERDISHCONFIRM="GET_W_ORDERDISHCONFIRM"; //�������ţ���ȡ���ȷ�ϵ��е�����(orderdishconfirm��)
public static final String GET_W_VEGEIDTONAME="GET_W_VEGEIDTONAME"; //���ݲ�Ʒid��ò�Ʒ����S
public static final String DELETE_W_ORDERCONFIRMVEGE="DELETE_W_ORDERCONFIRMVEGE";//ɾ�����ȷ�ϵ��еĲ�Ʒ
public static final String DELETE_W_VEGESINGLECOUNT="DELETE_W_VEGESINGLECOUNT";//��ĳ����Ʒ��������1
public static final String ADD_W_VEGESINGLECOUNT="ADD_W_VEGESINGLECOUNT";//��ĳ����Ʒ��������1
//=================����������Ϣ=========================
public static final String TESTCONNECT="TESTCONNECT";//��������
public static final String GET_W_VEGEINTROINFO="GET_W_VEGEINTROINFO";//���ݲ�Ʒ��id�õ���Ʒ���ܵ������Ϣ
//==============================================
public static final String SEARCH_USERCID="SEARCH_USERCID";
public static final String SEARCH_USERBYNAME="SEARCH_USERBYNAME";
//���Ա���Ƿ��Ѿ���½
public static final String IS_HASlOGIN="IS_HASlOGIN";
public static final String UPDATE_LOGINFLG="UPDATE_LOGINFLG";
//�쳣���ͱ�ʶ
public static final int UnknownHost=0;
public static final int SocketTimeout=1;
public static final int IOExcep=2;
public static final int StringIndexOutOfBounds=3;
public static final int NullPointer=4;
public static final int SocketExcep=5;
public static final int Excep=5;
public static final int EOFExcep=6;
public static final int ToastMessage=7;

public static Map<String,Map<String,boolean[]>> vegeMap=new HashMap<String,Map<String,boolean[]>>();

}