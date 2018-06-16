package com.bn.selectvege;

import static com.bn.selectvege.SelectVegeConstant.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.bn.R;
import com.bn.constant.Constant;
import com.bn.error.ResetErrorActivity;
import com.bn.main.MainActivity;
import com.bn.manageorder.OrderManageActivity;
import com.bn.showorder.ShowOrderActivity;
import com.bn.table.OpenTableActivity;
import com.bn.table.SelectTableActivity;
import com.bn.util.DataUtil;
import com.bn.vegeimage.VegeImageUIActivity;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.text.Editable;
//import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*SelectVegeActivity������˽��棬�����ʾ��Ʒ�����������𣬵������Ʒ
 �����ʱ�����Ҳ���ʾ����а��������в�Ʒ��ͼƬ������
 ��Ʒ��ͼƬ�Ͳ�Ʒ������ͨ���Զ���Ŀؼ�VegeImageGrid����ʾ��*/
public class SelectVegeActivity extends Activity 
{
	public static int count=0;
	//��Ų�Ʒ��Ϣ��Map
	public static Map<String,Object> vegeInfoMap;
	//��Ų�Ʒ�����Ϣ��Map
	public static Map<String,Object> childVegeInfo;
	//��Ų�Ʒ��ѡ��־λ��Map
	public static Map<String,boolean[]> vegeFlg;
	public static String waitMsg;
	//�����뿪SelectVegeActivity����ʱ����ʾ�����������
	public static String childcateVege;
	//�����뿪SelectVegeActivity����ʱ��ѡ�еĲ�Ʒ���������
	public boolean mainCateStack[];
	//�����뿪SelectVegeActivity����ʱ��ѡ�еĲ�Ʒ���������
	public String curChildCatePosi="0-0";
	public int groupPosi=-1;
	public int childPosi=-1;
	boolean storeState=false;
	boolean firstInto=true;
	public static int intoCount;
	public static boolean onclickflag=true;
	public static boolean onchildclickflag=true;
	//��Ų�ƷͼƬ
	Bitmap vegeImage[]=null;
	//��Ų�Ʒ����
	List<String[]> vegeInfo=new ArrayList<String[]>();
	//�Զ���ؼ�
	VegeImageGrid vegeImageGrid;
	//���������
	Dialog errordialog;
    String errorMsg;
    int excepFlg;
    ProgressDialog waitDialog;
    ArrayAdapter<String> autoCompleteAdapter;
    String selectedVegeName;
    boolean vegeSelectedFlg=false;
    //��ѯ�Ի���
    TextView tvtableid;
    //��Ʒ���ListView�ؼ�
    ExpandableListView vegeCateExpLV;
    Bundle b;
    public static int inde=-1;
    Intent intent=null;
	Handler handler=new Handler()
   	{
		@Override
   		public void handleMessage(Message msg) 
   		{
   			super.handleMessage(msg);
   			switch(msg.what)
   			{
   				case FRUSH_VEGEIMAGEGRID:
					if(vegeImageGrid!=null)
					{
						vegeImageGrid.invalidate();
					}
				break;
				//��ȡ��̨��Ϣ������ϢDialog
	    		case SHOW_TABLEERROE_DIALOG:
	    			b=msg.getData();
	    			ResetErrorActivity.errorMsg=b.getString("msg");
					ResetErrorActivity.errorFlg="CancleTableActivityFlg";
					intent=new Intent(SelectVegeActivity.this,ResetErrorActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
	    		break;
	    		//�򿪴���Ի���
				case OPEN_ERROR_DIALOG_MESSAGE:
					//��ȡ��Ϣ�е�����
					b=msg.getData();
					ResetErrorActivity.errorMsg=b.getString("msg");
					ResetErrorActivity.errorFlg="SelectVegeActivityFlg";
					intent=new Intent(SelectVegeActivity.this,ResetErrorActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
				 break;
				 //��ʾtoast��ʾ��Ϣ
				 case OPEN_ERROR_TOAST_MESSAGE:
					 b = msg.getData();
					 Toast.makeText(SelectVegeActivity.this,b.getString("msg"),Toast.LENGTH_LONG).show();
			     break; 
			     //��ת����Ʒ����
			     case GOTO_VEGE_INTRO_MESSAGE:
			    	 intent=new Intent(SelectVegeActivity.this,VegeImageUIActivity.class);
			    	 intent.putExtra("selectedVegeName", selectedVegeName);
	   				 startActivity(intent);
	   				 overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
				 break;
				 //��ת��ѡ���̨����
			     case GO_SELECT_TABLE:
	   				 Intent i=new Intent(SelectVegeActivity.this,SelectTableActivity.class);
	   				 startActivity(i);
	   				 overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
	   		     break;
	   		}
   		}
   	};
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		MainActivity.al.add(this);		
		super.onCreate(savedInstanceState);
		//ǿ�ƺ������ޱ��⣬ȫ��
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().getDecorView().setSystemUiVisibility(4);
		setContentView(R.layout.vege);
		//�Խ���ѡ�˽���Ĵ��������ۼ�,��intoCount=0ʱ��ʾ�״ν���ѡ�˽��棬Ӧ�ý�������Ϊ��ʼ״̬
		intoCount++;		
		if(vegeInfoMap!=null)
		{
			//��ȡ��ƷͼƬ
			vegeImage=(Bitmap[])vegeInfoMap.get("bmArr");
			//��ȡ��Ʒ����
			vegeInfo=(List<String[]>)vegeInfoMap.get("vegeInfolist");
			System.out.println("vegeInfo==================="); //
			for(String[] arr : vegeInfo)
			{
				for(String str : arr)
				{
					System.out.print(str+"**");
				}
				System.out.println();
			}
			//ExpandableListView������ʾ��Ʒ��������Լ�ÿ�����������������
			vegeCateExpLV=(ExpandableListView)this.findViewById(R.id.selectvege_eplv);
			if(vegeCateExpLV==null)
			{
				System.out.println("is null================");
			}
			//�����������Ϣ     
			vegeCateExpLV.setAdapter(new MyExpandableListView(vegeInfoMap));
			//�Զ���ؼ�
			vegeImageGrid=(VegeImageGrid)this.findViewById(R.id.vig);
			restoreUIState();
			//��ʼ��VegeImageGrid�����е�����
			initGridThread(); 
			//���Զ���ؼ���Ӽ���//////////////////////////////////////////////////////////////////////////////////
		   vegeImageGrid.addVegeImageGridListener
	         (
	        	new VegeImageGridListener()
	        	{
					public void onItemClick(int index,int sum) 
					{
						if(onclickflag&&sum==1)
						{
							if(!vegeFlg.get(childcateVege)[index])
							{
								String str[]=new String[8];
								str[0]=vegeInfo.get(index)[1];//��Ʒ����
								str[1]=vegeInfo.get(index)[2];//��Ʒ�۸�
								str[2]="1";//��Ʒ����
								str[3]="";//Ҫ��
								str[4]=vegeInfo.get(index)[0];//��ƷID
								str[6]=childcateVege;
								str[7]=index+"";
								Constant.dcvegemsg.add(str);
								vegeFlg.get(childcateVege)[index]=true;
								initGridThread();
							}
							else
							{
								vegeFlg.get(childcateVege)[index]=false;
								for(int i=0;i<Constant.dcvegemsg.size();i++)
								{
									if(Constant.dcvegemsg.get(i)[4].equals(vegeInfo.get(index)[0]))
									{
										Constant.dcvegemsg.remove(i);
										i--;
									}
								}
								initGridThread();
							}
						}
						else if(onclickflag)
					    {
						  inde=index;
						  VegeImageUIActivity.indexno=index;
//						  DataUtil.getVegeIntro(vegeInfo.get(index)[0], handler);
						  onclickflag=false;
						  childcateVege=vegeInfo.get(index)[4];
				    	  intent=new Intent(SelectVegeActivity.this,VegeImageUIActivity.class);
		   				  startActivity(intent);
		   				  overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
		   				  SelectVegeActivity.this.finish();
					    }	
					}
	        	}
	        );
		}
		//����������
		Button back=(Button)findViewById(R.id.vege_back);
       	//���������水ť��ע�����
		back.setOnClickListener
		(	
		   new OnClickListener()
		   {
				@Override
				public void onClick(View v) 
				{
					Intent i=new Intent(SelectVegeActivity.this,MainActivity.class);
				    i.putExtra("selectvege","selectvege");
					startActivity(i);
					SelectVegeActivity.this.finish();
					overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
				}       
		   }
		); 
		//��̨
		Button point_open=(Button)findViewById(R.id.vege_opentable);
		//��̨��ťע�����
		point_open.setOnClickListener
		(
       		new OnClickListener() 
       		{
       			@Override
    			public void onClick(View v) 
    			{
				   Intent intent=new Intent();
   				   intent.setClass(SelectVegeActivity.this,OpenTableActivity.class);
   				   intent.putExtra("resource","selectVege");
   				   startActivity(intent);
    			}
			}
		);
		//��Ӳ�Ʒ��ť
		Button addvege=(Button) this.findViewById(R.id.vege_addorder);	
		addvege.setOnClickListener
		(
		   new OnClickListener()
	       {
				@Override
				public void onClick(View v) 
				{
					new Thread()
					{
						public void run()
						{
							this.setName("addvege Thread");
							SelectTableActivity.operState=5;//��Ӳ�Ʒѡ���̨
							SelectTableActivity.ishasPerson=Constant.HASPERSON;
							DataUtil.searchTableState(handler,Constant.HASPERSON);
						}
					}.start();
				}
	       	}
        );
		//�ҵ��˵�
		Button moregn=(Button) this.findViewById(R.id.vege_moregn);
        //�ҵ��˵���ť��ע�����
		moregn.setOnClickListener
		(
		   new OnClickListener()
	       {
				@Override
				public void onClick(View v) 
				{		
					Intent intent=new Intent();
   				    intent.setClass(SelectVegeActivity.this, OrderManageActivity.class);
   				    startActivity(intent);
   				    overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
				}
	       	}
        );
		//�ѵ��Ʒ
		Button bt_order=(Button)findViewById(R.id.vege_showorder);
        bt_order.setOnClickListener
        (
	    	new OnClickListener() 
	    	{
				@Override
				public void onClick(View v)
				{//����ѡ�Ĳ�Ʒ���붩������
			        Intent intent = new Intent(SelectVegeActivity.this,ShowOrderActivity.class);
			        startActivity(intent);
			        SelectVegeActivity.this.finish();
			        overridePendingTransition(R.anim.out_to_left,R.anim.in_from_right);
				}    	
			}  
        );
		tvtableid=(TextView) this.findViewById(R.id.vege_tablebt);
		if(Constant.deskName==null)
		{
			tvtableid.setText("δ��̨");
		}
		else
		{
    	   tvtableid.append(Constant.deskName);
    	   Constant.defaultDeskName=Constant.deskName;
		}
		
	vegeCateExpLV.setOnGroupExpandListener(new OnGroupExpandListener()
		{
			@Override
			public void onGroupExpand(int groupPosition) 
			{
				mainCateStack[groupPosition]=true;
			}
			
		});
		vegeCateExpLV.setOnGroupCollapseListener( new OnGroupCollapseListener()
		{
			@Override
			public void onGroupCollapse(int groupPosition) 
			{
				mainCateStack[groupPosition]=false;
			}
		});
	}
	@Override
	protected void onPause() 
	{
		super.onPause();
		firstInto=false;
		storeState=true;
		SharedPreferences uiState=getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = uiState.edit();
		//��ʾ�Ѿ���ԭ��״̬���б���
		editor.putBoolean(ADDING_ITEM_KEY, storeState);
		//���浱ǰVegeImageGridչʾ�Ĳ�Ʒ����������
		editor.putString(CUR_CHILDCATE_NAME, childcateVege);
		//���浱ǰ��ѡ�е������λ��
		editor.putString(CUR_CHILDCATE_POSI, curChildCatePosi);
		//����ExpandableListView��GroupView���򿪵�״̬
		StringBuffer mainCateState=new StringBuffer();
		for(boolean b : mainCateStack)
		{
			if(b)
			{
				mainCateState.append("1"+"��");
			}
			else
			{
				mainCateState.append("0"+"��");
			}
		}
		editor.putString(MAINCATE_STATE_STACK,mainCateState.toString());
		editor.commit();
	}	
	@SuppressWarnings("unused")
	private void restoreUIState()
	{
		SharedPreferences uiState=getPreferences(Context.MODE_PRIVATE);
		//���������󣬵�һ�ν���ѡ�˽���
		if(intoCount==1)
		{
			SharedPreferences.Editor editor = uiState.edit();
			//��ʾ�Ƿ��Activityԭ��״̬���б���
			editor.putBoolean(ADDING_ITEM_KEY, false);
			storeState=false;
			for(int i=0; i<mainCateStack.length; i++)
			{
				mainCateStack[i]=false;
			}
			groupPosi=-1;
			childPosi=-1;
		}
		else
		{ 
			storeState = uiState.getBoolean(ADDING_ITEM_KEY, false);
			String childCateNameInfo=uiState.getString(CUR_CHILDCATE_NAME,null);
			String childCatePosiInfo=uiState.getString(CUR_CHILDCATE_POSI,null);
			String mainCateState=uiState.getString(MAINCATE_STATE_STACK,null);
			String first=uiState.getString(FIRSH_INTO_SELECTVEGEACTIVITY,"");
			
			if(storeState) //���״̬��Ϣ������
			{
				childcateVege=childCateNameInfo;
				if(childCatePosiInfo!=null)
				{
					curChildCatePosi=childCatePosiInfo;
				}
				String posi[]=curChildCatePosi.split("-");
				groupPosi=Integer.parseInt(posi[0]);
				childPosi=Integer.parseInt(posi[1]);
				String state[]=mainCateState.split("��");
				for(int i=0; i<state.length; i++)
				{
					if(state[i].equals("0"))
					{
						mainCateStack[i]=false;
					}
					else
					{
						mainCateStack[i]=true;
						vegeCateExpLV.expandGroup(i);
					}
				}				
			}
		}
	}
	private class MyExpandableListView extends BaseExpandableListAdapter
	{
		//��Ʒ������Ϣ
		List<String[]> mainCateList;
		//��Ʒ������Ϣ
		List<String[]> childCateList;
		
	 	@SuppressWarnings("unchecked")
		public MyExpandableListView(Map<String,Object> vegeCateInfo)
		{
	 		super();
	 		mainCateList=(List<String[]>)vegeCateInfo.get("vegeMainCateInfo");
	 		childCateList=(List<String[]>)vegeCateInfo.get("vegeChildCateInfo");
	 		//��������ÿ��GroupView�Ĵ�״̬
	 		mainCateStack=new boolean[mainCateList.size()];
		}
		@Override
		public Object getChild(int groupPosition, int childPosition) 
		{
			return childCateList.get(groupPosition)[childPosition];
		}		
		@Override
		public long getChildId(int groupPosition, int childPosition)
		{
			return childPosition;
		}
		@Override
		public View getChildView(final int groupPosition,final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) 
		{		
			LinearLayout llayout = new LinearLayout(SelectVegeActivity.this);
			llayout.setOrientation(LinearLayout.VERTICAL);
			llayout.setPadding(5,5,5,5);//������������
			if(storeState==true && groupPosi==groupPosition && childPosi==childPosition)
			{
				storeState=false;
				llayout.setBackgroundColor(Color.rgb(240, 144, 0));
			}
			else
			{
				if(childcateVege.equals(childCateList.get(groupPosition)[childPosition]))
				{
					llayout.setBackgroundColor(Color.rgb(240, 144, 0));
				}
				else
				{
					llayout.setBackgroundResource(R.drawable.expandlvline);
				}
			}
			TextView childTV= new TextView(SelectVegeActivity.this);
			childTV.setHeight(60);
			childTV.setTextSize(21);//���������С
			childTV.setGravity(Gravity.CENTER);
			childTV.setTextColor(Color.rgb(204, 102, 0));
			childTV.setText(getChild(groupPosition,childPosition).toString());
			llayout.addView(childTV);
			//�Բ�Ʒ������Itemע�����
			childTV.setOnClickListener
				(
					new OnClickListener() 
					{
						@Override
						public void onClick(View v) 
						{
							if(onchildclickflag)
							{
								onchildclickflag=false;
								View tempV=v;
								//���ڻ�ø�����Text����
								TextView inner_tv=(TextView)tempV;
								//������ڸ������Ĳ�Ʒ��Ϣ
								childcateVege=inner_tv.getText().toString();
								//��ø������λ��
								curChildCatePosi=groupPosition+"-"+childPosition;
								 //���Ƶ��ͻ����¸����ʱ��ExpandableListView������ɫ�仯
								for(int i=0;i<vegeCateExpLV.getChildCount();i++)
								{
									LinearLayout inner_ll=(LinearLayout)vegeCateExpLV.getChildAt(i);
									inner_ll.setBackgroundResource(R.drawable.expandlvline);
								}
								LinearLayout ll=(LinearLayout)((TextView)v).getParent();
								//���Ƶ�ǰ������ɫ
								ll.setBackgroundColor(Color.rgb(240, 144, 0));
								initGridThread();
							}
						}	
					}
				);
			
			return llayout;
		}
		@Override
		public int getChildrenCount(int arg0) 
		{
			return childCateList.get(arg0).length;
		}
		//��ȡ���������
		@Override
		public Object getGroup(int arg0) 
		{
			return mainCateList.get(arg0)[1];
		}
		//��ȡ���������
		@Override
		public int getGroupCount() 
		{
			return mainCateList.size();
		}
		@Override
		public long getGroupId(int arg0) 
		{
			return arg0;
		}
		@Override
		public View getGroupView(final int groupPosition, boolean arg1, View arg2,
				ViewGroup arg3) 
		{
			LinearLayout llayout = new LinearLayout(SelectVegeActivity.this);
			llayout.setOrientation(LinearLayout.HORIZONTAL);
			llayout.setPadding(5,2,0,0);//������������
			llayout.setBackgroundResource(R.drawable.expandlvline);	
			TextView mainTV= new TextView(SelectVegeActivity.this);
			mainTV.setHeight(60);
			mainTV.setTextSize(21);//���������С
			mainTV.setGravity(Gravity.CENTER);
			mainTV.setTextColor(Color.rgb(204, 102, 0));
			mainTV.setText(getGroup(groupPosition).toString());//��������Ŀ������
			llayout.addView(mainTV,0);
			return llayout;
		}
		@Override
		public boolean hasStableIds() 
		{
			return true;
		}
		@Override
		public boolean isChildSelectable(int arg0, int arg1) 
		{
			return true;
		}
	 }
	public void initVegeImageGrid()
	{
		//���ò�Ʒ��ͼƬ
		vegeImageGrid.setBMSer(vegeImage,vegeFlg.get(childcateVege));
		//���ò�Ʒ����
		vegeImageGrid.setTextSer(vegeInfo, SelectVegeConstant.FONT_COLOR);
		//����ͼƬ������Ŀ�ߣ���࣬�������������
		vegeImageGrid.setSizeSer
		(
			SelectVegeConstant.PIC_HEIGHT,
			SelectVegeConstant.PIC_WIDTH,
			SelectVegeConstant.PIC_FLG_HEIGHT,
			SelectVegeConstant.PIC_FLG_WIDTH,
			SelectVegeConstant.FONT_SIZE,
			SelectVegeConstant.PIC_SPAN,
			SelectVegeConstant.PIC_COLCOUNT
		);
		vegeImageGrid.calTotalHeight();
	}
	//��ʾ�Ź�����Ϣ���߳�
   @SuppressWarnings("unchecked")
   public void initGridThread()
   {
	   	new Thread()
		{
			public void run()
			{
				this.setName("initGrid Thread");
				DataUtil.getVegeInfo(childcateVege,handler);
				if(childVegeInfo==null)
				{
					VegeImageUIActivity.childVegeInfo=childVegeInfo;
					Message msg=new Message();
					msg.what=SelectVegeConstant.OPEN_ERROR_TOAST_MESSAGE;
					Bundle b=new Bundle();
					b.putString("msg","��ȡ���ݳ����Ƿ����»�ȡ");
					handler.sendMessage(msg);
				}
				else
				{
					vegeImage=(Bitmap[])childVegeInfo.get("bmArr");
					vegeInfo = (List<String[]>)childVegeInfo.get("vegeInfolist");
					VegeImageUIActivity.childVegeInfo=childVegeInfo;
					initVegeImageGrid();
					handler.sendEmptyMessage(FRUSH_VEGEIMAGEGRID); 
				}
			}
		}.start();	
	  }
   public void initGalleryThread(final String vegeCate)
   {
	   	new Thread()
		{
			public void run()
			{
				this.setName("initGalleryThread");
				DataUtil.getVegeInfoFor(vegeCate,handler);
			}
		}.start();	
	  }
}