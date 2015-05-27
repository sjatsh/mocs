package smtcl.mocs.dao.authority.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.persistence.hibernate.dao.impl.GenericHibernateDaoImpl;
import org.dreamwork.util.IDataCollection;
import org.dreamwork.util.ListDataCollection;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import smtcl.mocs.dao.authority.ICommonDao;


/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 2010-12-3
 * Time: 16:53:48
 * 公共模块
 */
public class CommonDaoImpl extends GenericHibernateDaoImpl implements ICommonDao {
    @SuppressWarnings ("unchecked")
    public CommonDaoImpl () {
        super (Object.class);
    }
// name SQL名称，Parameter 过滤参数名称
    @SuppressWarnings ("unchecked")
    public List<Map<String, Object>> listByQueryName (String name, Parameter... parameters) {
//        if (parameters.length == 0) return null;
        Query query = getSession ().getNamedQuery (name);
        if (query != null) {
            setQueryParameters (query, parameters);
            return query.list ();
        }
        return null;
    }

//   name SQL名称?parameters 过滤参数?
    public List<Map<String, Object>> listByQueryName (String name, Collection<Parameter> parameters) {
//        if (parameters.size () == 0) return null;
        Parameter[] ps = new Parameter[parameters.size ()];
        ps = parameters.toArray (ps);
        return listByQueryName (name, ps);
    }

    //   name SQL名称?parameters 过滤参数?
    @SuppressWarnings ("unchecked")
    public List<Map<String, Object>> listByQueryName (String name, Map<String, Object> parameters) {
//        if (parameters.size () == 0) return null;
        Query query = getSession ().getNamedQuery (name);
        if (query != null) {
            setQueryParameters (query, parameters);
            return query.list ();
        }
        return null;
    }

//  type 通用? parameters 过滤参数?
    public <T> T get (Class<T> type, Parameter... parameters) {
        List<T> list = find (type, null, parameters);
        return list == null || list.size () == 0 ? null : list.get (0);
    }

//  type 通用?parameters 过滤参数?
    public <T> T get (Class<T> type, Sort sort, Parameter... parameters) {
        List<T> list = find (type, Arrays.asList (sort), parameters);
        return list == null || list.size () == 0 ? null : list.get (0);
    }

//  type 通用?sort 排序  parameters 过滤参数?
    @SuppressWarnings ("unchecked")
    public <T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters) {
        return buildQuery (type, sort, parameters).list ();
    }

    public <T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters) {
        return  find (type, sort, Arrays.asList (parameters));
    }

//  type 通用?sort 排序  parameters 过滤参数? pageNo 首页  pageSize 页数  sort 排序 parameters 过滤参数?
    @SuppressWarnings ("unchecked")
    public <T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Collection<Parameter> parameters) {
        int count = getCount (type, parameters);
        Query query = buildQuery (type, sort, parameters);
//        query.setFetchSize (pageSize);
        query.setMaxResults (pageSize);
        query.setFirstResult ((pageNo - 1) * pageSize);
        List<T> list = query.list ();
        ListDataCollection<T> ldc = new ListDataCollection<T> ();
        ldc.setData (list);
        ldc.setPageNo (pageNo);
        ldc.setPageSize (pageSize);
        ldc.setTotalRows (count);
        return ldc;
    }

    public <T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Parameter... parameters) {
        return find (type, pageNo, pageSize, sort, Arrays.asList (parameters));
    }

    public void saveObject (Object object) {
        getSession ().save (object);
    }

    public void updateObject (Object object) {
        getSession ().update (object);
    }

    public<T, PK extends Serializable> boolean exists (Class<T> type, PK id) {
        return getSession ().get (type, id) != null;
    }

    public<T> boolean exists (Class<T> type, Parameter... parameters) {
        return this.get (type, parameters) != null;
    }

    public<T> boolean exists (Class<T> type, Collection<Parameter> parameters) {
        Parameter[] ps = new Parameter[parameters.size ()];
        ps = parameters.toArray (ps);
        return this.get (type, ps) != null;
    }

    public Session getCurrentSession() {
        return this.getSession();
    }
    
    
    @Override
    public IDataCollection executeQuery (int pageNo, int pageSize, String hql, Parameter... parameters) {
    	int p = hql.toLowerCase ().indexOf (" from ");
        int p2 = hql.toLowerCase ().indexOf (" order by ");
        String s;
        if (p2 < 0)
            s = "SELECT COUNT(*) FROM " + hql.substring (p + " from ".length ());
        else
            s = "SELECT COUNT(*) FROM " + hql.substring (p + " from ".length (), p2);
        Query query = getSession ().createQuery (s);
        setQueryParameters (query, parameters);
        List list = query.list ();
        int count = list.size()>1 ? list.size():((Number) list.get (0)).intValue ();
        ListDataCollection c = new ListDataCollection ();
        c.setTotalRows (count);
        if (count == 0) return c;

        query = getSession ().createQuery (hql);
        setQueryParameters (query, parameters);
        query.setFirstResult ((pageNo - 1) * pageSize);
        query.setMaxResults (pageSize);
        c.setData (query.list ());
        return c;
    }
    
    @Override
	public List<Map<String, Object>> executeNativeQuery (String sql, Parameter... parameters) {
        SQLQuery query = getSession ().createSQLQuery (sql);
        setQueryParameters (query, parameters);
        query.setResultTransformer (Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list ();
//        String[] aliases = query.getReturnAliases ();
//        return convertList (list, aliases);
    }
}