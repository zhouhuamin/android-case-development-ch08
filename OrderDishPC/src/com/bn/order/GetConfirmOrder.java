package com.bn.order;

import java.awt.Font;
import java.awt.Image;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.bn.util.TypeExchangeUtil;
/*
 * ���ݲ����Ų�ѯ����Ķ�����Ϣ
 * */
@SuppressWarnings("serial")
public class GetConfirmOrder extends JFrame 
{
	String order;
	String xxorder;
	JLabel jtitle=new JLabel("��������");//���塰�������顱JLabel
	JLabel jl[]=
	{
			new JLabel("����̨"),new JLabel("����"),new JLabel("����"),
			new JLabel("����Աid"),new JLabel("�ܼ۸�")
	};//����JLabel����
	JTextField jtf[]=
	{
			new JTextField(""),new JTextField(""),new JTextField(""),
			new JTextField(""),new JTextField("")
	};//����JTextField����
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Vector<String> title=new Vector();//��������
	{
	    title.add("��Ʒ����");
	    title.add("��̨����");
	    title.add("��Ʒ����");
		title.add("��Ʒ�۸�");
		title.add("��ע");
	}
	public GetConfirmOrder(String order,String xxorder)//����ȷ�Ϸ���
	{
		Image image=this.getToolkit().getImage("src/com/bn/image/tb1.jpg");//����ͼƬ
		this.setIconImage(image);//����ͼ��
	    this.order=order;
	    this.xxorder=xxorder;
	    if(order.length()==0)//���������ϢΪ�գ�����ʾ"�˲������޶���"
	    {
	    	JOptionPane.showMessageDialog(GetConfirmOrder.this, "�˲������޶���","��ʾ",JOptionPane.INFORMATION_MESSAGE);		
		}
	    else
	    {//��ʼ������
			Vector<Vector<String>> orderinfo=TypeExchangeUtil.strToVector(xxorder);
			DefaultTableModel dtmtable=new DefaultTableModel(orderinfo,title);//����ģ��	
			JTable table=new JTable(dtmtable);//����
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//���ñ��Զ������ߴ��
			table.setRowHeight(25);//�����и�25
			String str[][]=TypeExchangeUtil.getString(order);//��������Ϣ����������
			JScrollPane jspt=new JScrollPane(table);//������еķָ���
			jspt.setBounds(20,100,800,380);//����ָ�����λ�����С
			jtitle.setBounds(400, 5,200,50);//��������λ�����С
			jtitle.setFont(new Font("����",Font.BOLD,24));//���ñ��������
			this.setLayout(null);//���ý��沼����ΪĬ��
			System.out.println("sssss"+str[0].length);
			for(int i=0;i<jl.length;i++)//����JLabel�����塢JTextField��ֵ������ӵ������У�����JTextFieldΪ���ɱ༭
			{
				jl[i].setFont(new Font("����",Font.BOLD,12));
				jtf[i].setText(str[0][i]);
				this.add(jl[i]);
				this.add(jtf[i]);
				jtf[i].setEditable(false);
			}
			//����JLabel��JTextField��λ�����С
			jl[0].setBounds(20,60,60,30);jtf[0].setBounds(60,60,60,30);
			jl[1].setBounds(130,60,30,30);jtf[1].setBounds(160,60,80,30);
			jl[2].setBounds(240,60,30,30);jtf[2].setBounds(270,60,60,30);
			jl[3].setBounds(330,60,80,30);jtf[3].setBounds(400,60,150,30);
			jl[4].setBounds(560,60,80,30);jtf[4].setBounds(630,60,80,30);
			this.add(jtitle);//��������ӵ�������
			this.add(jspt);//���ָ�����ӵ�������
			this.setVisible(true);//���ý������
			this.setTitle("��Ʒ��ϸ��Ϣ");//���ý������
			this.setBounds(300, 100, 850,550);//���ý���λ�����С
		}
  }
}
