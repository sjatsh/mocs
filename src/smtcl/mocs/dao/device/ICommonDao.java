package smtcl.mocs.dao.device;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericDao;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.IDataCollection;

/**
 * DAO层通用接口
 * @创建时间 2012-12-21
 * @作者 余涛
 * @修改者： 
 * @修改日期： 
 * @修改说明
 * @version V1.0
 */
public interface ICommonDao extends IGenericDao {
	/**
	 * 通过参数名称过滤
	 * @param name SQL名称
	 * @param parameters 参数
	 * @return List<Map<String, Object>>
	 */
    List<Map<String, Object>> listByQueryName (String name, Parameter... parameters);
    
    /**
   	 * 通过参数名称过滤
   	 * @param name SQL名称
   	 * @param parameters 参数集合
   	 * @return List<Map<String, Object>>
   	 */
    List<Map<String, Object>> listByQueryName (String name, Collection<Parameter> parameters);
    
    /**
   	 * 通过参数名称过滤
   	 * @param name SQL名称
   	 * @param parameters 参数集合
   	 * @return List<Map<String, Object>>
   	 */
    List<Map<String, Object>> listByQueryName (String name, Map<String, Object> parameters);

    /**
   	 * 通过参数名称过滤
   	 * @param type 通用泛型
   	 * @param parameters 参数
   	 * @return T
   	 */
    public<T> T get (Class<T> type, Parameter... parameters);
    
    /**
   	 * 通过参数名称过滤
   	 * @param type 通用泛型
   	 * @param sort 排序
   	 * @param parameters 参数
   	 * @return T
   	 */    
    public<T> T get (Class<T> type, Sort sort, Parameter... parameters);
    
    /**
   	 * 通过参数名称过滤
   	 * @param type 通用泛型
   	 * @param sort 排序
   	 * @param parameters 参数集合
   	 * @return List<T>
   	 */
    public<T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters);
    
    /**
   	 * 通过参数名称过滤
   	 * @param type 通用泛型
   	 * @param sort 排序
   	 * @param parameters 参数
   	 * @return List<T>
   	 */
    public<T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters);
    
    /**
   	 * 通过参数名称过滤
   	 * @param type 通用泛型
   	 * @param pageNo 页码
   	 * @param pageSize 页数
   	 * @param sort 排序
   	 * @param parameters 参数集合
   	 * @return IDataCollection<T>
   	 */
    public<T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Collection<Parameter> parameters);
    
    public<T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Parameter... parameters);

    public void saveObject (Object object);
    
    public void updateObject (Object object);

    public<T, PK extends Serializable> boolean exists (Class<T> type, PK id);
    
    public<T> boolean exists (Class<T> type, Parameter... parameters);
    
    public<T> boolean exists (Class<T> type, Collection<Parameter> parameters);
}