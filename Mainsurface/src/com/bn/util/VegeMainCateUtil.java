package com.bn.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bn.constant.Constant;

public class VegeMainCateUtil 
{
	static Map<String,List<String[]>> vMCData=new HashMap<String,List<String[]>>();
	public static List<String[]> getMainCateInfo(String roomId) throws Exception
	{
		List<String[]> mainCateInfo=new ArrayList<String[]>();
		if(vMCData.containsKey(roomId))
		{//�鿴�ڴ����Ƿ�����Ը÷���idΪ����ֵ
			mainCateInfo=vMCData.get(roomId);
			return mainCateInfo;
		}
		//�鿴SD��
		mainCateInfo=DBUtil.getVegeMainCateByRoomId(roomId);
		if(mainCateInfo.size()!=0)
		{//SD���д���
			vMCData.put(roomId, mainCateInfo);
			return mainCateInfo;
		}
		//SD���в����ڣ����ӷ�������ȡ����
		DataGetUtilSimple.ConnectSevert(Constant.GET_W_MCGINFO+Constant.roomId);
		mainCateInfo=TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);
		//���뱾��SD����
		DBUtil.insertMainCate(roomId, mainCateInfo);
		//�����ڴ�
		vMCData.put(roomId, mainCateInfo);
		return mainCateInfo;
	}
}
