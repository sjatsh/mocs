package smtcl.mocs.dao.authority.impl;

import org.apache.log4j.Logger;
import org.dreamwork.jasmine2.engine.HttpContext;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.persistence.hibernate.dao.impl.GenericHibernateDaoImpl;
import org.dreamwork.util.IDataCollection;
import org.dreamwork.util.ListDataCollection;
import org.dreamwork.util.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 11-1-12
 * Time: ÏÂÎç2:16
 * ¹ýÂË ²éÑ¯
 */
public class FilterGenericDaoHibernateImpl<T, PK extends Serializable> extends GenericHibernateDaoImpl<T, PK> {
    private static final Logger logger = Logger.getLogger (FilterGenericDaoHibernateImpl.class);

    public FilterGenericDaoHibernateImpl (Class<T> type) {
        super (type);
    }

    @SuppressWarnings ("unchecked")
    @Override
    public T get (Parameter... parameters) {
        return this.get ((Sort)null, parameters);
    }

    @SuppressWarnings ("unchecked")
    @Override
    public T get (Sort sort, Parameter... parameters) {
        Criteria criteria = this.createCriteria (type, 1, 1, Arrays.asList (sort), Arrays.asList (parameters));
        List list = criteria.list ();
        return list.size () > 0 ? (T) list.get (0) : null;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public List<T> find (Parameter... parameters) {
        Criteria criteria = createCriteria (type, -1, -1, null, Arrays.asList (parameters));
        return (List<T>) criteria.list ();
    }

    @SuppressWarnings ("unchecked")
    public IDataCollection<T> find (int pageNo, int pageSize, Collection<Sort> s, Collection<Parameter> p) {
        int count = getCount (type, p);
        ListDataCollection<T> ret = new ListDataCollection<T> ();
        ret.setPageNo (pageNo);
        ret.setPageSize (pageSize);
        ret.setTotalRows (count);

        if (count != 0) {
            Criteria criteria = createCriteria (type, pageNo, pageSize, s, p);
            ret.setData (criteria.list ());
        }

        return ret;
    }

    protected Criteria createCriteria (Class type, int pageNo, int pageSize, Collection<Sort> sort, Collection<Parameter> parameters) {
        Criteria criteria = getSession ().createCriteria (type);
        setCriteriaParameters (criteria, parameters);

        if (sort != null && sort.size () > 0) for (Sort s : sort) {
            if (s == null) continue;
            if (s.direction == Sort.Direction.ASC)
                criteria.addOrder (Order.asc (s.fieldName));
            else
                criteria.addOrder (Order.desc (s.fieldName));
        }

        if (pageSize != -1 && pageNo != -1) {
            int first = (pageNo - 1) * pageSize;
            criteria.setFirstResult (first);
            criteria.setMaxResults (pageSize);
        }
        return criteria;
    }

    @SuppressWarnings ("unchecked")
    protected void setCriteriaParameters (Criteria criteria, Collection<Parameter> parameters) {
        for (Parameter param : parameters) {
            criteria.add (buildCriterion (param));
        }

        HttpContext context = HttpContext.current ();
        if (context == null) return;

        Class<T> filterType = (Class<T>) context.getAttribute ("filter.type");
        if (filterType == null || filterType != type) return;

        String filterName = (String) context.getAttribute ("filter.name");
        if (StringUtil.isEmpty (filterName)) return;

        Collection<Parameter> ps = (Collection<Parameter>) context.getAttribute ("filter.parameters");
        if (ps != null && ps.size () > 0) for (Parameter p : ps) {
            criteria.add (buildCriterion (p));
        }
    }

    @Override
    @SuppressWarnings ("unchecked")
    protected int getCount (Class type, Collection<Parameter> parameters) {
        StringBuilder builder = new StringBuilder ();
        Map<String, Object> map = new HashMap<String, Object> ();
        builder.append ("SELECT count(*) FROM ").append (type.getName ()).append (" AS t");
        if (parameters.size () > 0) {
            builder.append (" WHERE ").append (this.buildWhere ("t", map, parameters));
        }
        StringBuilder filter = new StringBuilder ();
        applyFilter (type, filter, map);
        if (parameters.size () > 0 && filter.length () > 0)
            builder.append (" AND ").append (filter);

        Query query = getSession ().createQuery (builder.toString ());
        setQueryParameters (query, map);

        List list = query.list ();
        return list.size () > 0 ? ((Number) list.get (0)).intValue () : 0;
    }

    @SuppressWarnings ("unchecked")
    protected void applyFilter (Class<T> type, StringBuilder builder, Map<String, Object> map) {
        HttpContext context = HttpContext.current ();
        if (context == null) return;

        Class<T> filterType = (Class<T>) context.getAttribute ("filter.type");
        if (filterType == null || filterType != type) return;

        String filterName = (String) context.getAttribute ("filter.name");
        if (StringUtil.isEmpty (filterName)) return;

        Collection<Parameter> parameters = (Collection<Parameter>) context.getAttribute ("filter.parameters");
        builder.append (buildWhere ("t", map, parameters));
    }

    private Criterion buildCriterion (Parameter p) {
        switch (p.operator) {
//            EQ, NE, LT, LE, GT, GE, LIKE, IN, NOT_IN, IS_NULL, IS_NOT_NULL, BETWEEN
            case EQ : return Restrictions.eq (p.fieldName, p.value);
            case NE : return Restrictions.ne (p.fieldName, p.value);
            case LE : return Restrictions.le (p.fieldName, p.value);
            case LT : return Restrictions.lt (p.fieldName, p.value);
            case GE : return Restrictions.ge (p.fieldName, p.value);
            case GT : return Restrictions.gt (p.fieldName, p.value);
            case LIKE : return Restrictions.like (p.fieldName, "%" + p.value + '%');
            case IN :
                if (p.value == null) throw new IllegalArgumentException (p.fieldName);
                Class t = p.value.getClass ();
                if (t.isArray ()) {
                    Object[] a = toArray (p.value);
                    return Restrictions.in (p.fieldName, a);
                } else if (p.value instanceof Collection) {
                    return Restrictions.in (p.fieldName, (Collection) p.value);
                } else {
                    throw new IllegalArgumentException (p.fieldName);
                }
            case NOT_IN :
                if (p.value == null) throw new IllegalArgumentException (p.fieldName);
                Class type = p.value.getClass ();
                if (type.isArray ()) {
                    Object[] a = toArray (p.value);
                    return Restrictions.not (Restrictions.in(p.fieldName, a));
                } else if (p.value instanceof Collection) {
                    return Restrictions.not (Restrictions.in (p.fieldName, (Object[]) p.value));
                } else {
                    throw new IllegalArgumentException (p.fieldName);
                }
            case IS_NULL : return Restrictions.isNull (p.fieldName);
            case IS_NOT_NULL : return Restrictions.isNotNull (p.fieldName);
            case BETWEEN :
                Object[] o = (Object[]) p.value;
                if (o.length < 2)
                    throw new IllegalArgumentException (p.fieldName);
                return Restrictions.between (p.fieldName, o[0], o[1]);
            default :
                throw new IllegalArgumentException (p.fieldName);
        }
    }

    private Object[] toArray (Object o) {
        if (o instanceof long[]) {
            long[] arr = (long[]) o;
            Long[] ret = new Long [arr.length];
            for (int i = 0; i < arr.length; i ++) ret [i] = arr [i];
            return ret;
        }
        if (o instanceof int[]) {
            int[] arr = (int[]) o;
            Integer[] ret = new Integer [arr.length];
            for (int i = 0; i < arr.length; i ++) ret [i] = arr [i];
            return ret;
        }
        if (o instanceof byte[]) {
            byte[] arr = (byte[]) o;
            Byte[] ret = new Byte [arr.length];
            for (int i = 0; i < arr.length; i ++) ret [i] = arr [i];
            return ret;
        }
        if (o instanceof short[]) {
            short[] arr = (short[]) o;
            Short[] ret = new Short [arr.length];
            for (int i = 0; i < arr.length; i ++) ret [i] = arr [i];
            return ret;
        }
        if (o instanceof char[]) {
            char[] arr = (char[]) o;
            Character[] ret = new Character [arr.length];
            for (int i = 0; i < arr.length; i ++) ret [i] = arr [i];
            return ret;
        }
        if (o instanceof boolean[]) {
            boolean[] arr = (boolean[]) o;
            Boolean[] ret = new Boolean [arr.length];
            for (int i = 0; i < arr.length; i ++) ret [i] = arr [i];
            return ret;
        }
        if (o instanceof float[]) {
            float[] arr = (float[]) o;
            Float[] ret = new Float [arr.length];
            for (int i = 0; i < arr.length; i ++) ret [i] = arr [i];
            return ret;
        }
        if (o instanceof double[]) {
            double[] arr = (double[]) o;
            Double[] ret = new Double [arr.length];
            for (int i = 0; i < arr.length; i ++) ret [i] = arr [i];
            return ret;
        }
        return (Object[]) o;
    }
}