package com.bn.worker;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.RowFilter;
import javax.swing.table.*;
//����ѡ���������ѯ��Ӧ��Ա����Ϣ
public class QueryWorkerInfo 
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public QueryWorkerInfo(TableRowSorter sorter,Map map)
	{   //��ѯԱ����Ϣ�����롱�Ĺ�ϵ
		ArrayList andList = new ArrayList();
		RowFilter rfAnd = null;		
		if(map.get("tfWId")!=null)
		{
			RowFilter rfId = RowFilter.regexFilter(((String)map.get("tfWId")).trim(),0);
			andList.add(rfId);
		}	
		if(map.get("tfWName")!=null)
		{
			RowFilter rfName = RowFilter.regexFilter(((String)map.get("tfWName")).trim(),1);
			andList.add(rfName);
		}
		if(map.get("JRole")!=null)
		{
			RowFilter rfRole = RowFilter.regexFilter(((String)map.get("JRole")).trim(),4);
			andList.add(rfRole);
		}
		if(!andList.isEmpty())
		{
			rfAnd = RowFilter.andFilter(andList); 
		}
		sorter.setRowFilter(rfAnd);
	}
}