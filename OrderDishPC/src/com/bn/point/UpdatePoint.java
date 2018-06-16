package com.bn.point;

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
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;

import static com.bn.constant.Constant.*;
import static com.bn.pcinfo.TableListener.*;
@SuppressWarnings("serial")
/*
 * ���²�̨��Ϣ
 * */
public class UpdatePoint extends JFrame implements ActionListener
{
	JLabel jl[]=
	{
			new JLabel("�޸Ĳ�̨"),new JLabel("��̨id"),new JLabel("��̨����"),
			new JLabel("��̨����"),new JLabel("�Ƿ����"),
			new JLabel("��������"),new JLabel("�ؼ���"),new JLabel("��̨����"),		
	};//����JLabel����
	JButton jb[]=
	{
			new JButton("�޸�"),new JButton("ȡ��")
	};//����JButton����
	JTextField jtf[]=
	{
			new JTextField(""),new JTextField(""),
			new JTextField(""),new JTextField("")
	};//����JTextField����
	JComboBox jcb[]={new JComboBox(),new JComboBox(),new JComboBox()};//����JComboBox����
	final StringBuilder sb=new StringBuilder();//�����ַ���
	public  DefaultMutableTreeNode curnode;//���嵱ǰ�ڵ�
	public MainUI mainui;
	//������ֳߴ�
	int width=480;
	int height=400;
	int leftf=width/24;
	int leftm=width/6;
	int leftb=width/4;
	int topf=height/7;
	int topv=height/14;
  public UpdatePoint(DefaultMutableTreeNode curnode,MainUI mainui)//���²�̨����
  {
	 this.curnode=curnode;
	 this.mainui=mainui;
	 Image image=this.getToolkit().getImage("src/com/bn/image/tb1.jpg");//����ͼƬ
	 this.setIconImage(image);//���ý���ͼ��
	 initUI();//����initUI����
  }
  public void initUI()
  {
	    this.setLayout(null);//���沼��������ΪĬ��
		jl[0].setBounds(width/2-80,2,200,topf+10);//����jl[0]��λ�����С
		jl[0].setFont(new Font("����",Font.BOLD,24));//����jl[0]������
		this.add(jl[0]);//��jl[0]��ӵ�������
		for(int i=1;i<jl.length;i++)//����JLabel�����塢λ�����С������ӵ�������
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
		jtf[0].setBounds(leftf+leftm,topf,leftb,topv);//����jtf[0]��λ�����С
		jtf[0].setText(values[0]);//����jtf[0]��ֵ
		jtf[0].setEditable(false);//����jtf[0]Ϊ���ɱ༭
		jtf[1].setBounds(2*leftf+2*leftm+leftb,topf,leftb,topv);//����jtf[1]��λ�����С
		jtf[1].setText(values[1]);//����jtf[1]��ֵ
		jtf[2].setBounds(leftf+leftm,topf+4*topv,leftb,topv);//����jtf[2]��λ�����С
		jtf[2].setText(values[4]);//����jtf[2]��ֵ
		jtf[3].setBounds(2*leftf+2*leftm+leftb,topf+4*topv,leftb,topv);//����jtf[3]��λ�����С
		jtf[3].setText(values[5]);//����jtf[3]��ֵ
		jcb[0].setBounds(leftf+leftm,topf+2*topv,leftb,topv);//����jcb[0]��λ�����С
		jcb[1].setBounds(2*leftf+2*leftm+leftb,topf+2*topv,leftb,topv);//����jcb[1]��λ�����С
		jcb[2].setBounds(leftf+leftm,topf+6*topv,leftb,topv);//����jcb[2]��λ�����С
	    jb[0].setBounds(leftf+leftm,topf+8*topv,80,30);//����jb[0]��λ�����С
	    jb[1].setBounds(2*leftf+2*leftm+leftb/2,topf+8*topv,80,30);//����jb[1]��λ�����С
		for(int i=0;i<jtf.length;i++)//��JTextField��ӵ������У�����Ӽ���
		{
			this.add(jtf[i]);
			jtf[i].addActionListener(this);
		}
		//�����ϻ�ȡ��Ϣ
		SocketClient.ConnectSevert(Constant.GET_RMNAME);//�ӷ�������ȡ��������
		String allname=SocketClient.readinfo;
		SocketClient.ConnectSevert(Constant.GET_RTNAME);//�ӷ�������ȡ��̨�������
		String alltname=SocketClient.readinfo;
		String str[]=TypeExchangeUtil.getStringInfo(allname,0);
		String strt[]=TypeExchangeUtil.getStringInfo(alltname, 0);
		for(String s:strt)//����̨���������ӵ�jcb[0]��ѡ����
		{
			jcb[0].addItem(s);
		}
		for(String s:str)//������������ӵ�jcb[2]��ѡ����
		{
			jcb[2].addItem(s);
		}
		jcb[1].addItem("����");jcb[1].addItem("ͣ��");//��������������ͣ�á�����ѡ����ӵ�jcb[1]��
		for(int i=0;i<jb.length;i++)//��JButton��ӵ�������
		{
			this.add(jb[i]);
		}
		for(int i=0;i<jcb.length;i++)//��JComboBox��ӵ������У�����Ӽ���
		{
			this.add(jcb[i]);
			jcb[i].addActionListener(this);
		}
		jcb[0].setSelectedItem(values[2]);//����JComboBox��Ĭ��ѡ��
		jcb[1].setSelectedItem(values[3]);
		jcb[2].setSelectedItem(values[6]);
		jb[0].addActionListener//jb[0]�ļ���
		(
		   new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					updateInfo();//����updataInfo����
				}
	       }
	     );
		jb[1].addActionListener//jb[1]�ļ���
		(
		   new ActionListener()
		   {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					UpdatePoint.this.dispose();//���½���ر�
				}
		   }
		 );
		this.setBounds(300, 150,480,400);//���ý����λ�����С	
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
		this.setVisible(true);//����Ϊ�ɼ�
		this.setTitle("���²�̨");//���ñ���
		Image image=this.getToolkit().getImage("src/com/bn/image/tb1.jpg");//����ͼƬ
		this.setIconImage(image);//����ͼ��
		jtf[1].requestFocusInWindow();//��ý���
  }
  @SuppressWarnings("static-access")
  public void updateInfo()//������Ϣ����
    {
	   for(int i=0;i<jtf.length;i++)//��JTextField�е���Ϣ��ӵ�sb��
	    {
			sb.append(Constant.UPDATE_POINT+jtf[i].getText().toString());
		}
		for(int i=0;i<jcb.length;i++)//��JComboBox��ѡ�е�ֵ��ӵ�sb��
		{
			sb.append(Constant.UPDATE_POINT+jcb[i].getSelectedItem().toString());
		}
		SocketClient.ConnectSevert(sb.toString());//����Ϣ����������
		String addbackinfo=SocketClient.readinfo;
		if(addbackinfo.equals("ok"))//������ء�ok��������ʾ��Ϣ"���²�̨�ɹ�"
		{
			JOptionPane.showMessageDialog(UpdatePoint.this,"���²�̨�ɹ�","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
			//���������棬���½���
			SocketClient.ConnectSevert(GET_POINT+curnode.toString());
			String getinfo=SocketClient.readinfo;
			Vector<Vector<String>> data=TypeExchangeUtil.strToVector(getinfo);
			mainui.createJTable(data,title,20,MainUI.topheight,MainUI.midwidth,MainUI.buttomheight);
            mainui.createRight(curnode);
		    this.dispose();//����ر�
		}
		else
		{   //�������ʾ��Ϣ"����ʧ��,����������Ϣ"
			JOptionPane.showMessageDialog(UpdatePoint.this,"����ʧ��,����������Ϣ","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
		}
  }
  public static void main(String args[])//������
  {
	  try 
	  {//windows���
		 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	  } 
	  catch (Exception e) 
	  {
	    e.printStackTrace();
	  }
  }
@Override
public void actionPerformed(ActionEvent e)
{
	 if(e.getSource().equals(jtf[1]))
	 {
		 jcb[0].requestFocus();//jcb[0]��ý���
	 }
	 else if(e.getSource().equals(jcb[0]))
	 {
		 jcb[1].requestFocus();//jcb[1]��ý���
	 }
	 else if(e.getSource().equals(jcb[1]))
	 {
		 jtf[2].requestFocus();//jtf[2]��ý���
	 }
	 else if(e.getSource().equals(jtf[2]))
	 {
		 jtf[3].requestFocus();//jtf[3]��ý���
	 }
	 else if(e.getSource().equals(jtf[3]))
	 {
		 jcb[2].requestFocus();//jcb[2]��ý���
	 }
	 else if(e.getSource().equals(jcb[2]))
	 {
		 jb[0].requestFocus();//jb[0]��ý���
	 }
}
}
