package com.bn.vege;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static com.bn.util.TypeExchangeUtil.*;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
import javax.swing.JTextField;
//import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;

import static com.bn.constant.Constant.*;
/*��Ӳ�Ʒ�Ľ���*/
public class AddVegeUI implements ActionListener
{
    MainUI mui;
	String strinfo;
	public AddVegeUI(MainUI mui)//��Ӳ�Ʒ����
	{
		this.mui=mui;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		SocketClient.ConnectSevert(GET_VEGEMAXNO);//��ò�Ʒ��Ϣ
		String index=SocketClient.readinfo;
		System.out.println("index"+index);
		strinfo=((Integer.parseInt(index)+1)<10)?("00"+(Integer.parseInt(index)+1)):
			(((Integer.parseInt(index)+1)<100)?("0"+(Integer.parseInt(index)+1)):((Integer.parseInt(index)+1)+""));
        addVege addvege=new addVege(mui);//��Ӳ�Ʒ����
        addvege.setVisible(true);//����Ϊ�ɼ�
	}
	@SuppressWarnings("serial")
	class addVege extends JFrame//��Ӳ�Ʒ����
	{
		   DefaultMutableTreeNode curenode=Constant.vegenode[5];//��Ʒ����ڵ�
		   MainUI mainui;
		   int width;
		   int height;
		   Font font=new Font("����",Font.BOLD,14);//������������
		   Font fonttitle=new Font("����",Font.BOLD,20);
		   @SuppressWarnings("rawtypes")
		public addVege(final MainUI mainui)
		   {
			   this.mainui=mainui;
			   JPanel jp=new JPanel();//����JPanel
			   jp.setLayout(null);//����JPanel������ΪĬ��
				 this.setBounds(200, 0, 560, 500);//���ý����λ�����С
				 width=560;
				 height=720;
				 //��߾�
				 int leftwidth=width/25;
				 //�ߵľ���
				 int topheight=height/90;
				 //�ұ߾�
				 int rightwidth=width/2+leftwidth;

				 //jlabel�Ŀ��
				 int lwidth=width/2/3;
				 //JLabel�ĸ߶�
				 int lheight=height/22;
				 //text�Ŀ��
				 int twidth=width/2/2;
				 //text�ĸ߶�
				 int theight=height/22;
				 //�õ����text��ߵı߾�
				 int tleftwidth=leftwidth+lwidth;
				 //�õ��ұ�text��ߵı߾�
				 int trightwidth=rightwidth+lwidth;
				 //�õ�������λ
				 SocketClient.ConnectSevert(GET_CU);
				 String getcumsg=SocketClient.readinfo;
				 List<String[]> cudata=TypeExchangeUtil.strToList(getcumsg);
				 Vector custr=getnamebynoone(cudata);
				 //�õ���ϵ������
				 SocketClient.ConnectSevert(GET_VT);
				 String getvt=SocketClient.readinfo;
				 List<String[]> vtdata=TypeExchangeUtil.strToList(getvt);
				 Vector vtstr=getnamebynoone(vtdata);
				 
				 //�õ���������
				 SocketClient.ConnectSevert(GET_CG);
				 String getcg=SocketClient.readinfo;
				 List<String[]> cgdata=TypeExchangeUtil.strToList(getcg);
				 Vector cgstr=getnamebynotwo(cgdata);
				 
				 //�õ���������
				 SocketClient.ConnectSevert(GET_VS);
				 String getgg=SocketClient.readinfo;
				 List<String[]> ggdata=TypeExchangeUtil.strToList(getgg);
				 Vector ggstr=getnamebynoone(ggdata);
				 
				 JLabel jltitle=new JLabel("��Ӳ�Ʒ");//�������
				 jltitle.setFont(fonttitle);//���ñ�������
				 jltitle.setBounds(width/3, topheight, 2*lwidth, lheight);//���ñ���λ�����С
				 jp.add(jltitle);//��������ӵ�JPanel��
				 
				//��ӵ�һ�пؼ�
				//��ӱ�ſؼ�
				 JLabel jlid=new JLabel("�� Ʒ ID��");//������JLabel
				 jlid.setFont(font);//��������
				 jlid.setBounds(leftwidth, 2*topheight+lheight, lwidth, lheight);//����λ�����С
				 jp.add(jlid);//�����JLabel��ӵ�JPanel��
				 JLabel jlidshow=new JLabel(strinfo);//���塰��Ʒid��JLabel
				 jlidshow.setFont(font);//��������
				 jlidshow.setBounds(tleftwidth, 2*topheight+lheight, twidth, lheight);//����λ�����С
				 jp.add(jlidshow);//������Ʒid��JLabel��ӵ�JPanel��
			   //��ӵڶ��пؼ�
		       //���������������
		         final JLabel jlmcgname=new JLabel("��Ʒ���ƣ�");//��������JLabel
		         jlmcgname.setFont(font);//��������
		         jlmcgname.setBounds(leftwidth,3*topheight+2*lheight,lwidth,lheight);//����λ�����С
				 jp.add(jlmcgname);//��JLabel��ӵ�JPanel��
		         final JTextField jetcgname=new JTextField();//���塰��Ʒ���ơ�JTextField
		         jetcgname.setBounds(tleftwidth, 3*topheight+2*lheight, twidth, theight);//����λ�����С
		         jp.add(jetcgname);//������Ʒ���ơ�JTextField��ӵ�JPanel��
		         
		       //��׼�۸�
		         JLabel jlprice=new JLabel("��׼�۸�");//����۸�JLabel
		         jlprice.setFont(font);//��������
		         jlprice.setBounds(rightwidth, 3*topheight+2*lheight,lwidth,lheight);//����λ�����С
				 jp.add(jlprice);//��JLabel��ӵ�JPanel��
				 final JTextField jetprice=new JTextField();//���塰��׼�۸�JTextField
				 jetprice.setBounds(trightwidth, 3*topheight+2*lheight, twidth, theight);//����λ�����С
		         jp.add(jetprice);//������׼�۸�JTextField��ӵ�JPanel��
		         
		       //��ӵ����пؼ�
		       //��Ӽ�����λ�ؼ�
				 final JLabel jlcu=new JLabel("������λ��");//���������λJLabel
				 jlcu.setFont(font);//��������
				 jlcu.setBounds(leftwidth,  4*topheight+lheight*3, lwidth, lheight);//����λ�����С
		         jp.add(jlcu);//��JLabel��ӵ�JPanel��
		         final JComboBox jcbcu=new JComboBox(custr);//���塰������λ��JComboBox
		         jcbcu.setBounds(tleftwidth, 4*topheight+lheight*3, twidth, theight);//����λ�����С
		         jp.add(jcbcu);//��JComboBox��ӵ�JPanel��
		         
		       //������ؼ�
				 JLabel jlcg=new JLabel("�������");//�������JLabel
				 jlcg.setFont(font);//��������
				 jlcg.setBounds(rightwidth, 4*topheight+lheight*3, lwidth, lheight);//����λ�����С
				 jp.add(jlcg);//��JLabel��ӵ�JPanel��
				 final JComboBox jcbcg=new JComboBox(cgstr);//���塰�������JComboBox
				 jcbcg.setBounds(trightwidth,4*topheight+lheight*3, twidth, theight);//����λ�����С
				 jp.add(jcbcg);//��JComboBox��ӵ�JPanel��
		         
		         //��ӵ����пؼ�
				//��Ӳ�ϵ�ؼ�
				 JLabel jlvt=new JLabel("��    ϵ��");//�����ϵJLabel
				 jlvt.setFont(font);//��������
				 jlvt.setBounds(leftwidth, 5*topheight+lheight*4, lwidth, lheight);//����λ�����С
				 jp.add(jlvt);//��JLabel��ӵ�JPanel��
				 final JComboBox jcbvt=new JComboBox(vtstr);//���塰��ϵ��JComboBox
				 jcbvt.setBounds(tleftwidth,5*topheight+lheight*4, twidth, theight);//����λ�����С
				 jp.add(jcbvt);//��JComboBox��ӵ�JPanel��
				 
				//��ӹ��
				 final JLabel jlgg=new JLabel("��    ��");//������JLabel
				 jlgg.setFont(font);//��������
				 jlgg.setBounds(rightwidth,  5*topheight+lheight*4, lwidth, lheight);//����λ�����С
		         jp.add(jlgg);//��JLabel��ӵ�JPanel��
		         final JComboBox jcbgg=new JComboBox(ggstr);//���塰���JComboBox
		         jcbgg.setBounds(trightwidth,  5*topheight+lheight*4, twidth, theight);//����λ�����С
		         jp.add(jcbgg);//��JComboBox��ӵ�JPanel��
		         
				//��ӵ����пؼ�
				 //��ӽ��ܿؼ�
				 JLabel jljs=new JLabel("��    �ܣ�");//�������JLabel
				 jljs.setFont(font);//��������
				 jljs.setBounds(leftwidth, 6*topheight+lheight*6+lheight/4, lwidth, 2*lheight);//����λ�����С
				 jp.add(jljs);//��JLabel��ӵ�JPanel��
				 final JTextArea jtajs=new JTextArea();//���塰���ܡ�JTextArea
				 jtajs.setLineWrap(true);//����Ϊ�Զ�����
				 JScrollPane jspjs=new JScrollPane(jtajs);//�������������ӵ�JTextArea��
				 jspjs.setBounds(tleftwidth, 6*topheight+lheight*6+lheight/4, trightwidth, theight+lheight*2);//����λ�����С
				 jp.add(jspjs);//��JTextArea��ӵ�JPanel��
				 
				//��ӵ����пؼ�
		         //��Ӱ�ť�ؼ�
		         JButton jbok=new JButton("��һ��");//������һ��JButton
		         jbok.setFont(font);//��������
		         jp.add(jbok);//��JButton��ӵ�JPanel��
		         jbok.setBounds(width/2-width/2/4,  8*topheight+lheight*8+lheight*2+lheight/4, lwidth, theight);//����λ�����С
		         jbok.addActionListener//��ť��Ӽ���
		         (
		           new ActionListener()
		           {
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						Pattern pattern = Pattern.compile("[0-9]+.?[0-9]*");  
						if(jetcgname.getText().equals(""))
						{
							JOptionPane.showMessageDialog(addVege.this, "��Ʒ���Ʋ���Ϊ�գ�������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						else if(jetprice.getText().toString().equals(""))
						{
							JOptionPane.showMessageDialog(addVege.this, "��Ʒ�۸���Ϊ�գ�������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						else if(!pattern.matcher(jetprice.getText().toString()).matches())
						{
							JOptionPane.showMessageDialog(addVege.this, "��Ʒ�۸��ʽ���벻��ȷ��������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}						
						else
						{
							String vegename=jetcgname.getText().toString();
							String vegejg=jetprice.getText();
							String vegecu=jcbcu.getSelectedItem().toString();
							String vegegg=jcbgg.getSelectedItem().toString();
							String vegecg=jcbcg.getSelectedItem().toString();
							String vegevt=jcbvt.getSelectedItem().toString();
							String vegeinfo=jtajs.getText().toString();
							//��Ӳ�Ʒ
							String sendmsg=ADD_VEGE+strinfo+ADD_VEGE+vegename+ADD_VEGE+vegejg			
							+ADD_VEGE+vegecu+ADD_VEGE+vegegg+ADD_VEGE+vegecg+ADD_VEGE+vegevt
							+ADD_VEGE+vegeinfo;
				 
							SocketClient.ConnectSevert(sendmsg);//����Ʒ��Ϣ����������
							String readinfo=SocketClient.readinfo;
							
							if(readinfo.equals("ok"))//������ء�ok��
							{
								//�����Ʒ��ӳɹ��������һ��
						        AddMainImage ami=new AddMainImage(mainui,strinfo);
						        ami.setVisible(true);//���������ͼ����
						        addVege.this.setVisible(false);//��Ӳ�Ʒ������Ϊ���ɼ�
						    }
							else
						    {
						    	JOptionPane.showMessageDialog(addVege.this, "��Ʒ���ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						    	addVege.this.setVisible(false);
						    }
						}
					  }
					}	
		         );
		         JButton jbfali=new JButton("ȡ��");
		         jbfali.setFont(font);//���á�ȡ������ť����
		         jp.add(jbfali);//����ȡ������ť��ӵ�JPanel��
		         jbfali.setBounds(width/2+width/2/4, 8*topheight+lheight*8+lheight*2+lheight/4, lwidth, theight);
		         jbfali.addActionListener//��ȡ������ť��Ӽ���
		         (
		            new ActionListener()
		            {
						@Override
						public void actionPerformed(ActionEvent e)
						{
							addVege.this.setVisible(false);
						}
		            }
		         );
		         
		         this.add(jp);//��JPanel��ӵ�������
		         this.setVisible(true);//���ý���Ϊ�ɼ�
		         this.setEnabled(true);//���ý���Ϊ����
		         
		         this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ý���رշ�ʽ
		 		 this.setResizable(false);//���ý���ߴ�̶�
		 		 this.setTitle("��Ӳ�Ʒ");//���ñ���
		 		 Image image=this.getToolkit().getImage("src/com/bn/img/tb1.jpg");//����ͼƬ
				 this.setIconImage(image);//���ý���ͼ��
		   }
	}
}
