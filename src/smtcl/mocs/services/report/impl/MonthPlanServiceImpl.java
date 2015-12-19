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
	private String[] delivery=new String[5];//五个产能周
	private Date[] productivityWeek=new Date[6];//六个产能周节点 通过节点时间 获取这一周对应的产能 通过时间节点查询每个周的产能
	
	 /**
     * 1.添加事务注解
     * 使用propagation 指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时如何使用事务。
     * 默认取值为REQUIRED，即使用调用方法的事务
     * REQUIRES_NEW：使用自己的事务，调用的事务方法的事务被挂起。
     *
     * 2.使用isolation 指定事务的隔离级别，最常用的取值为READ_COMMITTED
     * 3.默认情况下 Spring 的声明式事务对所有的运行时异常进行回滚，也可以通过对应的属性进行设置。通常情况下，默认值即可。
     * 4.使用readOnly 指定事务是否为只读。 表示这个事务只读取数据但不更新数据，这样可以帮助数据库引擎优化事务。若真的是一个只读取数据库值得方法，应设置readOnly=true
     * 5.使用timeOut 指定强制回滚之前事务可以占用的时间。
     */
	
	
	/**
	 * @author JunSun
	 * 获取分厂总的表头信息
	 */
	/*@Transactional(propagation=Propagation.REQUIRES_NEW,
			isolation=Isolation.READ_COMMITTED,
			rollbackFor={},
			readOnly=true,
			timeout=3)*/
	@Override
	public List<Map<String, Object>> getTotalTableHeadData(String number, String search,Date searchTime) {
		
		System.out.println("这里是分厂总的表头数据查询");
		List<Map<String, Object>> totalTableHeadData=new ArrayList<Map<String, Object>>();//存放表头数据
		Map<String, Object> map=new HashMap<String, Object>();
		productivityWeek(searchTime);//设置四个产能周
		
		//五个产能周的日期显示
		map.put("delivery1", delivery[0]);
		map.put("delivery2", delivery[1]);
		map.put("delivery3", delivery[2]);
		map.put("delivery4", delivery[3]);
		map.put("delivery5", delivery[4]);
		//总投料和
		map.put("feedingNum", this.totalFeeding(searchTime, -1, null));
		//总提交和 
		map.put("submitNum", this.totalSubmit(searchTime, -1, null));
		
		System.out.println("产能周----"+delivery[0]+" "+delivery[1]+" "+delivery[2]+" "+delivery[3]+" "+delivery[4]);
		
		//过热总和
		map.put("overHeat", this.totalOvearHeat(searchTime, -1, null));//目前不计算，后期由ERP接入
		//产能周提交总和
		for(int i=0;i<5;i++){
			
			if(i==4&& delivery[4].equals(" ")){
				
				map.put("deliveryNum"+(i+1), " ");
				
			}else{
				
				double total=totalProductivityWeek(number, search,productivityWeek[i], productivityWeek[i+1], -1, null);
				map.put("deliveryNum"+(i+1), total);
			
			}
		}
		//投料的品种数
		map.put("typeOfFeeding", this.totalType(searchTime, -1));
		//提交的品种数
		map.put("typeOfSubmit", this.totalType(searchTime, -1));
		//过热对应品种数
		map.put("typeOfOverHeat", this.totalType(searchTime, -1));
		//交期对应品种数
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
	 * 获取所有的大类的类名 以及 大类表头信息 以及每个大类所有的信息
	 */
	@Transactional()
	@Override
	public List<Map<String, Object>> getPartClassAndHeadData(String number, String search, Date searchTime) {
		
		System.out.println("这里是所有零件大类的名称以及数据查询");
		List<Map<String, Object>> partClassAndHeadData=new ArrayList<Map<String, Object>>();//存放所有大类表头数据
		List<Map<String, Object>> className=new ArrayList<Map<String, Object>>();//存放大类型号 名称
		
		//获取所有大类名
		className=this.getAllPartClass(search,searchTime);
		
//		productivityWeek(searchTime);//设置四个产能周
		
		for(Map<String, Object> map:className){//根据大类的
			
			Map<String, Object> head=new HashMap<String, Object>();//存放每个大类的类名以及表头信息
			head.put("partClass", (String)map.get("name"));
			head.put("feedingNum", this.totalFeeding(searchTime, Integer.valueOf(map.get("id").toString()), null));
			head.put("submitNum", this.totalSubmit(searchTime, Integer.valueOf(map.get("id").toString()), null));
			head.put("overHeat", this.totalOvearHeat(searchTime, Integer.valueOf(map.get("id").toString()), null));
			//交期计划数量
			double total;
			for(int i=0;i<5;i++){
				
				if(i==4&& delivery[4].equals(" ")){
					
					head.put("deliveryNum"+(i+1), " ");
					
				}else{
					
					total=this.totalProductivityWeek(number, search, productivityWeek[i], productivityWeek[i+1], Integer.valueOf(map.get("id").toString()), null);
					head.put("deliveryNum"+(i+1), total);
				}
			}
			//投料的品种数
			head.put("typeOfFeeding", this.totalType(searchTime, Integer.valueOf(map.get("id").toString())));
			//提交的品种数
			head.put("typeOfSubmit", this.totalType(searchTime, Integer.valueOf(map.get("id").toString())));
			//过热对应品种数
			head.put("typeOfOverHeat", this.totalType(searchTime, Integer.valueOf(map.get("id").toString())));
			//交期对应品种数
			for(int i=0;i<5;i++){
				
				if(i==4&& delivery[4].equals(" ")){
					
					head.put("typeOfDelivery"+(i+1), " ");
					
				}else{
					
					int type=totalType(searchTime, Integer.valueOf(map.get("id").toString()));
					head.put("typeOfDelivery"+(i+1), type);
				}
			}
			//每个大类对应的表体信息
			head.put("partClassData", this.getTypeData(number, search,searchTime, Integer.valueOf(map.get("id").toString())));
	
			partClassAndHeadData.add(head);
		}	
		return partClassAndHeadData;
	}
	
	/**
	 * @author JunSun
	 * 产能周计算公式
	 */
	@SuppressWarnings("deprecation")
	public void productivityWeek(Date searchTime){
		
		for (int i=0; i<delivery.length;i++) {
			
			delivery[i]="";
		}
		for(int i=0;i<productivityWeek.length;i++){
			
			productivityWeek[i]=null;
		}
		
		System.out.println("这里是产能周计算方法，拿到的searchTime是----"+(searchTime.getYear()+1900)+"-"+(searchTime.getMonth()+1)+"-"+searchTime.getDay());
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,(searchTime.getYear()+1900));
		//设置月份
		cal.set(Calendar.MONTH, searchTime.getMonth());
		//设置日历中月份的第1天
		cal.set(Calendar.DAY_OF_MONTH, 1);
		//获得这个月第一天 是星期几 1-星期日 2-星期一 。。。递推
		int dateIndex=cal.get(Calendar.DAY_OF_WEEK);
		//获得这个月最后一天
		int lastDate=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日期为最后一天
		cal.set(Calendar.DAY_OF_MONTH, lastDate);
		//获取这个月最后一天是星期几
		int lastDateIndex=cal.get(Calendar.DAY_OF_WEEK);
		//日期设置回第一天
		cal.set(Calendar.DAY_OF_MONTH, 1);
		if(dateIndex==1){//如果是星期天 则工作日往后递推
		
			
			for (int i = 0; i < 4; i++) {

					// 获取五个时间节点 用于分产能周对数据库查询
					if (i == 0) {
						cal.set(Calendar.DAY_OF_MONTH, dateIndex + 1);
						productivityWeek[i] = cal.getTime();
						cal.set(Calendar.DAY_OF_MONTH, dateIndex + 7);
						productivityWeek[i + 1] = cal.getTime();
					} else {
	
						cal.set(Calendar.DAY_OF_MONTH, dateIndex + 7);
						productivityWeek[i + 1] = cal.getTime();
					}
					// 分出四个产能周
					delivery[i] = (dateIndex + 1) + "#-" + (dateIndex + 7) + "#";
					dateIndex += 7;
				
				
			}
			productivityWeek[5] = null;
			delivery[4] = " ";
			
		}else if(dateIndex==2){
			
			for(int i=0;i<4;i++){
				
				// 获取五个时间节点 用于分产能周对数据库查询
				if (i == 0) {
					cal.set(Calendar.DAY_OF_MONTH, dateIndex-1);
					productivityWeek[i] = cal.getTime();
					cal.set(Calendar.DAY_OF_MONTH, dateIndex + 5);
					productivityWeek[i + 1] = cal.getTime();
				} else {

					cal.set(Calendar.DAY_OF_MONTH, dateIndex + 5);
					productivityWeek[i + 1] = cal.getTime();
				}
				// 分出四个产能周
				delivery[i] = (dateIndex-1) + "#-" + (dateIndex + 5) + "#";
				dateIndex += 7;
			}
			productivityWeek[5] = null;
			delivery[4] = " ";
			
		}else{//如果这个月一号不是星期天
			
			cal.set(Calendar.MONTH, searchTime.getMonth()-1);//日期设置上一个月
			int lastDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);//获取上个月的天数
			System.out.println("上一个月天数----"+lastDay);
			for(int i=0;i<5;i++){
					
				if(i==0){//如果是第一个跨月产能周
						
					int firstDay=lastDay-dateIndex+3;
					if(firstDay>lastDay){
						
						firstDay-=lastDay;
					}
					cal.set(Calendar.DAY_OF_MONTH, firstDay);
					productivityWeek[i]=cal.getTime();
					cal.set(Calendar.MONTH, searchTime.getMonth());//日期设置回到这个月
					cal.set(Calendar.DAY_OF_MONTH, 9-dateIndex);
					productivityWeek[i+1]=cal.getTime();
					
					delivery[i]+=(firstDay)+"#-";
					delivery[i]+=(9-dateIndex)+"#";
					
				}else if(i==4&& (dateIndex-lastDateIndex)>=5){//判断这个月有没有跨越六行
				
					int firstDay=(10-dateIndex)+((i-1)*7);
					cal.set(Calendar.DAY_OF_MONTH, firstDay+6);
					productivityWeek[i+1]=cal.getTime();
					
					delivery[i]+=firstDay+"#-"+(firstDay+6)+"#";
					
				}else if(i==4){
					
					productivityWeek[5]=null;
					delivery[4]=" ";
					
				}else{//不是跨越财产能周
					
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
	 * 根据日期 和 大类名称   查询该零件大类的信息
	 */
	@Override
	public List<Map<String, Object>> getTypeData(String number,String search, Date searchTime,int partClass) {
		
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> name=new ArrayList<Map<String,Object>>();
		String searchDate=sdf.format(searchTime);
		//productivityWeek(searchTime);//设置四个产能周	
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
		for(Map<String, Object> map:name){//根据大类中所有零件名获取零件信息
			
			System.out.println("大类是："+partClass+" 零件类型是："+map.get("name"));
			Map<String, Object> typeData=new HashMap<String, Object>();
			String no=(String)map.get("no");
			String partName=(String)map.get("name");
			String[] part=partName.split("-");
			if(part.length==1){//没有小型号
				
				typeData.put("typeNo", part[0]);
				typeData.put("part", "01");
			}else{//有小型号
				
				char[] partNum=part[part.length-1].toCharArray();//取出最后一个字符串
				int flag=0;
				for(char ch:partNum){
					
					if(!Character.isDigit(ch)){
						
						flag=1;
					}
				}
				if(flag==1){//最后一个字符串不是小型号
					
					typeData.put("typeNo", partName);
					typeData.put("part", "01");
					
				}else{//最后一个字符串是数字的小型号
					
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
			//该零件投料数量
			typeData.put("typeFeeding", this.totalFeeding(searchTime, partClass, no));
			//该零件的提交数量
			typeData.put("typeSubmit", this.totalSubmit(searchTime, partClass, no));
			//该零件的过热数量
			typeData.put("typeOverHeat", this.totalOvearHeat(searchTime, partClass, no));
			//产能周计划数量计算
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
	 * 计算投料总和
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double totalFeeding(Date search, int partClass, String partNo) {
		
		String searchTime=sdf.format(search);
		String hql="";
		double total=0;
		List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();
		if(partClass==-1){//如果给定大类为空，查询分厂投料总和
			
			hql="select new Map("
					+ "monthPlan.planNum as feeding) "
					+ "from TJobplanInfo monthPlan "
					+ "where DATE_FORMAT(monthPlan.realStarttime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m') ";
			
		}else if(partClass!=-1&& partNo!=null){//查询某个大类中的小类的信息
			
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
			
		}else{//如果给定大类不为空，查询对应大类的投料总和
			
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
		System.out.println("投料合"+total);
		return total;
	}

	/**
	 * @author JunSun
	 * 计算提交总和
	 */
	@Override
	public double totalSubmit(Date search, int partClass, String partNo) {
		
		String searchTime=sdf.format(search);
		String hql=null;
		double total=0;
		if(partClass==-1){//如果给定大类为空，查询分厂提交总和
			
			hql="select new Map("
					+ "monthPlan.finishNum as submit) "
					+ "from TJobplanInfo monthPlan "
					+ "where DATE_FORMAT(monthPlan.realEndtime, '%Y%m')=DATE_FORMAT('"+searchTime+"','%Y%m') ";
			
		}else if(partClass!=-1&& partNo!=null){//查询大类中某个小类的信息
			
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
			
		}else{//如果给定大类不为空，查询对应大类的提交总和
			
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
		System.out.println("提交合"+total);
		return total;
	
	}
	/**
	 * @author JunSun
	 * 计算过热总和
	 */
	@Override
	public double totalOvearHeat(Date search, int partClass, String partNo) {
		return 0;
	}
	/**
	 * @author JunSun
	 * 产能周划分计划数量总和 以及 提交总和
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
		}else if(partClass!=-1&& partNo!=null){//查询大类中某个小类的信息
			
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
	 * 计算大类 以及总的品种数目
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
		if((productivityWeek[0].getMonth()+1)>(searchTime.getMonth()+1)){//如果日期跨月
		
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
