package com.bn.logout;

import com.bn.R;
import com.bn.error.ResetErrorActivity;
import com.bn.main.MainActivity;
import com.bn.util.DataUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import static com.bn.logout.LogoutConstant.*;
/*�û�����*/
public class LogoutActivity extends Activity 
{
	public SharedPreferences sp=null;
	 //��ʾ����Ի���
	 public String errorMsg;
	 public String pointMsg;
	 //��¼��¼�˵�id������
	 String userid;
	 String userpw;
	 Bundle b;
	 //��Ҫʹ�õĿؼ�
	 ToggleButton isrem;
	 EditText logoutid;
	 EditText logoutpw;
	 Intent intent;
	 public static String sourceFlg=null;
	 Handler handler=new Handler()
	 {
		public void handleMessage(Message msg) 
		{
		  switch(msg.what)
		  {
		    //��Ȩ��½��Ϣ��ʾ
		    case SHOW_AUTH_TOST_MESSAGE:
		    	 Bundle b=msg.getData();
		    	 String showmessage=b.getString("msg");
		    	 Toast.makeText(LogoutActivity.this,showmessage,Toast.LENGTH_LONG).show();
		    	 LogoutActivity.this.finish();
		    break;
		    //��ע���Ի���
		    case CANCEL_LOGIN_MESSAGE:
		    	  b=msg.getData();
				  ResetErrorActivity.errorMsg=b.getString("msg");
				  ResetErrorActivity.errorFlg="CancelLogoutActivityFlg";
				  intent=new Intent(LogoutActivity.this,ResetErrorActivity.class);
				  startActivity(intent);
				  LogoutActivity.this.finish();
				  overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
		    break;
		    case SHOW_AUTH_ERROR_MESSAGE:
		    	//��ȡ��Ϣ�е�����
		    	  b=msg.getData();
				  ResetErrorActivity.errorMsg=b.getString("msg");
				  ResetErrorActivity.errorFlg="LogoutActivityFlg";
			      Intent intent=new Intent(LogoutActivity.this,ResetErrorActivity.class);
				  startActivity(intent);
				  LogoutActivity.this.finish();
				  overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
		    break;
		  }
		}
	 };	 	
	   @Override
	  public void onCreate(Bundle savedInstanceState) 
	  {
		   MainActivity.al.add(this);
	        super.onCreate(savedInstanceState);
	        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        this.getWindow().getDecorView().setSystemUiVisibility(4);
	        setContentView(R.layout.logout);
	        this.setFinishOnTouchOutside(false);
	        //��ȡ�ؼ�
	        Button blogoutok=(Button) this.findViewById(R.id.logoutok);
	        Button blogoutreset=(Button)this.findViewById(R.id.logoutreset);
	        logoutid=(EditText)this.findViewById(R.id.logoutid);
	   	    logoutpw=(EditText) this.findViewById(R.id.logoutpw);
	   	    logoutid.setBackgroundResource(R.drawable.editview_bk_image);
	   	    logoutpw.setBackgroundResource(R.drawable.editview_bk_image);
	   	    //���ִ�mainActivity ��������ExceptionDialog����
	   	    Intent intent=this.getIntent();
	   	    sourceFlg=intent.getStringExtra("source");
			//ע����ť�ļ���
	    	 blogoutok.setOnClickListener
	         (
	           new OnClickListener()
	            {
				  public void onClick(View v) 
				   {
					 //��ȡ������Ϣ
					    userid=logoutid.getText().toString();
						userpw=logoutpw.getText().toString();
						//�жϵ�¼�����Ȩ��
						 if(userid.equals(""))
						  {
							 Toast.makeText(LogoutActivity.this, "�������û����!!!", Toast.LENGTH_SHORT).show(); 
						  }
						  else if(userpw.equals(""))
						  {
							  Toast.makeText(LogoutActivity.this, "����������!!!", Toast.LENGTH_SHORT).show(); 
						  }
						  else
						  {
							  validateThread();
						  }
				    }	
		       }        	
	         );
	        //ȡ����ť����
	     blogoutreset.setOnClickListener
	        (
	          new OnClickListener()
	           {
				 public void onClick(View v)
				 {
					LogoutActivity.this.finish();
				 }
	           }
	        );
	  	}
	   public void validateThread()
	   {
		   //�����߳̽����ж�
			  new Thread()
			  {
				  public void run()
				  {
					 this.setName("validateThread---LogoutActivity");
				     boolean isHasLoginAuth;
					try {
						 isHasLoginAuth = DataUtil.logoutValidate(userid, userpw,handler);
					     if(isHasLoginAuth==false)
					     {//���û�е�½Ȩ�޷���handler
					    	 Message msg=new Message();
					    	 msg.what=LogoutConstant.SHOW_AUTH_TOST_MESSAGE;
					         Bundle b=new Bundle();
					         b.putString("msg","  �Բ��������������  ");
					         msg.setData(b);
					    	 handler.sendMessage(msg);
					     }
					     else
					     {//�����ȷ����ע��ȷ�϶Ի���
					    	 sp=getSharedPreferences("info",Context.MODE_PRIVATE);
					    	 if(sp.getString("user",null).equals(userid))
					    	 {
					    		 Message msg=new Message();
						    	 msg.what=LogoutConstant.CANCEL_LOGIN_MESSAGE;
						         Bundle b=new Bundle();
						         b.putString("msg","     ��ȷ��Ҫע��!!!   ");
						         msg.setData(b);
						    	 handler.sendMessage(msg);
					    	 }
					    	 else
					    	 {
					    		 Message msg=new Message();
						    	 msg.what=LogoutConstant.SHOW_AUTH_TOST_MESSAGE;
						         Bundle b=new Bundle();
						         b.putString("msg","��δ��¼����ע��������");
						         msg.setData(b);
						    	 handler.sendMessage(msg);
					    	 }
					     }
					} 
					catch (Exception e)
					{
						e.printStackTrace();
						 Message msg=new Message();
				    	 msg.what=LogoutConstant.SHOW_AUTH_TOST_MESSAGE;
				         Bundle b=new Bundle();
				         b.putString("msg","��������⣬�����Ƿ����Ӻ�����");
				         msg.setData(b);
				    	 handler.sendMessage(msg);
					}
				  }
			  }.start();
	      }
  }

