package news.ssp.service;

import java.util.List;
import java.util.Map;

import news.ssp.entity.Article;

/**
 * ����Service�ӿ�
 * @author user
 *
 */
public interface ArticleService {

	/**
	 * ��ѯִ����������µ����Ӹ���
	 * @param typeId
	 * @return
	 */
	public Integer getNumByTypeId(Integer typeId);
	
	/**
	 * ����������ҳ��ѯ���ͼ���
	 * @param map
	 * @return
	 */
	public List<Article> list(Map<String,Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * ��ȡ7����������
	 * @return
	 */
	public List<Article> getHot();
	
	/**
	 * ���������Ϣ
	 * @param article
	 * @return
	 */
	public Integer add(Article article);
	
	/**
	 * ɾ��������Ϣ
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * �޸�������Ϣ
	 * @param article
	 * @return
	 */
	public Integer update(Article article);
	
	/**
	 * ͨ��id��ѯ����
	 * @param id
	 * @return
	 */
	public Article findById(Integer id);
	
	/**
	 * ����������ҳ��ѯ���ͼ��� ���ݷ������ڽ�������
	 * @param map
	 * @return
	 */
	public List<Article> list2(Map<String,Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal2(Map<String,Object> map);
	
	/**
	 * ��ȡ��һ������
	 * @param id
	 * @return
	 */
	public Article getLastArticle(Integer id);
	
	/**
	 * ��ȡ��һ������
	 * @param id
	 * @return
	 */
	public Article getNextArticle(Integer id);
}
