package com.bn.vege;

import static com.bn.constant.Constant.GET_VEGEIMAGEMAXNO;
import static com.bn.constant.Constant.INSERT_IMAGE;
import static com.bn.constant.Constant.UPLOAD_IMAGE;
import static com.bn.constant.Constant.UPLOAD_ZIMAGE;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;
/*����Ӳ�Ʒ��ʱ����Ӳ�Ʒ��ͼ*/
@SuppressWarnings("serial")
public class AddChildImage extends JFrame
{
	MainUI mainui;
	DefaultMutableTreeNode curenode=Constant.vegenode[5];//��Ʒ��Ϣ�ڵ�
    int width=560;//����ߴ�
    int height=700;
    Font font=new Font("����",Font.BOLD,14);//������������
    Font fonttitle=new Font("����",Font.BOLD,20);
    List<File> imagelist=new ArrayList<File>();
    String vegeId;
    static int length;
    public AddChildImage(final MainUI mainui, final String vegeId)//�����ͼ����
    {
	   this.mainui=mainui;
	   this.vegeId=vegeId;
	   final JPanel jp=new JPanel();//����JPanel
	   jp.setLayout(null);//����JPanel������ΪĬ��
	   this.setBounds(200, 0, 560, 700);//����JPanel��λ�����С
	    //��߾�
		 int leftwidth=width/30;
		 //�ߵľ���
		 int topheight=height/90;
		 //jlabel�Ŀ��
		 int lwidth=width/2/3;
		 //JLabel�ĸ߶�
		 int lheight=height/22;
		 //�Ź���֮��ľ���
		 int span=width/100;
		 
		 int vigwidth=510;
		 int vigheight=540;
		 
		 
		 final VegeImageGrid vig =new VegeImageGrid(vigwidth,vigheight,span,imagelist);
	     JScrollPane jsp=new JScrollPane();//���������
	     jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //�������������
	     jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//���ú��������
	     jsp.setAutoscrolls(true);
	     jsp.setViewportView(vig);//����������ӵ�vig
	     jsp.setBounds(leftwidth, 2*topheight+2*lheight, vigwidth, vigheight);//���ù�������λ�����С
	     jp.add(jsp);//����������ӵ�JPanel
		 
		 JLabel jltitle=new JLabel("��Ӳ�Ʒ��ͼ");//���ñ���
		 jltitle.setFont(fonttitle);//���ñ�������
		 jltitle.setBounds(width/2-width/2/4, topheight, 2*lwidth, lheight);//���ñ���λ�����С
		 jp.add(jltitle);//��������ӵ�JPanel��
		 
		 final JButton jbselect=new JButton("ѡ��ͼƬ");//���ð�ť
         jbselect.setFont(font);//���ð�ť����
         jp.add(jbselect);//����ť��ӵ�JPanel��
         jbselect.setBounds(leftwidth, topheight+lheight, lwidth, lheight);//���ð�ťλ�����С
         jbselect.addActionListener//��ť��Ӽ���
         (
        	 new ActionListener()
        		 {
						@SuppressWarnings("unused")
						@Override
					public void actionPerformed(ActionEvent e) 
						{
							JFileChooser jfc=new JFileChooser("c:\\");
							jfc.showOpenDialog(jbselect);//�����ļ�ѡ����
							jfc.setCurrentDirectory(null);//���õ�ǰĿ¼Ϊ��
							File file=jfc.getSelectedFile();//����ѡ���ļ�
							String path=file.getAbsolutePath().toString();//����·��
							 if(!(file.exists() && file.isFile() && file.getName().endsWith(".jpg")))
							 {  
								 JOptionPane.showMessageDialog(AddChildImage.this, "ѡ���ļ�����������ѡ��һ����ȷ���ļ���","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						     } 
							 else
							 {   //�ļ���ӵ�imagelist
								 imagelist.add(file);
								 VegeImageGrid.curfile=file;
								 VegeImageGrid.imageList=imagelist;
								 //�ػ�
								 vig.drawImageGrid();
								 jp.updateUI();
							 }
						  }	
					}		 
                 );
         //�ϴ��ؼ�
         final JButton jbupload=new JButton("�ϴ�");//���尴ť
         jbupload.setFont(font);//���ð�ť����
         jp.add(jbupload);//����ť��ӵ�JPanel��
         jbupload.setBounds(width/2+width/4, topheight+lheight, lwidth, lheight);//���ð�ť��λ�����С
         jbupload.addActionListener//��ť��Ӽ���
         (
           new ActionListener()
            {
				@SuppressWarnings({ "unchecked", "static-access" })
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					int num=0;
					for(int i=0;i<imagelist.size();i++)
					{
						byte[] imagemsg=TypeExchangeUtil.filetobyte(imagelist.get(i));//�õ�ͼƬ
						SocketClient.ConnectSevertBYTE(UPLOAD_IMAGE ,UPLOAD_ZIMAGE, imagemsg);
						String readinfo=SocketClient.readinfo;
						if(!readinfo.equals(null))//���Ϊ��
						{
							//�õ������
							SocketClient.ConnectSevert(GET_VEGEIMAGEMAXNO);
							String index=SocketClient.readinfo;
							String strinfo=((Integer.parseInt(index)+1)<10)?("00"+(Integer.parseInt(index)+1)):
								(((Integer.parseInt(index)+1)<100)?("0"+(Integer.parseInt(index)+1)):((Integer.parseInt(index)+1)+""));
				    		SocketClient.ConnectSevert(INSERT_IMAGE+strinfo+INSERT_IMAGE+vegeId+INSERT_IMAGE+readinfo+INSERT_IMAGE+"0");
				    		String info=SocketClient.readinfo;
					    	if(info.equals("fail"))//������ء�fail��,����ʾ��Ϣ"��Ʒ���ʧ��"
					    	{
	                        	JOptionPane.showMessageDialog(AddChildImage.this, "��Ʒ���ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
							}
					    	else
					    	{
					    		num++;
					    	}
			    	    }
					}
				  if(num>=imagelist.size())
					  {
						JOptionPane.showMessageDialog(AddChildImage.this, "��Ʒ��ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				    	SocketClient.ConnectSevert(Constant.GET_USEVEGE);
				    	String getinfo=SocketClient.readinfo;
						@SuppressWarnings("rawtypes")
						Vector title=new Vector();//����
						{
							title.add("��ƷID");
							title.add("��Ʒ����");
							title.add("�۸�");
							title.add("����");
							title.add("������λ");
							title.add("��Ʒ���");
							title.add("��Ʒ����");
							title.add("ϵ��");
							title.add("���");
						}
						if(getinfo.equals("fail"))
						{
	                    	JOptionPane.showMessageDialog(AddChildImage.this, "��ȡ��Ϣʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{   //���õ������ݡ����⴫��createJTable������
							Vector<Vector<String>> data=TypeExchangeUtil.strToVector(getinfo);
							int topwidth=(int) (768*0.07);
							int midwidth=(int) ((1024-1024*0.25)*0.78);
							int buttomheight=(int) (768*0.6);
							mainui.createJTable(data,title,20,topwidth,midwidth,buttomheight);
							mainui.createRight(curenode);
						}
						AddChildImage.this.setVisible(false);//�����ͼ��������Ϊ���ɼ�
					}		
				  }
			}
         );
        this.add(jp);//��JPanel��ӵ�������
		this.setResizable(false);//���ý���Ϊ�̶��ߴ�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
		this.setBounds(200, 0, 560, 700);//���ý����λ�����С
		this.setVisible(true);//���ý���Ϊ�ɼ�
		this.setTitle("��Ʒ�༭");//���ý������
    }
}
