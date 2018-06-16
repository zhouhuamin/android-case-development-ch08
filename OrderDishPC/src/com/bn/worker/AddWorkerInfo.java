package com.bn.worker;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import com.bn.constant.Constant;
import com.bn.pcinfo.MainUI;
import com.bn.util.SocketClient;
import static com.bn.constant.Constant.*;
@SuppressWarnings("serial")
/*
 * ���Ա����Ϣ
 * */
public class AddWorkerInfo extends JFrame 
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
	public AddWorkerInfo(final MainUI mainUI,final Vector<String> title)//���Ա����Ϣ����
	{
		Image image=this.getToolkit().getImage("src/com/bn/image/tb1.jpg");//����ͼƬ
		this.setIconImage(image);//���ý���ͼ��
		this.mainUI = mainUI;
		this.title = title;
		final DefaultMutableTreeNode curenode=Constant.authority[0];//��ǰ�ڵ�ΪԱ������
		this.setTitle("���Ա����Ϣ");//���ñ���
		this.setBounds(300,40,width,height);//����λ�����С
		jp.setLayout(null);//����JPanel������ΪĬ��
		this.add(jp);//��JPanel��ӵ�������
		this.setVisible(true);//��������Ϊ�ɼ�		
		//����Label����Ĵ�С
		for(JLabel jlabel : jl)
		{
			jlabel.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));
		}
		//��ȡԱ�������id
		String nextId =""; 
	    SocketClient.ConnectSevert(GET_WORKERMAXNO);
		String maxId=SocketClient.readinfo;
		int nId = Integer.parseInt(maxId)+1;
		if(nId<10)
		{
			nextId = "00" + nId;
		}
		else if(nId<100)
		{
			nextId = "0" + nId;
		}
		else
		{
			nextId = ""+ nId;
		}
		//�������ֵ�û�ȡ����Ϊ�����б���ѡ��
		String gender[] = {"��","Ů"};  //0-��   1-Ů
		List<String[][]> FKList = WorkerInfoTransform.getFKName();
		final String roleArr[][] = FKList.get(0);
		String roleName[] = new String[roleArr.length];//��ý�ɫ����
		for(int i=0; i<roleArr.length; i++)
		{
			roleName[i] = roleArr[i][1]; //����ɫ���Ƹ���roleName����
		}
		//Ա��id
		jl[0].setBounds(left,topSpan,jlWidth,jlHeight);//����Ա��idλ�����С
		jp.add(jl[0]);//��jl[0]��ӵ�JPanel��
		final JLabel jlid = new JLabel(); //Ϊ�����ύ��ť������ʹ��jlid��������˸�������Ϊfinal��
		jlid.setText(nextId);//����JLabel��ֵ
		jlid.setBounds(tLeft,topSpan,jlWidth,jlHeight);//����JLabel��λ�����С
		jp.add(jlid);//��JLabel��ӵ�JPanel��		
		//��������¼����
		jl[1].setBounds(left,topSpan+rowSpan,jlWidth,jlHeight);//��������JLabelλ�����С
		jp.add(jl[1]);//������JLabel��ӵ�JPanel��
		final JTextField tfName = new JTextField();//��������JTextField
		tfName.setText("");//��������Ϊ��
		tfName.setBounds(tLeft,topSpan+rowSpan,lwidth,tHeight);//��������JTextFieldλ�����С
		tfName.requestFocus();  //�����Ի������Ȼ�ý��㣬��ΪԱ�������ΪĬ�ϸ���
		jp.add(tfName);//Ա��������ӵ�JPanel��
		JLabel jHint1 = new JLabel("*");//����JLabel
		jHint1.setBounds(tLeft+lwidth+10,topSpan+rowSpan,35,35);//����JLabelλ�����С
		jp.add(jHint1);//��JLabel��ӵ�JPanel��
		
		jl[2].setBounds(left,topSpan+rowSpan*2,jlWidth,jlHeight);//��������JLabelλ�����С
		jp.add(jl[2]);//������JLabel��ӵ�JPanel��
		final JTextField tfPassword = new JTextField();//��������JTextField
		tfPassword.setText("8888");//��������Ĭ��Ϊ8888
		tfPassword.setBounds(tLeft,topSpan+rowSpan*2,lwidth,tHeight);//��������JTextFieldλ�����С
		jp.add(tfPassword);//Ա��������ӵ�JPanel��
		JLabel jHint2 = new JLabel("*");//����JLabel
		jHint2.setBounds(tLeft+lwidth+10,topSpan+rowSpan*2,35,35);//����JLabelλ�����С
		jp.add(jHint2);//��JLabel��ӵ�JPanel��
		//�Ա�
		jl[3].setBounds(left,topSpan+rowSpan*3,jlWidth,jlHeight);//�����Ա�JLabelλ�����С
		jp.add(jl[3]);//��JLabel��ӵ�JPanel��
		final JComboBox jSex=new JComboBox (gender);//�����Ա�JComboBox
		jSex.setSelectedItem(gender[0]);//��ѡ����ӵ��Ա�JComboBox��
		jSex.setBounds(tLeft,topSpan+rowSpan*3,lwidth,tHeight);//����JComboBoxλ�����С
		jSex.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
		jp.add(jSex);//���Ա�JComboBox��ӵ�JPanel��
		jSex.addKeyListener(new KeyListener()//JCombobox��Ӽ���
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
		jl[4].setBounds(left,topSpan+rowSpan*4,jlWidth,jlHeight);//���ý�ɫid JLabel��λ�����С
		jp.add(jl[4]);//����ɫid JLabel��ӵ�JPanel��
		final JComboBox jRole=new JComboBox (roleName);//�����ɫid JCombobox
		jRole.setSelectedItem(roleName[0]);//��ѡ����ӵ�JCombobox
		jRole.setBounds(tLeft,topSpan+rowSpan*4,tWidth,tHeight);//����JComboBoxλ�����С
		jRole.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
		jp.add(jRole);//����ɫid JComboBox��ӵ�JPanel��
		jRole.addKeyListener(new KeyListener()//JComboBox��Ӽ���
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
		JLabel jHint3 = new JLabel("*");//����JLabel
		jHint3.setBounds(tLeft+jlWidth+colSpan+jlWidth+tWidth+20,topSpan+rowSpan*10,35,35);
		jp.add(jHint3);//��ӵ�JPanel��
		
		JButton submit = new JButton("�ύ");//�����ύ��ť
		submit.setBounds(tLeft,topSpan+rowSpan*7,jlWidth,jlHeight);//���ð�ťλ�����С
		submit.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
		JButton close = new JButton("����");//���÷��ذ�ť
		close.setBounds(tLeft+jlWidth+colSpan+jlWidth+10,topSpan+rowSpan*7,jlWidth,jlHeight);//����λ�����С
		close.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
		jp.add(submit);//�����ύ����ť��ӵ�JPanel��
		jp.add(close);//����ȡ������ť��ӵ�JPanel��
		submit.addActionListener//�ύ��ť��Ӽ���
		( 
			new ActionListener()
			{
			  @SuppressWarnings("static-access")
			  @Override
			  public void actionPerformed(ActionEvent arg0)
			  {
				String value[] = new String[5];
				value[0] = jlid.getText();
				value[1] = tfName.getText();
				value[2] = tfPassword.getText();
				value[3] = jSex.getSelectedItem().toString().equals("��")?"0":"1";
				value[4] = roleArr[jRole.getSelectedIndex()][0]; //���ݱ�ѡ���������ֵ�ҵ�ѡ���Ӧ��id
				if(value[1].equals("") || value[2].equals("") || value[3].equals("") || value[4].equals(""))
				{
					JOptionPane.showMessageDialog
           	       (
           	    		   AddWorkerInfo.this,
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
           	    		   AddWorkerInfo.this,
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
           	    		   AddWorkerInfo.this,
	       	    		   "�����������ֱ�ʾ",
	       	    		   "��ʾ",
	       	    		   JOptionPane.INFORMATION_MESSAGE
	           	    );
				}
				else
				{
					StringBuffer submitContent = new StringBuffer();//���������
					for(int i=0; i<value.length; i++)
					{
						submitContent.append(ADD_WORKERINFO+value[i]);//��Ա����Ϣ��ӵ��ַ�����
					}
					SocketClient.ConnectSevert(submitContent.toString());//��Ա����Ϣ����������
					String readinfo=SocketClient.readinfo;
					if(readinfo.equals("ok"))//������ء�ok��
					{   //���½���
						JOptionPane.showMessageDialog(AddWorkerInfo.this, "Ա����Ϣ��ӳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						SocketClient.ConnectSevert(GET_WORKERINFO);
						String getinfo=SocketClient.readinfo;
						Vector<Vector<String>> data = WorkerInfoTransform.Transform(getinfo);
						mainUI.createJTable(data,title,20,250,midwidth,400);
						mainUI.createRight(curenode);
						AddWorkerInfo.this.setVisible(false);//���Ա����Ϣ����ر�
					}
					else
					{
						JOptionPane.showMessageDialog(AddWorkerInfo.this, "Ա����Ϣ���ʧ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
						AddWorkerInfo.this.setVisible(false);
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
					AddWorkerInfo.this.setVisible(false);//����ر�
				}
		   }
	   );
	}
}
