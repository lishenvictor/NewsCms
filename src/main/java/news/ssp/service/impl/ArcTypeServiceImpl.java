package news.ssp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import news.ssp.dao.ArcTypeDao;
import org.springframework.stereotype.Service;

import news.ssp.entity.ArcType;
import news.ssp.service.ArcTypeService;

/**
 * 新闻类别Service实现类
 * @author user
 *
 */
@Service("arcTypeService")
public class ArcTypeServiceImpl implements ArcTypeService{

	@Resource
	private ArcTypeDao arcTypeDao;
	
	public List<ArcType> list(Map<String, Object> map) {
		return arcTypeDao.list(map);
	}

	public Long getTotal(Map<String, Object> map) {
		return arcTypeDao.getTotal(map);
	}

	public Integer add(ArcType arcType) {
		return arcTypeDao.add(arcType);
	}

	public Integer update(ArcType arcType) {
		return arcTypeDao.update(arcType);
	}

	public Integer delete(Integer id) {
		return arcTypeDao.delete(id);
	}

}
