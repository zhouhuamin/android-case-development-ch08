package com.bn.serverinfo;//���������

import java.awt.image.BufferedImage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.bn.serverinfo.TypeExchangeUtil.*;
import static com.bn.serverinfo.Constant.*;
public class ServerAgentThread extends Thread{//����һ����ServerAgentThread�ļ̳��̵߳���
		private Socket sc;         //����Socket
		private DataInputStream in;//��������������
		private DataOutputStream out;//�������������
		private static final String ok="ok";//�����ַ���ok
		private static final String fail="fail";//�����ַ���fail
		InputStream ips;//����������
		int count;//��������
		public ServerAgentThread(Socket sc){//���幹����		
			try{			
				this.sc=sc;//����Socket
				in=new DataInputStream(sc.getInputStream());//����������������
				out=new DataOutputStream(sc.getOutputStream());	//���������������
			}
			catch(Exception e){			
				e.printStackTrace();//�����쳣
			}
		}
		public void run(){//��дrun����
			try{
				String readinfo=IOUtil.readStr(in);//�������ж�ȡ�����ݷ����ַ�����
				System.out.println("readinfo==="+readinfo);//��ӡ�����ж�ȡ������
		//================Pc�������ʶͷ begin====================

		//�õ�������λ����Ϣ
		if(readinfo.equals(GET_CU)){				
					List<String[]> list=DBUtil.getCU();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//�õ�������λ�������
				else if(readinfo.equals(GET_CUMAXNO)){				
					String maxno=DBUtil.getCUMaxNO();
					IOUtil.writeStr(out,  MyConverter.escape(maxno));
				}
				//���¼�����λ
				else if(readinfo.startsWith(UPDATE_CU)){				
					String[] getmsg=readinfo.split(UPDATE_CU);
					String strinfo=DBUtil.updateCU(getmsg);
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//ɾ��������λ��Ϣ
				else if(readinfo.startsWith(DEL_CU)){				
					String[] getmsg=readinfo.split(DEL_CU);
					String strinfo=DBUtil.delCU(getmsg[1]);
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//�õ���ϵ��Ϣ
				else if(readinfo.equals(GET_VT)){				
					List<String[]> list=DBUtil.getVegeType();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//�õ���ϵ�������
				else if(readinfo.equals( GET_VTMAXNO)){				
					String maxno=DBUtil.getVTMaxNo();
					IOUtil.writeStr(out,  MyConverter.escape(maxno));
				}
				//���²�ϵ��Ϣ
				else if(readinfo.startsWith(UPDATE_VT)){
					String[] getmsg=readinfo.split(UPDATE_VT);
					String strinfo=DBUtil.updateVT(getmsg);
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//ɾ����ϵ
				else  if(readinfo.startsWith(DEL_VT)){
					String[] getmsg=readinfo.split(DEL_VT);
					String strinfo=DBUtil.delVT(getmsg[1]);
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//�����������Ϣ
				else if(readinfo.equals(GET_MCG)){				
					List<String[]> list=DBUtil.getMCG();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//�õ������������
				else if(readinfo.equals(GET_MCGMAXNO)){
					String maxno=DBUtil.getMCGMaxNo();
					System.out.println(maxno);
					IOUtil.writeStr(out,  MyConverter.escape(maxno));
				}//�����������Ϣ
				else if(readinfo.startsWith(UPDATE_CMG)){				
					String[] getmsg=readinfo.split(UPDATE_CMG);
					//���ݲ��ŵ����Ƶõ����ŵ�id
					String strinfo=DBUtil.updateMCG(getmsg[1],getmsg[2]);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
                // ���������Ϣ
				else if(readinfo.startsWith(ADD_CMG)){				
					String[] getmsg=readinfo.split(ADD_CMG);
					//���ݲ��ŵ����Ƶõ����ŵ�id
					String strinfo=DBUtil.addMCG(getmsg[1],getmsg[2]);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//ɾ��������Ϣ
				else if(readinfo.startsWith(DEL_CMG)){				
					String[] getmsg=readinfo.split(DEL_CMG);
					String strinfo=DBUtil.delMCG(getmsg[1]);
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}//�õ���Ʒ������Ϣ
				else if(readinfo.equals(GET_CG)){				
					List<String[]> list=DBUtil.getCG();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out,MyConverter.escape(strinfo));
				}//�õ���Ʒ���������
				else if(readinfo.equals(GET_CGMAXNO)){				
					String maxno=DBUtil.getCGMaxNo();
					IOUtil.writeStr(out,  MyConverter.escape(maxno));
				}
				//��Ӳ�Ʒ���
				else if(readinfo.startsWith(ADD_CG)){				
					String[] getmsg=readinfo.split(ADD_CG);
					//������������Ƶõ������id
					String mcgid=DBUtil.getMCGidByname(getmsg[5]);
					String strinfo=DBUtil.addCG(getmsg,mcgid);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//���²�Ʒ�����Ϣ
				else if(readinfo.startsWith(UPDATE_CG)){				
					String[] getmsg=readinfo.split(UPDATE_CG);
					//������������Ƶõ������id
					String mcgid=DBUtil.getMCGidByname(getmsg[5]);
					String time=gettime();
					String strinfo=DBUtil.updateCG(getmsg,time,mcgid);//���ø��²�Ʒ�����Ϣ�ķ���
					if(strinfo==null){					
						IOUtil.writeStr(out, MyConverter.escape(fail));
					}else{					
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}//ɾ����Ʒ���
				else if(readinfo.startsWith(DEL_CG)){				
					String[] getmsg=readinfo.split(DEL_CG);
					String strinfo=DBUtil.delCG(getmsg[1]);//����ɾ����Ʒ���ķ���
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//�õ�������Ϣ
				else if(readinfo.equals(GET_ROOM)){				
					List<String[]> list=DBUtil.getRoom();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//�õ�������Ϣ
				else if(readinfo.equals(GET_VS)){				
					List<String[]> list=DBUtil.getVS();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//�õ����������
				else if(readinfo.equals(GET_VSMAXNO)){				
					String maxno=DBUtil.getVSMaxNO();
					IOUtil.writeStr(out,  MyConverter.escape(maxno));
				}
				//���¹����Ϣ
				else if(readinfo.startsWith(UPDATE_VS)){			
					String[] getmsg=readinfo.split(UPDATE_VS);
					String strinfo=DBUtil.updateVS(getmsg);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//ɾ�������Ϣ
				else if(readinfo.startsWith(DEL_VS)){				
					String[] getmsg=readinfo.split(DEL_VS);
					String strinfo=DBUtil.delVS(getmsg[1]);//����ɾ�������Ϣ�ķ���
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}//�������౻�����Ƶõ�����������
				else if(readinfo.startsWith(GETMCGNAMEBYCGNAME)){				
					String[] getmsg=readinfo.split(GETMCGNAMEBYCGNAME);
					String strinfo=DBUtil.getMcgnameByCgname(getmsg[1]);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
					//��ͼƬ���ص�������
				else if(readinfo.startsWith(UPLOAD_IMAGE)){				
                       //��Stringת����byte����
					   String mz=in.readUTF();
					   byte[] imagemsg=IOUtil.readBytes(in);
			            File dir = new File(IMAGE_PATH);//ͨ��������·��pathת��Ϊ����·����������һ���� File ʵ��
			            //�����ļ���
			            if(!dir.exists()){//�����ļ��в����ڣ���ô���½�һ���ļ���			            
			            	dir.mkdir();
			            }
			           //�����ļ�
			            File file = new File(dir,count+".jpg");//���ݸ����ļ��еĳ���·������ͼƬ��·�����ַ�������һ���� File ʵ��
			            while(file.exists()) {//���ļ�����ʱ			           
			               file = new File(dir,(++count)+".jpg"); //ѭ������ ���ļ�ʵ��
			            }
			            String filename=count+".jpg";//�����ļ�����
					   if(mz.equals(UPLOAD_MIMAGE)){//������ͼ
						   BufferedImage image=PicScaleUtil.scaledImg(imagemsg, width, height);//����ͼƬ
						   PicScaleUtil.saveImage(image,IMAGE_PATH+filename);//����ͼƬ
				           BufferedImage imagem=PicScaleUtil.scaledImgByWid(image, xwidth);//����ȵȱ�������ͼƬ
				           String[] s=filename.split(".jpg");//��ͼƬ������Ϣ��".jpg"Ϊ��ָ�
				           String mname=String.valueOf((Integer.parseInt(s[0])+1));//������ͼ�����ַ�������ͼ����Ϊ��ͼ���Ƶ�����+1��
				           PicScaleUtil.saveImage(imagem, IMAGE_PATH+mname+".jpg");//����ͼƬ
				           IOUtil.writeStr(out,  MyConverter.escape(IMAGE_PATH+filename)+"#"+MyConverter.escape(IMAGE_PATH+mname+".jpg")); //������ͼ��Ϣд����  
					   }else if(mz.equals(UPLOAD_ZIMAGE)){//������ͼ
						   BufferedImage image=PicScaleUtil.scaledImg(imagemsg, width, height);//����ͼƬ
						   PicScaleUtil.saveImage(image,IMAGE_PATH+filename);//����ͼƬ
				           IOUtil.writeStr(out, MyConverter.escape(IMAGE_PATH+filename));//����ͼ��Ϣд�뵽��
					   }else if(mz.equals(UPLOAD_MAIN_VEGEIAMGE)) {//�ϴ���Ʒ��ͼ					  
						   BufferedImage image=PicScaleUtil.scaledImg(imagemsg, width, height);//����ͼƬ
						   BufferedImage imagem=PicScaleUtil.scaledImgByWid(image, xwidth);//����ȵȱ�������ͼƬ
				           PicScaleUtil.saveImage(imagem, IMAGE_PATH+filename);//����ͼƬ
				           IOUtil.writeStr(out, MyConverter.escape(IMAGE_PATH+filename));//д�뵽��
					   }
				}//�õ���Ʒ��Ϣ
				else if(readinfo.equals(GET_VEGE)){				
					List<String[]> vegelist=DBUtil.getVege();
					String strinfo=TypeExchangeUtil.listToString(vegelist);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//�õ���Ʒ�������
				else if(readinfo.equals(GET_VEGEMAXNO)){				
					String maxno=DBUtil.getVegeMaxNO();
					IOUtil.writeStr(out,  MyConverter.escape(maxno));
				}
				//��Ӳ�Ʒ
				else if(readinfo.startsWith(ADD_VEGE)){
					String[] getmsg=readinfo.split(ADD_VEGE);
					//���ݼ�����λ�����Ƶõ�������λ��id
					String cuid=DBUtil.getCUidByname(getmsg[4]);
					//	//���ݹ������Ƶõ�����id
					String ggid=DBUtil.getVSidByname(getmsg[5]);
					//�����������Ƶõ�����id
					String cgid=DBUtil.getCGidByname(getmsg[6]);
					//����ϵ������Ƶõ�ϵ���id
					String vtid=DBUtil.getVTidByname(getmsg[7]);
					//��Ӳ�Ʒ��Ϣ
					String strinfo=DBUtil.addVege(getmsg,cuid,cgid,vtid,ggid);
					if(strinfo.equals("ok")){											
						IOUtil.writeStr(out, MyConverter.escape(ok));
						}else{
							IOUtil.writeStr(out,  MyConverter.escape(fail));
						}
				}
			      //ɾ����Ʒ��Ϣ
			       else if(readinfo.startsWith(DEL_VEGE)){				
					String[] getmsg=readinfo.split(DEL_VEGE);
					String strinfo=DBUtil.delVege(getmsg[1]);
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				else if(readinfo.startsWith(GET_IMAGE)){				
					String[] str=readinfo.split(GET_IMAGE);
					System.out.println("str[1]===="+str[1]);
					byte[] imagemsg=PicScaleUtil.getPic(str[1]);
					IOUtil.writeBytes(out, imagemsg);
				}//���²�Ʒ��Ϣ
				else if(readinfo.startsWith(UPDATE_VEGE)){				
					String[] getmsg=readinfo.split(UPDATE_VEGE);
					//���ݼ�����λ�����Ƶõ�������λ��id
					String cuid=DBUtil.getCUidByname(getmsg[4]);
					//���ݹ������Ƶõ�����id
					String ggid=DBUtil.getVSidByname(getmsg[7]);
					//�����������Ƶõ�����id
					String cgid=DBUtil.getCGidByname(getmsg[5]);
					//����ϵ������Ƶõ�ϵ���id
					String vtid=DBUtil.getVTidByname(getmsg[6]);
					//���²�Ʒ��Ϣ
					String strinfo=DBUtil.updateVege(getmsg,cuid,cgid,vtid,ggid);
					if(strinfo.equals("ok")){					
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}else{					
						IOUtil.writeStr(out, MyConverter.escape(fail));
					}
				}//���ݲ�Ʒid�õ���Ʒ����Ϣ
				else if(readinfo.startsWith(GS_VEGE)){				
					String[] getmsg=readinfo.split(GS_VEGE);//������id�õ���Ʒ��Ϣ��GS_VEGEΪ��ָ�
					String[] msgvege=getmsg[1].split("#");
					List<String[]> vegelist=DBUtil.getVegeByid(msgvege);
					List<String[]> list=new ArrayList<String[]>();//�����µĴ���ַ����������͵��б�
					for(String[] s:vegelist){//������Ʒ��Ϣ�б�					
						String[] msg=new String[s.length];//
						System.out.println(s.length);
						for(int i=0;i<s.length;i++){//�����ַ�������					
							msg[i]=s[i];//�����Ѻ�Ĳ�Ʒ��Ϣ�����µ��ַ���������
						}
						list.add(msg);//�����ַ�����������б�
					}
					String strinfo=TypeExchangeUtil.listToString(list);//��������ת��
					IOUtil.writeStr(out,MyConverter.escape(strinfo));//д����
				}//�õ����в�����Ϣ
				else if(readinfo.startsWith(GET_ROOM)){				
					List<String[]> list=DBUtil.getRoom();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//���²�����Ϣ
				else if(readinfo.startsWith(UPDATE_ROOM)){				
					String str[]=readinfo.split(UPDATE_ROOM);
					String strinfo=DBUtil.updateRoom(str[1],str[2],str[3]);
					if(strinfo==null){					
						IOUtil.writeStr(out, MyConverter.escape(fail));
					}else{					
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}//�õ���������
				else if(readinfo.startsWith(GET_MAXROOMNO)){				
					String strinfo=DBUtil.getMaxRoomId();
					IOUtil.writeStr(out,  MyConverter.escape(strinfo));
				}//�õ����в�������
				else if(readinfo.startsWith(GET_RMNAME)){				
					List<String[]> list=DBUtil.getRoomName();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out,  MyConverter.escape(strinfo));
				}//�õ����в�̨
				else if(readinfo.startsWith(GET_POINT)){				
					String str[]=readinfo.split(GET_POINT);
					List<String[]> list=DBUtil.getPointByroom(str[1]);
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//�õ���̨���no
				else if(readinfo.startsWith(GET_MAXPOINTNO)){			
					int maxnum=Integer.parseInt(DBUtil.getMaxPointId());
					String str=((maxnum+1)<10)?("00"+(maxnum+1)):("0"+(maxnum+1));
					IOUtil.writeStr(out, MyConverter.escape(str));
				}//�õ���̨����
				else if(readinfo.startsWith(GET_RTNAME)){				
					List<String[]> list=DBUtil.getRoomTypeName();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}//��Ӳ�̨
				else if(readinfo.startsWith(ADD_POINT)){
					String str[]=readinfo.split(ADD_POINT);
					int num=Integer.parseInt(str[3]);
					String strinfo=DBUtil.addPoint(str[1],str[2],num,str[4],str[5],str[6]);
					IOUtil.writeStr(out,  MyConverter.escape(strinfo));
				}//ɾ����̨��Ϣ
				else if(readinfo.startsWith(DEL_POINT)){				
					String str[]=readinfo.split(DEL_POINT);
					String strinfo=DBUtil.delPoint(str[1]);
					if(strinfo==null){					
						IOUtil.writeStr(out, MyConverter.escape(fail));
					}else{					
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}//���²�̨��Ϣ
				else if(readinfo.startsWith(UPDATE_POINT)){				
					String str[]=readinfo.split(UPDATE_POINT);//�����²�̨��Ϣ��UPDATE_POINTΪ��ָ�
					int num=Integer.parseInt(str[3]);
					String strinfo=DBUtil.updatePoint(str[1],str[2],num, str[4],str[5],str[6],str[7]);
					if(strinfo==null){					
						IOUtil.writeStr(out, MyConverter.escape(fail));
					}else{					
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//�õ�δ���˵����ж�����Ϣ
				else if(readinfo.startsWith(GET_ORDER)){				
					List<String[]> list=DBUtil.getOrderInfo();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//�õ����ս��˵Ķ�����Ϣ
				else if(readinfo.startsWith(GET_ALACCORDER)){				
					List<String[]> list=DBUtil.getAlAccOrderInfo();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//���ݲ�����ѯ����
				else if(readinfo.startsWith(SEARCH_ORDER)){				
					String str[]=readinfo.split(SEARCH_ORDER);
					List<String[]> list=DBUtil.SOrderInfoByPName(str[1]);
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//�õ�������ϸ��Ϣ
				else if(readinfo.startsWith(SEARCH_ODXX)){				
					String str[]=readinfo.split(SEARCH_ODXX);
					List<String[]> list=DBUtil.SOrderXxByPid(str[1]);
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//���ݲ�Ʒ������Ϣ
				else if(readinfo.startsWith(GET_VGSOMEINFO)){				
					List<String[]> list=DBUtil.getVegeSomeInfo();
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//�õ�Ա����Ϣ
				else if(readinfo.startsWith(GET_WORKERINFO)){				
					List<String[]> list = DBUtil.getWorkerInfo();
					String strinfo = TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//����Ա��id,��ѯĳһ��Ա������Ϣ
				else if(readinfo.startsWith(QUERY_W_ONEOFWORKERINFOBYID)){				
					String params[]=readinfo.split(QUERY_W_ONEOFWORKERINFOBYID);
					String[] resultArr = DBUtil.getOneOfWorkerInfo(params[1]);
					String strinfo = TypeExchangeUtil.stringArrayToString(resultArr);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}

				//���Ա���������
				else if(readinfo.equals(GET_WORKERMAXNO)){				
					String str = DBUtil.getWorkerMaxNo();
					IOUtil.writeStr(out, MyConverter.escape(str));
				}
				//���Ա����Ϣ
				else if(readinfo.startsWith(ADD_WORKERINFO)){				
					String str[] = readinfo.split(ADD_WORKERINFO);
					String strInfo = DBUtil.addWorkerInfo(str);
					if(strInfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//ɾ��Ա����Ϣ
				else if(readinfo.startsWith(DEL_WORKERINFO)){				
					String str[] = readinfo.split(DEL_WORKERINFO);
					String strinfo=DBUtil.delWorkerInfo(str[1]);
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//����Ա��
				else if(readinfo.startsWith(UPDATE_WORKERINFO)){				
					String str[] = readinfo.split(UPDATE_WORKERINFO);
					String strinfo = DBUtil.updateWorkerInfo(str);
					if(strinfo==null){
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}else{
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
				//�õ���ɫ��Ϣ
				else if(readinfo.startsWith(GET_ROLEINFO)){				
					List<String[]> list = DBUtil.getRole();
					String strinfo = listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//�õ�Ȩ����Ϣ
				else if(readinfo.startsWith(GET_AUTHORITYINFO)){				
					List<String[]> list = DBUtil.getAuthority();
					String strinfo = listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//�õ���ɫ-Ȩ����Ϣ
				else if(readinfo.startsWith(GET_AUTHORITY_ROLE)){				
					List<String[]> list = DBUtil.getAuthorityRole();
					String strinfo = listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				
				//-----------------------���������---------
				//��������δ���˵���Ϣ
				else if(readinfo.startsWith(SEARCH_ACCORDER)){				
					String str[]=readinfo.split(SEARCH_ACCORDER);//���ݲ�̨��ѯ����
					List<String[]> list= DBUtil.SAccountInfoByPName(str[1]);//���ݲ�̨���Ʋ�ѯδ���˵Ķ�����Ϣ(���ڽ���)
					if(list==null || list.size()==0){//����ϢΪ��					
						System.out.println("null0---------------");//��ӡnull0��ʾ
					}
					for(String[] arr : list){//������Ϣ�б�					
						for(String strr : arr){						
							System.out.print(strr+" ");
						}
						System.out.println();
					}
					String strinfo=TypeExchangeUtil.listToString(list);//��������ת��
					System.out.println("strinfo="+strinfo);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//���ݲ�̨��ѯ������������
				else if(readinfo.startsWith(SEARCH_ACCOUNT_VEGE)){				
					String str[]=readinfo.split(SEARCH_ACCOUNT_VEGE);
					List<String[]> list= DBUtil.SOrderACountByPid(str[1]);
					String strinfo=TypeExchangeUtil.listToString(list);
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
				//������˵����б���
				else if(readinfo.startsWith(INSERT_ACCOUNT_VEGE)){				
				    String str[]=readinfo.split(INSERT_ACCOUNT_VEGE);
				    System.out.println(str.length);
					String strinfo= DBUtil.insertAccount(str);//���˵��������ݿ�
					if(strinfo==null){					
						IOUtil.writeStr(out,  MyConverter.escape(fail));
					}
					else{					
						IOUtil.writeStr(out, MyConverter.escape(ok));
					}
				}
		//==============PC�˱�ʶend=====================================
				
		//================����ֳֶ���Ϣbegin===========================	
	   //�жϵ�½Ȩ��
			else if(readinfo.startsWith(SURE_WAITER)){			
				String[] getmsg=readinfo.split(SURE_WAITER);
				String wmm=DBUtil.pdWaiter(getmsg[1]);
				if(wmm!=null&&wmm.equals(getmsg[2])){//����Ϣ���գ���Ա��������ȷ
					IOUtil.writeStr(out, MyConverter.escape(ok));
				}else{
					IOUtil.writeStr(out,  MyConverter.escape(fail));
				}
			}
			//���Ա���Ƿ��Ѿ���½
			else if(readinfo.startsWith(IS_HASlOGIN)){			
				String[] getmsg=readinfo.split(IS_HASlOGIN);
				String str=DBUtil.isHasLogin(getmsg[1]);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}
			//����Ա����½��Ϣ
			else if(readinfo.startsWith(UPDATE_LOGINFLG)){			
				String[] getmsg=readinfo.split(UPDATE_LOGINFLG);
				String str=DBUtil.updateLoginFlg(getmsg[1],getmsg[2]);
				if(str==null){				
					IOUtil.writeStr(out,  MyConverter.escape(fail));
				}
				else{				
					IOUtil.writeStr(out, MyConverter.escape(ok));
				}
			}
				//�޸ķ���Ա����
			else if(readinfo.startsWith(RESET_PASSWORD)){			
				String[] getmsg=readinfo.split(RESET_PASSWORD);
				String str=DBUtil.updatePassWord(getmsg[1],getmsg[2]);
				if(str==null){				
					IOUtil.writeStr(out,  MyConverter.escape(fail));
				}
				else{				
					IOUtil.writeStr(out, MyConverter.escape(ok));
				}
			}
			//��ѯ��½Ȩ��
			else if(readinfo.startsWith(GET_LOGINAUTH)){			
				String[] str=readinfo.split(GET_LOGINAUTH);
				List<String[]> list=DBUtil.getLoginAuth(str[1]);
				String strinfo=TypeExchangeUtil.listToString(list);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
	     //=================����ֳ�end================
		 //=================�ͻ��ֳֶ�begin============
		//���²�̨��Ϣ��״̬��
			else if(readinfo.startsWith(UPDATECLIENT_POINT)){			
				String[] getinfo=readinfo.split(UPDATECLIENT_POINT);
				String strinfo=DBUtil.updateClientPoint(getinfo[1],getinfo[2]);
				if(strinfo==null){				
					IOUtil.writeStr(out, MyConverter.escape(fail));
				}
				else{				
					IOUtil.writeStr(out, MyConverter.escape(ok));
				}
			}
				//���²�̨��Ϣ��״̬��
			else if(readinfo.startsWith(GET_W_GUESTNUMBYNAME)){			
				String[] getinfo=readinfo.split(GET_W_GUESTNUMBYNAME);
				String strinfo=DBUtil.getPointGuestNumByName(getinfo[1],getinfo[2]);
				if(strinfo==null){				
					IOUtil.writeStr(out, MyConverter.escape(fail));
				}
				else{				
					IOUtil.writeStr(out, MyConverter.escape(ok));
				}
			}
				
		//============================�ͻ��ֳ�end=============================		
		//========================����Ա�ֳֵ�begin=====================================
			//��ȡ���еĲ�����Ϣ�Ͳ�̨������Ϣ
			else if(readinfo.equals(GET_W_ROOM_POINTTYPE)){ 			
				List<String[]> roomInfo = DBUtil.getRoomIdName();//��ȡ���еĲ���id,��ź����� ��Ϣ
				List<String[]> pointTypeInfo = DBUtil.getRoomTypeIdName();//��ȡ���еĲ������͵�ID������
				String str1 = TypeExchangeUtil.listToString(roomInfo);
				System.out.println("roominfo="+str1);//��ӡ������Ϣ
				String str2 = TypeExchangeUtil.listToString(pointTypeInfo);
				IOUtil.writeStr(out, MyConverter.escape(str1+"~"+str2));
			}
			//ͨ������id�Ͳ�̨����id ��ȡ��Ӧ�Ĳ�̨��Ϣ
			else if(readinfo.startsWith(GET_W_POINTINFO)){			
				String params[] = readinfo.split(GET_W_POINTINFO);
				List<String[]> pointInfo = DBUtil.getPointForWaiter(params[1],params[2]);//��ȡ����Ա�ֳֶ���Ҫ�Ĳ�̨��Ϣ
				String str = TypeExchangeUtil.listToString(pointInfo);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}	
			//�õ�������Ϣ
			else if(readinfo.startsWith(GET_W_MCGINFO)){			
				String read[] = readinfo.split(GET_W_MCGINFO);
				List<String[]> info = DBUtil.getMCGinfo(read[1]);
				String str=TypeExchangeUtil.listToString(info);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}					
				//�õ����ȷ�ϵ���Ϣ
			else if(readinfo.startsWith(GET_W_ORDERDISHCONFIRM)){			
				String params[] = readinfo.split(GET_W_ORDERDISHCONFIRM);
				List<String[]> orderConfirmInfo = DBUtil.getOrderConfirmForWaiter(params[1]);//����Ա�˻�ȡ����ȷ�ϵ��еĲ�����Ϣ
				for(String[] arr: orderConfirmInfo){//������ȷ�ϵ��е���Ϣ���б��뵽�ַ���������				
					for(String str: arr){					
						System.out.print(str+" ");
					}
					System.out.println();
				}
				String str = TypeExchangeUtil.listToString(orderConfirmInfo);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}			
            //���µ��ȷ�ϵ�
			else if(readinfo.startsWith(UPDATE_W_ORDERDISHCONFIRM)){			
				String params[] = readinfo.split(UPDATE_W_ORDERDISHCONFIRM);
				List<String[] > str = TypeExchangeUtil.strToList(params[1]);
				String result = DBUtil.updateOrderConfirm(str);
				IOUtil.writeStr(out, MyConverter.escape(result));
			}		
			//�����ȷ�ϵ��е�ĳ����Ʒ��������1�������ò�Ʒ���˲�������1
			else if(readinfo.startsWith(DELETE_W_VEGESINGLECOUNT)){			
				String params[] = readinfo.split(DELETE_W_VEGESINGLECOUNT);
				String result = DBUtil.subVegeCount(params);
				IOUtil.writeStr(out, MyConverter.escape(result));
			}
			//�����ȷ�ϵ��е�ĳ����Ʒ��������1
			else if(readinfo.startsWith(ADD_W_VEGESINGLECOUNT)){			
				String params[] = readinfo.split(ADD_W_VEGESINGLECOUNT);
				String result = DBUtil.addVegeCount(params);
				IOUtil.writeStr(out, MyConverter.escape(result));
			}
			//ɾ�����ȷ�ϵ��е�ĳ����Ʒ
			else if(readinfo.startsWith(DELETE_W_ORDERCONFIRMVEGE)){			
				String params[] = readinfo.split(DELETE_W_ORDERCONFIRMVEGE);
				String result = DBUtil.deleteOrderConfirmVege(params);
				IOUtil.writeStr(out, MyConverter.escape(result));
			}
			//��ѯ�������е����ж���
			else if(readinfo.startsWith(QUERY_W_DESKNAMEOFORDER)){			
				String[] resultArr = DBUtil.queryDeskNameByUserId();
				String result=TypeExchangeUtil.stringArrayToString(resultArr);
				IOUtil.writeStr(out, MyConverter.escape(result));
			}			
		//--------------------------------------end���ֳֶˣ�
		//--------------------------------------begin��PC��ǰ̨��			
			//����·���õ�ͼƬ
			else if(readinfo.startsWith(GET_W_PICTUREINFO)){			
				String[] str=readinfo.split(GET_W_PICTUREINFO);
				String imagepath=IMAGE_PATH+str[1]+".jpg";//�õ�ͼƬ·��
				byte[] imagemsg=PicScaleUtil.getPic(imagepath);//�Ӵ��̻��ͼƬ
				IOUtil.writeBytes(out, imagemsg);
			}
			//���ݲ�ƷID���ӿ���п۳��ò�Ʒ���õ��Ĳ��ϵ�����
			//ͨ����̨Id��ò�ѯ�ò�̨δ�����˵��ţ���Ϊ�գ����ȡ��ǰ��󶩵��Ų����ݲ�̨Id��Ա��Id���ܼ۸񡢹˿����������¶���
			else if(readinfo.startsWith(ADD_W_NEWORDER)){			
				String[] str=readinfo.split(ADD_W_NEWORDER);
				List<String []> orderList=TypeExchangeUtil.strToList(str[1]);//��������ת��
				String strinfo = null;
				strinfo=DBUtil.addNewOrder(orderList,str[2],str[3],str[4],str[5]);//����¶���
				if(strinfo==null){				
					IOUtil.writeStr(out, MyConverter.escape(fail));
				}
				else{				
					IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}	
			}
			//�������ȷ�ϵ�
			else if(readinfo.startsWith(ADD_W_NEWDETIALORDER)){			
				String str[]=readinfo.split(ADD_W_NEWDETIALORDER);
				List<String []> orderList=TypeExchangeUtil.strToList(str[1]);
				String strinfo=DBUtil.addNewDetialOrder(orderList);
				if(strinfo==null){				
					IOUtil.writeStr(out, MyConverter.escape(fail));
				}
				else{				
					IOUtil.writeStr(out, MyConverter.escape(ok));
				}
			}
					
			//���������õ�����������
			else if(readinfo.startsWith(GET_CGBYMCG)){			
				String[] getinfo=readinfo.split(GET_CGBYMCG);
				List<String> strinfo=DBUtil.getCGByMCG(getinfo[1]);
				String str=TypeExchangeUtil.listString(strinfo);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}						
			//�õ�Ҫ�޸ĵĲ�̨��ԭ������
			else if(readinfo.startsWith(GET_CURDESK_GUESTNUM)){			
				String[] str=readinfo.split(GET_CURDESK_GUESTNUM);
				String strinfo=DBUtil.getCurDeskGuestNum(str[1]);//�õ���ǰ��̨������
				System.out.println("strinfo="+strinfo);
				if(strinfo==null){//����ϢΪ��				
					System.out.println("fail");//��ӡʧ����ʾ
					IOUtil.writeStr(out,  MyConverter.escape(fail));
				}else{								
					System.out.println("ok");
				  IOUtil.writeStr(out, MyConverter.escape(strinfo));
				}
			}
				//ͨ������id�Ͳ�̨����id ��ȡ��Ӧ�Ĳ�̨�����˲�̨��Ϣ
			else if(readinfo.startsWith(GET_W_NOPERSONPOINTINFO)){			
				String params[] = readinfo.split(GET_W_NOPERSONPOINTINFO);
				List<String[]> pointInfo = DBUtil.getNoPersonPoint(params[1],params[2]);//��ȡ����Ա�ֳֶ����˵Ĳ�̨��Ϣ
				String str = TypeExchangeUtil.listToString(pointInfo);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}
			//�õ��������˵Ĳ�̨
			else if(readinfo.startsWith(GET_W_HASPERSONPOINTINFO)){			
				String params[] = readinfo.split(GET_W_HASPERSONPOINTINFO);
				List<String[]> pointInfo = DBUtil.getHasPersonPoint(params[1],params[2]);//��ȡ����Ա�ֳֶ����˵Ĳ�̨��Ϣ
				String str = TypeExchangeUtil.listToString(pointInfo);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}			
		   //�޸Ĳ�̨����
			else if(readinfo.startsWith(CHANGE_GUESTNUM)){			
				String[] str=readinfo.split(CHANGE_GUESTNUM);
				String strinfo=DBUtil.changeGuestnum(str[1], str[2]);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}		  
		   //�õ���Ʒ������ͼƬ
			else if(readinfo.equals(GET_VEGEIMAGE)){			
				List<String[]> list=DBUtil.getALLVegeImage();
				String str=TypeExchangeUtil.listToString(list);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}
			//�õ���ƷͼƬ�������
			else if(readinfo.startsWith(GET_VEGEIMAGEMAXNO)){			
				String maxno=DBUtil.getVegeImageMaxNo();
				IOUtil.writeStr(out, MyConverter.escape(maxno));
			}
			//����ƷͼƬ��ӽ���
			else if(readinfo.startsWith(INSERT_IMAGE)){			
				String[] getinfo=readinfo.split(INSERT_IMAGE);
				String strinfo=DBUtil.addVegeImage(getinfo[1],getinfo[2],getinfo[3],getinfo[4]);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
			//�жϵ�ǰ��Ʒ�Ƿ������ͼ
			else if(readinfo.startsWith(PDVEGEMAIN)){			
				String[] getinfo=readinfo.split(PDVEGEMAIN);
				String strinfo=DBUtil.pdVegeImage(getinfo[1]);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
			//�õ�ָ����Ʒ����ͼƬ
			else if(readinfo.startsWith(GET_VEGEMAINIMAGE)){			
				String[] getstr=readinfo.split(GET_VEGEMAINIMAGE);
				String strinfo=DBUtil.getVegeMainImage(getstr[1]);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
			//�޸Ĳ�Ʒ��ͼ
			else if(readinfo.startsWith(UPDATE_MAINIMAGE)){			
				String[] getstr=readinfo.split(UPDATE_MAINIMAGE);
				String strinfo=DBUtil.updateVegeMainImage(getstr);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
		   //ͨ����ƷID��ò�ƷͼƷ��Ϣ�������ò�Ʒ�ĵ�һ��ͼƬ·�����ط���Ա�ֳֶ�
			else if(readinfo.startsWith(GET_W_PICBYVEGEID)){			 
				String params[] = readinfo.split(GET_W_PICBYVEGEID);
				String str = DBUtil.getVegeMainImage(params[1]);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}
			//�ж��Ƿ����û��ͼƬ������ͼƬ
			else if(readinfo.startsWith(ISHAVENULLPIC)){			
				  File dir = new File(IMAGE_PATH);  //����ͼƬ�ļ�
		          File file = new File(dir,IMAGE_NULLNAME);//������ͼ���ļ�  
		          if(file.exists()){ //��������ͼ���ļ�		          
		        	 IOUtil.writeStr(out, MyConverter.escape(IMAGE_PATH+IMAGE_NULLBIGNAME+"#"+IMAGE_PATH+IMAGE_NULLNAME));//����ͼ����Ϣ���ܺ�д����
		          }
		          else{		          
		            IOUtil.writeStr(out, MyConverter.escape(fail));//��ʧ����ʾ��Ϣд����
		          }
			}
				//ɾ����ͼ�Ĵ�ͼ
			else if(readinfo.startsWith(DELETE_ZBIMAGE)){			
				String[] getinfo=readinfo.split(DELETE_ZBIMAGE);
				String str=DBUtil.delZDImage(getinfo[1],IMAGE_PATH+getinfo[2]);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}
		   // ��������ȡ��Ʒ
			else if(readinfo.startsWith(SEARCH_VGBYCATE)){			
				String[] getstr=readinfo.split(SEARCH_VGBYCATE);
				List<String[]> list=DBUtil.searchVegeByCate(getstr[1]);
				String sendstr=TypeExchangeUtil.listToString(list);
				IOUtil.writeStr(out, MyConverter.escape(sendstr));
			}
		   //���ݲ�Ʒid�õ�����˵�������ͼ
			else if(readinfo.startsWith(SEARCH_ZPATHBYVGID)){			
				String[] getstr=readinfo.split(SEARCH_ZPATHBYVGID);
				String str=DBUtil.searchzpathByvgid(getstr[1]);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}
			//����Ա�ֳֶ˵õ���Ʒ��Ϣ
			else if(readinfo.startsWith(GET_W_VEGEINFO)){			
				String[] getstr=readinfo.split(GET_W_VEGEINFO);
				List<String[]> vegelist=DBUtil.getWVege(getstr[1]);
				String strinfo=TypeExchangeUtil.listToString(vegelist);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
		   //���ݲ�Ʒid�õ���Ʒ�������Ϣ
			else if(readinfo.startsWith(SEARCH_W_VGBYID)){			
				String[] getinfo=readinfo.split(SEARCH_W_VGBYID);
				String[] vegelist=DBUtil.searchWVegeByid(getinfo[1]);
				//���ݲ�Ʒid�õ���ͼ·��
				String mainpath=DBUtil.getVegeMainImage(getinfo[1]);
				//���ݲ�Ʒid�õ���ͼ·��
				String imagemsg=DBUtil.searchzpathByvgid(getinfo[1]);
				String[] msg=new String[vegelist.length+2];//���б��ȼ�2�������ַ�������
				for(int i=0;i<vegelist.length;i++){
					msg[i]=vegelist[i];
				}
				msg[22]=mainpath;//��ͼ·��
				msg[23]=imagemsg;//��ͼ·��
				String strinfo=TypeExchangeUtil.stringArrayToString(msg);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
			//�����Ƿ�������
			else if(readinfo.startsWith(TESTCONNECT)){			
				IOUtil.writeStr(out, MyConverter.escape("success"));//����ʾ�ɹ���Ϣд����
			}			
			//------------------------------------------------end------------------------------------------------------//

				//pc�˸��ݲ�Ʒid�õ���Ʒ��ͼ�Ĵ�ͼ
			else if(readinfo.startsWith(GET_MBIMAGE)){			
				String[] getstr=readinfo.split(GET_MBIMAGE);
				String str=DBUtil.searchmbimagepath(getstr[1]);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}
				//pc�˸��ݲ�Ʒid�õ���Ʒ��ͼ
			else if(readinfo.startsWith(GET_ZBIMAGE)){			
				String[] getstr=readinfo.split(GET_ZBIMAGE);
				String str=DBUtil.searchzimagepath(getstr[1]);
				IOUtil.writeStr(out, MyConverter.escape(str));
			}
			//�޸Ĳ�Ʒ��ͼ�Ĵ�ͼ
			else if(readinfo.startsWith(UPDATE_MAINBIGIMAGE)){			
				String[] getstr=readinfo.split(UPDATE_MAINBIGIMAGE);
				String strinfo=DBUtil.updateVegeMainBigImage(getstr);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
			//���pc�����õĲ�Ʒ��Ϣ
			else if(readinfo.startsWith(GET_USEVEGE)){			
				List<String[]> vegelist=DBUtil.getUseVege();				
				String strinfo=TypeExchangeUtil.listToString(vegelist);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
			//����Ա��id�õ�Ա������
			else if(readinfo.startsWith(GET_WORKERNAMEBYID)){			
				String[] getinfo=readinfo.split(GET_WORKERNAMEBYID);
				String strinfo=DBUtil.getWorkernameById(getinfo[1]);
				IOUtil.writeStr(out, MyConverter.escape(strinfo));
			}
			System.out.println("���͹�ȥ��Ϣ");
			}catch(Exception e){
				e.printStackTrace();
			}finally{
			try {in.close();} catch (IOException e) {e.printStackTrace();}//�ر���
			try {out.close();} catch (IOException e) {e.printStackTrace();}
			try {sc.close();} catch (IOException e) {e.printStackTrace();}
			}			
		}	
		   public  String gettime(){//����ʱ���ȡ����
			   Calendar   c   =   Calendar.getInstance(); // ʹ��Ĭ��ʱ�������Ի������һ������
		       c.setTime(new   java.util.Date()); //ʹ�ø����� Date ���ô� Calendar ��ʱ��
		       int   year   =   c.get(Calendar.YEAR); //���ظ����������ֶε�ֵ
		       int   month   =   c.get(Calendar.MONTH)+1; //���ظ����������ֶε�ֵ
		       int   day   =   c.get(Calendar.DAY_OF_MONTH);  //���ظ����������ֶε�ֵ
		       int   hour   =   c.get(Calendar.HOUR_OF_DAY); //���ظ�������ʱ�ֶε�ֵ
		       int   minute   =   c.get(Calendar.MINUTE); //���ظ����������ֶε�ֵ
		       int   second   =   c.get(Calendar.SECOND);//���ظ����������ֶε�ֵ
		       return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;//���õ���ʱ����Ϣ����
		   }

}
