package smtcl.mocs.services.report.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.hibernate.type.CharacterArrayType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import smtcl.mocs.services.report.IMonthPlanService;

public class MonthPlanServiceImpl extends GenericServiceSpringImpl<Object, String> implements IMonthPlanService {

	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String[] delivery=new String[5];//���������
	private Date[] productivityWeek=new Date[6];//���������ܽڵ� ͨ���ڵ�ʱ�� ��ȡ��һ�ܶ�Ӧ�Ĳ��� ͨ��ʱ��ڵ��ѯÿ���ܵĲ���
	
	 /**
     * 1.�������ע��
     * ʹ��propagation ָ������Ĵ�����Ϊ������ǰ�����񷽷�������һ�����񷽷�����ʱ���ʹ������
     * Ĭ��ȡֵΪREQUIRED����ʹ�õ��÷���������
     * REQUIRES_NEW��ʹ���Լ������񣬵��õ����񷽷������񱻹���
     *
     * 2.ʹ��isolation ָ������ĸ��뼶����õ�ȡֵΪREAD_COMMITTED
     * 3.Ĭ������� Spring ������ʽ��������е�����ʱ�쳣���лع���Ҳ����ͨ����Ӧ�����Խ������á�ͨ������£�Ĭ��ֵ���ɡ�
     * 4.ʹ��readOnly ָ�������Ƿ�Ϊֻ���� ��ʾ�������ֻ��ȡ���ݵ����������ݣ��������԰������ݿ������Ż������������һ��ֻ��ȡ���ݿ�ֵ�÷�����Ӧ����readOnly=true
     * 5.ʹ��timeOut ָ��ǿ�ƻع�֮ǰ�������ռ�õ�ʱ�䡣
     */
	
	
	/**
	 * @author JunSun
	 * ��ȡ�ֳ��ܵı�ͷ��Ϣ
	 */
	/*@Transactional(propagation=Propagation.REQUIRES_NEW,
			isolation=Isolation.READ_COMMITTED,
			rollbackFor={},
			readOnly=true,
			timeout=3)*/
	@Override
	public List<Map<String, Object>> getTotalTableHeadData(String number, String search,Date searchTime) {
		
		System.out.println("�����Ƿֳ��ܵı�ͷ���ݲ�ѯ");
		List<Map<String, Object>> totalTableHeadData=new ArrayList<Map<String, Object>>();//��ű�ͷ����
		Map<String, Object> map=new HashMap<String, Object>();
		productivityWeek(searchTime);//�����ĸ�������
		
		//��������ܵ�������ʾ
		map.put("delivery1", delivery[0]);
		map.put("delivery2", delivery[1]);
		map.put("delivery3", delivery[2]);
		map.put("delivery4", delivery[3]);
		map.put("delivery5", delivery[4]);
		//��Ͷ�Ϻ�
		map.put("feedingNum", this.totalFeeding(searchTime, -1, null));
		//���ύ�� 
		map.put("submitNum", this.totalSubmit(searchTime, -1, null));
		
		System.out.println("������----"+delivery[0]+" "+delivery[1]+" "+delivery[2]+" "+delivery[3]+" "+delivery[4]);
		
		//�����ܺ�
		map.put("overHeat", this.totalOvearHeat(searchTime, -1, null));//Ŀǰ�����㣬������ERP����
		//�������ύ�ܺ�
		for(int i=0;i<5;i++){
			
			if(i==4&& delivery[4].equals(" ")){
				
				map.put("deliveryNum"+(i+1), " ");
				
			}else{
				
				double total=totalProductivityWeek(number, search,productivityWeek[i], productivityWeek[i+1], -1, null);
				map.put("deliveryNum"+(i+1), total);
			
			}
		}
		//Ͷ�ϵ�Ʒ����
		map.put("typeOfFeeding", this.totalType(searchTime, -1));
		//�ύ��Ʒ����
		map.put("typeOfSubmit", this.totalType(searchTime, -1));
		//���ȶ�ӦƷ����
		map.put("typeOfOverHeat", this.totalType(searchTime, -1));
		//���ڶ�ӦƷ����
		for(int i=0;i<5;i++){
		
			if(i==4&& delivery[4].equals(" ")){
				
				map.put("typeOfDelivery"+(i+1), " ");
				
			}else{
				
				int type=this.totalType(searchTime, -1);
				map.put("typeOfDelivery"+(i+1), type);
			}
			
		}
		
		totalTableHeadData.add(map);
		
		return totalTableHeadData;
	}
	
	/**
	 * @author JunSun
	 * ��ȡ���еĴ�������� �Լ� �����ͷ��Ϣ �Լ�ÿ���������е���Ϣ
	 */
	@Transactional()
	@Override
	public List<Map<String, Object>> getPartClassAndHeadData(String number, String search, Date searchTime) {
		
		System.out.println("�����������������������Լ����ݲ�ѯ");
		List<Map<String, Object>> partClassAndHeadData=new ArrayList<Map<String, Object>>();//������д����ͷ����
		List<Map<String, Object>> className=new ArrayList<Map<String, Object>>();//��Ŵ����ͺ� ����
		
		//��ȡ���д�����
		className=this.getAllPartClass(search,searchTime);
		
//		productivityWeek(searchTime);//�����ĸ�������
		
		for(Map<String, Object> map:className){//���ݴ����
			
			Map<String, Object> head=new HashMap<String, Object>();//���ÿ������������Լ���ͷ��Ϣ
			head.put("partClass", (String)map.get("name"));
			head.put("feedingNum", this.totalFeeding(searchTime, Integer.valueOf(map.get("id").toString()), null));
			head.put("submitNum", this.totalSubmit(searchTime, Integer.valueOf(map.get("id").toString()), null));
			head.put("overHeat", this.totalOvearHeat(searchTime, Integer.valueOf(map.get("id").toString()), null));
			//���ڼƻ�����
			double total;
			for(int i=0;i<5;i++){
				
				if(i==4&& delivery[4].equals(" ")){
					
					head.put("deliveryNum"+(i+1), " ");
					
				}else{
					
					total=this.totalProductivityWeek(number, search, productivityWeek[i], productivityWeek[i+1], Integer.valueOf(map.get("id").toString()), null);
					head.put("deliveryNum"+(i+1), total);
				}
			}
			//Ͷ�ϵ�Ʒ����
			head.put("typeOfFeeding", this.totalType(searchTime, Integer.valueOf(map.get("id").toString())));
			//�ύ��Ʒ����
			head.put("typeOfSubmit", this.totalType(searchTime, Integer.valueOf(map.get("id").toString())));
			//���ȶ�ӦƷ����
			head.put("typeOfOverHeat", this.totalType(searchTime, Integer.valueOf(map.get("id").toString())));
			//���ڶ�ӦƷ����
			for(int i=0;i<5;i++){
				
				if(i==4&& delivery[4].equals(" ")){
					
					head.put("typeOfDelivery"+(i+1), " ");
					
				}else{
					
					int type=totalType(searchTime, Integer.valueOf(map.get("id").toString()));
					head.put("typeOfDelivery"+(i+1), type);
				}
			}
			//ÿ�������Ӧ�ı�����Ϣ
			head.put("partClassData", this.getTypeData(number, search,searchTime, Integer.valueOf(map.get("id").toString())));
	
			partClassAndHeadData.add(head);
		}	
		return partClassAndHeadData;
	}
	
	/**
	 * @author JunSun
	 * �����ܼ��㹫ʽ
	 */
	@SuppressWarnings("deprecation")
	public void productivityWeek(Date searchTime){
		
		for (int i=0; i<delivery.length;i++) {
			
			delivery[i]="";
		}
		for(int i=0;i<productivityWeek.length;i++){
			
			productivityWeek[i]=null;
		}
		
		System.out.println("�����ǲ����ܼ��㷽�����õ���searchTime��----"+(searchTime.getYear()+1900)+"-"+(searchTime.getMonth()+1)+"-"+searchTime.getDay());
		Calendar cal = Calendar.getInstance();
		//�������
		cal.set(Calendar.YEAR,(searchTime.getYear()+1900));
		//�����·�
		cal.set(Calendar.MONTH, searchTime.getMonth());
		//�����������·ݵĵ�1��
		cal.set(Calendar.DAY_OF_MONTH, 1);
		//�������µ�һ�� �����ڼ� 1-������ 2-����һ ����������
		int dateIndex=cal.get(Calendar.DAY_OF_WEEK);
		//�����������һ��
		int lastDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//��������Ϊ���һ��
		cal.set(Calendar.DAY_OF_MONTH, lastDate);
		//��ȡ��������һ�������ڼ�
		int lastDateIndex=cal.get(Calendar.DAY_OF_WEEK);
		//�������ûص�һ��
		cal.set(Calendar.DAY_OF_MONTH, 1);
		if(dateIndex==1){//����������� �������������
		
			
			for (int i = 0; i < 4; i++) {

					// ��ȡ���ʱ��ڵ� ���ڷֲ����ܶ����ݿ��ѯ
					if (i == 0) {
						cal.set(Calendar.DAY_OF_MONTH, dateIndex + 1);
						productivityWeek[i] = cal.getTime();
						cal.set(Calendar.DAY_OF_MONTH, dateIndex + 7);
						productivityWeek[i + 1] = cal.getTime();
					} else {
	
						cal.set(Calendar.DAY_OF_MONTH, dateIndex + 7);
						productivityWeek[i + 1] = cal.getTime();
					}
					// �ֳ��ĸ�������
					delivery[i] = (dateIndex + 1) + "#-" + (dateIndex + 7) + "#";
					dateIndex += 7;
				
				
			}
			productivityWeek[5] = null;
			delivery[4] = " ";
			
		}else if(dateIndex==2){
			
			for(int i=0;i<4;i++){
				
				// ��ȡ���ʱ��ڵ� ���ڷֲ����ܶ����ݿ��ѯ
				if (i == 0) {
					cal.set(Calendar.DAY_OF_MONTH, dateIndex-1);
					productivityWeek[i] = cal.getTime();
					cal.set(Calendar.DAY_OF_MONTH, dateIndex + 5);
					productivityWeek[i + 1] = cal.getTime();
				} else {

					cal.set(Calendar.DAY_OF_MONTH, dateIndex + 5);
					productivityWeek[i + 1] = cal.getTime();
				}
				// �ֳ��ĸ�������
				delivery[i] = (dateIndex-1) + "#-" + (dateIndex + 5) + "#";
				dateIndex += 7;
			}
			productivityWeek[5] = null;
			delivery[4] = " ";
			
		}else{//��������һ�Ų���������
			
			cal.set(Calendar.MONTH, searchTime.getMonth()-1);//����������һ����
			int lastDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//��ȡ�ϸ��µ�����
			System.out.println("��һ��������----"+lastDay);
			for(int i=0;i<5;i++){
					
				if(i==0){//����ǵ�һ�����²�����
						
					int firstDay=lastDay-dateIndex+3;
					if(firstDay>lastDay){
						
						firstDay-=lastDay;
					}
					cal.set(Calendar.DAY_OF_MONTH, firstDay);
					productivityWeek[i]=cal.getTime();
					cal.set(Calendar.MONTH, searchTime.getMonth());//�������ûص������
					cal.set(Calendar.DAY_OF_MONTH, 9-dateIndex);
					productivityWeek[i+1]=cal.getTime();
					
					delivery[i]+=(firstDay)+"#-";
					delivery[i]+=(9-dateIndex)+"#";
					
				}else if(i==4&& (dateIndex-lastDateIndex)>=5){//�ж��������û�п�Խ����
				
					int firstDay=(10-dateIndex)+((i-1)*7);
					cal.set(Calendar.DAY_OF_MONTH, firstDay+6);
					productivityWeek[i+1]=cal.getTime();
					
					delivery[i]+=firstDay+"#-"+(firstDay+6)+"#";
					
				}else if(i==4){
					
					productivityWeek[5]=null;
					delivery[4]=" ";
					
				}else{//���ǿ�Խ�Ʋ�����
					
					int firstDay=(10-dateIndex)+((i-1)*7);
					cal.set(Calendar.DAY_OF_MONTH, firstDay+6);
					productivityWeek[i+1]=cal.getTime();
					delivery[i]+=firstDay+"#-"+(firstDay+6)+"#";
				}
			}
			
		}
	
		
	}

	/**
	 * @author JunSun
	 * �������� �� ��������   ��ѯ������������Ϣ
	 */
	@Override
	public List<Map<String, Object>> getTypeData(String number,String search, Date searchTime,int partClass) {
		
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> name=new ArrayList<Map<String,Object>>();
		String searchDate=sdf.format(searchTime);
		//productivityWeek(searchTime);//�����ĸ�������	
		String hql="";
		
		if((productivityWeek[0].getMonth()+1)>(searchTime.getMonth()+1)){
			
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.YEAR,searchTime.getYear()+1900);
			cal.set(Calendar.MONTH, searchTime.getMonth()+1);
			int lastDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDate);	
			String lastDay=sdf.format(cal.getTime());
			String day=sdf.format(productivityWeek[0]);
			
			
			hql="select distinct new Map("
					+ "partType.no as no, partType.name as name)"
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClasss "
					+ "where monthPlan.TPartTypeInfo.id=partType.id " 
							+ "and partType.no=materailType.no "
							+ "and materailType.PId=materialClasss.MClassid "
							+ "and materialClasss.MClassid="+partClass+" "
							+ "and ("
							+ "			  (DATE_FORMAT(monthPlan.planStarttime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
									+ "or (DATE_FORMAT(monthPlan.planEndtime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
									+ "or (DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
									+ "or (DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
									+ "or ("
									+ "		(DATE_FORMAT(monthPlan."+search+",'%Y-%m-%d %T')>=DATE_FORMAT('"+day+"','%Y-%m-%d %T'))"
									+  "and (DATE_FORMAT(monthPlan."+search+",'%Y-%m-%d %T')<=DATE_FORMAT('"+lastDay+"','%Y-%m-%d %T'))"
										+ ")"
								+ ")";
			
	
		}else {
			
			hql="select distinct new Map("
				+ "partType.no as no, partType.name as name)"
				+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClasss "
				+ "where monthPlan.TPartTypeInfo.id=partType.id " 
						+ "and partType.no=materailType.no "
						+ "and materailType.PId=materialClasss.MClassid "
						+ "and materialClasss.MClassid="+partClass+" "
						+ "and ("
						+ "			  (DATE_FORMAT(monthPlan.planStarttime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
								+ "or (DATE_FORMAT(monthPlan.planEndtime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
								+ "or (DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
								+ "or (DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
						+ ")";
		}
		name=dao.executeQuery(hql);
		for(Map<String, Object> map:name){//���ݴ����������������ȡ�����Ϣ
			
			System.out.println("�����ǣ�"+partClass+" ��������ǣ�"+map.get("name"));
			Map<String, Object> typeData=new HashMap<String, Object>();
			String no=(String)map.get("no");
			String partName=(String)map.get("name");
			String[] part=partName.split("-");
			if(part.length==1){//û��С�ͺ�
				
				typeData.put("typeNo", part[0]);
				typeData.put("part", "01");
			}else{//��С�ͺ�
				
				char[] partNum=part[part.length-1].toCharArray();//ȡ�����һ���ַ���
				int flag=0;
				for(char ch:partNum){
					
					if(!Character.isDigit(ch)){
						
						flag=1;
					}
				}
				if(flag==1){//���һ���ַ�������С�ͺ�
					
					typeData.put("typeNo", partName);
					typeData.put("part", "01");
					
				}else{//���һ���ַ��������ֵ�С�ͺ�
					
					String partNm="";
					for(int i=0;i<part.length-1;i++){
						
						if(i==part.length-2){
							
							partNm+=part[i];
						}else{
							
							partNm+=part[i]+"-";
						}
						
					}
					
					typeData.put("typeNo", partNm);
					typeData.put("part", part[part.length-1]);
				}
				
			}
			//�����Ͷ������
			typeData.put("typeFeeding", this.totalFeeding(searchTime, partClass, no));
			//��������ύ����
			typeData.put("typeSubmit", this.totalSubmit(searchTime, partClass, no));
			//������Ĺ�������
			typeData.put("typeOverHeat", this.totalOvearHeat(searchTime, partClass, no));
			//�����ܼƻ���������
			for(int i=0;i<5;i++){
				
				if(i==4&& delivery[4].equals(" ")){
					
					typeData.put("typeDelivery"+(i+1), " ");
				}else{
					
					typeData.put("typeDelivery"+(i+1), this.totalProductivityWeek(number, search,productivityWeek[i], productivityWeek[i+1], partClass, no));
				}
			}
			data.add(typeData);
		}
		return data;
	}


	/**
	 * @author JunSun
	 * ����Ͷ���ܺ�
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double totalFeeding(Date search, int partClass, String partNo) {
		
		String searchTime=sdf.format(search);
		String hql="";
		double total=0;
		List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();
		if(partClass==-1){//�����������Ϊ�գ���ѯ�ֳ�Ͷ���ܺ�
			
			hql="select new Map("
					+ "monthPlan.planNum as feeding) "
					+ "from TJobplanInfo monthPlan "
					+ "where DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m') ";
			
		}else if(partClass!=-1&& partNo!=null){//��ѯĳ�������е�С�����Ϣ
			
			hql+="select new Map("
					+ "monthPlan.planNum as feeding)"
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClasss "
					+ "where monthPlan.TPartTypeInfo.id=partType.id " 
					+ "and DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m') "
							+ "and monthPlan.TPartTypeInfo.id=partType.id "
							+ "and partType.no=materailType.no "
							+ "and materailType.PId=materialClasss.MClassid "
							+ "and materialClasss.MClassid="+partClass+" "
									+ "and partType.no='"+partNo+"'";
			
		}else{//����������಻Ϊ�գ���ѯ��Ӧ�����Ͷ���ܺ�
			
			hql+="select new Map("
					+ "monthPlan.planNum as feeding)"
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClasss "
					+ "where monthPlan.TPartTypeInfo.id=partType.id " 
					+ "and DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m') "
							+ "and monthPlan.TPartTypeInfo.id=partType.id "
							+ "and partType.no=materailType.no "
							+ "and materailType.PId=materialClasss.MClassid "
							+ "and materialClasss.MClassid="+partClass+" ";
			
		}
		list=dao.executeQuery(hql);
		for (Map<String, Object> map : list) {
			
			System.out.println(map.get("feeding"));
			total+=(Integer) map.get("feeding");
		}
		System.out.println("Ͷ�Ϻ�"+total);
		return total;
	}

	/**
	 * @author JunSun
	 * �����ύ�ܺ�
	 */
	@Override
	public double totalSubmit(Date search, int partClass, String partNo) {
		
		String searchTime=sdf.format(search);
		String hql=null;
		double total=0;
		if(partClass==-1){//�����������Ϊ�գ���ѯ�ֳ��ύ�ܺ�
			
			hql="select new Map("
					+ "monthPlan.finishNum as submit) "
					+ "from TJobplanInfo monthPlan "
					+ "where DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m') ";
			
		}else if(partClass!=-1&& partNo!=null){//��ѯ������ĳ��С�����Ϣ
			
			hql="select new Map("
					+ "monthPlan.finishNum as submit)"
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClasss "
					+ "where monthPlan.TPartTypeInfo.id=partType.id "
					+ "and DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m') "
							+ "and monthPlan.TPartTypeInfo.id=partType.id "
							+ "and partType.no=materailType.no "
							+ "and materailType.PId=materialClasss.MClassid "
							+ "and materialClasss.MClassid="+partClass+" "
							+ "and partType.no='"+partNo+"'";
			
		}else{//����������಻Ϊ�գ���ѯ��Ӧ������ύ�ܺ�
			
			hql="select new Map("
					+ "monthPlan.finishNum as submit)"
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClasss "
					+ "where monthPlan.TPartTypeInfo.id=partType.id "
					+ "and DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m') "
							+ "and monthPlan.TPartTypeInfo.id=partType.id "
							+ "and partType.no=materailType.no "
							+ "and materailType.PId=materialClasss.MClassid "
							+ "and materialClasss.MClassid="+partClass;
			
		}
		List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		list=dao.executeQuery(hql);	
		for (Map<String, Object> map : list) {
			
			total+=(Integer)map.get("submit");
		}
		System.out.println("�ύ��"+total);
		return total;
	
	}
	/**
	 * @author JunSun
	 * ��������ܺ�
	 */
	@Override
	public double totalOvearHeat(Date search, int partClass, String partNo) {
		return 0;
	}
	/**
	 * @author JunSun
	 * �����ܻ��ּƻ������ܺ� �Լ� �ύ�ܺ�
	 */
	@SuppressWarnings("unchecked")
	public double totalProductivityWeek(String number,String search,Date start, Date end, int partClass, String partNo) {
		
		String startTime=sdf.format(start);
		String endTime=sdf.format(end);
		double total=0;
		String hql=null;
		if(partClass==-1){
			
			hql="select new Map(" 
					+ "monthPlan."+number+" as num) "
					+ "from TJobplanInfo monthPlan "
					+ "where monthPlan."+search+">DATE_FORMAT('"+startTime+"','%Y-%m-%d %T') "
						  + "and monthPlan."+search+"<=DATE_FORMAT('"+endTime+"', '%Y-%m-%d %T') ";
		}else if(partClass!=-1&& partNo!=null){//��ѯ������ĳ��С�����Ϣ
			
			hql="select new Map("
					+ "monthPlan."+number+" as num)"
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClasss "
					+ "where monthPlan."+search+">DATE_FORMAT('"+startTime+"','%Y-%m-%d %T') "
						  + "and monthPlan."+search+"<=DATE_FORMAT('"+endTime+"', '%Y-%m-%d %T') "
						  + "and monthPlan.TPartTypeInfo.id=partType.id "
						  + "and partType.no=materailType.no "
						  + "and materailType.PId=materialClasss.MClassid "
						  + "and materialClasss.MClassid="+partClass+" "
						  		+ "and partType.no='"+partNo+"'";
		}else{
			
			hql="select new Map("
					+ "monthPlan."+number+" as num)"
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClasss "
					+ "where monthPlan."+search+">DATE_FORMAT('"+startTime+"','%Y-%m-%d %T') "
						  + "and monthPlan."+search+"<=DATE_FORMAT('"+endTime+"', '%Y-%m-%d %T') "
						  + "and monthPlan.TPartTypeInfo.id=partType.id "
						  + "and partType.no=materailType.no "
						  + "and materailType.PId=materialClasss.MClassid "
						  + "and materialClasss.MClassid="+partClass;
		}
		List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		list=dao.executeQuery(hql);	
		for (Map<String, Object> map : list) {
			
			total+=(Integer) map.get("num");
		}
		return total;
	}
	/**
	 * @author JunSun
	 * ������� �Լ��ܵ�Ʒ����Ŀ
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int totalType(Date search, int partClass) {
		
		int type=0;
		String searchTime=sdf.format(search);
		String hql=null;
		if(partClass==-1){
			
			hql="select distinct monthPlan.TPartTypeInfo.id as id "
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClass "
					+ "where ("
				+ "			  (DATE_FORMAT(monthPlan.planStarttime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m'))"
						+ "or (DATE_FORMAT(monthPlan.planEndtime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m'))"
						+ "or (DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m'))"
						+ "or (DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m'))"
				+ ")";
		}else{
			
			hql="select distinct monthPlan.TPartTypeInfo.id,partType.typeNo "
					+ "from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClass   "
						  + "where monthPlan.TPartTypeInfo.id=partType.id "
						  + "and partType.no=materailType.no "
						  + "and materailType.PId="+partClass+" "
						  		+ "and ("
				+ "			  (DATE_FORMAT(monthPlan.planStarttime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m'))"
						+ "or (DATE_FORMAT(monthPlan.planEndtime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m'))"
						+ "or (DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m'))"
						+ "or (DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m'))"
						+ ")";
						 
		}
		List<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		list=dao.executeQuery(hql);	
		type=list.size();
		return type;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getAllPartClass(String search,Date searchTime) {

		String searchDate=sdf.format(searchTime);
		
		String hql="";
		if((productivityWeek[0].getMonth()+1)>(searchTime.getMonth()+1)){//������ڿ���
		
			Calendar cal=Calendar.getInstance();
			cal.set(Calendar.YEAR,searchTime.getYear()+1900);
			cal.set(Calendar.MONTH, searchTime.getMonth()+1);
			int lastDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDate);	
			String lastDay=sdf.format(cal.getTime());
			String day=sdf.format(productivityWeek[0]);
			
			hql="select distinct new Map(materialClass.MClassid as id, materialClass.MClassname as name)"
					+ " from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClass "
					+ "where monthPlan.TPartTypeInfo.id=partType.id "
					+ "and partType.no=materailType.no "
					+ "and materailType.PId=materialClass.MClassid "
					+ "and ("
					+ "			  (DATE_FORMAT(monthPlan.planStarttime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
							+ "or (DATE_FORMAT(monthPlan.planEndtime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
							+ "or (DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
							+ "or (DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
							+ "or ("
							+ "		(DATE_FORMAT(monthPlan."+search+",'%Y-%m-%d %T')>=DATE_FORMAT('"+day+"','%Y-%m-%d %T'))"
							+  "and (DATE_FORMAT(monthPlan."+search+",'%Y-%m-%d %T')<=DATE_FORMAT('"+lastDay+"','%Y-%m-%d %T'))"
								+ ")"
						+ ")";
		
		}else{
			
			hql="select distinct new Map(materialClass.MClassid as id, materialClass.MClassname as name)"
					+ " from TJobplanInfo monthPlan,TPartTypeInfo partType,TMaterailTypeInfo materailType,TMaterialClass materialClass "
					+ "where monthPlan.TPartTypeInfo.id=partType.id "
					+ "and partType.no=materailType.no "
					+ "and materailType.PId=materialClass.MClassid "
					+ "and ("
					+ "			  (DATE_FORMAT(monthPlan.planStarttime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
							+ "or (DATE_FORMAT(monthPlan.planEndtime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
							+ "or (DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
							+ "or (DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchDate+"','%Y%m'))"
						+ ")";
		}
		
		return dao.executeQuery(hql);
	}

	public String[] getDelivery() {
		return delivery;
	}

	public void setDelivery(String[] delivery) {
		this.delivery = delivery;
	}

	public Date[] getProductivityWeek() {
		return productivityWeek;
	}

	public void setProductivityWeek(Date[] productivityWeek) {
		this.productivityWeek = productivityWeek;
	}

	
}
