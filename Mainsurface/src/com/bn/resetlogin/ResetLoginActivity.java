package com.bn.resetlogin;

import com.bn.R;
import com.bn.constant.Constant;
import com.bn.error.ResetErrorActivity;
import com.bn.main.MainActivity;
import com.bn.resetmain.ResetDialogActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetLoginActivity extends Activity 
{
	 //��¼��¼�˵�id������
	 String userpw;
	 //��Ҫʹ�õĿؼ�
	 EditText loginpw;
	   @Override
	  public void onCreate(Bundle savedInstanceState)
	  {
		   MainActivity.al.add(this);
	        super.onCreate(savedInstanceState);
	        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        this.getWindow().getDecorView().setSystemUiVisibility(4);
	        setContentView(R.layout.administratordialog);
	        this.setFinishOnTouchOutside(false);
	        //��ȡ�ؼ�
	        Button bok=(Button) this.findViewById(R.id.ok);
	        Button breset=(Button)this.findViewById(R.id.reset);
		    loginpw=(EditText) this.findViewById(R.id.loginpw);
			loginpw.setBackgroundResource(R.drawable.editview_bk_image);
			//��½��ť�ļ���
	        bok.setOnClickListener
	        (
	         new OnClickListener()
	           {
				 public void onClick(View v) 
				   {
					 //��ȡ������Ϣ
						 userpw=loginpw.getText().toString();
						//�жϵ�¼�����Ȩ��
						  if(userpw.equals(""))
						  {
							  Toast.makeText(ResetLoginActivity.this, "����������!!!", Toast.LENGTH_SHORT).show(); 
						  }
						  else
						  {
							  if(userpw.equals(Constant.RESETPASSWORD))
							  {
								  Intent intent=new Intent(ResetLoginActivity.this,ResetDialogActivity.class);
								  startActivity(intent);
								  ResetLoginActivity.this.finish();
								  overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
							  }
							  else
							  {
								  ResetErrorActivity.errorMsg="����������������������룡����";
								  ResetErrorActivity.errorFlg="ResetLoginActivityFlg";
								  Intent intent=new Intent(ResetLoginActivity.this,ResetErrorActivity.class);
								  startActivity(intent);
								  ResetLoginActivity.this.finish();
								  overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
							  }
						  }
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
					ResetLoginActivity.this.finish();
				 }
	           }
	        );
	  }
}
