package com.bn.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bn.R;
import com.bn.constant.Constant;
import com.bn.error.ResetErrorActivity;
import com.bn.main.MainActivity;
import com.bn.selectvege.SelectVegeActivity;
import com.bn.util.DataUtil;
import static com.bn.table.TableInfoConstant.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class SelectTableActivity extends Activity 
{
	public static int count=0;
	//��ȡ���еĴ�����Ϣ����̨���ͣ���ʼ����Ĳ�̨����
	public static Map<String,Object> pointInfo;
	//��̨��Ϣ
	public static List<String[]> initTableInfo;
	//������ʶ��ʲô������0��̨��1ȡ����̨��2��̨��3ת̨ 4�޸Ŀ�̨��
	public static int operState;
	//��¼Ҫ��ѯ����
	public static String ishasPerson;
	public static TableInfoGrid tifg;
	//����ͼƬ
	static Bitmap dtBM[]=new Bitmap[4];
	static Bitmap bxBM[]=new Bitmap[4];
	//���в�����Ϣ
	String[][] roomIdName;
	//��������
	String[][] pointType;
	String roomid;
	String ptypeid;
	ExpandableListView tableCategory;
	TextView showselectinfo;
	int selectindex=-1;//��¼�ϴε��λ��
	public static int recordindex=-1;//��¼����λ��
	String perState;
	public static String curtinfo[]=new String[7];
    String errorMsg;
    Intent intent;
    SharedPreferences sp=null;
    Editor editor=null;
    public  Handler handler=new Handler()
    {
		@Override
    	public void handleMessage(Message msg) 
		{
    		switch(msg.what)
    		{
    		//����ĳ������ѯ��Ӧ�Ĳ�̨��ʾ
    		case INIT_TABLEGRID_HANDLERID:
    			if(tifg!=null)
    			{
    			  tifg.invalidate();
    			}
    		break;
    		//��ʾ������ϢDialog
    		case SHOW_TABLEERROE_DIALOG:
    			Bundle b=msg.getData();
				ResetErrorActivity.errorMsg=b.getString("msg");
				ResetErrorActivity.errorFlg="SelectTableActivityFlg";
				Intent intent=new Intent(SelectTableActivity.this,ResetErrorActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    		break;
    		}
    	}
    };
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState)
	{
		MainActivity.al.add(this);
		super.onCreate(savedInstanceState);
		//ǿ�ƺ������ޱ��⣬ȫ��
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().getDecorView().setSystemUiVisibility(4);
		setContentView(R.layout.selecttable);
		sp=SelectTableActivity.this.getSharedPreferences("info",Context.MODE_PRIVATE);
	    editor=sp.edit();
		Button breset=(Button) this.findViewById(R.id.pointback);
		Button brefrush=(Button)this.findViewById(R.id.pointrefrush);
		showselectinfo=(TextView)this.findViewById(R.id.tableselectshow);
		//��ȡ�Զ���tableinfoGride
		tifg=(TableInfoGrid) this.findViewById(R.id.tableinfogrid);
		//ExpandableListView������ʾ��̨�Ĳ��������
	    tableCategory=(ExpandableListView)this.findViewById(R.id.selecttable_eplv);
		//��ʼ��ͼƬ
		bxBM[0]=BitmapFactory.decodeResource(getResources(),R.drawable.room_free);//����
		bxBM[1]=BitmapFactory.decodeResource(getResources(),R.drawable.room_using);//����
		bxBM[2]=BitmapFactory.decodeResource(getResources(),R.drawable.room_pre);//ѡ��
		bxBM[3]=BitmapFactory.decodeResource(getResources(),R.drawable.room_checkout);//�Ѿ�����
		dtBM[0]=BitmapFactory.decodeResource(getResources(),R.drawable.tabletemp_free);
		dtBM[1]=BitmapFactory.decodeResource(getResources(),R.drawable.tabletemp_using);
		dtBM[2]=BitmapFactory.decodeResource(getResources(),R.drawable.tabletemp_pre);
		dtBM[3]=BitmapFactory.decodeResource(getResources(),R.drawable.tabletemp_checkout);
		//�õ���ʼ�����������̨��Ϣ
		roomIdName=(String[][]) pointInfo.get("room");
		pointType= (String[][]) pointInfo.get("pointtype");
		initTableInfo=(List<String[]>) pointInfo.get("initpointinfo");
		//��ʼ������̨
		 roomid = roomIdName[0][0];//������
         ptypeid = pointType[0][0];//��̨����
		if(roomIdName==null||pointType==null||initTableInfo==null)
		{
			Toast.makeText(SelectTableActivity.this,"���޲�����̨��Ϣ",Toast.LENGTH_SHORT).show();
		}
		else
		{
		//��ʼ����̨��Ϣ��ʾ
		initTableGridInfo();
		
		 tifg.addTableInfoGridListener
	        (
	        	new TableInfoGridListener()
	        	{
					@Override
					public void onItemClick(int index) 
					{
						recordindex=index;
						curtinfo=initTableInfo.get(index);
						if(selectindex==index)
						{
							selectindex=-1;
						}
					    //�޸ĳɵ���״̬ 
						perState=curtinfo[3];//��¼ѡ��ǰ״̬
						curtinfo[3]="3";//��Ϊѡ��״̬
						initTableInfo.remove(index);
						initTableInfo.add(index, curtinfo);
						//���ȡ����ѡ����������ǰ�Ļָ���ǰ��״̬
						if(selectindex!=-1&&selectindex<initTableInfo.size())
						{
							//�õ���һ��ѡ�еĲ�̨
							String pertinfo[]=initTableInfo.get(selectindex);
							pertinfo[3]=perState;//�ı��ԭ��״̬
							initTableInfo.remove(selectindex);
							initTableInfo.add(selectindex,pertinfo);
						}
						    selectindex=index;
							showselectinfo.setText("����ǰѡ���Ϊ:  "+curtinfo[5]+"--"+curtinfo[6]+"--��̨:"+curtinfo[2]);
							switch(operState)
							{
								case 0://��̨
									intent=new Intent(SelectTableActivity.this,OpenTableActivity.class);
									intent.putExtra("tableinfo",curtinfo);
									intent.putExtra("resource", "selectTable");
									startActivity(intent);
								break;
								case 5://��ĳ����̨�Ӳ�
									if(Constant.dcvegemsg.size()!=0&&Constant.deskName!=null)
									 {
										if(Constant.allpointvegeinfo.containsKey(Constant.deskName))
										   {//��¼��ǰ��Ʒ
											Constant.allpointvegeinfo.remove(Constant.deskName);
											Constant.vegeMap.remove(Constant.deskName);
										   }
										List<String[]> list=new ArrayList<String[]>();
										Map<String,boolean[]> curVegeFlgMap=new HashMap<String,boolean[]>();
										for(int i=0;i<Constant.dcvegemsg.size();i++)
										{
											list.add(Constant.dcvegemsg.get(i));
										}
										for(int i=0;i<SelectVegeActivity.vegeFlg.size();i++)
										{
											curVegeFlgMap.putAll(SelectVegeActivity.vegeFlg);
										}
										Constant.dcvegemsg.clear();
										Constant.allpointvegeinfo.put(Constant.deskName,list); 
										Constant.vegeMap.put(Constant.deskName, curVegeFlgMap);
									    SelectVegeActivity.vegeFlg.clear();////////////////////////////////////
									 }
									Constant.deskId=curtinfo[0];
									Constant.deskName=curtinfo[2];
									Constant.defaultDeskName=curtinfo[2];
									if(Constant.allpointvegeinfo.containsKey(curtinfo[2]))
									{
										List<String[]> list=Constant.allpointvegeinfo.get(curtinfo[2]);
										Constant.dcvegemsg=list;
									}
									if(Constant.vegeMap.containsKey(Constant.deskName))
								    {
									    SelectVegeActivity.vegeFlg=Constant.vegeMap.get(Constant.deskName);
								    }
									//���֮ǰû���µ���������Ҫ���»�ȡ
									if(sp.contains(curtinfo[2]))
									{
									  Constant.guestNum=sp.getInt(curtinfo[2], 0);
									  editor.remove(curtinfo[2]);
							    	  editor.commit();
									}
									Intent intent=new Intent(SelectTableActivity.this,SelectVegeActivity.class);
									startActivity(intent);
									SelectTableActivity.this.finish();
								break;
							}
						}
					}
	        );
  breset.setOnClickListener
     (
    	new OnClickListener()
    	  {
				@Override
				public void onClick(View v) 
				{
					Intent intent=new Intent(SelectTableActivity.this,SelectVegeActivity.class);
					startActivity(intent);
					SelectTableActivity.this.finish();
					overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
				}
          }
     );
	  brefrush.setOnClickListener
	  (
		  new OnClickListener()
		  {
			@Override
			public void onClick(View v) 
			{
				new Thread()
                {
				  @Override
             	   public void run()
             	   {  
					   
 	                   initTableInfo();
             	   }
                }.start();
			}
          }
	  );
		//�����������Ϣ
		tableCategory.setAdapter(new MyExpandableListAdapter());
   }
		
	}
	public void initTableGridInfo()
	{
		if(initTableInfo==null)
		{
		  Toast.makeText(this,"���޿��в�̨,��鿴��������",Toast.LENGTH_SHORT).show();
		}
		else
		{
			tifg.setTextSer
			(
				TableInfoConstant.TEXTSIZE, 
				TableInfoConstant.FONT_COLOR,
				TableInfoConstant.TEXTHEIGHT,
				initTableInfo
	        );
			tifg.setImageSizeSer
			(
				TableInfoConstant.COLCOUNT,
				TableInfoConstant.TIMAGE_WIDHT,
				TableInfoConstant.TIMAGE_HEIGHT
			);
			tifg.setTableImageSer(dtBM,bxBM);
			tifg.setLayoutSer
			(
				TableInfoConstant.SPAN,
				TableInfoConstant.LEFT_MARGIN,
				TableInfoConstant.TOP_MARGIN
			);
			tifg.calTotalHeight();
		}
	}
	private class MyExpandableListAdapter extends BaseExpandableListAdapter
	{
		private String[] roomName;
		private String[][] pointTypeName;
		public  MyExpandableListAdapter()
		{
			roomName = new String[roomIdName.length];
			pointTypeName = new String[roomIdName.length][pointType.length];
			for(int i=0; i<roomIdName.length; i++)
			{
			  roomName[i] = roomIdName[i][2];
			}
			for(int i=0; i<roomIdName.length; i++)
			{
				for(int j=0; j<pointType.length; j++)
				{
					pointTypeName[i][j] = pointType[j][1]; //�м���������pointTypeName������м��У�ÿ�е�������ͬ
				}
			}
		}
        @Override
        public Object getChild(int groupPosition, int childPosition) 
        {
            return pointTypeName[groupPosition][childPosition];
        }
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            return childPosition;
        }
        @Override
        public int getChildrenCount(int groupPosition) 
        {
        	return pointTypeName[groupPosition].length;
        }
        public TextView getGenericView()
         {
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,70);
            TextView textView = new TextView(SelectTableActivity.this);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textView.setTextSize(26);
            textView.setTextColor(R.color.black);
            textView.setBackgroundResource(R.drawable.border);
            return textView;
        }
        @Override
        public View getChildView(int groupPosition, int childPosition, 
        		boolean isLastChild,View convertView, ViewGroup parent) 
        {
        	final int gp=groupPosition; 
            final int cp=childPosition; 

            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
            			ViewGroup.LayoutParams.WRAP_CONTENT, 60);
            LinearLayout ll=new LinearLayout(SelectTableActivity.this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
           
            ImageView iv=new ImageView(SelectTableActivity.this);
            if(childPosition==0)
            {
            	iv.setBackgroundResource(R.drawable.tabletemp_free);
            }else{
            	iv.setBackgroundResource(R.drawable.room_free);
            }
            iv.setLayoutParams(new LayoutParams(40,40));
            ll.addView(iv);
        	TextView textView = new TextView(SelectTableActivity.this);
        	textView.setLayoutParams(lp);
            ll.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textView.setPadding(60, 0,0, 0);
            textView.setTextSize(22);
            textView.setTextColor(R.color.black);
            textView.setText(getChild(groupPosition, childPosition).toString());
            textView.setOnClickListener
            (
            	new OnClickListener() 
                 {
					public void onClick(final View v) 
					{
						//���Ƶ��ͻ����¸����ʱ��ExpandableListView������ɫ�仯
						for(int i=0;i<tableCategory.getChildCount();i++)
						{
							LinearLayout inner_ll=(LinearLayout)tableCategory.getChildAt(i);
							inner_ll.setBackgroundResource(R.drawable.expandlvline);
						}
						LinearLayout ll=(LinearLayout)((TextView)v).getParent();
						//���Ƶ�ǰ������ɫ
						ll.setBackgroundColor(Color.rgb(240, 144, 0));
					   roomid = roomIdName[gp][0];//������
 	                   ptypeid = pointType[cp][0];//��̨����
 	                   showselectinfo.setText("����ǰѡ���Ϊ:  "+roomIdName[gp][2]+"--"+pointType[cp][1]);
	                   new Thread()
	                   {@Override
	                	   public void run()
	                	   {  
	    	                   initTableInfo();
	                	   }
	                   }.start();
	                } 
                 }
            );  
            ll.addView(textView);
            ll.setBackgroundResource(R.drawable.border);
            return ll;
        }
        @Override
        public Object getGroup(int groupPosition) 
        {
            return roomName[groupPosition];
        }
        @Override
        public int getGroupCount()
        {
            return roomName.length;
        }
        @Override
        public long getGroupId(int groupPosition) 
        {
            return groupPosition;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
        		View convertView,ViewGroup parent) 
        {
        	LinearLayout ll=new LinearLayout(SelectTableActivity.this);
            TextView textView = getGenericView();
            textView.setWidth(200);
            textView.setText(getGroup(groupPosition).toString());
            textView.setTextColor(R.color.black);
            textView.setPadding(40, 0, 0, 0);
            textView.setBackgroundResource(R.drawable.border);
            ll.addView(textView);
            return ll;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) 
        {
            return true;       //�������б����Ƿ��ѡ
        }
        @Override
        public boolean hasStableIds()
        {
            return true;
        }
	}
	//���ݲ�ͬ������ʼ�����ӽ���
	public void initTableInfo()
	{
	   selectindex=-1;
	   if(ishasPerson.equals(Constant.NOPERSON))
	   {
		 DataUtil.getPointInfo(roomid, ptypeid,Constant.NOPERSON,handler);//��ѯ��Ӧ�Ĳ�̨
	   }
	   else if(ishasPerson.equals(Constant.HASPERSON))
	   {
		   DataUtil.getPointInfo(roomid, ptypeid,Constant.HASPERSON,handler);//��ѯ��Ӧ�Ĳ�̨
	   }
	   else if(ishasPerson.equals(Constant.ALLPERSON))
	   {
		   DataUtil.getPointInfo(roomid,ptypeid,Constant.ALLPERSON,handler);//��ѯ��Ӧ�Ĳ�̨
	   }
	   else if(ishasPerson.equals(Constant.MAINHASPERSON))
		{
		   DataUtil.getPointInfo(roomid,ptypeid,Constant.MAINHASPERSON,handler);
		}
	   else if(ishasPerson.equals(Constant.COMBINEPOINT))
	   {
		   DataUtil.getPointInfo(roomid,ptypeid,Constant.COMBINEPOINT,handler); 
	   }
      initTableGridInfo();//��ʼ���Զ���ؼ�
      handler.sendEmptyMessage(INIT_TABLEGRID_HANDLERID);//����handler�����ػ�
	}
}
