package smtcl.mocs.beans.equipment;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.device.TNodes;
import smtcl.mocs.services.device.IDeviceService;
import smtcl.mocs.services.device.IOrganizationService;
import smtcl.mocs.services.jobplan.IEquipmentService;

/**
 * 设备拖拽保存
 * @创建时间 2014-08-12
 * @作者 liguoqiang
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
@ManagedBean(name="equipmentDragSaveBean")
@ViewScoped
public class EquipmentDragSaveBean {
	private IEquipmentService equipmentService = (IEquipmentService)ServiceFactory.getBean("equipmentService");
	private	IDeviceService deviceService=(IDeviceService)ServiceFactory.getBean("deviceService");
	private IOrganizationService organizationService=(IOrganizationService)ServiceFactory.getBean("organizationService");//获取注入;
	private String nodeid;
	private String nodeids;
	private String equData;
	private String showMsg;
	private String bgpath;
	private String xyAxisNull;
	private String nodeName;
	/**
	 * 构造方法
	 */
	public EquipmentDragSaveBean(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	nodeid=session.getAttribute("nodeid2")+"";
		nodeids=deviceService.getNodeAllId(nodeid);
		equData=equipmentService.getEquData(nodeids,1);
		xyAxisNull=equipmentService.getEquData(nodeids,2);
		List<TNodes> tnode=organizationService.getTNodesById(nodeid);
		if(null!=tnode&&tnode.size()>0){
			TNodes td=tnode.get(0);
			bgpath=td.getPath();
			nodeName=td.getNodeName();
		}
	}
	
	public void saveEquipment(){
		showMsg=equipmentService.saveEquData(equData);
		//showMsg="修改成功";
	}

	public String getEquData() {
		return equData;
	}

	public void setEquData(String equData) {
		this.equData = equData;
	}

	public String getShowMsg() {
		return showMsg;
	}

	public void setShowMsg(String showMsg) {
		this.showMsg = showMsg;
	}

	public String getBgpath() {
		return bgpath;
	}

	public void setBgpath(String bgpath) {
		this.bgpath = bgpath;
	}

	public String getXyAxisNull() {
		return xyAxisNull;
	}

	public void setXyAxisNull(String xyAxisNull) {
		this.xyAxisNull = xyAxisNull;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
}
