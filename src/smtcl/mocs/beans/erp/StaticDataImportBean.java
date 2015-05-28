 package smtcl.mocs.beans.erp;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.dreamwork.persistence.ServiceFactory;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import smtcl.mocs.model.ErpMaterialDataModel;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.erp.IERPSerice;


/**
 * 2014-07-01
 * @author liguoqiang
 *
 */
@ManagedBean(name="staticDataImportBean")
@ViewScoped
public class StaticDataImportBean {
	private TreeNode wisRoot; //wis树
	private TreeNode erpRoot;//erp树
	private TreeNode selectedWisRoot;
	private TreeNode[] selectedErpRoot;
	private IERPSerice erpCommonService = (IERPSerice)ServiceFactory.getBean("erpCommonService");//注入另外一个数据库的service
	private IPartService wisPartService=(IPartService)ServiceFactory.getBean("partService");
	private List<Map<String,Object>> partlist;//零件下拉框列表
	private String selectedPartNo;//当前选中零件编号
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	public  String nodeid="8a8abc8d44d361fd0144d3c76dd60001";
	private String selectedData="0";//设置数据查询条件
	private String materailNo;//查询物料编码
	private List<Map<String,Object>> erpMaterailList;//erp物料左表关于物料
	private ErpMaterialDataModel erpMaterail;
	private Map<String,Object>[] selectErpMaterail;//erp选中的物料关于物料
	private List<Map<String,Object>> wisMaterailList;//生成wis物料右表
	private List<Map<String,Object>> erpPartList;//erp物料左表 关于零件
	private ErpMaterialDataModel erpPart;
	private List<Map<String,Object>> wisPartList;//生成wis零件右表
	private Map<String,Object>[] selectErpPart;//erp选中的物料关于零件
	private String partlNo;//查询零件编号
	
	
	public StaticDataImportBean(){
		partlist=erpCommonService.getErpPartTypeList(null, null, null);//erp查询零件
		selectOnChange();
		selectPartOnChange();
	}
	
	//	加载对比树
	public void LoadTree(){
		wisRoot =newRootChildren("test","Root","none", null);//wis创建顶级节点，该节点不做显示
		erpRoot =newRootChildren("test","Root","none", null);//erp创建顶级节点，该节点不做显示
		
		TreeNode wispartRoot =newRootChildren("test","零件","none", wisRoot);//wis创建零件节点
		TreeNode erppartRoot =newRootChildren("test","零件(物料)","none", erpRoot);//wis创建零件节点
		//List<Map<String,Object>> wisplist=wisPartService.getTPartTypeInfo(nodeid, selectedPartNo, startTime, endTime);//wis查询零件
		List<Map<String,Object>> erpplist=erpCommonService.getErpPartTypeList(startTime, endTime, selectedPartNo);//erp查询零件
		for(Map<String,Object> erpPartMap:erpplist){
			
			//wis查询单个零件零件
			List<Map<String,Object>> wisplist=wisPartService.getTPartTypeInfo(nodeid, erpPartMap.get("ITEMCODE").toString(), null, null);
			if(null!=wisplist&&wisplist.size()>0){
				//erp创建零件子节点
				TreeNode erprealpartRoot =newRootChildren("test",erpPartMap.get("ITEMCODE").toString(),"none", erppartRoot);
				Map<String,Object> wisPartMap=wisplist.get(0);
				//wis创建零件子节点
				TreeNode wisrealpartRoot =newRootChildren("test",wisPartMap.get("Name").toString(),"none", wispartRoot);
				//erp 查找工序
				List<Map<String,Object>> erpProcesslist=erpCommonService.getErpProcess(erpPartMap.get("ID").toString());
				//wis查询工艺方案
				List<Map<String,Object>> wisprocesslist=wisPartService.getTProcessplanInfo(wisPartMap.get("Name").toString());
				//wis创建零件工序节点
				TreeNode erppartProcessRoot =newRootChildren("test","零件工序","none", erprealpartRoot);
				if(null!=erpProcesslist&&erpProcesslist.size()>0){
					if(null!=wisprocesslist&&wisprocesslist.size()>0){
						Map<String,Object> wisprocessPlanMap=wisprocesslist.get(0);
						//wis创建工艺方案节点
						TreeNode wispartProcessPlanRoot =newRootChildren("test",wisprocessPlanMap.get("processPlanName").toString(),"none",
								wisrealpartRoot);
						TreeNode wispartProcessRoot =newRootChildren("test","零件工序","none", wispartProcessPlanRoot);//wis创建零件工序节点
						for(Map<String,Object> erpprocessMap:erpProcesslist){
							Map<String,Object> wisprocessMapvalue = null;
							//为中间库的工序开始匹配wis系统的工序
							for(Map<String,Object> wisprocessMap:wisprocesslist){
								if(erpprocessMap.get("processNum").toString().equals(wisprocessMap.get("processOrder").toString())){
									wisprocessMapvalue=wisprocessMap;
									break;
								}
							}
							if(null!=wisprocessMapvalue){
								if(erpprocessMap.get("processName").toString().equals(wisprocessMapvalue.get("processName").toString())){
									TreeNode erpProcessRoot =newRootChildren(erpPartMap.get("ITEMCODE").toString()+","+erpprocessMap.get("processNum").toString(),
											erpprocessMap.get("processNum").toString()+"--"+
											erpprocessMap.get("processName").toString(),"none", erppartProcessRoot);//erp创建工序节点子节点
											erpProcessRoot.setExpanded(false);
									TreeNode wisProcessRoot =newRootChildren("test",wisprocessMapvalue.get("processOrder").toString()+"--"+
											wisprocessMapvalue.get("processName").toString(),"none", wispartProcessRoot);//wis创建零件工序节点子节点
											wisProcessRoot.setExpanded(false);
									/*-------------------------------------wis与erp物料比对 none start-----------------------------------------*/
									TreeNode wispartProcessWLRoot =newRootChildren("test","工序物料","none", wisProcessRoot);//wis创建工序物料节点
									TreeNode erppartProcessWLRoot =newRootChildren("test","工序BOM","none", erpProcessRoot);//erp创建工序物料节点
									//wis查询工序物料
									List<Map<String,Object>> processWLlist=wisPartService.getProcessWL(wisprocessMapvalue.get("processId").toString());
									//erp查询工序物料
									List<Map<String,Object>> erpMateraillist=erpCommonService.getErpMaterail(erpprocessMap.get("partId").toString(),erpprocessMap.get("processNum").toString());
									if(null!=erpMateraillist&&erpMateraillist.size()>0){
										for(Map<String,Object> erpMaterailMap:erpMateraillist){
											Map<String,Object> processWlMapValue=null;
											//wis 和 erp 匹配物料
											for(Map<String,Object> processWlMap:processWLlist){
												//System.out.println(processWlMap.get("No").toString()+"\n"+erpMaterailMap.get("itemCode").toString());
												if(processWlMap.get("No").toString().equals(erpMaterailMap.get("componentCode").toString())){
													processWlMapValue=processWlMap;
													break;
												}
											}
											if(null!=processWlMapValue){
												//完全比配
												if(null==erpMaterailMap.get("MaterailCost")){
													erpMaterailMap.put("MaterailCost", "0.0");
												}
												if(processWlMapValue.get("Name").toString().equals(erpMaterailMap.get("componentDesc").toString())&&
												   processWlMapValue.get("RequirementNum").toString().equals(erpMaterailMap.get("componentQuantity").toString())&&
												   Double.parseDouble(processWlMapValue.get("Price").toString())==Double.parseDouble(erpMaterailMap.get("MaterailCost").toString())){
													//wis创建工序物料子节点
													newRootChildren("test",processWlMapValue.get("No")+"("+processWlMapValue.get("Name")+")--"
															+processWlMapValue.get("RequirementNum")+"(￥"+processWlMapValue.get("Price")+")","none",
															wispartProcessWLRoot);
													//erp创建工序物料节点子节点
													newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
															+erpMaterailMap.get("componentQuantity")+"(￥"+erpMaterailMap.get("MaterailCost")+")","none", 
															erppartProcessWLRoot);
												}else{
													//不完全匹配
													//wis创建工序物料子节点
													newRootChildren("test",processWlMapValue.get("No")+"("+processWlMapValue.get("Name")+")--"
															+processWlMapValue.get("RequirementNum")+"(￥"+processWlMapValue.get("Price")+")","yellow",
															wispartProcessWLRoot);
													//erp创建工序物料节点子节点
													newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
															+erpMaterailMap.get("componentQuantity")+"(￥"+erpMaterailMap.get("MaterailCost")+")","yellow", 
															erppartProcessWLRoot);
													//修改erp父节点颜色
													updateNode(erppartProcessWLRoot, "yellow", null, null);
													updateNode(erpProcessRoot, "yellow", null, null);
													//修改wis父节点颜色
													updateNode(wispartProcessWLRoot, "yellow", null, null);
													updateNode(wisProcessRoot, "yellow", null, null);
												}
											}else{
												//erp创建工序物料节点子节点
												newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
														+erpMaterailMap.get("componentQuantity")+"(￥"+erpMaterailMap.get("MaterailCost")+")","green", 
														erppartProcessWLRoot);
												//修改erp父节点颜色
												updateNode(erppartProcessWLRoot, "yellow", null, null);
												updateNode(erpProcessRoot, "yellow", null, null);
												//修改wis父节点颜色
												updateNode(wispartProcessWLRoot, "yellow", null, null);
												updateNode(wisProcessRoot, "yellow", null, null);
											}
											
										}
										
									}
									/*-------------------------------------wis与erp物料比对 none end-----------------------------------------*/
									
									/*-------------------------------------wis与erp机台比对 none start-----------------------------------------*/
									TreeNode wispartProcessJTRoot =newRootChildren("test","工序机台","none", wisProcessRoot);//wis创建机台节点
									TreeNode erppartProcessJTRoot =newRootChildren("test","工序资源","none", erpProcessRoot);//erp创建机台节点
									//erp 查找机台
									List<Map<String,Object>> erpJTlist=erpCommonService.getErpmachine(erpPartMap.get("ID").toString(), 
											erpprocessMap.get("processNum").toString());
									//wis 查找机台
									List<Map<String,Object>> processJTlist=wisPartService.getProcessJTData(wisprocessMapvalue.get("processId").toString());
									if(null!=erpJTlist&&erpJTlist.size()>0){
										for(Map<String,Object> erpJTMap:erpJTlist){
											Map<String,Object> processJTMapValue=null;
											for(Map<String,Object> processJTMap:processJTlist){
												if(processJTMap.get("typecode").toString().equals(erpJTMap.get("RESOURCECODE").toString())){
													processJTMapValue=processJTMap;
												}
											}
											if(null!=processJTMapValue){
												//完全匹配
												if((Double.parseDouble(erpJTMap.get("USAGERATEORAMOUNT").toString().replace(",", ""))*60)==
														Double.parseDouble(processJTMapValue.get("theoryWorktime").toString().replace(",", ""))){
													//wis创建机台子节点
													newRootChildren("test",""+processJTMapValue.get("typecode")+"("+processJTMapValue.get("theoryWorktime")+")",
															"none", wispartProcessJTRoot);
													//erp创建机台子节点
													newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","none", 
															erppartProcessJTRoot);
												}else{
												//不完全匹配
													//wis创建机台子节点
													newRootChildren("test",""+processJTMapValue.get("typecode")+"("+processJTMapValue.get("processingTime")+")",
															"yellow", wispartProcessJTRoot);
													//erp创建机台子节点
													newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","yellow",
															erppartProcessJTRoot);
													//修改erp父节点颜色
													updateNode(erppartProcessJTRoot, "yellow", null, null);
													updateNode(erpProcessRoot, "yellow", null, null);
													//修改wis父节点颜色
													updateNode(wispartProcessJTRoot, "yellow", null, null);
													updateNode(wisProcessRoot, "yellow", null, null);
												}
												
											}else if(null!=processJTlist&&processJTlist.size()>0){
												//erp创建机台子节点
												newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")",
														"yellow", erppartProcessJTRoot);
												processJTMapValue=processJTlist.get(0);
												//wis创建机台子节点
												newRootChildren("test",""+processJTMapValue.get("typecode")+"("+processJTMapValue.get("theoryWorktime")+")",
														"none", wispartProcessJTRoot);
												//修改erp父节点颜色
												updateNode(erppartProcessJTRoot, "yellow", null, null);
												updateNode(erpProcessRoot, "yellow", null, null);
												//修改wis父节点颜色
												updateNode(wispartProcessJTRoot, "yellow", null, null);
												updateNode(wisProcessRoot, "yellow", null, null);
											}
											else{
												//erp创建机台子节点
												newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")",
														"green", erppartProcessJTRoot);

												//修改erp父节点颜色
												updateNode(erppartProcessJTRoot, "yellow", null, null);
												updateNode(erpProcessRoot, "yellow", null, null);
												//修改wis父节点颜色
												updateNode(wispartProcessJTRoot, "yellow", null, null);
												updateNode(wisProcessRoot, "yellow", null, null);
											}
											
										}
									}
									/*-------------------------------------wis与erp机台比对 none end-----------------------------------------*/
									
									/*-------------------------------------wis与erp成本比对 none start-----------------------------------------*/
									TreeNode wispartProcessCBRoot =newRootChildren("test","成本与折旧","none", wisProcessRoot);	//wis创建成本节点
									TreeNode erppartProcessCBRoot =newRootChildren("test","成本与折旧","none", erpProcessRoot);	//erp创建成本节点
									//erp成本数据
									Map<String,Object> erpCBMap=erpCommonService.getErpCost(erpPartMap.get("ID").toString(), 
											erpprocessMap.get("processNum").toString());
									//wis成本数据
									List<Map<String,Object>> processCBlist=wisPartService.getCBData(wisprocessMapvalue.get("processId").toString());
									//erp创建成本节点子节点
									if(null!=processCBlist&&processCBlist.size()>0){
										Map<String,Object> processCBMap=processCBlist.get(0);
										//处理成本每个数据的比较
										HandleCB(processCBMap, erpCBMap, wispartProcessCBRoot, erppartProcessCBRoot,erpProcessRoot, wisProcessRoot);
									}else{
										//创建erp成本子节点
										newRootChildren("test","主料成本--"+erpCBMap.get("zl"),"green", erppartProcessCBRoot);
										newRootChildren("test","辅料成本--"+erpCBMap.get("fl"),"green", erppartProcessCBRoot);
										newRootChildren("test","人员成本--"+erpCBMap.get("ry"),"green", erppartProcessCBRoot);
										newRootChildren("test","能源成本--"+erpCBMap.get("ny"),"green", erppartProcessCBRoot);
										newRootChildren("test","设备折旧--"+erpCBMap.get("zj"),"green", erppartProcessCBRoot);
										newRootChildren("test","资源消耗--"+erpCBMap.get("zy"),"green", erppartProcessCBRoot);
										//修改erp父节点颜色
										updateNode(erppartProcessCBRoot, "yellow", null, null);
										updateNode(erpProcessRoot, "yellow", null, null);
										//修改wis父节点颜色
										updateNode(wispartProcessCBRoot, "yellow", null, null);
										updateNode(wisProcessRoot, "yellow", null, null);
									}
									/*-------------------------------------wis与erp成本比对 none end-----------------------------------------*/
								}else{
									TreeNode erpProcessRoot =newRootChildren(erpPartMap.get("ITEMCODE").toString()+","+erpprocessMap.get("processNum").toString(),
											erpprocessMap.get("processNum").toString()+"--"+
											erpprocessMap.get("processName").toString(),"yellow", erppartProcessRoot);//erp创建工序节点子节点
											erpProcessRoot.setExpanded(false);
									TreeNode wisProcessRoot =newRootChildren("test",wisprocessMapvalue.get("processOrder").toString()+"--"+
											wisprocessMapvalue.get("processName").toString(),"yellow", wispartProcessRoot);//wis创建零件工序节点子节点
											wisProcessRoot.setExpanded(false);
									/*-------------------------------------wis yellow start-----------------------------------------*/
									//wis创建工序物料节点
									TreeNode wispartProcessWLRoot =newRootChildren("test","工序物料","yellow", wisProcessRoot);
									//wis查询工序物料
									List<Map<String,Object>> processWLlist=wisPartService.getProcessWL(wisprocessMapvalue.get("processId").toString());
									if(null!=processWLlist&&processWLlist.size()>0){
										for(Map<String,Object> processWlMap:processWLlist){
											//wis创建工序物料子节点
											newRootChildren("test",processWlMap.get("No")+"--"+processWlMap.get("Name")+"--"
													+processWlMap.get("RequirementNum")+"--"+processWlMap.get("Price"),"yellow", wispartProcessWLRoot);
										}
									}
									//wis创建机台节点
									TreeNode wispartProcessJTRoot =newRootChildren("test","工序机台","yellow", wisProcessRoot);
									List<Map<String,Object>> processJTlist=wisPartService.getProcessJTData(wisprocessMapvalue.get("processId").toString());
									if(null!=processJTlist&&processJTlist.size()>0){
										for(Map<String,Object> processJTMap:processJTlist){
											//wis创建机台子节点
											newRootChildren("test",""+processJTMap.get("typecode")+"("+processJTMap.get("processingTime")+")",
													"yellow", wispartProcessJTRoot);
										}
									}
									//wis创建成本节点
									TreeNode wispartProcessCBRoot =newRootChildren("test","成本与折旧","yellow", wisProcessRoot);	
									//wis查询成本属性
									List<Map<String,Object>> processCBlist=wisPartService.getCBData(wisprocessMapvalue.get("processId").toString());
									if(null!=processCBlist&&processCBlist.size()>0){
										for(Map<String,Object> processCBMap:processCBlist){
											//wis创建成本子节点
											newRootChildren("test","主料成本--"+processCBMap.get("mainMaterialCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","辅料成本--"+processCBMap.get("subsidiaryMaterialCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","人员成本--"+processCBMap.get("peopleCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","能源成本--"+processCBMap.get("energyCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","设备折旧--"+processCBMap.get("deviceCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","资源消耗--"+processCBMap.get("resourceCost"),"yellow", wispartProcessCBRoot);
										}
									}
									/*-------------------------------------wis yellow end-----------------------------------------*/
									
									/*-------------------------------------erp yellow start-----------------------------------------*/		
									TreeNode erppartProcessWLRoot =newRootChildren("test","工序物料","yellow", erpProcessRoot);//erp创建工序物料节点
									List<Map<String,Object>> erpMateraillist=erpCommonService.getErpMaterail(erpprocessMap.get("partId").toString(),erpprocessMap.get("processNum").toString());
									if(null!=erpMateraillist&&erpMateraillist.size()>0){
										for(Map<String,Object> erpMaterailMap:erpMateraillist){
											//erp创建工序物料节点子节点
											newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
													+erpMaterailMap.get("componentQuantity")+"(￥"+erpMaterailMap.get("MaterailCost")+")","green", 
													erppartProcessWLRoot);
										}
									}
									//erp创建机台节点
									TreeNode erppartProcessJTRoot =newRootChildren("test","工序机台","yellow", erpProcessRoot);
									//erp 查找机台
									List<Map<String,Object>> erpJTlist=erpCommonService.getErpmachine(erpPartMap.get("ID").toString(), 
											erpprocessMap.get("processNum").toString());
									if(null!=erpJTlist&&erpJTlist.size()>0){
										for(Map<String,Object> erpJTMap:erpJTlist){
											newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","yellow", erppartProcessJTRoot);
										}
									}
									//erp创建成本节点
									TreeNode erppartProcessCBRoot =newRootChildren("test","成本与折旧","yellow", erpProcessRoot);
									//erp查找成熟数据
									Map<String,Object> erpCBMap=erpCommonService.getErpCost(erpPartMap.get("ID").toString(), 
											erpprocessMap.get("processNum").toString());
									//erp创建成本节点子节点
									newRootChildren("test","主料成本--"+erpCBMap.get("zl"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","辅料成本--"+erpCBMap.get("fl"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","人员成本--"+erpCBMap.get("ry"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","能源成本--"+erpCBMap.get("ny"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","设备折旧--"+erpCBMap.get("zj"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","资源消耗--"+erpCBMap.get("zy"),"yellow", erppartProcessCBRoot);
								/*-------------------------------------erp yellow end-----------------------------------------*/
								}
							}else{
								/*-------------------------------------erp green start-----------------------------------------*/
								TreeNode erpProcessRoot =newRootChildren(erpPartMap.get("ITEMCODE").toString()+","+erpprocessMap.get("processNum").toString(),
										erpprocessMap.get("processNum").toString()+"--"+
										erpprocessMap.get("processName").toString(),"green", erppartProcessRoot);//erp创建工序节点子节点
										erpProcessRoot.setExpanded(false);
								//erp创建工序物料节点
								TreeNode erppartProcessWLRoot =newRootChildren("test","工序物料","green", erpProcessRoot);
								//erp查找工序物料
								List<Map<String,Object>> erpMateraillist=erpCommonService.getErpMaterail(erpprocessMap.get("partId").toString(),erpprocessMap.get("processNum").toString());
								if(null!=erpMateraillist&&erpMateraillist.size()>0){
									for(Map<String,Object> erpMaterailMap:erpMateraillist){
										//erp创建工序物料节点子节点
										newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
										+erpMaterailMap.get("componentQuantity")+"(￥"+erpMaterailMap.get("MaterailCost")+")","green", 
										erppartProcessWLRoot);
									}
									
								}
								//erp创建机台节点
								TreeNode erppartProcessJTRoot =newRootChildren("test","工序机台","green", erpProcessRoot);
								//erp 查找机台
								List<Map<String,Object>> erpJTlist=erpCommonService.getErpmachine(erpPartMap.get("ID").toString(), 
										erpprocessMap.get("processNum").toString());
								if(null!=erpJTlist&&erpJTlist.size()>0){
									for(Map<String,Object> erpJTMap:erpJTlist){
										newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","green", erppartProcessJTRoot);
									}
								}
								//erp创建成本节点
								TreeNode erppartProcessCBRoot =newRootChildren("test","成本与折旧","green", erpProcessRoot);	
								//erp查找成本数据
								Map<String,Object> erpCBMap=erpCommonService.getErpCost(erpPartMap.get("ID").toString(), 
										erpprocessMap.get("processNum").toString());
								//erp创建成本节点子节点
								newRootChildren("test","主料成本--"+erpCBMap.get("zl"),"green", erppartProcessCBRoot);
								newRootChildren("test","辅料成本--"+erpCBMap.get("fl"),"green", erppartProcessCBRoot);
								newRootChildren("test","人员成本--"+erpCBMap.get("ry"),"green", erppartProcessCBRoot);
								newRootChildren("test","能源成本--"+erpCBMap.get("ny"),"green", erppartProcessCBRoot);
								newRootChildren("test","设备折旧--"+erpCBMap.get("zj"),"green", erppartProcessCBRoot);
								newRootChildren("test","资源消耗--"+erpCBMap.get("zy"),"green", erppartProcessCBRoot);
								/*-------------------------------------erp green start-----------------------------------------*/
							}
						}
					}else{
						for(Map<String,Object> erpprocessMap:erpProcesslist){
							/*-------------------------------------erp green start-----------------------------------------*/
							TreeNode erpProcessRoot =newRootChildren(erpPartMap.get("ITEMCODE").toString()+","+erpprocessMap.get("processNum").toString(),
									erpprocessMap.get("processNum").toString()+"--"+
									erpprocessMap.get("processName").toString(),"green", erppartProcessRoot);//erp创建工序节点子节点
									erpProcessRoot.setExpanded(false);
							//erp创建工序物料节点
							TreeNode erppartProcessWLRoot =newRootChildren("test","工序物料","green", erpProcessRoot);
							//erp查找工序物料
							List<Map<String,Object>> erpMateraillist=erpCommonService.getErpMaterail(erpprocessMap.get("partId").toString(),erpprocessMap.get("processNum").toString());
							if(null!=erpMateraillist&&erpMateraillist.size()>0){
								for(Map<String,Object> erpMaterailMap:erpMateraillist){
									//erp创建工序物料节点子节点
									newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
									+erpMaterailMap.get("componentQuantity")+"(￥"+erpMaterailMap.get("MaterailCost")+")","green", 
									erppartProcessWLRoot);
								}
								
							}
							//erp创建机台节点
							TreeNode erppartProcessJTRoot =newRootChildren("test","工序机台","green", erpProcessRoot);
							//erp 查找机台
							List<Map<String,Object>> erpJTlist=erpCommonService.getErpmachine(erpPartMap.get("ID").toString(), 
									erpprocessMap.get("processNum").toString());
							if(null!=erpJTlist&&erpJTlist.size()>0){
								for(Map<String,Object> erpJTMap:erpJTlist){
									newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","green", erppartProcessJTRoot);
								}
							}
							//erp创建成本节点
							TreeNode erppartProcessCBRoot =newRootChildren("test","成本与折旧","green", erpProcessRoot);	
							//erp查找成本数据
							Map<String,Object> erpCBMap=erpCommonService.getErpCost(erpPartMap.get("ID").toString(), 
									erpprocessMap.get("processNum").toString());
							//erp创建成本节点子节点
							newRootChildren("test","主料成本--"+erpCBMap.get("zl"),"green", erppartProcessCBRoot);
							newRootChildren("test","辅料成本--"+erpCBMap.get("fl"),"green", erppartProcessCBRoot);
							newRootChildren("test","人员成本--"+erpCBMap.get("ry"),"green", erppartProcessCBRoot);
							newRootChildren("test","能源成本--"+erpCBMap.get("ny"),"green", erppartProcessCBRoot);
							newRootChildren("test","设备折旧--"+erpCBMap.get("zj"),"green", erppartProcessCBRoot);
							newRootChildren("test","资源消耗--"+erpCBMap.get("zy"),"green", erppartProcessCBRoot);
							/*-------------------------------------erp green start-----------------------------------------*/
						}
						
					}
				}
				
			}
		}
	}
	/**
	 * 创建节点
	 * @param id 节点id 
	 * @param name 节点名字
	 * @param color 节点颜色
	 * @param partProcessRoot
	 * @return
	 */
	public TreeNode newRootChildren(String id,String name,String color,TreeNode partProcessRoot){
		Map<String,Object> partProcessCBMap=new HashMap<String, Object>();
		partProcessCBMap.put("id", id);
		partProcessCBMap.put("Name", name);
		partProcessCBMap.put("Color", color);
		TreeNode partProcessCBRoot = new DefaultTreeNode(partProcessCBMap, partProcessRoot);//创建节点
		partProcessCBRoot.setExpanded(true);
		return partProcessCBRoot;
	}
	/**
	 * 修改节点属性
	 * @param treeNode 需要修改的节点
	 * @param color  修改节点的背景颜色
	 * @param name   修改节点的名字
	 * @param id  	  修改节点的id
	 */
	@SuppressWarnings("unchecked")
	public void updateNode(TreeNode treeNode,String color,String name,String id){
		Map<String,Object> erppMap=(Map<String,Object>)treeNode.getData();
		if(null!=color&&!"".equals(color)){
			erppMap.put("Color", color);
		}
		if(null!=name&&!"".equals(name)){
			erppMap.put("Name", name); 
		}
		if(null!=id&&!"".equals(id)){
			erppMap.put("id", id);
		}
		
	}
	/**
	 * 成本数据比较
	 * @param processCBMap  wis成本数据
	 * @param erpCBMap	    erp成本数据
	 * @param wispartProcessCBRoot  wis成本父节点
	 * @param erppartProcessCBRoot	erp成本父节点
	 * @param erpProcessRoot		erp成本爷爷节点
	 * @param wisProcessRoot		wis成本爷爷节点
	 */
	public void HandleCB(Map<String,Object> processCBMap,Map<String,Object> erpCBMap,TreeNode wispartProcessCBRoot,
			TreeNode erppartProcessCBRoot,TreeNode erpProcessRoot,TreeNode wisProcessRoot){
		boolean bool=false;//匹配标识  标识是否有不同
		//wis创建成本子节点
		if(Double.parseDouble(processCBMap.get("mainMaterialCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("zl").toString().replace(",", ""))){
			newRootChildren("test","主料成本--"+processCBMap.get("mainMaterialCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","主料成本--"+erpCBMap.get("zl"),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","主料成本--"+processCBMap.get("mainMaterialCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","主料成本--"+erpCBMap.get("zl"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("subsidiaryMaterialCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("fl").toString().replace(",", ""))){
			newRootChildren("test","辅料成本--"+processCBMap.get("subsidiaryMaterialCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","辅料成本--"+erpCBMap.get("fl"),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","辅料成本--"+processCBMap.get("subsidiaryMaterialCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","辅料成本--"+erpCBMap.get("fl"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("peopleCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("ry").toString().replace(",", ""))){
			newRootChildren("test","人员成本--"+processCBMap.get("peopleCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","人员成本--"+erpCBMap.get("ry").toString().replace(",", ""),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","人员成本--"+processCBMap.get("peopleCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","人员成本--"+erpCBMap.get("ry"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("energyCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("ny").toString().replace(",", ""))){
			newRootChildren("test","能源成本--"+processCBMap.get("energyCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","能源成本--"+erpCBMap.get("ny"),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","能源成本--"+processCBMap.get("energyCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","能源成本--"+erpCBMap.get("ny"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("deviceCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("zj").toString().replace(",", ""))){
			newRootChildren("test","设备折旧--"+processCBMap.get("deviceCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","设备折旧--"+erpCBMap.get("zj").toString().replace(",", ""),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","设备折旧--"+processCBMap.get("deviceCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","设备折旧--"+erpCBMap.get("zj"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("resourceCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("zy").toString().replace(",", ""))){
			newRootChildren("test","资源消耗--"+processCBMap.get("resourceCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","资源消耗--"+erpCBMap.get("zy"),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","资源消耗--"+processCBMap.get("resourceCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","资源消耗--"+erpCBMap.get("zy"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(bool){
			//修改erp父节点颜色
			updateNode(erppartProcessCBRoot, "yellow", null, null);
			updateNode(erpProcessRoot, "yellow", null, null);
			//修改wis父节点颜色
			updateNode(wispartProcessCBRoot, "yellow", null, null);
			updateNode(wisProcessRoot, "yellow", null, null);
		}
	}
	
	/**
	 * 选中数据导入
	 */
	public void ImportDataERP(){
		try {
			 erpCommonService.saveWisData(selectedErpRoot, nodeid);
			 FacesMessage msg = new FacesMessage("数据导入","导入成功!");  
		     FacesContext.getCurrentInstance().addMessage(null, msg); 
		} catch (RuntimeException e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage("数据导入","导入失败!");  
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		LoadTree();
	}
	

	/**
	 * 下拉框改变事件
	 */
	public void selectOnChange(){
		erpMaterailList=erpCommonService.getErpMaterailSelect(selectedData,null,1);
		erpMaterail=new ErpMaterialDataModel(erpMaterailList);
		wisMaterailList=new ArrayList<Map<String,Object>>();
		selectErpMaterail=null;
	}
	/**
	 * erp物料赛选条件
	 */
	public void WlSearch(){
		erpMaterailList=erpCommonService.getErpMaterailSelect(selectedData,materailNo,1);
		erpMaterail=new ErpMaterialDataModel(erpMaterailList);
		selectErpMaterail=null;
	}
	/**
	 * 导入物料数据
	 */
	public void importMaterailData(){
		for(Map<String,Object> erpMaterail:selectErpMaterail){
			boolean bool=true;
			for(Map<String,Object> erpMap:wisMaterailList){
				if(erpMaterail.get("itemCode").toString().equals(erpMap.get("itemCode").toString())){
					bool=false;
				}
			}
			if(bool){
				wisMaterailList.add(erpMaterail);	
			}
			
		}
	}
	/**
	 * 物料数据完成
	 */
	public void fishMaterailImport(){
		String mesg=erpCommonService.saveMaterailData(wisMaterailList, nodeid);
		FacesMessage msg = new FacesMessage("物料数据导入",mesg);  
	    FacesContext.getCurrentInstance().addMessage(null, msg); 
	    selectOnChange();
	}
	
	
	/**
	 * 零件下拉框改变
	 */
	public void selectPartOnChange(){
		erpPartList=erpCommonService.getErpMaterailSelect(selectedData,null,2);
		erpPart=new ErpMaterialDataModel(erpPartList);
		wisPartList=new ArrayList<Map<String,Object>>();
		selectErpPart=null;
	}
	/**
	 * 导入零件数据
	 */
	public void importPartData(){
		for(Map<String,Object> erpPart:selectErpPart){
			boolean bool=true;
			for(Map<String,Object> erpMap:wisPartList){
				if(erpPart.get("itemCode").toString().equals(erpMap.get("itemCode").toString())){
					bool=false;
				}
			}
			if(bool){
				wisPartList.add(erpPart);
			}
		}
	}
	/**
	 * 零件数据完成
	 */
	public void fishPartImport(){
		String mesg=erpCommonService.savePartData(wisPartList, nodeid);
		FacesMessage msg = new FacesMessage("零件数据导入",mesg);
	    FacesContext.getCurrentInstance().addMessage(null, msg); 
	    selectPartOnChange();
	}
	/**
	 * erp物料赛选条件for 零件
	 */
	public void PratSearch(){
		erpPartList=erpCommonService.getErpMaterailSelect(selectedData,partlNo,2);
		erpPart=new ErpMaterialDataModel(erpPartList);
		selectErpPart=null;
	}
	
	public TreeNode getWisRoot() {
		return wisRoot;
	}
	public void setWisRoot(TreeNode wisRoot) {
		this.wisRoot = wisRoot;
	}
	public TreeNode getErpRoot() {
		return erpRoot;
	}
	public void setErpRoot(TreeNode erpRoot) {
		this.erpRoot = erpRoot;
	}
	public TreeNode getSelectedWisRoot() {
		return selectedWisRoot;
	}
	public void setSelectedWisRoot(TreeNode selectedWisRoot) {
		this.selectedWisRoot = selectedWisRoot;
	}
	public TreeNode[] getSelectedErpRoot() {
		return selectedErpRoot;
	}
	public void setSelectedErpRoot(TreeNode[] selectedErpRoot) {
		this.selectedErpRoot = selectedErpRoot;
	}
	public List<Map<String,Object>> getPartlist() {
		return partlist;
	}
	public void setPartlist(List<Map<String,Object>> partlist) {
		this.partlist = partlist;
	}
	public String getSelectedPartNo() {
		return selectedPartNo;
	}
	public void setSelectedPartNo(String selectedPartNo) {
		this.selectedPartNo = selectedPartNo;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getSelectedData() {
		return selectedData;
	}
	public void setSelectedData(String selectedData) {
		this.selectedData = selectedData;
	}
	public String getMaterailNo() {
		return materailNo;
	}
	public void setMaterailNo(String materailNo) {
		this.materailNo = materailNo;
	}
	public List<Map<String, Object>> getErpMaterailList() {
		return erpMaterailList;
	}
	public void setErpMaterailList(List<Map<String, Object>> erpMaterailList) {
		this.erpMaterailList = erpMaterailList;
	}
	public List<Map<String, Object>> getWisMaterailList() {
		return wisMaterailList;
	}
	public void setWisMaterailList(List<Map<String, Object>> wisMaterailList) {
		this.wisMaterailList = wisMaterailList;
	}
	public ErpMaterialDataModel getErpMaterail() {
		return erpMaterail;
	}
	public void setErpMaterail(ErpMaterialDataModel erpMaterail) {
		this.erpMaterail = erpMaterail;
	}
	public Map<String, Object>[] getSelectErpMaterail() {
		return selectErpMaterail;
	}
	public void setSelectErpMaterail(Map<String, Object>[] selectErpMaterail) {
		this.selectErpMaterail = selectErpMaterail;
	}

	public List<Map<String, Object>> getErpPartList() {
		return erpPartList;
	}

	public void setErpPartList(List<Map<String, Object>> erpPartList) {
		this.erpPartList = erpPartList;
	}

	public ErpMaterialDataModel getErpPart() {
		return erpPart;
	}

	public void setErpPart(ErpMaterialDataModel erpPart) {
		this.erpPart = erpPart;
	}

	public List<Map<String, Object>> getWisPartList() {
		return wisPartList;
	}

	public void setWisPartList(List<Map<String, Object>> wisPartList) {
		this.wisPartList = wisPartList;
	}

	public Map<String, Object>[] getSelectErpPart() {
		return selectErpPart;
	}

	public void setSelectErpPart(Map<String, Object>[] selectErpPart) {
		this.selectErpPart = selectErpPart;
	}

	public String getPartlNo() {
		return partlNo;
	}

	public void setPartlNo(String partlNo) {
		this.partlNo = partlNo;
	}
	
}
