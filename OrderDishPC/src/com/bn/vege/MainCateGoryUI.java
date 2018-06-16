package com.bn.vege;
/*��������Ľ���*/
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;


import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;

import static com.bn.constant.Constant.*;
import static com.bn.pcinfo.TableListener.title;
//import static com.bn.util.TypeExchangeUtil.*;
public class MainCateGoryUI implements ActionListener
{
    String strinfo;
    MainUI mui;
    public MainCateGoryUI(MainUI mui)
    {
    	this.mui=mui;
    }
	@Override
	public void actionPerformed(ActionEvent e)
	{
		SocketClient.ConnectSevert(GET_MCGMAXNO);//��ò�Ʒ�������ID
		String index=SocketClient.readinfo;
		System.out.println("index"+index);
		strinfo=((Integer.parseInt(index)+1)<10)?("00"+(Integer.parseInt(index)+1)):
			(((Integer.parseInt(index)+1)<100)?("0"+(Integer.parseInt(index)+1)):((Integer.parseInt(index)+1)+""));
        addMainCateGory addmcg=new addMainCateGory(mui);
        addmcg.setVisible(true);//�������������棬����Ϊ�ɼ�
	}
   @SuppressWarnings("serial")
class addMainCateGory extends JFrame
{
	   DefaultMutableTreeNode curenode=Constant.vegenode[0];//��ǰ�ڵ�Ϊ��Ʒ����
	   MainUI mainui;
	   int width;//���ÿ��
	   int height;//���ø߶�
	   Font font=new Font("����",Font.BOLD,14);//������������
	   Font fonttitle=new Font("����",Font.BOLD,20);
	   @SuppressWarnings("rawtypes")
	public addMainCateGory(final MainUI mainui)
	   {
			 JPanel jp=new JPanel();//����JPanel
			jp.setLayout(null);//����JPanel������ΪĬ��
			 this.setBounds(200, 100, 520, 250);//���ý���λ�����С
			 width=540;//���ÿ��
			 height=300;//���ø߶�
			 //��߾�
			 int leftwidth=width/20;
			 //�ߵľ���
			 int topheight=height/60;
			 //�ұ߾�
			 int rightwidth=width/2;
			 //jlabel�Ŀ��
			 int lwidth=width/2/3;
			 //JLabel�ĸ߶�
			 int lheight=height/10;
			 //text�Ŀ��
			 int twidth=width/2/2;
			 //text�ĸ߶�
			 int theight=height/12;
			 //�õ����text��ߵı߾�
			 int tleftwidth=leftwidth+lwidth;
			 //�õ��ұ�text��ߵı߾�
			 int trightwidth=rightwidth+lwidth;
			 
			 JLabel jltitle=new JLabel("��Ӳ�Ʒ����");//�������
			 jltitle.setFont(fonttitle);//��������
			 jltitle.setBounds(width/3, topheight, 2*lwidth, lheight);//����λ�����С
			 jp.add(jltitle);//��������ӵ�JPanel��
				 
		//��ӵ�һ�пؼ�
			//��ӱ�ſؼ�
			 JLabel jlid=new JLabel("����ID��");//��������id JLabel
			 jlid.setFont(font);//��������
			 jlid.setBounds(leftwidth, 3*topheight+2*lheight, lwidth, lheight);//����λ�����С
			 jp.add(jlid);//��JLabel��ӵ�JPanel
			 JLabel jlidshow=new JLabel(strinfo);//���塰����ID��JLabel
			 jlidshow.setFont(font);//��������
			 jlidshow.setBounds(tleftwidth, 3*topheight+2*lheight, twidth, lheight);//����λ�����С
			 jp.add(jlidshow);//��JLabel��ӵ�JPanel��

	       //���������������
	         final JLabel jlmcgname=new JLabel("�������ƣ�");//��������JLabel
	         jlmcgname.setFont(font);//��������
	         jlmcgname.setBounds(rightwidth,3*topheight+2*lheight,lwidth,lheight);//����λ�����С
			 jp.add(jlmcgname);//��JLabel��ӵ�JPanel��
	         final JTextField jetcgname=new JTextField();//���塰�������ơ�JTextField
	         jetcgname.setBounds(trightwidth, 3*topheight+2*lheight, twidth, theight);//����λ�����С
	         jp.add(jetcgname);//��JTextField��ӵ�JPanel��
				         				         
	       //��Ӱ�ť�ؼ�
	         JButton jbok=new JButton("���");//������Ӱ�ť
	         jbok.setFont(font);//��������
	         jp.add(jbok);//����ť��ӵ�JPanel��
	         jbok.setBounds(width/2/4,  7*topheight+lheight*5, lwidth, theight);//����λ�����С
	         jbok.addActionListener//��ť��Ӽ���
	         (
	            new ActionListener()
	            {
					@SuppressWarnings({ "unchecked", "static-access" })
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						if(jetcgname.getText().equals(""))
						{
							JOptionPane.showMessageDialog(addMainCateGory.this, "�������Ʋ���Ϊ�գ�������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						else 
						{
							String pmcgname=jetcgname.getText().toString();
							String msg= ADD_CMG+strinfo+ ADD_CMG+pmcgname;							
							SocketClient.ConnectSevert(msg);//��������Ϣ����������
							String readinfo=SocketClient.readinfo;
							if(readinfo.equals("ok"))
							{
								JOptionPane.showMessageDialog(addMainCateGory.this, "�����Ϣ��ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
								title=new Vector();//����
								{
									title.add("����ID");title.add("����");

								}
								SocketClient.ConnectSevert(GET_MCG);//���������Ϣ
								String getinfo=SocketClient.readinfo;
								if(getinfo.equals("fail"))
								{
		                        	JOptionPane.showMessageDialog(addMainCateGory.this, "��ȡ��Ϣʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
								}
								else
								{   //���½���
									Vector<Vector<String>> data=TypeExchangeUtil.strToVector(getinfo);
									int topwidth=(int) (768*0.07);
									int midwidth=(int) ((1024-1024*0.25)*0.78);
									int buttomheight=(int) (768*0.6);
									mainui.createJTable(data,title,20,topwidth,midwidth,buttomheight);
									mainui.createRight(curenode);
								}
								addMainCateGory.this.setVisible(false);//�������������治�ɼ�
							}
							else
							{
								JOptionPane.showMessageDialog(addMainCateGory.this, "�����Ϣ���ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
								addMainCateGory.this.setVisible(false);
							}
						}
					}
	            }
	         );
	         //ȡ����ť�ؼ�
	         JButton jbfali=new JButton("ȡ��");
	         jbfali.setFont(font);//��������
	         jp.add(jbfali);//��ȡ����ť��ӵ�JPanel��
	         jbfali.setBounds(width/2+width/2/4, 7*topheight+lheight*5, lwidth, theight);//����λ�����С
	         jbfali.addActionListener//��ť��Ӽ���
	         (
	           new ActionListener()
	           {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					jlmcgname.setText("");//���������ÿ�
				}
	           }
	         );
				         
	         this.add(jp);//��JPanel��ӵ�������
	         this.setVisible(true);//��������Ϊ�ɼ�
	         this.setEnabled(true);//��������Ϊ����	         
	         this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
	 		 this.setResizable(false);//���ý���ߴ�̶�
	 		 this.setTitle("��Ӳ�Ʒ����");//���ñ���
	 		 Image image=this.getToolkit().getImage("src/com/bn/img/tb1.jpg");//�������
			 this.setIconImage(image);//����ͼ��      
		 }
   }
}
