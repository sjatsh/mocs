/**
 * com.swg.authority.service.imp IAuthService.java
 */
package smtcl.mocs.services.authority;

import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;

/**
 * @author gaokun
 * @create Nov 14, 2012 5:05:30 PM
 */
public interface IAuthService extends IGenericService<Object, String>  {
	
	public List<Map> queryBindAuth(String condition, String type);

}
