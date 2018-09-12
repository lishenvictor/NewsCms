package blog.open1111.dao;

import java.util.List;
import java.util.Map;

import blog.open1111.entity.Link;

/**
 * 友情链接Dao接口
 * @author user
 *
 */
public interface LinkDao {

	/**
	 * 根据条件分页查询友情链接集合
	 * @param map
	 * @return
	 */
	public List<Link> list(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 友情链接添加
	 * @param link
	 * @return
	 */
	public Integer add(Link link);
	
	/**
	 * 友情链接更新
	 * @param link
	 * @return
	 */
	public Integer update(Link link);
	
	/**
	 * 友情链接删除
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}
