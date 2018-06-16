package com.bn.reset;

import com.bn.R;
import com.bn.error.ResetErrorActivity;
import com.bn.main.MainActivity;
import com.bn.util.DataUtil;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.bn.constant.Constant.*;
/*�����������ӵ�IP��point�����Բ����Ƿ�����*/
public class ResetUIActivity extends Activity
{	
	Dialog backtestDialog;
	SharedPreferences spipandpoint;	
	String gettestmsg;
	Handler handler=new Handler()
	{
		Bundle b;
		public void handleMessage(Message msg) 
   		{
   			super.handleMessage(msg);
   			switch(msg.what)
   			{
				 case ResetConstant.TESTCONNECT:
					 //��ȡ��Ϣ�е�����
					  b=msg.getData();
					 //��ȡ�����ַ���
					  gettestmsg=b.getString("msg");
					  if(gettestmsg.equals("success"))
					   {
						  ResetErrorActivity.errorMsg="��ϲ�������Ӳ��Գɹ�!!!";
						  ResetErrorActivity.errorFlg="ResetUIActivityFlg";
						  Intent intent=new Intent(ResetUIActivity.this,ResetErrorActivity.class);
						  startActivity(intent);
						  overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
					   }
			     break;
				 case ResetConstant.TESTCONNECTERROR:
					 ResetErrorActivity.errorMsg="�Բ������Ӳ���ʧ�ܣ�����������!!!";
					 ResetErrorActivity.errorFlg="ResetUIActivityFlg";
					 Intent intent=new Intent(ResetUIActivity.this,ResetErrorActivity.class);
					 startActivity(intent);
					 overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
				 break;
   			}
   		}
	};
    public void onCreate(Bundle savedInstanceState)
    {
    	MainActivity.al.add(this);
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(4);
        setContentView(R.layout.reseat);
        initReset();
    }
	private void initReset() 
	{
		int[] wh=getwidthorheight();
		spipandpoint=this.getSharedPreferences("ipandpoint", Context.MODE_PRIVATE);
		IP=spipandpoint.getString("ip", ip);
		POINT=spipandpoint.getInt("point", point);
		Button bmain=(Button)this.findViewById(R.id.bsetmain);
		TextView tvset=(TextView)this.findViewById(R.id.tvset);
		Button bsave=(Button)this.findViewById(R.id.bsetsave);
		TextView tvip=(TextView)this.findViewById(R.id.tvsetip);
		final EditText etip=(EditText)this.findViewById(R.id.etsetip);
		TextView tvpoint=(TextView)this.findViewById(R.id.tvsetpoint);
		final EditText etpoint=(EditText)this.findViewById(R.id.etsetpoint);
		Button btest=(Button)this.findViewById(R.id.bsettest);
		//����������ķ��ذ�ť
		bmain.setOnClickListener
		(
		  new OnClickListener()
		  {
			public void onClick(View v) 
			{
				Intent intent=new Intent(ResetUIActivity.this,MainActivity.class);
				startActivity(intent);
				ResetUIActivity.this.finish();
				overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
			}
		  }
		);	
		//����ϵͳ���õ�textview
		tvset.setTextSize(wh[0]/40);
		//���ñ��水ť
		bsave.setOnClickListener
		(
		  new OnClickListener()
		  {
			public void onClick(View v) 
			{
				//��ip��point����preferences��
				String ip=etip.getText().toString();
				int point=Integer.parseInt(etpoint.getText().toString());
				SharedPreferences.Editor editor=spipandpoint.edit();
				//ɾ��preferences�е�ip��point
				editor.remove("ip");
				editor.remove("point");
				//���µ�ip��point����preferences��
				editor.putString("ip", ip);
				editor.putInt("point", point);
				editor.commit();
				Intent intent=new Intent(ResetUIActivity.this,MainActivity.class);
				startActivity(intent);
				ResetUIActivity.this.finish();
				overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
			}
		  }
		);
		//����ip
		tvip.setTextSize(wh[0]/50);
		etip.setTextSize(wh[0]/50);
		etip.setWidth(wh[0]/4);
		etip.setText(IP);
		etip.setBackgroundDrawable(ResetUIActivity.this.getResources().getDrawable(R.drawable.editview_bk_image));
		//����point
		tvpoint.setTextSize(wh[0]/50);
		etpoint.setTextSize(wh[0]/50);
		etpoint.setWidth(wh[0]/4);
		etpoint.setText(String.valueOf(POINT));
		etpoint.setBackgroundDrawable(ResetUIActivity.this.getResources().getDrawable(R.drawable.editview_bk_image));
		//����test
		btest.setTextSize(wh[0]/50);
		
		btest.setOnClickListener
		(
		  new OnClickListener()
		  {
			public void onClick(View v) 
			{
				IP=etip.getText().toString();
				POINT=Integer.parseInt(etpoint.getText().toString());
	            DataUtil.testConnect(handler);
			}
		  }
		);
	}
	@SuppressWarnings("deprecation")
	public int[] getwidthorheight()
    {
 	   int[] worh=new int[2];
 	   /*���ƵĻ�Ŀ�͸�*/
        int screenWidth;//��Ļ���
        int screenHeight;//��Ļ�߶�
        WindowManager windowManager = ResetUIActivity.this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        worh[0]=screenWidth;
        worh[1]=screenHeight;
        return worh;
    }
}
