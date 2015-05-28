package smtcl.mocs.beans.erp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.ErpMaterialDataModel;
import smtcl.mocs.pojos.job.TProductionScrapInfo;
import smtcl.mocs.services.erp.IERPSerice;
import smtcl.mocs.services.jobplan.IJobPlanService;



/**
 * 2017-07-01
 * @author liguoqiang
 *
 */
@ManagedBean(name="productionScrapAddBean")
@ViewScoped
public class ProductionScrapAddBean{
	private List<Map<String,Object>> scrapUserList;//�����˻��б�
	private String scrapUserName;
	private List<Map<String,Object>> jobdispatchNoList;//��������б�
	private List<Map<String,Object>> erpTaskNoList;//erp������б�
	private List<Map<String,Object>> processNoList;//�����б�
	private List<Map<String,Object>>  userList;//�û��б�
	private List<Map<String,Object>> sectionList;//�����б�
	private String jobdispatchNo;//�������
	private String processScrapNum;//��������
	private String materialScrapNum;//�Ϸ�����
	private String scrapSum;//��������
	private List<String> isCurrentProcess;//�Ƿ��������Ѽӹ�
	private TProductionScrapInfo  tps=new TProductionScrapInfo();//������������
	private IJobPlanService jobPlanService = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
	private IERPSerice erpCommonService = (IERPSerice)ServiceFactory.getBean("erpCommonService");//ע������һ�����ݿ��service
	private TreeNode jobroot;//������ʾ
	private TreeNode jobselectroot;//����ѡ��
	private Date checkTime;//��Ʊ����
	private String message;//����Ƿ�ɹ���Ϣ
	private String tackTike;//��Ʊ��Ա
	private String onlineProcessId;//ǰ������id
	private String processPlanID;//��ǰ�����Ĺ��շ���id
	private List<Map<String,Object>> partList;//���list
	private String selectedPartId;//ѡ�е����id
	private List<Map<String,Object>> jobPlanList;//�����б�
	private String jobPlanId;//ѡ�е�����id
	private String nodeid;
	private List<Map<String,Object>> checkList;
	private List isConcession;//�Ƿ��ò�����
	private List<Map<String,Object>> porcessTimeList;//���� ����ʱ�嵥
	private ErpMaterialDataModel processList;//��ʱ�㷨 ����������ʾ
	private Map<String,Object>[] selectProcess;//ѡ�еĹ�ʱ
	private String djgs;//������ʱ
	private String hjgs;//�ϼƹ�ʱ
	private String ljgs;//�ۼƹ�ʱ
	private String dxgs;//����ʱ
	private List<Object> selectedpl=new ArrayList<Object>();//ѡ�й��򼯺�
	private List<Map<String,Object>> zrcjlist=new ArrayList<Map<String,Object>>();//���β����б�
	
	@SuppressWarnings("unchecked")
	public ProductionScrapAddBean(){
//		String wy=StringUtils.getUniqueString();//Ψһ��ʶ
//		String tzdcode="BF_";
//		tps.setTzdCode(tzdcode);
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		IERPSerice erpCommonService = (IERPSerice)ServiceFactory.getBean("erpCommonService");//ע������һ�����ݿ��service
		//Ԥ�����û��б�
//		IUserService us = (IUserService) ServiceFactory.getBean ("userService");
//		List<User> userlsit=us.getUserbyAll(); 
		userList=new ArrayList<Map<String,Object>>();
		//��ʼ�������˻��б�
		scrapUserList=new ArrayList<Map<String,Object>>();
		scrapUserList=erpCommonService.getScrapUser();
		//���β��������б�
		zrcjlist=erpCommonService.getWisVendorListMapForAll();
		
		
		//Ԥ���ع������
//		IJobPlanService jobPlanService2 = (IJobPlanService)ServiceFactory.getBean("jobPlanService");
		jobdispatchNoList=new ArrayList<Map<String,Object>>();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid")+"";
		jobdispatchNoList=jobPlanService.getJobdispatchList(nodeid,null,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchNoList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
		
		//Ԥ���ع����б�
		sectionList=new ArrayList<Map<String,Object>>();
		String[] gd={"ETC�����üӹ���ԪETC����","ETC�����üӹ���Ԫ���ܹ���","ETC�����üӹ���Ԫ�������","��K�ӹ���Ԫ��K������","��K�ӹ���Ԫ���Ṥ��",
				"��K�ӹ���Ԫ���͹���","��K�ӹ���Ԫ��������","СK�ӹ���Ԫ36������","СK�ӹ���Ԫ���Լӹ�����","СK�ӹ���Ԫ����͵����� ","СK�ӹ���Ԫ���幤��"};
		for(int i=0;i<gd.length;i++){
			Map<String,Object> gdmap=new HashMap<String, Object>();
			 gdmap.put("gd", gd[i]);
			 sectionList.add(gdmap);
		}
		//Ԥ���� �����Ա�б�
		checkList=new ArrayList<Map<String,Object>>();
		String[] check={"�Ӽ�01","�Ӽ�02","�Ӽ�03","�Ӽ�09","�Ӽ�10","�Ӽ�12","�Ӽ�15","�Ӽ�16","�Ӽ�17","�Ӽ�18","�Ӽ�20","�Ӽ�24","�Ӽ�25",
				"�Ӽ�29"};
		for(int i=0;i<check.length;i++){
			Map<String,Object> gdmap=new HashMap<String, Object>();
			 gdmap.put("check", check[i]);
			 checkList.add(gdmap);
		}
		 
		 tps.setInvoiceDat(new Date());
		 checkTime=new Date();
		 //����������ҳ�� ����������
		if ( null!=request.getParameter("jobdispatchNO")){
			String jobdisplatNoselect=request.getParameter("jobdispatchNO");
			Map<String,Object> mapJob=jobPlanService.getDataByjobdispatchNo(jobdisplatNoselect);
			erpTaskNoList=new ArrayList<Map<String,Object>>();
			tps.setEntityName(null==mapJob.get("taskNO")?"":mapJob.get("taskNO").toString());//erp�����
			tps.setItemCode(null==mapJob.get("itemCode")?"":mapJob.get("itemCode").toString());//���ϱ�����Ϣ���
			tps.setItemDesc(null==mapJob.get("itemDesc")?"":mapJob.get("itemDesc").toString());//����������Ϣ���
			tps.setToOperationNum(null==mapJob.get("toOperationNum")?"":mapJob.get("toOperationNum").toString());//��� ������
			onlineProcessId=null==mapJob.get("onlineProcessId")?"":mapJob.get("onlineProcessId").toString();//����ǰ������
			jobdispatchNo=jobdisplatNoselect;//��ǰѡ�񹤵��� ��ʾ
			tps.setResult("����");
			processNoList=new ArrayList<Map<String,Object>>();
			processNoList=(List<Map<String,Object>>)mapJob.get("zrOperationNum");//���ι����б�
			processPlanID=mapJob.get("processPlanID")+"";
			selectedPartId=mapJob.get("partId")+"";//����id
			request.setAttribute("jobdispatchNO", null);
			processList=new ErpMaterialDataModel(processNoList);
		}
		//Ԥ��������б���Ϣ
		partList=erpCommonService.getTPartTypeInfoByNodeid(nodeid,null);
	} 
	

	/**
	 * ���������ı��¼�
	 */
	public void selectPartChange(){
		jobPlanList=erpCommonService.getJopPlanByPartId(selectedPartId);
		List<Map<String,Object>> partlist=erpCommonService.getTPartTypeInfoByNodeid(nodeid,selectedPartId);
		if(null!=partlist&&partlist.size()>0){
			Map<String,Object> partMap=partlist.get(0);
			tps.setItemCode(partMap.get("no").toString() );
		}
		jobdispatchNo="";
		tps.setEntityName("");
		tps.setToOperationNum("");
		processNoList=new ArrayList<Map<String,Object>>();
		processList=new ErpMaterialDataModel(processNoList);
		djgs="0";//������ʱ
		hjgs="0";//�ϼƹ�ʱ
		ljgs="0";//�ۼƹ�ʱ
		dxgs="0";//����ʱ
		isCurrentProcess = new ArrayList<String>();
		isConcession=new ArrayList();
	}
	
	
	/**
	 * ����������ı��¼�
	 */
	public void selectJobPlanChange(){
		jobdispatchNoList=jobPlanService.getJobdispatchList(nodeid,null,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchNoList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
	}
	
	public void onkeyupJobList() {//������Ű��²�ѯ�¼�
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		String nodeid=session.getAttribute("nodeid")+"";
		jobdispatchNoList=jobPlanService.getJobdispatchList(nodeid,jobdispatchNo,jobPlanId);
		jobroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:jobdispatchNoList){
			TreeNode node0 = new DefaultTreeNode(mm, jobroot);
		}
    }
	
	@SuppressWarnings("unchecked")
	public void OnTreeNodeSelect(NodeSelectEvent event){//�������ѡ���¼�
		Map<String,Object> map=(Map<String,Object>)event.getTreeNode().getData();
		String jobdisplatNoselect=map.get("no").toString();//��ȡ�������
		Map<String,Object> mapJob=jobPlanService.getDataByjobdispatchNo(jobdisplatNoselect);
		
		erpTaskNoList=new ArrayList<Map<String,Object>>();
		tps.setEntityName(null==mapJob.get("taskNO")?"":mapJob.get("taskNO").toString());//erp�����
		
		tps.setItemCode(null==mapJob.get("itemCode")?"":mapJob.get("itemCode").toString());//���ϱ�����Ϣ���
		tps.setItemDesc(null==mapJob.get("itemDesc")?"":mapJob.get("itemDesc").toString());//����������Ϣ���
		tps.setToOperationNum(null==mapJob.get("toOperationNum")?"":mapJob.get("toOperationNum").toString());//��� ������
		onlineProcessId=null==mapJob.get("onlineProcessId")?"":mapJob.get("onlineProcessId").toString();//����ǰ������
		
		jobdispatchNo=jobdisplatNoselect;//��ǰѡ�񹤵��� ��ʾ
		tps.setResult("����");
		
		selectedPartId=mapJob.get("partId")+"";//����id
		
		processNoList=new ArrayList<Map<String,Object>>();
		processNoList=(List<Map<String,Object>>)mapJob.get("zrOperationNum");//���ι����б�
		processPlanID=mapJob.get("processPlanID")+"";
		
		//��ӹ�ʱ�б�����
		processList=new ErpMaterialDataModel(processNoList);
	}
	/**
	 * �����Ϸ� �����ܷ�
	 */
	public void GFLFSUM(){
		scrapSum="0";
		if(null!=processScrapNum&&!"".equals(processScrapNum)){
			scrapSum=Integer.parseInt(processScrapNum)+"";
		}
		if(null!=materialScrapNum&&!"".equals(materialScrapNum)){
			scrapSum=(Integer.parseInt(scrapSum)+Integer.parseInt(materialScrapNum))+"";
		}
		
	}
	/**
	 * ���㹤ʱ��Ϣ
	 */
	public void jsgsInfo(){
		int onePorcessTime=0;//������ʱ
		int totalProcessTime=0;//�ϼƹ�ʱ
		int totalTime=0;//�ۼƹ�ʱ
		int processTime=0;//����ʱ
		
		for(Map<String,Object> proMap:processNoList){
			if(proMap.get("processOrder").toString().equals(tps.getToOperationNum())){//�ж�������
				//��ֵ ����ʱ
				processTime=Integer.parseInt(null==proMap.get("processingTime")?"0":proMap.get("processingTime").toString());
				break;
			}
		}
		for(Map<String,Object> processMap:selectProcess){
			//�жϵ�ǰ�Ƿ�ѡ�������� ���ѡ����ѡ �Ƿ�������ӹ�
			if(processMap.get("processOrder").toString().equals(tps.getToOperationNum())){
				isCurrentProcess.add("��");
			}
			//������ʱ
			onePorcessTime=onePorcessTime+Integer.parseInt(null==processMap.get("processingTime")?"0":processMap.get("processingTime").toString());
			
			selectedpl.add(processMap.get("processOrder"));
		}
		//�ϼƹ�ʱ
		totalProcessTime=processTime*Integer.parseInt(null==scrapSum?"0":scrapSum);
		//�ۼƹ�ʱ
		totalTime=onePorcessTime*Integer.parseInt(null==scrapSum?"0":scrapSum);
		
		dxgs=processTime+"";//����ʱ
		djgs=onePorcessTime+"";//������ʱ
		hjgs=totalProcessTime+"";//�ϼƹ�ʱ
		if(null!=isConcession&&isConcession.size()>0){
			ljgs=(totalTime*0.5)+"";//�ۼƹ�ʱ
		}else{
			ljgs=totalTime+"";//�ۼƹ�ʱ
		}
		
		message=erpCommonService.getJobdispatchTSXX(jobdispatchNo, onlineProcessId, processScrapNum, materialScrapNum, isCurrentProcess);
		
	}
	
	/**
	 * �Ƿ�������ѡ�з��� 
	 */
	@SuppressWarnings("unchecked")
	public void SelectCurrentProcess(){
		if(isCurrentProcess.size()>0){//�����ǰ�Ѿ���ѡ�� �Ƿ�������ӹ�  ��ô ��ʱ�㷨������������빴ѡ
			Map<String,Object>[] ls=selectProcess;
			boolean bool=true;//�жϵ�ǰѡ���Ƿ��Ѿ����������� Ĭ��û��ѡ��
			for(Map<String,Object> pMap:selectProcess){
				if(pMap.get("processOrder").toString().equals(tps.getToOperationNum())){
					bool=false;//��ѡ��
					break;
				}
			}
			if(bool){
				for(Map<String,Object> proMap:processNoList){
					if(proMap.get("processOrder").toString().equals(tps.getToOperationNum())){//�ж�������
						selectProcess=new Map[null==selectProcess?0:selectProcess.length+1];//���������1��
						if(null!=ls&&ls.length>0){
							for(int i=0;i<ls.length;i++){
								selectProcess[i]=ls[i];
							}
							selectProcess[ls.length]=proMap;	
						}else{
							selectProcess[0]=proMap;	
						}
						break;
					}
				}
				processList=new ErpMaterialDataModel(processNoList);
			}
		}else{
			Map<String,Object>[] ls=selectProcess;
			selectProcess=new Map[null==selectProcess?0:ls.length-1];
			if(selectProcess.length>0){
				int j=0;
				for(int i=0;i<ls.length;i++){
					 Map<String,Object> pMap=ls[i];
					 System.out.println(pMap.get("processOrder").toString()+"---"+tps.getToOperationNum().toString());
					if(!pMap.get("processOrder").toString().trim().equals(tps.getToOperationNum().toString().trim())){
						selectProcess[j]=ls[i];
						j++;
					}
				}
			}
			
		}
	}
	/**
	 * ��������
	 */
	public void saveData(String parm){
		if("1".equals(parm)){
			
		}else{
			if(null!=isConcession&&isConcession.size()>0){
				djgs=(Integer.parseInt(djgs)*0.5)+"";//������ʱ
			}
			message=erpCommonService.saveTProductionScrapInfo(tps, jobdispatchNo, isCurrentProcess, processScrapNum,materialScrapNum, 
					onlineProcessId,processPlanID,selectedPartId,djgs,selectedpl,scrapUserName);
			if(message.equals("����ɹ�")){
				tps=new TProductionScrapInfo();
				jobdispatchNo="";
				selectedPartId="";
				jobPlanId="";
				processScrapNum="";
				materialScrapNum="";
				scrapSum="";
				tackTike="";
				isCurrentProcess=new ArrayList();
			}
		}
	}
	
	public List<Map<String, Object>> getScrapUserList() {
		return scrapUserList;
	}
	public void setScrapUserList(List<Map<String, Object>> scrapUserList) {
		this.scrapUserList = scrapUserList;
	}
	public List<Map<String, Object>> getJobdispatchNoList() {
		return jobdispatchNoList;
	}
	public void setJobdispatchNoList(List<Map<String, Object>> jobdispatchNoList) {
		this.jobdispatchNoList = jobdispatchNoList;
	}
	
	public List<Map<String, Object>> getErpTaskNoList() {
		return erpTaskNoList;
	}
	public void setErpTaskNoList(List<Map<String, Object>> erpTaskNoList) {
		this.erpTaskNoList = erpTaskNoList;
	}
	public List<Map<String, Object>> getProcessNoList() {
		return processNoList;
	}
	public void setProcessNoList(List<Map<String, Object>> processNoList) {
		this.processNoList = processNoList;
	}
	public List<Map<String, Object>> getUserList() {
		return userList;
	}
	public void setUserList(List<Map<String, Object>> userList) {
		this.userList = userList;
	}
	public List<Map<String, Object>> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<Map<String, Object>> sectionList) {
		this.sectionList = sectionList;
	}
	public TProductionScrapInfo getTps() {
		return tps;
	}
	public void setTps(TProductionScrapInfo tps) {
		this.tps = tps;
	}
	public String getJobdispatchNo() {
		return jobdispatchNo;
	}
	public void setJobdispatchNo(String jobdispatchNo) {
		this.jobdispatchNo = jobdispatchNo;
	}
	public String getProcessScrapNum() {
		return processScrapNum;
	}
	public void setProcessScrapNum(String processScrapNum) {
		this.processScrapNum = processScrapNum;
	}
	public String getMaterialScrapNum() {
		return materialScrapNum;
	}
	public void setMaterialScrapNum(String materialScrapNum) {
		this.materialScrapNum = materialScrapNum;
	}
	public String getScrapSum() {
		return scrapSum;
	}
	public void setScrapSum(String scrapSum) {
		this.scrapSum = scrapSum;
	}
	public List getIsCurrentProcess() {
		return isCurrentProcess;
	}
	public void setIsCurrentProcess(List isCurrentProcess) {
		this.isCurrentProcess = isCurrentProcess;
	}
	public TreeNode getJobroot() {
		return jobroot;
	}
	public void setJobroot(TreeNode jobroot) {
		this.jobroot = jobroot;
	}
	public TreeNode getJobselectroot() {
		return jobselectroot;
	}
	public void setJobselectroot(TreeNode jobselectroot) {
		this.jobselectroot = jobselectroot;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTackTike() {
		return tackTike;
	}
	public void setTackTike(String tackTike) {
		this.tackTike = tackTike;
	}
	public List<Map<String,Object>> getPartList() {
		return partList;
	}
	public void setPartList(List<Map<String,Object>> partList) {
		this.partList = partList;
	}
	public String getSelectedPartId() {
		return selectedPartId;
	}
	public void setSelectedPartId(String selectedPartId) {
		this.selectedPartId = selectedPartId;
	}
	public List<Map<String,Object>> getJobPlanList() {
		return jobPlanList;
	}
	public void setJobPlanList(List<Map<String,Object>> jobPlanList) {
		this.jobPlanList = jobPlanList;
	}
	public String getJobPlanId() {
		return jobPlanId;
	}
	public void setJobPlanId(String jobPlanId) {
		this.jobPlanId = jobPlanId;
	}
	public List<Map<String,Object>> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<Map<String,Object>> checkList) {
		this.checkList = checkList;
	}
	public List getIsConcession() {
		return isConcession;
	}
	public void setIsConcession(List isConcession) {
		this.isConcession = isConcession;
	}
	public List<Map<String, Object>> getPorcessTimeList() {
		return porcessTimeList;
	}
	public void setPorcessTimeList(List<Map<String, Object>> porcessTimeList) {
		this.porcessTimeList = porcessTimeList;
	}
	public ErpMaterialDataModel getProcessList() {
		return processList;
	}
	public void setProcessList(ErpMaterialDataModel processList) {
		this.processList = processList;
	}
	public Map<String, Object>[] getSelectProcess() {
		return selectProcess;
	}
	public void setSelectProcess(Map<String, Object>[] selectProcess) {
		this.selectProcess = selectProcess;
	}
	public String getDjgs() {
		return djgs;
	}
	public void setDjgs(String djgs) {
		this.djgs = djgs;
	}
	public String getHjgs() {
		return hjgs;
	}
	public void setHjgs(String hjgs) {
		this.hjgs = hjgs;
	}
	public String getLjgs() {
		return ljgs;
	}
	public void setLjgs(String ljgs) {
		this.ljgs = ljgs;
	}
	public String getDxgs() {
		return dxgs;
	}
	public void setDxgs(String dxgs) {
		this.dxgs = dxgs;
	}
	public List<Map<String, Object>> getZrcjlist() {
		return zrcjlist;
	}
	public void setZrcjlist(List<Map<String, Object>> zrcjlist) {
		this.zrcjlist = zrcjlist;
	}
	public String getScrapUserName() {
		return scrapUserName;
	}
	public void setScrapUserName(String scrapUserName) {
		this.scrapUserName = scrapUserName;
	}
	
	
}
