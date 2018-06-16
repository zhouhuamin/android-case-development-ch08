package com.bn.vege;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import com.bn.constant.Constant;
import com.bn.util.SocketClient;

import static com.bn.constant.Constant.*;

@SuppressWarnings("serial")
public class VegeInfo extends JFrame{
	JLabel jtitle=new JLabel("��Ʒ����");//����JLabel
	Font font=new Font("����",Font.BOLD,22);//��������
	DefaultMutableTreeNode curenode=Constant.vegenode[5];//��ǰ�ڵ�Ϊ��Ʒ����
	int width;//������
	int height;//����߶�
	JPanel jp=new JPanel();//����JPanel
	 byte[] imagebyte;
	@SuppressWarnings({ "unused" })
	public VegeInfo(final String[] s) {
		
		Image image=this.getToolkit().getImage("src/com/bn/img/kt.jpg");//����ͼƬ
		this.setIconImage(image);//���ý���ͼ��
		width=560;//������ֵ
		height=710;//����߶�ֵ
		 //��߾�
		 int leftwidth=width/35;
		 //�ߵľ���
		 int topheight=height/90;
		 //�ұ߾�
		 int rightwidth=width/2+leftwidth;
		 //jlabel�Ŀ��
		 int lwidth=width/2/3;
		 //JLabel�ĸ߶�
		 int lheight=height/26;
		 //text�Ŀ��
		 int twidth=width/5;
		 //text�ĸ߶�
		 int theight=height/26;
		 //�õ����text��ߵı߾�
		 int tleftwidth=leftwidth+lwidth;
		 //�õ��ұ�text��ߵı߾�
		 int trightwidth=rightwidth+lwidth;
         //jep�Ŀ��
         int jepwidth=width/2-leftwidth*2/4;
         //jep�ĸ߶�
         int jepheight=5*lheight+4*topheight; 
		                      
		   jp.setLayout(null);//����JPanel������ΪĬ��
	       //��һ�пؼ�
	       JLabel jlid=new JLabel();//����JLabel
	       jlid.setText("�� Ʒ id��");//����JLabel��ֵ
	       jlid.setBounds(leftwidth, topheight, lwidth, lheight);//����λ�����С
		   jp.add(jlid);//��JLabel��ӵ�JPanel��
		   JTextField jlidshow=new JTextField();//���塰��Ʒid��JTextField
		   jlidshow.setEditable(false);//����Ϊ���ɱ༭
		   jlidshow.setText(s[0]);//����JTextField��ֵ
		   jlidshow.setBounds(leftwidth+lwidth, topheight, twidth, lheight);//����λ�����С
		   jp.add(jlidshow);//��JTextField��ӵ�JPanel��

		   //�ڶ��пؼ�
		   JLabel jlprice=new JLabel();//����JLabel
		   jlprice.setText("��Ʒ�۸�");//����JLabel��ֵ
		   jlprice.setBounds(leftwidth, topheight*2+lheight, lwidth, lheight);//����λ�����С
		   jp.add(jlprice);//��JLabel��ӵ�Jpanel��
		   final JTextField jetprice=new JTextField();//���塰��Ʒ�۸�JTextField
		   jetprice.setText(s[2]);//����JTextField��ֵ
		   jetprice.setEditable(false);//����Ϊ���ɱ༭
		   jetprice.setBounds(leftwidth+lwidth, topheight*2+lheight, twidth, lheight);//����λ�����С
		   jp.add(jetprice);//��JTextField��ӵ�JPanel��
		   
		   //�����пؼ�
		   JLabel jlcg=new JLabel();//����JLabel
		   jlcg.setText("�������");//����JLabel��ֵ
		   jlcg.setBounds(leftwidth, topheight*3+lheight*2, lwidth, lheight);//����λ�����С
		   jp.add(jlcg);//��JLable��ӵ�JPanel��
		   final JTextField jcbcg=new JTextField();//���塰�������JTextField
		   jcbcg.setText(s[4]);//����JTextField��ֵ
		   jcbcg.setEditable(false);//����Ϊ���ɱ༭
		   jcbcg.setBounds(leftwidth+lwidth, topheight*3+lheight*2, twidth, lheight);//����λ�����С
		   jp.add(jcbcg); //��JTextField��ӵ�JPanel��
		   		   
		   //�����пؼ�
		   JLabel jlcu=new JLabel();//����JLabel
		   jlcu.setText("������λ��");//����JLabel��ֵ
		   jlcu.setBounds(leftwidth, topheight*4+lheight*3, lwidth, lheight);//����λ�����С
		   jp.add(jlcu);//��JLabel��ӵ�JPanel��
		   final JTextField jcbcu=new JTextField();//���塰������λ��JTextField
		   jcbcu.setText(s[3]);//����JTextField��ֵ
		   jcbcu.setEditable(false);//����Ϊ���ɱ༭
		   jcbcu.setBounds(leftwidth+lwidth, topheight*4+lheight*3, twidth, lheight);//����λ�����С
		   jp.add(jcbcu);//��  JTextField��ӵ�JPanel��
		   
		   //�����пؼ�
		   JLabel jlvt=new JLabel();//����JLabel
		   jlvt.setText("����ϵ��");//����JLabel��ֵ
		   jlvt.setBounds(leftwidth, topheight*5+lheight*4, lwidth, lheight);//����λ�����С
		   jp.add(jlvt);//��JLabel��ӵ�JPanel��
		   final JTextField jcbvt=new JTextField();//���塰����ϵ��JTextField
		   jcbvt.setText(s[6]);//����JTextField��ֵ
		   jcbvt.setEditable(false);//����Ϊ���ɱ༭
		   jcbvt.setBounds(leftwidth+lwidth, topheight*5+lheight*4, twidth, lheight);//����λ�����С
		   jp.add(jcbvt);//��JTextField��ӵ�JPanel
		   
		   //�����пؼ�
		   JLabel jlgg=new JLabel();//����JLabel
		   jlgg.setText("��    ��");//����JLabel��ֵ
		   jlgg.setBounds(leftwidth, topheight*6+lheight*5, lwidth, lheight);//����λ�����С
		   jp.add(jlgg);//��JLabel��ӵ�JPanel��
		   final JTextField jcbgg=new JTextField();//���塰���JTextField
		   jcbgg.setText(s[7]);//����JTextField��ֵ
		   jcbgg.setEditable(false);//����Ϊ���ɱ༭
		   jcbgg.setBounds(leftwidth+lwidth, topheight*6+lheight*5, twidth, lheight);//����λ�����С
		   jp.add(jcbgg);//��JTextField��ӵ�JPanel
		   
           //�����пؼ�
		   JLabel jlintro=new JLabel();//����JLabel
		   jlintro.setText("��Ʒ���ܣ�");//����JLabel��ֵ
		   jlintro.setBounds(leftwidth, topheight*7+lheight*6, lwidth, lheight);//����λ�����С
		   jp.add(jlintro);//��JLabel��ӵ�JPanel��
		   final JTextArea jtajs=new JTextArea();//���塰��Ʒ���ܡ�JTextArea
		   JScrollPane jspintro=new JScrollPane(jtajs);//���������������ӵ�JTextArea��
		   jtajs.setLineWrap(true);//����Ϊ�Զ�����
		   jtajs.setEditable(false);//����Ϊ���ɱ༭
		   jtajs.setText(s[8]);//����JTextField��ֵ
		   jspintro.setBounds(tleftwidth,topheight*7+lheight*6, twidth*2, 6*lheight);//����λ�����С
		   jp.add(jspintro);//��JTextField��ӵ�JPanel��
		 		     
		  	JSplitPane jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//���ú���ָ���
	        jsp.setDividerSize(4);//���÷ָ����ߴ�
	        jsp.setDividerLocation(350);//���÷ָ���λ��
	        jsp.setLeftComponent(jp);//��JPanel���õ��ָ��������
	          
	         List<byte[]> imagelist=new ArrayList<byte[]>();
	         //���ݲ�Ʒid�õ���Ʒ��ͼ�Ĵ�ͼ
	  		 SocketClient.ConnectSevert(GET_MBIMAGE+s[0]);
	  		 final String vegemainbigpath=SocketClient.readinfo;
	  		 SocketClient.ConnectSevert(GET_IMAGE+vegemainbigpath);
	  		 byte mainbigimagemsg[] =SocketClient.data;
	  		 imagelist.add(mainbigimagemsg);
	  		 
	  		 //���ݲ�Ʒid�õ���Ʒ��ͼƬ·��
			 SocketClient.ConnectSevert(GET_ZBIMAGE+s[0]);
			 String vegechildpath=SocketClient.readinfo;
		     if(!vegechildpath.equals(""))
		     {
			   String[] path=vegechildpath.split(",");
		       for(int i=0;i<path.length;i++)
			    {
				   SocketClient.ConnectSevert(GET_IMAGE+path[i]);
				   byte imagemsg[] =SocketClient.data;
				   //�������ͼ�Ĵ�ͼ���־λ1�������־λ0 
				   imagelist.add(imagemsg);
			    }
		     }
		     
		     JScrollPane jspr=new JScrollPane();//���������
		     jspr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//�������������
		     jspr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//���ú��������
		     jspr.setAutoscrolls(true);
		     JPanel jpr=new JPanel();//����JPanel
		     jpr.setLayout(null);//����JPanel������ΪĬ��
		     int span=5;
		     int unitWidth=350;
		     int unitHeight=300;
	
		     for(int i=0;i<imagelist.size();i++)
				{
					JButton jb=new JButton();//����JButton
					jb.setIcon(new ImageIcon(imagelist.get(i)));//���ð�ťͼ��
					jb.setLayout(null);//���ð�ť������ΪĬ��
					if(i==0)
					{   //���ð�ťλ�����С
						jb.setBounds(span, span+i*(unitHeight+span-unitHeight/9), unitWidth-unitWidth/9, unitHeight-unitHeight/9);
						JLabel jl=new JLabel();//����JLabel
						jl.setIcon(new ImageIcon("src/com/bn/image/flag.png"));//����JLabelͼ��
						jl.setBounds(0, 0, unitWidth/4, unitHeight/6);//����λ�����С
						jb.add(jl);//JLabel��ӵ�JButton��
					}
					else
					{
						jb.setBounds(span, span+i*(unitHeight+span-unitHeight/9), unitWidth-unitWidth/9, unitHeight-unitHeight/9);
					}
					jpr.add(jb);//JButton��ӵ�JPanel��
				}
		        int length=span+imagelist.size()*(unitHeight+span-unitHeight/10);
		        jpr.setPreferredSize(new Dimension(400, length));
		        jspr.setViewportView(jpr);//����������ӵ�JPanel��
		        jsp.setRightComponent(jspr);//����������ӵ��ָ����ұ�
		        this.add(jsp);//���ָ�����ӵ�������
				this.setResizable(false);//���ý���̶��ߴ�
				this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
				this.setBounds(200, 0, 700, 500);//���ý���λ�����С
				this.setVisible(true);//���ý���ɼ�
				this.setTitle("��Ʒ����");//���ý������
	}
}