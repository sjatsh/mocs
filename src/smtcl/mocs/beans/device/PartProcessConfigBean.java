package smtcl.mocs.beans.device;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.PartProcessDataModel;
import smtcl.mocs.model.TProcessplanInfoModel;
import smtcl.mocs.model.TQualityParamDataModel;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.pojos.job.TQualityParam;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;

/**
 * �����������
 * @����ʱ�� 2013-07-12
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��  
 * @version V1.0
 */
@ManagedBean(name="partProcessConfigBean")
@ViewScoped
public class PartProcessConfigBean {
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	/**
	 * ��ѯ����
	 */
	private String query;
	/**
	 * ���νڵ�
	 */
	private TreeNode root;
	/**
	 * ��ǰѡ��ڵ�
	 */
	private TreeNode selectedPart;  
	/**
	 * ���շ�������������
	 */
	private List<Map<String,Object>> processPlan=new ArrayList<Map<String,Object>>();
	
	/**
	 * ��ǰѡ�еĹ��շ���
	 */
	private String selectProcessPlan;
	/**
	 * dataTable����  ʵ���˶���ѡ��
	 */
	private PartProcessDataModel mediumPartModel;
	/**
	 * ������շ���dataTable
	 */
	private TProcessplanInfoModel processPlanModel;
	/**
	 * dataTableѡ�е���
	 */
	private TProcessInfo[] selectedRowPart;
	/**
	 * ������շ���dataTableѡ����
	 */
	private TProcessplanInfo[] selectedRowPlan;
	/**
	 * Ҫ����������
	 */
	private TProcessInfo addPart=new TProcessInfo();
	/**
	 * ������·����
	 */
	private List<TProcessInfo> gyxllist;
	/**
	 * �ɱ�����
	 */
	private List<Map<String,Object>> cblist;
	/**
	 * ��������
	 */
	private List<Map<String,Object>> wllist;
	/**
	 * �о�����
	 */
	private List<Map<String,Object>> jjlist;
	/**
	 * ��������
	 */
	private List<Map<String,Object>> djlist;
	/**
	 * ��̨����
	 */
	private List<Map<String,Object>> jtlist;
	/**
	 * �ʼ�����
	 */
	private List<Map<String,Object>> zjlist;
	/**
	 * ��ǰѡ�����
	 */
	private String selectProcess;
	/**
	 * ������շ�����ѯ����
	 */
	private String processPlanSearch;
	/**
	 * ������������շ���
	 */
	private TProcessplanInfo addPlal=new TProcessplanInfo();
	/**
	 * ��ת����
	 */
	private Map<String,String> path;
	
	private String loadprocess="1";
	private String nodeid;
	
	public void setLoadprocess(String loadprocess) {
		this.loadprocess = loadprocess;
	}
	public String getLoadprocess() {
		Constants.LOADCOUNT=1;
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=session.getAttribute("nodeid2")+"";
		root = new DefaultTreeNode("Root", null);
        root.setExpanded(true);
        TreeNode partInfo = new DefaultTreeNode("�����Ϣ�б�(����|���)", root); 
        partInfo.setExpanded(true);
        List<Map<String,Object>> result=partService.getPartTree(null,nodeid);
        for(Map<String,Object> mm:result){
        	 TreeNode part = new DefaultTreeNode(mm.get("name")+"|"+mm.get("no"), partInfo); 
        	 part.setExpanded(true);
        }
        //processPlan=new ArrayList<Map<String,Object>>();
       // gyxllist=new ArrayList<TProcessInfo>();
       //List<TProcessInfo> pprs=new ArrayList<TProcessInfo>();
		//mediumPartModel =new PartProcessDataModel(pprs);
		
		return loadprocess;
	}

	

	/**
	 * ���췽��
	 */
    public PartProcessConfigBean() {  
        selectProcessPlan="";
        Constants.LOADCOUNT=1;
    }  
    
    /**
	 *  ��ѯ����
	 */
	public void queryData(){
		root = new DefaultTreeNode("Root", null);
        root.setExpanded(true);
        TreeNode partInfo = new DefaultTreeNode("�����Ϣ�б�(����|���|ͼ��)", root); 
        partInfo.setExpanded(true);
        if("����ID/���/����".equals(query))
			query=null;
        List<Map<String,Object>> result=partService.getPartTree(query,nodeid);
        for(Map<String,Object> mm:result){
        	 TreeNode part = new DefaultTreeNode(mm.get("name")+"|"+mm.get("no")+"|"+mm.get("drawingno"), partInfo); 
        	 part.setExpanded(true);
        }
	}
	
	/**
	 * ���ڵ㵥���¼�����ȡ��������б�
	 */
	public void getPartProcessList(NodeSelectEvent event){
		String name=(event.getTreeNode().toString());
		String[] str=name.split("\\|");
		selectProcess=str[1];
		query=str[0];
		List<TProcessplanInfo> result=partService.getTProcessplanInfo(str[1],null);
		processPlan=new ArrayList<Map<String,Object>>();
		if(null!=result&&result.size()>0){
			for(TProcessplanInfo tpp:result){
				Map map=new HashMap();
				map.put("id",tpp.getId());
				map.put("name", tpp.getName());
				processPlan.add(map);
				if(tpp.getDefaultSelected()==1){
					selectProcessPlan=tpp.getId().toString();
				}
			}
		}else{
			processPlan=new ArrayList<Map<String,Object>>();
			selectProcessPlan=null;
		}
		
		if(null==selectProcessPlan||"".equals(selectProcessPlan)){
			if(result!=null&&result.size()>0)
			selectProcessPlan=((TProcessplanInfo)result.get(0)).getId().toString();
		}
		
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		mediumPartModel =new PartProcessDataModel(pprs);
		gyxllist=pprs;
		path=new HashMap<String, String>();
		path.put("ljgxxd", selectProcess+","+selectProcessPlan+",ljgxxd");
		path.put("ljgxxdup", selectProcess+","+selectProcessPlan+",ljgxxdup");
	}
	
	/**
	 *����ѡ��������ı䷽��
	 */
	public void selectChange( ){
		System.out.println(selectProcessPlan);
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		mediumPartModel =new PartProcessDataModel(pprs);
		gyxllist=partService.getTProcessInfo(selectProcessPlan);
		path=new HashMap<String, String>();
		path.put("ljgxxd", selectProcess+","+selectProcessPlan+",ljgxxd");
		path.put("ljgxxdup", selectProcess+","+selectProcessPlan+",ljgxxdup");
	}
	
	/**
	 * dataTable���·���
	 */
	public void onEdit(RowEditEvent event){
		TProcessInfo tpart=(TProcessInfo) event.getObject();
        partService.saveTProcessInfo(tpart);
	}
	
	/**
	 * dataTableȡ������
	 */
	public void onCancel(){
		
	}
	
	/**
	 * dataTableɾ������
	 */
	public void onDelete(){
		for(TProcessInfo part:selectedRowPart){
    		partService.deleteTProcessInfo(part);
    		
    	}
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		mediumPartModel =new PartProcessDataModel(pprs);	
	}
	/**
	 * ��ȡ������·����
	 */
	public void getGYXLData(){
		gyxllist=partService.getTProcessInfo(selectProcessPlan);
	}
	/**
	 * ��ȡ�ɱ�����
	 */
	public void getCBData(){
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		cblist=partService.getCBData(pprs);
	}
	
	/**
	 * ��ȡ��������
	 */
	public void getWLData(){
		List<TProcessInfo> pprs=partService.getTProcessInfo(selectProcessPlan);
		wllist=partService.getWLData(pprs);
	}
	
	/**
	 * ��ȡ�о�����
	 */
	public void getJJData(){
		 jjlist=partService.getJJData(selectProcessPlan);
	}
	/**
	 * ��ȡ��������
	 */
	public void getDJData(){
		djlist=partService.getDJData(selectProcessPlan);
	}
	/**
	 * ��ȡ��̨����
	 */
	public void getJTData(){
		jtlist=partService.getJTData(selectProcessPlan);
	}
	/**
	 * ��ȡ�ʼ�����
	 */
	public void getZJData(){
		 zjlist=partService.getTQualityParamByProcessPlanId(selectProcessPlan);
		   
	}
	/**
	 *��������
	 */
	public String toAddGuide(){
		
		return "PartProcessGuide";
	}
	/**
	 * �������շ���
	 * @return
	 */
	public String toAddProcessPlan(){
		return "ProcessPlanInfoPath";
	}
	/**
	 * �༭��
	 * @return
	 */
	public String toUpdateGuide(){
		return "PartProcessGuideUpdatePath";
	}
	/**
	 * ���շ���ά����ť����¼�
	 */
	public void processPlanData(){
		List<TProcessplanInfo> rs=partService.getTProcessplanInfo(selectProcess,null);
		processPlanModel=new TProcessplanInfoModel(rs);
	}
	/**
	 * ���շ����������ɲ���
	 */
	public void onShowAddPanl(){
		List<TProcessplanInfo> rs=partService.getTProcessplanInfo(selectProcess,null);
		if(null==rs||rs.size()<1){
			addPlal.setName((selectProcess+"_01"));
		}else{
			TProcessplanInfo tpi=rs.get(rs.size()-1);
			String name=tpi.getName();
			int tt=name.lastIndexOf("_");
			String num=name.substring(tt+1,name.length());
			System.out.println(num);
			 DecimalFormat d=new DecimalFormat("00");
			addPlal.setName((selectProcess+"_"+d.format((Integer.parseInt(num)+1))));
		}
	}
	/**
	 * �������շ���
	 */
	public void addPlanData(){
		TUser userinfo=(TUser)FaceContextUtil.getSessionMap().get(Constants.USER_SESSION_KEY);
		Date date=new Date();
		TProcessplanInfo s=new TProcessplanInfo();
		s.setName(addPlal.getName());
		s.setOperator(userinfo.getLoginName());
		s.setCreateDate(date);
		s.setDescription(addPlal.getDescription());
		s.setStatus(0);
		s.setDefaultSelected(0);
		s.setNodeid(nodeid);
		List<TPartTypeInfo> tPartTypeInfo=partService.getTPartTypeInfoByNo(selectProcess);
		s.setTPartTypeInfo(tPartTypeInfo.get(0));
		String rs=partService.saveTProcessplanInfo(s); //�������շ���
		partService.updateDeDefaultTProcessplanInfo(s);//��ΪĬ��
		if(rs.equals("1")){
			FacesMessage msg = new FacesMessage("Succesful","�ļ�����ɹ�");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else if(rs.equals("0")){
			FacesMessage msg = new FacesMessage("Fall","�ļ�����ʧ��,�Ѵ���");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("Fall","�ļ�����ʧ��!");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		this.processPlanData();
	}
	/**
	 * ���շ������°�ť
	 * @param event
	 */
	public void onPlanEdit(RowEditEvent event){
		for(TProcessplanInfo plan:selectedRowPlan){
			String str=partService.updateTProcessplanInfo(plan);
			if(plan.getDefaultSelected().equals("1")){
				str=partService.updateDeDefaultTProcessplanInfo(plan);	
			}
			 
    	}
		this.searchTProcessplanInfo();
	}
	/**
	 * ���շ���ɾ����ť
	 */
	public void onDeletePlan(){
		for(TProcessplanInfo plan:selectedRowPlan){
			plan.setStatus(1);
			String str=partService.updateTProcessplanInfo(plan);
    	}
		this.processPlanData();
	}
	/**
	 * ��ΪĬ��
	 */
	public void onDefault(){
		String str="";
		if(selectedRowPlan.length>1){
			FacesMessage msg = new FacesMessage("���շ���Ĭ������","Ĭ�Ϲ��շ���һ�����ֻ������һ��Ĭ�Ϲ��շ���!");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			for(TProcessplanInfo plan:selectedRowPlan){
				 str=partService.updateDeDefaultTProcessplanInfo(plan);
	    	}
			if(str.equals("���óɹ�")){
				FacesMessage msg = new FacesMessage("���շ���Ĭ������","���óɹ�!");  
		        FacesContext.getCurrentInstance().addMessage(null, msg);
		        this.processPlanData();
			}else{
				FacesMessage msg = new FacesMessage("���շ���Ĭ������","����ʧ��!");  
		        FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}
	/**
	 * ���շ���ģ����ѯ
	 */
	public void searchTProcessplanInfo(){
        if("����ID/���/����".equals(processPlanSearch))
        	processPlanSearch=null;
		List<TProcessplanInfo> rs=partService.getTProcessplanInfo(selectProcess,processPlanSearch);
		processPlanModel=new TProcessplanInfoModel(rs);
	}
	
    public TreeNode getSelectedPart() {
		return selectedPart;
	}

	public void setSelectedPart(TreeNode selectedPart) {
		this.selectedPart = selectedPart;
	}

	public TreeNode getRoot() {  
        return root;  
    }

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Map<String, Object>> getProcessPlan() {
		return processPlan;
	}

	public void setProcessPlan(List<Map<String, Object>> processPlan) {
		this.processPlan = processPlan;
	}

	public String getSelectProcessPlan() {
		return selectProcessPlan;
	}

	public void setSelectProcessPlan(String selectProcessPlan) {
		this.selectProcessPlan = selectProcessPlan;
	}

	public PartProcessDataModel getMediumPartModel() {
		return mediumPartModel;
	}

	public void setMediumPartModel(PartProcessDataModel mediumPartModel) {
		this.mediumPartModel = mediumPartModel;
	}

	public TProcessInfo[] getSelectedRowPart() {
		return selectedRowPart;
	}

	public void setSelectedRowPart(TProcessInfo[] selectedRowPart) {
		this.selectedRowPart = selectedRowPart;
	}

	public TProcessInfo getAddPart() {
		return addPart;
	}

	public void setAddPart(TProcessInfo addPart) {
		this.addPart = addPart;
	}

	public List<Map<String, Object>> getCblist() {
		return cblist;
	}

	public void setCblist(List<Map<String, Object>> cblist) {
		this.cblist = cblist;
	}

	public List<Map<String, Object>> getWllist() {
		return wllist;
	}

	public void setWllist(List<Map<String, Object>> wllist) {
		this.wllist = wllist;
	}

	public List<Map<String, Object>> getJjlist() {
		return jjlist;
	}

	public void setJjlist(List<Map<String, Object>> jjlist) {
		this.jjlist = jjlist;
	}

	public List<Map<String, Object>> getDjlist() {
		return djlist;
	}

	public void setDjlist(List<Map<String, Object>> djlist) {
		this.djlist = djlist;
	}

	public List<Map<String, Object>> getJtlist() {
		return jtlist;
	}

	public void setJtlist(List<Map<String, Object>> jtlist) {
		this.jtlist = jtlist;
	}

	public String getSelectProcess() {
		return selectProcess;
	}

	public void setSelectProcess(String selectProcess) {
		this.selectProcess = selectProcess;
	}

	public List<TProcessInfo> getGyxllist() {
		return gyxllist;
	}

	public void setGyxllist(List<TProcessInfo> gyxllist) {
		this.gyxllist = gyxllist;
	}

	public TProcessplanInfoModel getProcessPlanModel() {
		return processPlanModel;
	}

	public void setProcessPlanModel(TProcessplanInfoModel processPlanModel) {
		this.processPlanModel = processPlanModel;
	}

	public TProcessplanInfo[] getSelectedRowPlan() {
		return selectedRowPlan;
	}

	public void setSelectedRowPlan(TProcessplanInfo[] selectedRowPlan) {
		this.selectedRowPlan = selectedRowPlan;
	}

	public String getProcessPlanSearch() {
		return processPlanSearch;
	}

	public void setProcessPlanSearch(String processPlanSearch) {
		this.processPlanSearch = processPlanSearch;
	}

	public TProcessplanInfo getAddPlal() {
		return addPlal;
	}

	public void setAddPlal(TProcessplanInfo addPlal) {
		this.addPlal = addPlal;
	}

	public Map<String, String> getPath() {
		return path;
	}

	public void setPath(Map<String, String> path) {
		this.path = path;
	}
	public List<Map<String,Object>> getZjlist() {
		return zjlist;
	}
	public void setZjlist(List<Map<String,Object>> zjlist) {
		this.zjlist = zjlist;
	}
	
}
