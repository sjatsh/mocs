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
	private TreeNode wisRoot; //wis��
	private TreeNode erpRoot;//erp��
	private TreeNode selectedWisRoot;
	private TreeNode[] selectedErpRoot;
	private IERPSerice erpCommonService = (IERPSerice)ServiceFactory.getBean("erpCommonService");//ע������һ�����ݿ��service
	private IPartService wisPartService=(IPartService)ServiceFactory.getBean("partService");
	private List<Map<String,Object>> partlist;//����������б�
	private String selectedPartNo;//��ǰѡ��������
	private Date startTime;//��ʼʱ��
	private Date endTime;//����ʱ��
	public  String nodeid="8a8abc8d44d361fd0144d3c76dd60001";
	private String selectedData="0";//�������ݲ�ѯ����
	private String materailNo;//��ѯ���ϱ���
	private List<Map<String,Object>> erpMaterailList;//erp��������������
	private ErpMaterialDataModel erpMaterail;
	private Map<String,Object>[] selectErpMaterail;//erpѡ�е����Ϲ�������
	private List<Map<String,Object>> wisMaterailList;//����wis�����ұ�
	private List<Map<String,Object>> erpPartList;//erp������� �������
	private ErpMaterialDataModel erpPart;
	private List<Map<String,Object>> wisPartList;//����wis����ұ�
	private Map<String,Object>[] selectErpPart;//erpѡ�е����Ϲ������
	private String partlNo;//��ѯ������
	
	
	public StaticDataImportBean(){
		partlist=erpCommonService.getErpPartTypeList(null, null, null);//erp��ѯ���
		selectOnChange();
		selectPartOnChange();
	}
	
	//	���ضԱ���
	public void LoadTree(){
		wisRoot =newRootChildren("test","Root","none", null);//wis���������ڵ㣬�ýڵ㲻����ʾ
		erpRoot =newRootChildren("test","Root","none", null);//erp���������ڵ㣬�ýڵ㲻����ʾ
		
		TreeNode wispartRoot =newRootChildren("test","���","none", wisRoot);//wis��������ڵ�
		TreeNode erppartRoot =newRootChildren("test","���(����)","none", erpRoot);//wis��������ڵ�
		//List<Map<String,Object>> wisplist=wisPartService.getTPartTypeInfo(nodeid, selectedPartNo, startTime, endTime);//wis��ѯ���
		List<Map<String,Object>> erpplist=erpCommonService.getErpPartTypeList(startTime, endTime, selectedPartNo);//erp��ѯ���
		for(Map<String,Object> erpPartMap:erpplist){
			
			//wis��ѯ����������
			List<Map<String,Object>> wisplist=wisPartService.getTPartTypeInfo(nodeid, erpPartMap.get("ITEMCODE").toString(), null, null);
			if(null!=wisplist&&wisplist.size()>0){
				//erp��������ӽڵ�
				TreeNode erprealpartRoot =newRootChildren("test",erpPartMap.get("ITEMCODE").toString(),"none", erppartRoot);
				Map<String,Object> wisPartMap=wisplist.get(0);
				//wis��������ӽڵ�
				TreeNode wisrealpartRoot =newRootChildren("test",wisPartMap.get("Name").toString(),"none", wispartRoot);
				//erp ���ҹ���
				List<Map<String,Object>> erpProcesslist=erpCommonService.getErpProcess(erpPartMap.get("ID").toString());
				//wis��ѯ���շ���
				List<Map<String,Object>> wisprocesslist=wisPartService.getTProcessplanInfo(wisPartMap.get("Name").toString());
				//wis�����������ڵ�
				TreeNode erppartProcessRoot =newRootChildren("test","�������","none", erprealpartRoot);
				if(null!=erpProcesslist&&erpProcesslist.size()>0){
					if(null!=wisprocesslist&&wisprocesslist.size()>0){
						Map<String,Object> wisprocessPlanMap=wisprocesslist.get(0);
						//wis�������շ����ڵ�
						TreeNode wispartProcessPlanRoot =newRootChildren("test",wisprocessPlanMap.get("processPlanName").toString(),"none",
								wisrealpartRoot);
						TreeNode wispartProcessRoot =newRootChildren("test","�������","none", wispartProcessPlanRoot);//wis�����������ڵ�
						for(Map<String,Object> erpprocessMap:erpProcesslist){
							Map<String,Object> wisprocessMapvalue = null;
							//Ϊ�м��Ĺ���ʼƥ��wisϵͳ�Ĺ���
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
											erpprocessMap.get("processName").toString(),"none", erppartProcessRoot);//erp��������ڵ��ӽڵ�
											erpProcessRoot.setExpanded(false);
									TreeNode wisProcessRoot =newRootChildren("test",wisprocessMapvalue.get("processOrder").toString()+"--"+
											wisprocessMapvalue.get("processName").toString(),"none", wispartProcessRoot);//wis�����������ڵ��ӽڵ�
											wisProcessRoot.setExpanded(false);
									/*-------------------------------------wis��erp���ϱȶ� none start-----------------------------------------*/
									TreeNode wispartProcessWLRoot =newRootChildren("test","��������","none", wisProcessRoot);//wis�����������Ͻڵ�
									TreeNode erppartProcessWLRoot =newRootChildren("test","����BOM","none", erpProcessRoot);//erp�����������Ͻڵ�
									//wis��ѯ��������
									List<Map<String,Object>> processWLlist=wisPartService.getProcessWL(wisprocessMapvalue.get("processId").toString());
									//erp��ѯ��������
									List<Map<String,Object>> erpMateraillist=erpCommonService.getErpMaterail(erpprocessMap.get("partId").toString(),erpprocessMap.get("processNum").toString());
									if(null!=erpMateraillist&&erpMateraillist.size()>0){
										for(Map<String,Object> erpMaterailMap:erpMateraillist){
											Map<String,Object> processWlMapValue=null;
											//wis �� erp ƥ������
											for(Map<String,Object> processWlMap:processWLlist){
												//System.out.println(processWlMap.get("No").toString()+"\n"+erpMaterailMap.get("itemCode").toString());
												if(processWlMap.get("No").toString().equals(erpMaterailMap.get("componentCode").toString())){
													processWlMapValue=processWlMap;
													break;
												}
											}
											if(null!=processWlMapValue){
												//��ȫ����
												if(null==erpMaterailMap.get("MaterailCost")){
													erpMaterailMap.put("MaterailCost", "0.0");
												}
												if(processWlMapValue.get("Name").toString().equals(erpMaterailMap.get("componentDesc").toString())&&
												   processWlMapValue.get("RequirementNum").toString().equals(erpMaterailMap.get("componentQuantity").toString())&&
												   Double.parseDouble(processWlMapValue.get("Price").toString())==Double.parseDouble(erpMaterailMap.get("MaterailCost").toString())){
													//wis�������������ӽڵ�
													newRootChildren("test",processWlMapValue.get("No")+"("+processWlMapValue.get("Name")+")--"
															+processWlMapValue.get("RequirementNum")+"(��"+processWlMapValue.get("Price")+")","none",
															wispartProcessWLRoot);
													//erp�����������Ͻڵ��ӽڵ�
													newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
															+erpMaterailMap.get("componentQuantity")+"(��"+erpMaterailMap.get("MaterailCost")+")","none", 
															erppartProcessWLRoot);
												}else{
													//����ȫƥ��
													//wis�������������ӽڵ�
													newRootChildren("test",processWlMapValue.get("No")+"("+processWlMapValue.get("Name")+")--"
															+processWlMapValue.get("RequirementNum")+"(��"+processWlMapValue.get("Price")+")","yellow",
															wispartProcessWLRoot);
													//erp�����������Ͻڵ��ӽڵ�
													newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
															+erpMaterailMap.get("componentQuantity")+"(��"+erpMaterailMap.get("MaterailCost")+")","yellow", 
															erppartProcessWLRoot);
													//�޸�erp���ڵ���ɫ
													updateNode(erppartProcessWLRoot, "yellow", null, null);
													updateNode(erpProcessRoot, "yellow", null, null);
													//�޸�wis���ڵ���ɫ
													updateNode(wispartProcessWLRoot, "yellow", null, null);
													updateNode(wisProcessRoot, "yellow", null, null);
												}
											}else{
												//erp�����������Ͻڵ��ӽڵ�
												newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
														+erpMaterailMap.get("componentQuantity")+"(��"+erpMaterailMap.get("MaterailCost")+")","green", 
														erppartProcessWLRoot);
												//�޸�erp���ڵ���ɫ
												updateNode(erppartProcessWLRoot, "yellow", null, null);
												updateNode(erpProcessRoot, "yellow", null, null);
												//�޸�wis���ڵ���ɫ
												updateNode(wispartProcessWLRoot, "yellow", null, null);
												updateNode(wisProcessRoot, "yellow", null, null);
											}
											
										}
										
									}
									/*-------------------------------------wis��erp���ϱȶ� none end-----------------------------------------*/
									
									/*-------------------------------------wis��erp��̨�ȶ� none start-----------------------------------------*/
									TreeNode wispartProcessJTRoot =newRootChildren("test","�����̨","none", wisProcessRoot);//wis������̨�ڵ�
									TreeNode erppartProcessJTRoot =newRootChildren("test","������Դ","none", erpProcessRoot);//erp������̨�ڵ�
									//erp ���һ�̨
									List<Map<String,Object>> erpJTlist=erpCommonService.getErpmachine(erpPartMap.get("ID").toString(), 
											erpprocessMap.get("processNum").toString());
									//wis ���һ�̨
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
												//��ȫƥ��
												if((Double.parseDouble(erpJTMap.get("USAGERATEORAMOUNT").toString().replace(",", ""))*60)==
														Double.parseDouble(processJTMapValue.get("theoryWorktime").toString().replace(",", ""))){
													//wis������̨�ӽڵ�
													newRootChildren("test",""+processJTMapValue.get("typecode")+"("+processJTMapValue.get("theoryWorktime")+")",
															"none", wispartProcessJTRoot);
													//erp������̨�ӽڵ�
													newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","none", 
															erppartProcessJTRoot);
												}else{
												//����ȫƥ��
													//wis������̨�ӽڵ�
													newRootChildren("test",""+processJTMapValue.get("typecode")+"("+processJTMapValue.get("processingTime")+")",
															"yellow", wispartProcessJTRoot);
													//erp������̨�ӽڵ�
													newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","yellow",
															erppartProcessJTRoot);
													//�޸�erp���ڵ���ɫ
													updateNode(erppartProcessJTRoot, "yellow", null, null);
													updateNode(erpProcessRoot, "yellow", null, null);
													//�޸�wis���ڵ���ɫ
													updateNode(wispartProcessJTRoot, "yellow", null, null);
													updateNode(wisProcessRoot, "yellow", null, null);
												}
												
											}else if(null!=processJTlist&&processJTlist.size()>0){
												//erp������̨�ӽڵ�
												newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")",
														"yellow", erppartProcessJTRoot);
												processJTMapValue=processJTlist.get(0);
												//wis������̨�ӽڵ�
												newRootChildren("test",""+processJTMapValue.get("typecode")+"("+processJTMapValue.get("theoryWorktime")+")",
														"none", wispartProcessJTRoot);
												//�޸�erp���ڵ���ɫ
												updateNode(erppartProcessJTRoot, "yellow", null, null);
												updateNode(erpProcessRoot, "yellow", null, null);
												//�޸�wis���ڵ���ɫ
												updateNode(wispartProcessJTRoot, "yellow", null, null);
												updateNode(wisProcessRoot, "yellow", null, null);
											}
											else{
												//erp������̨�ӽڵ�
												newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")",
														"green", erppartProcessJTRoot);

												//�޸�erp���ڵ���ɫ
												updateNode(erppartProcessJTRoot, "yellow", null, null);
												updateNode(erpProcessRoot, "yellow", null, null);
												//�޸�wis���ڵ���ɫ
												updateNode(wispartProcessJTRoot, "yellow", null, null);
												updateNode(wisProcessRoot, "yellow", null, null);
											}
											
										}
									}
									/*-------------------------------------wis��erp��̨�ȶ� none end-----------------------------------------*/
									
									/*-------------------------------------wis��erp�ɱ��ȶ� none start-----------------------------------------*/
									TreeNode wispartProcessCBRoot =newRootChildren("test","�ɱ����۾�","none", wisProcessRoot);	//wis�����ɱ��ڵ�
									TreeNode erppartProcessCBRoot =newRootChildren("test","�ɱ����۾�","none", erpProcessRoot);	//erp�����ɱ��ڵ�
									//erp�ɱ�����
									Map<String,Object> erpCBMap=erpCommonService.getErpCost(erpPartMap.get("ID").toString(), 
											erpprocessMap.get("processNum").toString());
									//wis�ɱ�����
									List<Map<String,Object>> processCBlist=wisPartService.getCBData(wisprocessMapvalue.get("processId").toString());
									//erp�����ɱ��ڵ��ӽڵ�
									if(null!=processCBlist&&processCBlist.size()>0){
										Map<String,Object> processCBMap=processCBlist.get(0);
										//����ɱ�ÿ�����ݵıȽ�
										HandleCB(processCBMap, erpCBMap, wispartProcessCBRoot, erppartProcessCBRoot,erpProcessRoot, wisProcessRoot);
									}else{
										//����erp�ɱ��ӽڵ�
										newRootChildren("test","���ϳɱ�--"+erpCBMap.get("zl"),"green", erppartProcessCBRoot);
										newRootChildren("test","���ϳɱ�--"+erpCBMap.get("fl"),"green", erppartProcessCBRoot);
										newRootChildren("test","��Ա�ɱ�--"+erpCBMap.get("ry"),"green", erppartProcessCBRoot);
										newRootChildren("test","��Դ�ɱ�--"+erpCBMap.get("ny"),"green", erppartProcessCBRoot);
										newRootChildren("test","�豸�۾�--"+erpCBMap.get("zj"),"green", erppartProcessCBRoot);
										newRootChildren("test","��Դ����--"+erpCBMap.get("zy"),"green", erppartProcessCBRoot);
										//�޸�erp���ڵ���ɫ
										updateNode(erppartProcessCBRoot, "yellow", null, null);
										updateNode(erpProcessRoot, "yellow", null, null);
										//�޸�wis���ڵ���ɫ
										updateNode(wispartProcessCBRoot, "yellow", null, null);
										updateNode(wisProcessRoot, "yellow", null, null);
									}
									/*-------------------------------------wis��erp�ɱ��ȶ� none end-----------------------------------------*/
								}else{
									TreeNode erpProcessRoot =newRootChildren(erpPartMap.get("ITEMCODE").toString()+","+erpprocessMap.get("processNum").toString(),
											erpprocessMap.get("processNum").toString()+"--"+
											erpprocessMap.get("processName").toString(),"yellow", erppartProcessRoot);//erp��������ڵ��ӽڵ�
											erpProcessRoot.setExpanded(false);
									TreeNode wisProcessRoot =newRootChildren("test",wisprocessMapvalue.get("processOrder").toString()+"--"+
											wisprocessMapvalue.get("processName").toString(),"yellow", wispartProcessRoot);//wis�����������ڵ��ӽڵ�
											wisProcessRoot.setExpanded(false);
									/*-------------------------------------wis yellow start-----------------------------------------*/
									//wis�����������Ͻڵ�
									TreeNode wispartProcessWLRoot =newRootChildren("test","��������","yellow", wisProcessRoot);
									//wis��ѯ��������
									List<Map<String,Object>> processWLlist=wisPartService.getProcessWL(wisprocessMapvalue.get("processId").toString());
									if(null!=processWLlist&&processWLlist.size()>0){
										for(Map<String,Object> processWlMap:processWLlist){
											//wis�������������ӽڵ�
											newRootChildren("test",processWlMap.get("No")+"--"+processWlMap.get("Name")+"--"
													+processWlMap.get("RequirementNum")+"--"+processWlMap.get("Price"),"yellow", wispartProcessWLRoot);
										}
									}
									//wis������̨�ڵ�
									TreeNode wispartProcessJTRoot =newRootChildren("test","�����̨","yellow", wisProcessRoot);
									List<Map<String,Object>> processJTlist=wisPartService.getProcessJTData(wisprocessMapvalue.get("processId").toString());
									if(null!=processJTlist&&processJTlist.size()>0){
										for(Map<String,Object> processJTMap:processJTlist){
											//wis������̨�ӽڵ�
											newRootChildren("test",""+processJTMap.get("typecode")+"("+processJTMap.get("processingTime")+")",
													"yellow", wispartProcessJTRoot);
										}
									}
									//wis�����ɱ��ڵ�
									TreeNode wispartProcessCBRoot =newRootChildren("test","�ɱ����۾�","yellow", wisProcessRoot);	
									//wis��ѯ�ɱ�����
									List<Map<String,Object>> processCBlist=wisPartService.getCBData(wisprocessMapvalue.get("processId").toString());
									if(null!=processCBlist&&processCBlist.size()>0){
										for(Map<String,Object> processCBMap:processCBlist){
											//wis�����ɱ��ӽڵ�
											newRootChildren("test","���ϳɱ�--"+processCBMap.get("mainMaterialCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","���ϳɱ�--"+processCBMap.get("subsidiaryMaterialCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","��Ա�ɱ�--"+processCBMap.get("peopleCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","��Դ�ɱ�--"+processCBMap.get("energyCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","�豸�۾�--"+processCBMap.get("deviceCost"),"yellow", wispartProcessCBRoot);
											newRootChildren("test","��Դ����--"+processCBMap.get("resourceCost"),"yellow", wispartProcessCBRoot);
										}
									}
									/*-------------------------------------wis yellow end-----------------------------------------*/
									
									/*-------------------------------------erp yellow start-----------------------------------------*/		
									TreeNode erppartProcessWLRoot =newRootChildren("test","��������","yellow", erpProcessRoot);//erp�����������Ͻڵ�
									List<Map<String,Object>> erpMateraillist=erpCommonService.getErpMaterail(erpprocessMap.get("partId").toString(),erpprocessMap.get("processNum").toString());
									if(null!=erpMateraillist&&erpMateraillist.size()>0){
										for(Map<String,Object> erpMaterailMap:erpMateraillist){
											//erp�����������Ͻڵ��ӽڵ�
											newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
													+erpMaterailMap.get("componentQuantity")+"(��"+erpMaterailMap.get("MaterailCost")+")","green", 
													erppartProcessWLRoot);
										}
									}
									//erp������̨�ڵ�
									TreeNode erppartProcessJTRoot =newRootChildren("test","�����̨","yellow", erpProcessRoot);
									//erp ���һ�̨
									List<Map<String,Object>> erpJTlist=erpCommonService.getErpmachine(erpPartMap.get("ID").toString(), 
											erpprocessMap.get("processNum").toString());
									if(null!=erpJTlist&&erpJTlist.size()>0){
										for(Map<String,Object> erpJTMap:erpJTlist){
											newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","yellow", erppartProcessJTRoot);
										}
									}
									//erp�����ɱ��ڵ�
									TreeNode erppartProcessCBRoot =newRootChildren("test","�ɱ����۾�","yellow", erpProcessRoot);
									//erp���ҳ�������
									Map<String,Object> erpCBMap=erpCommonService.getErpCost(erpPartMap.get("ID").toString(), 
											erpprocessMap.get("processNum").toString());
									//erp�����ɱ��ڵ��ӽڵ�
									newRootChildren("test","���ϳɱ�--"+erpCBMap.get("zl"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","���ϳɱ�--"+erpCBMap.get("fl"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","��Ա�ɱ�--"+erpCBMap.get("ry"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","��Դ�ɱ�--"+erpCBMap.get("ny"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","�豸�۾�--"+erpCBMap.get("zj"),"yellow", erppartProcessCBRoot);
									newRootChildren("test","��Դ����--"+erpCBMap.get("zy"),"yellow", erppartProcessCBRoot);
								/*-------------------------------------erp yellow end-----------------------------------------*/
								}
							}else{
								/*-------------------------------------erp green start-----------------------------------------*/
								TreeNode erpProcessRoot =newRootChildren(erpPartMap.get("ITEMCODE").toString()+","+erpprocessMap.get("processNum").toString(),
										erpprocessMap.get("processNum").toString()+"--"+
										erpprocessMap.get("processName").toString(),"green", erppartProcessRoot);//erp��������ڵ��ӽڵ�
										erpProcessRoot.setExpanded(false);
								//erp�����������Ͻڵ�
								TreeNode erppartProcessWLRoot =newRootChildren("test","��������","green", erpProcessRoot);
								//erp���ҹ�������
								List<Map<String,Object>> erpMateraillist=erpCommonService.getErpMaterail(erpprocessMap.get("partId").toString(),erpprocessMap.get("processNum").toString());
								if(null!=erpMateraillist&&erpMateraillist.size()>0){
									for(Map<String,Object> erpMaterailMap:erpMateraillist){
										//erp�����������Ͻڵ��ӽڵ�
										newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
										+erpMaterailMap.get("componentQuantity")+"(��"+erpMaterailMap.get("MaterailCost")+")","green", 
										erppartProcessWLRoot);
									}
									
								}
								//erp������̨�ڵ�
								TreeNode erppartProcessJTRoot =newRootChildren("test","�����̨","green", erpProcessRoot);
								//erp ���һ�̨
								List<Map<String,Object>> erpJTlist=erpCommonService.getErpmachine(erpPartMap.get("ID").toString(), 
										erpprocessMap.get("processNum").toString());
								if(null!=erpJTlist&&erpJTlist.size()>0){
									for(Map<String,Object> erpJTMap:erpJTlist){
										newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","green", erppartProcessJTRoot);
									}
								}
								//erp�����ɱ��ڵ�
								TreeNode erppartProcessCBRoot =newRootChildren("test","�ɱ����۾�","green", erpProcessRoot);	
								//erp���ҳɱ�����
								Map<String,Object> erpCBMap=erpCommonService.getErpCost(erpPartMap.get("ID").toString(), 
										erpprocessMap.get("processNum").toString());
								//erp�����ɱ��ڵ��ӽڵ�
								newRootChildren("test","���ϳɱ�--"+erpCBMap.get("zl"),"green", erppartProcessCBRoot);
								newRootChildren("test","���ϳɱ�--"+erpCBMap.get("fl"),"green", erppartProcessCBRoot);
								newRootChildren("test","��Ա�ɱ�--"+erpCBMap.get("ry"),"green", erppartProcessCBRoot);
								newRootChildren("test","��Դ�ɱ�--"+erpCBMap.get("ny"),"green", erppartProcessCBRoot);
								newRootChildren("test","�豸�۾�--"+erpCBMap.get("zj"),"green", erppartProcessCBRoot);
								newRootChildren("test","��Դ����--"+erpCBMap.get("zy"),"green", erppartProcessCBRoot);
								/*-------------------------------------erp green start-----------------------------------------*/
							}
						}
					}else{
						for(Map<String,Object> erpprocessMap:erpProcesslist){
							/*-------------------------------------erp green start-----------------------------------------*/
							TreeNode erpProcessRoot =newRootChildren(erpPartMap.get("ITEMCODE").toString()+","+erpprocessMap.get("processNum").toString(),
									erpprocessMap.get("processNum").toString()+"--"+
									erpprocessMap.get("processName").toString(),"green", erppartProcessRoot);//erp��������ڵ��ӽڵ�
									erpProcessRoot.setExpanded(false);
							//erp�����������Ͻڵ�
							TreeNode erppartProcessWLRoot =newRootChildren("test","��������","green", erpProcessRoot);
							//erp���ҹ�������
							List<Map<String,Object>> erpMateraillist=erpCommonService.getErpMaterail(erpprocessMap.get("partId").toString(),erpprocessMap.get("processNum").toString());
							if(null!=erpMateraillist&&erpMateraillist.size()>0){
								for(Map<String,Object> erpMaterailMap:erpMateraillist){
									//erp�����������Ͻڵ��ӽڵ�
									newRootChildren("test",erpMaterailMap.get("componentCode")+"("+erpMaterailMap.get("componentDesc")+")--"
									+erpMaterailMap.get("componentQuantity")+"(��"+erpMaterailMap.get("MaterailCost")+")","green", 
									erppartProcessWLRoot);
								}
								
							}
							//erp������̨�ڵ�
							TreeNode erppartProcessJTRoot =newRootChildren("test","�����̨","green", erpProcessRoot);
							//erp ���һ�̨
							List<Map<String,Object>> erpJTlist=erpCommonService.getErpmachine(erpPartMap.get("ID").toString(), 
									erpprocessMap.get("processNum").toString());
							if(null!=erpJTlist&&erpJTlist.size()>0){
								for(Map<String,Object> erpJTMap:erpJTlist){
									newRootChildren("test",erpJTMap.get("RESOURCECODE")+"("+erpJTMap.get("USAGERATEORAMOUNT")+")","green", erppartProcessJTRoot);
								}
							}
							//erp�����ɱ��ڵ�
							TreeNode erppartProcessCBRoot =newRootChildren("test","�ɱ����۾�","green", erpProcessRoot);	
							//erp���ҳɱ�����
							Map<String,Object> erpCBMap=erpCommonService.getErpCost(erpPartMap.get("ID").toString(), 
									erpprocessMap.get("processNum").toString());
							//erp�����ɱ��ڵ��ӽڵ�
							newRootChildren("test","���ϳɱ�--"+erpCBMap.get("zl"),"green", erppartProcessCBRoot);
							newRootChildren("test","���ϳɱ�--"+erpCBMap.get("fl"),"green", erppartProcessCBRoot);
							newRootChildren("test","��Ա�ɱ�--"+erpCBMap.get("ry"),"green", erppartProcessCBRoot);
							newRootChildren("test","��Դ�ɱ�--"+erpCBMap.get("ny"),"green", erppartProcessCBRoot);
							newRootChildren("test","�豸�۾�--"+erpCBMap.get("zj"),"green", erppartProcessCBRoot);
							newRootChildren("test","��Դ����--"+erpCBMap.get("zy"),"green", erppartProcessCBRoot);
							/*-------------------------------------erp green start-----------------------------------------*/
						}
						
					}
				}
				
			}
		}
	}
	/**
	 * �����ڵ�
	 * @param id �ڵ�id 
	 * @param name �ڵ�����
	 * @param color �ڵ���ɫ
	 * @param partProcessRoot
	 * @return
	 */
	public TreeNode newRootChildren(String id,String name,String color,TreeNode partProcessRoot){
		Map<String,Object> partProcessCBMap=new HashMap<String, Object>();
		partProcessCBMap.put("id", id);
		partProcessCBMap.put("Name", name);
		partProcessCBMap.put("Color", color);
		TreeNode partProcessCBRoot = new DefaultTreeNode(partProcessCBMap, partProcessRoot);//�����ڵ�
		partProcessCBRoot.setExpanded(true);
		return partProcessCBRoot;
	}
	/**
	 * �޸Ľڵ�����
	 * @param treeNode ��Ҫ�޸ĵĽڵ�
	 * @param color  �޸Ľڵ�ı�����ɫ
	 * @param name   �޸Ľڵ������
	 * @param id  	  �޸Ľڵ��id
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
	 * �ɱ����ݱȽ�
	 * @param processCBMap  wis�ɱ�����
	 * @param erpCBMap	    erp�ɱ�����
	 * @param wispartProcessCBRoot  wis�ɱ����ڵ�
	 * @param erppartProcessCBRoot	erp�ɱ����ڵ�
	 * @param erpProcessRoot		erp�ɱ�үү�ڵ�
	 * @param wisProcessRoot		wis�ɱ�үү�ڵ�
	 */
	public void HandleCB(Map<String,Object> processCBMap,Map<String,Object> erpCBMap,TreeNode wispartProcessCBRoot,
			TreeNode erppartProcessCBRoot,TreeNode erpProcessRoot,TreeNode wisProcessRoot){
		boolean bool=false;//ƥ���ʶ  ��ʶ�Ƿ��в�ͬ
		//wis�����ɱ��ӽڵ�
		if(Double.parseDouble(processCBMap.get("mainMaterialCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("zl").toString().replace(",", ""))){
			newRootChildren("test","���ϳɱ�--"+processCBMap.get("mainMaterialCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","���ϳɱ�--"+erpCBMap.get("zl"),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","���ϳɱ�--"+processCBMap.get("mainMaterialCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","���ϳɱ�--"+erpCBMap.get("zl"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("subsidiaryMaterialCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("fl").toString().replace(",", ""))){
			newRootChildren("test","���ϳɱ�--"+processCBMap.get("subsidiaryMaterialCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","���ϳɱ�--"+erpCBMap.get("fl"),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","���ϳɱ�--"+processCBMap.get("subsidiaryMaterialCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","���ϳɱ�--"+erpCBMap.get("fl"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("peopleCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("ry").toString().replace(",", ""))){
			newRootChildren("test","��Ա�ɱ�--"+processCBMap.get("peopleCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","��Ա�ɱ�--"+erpCBMap.get("ry").toString().replace(",", ""),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","��Ա�ɱ�--"+processCBMap.get("peopleCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","��Ա�ɱ�--"+erpCBMap.get("ry"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("energyCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("ny").toString().replace(",", ""))){
			newRootChildren("test","��Դ�ɱ�--"+processCBMap.get("energyCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","��Դ�ɱ�--"+erpCBMap.get("ny"),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","��Դ�ɱ�--"+processCBMap.get("energyCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","��Դ�ɱ�--"+erpCBMap.get("ny"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("deviceCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("zj").toString().replace(",", ""))){
			newRootChildren("test","�豸�۾�--"+processCBMap.get("deviceCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","�豸�۾�--"+erpCBMap.get("zj").toString().replace(",", ""),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","�豸�۾�--"+processCBMap.get("deviceCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","�豸�۾�--"+erpCBMap.get("zj"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(Double.parseDouble(processCBMap.get("resourceCost").toString().replace(",", ""))==Double.parseDouble(erpCBMap.get("zy").toString().replace(",", ""))){
			newRootChildren("test","��Դ����--"+processCBMap.get("resourceCost"),"none", wispartProcessCBRoot);
			newRootChildren("test","��Դ����--"+erpCBMap.get("zy"),"none", erppartProcessCBRoot);
		}else{
			newRootChildren("test","��Դ����--"+processCBMap.get("resourceCost"),"yellow", wispartProcessCBRoot);
			newRootChildren("test","��Դ����--"+erpCBMap.get("zy"),"yellow", erppartProcessCBRoot);
			bool=true;
		}
		
		if(bool){
			//�޸�erp���ڵ���ɫ
			updateNode(erppartProcessCBRoot, "yellow", null, null);
			updateNode(erpProcessRoot, "yellow", null, null);
			//�޸�wis���ڵ���ɫ
			updateNode(wispartProcessCBRoot, "yellow", null, null);
			updateNode(wisProcessRoot, "yellow", null, null);
		}
	}
	
	/**
	 * ѡ�����ݵ���
	 */
	public void ImportDataERP(){
		try {
			 erpCommonService.saveWisData(selectedErpRoot, nodeid);
			 FacesMessage msg = new FacesMessage("���ݵ���","����ɹ�!");  
		     FacesContext.getCurrentInstance().addMessage(null, msg); 
		} catch (RuntimeException e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage("���ݵ���","����ʧ��!");  
		    FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		LoadTree();
	}
	

	/**
	 * ������ı��¼�
	 */
	public void selectOnChange(){
		erpMaterailList=erpCommonService.getErpMaterailSelect(selectedData,null,1);
		erpMaterail=new ErpMaterialDataModel(erpMaterailList);
		wisMaterailList=new ArrayList<Map<String,Object>>();
		selectErpMaterail=null;
	}
	/**
	 * erp������ѡ����
	 */
	public void WlSearch(){
		erpMaterailList=erpCommonService.getErpMaterailSelect(selectedData,materailNo,1);
		erpMaterail=new ErpMaterialDataModel(erpMaterailList);
		selectErpMaterail=null;
	}
	/**
	 * ������������
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
	 * �����������
	 */
	public void fishMaterailImport(){
		String mesg=erpCommonService.saveMaterailData(wisMaterailList, nodeid);
		FacesMessage msg = new FacesMessage("�������ݵ���",mesg);  
	    FacesContext.getCurrentInstance().addMessage(null, msg); 
	    selectOnChange();
	}
	
	
	/**
	 * ���������ı�
	 */
	public void selectPartOnChange(){
		erpPartList=erpCommonService.getErpMaterailSelect(selectedData,null,2);
		erpPart=new ErpMaterialDataModel(erpPartList);
		wisPartList=new ArrayList<Map<String,Object>>();
		selectErpPart=null;
	}
	/**
	 * �����������
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
	 * ����������
	 */
	public void fishPartImport(){
		String mesg=erpCommonService.savePartData(wisPartList, nodeid);
		FacesMessage msg = new FacesMessage("������ݵ���",mesg);
	    FacesContext.getCurrentInstance().addMessage(null, msg); 
	    selectPartOnChange();
	}
	/**
	 * erp������ѡ����for ���
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
