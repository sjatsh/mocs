package smtcl.mocs.services.device;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericService;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.IDataCollection;

/**
 * 
 * 公共接口
 * @作者：YuTao  
 * @创建时间：2013-1-1 下午11:27:01
 * @修改者： 
 * @修改日期： 
 * @修改说明： 
 * @version V1.0
 */
public interface ICommonService extends IGenericService<Object, String> {
	
    List<Map<String, Object>> listByQueryName (String name, Parameter... parameters);
    
    List<Map<String, Object>> listByQueryName (String name, Collection<Parameter> parameters);
    
    List<Map<String, Object>> listByQueryName (String name, Map<String, Object> parameters);

    public<T> T get (Class<T> type, Parameter... parameters);
    
    public<T> T get (Class<T> type, Sort sort, Parameter... parameters);
    
    public<T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters);
    
    public<T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters);
    
    public<T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Collection<Parameter> parameters);
    
    public<T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Parameter... parameters);

    public<T, PK> int deleteById (Class<T> type, String pkName, PK pk);
    
    public<T, PK extends Serializable> int deleteById (Class<T> type, String pkName, PK... pks);
    
    public<T, PK> int deleteById (Class<T> type, String pkName, Collection<PK> pks);

    public<T, PK extends Serializable> boolean exists (Class<T> type, PK id);
    
    public<T> boolean exists (Class<T> type, Parameter... parameters);
    
    public<T> boolean exists (Class<T> type, Collection<Parameter> parameters);

    public void saveObject (Object object);
    
    public void saveObjects (Object... object);
    
    public<T> void saveObjectCollection (Collection<T> object);

    public void updateObject (Object object);
    
    public void updateObjects (Object... object);
    
    public<T> void updateObjectCollection (Collection<T> object);

}
