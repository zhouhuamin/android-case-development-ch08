package com.bn.point;

import static com.bn.constant.Constant.*;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import com.bn.constant.Constant;
import static com.bn.pcinfo.TableListener.*;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;
/*
 * ��Ӳ�̨��Ϣ���ұ��浽����
 * */
@SuppressWarnings("serial")
public class AddPoint extends JFrame 
{
	JLabel jl[]=
	   {
			new JLabel("��Ӳ�̨"),new JLabel("��̨id"),new JLabel("��̨����"),
			new JLabel("��̨����"),new JLabel("��������"),
			new JLabel("��������"),new JLabel("�ؼ���")		
	    };//����JLabel����
	JButton jb[]={new JButton("���"),new JButton("ȡ��")};//����JButton����
	JTextField jtf[]=
	{
			new JTextField(""),new JTextField(""),new JTextField(""),
			new JTextField(""),new JTextField("")
	};//����JTextField����
	JComboBox jcb[]={new JComboBox(),new JComboBox()};//����JComboBox����
	final StringBuilder sb=new StringBuilder();//�����ַ���
	public  DefaultMutableTreeNode curnode;//���嵱ǰ�ڵ�
	public MainUI mainui;
	//������ֳߴ�
	int width=480;
	int height=300;
	int leftf=width/24;
	int leftm=width/6;
	int leftb=width/4;
	int topf=height/5;
	int topv=height/10;
  public AddPoint( DefaultMutableTreeNode curnode,MainUI mainui)
  {
	  this.curnode=curnode;
	  this.mainui=mainui;
	  initUI();//����initUI����
  }
  //��ʼ����Ӳ�̨����
  public void initUI()
  {
		Image image=this.getToolkit().getImage("src/com/bn/image/tb1.jpg");//����ͼƬ
		this.setIconImage(image);//���ý���ͼ��
	    this.setLayout(null);//���ý��沼����ΪĬ��
		jl[0].setBounds(width/2-80,2,200,topf+10);//����jl[0]��λ�����С
		jl[0].setFont(new Font("����",Font.BOLD,24));//����jl[0]����
		this.add(jl[0]);//��jl[0]��ӵ�������
		for(int i=1;i<jl.length;i++)//����JLabel�����塢λ�����С������JLabel��ӵ�������
		{
			jl[i].setFont(new Font("����",Font.BOLD,18));
			if(i%2!=0)
			{
				jl[i].setBounds(leftf, topf+topv*(i-1),leftm,topv);
			}
			else
			{
				jl[i].setBounds(2*leftf+leftm+leftb, topf+topv*(i-2),leftm,topv);
			}
			this.add(jl[i]);
		}
		jtf[0].setBounds(leftf+leftm,topf,leftb,topv);//����JTextField��λ�����С
		//��ȡ��̨���ֵ���Զ���ʾ���ֵ���Ҳ��ɸģ�
		SocketClient.ConnectSevert(Constant.GET_MAXPOINTNO);
		String maxno=SocketClient.readinfo;
		jtf[0].setText(maxno);//����jtf[0]��ֵ
		jtf[0].setEditable(false);//����jtf[0]Ϊ���ɱ༭
		jtf[1].setBounds(2*leftf+2*leftm+leftb,topf,leftb,topv);//����jtf[1]��λ�����С
		jtf[2].setBounds(leftf+leftm,topf+4*topv,leftb,topv);//����jtf[2]��λ�����С
		jtf[3].setBounds(2*leftf+2*leftm+leftb,topf+4*topv,leftb,topv);//����jtf[3]��λ�����С
		jcb[0].setBounds(leftf+leftm,topf+2*topv,leftb,topv);//����jcb[0]��λ�����С
		jcb[1].setBounds(2*leftf+2*leftm+leftb,topf+2*topv,leftb,topv);//����jcb[1]��λ�����С
	    jb[0].setBounds(leftf+leftm,topf+6*topv,80,30);//����jb[0]��λ�����С
	    jb[1].setBounds(2*leftf+2*leftm+leftb/2,topf+6*topv,80,30);//����jb[1]��λ�����С
		for(int i=0;i<jtf.length;i++)//��JTextField��ӵ�������
		{
			this.add(jtf[i]);
		}
		//ͨ����������ò�������
		SocketClient.ConnectSevert(Constant.GET_RMNAME);
		String allname=SocketClient.readinfo;
		//ͨ����������ò�̨������
		SocketClient.ConnectSevert(Constant.GET_RTNAME);
		String alltname=SocketClient.readinfo;
		String str[]=TypeExchangeUtil.getStringInfo(allname, 0);//���������Ʒ���������
		String strt[]=TypeExchangeUtil.getStringInfo(alltname, 0);//����̨����������������
		for(String s:strt)//����̨��������ӵ�jcb[0]��ѡ����
		{
			jcb[0].addItem(s);
		}
		for(String s:str)//����������ӵ�jcb[1]��ѡ����
		{
			jcb[1].addItem(s);
		}
		jcb[1].setSelectedItem(curnode.toString());//jcb[1]��ѡ�����ǵ�ǰ�ڵ�
		for(int i=0;i<2;i++)//��JComboBox��JButton��ӵ�������
		{
			this.add(jb[i]);
			this.add(jcb[i]);
		}
		jb[0].addActionListener//��Ӱ�ť��Ӽ���
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					addInfo();//����addInfo����
				}
	       }
		);
		jb[1].addActionListener//ȡ����ť��Ӽ���
		(
			new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						for(int i=1;i<jtf.length;i++)
						{
							jtf[i].setText("");//JTextField�ÿ�
						}
					}
		      }
		);
		
		jtf[1].addActionListener//jtf[1]��Ӽ���
		(
		 new ActionListener()
		  {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jcb[0].requestFocus();//��ý���
			}
		  }
		);
		jcb[0].addActionListener//jcb[0]��Ӽ���
		(
				 new ActionListener()
				  {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						jcb[1].requestFocus();//��ý���
					}
				  }
			);
		jtf[2].addActionListener//jtf[2]��Ӽ���
		(
		 new ActionListener()
		  {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jtf[3].requestFocus();//��ý���
			}
		  }
		);
		jtf[3].addActionListener//jtf[3]��Ӽ���
		(
				new ActionListener()
				  {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						jb[0].requestFocus();//jb[0]��ý���
					}
				  }	
		);
		jcb[1].addActionListener//jcb[1]��Ӽ���
		(
				new ActionListener()
				  {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						jtf[2].requestFocus();//jtf[2]��ý���
					}
				  }	
		);
		this.setBounds(300, 150,480,320);//���ý����λ�����С
		this.setTitle("��Ӳ�̨");//���ý���ı���
		this.setAlwaysOnTop(true);//���ý��������ڶ�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
		this.setVisible(true);//����Ϊ�ɼ�
		jtf[1].requestFocusInWindow();//jtf[1]��ý���
  }
  //��Ӳ�̨��Ϣ����
  public void addInfo()
    {
	    for(int i=0;i<jtf.length-1;i++)
	    {
			sb.append(Constant.ADD_POINT+jtf[i].getText().toString());//��JTextField��ֵ��ӵ��ַ���sb��
		}
		for(int i=0;i<jcb.length;i++)//��JComboBox��ֵ��ӵ��ַ���sb��
		{
			sb.append(Constant.ADD_POINT+jcb[i].getSelectedItem().toString());
		}
		SocketClient.ConnectSevert(sb.toString());//����Ϣ����������
		String addbackinfo=SocketClient.readinfo;
		if(addbackinfo.equals("ok"))//������ء�ok��
		{
			//��ӳɹ���������ʾ
			JOptionPane.showMessageDialog(AddPoint.this,"��Ӳ�̨�ɹ�","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			this.dispose();//�رյ�ǰ����
			SocketClient.ConnectSevert(GET_POINT+curnode.toString());//�ӷ�������ò�̨��Ϣ
			String getinfo=SocketClient.readinfo;
			Vector<Vector<String>> data=TypeExchangeUtil.strToVector(getinfo);//���½���
		    MainUI.createJTable(data,title,20,MainUI.topheight,MainUI.midwidth,MainUI.buttomheight);
			new AddPoint(curnode,mainui);
		}
		else
		{   //���򣬳���ʾ��Ϣ"���ʧ��,����������Ϣ"
			JOptionPane.showMessageDialog(AddPoint.this,"���ʧ��,����������Ϣ","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
		}
		for(int i=1;i<jtf.length;i++)//JTextField�ÿ�
		{
			jtf[i].setText("");
		}
  }
  public static void main(String args[])//������
  {
	  try {//windows���
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
  }
}
