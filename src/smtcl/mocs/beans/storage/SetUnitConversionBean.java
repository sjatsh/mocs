package smtcl.mocs.beans.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import smtcl.mocs.model.TUnitConversionModel;
import smtcl.mocs.services.storage.ISetUnitServcie;
@ManagedBean(name = "SUConversionBean")
@ViewScoped
public class SetUnitConversionBean implements Serializable {
	/**
	 * 计量单位的基本信息接口
	 */
	private  ISetUnitServcie setunitService=(ISetUnitServcie) ServiceFactory.getBean("setunitService");
	/**
	 * 换算标准集合
	 */
	private List<Map<String,Object>> UConversionlist=new ArrayList<Map<String,Object>>();
	
	/**
	 * 换算分类内集合
	 */
	private List<Map<String,Object>> UConversionlist1=new ArrayList<Map<String,Object>>();
	

	/**
	 * 换算分类间集合
	 */
	private List<Map<String,Object>> UConversionlist2=new ArrayList<Map<String,Object>>();
	
	/**
	 * 物料编码集合
	 */
	private List<Map<String,Object>>  matenoList;
	private List<Map<String,Object>> unitnameList;
	private List<Map<String,Object>> unitnameList1;
	/**
	 * dataTable数据实现了多行选中
	 */
	
	private TUnitConversionModel medTUnitConversionModel;
	private TUnitConversionModel medTUnitConversionModel1;
	private TUnitConversionModel medTUnitConversionModel2;
	
	/**
	 * 单位类别名称下拉列表
	 */
	private List unitTypeList;
	
    /**
     * 单位类别名称
     */
	private String unittypename;
	private String unittypename1;
	private String unittypename2;
	private String unittypename3;
	
	/**
	 * 单位名称下拉列表
	 */
	
	private List unitNameList;
	private List unitNameList1;
	
	/**
	 * 单位名称
	 */
	private String unitname;
	private String unitname1;
	
	
	
	/**
	 * 基准单位
	 */
	private String unit;
	private String unit1;
	private String unit2;
	private String unit3;
	/**
	 * 换算
	 */
	private Double ratio;
	private Double ratio1;
	private Double ratio2;
	/**
	 * 换算类型
	 */
	private String conversionType;
	
	/**
	 *物料编码
	 */
	private String no;
	private String no1;
	private TreeNode noroot;//物料编码显示
	private TreeNode noroot1;//物料编码显示
	private TreeNode noselectroot;//物料编码选中
	private TreeNode noselectroot1;//物料编码选中
	/**
	 * 物料说明
	 */
	private String matename;
	private String matename1;
	
	
	/**
	 * dataTable选中行
	 */
	private Map[] selectuncon;
	private Map[] selectuncon1;
	private Map[] selectuncon2;
	/**
	 * 判断是否选中的行
	 */
	private String selected;
	
	/**
	 * 节点Id
	 */
	private String nodeid;
	
	/**
	 * 构造方法
	 */
	public SetUnitConversionBean(){
		
		
	}
	
	/**
	 * 获取标准页得数据
	 */
	public void getBz(){
		searchList();
	}
	
	/**
	 * datatable中的数据的查询方法
	 */
	
	public void searchList(){
		
		String unitname2=null;
		String conunitId=null;
		String Id=null;
		UConversionlist=setunitService.getunitConversionInfo(nodeid);
		for(Map<String,Object> mm:UConversionlist){
			unittypename=mm.get("unittypename").toString();
			conunitId=mm.get("conunitId").toString();
			unitnameList=setunitService.getConversionUnit(unittypename,nodeid);
			for(Map<String,Object> map:unitnameList){
				Id=map.get("Id").toString();
				if(conunitId.equals(Id)){
					unitname2=map.get("unitname").toString();
					mm.put("unitname",unitname2);
				}
				
			 }
			mm.put("unitnameList",unitnameList);
		}
		
		medTUnitConversionModel=new TUnitConversionModel(UConversionlist);
	}

	/**
     * 单位类别名称的下拉列表
     * 
     */
	public List getunitTypeList(){
		if (null == unitTypeList) {
			unitTypeList = new ArrayList();
		} else
			unitTypeList.clear();
		unitTypeList.add(new SelectItem(null, "请选择"));
		List mtModellist = setunitService.getunitTypeName(nodeid);
		for (int i = 0; i < mtModellist.size(); i++) {
			Map<String, Object> record = (Map<String, Object>) mtModellist
					.get(i);
			unitTypeList.add(new SelectItem(record.get("unittypename")));
		}
		return unitTypeList;
		
	}
	
	/**	
	 * 单位名称的下拉列表
	 */
	
	public List getunitNameList(){
	   if (null == unitNameList) {
		   unitNameList = new ArrayList();
		} else
			unitNameList.clear();
	   unitNameList.add(new SelectItem(null, "请选择"));
	   List mtModellist = setunitService.getConversionUnit(unittypename,nodeid);
			for (int j = 0; j < mtModellist.size(); j++) {
				Map<String, Object> record = (Map<String, Object>) mtModellist
						.get(j);
				unitNameList.add(new SelectItem(record.get("unitname")));
			}
			
	 return unitNameList;
		
	}
	

	public List getunitNameList1(){
	   if (null == unitNameList1) {
		   unitNameList1 = new ArrayList();
		} else
			unitNameList1.clear();
	   unitNameList1.add(new SelectItem(null, "请选择"));
	   List mtModellist = setunitService.getConversionUnit(unittypename1,nodeid);
			for (int j = 0; j < mtModellist.size(); j++) {
				Map<String, Object> record = (Map<String, Object>) mtModellist
						.get(j);
				unitNameList1.add(new SelectItem(record.get("unitname")));
			}
			
	 return unitNameList1;
		
	}
	
	
	/**
	 * 新增单位换算标准信息
	 */
	
	public void addTUnitConverInfo(){
		
		String tt=setunitService.addTUnitConversion(this);
		
		if(tt.equals("添加成功")){
			FacesMessage msg = new FacesMessage("单位换标准算添加","添加成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList();
		}else if(tt.equals("已存在")){
			FacesMessage msg = new FacesMessage("单位换算标准添加","数据已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("单位换算标准添加","添加失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		
	}
	/**
	 * 修改单位换算标准信息
	 */

	public void updateTUnitConversion(RowEditEvent event){
	    Map<String, Object> tuncon= (Map<String, Object>) event.getObject();
		String tt=setunitService.updateTUnitConversion(tuncon);
		if(tt.equals("修改成功")){
			FacesMessage msg = new FacesMessage("单位换算标准修改","修改成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList();
		}else if(tt.equals("与基准单位相同")){
			FacesMessage msg = new FacesMessage("单位换算标准修改","不能与基准单位相同！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("单位换算标准修改","修改失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
	}
	
	

	
	
	/**
	 * 取消
	 */
	public void onCancel(RowEditEvent event){
		
	}
	
	
	/**
	 * 选中
	 */
	public void onSelected(){
	    for(Map tt:selectuncon){
	    	selected=tt.get("Id").toString();
	    }
	}
	
	/**
	 * 删除单位换算标准信息
	 */
	public void deleteTUnitConversion(){
		 for(Map map:selectuncon){
			 setunitService.deleteTUnitConversion(map);
		 }
		 searchList();
	}
	/**
	 * 物料编码查询事件
	 */
	public void onkeyupNoList() {
		matenoList=setunitService.getMaterNo(nodeid, no);
		noroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:matenoList){
			TreeNode node0 = new DefaultTreeNode(mm, noroot);
		}
    }
	
	/**
	 * 物料编码查询事件
	 */
	public void onkeyupNoList1() {
		matenoList=setunitService.getMaterNo(nodeid, no1);
		noroot1 = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:matenoList){
			TreeNode node0 = new DefaultTreeNode(mm, noroot1);
		}
    }
	/**
	 * 物料编码选择事件
	 */
	public void OnTreeNodeSelect(NodeSelectEvent event){
		Map<String,Object> map=(Map<String,Object>)event.getTreeNode().getData();
	    no=map.get("no").toString();//获取物料编码
	    
		
	}
	
	/**
	 * 物料编码选择事件
	 */
	public void OnTreeNodeSelect1(NodeSelectEvent event){
		Map<String,Object> map=(Map<String,Object>)event.getTreeNode().getData();
	    no1=map.get("no").toString();
		
	}
	/**
	 * 获取得分类内数据
	 */
	public void getFln(){
		searchList1();
	}
	
	
	/**
	 * 获取单位分类内的数据
	 */
    public void searchList1(){
    	String unitname2=null;
		String conunitId=null;
		String Id=null;
		UConversionlist1=setunitService.getunitConclassifyInfo(nodeid);
		for(Map<String,Object> mm:UConversionlist1){
			unittypename1=mm.get("unittypename").toString();
			conunitId=mm.get("conunitId").toString();
			unitnameList1=setunitService.getConversionUnit(unittypename1,nodeid);
			for(Map<String,Object> map:unitnameList1){
				Id=map.get("Id").toString();
				if(conunitId.equals(Id)){
					unitname2=map.get("unitname").toString();
					mm.put("unitname",unitname2);
				}
				
			 }
			mm.put("unitnameList1",unitnameList1);
		}
		medTUnitConversionModel1=new TUnitConversionModel(UConversionlist1);
	}
	
    
    
    

	/**
	 * 新增单位换算分类内的信息
	 */
	
	public void addTunitConclassify(){
		
		String tt=setunitService.addTunitConclassify(this);
		
		if(tt.equals("添加成功")){
			FacesMessage msg = new FacesMessage("单位换算分类内添加","添加成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList1();
		}else if(tt.equals("已存在")){
			FacesMessage msg = new FacesMessage("单位换算分类内添加","数据已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("单位换算分类内添加","添加失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		
	}
	
	
	/**
	 * 修改单位分类内的信息
	 */
	public void updateTunitConclassify(RowEditEvent event){
		Map<String, Object> tuncon1= (Map<String, Object>) event.getObject();
		String tt=setunitService.updateTunitConclassify(tuncon1);
		if(tt.equals("修改成功")){
			FacesMessage msg = new FacesMessage("单位换算分类内修改","修改成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList1();
		}else if(tt.equals("与基准单位相同")){
			FacesMessage msg = new FacesMessage("单位换算分类修改","不能与基准单位相同！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("单位换算分类内修改","修改失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	/**
	 * 选中
	 */
	public void onSelected1(){
	    for(Map tt:selectuncon1){
	    	selected=tt.get("Id").toString();
	    }
	}
	
	/**
	 * 删除单位换算分类内的信息
	 */
	public void deleteTunitConclassify(){
		 for(Map map:selectuncon1){
			 setunitService.deleteTunitConclassify(map);
		 }
		 searchList1();
	}
	
	/**
	 * 获取得分类间数据
	 */
	public void getFlj(){
		searchList2();
	}
	
	/**
	 * 获取单位分类间的数据
	 */
    public void searchList2(){
		
		UConversionlist2=setunitService.getunitConsortInfo(nodeid);
		medTUnitConversionModel2=new TUnitConversionModel(UConversionlist2);
	}
	
	/**
	 * 新增分类间的信息
	 */
	public void addUnitConsortInfo(){
		
        String tt=setunitService.addUnitConsortInfo(this);
		if(tt.equals("添加成功")){
			FacesMessage msg = new FacesMessage("单位换算分类间添加","添加成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList2();
		}else if(tt.equals("已存在")){
			FacesMessage msg = new FacesMessage("单位换算分类间添加","数据已存在！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}else{
			FacesMessage msg = new FacesMessage("单位换算分类间添加","添加失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		
	}
	/**
	 * 修改单位分类间的信息
	 */
	public void updateUnitConsortInfo(RowEditEvent event){
		Map<String, Object> tuncon2= (Map<String, Object>) event.getObject();
		String tt=setunitService.updateUnitConsortInfo(tuncon2);
		if(tt.equals("修改成功")){
			FacesMessage msg = new FacesMessage("单位换算分类间修改","修改成功！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			searchList2();
		}else{
			FacesMessage msg = new FacesMessage("单位换算分类间修改","修改失败！");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	/**
	 * 选中
	 */
	public void onSelected2(){
	    for(Map tt:selectuncon2){
	    	selected=tt.get("Id").toString();
	    }
	}
	
	/**
	 * 删除单位换算分类间的信息
	 */
	public void deleteUnitConsortInfo(){
		 for(Map map:selectuncon2){
			 setunitService.deleteUnitConsortInfo(map);
		 }
		 searchList2();
	}
	
	
	
	/************** set,get方法 ***************************/
	

	public String getUnittypename() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		nodeid=(String)session.getAttribute("nodeid2");
		matenoList=setunitService.getMaterNo(nodeid, null);
		noroot = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:matenoList){
			TreeNode node0 = new DefaultTreeNode(mm, noroot);
		}
		noroot1 = new DefaultTreeNode("Root", null);
		for(Map<String,Object> mm:matenoList){
			TreeNode node0 = new DefaultTreeNode(mm, noroot1);
		}
		return unittypename;
	}

	public void setUnittypename(String unittypename) {
		this.unittypename = unittypename;
	}

	public String getUnit() {
		List<Map<String,Object>>lst=setunitService.getunitName(unittypename,nodeid);
		for(Map<String,Object> map:lst){
			unit=map.get("unit").toString();
		}
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public String getConversionType() {
		return conversionType;
	}

	public void setConversionType(String conversionType) {
		this.conversionType = conversionType;
	}

	public TUnitConversionModel getMedTUnitConversionModel() {
		return medTUnitConversionModel;
	}

	public void setMedTUnitConversionModel(
			TUnitConversionModel medTUnitConversionModel) {
		this.medTUnitConversionModel = medTUnitConversionModel;
	}

	

	public Map[] getSelectuncon() {
		return selectuncon;
	}

	public void setSelectuncon(Map[] selectuncon) {
		this.selectuncon = selectuncon;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getNodeid() {
		
		return nodeid;
	}

	public List<Map<String, Object>> getUConversionlist() {
		return UConversionlist;
	}

	public void setUConversionlist(List<Map<String, Object>> uConversionlist) {
		UConversionlist = uConversionlist;
	}

	public List<Map<String, Object>> getUConversionlist1() {
		return UConversionlist1;
	}

	public void setUConversionlist1(List<Map<String, Object>> uConversionlist1) {
		UConversionlist1 = uConversionlist1;
	}

	public TUnitConversionModel getMedTUnitConversionModel1() {
		return medTUnitConversionModel1;
	}

	public void setMedTUnitConversionModel1(
			TUnitConversionModel medTUnitConversionModel1) {
		this.medTUnitConversionModel1 = medTUnitConversionModel1;
	}

	public String getNo() {
		
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getMatename() {
		List<Map<String,Object>>lst=setunitService.getMaterName(nodeid, no);
		for(Map<String,Object> map1:lst){
			matename=map1.get("matename").toString();
		}
		return matename;
	}

	public void setMatename(String matename) {
		this.matename = matename;
	}

	public String getUnittypename1() {
		return unittypename1;
	}

	public void setUnittypename1(String unittypename1) {
		this.unittypename1 = unittypename1;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getUnitname1() {
		return unitname1;
	}

	public void setUnitname1(String unitname1) {
		this.unitname1 = unitname1;
	}

	public String getUnit1() {
		List<Map<String,Object>>lst=setunitService.getunitName(unittypename1,nodeid);
		for(Map<String,Object> map:lst){
			unit1=map.get("unit").toString();
		}
		return unit1;
	}

	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}

	public Double getRatio1() {
		return ratio1;
	}

	public void setRatio1(Double ratio1) {
		this.ratio1 = ratio1;
	}

	public List<Map<String, Object>> getUConversionlist2() {
		return UConversionlist2;
	}

	public void setUConversionlist2(List<Map<String, Object>> uConversionlist2) {
		UConversionlist2 = uConversionlist2;
	}

	public TUnitConversionModel getMedTUnitConversionModel2() {
		return medTUnitConversionModel2;
	}

	public void setMedTUnitConversionModel2(
			TUnitConversionModel medTUnitConversionModel2) {
		this.medTUnitConversionModel2 = medTUnitConversionModel2;
	}

	public String getNo1() {
		return no1;
	}

	public void setNo1(String no1) {
		this.no1 = no1;
	}

	public String getMatename1() {
		List<Map<String,Object>>lst=setunitService.getMaterName(nodeid, no1);
		for(Map<String,Object> map:lst){
			matename1=map.get("matename").toString();
		}
		
		return matename1;
	}

	public void setMatename1(String matename1) {
		this.matename1 = matename1;
	}

	public Double getRatio2() {
		return ratio2;
	}

	public void setRatio2(Double ratio2) {
		this.ratio2 = ratio2;
	}

	public String getUnittypename2() {
		return unittypename2;
	}

	public void setUnittypename2(String unittypename2) {
		this.unittypename2 = unittypename2;
	}

	public String getUnittypename3() {
		return unittypename3;
	}

	public void setUnittypename3(String unittypename3) {
		this.unittypename3 = unittypename3;
	}

	public String getUnit2() {
		List<Map<String,Object>>lst=setunitService.getunitName(unittypename2,nodeid);
		for(Map<String,Object> map:lst){
			unit2=map.get("unit").toString();
		}
		return unit2;
	}

	public void setUnit2(String unit2) {
		this.unit2 = unit2;
	}

	public String getUnit3() {
		List<Map<String,Object>>lst=setunitService.getunitName(unittypename3,nodeid);
		for(Map<String,Object> map:lst){
			unit3=map.get("unit").toString();
		}
		return unit3;
	}

	public void setUnit3(String unit3) {
		this.unit3 = unit3;
	}

	public Map[] getSelectuncon1() {
		return selectuncon1;
	}

	public void setSelectuncon1(Map[] selectuncon1) {
		this.selectuncon1 = selectuncon1;
	}

	public Map[] getSelectuncon2() {
		return selectuncon2;
	}

	public void setSelectuncon2(Map[] selectuncon2) {
		this.selectuncon2 = selectuncon2;
	}

	public List<Map<String, Object>> getMatenoList() {
		return matenoList;
	}

	public void setMatenoList(List<Map<String, Object>> matenoList) {
		this.matenoList = matenoList;
	}

	public TreeNode getNoroot() {
		return noroot;
	}

	public void setNoroot(TreeNode noroot) {
		this.noroot = noroot;
	}

	public TreeNode getNoselectroot() {
		return noselectroot;
	}

	public void setNoselectroot(TreeNode noselectroot) {
		this.noselectroot = noselectroot;
	}

	public TreeNode getNoroot1() {
		return noroot1;
	}

	public void setNoroot1(TreeNode noroot1) {
		this.noroot1 = noroot1;
	}

	public TreeNode getNoselectroot1() {
		return noselectroot1;
	}

	public void setNoselectroot1(TreeNode noselectroot1) {
		this.noselectroot1 = noselectroot1;
	}

	public List<Map<String, Object>> getUnitnameList() {
		return unitnameList;
	}

	public void setUnitnameList(List<Map<String, Object>> unitnameList) {
		this.unitnameList = unitnameList;
	}

	public List<Map<String, Object>> getUnitnameList1() {
		return unitnameList1;
	}

	public void setUnitnameList1(List<Map<String, Object>> unitnameList1) {
		this.unitnameList1 = unitnameList1;
	}

	
	
	
	
	
}
