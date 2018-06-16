package com.bn.account;

import static com.bn.constant.Constant.GET_ORDER;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;
public class AccountOrder extends JFrame 
{
	private static final long serialVersionUID = 1L;
	DefaultMutableTreeNode curenode1=Constant.accountnode[0];//���˽ڵ�
	Font font=new Font("����",Font.BOLD,20);//��������
	Vector<String> title=new Vector<String>();//����
	{
	    title.add("��Ʒ����");
	    title.add("��Ʒ����");
	    title.add("��Ʒ�۸�");
	    title.add("��Ʒ���");
	}
	public  Vector<Vector<String>> data;//������������
	String str[][]=null;//����ն�ά����
	MainUI mainui;//������
	Pattern pattern = Pattern.compile("[0-9]*");//ָ������Ϊ���ֵ�������ʽ  
	JLabel jl[]=
	  {
		new JLabel("��̨����")
		,new JLabel("Ӧ   ��")
		,new JLabel("ʵ    ��")
		,new JLabel("��    ��")
	  };//JLabel����
	  JLabel jrl[]=
	  {
			new JLabel("����"),new JLabel("����"),
			new JLabel("���ʱ��"),new JLabel("����Ա��")
	  };//JLabel����
	  
	  JTextField jtf[]=
	  {
		new JTextField(""),new JTextField("")
		,new JTextField(""),new JTextField("")
	  };//JTextField����
	  JTextField jtfr[]=
	  {
		new JTextField(""),new JTextField(""),new JTextField(""),new JTextField(""),
	  };//JTextField����
	  JButton jcount=null;//���㰴ť
	  JButton jok=new JButton("ȷ��");//ȷ����ť
	  JButton jreset=new JButton("ȡ��");//ȡ����ť
  public AccountOrder(String order,String xxorder,final MainUI mainui)
  {
	  this.mainui=mainui;
	  JLabel jtitle=new JLabel("�˵�");//����ΪJLabel�˵�
	  JSplitPane jspt=new JSplitPane();//���÷ָ���
	  JPanel jpl=new JPanel();//��������JPanel
	  JPanel jpr=new JPanel();
	  jspt.setLeftComponent(jpl);//���÷ָ�����JPanel�����λ��
	  jspt.setRightComponent(jpr);
	  jspt.setDividerSize(15);//���÷ָ����ߴ�
	  jspt.setDividerLocation(250);//���÷ָ���λ��	  
	  
	  final JCheckBox isfp=new JCheckBox("�Ƿ񿪷�Ʊ");//����JCheckBox
	  isfp.setFont(font);//��������
	  isfp.setSelected(false);//����Ĭ�ϲ�ѡ��
	  isfp.setBounds(20,260,160, 30);//����λ�����С
	  JPanel jinfo1=new JPanel();//����JPanel
	  jinfo1.setBounds(0,0, 250,300);//����JPanelλ�����С
	  jinfo1.setBorder(BorderFactory.createLineBorder(Color.black));//����JPanel�߿�
	  jinfo1.setLayout(null);//����JPanel������ΪĬ��
	  jpl.add(jinfo1);//��JPanel��ӵ�jpl��
	  jinfo1.add(isfp);//��JCheckBox��ӵ�JPanel��
	  jcount=new JButton("����");//������㰴ť
	  jcount.setBounds(180,260,60,30);//���ð�ťλ�����С
	  jinfo1.add(jcount);//����ť��ӵ�JPanel��
	  jtf[2].requestFocus();//���ý���
	  //�����һ�Ǯ��
	  jcount.addActionListener//���㰴ť����
	  (
		new ActionListener()
		  {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String allmoney=jtf[2].getText().toString();//��jtf[2]��ֵ����allmoney
				String zmoney=jtf[1].getText().toString();//��jtf[1]��ֵ����zmoney
				System.out.println("allmoney"+allmoney);
				if(allmoney.equals("")||!pattern.matcher(allmoney).matches())//���Ǯ����ʽ��ƥ�䣬����ʾ
				{
					JOptionPane.showMessageDialog(AccountOrder.this,"������ȷ��ʽ��Ǯ��","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(Float.parseFloat(allmoney)<Float.parseFloat(zmoney))//���Ӧ��Ǯ������ʵ��Ǯ��������ʾ
				{
					JOptionPane.showMessageDialog
			  	       (
				    		   AccountOrder.this,
				    		   "����Ǯ������",
				    		   "��ʾ",
				    		   JOptionPane.INFORMATION_MESSAGE
			  	    	);
				}
				else
				{   //�����һ�Ǯ��=ʵ��Ǯ��-Ӧ��Ǯ��
					jtf[3].setText(Float.parseFloat(allmoney)-Float.parseFloat(zmoney)+"");
				}
			}
		  
	      }
	   );
	  
	  jok.setBounds(20,570,80,40);//ȷ����ťλ�����С
	  jpl.add(jok);//��ȷ����ť��ӵ�jpl��
	  jreset.setBounds(140,570,80,40);//ȡ����ťλ�����С
	  jpl.add(jreset);//��ȡ����ť��ӵ�jpl��
	  str=TypeExchangeUtil.getString(order);//������Ϣ
	  for(int i=0;i<4;i++)//ͨ��ѭ����䣬����JLabel��λ�����С�����壬����ӵ�jinfo1��
	  {
		  jl[i].setBounds(5,20+i*40,100,30);
		  jinfo1.add(jl[i]);
		  jl[i].setFont(font);
	  }
	  jtf[0].setText(str[0][0]);//����jtf[0]��ֵ
	  jtf[1].setText(str[0][5]);//����jtf[1]��ֵ
	  
	  for(int i=0;i<jtf.length;i++)//ͨ��ѭ���������JTextField��λ�����С������ӵ�jinfo1��
	  {
		  jtf[i].setBounds(100,20+i*40,120,30);
		  jinfo1.add(jtf[i]);
	  }
	  for(int i=0;i<2;i++)//��JTextField����Ϊ���ɱ༭
	  {
		  jtf[i].setEditable(false); 
	  }
	  jtf[3].setEditable(false);//��jtf[3]����Ϊ���ɱ༭
	  for(int i=0;i<jrl.length;i++)//����JLabel�����壬����ӵ�jpr��
	  {
		  jrl[i].setFont(new Font("����",Font.BOLD,14));
		  jpr.add(jrl[i]);
	  }
	  
	  jtitle.setBounds(280,5,100,40);//���ñ����λ�����С
	  jtitle.setFont(font);//���ñ��������
	  jpr.add(jtitle);//��������ӵ�jpr��
	  jrl[0].setBounds(20,40,60,30);//����jrl[0]��λ�����С
	  jrl[1].setBounds(140,40,60,30);//����jrl[1]��λ�����С
	  jrl[2].setBounds(220,40,80,30);//����jrl[2]��λ�����С
	  jrl[3].setBounds(460,40,80,30);//����jrl[3]��λ�����С
	  
	  jtfr[0].setBounds(60,40,80,30);//����jtfr[0]��λ�����С
	  jtfr[1].setBounds(180,40,40,30);//����jtfr[1]��λ�����С
	  jtfr[2].setBounds(280,40,180,30);//����jtfr[2]��λ�����С
	  jtfr[3].setBounds(530,40,60,30);//����jtfr[3]��λ�����С
	  for(int i=0;i<jtfr.length;i++)//��JTextField��ӵ�jpr�У�������JTextField��ֵ�����壬����Ϊ�ɱ༭
	  {
		  jpr.add(jtfr[i]);
		  jtfr[i].setText(str[0][i+1]);
		  jtfr[i].setEnabled(false);
		  jtfr[i].setFont(new Font("����",Font.BOLD,14));
	  }
	  //��ʼ��������
	  Vector<Vector<String>> orderinfo=TypeExchangeUtil.strToVector(xxorder);//�����Ʒ��Ϣ
	  DefaultTableModel dtmtable=new DefaultTableModel(orderinfo,title);//������ģ��	
	  JTable table=new JTable(dtmtable);//����
	  DefaultTableColumnModel dtcm=(DefaultTableColumnModel) table.getColumnModel();//���JTable����
	  dtcm.getColumn(0).setResizable(false);//��һ������Ϊ�̶��ߴ�
	  table.setRowHeight(25);//�����и�Ϊ25
	  JScrollPane jscrollp=new JScrollPane(table);//�������������ӵ�����
	  jscrollp.setBounds(20,80,580,450);//���ù�������λ�����С
	  jpr.add(jscrollp);//����������ӵ�jpr��
	  jpl.setLayout(null);//����jpl�Ĳ�����ΪĬ��
	  jpr.setLayout(null);//����jpr�Ĳ�����ΪĬ��
	  this.add(jspt);//���ָ�����ӵ�������
	  this.setEnabled(true);//����������Ϊ�ɼ�
      this.setBounds(150,50,900,650);//�����������λ�����С		
	  this.setTitle("����");//���ñ���
	  this.setVisible(true);//����Ϊ����
	  this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
	  Image image=this.getToolkit().getImage("src/com/bn/image/tb1.jpg");//����ͼƬ
	  this.setIconImage(image);//���ý���ͼ��
	  //��ӽ���
	  jtf[2].requestFocusInWindow();
	jok.addActionListener//ȷ����ť��Ӽ���
	(
	   new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String isfpflg;
					//�Ƿ񿪷�Ʊ
					if(isfp.isSelected())
					{
						isfpflg="1";
					}
					else
					{
						isfpflg="0";
					}
					//����id
					String info=Constant.INSERT_ACCOUNT_VEGE+str[0][1];//����
					//��ȡǮ����Ϣ
					for(int i=0;i<jtf.length;i++)
					{
						String infot=jtf[i].getText().toString();
						if(infot.equals(""))
						{
							infot="0";
						}
						info=info+Constant.INSERT_ACCOUNT_VEGE+infot;
					}
					info=info+Constant.INSERT_ACCOUNT_VEGE+Constant.operator;//������
					info=info+Constant.INSERT_ACCOUNT_VEGE+isfpflg;//��Ʊ��ʾ
					String allmoney=jtf[2].getText().toString();//��jtf[2]��ֵ����allmoney										
					if(allmoney.equals("")||!pattern.matcher(allmoney).matches())//���Ǯ����ʽ��ƥ�䣬����ʾ
					{
						JOptionPane.showMessageDialog(AccountOrder.this,"��������ֵǮ��","��Ϣ",JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{   //����Ϣ����������
						SocketClient.ConnectSevert(info);
						if(SocketClient.readinfo.equals("ok"))//������ء�ok��,����ʾ"����ɹ�"
						{
							JOptionPane.showMessageDialog
							(
									AccountOrder.this,
									"����ɹ�",
									"��Ϣ",
									JOptionPane.INFORMATION_MESSAGE
							);
						    AccountOrder.this.dispose();//���˽���ر�
						    
						}
						else
						{
							JOptionPane.showMessageDialog//�������ʾ"������Ϣ"
							(
									AccountOrder.this,
									"������Ϣ",
									"��Ϣ",
									JOptionPane.INFORMATION_MESSAGE
							);
						}
						SocketClient.ConnectSevert(GET_ORDER);//�ӷ�������ȡ������Ϣ
						String getinfo=SocketClient.readinfo;
						Vector<String>title=new Vector<String>();//����
						{
							title.add("��̨����");title.add("������");title.add("�˿�����");
							title.add("����ʱ��");title.add("����Աid");title.add("�ܼ۸�");
						}
						if(getinfo.equals("fail"))//������ء�fail��,������ʾ��Ϣ"��ȡ��Ϣʧ��"
						{
							JOptionPane.showMessageDialog
	                  	       (
			       	    		   AccountOrder.this,
			       	    		   "��ȡ��Ϣʧ��",
			       	    		   "��ʾ",
			       	    		   JOptionPane.INFORMATION_MESSAGE
	                  	    	);
						}
						else{
							if(getinfo.length()==0)
							{
								JOptionPane.showMessageDialog//�����ϢΪ�գ�����ʾ"��ϢΪ��"
		                  	       (
		                  	    	   AccountOrder.this,
				       	    		   "��ϢΪ��",
				       	    		   "��ʾ",
				       	    		   JOptionPane.INFORMATION_MESSAGE
		                  	    	);
						     }
							//�����ݡ����⴫��createJTable������
						    data=TypeExchangeUtil.strToVector(getinfo);
							MainUI.createJTable(data,title,20,MainUI.topheight+80,MainUI.midwidth+100,MainUI.buttomheight);
							mainui.createRight(curenode1);
						}
					}
				}
			}
		);
	  }
}