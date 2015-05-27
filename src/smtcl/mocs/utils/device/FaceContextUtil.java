package smtcl.mocs.utils.device;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * 
 * JSF容器工具
 * @作者：YuTao  
 * @创建时间：2012-12-20 下午3:45:48
 * @修改者： 
 * @修改日期： 
 * @修改说明： 
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
