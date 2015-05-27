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
 * DAO��ͨ�ýӿ�
 * @����ʱ�� 2012-12-21
 * @���� ����
 * @�޸��ߣ� 
 * @�޸����ڣ� 
 * @�޸�˵��
 * @version V1.0
 */
public interface ICommonDao extends IGenericDao {
	/**
	 * ͨ���������ƹ���
	 * @param name SQL����
	 * @param parameters ����
	 * @return List<Map<String, Object>>
	 */
    List<Map<String, Object>> listByQueryName (String name, Parameter... parameters);
    
    /**
   	 * ͨ���������ƹ���
   	 * @param name SQL����
   	 * @param parameters ��������
   	 * @return List<Map<String, Object>>
   	 */
    List<Map<String, Object>> listByQueryName (String name, Collection<Parameter> parameters);
    
    /**
   	 * ͨ���������ƹ���
   	 * @param name SQL����
   	 * @param parameters ��������
   	 * @return List<Map<String, Object>>
   	 */
    List<Map<String, Object>> listByQueryName (String name, Map<String, Object> parameters);

    /**
   	 * ͨ���������ƹ���
   	 * @param type ͨ�÷���
   	 * @param parameters ����
   	 * @return T
   	 */
    public<T> T get (Class<T> type, Parameter... parameters);
    
    /**
   	 * ͨ���������ƹ���
   	 * @param type ͨ�÷���
   	 * @param sort ����
   	 * @param parameters ����
   	 * @return T
   	 */    
    public<T> T get (Class<T> type, Sort sort, Parameter... parameters);
    
    /**
   	 * ͨ���������ƹ���
   	 * @param type ͨ�÷���
   	 * @param sort ����
   	 * @param parameters ��������
   	 * @return List<T>
   	 */
    public<T> List<T> find (Class<T> type, List<Sort> sort, Collection<Parameter> parameters);
    
    /**
   	 * ͨ���������ƹ���
   	 * @param type ͨ�÷���
   	 * @param sort ����
   	 * @param parameters ����
   	 * @return List<T>
   	 */
    public<T> List<T> find (Class<T> type, List<Sort> sort, Parameter... parameters);
    
    /**
   	 * ͨ���������ƹ���
   	 * @param type ͨ�÷���
   	 * @param pageNo ҳ��
   	 * @param pageSize ҳ��
   	 * @param sort ����
   	 * @param parameters ��������
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