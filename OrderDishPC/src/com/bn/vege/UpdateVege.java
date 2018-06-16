package com.bn.vege;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static com.bn.util.TypeExchangeUtil.*;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;

import static com.bn.constant.Constant.*;
/*�޸Ĳ�Ʒ�Ľ���*/
@SuppressWarnings("serial")
public class UpdateVege extends JFrame
{
	StringBuilder yysb=new StringBuilder();//���������ַ���
	StringBuilder clsb=new StringBuilder();
	JLabel jtitle=new JLabel("�༭��Ʒ");//�������
	Font font=new Font("����",Font.BOLD,22);//��������
	DefaultMutableTreeNode curenode=Constant.vegenode[5];//��ǰ�ڵ�Ϊ��Ʒ����
	int width;//���ÿ��
	int height;//���ø߶�
	JPanel jp=new JPanel();//����JPanel
	 byte[] imagebyte;
	 MainUI mainui;
	@SuppressWarnings({ "rawtypes", "unused" })
	public UpdateVege(final MainUI mainui,final String[] s)//���²�Ʒ����
	{
		Image image=this.getToolkit().getImage("src/com/bn/img/kt.jpg");//����ͼƬ
		this.setIconImage(image);//���ý���ͼ��
		this.mainui=mainui;
		width=560;//���ÿ��
		height=710;//���ø߶�
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
		 int twidth=width/4;
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
        //�õ�������λ������
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
		 
		 jp.setLayout(null);
		   //��һ�пؼ�
		   JLabel jlid=new JLabel();//�����Ʒid JLabel
		   jlid.setText("�� Ʒ id��");//����JLabel��ֵ
		   jlid.setBounds(leftwidth, topheight, lwidth, lheight);//����λ�����С
		   jp.add(jlid);//��JLabel��ӵ�JPanel��
		   JLabel jlidshow=new JLabel();//���塰��Ʒid��JLabel
		   jlidshow.setText(s[0]);//����JLabel��ֵ
		   jlidshow.setBounds(tleftwidth, topheight, twidth, lheight);//����JLabelλ�����С
		   jp.add(jlidshow);//��JLabel��ӵ�JPanel��
           //�ڶ��пؼ�
		   JLabel jlname=new JLabel();//��������JLabel
		   jlname.setText("��Ʒ���ƣ�");//����JLabel��ֵ
		   jlname.setBounds(leftwidth, topheight*2+lheight, lwidth, lheight);//����λ�����С
		   jp.add(jlname);//��JLabel��ӵ�JPanel��
		   final JTextField jetname=new JTextField();//���塰��Ʒ���ơ�JTextField
		   jetname.setText(s[1]);//����JTextField��ֵ
		   jetname.setBounds(tleftwidth, topheight*2+lheight, twidth+leftwidth, lheight);//����λ�����С
		   jp.add(jetname);//��JTextField��ӵ�JPanel��
		   
		   JLabel jlcg=new JLabel();//�������JLabel
		   jlcg.setText("�������");//����JLabel��ֵ
		   jlcg.setBounds(rightwidth, topheight*2+lheight, lwidth, lheight);//����λ�����С
		   jp.add(jlcg);//��JLabel��ӵ�JPanel��
		   final JComboBox jcbcg=new JComboBox(cgstr);//���塰�������JComboBox
		   jcbcg.setSelectedItem(s[4]);//����JComboBox��ѡ��
		   jcbcg.setBounds(trightwidth, topheight*2+lheight, twidth, lheight);//����λ�����С
		   jp.add(jcbcg);//��JCombobox��ӵ�JPanel��
		   
		 //�����пؼ�
		   JLabel jlprice=new JLabel();//����۸�JLabel
		   jlprice.setText("��Ʒ�۸�");//����JLabel��ֵ
		   jlprice.setBounds(leftwidth, topheight*3+lheight*2, lwidth, lheight);//����λ�����С
		   jp.add(jlprice);//��JLabel��ӵ�JPanel��
		   final JTextField jetprice=new JTextField();//���塰��Ʒ�۸�JTextField
		   jetprice.setText(s[2]);//����JTextField��ֵ
		   jetprice.setBounds(tleftwidth, topheight*3+lheight*2, twidth+leftwidth, lheight);//����λ�����С
		   jp.add(jetprice);//��JTextField��ӵ�JPanel��
		   
		    JLabel jlcu=new JLabel();//���������λJLabel
		   jlcu.setText("������λ��");//����JLabel��ֵ
		   jlcu.setBounds(rightwidth, topheight*3+lheight*2, lwidth, lheight);//����λ�����С
		   jp.add(jlcu);//��JLabel��ӵ�JPanel��
		   final JComboBox jcbcu=new JComboBox(custr);//���塰������λ��JComboBox
		   jcbcu.setSelectedItem(s[3]);//����JComboBox��ѡ��
		   jcbcu.setBounds(trightwidth, topheight*3+lheight*2, twidth, lheight);//����λ�����С
		   jp.add(jcbcu);//��JComboBox��ӵ�JPanel��
		   
		 //�����пؼ�
		   JLabel jlgg=new JLabel();//����JLabel
		   jlgg.setText("��    ��");//����JLabel��ֵ
		   jlgg.setBounds(leftwidth, topheight*4+lheight*3, lwidth, lheight);//����λ�����С
		   jp.add(jlgg);//��JLabel��ӵ�JPanel��
		   final JComboBox jcbgg=new JComboBox(ggstr);//���塰���JComboBox
		   jcbgg.setSelectedItem(s[7]);//����JComboBox��ֵ
		   jcbgg.setBounds(tleftwidth, topheight*4+lheight*3, twidth, lheight);//����λ�����С
		   jp.add(jcbgg);//��JComboBox��ӵ�JPanel��
		   
		   JLabel jlvt=new JLabel();//����JLabel
		   jlvt.setText("����ϵ��");//����JLabel��ֵ
		   jlvt.setBounds(rightwidth, topheight*4+lheight*3, lwidth, lheight);//����λ�����С
		   jp.add(jlvt);//��JLabel��ӵ�JPanel��
		   final JComboBox jcbvt=new JComboBox(vtstr);//���塰����ϵ��JComboBox
		   jcbvt.setSelectedItem(s[6]);//����JComboBox��ֵ
		   jcbvt.setBounds(trightwidth, topheight*4+lheight*3, twidth, lheight);//����λ�����С
		   jp.add(jcbvt);//��JComboBox��ӵ�JPanel��
		     
		   //�����пؼ�
	       JLabel jlintro=new JLabel();//�������JLabel
	  	   jlintro.setText("��Ʒ���ܣ�");//���ñ���
	  	   jlintro.setBounds(leftwidth, topheight*7+lheight*6, lwidth, lheight);//����λ�����С
	  	   jp.add(jlintro);//��JLabe��ӵ�JPanel��
	  	   final JTextArea jtajs=new JTextArea();//���塰��Ʒ���ܡ�JTextArea
	  	   JScrollPane jspintro=new JScrollPane(jtajs);//���������������ӵ�JTextArea��
		   jtajs.setLineWrap(true);//����JTextAreaΪ�Զ�����
		   jtajs.setText(s[8]);//����JTextArea��ֵ
	  	   jspintro.setBounds(tleftwidth,topheight*7+lheight*6, twidth*3, 3*lheight);//����λ�����С
	  	   jp.add(jspintro);//����������ӵ�JPanel��

	       JButton jbok=new JButton("��һ��");//������һ����ť
	       jbok.setBounds(6*leftwidth+2*lwidth, topheight*10+lheight*11, lwidth+leftwidth, lheight);
	       jp.add(jbok);//����ť��ӵ�JPanel��
		    jbok.addActionListener//��ť��Ӽ���
		    (
		       new ActionListener()
		       {
					@Override
					public void actionPerformed(ActionEvent e)
					{
						Pattern pattern = Pattern.compile("[0-9]+.?[0-9]*");//����ʽ��� 
						if(jetname.getText().equals(""))
						{
							JOptionPane.showMessageDialog(UpdateVege.this, "��Ʒ���Ʋ���Ϊ�գ�������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						else if(jetprice.getText().toString().equals(""))
						{
							JOptionPane.showMessageDialog(UpdateVege.this, "��Ʒ�۸���Ϊ�գ�������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						else if(!pattern.matcher(jetprice.getText().toString()).matches())
						{
							JOptionPane.showMessageDialog(UpdateVege.this, "��Ʒ�۸��ʽ���벻��ȷ��������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
						String vegename=jetname.getText().toString();
						String vegejg=jetprice.getText();
						String vegecu=jcbcu.getSelectedItem().toString();
						String vegegg=jcbgg.getSelectedItem().toString();
						String vegecg=jcbcg.getSelectedItem().toString();
						String vegevt=jcbvt.getSelectedItem().toString();
						String vegeinfo=jtajs.getText().toString();

						//���²�Ʒ
						String sendmsg=UPDATE_VEGE+s[0]
						+UPDATE_VEGE+vegename						
						+UPDATE_VEGE+vegejg						
						+UPDATE_VEGE+vegecu
						+UPDATE_VEGE+vegecg
						+UPDATE_VEGE+vegevt
						+UPDATE_VEGE+vegegg
						+UPDATE_VEGE+vegeinfo;
						SocketClient.ConnectSevert(sendmsg);//����Ʒ��Ϣ����������
						String readinfo=SocketClient.readinfo;
					    if(readinfo.equals("ok"))
					    {
					    	//�޸ĳɹ��������һ���棬��ͼƬ�����޸�
					    	UpdateVegeImage uvi=new UpdateVegeImage(mainui,s[0]);
					    	uvi.setVisible(true);
					    	UpdateVege.this.setVisible(false);//���²�Ʒ���治�ɼ�
					    }else
					    {
					    	JOptionPane.showMessageDialog(UpdateVege.this, "��Ʒ�޸�ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					    	UpdateVege.this.setVisible(false);
					    }
					}
				}  
		    }
		  );
		       
       JButton jbback=new JButton("ȡ��");//���塰ȡ������ť
       jbback.setBounds(8*leftwidth+3*lwidth,topheight*10+lheight*11, lwidth+leftwidth, lheight);
       jp.add(jbback);//����ť��ӵ�JPanel��
       jbback.addActionListener//��ť��Ӽ���
       (
    	  new ActionListener()
    	  {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				UpdateVege.this.setVisible(false);//���²�Ʒ����ر�
			}
    	  }
       );
        this.add(jp);//��JPanel��ӵ�������
		this.setResizable(false);//����������Ϊ�̶��ߴ�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
		this.setBounds(200, 0, 560, 450);//����λ�����С
		this.setVisible(true);//��������Ϊ�ɼ�
		this.setTitle("��Ʒ�༭");//���ý������		
	}
}
