package com.bn.pcinfo;

import static com.bn.pcinfo.TableListener.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import com.bn.order.GetConfirmOrder;
import com.bn.point.AddPoint;
import com.bn.point.UpdatePoint;
import com.bn.util.SocketClient;
import com.bn.util.TypeExchangeUtil;
import com.bn.vege.AddVegeUI;
import com.bn.vege.CateGoryUI;
import com.bn.vege.MainCateGoryUI;
import com.bn.vege.UpdateVege;
import com.bn.vege.VegeInfo;
import com.bn.worker.AddWorkerInfo;
import com.bn.worker.EditWorkerInfo;
import com.bn.worker.QueryWorkerInfo;
import com.bn.worker.WorkerInfoTransform;
import static com.bn.constant.Constant.*;
import com.bn.account.AccountOrder;
import com.bn.constant.Constant;
/*
 * ����������ΪJTree,�Ҳ����ѡ�еĽڵ���г�ʼ�����ж�Ӧ��ɾ���ڲ��࣬�����ڲ��࣬����ڲ��࣬���ھ���İ�ť����ɾ�Ĳ���
 * �Ҳ�������ʾ��JTable��ʾ ��JTable ��Ӽ���
 * */
@SuppressWarnings("serial")
public class MainUI extends JFrame 
{
	Font font=new Font("����",Font.ITALIC,13);
	Font f=new Font("����",Font.BOLD,18);
	Font fonttitle=new Font("����",Font.BOLD,20);//�Զ��弸�������ʽ
	public int screenwidth;//������Ļ���
	public int screenheight;//������Ļ�߶�
	public static int leftwidth;
	public static int midwidth;
	public static int rightwidth;
	public static int topheight;
	public static int buttomheight;
	public  Vector<Vector<String>> data;//���������������������
	public  DefaultMutableTreeNode curnode;
	public static  JTable jtable;
	private static String fail="fail";
	static JSplitPane jspOuter=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//����ָ����Ϊˮƽ�ָ�
	DefaultTreeModel dtm=new DefaultTreeModel(temproot);//���ڵ�
	JTree jt=new JTree(dtm);//������
	DefaultMutableTreeNode[] roomnode=null;//�����������Ľڵ�
	DefaultMutableTreeNode pointnode[]=null;
	JScrollPane jsp=new JScrollPane(jt);//���������
	static JPanel jp=new JPanel();
    public JCheckBox jct[];//���帴ѡ��
    //����ɾ�������¡���Ӽ���
	DelButtonListener dellistener=new DelButtonListener();
	 UpdateButtonListener updatelistener=new UpdateButtonListener();
	 AddButtonListener addlistener=new AddButtonListener();
	 
     JLabel jladd=null;
     JTextField jtfadd=null;
	 public JCheckBox jCheckBox;
//������淽��
	public MainUI()
	{
		getScreen(); //����getScreen()����
		CreateTree();//����Create()����
	    MainUIListener tl=new  MainUIListener();//�����������
	    //���ڵ����ͼ��
		jt.setCellRenderer
		 (
				 new DefaultTreeCellRenderer()
				 {
					 public Icon getClosedIcon() 
					 {
                    //��������Ҫ��ͼ��
						 Icon myIcon=new ImageIcon("src/com/bn/image/304.png");
						 return myIcon;                                                                                                                                   
					 }
					 public Icon getOpenIcon()
					 {
						 Icon myIcon=new ImageIcon("src/com/bn/image/304.png");
						 return myIcon;                                         
					 }
					 public Icon getLeafIcon()
					 {
						 Icon myIcon=new ImageIcon("src/com/bn/image/13.png");
						 return myIcon;      
					 }
				 }
		);
	    jt.addTreeSelectionListener(tl);//������Ӽ�������
		jt.setEditable(true);//������Ϊ�ɱ༭
		jt.setEnabled(false);//������Ϊ������
		jt.setShowsRootHandles(true);//��������ʾ�ڵ���
		jspOuter.setLeftComponent(jsp);//�����������õ��ָ��������
		jspOuter.setRightComponent(jp);//��Jpanel���õ��ָ������ұ�
		//��½����
		final JPanel jplogin=new JPanel();//�����¼Jpanel
		jplogin.setBounds(150,100,400,300);//����JPanel��λ�ü���С
		jplogin.setLayout(null);//���ô������Ĳ��ֹ�����Ϊ��
		jp.setLayout(null);
		JLabel jtitle=new JLabel("Ա����½");//���ñ���Ϊ��Ա����½��
		JLabel jllogin[]={new JLabel("��ţ�"),new JLabel("���룺")};//����JLabel���飬������š�����
		final JTextField jtflogin=new JTextField("");//�����������
		final JPasswordField jtfpw=new JPasswordField("");//�������������
		jtitle.setFont(new Font("����",Font.BOLD,24));//������������
		jplogin.add(jtitle);//��������ӵ���½Jpanel��
		jtitle.setBounds(150,30,200, 40);//���ñ����λ�����С
		for(int i=0;i<jllogin.length;i++)//��ѭ����佫��ţ�������ӵ���½Jpanel��
		{
			jllogin[i].setBounds(80,80+i*60,80,40);
			jllogin[i].setFont(fonttitle);
			jplogin.add(jllogin[i]);
		}
	     //��ȡ����
		jtflogin.setBounds(160,80,120,40);//���ñ��������λ�����С
		jtflogin.setFont(font);//���ñ�ŵ�����
		jplogin.add(jtflogin);//������������ӵ���½Jpanel��
		jtfpw.setBounds(160,140,120,40);//��������������λ�����С
		jtfpw.setFont(font);//�������������
		jplogin.add(jtfpw);//�������������ӵ���½Jpanel��
		final JButton jok=new JButton("ȷ��");//���塰ȷ������ť
		JButton jreset=new JButton("ȡ��");//���塰ȡ������ť
		jok.setBounds(100,220,80,40);//���á�ȷ������ť��λ�����С
		jreset.setBounds(200,220,80,40);//���á�ȡ������ť��λ�����С
		jplogin.add(jok);//����ȷ������ť��ӵ���½Jpanel��
		jplogin.add(jreset);//����ȡ������ť��ӵ���½Jpanel��
		jplogin.setBorder(BorderFactory.createLineBorder(Color.black));//���õ�½Jpanel�ı߿�
		jp.add(jplogin);//����½Jpanel��ӵ�������Jpanel��
		jtflogin.addActionListener//������������Ӽ���
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					jtfpw.requestFocus();//��ý���
				}
		    }
		);
		jtfpw.addActionListener//�������������Ӽ���
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					jok.requestFocus();//��ý���
				}
		    }
		);
		
		//����½��ť��Ӽ���
		jok.addActionListener
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String userid=jtflogin.getText().toString();//�����������ֵ����userid
					@SuppressWarnings("deprecation")//��ʾ�������ڵķ���
					String userpw=jtfpw.getText().toString();//������������ֵ����userpw
					System.out.println("tttt"+userid+userpw);
					//ͨ������SocketClient��ConnectSevert��������������е��û���������봫��������
					SocketClient.ConnectSevert(Constant.SURE_WAITER+userid+Constant.SURE_WAITER+userpw);
					if(SocketClient.readinfo.equals("ok"))//������ء�ok��
					 {
						int i=0;
						SocketClient.ConnectSevert(Constant.GET_LOGINAUTH+userid);//���û���Ŵ���������
						//ͨ���û���Ż�ô��û���Ȩ����Ϣ
						String authoryinfo[][]=TypeExchangeUtil.getString(SocketClient.readinfo);
						for(i=0;i<authoryinfo.length;i++)
						{
							   if(authoryinfo[i][0].equals("��½"))//���Ȩ�����С���½��Ȩ��
							   {
								jt.setEnabled(true);//��������Ϊ����
								jplogin.setVisible(false);//ʹ��½Jpanel���ɼ�
								JOptionPane.showMessageDialog//������ʾ��Ϣ����½�ɹ���
								(
									MainUI.this,
									"��½�ɹ�",
									"��ʾ",
									JOptionPane.INFORMATION_MESSAGE
								);
								//�鿴�����Ƿ���Ԥ����̨������Ԥ����̨��Ϊæ
								String todaytime=TypeExchangeUtil.gettime();//ȡ�õ�ǰ��ʱ��
								String times[]=todaytime.split(" ");
								for(int j=0;j<times.length;j++)
								{
									System.out.println("time="+times[j]);
								}
								SocketClient.ConnectSevert(UPDATE_TODAY_POINTSTATE+times[0]);
								//��¼������
								Constant.operator=userid;
								break;
							   }
						 }
						 if(i>=authoryinfo.length)//���Ȩ�����ޡ���½��
						 {
							 JOptionPane.showMessageDialog//������ʾ��Ϣ����Ȩ�޵�½��
								(
									MainUI.this,
									"���޵�½Ȩ��",
									"��ʾ",
									JOptionPane.INFORMATION_MESSAGE
								);
							 jtflogin.setText("");//�����������ÿ�
							 jtfpw.setText("");//������������ÿ�
							 jtflogin.requestFocusInWindow();//��½������ý���
						 }
					 }
					 else
					 {
						 JOptionPane.showMessageDialog//���������ʾ��Ϣ��"����������ȷ���û�������"
							(
								MainUI.this,
								"����������ȷ���û�������",
								"��ʾ",
								JOptionPane.INFORMATION_MESSAGE
							);
						 jtflogin.setText("");//�����������ÿ�
						 jtfpw.setText("");//������������ÿ�
						 jtflogin.requestFocusInWindow();//��½������ý���
					 }
				}
			}
		);
		jreset.addActionListener//ȡ����ť�ļ���
		(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					jtflogin.setText("");//�����������ÿ�
					jtfpw.setText("");//������������ÿ�
				}
			}
		);
		//����
		jspOuter.setDividerSize(15);//���÷ָ�����СΪ15
		jspOuter.setDividerLocation(200);//���÷ָ���λ��
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//ȫ��
		Image image=this.getToolkit().getImage("src/com/bn/image/tb1.jpg");//����ͼƬ
		this.setIconImage(image);//����ͼ��Ϊimage
		this.add(jspOuter);//���ָ�����ӽ���
		this.setEnabled(true);//����������Ϊ����
        this.setBounds(0, 0,1024,768);//����������λ�����С		
		this.setTitle("���ϵͳ");//���ñ���
		this.setVisible(true);//����Ϊ�ɼ�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ùرշ�ʽ
		this.addWindowStateListener//��������Ӽ���
		(
			new WindowAdapter()
			{
			   public  void windowStateChanged(WindowEvent e)
			   {
				getScreen();//����getScreen()����
			  }
		    }
		);
	}
	public void getScreen()//���ø��ֳߴ�
	{
	   screenwidth=1024;
	   screenheight=768;
		System.out.println("ScreenWidth"+screenwidth+"screenheight"+screenheight);
	    leftwidth=(int) (screenwidth*0.25);
	    midwidth=(int)((screenwidth-leftwidth)*0.99);
	    topheight=(int) (screenheight*0.07);
	    buttomheight=(int)(screenheight*0.6);
	}
	//������
	public void CreateTree()
	{
		for(int i=0;i<dmtn.length;i++)
		{
			dtm.insertNodeInto(dmtn[i], temproot, i);//��dtm[]�еĽڵ���ӵ�temproot���ڵ�����
		}
		for(int i=0;i<vegenode.length;i++)//dmtn[0]��Ӳ�Ʒ�ڵ�
		{ 
			dmtn[0].add(vegenode[i]);
		}
		for(int i=0;i<roompointnode.length;i++)//dmtn[1]��Ӳ����ڵ�
		{
			dmtn[1].add(roompointnode[i]);
		}
		for(int i=0;i<ordernode.length;i++)//dmtn[3]��Ӷ����ڵ�
		{
			dmtn[3].add(ordernode[i]);
		}
		
		//Ա������
		for(int i=0;i<authority.length;i++)//dmtn[2]���Ա���ڵ�
		{
			dmtn[2].add(authority[i]);
		}
		for(int i=0;i<accountnode.length;i++)//dmtn[4]��ӽ��˽ڵ�
		{
			dmtn[4].add(accountnode[i]);
		}
		//��ȡ���в�������
		SocketClient.ConnectSevert(GET_RMNAME);
		String roomname=SocketClient.readinfo;
		if(roomname.equals(SOCKET_ERROR))//��������������
		{
			JOptionPane.showMessageDialog
			(
				MainUI.this,
				"���������ӳ�ʱ����鿴�������������",
				"��ʾ",
				JOptionPane.INFORMATION_MESSAGE
			);
			System.exit(0);
		}
		else if(roomname.equals(SOCKET_IOERROR))
		{
			JOptionPane.showMessageDialog
			(
				MainUI.this,
				"��ȡ�����쳣����鿴�������������",
				"��ʾ",
				JOptionPane.INFORMATION_MESSAGE
			);
		}
		else
		{
			String info[]=TypeExchangeUtil.getStringInfo(roomname, 0);//��ò�����Ϣ
			roomnode=new DefaultMutableTreeNode[info.length];//��������ڵ�
			for(int i=0;i<info.length;i++)
			{//��������ӽڵ�
				roomnode[i]=new DefaultMutableTreeNode(info[i]);
				roompointnode[0].add(roomnode[i]);
			}
		}
		
	} 
	@SuppressWarnings("rawtypes")
	public void FitTableColumns(JTable myTable) {               //����table���п������ݵ���
		
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();
        Enumeration columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(
                    column.getIdentifier());
            int width = (int) myTable.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(myTable,
                            column.getIdentifier(), false, false, -1, col)
                    .getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++){
                int preferedWidth = (int) myTable.getCellRenderer(row, col)
                        .getTableCellRendererComponent(myTable,
                                myTable.getValueAt(row, col), false, false,
                                row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column);
            column.setWidth(width + myTable.getIntercellSpacing().width+6);
        }
    }

	//�����Ҳ��JTable
		public static void createJTable(Vector<Vector<String>> list,
				final Vector<String> title,int x,int y,int width,int height)
		{
			primarykey=null;//��������Ϊ��
			jp.setLayout(null);//����JPanel������ΪĬ��FlowLayout
	        jp.removeAll();//��JPanel���Ƴ��������
			DefaultTableModel dtmtable=new DefaultTableModel(list,title)//�������ģ��
			{
				@Override  
				public boolean isCellEditable(int row,int column)//��� row �� column λ�õĵ�Ԫ���ǿɱ༭�ģ��򷵻� true
				{  
				    if(column == 0)
				    {  
					    return false;  
				    }
				    return true;
		        } 
			};
			//����JTable
			jtable=new JTable(dtmtable){
				//�������õ���JTable�еĵ�Ԫ��ʱ����任Ϊ�ɱ༭״̬
				public void changeSelection(int rowIndex, int columnIndex,
			                boolean toggle, boolean extend)
				{
	                super.changeSelection(rowIndex, columnIndex, false, false);
	                super.editCellAt(rowIndex, columnIndex, null);
	                this.requestFocus();
				}
			};
			jtable.setRowHeight(25);//���ñ�ĵ�Ԫ��߶�Ϊ25����
			jtable.requestFocus();//������뽹��
			DefaultTableColumnModel dtcm=(DefaultTableColumnModel) jtable.getColumnModel();//��������ģ��
			dtcm.getColumn(0).setResizable(false);//���õ�һ�гߴ粻�ɱ�
			JScrollPane jspt=new JScrollPane(jtable);//�����м��������
			jspt.setBounds(x,y,midwidth,height);//���ù�������λ�����С
			jp.add(jspt);//����������ӵ�Jpanel��
		}
	//��������Ľڵ���ʾ��Ӧ������
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createRight( DefaultMutableTreeNode curenode)
	{
        JButton jb[]=null;
        //���ݽڵ㲻ͬ��ʾ���ݲ�ͬ
        if(curenode.toString().equals("������λ����"))
		{
			jb=new JButton[3];
			//ΪJTable����������
			final TableRowSorter sorter = new TableRowSorter(jtable.getModel());  
			jtable.setRowSorter(sorter);
			jb[0]=new JButton("��Ӽ�����λ");
			jb[1]=new JButton("ɾ��������λ");
			jb[2]=new JButton("���������λ");
		}else if(curenode.toString().equals("��ϵ����"))
		{
			jb=new JButton[3];
			//ΪJTable����������
			final TableRowSorter sorter = new TableRowSorter(jtable.getModel());  
			jtable.setRowSorter(sorter);
			jb[0]=new JButton("��Ӳ�ϵ");
			jb[1]=new JButton("ɾ����ϵ");
			jb[2]=new JButton("�����ϵ");
		}else if(curenode.toString().equals("�������"))
		{
			jb=new JButton[3];
			jb[0]=new JButton("�������");
			jb[1]=new JButton("ɾ������");
			jb[2]=new JButton("��������");
		}
		else if(curenode.toString().equals("������"))
		{
			jb=new JButton[3];
			jb[0]=new JButton("������");
			jb[1]=new JButton("ɾ�����");
			jb[2]=new JButton("�������");
		}else if(curenode.toString().equals("��Ʒ����"))
		{
			jb=new JButton[4];
			jb[0]=new JButton("��Ӳ�Ʒ");
			jb[1]=new JButton("ɾ����Ʒ");
			jb[2]=new JButton("�޸Ĳ�Ʒ");
//			jb[3]=new JButton("��ѯ��Ʒ");
			jb[3]=new JButton("��Ʒ����");
		}else if(curenode.toString().equals("������"))
		{
			jb=new JButton[3];
			//ΪJTable����������
			final TableRowSorter sorter = new TableRowSorter(jtable.getModel());  
			jtable.setRowSorter(sorter);
			jb[0]=new JButton("��ӹ��");
			jb[1]=new JButton("ɾ�����");
			jb[2]=new JButton("������");
		}
		else if(curenode.toString().equals("������Ϣ"))
		{	
		    jb=new JButton[4];
			jb[0]=new JButton("");
			jb[1]=new JButton("");
			jb[2]=new JButton("");
			jb[3]=new JButton("");	
			jb[0].setVisible(false);
			jb[1].setVisible(false);
			jb[2].setVisible(false);
			jb[3].setVisible(false);			
		} 
		else if(curenode.getParent().toString().equals("������Ϣ"))
		{
			 jb=new JButton[3];
				jb[0]=new JButton("��Ӳ�̨");
				jb[1]=new JButton("ɾ����̨");
				jb[2]=new JButton("�޸Ĳ�̨");
				
		}
       /////////////////////����ÿһ��Ĺ���////////////////////
		//����������Ϣ���趨  
         	if(curenode.toString().equals("�������"))//�����ǰ�ڵ��ǡ��������
            {
            	MainCateGoryUI mcui=new MainCateGoryUI(this);//����mcui
            	//ΪJTable����������
            	final TableRowSorter sorter = new TableRowSorter(jtable.getModel());  
    			jtable.setRowSorter(sorter);
    			//Ϊ������ť��Ӽ���
            	jb[0].addActionListener(mcui);//��Ӳ���            	
    			jb[1].addActionListener(dellistener);//ɾ������    	    	
    	    	jb[2].addActionListener(updatelistener);//���²���
            }else if(curenode.toString().equals("������"))//�����ǰ�ڵ��ǡ�������
            {
            	CateGoryUI cui=new CateGoryUI(this);//����cui
            	//ΪJTable����������
            	final TableRowSorter sorter = new TableRowSorter(jtable.getModel());  
    			jtable.setRowSorter(sorter);
    			//Ϊ������ť��Ӽ���
            	jb[0].addActionListener(cui);//��Ӳ���            	
    			jb[1].addActionListener(dellistener);//ɾ������    	    	
    	    	jb[2].addActionListener(updatelistener);//���²���
            }
            else if(curenode.toString().equals("��Ʒ����"))//�����ǰ�ڵ��ǡ���Ʒ����
            {
            	//���ж��Ƿ����û�в�ƷͼƬ��ͼƬ������ڲ������κβ�����������������һ��û�в�ƷͼƬ��ͼƬ
            	SocketClient.ConnectSevert(ISHAVENULLPIC);
				String readinfo=SocketClient.readinfo;
            	if(readinfo.equals(fail))
            	{
            		//���ص�ǰû�в�ƷͼƬ��ͼƬ
            		File file=new File(IMAGENULL_PATH);
                	byte[] imagemsg=TypeExchangeUtil.filetobyte(file);
                	SocketClient.ConnectSevertBYTE(UPLOAD_IMAGE,UPLOAD_MIMAGE, imagemsg);
                	String imagep=SocketClient.readinfo;
                	String[] mid=imagep.split("#");
                	vegenullBigimage=mid[0];
                	vegenullimage=mid[1];
            	}
            	else
            	{
            		String[] str=readinfo.split("#");
            		vegenullBigimage=str[0];
            		vegenullimage=str[1];
            	}
            	jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//�����ñ�ʱ������Զ�����ģʽ��
            	//ΪJTable����������
            	final TableRowSorter sorter = new TableRowSorter(jtable.getModel());  
    			jtable.setRowSorter(sorter);
            	AddVegeUI addvui=new AddVegeUI(this);//����addvui
            	//Ϊ�ĸ���ť��Ӽ���
            	jb[0].addActionListener(addvui);//��Ӳ���
            	jb[1].addActionListener(dellistener);//ɾ������
    			jb[2].addActionListener//�޸Ĳ�Ʒ��Ϣ
    			(
    					new ActionListener()
    					{
    						@Override
    						public void actionPerformed(ActionEvent e) 
    						{
    							String[] value=TableListener.vegemsg;//��ò�Ʒ��Ϣ
    							String pk=TableListener.primarykey;//��ò�Ʒ����
    							if(pk==null)//���Ϊ�գ������ʾ��Ϣ����ѡ�������޸ĵĲ�Ʒ��
    							{
    								JOptionPane.showMessageDialog
    								(
    										MainUI.this,
    										"��ѡ�������޸ĵĲ�Ʒ",
    										"��ʾ",
    										JOptionPane.INFORMATION_MESSAGE
    										);
    							}
    							else
    							{    //����͵���UpdataVege���������²�Ʒ��Ϣ
    								UpdateVege vmi=new UpdateVege(MainUI.this,value);
    								vmi.setVisible(true);
    							}
    						}		
    					}
    			 );
    			jb[3].addActionListener//��ȡ��Ʒ��Ϣ
    			(
    			  new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						String[] value=TableListener.vegemsg;//��ò�Ʒ��Ϣ
						String pk=TableListener.primarykey;//��ȡ��Ʒ����
						if(pk==null)//���Ϊ�գ������ʾ��Ϣ����ѡ������鿴�Ĳ�Ʒ��
						{
							JOptionPane.showMessageDialog
							(
									MainUI.this,
									"��ѡ������鿴�Ĳ�Ʒ",
									"��ʾ",
									JOptionPane.INFORMATION_MESSAGE
									);
						}
						else
						{
							VegeInfo vi=new VegeInfo(value);//�������ʾ��Ʒ��ϸ��Ϣ
							vi.setVisible(true);
						}
					}
    			  }
    			);
            }
/****************************************************��������begin*******************************************************************/
           else if(curenode.toString().equals("������Ϣ"))//�����ǰ�ڵ��ǡ�������Ϣ��
           {
        	    JPanel jpOrderInfo=new JPanel();//���嶩����ϢJPanel
        	    jpOrderInfo.setLayout(null);//������ϢJPanel������ΪĬ��FlowLayout
    			jladd=new JLabel("�����̨�ţ�");//����JLabel
    			jladd.setFont(f);//����JLabel������
    			jtfadd=new JTextField("");//���������
    			final JButton jsearch=new JButton("���Ҷ���");//���塰���Ҷ�������ť
    			JButton jsearchxx=new JButton("�鿴��ϸ����");//���塰�鿴��ϸ��������ť
    			jsearch.setFont(font);//���á����Ҷ�������ť������    			
    			jsearchxx.setFont(font);//���á�������ϸ��������ť������
    			jladd.setBounds(60,20,120,35);//����JLbel��λ�����С
    			jtfadd.setBounds(180,20,150,35);//����������λ�����С
    			jsearch.setBounds(360,20,100,35);//���á����Ҷ�������ť��λ�����С
    			jsearchxx.setBounds(500,20,140,35);//���á��鿴��ϸ��������ť��λ�����С
    			//��JLbel������򡢡����Ҷ�������ť�����鿴��ϸ��������ť��ӵ�������ϢJPanel��
    			jpOrderInfo.add(jladd);jpOrderInfo.add(jtfadd);jpOrderInfo.add(jsearch);jpOrderInfo.add(jsearchxx);
    			jpOrderInfo.setVisible(true);//��������ϢJPanel����Ϊ�ɼ�
    			//���ö�����ϢJPanel�ı߿�
    			jpOrderInfo.setBorder(BorderFactory.createLineBorder(Color.black));
    			jpOrderInfo.setBounds(20,60,midwidth,60);//���ö�����ϢJPanel��λ�����С
    			jp.add(jpOrderInfo);//��������ϢJPanel��ӵ�������JPanel��
    			//��enter�ò�ѯ��ť��ȡ����
    			jtfadd.addActionListener
    			(
    				new ActionListener()
    					{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								jsearch.requestFocus();//�����Ҷ�������ť��ý���
							}
    			        }
    			);
    		jsearch.addActionListener//�����Ҷ�������ť��Ӽ���
    			(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							 String pointid=jtfadd.getText();//��������е�ֵ����pointid
							 if(pointid.equals(""))//���Ϊ��,����ʾ��Ϣ���������̨�š�
							 {
								 JOptionPane.showMessageDialog
		                 	       (
		                 	    		   MainUI.this,
		                 	    		   "�������̨��",
		                 	    		   "��ʾ",
		                 	    		   JOptionPane.INFORMATION_MESSAGE
		                 	    	);
							 }
							 else
							 {   //���򣬾ͽ���̨�Ŵ���������������ô˲�̨�Ķ�����Ϣ
								 SocketClient.ConnectSevert(SEARCH_ORDER+pointid);
								 String list=SocketClient.readinfo;
								title=new Vector();//�������
								{
									title.add("��̨");title.add("������");title.add("�˿�����");
									title.add("����ʱ��");title.add("����Աid");title.add("�ܼ۸�");
								}
								if(list.length()==0)//���������ϢΪ�գ�����ʾ��Ϣ���˲������޶�����
								{
									JOptionPane.showMessageDialog
			                 	       (
		                 	    		   MainUI.this,
		                 	    		   "�˲������޶���",
		                 	    		   "��ʾ",
		                 	    		   JOptionPane.INFORMATION_MESSAGE
			                 	    	);
								    jtfadd.setText("");//��������ÿ�
								}
								else
								{  //����ͽ���������Ϣ����⴫��createJTable����������
								   data=TypeExchangeUtil.strToVector(list);
								   createJTable(data,title,20,topheight+80,midwidth+100,buttomheight);
								   createRight(curnode);
								}
							 }
    					}
    			     }
    			);
    		jsearchxx.addActionListener//���鿴��ϸ��������ť����
    		(
    			new ActionListener()
    			{
    				@Override
    			    public void actionPerformed(ActionEvent e)
    				{
						String pointid=jtfadd.getText();//��ò�����
						 if(pointid.equals(""))//���������Ϊ��
						 {
							 if(primarykey!=null)//���������Ϊ�գ������ž͵�������
							 {
								 pointid=primarykey;
							 }
							 else
							 {     //�������ʾ��Ϣ"��ѡ�����л��������̨��"
								   JOptionPane.showMessageDialog
		                 	       (
	                 	    		   MainUI.this,
	                 	    		   "��ѡ�����л��������̨��",
	                 	    		   "��ʾ",
	                 	    		   JOptionPane.INFORMATION_MESSAGE
		                 	    	);
    						 }
    					 }
    					if(pointid.length()!=0) //��������Ų�Ϊ��
    					{   //�������Ŵ�������������ѯ����
    						SocketClient.ConnectSevert(SEARCH_ACCORDER+pointid);
    					    String order=SocketClient.readinfo;//�õ�����
    					    System.out.println("---------order="+order);
    					    if(order.length()!=0)//���������Ϊ��
    						{   //���ݶ�����ѯ��������
    							SocketClient.ConnectSevert(SEARCH_ODXX+pointid);
    							String list=SocketClient.readinfo;//�õ���������
    							try 
    							{//windows���
    								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    							} 
    							catch (Exception e1)
    							{
    								e1.printStackTrace();
    							}
    						    new GetConfirmOrder(order,list);//������������������Ϊ��������GetConfirmOrder()
    						}
    						else//�������ʾ��Ϣ"�˲������޶���"
    						{
    							JOptionPane.showMessageDialog
    	                 	       (
	                 	    		   MainUI.this,
	                 	    		   "�˲������޶���",
	                 	    		   "��ʾ",
	                 	    		   JOptionPane.INFORMATION_MESSAGE
    	                 	    	);
    						    jtfadd.setText("");//��������ÿ�
    						}
    					}
    				}
    			}
    		);
    	   }
         else if(curenode.toString().equals("����"))//�����ǰ�ڵ��ǽ���
            {
    			//jp.updateUI();
        	    JPanel jpOrderInfo=new JPanel();//���嶩����ϢJpanel
        	    jpOrderInfo.setLayout(null);//������ϢJpanel������ΪĬ��FlowLayout
    			jladd=new JLabel("�����̨�ţ�");//����Jpanel
    			jladd.setFont(f);//����Jpanel������
    			jtfadd=new JTextField("");//���������
    			final JButton jsearch=new JButton("����");//���塰���ˡ���ť
    			jladd.setBounds(60,20,120,35);//����Jpanel��λ�����С
    			jtfadd.setBounds(180,20,150,35);//����������λ�����С
    			jsearch.setBounds(360,20,80,35);//���á����ˡ���ť��λ�����С
    			//��JLbel������򡢡����ˡ���ť��ӵ�������ϢJpanel��
    			jpOrderInfo.add(jladd);jpOrderInfo.add(jtfadd);jpOrderInfo.add(jsearch);
    			jtfadd.requestFocusInWindow();//������ý���
    			jpOrderInfo.setVisible(true);//��������ϢJpanel����Ϊ�ɼ�
    			jpOrderInfo.setBorder(BorderFactory.createLineBorder(Color.black));//����Jpanel�ı߿�
    			jpOrderInfo.setBounds(20,60,midwidth,60);//���ö�����ϢJpanel��λ�����С
    			jp.add(jpOrderInfo);//��������ϢJpanel��ӵ�������Jpanel��
    			jtfadd.addActionListener//��enter�ò�ѯ��ť��ȡ����
    			(
    				new ActionListener()
    				{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							jsearch.requestFocus();//�����ˡ���ť��ý���
						}
    			     }
    			);
    			jsearch.addActionListener//�������ˡ���ť��Ӽ���
    			(
					new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							String pointid=jtfadd.getText();//��ò�̨��
							 if(pointid.equals(""))//���Ϊ��
							 {
								 if(primarykey!=null)//���������Ϊ��
								 {
									 pointid=primarykey;//��̨�ž͵�������
								 }
								 else
								 {     //����ͳ���ʾ��Ϣ"��ѡ�����л��������̨��"
									   JOptionPane.showMessageDialog
			                 	       (
		                 	    		   MainUI.this,
		                 	    		   "��ѡ�����л��������̨��",
		                 	    		   "��ʾ",
		                 	    		   JOptionPane.INFORMATION_MESSAGE
			                 	    	);
	    						 }
	    					 }
	    					if(pointid.length()!=0) //�����̨�Ų�Ϊ��
	    					{
	    						//������Ϣ
	    						SocketClient.ConnectSevert(SEARCH_ORDER+pointid);
	    					    String order=SocketClient.readinfo;
	    						if(order.length()!=0)
	    						{//�����Ʒ��Ϣ
	    							SocketClient.ConnectSevert(SEARCH_ACCOUNT_VEGE+pointid);
	    							String list=SocketClient.readinfo;
	    							try 
	    							{//windows���
	    								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    							} 
	    							catch (Exception e1) 
	    							{
	    								e1.printStackTrace();
	    							}
	    						   new AccountOrder(order,list,MainUI.this);//����֧��	
	    						}
	    						else
	    						{  //�������ʾ��Ϣ"�˲������޶���"
	    						   JOptionPane.showMessageDialog
	    	                 	    (
		                 	    		   MainUI.this,
		                 	    		   "�˲������޶���",
		                 	    		   "��ʾ",
		                 	    		   JOptionPane.INFORMATION_MESSAGE
	    	                 	    );
	    						    jtfadd.setText("");//������ÿ�
	    						}
							 }
    					}
    			     }
    			);
    			
            }
/****************************************************����end*******************************************************************/


            else if(curenode.toString().equals("Ա������"))//�����ǰ�ڵ�Ϊ��Ա������
            {
            	int contentFont=14;
    			jtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//��������Ĵ�Сʱ������Զ�����ģʽ��
    			//ΪJTable����������
    			final TableRowSorter sorter = new TableRowSorter(jtable.getModel());  
    			jtable.setRowSorter(sorter);
    			
    			jb=new JButton[4];
    			//Ϊ�����ĸ���ť��Ӽ���
    			jb[0] = new JButton("���Ա��");//���
    			jb[0].addActionListener(new ActionListener()
    			{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						new AddWorkerInfo(MainUI.this,title);//�������Ա����Ϣ����
					}
    			});
    			jb[1] = new JButton("ɾ��Ա��");//ɾ��
    			jb[1].addActionListener(dellistener);
    			jb[2] = new JButton("�༭Ա��");//�༭
    			jb[2].addActionListener( new ActionListener()
    			{
					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						String pk = TableListener.primarykey;//�������
						if(pk==null)//���Ϊ�գ�����ʾ��Ϣ"��ѡ�������޸ĵ�Ա����Ϣ"
						{   
							JOptionPane.showMessageDialog
	                 	       (
	                 	    		   MainUI.this,
	                 	    		   "��ѡ�������޸ĵ�Ա����Ϣ",
	                 	    		   "��ʾ",
	                 	    		   JOptionPane.INFORMATION_MESSAGE
	                 	    	);
						}
						else
						{   //���򣬽�����������������ͨ��������ѯԱ����Ϣ
							SocketClient.ConnectSevert(QUERY_W_ONEOFWORKERINFOBYID+pk);
							String getinfo=SocketClient.readinfo;//���Ա����Ϣ
							System.out.println("QUERY_W_ONEOFWORKERINFOBYID---"+getinfo);
							if(getinfo!=null && getinfo.length()!=0)//���Ա����Ϣ��Ϊ��
							{
								String val[] = getinfo.split("#");//���Ա����Ϣ������
								for(int i=0; i<val.length; i++)
								{
									if(val[i].equals("null"))
									{
										val[i]="";
									}
									System.out.println(val[i]+" ");
								}
								EditWorkerInfo ewi = new EditWorkerInfo(MainUI.this,title);
								ewi.editWorker(val);//�����༭Ա����Ϣ����
							}
							else
							{   //�������ʾ��Ϣ"��ȡ��Ϣʧ��"
								JOptionPane.showMessageDialog
		                  	       (
				       	    		   MainUI.this,
				       	    		   "��ȡ��Ϣʧ��",
				       	    		   "��ʾ",
				       	    		   JOptionPane.INFORMATION_MESSAGE
		                  	    	);
							}
						}
					}    				
    			});
    			jb[3] = new JButton("�鿴ȫ��");
    			jb[3].addActionListener//��ȡȫ��Ա����Ϣ
    			(
    				new ActionListener()
    				{
					 @Override
					 public void actionPerformed(ActionEvent arg0) 
					  { //ͨ����������ȡ����Ա����Ϣ
						SocketClient.ConnectSevert(GET_WORKERINFO);
						String getinfo=SocketClient.readinfo;
						JOptionPane.showMessageDialog//��ʾ��Ϣ"��ȡ��Ϣ�ɹ�"
              	       (
              	    		   MainUI.this,
              	    		   "��ȡ��Ϣ�ɹ�",
              	    		   "��ʾ",
              	    		   JOptionPane.INFORMATION_MESSAGE
              	    	);
						if(getinfo.equals("fail"))//������ء�fail��,����ʾ��Ϣ"��ȡ��Ϣʧ��"
						{
							JOptionPane.showMessageDialog
	                 	       (
	                 	    		   MainUI.this,
	                 	    		   "��ȡ��Ϣʧ��",
	                 	    		   "��ʾ",
	                 	    		   JOptionPane.INFORMATION_MESSAGE
	                 	    	);
						}
						else
						{    //����Ա����Ϣ����⴫��createJTable()������
							 data = WorkerInfoTransform.Transform(getinfo);
							 createJTable(data,title,20,250,midwidth,400);
							 createRight(curnode);
						}
					}
    				
    			});    			
                JPanel jpQuery = new JPanel();//��ѯJPanel
    			jpQuery.setLayout(null);  //JPanel��Ĭ�ϲ��ֿ�������FlowLayout
    			jpQuery.setBorder(BorderFactory.createLineBorder(Color.black));  //ΪJPanel�����߱߿�
    			jpQuery.setBounds(20,90,midwidth,115);//���ò�ѯJpanel��λ�����С
    			jp.add(jpQuery);//����ѯJpanel��ӵ�������Jpanel��
    			JLabel jlTitle = new JLabel("��ѯ��");//����JLabel
    			jlTitle.setBounds(10,5,40,20);//����JLabel��λ�����С
    			jlTitle.setFont(new Font("����",Font.TRUETYPE_FONT,13));//����JLabel������
    			jpQuery.add(jlTitle);//��JLabel��ӵ���ѯJpanel��
    			JLabel jlWorkerInfo[] = 
    				{
    					new JLabel("Ա��id��")
    					,new JLabel("Ա��������")
    					,new JLabel("��ɫ��")
    				};
    			//ͨ��WorkerInfoTransform�����������
    			List<String[][]> FKList = WorkerInfoTransform.getFKName();
    			String roleArr[][] = FKList.get(0);//��ý�ɫ����
    			String roleName[] = new String[roleArr.length];//����roleName����
    			for(int i=0; i<roleArr.length; i++){
    				roleName[i] = roleArr[i][1]; //����ɫ���Ƹ���roleName����
    			}
    			//Ա��id
    			jlWorkerInfo[0].setBounds(25,30,70,20);//����Ա��id JLabel��λ�����С
    			jlWorkerInfo[0].setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
    			jpQuery.add(jlWorkerInfo[0]);//��Ա��id JLbel��ӵ���ѯJPanel��
    			final JTextField tfWId = new JTextField();//����JTextField
    			tfWId.setBounds(100,30,70,20);  //Ĭ��������һ���ַ�ռ�߸�����
    			jpQuery.add(tfWId);//���������ӵ���ѯJPanel��
    			//Ա������
    			jlWorkerInfo[1].setBounds(25,55,70,20);//����Ա������JLbel��λ�����С
    			jlWorkerInfo[1].setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
    			jpQuery.add(jlWorkerInfo[1]);//��JLabel��ӵ���ѯJPanel��
    			final JTextField tfWName = new JTextField();//����Ա�����������
    			tfWName.setBounds(100,55,70,20);  //Ĭ��������һ���ַ�ռ�߸�����
    			jpQuery.add(tfWName);//��Ա�������������ӵ���ѯJPanel��
    			//��ɫ
    			jlWorkerInfo[2].setBounds(25,80,70,20);//����Ա����ɫJLbel��λ�����С
    			jlWorkerInfo[2].setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
    			jpQuery.add(jlWorkerInfo[2]);//��Ա����ɫJLbel��ӵ���ѯJPanel��
    			final JComboBox jRole = new JComboBox(roleName);//����Ա����ɫJComboBox
    			jRole.setBounds(100,80,80,25);  //Ĭ��������һ���ַ�ռ�߸�����
    			jpQuery.add(jRole);//��JComboBox��ӵ���ѯJPanel��
    			//��ѯ��ť
    			JButton jQue = new JButton("��ѯ");
    			jQue.setBounds(420,77,60,30);//���ð�ť��λ�����С
    			jQue.setFont(new Font("����",Font.TRUETYPE_FONT,contentFont));//��������
    			jpQuery.add(jQue);//����ť��ӵ���ѯJPanel��
    			//��ť��Ӽ���
    			jQue.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) 
					{
						Map<String,String> map = new HashMap<String,String>();//����map
						if(tfWId.getText().length()!=0){
							map.put("tfWId", tfWId.getText());
						}//���Ա��id��Ϊ�գ���Ա��id��ӵ�map��
						if(tfWName.getText().length()!=0){
							map.put("tfWName", tfWName.getText());
						}//���Ա��������Ϊ�գ���Ա��������ӵ�map��
						if(!jRole.getSelectedItem().toString().equals("---")){
							map.put("JRole",jRole.getSelectedItem().toString() );
						}//���Ա����ɫ��Ϊ��---������Ա����ɫ��ӵ�map��
						new QueryWorkerInfo(sorter,map);//����QueryWorkerInfo��ѯԱ��
					}    				
    			});
    		}
    		else
    		{
	            //������Ӽ���
    	        jb[0].addActionListener(addlistener);//��Ӳ���    	    	
    	        jb[1].addActionListener(dellistener);//ɾ������
    	        jb[2].addActionListener(updatelistener);//���²���       
    		}
            JPanel toolsJP=new JPanel();//����JPanel
    		toolsJP.setLayout(null);//����JPanel�Ĳ�����ΪĬ��Flowlayout
    		toolsJP.setBorder(BorderFactory.createLineBorder(Color.black));  //ΪJPanel�����߱߿�
    		toolsJP.setBounds(20,10,midwidth,40);//����JPanel��λ�����С
    		JLabel jl=new JLabel();//����JLabel
     	    jl.setText(curenode.toString());//����JLabel��ֵΪ��ǰ�ڵ��ֵ
     		jl.setFont(fonttitle);//����JLabel������
     		jl.setBounds(10,5,200,30);//����JLabel��λ�����С
     		jl.setVisible(true);//����JLabelΪ�ɼ�
     		toolsJP.add(jl);//��JLabel��ӵ�JPanel
    		jp.add(toolsJP);//��JPanel��ӵ�������JPanel��
    		if(jb!=null)
    		{
	    		for(int i=0;i<jb.length ;i++)//ͨ��ѭ���������������ť�����塢λ�ô�С������ӵ�JPanel��
	    		{
	    			jb[jb.length-1-i].setFont(font);
	    			jb[jb.length-1-i].setBounds(midwidth-(35+80)*(i+1),3,110,35);
	    			toolsJP.add(jb[jb.length-1-i]);
	    		}
    		}
            jspOuter.setDividerSize(1);	//���÷ָ����ߴ�Ϊ1
     		jp.setVisible(true);//����������JPanelΪ�ɼ�
            jp.updateUI();//���½���
            if(jtfadd!=null)
            {
            	jtfadd.requestFocusInWindow();//��ý���
            }
            //����JTable�������ݵĳ��ȵ����п�
            FitTableColumns(jtable);
			//ΪJTable���û�����
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();   
			//JTable�е����ݾ�����ʾ
			renderer.setHorizontalAlignment(JLabel.CENTER);
			jtable.setDefaultRenderer(Object.class,renderer);//JTable����Ⱦ��
			//JTable�еı�ͷ������ʾ
			((DefaultTableCellRenderer)jtable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
          //������Ӽ���
			TableListener tablelistener=new TableListener(jtable);
			jtable.getSelectionModel().addListSelectionListener(tablelistener);
			jtable.getModel().addTableModelListener(tablelistener);					
	}
	//�����������ݵ���Ľڵ��������Ҳ�Ĳ��֣�
	 private class MainUIListener implements TreeSelectionListener
	 {
		   private TreePath tp;
			@SuppressWarnings({ "rawtypes", "unchecked" })//ѹ�ƾ���
			@Override
			public void valueChanged(TreeSelectionEvent e) 
			{
				tp=e.getNewLeadSelectionPath();//���ص�ǰǰ��·��
				if(tp!=null)
				{//��ȡѡ�еĽڵ�
					curnode=(DefaultMutableTreeNode) tp.getLastPathComponent();
					//���������
					if(curnode.equals(Constant.vegenode[0]))
					{   //ͨ���������������
						SocketClient.ConnectSevert(GET_MCG);
						String getinfo=SocketClient.readinfo;
						title=new Vector();//�����ı���
						{
							title.add("����ID");title.add("����");

						}
                        if(getinfo.equals("fail"))
                        {   //������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
                        	JOptionPane.showMessageDialog
                   	       (
                   	    		   MainUI.this,
                   	    		   "��ȡ��Ϣʧ��",
                   	    		   "��ʾ",
                   	    		   JOptionPane.INFORMATION_MESSAGE
                   	    	);
						}
                        else
                        {    //�������ݡ����⴫��createJTable������
							 data=TypeExchangeUtil.strToVector(getinfo);
							 createJTable(data,title,20,topheight,midwidth,buttomheight);
							 createRight(curnode);
						}
					}//����ǲ�Ʒ���
					else if(curnode.equals(Constant.vegenode[1]))
					{   //ͨ����������ò�Ʒ���
						SocketClient.ConnectSevert(GET_CG);
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
						{
							title.add("���ID");
							title.add("����");
							title.add("����ʱ��");
							title.add("���༭ʱ��");
                            title.add("��������");
						}
                        if(getinfo.equals("fail"))//������ء�fail���������ʾ��Ϣ"��ȡ��Ϣʧ��"
                        {
                        	JOptionPane.showMessageDialog
                   	       (
                   	    		   MainUI.this,
                   	    		   "��ȡ��Ϣʧ��",
                   	    		   "��ʾ",
                   	    		   JOptionPane.INFORMATION_MESSAGE
                   	    	);
						}
                        else
                        {    //���򣬽����ݡ����⴫��createJTable������
							 data=TypeExchangeUtil.strToVector(getinfo);
							 createJTable(data,title,20,topheight,midwidth,buttomheight);
							 createRight(curnode);
						}
					}//����ǲ�ϵ
					else if(curnode.equals(Constant.vegenode[2])){
						SocketClient.ConnectSevert(GET_VT);//ͨ����������ò�ϵ
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
						{
							title.add("��ϵ���");title.add("����");
						}
                        if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
                        {
                        	JOptionPane.showMessageDialog
                   	       (
                   	    		   MainUI.this,
                   	    		   "��ȡ��Ϣʧ��",
                   	    		   "��ʾ",
                   	    		   JOptionPane.INFORMATION_MESSAGE
                   	    	);
						}
                        else
                        {    //�������ݡ����⴫��createJTable������
							 data=TypeExchangeUtil.strToVector(getinfo);
							 createJTable(data,title,20,topheight,midwidth,buttomheight);
							 createRight(curnode);
						}
					}//����Ǽ�����λ
					else if(curnode.equals(Constant.vegenode[3]))
					{   //ͨ����������ü�����λ
						SocketClient.ConnectSevert(GET_CU);
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
						{
							title.add("������λ���");title.add("����");title.add("��ע");

						}
                        if(getinfo.equals("fail"))//������ء�fail���������ʾ��Ϣ"��ȡ��Ϣʧ��"
                        {
                        	JOptionPane.showMessageDialog
                   	       (
                   	    		   MainUI.this,
                   	    		   "��ȡ��Ϣʧ��",
                   	    		   "��ʾ",
                   	    		   JOptionPane.INFORMATION_MESSAGE
                   	    	);
                        }
                        else
                        {    //�������ݡ����⴫��createJTable������
							 data=TypeExchangeUtil.strToVector(getinfo);
							 createJTable(data,title,20,topheight,midwidth,buttomheight);
							 createRight(curnode);
						}
					}//����ǹ��
					else if(curnode.equals(Constant.vegenode[4]))
					{   //ͨ����������ù��
						SocketClient.ConnectSevert(GET_VS);
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
						{
							title.add("�����");title.add("����");
						}
                        if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
                        {
                        	JOptionPane.showMessageDialog
                   	       (
                   	    		   MainUI.this,
                   	    		   "��ȡ��Ϣʧ��",
                   	    		   "��ʾ",
                   	    		   JOptionPane.INFORMATION_MESSAGE
                   	    	);
                        }
                        else
                        {    //�������ݡ����⴫��createJTable������
							 data=TypeExchangeUtil.strToVector(getinfo);
							 createJTable(data,title,20,topheight,midwidth,buttomheight);
							 createRight(curnode);
						}
					}					
					//����ǲ�Ʒ
					else if(curnode.equals(Constant.vegenode[5]))
					{   //ͨ����������ò�Ʒ��Ϣ
						SocketClient.ConnectSevert(GET_USEVEGE);
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
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
                        if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
                        {
                        	JOptionPane.showMessageDialog
                   	       (
                   	    		   MainUI.this,
                   	    		   "��ȡ��Ϣʧ��",
                   	    		   "��ʾ",
                   	    		   JOptionPane.INFORMATION_MESSAGE
                   	    	);
						}
                        else
						{    //�������ݡ����⴫��createJTable������
							 data=TypeExchangeUtil.strToVector(getinfo);
							 createJTable(data,title,20,topheight,midwidth,buttomheight);
							 createRight(curnode);
						}
					}//����ǲ���
					else if(curnode.equals(roompointnode[0]))
					{   //ͨ����������ò�����Ϣ
						SocketClient.ConnectSevert(GET_ROOM);
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
						{
							title.add("�������");title.add("��������");
						}
						if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
						{
							JOptionPane.showMessageDialog
	                  	       (
	                  	    		   MainUI.this,
	                  	    		   "��ȡ��Ϣʧ��",
	                  	    		   "��ʾ",
	                  	    		   JOptionPane.INFORMATION_MESSAGE
	                  	    	);
						}
						else
						{   //�������ݡ����⴫��createJTable������
							data=TypeExchangeUtil.strToVector(getinfo);
							createJTable(data,title,20,topheight+10,midwidth,buttomheight+100);
							createRight(curnode);
						}
					}//����ǲ�̨
					else if(curnode.getParent().equals(roompointnode[0]))
					{   
						curnode.removeAllChildren();//�����ǰ�ڵ���ӽڵ�
						SocketClient.ConnectSevert(GET_POINT+curnode.toString());//ͨ����������õ�ǰ�ڵ����Ϣ
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
						{
							title.add("��̨��");title.add("��̨����");title.add("����");
							title.add("�Ƿ�ͣ��");title.add("��������");title.add("��ǰ״̬");title.add("���ڲ���");
						}
						if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
						{
							JOptionPane.showMessageDialog
	                  	       (
                  	    		   MainUI.this,
                  	    		   "��ȡ��Ϣʧ��",
                  	    		   "��ʾ",
                  	    		   JOptionPane.INFORMATION_MESSAGE
	                  	    	);
						}
						else
						{//��������Ӿ����̨�ڵ�
							if(getinfo.length()!=0){
							String str[]=TypeExchangeUtil.getStringInfo(getinfo, 1);
							 pointnode=new DefaultMutableTreeNode[str.length];//�õ������ӽڵ����
								for(int i=0;i<str.length;i++)
								{   //���ӽڵ���ӵ������ڵ���
									pointnode[i]=new DefaultMutableTreeNode(str[i]);
									curnode.add(pointnode[i]);
								}
						}
						else
						{   //�������ʾ��Ϣ"��ȡ��Ϣʧ��"
							JOptionPane.showMessageDialog
	                  	       (
                  	    		   MainUI.this,
                  	    		   "��ȡ��Ϣʧ��",
                  	    		   "��ʾ",
                  	    		   JOptionPane.INFORMATION_MESSAGE
	                  	    	);
						}//Ȼ�����ݡ����⴫��createJTable������
						data=TypeExchangeUtil.strToVector(getinfo);
						createJTable(data,title,20,topheight,midwidth,buttomheight+100);
						createRight(curnode);
						}
					}
                    //�����δ���˵Ķ�����Ϣ
					else if(curnode.equals(ordernode[0]))
					{   //ͨ������������˵���Ϣ
						SocketClient.ConnectSevert(GET_ORDER);
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
						{
							title.add("��̨����");title.add("������");title.add("�˿�����");
							title.add("����ʱ��");title.add("����Աid");title.add("�ܼ۸�");
						}
						if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
						{
							JOptionPane.showMessageDialog
	                  	       (
			       	    		   MainUI.this,
			       	    		   "��ȡ��Ϣʧ��",
			       	    		   "��ʾ",
			       	    		   JOptionPane.INFORMATION_MESSAGE
	                  	    	);
						}
						else{
							if(getinfo.length()==0)//�����ϢΪ�գ������ʾ��Ϣ"��ϢΪ��"
							{
								JOptionPane.showMessageDialog
		                  	       (
				       	    		   MainUI.this,
				       	    		   "��ϢΪ��",
				       	    		   "��ʾ",
				       	    		   JOptionPane.INFORMATION_MESSAGE
		                  	    	);
						     }//�����ݡ����⴫��createJTable������
							data=TypeExchangeUtil.strToVector(getinfo);
							createJTable(data,title,20,topheight+80,midwidth+100,buttomheight);
							createRight(curnode);
						}
					}
					//�����Ա����Ϣ
					else if(curnode.equals(Constant.authority[0]))
					{  //����Table�ı�ͷ�ͱ��е�����
						SocketClient.ConnectSevert(GET_WORKERINFO);
						String getinfo=SocketClient.readinfo;
						title = new Vector();  //����Jtable�ı�������
						{
							for(int i=0; i<workerPrivateInfo.length; i++)
							{
								title.add(workerPrivateInfo[i]);
							}
						}
						if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
						{
							JOptionPane.showMessageDialog
	                  	       (
			       	    		   MainUI.this,
			       	    		   "��ȡ��Ϣʧ��",
			       	    		   "��ʾ",
			       	    		   JOptionPane.INFORMATION_MESSAGE
	                  	    	);
						}
						else
						{   //�������ݡ����⴫��createJTable������
							data = WorkerInfoTransform.Transform(getinfo);
							createJTable(data,title,20,250,midwidth,400);
							createRight(curnode);
						}
					}
					else if(curnode.equals(Constant.accountnode[0]))
					{   //ͨ������������˵���Ϣ
						SocketClient.ConnectSevert(GET_ORDER);
						String getinfo=SocketClient.readinfo;
						title=new Vector();//����
						{
							title.add("��̨����");title.add("������");title.add("�˿�����");
							title.add("����ʱ��");title.add("����Աid");title.add("�ܼ۸�");
						}
						if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
						{
							JOptionPane.showMessageDialog
	                  	       (
			       	    		   MainUI.this,
			       	    		   "��ȡ��Ϣʧ��",
			       	    		   "��ʾ",
			       	    		   JOptionPane.INFORMATION_MESSAGE
	                  	    	);
						}
						else{
							if(getinfo.length()==0)//�����ϢΪ�գ�����ʾ��Ϣ"��ϢΪ��"
							{
								JOptionPane.showMessageDialog
		                  	       (
				       	    		   MainUI.this,
				       	    		   "��ϢΪ��",
				       	    		   "��ʾ",
				       	    		   JOptionPane.INFORMATION_MESSAGE
		                  	    	);
						     }//�����ݡ����⴫��createJTable������
							data=TypeExchangeUtil.strToVector(getinfo);
							createJTable(data,title,20,topheight+80,midwidth+100,buttomheight);
							createRight(curnode);
						}
					}				
				}
			}
		}
	//�ڲ���ɾ����ť�ļ���
	 private class DelButtonListener implements ActionListener
	 {
		 String getinfo=null;
		 //�����ť�����ɾ����Ǻ͵õ��������������
	      public void getBackInfo(String dels,String gets)
	      {//����Ϊ����δѡ����
	    	  if(primarykey==null)
	    	  {
	    		  JOptionPane.showMessageDialog//����ʾ��Ϣ"��ѡ��ɾ����"
         	       (
      	    		   MainUI.this,
      	    		   "��ѡ��ɾ����",
      	    		   "��ʾ",
      	    		   JOptionPane.INFORMATION_MESSAGE
         	    	);
			    }
	    	  else
	    	  {//��ʾ�Ƿ�ɾ��
	    	    String backinfo = "";
	            int index=JOptionPane.showConfirmDialog//����ʾ��Ϣ���Ƿ�ɾ��
	            (
	            		MainUI.this,
			           "ȷ��ɾ��"+primarykey,
			           "��ʾ",
			           JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE
			    );
		        if(index==0)//�����0��ִ��
		          {    	 
		    	    SocketClient.ConnectSevert(dels+primarykey);//����ɾ��
		    	    backinfo=SocketClient.readinfo;
					if(backinfo.equals("ok"))
					{
						SocketClient.ConnectSevert(gets);
						getinfo=SocketClient.readinfo;
						if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
						{
							JOptionPane.showMessageDialog
	               	       (
			       	    		   MainUI.this,
			       	    		   "��ȡ��Ϣʧ��",
			       	    		   "��ʾ",
			       	    		   JOptionPane.INFORMATION_MESSAGE
	               	    	);
						}
						else
						{
							data=TypeExchangeUtil.strToVector(getinfo);//����õ���Ϣת��Ϊ��������
						}
					}
					//��TABle������0�������ÿ�
					row=0;
					primarykey=null;
		        }
		     }
	      }
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JButton b=(JButton) e.getSource();
				if(b.getText().equals("ɾ��������λ"))
				{
					getBackInfo(DEL_CU,GET_CU);//����getBackInfo()����ɾ����Ǻ͵õ��������������
					createJTable(data,title,20,topheight,midwidth,buttomheight);
					createRight(curnode);
				}
				else if(b.getText().equals("ɾ����ϵ"))
				{
					getBackInfo(DEL_VT,GET_VT);//����getBackInfo()����ɾ����Ǻ͵õ��������������
					createJTable(data,title,20,topheight,midwidth,buttomheight);
					createRight(curnode);
				}
				else if(b.getText().equals("ɾ������"))
				{
					getBackInfo(DEL_CMG,GET_MCG);//����getBackInfo()����ɾ����Ǻ͵õ��������������
					createJTable(data,title,20,topheight,midwidth,buttomheight);
					createRight(curnode);
				}
				else if(b.getText().equals("ɾ�����"))
				{
					getBackInfo(DEL_CG,GET_CG);//����getBackInfo()����ɾ����Ǻ͵õ��������������
					createJTable(data,title,20,topheight,midwidth,buttomheight);
					createRight(curnode);
				}
				else if(b.getText().equals("ɾ�����"))
				{
					getBackInfo(DEL_VS,GET_VS);//����getBackInfo()����ɾ����Ǻ͵õ��������������
					createJTable(data,title,20,topheight,midwidth,buttomheight);
					createRight(curnode);
				}
				else if(b.getText().equals("ɾ����Ʒ"))
				{
					getBackInfo(DEL_VEGE,GET_VEGE);//����getBackInfo()����ɾ����Ǻ͵õ��������������
					createJTable(data,title,20,topheight,midwidth,buttomheight);
					createRight(curnode);
				}
				else if(b.getText().equals("ɾ����̨"))
				{
					getBackInfo(DEL_POINT,GET_POINT+curnode.toString());//����getBackInfo()����ɾ����Ǻ͵õ��������������
					createJTable(data,title,20,topheight,midwidth,buttomheight+100);
					createRight(curnode);
					
				}
			    else if(b.getText().equals("ɾ��Ա��"))
			    {
					getBackInfo(DEL_WORKERINFO,GET_WORKERINFO);//����getBackInfo()����ɾ����Ǻ͵õ��������������
					createJTable(data,title,20,250,midwidth,400);
					createRight(curnode);
				}
			}
		}
	//���°�ť�ļ���
	 private class UpdateButtonListener implements ActionListener
	 {
    //���²���
		public void getBackInfo(String s, String gets)
		{
    	 StringBuffer info=new StringBuffer();//�����ַ���info
			for(int i=0;i<values.length;i++)//����õ�ֵ��ӵ�info��
			{
			  info.append(s+values[i]);
			}
			SocketClient.ConnectSevert(info.toString());//��info����������
			String backinfo=SocketClient.readinfo;
			if(backinfo.equals("ok"))//������ء�ok��
			{
				SocketClient.ConnectSevert(gets);//������Ϣ
				String getinfo=SocketClient.readinfo;
				JOptionPane.showMessageDialog//����ʾ��Ϣ"����ɹ�"
       	       (
    	    		   MainUI.this,
    	    		   "����ɹ�",
    	    		   "��ʾ",
    	    		   JOptionPane.INFORMATION_MESSAGE
       	    	);
				if(getinfo.equals("fail"))//������ء�fail��������ʾ��Ϣ"��ȡ��Ϣʧ��"
				{
					JOptionPane.showMessageDialog
           	       (
	       	    		MainUI.this,
	       	    		"��ȡ��Ϣʧ��",
	       	    		"��ʾ",
	       	    		JOptionPane.INFORMATION_MESSAGE
           	    	);
				}
				else
				{    //�����ݡ����⴫��createJTable������
					 data=TypeExchangeUtil.strToVector(getinfo);
					 createJTable(data,title,20,topheight,midwidth,buttomheight);
					 createRight(curnode);
				}
			}
			else if(backinfo.equals("fail"))//������ء�fail��
			{
				JOptionPane.showMessageDialog//����ʾ��Ϣ"����ʧ��"
       	       (
    	    		   MainUI.this,
    	    		   "����ʧ��",
    	    		   "��ʾ",
    	    		   JOptionPane.INFORMATION_MESSAGE
       	    	);
			}
			//��TABle������0�������ÿ�
			row=0;
			primarykey=null;
     }
		@Override
		public void actionPerformed(ActionEvent e)
		{
	       JButton b=(JButton) e.getSource();
		   if(primarykey==null)//�������Ϊ�գ�����ʾ��Ϣ"��ѡ���޸���"
		   {
			  JOptionPane.showMessageDialog
    	       (
 	    		   MainUI.this,
 	    		   "��ѡ���޸���",
 	    		   "��ʾ",
 	    		   JOptionPane.INFORMATION_MESSAGE
    	    	);
		    }
		 else
		 {
			if(b.getText().equals("���������λ"))
			{
				getBackInfo(UPDATE_CU, GET_CU);//����getBackInfo��������������
				createJTable(data,title,20,topheight,midwidth,buttomheight);
			      createRight(curnode);
			}else if(b.getText().equals("�����ϵ"))
			{
				getBackInfo(UPDATE_VT, GET_VT);//����getBackInfo��������������
				createJTable(data,title,20,topheight,midwidth,buttomheight);
			      createRight(curnode);
			}else if(b.getText().equals("��������"))
			{
				getBackInfo(UPDATE_CMG, GET_MCG);//����getBackInfo��������������
				createJTable(data,title,20,topheight,midwidth,buttomheight);
			      createRight(curnode);
			}else if(b.getText().equals("�������"))
			{
				getBackInfo(UPDATE_CG, GET_CG);//����getBackInfo��������������
				createJTable(data,title,20,topheight,midwidth,buttomheight);
			      createRight(curnode);
			}else if(b.getText().equals("������"))
			{
				getBackInfo(UPDATE_VS, GET_VS);//����getBackInfo��������������
				createJTable(data,title,20,topheight,midwidth,buttomheight);
			      createRight(curnode);
			}
			else if(b.getText().equals("�޸Ĳ�̨"))
			{ 
			      new UpdatePoint(curnode,MainUI.this);//����UpdatePoint���������²�̨
		    }
			}
		}
	}
	 //��Ӱ�ť�ļ���
	 private class AddButtonListener implements ActionListener
	 {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JButton b=(JButton) e.getSource();
				if(b.getText().equals("��Ӽ�����λ"))
				{
					getBackInfo(GET_CUMAXNO);//����getBackInfo�����������Ϣ
				}else if(b.getText().equals("��Ӳ�ϵ"))
				{
					getBackInfo(GET_VTMAXNO);//����getBackInfo�����������Ϣ
				}else if(b.getText().equals("��ӹ��"))
				{
					getBackInfo(GET_VSMAXNO);//����getBackInfo�����������Ϣ
				}

				else if(b.getText().equals("������"))
				{
					getBackInfo(GET_CGMAXNO);//����getBackInfo�����������Ϣ
				}
				else if(b.getText().equals("��Ӳ�̨"))
				{
					 try 
					 {//windows���
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					 } 
					 catch (Exception e1) 
					 {
						e1.printStackTrace();
					 }
					new AddPoint(curnode,MainUI.this);//����AddPiont��������Ӳ�̨
				}
			}
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void getBackInfo(String s)//�����Ϣ����
			{
				DefaultTableModel dtm=(DefaultTableModel) jtable.getModel();
				Vector newvc=new Vector();
				if(s.equals(GET_AUTHROLEMAXNO))//���Ա����Ϣ
				{
					newvc.add("");
					newvc.add("");
				}
				else
				{
					SocketClient.ConnectSevert(s);//ͨ�������������Ϣ
					String index=SocketClient.readinfo;
					System.out.println("index="+index);
					String in=((Integer.parseInt(index)+1)<10)?("00"+(Integer.parseInt(index)+1)):
									(((Integer.parseInt(index)+1)<100)?
											("0"+(Integer.parseInt(index)+1)):((Integer.parseInt(index)+1)+""));
					
					if(s.equals(GET_SA_MAXNO)||s.equals(GET_VSMAXNO))//��ӹ����Ϣ
					{
						newvc.add(String.valueOf(in));
						newvc.add("");	
					}
					else if(s.equals(GET_AUTHORITYMAXNO) || s.equals(GET_ROLEMAXNO))//��ӽ�ɫ��Ϣ
					{	
						newvc.add(in);
						newvc.add("");
					}
					else 
					{
						newvc.add(String.valueOf(in));//���������Ϣ
						newvc.add("");
						newvc.add("����");
					}
				}
			    dtm.getDataVector().add(newvc);//������Ϣ��ӵ�����
			    ((DefaultTableModel)jtable.getModel()).fireTableStructureChanged();//֪ͨ���м���������Ľṹ�Ѹ���
			    //����JTable�������ݵĳ��ȵ����п�
	            FitTableColumns(jtable);
	            //��������ӵ��еĵ�1��Ϊ�༭״̬
	            jtable.changeSelection(jtable.getRowCount()-1,1,false,false);
			    row=0;
				primarykey="0";
			}
		}
	 public static void main(String[] args)//������
	 {
		try 
		{//windows���
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		new MainUI();
	}
}