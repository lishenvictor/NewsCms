package blog.open1111.service;

import java.util.List;
import java.util.Map;

import blog.open1111.entity.Link;

/**
 * ��������Service�ӿ�
 * @author user
 *
 */
public interface LinkService {

	/**
	 * ����������ҳ��ѯ�������Ӽ���
	 * @param map
	 * @return
	 */
	public List<Link> list(Map<String,Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * ������������
	 * @param link
	 * @return
	 */
	public Integer add(Link link);
	
	/**
	 * �������Ӹ���
	 * @param link
	 * @return
	 */
	public Integer update(Link link);
	
	/**
	 * ��������ɾ��
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}