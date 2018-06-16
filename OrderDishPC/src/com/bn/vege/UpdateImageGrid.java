package com.bn.vege;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JPanel;

/*VegeImageGrid�Զ���ؼ������޸Ĳ�Ʒʱ��Ϊ�޸�ͼƬ���õ��Ŀؼ�
 */
@SuppressWarnings("serial")
public class UpdateImageGrid extends JPanel implements ActionListener
{
	//����
    int colNum=3;
    //��λ���
    static int unitWidth;
    //ͼƬ�߶�
    static int unitHeight;
    //���
    static int span;
    //�Ź���Ŀ��
    static int totalwidth;
    //�Ź���ĸ߶�
    static int totalheight;
    //ͼƬ��Ϣ
    static List<byte[]> imageList;
    static byte[] imagemsg;
    static int mainindex;
    int imagemsglength;
	@SuppressWarnings("static-access")
	public UpdateImageGrid(int vigwidth, int vigheight, int span2, List<byte[]> imagelist2, int mainindex2) 
	{
		this.totalwidth=vigwidth;
		this.totalheight=vigheight;
		this.span=span2;
		this.imageList=imagelist2;
		this.mainindex=mainindex2;
		//����ͼƬ�Ŀ��
		unitWidth=(totalwidth-4*span)/3;
		unitHeight=(totalheight-4*span)/3;
		initdrawImageGrid();
	}
	
	private void initdrawImageGrid()
	{
		this.setLayout(null);//���ý��沼����ΪĬ��
		for(int i=0;i<imageList.size();i++)
		{
			JButton jb=new JButton();//���尴ť
			jb.setIcon(new ImageIcon(imageList.get(i)));//���ð�ťͼ��
			jb.addActionListener(this);//��ť��Ӽ���
			jb.setLayout(null);//��ť������ΪĬ��
			if(i==mainindex)
			{   //���ð�ťλ�����С
				jb.setBounds(span+(i%colNum)*(unitWidth+span)+unitWidth/10/2, span+(i/colNum)*(unitHeight+span)+unitHeight/10/2, unitWidth-unitWidth/10, unitHeight-unitHeight/10);
				JLabel jl=new JLabel();//����JLabel
				jl.setIcon(new ImageIcon("src/com/bn/image/flag.png"));//����JLabel��ͼ��
				jl.setBounds(0, 0, unitWidth/4, unitHeight/6);//����JLabel��λ�����С
				jb.add(jl);
			}else{
				jb.setBounds(span+(i%colNum)*(unitWidth+span), span+(i/colNum)*(unitHeight+span), unitWidth, unitHeight);
			}
			this.add(jb);//����ť��ӵ�������
		}
		int length=span+(imageList.size()/colNum)*(unitHeight+span);//���峤��
		if(imageList.size()%colNum!=0)
		{
		     length=span+(imageList.size()/colNum+1)*(unitHeight+span);
			 this.setPreferredSize(new Dimension(totalwidth, length));
		}
		else
		{
			 this.setPreferredSize(new Dimension(totalwidth, length));
		}
		this.setVisible(true);
	}
	public  void drawImageGrid()
	{
		this.setLayout(null);//������沼����ΪĬ��
		JButton jb=new JButton();//���尴ť
		jb.addActionListener(this);//��ť��Ӽ���
		jb.setIcon(new ImageIcon(imagemsg));//���ð�ťͼ��
		jb.setBounds(span+((imageList.size()-1)%colNum)*(unitWidth+span), span+((imageList.size()-1)/colNum)*(unitHeight+span), unitWidth, unitHeight);
		this.add(jb);
		int length=span+(imageList.size()/colNum)*(unitHeight+span);
		if(imageList.size()%colNum!=0)
		{
		     length=span+(imageList.size()/colNum+1)*(unitHeight+span);
			 this.setPreferredSize(new Dimension(totalwidth, length));//���ý������ѡ��С
		}
		else
		{
			 this.setPreferredSize(new Dimension(totalwidth, length));
		}
		this.setVisible(true);
	}
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton jb=(JButton) e.getSource();
		int x=jb.getX();
		int y=jb.getY();
		int row=y/(span+unitHeight);
		int col=x/(span+unitWidth);
		int index=row*this.colNum+col;
		this.removeAll();
		this.mainindex=index;
		this.initdrawImageGrid();
		this.updateUI();
	}
}
