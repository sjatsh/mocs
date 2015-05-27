package smtcl.mocs.beans.device;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.dreamwork.persistence.ServiceFactory;

import smtcl.mocs.pojos.job.TPartTypeInfo;
import smtcl.mocs.pojos.job.TProcessplanInfo;
import smtcl.mocs.services.device.IPartService;

/**
 * ���շ���
 * @����ʱ�� 2013-07-22
 * @���� liguoqiang
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
@ManagedBean(name="processplanInfoBean")
@ViewScoped
public class ProcessplanInfoBean {
	private IPartService partService=(IPartService)ServiceFactory.getBean("partService");
	private TProcessplanInfo plan=new TProcessplanInfo();
	private String partNo;
	private String partName;
	
	public ProcessplanInfoBean(){
		Map map =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String sp = (String)map.get("planProcess");//������
		System.out.println(sp);
		List<TPartTypeInfo> tPartTypeInfo=partService.getTPartTypeInfoByNo(sp);
		if(null!=tPartTypeInfo&&tPartTypeInfo.size()>0){
			partNo=tPartTypeInfo.get(0).getNo();
			partName=tPartTypeInfo.get(0).getName();
		}
	
	}
	/**
	 * ����
	 */
	public void SavePlan(){
		Date date=new Date();
		TProcessplanInfo s=new TProcessplanInfo();
		s.setName(plan.getName());
		s.setOperator(plan.getOperator());
		s.setCreateDate(date);
		s.setDescription(plan.getDescription());
		List<TPartTypeInfo> tPartTypeInfo=partService.getTPartTypeInfoByNo(partNo);
		s.setTPartTypeInfo(tPartTypeInfo.get(0));
		
		String rs=partService.saveTProcessplanInfo(s);
		
		if(rs.equals("1")){
			FacesMessage msg = new FacesMessage("Succesful","�ļ�����ɹ�");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}else {
			FacesMessage msg = new FacesMessage("Fall","�ļ�����ʧ��");  
	        FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public TProcessplanInfo getPlan() {
		return plan;
	}

	public void setPlan(TProcessplanInfo plan) {
		this.plan = plan;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

}
