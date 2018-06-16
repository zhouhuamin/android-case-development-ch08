package com.bn.util;

import java.util.HashMap;
import java.util.Map;

import com.bn.constant.Constant;

public class VegeChildCateUtil
{
	static Map<String,String[]> vCCData=new HashMap<String,String[]>();
	public static String[] getChildCateInfo(String mainCateName) throws Exception
	{
		String [] childCateInfo;
		if(vCCData.containsKey(mainCateName))
		{//�鿴�ڴ����Ƿ�����Ը���������Ϊ����ֵ
			childCateInfo=vCCData.get(mainCateName);
			return childCateInfo;
		}
		//�鿴SD��
		childCateInfo=DBUtil.getChildCateByName(mainCateName);
		if(childCateInfo.length!=0)
		{//SD���д���
			vCCData.put(mainCateName, childCateInfo);
			return childCateInfo;
		}
		//SD���в����ڣ����ӷ�������ȡ����
		DataGetUtilSimple.ConnectSevert(Constant.GET_CGBYMCG+mainCateName);
		String str=DataGetUtilSimple.readinfo;
		childCateInfo=TypeExchangeUtil.stringtoStringArray(str);
		//���뱾��SD����
		DBUtil.insertChildCate(mainCateName, childCateInfo);
		//�����ڴ���
		vCCData.put(mainCateName, childCateInfo);
		return childCateInfo;
	}
}
