package news.ssp.service;

import java.util.List;
import java.util.Map;

import news.ssp.entity.Article;

/**
 * 帖子Service接口
 * @author user
 *
 */
public interface ArticleService {

	/**
	 * 查询执行帖子类别下的帖子个数
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
	 * 获取7条热门帖子
	 * @return
	 */
	public List<Article> getHot();
	
	/**
	 * 添加帖子信息
	 * @param article
	 * @return
	 */
	public Integer add(Article article);
	
	/**
	 * 删除帖子信息
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 修改帖子信息
	 * @param article
	 * @return
	 */
	public Integer update(Article article);
	
	/**
	 * 通过id查询帖子
	 * @param id
	 * @return
	 */
	public Article findById(Integer id);
	
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
	 * 获取上一个帖子
	 * @param id
	 * @return
	 */
	public Article getLastArticle(Integer id);
	
	/**
	 * 获取下一个帖子
	 * @param id
	 * @return
	 */
	public Article getNextArticle(Integer id);
}
