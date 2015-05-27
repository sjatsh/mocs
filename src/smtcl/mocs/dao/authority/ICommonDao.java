package smtcl.mocs.dao.authority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.IGenericDao;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.IDataCollection;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 2010-12-3
 * Time: 16:55:21
 * 公共接口
 */
public interface ICommonDao extends IGenericDao {
    List<Map<String, Object>> listByQueryName (String name, Parameter... parameters);
    List<Map<String, Object>> listByQueryName (String name, Collection<Parameter> parameters);
    List<Map<String, Object>> listByQueryName (String name, Map<String, Object> parameters);

    public<T> T get (Class<T> type, Parameter... parameters);
    public<T> T get (Class<T> type, Sort sort, Parameter... parameters);
    public<T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters);
    public<T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters);
    public<T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Collection<Parameter> parameters);
    public<T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Parameter... parameters);

    public void saveObject (Object object);
    public void updateObject (Object object);

    public<T, PK extends Serializable> boolean exists (Class<T> type, PK id);
    public<T> boolean exists (Class<T> type, Parameter... parameters);
    public<T> boolean exists (Class<T> type, Collection<Parameter> parameters);
}