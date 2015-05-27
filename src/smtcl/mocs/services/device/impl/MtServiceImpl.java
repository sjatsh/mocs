package smtcl.mocs.services.device.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;

import smtcl.mocs.pojos.device.TEquipmentInfo;
import smtcl.mocs.pojos.device.TMachinesInfo;
import smtcl.mocs.pojos.device.TNodeMachine;
import smtcl.mocs.services.device.ICommonService;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IMtService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * �豸����������ṩcap�ӿ�
 * @���ߣ�YuTao
 * @����ʱ�䣺2012-11-12 ����1:59:08
 * @�޸��ߣ�
 * @�޸����ڣ�
 * @�޸�˵����
 * @version V1.0
 */
public class MtServiceImpl extends	GenericServiceSpringImpl<TMachinesInfo, String> implements IMtService {

	/**
	 * �����ӿ�ʵ��
	 */
	private ICommonService commonService;
	/**
	 * �豸�ӿ�ʵ��
	 */
	private IDeviceService deviceService;

	/**
	 * ����豸����SYMG������ͨ������ID�͹��������ȡ����������Ϣ(JSON��ʽ)
	 * @param machinSeriaNo �������к�
	 * @param password  ��������
	 * @param nodeID  �ڵ�ID
	 * @return String  Json�ַ���    "0"��ʾ �������кŲ�����    "1"��ʾ ���벻��ȷ     "2"��ʾ �Ѿ�����    "3"��ʾ ��ȷ���
	 */
	@Override
	public String getDeviceRelationMtInfo(String machinSeriaNo,
			String password, String nodeID) {
		Map<String, Object> data = new HashMap<String, Object>();
		
		Collection<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(new Parameter("symgMSerialNo",machinSeriaNo,Operator.EQ));
		List<TMachinesInfo> machineSerialNoList = commonService.find(TMachinesInfo.class, (List<Sort>)null, parameters);

		if(machineSerialNoList==null||machineSerialNoList.size()<1){
			data.put("returnType", 0);
			data.put("message", "�������кŲ�����");
		} else {
			TMachinesInfo machineInfo=machineSerialNoList.get(0);
			String pwd=machineInfo.getSymgMActivationCode();
			if (pwd==null||!pwd.equals(password)){
				data.put("returnType", 1);
				data.put("message", "���벻��ȷ");
			} else {
				Collection<Parameter> parameters2 = new HashSet<Parameter>();
				parameters2.add(new Parameter("TMachinesInfo", machineInfo, Operator.EQ));
				List<TNodeMachine> relevance = commonService.find(TNodeMachine.class, (List<Sort>)null, parameters2);
				if (relevance != null&&relevance.size()>0) {
					data.put("returnType", 2);
					data.put("message", "�Ѿ�����");
				} else {
//					String hql3 = "SELECT new MAP(b.symgMachineId AS machineId, "
//							+ " d.symgMtName AS machineType, "
//							+ " b.symgMName AS machineName, "
//							+ " c.orderNo AS orderNo, "
//							+ " a.buyerName AS customerName, "
//							+ " c.deliverDate AS outDate, "
//							+ " c.signDate AS order_createDate, "
//							+ " c.setupDate AS install_startDate, "
//							+ " b.shiftNo AS assemble_shiftNo)"
//							+ " FROM TMachineBuyer a,TMachinesInfo b,TMachineOrders c,TMachineType d"
//							+ " WHERE c.TMachineBuyer.symgBuyerId = a.symgBuyerId "
//							+ " AND b.symgMachineId = c.TMachinesInfo.symgMachineId "
//							+ " AND b.symgMTypeId = d.symgMachineTypeId "
//							+ " AND b.symgMSerialNo ='" + machinSeriaNo+"'";
					String hqlTMI = "SELECT new MAP( b.symgMachineId AS machineId, "
									+ " b.symgMName AS machineName, "
									+ " b.shiftNo AS assemble_shiftNo, "
									+ " b.symgMTypeId AS symgMTypeId )"
									+ " FROM TMachinesInfo b "
									+ " WHERE b.symgMSerialNo ='" + machinSeriaNo + "'";
					List<Map<String, Object>> resultTMI = dao.executeQuery(hqlTMI);
					Map mapTMI = (Map) resultTMI.get(0);
					data.put("returnType", 3);
					data.put("message", "��ȷ���");
					data.put("machineId", mapTMI.get("machineId"));
					data.put("machineName", mapTMI.get("machineName"));
					data.put("assemble_shiftNo", mapTMI.get("assemble_shiftNo"));
					data.put("machineSeriaNo", machinSeriaNo);
					
					String hqlTMT = "SELECT new MAP(d.symgMtName AS machineType )"
									+ " FROM TMachineType d "
									+ " WHERE d.symgMachineTypeId = '" + mapTMI.get("symgMTypeId").toString() + "'";
					List<Map<String, Object>> resultTMT = dao.executeQuery(hqlTMT);
					if(resultTMT.size() != 0){
					Map mapTMT = (Map) resultTMT.get(0);
					data.put("machineType", mapTMT.get("machineType"));
					
					String hqlTMO = "SELECT new MAP(c.orderNo AS orderNo, "
							+ " c.deliverDate AS outDate, "
							+ " c.signDate AS order_createDate, "
							+ " c.setupDate AS install_startDate, "
							+ " c.TMachineBuyer.symgBuyerId AS symgBuyerId) "
							+ " FROM TMachineOrders c"
							+ " WHERE c.TMachinesInfo.symgMachineId = '" + mapTMI.get("machineId").toString() + "'";
					List<Map<String, Object>> resultTMO = dao.executeQuery(hqlTMO);
						
					if(resultTMO.size() != 0){
						Map mapTMO = (Map) resultTMO.get(0);
						data.put("orderNo", mapTMO.get("orderNo"));
						data.put("outDate", mapTMO.get("outDate"));
						data.put("order_createDate", mapTMO.get("order_createDate"));
						data.put("install_startDate", mapTMO.get("install_startDate"));
						
					String hqlTMB = "SELECT a.buyerName AS customerName, "
									+ " FROM TMachineBuyer a "
									+ " WHERE a.symgBuyerId = '" + mapTMO.get("symgBuyerId").toString() + "'";
					List<Map<String, Object>> resultTMB = dao.executeQuery(hqlTMB);
					if(resultTMB.size() != 0){
						Map mapTMB = (Map) resultTMB.get(0);
						data.put("customerName", mapTMB.get("customerName"));
					}else{
						data.put("customerName", "");
					}
					}else{
						data.put("orderNo", "");
						data.put("outDate", "");
						data.put("order_createDate", "");
						data.put("install_startDate", "");
					}
					}
					else{
						data.put("machineType", "");
					}
//					List<Map<String, Object>> result = dao.executeQuery(hql3);
//					Map map = (Map) result.get(0);
//					map.put("message", "��ȷ���");
//					map.put("returnType", 3);
//					map.put("machineSeriaNo", machinSeriaNo);
//					data=map;
				}
			}
		}
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(data);
	}

	/**
     * �豸�����������Ϣ��д����������ϵͳ
     * @param machinSeriaNo �������к�
     * @param nodeID �ڵ�ID
     * @param userId  �û�ID
     * @param operateType �������(1�����   2����̨�豸ɾ��   3����̨�豸ɾ��)
     * @return boolean
     */
	@Override
	public boolean modifyMtInfo(String machinSeriaNo, String nodeID,
			Long userId, String operateType) {
		boolean bool=true;
		Collection<Parameter> parameters1 = new HashSet<Parameter>();
		parameters1.add(new Parameter("symgMSerialNo", machinSeriaNo, Operator.EQ));
		List<TMachinesInfo> Listtmi = commonService.find(TMachinesInfo.class, (List<Sort>)null, parameters1);
		Collection<Parameter> parametersX = new HashSet<Parameter>();
		String hql = "FROM TNodeMachine WHERE TMachinesInfo.symgMSerialNo = '" + machinSeriaNo + "'";
		List hasSeriaNo = dao.executeQuery(hql, parametersX);
		
		if(operateType.equals("1")){
			
			if(hasSeriaNo.size() == 1){
				bool =false;
			}else{
			try{
			
			Collection<Parameter> parameters2 = new HashSet<Parameter>();			
			parameters2.add(new Parameter("equSerialNo", machinSeriaNo, Operator.EQ));
			TNodeMachine tnm=new TNodeMachine();	
			
			if(Listtmi!=null) tnm.setTMachinesInfo(Listtmi.get(0));
			
			tnm.setEquNodeID(nodeID);  //�豸�ڵ�ID
			// ��ȡ��¼�û���
			//TUser tu=(TUser)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		   
		    tnm.setInteractionDate(new Date());
		   // tnm.setInteractionOperator(tu.getLoginName());		   
		    commonService.save(tnm);
			}
			catch (Exception e){
				e.printStackTrace();
				bool = false;
			}
		}
		}
		
		if(operateType.equals("2")){
			if(hasSeriaNo.size() == 0){
				bool =true;
			}else{
			try{
				Collection<Parameter> parameters = new HashSet<Parameter>();
				if(Listtmi!=null){
					parameters.add(new Parameter("TMachinesInfo", Listtmi, Operator.EQ));
					List<TNodeMachine> deleteName = commonService.find(TNodeMachine.class, (List<Sort>)null, parameters);
					commonService.delete(deleteName.get(0));
				}
			}
			catch (Exception e){
				e.printStackTrace();
				bool = false;
			}
		}
		}
		
		if(operateType.equals("3")){			
			List<TEquipmentInfo> machineInNode = deviceService.getNodesAllTEquipmentInfo(nodeID);			
			if(machineInNode.size() == 0){
				bool = true;
			}else{
			try{
				for(int i=0;i<machineInNode.size();i++){					
					String del_hql="delete from TNodeMachine a where a.equNodeID='"+machineInNode.get(i).getTNodes().getNodeId()+"'";
				    dao.executeUpdate(del_hql);
				}
			}
			catch (Exception e){
				e.printStackTrace();
				bool = false;
			}
		}
		}
		return bool;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	
	public IDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}

}