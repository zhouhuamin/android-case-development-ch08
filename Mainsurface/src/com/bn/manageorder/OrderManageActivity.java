package com.bn.manageorder;

import java.util.ArrayList;
import java.util.List;
import com.bn.constant.Constant;
import com.bn.selectvege.SelectVegeActivity;
import com.bn.util.DataUtil;
//import com.bn.util.TypeExchangeUtil;
import com.bn.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static com.bn.manageorder.OrderManageConstant.*;

public class OrderManageActivity extends Activity
{
	public static List<String[]> orderDishConfirm; 
	public static List<String[]> vegeInforesult; 
	List<String[]> causeStore=new ArrayList<String[]>();
	public static List<String[]> vegePrimaryKey=new ArrayList<String[]>();
	List<String[]> addSubConfirmList;
	public static String deleteResult; //ɾ����Ʒ������-Constant.OK��Constant.FAIL
	public static String addResult;  //��Ӳ�Ʒ����������-Constant.OK��Constant.FAIL
	public static String subResult;  //���ٲ�Ʒ����������-Constant.OK��Constant.FAIL
	public static String updateResult; //���²�ƷҪ������-Constant.OK��Constant.FAIL
	ListView orderInfoLV;
	String orderId;
	MyOrderManageAdapter myOrderManageAdapter;
	String deleteVegeCertainInfo[]=new String[4];  //���orderdishconfirm��������������ĸ��ֶκ��˲�ԭ��id
	String cancelVegeCertainInfo[]=new String[4];  //���orderdishconfirm��������������ĸ��ֶκ��˲�ԭ��id
	public static String deskName[];
	int tempModifiedPosi; //��¼��ʱ���޸ĵ�λ��
	String currentDeskName;
	TextView tv_waiterno; //����Ա���
	public static TextView tv_orderid;  //����id
	TextView tv_ordertime;  //�µ�ʱ��
	TextView tv_deskName;   //��̨id
	TextView tv_allMoney;
	String saveFlag="";
	String queryFlag="";
	String queryDeskName;
	int curExcepSource;   //��¼�쳣����Դ
	int selectedPosi;
	public static boolean alreadExistOrder;
	//�жϸò�Ʒ��ǰʱ���Ƿ�����˲�
	Handler handler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			Intent intent=null;
			Bundle bundle=null;
			switch(msg.what)
			{
				case INIT_ORDERMANAGE:
					if(orderDishConfirm!=null && orderDishConfirm.size()!=0)
					{
						myOrderManageAdapter=new MyOrderManageAdapter();
						orderInfoLV.setAdapter(myOrderManageAdapter);				
						tv_waiterno.setText(orderDishConfirm.get(0)[0]);
						tv_orderid.setText(orderDishConfirm.get(0)[1]);
						tv_ordertime.setText(orderDishConfirm.get(0)[2]);
						tv_deskName.setText( currentDeskName);						
						//�����ܽ��
						float allMoney=0;
						for(String[] str:orderDishConfirm)
						{
							allMoney=allMoney+Float.parseFloat(str[5])*Float.parseFloat(str[4]);
						}
						tv_allMoney.setText(allMoney+"");
					}
				break;
				case TOAST_HINT:
					bundle=msg.getData();
					String hintMsg=bundle.getString("hint");
					Toast.makeText(OrderManageActivity.this,hintMsg, Toast.LENGTH_SHORT).show();
				break;
				case FRESH_ADAPTER_DATA:
					//�����ܽ��
					float allMoney=0;
					if(orderDishConfirm!=null && orderDishConfirm.size()!=0)
					{
						for(String[] str:orderDishConfirm)
						{
							allMoney=allMoney+Float.parseFloat(str[5])*Float.parseFloat(str[4]);
						}
					}
					tv_allMoney.setText(allMoney+"");
					orderInfoLV.setAdapter(myOrderManageAdapter);					
				break;
				case ADD_SUB_DEL_RESULT_HANDLE:
					bundle=msg.getData();
					String action=bundle.getString("action");
					//ɾ������
					if(action.equals("DELETE"))
					{
						if(tempModifiedPosi<=orderDishConfirm.size()-1)
						{
							orderDishConfirm.remove(tempModifiedPosi);  //�Ӳ�Ʒ��Ϣ��ɾ���ò�Ʒ							
							handler.sendEmptyMessage(FRESH_ADAPTER_DATA);
						}
					}
					//���ٲ�Ʒ����
					else if(action.equals("SUB"))
					{
						float vegeCount=Float.parseFloat(orderDishConfirm.get(tempModifiedPosi)[4]);
						if(vegeCount>1)
						{
							orderDishConfirm.get(tempModifiedPosi)[4]=vegeCount-1+"";
						}						
						handler.sendEmptyMessage(FRESH_ADAPTER_DATA);
					}
					//���Ӳ�Ʒ����
					else if(action.equals("ADD"))
					{
							float vegeCount=Float.parseFloat(orderDishConfirm.get(tempModifiedPosi)[4]);
							//��Ʒ����
							orderDishConfirm.get(tempModifiedPosi)[4]=vegeCount+1+"";
							handler.sendEmptyMessage(FRESH_ADAPTER_DATA);						
					}
						
				break;
				//�쳣����Ի���
				case OPEN_ERROR_DIALOG_MESSAGE:
					bundle=msg.getData();
					String excepHint=bundle.getString("msg");
					curExcepSource=bundle.getInt("excepFlg");
					intent=new Intent(OrderManageActivity.this,ExceptionHandleDialog.class);
					intent.putExtra("excepHint", excepHint);
					startActivityForResult(intent,4);
				break; 
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//ǿ�ƺ������ޱ��⣬ȫ��
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����status Bar
	    getWindow().getDecorView().setSystemUiVisibility(4);
	    setContentView(R.layout.ordermanage);		
	    orderInfoLV = (ListView)this.findViewById(R.id.om_listview01);
		tv_waiterno = (TextView)this.findViewById(R.id.om_tv_waiterno);
		tv_orderid = (TextView)this.findViewById(R.id.om_tv_orderid);
		tv_ordertime = (TextView)this.findViewById(R.id.om_tv_ordertime);
		tv_deskName = (TextView)this.findViewById(R.id.om_tv_deskid);
		tv_allMoney = (TextView)this.findViewById(R.id.om_tv_allmoney);		
		//�����ݽ��г�ʼ��
		currentDeskName = Constant.defaultDeskName;
		if(currentDeskName!=null)
		{
			tv_deskName.setText(currentDeskName);
		}
		initOrderManage(currentDeskName);		
		//Ϊ��ťע�����
		Button but_back = (Button)this.findViewById(R.id.om_but_back);
		//���ذ�ť
		but_back.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
				if(orderDishConfirm!=null)
				{
					orderDishConfirm=null;
				}
				if(vegeInforesult!=null)
				{
					vegeInforesult=null;
				}
				Intent intent=new Intent(OrderManageActivity.this,SelectVegeActivity.class);
				startActivity(intent);
				OrderManageActivity.this.finish();
				overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
			}			
		});	
		orderInfoLV.setOnItemClickListener
		(
		  new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int posi,
						long arg3) 
				{
					selectedPosi=posi;
					myOrderManageAdapter.notifyDataSetInvalidated();
				}
			}
		 );
	}	
	public void initOrderManage(final String deskName)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				DataUtil.initOrderManageInfo(deskName,handler);
				if(vegeInforesult!=null)
				{
					orderDishConfirm=vegeInforesult;
					handler.sendEmptyMessage(OrderManageConstant.INIT_ORDERMANAGE);
				}
			}
		}.start();
	}	
	class MyOrderManageAdapter extends BaseAdapter
    {
		LayoutInflater inflater=LayoutInflater.from(OrderManageActivity.this);
		public MyOrderManageAdapter(){}
		
		@Override
		public int getCount() 
		{
			if(orderDishConfirm==null)
			{
				return 0;
			}
			return orderDishConfirm.size();
		}
		@Override
		public Object getItem(int arg0) { return null; }
		@Override
		public long getItemId(int arg0) { return 0; }		
		@SuppressWarnings("static-access")
		@Override
		public View getView(final int posi, View arg1, ViewGroup arg2) 
		{
			LinearLayout llayout = (LinearLayout)inflater.inflate(R.layout.forordermanage, null).findViewById(R.id.fom_linearlayout01);
			llayout.setDescendantFocusability(arg2.FOCUS_BLOCK_DESCENDANTS);//Ŀ�ģ�ʹ��lnearlayout���Ի�ý���
			if(selectedPosi==posi)
			{
				llayout.setBackgroundResource(R.color.white_yellow);
			}
			//��������
			TextView tv_tableName = (TextView)llayout.getChildAt(0);
			tv_tableName.setText(orderDishConfirm.get(posi)[7]);
			tv_tableName.setPadding(5,5,5,5);
			tv_tableName.setGravity(Gravity.LEFT);
			//��Ʒ����
			TextView tv_vegename = (TextView)llayout.getChildAt(1);
			tv_vegename.setText(orderDishConfirm.get(posi)[8]);
			tv_vegename.setPadding(5,5,5,5);
			tv_vegename.setGravity(Gravity.LEFT);
			//���ٲ�Ʒ����
			Button but_subCount=(Button)llayout.getChildAt(2);
			but_subCount.setBackgroundResource(R.drawable.buttondec);
			//��Ʒ����
			final TextView tv_vegecount = (TextView)llayout.getChildAt(3);
			tv_vegecount.setText(orderDishConfirm.get(posi)[4]);
			tv_vegecount.setPadding(5,5,5,5);
			//���Ӳ�Ʒ����
			Button but_addCount = (Button)llayout.getChildAt(4);
			but_addCount.setBackgroundResource(R.drawable.buttonadd);
			but_addCount.setOnClickListener( new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					tempModifiedPosi=posi;
					addVegeSingleCount(posi);
				}
			});
			//���ٲ�Ʒ���� ��ťע�����
			but_subCount.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					tempModifiedPosi=posi;
					if(Float.parseFloat(tv_vegecount.getText().toString())>1)
					{
							subVegeSingleCount(posi);
					}
				}
			}
		);
			//��Ʒ�۸�
			TextView tv_vegeprice = (TextView)llayout.getChildAt(5);
			tv_vegeprice.setText(orderDishConfirm.get(posi)[5]);
			tv_vegeprice.setPadding(5,5,5,5);
			//ɾ����ť
			final Button but_delete = (Button)llayout.getChildAt(6);
			but_delete.setBackgroundResource(R.drawable.buttondel);
			but_delete.setOnClickListener( new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					tempModifiedPosi=posi;
					deleteVegeInfo(posi);
				}
			}
		);
			return llayout;
		}
	}	
		public String getNameFromId(String id, List<String[]> listName)
		{
			for(String[] arr: listName)
			{
				if(arr[0].equals(id))
				{
					return arr[1];
				}
			}
			return null;
		}
	public void addVegeSingleCount(final int posi)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				this.setName("addVegeSingleCountThread--OrderManageActivity");
				//�ĸ������ֶ���ɵ�����
				cancelVegeCertainInfo[0]=orderDishConfirm.get(posi)[0]; //odc_user_id
				cancelVegeCertainInfo[1]=orderDishConfirm.get(posi)[1]; //odc_orderId
				cancelVegeCertainInfo[2]=orderDishConfirm.get(posi)[3]; //odc_vege_id
				cancelVegeCertainInfo[3]=orderDishConfirm.get(posi)[6]; //odc_code
				addResult=DataUtil.addVegeCount(cancelVegeCertainInfo);
				Message msg=new Message();
				Bundle bundle=new Bundle();
				msg.what=OrderManageConstant.ADD_SUB_DEL_RESULT_HANDLE;
				bundle.putString("action", "ADD");
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		}.start();
	}	
	public void subVegeSingleCount(final int posi)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				this.setName("subVegeSingleCountThread--OrderManageActivity");
				//�ĸ������ֶ���ɵ�����
				cancelVegeCertainInfo[0]=orderDishConfirm.get(posi)[0]; //odc_user_id
				cancelVegeCertainInfo[1]=orderDishConfirm.get(posi)[1]; //odc_orderId
				cancelVegeCertainInfo[2]=orderDishConfirm.get(posi)[3]; //odc_vege_id
				cancelVegeCertainInfo[3]=orderDishConfirm.get(posi)[6]; //odc_code
				subResult=DataUtil.subVegeCount(cancelVegeCertainInfo);
				Message msg=new Message();
				Bundle bundle=new Bundle();
				msg.what=OrderManageConstant.ADD_SUB_DEL_RESULT_HANDLE;
				bundle.putString("action", "SUB");
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		}.start();
	}	
	public void deleteVegeInfo(final int posi)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				this.setName("deleteVegeInfoThread--OrderManageActivity");
//				int cvCausePosi=Integer.parseInt(causeStore.get(posi)[1]);
				//�ĸ������ֶ���ɵ�����
				deleteVegeCertainInfo[0]=orderDishConfirm.get(posi)[0]; //odc_user_id
				deleteVegeCertainInfo[1]=orderDishConfirm.get(posi)[1]; //odc_orderId
				deleteVegeCertainInfo[2]=orderDishConfirm.get(posi)[3]; //odc_vege_id
				deleteVegeCertainInfo[3]=orderDishConfirm.get(posi)[6]; //odc_code
//				deleteVegeCertainInfo[4]=cancelDishCause.get(cvCausePosi)[0]; //�˲�ԭ��id
				//�жϸò�Ʒ��ǰʱ���Ƿ�����˲�
				deleteResult=DataUtil.deleteVegeInfo(deleteVegeCertainInfo);
				Message msg=new Message();
				Bundle bundle=new Bundle();
				msg.what=OrderManageConstant.ADD_SUB_DEL_RESULT_HANDLE;
				bundle.putString("action", "DELETE");
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
		}.start();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
	{
		if(requestCode==1 && resultCode==1)
		{
			String action=intent.getStringExtra("action");
			causeStore.get(tempModifiedPosi)[0]="yes";
			if(action.equals("deleteVege"))
			{
				deleteVegeInfo(tempModifiedPosi);  //�Ӳ�Ʒ��Ϣ��ɾ���ò�Ʒ
				handler.sendEmptyMessage(FRESH_ADAPTER_DATA);
			}
			else if(action.equals("subCount"))
			{
				subVegeSingleCount(tempModifiedPosi);
			}
		}
		if(requestCode==3 && resultCode==3)
		{
			queryDeskName=intent.getStringExtra("selectedDesk");
			if(addSubConfirmList!=null)
			{
				addSubConfirmList=null;
			}
			//�����ö����Ѿ����ڣ���Ϊ�Ѿ����ڵĶ������������޲�Ʒ����ȡ��Ʒ����ʱ�ǿյģ����ô˱�־λ��Ŀ����
			//�Զ��������ںͶ������ڵ����޲�Ʒ��Ϣ�������������
			alreadExistOrder=true;
			initOrderManage(queryDeskName);
			currentDeskName = queryDeskName;    //���õ�ǰ�����Ӻ�
		}
		if(requestCode==4 && resultCode==4)
		{
			String resultMsg=intent.getStringExtra("excepHandleFlag");
			if(resultMsg.endsWith("YES"))
			{
				switch(curExcepSource)
				{
					case INIT_ORDERMANAGE_EXCEP:
						new Thread()
						{
							@Override
							public void run()
							{
								this.setName("onActivityResultThread4--OrderManageActivity");
								initOrderManage(currentDeskName);
							}
						}.start();
					break;
				}				
			}
		}	
	}	
}
