package com.bn.util;

import static com.bn.table.SelectTableActivity.*;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.*;
import static com.bn.table.TableInfoConstant.*;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.bn.constant.Constant;
import com.bn.login.LoginConstant;
//import com.bn.main.MainActivity;
import com.bn.main.MainConstant;
import com.bn.manageorder.OrderManageActivity;
import com.bn.manageorder.OrderManageConstant;
import com.bn.reset.ResetConstant;
import com.bn.resetpw.ResetPassWordConstant;
import com.bn.selectvege.SelectVegeConstant;
import com.bn.selectvege.SelectVegeActivity;
import com.bn.showorder.ShowOrderConstant;
import com.bn.table.SelectTableActivity;
import com.bn.table.TableInfoConstant;
//import com.bn.user.UserConstant;
import com.bn.util.DataGetUtilSimple;
import com.bn.vegeimage.VegeImageConstant;
import com.bn.vegeimage.VegeImageUIActivity;
import com.bn.vegeinfo.VegeActivity;
/* DataUtil�����࣬������װ��ʱ�Ĺ��� */
public class DataUtil 
{
	public static void resetPassWord(final String userid,final String password,final String newpw,final Handler handler)
	{
		new Thread()
		{
			public void run()
			{
				try{
					 DataGetUtilSimple.ConnectSevert(Constant.SURE_WAITER+userid+Constant.SURE_WAITER+password);
					 if(DataGetUtilSimple.readinfo.equals("ok"))
					 {
						 DataGetUtilSimple.ConnectSevert(Constant.RESET_PASSWORD+userid+Constant.RESET_PASSWORD+newpw);
						 if(DataGetUtilSimple.readinfo.equals("ok"))
						 {
							 sendHandlerMsg
								(
									ResetPassWordConstant.RESET_PASSWORD_SUCCESS,
									"�޸ĳɹ�",
									Constant.ToastMessage,
									handler
								);
						 }
						 else
						 {
							 sendHandlerMsg
								(
									ResetPassWordConstant.RESET_PASSWORD_MESSAGE,
									"�޸�ʧ�ܣ�����������",
									Constant.ToastMessage,
									handler
								); 
						 }
			         }
					 else
					 {
						 sendHandlerMsg
							(
								ResetPassWordConstant.RESET_PASSWORD_MESSAGE,
								"�����û��������!!",
								Constant.ToastMessage,
								handler
							); 
					 }
				}
				  catch(Exception e)
				{
					  sendHandlerMsg
						(
							ResetPassWordConstant.RESET_PASSWORD_MESSAGE,
							"����ʧ�ܣ���",
							Constant.UnknownHost,
							handler
						);
				}
			}
		}.start();
	}
	//ͨ������ID��ʼ������������Ϣ
	public static void initCateInfoByRoomId(String roomId,final Handler handler)
	{
		new Thread()
		{
			public void run() 
			{
				try
				{
					List<String[]> childCateList=new ArrayList<String[]>();
					Map<String,Object> vegeInfo = new HashMap<String,Object>();
					Map<String,boolean[]> vegeFlg=new HashMap<String,boolean[]>();
					//���ݲ���ID�����Ӧ������Ʒ��Ϣ
					List<String[]> mainCateList=VegeMainCateUtil.getMainCateInfo(Constant.roomId);
				    //�������ݿ��еĲ�Ʒ������Ϣ�Լ���������ID����������Ϣ  		
					for(int i=0;i<mainCateList.size();i++)
				    {
						childCateList.add(VegeChildCateUtil.getChildCateInfo(mainCateList.get(i)[1]));
				    }
					//Ĭ����ʾ�Ĳ�Ʒ
					List<String[]> list = VegeManagerUtil.getVege(childCateList.get(0)[0]);
					//�ò�Ʒ��ѡ��־λ����
					boolean []booFlg=new boolean[list.size()];
					Bitmap bmArr[] = new Bitmap[list.size()];
					for(int i=0; i<list.size(); i++)
					{		
						String[] vgi=list.get(i)[5].split("/");
						String bm=vgi[vgi.length-1];
						String bmId=bm.replace(".jpg","");
						Bitmap broadcastBmpt=PicManagerUtil.getBM(bmId);
						bmArr[i]=broadcastBmpt;
						booFlg[i]=false;
					}
					vegeInfo.put("vegeInfolist", list);
					vegeInfo.put("bmArr", bmArr);
					vegeInfo.put("vegeMainCateInfo", mainCateList);
					vegeInfo.put("vegeChildCateInfo", childCateList);
					vegeFlg.put(childCateList.get(0)[0], booFlg);
					SelectVegeActivity.vegeInfoMap=vegeInfo;	
					SelectVegeActivity.vegeFlg=vegeFlg;
					SelectVegeActivity.childcateVege=childCateList.get(0)[0];
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
				}
				catch(UnknownHostException e)
				{
				    e.printStackTrace();
				    //�رյȴ��Ի���
				    handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					//��������Ի���
				    sendHandlerMsg
					(
						MainConstant.OPEN_ERROR_TOAST_MESSAGE,
						"���ӷ�����ʧ�ܣ���鿴�����������³���",
						Constant.UnknownHost,
						handler
					);
		    	}
				catch(SocketTimeoutException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					//��������Ի���
					sendHandlerMsg
					(
						MainConstant.OPEN_GRIDERROR_DIALOG_MESSAGE,
						"�������ӳ�ʱ�����������������,�Ƿ�����",
						Constant.SocketTimeout,
						handler
					);
				}
				catch(OutOfMemoryError e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					//��������Ի���
					sendHandlerMsg
					(
						MainConstant.OPEN_ERROR_TOAST_MESSAGE,
						"��ȡ�����쳣�������³���",
						Constant.SocketTimeout,
						handler
					);
				}
				catch(StringIndexOutOfBoundsException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					//��������Ի���
					sendHandlerMsg
					(
						MainConstant.OPEN_GRIDERROR_DIALOG_MESSAGE,
						"���ݻ�ȡ�����Ƿ�����",
						Constant.StringIndexOutOfBounds,
						handler
					);
				}
				catch(EOFException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					//��������Ի���
					sendHandlerMsg
					(
						MainConstant.OPEN_ERROR_TOAST_MESSAGE,
						"��ȡ����ʧ�ܣ�������������\n�����������Ȳ�Ʒ��Ϣ�������ٵ��",
						Constant.EOFExcep,
						handler
					);
				}
				catch(NullPointerException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					//��������Ի���
					sendHandlerMsg
					(
						MainConstant.OPEN_GRIDERROR_DIALOG_MESSAGE,
						"��ȡ����ʧ�ܣ�������������",
						Constant.NullPointer,
						handler
					);
				}
				catch(SocketException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					//��������Ի���
					sendHandlerMsg
					(
						MainConstant.OPEN_GRIDERROR_DIALOG_MESSAGE,
						"�������粻�ȶ����ܵ������ݳ����Ƿ����»�ȡ����",
						Constant.SocketExcep,
						handler
					);
				} 
				catch(IOException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					sendHandlerMsg
					(
						MainConstant.OPEN_ERROR_TOAST_MESSAGE,
						"���ݼ����������������ٵ��",
						Constant.IOExcep,
						handler
					);
				}
				catch (Exception e) 
				{  
					e.printStackTrace();
					//�رյȴ��Ի���
					handler.sendEmptyMessage(MainConstant.CANCEL_WAIT_DIALOG_FOR_GRID);
					sendHandlerMsg
					(
						MainConstant.OPEN_GRIDERROR_DIALOG_MESSAGE,
						"���ݼ��س����Ƿ����¼���",
						Constant.IOExcep,
						handler
					);
				}
			}
		}.start();
	}
	//����������Ʋ�ѯ���в�Ʒ��Ϣ���ٸ��ݲ�ƷͼƬ��ȡͼƬ��ʾ
	public static void getVegeInfo(final String str,final Handler handler)
	{
		 
		Map<String,Object> vegeInfo = new HashMap<String,Object>();
		List<String[]> list;
		try
		{
			list = VegeManagerUtil.getVege(str);
		    Bitmap bmArr[] = new Bitmap[list.size()];
		    boolean []booFlg=new boolean[list.size()];
			for(int i=0; i<list.size(); i++)
			{		
				String[] vgi=list.get(i)[5].split("/");
				String bm=vgi[vgi.length-1];
				String bmId=bm.replace(".jpg","");
				Bitmap broadcastBmpt=PicManagerUtil.getBM(bmId);
				bmArr[i]=broadcastBmpt;
				booFlg[i]=false;
			}
			vegeInfo.put("vegeInfolist", list);
			vegeInfo.put("bmArr", bmArr);
			SelectVegeActivity.childVegeInfo=vegeInfo;
			if(!SelectVegeActivity.vegeFlg.containsKey(str))
			{//�������Ը��������Ϊ����ֵ����Ӹ�ֵ
			  SelectVegeActivity.vegeFlg.put(str, booFlg);
			}
		   }
		catch(SocketTimeoutException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_DIALOG_MESSAGE,
				"��ȡ���ݳ�ʱ���Ƿ�����",
				Constant.SocketTimeout,
				handler
			);
		}
		catch(OutOfMemoryError e)
		{
			e.printStackTrace();
			//��������Ի���
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_TOAST_MESSAGE,
				"���������������ݻ�ȡ������...",
				Constant.SocketTimeout,
				handler
			);
		}
		catch(StringIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_DIALOG_MESSAGE,
				"���ݻ�ȡ�����Ƿ�����",
				Constant.StringIndexOutOfBounds,
				handler
			);
		}
		catch(EOFException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_TOAST_MESSAGE,
				"���ݻ�ȡ���������ĵȴ�...",
				Constant.EOFExcep,
				handler
			);
		}
		catch(SocketException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_DIALOG_MESSAGE,
				"�����������粻�ȶ��������ݲ�ȫ���Ƿ����»�ȡ����",
				Constant.SocketExcep,
				handler
			);
		} 
		catch(IOException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_TOAST_MESSAGE,
				"��ȡ����ʧ��,��ȴ�...",
				Constant.IOExcep,
				handler
			);
		}
		catch (Exception e) 
		{  
			e.printStackTrace();
			//��������Ի���
		    sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_DIALOG_MESSAGE,
				"��ȡ����ʧ�ܣ������³���",
				Constant.Excep,
				handler
			);
		}		
	}
	//���ݲ�ѯ�Ĳ�Ʒ����𣬲�ѯ������е����в�Ʒ��Ϣ
	public static void getVegeInfoFor(final String str,final Handler handler)
	{		 
		Map<String,Object> vegeInfo = new HashMap<String,Object>();
		List<String[]> list;
		try
		{
			list = VegeManagerUtil.getVege(str);
		    Bitmap bmArr[] = new Bitmap[list.size()];
		    boolean []booFlg=new boolean[list.size()];
			for(int i=0; i<list.size(); i++)
			{		
				String[] vgi=list.get(i)[5].split("/");
				String bm=vgi[vgi.length-1];
				String bmId=bm.replace(".jpg","");
				Bitmap broadcastBmpt=PicManagerUtil.getBM(bmId);
				bmArr[i]=broadcastBmpt;
				booFlg[i]=false;
			}
			vegeInfo.put("vegeInfolist", list);
			vegeInfo.put("bmArr", bmArr);
			VegeImageUIActivity.childVegeInfo=vegeInfo;
			if(VegeImageUIActivity.childVegeInfo==null)
			{
				Message msg=new Message();
				msg.what=SelectVegeConstant.OPEN_ERROR_TOAST_MESSAGE;
				Bundle b=new Bundle();
				b.putString("msg","��ȡ���ݳ����Ƿ����»�ȡ");
				handler.sendMessage(msg);
			}
			else
			{
				handler.sendEmptyMessage(SelectVegeConstant. GOTO_VEGE_INTRO_MESSAGE);
			}
		   }
		catch(SocketTimeoutException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_DIALOG_MESSAGE,
				"��ȡ���ݳ�ʱ���Ƿ�����",
				Constant.SocketTimeout,
				handler
			);
		}
		catch(OutOfMemoryError e)
		{
			e.printStackTrace();
			//��������Ի���
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_TOAST_MESSAGE,
				"���������������ݻ�ȡ������...",
				Constant.SocketTimeout,
				handler
			);
		}
		catch(StringIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_DIALOG_MESSAGE,
				"���ݻ�ȡ�����Ƿ�����",
				Constant.StringIndexOutOfBounds,
				handler
			);
		}
		catch(EOFException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_TOAST_MESSAGE,
				"���ݻ�ȡ���������ĵȴ�...",
				Constant.EOFExcep,
				handler
			);
		}
		catch(SocketException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_DIALOG_MESSAGE,
				"�����������粻�ȶ��������ݲ�ȫ���Ƿ����»�ȡ����",
				Constant.SocketExcep,
				handler
			);
		} 
		catch(IOException e)
		{
			e.printStackTrace();
			sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_TOAST_MESSAGE,
				"��ȡ����ʧ��,��ȴ�...",
				Constant.IOExcep,
				handler
			);
		}
		catch (Exception e) 
		{  
			e.printStackTrace();
			//��������Ի���
		    sendHandlerMsg
			(
				SelectVegeConstant.OPEN_ERROR_DIALOG_MESSAGE,
				"��ȡ����ʧ�ܣ������³���",
				Constant.Excep,
				handler
			);
		}		
	}

	//��½�������Ա��id�鿴�Ƿ��е�¼Ȩ��
	@SuppressWarnings("unused")
	public static void loginValidate(String userid,String userpw,Handler handler)throws Exception 
	{
		//�ж������������û�id�Ƿ�Ϸ�
		 DataGetUtilSimple.ConnectSevert(Constant.SURE_WAITER+userid+Constant.SURE_WAITER+userpw);
		 if(DataGetUtilSimple.readinfo.equals("ok"))
		 {
			//���жϴ�Ա���Ƿ��������ط���½
			 DataGetUtilSimple.ConnectSevert(Constant.IS_HASlOGIN+userid);
			 if(DataGetUtilSimple.readinfo.equals("0"))
			 {
				int i=0;
				DataGetUtilSimple.ConnectSevert(Constant.GET_LOGINAUTH+userid);
				String authoryinfo[][]=TypeExchangeUtil.getString(DataGetUtilSimple.readinfo);
				for(i=0;i<authoryinfo.length;i++)
				{
					   if(authoryinfo[i][0].equals("��½"))
					   {
						   DataGetUtilSimple.ConnectSevert(Constant.UPDATE_LOGINFLG+userid+Constant.UPDATE_LOGINFLG+"1");
						   Message msg=new Message();
							Bundle b=new Bundle();
							msg.what=LoginConstant.LOGIN_SUCCESS;
							b.putString("msg","��½�ɹ�");
							b.putString("action","INIT_VEGEIMAGEGRID");
							msg.setData(b);
							handler.sendMessage(msg);
					   }
					   break;
				  }				
				 if(i>=authoryinfo.length)
				 {
					 sendHandlerMsg
						(
						    LoginConstant.SHOW_AUTH_TOST_MESSAGE,
							"���޵�½Ȩ�ޣ�����",
							Constant.Excep,
							handler
						); 
				 }
		    }
			else
			 {
				sendHandlerMsg
				(
				    LoginConstant.SHOW_AUTH_TOST_MESSAGE,
				    "���������ط��Ѿ���½����ע��������",
					Constant.Excep,
					handler
				);
			 }
		 }
		 else 
		 {
			 sendHandlerMsg
				(
				    LoginConstant.SHOW_AUTH_TOST_MESSAGE,
					"��������������û��������ϣ�����",
					Constant.Excep,
					handler
				);
		 }
	}
	//ע��ʱ����Ȩ��
	//��½�������Ա��id�鿴�Ƿ��е�¼Ȩ��
	public static boolean logoutValidate(String userid,String userpw,Handler handler)throws Exception
	{
		//�ж������������û�id�Ƿ�Ϸ�
		 DataGetUtilSimple.ConnectSevert(Constant.SURE_WAITER+userid+Constant.SURE_WAITER+userpw);
		 if(DataGetUtilSimple.readinfo.equals("ok"))
		 {
			int i=0;
			DataGetUtilSimple.ConnectSevert(Constant.GET_LOGINAUTH+userid);
			String authoryinfo[][]=TypeExchangeUtil.getString(DataGetUtilSimple.readinfo);
			for(i=0;i<authoryinfo.length;i++)
			{
			   if(authoryinfo[i][0].equals("��½"))
			   {
				return true;
			   }
			 }
			 if(i>=authoryinfo.length)
			 {
				return false; 
			 }
		 }else{
			 return false;
		 }
		return false;
	}
	public static void updateLoginFlg(String userid)
	{
		try
		{
			DataGetUtilSimple.ConnectSevert(Constant.UPDATE_LOGINFLG+userid+Constant.UPDATE_LOGINFLG+"0");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void openTableInfo(final Handler handler)
	{
		new Thread()
		   {
			   public void run()
			   {
				   try{
						Map<String,Object> pointInfo = new HashMap<String,Object>();
						DataGetUtilSimple.ConnectSevert(Constant.GET_W_ROOM_POINTTYPE);
						String str[] = DataGetUtilSimple.readinfo.split("~");
						//��ȡ������Ϣ
						String[][] listroom=TypeExchangeUtil.getString(str[0]);
						//��ȡ��̨����
						String[][] listptype=TypeExchangeUtil.getString(str[1]);
						DataGetUtilSimple.ConnectSevert(Constant.GET_W_NOPERSONPOINTINFO+listroom[0][0]+Constant.GET_W_NOPERSONPOINTINFO+listptype[0][0]);
						List<String []> initpointinfo = TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);
						pointInfo.put("room",listroom);
						pointInfo.put("pointtype",listptype);
						pointInfo.put("initpointinfo",initpointinfo);
						SelectTableActivity.pointInfo=pointInfo;
						handler.sendEmptyMessage(TableInfoConstant.GO_SELECT_TABLE);
					}
					catch(SocketTimeoutException e)
					{
						e.printStackTrace();
						//�رյȴ��Ի���
						//��������Ի���
						sendHandlerMsg
						(
							TableInfoConstant.SHOW_TOAST_MESSAGE,
							"���ӳ�ʱ�����������������⣬�����³���",
							Constant.ToastMessage,
							handler
						);
					}
					catch(IOException e)
					{
						e.printStackTrace();
						//�رյȴ��Ի���
						sendHandlerMsg
						(
								TableInfoConstant.SHOW_TOAST_MESSAGE,
								"���ݻ�ȡ�쳣�������»�ȡ",
								Constant.ToastMessage,
								handler
						);
					}
					catch(Exception e)
					  {
						e.printStackTrace();
						//�رյȴ��Ի���
						sendHandlerMsg
						(
								TableInfoConstant.SHOW_TOAST_MESSAGE,
								"���ݻ�ȡ�쳣�������»�ȡ",
								Constant.ToastMessage,
								handler
						 );
					}
			   }
		   }.start();
	}
public static void setNewOrder(final String userid,final String password,final Handler handler)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
				   DataGetUtilSimple.ConnectSevert(Constant.SURE_WAITER+userid+Constant.SURE_WAITER+password);
				   if(DataGetUtilSimple.readinfo.equals("ok"))
				   {
					List<String[]> orderList=Constant.dcvegemsg;
					float totalPri = 0;
					String getresult=null;
					List<String[]> passOrderList = new ArrayList<String[]>(orderList.size());
					List<String[]> passVegeList=new ArrayList<String[]>(orderList.size());
					String orderId[];
					
					if(orderList.size()==0)
					{
						Message msg=new Message();
						Bundle b=new Bundle();
						b.putString("prompt_toast","��δѡ���Ʒ");
						msg.setData(b);
						msg.what=ShowOrderConstant.OPEN_PROMPT_TOAST_MESSAGE;
						handler.sendMessage(msg);
					}
					for(int i=0;i<orderList.size();i++)
					{
						if(!orderList.get(i)[2].equals("0"))
						{//����ƷID�Ͳ�Ʒ�����������������в���
							String vegeStr[]=new String[2];
							vegeStr[0]=orderList.get(i)[4];//��ƷID
							vegeStr[1]=orderList.get(i)[2];//��Ʒ����
							passVegeList.add(vegeStr);		 
							//�����Ʒ�ܼ۸�
							totalPri=totalPri+Float.parseFloat(orderList.get(i)[2])*Float.parseFloat(orderList.get(i)[1]);
						}
					}
					String tempVegeStr=TypeExchangeUtil.listToString(passVegeList);
					DataGetUtilSimple.ConnectSevert(Constant.ADD_W_NEWORDER+tempVegeStr+
													Constant.ADD_W_NEWORDER+Constant.deskName+
													Constant.ADD_W_NEWORDER+userid+
													Constant.ADD_W_NEWORDER+(totalPri+"")+
													Constant.ADD_W_NEWORDER+Constant.guestNum);
					getresult=DataGetUtilSimple.readinfo;
					if(!getresult.startsWith("sucess"))
					{
						if(getresult.equals("fail"))
						{//��������ʧ��
							Message msg=new Message();
							Bundle b=new Bundle();
							b.putString("prompt_toast","���ִ����������ύ");
							msg.setData(b);
							msg.what=ShowOrderConstant.OPEN_PROMPT_TOAST_MESSAGE;
							handler.sendMessage(msg);
						}
						else
						{
							Message msg=new Message();
							Bundle b=new Bundle();
							b.putString("prompt_toast", getresult.substring(0, getresult.length()-1)+"����������ѡ��������Ʒ");
							msg.setData(b);
							msg.what=ShowOrderConstant.OPEN_PROMPT_TOAST_MESSAGE;
							handler.sendMessage(msg);
						}
					}
					else
					{//���������ɹ�
						orderId=getresult.split("sucess");
						for(int i=0;i<orderList.size();i++)
						{
							if(!orderList.get(i)[2].equals("0"))
							{//��������Ϊ0�Ĳ�Ʒ�ύ
								String orderStr[]=new String[8];
								orderStr[0]=orderList.get(i)[4];//��ƷID
						   		orderStr[1]=userid;//�����ID
						   		orderStr[2]=orderId[1];//����ID
						   		orderStr[3]=orderList.get(i)[2];//������Ʒ����
						   		orderStr[4]=orderList.get(i)[1];//������Ʒ�۸�
//						   		orderStr[5]=orderList.get(i)[3];//����Ҫ��
						   		orderStr[7]=i+"";//���
								passOrderList.add(orderStr);
							}
						}
						String tempOrderStr=TypeExchangeUtil.listToString(passOrderList);
						DataGetUtilSimple.ConnectSevert(Constant.ADD_W_NEWDETIALORDER+tempOrderStr);
						String getin=DataGetUtilSimple.readinfo;
						if(getin.equals("ok"))
						{//�����ύ�ɹ�
							Message msg=new Message();
							Bundle b=new Bundle();
							b.putString("prompt_toast","�����ύ�ɹ�");
							msg.setData(b);
							msg.what=ShowOrderConstant.REMOVE_GUESTNUM_MESSAGE;
							handler.sendMessage(msg);
						}
					}
				}
				else
				{
					Message msg=new Message();
					Bundle b=new Bundle();
					b.putString("prompt_toast","���������Ȩ�¶���");
					msg.setData(b);
					msg.what=ShowOrderConstant.OPEN_PROMPT_TOAST_MESSAGE;
					handler.sendMessage(msg);
				}
				}
				catch(SocketTimeoutException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						ShowOrderConstant.OPEN_ERROR_DIALOG_MESSAGE,
						"��ȡ���ݳ�ʱ���������������Ƿ���ͨ���Ƿ�ֱ������",
						Constant.SocketTimeout,
						handler
					);
				}
				catch(EOFException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						ShowOrderConstant.OPEN_ERROR_TOAST_MESSAGE,
						"��ȡ����ʧ�ܣ�������������\n�����������Ȳ�Ʒ��Ϣ�������ٵ��",
						Constant.EOFExcep,
						handler
					);
				}
				catch(SocketException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						ShowOrderConstant.OPEN_ERROR_DIALOG_MESSAGE,
						"�����������粻�ȶ��������ݲ�ȫ���Ƿ����»�ȡ����",
						Constant.SocketExcep,
						handler
					);
				} 
				catch(NullPointerException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						ShowOrderConstant.OPEN_ERROR_TOAST_MESSAGE,
						"NullPointerException��ȡ����ʧ�ܣ�������������",
						Constant.NullPointer,
						handler
					);
				}
				catch (Exception e) 
				{  
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						ShowOrderConstant.OPEN_ERROR_DIALOG_MESSAGE,
						"��ȡ����ʧ�ܣ������³���",
						Constant.Excep,
						handler
					);
				}
			}
		}.start();
	}
	//��������Ĳ������ͺͲ�̨���Ͳ�ѯ����Ӧ�Ĳ�̨
	public static void getPointInfo(String roomid,String ptypeid,String ishasperson,Handler handler)
		{
			try{
				if(ishasperson.equals(Constant.ALLPERSON))//����
				{
					DataGetUtilSimple.ConnectSevert(Constant.GET_W_POINTINFO+roomid+Constant.GET_W_POINTINFO+ptypeid);
				}else if(ishasperson.equals(Constant.NOPERSON))
				{
					DataGetUtilSimple.ConnectSevert(Constant.GET_W_NOPERSONPOINTINFO+roomid+Constant.GET_W_NOPERSONPOINTINFO+ptypeid);
				}
				else if(ishasperson.equals(Constant.HASPERSON))
				{
					DataGetUtilSimple.ConnectSevert(Constant.GET_W_HASPERSONPOINTINFO+roomid+Constant.GET_W_HASPERSONPOINTINFO+ptypeid);
				}
				initTableInfo=TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);
			}catch(SocketTimeoutException e)
			{
				e.printStackTrace();
				//��������Ի���
				sendHandlerMsg
				(
					SHOW_TABLEERROE_DIALOG,
					"���ӳ�ʱ�����������������⣬�Ƿ�����",
					Constant.SocketTimeout,
					handler
				);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				sendHandlerMsg
				(
						SHOW_TABLEERROE_DIALOG,
						"��ȡ���ݳ�ʱ�����������������������",
						Constant.IOExcep,
						handler
				);
			}
			catch(Exception e)
			  {
				e.printStackTrace();
				sendHandlerMsg
				(
						SHOW_TABLEERROE_DIALOG,
						"��ȡ���ݳ�ʱ�����������������������",
						Constant.IOExcep,
						handler
				);
				}
		}
	//�޸Ĳ�̨��״̬����̨��
		public static void openTableUpdate(String tname,String guestnum,Handler handler)
		{
			try
			{
				    DataGetUtilSimple.ConnectSevert(Constant.GET_W_GUESTNUMBYNAME+tname+Constant.GET_W_GUESTNUMBYNAME+guestnum);
				    if(DataGetUtilSimple.readinfo.equals("ok"))
				    {
				//���²�̨״̬
					 DataGetUtilSimple.ConnectSevert(Constant.UPDATECLIENT_POINT+tname+Constant.UPDATECLIENT_POINT+"1");
					 if(DataGetUtilSimple.readinfo.equals("ok"))
					 {
						 sendHandlerMsg
							(
								TableInfoConstant.SHOW_TOAST_MESSAGE,
							     "��̨�ɹ������������",
								Constant.ToastMessage,
								handler
							);
						 handler.sendEmptyMessage(CLOSE_CUR_ACTIVITY);
					 }
					 else
					 {
						 sendHandlerMsg
							(
								TableInfoConstant.SHOW_TOAST_MESSAGE,
							     "��̨ʧ�ܣ��˲�̨�Ѿ���̨�����˽����ѹ���",
								Constant.ToastMessage,
								handler
							); 
					 }
				    }
				 else
				 {
					 sendHandlerMsg
						(
							TableInfoConstant.SHOW_TOAST_MESSAGE,
						     "����������������Ƿ񳬳����̨�Ѿ���̨",
							Constant.ToastMessage,
							handler
						); 
				 }
			}
			catch(SocketTimeoutException e)
			{
				e.printStackTrace();
				//��������Ի���
				sendHandlerMsg
				(
					TableInfoConstant.SHOW_UPDATETABLEERROR_DIALOG,
					"���ӳ�ʱ�����������������⣬�Ƿ�����",
					Constant.SocketTimeout,
					handler
				);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				sendHandlerMsg
				(
						TableInfoConstant.SHOW_UPDATETABLEERROR_DIALOG,
						"��ȡ���ݳ�ʱ�����������������������",
						Constant.IOExcep,
						handler
				);
			}
			catch(Exception e)
			  {
				e.printStackTrace();
				sendHandlerMsg
				(
						TableInfoConstant.SHOW_UPDATETABLEERROR_DIALOG,
						"��ȡ���ݳ�ʱ�����������������������",
						Constant.IOExcep,
						handler
				);
				}
		}
	//��SelectVege��ֱ�ӳ�ʼ����̨����
	public static void searchTableState(final Handler handler,final String ishasperson)
			{
		
			new Thread()
			{
				public void run()
			      {
					this.setName("cancelopentableThread--MoreFunctionActivity");
					try{
						Map<String,Object> pointInfo = new HashMap<String,Object>();
						DataGetUtilSimple.ConnectSevert(Constant.GET_W_ROOM_POINTTYPE);
						String str[] = DataGetUtilSimple.readinfo.split("~");
						//��ȡ������Ϣ
						String[][] listroom=TypeExchangeUtil.getString(str[0]);
						//��ȡ��̨����
						String[][] listptype=TypeExchangeUtil.getString(str[1]);
						if(ishasperson.equals(Constant.ALLPERSON))//����
						{
							DataGetUtilSimple.ConnectSevert(Constant.GET_W_POINTINFO+listroom[0][0]+Constant.GET_W_POINTINFO+listptype[0][0]);
						}else if(ishasperson.equals(Constant.NOPERSON))
						{
							DataGetUtilSimple.ConnectSevert(Constant.GET_W_NOPERSONPOINTINFO+listroom[0][0]+Constant.GET_W_NOPERSONPOINTINFO+listptype[0][0]);
						}
						else if(ishasperson.equals(Constant.HASPERSON))
						{
							DataGetUtilSimple.ConnectSevert(Constant.GET_W_HASPERSONPOINTINFO+listroom[0][0]+Constant.GET_W_HASPERSONPOINTINFO+listptype[0][0]);
						}					
						List<String []> initpointinfo = TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);
						
						pointInfo.put("room",listroom);
						pointInfo.put("pointtype",listptype);
						pointInfo.put("initpointinfo",initpointinfo);
						SelectTableActivity.pointInfo=pointInfo;
						handler.sendEmptyMessage(SelectVegeConstant.GO_SELECT_TABLE);
					}
				catch(SocketTimeoutException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					//��������Ի���
					sendHandlerMsg
					(
						SelectVegeConstant.SHOW_TABLEERROE_DIALOG,
						"���ӳ�ʱ�����������������⣬�����³���",
						Constant.SocketTimeout,
						handler
					);
				}
				catch(IOException e)
				{
					e.printStackTrace();
					sendHandlerMsg
					(
							SelectVegeConstant.SHOW_TABLEERROE_DIALOG,
							"��ȡ���ݳ�ʱ�����������������������",
							Constant.IOExcep,
							handler
					);
				}
				catch(Exception e)
				  {
					e.printStackTrace();
					sendHandlerMsg
					(
							SelectVegeConstant.SHOW_TABLEERROE_DIALOG,
							"��ȡ���ݳ�ʱ�����������������������",
							Constant.IOExcep,
							handler
					);
					}
			      }
			}.start();
		      
			}	
	//��̨��ʾ���˲�̨��Ϣ
	public static void newTableInfo(Handler handler)
			{
				try{
					Map<String,Object> pointInfo = new HashMap<String,Object>();
					DataGetUtilSimple.ConnectSevert(Constant.GET_W_ROOM_POINTTYPE);
					String str[] = DataGetUtilSimple.readinfo.split("~");
					//��ȡ������Ϣ
					String[][] listroom=TypeExchangeUtil.getString(str[0]);
					//��ȡ��̨����
					String[][] listptype=TypeExchangeUtil.getString(str[1]);
					DataGetUtilSimple.ConnectSevert(Constant.GET_W_NOPERSONPOINTINFO+listroom[0][0]+Constant.GET_W_NOPERSONPOINTINFO+listptype[0][0]);
					List<String []> initpointinfo = TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);
					pointInfo.put("room",listroom);
					pointInfo.put("pointtype",listptype);
					pointInfo.put("initpointinfo",initpointinfo);
					SelectTableActivity.pointInfo=pointInfo;
//					handler.sendEmptyMessage(TableInfoConstant.GO_SELECT_TOW_TABLE);
				}
				catch(SocketTimeoutException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					//��������Ի���
					sendHandlerMsg
					(
						TableInfoConstant.SHOW_TOAST_MESSAGE,
						"���ӳ�ʱ�����������������⣬�����³���",
						Constant.ToastMessage,
						handler
					);
				}
				catch(IOException e)
				{
					e.printStackTrace();
					//�رյȴ��Ի���
					sendHandlerMsg
					(
							TableInfoConstant.SHOW_TOAST_MESSAGE,
							"���ݻ�ȡ�쳣�������»�ȡ",
							Constant.ToastMessage,
							handler
					);
				}
				catch(Exception e)
				  {
					e.printStackTrace();
					//�رյȴ��Ի���
					sendHandlerMsg
					(
							TableInfoConstant.SHOW_TOAST_MESSAGE,
							"���ݻ�ȡ�쳣�������»�ȡ",
							Constant.ToastMessage,
							handler
					);
					}
			}
	//���˲�̨��ʾ��̨��Ϣ
	public static void oldTableInfo(final Handler handler)
		{
		   new Thread()
		   {
			   public void run()
			   {
				   try{
						Map<String,Object> pointInfo = new HashMap<String,Object>();
						DataGetUtilSimple.ConnectSevert(Constant.GET_W_ROOM_POINTTYPE);
						String str[] = DataGetUtilSimple.readinfo.split("~");
						//��ȡ������Ϣ
						String[][] listroom=TypeExchangeUtil.getString(str[0]);
						//��ȡ��̨����
						String[][] listptype=TypeExchangeUtil.getString(str[1]);
						DataGetUtilSimple.ConnectSevert(Constant.GET_W_HASPERSONPOINTINFO+listroom[0][0]+Constant.GET_W_HASPERSONPOINTINFO+listptype[0][0]);
						List<String []> initpointinfo = TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);
						pointInfo.put("room",listroom);
						pointInfo.put("pointtype",listptype);
						pointInfo.put("initpointinfo",initpointinfo);
						SelectTableActivity.pointInfo=pointInfo;
						handler.sendEmptyMessage(TableInfoConstant.GO_SELECT_TABLE);
					}
					catch(SocketTimeoutException e)
					{
						e.printStackTrace();
						//�رյȴ��Ի���
						//��������Ի���
						sendHandlerMsg
						(
							TableInfoConstant.SHOW_TOAST_MESSAGE,
							"���ӳ�ʱ�����������������⣬�����³���",
							Constant.ToastMessage,
							handler
						);
					}
					catch(IOException e)
					{
						e.printStackTrace();
						//�رյȴ��Ի���
						sendHandlerMsg
						(
								TableInfoConstant.SHOW_TOAST_MESSAGE,
								"���ݻ�ȡ�쳣�������»�ȡ",
								Constant.ToastMessage,
								handler
						);
					}
					catch(Exception e)
					  {
						e.printStackTrace();
						//�رյȴ��Ի���
						sendHandlerMsg
						(
								TableInfoConstant.SHOW_TOAST_MESSAGE,
								"���ݻ�ȡ�쳣�������»�ȡ",
								Constant.ToastMessage,
								handler
						 );
					}
			   }
		   }.start();
			
		}
	//���˲�̨��ʾ��̨��Ϣ
		public static void mergeTableInfo(final Handler handler)
			{
			   new Thread()
			   {
				   public void run()
				   {
					   try{
							Map<String,Object> pointInfo = new HashMap<String,Object>();
							DataGetUtilSimple.ConnectSevert(Constant.GET_W_ROOM_POINTTYPE);
							String str[] = DataGetUtilSimple.readinfo.split("~");
							//��ȡ������Ϣ
							String[][] listroom=TypeExchangeUtil.getString(str[0]);
							//��ȡ��̨����
							String[][] listptype=TypeExchangeUtil.getString(str[1]);
							DataGetUtilSimple.ConnectSevert(Constant.GET_W_HASPERSONPOINTINFO+listroom[0][0]+Constant.GET_W_HASPERSONPOINTINFO+listptype[0][0]);
							List<String []> initpointinfo = TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);
							pointInfo.put("room",listroom);
							pointInfo.put("pointtype",listptype);
							pointInfo.put("initpointinfo",initpointinfo);
							SelectTableActivity.pointInfo=pointInfo;
//							handler.sendEmptyMessage(TableInfoConstant.GO_SELECT_TOW_TABLE);
						}
						catch(SocketTimeoutException e)
						{
							e.printStackTrace();
							//�رյȴ��Ի���
							//��������Ի���
							sendHandlerMsg
							(
								TableInfoConstant.SHOW_TOAST_MESSAGE,
								"���ӳ�ʱ�����������������⣬�����³���",
								Constant.ToastMessage,
								handler
							);
						}
						catch(IOException e)
						{
							e.printStackTrace();
							//�رյȴ��Ի���
							sendHandlerMsg
							(
									TableInfoConstant.SHOW_TOAST_MESSAGE,
									"���ݻ�ȡ�쳣�������»�ȡ",
									Constant.ToastMessage,
									handler
							);
						}
						catch(Exception e)
						  {
							e.printStackTrace();
							//�رյȴ��Ի���
							sendHandlerMsg
							(
									TableInfoConstant.SHOW_TOAST_MESSAGE,
									"���ݻ�ȡ�쳣�������»�ȡ",
									Constant.ToastMessage,
									handler
							 );
						}
				   }
			   }.start();				
			}		
//���ݲ�Ʒid�õ���Ʒ��Ϣ
public static void getVegeIntro(final String vegeid,final Handler handler) 
{
	new Thread()
	{
		public void run()
		{	
			 Map<String,Object> vegeintro=new HashMap<String,Object>();
			 try
			 {
			   String[] vegemsg=VegeIntroMsgUtil.getVege(vegeid);
		       final String[] str=vegemsg[7].split(",");
		       Bitmap[] vegeimage=new Bitmap[str.length];
		       for(int i=0;i<str.length;i++)
		       {
		    	   String[] vgi=str[i].split("/");
				   String bm=vgi[vgi.length-1];
				   String bmId=bm.replace(".jpg","");
				   Bitmap broadcastBmp=PicManagerUtil.getBM(bmId);
				   vegeimage[i]=broadcastBmp;
		       }
		       vegeintro.put("vegeinfo", vegemsg);
		       vegeintro.put("vegebminfo", vegeimage);	    
		       VegeActivity.vegeintromap=vegeintro;
		       handler.sendEmptyMessage(VegeImageConstant. GOTO_VEGE_INTRO_MESSAGE);		       
			 }
			 catch(UnknownHostException e)
			 {
			    e.printStackTrace();
				//��������Ի���
				sendHandlerMsg
				(
					VegeImageConstant.OPEN_ERROR_TOAST_MESSAGE,
					"���ӷ�����ʧ�ܣ���鿴�����������³���",
					Constant.UnknownHost,
					handler
				);
		     }
			 catch(SocketTimeoutException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						VegeImageConstant.OPEN_ERROR_DIALOG_MESSAGE,
						"��ȡ���ݳ�ʱ��������������",
						Constant.SocketTimeout,
						handler
					);
				}
				catch(StringIndexOutOfBoundsException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						VegeImageConstant.OPEN_ERROR_DIALOG_MESSAGE,
						"���ݻ�ȡ����������������",
						Constant.StringIndexOutOfBounds,
						handler
					);
				}
				catch(EOFException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						VegeImageConstant.OPEN_ERROR_TOAST_MESSAGE,
						"���ݻ�ȡ����������������",
						Constant.EOFExcep,
						handler
					);
				}
				catch(NullPointerException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						VegeImageConstant.OPEN_ERROR_DIALOG_MESSAGE,
						"NullPointerException��ȡ����ʧ�ܣ�������������",
						Constant.NullPointer,
						handler
					);
				}
				catch(SocketException e)
				{
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						VegeImageConstant.OPEN_ERROR_DIALOG_MESSAGE,
						"��������ʧ�ܣ�������������",
						Constant.SocketExcep,
						handler
					);
				} 
				catch(IOException e)
				{
					e.printStackTrace();
					sendHandlerMsg
					(
						VegeImageConstant.OPEN_ERROR_TOAST_MESSAGE,
						"��ȡ���ݳ�ʱ���Ƿ�����",
						Constant.IOExcep,
						handler
					);
				}
				catch (Exception e) 
				{  
					e.printStackTrace();
					//��������Ի���
					sendHandlerMsg
					(
						VegeImageConstant.OPEN_ERROR_DIALOG_MESSAGE,
						"��ȡ����ʧ�ܣ������³���",
						Constant.Excep,
						handler
					);
				}
		}
	}.start();
}
//���Ͳ��ԣ��Ƿ����ӵ��Ϸ�����
public static void testConnect(final Handler handler)
{
	 final Message msg=new Message();
	 final Bundle b=new Bundle();
	 msg.what=ResetConstant.TESTCONNECT;
	new Thread()
	{
		public void run()
		{
			 try
			 {
				DataGetUtilSimple.ConnectSevert(Constant.TESTCONNECT);
				 String testmsg=DataGetUtilSimple.readinfo;
				 b.putString("msg",testmsg);
				 msg.setData(b);
				 handler.sendMessage(msg);
			 }
			 catch(UnknownHostException e)
			 {
				 e.printStackTrace();
				 handler.sendEmptyMessage(ResetConstant.TESTCONNECTERROR);
			 }
			 catch(SocketException e)
			 {
				 e.printStackTrace();
				//��������Ի���
				handler.sendEmptyMessage(ResetConstant.TESTCONNECTERROR);
			 }
			catch (Exception e) 
			{  
				e.printStackTrace();
				//��������Ի���
				handler.sendEmptyMessage(ResetConstant.TESTCONNECTERROR);
			}
		}
	}.start();
}
//���ݲ�̨�ŵõ�����
public static int guestNumByDeskId(final String deskname,final Handler handler)
{	
    int orderguestNum=0;
	 try
	 {
		 DataGetUtilSimple.ConnectSevert(Constant.GET_CURDESK_GUESTNUM+deskname);
		 if(!DataGetUtilSimple.readinfo.equals("fail"))
		 {
			 orderguestNum=Integer.parseInt(DataGetUtilSimple.readinfo);
		 }
		 handler.sendEmptyMessage(TableInfoConstant.GUEST_NUM_MESSAGE);
	 }
	 catch(UnknownHostException e)
	 {
		 sendHandlerMsg
			(
				TableInfoConstant.SHOW_TOAST_MESSAGE,
				"������������³���",
				Constant.ToastMessage,
				handler
			);
	 }
	 catch(SocketException e)
	 {
		 sendHandlerMsg
			(
				TableInfoConstant.SHOW_TOAST_MESSAGE,
				"������������³���",
				Constant.ToastMessage,
				handler
			);
	 }
	catch (Exception e) 
	{  
		sendHandlerMsg
		(
			TableInfoConstant.SHOW_TOAST_MESSAGE,
			"������������³���",
			Constant.ToastMessage,
			handler
		);
	}
	return orderguestNum;
}
//���ʹ���handler��Ϣ
public static void sendHandlerMsg(int msgwhat,String errorpoint,int excepFlg,Handler h)
{
	//��������Ի���
	Message msg=new Message();
	Bundle b=new Bundle();
	msg.what=msgwhat;
	b.putInt("excepFlg",excepFlg);
	b.putString("msg",errorpoint);
	msg.setData(b);
	h.sendMessage(msg);
}
//==========================================================��������begin
//��ʼ������������Ϣ
//if�����жϺ��쳣�������Ӧ�ý��ʹ��
public static void initOrderManageInfo(String deskName,Handler handler)
{
	try
	{
		//�жϵ�ǰ�Ƿ�̨��û�п�̨�������ʾ
		if(deskName==null)
		{
			Message msg=new Message();
			Bundle bundle=new Bundle();
			msg.what=OrderManageConstant.TOAST_HINT;
			bundle.putString("hint", "δ��̨��������ѯ��ť��ѯ����");
			msg.setData(bundle);
			handler.sendMessage(msg);
			return;  
		}
		//���ݲ�̨id,��ȡ��Ӧ�ĵ��ȷ�ϵ��е���Ϣ
		DataGetUtilSimple.ConnectSevert(Constant.GET_W_ORDERDISHCONFIRM+deskName);
		String orderDishConfirmInfo=DataGetUtilSimple.readinfo;
		//�Կ�̨��û���µ�����������ж�
		if(orderDishConfirmInfo.length()==0)
		{	//������������ѯ�Ľ�����Ϊnull��˵����ǰ�������޶���
			Message msg=new Message();
			Bundle bundle=new Bundle();
			msg.what=OrderManageConstant.TOAST_HINT;
			if(OrderManageActivity.alreadExistOrder || (OrderManageActivity.tv_orderid!=null && OrderManageActivity.tv_orderid.length()!=0))
			{
				bundle.putString("hint", "��ǰ���Ӷ������޲�Ʒ��Ϣ");
				OrderManageActivity.alreadExistOrder=false;
				OrderManageActivity.vegeInforesult=null;
				OrderManageActivity.orderDishConfirm=null;
				handler.sendEmptyMessage(OrderManageConstant.FRESH_ADAPTER_DATA);
			}
			else
			{
				bundle.putString("hint", "��ǰ�����޶���");
			}
			msg.setData(bundle);
			handler.sendMessage(msg);
			return;
		}
		//���ȷ�ϵ��е���Ϣ
		List<String[]> orderDishConfirm = TypeExchangeUtil.strToList(orderDishConfirmInfo);
		OrderManageActivity.vegeInforesult=orderDishConfirm;
	}
	catch(UnknownHostException e)
	{
	    e.printStackTrace();
		//��������Ի���
	    sendHandlerMsg
		(
			OrderManageConstant.OPEN_ERROR_DIALOG_MESSAGE,
			"���ӷ�����ʧ�ܣ���鿴�����������³���",
			OrderManageConstant.INIT_ORDERMANAGE_EXCEP,
			handler
		);
	}
	catch(SocketTimeoutException e)
	{
		e.printStackTrace();
		//��������Ի���
		sendHandlerMsg
		(
			OrderManageConstant.OPEN_ERROR_DIALOG_MESSAGE,
			"�������ӳ�ʱ�����������������,�Ƿ�����",
			OrderManageConstant.INIT_ORDERMANAGE_EXCEP,
			handler
		);
	}
	catch(NullPointerException e)
	{
		e.printStackTrace();
		//ͨ��Toast�����쳣��ʾ����Ϊ��Щ��ȡ���������ǿգ���˷�����ָ���쳣
		Message msg=new Message();
		Bundle bundle=new Bundle();
		msg.what=OrderManageConstant.TOAST_HINT;
		bundle.putString("hint", "��������ʧ�������³���");
		msg.setData(bundle);
		handler.sendMessage(msg);
	}
	catch(OutOfMemoryError e)
	{
		e.printStackTrace();
		//��������Ի���
		sendHandlerMsg
		(
			OrderManageConstant.OPEN_ERROR_DIALOG_MESSAGE,
			"��ȡ�����쳣�������³���",
			OrderManageConstant.INIT_ORDERMANAGE_EXCEP,
			handler
		);
	}
	catch(EOFException e)
	{
		e.printStackTrace();
		//��������Ի���
		sendHandlerMsg
		(
			OrderManageConstant.OPEN_ERROR_DIALOG_MESSAGE,
			"��ȡ����ʧ�ܣ��Ƿ�����",
			OrderManageConstant.INIT_ORDERMANAGE_EXCEP,
			handler
		);
	}
	catch(SocketException e)
	{
		e.printStackTrace();
		//��������Ի���
		sendHandlerMsg
		(
			OrderManageConstant.OPEN_ERROR_DIALOG_MESSAGE,
			"�������������쳣���Ƿ����»�ȡ����",
			OrderManageConstant.INIT_ORDERMANAGE_EXCEP,
			handler
		);
	} 
	catch (Exception e) 
	{  
		//��������Ի���
		sendHandlerMsg
		(
			OrderManageConstant.OPEN_ERROR_DIALOG_MESSAGE,
			"��ȡ����ʧ�ܣ������³���",
			OrderManageConstant.INIT_ORDERMANAGE_EXCEP,
			handler
		);
	}
}
//ɾ�����ȷ�ϵ��еĲ�Ʒ
public static String deleteVegeInfo(String[] vegeInfo)
{
	String result=Constant.FAIL;
	StringBuffer sb=new StringBuffer();
	try
	{
		if(vegeInfo!=null)
		{
			for(int i=0; i<vegeInfo.length; i++)
			{
				sb.append(Constant.DELETE_W_ORDERCONFIRMVEGE+vegeInfo[i]);
			}
		}
		DataGetUtilSimple.ConnectSevert(sb.toString());
		result=DataGetUtilSimple.readinfo;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return result;
	}
	return result;
}
//��Ӳ�Ʒ����
public static String addVegeCount(String[] vegeInfo)
{
	String result=Constant.FAIL;
	StringBuffer sb=new StringBuffer();
	try
	{
		if(vegeInfo!=null)
		{
			for(int i=0; i<vegeInfo.length; i++)
			{
				sb.append(Constant.ADD_W_VEGESINGLECOUNT+vegeInfo[i]);
			}
		}
		DataGetUtilSimple.ConnectSevert(sb.toString());
		result=DataGetUtilSimple.readinfo;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return result;
	}
	return result;
}
//���ٲ�Ʒ����
public static String subVegeCount(String[] vegeInfo)
{
	String result=Constant.FAIL;
	StringBuffer sb=new StringBuffer();
	try
	{
		if(vegeInfo!=null)
		{
			for(int i=0; i<vegeInfo.length; i++)
			{
				sb.append(Constant.DELETE_W_VEGESINGLECOUNT+vegeInfo[i]);
			}
		}
		DataGetUtilSimple.ConnectSevert(sb.toString());
		result=DataGetUtilSimple.readinfo;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return result;
	}
	return result;
}
   //���ز�Ʒ��Ϣ������sqlite���ݿ�
   public static void uploadvege(final Handler handler)
   {
	   new Thread()
	 {
		public void run() 
		{
		try{
				//���ز�Ʒ��Ϣ
				DataGetUtilSimple.ConnectSevert(Constant.DOWNLOAD_VEGE);
				List<String[]> vegemsg=TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);				
				//�õ���Ʒ��ͼƬ��Ϣ
				for(int i=0;i<vegemsg.size();i++)
				{
			      //�õ���Ʒ����ͼƬ
				  DataGetUtilSimple.ConnectSevert(Constant.GET_IMAGE+vegemsg.get(i)[22]);
				  byte[] main_image= DataGetUtilSimple.data;
				  //�����µ�ͼƬ·��
				  String mainpath=vegemsg.get(i)[22];
				  String[] imagepath=mainpath.split("/");
				  String imagename=imagepath[imagepath.length-1];
				  //����sdcard��
				  PicUtil.saveImage(main_image, imagename);
				  //��·�������޸�
				  vegemsg.get(i)[22]=Environment.getExternalStorageDirectory().toString()+"/OrderDish/pic/"+imagename;				  
				  //�õ���Ʒ����ͼƬ
				  String[] path=vegemsg.get(i)[23].split(",");
				  StringBuilder sbmsgpath=new StringBuilder();
				  for(int j=0;j<path.length;j++)
				  {
					  DataGetUtilSimple.ConnectSevert(Constant.GET_IMAGE+path[j]);
 					  byte[] imagemsg= DataGetUtilSimple.data;
 					  //�����µ�ͼƬ·��
 					  imagepath=path[j].split("/");
 					  imagename=imagepath[imagepath.length-1];
					  //����sdcard��
					  PicUtil.saveImage(imagemsg, imagename);
					  sbmsgpath.append(Environment.getExternalStorageDirectory().toString()+"/OrderDish/pic/"+imagename+",");
				   }
				  //��·�������޸�
				  vegemsg.get(i)[23]=sbmsgpath.toString();
				}	
				//�������ݿ�
			    DBUtil.insert("vege",vegemsg);
			    
			    List<String[]> childCateList=new ArrayList<String[]>();
				//���ݲ���ID�����Ӧ������Ʒ��Ϣ
				DataGetUtilSimple.ConnectSevert(Constant.GET_W_MCGINFO+Constant.roomId);
				List<String[]> mainCateList=TypeExchangeUtil.strToList(DataGetUtilSimple.readinfo);
			    //�������ݿ��еĲ�Ʒ������Ϣ�Լ���������ID����������Ϣ  		
				for(int i=0;i<mainCateList.size();i++)
			    {
					DataGetUtilSimple.ConnectSevert(Constant.GET_CGBYMCG+mainCateList.get(i)[1]);
					String cgmsginfo=DataGetUtilSimple.readinfo;
					String[] c=cgmsginfo.split("#");
					if(cgmsginfo.length()==0)
					{
						childCateList.add(null);
					}
					else
					{
						childCateList.add(c);
					}
			    }
				//�������ݿ�
			    DBUtil.insertCate(mainCateList,childCateList);
			    handler.sendEmptyMessage(MainConstant.OPEN_UPLOAD_DIALOG_MESSGE);
			}
			catch(UnknownHostException e)
			{
			    e.printStackTrace();
				//��������Ի���
			    sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"���ӷ�����ʧ�ܣ���鿴��������",
					Constant.UnknownHost,
					handler
				);
	    	}
			catch(NoSdCardException e)
			{
			    e.printStackTrace();
				//��������Ի���
			    sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"δ��⵽SD��,����룡����",
					Constant.ToastMessage,
					handler
				);
	    	}
			catch(SocketTimeoutException e)
			{
				e.printStackTrace();
				//��������Ի���
				sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"�������ӳ�ʱ�����������������",
					Constant.SocketTimeout,
					handler
				);
			}
			catch(OutOfMemoryError e)
			{
				e.printStackTrace();
				//��������Ի���
				sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"��ȡ�����쳣����鿴�������",
					Constant.SocketTimeout,
					handler
				);
			}
			catch(StringIndexOutOfBoundsException e)
			{
				e.printStackTrace();
				//��������Ի���
				sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"���ݻ�ȡ�������Ժ�����",
					Constant.StringIndexOutOfBounds,
					handler
				);
			}
			
			catch(NullPointerException e)
			{
				e.printStackTrace();
				//��������Ի���
				sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"��ȡ����ʧ�ܣ�������������",
					Constant.NullPointer,
					handler
				);
			}
			catch(SocketException e)
			{
				e.printStackTrace();
				//��������Ի���
				sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"�������粻�ȶ����ܵ������ݳ�����鿴�������",
					Constant.SocketExcep,
					handler
				);
			} 
			catch(IOException e)
			{
				e.printStackTrace();
				sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"���ݼ����������Ժ�����",
					Constant.IOExcep,
					handler
				);
			}
			catch (Exception e) 
			{  
				e.printStackTrace();
				sendHandlerMsg
				(
					MainConstant.OPEN_UPLOADERROR_DISLOG_MESSAGE,
					"���ݼ��س������Ժ�����",
					Constant.IOExcep,
					handler
				);
			}
		}
	 }.start();
   }
}