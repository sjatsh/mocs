package smtcl.mocs.beans.device;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.beans.authority.cache.TreeNode;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.utils.device.FaceContextUtil;
import smtcl.mocs.utils.device.MapXYM;


/**
 * 查看组织节点整体设备状态
 * @创建时间 2012-12-06
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="devicesOverviewBean")
@SessionScoped
public class DevicesOverviewBean {
	/**
	 * 当前节点
	 */
	private String nodeStr;              
	/**
	 * 设备业务逻辑
	 */
	private	IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * x轴坐标
	 */
	private String xz; 
	/**
	 * y轴坐标
	 */
	private String yz;
	/**
	 *  设备名字
	 */
	private String equipmentName;
	/**
	 * 设备图片
	 */
	private String image;
	/**
	 * 设备状态图片
	 */
	private String status;
	/**
	 * 实时更新时替换状态图片
	 */
	private String upimage;
	/**
	 * 实时更新时数据条数
	 */
	private List<Map<String, Object>>  neId;
	
	/**
	 * 获取当前点击节点 
	 * @return String
	 */
	public String getNodeStr() {
		TreeNode currentNode = (TreeNode) FaceContextUtil
				.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			long startTime=System.currentTimeMillis();   //获取开始时间
			 this.nodeStr=currentNode.getNodeName(); //将单机节点的值赋给当前节点nodeStr 
	    		String nodeid="";
	    		
	    		for(TreeNode tt:currentNode.getChildNodes()){
	    			if (tt.getNodeType().equals("13")) {
	    				nodeid=nodeid+",'"+tt.getNodeId()+"'";
	    			}
	    		};
		 equipmentName="";
    	 xz="";
    	 yz="";
    	 upimage="";
    	 image="";
    	 
		if(nodeid.length()>0){
		 nodeid=nodeid.substring(1, nodeid.length());
    	 List<Map<String, Object>> list=deviceService.getNodesAllEquNameStruts(nodeid);
    	 neId=list;
    	 
    	 if(null!=list&&list.size()>0){
    		 for(int i=0;i<list.size();i++){
        			 Map<String, Object> tf=(Map<String, Object>)list.get(i);
        			 equipmentName=equipmentName+","+tf.get("equSerialNo");
        			 String status=tf.get("status").toString();
        			 if(status.equals("运行")||status.equals("加工")){
        				 status="0"; 
        			 }else if(status.equals("脱机")||status.equals("关机")){
        				 status="1";
        			 }else if(status.equals("准备")){
        				 status="2"; 
        			 } else if(status.equals("故障")||status.equals("中断")||status.equals("急停")){
        				 status="3"; 
        			 }else{
        				 status="4"; 
        			 }
        			 String path="";
        			 if(null!=tf.get("path")&&!"".equals(tf.get("path")))
        				 image=image+","+tf.get("path");
        			 else
        				 image=image+","+"images/TC500R.png";
            		 xz=xz+","+MapXYM.BIGX[i];
            		 yz=yz+","+MapXYM.BIGY[i];
            		 upimage=upimage+","+MapXYM.BIGSM[Integer.parseInt(status)];
        	 } 
    	 }
		}
		if(equipmentName.length()>0){
			equipmentName=equipmentName.substring(1,equipmentName.length());
	    	 xz=xz.substring(1, xz.length());
			 yz=yz.substring(1, yz.length());
			 image=image.substring(1,image.length());
			 upimage=upimage.substring(1,upimage.length());
		}
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
		}
		return nodeStr;
	}
	
	/**
	 * 实时刷新时数据加载
	 */
	public void refreshData(){
		upimage="";
		if(null!=neId&&neId.size()>0){
			 for(int i=0;i<neId.size();i++){
    			 Map<String, Object> tf=(Map<String, Object>)neId.get(i);
    			 String status=tf.get("status").toString();
    			 if(status.equals("运行")||status.equals("加工")){
    				 status="0"; 
    			 }else if(status.equals("脱机")||status.equals("关机")){
    				 status="1";
    			 }else if(status.equals("准备")){
    				 status="2"; 
    			 } else if(status.equals("故障")||status.equals("中断")||status.equals("急停")){
    				 status="3"; 
    			 }else{
    				 status="4"; 
    			 }
    			 upimage=upimage+","+MapXYM.BIGSM[Integer.parseInt(status)];
			 } 
			 upimage=upimage.substring(1,upimage.length());
		}
	}
	
	public void setNodeStr(String nodeStr) {
		this.nodeStr = nodeStr;
	}

	public String getXz() {
		return xz;
	}

	public void setXz(String xz) {
		this.xz = xz;
	}

	public String getYz() {
		return yz;
	}

	public void setYz(String yz) {
		this.yz = yz;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getUpimage() {
		return upimage;
	}

	public void setUpimage(String upimage) {
		this.upimage = upimage;
	}
	
}
