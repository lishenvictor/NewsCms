package news.ssp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import news.ssp.dao.LinkDao;
import org.springframework.stereotype.Service;

import news.ssp.entity.Link;
import news.ssp.service.LinkService;

/**
 * ��������Serviceʵ����
 * @author user
 *
 */
@Service("linkService")
public class LinkServiceImpl implements LinkService{

	@Resource
	private LinkDao linkDao;
	
	public List<Link> list(Map<String, Object> map) {
		return linkDao.list(map);
	}

	public Long getTotal(Map<String, Object> map) {
		return linkDao.getTotal(map);
	}

	public Integer add(Link link) {
		return linkDao.add(link);
	}

	public Integer update(Link link) {
		return linkDao.update(link);
	}

	public Integer delete(Integer id) {
		return linkDao.delete(id);
	}

}
