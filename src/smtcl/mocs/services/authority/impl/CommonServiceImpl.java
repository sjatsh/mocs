package smtcl.mocs.services.authority.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dreamwork.persistence.GenericServiceSpringImpl;
import org.dreamwork.persistence.Operator;
import org.dreamwork.persistence.Parameter;
import org.dreamwork.persistence.Sort;
import org.dreamwork.util.IDataCollection;

import smtcl.mocs.dao.authority.ICommonDao;
import smtcl.mocs.services.authority.ICommonService;


/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 2010-12-6
 * Time: 13:46:11
 */
public class CommonServiceImpl extends GenericServiceSpringImpl<Object, String> implements ICommonService {
	/**
	 * CommonServiceImpl »’÷æ
	 */
	private final static Logger logger = Logger.getLogger(CommonServiceImpl.class);
	
    protected ICommonDao commonDao;
    public List<Map<String, Object>> listByQueryName (String name, Parameter... parameters) {
        return commonDao.listByQueryName (name, parameters);
    }

    public List<Map<String, Object>> listByQueryName (String name, Collection<Parameter> parameters) {
        return commonDao.listByQueryName (name, parameters);
    }

    public List<Map<String, Object>> listByQueryName (String name, Map<String, Object> parameters) {
        return commonDao.listByQueryName (name, parameters);
    }

    public <T> T get (Class<T> type, Parameter... parameters) {
        return commonDao.get (type, parameters);
    }

    public <T> T get (Class<T> type, Sort sort, Parameter... parameters) {
        return commonDao.get (type, sort, parameters);
    }

    public <T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters) {
        return commonDao.find (type, sort, parameters);
    }

    public <T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters) {
        return commonDao.find (type, sort, parameters);
    }

    public <T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Collection<Parameter> parameters) {
        return commonDao.find (type, pageNo, pageSize, sort, parameters);
    }

    public <T> IDataCollection<T> find (Class<T> type, int pageNo, int pageSize, List<Sort> sort, Parameter... parameters) {
        return commonDao.find (type, pageNo, pageSize, sort, parameters);
    }

    public <T, PK> int deleteById (Class<T> type, String pkName, PK pk) {
        String hql = "delete " + type.getName () + " where " + pkName + " = :" + pkName;
        return commonDao.executeUpdate (hql, new Parameter (pkName, pk, Operator.EQ));
    }

    public <T, PK extends Serializable> int deleteById (Class<T> type, String pkName, PK... pks) {
/*
        String hql = "delete " + type.getName () + " where " + pkName + " in (:" + pkName + ")";
        return commonDao.executeUpdate (hql, new Parameter (pkName, pks, Operator.IN));
*/
        for (PK id : pks) {
            T t = (T) commonDao.get (type, id);
            commonDao.delete (t);
        }
        return pks.length;
    }

    public <T, PK> int deleteById (Class<T> type, String pkName, Collection<PK> pks) {
        Object[] a = new Object[pks.size ()];
        a = pks.toArray (a);
        String hql = "delete " + type.getName () + " where " + pkName + " in (:" + pkName + ")";
        return commonDao.executeUpdate (hql, new Parameter (pkName, a, Operator.IN));
    }

    public void saveObject (Object object) {
        commonDao.saveObject (object);
    }

    public void saveObjects (Object... object) {
        for (Object o : object) commonDao.saveObject (o);
    }

    public<T> void saveObjectCollection (Collection<T> object) {
        for (T o : object) commonDao.saveObject (o);
    }

    public void updateObject (Object object) {
        commonDao.updateObject (object);
    }

    public void updateObjects (Object... object) {
        for (Object o : object) commonDao.updateObject (o);
    }

    public<T> void updateObjectCollection (Collection<T> object) {
        for (T o : object) commonDao.updateObject (o);
    }

    public ICommonDao getCommonDao () {
        return commonDao;
    }

    public void setCommonDao (ICommonDao commonDao) {
        this.commonDao = commonDao;
        this.dao = commonDao;
    }

    @Override
    public List executeQuery (String hql, Parameter... parameters) {
        return commonDao.executeQuery (hql, parameters);    //To change body of overridden methods use File | Settings | File Templates.
    }

/*
    @Override
    public List executeQuery (String hql, Collection<Parameter> parameters) {
        return commonDao.executeQuery (hql, parameters);    //To change body of overridden methods use File | Settings | File Templates.
    }
*/

    @Override
    public IDataCollection executeQuery (int pageNo, int pageSize, String hql, Parameter... parameters) {
        return commonDao.executeQuery (pageNo, pageSize, hql, parameters);    //To change body of overridden methods use File | Settings | File Templates.
    }

/*
    @Override
    public IDataCollection executeQuery (int pageNo, int pageSize, String hql, Collection<Parameter> parameters) {
        return commonDao.executeQuery (pageNo, pageSize, hql, parameters);    //To change body of overridden methods use File | Settings | File Templates.
    }
*/

    public <T, PK extends Serializable> boolean exists (Class<T> type, PK id) {
        return commonDao.exists (type, id);
    }

    public <T> boolean exists (Class<T> type, Parameter... parameters) {
        return commonDao.exists (type, parameters);
    }

    public <T> boolean exists (Class<T> type, Collection<Parameter> parameters) {
        return commonDao.exists (type, parameters);
    }
    
    @Override
    public int executeUpdate(String hql, Parameter... parameter){
    	return commonDao.executeUpdate(hql, parameter);
    }
}