package com.bn.vege;

import static com.bn.constant.Constant.DEL_VEGE;
import static com.bn.constant.Constant.GET_VEGEIMAGEMAXNO;
import static com.bn.constant.Constant.INSERT_IMAGE;
import static com.bn.constant.Constant.UPLOAD_IMAGE;
import static com.bn.constant.Constant.UPLOAD_MIMAGE;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;
/*����Ӳ�Ʒ��ʱ����Ӳ�Ʒ��ͼ�Ľ���*/
@SuppressWarnings("serial")
public class AddMainImage extends JFrame
{
	MainUI mainui;
	String vegeId;//��ƷID
	int width=350;//���ÿ��
	int height=400;//���ø߶�
	int leftSpan;//������
	int topSpan=20;//������
	int imageWidth=280;//ͼƬ���
	int imageHeight=210;//ͼƬ�߶�
	int jAddHeight=30;//��ť����ʾ�������߶�
	int jTextWidth=200;//�������
	int buttonWidth=68;//��ť���
	JButton buttonNext;//����һ������ť
	JButton buttonCancel;//��ȡ������ť
	JButton buttonAdd;//���������ť
	JTextField jText;//·���༭��
	JLabel jImage;
	JPanel jPanel=new JPanel();
	byte[] imageByte;
	JLabel jTitle=new JLabel("��Ӳ�Ʒ��ͼ");//����
	Font font=new Font("����",Font.BOLD,22);//��������
	Font buttonFont=new Font("����",Font.TRUETYPE_FONT,12);
	public AddMainImage(final MainUI mainui, final String vegeId)
	{
		try
		{//windows���
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    	this.mainui=mainui;
		this.vegeId=vegeId;
		
		leftSpan=(width-imageWidth)/2;
		jPanel.setLayout(null);
		this.setBounds(200, 200, width, height);
		//����ͼƬ��ʾ����
		jImage=new JLabel("");
		jImage.setBounds
		(
			leftSpan,
			topSpan,
			imageWidth,
			imageHeight
		);
		jImage.setBorder(BorderFactory.createLoweredBevelBorder());//���ñ߿�
		jPanel.add(jImage);
		//�������ͼƬ����
		//��ʾ��Ϣ����
		JLabel jAdd=new JLabel("��ѡ��Ҫ��ӵ���ͼͼƬ:");
		jAdd.setBounds
		(
			5,
			topSpan+imageHeight,
			200, 
			jAddHeight
		);
		jPanel.add(jAdd);
		//���������
		jText=new JTextField();
		jText.setBounds
		(
			leftSpan,
			topSpan+imageHeight+jAddHeight,
			jTextWidth,
			jAddHeight
		);
		jPanel.add(jText);
		//���������ť����
		buttonAdd=new JButton();
		buttonAdd.setText("�  ��");
		buttonAdd.setFont(buttonFont);//���ð�ť����
		buttonAdd.setBounds
		(
			leftSpan+jTextWidth+15,
			topSpan+imageHeight+jAddHeight,
			buttonWidth,
			jAddHeight
		);
		//Ϊ���������ť��Ӽ���
		buttonAdd.addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					JFileChooser jfc=new JFileChooser("c:\\");
					jfc.showOpenDialog(buttonAdd);//�����ļ�ѡ���
					jfc.setCurrentDirectory(null);
					File file=jfc.getSelectedFile();
					String path="";
					try
					{
						 path=file.getAbsolutePath().toString();//�����ļ�·��
						 jText.setText(path);
						 if(!(file.exists()&&file.isFile()&&file.getName().endsWith(".jpg")))  
						 {  
							 JOptionPane.showMessageDialog
							 (
								 AddMainImage.this,
								 "ѡ���ļ�����������ѡ��һ����ȷ���ļ���","��ʾ",
								 JOptionPane.INFORMATION_MESSAGE
							 );
						 } 
						 else
						 {   //����ͼƬ�����Ű汾
							 Image image = getToolkit().getImage(path);  
							 image = image.getScaledInstance(imageWidth,imageHeight,Image.SCALE_DEFAULT);  
							 jImage.setIcon(new ImageIcon(image));
							 imageByte=TypeExchangeUtil.filetobyte(file);  
						 }
					}
					catch(Exception e)
					{
						System.out.println("Exception: the path is null");
					}
				}
			}
		);
		jPanel.add(buttonAdd);//����ť��ӵ�JPanel��
		//����һ������ť����
		buttonNext=new JButton();
		buttonNext.setText("��һ��");
		buttonNext.setFont(buttonFont);//���ð�ť����
		buttonNext.setBounds//���ð�ťλ�����С
		(
			2*leftSpan,
			2*topSpan+imageHeight+jAddHeight+jAddHeight+5,
			buttonWidth,
			jAddHeight
		);
		//����һ������ťע�����
		buttonNext.addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					if(imageByte==null)
					{
					    SocketClient.ConnectSevert(GET_VEGEIMAGEMAXNO);
	                	String i=SocketClient.readinfo;
						String indexx=((Integer.parseInt(i)+1)<10)?("00"+(Integer.parseInt(i)+1)):
							(((Integer.parseInt(i)+1)<100)?("0"+(Integer.parseInt(i)+1)):((Integer.parseInt(i)+1)+""));
						//�����ͼ�Ĵ�ͼ
						SocketClient.ConnectSevert(INSERT_IMAGE+indexx+INSERT_IMAGE+vegeId+INSERT_IMAGE+Constant.vegenullBigimage+INSERT_IMAGE+"2");
		    		    String info=SocketClient.readinfo;
		    			String mid=((Integer.parseInt(indexx)+1)<10)?("00"+(Integer.parseInt(indexx)+1)):
							(((Integer.parseInt(indexx)+1)<100)?("0"+(Integer.parseInt(indexx)+1)):((Integer.parseInt(indexx)+1)+""));
		    			//�����ͼ��Сͼ
		    			SocketClient.ConnectSevert(INSERT_IMAGE+mid+INSERT_IMAGE+vegeId+INSERT_IMAGE+Constant.vegenullimage+INSERT_IMAGE+"1");
		    			info=SocketClient.readinfo;
		    			if(info.equals("fail"))
				    	{
                        	JOptionPane.showMessageDialog
                        	(
                    			AddMainImage.this, 
                    			"ͼƬ���ʧ��",
                    			"��ʾ",
                    			JOptionPane.INFORMATION_MESSAGE
                        	);
						}
				    	else
				    	{
							//�����ͼ�ɹ�������һ��
							AddChildImage aci=new AddChildImage(mainui,vegeId);
							aci.setVisible(true);
							AddMainImage.this.setVisible(false);//�����ͼ��������Ϊ���ɼ�
						}
					}
					else
					{
		    			SocketClient.ConnectSevertBYTE(UPLOAD_IMAGE, UPLOAD_MIMAGE, imageByte);
						String readinfo=SocketClient.readinfo;
		    			if(!readinfo.equals(null))
		    			{
		    				String[] path=readinfo.split("#");
		    				SocketClient.ConnectSevert(GET_VEGEIMAGEMAXNO);
							String index=SocketClient.readinfo;
							String strinfo=((Integer.parseInt(index)+1)<10)?("00"+(Integer.parseInt(index)+1)):
								(((Integer.parseInt(index)+1)<100)?("0"+(Integer.parseInt(index)+1)):((Integer.parseInt(index)+1)+""));
		    				//�����ͼ�Ĵ�ͼ
			    			SocketClient.ConnectSevert(INSERT_IMAGE+strinfo+
								    					INSERT_IMAGE+vegeId+
								    					INSERT_IMAGE+path[0]+
								    					INSERT_IMAGE+"2");
			    			String info=SocketClient.readinfo;
			    			String mid=((Integer.parseInt(strinfo)+1)<10)?("00"+(Integer.parseInt(strinfo)+1)):
								(((Integer.parseInt(strinfo)+1)<100)?("0"+(Integer.parseInt(strinfo)+1)):((Integer.parseInt(strinfo)+1)+""));
			    			//�����ͼ��Сͼ
			    			SocketClient.ConnectSevert(INSERT_IMAGE+mid+
								    					INSERT_IMAGE+vegeId+
								    					INSERT_IMAGE+path[1]+
								    					INSERT_IMAGE+"1");
			    			info=SocketClient.readinfo;
					    	if(info.equals("fail"))
					    	{
	                        	JOptionPane.showMessageDialog
	                        	(
                        			AddMainImage.this, 
                        			"ͼƬ���ʧ��",
                        			"��ʾ",
                        			JOptionPane.INFORMATION_MESSAGE
	                        	);
							}
					    	else
					    	{
								//�����ͼ�ɹ�������һ��
								AddChildImage aci=new AddChildImage(mainui,vegeId);
								aci.setVisible(true);
								AddMainImage.this.setVisible(false);
							}
		    			}
		    			else
		    			{
		    				JOptionPane.showMessageDialog
		    				(
	    						AddMainImage.this,
	    						"ͼƬ�ϴ�ʧ��",
	    						"��ʾ",
	    						JOptionPane.INFORMATION_MESSAGE
		    				);
					    	AddMainImage.this.setVisible(false);
		    			}
			    	}
			    }
			}
		);
		jPanel.add(buttonNext);
		//��ȡ������ť����
		buttonCancel=new JButton();
		buttonCancel.setText("ȡ  ��");
		buttonCancel.setFont(buttonFont);//����ȡ����ť����
		buttonCancel.setBounds//����ȡ����ťλ�����С
		(
			width-2*leftSpan-buttonWidth, 
			2*topSpan+imageHeight+jAddHeight+jAddHeight+5,
			buttonWidth,
			jAddHeight
		);
		buttonCancel.addActionListener//ȡ����ť��Ӽ���
		(
		  new ActionListener()	
		  {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//����ȡ����ť����һ����ӵĲ�Ʒ�ӿ���ɾ��
			 SocketClient.ConnectSevert(DEL_VEGE+vegeId);
	    	 String backinfo=SocketClient.readinfo;
	    	 AddMainImage.this.setVisible(false);
			}
		  }
		);
		jPanel.add(buttonCancel);//��ȡ����ť��ӵ�JPanel��
		this.add(jPanel);//��JPanel��ӵ�������
		this.setVisible(true);//���ý���Ϊ�ɼ�
		this.setEnabled(true);//���ý������
		this.setTitle("��Ӳ�Ʒ��ͼ");//���ý������
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//���ùرշ�ʽ
		this.setResizable(false);//���ý���ߴ�̶�
		Image image=this.getToolkit().getImage("src/com/bn/img/kt.jpg");//����ͼƬ
		this.setIconImage(image);//���ý���ͼ��
	}
}
