package com.bn.vege;

import static com.bn.constant.Constant.*;

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
/*�޸Ĳ�Ʒ��ͼƬ�Ľ���*/
@SuppressWarnings("serial")
public class UpdateVegeImage extends JFrame
{
	MainUI mainui;
	DefaultMutableTreeNode curenode=Constant.vegenode[5];//��ǰ�ڵ�Ϊ��Ʒ����
    int width=560;//������
    int height=700;//����߶�
    Font font=new Font("����",Font.BOLD,14);//������������
    Font fonttitle=new Font("����",Font.BOLD,20);
    
    String vegeId;
    List<byte[]> imagelist=new ArrayList<byte[]>();
    int mainindex;
    int mainpathlength;
    @SuppressWarnings("unused")
	public UpdateVegeImage(final MainUI mainui, final String vegeId)
    {
	   this.mainui=mainui;
	   this.vegeId=vegeId;
	   final JPanel jp=new JPanel();//����JPanel
	   jp.setLayout(null);//����JPanel������ΪĬ��
	   this.setBounds(200, 0, 560, 700);//����λ�����С
	    //��߾�
		 int leftwidth=width/30;
		 //�ߵľ���
		 int topheight=height/90;
		 //JLabel�Ŀ��
		 int lwidth=width/2/3;
		 //JLabel�ĸ߶�
		 int lheight=height/22;
		 //�Ź���֮��ľ���
		 int span=width/100;
		 
		 int vigwidth=510;
		 int vigheight=540;
		 //���ݲ�Ʒid�õ���Ʒ��ͼ�Ĵ�ͼ
		 SocketClient.ConnectSevert(GET_MBIMAGE+vegeId);
		 final String vegemainbigpath=SocketClient.readinfo;
		 SocketClient.ConnectSevert(GET_IMAGE+vegemainbigpath);
		 byte mainbigimagemsg[] =SocketClient.data;
		 imagelist.add(mainbigimagemsg);
		 mainindex=0;
		 
		//���ݲ�Ʒid�õ���Ʒ��ͼ·��
		 SocketClient.ConnectSevert(GET_W_PICBYVEGEID+vegeId);
		 final String vegemainpath=SocketClient.readinfo;
		 SocketClient.ConnectSevert(GET_IMAGE+vegemainpath);
		 byte mainimagemsg[] =SocketClient.data;
		 
		 //���ݲ�Ʒid�õ���Ʒ��ͼƬ·��
		 SocketClient.ConnectSevert(GET_ZBIMAGE+vegeId);
		 String vegechildpath=SocketClient.readinfo;
	     final List<String> childpath=new ArrayList<String>();
	     if(vegechildpath.equals(""))
	     {
	    	 mainpathlength=1;
	     }
	     else
	     {
			 String[] path=vegechildpath.split(",");
			 mainpathlength=path.length+1;//��¼�ʼͼƬ�ĸ���
		     for(int i=0;i<path.length;i++)
			 {
		    	 childpath.add(path[i]);
				 SocketClient.ConnectSevert(GET_IMAGE+childpath.get(i));
				 byte imagemsg[] =SocketClient.data;
				 //�������ͼ�Ĵ�ͼ���־λ1�������־λ0 
				 imagelist.add(imagemsg);
			 }
	     }
		 //����UpdataImageGird���������²�ƷͼƬ
		 final UpdateImageGrid uig =new UpdateImageGrid(vigwidth,vigheight,span,imagelist,mainindex);
	     JScrollPane jsp=new JScrollPane();//���������
	     jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//������������� 
	     jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//���ú��������
	     jsp.setAutoscrolls(true);//����Ϊ�Զ������ߴ�
	     jsp.setBounds(leftwidth, 2*topheight+2*lheight, vigwidth, vigheight);
	     jsp.setViewportView(uig);//���������ŵ����ͼƬ����
	     jp.add(jsp);//����������ӵ�JPanel��

		 JLabel jltitle=new JLabel("�޸Ĳ�ƷͼƬ");//����JLabel
		 jltitle.setFont(fonttitle);//��������
		 jltitle.setBounds(width/2-width/2/4, topheight, 2*lwidth, lheight);//���ñ���λ�����С
		 jp.add(jltitle);//��������ӵ�JPanel��
		 
		 final JButton jbselect=new JButton("���ͼƬ");//����JButton
         jbselect.setFont(font);//��������
         jp.add(jbselect);//��JButton��ӵ�JPanel��
         jbselect.setBounds(leftwidth, topheight+lheight, lwidth, lheight);//���ð�ťλ�����С
         jbselect.addActionListener//��ť��Ӽ���
         (
        	new ActionListener()
        	{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					JFileChooser jfc=new JFileChooser("c:\\");
					jfc.showOpenDialog(jbselect);//�����ļ�ѡ����
					jfc.setCurrentDirectory(null);
					File file=jfc.getSelectedFile();
					 if(!(file.exists() && file.isFile() && file.getName().endsWith(".jpg"))) 
					 {  
						 JOptionPane.showMessageDialog(UpdateVegeImage.this, "ѡ���ļ�����������ѡ��һ����ȷ���ļ���","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				     } 
					 else
					 {
						 //��ͼƬ����Ź�����
						 byte[] imagemsg=TypeExchangeUtil.filetobyte(file);
						 imagelist.add(imagemsg);
						 UpdateImageGrid.imagemsg=imagemsg;
						 //�ػ�
						 uig.drawImageGrid();
						 jp.updateUI();
					 }
				  }	
				}		 
           );
         //�ϴ��ؼ�
         final JButton jbupload=new JButton("����");
         jbupload.setFont(font);//��������
         jp.add(jbupload);//����ť��ӵ�JPanel��
         jbupload.setBounds(width/2+width/4, topheight+lheight, lwidth, lheight);//����λ�����С
         jbupload.addActionListener//��ť��Ӽ���
         (
           new ActionListener()
           {
			@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//�����ͼ��Сͼ
				if(mainindex!=uig.mainindex)
				{
	    			//�����µ���ͼ���������
	    			byte[] imagemsg=imagelist.get(uig.mainindex);
	    			SocketClient.ConnectSevertBYTE(UPLOAD_IMAGE, UPLOAD_MAIN_VEGEIAMGE, imagemsg);
                    String imagepth=SocketClient.readinfo;
                    
                    //���µ���ͼ�����¼��
                    SocketClient.ConnectSevert(GET_VEGEIMAGEMAXNO);
                    String index=SocketClient.readinfo;
				    String strinfo=((Integer.parseInt(index)+1)<10)?("00"+(Integer.parseInt(index)+1)):
					(((Integer.parseInt(index)+1)<100)?("0"+(Integer.parseInt(index)+1)):((Integer.parseInt(index)+1)+""));
				   
				   SocketClient.ConnectSevert(UPDATE_MAINIMAGE+strinfo+UPDATE_MAINIMAGE+vegeId+UPDATE_MAINIMAGE+imagepth+UPDATE_MAINIMAGE+"1");
				   String info=SocketClient.readinfo;
                   //������Ѿ����ڵ���ͼ��ֻ��Ҫ�޸ı�־λ
				   if(uig.mainindex<mainpathlength)
				   {
					 String path=childpath.get(uig.mainindex-1);
					 SocketClient.ConnectSevert(UPDATE_MAINBIGIMAGE+strinfo+UPDATE_MAINBIGIMAGE+vegeId+UPDATE_MAINBIGIMAGE+path+UPDATE_MAINBIGIMAGE+"2");
					 info=SocketClient.readinfo;
				   }
				}
				//�����ͼ
				  for(int i=0;i<imagelist.size();i++)
				  {
					if(i>=mainpathlength)//������Ǵӿ����ȡ����Ҫ���ص����в���ӽ����ݿ���
					{
						//�����µ���ͼ���������
		    			byte[] imagemsg=imagelist.get(i);
		    			SocketClient.ConnectSevertBYTE(UPLOAD_IMAGE,UPLOAD_ZIMAGE, imagemsg);
	                    String readinfo=SocketClient.readinfo;
	                  //���µ���ͼ�����¼��
	                    SocketClient.ConnectSevert(GET_VEGEIMAGEMAXNO);
	                    String index=SocketClient.readinfo;
					    String strinfo=((Integer.parseInt(index)+1)<10)?("00"+(Integer.parseInt(index)+1)):
						(((Integer.parseInt(index)+1)<100)?("0"+(Integer.parseInt(index)+1)):((Integer.parseInt(index)+1)+""));
					    SocketClient.ConnectSevert(INSERT_IMAGE+strinfo+INSERT_IMAGE+vegeId+INSERT_IMAGE+readinfo+INSERT_IMAGE+"0");
					     String info=SocketClient.readinfo;	
					    //���������ͼ�Ĵ�ͼ��ֱ�Ӳ壬�������ͼ�Ĵ�ͼ��ԭ����ͼ�Ĵ�ͼ��־��0
					    if(i==uig.mainindex)
					    {
					    //����Ǻ���ӵ���ͼ�Ĵ�ͼ��ԭ����ͼ�Ĵ�ͼ��־λ��Ϊ0
					    SocketClient.ConnectSevert(UPDATE_MAINBIGIMAGE+strinfo+UPDATE_MAINBIGIMAGE+vegeId+UPDATE_MAINBIGIMAGE+readinfo+UPDATE_MAINBIGIMAGE+"2");
					    info=SocketClient.readinfo;	
					    }
					   
					 }
				  }
				  
				  JOptionPane.showMessageDialog(UpdateVegeImage.this, "��ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					SocketClient.ConnectSevert(GET_USEVEGE);//��ȡ��Ʒ��Ϣ
			    	String getinfo=SocketClient.readinfo;
			    	Vector title=new Vector();//����
					{
						title.add("��ƷID");
						title.add("��Ʒ����");
						title.add("�۸�");
						title.add("������λ");
						title.add("��Ʒ���");
						title.add("��Ʒ����");
						title.add("ϵ��");
						title.add("���");
						title.add("����");
					}
					if(getinfo.equals("fail"))
					{
                  	    JOptionPane.showMessageDialog(UpdateVegeImage.this, "��ȡ��Ϣʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
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
			    	UpdateVegeImage.this.setVisible(false);//�رո��²�ƷͼƬ����
				}
           }
         );
        this.add(jp);//��JPanel��ӵ�������
		this.setResizable(false);//��������Ϊ�̶��ߴ�
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
		this.setBounds(200, 0, 560, 700);//���ý���λ�����С
		this.setVisible(true);//���ý���Ϊ�ɼ�
		this.setTitle("��Ʒ�༭");//���ý������
    }
}
