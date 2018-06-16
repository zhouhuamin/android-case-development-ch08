package com.bn.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bn.constant.Constant;
/*
 * VegeIntroMsgUtil���ڲ�Ʒid����Ʒ���ƣ��۸񣬽��ܣ���λ��Ӫ�������ϣ���·����Ϣ�Ļ��棬
 * ����Ҫĳ����Ʒ��Ϣʱ���������ڴ��в��ң������ֱ��ʹ�ã�û����ӷ��������
 */
public class VegeIntroMsgUtil
{
	//���Ʒ��id�Ͳ�Ʒ����Ϣ
    static Map<String,String[]> vgData=new HashMap<String,String[]>();
    public static String[] getVege(String vegeid) throws Exception
    {
    	String[] vegeinfo=new String[8]; 
    	if(vgData.containsKey(vegeid))
    	{
    		vegeinfo=vgData.get(vegeid);
    		return vegeinfo;
    	}   	
    	//�ڶ�����sqlite�в���
    	vegeinfo=DBUtil.getVegeintroByid(vegeid);
    	if(vegeinfo[0]!=null)
    	{
			vgData.put(vegeid,vegeinfo); 
    		return vegeinfo;
    	}
		DataGetUtilSimple.ConnectSevert(Constant.SEARCH_W_VGBYID+vegeid);
		String backinfo=DataGetUtilSimple.readinfo;
		String[] vegeintromsg=TypeExchangeUtil.stringtoStringArray(backinfo);
		vegeintromsg=PicUtil.saveImagemsg(vegeintromsg);
		//�����
	    List<String[]> vegemsg=TypeExchangeUtil.stringArrayToList(vegeintromsg);
		DBUtil.insertAll("vege", vegemsg);
		//����sqlite
		vegeinfo[0]=vegeintromsg[0];
		vegeinfo[1]=vegeintromsg[1];
		vegeinfo[2]=vegeintromsg[2];
		vegeinfo[3]=vegeintromsg[8];
		vegeinfo[4]=vegeintromsg[3];
		vegeinfo[7]=vegeintromsg[23];
		vgData.put(vegeid,vegeinfo);  	
		return vegeinfo;
    }
}
