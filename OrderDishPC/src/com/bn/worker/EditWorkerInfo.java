package com.bn.worker;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import static com.bn.constant.Constant.*;  //����ͨ����������Constant���еĳ����ͷ���
/*
 * �༭�޸�Ա����Ϣ������
 * */
@SuppressWarnings("serial")
public class EditWorkerInfo extends JFrame
{
	MainUI mainUI;
	Vector<String> title=null;
	int width=500;//������
	int height=450;//����߶�
	int jlWidth=60;
	int jlHeight=35;
	int lwidth=100;
	int tWidth=120;
	int tHeight=35;
	int left=15;             //��߾�
	int topSpan=10;          //��һ�о��붥������
	int rowSpan=jlHeight+12; //�м��
	int colSpan=130;  //�м��
	int tLeft=left+jlWidth+10;
	int midwidth=(int) ((1024-1024*0.25)*0.78);
	int contentFont=12;
	
	JPanel jp=new JPanel();//����JPanel
	byte[] imagebyte;
	//����JLabel����
	JLabel jl[] = 
	{
			new JLabel("Ա��id��")
			,new JLabel("Ա��������")
			,new JLabel("��¼���룺")
			,new JLabel("�Ա�")
			,new JLabel("��ɫ��")			
	};
	public EditWorkerInfo(MainUI mainUI,Vector<String> title)
	{
		this.mainUI = mainUI;
		this.title = title;
		Image image=this.getToolkit().getImage("src/com/bn/image/tb1.jpg");
		this.setIconImage(image);
	}
	public void editWorker(final String[] val)
	{
		final DefaultMutableTreeNode curenode=Constant.authority[0];//��ǰ�ڵ�ΪԱ������
		this.setTitle("�༭Ա����Ϣ");//���ý������
		this.setBounds(300,40,width,height);//���ý���λ�����С
		jp.setLayout(null);//����JPanel������ΪĬ��
		this.add(jp);//��JPanel��ӵ�����
		this.setVisible(true);//���ý���Ϊ�ɼ�
		
		//����Label����Ĵ�С
		for(JLabel jlabel : jl)
		{
			jlabel.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));
		}
		//�������ֵ�û�ȡ����Ϊ�����б���ѡ��
		String gender[] = {"��","Ů"};  //0-��   1-Ů
		List<String[][]> FKList = WorkerInfoTransform.getFKName();
		final String roleArr[][] = FKList.get(0);
		String roleName[] = new String[roleArr.length];
		for(int i=0; i<roleArr.length; i++)
		{
			roleName[i] = roleArr[i][1]; //����ɫ���Ƹ���roleName����
		}
		//Ա��id
		jl[0].setBounds(left,topSpan,jlWidth,jlHeight);
		jp.add(jl[0]);
		final JLabel jlid = new JLabel(); //Ϊ�����ύ��ť������ʹ��jlid��������˸�������Ϊfinal��
		jlid.setText(val[0]);
		jlid.setBounds(tLeft,topSpan,jlWidth,jlHeight);
		jp.add(jlid);
		//��������¼����
		jl[1].setBounds(left,topSpan+rowSpan*1,jlWidth,jlHeight);
		jp.add(jl[1]);
		final JTextField tfName = new JTextField();
		tfName.setText(val[1]);
		tfName.setBounds(tLeft,topSpan+rowSpan*1,lwidth,tHeight);
		jp.add(tfName);
		JLabel jHint1 = new JLabel("*");
		jHint1.setBounds(tLeft+lwidth+10,topSpan+rowSpan*1,35,35);
		jp.add(jHint1);
		
		jl[2].setBounds(left,topSpan+rowSpan*2,jlWidth,jlHeight);
		jp.add(jl[2]);
		final JTextField tfPassword = new JTextField();
		tfPassword.setText(val[2]);
		tfPassword.setBounds(tLeft,topSpan+rowSpan*2,lwidth,tHeight);
		jp.add(tfPassword);
		JLabel jHint2 = new JLabel("*");
		jHint2.setBounds(tLeft+lwidth+10,topSpan+rowSpan*2,35,35);
		jp.add(jHint2);
		//�Ա�
		jl[3].setBounds(left,topSpan+rowSpan*3,jlWidth,jlHeight);
		jp.add(jl[3]);
		final JComboBox jSex=new JComboBox (gender);
		jSex.setSelectedItem((val[3].equals("��"))?gender[0]:gender[1]);
		jSex.setBounds(tLeft,topSpan+rowSpan*3,lwidth,tHeight);
		jSex.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));
		jp.add(jSex);
		jSex.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode()==KeyEvent.VK_DOWN )
				{
					jSex.setPopupVisible(true);
					jSex.showPopup();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { }
		});
		//��ɫid
		jl[4].setBounds(left,topSpan+rowSpan*4,jlWidth,jlHeight);
		jp.add(jl[4]);
		final JComboBox jRole=new JComboBox (roleName);
		jRole.setSelectedItem(val[4]);
		jRole.setBounds(tLeft,topSpan+rowSpan*4,tWidth,tHeight);
		jRole.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));
		jp.add(jRole);
		jRole.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode()==KeyEvent.VK_DOWN )
				{
					jRole.setPopupVisible(true);
					jRole.showPopup();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { }
		});
		
		JLabel jHint3 = new JLabel("*");
		jHint3.setBounds(tLeft+jlWidth+colSpan+jlWidth+tWidth+20,topSpan+rowSpan*10,35,35);
		jp.add(jHint3);
		
		JButton submit = new JButton("�ύ");//�����ύ��ť
		submit.setBounds(tLeft,topSpan+rowSpan*7,jlWidth,jlHeight);//�����ύ��ťλ�����С
		submit.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
		JButton close = new JButton("����");//���巵�ذ�ť
		close.setBounds(tLeft+jlWidth+colSpan+jlWidth+10,topSpan+rowSpan*7,jlWidth,jlHeight);//����λ�����С
		close.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
		jp.add(submit);//���ύ��ť��ӵ�JPanel��
		jp.add(close);//��ȡ����ť��ӵ�JPanel��
		
		submit.addActionListener//�ύ��ť��Ӽ���
		( 
		  new ActionListener()
			{
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String value[] = new String[20];
				value[0] = jlid.getText();
				value[1] = tfName.getText();
				value[2] = tfPassword.getText();
				value[3] = jSex.getSelectedItem().toString().equals("��")?"0":"1";
				value[4] = roleArr[jRole.getSelectedIndex()][0]; //���ݱ�ѡ���������ֵ�ҵ�ѡ���Ӧ��id
				
				if(value[1].equals("") || value[2].equals("") || value[3].equals("") || value[4].equals(""))
				{
					JOptionPane.showMessageDialog
           	        (
           	    		EditWorkerInfo.this,
	       	    		"�뽫��Ҫ��Ϣ��д����",
	       	    		"��ʾ",
	       	    		JOptionPane.INFORMATION_MESSAGE
           	    	);
				}
				//�����������ʽ�Ŀ���
				else if(!value[1].matches("[^0-9-_]*"))
				{
					JOptionPane.showMessageDialog
	           	    (
	           	    	EditWorkerInfo.this,
	       	    		"�����������ʽ����",
	       	    		"��ʾ",
	       	    		JOptionPane.INFORMATION_MESSAGE
	           	    );
				}
				//����������ʽ�Ŀ���
				else if(!value[2].matches("\\d+"))
				{
					JOptionPane.showMessageDialog
	           	    (
	           	    	EditWorkerInfo.this,
	       	    		"�����������ֱ�ʾ",
	       	    		"��ʾ",
	       	    		JOptionPane.INFORMATION_MESSAGE
	           	    );
				}
				else
				{
					StringBuffer submitContent = new StringBuffer();//�����ַ���
					for(int i=0; i<value.length; i++)
					{
						submitContent.append(UPDATE_WORKERINFO+value[i]);//��Ա����Ϣ��ӵ��ַ�����
					}
					SocketClient.ConnectSevert(submitContent.toString());//��Ա����Ϣ����������
					String readinfo=SocketClient.readinfo;
					if(readinfo.equals("ok"))
					{   //���½���
						JOptionPane.showMessageDialog(EditWorkerInfo.this, "Ա����Ϣ�޸ĳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						SocketClient.ConnectSevert(GET_WORKERINFO);
						String getinfo=SocketClient.readinfo;
						Vector<Vector<String>> data = WorkerInfoTransform.Transform(getinfo);
						mainUI.createJTable(data,title,20,250,midwidth,400);
						mainUI.createRight(curenode);
						EditWorkerInfo.this.setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(EditWorkerInfo.this, "Ա����Ϣ�޸�ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						EditWorkerInfo.this.setVisible(false);
					}
				}
			}
		}
	  );
	close.addActionListener//ȡ����ť����
	(
		new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				EditWorkerInfo.this.setVisible(false);
			}
		}
	);
}		
}