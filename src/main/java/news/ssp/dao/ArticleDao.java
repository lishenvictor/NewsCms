package news.ssp.dao;

import java.util.List;
import java.util.Map;

import news.ssp.entity.Article;

/**
 * 新闻Dao接口
 * @author user
 *
 */
public interface ArticleDao {

	/**
	 * 查询执行新闻类别下的新闻个数
	 * @param typeId
	 * @return
	 */
	public Integer getNumByTypeId(Integer typeId);
	
	/**
	 * 根据条件分页查询博客集合
	 * @param map
	 * @return
	 */
	public List<Article> list(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 根据条件分页查询博客集合 根据发布日期降序排列
	 * @param map
	 * @return
	 */
	public List<Article> list2(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal2(Map<String,Object> map);
	
	/**
	 * 获取7条热门新闻
	 * @return
	 */
	public List<Article> getHot();
	
	/**
	 * 添加新闻信息
	 * @param article
	 * @return
	 */
	public Integer add(Article article);
	
	/**
	 * 修改新闻信息
	 * @param article
	 * @return
	 */
	public Integer update(Article article);
	
	/**
	 * 删除新闻信息
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 通过id查询新闻
	 * @param id
	 * @return
	 */
	public Article findById(Integer id);
	
	/**
	 * 获取上一个新闻
	 * @param id
	 * @return
	 */
	public Article getLastArticle(Integer id);
	
	/**
	 * 获取下一个新闻
	 * @param id
	 * @return
	 */
	public Article getNextArticle(Integer id);
}
