package smtcl.mocs.services.erp.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.dreamwork.persistence.Parameter;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import smtcl.mocs.dao.device.ICommonDao;
import smtcl.mocs.pojos.device.TUser;
import smtcl.mocs.pojos.erp.WisQaScrap;
import smtcl.mocs.pojos.erp.WisQaScrapId;
import smtcl.mocs.pojos.erp.WisTransfer;
import smtcl.mocs.pojos.erp.WisTransferId;
import smtcl.mocs.pojos.job.TEquipmenttypeInfo;
import smtcl.mocs.pojos.job.TJobdispatchlistInfo;
import smtcl.mocs.pojos.job.TJobplanInfo;
import smtcl.mocs.pojos.job.TMaterailTypeInfo;
import smtcl.mocs.pojos.job.TPartProcessCost;
import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessEquipment;
import smtcl.mocs.pojos.job.TProcessInfo;
import smtcl.mocs.pojos.job.TProcessmaterialInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.pojos.job.TProductionEvents;
import smtcl.mocs.pojos.job.TProductionScrapInfo;
import smtcl.mocs.services.device.IPartService;
import smtcl.mocs.services.erp.IERPSerice;
import smtcl.mocs.utils.device.Constants;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.StringUtils;

@Transactional
public class ERPServiceImpl implements IERPSerice {
	
	/**
	 * erp���commonDao����
	 */
	protected ICommonDao erpCommonDao;
	public ICommonDao getErpCommonDao() {
		return erpCommonDao;
	}
	public void setErpCommonDao(ICommonDao erpCommonDao) {
		this.erpCommonDao = erpCommonDao;
	}
	
	private ICommonDao commonDao;
	public ICommonDao getCommonDao() {
		return commonDao;
	}
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}
	
	private IPartService partService;
	public IPartService getPartService() {
		return partService;
	}
	public void setPartService(IPartService partService) {
		this.partService = partService;
	}

	
	/**
	 * ���� TProductionScrapInfo����
	 * ���򱨷���Ϣ�ش�
	 * @param tps  				���϶���
	 * @param wy   				Ψһ��ʶ
	 * @param processScrapNum   ��������
	 * @param materialScrapNum	�Ϸ�����
	 * @param totleTime			������ʱ
	 * @param totlePrice		��Э����
	 * @param scrapType			��������
	 * @return
	 */
	public String saveWisQaScrap(TProductionScrapInfo tps,String wy,String processScrapNum,String materialScrapNum,
			String totleTime,String totlePrice,int scrapType){
		String hql="from WisQaScrap where id.tzdCode='"+tps.getTzdCode()+"'";
		List list=erpCommonDao.executeQuery(hql);
		if(null!=list&&list.size()>0){
			return "�Ѵ���";
		}
		WisQaScrap bgftps=new WisQaScrap();
		WisQaScrapId bgftpsid=new WisQaScrapId();
		
		if(scrapType==1){
			bgftpsid.setTzdCode("BF_"+tps.getTzdCode()+"_1");
			bgftpsid.setZrOperationNum(tps.getZrOperationNum());
			bgftpsid.setQuantity(processScrapNum);
			bgftpsid.setFpType("����");
			bgftpsid.setHour((Integer.parseInt(totleTime)*Integer.parseInt(processScrapNum))+"");
		}else if(scrapType==4){
			bgftpsid.setTzdCode("BF_"+tps.getTzdCode()+"_2");
			bgftpsid.setZrOperationNum("0");
			bgftpsid.setQuantity(materialScrapNum);	
			bgftpsid.setFpType("�Ϸ�");
			bgftpsid.setHour((Integer.parseInt(totleTime)*Integer.parseInt(materialScrapNum))+"");
		}
	
		bgftpsid.setEntityName(tps.getEntityName());;
		bgftpsid.setItemCode(tps.getItemCode());
		bgftpsid.setItemDesc(tps.getItemDesc());
		bgftpsid.setToOperationNum(tps.getToOperationNum());
		bgftpsid.setResponser(tps.getResponser());
		bgftpsid.setGd(tps.getGd());
		bgftpsid.setChecker(tps.getCheckOperation());
		bgftpsid.setInvoiceDate(StringUtils.formatDate(tps.getInvoiceDat(),2));
		bgftpsid.setReson(tps.getReson());
		bgftpsid.setVendor(tps.getVendor());
		bgftpsid.setQaResult(tps.getResult());
		bgftpsid.setUploadDate(new Date());
		bgftpsid.setReqQaId(wy);
		
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
     	Map<String, String> map=StringUtils.getUrlForAll(request.getServletContext(), "ErpParm.properties", null);
     	String orgId=StringUtils.getUrlForOne(request.getServletContext(),"ErpParm.properties","erp.organization_id");
     	 
		bgftpsid.setOrganizationId(Long.parseLong(orgId));
		bgftpsid.setFlag(Long.parseLong("0"));
		bgftpsid.setOutsourcingFee(totlePrice);
		bgftps.setId(bgftpsid);
		erpCommonDao.save(WisQaScrap.class, bgftps);
		return "����ɹ�";
	}

	/**
	 * ���� �ش���
	 * WisTransfer
	 * @param tps
	 * @param resourceCode  ��Դ����
	 * @param SumScrapNum ��������
	 * @return
	 */
	public String saveWisTransfer(TProductionScrapInfo tps,String resourceCode,String SumScrapNum,String wy,String scrapUserName){
			WisTransfer wistf=new WisTransfer();
			WisTransferId wistfi=new WisTransferId();
			wistfi.setEntityName(tps.getEntityName());
			wistfi.setOperationNum(Long.parseLong(tps.getToOperationNum()));
			wistfi.setUploadDate(new Date());
			
			wistf.setBfAccount(scrapUserName);
			wistf.setId(wistfi);
			wistf.setQuantity(Long.parseLong(SumScrapNum));
			wistf.setIntraoperationStep(Long.parseLong("5"));
			wistf.setCmpFlag(Long.parseLong("0"));
			wistf.setFlag(Long.parseLong("0"));
			
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();		
	     	Map<String, String> map=StringUtils.getUrlForAll(request.getServletContext(), "ErpParm.properties", null);
	     	String orgId=StringUtils.getUrlForOne(request.getServletContext(),"ErpParm.properties","erp.organization_id");
	     	
			wistf.setOrganizationId(Long.parseLong(orgId));
			wistf.setReqQaId(wy);
			erpCommonDao.save(WisTransfer.class, wistf);
			return "����ɹ�";
	}
	
	/**
	 * ����ʱ�� �����������ѯ����
	 * @param startTime 
	 * @param endTime
	 * @param ItemCode
	 * @return
	 */
	public List<Map<String,Object>> getErpPartTypeList(Date startTime,Date endTime,String ItemCode){
//		String hql="select new Map("
//				+ ""
//				+ " a.id.itemId as Id,"
//				+ " a.itemCode as itemCode,"
//				+ " a.itemDesc as itemDesc,"
//				+ " a.planningMakeBuyCode as planningMakeBuyCode,"
//				+ " a.flag as flag)"
//				+ " from WisItem a where 1=1 ";
		String sql="select a.Id,a.itemCode,a.itemDesc,a.planningMakeBuyCode,a.flag "
				+ " from ("
				+ "			select rank() over(partition by item_code order by upload_date desc) as rankNum,"
				+ "				item_id as Id,item_code as itemCode,item_desc as itemDesc,planning_Make_Buy_Code as planningMakeBuyCode,"
				+ "             flag as flag,upload_date as uploadDate"
				+ "			from wis_item ) a "
				+ " where a.ranknum=1 ";
		if(null!=ItemCode&&!"".equals(ItemCode)){
			sql+=" and a.itemCode='"+ItemCode+"'";
		}
		if(null!=startTime&&!"".equals(startTime.toString())){
			sql+=" and a.id.uploadDate>=to_date('"+StringUtils.formatDate(startTime, 3)+"','yyyy-mm-dd HH24:mi:ss')";
		}
		if(null!=endTime&&!"".equals(endTime.toString())){
			sql+=" and a.id.uploadDate<=to_date('"+StringUtils.formatDate(endTime,3)+"','yyyy-mm-dd HH24:mi:ss')";
		}
		sql+=" order by a.itemCode";
//		return erpCommonDao.executeQuery(hql);
		return erpCommonDao.executeNativeQuery(sql);
	}
	/**
	 * �������id ��ȡ����
	 * @param ItemId
	 * @return
	 */
	public List<Map<String,Object>> getErpProcess(String ItemId){
		String sql="select max(seq_id) as SEQID from WIS_ROUTE";
		List<Map<String,Object>> seqidlist=erpCommonDao.executeNativeQuery(sql);
		String seq=null;
		if(null!=seqidlist&&seqidlist.size()>0){
			Map<String,Object> seqMap=seqidlist.get(0);
			seq=seqMap.get("SEQID")+"";
		}
		String hql="select new Map("
				+ " a.id.assemblyItemId as partId,"
				+ " a.id.operationNum as processNum,"
				+ " a.id.operationDesc as processName)"
				+ " from WisRoute a "
				+ " where a.id.assemblyItemId='"+ItemId+"'";
		if(null!=seq){
				hql+=" and a.id.seqId='"+seq+"'";
		}
			hql+= " order by a.id.operationNum";
		return erpCommonDao.executeQuery(hql);
	}
	
	
	/**
	 * ��ȡ�ɱ�
	 * @param ItemId ���id
	 * @param processNum �����
	 * @return
	 */
	public Map<String,Object> getErpCost(String ItemId,String processNum){
		DecimalFormat df=(DecimalFormat)NumberFormat.getInstance(); 
			df.setMaximumFractionDigits(2); 
		Map<String,Object> rs=new HashMap<String, Object>();
		String sqlseq="select max(seq_id) as SEQID from Wis_Bom";
		List<Map<String,Object>> seqidlist=erpCommonDao.executeNativeQuery(sqlseq);
		String seq=null;
		if(null!=seqidlist&&seqidlist.size()>0){
			Map<String,Object> seqMap=seqidlist.get(0);
			seq=seqMap.get("SEQID")+"";
		}
		
		String sql="select a.price,a.componentCode,a.operationNum "
				+ "	from (select b.component_cost as price,b.component_code as componentCode,b.operation_num as operationNum ,"
				+ "			b.item_id as itemId,b.seq_id as seqId,"
				+ "			rank() over(partition by a.item_code order by a.upload_date desc) as rankNum"
				+ "			from wis_item a,wis_bom b"
				+ "			where a.item_id=b.item_id ) a "
				+ " where rankNum=1 and a.itemId="+ItemId+"";
		if(null!=seq){
			sql+= " and a.seqId='"+seq+"' ";
		}
			sql+= " order by a.price desc ";

		List<Map<String,Object>> zflist=erpCommonDao.executeNativeQuery(sql);
		String zl="0";//����
		String fl="0";//����
		
		for(int i=0;i<zflist.size();i++){
			Map<String,Object> zfmap=zflist.get(i);
			String opernum=zfmap.get("OPERATIONNUM").toString();
			String price=null==zfmap.get("PRICE")?"0":zfmap.get("PRICE").toString();
			if(i==0){
				if(opernum.equals(processNum)){
					zl=(Double.parseDouble(price))+"";
				}else{
					zl=(Double.parseDouble("0"))+"";
				}
				
			}else{
				if(opernum.equals(processNum)){
					fl=(Double.parseDouble(fl)+Double.parseDouble(price))+"";
				}
				
			}
		}
		rs.put("zl", df.format(Double.parseDouble(zl)));
		rs.put("fl", df.format(Double.parseDouble(fl)));
		
		String sqlseq2="select max(seq_id) as SEQID from wis_op_resource";
		List<Map<String,Object>> seqidlist2=erpCommonDao.executeNativeQuery(sqlseq2);
		String seq2=null;
		if(null!=seqidlist2&&seqidlist2.size()>0){
			Map<String,Object> seqMap2=seqidlist2.get(0);
			seq2=seqMap2.get("SEQID")+"";
		}
		String sql2="select  a.rate,a.resourceType,a.usageRateOrAmount "
				+ " from (select b.rate as rate,b.resource_type as resourceType,"
				+ "			a.usage_rate_or_amount as usageRateOrAmount,a.assembly_item_id as assemblyItemId,"
				+ "			a.operation_num as operationNum,a.seq_id as seqId,"
				+ "			rank() over(partition by b.resource_code order by b.upload_date desc) as rankNum  "
				+ "		  from wis_op_resource a,wis_resource b"
				+ "		  where  a.resource_code=b.resource_code and b.resource_type=1) a "
				+ " where a.rankNum=1 "
				+ " and a.assemblyItemId="+ItemId+""
				+ " and a.operationNum='"+processNum+"'";
		if(null!=seq2){
			 sql2+=" and a.seqId='"+seq2+"'";
		}
		
		List<Map<String,Object>> rycblist=erpCommonDao.executeNativeQuery(sql2);
		String ry="0";//��Դ
		String zj="0";//�۾�
		for(Map<String,Object> rycbMap:rycblist){
			String usage=null==rycbMap.get("USAGERATEORAMOUNT")?"0":rycbMap.get("USAGERATEORAMOUNT").toString();
			String rate=null==rycbMap.get("RATE")?"0":rycbMap.get("RATE").toString();
			if(rycbMap.get("RESOURCETYPE").toString().equals("2")){
				ry=(Double.parseDouble(usage)*
						Double.parseDouble(rate))+"";
			}else if(rycbMap.get("RESOURCETYPE").toString().equals("1")){
				zj=(Double.parseDouble(usage)*Double.parseDouble(rate))+"";
			}
		}
		rs.put("ry", df.format(Double.parseDouble(ry)));
		rs.put("zj", df.format(Double.parseDouble(zj)));
		rs.put("ny", df.format(Double.parseDouble("0")));
		rs.put("zy", df.format(Double.parseDouble("0")));
		return rs;
	}
	
	/**
	 * ���ݹ����ȡ����
	 * @param partId
	 * @return
	 */
	public List<Map<String,Object>> getErpMaterail(String partId,String processNum){
		String sql="select max(seq_id) as SEQID from Wis_Bom";
		List<Map<String,Object>> seqidlist=erpCommonDao.executeNativeQuery(sql);
		String seq=null;
		if(null!=seqidlist&&seqidlist.size()>0){
			Map<String,Object> seqMap=seqidlist.get(0);
			seq=seqMap.get("SEQID")+"";
		}
		String hql="select distinct new Map("
				+ " a.itemCode as itemCode,"
				+ " a.itemDesc as itemDesc,"
				+ " a.id.itemId as MaterailId,"
				+ " a.uomDesc as uomDesc,"
				+ " b.componentCost as MaterailCost,"
				+ " b.id.componentCode as componentCode,"
				+ " b.componentDesc as componentDesc,"
				+ " b.componentQuantity as componentQuantity)"
				+ " from WisItem a,WisBom b,WisRoute c "
				+ " where a.id.itemId=b.id.itemId "
				+ " and b.id.operationNum=c.id.operationNum "
				+ " and b.id.itemId='"+partId+"'"
				+ " and b.id.operationNum="+processNum;
		if(null!=seq){
			hql+="  and b.id.seqId='"+seq+"'";
		}
			hql+="  order by b.componentCost desc";
		return erpCommonDao.executeQuery(hql);
	}

	/**
	 * ��ȡ��̨����
	 * @param ItemId
	 * @param processNum
	 * @return
	 */
	public List<Map<String,Object>> getErpmachine (String ItemId,String processNum){
		String sqlseq="select max(seq_id) as SEQID from wis_op_resource";
		List<Map<String,Object>> seqidlist=erpCommonDao.executeNativeQuery(sqlseq);
		String seq=null;
		if(null!=seqidlist&&seqidlist.size()>0){
			Map<String,Object> seqMap=seqidlist.get(0);
			seq=seqMap.get("SEQID")+"";
		}
		String sql="select  a.resourceCode,a.resourceDesc,a.usageRateOrAmount "
				+ " from (select b.resource_code as resourceCode,b.resource_desc as resourceDesc,"
				+ "			a.usage_rate_or_amount as usageRateOrAmount,a.assembly_item_id as assemblyItemId,"
				+ "			a.operation_num as operationNum,a.seq_id as seqId,"
				+ "			rank() over(partition by b.resource_code order by b.upload_date desc) as rankNum  "
				+ "		  from wis_op_resource a,wis_resource b"
				+ "		  where  a.resource_code=b.resource_code and b.resource_type=1) a "
				+ " where a.rankNum=1 "
				+ " and a.assemblyItemId="+ItemId+""
				+ " and a.operationNum='"+processNum+"'";
		if(null!=seq){
			 sql+=" and a.seqId='"+seq+"'";
		}
		//return erpCommonDao.executeQuery(hql);
		return erpCommonDao.executeNativeQuery(sql);
	}
	/**
	 * ���������� ����� ��ȡ �ӹ�ʱ�� �������ֵ���Ϣ
	 * @param ItemCode
	 * @param processNum
	 * @return
	 */
	public List<Map<String,Object>> getProcessData(String ItemCode,String processNum){
		String sqlseq1="select max(seq_id) as SEQID from WIS_ROUTE";
		String sqlseq2="select max(seq_id) as SEQID from wis_op_resource";
		List<Map<String,Object>> seqidlist=erpCommonDao.executeNativeQuery(sqlseq1);
		List<Map<String,Object>> seqidlist2=erpCommonDao.executeNativeQuery(sqlseq2);
		String seq=null;
		String seq2=null;
		if(null!=seqidlist&&seqidlist.size()>0){
			Map<String,Object> seqMap=seqidlist.get(0);
			seq=seqMap.get("SEQID")+"";
		}
		if(null!=seqidlist2&&seqidlist2.size()>0){
			Map<String,Object> seqMap2=seqidlist2.get(0);
			seq2=seqMap2.get("SEQID")+"";
		}
		String sql="select a.operationNum,a.operationDesc ,a.usageRateOrAmount,a.itemId  "
				+ " from (select distinct  a.operation_num as operationNum,a.operation_desc as operationDesc,c.item_code as itemCode,"
				+ "			b.usage_rate_or_amount as usageRateOrAmount,c.item_id as itemId,a.seq_id as routeSeqId,b.seq_id as opSeqId,"
				+ "			rank() over(partition by c.item_code order by c.upload_date desc) as rankNum"
				+ "			from  Wis_Route a, wis_op_resource b, Wis_Item c ,wis_resource d"
				+ "			where a.operation_num=b.operation_num and a.assembly_item_id=c.item_id and b.resource_code=d.resource_code"
				+ "			and d.resource_type=1) a"
				+ " where a.rankNum=1 and a.itemCode='"+ItemCode+"'"
				+ " and a.operationNum='"+processNum+"'";
		if(null!=seq){
			 sql+=" and a.routeSeqId='"+seq+"'";
		}
		if(null!=seq2){
			 sql+=" and a.opSeqId='"+seq2+"'";
		}
		return erpCommonDao.executeNativeQuery(sql);
	}
	/*
	 * *
	 * ���������� ����� ��ȡ �ӹ�ʱ�� �������ֵ���Ϣ
	 * @param ItemCode
	 * @param processNum
	 * @return
	 */
	public List<Map<String,Object>> getProcessData2(String ItemCode,String processNum){
		String sqlseq1="select max(seq_id) as SEQID from WIS_ROUTE";
		List<Map<String,Object>> seqidlist=erpCommonDao.executeNativeQuery(sqlseq1);
		String seq=null;
		if(null!=seqidlist&&seqidlist.size()>0){
			Map<String,Object> seqMap=seqidlist.get(0);
			seq=seqMap.get("SEQID")+"";
		}
		String sql="select a.operationNum,a.operationDesc ,a.itemId  "
				+ " from (select distinct  a.operation_num as operationNum,a.operation_desc as operationDesc,c.item_code as itemCode,"
				+ "			c.item_id as itemId,a.seq_id as routeSeqId,"
				+ "			rank() over(partition by c.item_code order by c.upload_date desc) as rankNum"
				+ "			from  Wis_Route a, Wis_Item c "
				+ "			where  a.assembly_item_id=c.item_id "
				+ "			) a"
				+ " where a.rankNum=1 and a.itemCode='"+ItemCode+"'"
				+ " and a.operationNum='"+processNum+"'";
		if(null!=seq){
			 sql+=" and a.routeSeqId='"+seq+"'";
		}
		return erpCommonDao.executeNativeQuery(sql);
	}
	/**
	 * �������ݵ�wis
	 * @param selectedErpRoot
	 * @param nodeid
	 * @return
	 */
	public String saveWisData(TreeNode[] selectedErpRoot,String nodeid){
		String mesg="";
		try {
			
			String itemCod="";
			String itemId="";
			List<String> processNumlist=new ArrayList<String>();
			mesg="��ȡ������������";
			for(TreeNode node:selectedErpRoot){
				Map<String,Object> nodeMap=(Map<String,Object>)node.getData();
				System.out.println(nodeMap.get("id"));
				if(!"test".equals(nodeMap.get("id").toString())){
					String[] zj=nodeMap.get("id").toString().split(",");
					 itemCod=zj[0];//�������
					String processNum=zj[1];//�����
					processNumlist.add(processNum);
//					
				}
			}
			if(null!=itemCod&&!"".equals(itemCod)){
	/*----------------------------------------------------���շ����������start--------------------------------------------------------*/
				mesg="��ȡ���շ�������";
				List<TProcessplanInfo> rs= this.getTProcessplanInfo(itemCod, null);//��ȡ���շ���
				TProcessplanInfo addPlal=new TProcessplanInfo();//Ҫ��ӵĹ��շ���
				if(null==rs||rs.size()<1){//���ù��շ������ɹ���
					addPlal.setName((itemCod+"_01"));
				}else{
					TProcessplanInfo tpi=rs.get(rs.size()-1);
					String name=tpi.getName();
					int tt=name.lastIndexOf("_");
					String num=name.substring(tt+1,name.length());
					System.out.println(num);
					 DecimalFormat d=new DecimalFormat("00");
					addPlal.setName((itemCod+"_"+d.format((Integer.parseInt(num)+1))));
				}
				//Ϊ���շ������һЩ��Ҫ������
				TUser userinfo=(TUser)FaceContextUtil.getSessionMap().get(Constants.USER_SESSION_KEY);
				//taddPlal.setOperator(userinfo.getLoginName());
				Date date=new Date();
				addPlal.setCreateDate(date);
				addPlal.setStatus(0);
				addPlal.setDefaultSelected(0);
				addPlal.setNodeid(nodeid);
				List<TPartTypeInfo> tPartTypeInfo=this.getTPartTypeInfoByNo(itemCod);
				TPartTypeInfo part=tPartTypeInfo.get(0);
				addPlal.setTPartTypeInfo(part);
				mesg="��ӹ��շ�������";
				String gyfamesg=this.saveTProcessplanInfo(addPlal); //�������շ���
				this.updateDeDefaultTProcessplanInfo(addPlal);//��ΪĬ��
				if(gyfamesg.equals("0")){
					mesg="���빤�շ�������";
				}
				List<String> wlidlist=new ArrayList<String>();
		/*----------------------------------------------------���շ����������end--------------------------------------------------------*/
				Long qProcessNum=null;
				for(int i=0;i<processNumlist.size();i++){
					String processNum=processNumlist.get(i);
					/*----------------------------------------------------����start--------------------------------------------------------*/			
					TProcessInfo process=new TProcessInfo();
					process.setNo(addPlal.getName()+"_"+(i+1));
					process.setProgramId(Long.parseLong("0"));
					process.setTheoryWorktime(0);
					process.setInstallTime(0);
					process.setUninstallTime(0);
					process.setTProcessplanInfo(addPlal);
					process.setProcessDuration(0);
					process.setNeedcheck(0);
					process.setProcessType(2);
					process.setStatus(0);
					process.setNodeid(nodeid);
					//����ǰ����
					process.setOnlineProcessId(qProcessNum);
					
					//����Ϊβ��
					if(i==processNumlist.size()-1){
						process.setOfflineProcess(1);
					}else{
						process.setOfflineProcess(0);
					}

					
					List<Map<String,Object>> erpProcesslist=this.getProcessData2(itemCod, processNum);
					if(null!=erpProcesslist&&erpProcesslist.size()>0){
						Map<String,Object> erpProcessMap=erpProcesslist.get(0);
						itemId=erpProcessMap.get("ITEMID").toString();
						process.setProcessOrder(Integer.parseInt(processNum));
						process.setName(erpProcessMap.get("OPERATIONDESC").toString());
						List<Map<String,Object>> erpProcesslist2=this.getProcessData(itemCod, processNum);
						if(null!=erpProcesslist2&&erpProcesslist2.size()>0){
							Map<String,Object> erpProcessMap2=erpProcesslist2.get(0);
							double ratecount=0;
							if(Double.parseDouble(erpProcessMap2.get("USAGERATEORAMOUNT").toString())*3600-60>0){
								ratecount=Double.parseDouble(erpProcessMap2.get("USAGERATEORAMOUNT").toString())*3600-60;
							}
							process.setProcessingTime(ratecount);
							String rate=Double.parseDouble(erpProcessMap2.get("USAGERATEORAMOUNT").toString())*3600+"";
							if(rate.indexOf(".")>-1){
								int num=rate.indexOf(".");
								rate=rate.substring(0, num)+"";
							}
							process.setTheoryWorktime(Integer.parseInt(rate));
						}
					}else{
						process.setProcessingTime(Double.parseDouble("0"));
						process.setTheoryWorktime(Integer.parseInt("0"));
						process.setProcessOrder(Integer.parseInt(processNum));
						process.setName("");
					}
					mesg="��ӹ��򱨴�";
					commonDao.save(TProcessInfo.class, process);
					qProcessNum=process.getId();
					/*----------------------------------------------------����end--------------------------------------------------------*/
					/*----------------------------------------------------�ɱ�start--------------------------------------------------------*/
					 Map<String,Object> erpcbMap=this.getErpCost(itemId, processNum);
					 List<TPartTypeInfo> wispart=this.getTPartTypeInfoByNo(itemCod);
					 TPartProcessCost wiscb=new TPartProcessCost();
					 wiscb.setDeviceCost(Double.parseDouble(erpcbMap.get("zj").toString().replace(",", "")));//�豸�۾�
					 wiscb.setEnergyCost(Double.parseDouble(erpcbMap.get("ny").toString().replace(",", "")));//��Դ�ɱ�
					 wiscb.setMainMaterialCost(Double.parseDouble(erpcbMap.get("zl").toString().replace(",", "")));//���ϳɱ�
					 wiscb.setPeopleCost(Double.parseDouble(erpcbMap.get("ry").toString().replace(",", "")));//��Ա�ɱ�
					 wiscb.setResourceCost(Double.parseDouble(erpcbMap.get("zy").toString().replace(",", "")));//��Դ����
					 wiscb.setSubsidiaryMaterialCost(Double.parseDouble(erpcbMap.get("fl").toString().replace(",", "")));//���ϳɱ�
					 wiscb.setTProcessInfo(process);
					 if(null!=wispart&&wispart.size()>0){
						 wiscb.setTPartTypeInfo(wispart.get(0));
					 }
					 mesg="��ӳɱ�����";
					 commonDao.save(TPartProcessCost.class, wiscb);
					/*----------------------------------------------------�ɱ�end--------------------------------------------------------*/
					/*----------------------------------------------------����start--------------------------------------------------------*/
					 List<Map<String,Object>> erpwlllist=this.getErpMaterail(itemId, processNum);
					 if(null!=erpwlllist&&erpwlllist.size()>0){
						 for(Map<String,Object> erpwlMap:erpwlllist){
							 String hql="from TMaterailTypeInfo where no='"+erpwlMap.get("componentCode").toString()+"' or id='"+itemId+"'";
							 List<TMaterailTypeInfo> tmlist=commonDao.executeQuery(hql);
							 TMaterailTypeInfo wiswl=new TMaterailTypeInfo();
							 if(null!=tmlist&&tmlist.size()>0){
								 wiswl=tmlist.get(0);
								 //wiswl.setId(Long.parseLong(itemId));
								 wiswl.setNo(erpwlMap.get("componentCode").toString());
								 wiswl.setName(erpwlMap.get("componentDesc").toString());
								 wiswl.setUnit(erpwlMap.get("uomDesc").toString());
								 wiswl.setPrice(Double.parseDouble(null==erpwlMap.get("MaterailCost")?"0":erpwlMap.get("MaterailCost").toString()));
								 wiswl.setPId(-999);
								 wiswl.setStatus(0);
								 wiswl.setNodeId(nodeid);
								 mesg="������ϱ���";
								 commonDao.update(TMaterailTypeInfo.class, wiswl);
							 }else{
								 //wiswl.setId(Long.parseLong(itemId));
								 wiswl.setNo(erpwlMap.get("componentCode").toString());
								 wiswl.setName(erpwlMap.get("componentDesc").toString());
								 wiswl.setUnit(erpwlMap.get("uomDesc").toString());
								 wiswl.setPrice(Double.parseDouble(null==erpwlMap.get("MaterailCost")?"0":erpwlMap.get("MaterailCost").toString()));
								 wiswl.setPId(-999);
								 wiswl.setStatus(0);
								 wiswl.setNodeId(nodeid);
								 mesg="������ϱ���";
								 commonDao.save(TMaterailTypeInfo.class, wiswl);
							 }
							
								 TProcessmaterialInfo wispzj=new TProcessmaterialInfo();//���������м��
								 wispzj.setTMaterailTypeInfo(wiswl);
								 wispzj.setTProcessInfo(process);
								 wispzj.setRequirementNum(Integer.parseInt(erpwlMap.get("componentQuantity").toString()));
								 wispzj.setStatus(0);
								 commonDao.save(TProcessmaterialInfo.class, wispzj);
								 wlidlist.add(wispzj.getId().toString());
						 }
					 }
					/*----------------------------------------------------����end--------------------------------------------------------*/
					/*----------------------------------------------------��̨start--------------------------------------------------------*/
					 List<Map<String,Object>> jtlist=this.getErpmachine(itemId, processNum);
					 if(null!=jtlist&&jtlist.size()>0){
						 Map<String,Object> jtMap=jtlist.get(0);
						 String equtypehql="from TEquipmenttypeInfo  where erpResouceCode='"+jtMap.get("RESOURCECODE")+"'";
						 List<TEquipmenttypeInfo> equtypelist=commonDao.executeQuery(equtypehql);
						 if(null!=equtypelist&&equtypelist.size()>0){
							 TEquipmenttypeInfo te=(TEquipmenttypeInfo)equtypelist.get(0);
							 TProcessEquipment tpe=new TProcessEquipment();
							 tpe.setEquipmentTypeId(te.getId());
							 tpe.setProcessId(process.getId());
							 commonDao.save(TProcessEquipment.class, tpe);
						 }else{
							this.getTEquipmenttypeInfoAndSave(process);
						 }
					 }else{
						 this.getTEquipmenttypeInfoAndSave(process); 
					 }
					/*----------------------------------------------------��̨start--------------------------------------------------------*/
					process=null;
				}
				/*----------------�趨������------------------*/
				String hqltpi="from TProcessmaterialInfo a where a.id in("+StringUtils.returnHqlIN(wlidlist)+")"
						+ " order by a.TMaterailTypeInfo.price desc";
				List<TProcessmaterialInfo> tpilist=commonDao.executeQuery(hqltpi);
				if(null!=tpilist&&tpilist.size()>0){
					for(int i=0;i<tpilist.size();i++){
						TProcessmaterialInfo tpi=tpilist.get(i);
						if(i==0){
							tpi.setRequirementType("����");
						}else{
							tpi.setRequirementType("����");
						}
						commonDao.update(TProcessmaterialInfo.class, tpi);
					}
				}
			}
			mesg="��ӳɹ�";
			return mesg;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	public void getTEquipmenttypeInfoAndSave(TProcessInfo process){
		 String hqlequ="from TEquipmenttypeInfo where erpResouceCode='WIS_M'"; 
		
		 List<TEquipmenttypeInfo> equtypelist=commonDao.executeQuery(hqlequ);
		 TEquipmenttypeInfo teti=equtypelist.get(0);
		 TProcessEquipment tpe=new TProcessEquipment();
		 tpe.setEquipmentTypeId(teti.getId());
		 tpe.setProcessId(process.getId());
		 commonDao.save(TProcessEquipment.class, tpe);
	}
	/**
	 * ��ȡ��������
	 */
	public List<Map<String,Object>> getErpMaterailSelect(String isselect,String search,int pOrm){
		List<Map<String,Object>> rs=new ArrayList<Map<String,Object>>();
		String hql="select distinct new Map("
				+ " a.itemCode as itemCode,"
				+ " a.itemDesc as itemDesc,"
				+ " a.id.itemId as MaterailId,"
				+ " a.uomDesc as uomDesc,"
				+ " b.componentCost as MaterailCost,"
				+ " b.id.componentCode as componentCode,"
				+ " b.componentDesc as componentDesc,"
				+ " b.componentQuantity as componentQuantity)"
				+ " from WisItem a,WisBom b"
				+ " where a.id.itemId=b.id.itemId ";
		String searchHql="";
		if(pOrm==1){
			searchHql="select new Map(a.no as no) from TMaterailTypeInfo a";	
		}else if(pOrm==2){
			searchHql="select new Map(a.no as no) from TPartTypeInfo a";
			hql="select distinct new Map("
					+ " a.itemCode as itemCode,"
					+ " a.itemDesc as itemDesc,"
					+ " a.id.itemId as MaterailId,"
					+ " a.uomDesc as uomDesc)"
					+ " from WisItem a"
					+ " where 1=1";
		}
		if(isselect.equals("0")){
			List<Map<String,Object>> erprs= commonDao.executeQuery(searchHql);
			
			List<String> mlist=new ArrayList();
			for(Map<String,Object> wismap:erprs){
				String itemCode=wismap.get("no").toString();
				if(!mlist.contains(itemCode)){
					mlist.add(itemCode);
				}		
			}
			hql+=" and a.itemCode not in("+StringUtils.returnHqlIN(mlist)+")";
		}
		if(null!=search&&!"".equals(search)){
			hql+=" and a.itemCode like '%"+search+"%'";
		}
		rs= erpCommonDao.executeQuery(hql);
		return rs;
	}
	/**
	 * ���м�⵼����������
	 * @param milist
	 * @return
	 */
	public String saveMaterailData(List<Map<String,Object>> milist,String nodeid){
		try {
			if(null!=milist&&milist.size()>0){
				for(Map<String,Object> mi:milist){
					TMaterailTypeInfo tmt=new TMaterailTypeInfo();
					tmt.setId(Long.parseLong(mi.get("MaterailId").toString()));
					tmt.setNo(mi.get("itemCode").toString());
					tmt.setName(mi.get("itemDesc").toString());
					tmt.setUnit(mi.get("uomDesc").toString());
					tmt.setPrice(Double.parseDouble(null==mi.get("MaterailCost")?"0":mi.get("MaterailCost").toString()));
					tmt.setPId(-999);
					tmt.setStatus(0);
					tmt.setNodeId(nodeid);
					commonDao.save(TMaterailTypeInfo.class, tmt);
				}
				return "��ӳɹ�";
			}else{
				return "��ѡ����Ҫ���������";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "���ʧ��";
		}
	}
	/**
	 * ���м�⵼���������
	 * @param milist
	 * @return
	 */
	public String savePartData(List<Map<String,Object>> milist,String nodeid){
		try {
			if(null!=milist&&milist.size()>0){
				for(Map<String,Object> mi:milist){
					TPartTypeInfo tmt=new TPartTypeInfo();
					tmt.setId(Long.parseLong(mi.get("MaterailId").toString()));
					tmt.setNo(mi.get("itemCode").toString());
					tmt.setName(mi.get("itemDesc").toString());
					tmt.setStatus("�½�");
					tmt.setNodeid(nodeid);
					commonDao.save(TPartTypeInfo.class, tmt);
				}
				return "��ӳɹ�";
			}else{
				return "��ѡ����Ҫ���������";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "���ʧ��";
		}
	}
	
	/**
	 *  ������������
	 * @param tps ����ʵ����
	 * @param jobdispatchNo ��ǰѡ�еĹ�����
	 * @param isCurrentProcess �Ƿ��������Ѽӹ���
	 * @param processScrapNum ����
	 * @param materialScrapNum �Ϸ�
	 * @param onlineProcessId ǰ������id
	 * @param selectedPartId �������id
	 * @param djgs ������ʱ
	 * @param selectedpl ���򼯺�
	 * @return
	 */
	public String saveTProductionScrapInfo(TProductionScrapInfo tps,String jobdispatchNo,List isCurrentProcess,String processScrapNum,
			String materialScrapNum,String onlineProcessId,String processPlanID,String selectedPartId,String djgs,List selectedpl,
			String scrapUserName){
		String wy=StringUtils.getUniqueString();//Ψһ��ʶ
		processScrapNum=null==processScrapNum||"".equals(processScrapNum)?"0":processScrapNum;
		materialScrapNum=null==materialScrapNum||"".equals(materialScrapNum)?"0":materialScrapNum;
		int SumScrapNum=Integer.parseInt(processScrapNum)+Integer.parseInt(materialScrapNum);//�ܱ�����
		try {
			String tjihql="from TJobdispatchlistInfo where no='"+jobdispatchNo+"'";
			List<TJobdispatchlistInfo> tjilist=commonDao.executeQuery(tjihql);
			TJobdispatchlistInfo tji=new TJobdispatchlistInfo();
			if(null!=tjilist&&tjilist.size()>0){
				tji=tjilist.get(0);//���ҹ���	
			}
			//�������μƻ�
			String jobplansql="from TJobplanInfo where id="+tji.getJobplanId();
			TJobplanInfo tjpi=(TJobplanInfo)commonDao.executeQuery(jobplansql).get(0);
			//����ǰ������
			String onlinehql=" from TJobdispatchlistInfo a"
					  + " where a.TProcessInfo.id='"+onlineProcessId+"'"
					  + " and a.batchNo='"+tji.getBatchNo()+"'"; 
			List<TJobdispatchlistInfo> onlinelist=commonDao.executeQuery(onlinehql);//���� ǰ������
			//������Э����
			String wxfysql="select sum(c.rate) as wxfy from wis_item a,wis_op_resource b,wis_resource c"
					+"	where a.item_id=b.assembly_item_id"
					+"	and b.resource_code=c.resource_code"
					+"	and c.charge_type=3"
					+"	and c.base_type=1"
					+"	and a.item_code='"+tps.getItemCode()+"'"
					+"	and b.operation_num in("+StringUtils.returnHqlIN(selectedpl)+")";
			List<Map<String,Object>> wxfylist=erpCommonDao.executeNativeQuery(wxfysql);
			String totlePrice="0";//��Э����
			if(null!=wxfylist&&wxfylist.size()>0){
				Map<String,Object> wxfyMap=wxfylist.get(0);
				totlePrice=null==wxfyMap.get("wxfy")||"".equals(wxfyMap.get("wxfy").toString())?"0":wxfyMap.get("wxfy").toString();
			}
			
			String resourceCode="test";//��Դ����  �㷨��δʵ��
			if(isCurrentProcess.size()>0){//�ж��Ƿ�ѡ�������Ѽӹ�
				if((tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					return "�����Ա�����:"+tji.getFinishNum();
				}else{
					//���㹤ʱ
					String totleTime=djgs;
					
					//���湤��	
					if(null!=processScrapNum&&!"".equals(processScrapNum)&&!"0".equals(processScrapNum)){
						//--------------------------------------wis ���汨��---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_1'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis�����Ѵ��ڸñ�����Ϣ,���������뱨�ϵ��š�";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, processScrapNum, djgs, selectedPartId, 1);
						}
						//--------------------------------------wis ���汨��---------------------------------------
						//��ӹ�������
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(processScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(processScrapNum));
						tji.setFinishNum(tji.getFinishNum()-Integer.parseInt(processScrapNum));
						//������α���
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(processScrapNum));
						//������α���
						String hql="from TJobdispatchlistInfo a where a.TProcessInfo.processOrder="+tps.getZrOperationNum()+" "
								+ " and a.TProcessInfo.TProcessplanInfo.id="+processPlanID
								+ " and a.batchNo='"+tji.getBatchNo()+"'";
						List<TJobdispatchlistInfo> rstjlist=commonDao.executeQuery(hql);
						if(null!=rstjlist&&rstjlist.size()>0){
							TJobdispatchlistInfo tjob=rstjlist.get(0);
							tjob.setDutyScrapNum(tjob.getDutyScrapNum()+Integer.parseInt(processScrapNum));
							commonDao.update(TJobdispatchlistInfo.class, tjob);
						}
						
						/*-----------------------���浽�м������start-----------------------------*/
						String erpmesg=this.saveWisQaScrap(tps, wy, processScrapNum, materialScrapNum, totleTime, totlePrice, 1);
						if("�Ѵ���".equals(erpmesg)){
							return "�м���Ѵ��ڸñ��ϵ���Ϣ,���������뱨�ϵ��š�";
						}
						/*-----------------------���浽�м������end-----------------------------*/
					}				
					
					if(null!=materialScrapNum&&!"".equals(materialScrapNum)&&!"0".equals(materialScrapNum)){//�����Ϸ�
						//--------------------------------------wis ���汨��---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_2'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis�����Ѵ��ڸñ�����Ϣ,���������뱨�ϵ��š�";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, materialScrapNum, djgs, selectedPartId,4);
						}
						//--------------------------------------wis ���汨��---------------------------------------
						//��ӹ�������
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setFinishNum(tji.getFinishNum()-Integer.parseInt(materialScrapNum));
						//������α���
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum));
						/*-----------------------���浽�м������start-----------------------------*/
						String erpmesg=this.saveWisQaScrap(tps, wy, processScrapNum, materialScrapNum, totleTime, totlePrice, 4);
						if("�Ѵ���".equals(erpmesg)){
							return "�м���Ѵ��ڸñ��ϵ���Ϣ,���������뱨�ϵ��š�";
						}
						/*-----------------------���浽�м������end-----------------------------*/
					}
					//�жϹ����Ƿ����
					if(tji.getProcessNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue()<=0){
						tji.setStatus(70);
						tji.setRealEndtime(new Date());

					}
					//�ж��Ƿ����ǰ�򹤵�
					if(null!=onlinelist&&onlinelist.size()>0){
						TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
						//�жϹ����Ƿ����
						//ǰ�򹤵��������=���򹤵��������+���򹤵��ķ��ֱ���������ǰ�򹤵�״̬��Ϊ���رգ�״̬=������ʱ�����򹤵�����Ϊ�����蹤�˲����������ϲ��ٿɿ���������״̬��Ϊ���
						if((mm.getFinishNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue())<=0&&
								(mm.getStatus()==60||mm.getStatus()==70)){
							tji.setStatus(70);
							tji.setRealEndtime(new Date());

						}
					}
					//���湤��
					commonDao.update(TJobdispatchlistInfo.class,tji);
					//��������
					commonDao.update(TJobplanInfo.class,tjpi);
					//�жϸ������Ƿ�ȫ�����
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//�ж��Ƿ�β��
						if(tp.getOfflineProcess()==1){
							//�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ������Ա�������ɹرո����Σ����ڽ�������ʾ���ݲ��Ը�״̬�����Ϳ��Ʊ仯���ڹر�����ʱ�����鹤�����رգ�״̬=������
							if(tjpi.getPlanNum().intValue()-tjpi.getScrapNum().intValue()-tji.getFinishNum().intValue()<=0){
								String jobdispatchsql="from TJobdispatchlistInfo where jobplanId='"+tjpi.getId()+"'";
								List<TJobdispatchlistInfo> tjobdispathclist=commonDao.executeQuery(jobdispatchsql);
								for(TJobdispatchlistInfo tj:tjobdispathclist){
									if(tj.getStatus()!=60||tj.getStatus()!=70)
									{
										tj.setStatus(70);
										tj.setRealEndtime(new Date());
										commonDao.update(TJobdispatchlistInfo.class, tj);
									}
								}
							}
						}
					}
					/*-----------------------���浽�м������start-----------------------------*/
					String erpmesg=this.saveWisTransfer(tps,resourceCode,SumScrapNum+"",wy,scrapUserName);
					if("�Ѵ���".equals(erpmesg)){
						return "�м���Ѵ��ڸñ��ϵ���Ϣ,���������뱨�ϵ��š�";
					}
					/*-----------------------���浽�м������end-----------------------------*/
				}
			}else{
				//�������ǰ������
				if(null!=onlinelist&&onlinelist.size()>0){
					TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
					String onlinecount=mm.getFinishNum()+"";
					int count=Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum);
					//ǰ���������-��ǰ���������-��ǰ���򱨷���   ����ɱ�������
					if((Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum()-count)<0){
						return "�����Ա�����:"+(Integer.parseInt(onlinecount)-tji.getFinishNum()-tji.getWisScrapNum());
					}
				}
				if((tji.getProcessNum()-tji.getFinishNum()-Integer.parseInt(processScrapNum)-Integer.parseInt(materialScrapNum))<0){
					int count=tji.getProcessNum()-tji.getFinishNum();
					return "�����Ա�����:"+count+"";
				}else{
					//���㹤ʱ
					String totleTime=djgs;
					
					//���湤��	����
					if(null!=processScrapNum&&!"".equals(processScrapNum)&&!"0".equals(processScrapNum)){
						//--------------------------------------wis ���汨��---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_1'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis�����Ѵ��ڸñ�����Ϣ,���������뱨�ϵ��š�";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, processScrapNum, djgs, selectedPartId, 1);
						}
						
						//--------------------------------------wis ���汨��---------------------------------------
						/*-----------------------���浽�м������start-----------------------------*/
						String erpmesg=this.saveWisQaScrap(tps, wy, processScrapNum, materialScrapNum, totleTime, totlePrice, 1);
						if("�Ѵ���".equals(erpmesg)){
							return "�м���Ѵ��ڸñ��ϵ���Ϣ,���������뱨�ϵ��š�";
						}
						/*-----------------------���浽�м������end-----------------------------*/
						//��ӹ�������
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(processScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(processScrapNum));
						//������α���
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(processScrapNum));
						//������α���
						String hql="from TJobdispatchlistInfo a where a.TProcessInfo.processOrder="+tps.getZrOperationNum()+""
								+ " and a.TProcessInfo.TProcessplanInfo.id="+processPlanID
						 	    + " and a.batchNo='"+tji.getBatchNo()+"'"; 
						List<TJobdispatchlistInfo> rstjlist=commonDao.executeQuery(hql);
						if(null!=rstjlist&&rstjlist.size()>0){
							TJobdispatchlistInfo tjobtest=rstjlist.get(0);
							tjobtest.setDutyScrapNum((null==tjobtest.getDutyScrapNum()?0:tjobtest.getDutyScrapNum())+Integer.parseInt(processScrapNum));
							commonDao.update(TJobdispatchlistInfo.class, tjobtest);
						}
					}
					//�����Ϸϴ���
					if(null!=materialScrapNum&&!"".equals(materialScrapNum)&&!"0".equals(materialScrapNum)){
						//--------------------------------------wis ���汨��---------------------------------------
						String wistpesql="from TProductionEvents where eventNo='Bz_"+tps.getTzdCode()+"_2'";
						List wistpelist=commonDao.executeQuery(wistpesql);
						if(null!=wistpelist&&wistpelist.size()>0){
							return "wis�����Ѵ��ڸñ�����Ϣ,���������뱨�ϵ��š�";
						}else{
							this.saveTProductionEvents(tps, jobdispatchNo, materialScrapNum, djgs, selectedPartId,4);
						}
						//--------------------------------------wis ���汨��---------------------------------------
						//��ӹ�������
						tji.setErpScrapNum(tji.getErpScrapNum()+Integer.parseInt(materialScrapNum));
						tji.setWisScrapNum(tji.getWisScrapNum()+Integer.parseInt(materialScrapNum));
						//������α���
						tjpi.setScrapNum((null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum));
						/*-----------------------���浽�м������start-----------------------------*/
						String erpmesg=this.saveWisQaScrap(tps, wy, processScrapNum, materialScrapNum, totleTime, totlePrice, 4);
						/*-----------------------���浽�м������end-----------------------------*/
					}
					
					//�жϹ����Ƿ����
					if(tji.getProcessNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue()<=0){
						tji.setStatus(70);
						tji.setRealEndtime(new Date());

					}
					//�ж��Ƿ����ǰ�򹤵�
					if(null!=onlinelist&&onlinelist.size()>0){
						TJobdispatchlistInfo mm=(TJobdispatchlistInfo)onlinelist.get(0);
						//�жϹ����Ƿ����
						//ǰ�򹤵��������=���򹤵��������+���򹤵��ķ��ֱ���������ǰ�򹤵�״̬��Ϊ���رգ�״̬=������ʱ�����򹤵�����Ϊ�����蹤�˲����������ϲ��ٿɿ���������״̬��Ϊ���
						if((mm.getFinishNum().intValue()-tji.getFinishNum().intValue()-tji.getWisScrapNum().intValue())<=0&&
								(mm.getStatus()==60||mm.getStatus()==70)){
							tji.setStatus(70);
							tji.setRealEndtime(new Date());

						}
					}
					//���湤��
					commonDao.update(TJobdispatchlistInfo.class,tji);
					//��������
					commonDao.update(TJobplanInfo.class,tjpi);
					//�жϸ������Ƿ�ȫ�����
					if(null!=onlinelist&&onlinelist.size()>0){
						String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
						TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
						//�ж��Ƿ�β��
						if(tp.getOfflineProcess()==1){
							//�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ������Ա�������ɹرո����Σ����ڽ�������ʾ���ݲ��Ը�״̬�����Ϳ��Ʊ仯���ڹر�����ʱ�����鹤�����رգ�״̬=������
							if(tjpi.getPlanNum().intValue()-tjpi.getScrapNum().intValue()-tji.getFinishNum().intValue()<=0){
								String jobdispatchsql="from TJobdispatchlistInfo where jobplanId='"+tjpi.getId()+"'";
								List<TJobdispatchlistInfo> tjobdispathclist=commonDao.executeQuery(jobdispatchsql);
								for(TJobdispatchlistInfo tj:tjobdispathclist){
									if(tj.getStatus()!=60||tj.getStatus()!=70)
									{
										tj.setStatus(70);
										tj.setRealEndtime(new Date());
										commonDao.update(TJobdispatchlistInfo.class, tj);
									}

								}
							}
						}
					}
					/*-----------------------���浽�м������start-----------------------------*/
					String erpmesg =this.saveWisTransfer(tps,resourceCode,SumScrapNum+"",wy,scrapUserName);
					/*-----------------------���浽�м������end-----------------------------*/
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return "����ɹ�";
	}
	/**
	 * ��ȡ���γ���
	 * @return
	 */
	public List<Map<String,Object>> getWisVendorListMapForAll(){
		String hql="select new Map("
				+ " a.vendorName as vendorName) "
				+ " from WisVendor a";
		return erpCommonDao.executeQuery(hql);
	}
	/**
	 * ��ȡ�����˻��б�
	 * @return
	 */
	public List<Map<String,Object>> getScrapUser(){
		String hql="select new Map("
				+ " dispositionId as dispositionId,"
				+ " segment1 as segment1)"
				+ " from WisAccount ";
		return erpCommonDao.executeQuery(hql);
	}
	
	public void saveTProductionEvents(TProductionScrapInfo tps,String jobdispatchNo,String processScrapNum,String djgs,
			String selectedPartId,int scriptType){
		TProductionEvents wisTpe=new TProductionEvents();
		
		wisTpe.setJobdispatchNo(jobdispatchNo);
		wisTpe.setProcessNum(Integer.parseInt(processScrapNum));
		TUser user = (TUser) FaceContextUtil.getContext().getSessionMap().get(Constants.USER_SESSION_KEY);
		String sql="SELECT b.no FROM t_user a,t_member_info b"
					+"	WHERE a.memberid=b.id"
					+"	and a.UserID="+user.getUserId();
		List<Map<String,Object>> userlist=commonDao.executeNativeQuery(sql);
		if(null!=userlist&&userlist.size()>0){
			Map<String,Object> userMap=userlist.get(0);
			wisTpe.setOperatorNo(userMap.get("no").toString());//���˺�
		}
		wisTpe.setWorkTime(Integer.parseInt(djgs));
		wisTpe.setOperateDate(new Date());
		wisTpe.setOperateReason(tps.getReson());
		wisTpe.setPartTypeID(Integer.parseInt(selectedPartId));
		if(scriptType==1){
			wisTpe.setEventNo("BF_"+tps.getTzdCode()+"_1");
			String sql2="SELECT b.no FROM t_user a,t_member_info b"
					+"	WHERE a.memberid=b.id"
					+"	and a.UserID="+tps.getResponser();
			List<Map<String,Object>> userlist2=commonDao.executeNativeQuery(sql);
			if(null!=userlist2&&userlist2.size()>0){
				Map<String,Object> userMap=userlist2.get(0);
				//�����˹���
				wisTpe.setResponseNo(userMap.get("no").toString());
			}
			wisTpe.setEventType(1);//����Ϊ����
		}else if(scriptType==4){
			wisTpe.setEventNo("BF_"+tps.getTzdCode()+"_2");
			//���γ���
			wisTpe.setResponseNo(tps.getVendor());
			wisTpe.setEventType(4);//����Ϊ�Ϸ�
		}
		wisTpe.setResponseProcessNo(tps.getZrOperationNum());
		commonDao.save(TProductionEvents.class, wisTpe);
	}
	/**
	 * ��ȡ������ʾ��Ϣ
	 * @param jobdispatchNo
	 * @param onlineProcessId
	 * @param processScrapNum
	 * @param materialScrapNum
	 * @param isCurrentProcess
	 * @return
	 */
	
	public String getJobdispatchTSXX(String jobdispatchNo,String onlineProcessId,String processScrapNum,String materialScrapNum,
			List isCurrentProcess){
		try {
			String tjihql="from TJobdispatchlistInfo where no='"+jobdispatchNo+"'";
			List<TJobdispatchlistInfo> tjilist=commonDao.executeQuery(tjihql);
			TJobdispatchlistInfo tji=new TJobdispatchlistInfo();
			if(null!=tjilist&&tjilist.size()>0){
				tji=tjilist.get(0);//���ҹ���	
			}
			//�������μƻ�
			String jobplansql="from TJobplanInfo where id="+tji.getJobplanId();
			TJobplanInfo tjpi=(TJobplanInfo)commonDao.executeQuery(jobplansql).get(0);
			
			String processsql="from TProcessInfo where id="+tji.getTProcessInfo().getId();
			TProcessInfo tp=(TProcessInfo)commonDao.executeQuery(processsql).get(0);
			//�ж��Ƿ�β��
			if(tp.getOfflineProcess()==1){
				//�����ε��ܷ��ֱ�������β��������֮�͵������μƻ�����ʱ������Ա�������ɹرո����Σ����ڽ�������ʾ���ݲ��Ը�״̬�����Ϳ��Ʊ仯���ڹر�����ʱ�����鹤�����رգ�״̬=������
				int pc=(null==tjpi.getScrapNum()?0:tjpi.getScrapNum())+Integer.parseInt(materialScrapNum)+Integer.parseInt(processScrapNum);
				if(tjpi.getPlanNum()-pc-tji.getFinishNum()<=0){
					return "�رչ���";
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return "��������";
	}
	/**
	 * ���������Ż�ȡ���շ���
	 * @param no
	 * @return
	 */
	public List<TProcessplanInfo> getTProcessplanInfo(String no,String parm){
		String hql="from TProcessplanInfo a where a.TPartTypeInfo.no='"+no+"' and status=0";
		if(null!=parm&&!"".equals(parm)){
			hql=hql+" and name like '%"+parm+"%'";
		}
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<TProcessplanInfo> rs=commonDao.executeQuery(hql, parameters);
		return rs;
	}
	/**
	 * ���������Ų�ѯ�����Ϣ
	 * @param tPartTypeInfo
	 */
	public List<TPartTypeInfo> getTPartTypeInfoByNo(String no){
	   Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TPartTypeInfo where no='"+no+"'";
		return commonDao.executeQuery(hql, parameters);
		
	}
	/**
	 * ����id�ڵ�id��ȡ����б�
	 * @param nodeid
	 * @return
	 */
	public List<Map<String,Object>> getTPartTypeInfoByNodeid(String nodeid,String partId){
		   Collection<Parameter> parameters = new HashSet<Parameter>();
			String hql="select new Map("
					+ " name as name,"
					+ " id as id,"
					+ " no as no)"
					+ " from TPartTypeInfo where nodeid='"+nodeid+"'";
			if(null!=partId&&!"".equals(partId)){
				hql+= " and  id='"+partId+"'";
			}
			return commonDao.executeQuery(hql, parameters);
			
		}
	/**
	 * ���湤�շ���
	 * @param plan
	 * @return
	 */
	public String saveTProcessplanInfo(TProcessplanInfo plan){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		List<TProcessplanInfo> rs=commonDao.executeQuery("from TProcessplanInfo where name='"+plan.getName()+"' and status=0", parameters);
		if(null!=rs&&rs.size()>0){
			return "0";
		}else{
			try {
				commonDao.save(plan);
				return "1";
			} catch (Exception e) {
				e.printStackTrace();
				return "2";
			}
		}
	}
	/**
	 * ���շ�����ΪĬ��
	 * @param plan
	 * @return
	 */
	public String updateDeDefaultTProcessplanInfo(TProcessplanInfo plan){
		Collection<Parameter> parameters = new HashSet<Parameter>();
		String hql="from TProcessplanInfo where TPartTypeInfo.id='"+plan.getTPartTypeInfo().getId()+"' " +
				  " and defaultSelected=1 and id<>'"+plan.getId()+"'";
		List<TProcessplanInfo> rs=commonDao.executeQuery(hql, parameters);
		TProcessplanInfo p=new TProcessplanInfo();
		String hql2="from TProcessplanInfo where id='"+plan.getId()+"' ";
		List<TProcessplanInfo> rs2=commonDao.executeQuery(hql2, parameters);
		TProcessplanInfo pp=rs2.get(0);
		pp.setDefaultSelected(1);
		if(null!=rs&&rs.size()>0){
			p=rs.get(0);
			p.setDefaultSelected(0);
			try {
				commonDao.update(p);
				commonDao.update(pp);
				return "���óɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}else{
			try {
				commonDao.update(pp);
				return "���óɹ�";
			} catch (Exception e) {
				e.printStackTrace();
				return "����ʧ��";
			}
		}
	}
	/**
	 * ���������Ż�ȡ ���μƻ�
	 * @param partId
	 * @return
	 */
	public List<Map<String,Object>> getJopPlanByPartId(String partId){
		String hql="select new Map("
				+ " no as no,"
				+ " name as name,"
				+ " id as id)"
				+ " from TJobplanInfo "
				+ " where TPartTypeInfo.id="+partId+""
				+ " and planType=2";
		return commonDao.executeQuery(hql);
	}
	/**
	 * ���ݹ�����Ż�ȡ ���й�����Ϣ
	 * @param jobdispathNo
	 * @return
	 */
	public List<Map<String,Object>> getProcessTimeByjobdispathNo(String jobdispathNo){
		return null;	
	}
}
