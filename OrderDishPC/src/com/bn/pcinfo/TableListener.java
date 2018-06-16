package com.bn.pcinfo;

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
/*
 * ��JTable ��Ӽ���ʵ��ѡ��һ�еõ���һ�е�ֵ��Ϊ����������¼���е�ֵ��֮����Խ�����Ӧ��ɾ���͸��²���
 * */
public class TableListener implements ListSelectionListener,TableModelListener
{
	private JTable jtable;
	public static String values[]=null;//����ֵΪ��
	public static int row=0;//������Ϊ0
	public static  Vector<String> title;//�����������
	static String primarykey;//��¼ѡ���е�����
	static String primaryname;//��������
	static String[] vegemsg;//ֻ����ڲ�Ʒ��Ϣ
	public TableListener(JTable jtable)//��õ�ǰҪ�༭�ı�
	{
		this.jtable=jtable;
	}
	@Override
	public void valueChanged(ListSelectionEvent e)//�ı����ֵ�����õķ���
	{   //������¼��Ƕ�������¼�֮һ
		if(e.getValueIsAdjusting()==true){
	     row= jtable.getSelectedRow();//�õ���ǰѡ����
	     //���ѡ�е���Ϣ�в�Ϊ-1
	     if(row!=-1)
	     {
	       primarykey=(String) jtable.getValueAt(row, 0);//�õ���һ�е�ֵ
	       vegemsg=new String[jtable.getColumnCount()];//�����СΪ�����ֵ������
	      for(int i=0;i<vegemsg.length;i++)//��ȡ���е�ֵ����ӵ�������
	        {
				vegemsg[i]=(String) jtable.getValueAt(row, i);
			}
	        values=new String[jtable.getColumnCount()];//��¼ѡ���е�����ֵ
			for(int i=0;i<values.length;i++)
			{
				values[i]=(String) jtable.getValueAt(row, i);
			}
	     }
		}
	}
	@Override
	public void tableChanged(TableModelEvent e) 
	{
		values=new String[jtable.getColumnCount()];//��������
		row= jtable.getSelectedRow();//��¼��ǰѡ���е�����
		if(row!=-1)
		{
			for(int i=0;i<values.length;i++)//��¼ѡ����ֵ
			{
				values[i]=(String) jtable.getValueAt(row, i);
			}
		}
	}
//	public String getUserId()
//	{
//		row=jtable.getSelectedRow();//��¼��ǰѡ���е�����
//		if(row==-1)
//		{
//			return null;
//		}
//		String user_id=(String)jtable.getValueAt(row, 0);//�õ���һ�е�ֵ
//		return user_id;
//	}
}