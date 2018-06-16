package com.bn.vege;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import static com.bn.util.TypeExchangeUtil.*;
import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;

import static com.bn.constant.Constant.*;
import static com.bn.pcinfo.TableListener.title;
/*��������Ľ���*/
public class CateGoryUI implements ActionListener
{
     String strinfo;
     MainUI mui;
     public CateGoryUI(MainUI mui)
     {
    	 this.mui=mui;
     }
	@Override
	public void actionPerformed(ActionEvent e)
	{
		SocketClient.ConnectSevert(GET_CGMAXNO);//����������id
		String index=SocketClient.readinfo;
		System.out.println("index"+index);
		strinfo=((Integer.parseInt(index)+1)<10)?("00"+(Integer.parseInt(index)+1)):
			(((Integer.parseInt(index)+1)<100)?("0"+(Integer.parseInt(index)+1)):((Integer.parseInt(index)+1)+""));
        addCateGory addcg=new addCateGory(mui);
        addcg.setVisible(true);//���������棬������Ϊ�ɼ�
	}
   @SuppressWarnings("serial")
class addCateGory extends JFrame
{
	   DefaultMutableTreeNode curenode=Constant.vegenode[1];//��ǰ�ڵ��ǲ�Ʒ���ڵ�
	   MainUI mainui;
	   int width;//���ÿ��
	   int height;//���ø߶�
	   Font font=new Font("����",Font.BOLD,14);//������������
	   Font fonttitle=new Font("����",Font.BOLD,20);
		 @SuppressWarnings("rawtypes")
	public addCateGory(final MainUI mainui)
		 {
			 this.mainui=mainui;
			 JPanel jp=new JPanel();//����JPanel
			  jp.setLayout(null);//����JPanel������ΪĬ��
			 this.setBounds(200, 100, 520, 230);//���ý����λ�����С
			 width=540;//���ÿ��
			 height=300;//���ø߶�
			 //��߾�
			 int leftwidth=width/20;
			 //�ߵľ���
			 int topheight=height/60;
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
			//�õ����������
			 SocketClient.ConnectSevert(GET_MCG);
			 String getmcgname=SocketClient.readinfo;
			 System.out.println(getmcgname);
			 List<String[]> mcgdata=TypeExchangeUtil.strToList(getmcgname);
			 Vector mcgstr=getnamebynotwo(mcgdata);
			 
			 JLabel jltitle=new JLabel("��Ӳ�Ʒ���");//�������
			 jltitle.setFont(fonttitle);//��������
			 jltitle.setBounds(width/3, topheight, 2*lwidth, lheight);//����λ�����С
			 jp.add(jltitle);//��������ӵ�JPanel��
				 
		//��ӵ�һ�пؼ�
			//��ӱ�ſؼ�
			 JLabel jlid=new JLabel("���ID��");//�������id JLabel
			 jlid.setFont(font);//��������
			 jlid.setBounds(leftwidth, 2*topheight+lheight, lwidth, lheight);//����λ�����С
			 jp.add(jlid);//��JLabel��ӵ�JPanel��
			 JLabel jlidshow=new JLabel(strinfo);//���塰���ID��JLabel
			 jlidshow.setFont(font);//��������
			 jlidshow.setBounds(tleftwidth, 2*topheight+lheight, twidth, lheight);//����λ�����С
			 jp.add(jlidshow);//��JLabel��ӵ�JPanel��
		
	       //����������
	         final JLabel jlcgname=new JLabel("������ƣ�");//��������JLabel
	         jlcgname.setFont(font);//��������
	         jlcgname.setBounds(leftwidth,3*topheight+2*lheight,lwidth,lheight);//����λ�����С
			 jp.add(jlcgname);//��JLabel��ӵ�JPanel��
	         final JTextField jetcgname=new JTextField();//���塰������ơ�JTextField
	         jetcgname.setBounds(tleftwidth, 3*topheight+2*lheight, twidth, theight);//����λ�����С
	         jp.add(jetcgname);//��JTextField��ӵ�JPanel��
         
	         ////��������
	         JLabel jlmcg=new JLabel("�������ࣺ");//��������JLabel
	         jlmcg.setFont(font);//��������
	         jlmcg.setBounds(leftwidth, 4*topheight+3*lheight,lwidth,lheight);//����λ�����С
			 jp.add(jlmcg);//��JLabel��ӵ�JPanel��
			 final JComboBox jcbmcg=new JComboBox(mcgstr);//���塰�������ࡱJComboBox
			 jcbmcg.setBounds(tleftwidth, 4*topheight+3*lheight, twidth, theight);//����λ�����С
	         jp.add(jcbmcg);//��JComboBox��ӵ�JPanel��
         
	         //��ӵ����пؼ�
	         //��Ӱ�ť�ؼ�
	         JButton jbok=new JButton("���");//������Ӱ�ť
	         jbok.setFont(font);//��������
	         jp.add(jbok);//����Ӱ�ť��ӵ�JPanel��
	         jbok.setBounds(width/2/4,  6*topheight+lheight*4, lwidth, theight);//����λ�����С
	         jbok.addActionListener//��Ӱ�ť��Ӽ���
	         (
	            new ActionListener()
	            {
					@SuppressWarnings({ "unchecked", "static-access" })
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if(jetcgname.getText().equals(""))
						{
							JOptionPane.showMessageDialog(addCateGory.this, "��Ʒ���Ʋ���Ϊ�գ�������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						else 
						{
							String cgname=jetcgname.getText().toString();
							String cgmcg=jcbmcg.getSelectedItem().toString();
							String time=TypeExchangeUtil.gettime();
							//��Ӳ�Ʒ���
							String msg=ADD_CG+strinfo+ADD_CG+cgname+
							ADD_CG+time+ADD_CG+time+ADD_CG+cgmcg;
							SocketClient.ConnectSevert(msg);//����Ʒ�����Ϣ����������
							String readinfo=SocketClient.readinfo;
							
							if(readinfo.equals("ok"))
							{
								JOptionPane.showMessageDialog(addCateGory.this, "��Ʒ�����ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
								SocketClient.ConnectSevert(GET_CG);//��ò�Ʒ�����Ϣ
								String getinfo=SocketClient.readinfo;
								title=new Vector();//����
								{
									title.add("���ID");title.add("�����");title.add("����");
									title.add("�ؼ���");title.add("����ʱ��");title.add("���༭ʱ��");
		                            title.add("��������");
								}
								if(getinfo.equals("fail"))
								{
		                        	JOptionPane.showMessageDialog(addCateGory.this, "��ȡ��Ϣʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
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
								addCateGory.this.setVisible(false);//��������治�ɼ�
							}
							else
							{
								JOptionPane.showMessageDialog(addCateGory.this, "��Ʒ������ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
								addCateGory.this.setVisible(false);
							}
					  }
				}
            }
         );
         //ȡ����ť�ؼ�
         JButton jbfali=new JButton("ȡ��");
         jbfali.setFont(font);//��������
         jp.add(jbfali);//��ӵ�Jpanel��
         jbfali.setBounds(width/2+width/2/4, 6*topheight+lheight*4, lwidth, theight);//����λ�����С
         //ȡ����ť����
         jbfali.addActionListener
         (
           new ActionListener()
           {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				jetcgname.setText("");
			}
           }
         );
         this.add(jp);//��JPanel��ӵ�������
         this.setVisible(true);//���ý���Ϊ�ɼ�
         this.setEnabled(true);//���ý���Ϊ����        
         this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
 		 this.setResizable(false);//���ý���ߴ�̶�
 		 this.setTitle("��Ӳ�Ʒ���");//���ý������
 		 Image image=this.getToolkit().getImage("src/com/bn/img/tb1.jpg");//����ͼƬ
		 this.setIconImage(image);//���ý���ͼ��
					          
		}
   }
}
