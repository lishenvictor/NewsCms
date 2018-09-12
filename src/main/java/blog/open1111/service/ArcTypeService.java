package blog.open1111.service;

import java.util.List;
import java.util.Map;

import blog.open1111.entity.ArcType;

/**
 * 帖子类别Service接口
 * @author user
 *
 */
public interface ArcTypeService {

	/**
	 * 根据条件分页查询博客类别集合
	 * @param map
	 * @return
	 */
	public List<ArcType> list(Map<String,Object> map);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 博客类别添加
	 * @param arcType
	 * @return
	 */
	public Integer add(ArcType arcType);
	
	/**
	 * 博客类别更新
	 * @param arcType
	 * @return
	 */
	public Integer update(ArcType arcType);
	
	/**
	 * 博客类型删除
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
}
