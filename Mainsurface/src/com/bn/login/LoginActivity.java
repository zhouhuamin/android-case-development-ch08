package com.bn.login;

import static com.bn.login.LoginConstant.*;
import com.bn.R;
import com.bn.error.ResetErrorActivity;
import com.bn.main.MainActivity;
import com.bn.main.ProgressBarActivity;
import com.bn.selectvege.SelectVegeActivity;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
/* ��½���棬�����û��������벢����Ҫ�е�½Ȩ�޲ſɵ�½�ɹ���������������*/
public class LoginActivity extends Activity 
{
	 public  SharedPreferences sp;
	 //��ʾ����Ի���
	 public Dialog errorDialog;
	 public String errorMsg;
	 //��¼��¼�˵�id������
	 String userid;
	 String userpw;
	 String did;//��¼��SharedPreferences��ȡ�ļ�¼������
	 Bundle b;
	 //��Ҫʹ�õĿؼ�
	 ToggleButton isrem;
	 EditText loginid;
	 EditText loginpw;
	 Handler handler=new Handler()
	 {
		public void handleMessage(Message msg) 
	   	   {
			  switch(msg.what)
			  {
			    //��Ȩ��½��Ϣ��ʾ
			    case SHOW_AUTH_TOST_MESSAGE:
			    	  b=msg.getData();
			    	 String showmessage=b.getString("msg");
			    	 Toast.makeText(LoginActivity.this,showmessage,Toast.LENGTH_LONG).show();
			    	 loginid.setText("");
					 loginpw.setText("");
			    break;
			    //��Ȩ�޳��ִ���ʱ�򿪴���Ի���
			    case SHOW_AUTH_ERROR_MESSAGE:
			    	//��ȡ��Ϣ�е�����
					  b=msg.getData();
					  ResetErrorActivity.errorMsg=b.getString("msg");
					  ResetErrorActivity.errorFlg="LoginActivityFlg";
				      Intent intent=new Intent(LoginActivity.this,ResetErrorActivity.class);
					  startActivity(intent);
					  LoginActivity.this.finish();
					  overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
			    break;
			    case LOGIN_SUCCESS:
			    	 b=msg.getData();
			    	 String showm=b.getString("msg");
			    	//�Ƿ��ס
				     if(isrem.isChecked())
				       {
					    	//�����ȡ��ǰ����Ա���Ϊ�մ���
					    	if(did==null)
					    	{		
					    		SharedPreferences.Editor editor=sp.edit();
								editor.putString("user",userid);
								editor.commit();
					    	}
				    	}
				     //�����ʼ���Ź���Ľ�����
					 String action=b.getString("action");
					 intent=new Intent(LoginActivity.this,ProgressBarActivity.class);
					 intent.putExtra("resource", "login");
					 intent.putExtra("Action", action);
					 startActivity(intent);
		   			 LoginActivity.this.finish();
				     Toast.makeText(LoginActivity.this,showm,Toast.LENGTH_LONG).show();
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
	 			 Intent intent=new Intent(LoginActivity.this,SelectVegeActivity.class);
	 			 startActivity(intent);
	 			 LoginActivity.this.finish();
	 		}
	 	 }
	   @Override
	  public void onCreate(Bundle savedInstanceState)
	  {
		   MainActivity.al.add(this);
	        super.onCreate(savedInstanceState);
	        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        this.getWindow().getDecorView().setSystemUiVisibility(4);
	        setContentView(R.layout.loginui);
	        this.setFinishOnTouchOutside(false);
	        //��ȡ�ؼ�
	        Button bok=(Button) this.findViewById(R.id.ok);
	        Button breset=(Button)this.findViewById(R.id.reset);
	        loginid=(EditText)this.findViewById(R.id.loginid);
		    loginpw=(EditText) this.findViewById(R.id.loginpw);
    	    isrem= (ToggleButton) this.findViewById(R.id.login_imagebutton);	
			loginid.setBackgroundResource(R.drawable.editview_bk_image);
			loginpw.setBackgroundResource(R.drawable.editview_bk_image);
			//��½��ť�ļ���
	        bok.setOnClickListener
	        (
	         new OnClickListener()
	           {
				 public void onClick(View v) 
				   {
					 //��ȡ������Ϣ
						 userid=loginid.getText().toString();
						 userpw=loginpw.getText().toString();
						 sp=LoginActivity.this.getSharedPreferences("info",Context.MODE_PRIVATE);
					     did=sp.getString("user", null);
					    	//�����ȡ��ǰ����Ա���Ϊ�մ���
					     if(did!=null&&userid.equals(did))
					      {		
								Toast.makeText(LoginActivity.this, did+"Ϊ��ǰʹ���û������¼", Toast.LENGTH_SHORT).show(); 
					    	    LoginActivity.this.finish();
								return;
					      }
					     if(did!=null&&!(userid.equals(did)))
					     {
					    	 Toast.makeText(LoginActivity.this, "��ǰ�Ѿ����û�������ע��", Toast.LENGTH_SHORT).show(); 
					    	 LoginActivity.this.finish();
					    	 return;
					     }
						//�жϵ�¼�����Ȩ��
						 if(userid.equals(""))
						  {
							 Toast.makeText(LoginActivity.this, "�������û����!!!", Toast.LENGTH_SHORT).show(); 
						      return;
						  }
						   if(userpw.equals(""))
						  {
							  Toast.makeText(LoginActivity.this, "����������!!!", Toast.LENGTH_SHORT).show(); 
						      return;
						  }
						 //��½�߳�
						 validateThread();
				    }	
		       }        	
	         );
	        //ȡ����ť����
	        breset.setOnClickListener
	        (
	          new OnClickListener()
	           {
				 public void onClick(View v)
				 {
					LoginActivity.this.finish();
				 }
	           }
	        );
	  }
	 //����Ҫ�������ݶ�����һ���߳���
	 public void validateThread()
	 {
	   	  //�����߳̽����ж�
		  new Thread()
		  {
			  @Override
			  public void run()
			   {
				 try 
				 {
					DataUtil.loginValidate(userid, userpw,handler);
				 }
				 catch (Exception e) 
				  {//��������쳣�ͷ���handler
					 Message msg=new Message();
			    	 msg.what=LoginConstant.SHOW_AUTH_TOST_MESSAGE;
			         Bundle b=new Bundle();
			         b.putString("msg","����δ���ӣ�����������������µ�½");
			         msg.setData(b);
			    	 handler.sendMessage(msg);
			    	 return;
				}
			  }
		  }.start();
	   }
}
