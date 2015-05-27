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
 * �鿴��֯�ڵ������豸״̬
 * @����ʱ�� 2012-12-06
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="devicesOverviewBean")
@SessionScoped
public class DevicesOverviewBean {
	/**
	 * ��ǰ�ڵ�
	 */
	private String nodeStr;              
	/**
	 * �豸ҵ���߼�
	 */
	private	IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	/**
	 * x������
	 */
	private String xz; 
	/**
	 * y������
	 */
	private String yz;
	/**
	 *  �豸����
	 */
	private String equipmentName;
	/**
	 * �豸ͼƬ
	 */
	private String image;
	/**
	 * �豸״̬ͼƬ
	 */
	private String status;
	/**
	 * ʵʱ����ʱ�滻״̬ͼƬ
	 */
	private String upimage;
	/**
	 * ʵʱ����ʱ��������
	 */
	private List<Map<String, Object>>  neId;
	
	/**
	 * ��ȡ��ǰ����ڵ� 
	 * @return String
	 */
	public String getNodeStr() {
		TreeNode currentNode = (TreeNode) FaceContextUtil
				.getContext().getSessionMap().get("CURRENTNODE");
		if (currentNode != null) {
			long startTime=System.currentTimeMillis();   //��ȡ��ʼʱ��
			 this.nodeStr=currentNode.getNodeName(); //�������ڵ��ֵ������ǰ�ڵ�nodeStr 
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
        			 if(status.equals("����")||status.equals("�ӹ�")){
        				 status="0"; 
        			 }else if(status.equals("�ѻ�")||status.equals("�ػ�")){
        				 status="1";
        			 }else if(status.equals("׼��")){
        				 status="2"; 
        			 } else if(status.equals("����")||status.equals("�ж�")||status.equals("��ͣ")){
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
		long endTime=System.currentTimeMillis(); //��ȡ����ʱ��
		System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ms");
		}
		return nodeStr;
	}
	
	/**
	 * ʵʱˢ��ʱ���ݼ���
	 */
	public void refreshData(){
		upimage="";
		if(null!=neId&&neId.size()>0){
			 for(int i=0;i<neId.size();i++){
    			 Map<String, Object> tf=(Map<String, Object>)neId.get(i);
    			 String status=tf.get("status").toString();
    			 if(status.equals("����")||status.equals("�ӹ�")){
    				 status="0"; 
    			 }else if(status.equals("�ѻ�")||status.equals("�ػ�")){
    				 status="1";
    			 }else if(status.equals("׼��")){
    				 status="2"; 
    			 } else if(status.equals("����")||status.equals("�ж�")||status.equals("��ͣ")){
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
