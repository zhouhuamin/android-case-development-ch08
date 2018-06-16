package com.bn.worker;

import static com.bn.constant.Constant.*;

import java.util.List;
import java.util.Vector;

import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;
//Ȩ�޲�ѯ
public class RoleAuthTransform
{
	static String roleArr[][];
	static String authorityArr[][];
	static List<String[]> list;
	
	public static Vector<Vector<String>> transform(String roleAuthInfo)
	{
		list = TypeExchangeUtil.strToList(roleAuthInfo);
		//��ɫ
		SocketClient.ConnectSevert(GET_ROLEINFO);
		String roleInfo=SocketClient.readinfo;
		roleArr = TypeExchangeUtil.getString(roleInfo);
		//Ȩ��
		SocketClient.ConnectSevert(GET_AUTHORITYINFO);
		String authInfo=SocketClient.readinfo;
		authorityArr = TypeExchangeUtil.getString
		(authInfo);
		for(String[] str: list)
		{
			str[0] = getRoleIdToName(str[0]);
			str[1] = getAuthorityIdToName(str[1]);
		}
		return TypeExchangeUtil.listToVector(list);
	}
	//����ɫid ת��Ϊ��ɫ����
	public static String getRoleIdToName(String role_id)
	{
		if(role_id.length()!=0)
		{
			for(String arr[] : roleArr)
			{
				if(role_id.equals(arr[0]))
				{
					return arr[1];
				}
			}
		}
		return "";
	}
	//��Ȩ��idת��ΪȨ������
	public static String getAuthorityIdToName(String auth_id)
	{
		if(auth_id.length()!=0)
		{
			for(String arr[] : authorityArr)
			{
				if(auth_id.equals(arr[0]))
				{
					return arr[1];
				}
			}
		}
		return "";
	}
}
