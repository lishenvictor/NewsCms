package news.ssp.dao;

import java.util.List;
import java.util.Map;

import news.ssp.entity.ArcType;

/**
 * �������Dao�ӿ�
 * @author user
 *
 */
public interface ArcTypeDao {

	/**
	 * ����������ҳ��ѯ������𼯺�
	 * @param map
	 * @return
	 */
	public List<ArcType> list(Map<String,Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * ����������
	 * @param arcType
	 * @return
	 */
	public Integer add(ArcType arcType);
	
	/**
	 * ����������
	 * @param arcType
	 * @return
	 */
	public Integer update(ArcType arcType);
	
	/**
	 * ����id��ѯ��������ʵ��
	 * @param id
	 * @return
	 */
	public ArcType findById(Integer id);
	
	/**
	 * ��������ɾ��
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}
