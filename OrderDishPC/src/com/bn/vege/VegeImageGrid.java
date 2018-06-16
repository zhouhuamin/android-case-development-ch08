package com.bn.vege;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;


/*VegeImageGrid�Զ���ؼ�������Ӳ�Ʒ��ͼʱ�õ��Ŀؼ�
 */
@SuppressWarnings("serial")
public class VegeImageGrid extends JScrollPane
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
    static List<File> imageList=new ArrayList<File>();
    static byte[] imagemsg;
    
	protected static File curfile;
	@SuppressWarnings("static-access")
	public VegeImageGrid(int vigwidth, int vigheight, int span2, List<File> imagelist2) 
	{
		this.totalwidth=vigwidth;
		this.totalheight=vigheight;
		this.span=span2;
		this.imageList=imagelist2;
		//����ͼƬ�Ŀ��
		unitWidth=(totalwidth-4*span)/3;
		unitHeight=(totalheight-4*span)/3;
		drawImageGrid();
	}
	public  void drawImageGrid()
	{
		this.setLayout(null);
		if(imageList.size()>0)
		{
			String curpath=curfile.getAbsolutePath().toString();
			Image image = getToolkit().getImage(curpath);  
			image = image.getScaledInstance(unitWidth,unitHeight,Image.SCALE_DEFAULT);
			JButton jb=new JButton();
			jb.setIcon(new ImageIcon(image));
			jb.setBounds(span+((imageList.size()-1)%colNum)*(unitWidth+span), span+((imageList.size()-1)/colNum)*(unitHeight+span), unitWidth, unitHeight);
			this.add(jb);
		}
		int length=span+(imageList.size()/colNum)*(unitHeight+span);
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
	
}
