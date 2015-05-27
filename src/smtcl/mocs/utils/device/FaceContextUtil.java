package smtcl.mocs.utils.device;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * 
 * JSF��������
 * @���ߣ�YuTao  
 * @����ʱ�䣺2012-12-20 ����3:45:48
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵���� 
 * @version V1.0
 */
public class FaceContextUtil {

	public static Map<String,Object> getSessionMap(){		
		
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		
	}
	
    public static ExternalContext getContext(){		
		
		return FacesContext.getCurrentInstance().getExternalContext();
		
	}
}
