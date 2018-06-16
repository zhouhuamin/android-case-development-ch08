package com.bn.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bn.constant.Constant;

/*
 * VegeManagerUtil���ڲ�Ʒ���ݵĻ��棬����Ҫĳ����Ʒ��Ϣʱ������
 * ���ڴ��в��ң������ֱ��ʹ�ã�û����ӷ��������
 */
public class VegeManagerUtil 
{
    //���Ʒ��id��ͼ������
    static Map<String,List<String[]>> vgData=new HashMap<String,List<String[]>>();	    
    public static List<String[]> getVege(String vegecate) throws Exception
    {
    	List<String[]> vegeinfo=new ArrayList<String[]>(); 
    	//�ڴ���û��
    	if(vgData.containsKey(vegecate))
    	{
    		vegeinfo=vgData.get(vegecate);
    		return vegeinfo;
    	}
    	//�鿴sd��
    	vegeinfo=DBUtil.getVegeintroByCate(vegecate);
    	
    	if(vegeinfo.size()!=0)
    	{
    		vgData.put(vegecate, vegeinfo);
    		return vegeinfo;
    	}
    	//����������
		DataGetUtilSimple.ConnectSevert(Constant.GET_W_VEGEINFO+vegecate);
		String backinfo=DataGetUtilSimple.readinfo;
		vegeinfo=TypeExchangeUtil.strToList(backinfo);
		List<String[]> vegelist=new ArrayList<String[]>();
		//��ͼƬ�ŵ�pic��
		for(int i=0;i<vegeinfo.size();i++)
		{
			String[] vegemsg=PicUtil.saveImagemsg(vegeinfo.get(i));
			vegelist.add(vegemsg);
		}
		//����Ϣ�������
		DBUtil.insertAll("vege",vegelist);
		//����Ҫ����Ϣ�����ڴ�
		List<String[]> catevege=new ArrayList<String[]>();
		catevege=DBUtil.getVegeintroByCate(vegecate);
		vgData.put(vegecate,catevege);  	   
		return catevege;
    }
}
