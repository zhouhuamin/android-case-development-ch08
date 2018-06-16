package com.bn.main;

import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
//import java.util.Map;
import com.bn.R;
//import com.bn.util.*;
//import com.bn.util.DataUtil;
import com.bn.vegeinfo.VegeActivity;
import com.bn.constant.Constant;
import com.bn.login.LoginActivity;
import com.bn.logout.LogoutActivity;
import com.bn.resetlogin.ResetLoginActivity;
import com.bn.selectvege.SelectVegeActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import static com.bn.constant.Constant.IP;
import static com.bn.constant.Constant.POINT;
import static com.bn.main.MainConstant.*;

/*MainActivity�������棬ͨ���Զ���Ļ��ȿؼ�VegeGalleryList��ʾ�������Ƽ����Ĳ�ƷͼƬ������
 ���Զ��尴ť��ButtonListBar����ʾ�˷���Ա�ֳֶ˵Ļ������ܺͰ�����Ϣ�İ�ť��MainActivity��
 �������˰�ťʱ��ΪSelectVegeActivityҪ��ʾ�ĳ�ʼ�����ʼ�����ݣ�����ת֮ǰ��ʾ���ȶԻ���*/
public class MainActivity extends Activity 
{
	public static List<Activity> al=new ArrayList<Activity>();
	//�ݴ�Ի�����Ҫ��ʾ����Ϣ
    public static String waitMsg;
    public static String errorMsg;
    public static int excepFlg;
    public static boolean isHasSD=true;
	ProgressDialog waitDialog;
	Dialog errorDialog;
	Bundle b;
	SharedPreferences sp;
	Handler handler=new Handler()
   	{
//		@SuppressWarnings("unchecked")
		@Override
   		public void handleMessage(Message msg) 
   		{
   			super.handleMessage(msg);
   			Bundle bundle=null;
   			String action=null;
   			Intent intent=null;
   			switch(msg.what)
   			{
			     //�򿪲�Ʒ�Ź���Ի���
				 case OPEN_WAIT_DIALOG_FOR_GRID:
					 bundle=msg.getData();
					 action=bundle.getString("action");
					 intent=new Intent(MainActivity.this,ProgressBarActivity.class);
					 intent.putExtra("Action", action);
					 intent.putExtra("resource", "main");
					 startActivityForResult(intent,2);
			     break;

				 case GOTO_VEGE_INTRO_MESSAGE:
					 intent=new Intent(MainActivity.this,VegeActivity.class);
					 intent.putExtra("source", "MainActivity");
	   				 startActivity(intent);
	   				 overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
			     break;
				 //ֱ����ʾToast��ʾ����
				 case OPEN_ERROR_TOAST_MESSAGE:
					 b = msg.getData();
					 String hint=b.getString("msg");
					 Toast.makeText(MainActivity.this,hint,Toast.LENGTH_LONG).show();
			     break;
   			}
   		}
   	};
   	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
   	{
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==2 && resultCode==2)
		{
			 Intent intent=new Intent(MainActivity.this,SelectVegeActivity.class);
			 startActivity(intent);
			 MainActivity.this.finish();
		}
	 }
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);  
        al.add(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //����status Bar
	    getWindow().getDecorView().setSystemUiVisibility(4);
        setContentView(R.layout.main);     
        //��preferences�л���ϴεõ������ip��point
  		SharedPreferences spipandpoint=MainActivity.this.getSharedPreferences("ipandpoint", Context.MODE_PRIVATE);
  		IP=spipandpoint.getString("ip", Constant.ip);
  		POINT=spipandpoint.getInt("point", Constant.point);
        
        //��ţ̌���ͼƬ
        Bitmap buttonBMUp[]=new Bitmap[6];
        //��ť���µ�ͼƬ
        Bitmap buttonBMDown[]=new Bitmap[6];  
        //��ť��ı���ͼƬ
        Bitmap bmbg=BitmapFactory.decodeResource(this.getResources(), R.drawable.mbuttonback);
        //���ذ�ťͼƬ
        buttonBMUp[0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.setting_normal); //����
        buttonBMUp[1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.order_normal);   //���
        buttonBMUp[2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.logout_normal);  //ע��
        buttonBMUp[3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.exit_normal);   //�˳�
        buttonBMUp[4]=BitmapFactory.decodeResource(this.getResources(), R.drawable.help_normal);    //����
        buttonBMUp[5]=BitmapFactory.decodeResource(this.getResources(), R.drawable.about_normal);   //����
        buttonBMDown[0]=BitmapFactory.decodeResource(this.getResources(), R.drawable.setting_pressed); //����
        buttonBMDown[1]=BitmapFactory.decodeResource(this.getResources(), R.drawable.order_pressed);   //���
        buttonBMDown[2]=BitmapFactory.decodeResource(this.getResources(), R.drawable.logout_pressed);  //ע��
        buttonBMDown[3]=BitmapFactory.decodeResource(this.getResources(), R.drawable.exit_pressed);   //�˳�
        buttonBMDown[4]=BitmapFactory.decodeResource(this.getResources(), R.drawable.help_pressed);    //����
        buttonBMDown[5]=BitmapFactory.decodeResource(this.getResources(), R.drawable.about_pressed); //����

        ButtonListBar butListBar=(ButtonListBar)this.findViewById(R.id.bbar);
        //���ð�ť���ܱ�����̧�𡢰���ͼƬ
        butListBar.setBMSer(bmbg, buttonBMUp, buttonBMDown);
        //���ð�ť�ĸߡ���������ס���ť���
        butListBar.setSizeSer
        (
        	MainConstant.BUTTON_HEIGHT, 
        	MainConstant.BUTTON_WIDTH, 
        	MainConstant.BUTTONBAR_LEFTMARGIN, 
        	MainConstant.BUTTON_SPAN
        );
        //�Կؼ���Ӽ���
        butListBar.addButtonListBarListener
        (
        	new ButtonListBarListener()
        	{
			
				public void onButtonClick(int index) 
				{
					Intent intent;
					switch(index)
					{
					//���ð�ť
					  case 0:
						  intent=new Intent(MainActivity.this,ResetLoginActivity.class);
						  startActivity(intent);
						  overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right); 
					  break;
					  //��˰�ť
					  case 1:
						  sp=MainActivity.this.getSharedPreferences("info",Context.MODE_PRIVATE);
					       String did=sp.getString("user", null);
				    	   if(did!=null)
				    	   {
				    		   //ͨ������ID��ʼ������������Ϣ
								  Message msg=new Message();
								  Bundle b=new Bundle();
								  msg.what=MainConstant.OPEN_WAIT_DIALOG_FOR_GRID;
								  b.putString("action","INIT_VEGEIMAGEGRID");
								  msg.setData(b);
								  handler.sendMessage(msg);
				    	   }
				    	  else
						  {
							  //�򿪵�½����
							  intent = new Intent(MainActivity.this,LoginActivity.class);
							  startActivity(intent);
							  overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
						  }
					  break;
					  case 2:
						  //��ע������
						  sp=MainActivity.this.getSharedPreferences("info",Context.MODE_PRIVATE);
						  String isu=sp.getString("user", null);
						  if(isu!=null)
						  {
						  intent = new Intent(MainActivity.this,LogoutActivity.class);
						  intent.putExtra("source","mainactivity");
						  startActivity(intent);
						  overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
						  }
						  else
						  {
							  Toast.makeText(MainActivity.this, "��ǰ���û���¼������ע����", Toast.LENGTH_SHORT).show();
						  }
					  break;
					  case 3:
						  //������û���ʾ�Ƿ�ע��������ʾ�Ի���
						  sp=MainActivity.this.getSharedPreferences("info",Context.MODE_PRIVATE);
					       String userid=sp.getString("user", null);
				    	   if(userid!=null)
				    	   {
				    		     intent=new Intent(MainActivity.this,ExceptionDialog.class);
			                     intent.putExtra("errormsg","�Ƿ�ע����ǰ�û�");
			                     intent.putExtra("error","logoutCurUser");
			                     intent.putExtra("errortitle", "��Ϣ��ʾ��");
			                     startActivity(intent);
				    	   }
				    	   else
				    	   {
				    		   //�˳�ϵͳ
				    		   for(int i=0;i<MainActivity.al.size();i++)
								{
							           if(MainActivity.al.get(i)!=null)
							           {
							        	   MainActivity.al.get(i).finish();
							           }
							    }
								System.exit(0); 
				    	   }
					  break;
					}
				}
        	}
        );
	}
}