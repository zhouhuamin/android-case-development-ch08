package com.bn.vegeinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.bn.R;
import com.bn.main.MainActivity;
import com.bn.vegeimage.VegeImageUIActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class VegeActivity extends Activity 
{
	//��ǰ��������Ĳ�Ʒ����Ϣ
	public static List<String[]> dcvegemsg=new ArrayList<String[]>();
	//��Ų�ƷͼƬ����Ϣmap
	public static Map<String , Object> vegeintromap;
	VegeImageList vil;
	//��ƷͼƬ
	Bitmap[] vegeimage;
	//��Ʒ��Ϣ
	String[] vegeinfo;
	String source;
	protected void onCreate(Bundle savedInstanceState)
	{
		MainActivity.al.add(this);
		super.onCreate(savedInstanceState);
		//ǿ�ƺ������ޱ��⣬ȫ��
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().getDecorView().setSystemUiVisibility(4);
		setContentView(R.layout.vegeimage);
		//��ȡ��Ϣ
	    vegeimage=(Bitmap[]) vegeintromap.get("vegebminfo");
	    vegeinfo=(String[]) vegeintromap.get("vegeinfo");
	    //�������Ϊ��������Ƽ�����ģ�����Ի�ȡ��source.equals("MainActivity")
	    source=getIntent().getStringExtra("source");
	    
	    vil=(VegeImageList) this.findViewById(R.id.viil);
	    //��ʼ����Ʒ�ؼ�
	    initvegeimage();
	    
	    //�õ�textview��ʵ��
	    TextView tvcl=(TextView)this.findViewById(R.id.ivcl);
		
	    tvcl.setText("���ܣ�"+vegeinfo[3]);
	    tvcl.setTextSize(VegeConstant.FONT_SIZE);
		
		TextView tv=(TextView)this.findViewById(R.id.vivegename);
		//Button bdc=(Button)this.findViewById(R.id.vibdc);
		Button bexit=(Button)this.findViewById(R.id.viexit);
		
		//���ò�Ʒ���ƺͼ۸�
		tv.setText(vegeinfo[1]+"     "+"��"+vegeinfo[2]+"/"+vegeinfo[4]);
		//tv.setTextSize(25);
		
		//���÷��ذ�ť
		bexit.setOnClickListener(
		  new OnClickListener()
		  {
			public void onClick(View arg0) 
			{
				Intent intent=new Intent();
				if(source!=null)
				{//������������棨�����治�ٳ��ֽ�������
					intent.setClass(VegeActivity.this, MainActivity.class);
					intent.putExtra("selectvege", "selectvege");
				}
				else
				{
					intent.setClass(VegeActivity.this, VegeImageUIActivity.class);
				}
				 VegeImageUIActivity.onclickflag=true;
				startActivity(intent);
				VegeActivity.this.finish();
				overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
			}		
		  }
		);
	}
	//��ʼ����ƷͼƬ�ؼ�����Ϣ
	void initvegeimage()
	{
        vil.startY=0;
		vil.setBMser(vegeimage);
		vil.setSizeser(VegeConstant.VEGEIMAGE_WIDTH, VegeConstant.VEGEIAMGE_HEIGHT, VegeConstant.VEGE_PIC_SPAN);
		vil.calTotalHeight();
	}
}